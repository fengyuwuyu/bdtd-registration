/**
 * 处方药详情管理初始化
 */
var InhospitalTakeMedicalTakeMedical = {
    id: "PatientPrescriptionMedicineInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

InhospitalTakeMedicalTakeMedical.close = function() {
    parent.layer.close(window.parent.InhospitalTakeMedical.layerIndex);
};

/**
 * 初始化表格的列
 */
InhospitalTakeMedicalTakeMedical.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InhospitalTakeMedicalTakeMedical.id)
        },
        {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
        {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
        {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
        {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
        {title: '次/日', field: 'period', visible: true, align: 'center', valign: 'middle'},
        {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'}
           /* {title: '军医', field: 'physicianName', visible: false, align: 'center', valign: 'middle'}*/
    ];
};

InhospitalTakeMedicalTakeMedical.takeMedical = function () {
	Feng.confirm('请确认是否取药？', function(){
	    var id = $('#id').val();
	    var type = $('#type').val();
		Feng.ajaxAsyncJson(Feng.ctxPath + '/inhospitalTakeMedical/takeMedical', {id: id, type: type}, function(data) {
			if (data.success) {
				InhospitalTakeMedicalTakeMedical.close();
				window.parent.InhospitalTakeMedical.search();
			}
		});
	});
};

InhospitalTakeMedicalTakeMedical.getPatientInfoId = function() {
	return $('#patientInfoId').val();
}

InhospitalTakeMedicalTakeMedical.refresh = function () {
	var queryData = {patientInfoId: PatientInfo.id};
	InhospitalTakeMedicalTakeMedical.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InhospitalTakeMedicalTakeMedical.initColumn();
    var table = new BSTable(InhospitalTakeMedicalTakeMedical.id, "/inhospitalTakeMedical/medicalList", defaultColunms, {height: 450});
    var id = $('#id').val();
    var type = $('#type').val();
    table.setQueryParams({id: id, type: type});
    InhospitalTakeMedicalTakeMedical.table = table.init();
});
