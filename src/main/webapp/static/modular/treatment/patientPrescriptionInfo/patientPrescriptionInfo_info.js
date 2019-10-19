/**
 * 初始化患者处方详情对话框
 */
var PatientPrescriptionInfoInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientPrescriptionInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientPrescriptionInfo.layerIndex);
}


/**
 * 提交添加
 */
PatientPrescriptionInfoInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientPrescriptionInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientPrescriptionInfo.table.refresh();
        PatientPrescriptionInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientPrescriptionInfoInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientPrescriptionInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientPrescriptionInfo.table.refresh();
        PatientPrescriptionInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
