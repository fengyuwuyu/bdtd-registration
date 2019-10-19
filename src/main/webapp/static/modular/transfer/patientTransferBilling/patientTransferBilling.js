/**
 * 转诊管理初始化
 */
var PatientTransferBilling = {
    id: "PatientTransferBillingTable",	//表格id
    table: null,
    layerIndex: -1,
    ids: []
};

/**
 * 初始化表格的列
 */
PatientTransferBilling.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(PatientTransferBilling.id)
            },
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	if (value == 2) {
            		return '<span style="color:red;">未汇总</span>'
            	}
            	return '<span style="color:green;">已汇总</span>'
            }},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: false, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '初步诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '初诊军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '转往医院', field: 'hospital', visible: true, align: 'center', valign: 'middle'},
            {title: '开单人', field: 'billingName', visible: true, align: 'center', valign: 'middle'},
            {title: '开单时间', field: 'billingDate', visible: true, align: 'center', valign: 'middle'},
            {title: '汇总人', field: 'collectName', visible: true, align: 'center', valign: 'middle'},
            {title: '汇总时间', field: 'collectDate', visible: true, align: 'center', valign: 'middle'}
            /*
            {title: '备注', field: 'billingRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '拟转时间', field: 'preTransferDate', visible: true, align: 'center', valign: 'middle'},
            {title: '开单人', field: 'billingId', visible: true, align: 'center', valign: 'middle'},
            {title: '医院诊断', field: 'hospitalDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '手术名称', field: 'operationName', visible: true, align: 'center', valign: 'middle'},
            {title: '去向', field: 'disposition', visible: true, align: 'center', valign: 'middle'},
            {title: '治疗随访', field: 'treatmentVisit', visible: true, align: 'center', valign: 'middle'},
            {title: '住院信息', field: 'inHospitalInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '出院日期', field: 'leaveHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
            {title: '转诊时间', field: 'transferDate', visible: true, align: 'center', valign: 'middle'},*/
    ];
};

/**
 * 检查是否选中
 */
PatientTransferBilling.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};

PatientTransferBilling.exportExcel = function () {
	Feng.exportData('/patientTransferBilling/exportExcel');
};

/**
 * 打开查看转诊详情
 */
PatientTransferBilling.submitCollect = function () {
    if (this.checkSelectMulti()) {
    	Feng.confirm('确定开单汇总吗？', function() {
    		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferBilling/update', {ids: JSON.stringify(PatientTransferBilling.ids)}, function(data) {
        		PatientTransferBilling.table.refresh();
        		PatientTransferBilling.ids = [];
        	});
    	});
    }
};

/**
 * 删除转诊
 */
PatientTransferBilling.delete = function () {
	if (PatientTransferBilling.checkSelectMultiDelete()) {
        Feng.confirm("是否删除该选项？", function() {
        	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferBilling/toPreTransferStatus', {ids: JSON.stringify(PatientTransferBilling.ids)}, function(data) {
        		PatientTransferBilling.table.refresh();
        		PatientTransferBilling.ids = [];
        	});
        });
    }
};

PatientTransferBilling.checkSelectMultiDelete = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    
    PatientTransferBilling.ids = [];
    for(var i = 0; i < selected.length; i++) {
    	PatientTransferBilling.ids.push(selected[i].id);
    }
    return true;
};

PatientTransferBilling.checkSelectMulti = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    PatientTransferBilling.ids = [];
    for(var i = 0; i < selected.length; i++) {
    	if (selected[i].status == 3) {
    		Feng.error('包含已汇总记录，患者姓名是： ' + selected[i].userName);
    		return false;
    	}
    	PatientTransferBilling.ids.push(selected[i].id);
    }
    return true;
};

PatientTransferBilling.clearSearchForm = function() {
	Feng.clearSearchForm(function() {
		PatientTransferBilling.search();
	});
};
/**
 * 查询转诊列表
 */
PatientTransferBilling.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientTransferBilling.table.refresh({query: queryData});
	PatientTransferBilling.ids = [];
};

$(function () {
    var defaultColunms = PatientTransferBilling.initColumn();
    var table = new BSTable(PatientTransferBilling.id, "/patientTransferBilling/list", defaultColunms, {showColumns: false, showRefresh:false});
    PatientTransferBilling.table = table.init();
    
});
