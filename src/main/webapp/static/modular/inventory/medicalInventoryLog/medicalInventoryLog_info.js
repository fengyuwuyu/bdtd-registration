/**
 * 初始化药品出入库记录详情对话框
 */
var MedicalInventoryLogInfoDlg = {
    medicalInventoryLogInfoData : {}
};

/**
 * 清除数据
 */
MedicalInventoryLogInfoDlg.clearData = function() {
    this.medicalInventoryLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MedicalInventoryLogInfoDlg.set = function(key, val) {
    this.medicalInventoryLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MedicalInventoryLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MedicalInventoryLogInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventoryLog.layerIndex);
}

/**
 * 收集数据
 */
MedicalInventoryLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('medicalName')
    .set('spell')
    .set('producer')
    .set('specification')
    .set('unit')
    .set('produceBatchNum')
    .set('produceDate')
    .set('expireDate')
    .set('price')
    .set('amount')
    .set('inboundChannel')
    .set('logDate');
}

/**
 * 提交添加
 */
MedicalInventoryLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventoryLog.table.refresh();
        MedicalInventoryLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventoryLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventoryLog.table.refresh();
        MedicalInventoryLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventoryLogInfoData);
    ajax.start();
}

$(function() {

});
