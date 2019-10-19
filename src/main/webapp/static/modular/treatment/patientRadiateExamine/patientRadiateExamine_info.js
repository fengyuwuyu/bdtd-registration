/**
 * 初始化放射检查详情对话框
 */
var PatientRadiateExamineInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientRadiateExamineInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientRadiateExamine.layerIndex);
}


/**
 * 提交添加
 */
PatientRadiateExamineInfoDlg.addSubmit = function() {
	var patientInfoId = window.parent.PatientInfo.id;
	if (!patientInfoId) {
		Feng.msg('请先选择病人！');
		return;
	}
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();
	data['patientInfoId'] = patientInfoId;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientRadiateExamine/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientRadiateExamine.table.refresh();
        PatientRadiateExamineInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientRadiateExamineInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientRadiateExamine/update", function(data){
        Feng.success("修改成功!");
        window.parent.PatientRadiateExamine.table.refresh();
        PatientRadiateExamineInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
