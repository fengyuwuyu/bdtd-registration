/**
 * 门诊信息管理初始化
 */
var PatientInfo = {
    id: "PatientInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientInfo.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '入伍时间', field: 'enrolDate', visible: true, align: 'center', valign: 'middle'},
            {title: '就诊时间', field: 'clinicDate', visible: true, align: 'center', valign: 'middle'},
            {title: '主要诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '次要诊断', field: 'minorDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '转诊', field: 'transfer', visible: true, align: 'center', valign: 'middle'},
            {title: '住院', field: 'inhospital', visible: true, align: 'center', valign: 'middle'},
            {title: '训练伤', field: 'trainWound', visible: true, align: 'center', valign: 'middle'},
            {title: '发热', field: 'fever', visible: true, align: 'center', valign: 'middle'},
            {title: '病休', field: 'sickRest', visible: true, align: 'center', valign: 'middle'},
            {title: '诊断军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientInfo.seItem = selected[0];
        return true;
    }
};

PatientInfo.openPatientInfoChart = function () {
	var index = layer.open({
		type: 2,
		title: '门诊统计图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/comprehensive/openPatientInfoChart'
	});
	this.layerIndex = index;
};

PatientInfo.exportExcel = function () {
	Feng.exportData("/patientInfo/exportExcel");
};

/**
 * 重置搜索表单，并刷新
 */
PatientInfo.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientInfo.search();
    });
};


/**
 * 查询门诊信息列表
 */
PatientInfo.search = function () {
    var queryData = $('#searchForm').serializeObject();

    PatientInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInfo.initColumn();
    var table = new BSTable(PatientInfo.id, "/patientInfo/list", defaultColunms);
    table.setQueryParams({comprehensiveQuery: true});
    PatientInfo.table = table.init();
});
