/**
 * 初始化转诊详情对话框
 */
var PatientTransferBillingInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientTransferBillingInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientTransferBilling.layerIndex);
}


/**
 * 提交添加
 */
PatientTransferBillingInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientTransferBilling/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientTransferBilling.table.refresh();
        PatientTransferBillingInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientTransferBillingInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();
	console.log(window.parent.PatientTransferBilling.ids)
	data['ids'] = JSON.stringify(window.parent.PatientTransferBilling.ids);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientTransferBilling/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientTransferBilling.table.refresh();
        PatientTransferBillingInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
