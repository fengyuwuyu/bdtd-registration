/**
 * 就诊管理管理初始化
 */
var PrescriptionMedicineInfo = {
    id: "PrescriptionMedicineInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PrescriptionMedicineInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '药品id', field: 'medicineId', visible: true, align: 'center', valign: 'middle'},
            {title: '药品', field: 'medicineName', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
            {title: '周期', field: 'period', visible: true, align: 'center', valign: 'middle'},
            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
            {title: '处方id', field: 'patientPrescriptionId', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PrescriptionMedicineInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PrescriptionMedicineInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加就诊管理
 */
PrescriptionMedicineInfo.openAddPrescriptionMedicineInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加就诊管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/prescriptionMedicineInfo/prescriptionMedicineInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看就诊管理详情
 */
PrescriptionMedicineInfo.openPrescriptionMedicineInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '就诊管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/prescriptionMedicineInfo/prescriptionMedicineInfo_update/' + PrescriptionMedicineInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除就诊管理
 */
PrescriptionMedicineInfo.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/prescriptionMedicineInfo/delete", function (data) {
	            Feng.success("删除成功!");
	            PrescriptionMedicineInfo.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("prescriptionMedicineInfoId",PrescriptionMedicineInfo.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询就诊管理列表
 */
PrescriptionMedicineInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    PrescriptionMedicineInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PrescriptionMedicineInfo.initColumn();
    var table = new BSTable(PrescriptionMedicineInfo.id, "/prescriptionMedicineInfo/list", defaultColunms);
    table.setPaginationType("client");
    PrescriptionMedicineInfo.table = table.init();
});
