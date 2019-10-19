/**
 * 体温记录管理初始化
 */
var InHospitalTemperatureRecord = {
    id: "InHospitalTemperatureRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InHospitalTemperatureRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InHospitalTemperatureRecord.id)
        },
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '体温', field: 'animalHeat', visible: true, align: 'center', valign: 'middle'},
            {title: '日期', field: 'recordDate', visible: true, align: 'center', valign: 'middle'},
            {title: '时段', field: 'time', visible: true, align: 'center', valign: 'middle'},
            {title: '住院id', field: 'inHospitalId', visible: true, align: 'center', valign: 'middle'},
            {title: '操作员', field: 'operatorNo', visible: true, align: 'center', valign: 'middle'},
            {title: '操作员', field: 'operatorName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
InHospitalTemperatureRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        InHospitalTemperatureRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
InHospitalTemperatureRecord.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        InHospitalTemperatureRecord.search();
    });
};

/**
 * 点击添加体温记录
 */
InHospitalTemperatureRecord.openAddInHospitalTemperatureRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加体温记录',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/inHospitalTemperatureRecord/inHospitalTemperatureRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看体温记录详情
 */
InHospitalTemperatureRecord.openInHospitalTemperatureRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '体温记录详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/inHospitalTemperatureRecord/inHospitalTemperatureRecord_update/' + InHospitalTemperatureRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除体温记录
 */
InHospitalTemperatureRecord.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/inHospitalTemperatureRecord/delete", function (data) {
	            Feng.success("删除成功!");
	            InHospitalTemperatureRecord.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("inHospitalTemperatureRecordId",InHospitalTemperatureRecord.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询体温记录列表
 */
InHospitalTemperatureRecord.search = function () {
    var queryData = $('#searchForm').serializeObject();
    InHospitalTemperatureRecord.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InHospitalTemperatureRecord.initColumn();
    var table = new BSTable(InHospitalTemperatureRecord.id, "/inHospitalTemperatureRecord/list", defaultColunms, {height: 400});
    InHospitalTemperatureRecord.table = table.init();
});
