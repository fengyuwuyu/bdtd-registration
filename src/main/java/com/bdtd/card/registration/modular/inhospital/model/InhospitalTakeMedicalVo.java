package com.bdtd.card.registration.modular.inhospital.model;

import java.util.Arrays;

public class InhospitalTakeMedicalVo {

    private Long[] ids;
    private Integer type;
    private Integer inhospitalId;
    private String physicianName;
    private String remark;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInhospitalId() {
        return inhospitalId;
    }

    public void setInhospitalId(Integer inhospitalId) {
        this.inhospitalId = inhospitalId;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InhospitalTakeMedicalVo [ids=" + Arrays.toString(ids) + ", type=" + type + ", inhospitalId="
                + inhospitalId + ", physicianName=" + physicianName + ", remark=" + remark + "]";
    }

}
