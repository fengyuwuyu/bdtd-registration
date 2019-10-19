/**
 * 初始化门诊处置详情对话框
 */
var PatientClinicDisposisInfoDlg = {
		id: null
};

/**
 * 关闭此对话框
 */
PatientClinicDisposisInfoDlg.close = function() {
    parent.layer.close(window.parent.PatientClinicDisposis.layerIndex);
}

PatientClinicDisposisInfoDlg.resetForm = function() {
	$('#clinicDisgnosisForm').form('reset');
};

PatientClinicDisposisInfoDlg.loadForm = function() {
	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientClinicDisposis/findByPatientInfoId', {patientInfoId: PatientInfo.id}, function(data) {
		$('#clinicDisgnosisForm').form('load', data.data);
		if (data.data.skinTestMedicalId) {
			$('#skinTestMedicalId').combobox('setValue', data.data.skinTestMedicalId);
			PatientMedicalUsageStep.skinTestMedicalId = data.data.skinTestMedicalId;
		} else {
			PatientMedicalUsageStep.skinTestMedicalId = null;
		}
		
		PatientClinicDisposisInfoDlg.id = data.data.id;
		
	});
};
PatientClinicDisposisInfoDlg.cancelDisposis = function() {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	Feng.confirm('确定要取消门诊处置吗？', function() {
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientClinicDisposis/cancelDisposis', {patientClinicDisposisId: PatientClinicDisposisInfoDlg.id, patientInfoId: PatientInfo.id}, function(data) {
			PatientClinicDisposisInfoDlg.resetForm();
			PatientMedicalUsageStep.table.refresh();
		});
	});
}

/**
 * 提交添加
 */
PatientClinicDisposisInfoDlg.addSubmit = function() {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	if (!PatientInfo.submitPatientInfo()) {
		return;
	}
	
	if (PatientClinicDisposisInfoDlg.id) {
		PatientClinicDisposisInfoDlg.editSubmit();
		return;
	}
	
	var addForm = $('#clinicDisgnosisForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObject();
	data['patientInfoId'] = PatientInfo.id;
	
	if (data.skinTestMedicalId) {
		PatientMedicalUsageStep.skinTestMedicalId = data.skinTestMedicalId;
	}
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientClinicDisposis/add", function(data){
        Feng.success(data.message || "添加成功!");
        PatientClinicDisposisInfoDlg.id = data.id;
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
PatientClinicDisposisInfoDlg.editSubmit = function() {
	var editForm = $('#clinicDisgnosisForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObject();
	data.id = PatientClinicDisposisInfoDlg.id;
	
	if (data.skinTestMedicalId) {
		PatientMedicalUsageStep.skinTestMedicalId = data.skinTestMedicalId;
	}
	
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/patientClinicDisposis/update", function(data){
        Feng.success("修改成功!");
    });
    ajax.set(data);
    ajax.start();
}

$(function() {

});
