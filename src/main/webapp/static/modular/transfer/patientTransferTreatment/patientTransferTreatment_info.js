/**
 * 初始化转诊详情对话框
 */
var PatientTransferTreatmentInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientTransferTreatmentInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientTransferTreatment.layerIndex);
}

PatientTransferTreatmentInfoDlg.updateRemark = function() {
	var remarkForm = $('#remarkForm');
	if (!Feng.validateForm(remarkForm)) {
		return;
	}
	var data = remarkForm.serializeObject();
	console.log(data)
	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferTreatment/updateRemark', data, function(data) {
		if (data.success) {
			window.parent.PatientTransferTreatment.table.refresh();
	        PatientTransferTreatmentInfoDlg.close();
		}
	});
};

/**
 * 提交添加
 */
PatientTransferTreatmentInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientTransferTreatment/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientTransferTreatment.table.refresh();
        PatientTransferTreatmentInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientTransferTreatmentInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();
	console.log(window.parent.PatientTransferTreatment.ids)
	data['ids'] = JSON.stringify(window.parent.PatientTransferTreatment.ids);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientTransferTreatment/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientTransferTreatment.table.refresh();
        PatientTransferTreatmentInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
