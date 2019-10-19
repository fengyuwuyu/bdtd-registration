/**
 * 初始化外科检查详情对话框
 */
var ExaminationSurgeryInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationSurgeryInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationSurgery.layerIndex);
}


/**
 * 提交添加
 */
ExaminationSurgeryInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationSurgery/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationSurgery.table.refresh();
        ExaminationSurgeryInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationSurgeryInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationSurgery/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationSurgery.table.refresh();
        ExaminationSurgeryInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
