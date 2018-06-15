package com.bdtd.card.registration.modular.system.model;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 药品二级库存管理
 * </p>
 *
 * @author lilei123
 * @since 2018-06-14
 */
@TableName("bdtd_medical_inventory_second_level")
public class MedicalInventorySecondLevel extends Model<MedicalInventorySecondLevel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 生产批号
     */
    @TableField("produce_batch_num")
    private String produceBatchNum;
    @TableField("create_date")
    private Date createDate;
    @TableField("expire_date")
    private Date expireDate;
    private Double price;
    /**
     * 库存数量
     */
    @TableField("inventory_num")
    private Long inventoryNum;
    private Integer unit;
    /**
     * 进货渠道
     */
    @TableField("inbound_channel")
    private Integer inboundChannel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getProduceBatchNum() {
        return produceBatchNum;
    }

    public void setProduceBatchNum(String produceBatchNum) {
        this.produceBatchNum = produceBatchNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Long getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Long inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getInboundChannel() {
        return inboundChannel;
    }

    public void setInboundChannel(Integer inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MedicalInventorySecondLevel{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", produceBatchNum=" + produceBatchNum +
        ", createDate=" + createDate +
        ", expireDate=" + expireDate +
        ", price=" + price +
        ", inventoryNum=" + inventoryNum +
        ", unit=" + unit +
        ", inboundChannel=" + inboundChannel +
        "}";
    }
}
