/**
 * 病休管理管理初始化
 */
var PatientSickRest = {
    id: "PatientSickRestTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientSickRest.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '病患信息id', field: 'patientInfoId', visible: true, align: 'center', valign: 'middle'},
            {title: '病休方式', field: 'sickRestType', visible: true, align: 'center', valign: 'middle'},
            {title: '病休天数', field: 'sickRestDay', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '注意事项', field: 'noticeInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientSickRest.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientSickRest.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加病休管理
 */
PatientSickRest.openAddPatientSickRest = function () {
    var index = layer.open({
        type: 2,
        title: '添加病休管理',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientSickRest/patientSickRest_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看病休管理详情
 */
PatientSickRest.openPatientSickRestDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '病休管理详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientSickRest/patientSickRest_update/' + PatientSickRest.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除病休管理
 */
PatientSickRest.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientSickRest/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientSickRest.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientSickRestId",PatientSickRest.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询病休管理列表
 */
PatientSickRest.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientSickRest.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientSickRest.initColumn();
    var table = new BSTable(PatientSickRest.id, "/patientSickRest/list", defaultColunms);
    PatientSickRest.table = table.init();
});
