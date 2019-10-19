/**
 * 病患信息管理初始化
 */
var PatientInfoDetail = {
};


PatientInfoDetail.initTables = function () {
	var patientInfoId = $('#patientInfoId').val();
	var handleType = $('#handleType').val();
	
	if ((handleType & 1) == 1) {
		var prescriptionTable = new BSTable("PatientPrescriptionMedicineInfoTable", "/patientPrescriptionMedicineInfo/list", PatientInfoDetail.getDefaultColumnByHandleType(1), {showRefresh:false, showColumns: false, height: '100%', pagination: false});
		prescriptionTable.setQueryParams({patientInfoId: patientInfoId});
		prescriptionTable.init();
	}
	
	if ((handleType & 2) == 2) {
		var usageStepTable = new BSTable("PatientMedicalUsageStepTable", "/patientMedicalUsageStep/list", PatientInfoDetail.getDefaultColumnByHandleType(2), {showRefresh:false, showColumns: false, height: '100%', pagination: false});
		usageStepTable.setQueryParams({patientInfoId: patientInfoId});
		usageStepTable.init();
	}
	
	if ((handleType & 4) == 4) {
		var radiateExamineTable = new BSTable("PatientRadiateExamineTable", "/patientRadiateExamine/list", PatientInfoDetail.getDefaultColumnByHandleType(4), {showRefresh:false, showColumns: false, height: '100%', pagination: false});
		radiateExamineTable.setQueryParams({patientInfoId: patientInfoId});
		radiateExamineTable.init();
	}
	
	if ((handleType & 64) == 64) {
		var physicalTherapyTable = new BSTable("PatientPhysicalTherapyTable", "/patientPhysicalTherapy/findByPatientInfoId", PatientInfoDetail.getDefaultColumnByHandleType(64), {showRefresh:false, showColumns: false, height: '100%', pagination: false});
		physicalTherapyTable.setQueryParams({patientInfoId: patientInfoId});
		physicalTherapyTable.init();
	}
	
};

PatientInfoDetail.openUsageStepMedicalEdit = function (id) {
	if (id || this.check()) {
		var index = layer.open({
			type: 2,
			title: '药品使用步骤详情',
			area: ['1000px', '700px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInfo/usageStepDetail/' + id
		});
	}
};

PatientInfoDetail.getDefaultColumnByHandleType = function(handleType) {
	switch(handleType) {
	case 1:
		return [
        {field: 'selectItem', radio: true},
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
	case 2:
		return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '步骤',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc("PatientMedicalUsageStepTable")
            },
            {title: '医嘱内容', field: '', visible: true, align: 'center', valign: 'middle', formatter: function(value, row, index) {
            	return '<a href="javascript:void(0);" onclick="PatientInfoDetail.openUsageStepMedicalEdit('+row['id']+');">详情</a>';
            }},
            {title: '患者信息表id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
    ];
	case 4:
		return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc("PatientRadiateExamineTable")
            },
            {title: '部位', field: 'bodyPart', visible: true, align: 'center', valign: 'middle'},
            {title: '体位', field: 'position', visible: true, align: 'center', valign: 'middle'},
            
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
    ];
	case 64:
		return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc("PatientPhysicalTherapyTable")
            },
            {title: '理疗方式', field: 'phyrapyType', visible: true, align: 'center', valign: 'middle'},
            {title: '开始日期', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '次/日', field: 'countPerDay', visible: true, align: 'center', valign: 'middle'},
            {title: '天数', field: 'dayCount', visible: true, align: 'center', valign: 'middle'},
    ];
	}
}


$(function () {
    PatientInfoDetail.initTables();
});
