/**
 * 初始化耳鼻喉科详情对话框
 */
var ExaminationEntInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationEntInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationEnt.layerIndex);
}


/**
 * 提交添加
 */
ExaminationEntInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationEnt/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationEnt.table.refresh();
        ExaminationEntInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationEntInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationEnt/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationEnt.table.refresh();
        ExaminationEntInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
