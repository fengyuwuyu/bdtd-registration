/**
 * 初始化理疗单详情对话框
 */
var PatientPhysicalTherapyInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientPhysicalTherapyInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientPhysicalTherapy.layerIndex);
}


/**
 * 提交添加
 */
PatientPhysicalTherapyInfoDlg.addSubmit = function() {
	if (!window.parent.PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();
	data.patientInfoId = window.parent.PatientInfo.id;

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientPhysicalTherapy/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientPhysicalTherapy.refresh();
        PatientPhysicalTherapyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientPhysicalTherapyInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientPhysicalTherapy/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientPhysicalTherapy.refresh();
        PatientPhysicalTherapyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
