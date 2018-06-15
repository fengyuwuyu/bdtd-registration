/**
 * 药品管理管理初始化
 */
var MedicalInventoryStair = {
    id: "MedicalInventoryStairTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MedicalInventoryStair.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'ID号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '拼音', field: 'spell', visible: true, align: 'center', valign: 'middle'},
            {title: '生产商', field: 'producerStr', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'specificationStr', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unitStr', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MedicalInventoryStair.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MedicalInventoryStair.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加药品管理
 */
MedicalInventoryStair.openAddMedicalInventoryStair = function () {
    var index = layer.open({
        type: 2,
        title: '添加药品管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventoryStair/medicalInventoryStair_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看药品管理详情
 */
MedicalInventoryStair.openMedicalInventoryStairDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药品管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryStair/medicalInventoryStair_update/' + MedicalInventoryStair.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看药品二级管理详情
 */
MedicalInventoryStair.medicalInventorySecondLevelDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '二级药品管理',
            area: ['1200px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventorySecondLevel/' + MedicalInventoryStair.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除药品管理
 */
MedicalInventoryStair.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStair/delete", function (data) {
	            Feng.success("删除成功!");
	            MedicalInventoryStair.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("medicalInventoryStairId",MedicalInventoryStair.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询药品管理列表
 */
MedicalInventoryStair.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MedicalInventoryStair.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MedicalInventoryStair.initColumn();
    var table = new BSTable(MedicalInventoryStair.id, "/medicalInventoryStair/list", defaultColunms);
    table.setPaginationType("client");
    MedicalInventoryStair.table = table.init();
});
