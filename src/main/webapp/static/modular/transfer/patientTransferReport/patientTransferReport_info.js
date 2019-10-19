/**
 * 初始化转诊详情对话框
 */
var PatientTransferReportInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientTransferReportInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientTransferReport.layerIndex);
}


/**
 * 提交添加
 */
PatientTransferReportInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientTransferReport/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientTransferReport.table.refresh();
        PatientTransferReportInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientTransferReportInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientTransferReport/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientTransferReport.table.refresh();
        PatientTransferReportInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
