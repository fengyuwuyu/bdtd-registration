/**
 * 初始化病休管理详情对话框
 */
var PatientSickRestInfoDlg = {
	id: null,
	loadForm: function() {
		if (PatientInfo.checkOnDiagnosis()) {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientSickRest/findByPatientInfoId', {patientInfoId: PatientInfo.id}, function(data) {
				if (data.success && data.data) {
					$('#sickRestForm').form('load', data.data);
					PatientSickRestInfoDlg.id = data.data.id;
				}
			});
			
		}
	},
	cancelSickRest: function() {
		if (!PatientInfo.checkOnDiagnosis()) {
			return;
		}
		
		Feng.confirm('确定要取消病休诊断吗？', function() {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientSickRest/cancelSickRest', {patientInfoId: PatientInfo.id}, function(data) {
				if (data.code == 200) {
					$('#sickRestForm').form('reset');
					PatientSickRestInfoDlg.id = null;
				}
			});
		});
	}
};

/**
 * 关闭此对话框
 */
PatientSickRestInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientSickRest.layerIndex);
}


/**
 * 提交添加
 */
PatientSickRestInfoDlg.addSubmit = function() {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	if (!PatientInfo.submitPatientInfo()) {
		return;
	}
	
	var addForm = $('#sickRestForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	if (PatientSickRestInfoDlg.id) {
		PatientSickRestInfoDlg.editSubmit();
		return;
	}
	
	var data = addForm.serializeObject();
	data.patientInfoId = PatientInfo.id;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientSickRest/add", function(data){
        Feng.success("添加成功!");
        PatientSickRestInfoDlg.id = data.id
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientSickRestInfoDlg.editSubmit = function() {
	var editForm = $('#sickRestForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientSickRest/update", function(data){
        Feng.success("修改成功!");
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
