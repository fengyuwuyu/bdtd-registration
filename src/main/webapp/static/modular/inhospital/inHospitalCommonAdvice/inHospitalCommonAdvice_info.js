/**
 * 初始化常规医嘱详情对话框
 */
var InHospitalCommonAdviceInfoDlg = {

};

/**
 * 关闭此对话框
 */
InHospitalCommonAdviceInfoDlg.close = function() {
    parent.layer.close(window.parent.InHospitalCommonAdvice.layerIndex);
}


/**
 * 提交添加
 */
InHospitalCommonAdviceInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalCommonAdvice/add", function(data){
        Feng.success("添加成功!");
        window.parent.InHospitalCommonAdvice.table.refresh();
        InHospitalCommonAdviceInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
InHospitalCommonAdviceInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inHospitalCommonAdvice/update", function(data){
        Feng.success("修改成功!");
        window.parent.InHospitalCommonAdvice.table.refresh();
        InHospitalCommonAdviceInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
