/**
 * 初始化妇产科详情对话框
 */
var ExaminationGynaecologyAndObstetricsInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationGynaecologyAndObstetricsInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationGynaecologyAndObstetrics.layerIndex);
}


/**
 * 提交添加
 */
ExaminationGynaecologyAndObstetricsInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationGynaecologyAndObstetrics/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationGynaecologyAndObstetrics.table.refresh();
        ExaminationGynaecologyAndObstetricsInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationGynaecologyAndObstetricsInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationGynaecologyAndObstetrics/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationGynaecologyAndObstetrics.table.refresh();
        ExaminationGynaecologyAndObstetricsInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
