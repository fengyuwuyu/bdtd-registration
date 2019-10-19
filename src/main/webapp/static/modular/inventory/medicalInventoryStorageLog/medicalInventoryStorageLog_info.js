/**
 * 初始化出入库记录详情对话框
 */
var MedicalInventoryStorageLogInfoDlg = {

};

/**
 * 关闭此对话框
 */
MedicalInventoryStorageLogInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventoryStorageLog.layerIndex);
}


/**
 * 提交添加
 */
MedicalInventoryStorageLogInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStorageLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventoryStorageLog.table.refresh();
        MedicalInventoryStorageLogInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryStorageLogInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStorageLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventoryStorageLog.table.refresh();
        MedicalInventoryStorageLogInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
