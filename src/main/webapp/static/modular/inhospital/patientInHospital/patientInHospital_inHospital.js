/**
 * 管理初始化
 */
var PatientInHospitalInHospital = {
    id: "PatientInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientInHospitalInHospital.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientInHospitalInHospital.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: false, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '单位id', field: 'orgId', visible: false, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '主要诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '就诊时间', field: 'clinicDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

PatientInHospitalInHospital.submitTransferPatient = function() {
	var transferPatientForm = $('#transferPatientForm');
	if (transferPatientForm.form('validate')) {
		var data = transferPatientForm.serializeObject();
		var patientInfoId = data.id;
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/transferToInHospital', {patientInfoId: patientInfoId}, function(data) {
			window.parent.PatientInHospital.table.refresh();
			PatientInHospitalInHospital.close();
		});
	}
};

PatientInHospitalInHospital.submitPreInHospital = function() {
	if (this.checkSelectMulti()) {
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/updateStatusByIds', {ids: JSON.stringify(PatientInHospitalInHospital.ids), status: 2}, function(data) {
			window.parent.PatientInHospital.table.refresh();
			PatientInHospitalInHospital.close();
		});
	}
};

PatientInHospitalInHospital.close = function() {
	parent.layer.close(window.parent.PatientInHospital.layerIndex);
}

/**
 * 检查是否选中
 */
PatientInHospitalInHospital.checkSelectMulti = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    
    PatientInHospitalInHospital.ids = [];
    for(var i = 0; i < selected.length; i++) {
    	PatientInHospitalInHospital.ids.push(selected[i].id);
    }
    return true;
};

/**
 * 检查是否选中
 */
PatientInHospitalInHospital.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientInHospitalInHospital.seItem = selected[0];
        return true;
    }
};

/**
 * 删除
 */
PatientInHospitalInHospital.delete = function () {
    if (this.checkSelectMulti()) {
	    var operation = function() {
	        Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/delete', {ids: JSON.stringify(PatientInHospitalInHospital.ids)}, function(data) {
	        	PatientInHospitalInHospital.table.refresh();
	        });
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询列表
 */
PatientInHospitalInHospital.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientInHospitalInHospital.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInHospitalInHospital.initColumn();
    var table = new BSTable(PatientInHospitalInHospital.id, "/patientInHospital/list", defaultColunms, {height: 350});
    table.setQueryParams({status: 1});
    PatientInHospitalInHospital.table = table.init();

    PatientSearchCommon.initUserNameSearch({url: '/patientInfo/findCanInHospital'}, function(index, row) {
		$('#transferPatientForm').form('load', row);
	});
	
});
