/**
 * 患者处方管理初始化
 */
var PatientPrescriptionInfo = {
    id: "PatientPrescriptionInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientPrescriptionInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '患者信息id', field: 'patientInfoId', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientPrescriptionInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientPrescriptionInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加患者处方
 */
PatientPrescriptionInfo.openAddPatientPrescriptionInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加患者处方',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientPrescriptionInfo/patientPrescriptionInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看患者处方详情
 */
PatientPrescriptionInfo.openPatientPrescriptionInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '患者处方详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientPrescriptionInfo/patientPrescriptionInfo_update/' + PatientPrescriptionInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除患者处方
 */
PatientPrescriptionInfo.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientPrescriptionInfo/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientPrescriptionInfo.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientPrescriptionInfoId",PatientPrescriptionInfo.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询患者处方列表
 */
PatientPrescriptionInfo.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientPrescriptionInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientPrescriptionInfo.initColumn();
    var table = new BSTable(PatientPrescriptionInfo.id, "/patientPrescriptionInfo/list", defaultColunms);
    PatientPrescriptionInfo.table = table.init();
});
