package com.bdtd.card.registration.common.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.stylefeng.guns.core.common.annotion.EnumEntity;
import com.stylefeng.guns.core.common.annotion.EnumEntityList;
import com.stylefeng.guns.core.util.DictCacheFactory;

@Aspect
@Component
@ConditionalOnBean(value=DictCacheFactory.class)
public class EnumHandlerAop {
	
	private static final String SUFFIX = "ItemList";
	
	@Pointcut(value = "@annotation(com.stylefeng.guns.core.common.annotion.EnumEntityList)")
    public void cutService() {
    }
	
	@Around("cutService()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object result = point.proceed();
		MethodSignature msig = check(point);
		handleEnumEntitys(point, msig, result);
		return result;
		
	}
	
	private MethodSignature check(ProceedingJoinPoint point) {
		Signature sig = point.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        return (MethodSignature)sig;
	}

	@SuppressWarnings("unchecked")
	private void handleEnumEntitys(ProceedingJoinPoint point, MethodSignature msig, Object result) throws NoSuchMethodException, SecurityException {
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		EnumEntityList enumHandler = currentMethod.getAnnotation(EnumEntityList.class);
		EnumEntity[] enumEntitys = enumHandler.entityList();
		if (enumEntitys == null || enumEntitys.length == 0) {
			return;
		}
		
		List<EnumAdapterEntity> enumWrapperEntities = new ArrayList<>();
        for (EnumEntity enumEntity : enumEntitys) {
            enumWrapperEntities.add(new EnumAdapterEntity(enumEntity));
        }
        
        handleDictModels(point, enumWrapperEntities);
        
		List<Map<String, Object>> list = null;
		if (result instanceof List) {
			list = (List<Map<String, Object>>) result;
		} else if (result instanceof HashMap){
			Map<String, Object> pageMap = (Map<String, Object>) result;
			if (!pageMap.containsKey("rows")) {
			    return ;
			}
			list = (List<Map<String, Object>>) (pageMap.get("rows"));
		} else {
		    return;
		}
        
		EnumAdapterFactory.adapterRows(list, enumWrapperEntities);
	}
	
	private Model getModel(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
	    Model model = null;
        for (Object obj : args) {
            if (obj != null && Model.class.isAssignableFrom(obj.getClass())) {
                model = (Model)obj;
                break;
            }
        }
        return model;
	}

	private void handleDictModels(ProceedingJoinPoint point, List<EnumAdapterEntity> enumWrapperEntities) throws NoSuchMethodException, SecurityException {
        Model model = getModel(point);
        
        if (model == null) {
        	return;
        }
        
        enumWrapperEntities.forEach((item) -> {
        	model.addAttribute(item.getFieldName() + SUFFIX, EnumAdapterFactory.getItemList(item.getEnumName()));
        });
	}
	
}
