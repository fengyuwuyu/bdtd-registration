/**
 * 初始化眼科详情对话框
 */
var ExaminationOphthalmologyInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationOphthalmologyInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationOphthalmology.layerIndex);
}


/**
 * 提交添加
 */
ExaminationOphthalmologyInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationOphthalmology/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationOphthalmology.table.refresh();
        ExaminationOphthalmologyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationOphthalmologyInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationOphthalmology/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationOphthalmology.table.refresh();
        ExaminationOphthalmologyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
