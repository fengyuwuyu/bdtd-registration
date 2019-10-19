package com.bdtd.card.registration.modular.alert.model;

import java.sql.Date;

public class AlertFever {

    private Long depSerial;
    private String depName;
    private Integer count;
    private Date beginDate;
    private Date endDate;

    public AlertFever() {
    }

    public AlertFever(Long depSerial, String depName, Integer count, Date beginDate, Date endDate) {
        super();
        this.depSerial = depSerial;
        this.depName = depName;
        this.count = count;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Long getDepSerial() {
        return depSerial;
    }

    public void setDepSerial(Long depSerial) {
        this.depSerial = depSerial;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AlertFever [depSerial=" + depSerial + ", depName=" + depName + ", count=" + count + ", beginDate="
                + beginDate + ", endDate=" + endDate + "]";
    }

}
