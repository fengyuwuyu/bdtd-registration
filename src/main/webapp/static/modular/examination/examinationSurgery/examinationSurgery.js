/**
 * 外科检查管理初始化
 */
var ExaminationSurgery = {
    id: "ExaminationSurgeryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExaminationSurgery.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '体检信息表id', field: 'healthExaminationId', visible: true, align: 'center', valign: 'middle'},
            {title: '既往病史', field: 'irritabilityHistory', visible: true, align: 'center', valign: 'middle'},
            {title: '检查所见', field: 'checkInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '诊断', field: 'diagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '结论', field: 'conclusion', visible: true, align: 'center', valign: 'middle'},
            {title: '医师签名', field: 'signatureResponsiblePhysician', visible: true, align: 'center', valign: 'middle'},
            {title: '主检医师', field: 'responsiblePhysician', visible: true, align: 'center', valign: 'middle'},
            {title: '复检结论', field: 'recheckConclusion', visible: true, align: 'center', valign: 'middle'},
            {title: '复检医师', field: 'recheckDoctor', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExaminationSurgery.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExaminationSurgery.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加外科检查
 */
ExaminationSurgery.openAddExaminationSurgery = function () {
    var index = layer.open({
        type: 2,
        title: '添加外科检查',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/examinationSurgery/examinationSurgery_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看外科检查详情
 */
ExaminationSurgery.openExaminationSurgeryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '外科检查详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/examinationSurgery/examinationSurgery_update/' + ExaminationSurgery.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除外科检查
 */
ExaminationSurgery.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/examinationSurgery/delete", function (data) {
	            Feng.success("删除成功!");
	            ExaminationSurgery.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("examinationSurgeryId",ExaminationSurgery.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询外科检查列表
 */
ExaminationSurgery.search = function () {
    var queryData = $('#searchForm').serializeObject();
    ExaminationSurgery.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExaminationSurgery.initColumn();
    var table = new BSTable(ExaminationSurgery.id, "/examinationSurgery/list", defaultColunms);
    ExaminationSurgery.table = table.init();
});
