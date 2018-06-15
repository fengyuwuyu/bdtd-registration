/**
 * 初始化药品二级库存管理详情对话框
 */
var MedicalInventorySecondLevelInfoDlg = {
    medicalInventorySecondLevelInfoData : {}
};

/**
 * 清除数据
 */
MedicalInventorySecondLevelInfoDlg.clearData = function() {
    this.medicalInventorySecondLevelInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MedicalInventorySecondLevelInfoDlg.set = function(key, val) {
    this.medicalInventorySecondLevelInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MedicalInventorySecondLevelInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MedicalInventorySecondLevelInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventorySecondLevel.layerIndex);
}

/**
 * 收集数据
 */
MedicalInventorySecondLevelInfoDlg.collectData = function() {
    this
    .set('id')
    .set('parentId')
    .set('produceBatchNum')
    .set('createDate')
    .set('expireDate')
    .set('price')
    .set('inventoryNum')
    .set('unit')
    .set('inboundChannel')
    .set('medicalInventoryStairId');
}

/**
 * 提交添加
 */
MedicalInventorySecondLevelInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventorySecondLevel/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventorySecondLevel.table.refresh();
        MedicalInventorySecondLevelInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventorySecondLevelInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventorySecondLevelInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventorySecondLevel/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventorySecondLevel.table.refresh();
        MedicalInventorySecondLevelInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventorySecondLevelInfoData);
    ajax.start();
}

MedicalInventorySecondLevelInfoDlg.putInStorage = function() {
	this.clearData();
	this.set('id').set('inventoryNum', Math.abs(parseInt($('#inventoryNum').val())));
	
	var ajax = new $ax(Feng.ctxPath + "/medicalInventorySecondLevel/storage", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventorySecondLevel.table.refresh();
        MedicalInventorySecondLevelInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventorySecondLevelInfoData);
    ajax.start();
}

MedicalInventorySecondLevelInfoDlg.outOfStorage = function() {
	this.clearData();
	this.set('id').set('inventoryNum', Math.abs(parseInt($('#inventoryNum').val())) * -1);
	
	var ajax = new $ax(Feng.ctxPath + "/medicalInventorySecondLevel/storage", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventorySecondLevel.table.refresh();
        MedicalInventorySecondLevelInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventorySecondLevelInfoData);
    ajax.start();
}

$(function() {

});
