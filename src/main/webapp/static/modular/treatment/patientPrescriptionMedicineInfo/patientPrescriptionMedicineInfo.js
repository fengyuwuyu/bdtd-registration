/**
 * 处方药详情管理初始化
 */
var PatientPrescriptionMedicineInfo = {
    id: "PatientPrescriptionMedicineInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientPrescriptionMedicineInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '药品', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
            {title: '周期', field: 'period', visible: true, align: 'center', valign: 'middle'},
            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientPrescriptionMedicineInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientPrescriptionMedicineInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加处方药详情
 */
PatientPrescriptionMedicineInfo.openAddPatientPrescriptionMedicineInfo = function () {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
    var index = layer.open({
        type: 2,
        title: '添加处方药详情',
        area: ['1100px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientPrescriptionMedicineInfo/patientPrescriptionMedicineInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看处方药详情详情
 */
PatientPrescriptionMedicineInfo.openPatientPrescriptionMedicineInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '处方药详情详情',
            area: ['800px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientPrescriptionMedicineInfo/patientPrescriptionMedicineInfo_update/' + PatientPrescriptionMedicineInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除处方药详情
 */
PatientPrescriptionMedicineInfo.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientPrescriptionMedicineInfo/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientPrescriptionMedicineInfo.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientPrescriptionMedicineInfoId",PatientPrescriptionMedicineInfo.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询处方药详情列表
 */
PatientPrescriptionMedicineInfo.search = function () {
	console.log(window.PatientInfo.id);
	if (window.PatientInfo.id) {
		var queryData = {patientInfoId: window.PatientInfo.id};
		PatientPrescriptionMedicineInfo.table.refresh({query: queryData});
	}
};

PatientPrescriptionMedicineInfo.refresh = function () {
	var queryData = {patientInfoId: PatientInfo.id};
	PatientPrescriptionMedicineInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientPrescriptionMedicineInfo.initColumn();
    var table = new BSTable(PatientPrescriptionMedicineInfo.id, "/patientPrescriptionMedicineInfo/list", defaultColunms, {height: '100%', pagination: false});
    PatientPrescriptionMedicineInfo.table = table.init();
});
