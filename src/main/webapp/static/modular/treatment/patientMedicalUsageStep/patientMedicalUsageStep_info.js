/**
 * 初始化药品使用步骤详情对话框
 */
var PatientMedicalUsageStepInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientMedicalUsageStepInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientMedicalUsageStep.layerIndex);
}


/**
 * 提交添加
 */
PatientMedicalUsageStepInfoDlg.addSubmit = function() {
	console.log(window.parent.PatientInfo);
	if (!window.parent.PatientInfo.id) {
		Feng.info("请先选择要诊断的病人！");
		return;
	}
	
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientMedicalUsageStep/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientMedicalUsageStep.table.refresh();
        PatientMedicalUsageStepInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientMedicalUsageStepInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientMedicalUsageStep/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientMedicalUsageStep.table.refresh();
        PatientMedicalUsageStepInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
