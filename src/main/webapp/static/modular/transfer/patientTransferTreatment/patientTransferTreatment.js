/**
 * 转诊管理初始化
 */
var PatientTransferTreatment = {
    id: "PatientTransferTreatmentTable",	//表格id
    table: null,
    layerIndex: -1,
    ids: []
};

/**
 * 初始化表格的列
 */
PatientTransferTreatment.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(PatientTransferTreatment.id)
            },
            {title: '初诊军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: false, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '初步诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '拟转时间', field: 'preTransferDate', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            /*{title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '转诊医院', field: 'hospital', visible: true, align: 'center', valign: 'middle'},
            {title: '开单人', field: 'billingId', visible: true, align: 'center', valign: 'middle'},
            {title: '开单人', field: 'billingName', visible: true, align: 'center', valign: 'middle'},
            {title: '开单时间', field: 'billingDate', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'billingRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '医院诊断', field: 'hospitalDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '手术名称', field: 'operationName', visible: true, align: 'center', valign: 'middle'},
            {title: '去向', field: 'disposition', visible: true, align: 'center', valign: 'middle'},
            {title: '治疗随访', field: 'treatmentVisit', visible: true, align: 'center', valign: 'middle'},
            {title: '住院信息', field: 'inHospitalInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '出院日期', field: 'leaveHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '转诊时间', field: 'transferDate', visible: true, align: 'center', valign: 'middle'},*/
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientTransferTreatment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};
PatientTransferTreatment.checkOne = function () {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
	if(selected.length == 0){
		Feng.info("请先选中表格中的某一记录！");
		return false;
	} else if (selected.length > 1) {
		Feng.info("只能选中一条记录！");
		return false;
	}
	PatientTransferTreatment.seItem = selected[0];
	return true;
};

/**
 * 打开查看转诊详情
 */
PatientTransferTreatment.openUpdateRemark = function () {
	if (this.checkOne()) {
		var index = layer.open({
			type: 2,
			title: '更新备注',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientTransferTreatment/openUpdateRemark/' + PatientTransferTreatment.seItem.id
		});
		this.layerIndex = index;
	}
};

/**
 * 打开查看转诊详情
 */
PatientTransferTreatment.openBillingDetail = function () {
    if (this.checkSelectMulti()) {
        var index = layer.open({
            type: 2,
            title: '开单详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientTransferTreatment/openBillingDetail'
        });
        this.layerIndex = index;
    }
};

/**
 * 删除转诊
 */
PatientTransferTreatment.delete = function () {
	if (PatientTransferTreatment.checkSelectMulti()) {
        Feng.confirm("是否删除该选项？", function() {
        	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferTreatment/delete', {ids: JSON.stringify(PatientTransferTreatment.ids)}, function(data) {
        		PatientTransferTreatment.table.refresh();
        		PatientTransferTreatment.ids = [];
        	});
        });
    }
};

PatientTransferTreatment.checkSelectMulti = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    PatientTransferTreatment.ids = [];
    for(var i = 0; i < selected.length; i++) {
    	PatientTransferTreatment.ids.push(selected[i].id);
    }
    return true;
};

PatientTransferTreatment.clearSearchForm = function() {
	Feng.clearSearchForm(function() {
		PatientTransferTreatment.search();
	});
};

/**
 * 查询转诊列表
 */
PatientTransferTreatment.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientTransferTreatment.table.refresh({query: queryData});
	PatientTransferTreatment.ids = [];
};

$(function () {
    var defaultColunms = PatientTransferTreatment.initColumn();
    var table = new BSTable(PatientTransferTreatment.id, "/patientTransferTreatment/list", defaultColunms);
    PatientTransferTreatment.table = table.init();
});
