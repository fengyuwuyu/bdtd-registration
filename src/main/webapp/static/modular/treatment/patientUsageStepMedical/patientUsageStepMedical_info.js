/**
 * 初始化处方使用步骤详情详情对话框
 */
var PatientUsageStepMedicalInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientUsageStepMedicalInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientUsageStepMedical.layerIndex);
}


/**
 * 提交添加
 */
PatientUsageStepMedicalInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();
	var medicalName = $('#medicineId').find("option:selected").text();
	data['medicalName'] = medicalName;
	console.log(medicalName);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientUsageStepMedical/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientUsageStepMedical.table.refresh();
        PatientUsageStepMedicalInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientUsageStepMedicalInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientUsageStepMedical/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientUsageStepMedical.table.refresh();
        PatientUsageStepMedicalInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
