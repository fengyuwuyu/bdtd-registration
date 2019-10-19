/**
 * 住院信息录入管理初始化
 */
var PatientInHospital = {
    id: "PatientInHospitalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientInHospital.exportExcel = function () {
	if (this.check()) {
		$("#inHospitalId").val(PatientInHospital.seItem['id']);
		Feng.exportData('/inHospitalTemperatureRecord/exportExcel');
	}
};

/**
 * 初始化表格的列
 */
PatientInHospital.initColumn = function () {
    return [
	        {field: 'selectItem', radio: true},
	        {
	            title: '序号',
	            field: '',
	            align: 'center',
	            formatter: Feng.getTableSerialNumberFunc(PatientInHospital.id)
	        },
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '个人信息', field: 'userNo', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientInHospital.openUserInfo(\''+item['userNo']+'\');">个人信息</a>';
            }},
            {title: '入院日期', field: 'inHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '入院诊断', field: 'patientInfoId', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientInHospital.openDiagnosisDetail(\''+item['patientInfoId']+'\');">入院诊断</a>';
            }},
            {title: '病室', field: 'sickRoom', visible: true, align: 'center', valign: 'middle'},
            {title: '病床', field: 'sickBed', visible: true, align: 'center', valign: 'middle'},
            {title: '常规医嘱', field: 'commonAdvice', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientInHospital.openCommonAdviceDetail('+item['id']+');">常规医嘱</a>';
            }},
            {title: '长期医嘱', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientInHospital.openLongTermAdviceDetail('+item['id']+');">长期医嘱</a>';
            }},
            {title: '临时医嘱', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientInHospital.openTemporaryAdviceDetail('+item['id']+');">临时医嘱</a>';
            }},
            {title: '体温记录', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	return '<a href="javascript:void(0);" onclick="PatientInHospital.openTemperatureRecordDetail('+item['id']+');">体温记录</a>';
            }},
            {title: '主管医师', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '录入员', field: 'operatorName', visible: true, align: 'center', valign: 'middle'},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

PatientInHospital.openUserInfo = function(userNo) {
	var index = layer.open({
		type: 2,
		title: '个人信息详细',
		area: ['1100px', '450px'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/patientInHospital/openUserInfo/' + userNo
	});
	this.layerIndex = index;
};

PatientInHospital.openDiagnosisDetail = function(patientInfoId) {
	var index = layer.open({
        type: 2,
        title: '入院诊断详细',
        area: ['1100px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientInHospital/openDiagnosisDetail/' + patientInfoId
    });
    this.layerIndex = index;
};

PatientInHospital.openInHospital = function() {
	var index = layer.open({
        type: 2,
        title: '入院管理',
        area: ['1100px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientInHospital/openInHospital'
    });
    this.layerIndex = index;
};

PatientInHospital.openInHospitalDetail = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '住院信息修改',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInHospital/openInHospitalDetail/' + PatientInHospital.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospital.openLeaveHospital = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '出院信息',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInHospital/openLeaveHospital/' + PatientInHospital.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospital.openCommonAdvice = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '常规医嘱',
			area: ['1100px', '700px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/inHospitalCommonAdvice/' + PatientInHospital.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospital.openCommonAdviceDetail = function(id) {
	var index = layer.open({
		type: 2,
		title: '常规医嘱详细',
		area: ['1100px', '700px'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/inHospitalCommonAdvice/inHospitalCommonAdviceDetail/' + id
	});
	this.layerIndex = index;
};

PatientInHospital.openLongTermAdvice = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '长期医嘱详细',
			area: ['1100px', '700px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInHospital/openLongTermAdvice/' + PatientInHospital.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospital.openLongTermAdviceDetail = function(id) {
	var index = layer.open({
		type: 2,
		title: '长期医嘱详细',
		area: ['1100px', '700px'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/patientInHospital/openLongTermAdviceDetail/' + id
	});
	this.layerIndex = index;
};

PatientInHospital.openTemporaryAdvice = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '临时医嘱',
			area: ['1100px', '700px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInHospital/openTemporaryAdvice/' + PatientInHospital.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospital.openTemporaryAdviceDetail = function(id) {
	var index = layer.open({
		type: 2,
		title: '临时医嘱',
		area: ['1100px', '700px'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/patientInHospital/openTemporaryAdviceDetail/' + id
	});
	this.layerIndex = index;
};

PatientInHospital.openTemperatureRecord = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '体温记录',
			area: ['1100px', '300px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/inHospitalTemperatureRecord/openTemperatureRecord/' + PatientInHospital.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospital.openTemperatureRecordDetail = function(id) {
	var index = layer.open({
		type: 2,
		title: '体温记录图',
		area: ['100%', '100%'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/inHospitalTemperatureRecord/openTemperatureRecordDetail/' + id
	});
	this.layerIndex = index;
};

/**
 * 检查是否选中
 */
PatientInHospital.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientInHospital.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
PatientInHospital.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientInHospital.search();
    });
};

/**
 * 点击添加住院信息录入
 */
PatientInHospital.openAddPatientInHospital = function () {
    var index = layer.open({
        type: 2,
        title: '添加住院信息录入',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientInHospital/patientInHospital_add'
    });
    this.layerIndex = index;
};

/**
 * 删除住院信息录入
 */
PatientInHospital.delete = function () {
    if (this.check()) {
	    var operation = function() {
	        var ids = [PatientInHospital.seItem.id];
	        Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/delete', {ids: JSON.stringify(ids)}, function(data) {
	        	PatientInHospital.table.refresh();
	        });
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询住院信息录入列表
 */
PatientInHospital.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientInHospital.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInHospital.initColumn();
    var table = new BSTable(PatientInHospital.id, "/patientInHospital/list", defaultColunms);
    table.setQueryParams({status: 4});
    PatientInHospital.table = table.init();
});
