/**
 * 一般检查管理初始化
 */
var ExaminationCommon = {
    id: "ExaminationCommonTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExaminationCommon.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '发育情况', field: 'upgrowthSituation', visible: true, align: 'center', valign: 'middle'},
            {title: '婚否', field: 'married', visible: true, align: 'center', valign: 'middle'},
            {title: '身高', field: 'stature', visible: true, align: 'center', valign: 'middle'},
            {title: '体重', field: 'weight', visible: true, align: 'center', valign: 'middle'},
            {title: '血压', field: 'bloodPressure', visible: true, align: 'center', valign: 'middle'},
            {title: '既往病史', field: 'irritabilityHistory', visible: true, align: 'center', valign: 'middle'},
            {title: '医师签名', field: 'signatureResponsiblePhysician', visible: true, align: 'center', valign: 'middle'},
            {title: '主检医师', field: 'responsiblePhysician', visible: true, align: 'center', valign: 'middle'},
            {title: '复检结论', field: 'recheckConclusion', visible: true, align: 'center', valign: 'middle'},
            {title: '复检医师', field: 'recheckDoctor', visible: true, align: 'center', valign: 'middle'},
            {title: '体检信息表id', field: 'healthExaminationId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExaminationCommon.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExaminationCommon.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加一般检查
 */
ExaminationCommon.openAddExaminationCommon = function () {
    var index = layer.open({
        type: 2,
        title: '添加一般检查',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/examinationCommon/examinationCommon_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看一般检查详情
 */
ExaminationCommon.openExaminationCommonDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '一般检查详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/examinationCommon/examinationCommon_update/' + ExaminationCommon.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除一般检查
 */
ExaminationCommon.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/examinationCommon/delete", function (data) {
	            Feng.success("删除成功!");
	            ExaminationCommon.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("examinationCommonId",ExaminationCommon.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询一般检查列表
 */
ExaminationCommon.search = function () {
    var queryData = $('#searchForm').serializeObject();
    ExaminationCommon.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExaminationCommon.initColumn();
    var table = new BSTable(ExaminationCommon.id, "/examinationCommon/list", defaultColunms);
    ExaminationCommon.table = table.init();
});
