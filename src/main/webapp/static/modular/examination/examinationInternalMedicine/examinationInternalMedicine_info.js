/**
 * 初始化内科检查详情对话框
 */
var ExaminationInternalMedicineInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationInternalMedicineInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationInternalMedicine.layerIndex);
}


/**
 * 提交添加
 */
ExaminationInternalMedicineInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationInternalMedicine/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationInternalMedicine.table.refresh();
        ExaminationInternalMedicineInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationInternalMedicineInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationInternalMedicine/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationInternalMedicine.table.refresh();
        ExaminationInternalMedicineInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
