/**
 * 初始化就诊管理详情对话框
 */
var PrescriptionMedicineInfoInfoDlg = {
    prescriptionMedicineInfoInfoData : {}
};

/**
 * 清除数据
 */
PrescriptionMedicineInfoInfoDlg.clearData = function() {
    this.prescriptionMedicineInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PrescriptionMedicineInfoInfoDlg.set = function(key, val) {
    this.prescriptionMedicineInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PrescriptionMedicineInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PrescriptionMedicineInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.PrescriptionMedicineInfo.layerIndex);
}

/**
 * 收集数据
 */
PrescriptionMedicineInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('medicineId')
    .set('medicineName')
    .set('amount')
    .set('unit')
    .set('usage')
    .set('period')
    .set('dosage')
    .set('patientPrescriptionId')
    .set('type')
    .set('createDate')
    .set('updateDate');
}

/**
 * 提交添加
 */
PrescriptionMedicineInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/prescriptionMedicineInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.PrescriptionMedicineInfo.table.refresh();
        PrescriptionMedicineInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prescriptionMedicineInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PrescriptionMedicineInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/prescriptionMedicineInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.PrescriptionMedicineInfo.table.refresh();
        PrescriptionMedicineInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prescriptionMedicineInfoInfoData);
    ajax.start();
}

$(function() {

});
