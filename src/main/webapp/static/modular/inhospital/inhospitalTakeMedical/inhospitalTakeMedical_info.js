/**
 * 初始化详情对话框
 */
var InhospitalTakeMedicalInfoDlg = {

};

/**
 * 关闭此对话框
 */
InhospitalTakeMedicalInfoDlg.close = function() {
    parent.layer.close(window.parent.InhospitalTakeMedical.layerIndex);
}


/**
 * 提交添加
 */
InhospitalTakeMedicalInfoDlg.submitPrescription = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();
	
	Feng.ajaxAsyncJson(Feng.ctxPath + "/inhospitalTakeMedical/prescription", data, function(data) {
		if (data.success) {
			if (window.parent.InHospitalLongtermMedicineInfo) {
				window.parent.InHospitalLongtermMedicineInfo.table.refresh();
				parent.layer.close(window.parent.InHospitalLongtermMedicineInfo.layerIndex);
			} else if (window.parent.InHospitalTemporaryMedicineInfo) {
				window.parent.InHospitalTemporaryMedicineInfo.table.refresh();
				parent.layer.close(window.parent.InHospitalTemporaryMedicineInfo.layerIndex);
			}
		}
	})
}
/**
 * 提交添加
 */
InhospitalTakeMedicalInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inhospitalTakeMedical/add", function(data){
        Feng.success("添加成功!");
        window.parent.InhospitalTakeMedical.table.refresh();
        InhospitalTakeMedicalInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
InhospitalTakeMedicalInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inhospitalTakeMedical/update", function(data){
        Feng.success("修改成功!");
        window.parent.InhospitalTakeMedical.table.refresh();
        InhospitalTakeMedicalInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
