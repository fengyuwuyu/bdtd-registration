/**
 * 初始化临时医嘱药详情详情对话框
 */
var InHospitalTemporaryMedicineInfoInfoDlg = {

};

/**
 * 关闭此对话框
 */
InHospitalTemporaryMedicineInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.InHospitalTemporaryMedicineInfo.layerIndex);
}


/**
 * 提交添加
 */
InHospitalTemporaryMedicineInfoInfoDlg.addSubmit = function() {
	var inHospitalId = $('#inHospitalId').val();
	if (!inHospitalId) {
		Feng.error("服务器内部错误！");
		return;
	}
	
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();
	data['inHospitalId'] = inHospitalId;

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalTemporaryMedicineInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.InHospitalTemporaryMedicineInfo.table.refresh();
        InHospitalTemporaryMedicineInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
InHospitalTemporaryMedicineInfoInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalTemporaryMedicineInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.InHospitalTemporaryMedicineInfo.table.refresh();
        InHospitalTemporaryMedicineInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {
	if (window.MedicalCommon) {
		MedicalCommon.initMedicalSearch(function(index, row) {
			$('#unit').val(row.unit);
			$('#specification').val(row.specification);
			$('#medicalName').combogrid('setValue', row.medicalName);
			$('#medicineId').val(row.id);
		});
	}
});
