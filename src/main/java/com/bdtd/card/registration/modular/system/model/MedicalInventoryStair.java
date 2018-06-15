package com.bdtd.card.registration.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 药品一级库存表
 * </p>
 *
 * @author lilei123
 * @since 2018-06-15
 */
@TableName("bdtd_medical_inventory_stair")
public class MedicalInventoryStair extends Model<MedicalInventoryStair> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 药品名称
     */
    @TableField("medical_name")
    private String medicalName;
    /**
     * 拼音
     */
    private String spell;
    /**
     * 生产商
     */
    private Integer producer;
    /**
     * 规格
     */
    private Integer specification;
    /**
     * 单位
     */
    private Integer unit;
    /**
     * 备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicalName() {
        return medicalName;
    }

    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public Integer getProducer() {
        return producer;
    }

    public void setProducer(Integer producer) {
        this.producer = producer;
    }

    public Integer getSpecification() {
        return specification;
    }

    public void setSpecification(Integer specification) {
        this.specification = specification;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MedicalInventoryStair{" +
        "id=" + id +
        ", medicalName=" + medicalName +
        ", spell=" + spell +
        ", producer=" + producer +
        ", specification=" + specification +
        ", unit=" + unit +
        ", remark=" + remark +
        "}";
    }
}
