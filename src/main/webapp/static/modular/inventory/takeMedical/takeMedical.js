/**
 * 病患信息管理初始化
 */
var TakeMedical = {
    id: "PatientInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TakeMedical.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(TakeMedical.id)
            },
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value, row, index) {
            	if (value == 4) {
            		return '<span style="color: green">已取药</span>';
            	} else {
            		return '<span style="color: red">未取药</span>';
            	}
            }},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '主要诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '处理方式', field: 'handleType', visible: true, align: 'center', valign: 'middle'},
            {title: '处方时间', field: 'clinicDate', visible: true, align: 'center', valign: 'middle'},
            {title: '诊断军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '取药时间', field: 'takeMedicalDate', visible: false, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
TakeMedical.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TakeMedical.seItem = selected[0];
        return true;
    }
};

TakeMedical.clearSearchForm = function() {
	Feng.clearSearchForm(function() {
		TakeMedical.search();
	});
}

/**
 * 查询病患信息列表
 */
TakeMedical.search = function () {
    var queryData = $('#searchForm').serializeObject();
    
    if (!Feng.checkSearchTimeSection()) {
    	return;
    }
    
    queryData['diagnosisComplete'] = true;
    TakeMedical.table.refresh({query: queryData});
};

TakeMedical.openTakeMedical = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '处方药详情',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInfo/openPrescriptionMedicalDetail/' + TakeMedical.seItem.id
		});
		this.layerIndex = index;
	}
};

TakeMedical.openPrescriptionDetail = function() {
	if (this.check()) {
		var index = layer.open({
            type: 2,
            title: '处方详情',
            area: ['1400px', '800px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientInfo/patientInfo_detail/' + TakeMedical.seItem.id
        });
        this.layerIndex = index;
	}
};

$(function () {
    var defaultColunms = TakeMedical.initColumn();
    var table = new BSTable(TakeMedical.id, "/patientInfo/list", defaultColunms, {showColumns: false, showRefresh: false});
    table.setQueryParams({diagnosisComplete: true});
    TakeMedical.table = table.init();
});
