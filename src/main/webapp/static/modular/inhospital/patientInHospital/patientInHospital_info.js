/**
 * 初始化住院信息录入详情对话框
 */
var PatientInHospitalInfoDlg = {

};

/**
 * 关闭此对话框
 */
PatientInHospitalInfoDlg.close = function() {
	if (window.parent.PatientInHospitalQuery) {
		parent.layer.close(window.parent.PatientInHospitalQuery.layerIndex);
	} else {
		parent.layer.close(window.parent.PatientInHospital.layerIndex);
	}
}


/**
 * 提交添加
 */
PatientInHospitalInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientInHospital/add", function(data){
        Feng.success("添加成功!");
        window.parent.PatientInHospital.table.refresh();
        PatientInHospitalInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientInHospitalInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientInHospital/update", function(data){
        Feng.success("修改成功!");
        if (window.parent.PatientInHospitalQuery) {
        	window.parent.PatientInHospitalQuery.table.refresh();
        } else {
        	window.parent.PatientInHospital.table.refresh();
        }
        PatientInHospitalInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}


$(function() {

});
