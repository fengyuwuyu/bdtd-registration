/**
 * 病休信息管理初始化
 */
var PatientInHospitalComprehensiveQuery = {
    id: "PatientSickRestTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientInHospitalComprehensiveQuery.exportExcel = function () {
	Feng.exportData('/patientSickRest/exportExcel');
};

/**
 * 初始化表格的列
 */
PatientInHospitalComprehensiveQuery.initColumn = function () {
    return [
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientInHospitalComprehensiveQuery.id)
        },
        {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'userSex', visible: false, align: 'center', valign: 'middle'},
        {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
        {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
        {title: '入伍时间', field: 'enrolDate', visible: true, align: 'center', valign: 'middle'},
        {title: '诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
        {title: '病休方式', field: 'sickRestType', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	if (value == 1) {
        		return "全休";
        	}
        	return "半休";
        }},
        {title: '开始时间', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
        {title: '天数', field: 'sickRestDay', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	if (value == '病休中') {
        		return "<span style='color: red;'>病休中</span>";
        	}
        	return "<span style='color: green;'>已康复</span>";
        }},
        {title: '注意事项', field: 'noticeInfo', visible: false, align: 'center', valign: 'middle'},
        {title: '医师', field: 'physicianName', visible: true, align: 'center', valign: 'middle'}
    ];
};

PatientInHospitalComprehensiveQuery.openSickRestChart = function () {
	var index = layer.open({
		type: 2,
		title: '病休统计图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/comprehensive/openSickRestChart'
	});
	this.layerIndex = index;
};

/**
 * 重置搜索表单，并刷新
 */
PatientInHospitalComprehensiveQuery.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientInHospitalComprehensiveQuery.search();
    });
};

/**
 * 查询病休信息列表
 */
PatientInHospitalComprehensiveQuery.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientInHospitalComprehensiveQuery.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInHospitalComprehensiveQuery.initColumn();
    var table = new BSTable(PatientInHospitalComprehensiveQuery.id, "/patientSickRest/list", defaultColunms);
    PatientInHospitalComprehensiveQuery.table = table.init();
});
