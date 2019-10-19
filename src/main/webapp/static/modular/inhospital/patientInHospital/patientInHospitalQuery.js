/**
 * 住院信息录入管理初始化
 */
var PatientInHospitalQuery = {
    id: "PatientInHospitalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientInHospitalQuery.exportExcel = function () {
	if (this.check()) {
		$("#inHospitalId").val(PatientInHospitalQuery.seItem['id']);
		Feng.exportData('/inHospitalTemperatureRecord/exportExcel');
	}
};

/**
 * 初始化表格的列
 */
PatientInHospitalQuery.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientInHospitalQuery.id)
        },
        {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'userSex', visible: false, align: 'center', valign: 'middle'},
        {title: '个人信息', field: 'userNo', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientInHospitalQuery.openUserInfo(\''+item['userNo']+'\');">个人信息</a>';
        }},
        {title: '入院日期', field: 'inHospitalDate', visible: true, align: 'center', valign: 'middle'},
        {title: '入院诊断', field: 'patientInfoId', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientInHospitalQuery.openDiagnosisDetail(\''+item['patientInfoId']+'\');">入院诊断</a>';
        }},
        {title: '出院日期', field: 'leaveHospitalDate', visible: true, align: 'center', valign: 'middle'},
        {title: '出院诊断', field: 'outDiagnosis', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterLongValue()},
        {title: '病室', field: 'sickRoom', visible: true, align: 'center', valign: 'middle'},
        {title: '病床', field: 'sickBed', visible: true, align: 'center', valign: 'middle'},
        {title: '常规医嘱', field: 'commonAdvice', visible: false, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientInHospitalQuery.openCommonAdviceDetail('+item['id']+');">常规医嘱</a>';
        }},
        {title: '长期医嘱', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientInHospitalQuery.openLongTermAdviceDetail('+item['id']+');">长期医嘱</a>';
        }},
        {title: '临时医嘱', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientInHospitalQuery.openTemporaryAdviceDetail('+item['id']+');">临时医嘱</a>';
        }},
        {title: '体温记录', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientInHospitalQuery.openTemperatureRecordDetail('+item['id']+');">体温记录</a>';
        }},
        {title: '主管医师', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
        {title: '录入员', field: 'operatorName', visible: true, align: 'center', valign: 'middle'},
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'}
];
};

PatientInHospitalQuery.openTemperatureRecordDetail = function(id) {
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

PatientInHospitalQuery.openTemporaryAdviceDetail = function(id) {
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

PatientInHospitalQuery.openLongTermAdviceDetail = function(id) {
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

PatientInHospitalQuery.openCommonAdviceDetail = function(id) {
	var index = layer.open({
		type: 2,
		title: '常规医嘱',
		area: ['800px', '600px'], //宽高
		fix: false, //不固定
		maxmin: true,
		content: Feng.ctxPath + '/patientInHospital/openCommonAdviceDetail/' + id
	});
	this.layerIndex = index;
};

PatientInHospitalQuery.openDiagnosisDetail = function(patientInfoId) {
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

PatientInHospitalQuery.openUserInfo = function(userNo) {
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

PatientInHospitalQuery.openInhospitalDetail = function () {
	if (this.check()) {
		if (PatientInHospitalQuery.seItem['status'] == 4) {
			Feng.error('请去住院信息录入页面中办理出院！');
			return;
		}
		var index = layer.open({
			type: 2,
			title: '住院详情',
			area: ['1000px', '700px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInHospital/queryDetail/' + PatientInHospitalQuery.seItem['id']
		});
		this.layerIndex = index;
	}
};

PatientInHospitalQuery.openLeaveHospital = function () {
	if (this.check()) {
		if (PatientInHospitalQuery.seItem['status'] == 4) {
			Feng.error('请去住院信息录入页面中办理出院！');
			return;
		}
		var index = layer.open({
			type: 2,
			title: '出院信息',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientInHospital/openLeaveHospital/' + PatientInHospitalQuery.seItem['id']
		});
		this.layerIndex = index;
	}
}

/**
 * 检查是否选中
 */
PatientInHospitalQuery.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientInHospitalQuery.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
PatientInHospitalQuery.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientInHospitalQuery.search();
    });
};

/**
 * 点击添加住院信息录入
 */
PatientInHospitalQuery.openAddPatientInHospital = function () {
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
PatientInHospitalQuery.delete = function () {
    if (this.check()) {
	    var operation = function() {
	        var ids = [PatientInHospitalQuery.seItem.id];
	        Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/delete', {ids: JSON.stringify(ids)}, function(data) {
	        	PatientInHospitalQuery.table.refresh();
	        });
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询住院信息录入列表
 */
PatientInHospitalQuery.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientInHospitalQuery.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInHospitalQuery.initColumn();
    var table = new BSTable(PatientInHospitalQuery.id, "/patientInHospital/list", defaultColunms);
    table.setQueryParams({status: 9});
    PatientInHospitalQuery.table = table.init();
});
