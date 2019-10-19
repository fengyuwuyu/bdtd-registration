/**
 * 管理初始化
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
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(MedicalInventoryStair.id)
            },
            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '拼音', field: 'spell', visible: true, align: 'center', valign: 'middle'},
            {title: '生产企业', field: 'producer', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
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
 * 点击添加
 */
MedicalInventoryStair.openAddMedicalInventoryStair = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventoryStair/medicalInventoryStair_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
MedicalInventoryStair.openMedicalInventoryStairDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryStair/medicalInventoryStair_update/' + MedicalInventoryStair.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
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
 * 查询列表
 */
MedicalInventoryStair.search = function () {
    var queryData = $('#searchForm').serializeObject();
    MedicalInventoryStair.table.refresh({query: queryData});
};

/**
 * 重置搜索表单，并刷新
 */
MedicalInventoryStair.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
    	MedicalInventoryStair.search();
    });
};

$(function () {
    var defaultColunms = MedicalInventoryStair.initColumn();
    var table = new BSTable(MedicalInventoryStair.id, "/medicalInventoryStair/list", defaultColunms);
    MedicalInventoryStair.table = table.init();
});
