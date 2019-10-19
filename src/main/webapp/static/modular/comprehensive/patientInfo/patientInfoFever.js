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
            {title: '诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: "发热", field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
            	return "是"
            }},
            {title: '状态', field: 'inhospitalStatus', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
            	if (value === '住院') {
            		return '<span style="color:red">住院</span>'
            	} else if (value === '其他') {
            		return value;
            	}
            	return '<span style="color:green">' + value + '</span>'
            }},
            {title: '处理方式', field: 'handleType', visible: true, align: 'center', valign: 'middle'},
            {title: '医生', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '就诊时间', field: 'clinicDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

PatientInfo.exportTrainWoundExcel = function () {
	Feng.exportData('/patientInfo/exportTrainWoundExcel');
};

PatientInfo.exportFeverExcel = function () {
	Feng.exportData('/patientInfo/exportFeverExcel');
};

PatientInfo.openTrainWoundChart = function () {
	var index = layer.open({
		type: 2,
		title: '训练伤统计图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/comprehensive/openTrainWoundChart'
	});
	this.layerIndex = index;
};

PatientInfo.openFeverChart = function () {
	var index = layer.open({
		type: 2,
		title: '发热统计图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/comprehensive/openFeverChart'
	});
	this.layerIndex = index;
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
    var table = new BSTable(PatientInfo.id, "/patientInfo/feverList", defaultColunms);
    var queryData = $('#searchForm').serializeObject();
    table.setQueryParams(queryData);
    PatientInfo.table = table.init();
});
