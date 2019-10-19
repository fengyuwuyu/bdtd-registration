package com.bdtd.card.registration.common.utils.model;

import java.util.function.Function;

import com.stylefeng.guns.core.common.annotion.EnumEntity;

public class EnumAdapterEntity {

    private String fieldName;
    private String enumName;
    private String replaceName;
    private Function<Object, Object> adapterFunc;

    public EnumAdapterEntity() {
    }
    
    public EnumAdapterEntity(EnumEntity entity) {
        this.fieldName = entity.fieldName();
        this.enumName = entity.enumName();
        this.replaceName = entity.replaceName();
    }

    public EnumAdapterEntity(String fieldName, String enumName) {
        this.fieldName = fieldName;
        this.enumName = enumName;
    }

    public EnumAdapterEntity(String fieldName, String enumName, Function<Object, Object> adapterFunc) {
        this.fieldName = fieldName;
        this.enumName = enumName;
        this.adapterFunc = adapterFunc;
    }

    public Function<Object, Object> getAdapterFunc() {
        return adapterFunc;
    }

    public void setAdapterFunc(Function<Object, Object> adapterFunc) {
        this.adapterFunc = adapterFunc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getReplaceName() {
        return replaceName;
    }

    public void setReplaceName(String replaceName) {
        this.replaceName = replaceName;
    }

    @Override
    public String toString() {
        return "EnumAdapterEntity [fieldName=" + fieldName + ", enumName=" + enumName + ", replaceName=" + replaceName
                + ", adapterFunc=" + adapterFunc + "]";
    }

}
