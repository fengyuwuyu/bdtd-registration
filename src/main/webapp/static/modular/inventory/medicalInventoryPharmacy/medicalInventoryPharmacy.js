/**
 * 药房管理管理初始化
 */
var MedicalInventoryPharmacy = {
    id: "MedicalInventoryPharmacyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
MedicalInventoryPharmacy.openStorageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药库管理详情',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryPharmacy/medicalInventoryPharmacy_storageDetail/' + MedicalInventoryPharmacy.seItem.id
        });
        this.layerIndex = index;
    }
};

MedicalInventoryPharmacy.openPutInDrugStorage = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '退药还库',
			area: ['1000px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/medicalInventoryPharmacy/openPutInDrugStorage/' + MedicalInventoryPharmacy.seItem.id
		});
		this.layerIndex = index;
	}
};

/**
 * 初始化表格的列
 */
MedicalInventoryPharmacy.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(MedicalInventoryPharmacy.id)
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

MedicalInventoryPharmacy.exportExcel = function () {
    var queryData = $('#searchForm').serializeObject();
    if (Feng.isNull(queryData['expiredDate'])) {
    	delete queryData['expiredDate'];
    }
	Feng.exportData("/medicalInventoryDrugStorage/exportExcel", queryData);
};

MedicalInventoryPharmacy.openPutInStorage = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '入库管理详情',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/medicalInventoryPharmacy/medicalInventoryPharmacy_putInStorage/' + MedicalInventoryPharmacy.seItem.id
		});
		this.layerIndex = index;
	}
};

MedicalInventoryPharmacy.openPutOffStorage = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '出库管理详情',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/medicalInventoryPharmacy/medicalInventoryPharmacy_putOffStorage/' + MedicalInventoryPharmacy.seItem.id
		});
		this.layerIndex = index;
	}
};

/**
 * 检查是否选中
 */
MedicalInventoryPharmacy.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MedicalInventoryPharmacy.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加药房管理
 */
MedicalInventoryPharmacy.openAddMedicalInventoryPharmacy = function () {
    var index = layer.open({
        type: 2,
        title: '添加药房管理',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventoryPharmacy/medicalInventoryPharmacy_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看药房管理详情
 */
MedicalInventoryPharmacy.openMedicalInventoryPharmacyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药房管理详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryPharmacy/medicalInventoryPharmacy_update/' + MedicalInventoryPharmacy.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除药房管理
 */
MedicalInventoryPharmacy.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/medicalInventoryPharmacy/delete", function (data) {
	            Feng.success("删除成功!");
	            MedicalInventoryPharmacy.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("medicalInventoryPharmacyId",MedicalInventoryPharmacy.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 重置搜索表单，并刷新
 */
MedicalInventoryPharmacy.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
    	MedicalInventoryPharmacy.search();
    });
};

/**
 * 查询药房管理列表
 */
MedicalInventoryPharmacy.search = function () {
    var queryData = $('#searchForm').serializeObject();
    if (Feng.isNull(queryData['expiredDate'])) {
    	delete queryData['expiredDate'];
    }
    MedicalInventoryPharmacy.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MedicalInventoryPharmacy.initColumn();
    var table = new BSTable(MedicalInventoryPharmacy.id, "/medicalInventoryPharmacy/list", defaultColunms);
    MedicalInventoryPharmacy.table = table.init();
});
