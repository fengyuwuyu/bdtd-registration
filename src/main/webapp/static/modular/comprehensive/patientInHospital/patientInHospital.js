/**
 * 住院信息录入管理初始化
 */
var PatientInHospitalComprehensiveQuery = {
    id: "PatientInHospitalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientInHospitalComprehensiveQuery.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    PatientInHospitalComprehensiveQuery.seItem = selected[0];
    return true;
};

/**
 * 初始化表格的列
 */
PatientInHospitalComprehensiveQuery.initColumn = function () {
    return [
	        {field: 'selectItem', radio: true},
	        {
	            title: '序号',
	            field: '',
	            align: 'center',
	            formatter: Feng.getTableSerialNumberFunc(PatientInHospitalComprehensiveQuery.id)
	        },
	        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
	        	if (value == '本院') {
	        		return '<span style="color:green">本院</span>';
	        	} 
	        	return '<span style="color:blue">外院</span>';
	        }},
	        {title: '医院', field: 'hospital', visible: true, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '入伍时间', field: 'enrolDate', visible: true, align: 'center', valign: 'middle'},
            {title: '主要诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '次要诊断', field: 'minorDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '入院时间', field: 'inHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '出院时间', field: 'leaveHospitalDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

PatientInHospitalComprehensiveQuery.exportExcel = function () {
	Feng.exportData('/comprehensive/exportExcel');
};

PatientInHospitalComprehensiveQuery.openInHospital = function () {
	var index = layer.open({
		type: 2,
		title: '住院统计图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/comprehensive/openInhospitalChart'
	});
	this.layerIndex = index;
};

PatientInHospitalComprehensiveQuery.openReportDetail = function() {
	if (this.check()) {
		if (PatientInHospitalComprehensiveQuery.seItem['status'] !== '外院') {
			Feng.info('请选择住外院患者！');
			return;
		}
		var index = layer.open({
	        type: 2,
	        title: '回报内容详情',
	        area: ['1000px', '600px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Feng.ctxPath + '/patientTransferReport/openReportDetail1/' + PatientInHospitalComprehensiveQuery.seItem['id']
	    });
	    this.layerIndex = index;
	}
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
 * 查询住院信息录入列表
 */
PatientInHospitalComprehensiveQuery.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientInHospitalComprehensiveQuery.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInHospitalComprehensiveQuery.initColumn();
    var table = new BSTable(PatientInHospitalComprehensiveQuery.id, "/comprehensive/inhospitalList", defaultColunms);
    PatientInHospitalComprehensiveQuery.table = table.init();
});
