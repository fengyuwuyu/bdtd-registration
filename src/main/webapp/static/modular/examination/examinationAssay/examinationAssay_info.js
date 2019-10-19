/**
 * 初始化化验详情对话框
 */
var ExaminationAssayInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationAssayInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationAssay.layerIndex);
}


/**
 * 提交添加
 */
ExaminationAssayInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationAssay/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationAssay.table.refresh();
        ExaminationAssayInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationAssayInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationAssay/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationAssay.table.refresh();
        ExaminationAssayInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
