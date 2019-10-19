/**
 * 药库管理管理初始化
 */
var MedicalInventoryDrugStorage = {
    id: "MedicalInventoryDrugStorageTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MedicalInventoryDrugStorage.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(MedicalInventoryDrugStorage.id)
            },
            {title: '上级药品', field: 'parentId', visible: false, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '生产企业', field: 'producer', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '生产批号', field: 'produceBatchNum', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '生产日期', field: 'produceDate', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '有效期至', field: 'expireDate', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '价格', field: 'price', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '在库数量', field: 'inventoryNum', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '进货渠道', field: 'inboundChannel', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '创建时间', field: 'createDate', visible: false, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()},
            {title: '更新时间', field: 'updateDate', visible: false, align: 'center', valign: 'middle', formatter: Feng.formatterExpireDate()}
    ];
};

/**
 * 检查是否选中
 */
MedicalInventoryDrugStorage.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MedicalInventoryDrugStorage.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加药库管理
 */
MedicalInventoryDrugStorage.openAddMedicalInventoryDrugStorage = function () {
    var index = layer.open({
        type: 2,
        title: '添加药库管理',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventoryDrugStorage/medicalInventoryDrugStorage_add'
    });
    this.layerIndex = index;
};

MedicalInventoryDrugStorage.exportExcel = function () {
    var queryData = $('#searchForm').serializeObject();
    if (Feng.isNull(queryData['expiredDate'])) {
    	delete queryData['expiredDate'];
    }
	Feng.exportData("/medicalInventoryDrugStorage/exportExcel", queryData);
};

MedicalInventoryDrugStorage.openStorageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药库管理详情',
            area: ['1200px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryDrugStorage/medicalInventoryDrugStorage_storageDetail/' + MedicalInventoryDrugStorage.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看药库管理详情
 */
MedicalInventoryDrugStorage.openMedicalInventoryDrugStorageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药库管理详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryDrugStorage/medicalInventoryDrugStorage_update/' + MedicalInventoryDrugStorage.seItem.id
        });
        this.layerIndex = index;
    }
};

MedicalInventoryDrugStorage.openPutInStorage = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '入库管理详情',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/medicalInventoryDrugStorage/medicalInventoryDrugStorage_putInStorage/' + MedicalInventoryDrugStorage.seItem.id
		});
		this.layerIndex = index;
	}
};

MedicalInventoryDrugStorage.openPutOffStorage = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '出库管理详情',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/medicalInventoryDrugStorage/medicalInventoryDrugStorage_putOffStorage/' + MedicalInventoryDrugStorage.seItem.id
		});
		this.layerIndex = index;
	}
};

/**
 * 删除药库管理
 */
MedicalInventoryDrugStorage.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/medicalInventoryDrugStorage/delete", function (data) {
	            Feng.success("删除成功!");
	            MedicalInventoryDrugStorage.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("medicalInventoryDrugStorageId", MedicalInventoryDrugStorage.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询药库管理列表
 */
MedicalInventoryDrugStorage.search = function () {
    var queryData = $('#searchForm').serializeObject();
    if (Feng.isNull(queryData['expiredDate'])) {
    	delete queryData['expiredDate'];
    }
    console.log(queryData)
    MedicalInventoryDrugStorage.table.refresh({query: queryData});
};

/**
 * 重置搜索表单，并刷新
 */
MedicalInventoryDrugStorage.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
    	MedicalInventoryDrugStorage.search();
    });
};

$(function () {
    var defaultColunms = MedicalInventoryDrugStorage.initColumn();
    var table = new BSTable(MedicalInventoryDrugStorage.id, "/medicalInventoryDrugStorage/list", defaultColunms);
    MedicalInventoryDrugStorage.table = table.init();
});
