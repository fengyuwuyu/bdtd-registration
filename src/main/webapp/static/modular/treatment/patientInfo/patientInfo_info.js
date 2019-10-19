/**
 * 初始化病患信息详情对话框
 */
var PatientInfoInfoDlg = {
};

/**
 * 关闭此对话框
 */
PatientInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientInfo.layerIndex);
}

PatientInfoInfoDlg.searchUser = function() {
	var searchUserForm = $('#searchUserForm');
	if (!Feng.validateForm(searchUserForm)) {
		return;
	}
	
	var data = searchUserForm.serializeObject();
	
	//提交信息
	var ajax = new $ax(Feng.ctxPath + "/registration/patientInfo", function(data){
		$('#addForm').form('load', data);
	});
	ajax.set(data);
	ajax.start();
}

/**
 * 提交添加
 */
PatientInfoInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientInfo/add", function(data){
        Feng.success("添加成功!");
        PatientInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientInfoInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientInfo/update", function(data){
        Feng.success("修改成功!");
//        window.parent.PatientInfo.table.refresh();
        PatientInfoInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
};

$(function() {
	UserSearchCommon.initUserNameSearch();
});
