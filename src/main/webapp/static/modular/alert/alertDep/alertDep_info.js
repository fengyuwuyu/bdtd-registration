/**
 * 初始化报警部门详情对话框
 */
var AlertDepInfoDlg = {

};

/**
 * 关闭此对话框
 */
AlertDepInfoDlg.close = function() {
    parent.layer.close(window.parent.AlertDep.layerIndex);
}


/**
 * 提交添加
 */
AlertDepInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/alertDep/add", function(data){
        Feng.success("添加成功!");
        window.parent.AlertDep.table.refresh();
        AlertDepInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
AlertDepInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/alertDep/update", function(data){
        Feng.success("修改成功!");
        window.parent.AlertDep.table.refresh();
        AlertDepInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
