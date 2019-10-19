/**
 * 初始化处方药详情详情对话框
 */
var PatientPrescriptionMedicineInfoInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientPrescriptionMedicineInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientPrescriptionMedicineInfo.layerIndex);
}


/**
 * 提交添加
 */
PatientPrescriptionMedicineInfoInfoDlg.addSubmit = function() {
	var patientInfoId = window.parent.PatientInfo.id; 
	if (!patientInfoId) {
		Feng.info("请先选择要诊断的患者！");
		return;
	}
	
	
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();
	if (!data['medicineId']) {
		Feng.info("请先选择正确的药品！");
		return;
	}
		
	data['patientInfoId'] = patientInfoId;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientPrescriptionMedicineInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientPrescriptionMedicineInfo.table.refresh();
        PatientPrescriptionMedicineInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientPrescriptionMedicineInfoInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientPrescriptionMedicineInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientPrescriptionMedicineInfo.table.refresh();
        PatientPrescriptionMedicineInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {
	MedicalCommon.initMedicalSearch();
});
