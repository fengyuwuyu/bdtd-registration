/**
 * 初始化评残信息详情对话框
 */
var PatientDisabilityRatingInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientDisabilityRatingInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientDisabilityRating.layerIndex);
}


/**
 * 提交添加
 */
PatientDisabilityRatingInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientDisabilityRating/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientDisabilityRating.table.refresh();
        PatientDisabilityRatingInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientDisabilityRatingInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientDisabilityRating/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientDisabilityRating.table.refresh();
        PatientDisabilityRatingInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {
	UserSearchCommon.initUserNameSearch();
});
