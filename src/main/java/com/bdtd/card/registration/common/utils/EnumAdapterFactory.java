package com.bdtd.card.registration.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.util.StringUtil;

public class EnumAdapterFactory {

    // private static String[] packages =
    // {"com.bdtd.card.registration.common.model"};
    private static Map<String, Map<Object, Object>> enumCache = new HashMap<>();
    private static Map<String, List<Map<String, Object>>> enumListCache = new HashMap<>();
    private static List<String> classList = Arrays.asList("com.bdtd.card.registration.common.model.EnumFeverStatus",
            "com.bdtd.card.registration.common.model.EnumBloodType",
            "com.bdtd.card.registration.common.model.EnumCrippleNature",
            "com.bdtd.card.registration.common.model.EnumDisabilityRate",
            "com.bdtd.card.registration.common.model.EnumDisabilityRateResult",
            "com.bdtd.card.registration.common.model.EnumExaminationStatusMask",
            "com.bdtd.card.registration.common.model.EnumHandleTypeMask",
            "com.bdtd.card.registration.common.model.EnumInHospitalStatus",
            "com.bdtd.card.registration.common.model.EnumLongTermAdviceGroup",
            "com.bdtd.card.registration.common.model.EnumMarried",
            "com.bdtd.card.registration.common.model.EnumMedicalInventoryCategory",
            "com.bdtd.card.registration.common.model.EnumMedicalInventoryStorageType",
            "com.bdtd.card.registration.common.model.EnumOriginMask",
            "com.bdtd.card.registration.common.model.EnumOutpatientType",
            "com.bdtd.card.registration.common.model.EnumPatientInfoStatus",
            "com.bdtd.card.registration.common.model.EnumPatientTransferStatus",
            "com.bdtd.card.registration.common.model.EnumPrescriptionStatus",
            "com.bdtd.card.registration.common.model.EnumSickRestDay",
            "com.bdtd.card.registration.common.model.EnumSickRestType",
            "com.bdtd.card.registration.common.model.EnumTemperatureRecord",
            "com.bdtd.card.registration.common.model.EnumInhospitalTakeMedicalStatus",
            "com.bdtd.card.registration.common.model.EnumInhospitalTakeMedicalType",
            "com.bdtd.card.registration.common.model.EnumUpgrouthSituation");

    private static final Logger log = LoggerFactory.getLogger(EnumAdapterFactory.class);

    static {
        try {
            EnumAdapterFactory.init();
        } catch (Exception e) {
            log.error("初始化枚举工具类失败", e);
        }
    }

    public static void init() throws Exception {
        // for (String p : packages) {
        // ClasspathPackageScanner scanner = new ClasspathPackageScanner(p);
        // List<String> classList =
        // scanner.getFullyQualifiedClassNameList(EnumClassType.ENUM);
        // init(classList);
        // }
        init(classList);
        // log.debug("enumCache = {}", enumCache);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void init(List<String> classList) throws ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // log.debug("classList = {}", classList);
        if (classList.size() == 0) {
            return;
        }

        for (String className : classList) {
            Class clazz = Class.forName(className);
            String simpleClassName = clazz.getSimpleName();
            Method method = clazz.getMethod(Consts.ENUM_SELECT_METHOD_NAME);
            List<Map<String, Object>> itemList = null;
            try {
                itemList = (List<Map<String, Object>>) method.invoke(null);
                enumListCache.put(simpleClassName, itemList);
            } catch (Exception e) {
                log.error(simpleClassName);
            }

            Map<Object, Object> map = new TreeMap<>();

            itemList.forEach((item) -> {
                Object type = item.get(Consts.ENUM_SELECT_ID_FIELD_NAME);
                Object desc = item.get(Consts.ENUM_SELECT_NAME_FIELD_NAME);
                map.put(type, desc);
            });

            enumCache.put(simpleClassName, map);
        }
    }

    public static void adapterRows(List<Map<String, Object>> rows, List<EnumAdapterEntity> enumAdapters) {
        if (rows == null || rows.size() == 0) {
            return;
        }

        if (enumAdapters == null || enumAdapters.size() == 0) {
            throw new IllegalArgumentException(String.format("illagel enumAdapters [%s]", enumAdapters));
        }

        rows.stream().forEach((item) -> {
            enumAdapters.stream().forEach((entity) -> {
                String fieldName = entity.getFieldName();
                String replaceName = entity.getReplaceName();
                Object value = item.get(fieldName);
                if (value == null) {
                    return;
                }

                Function<Object, Object> adapterFunc = entity.getAdapterFunc();

                if (!StringUtil.isNullEmpty(replaceName)) {
                    fieldName = replaceName;
                }
                
                if (adapterFunc == null) {
                    Object adapterValue = getAdapterValue(entity.getEnumName(), value);
                    item.put(fieldName, adapterValue);
                    if (adapterValue == null) {
                        log.warn(String.format("not found value in enum, entity = %s, value = %d", entity, value));
                    }
                } else {
                    item.put(fieldName, adapterFunc.apply(value));
                }
            });
        });

    }

    public static Object getAdapterValue(String enumName, Object key) {
        Map<Object, Object> enumMap = enumCache.get(enumName);
        if (enumMap == null) {
            return null;
        }
        return enumMap.get(key);
    }

    public static List<Map<String, Object>> getItemList(String enumName) {
        return enumListCache.get(enumName);
    }

}
