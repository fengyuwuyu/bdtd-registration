/**
 * 初始化报警部门详情对话框
 */
var AlertUpdateConfigDlg = {

};

/**
 * 关闭此对话框
 */
AlertUpdateConfigDlg.close = function() {
    parent.layer.close(window.parent.Fever.layerIndex);
}

/**
 * 提交修改
 */
AlertUpdateConfigDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/alertFever/updateConfig", function(data){
        Feng.success("修改成功!");
        window.parent.Fever.table.refresh();
        AlertUpdateConfigDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
