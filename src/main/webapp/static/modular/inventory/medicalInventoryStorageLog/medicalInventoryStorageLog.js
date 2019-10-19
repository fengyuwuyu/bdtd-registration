/**
 * 出入库记录管理初始化
 */
var MedicalInventoryStorageLog = {
    id: "MedicalInventoryStorageLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MedicalInventoryStorageLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(MedicalInventoryStorageLog.id)
            },
            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '生产批号', field: 'produceBatchNum', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'type', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
            	if (value == 1) {
            		return "入药库";
            	} else if (value == 2) {
            		return "入药房";
            	} else if (value == 3) {
            		return "出药房";
            	}
            	return "";
            }},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '去向', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '价格', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '操作员', field: 'operatorName', visible: true, align: 'center', valign: 'middle'},
            {title: '时间', field: 'logDate', visible: true, align: 'center', valign: 'middle'}
            

           /*
            {title: '二级药品id', field: 'medicalId', visible: false, align: 'center', valign: 'middle'},
            {title: '分类', field: 'category', visible: false, align: 'center', valign: 'middle', formatter: function(value) {
            	if (value == 1) {
            		return "药库";
            	}
            	return "药房";
            }}
            {title: '进货渠道', field: 'inboundChannel', visible: false, align: 'center', valign: 'middle'},
            {title: '生产日期', field: 'produceDate', visible: false, align: 'center', valign: 'middle'},
            {title: '过期时间', field: 'expireDate', visible: false, align: 'center', valign: 'middle'},
            {title: '拼音', field: 'spell', visible: false, align: 'center', valign: 'middle'},
            {title: '生产企业', field: 'producer', visible: false, align: 'center', valign: 'middle'},
            */            
    ];
};

MedicalInventoryStorageLog.exportExcel = function () {
	Feng.exportData("/medicalInventoryStorageLog/exportExcel");
};

/**
 * 检查是否选中
 */
MedicalInventoryStorageLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MedicalInventoryStorageLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加出入库记录
 */
MedicalInventoryStorageLog.openAddMedicalInventoryStorageLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加出入库记录',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/medicalInventoryStorageLog/medicalInventoryStorageLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看出入库记录详情
 */
MedicalInventoryStorageLog.openMedicalInventoryStorageLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '出入库记录详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/medicalInventoryStorageLog/medicalInventoryStorageLog_update/' + MedicalInventoryStorageLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除出入库记录
 */
MedicalInventoryStorageLog.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/medicalInventoryStorageLog/delete", function (data) {
	            Feng.success("删除成功!");
	            MedicalInventoryStorageLog.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("medicalInventoryStorageLogId",MedicalInventoryStorageLog.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询出入库记录列表
 */
MedicalInventoryStorageLog.search = function () {
    var queryData = $('#searchForm').serializeObject();
    MedicalInventoryStorageLog.table.refresh({query: queryData});
};

/**
 * 重置搜索表单，并刷新
 */
MedicalInventoryStorageLog.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
    	MedicalInventoryStorageLog.search();
    });
};

$(function () {
    var defaultColunms = MedicalInventoryStorageLog.initColumn();
    var table = new BSTable(MedicalInventoryStorageLog.id, "/medicalInventoryStorageLog/list", defaultColunms);
    var queryData = $('#searchForm').serializeObject();
    table.setQueryParams(queryData)
    MedicalInventoryStorageLog.table = table.init();
});
