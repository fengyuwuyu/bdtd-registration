/**
 * 初始化一般检查详情对话框
 */
var ExaminationCommonInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationCommonInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationCommon.layerIndex);
}


/**
 * 提交添加
 */
ExaminationCommonInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationCommon/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationCommon.table.refresh();
        ExaminationCommonInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationCommonInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationCommon/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationCommon.table.refresh();
        ExaminationCommonInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
