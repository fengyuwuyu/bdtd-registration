package com.bdtd.card.registration.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 药品入库记录
 * </p>
 *
 * @author lilei123
 * @since 2018-06-14
 */
@TableName("bdtd_medical_inventory_log")
public class MedicalInventoryLog extends Model<MedicalInventoryLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("medical_name")
    private String medicalName;
    private String spell;
    private String producer;
    private Integer specification;
    private Integer unit;
    @TableField("produce_batch_num")
    private String produceBatchNum;
    @TableField("produce_date")
    private Date produceDate;
    @TableField("expire_date")
    private Date expireDate;
    private Double price;
    private Long amount;
    @TableField("inbound_channel")
    private String inboundChannel;
    @TableField("log_date")
    private Date logDate;


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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
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

    public String getProduceBatchNum() {
        return produceBatchNum;
    }

    public void setProduceBatchNum(String produceBatchNum) {
        this.produceBatchNum = produceBatchNum;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getInboundChannel() {
        return inboundChannel;
    }

    public void setInboundChannel(String inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MedicalInventoryLog{" +
        "id=" + id +
        ", medicalName=" + medicalName +
        ", spell=" + spell +
        ", producer=" + producer +
        ", specification=" + specification +
        ", unit=" + unit +
        ", produceBatchNum=" + produceBatchNum +
        ", produceDate=" + produceDate +
        ", expireDate=" + expireDate +
        ", price=" + price +
        ", amount=" + amount +
        ", inboundChannel=" + inboundChannel +
        ", logDate=" + logDate +
        "}";
    }
}
