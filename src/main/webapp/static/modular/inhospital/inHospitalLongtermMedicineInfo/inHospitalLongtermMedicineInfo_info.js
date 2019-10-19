/**
 * 初始化长期医嘱药详情详情对话框
 */
var InHospitalLongtermMedicineInfoInfoDlg = {

};

/**
 * 关闭此对话框
 */
InHospitalLongtermMedicineInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.InHospitalLongtermMedicineInfo.layerIndex);
}


/**
 * 提交添加
 */
InHospitalLongtermMedicineInfoInfoDlg.addSubmit = function() {
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
    var ajax = new $ax(Feng.ctxPath + "/inHospitalLongtermMedicineInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.InHospitalLongtermMedicineInfo.table.refresh();
        InHospitalLongtermMedicineInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
InHospitalLongtermMedicineInfoInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalLongtermMedicineInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.InHospitalLongtermMedicineInfo.table.refresh();
        InHospitalLongtermMedicineInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {
	MedicalCommon.initMedicalSearch(function(index, row) {
		$('#unit').val(row.unit);
		$('#specification').val(row.specification);
		$('#medicalName').combogrid('setValue', row.medicalName);
		$('#medicineId').val(row.id);
	});
});
