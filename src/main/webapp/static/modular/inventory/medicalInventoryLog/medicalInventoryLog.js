/**
 * 药品出入库记录管理初始化
 */
var MedicalInventoryLog = {
    id: "MedicalInventoryLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MedicalInventoryLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'spell', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'producer', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'produceBatchNum', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'produceDate', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'expireDate', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'inboundChannel', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'logDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MedicalInventoryLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MedicalInventoryLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加药品出入库记录
 */
MedicalInventoryLog.openAddMedicalInventoryLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加药品出入库记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventoryLog/medicalInventoryLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看药品出入库记录详情
 */
MedicalInventoryLog.openMedicalInventoryLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药品出入库记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryLog/medicalInventoryLog_update/' + MedicalInventoryLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除药品出入库记录
 */
MedicalInventoryLog.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/medicalInventoryLog/delete", function (data) {
	            Feng.success("删除成功!");
	            MedicalInventoryLog.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("medicalInventoryLogId",MedicalInventoryLog.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询药品出入库记录列表
 */
MedicalInventoryLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MedicalInventoryLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MedicalInventoryLog.initColumn();
    var table = new BSTable(MedicalInventoryLog.id, "/medicalInventoryLog/list", defaultColunms);
    table.setPaginationType("client");
    MedicalInventoryLog.table = table.init();
});
