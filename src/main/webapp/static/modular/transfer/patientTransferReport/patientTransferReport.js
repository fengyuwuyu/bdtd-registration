/**
 * 转诊管理初始化
 */
var PatientTransferReport = {
    id: "PatientTransferReportTable",	//表格id
    table: null,
    layerIndex: -1,
    ids: [],
    seItem: null
};

PatientTransferReport.exportExcel = function () {
	Feng.exportData('/patientTransferReport/exportExcel');
};

/**
 * 初始化表格的列
 */
PatientTransferReport.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center', 
                width: 50,
                formatter: Feng.getTableSerialNumberFunc(PatientTransferReport.id)
            },
            {title: '状态', field: 'status', width: 70, visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	if (value == 3) {
            		return '<span style="color:red;">未回报</span>'
            	} else if (value == 4) {
            		return '<span style="color:green;">已回报</span>'
            	}
            }},
            {title: '编号', field: 'userNo', width: 80, visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', width: 70, visible: true, align: 'center', valign: 'middle'},
            /*{title: '性别', field: 'userSex', visible: false, align: 'center', valign: 'middle'},*/
            {title: '单位', field: 'orgName', width: 120, visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', width: 70, visible: true, align: 'center', valign: 'middle'},
            /*{title: '入伍时间', field: 'enrolDate', width: 100, visible: true, align: 'center', valign: 'middle'},*/
            {title: '初步诊断', field: 'mainDiagnosis', width: 160, visible: true, align: 'center', valign: 'middle'/*, formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientTransferReport.openInitialDiagnosis('+item['patientInfoId']+');">详情</a>';
            }*/},
            {title: '开单汇总', field: 'billingName', width: 80, visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientTransferReport.openBillDetail('+item['id']+');">查看详细</a>';
            }},
            {title: '转诊时间', field: 'transferDate', width: 100, visible: true, align: 'center', valign: 'middle'},
            {title: '转诊医院', field: 'hospital', width: 100, visible: true, align: 'center', valign: 'middle'},
            {title: '医院诊断', field: 'hospitalDiagnosis', width: 160, visible: true, align: 'center', valign: 'middle'},
            {title: '回报详情', field: 'treatmentVisit', width: 80, visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientTransferReport.openReportDetail1('+item['id']+');">查看详细</a>';
            }},
           {title: '去向信息', field: 'disposition', width: 100, visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	if (value === '住院') {
            		return '<span style="color:red">住院</span>';
            	}
            	return value;
            }},
            {title: '回报时间', field: 'reportDate', width: 100, visible: true, align: 'center', valign: 'middle', formatter: function(value) {
            	if (!Feng.isNull(value)) {
            		return value.split(' ')[0];
            	}
            }}
            /*
            {title: '初诊军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '开单时间', field: 'billingDate', visible: true, align: 'center', valign: 'middle'},
            {title: '汇总人', field: 'collectName', visible: true, align: 'center', valign: 'middle'},
            {title: '汇总时间', field: 'collectDate', visible: true, align: 'center', valign: 'middle'}
            {title: '备注', field: 'billingRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '拟转时间', field: 'preTransferDate', visible: true, align: 'center', valign: 'middle'},
            {title: '开单人', field: 'billingId', visible: true, align: 'center', valign: 'middle'},
            {title: '手术名称', field: 'operationName', visible: true, align: 'center', valign: 'middle'},
            {title: '去向', field: 'disposition', visible: true, align: 'center', valign: 'middle'},
            {title: '治疗随访', field: 'treatmentVisit', visible: true, align: 'center', valign: 'middle'},
            {title: '住院信息', field: 'inHospitalInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '出院日期', field: 'leaveHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
            */
    ];
};

PatientTransferReport.openReportDetail = function() {
	if (this.check()) {
		var index = layer.open({
	        type: 2,
	        title: '回报内容详情',
	        area: ['1000px', '600px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Feng.ctxPath + '/patientTransferReport/openReportDetail/' + PatientTransferReport.seItem['id']
	    });
	    this.layerIndex = index;
	}
};

PatientTransferReport.openReportDetail1 = function(id) {
	var index = layer.open({
		type: 2,
		title: '回报内容详情',
		area: ['1000px', '450px'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/patientTransferReport/openReportDetail1/' + id
	});
	this.layerIndex = index;
};

PatientTransferReport.openInitialDiagnosis = function(patientInfoId) {
	var index = layer.open({
        type: 2,
        title: '初步诊断',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientTransferReport/openInitialDiagnosis/' + patientInfoId
    });
    this.layerIndex = index;
};

PatientTransferReport.openBillDetail = function(id) {
	var index = layer.open({
        type: 2,
        title: '开单汇总',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientTransferReport/openBillDetail/' + id
    });
    this.layerIndex = index;
};

PatientTransferReport.openDispositionDetail = function(id) {
	var index = layer.open({
        type: 2,
        title: '去向信息',
        area: ['800px', '360px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientTransferReport/openDispositionDetail/' + id
    });
    this.layerIndex = index;
};

/**
 * 检查是否选中
 */
PatientTransferReport.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    PatientTransferReport.seItem = selected[0];
    return true;
};

/**
 * 打开查看转诊详情
 */
PatientTransferReport.submitCollect = function () {
    if (this.checkSelectMulti()) {
    	Feng.confirm('确定开单汇总吗？', function() {
    		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferReport/update', {ids: JSON.stringify(PatientTransferReport.ids)}, function(data) {
        		PatientTransferReport.table.refresh();
        		PatientTransferReport.ids = [];
        	});
    	});
    }
};

/**
 * 删除转诊
 */
PatientTransferReport.delete = function () {
	if (PatientTransferReport.check()) {
        Feng.confirm("是否删除该选项？", function() {
        	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferReport/delete', {id: PatientTransferReport.seItem.id}, function(data) {
        		PatientTransferReport.table.refresh();
        	});
        });
    }
};

PatientTransferReport.checkSelectMultiDelete = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    
    PatientTransferReport.ids = [];
    for(var i = 0; i < selected.length; i++) {
    	PatientTransferReport.ids.push(selected[i].id);
    }
    return true;
};

PatientTransferReport.checkSelectMulti = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    PatientTransferReport.ids = [];
    for(var i = 0; i < selected.length; i++) {
    	if (selected[i].status == 3) {
    		Feng.error('包含已汇总记录，患者姓名是： ' + selected[i].userName);
    		return false;
    	}
    	PatientTransferReport.ids.push(selected[i].id);
    }
    return true;
};

PatientTransferReport.openTransferChart = function() {
	var index = layer.open({
		type: 2,
		title: '转诊统计图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/comprehensive/openTransferChart'
	});
	this.layerIndex = index;
};

PatientTransferReport.clearSearchForm = function() {
	Feng.clearSearchForm(function() {
		PatientTransferReport.search();
	});
};
/**
 * 查询转诊列表
 */
PatientTransferReport.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientTransferReport.table.refresh({query: queryData});
	PatientTransferReport.ids = [];
};

$(function () {
    var defaultColunms = PatientTransferReport.initColumn();
    var table = new BSTable(PatientTransferReport.id, "/patientTransferReport/list", defaultColunms, {showColumns: false, showRefresh:false});
    PatientTransferReport.table = table.init();
    
});
