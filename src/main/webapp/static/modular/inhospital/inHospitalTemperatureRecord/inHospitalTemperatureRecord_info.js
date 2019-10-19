/**
 * 初始化体温记录详情对话框
 */
var InHospitalTemperatureRecordInfoDlg = {

};

/**
 * 关闭此对话框
 */
InHospitalTemperatureRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientInHospital.layerIndex);
}


/**
 * 提交添加
 */
InHospitalTemperatureRecordInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalTemperatureRecord/add", function(data){
        Feng.success("添加成功!");
        InHospitalTemperatureRecordInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
InHospitalTemperatureRecordInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalTemperatureRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.InHospitalTemperatureRecord.table.refresh();
        InHospitalTemperatureRecordInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
