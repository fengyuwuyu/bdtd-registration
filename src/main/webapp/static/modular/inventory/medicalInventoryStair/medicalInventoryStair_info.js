/**
 * 初始化药品管理详情对话框
 */
var MedicalInventoryStairInfoDlg = {
    medicalInventoryStairInfoData : {}
};

/**
 * 清除数据
 */
MedicalInventoryStairInfoDlg.clearData = function() {
    this.medicalInventoryStairInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MedicalInventoryStairInfoDlg.set = function(key, val) {
    this.medicalInventoryStairInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MedicalInventoryStairInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MedicalInventoryStairInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventoryStair.layerIndex);
}

/**
 * 收集数据
 */
MedicalInventoryStairInfoDlg.collectData = function() {
    this
    .set('id')
    .set('medicalName')
    .set('spell')
    .set('producer')
    .set('specification')
    .set('unit')
    .set('remark');
}

/**
 * 提交添加
 */
MedicalInventoryStairInfoDlg.addSubmit = function() {

//    this.clearData();
//    this.collectData();

	if (!Feng.checkAddForm()) {
		return;
	}
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStair/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventoryStair.table.refresh();
        MedicalInventoryStairInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set($('#addForm').serializeObject());
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryStairInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStair/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventoryStair.table.refresh();
        MedicalInventoryStairInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.medicalInventoryStairInfoData);
    ajax.start();
}

$(function() {
	Feng.setSelectWidth();
});
