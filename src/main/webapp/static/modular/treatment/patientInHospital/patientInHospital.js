/**
 * 住院管理初始化
 */
var PatientInHospital = {
    id: "PatientInHospitalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientInHospital.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '用户编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '发热', field: 'fever', visible: true, align: 'center', valign: 'middle'},
            {title: '入院诊断', field: 'diagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '出院诊断', field: 'outDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '主管医师', field: 'physicianId', visible: true, align: 'center', valign: 'middle'},
            {title: '主管医师', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '病患信息id', field: 'patientInfoId', visible: true, align: 'center', valign: 'middle'},
            {title: '病室', field: 'sickRoom', visible: true, align: 'center', valign: 'middle'},
            {title: '病床', field: 'sickBed', visible: true, align: 'center', valign: 'middle'},
            {title: '常规医嘱', field: 'commonDoctorAdvice', visible: true, align: 'center', valign: 'middle'},
            {title: '长期医嘱', field: 'longTermDoctorAdvice', visible: true, align: 'center', valign: 'middle'},
            {title: '临时医嘱', field: 'temporaryDoctorAdvice', visible: true, align: 'center', valign: 'middle'},
            {title: '录入员', field: 'operatorId', visible: true, align: 'center', valign: 'middle'},
            {title: '住院时间', field: 'inHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '出院时间', field: 'leaveHospitalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
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
 * 点击添加住院
 */
PatientInHospital.openAddPatientInHospital = function () {
    var index = layer.open({
        type: 2,
        title: '添加住院',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientInHospital/patientInHospital_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看住院详情
 */
PatientInHospital.openPatientInHospitalDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '住院详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientInHospital/patientInHospital_update/' + PatientInHospital.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除住院
 */
PatientInHospital.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientInHospital/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientInHospital.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientInHospitalId",PatientInHospital.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询住院列表
 */
PatientInHospital.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientInHospital.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientInHospital.initColumn();
    var table = new BSTable(PatientInHospital.id, "/patientInHospital/list", defaultColunms);
    PatientInHospital.table = table.init();
});
