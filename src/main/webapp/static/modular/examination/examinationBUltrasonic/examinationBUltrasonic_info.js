/**
 * 初始化B超详情对话框
 */
var ExaminationBUltrasonicInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationBUltrasonicInfoDlg.close = function() {
    parent.layer.close(window.parent.ExaminationBUltrasonic.layerIndex);
}


/**
 * 提交添加
 */
ExaminationBUltrasonicInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationBUltrasonic/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExaminationBUltrasonic.table.refresh();
        ExaminationBUltrasonicInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
ExaminationBUltrasonicInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/examinationBUltrasonic/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExaminationBUltrasonic.table.refresh();
        ExaminationBUltrasonicInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
