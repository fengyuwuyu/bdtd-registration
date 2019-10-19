/**
 * 初始化详情对话框
 */
var PatientDiabilityRatingInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientDiabilityRatingInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientDiabilityRating.layerIndex);
}


/**
 * 提交添加
 */
PatientDiabilityRatingInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientDiabilityRating/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientDiabilityRating.table.refresh();
        PatientDiabilityRatingInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientDiabilityRatingInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientDiabilityRating/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientDiabilityRating.table.refresh();
        PatientDiabilityRatingInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
