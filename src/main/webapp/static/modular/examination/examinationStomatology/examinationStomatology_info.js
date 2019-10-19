/**
 * 初始化口腔科详情对话框
 */
var ExaminationStomatologyInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationStomatologyInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationStomatology.layerIndex);
}


/**
 * 提交添加
 */
ExaminationStomatologyInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationStomatology/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationStomatology.table.refresh();
        ExaminationStomatologyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationStomatologyInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationStomatology/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationStomatology.table.refresh();
        ExaminationStomatologyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
