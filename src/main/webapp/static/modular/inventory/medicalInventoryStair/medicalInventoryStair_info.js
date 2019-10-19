/**
 * 初始化详情对话框
 */
var MedicalInventoryStairInfoDlg = {

};

/**
 * 关闭此对话框
 */
MedicalInventoryStairInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventoryStair.layerIndex);
}


/**
 * 提交添加
 */
MedicalInventoryStairInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStair/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventoryStair.table.refresh();
        MedicalInventoryStairInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryStairInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStair/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventoryStair.table.refresh();
        MedicalInventoryStairInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
