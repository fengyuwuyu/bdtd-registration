/**
 * 处方药详情管理初始化
 */
var PatientPrescriptionMedicineInfo = {
    id: "PatientPrescriptionMedicineInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientPrescriptionMedicineInfo.close = function() {
    parent.layer.close(window.parent.TakeMedical.layerIndex);
};

/**
 * 初始化表格的列
 */
PatientPrescriptionMedicineInfo.initColumn = function () {
    return [
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '药品', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
            {title: '周期', field: 'period', visible: true, align: 'center', valign: 'middle'},
            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

PatientPrescriptionMedicineInfo.takeMedical = function () {
	Feng.confirm('请确认是否取药？', function(){
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInfo/confirmTakeMedical', {patientInfoId: PatientPrescriptionMedicineInfo.getPatientInfoId()}, function(data) {
			if (data.code == 200) {
				PatientPrescriptionMedicineInfo.close();
				window.parent.TakeMedical.search();
			}
		});
	});
};

PatientPrescriptionMedicineInfo.getPatientInfoId = function() {
	return $('#patientInfoId').val();
}

PatientPrescriptionMedicineInfo.refresh = function () {
	var queryData = {patientInfoId: PatientInfo.id};
	PatientPrescriptionMedicineInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientPrescriptionMedicineInfo.initColumn();
    var table = new BSTable(PatientPrescriptionMedicineInfo.id, "/patientPrescriptionMedicineInfo/list", defaultColunms, {height: '100%', pagination: false, showColumns: false, showRefresh:false});
    table.setQueryParams({patientInfoId: PatientPrescriptionMedicineInfo.getPatientInfoId()});
    PatientPrescriptionMedicineInfo.table = table.init();
});
