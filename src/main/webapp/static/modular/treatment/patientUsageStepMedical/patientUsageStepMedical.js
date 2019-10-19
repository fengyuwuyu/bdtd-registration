/**
 * 处方使用步骤详情管理初始化
 */
var PatientUsageStepMedical = {
    id: "PatientUsageStepMedicalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientUsageStepMedical.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '药品id', field: 'medicineId', visible: false, align: 'center', valign: 'middle'},
            {title: '药品', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '给药途径', field: 'doseWay', visible: true, align: 'center', valign: 'middle'},
            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
            {title: '次/天', field: 'period', visible: true, align: 'center', valign: 'middle'},
            {title: '使用步骤表id', field: 'usageStepId', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientUsageStepMedical.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientUsageStepMedical.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加处方使用步骤详情
 */
PatientUsageStepMedical.openAddPatientUsageStepMedical = function () {
    var index = layer.open({
        type: 2,
        title: '添加处方使用步骤详情',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientUsageStepMedical/patientUsageStepMedical_add/' + $('#usageStepId').val()
    });
    this.layerIndex = index;
};

/**
 * 打开查看处方使用步骤详情详情
 */
PatientUsageStepMedical.openPatientUsageStepMedicalDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '处方使用步骤详情详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientUsageStepMedical/patientUsageStepMedical_update/' + PatientUsageStepMedical.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除处方使用步骤详情
 */
PatientUsageStepMedical.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientUsageStepMedical/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientUsageStepMedical.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientUsageStepMedicalId",PatientUsageStepMedical.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询处方使用步骤详情列表
 */
PatientUsageStepMedical.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientUsageStepMedical.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientUsageStepMedical.initColumn();
    var table = new BSTable(PatientUsageStepMedical.id, "/patientUsageStepMedical/list", defaultColunms, {height: 560, pagination: false, showColumns: false});
    table.setQueryParams({usageStepId: $('#usageStepId').val()});
    PatientUsageStepMedical.table = table.init();
});
