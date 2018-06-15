/**
 * 药品二级库存管理管理初始化
 */
var MedicalInventorySecondLevel = {
    id: "MedicalInventorySecondLevelTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MedicalInventorySecondLevel.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '生产批号', field: 'produceBatchNum', visible: true, align: 'center', valign: 'middle'},
            {title: '生产日期', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '过期时间', field: 'expireDate', visible: true, align: 'center', valign: 'middle'},
            {title: '价格', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '库存数量', field: 'inventoryNum', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unitStr', visible: true, align: 'center', valign: 'middle'},
            {title: '进货渠道', field: 'inboundChannelStr', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MedicalInventorySecondLevel.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MedicalInventorySecondLevel.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加药品二级库存管理
 */
MedicalInventorySecondLevel.openAddMedicalInventorySecondLevel = function () {
    var index = layer.open({
        type: 2,
        title: '添加药品二级库存管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventorySecondLevel/medicalInventorySecondLevel_add/' + $('#medicalInventoryStairId').val()
    });
    this.layerIndex = index;
};

/**
 * 打开查看药品二级库存管理详情
 */
MedicalInventorySecondLevel.openMedicalInventorySecondLevelDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药品二级库存管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventorySecondLevel/medicalInventorySecondLevel_update/' + MedicalInventorySecondLevel.seItem.id
        });
        this.layerIndex = index;
    }
};

MedicalInventorySecondLevel.putInStorage = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '入库管理页面',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventorySecondLevel/putInStorage/' + MedicalInventorySecondLevel.seItem.id
        });
        this.layerIndex = index;
    }
};

MedicalInventorySecondLevel.putInStorage = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '出库管理页面',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventorySecondLevel/outOfStorage/' + MedicalInventorySecondLevel.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除药品二级库存管理
 */
MedicalInventorySecondLevel.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/medicalInventorySecondLevel/delete", function (data) {
	            Feng.success("删除成功!");
	            MedicalInventorySecondLevel.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("medicalInventorySecondLevelId",MedicalInventorySecondLevel.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询药品二级库存管理列表
 */
MedicalInventorySecondLevel.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MedicalInventorySecondLevel.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MedicalInventorySecondLevel.initColumn();
    var medicalInventoryStairId = $('#medicalInventoryStairId').val();
    var table = new BSTable(MedicalInventorySecondLevel.id, "/medicalInventorySecondLevel/list/" + medicalInventoryStairId, defaultColunms);
    table.setPaginationType("client");
    MedicalInventorySecondLevel.table = table.init();
});
