/**
 * 内科检查管理初始化
 */
var ExaminationInternalMedicine = {
    id: "ExaminationInternalMedicineTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExaminationInternalMedicine.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主检', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '体检信息id', field: 'healthExaminationId', visible: true, align: 'center', valign: 'middle'},
            {title: '心电图', field: 'ecgPath', visible: true, align: 'center', valign: 'middle'},
            {title: '医师签名', field: 'ecgDoctor', visible: true, align: 'center', valign: 'middle'},
            {title: '胸部透视', field: 'chestFluoroscopy', visible: true, align: 'center', valign: 'middle'},
            {title: '医师签名', field: 'cfDoctor', visible: true, align: 'center', valign: 'middle'},
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
ExaminationInternalMedicine.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExaminationInternalMedicine.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加内科检查
 */
ExaminationInternalMedicine.openAddExaminationInternalMedicine = function () {
    var index = layer.open({
        type: 2,
        title: '添加内科检查',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/examinationInternalMedicine/examinationInternalMedicine_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看内科检查详情
 */
ExaminationInternalMedicine.openExaminationInternalMedicineDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '内科检查详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/examinationInternalMedicine/examinationInternalMedicine_update/' + ExaminationInternalMedicine.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除内科检查
 */
ExaminationInternalMedicine.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/examinationInternalMedicine/delete", function (data) {
	            Feng.success("删除成功!");
	            ExaminationInternalMedicine.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("examinationInternalMedicineId",ExaminationInternalMedicine.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询内科检查列表
 */
ExaminationInternalMedicine.search = function () {
    var queryData = $('#searchForm').serializeObject();
    ExaminationInternalMedicine.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExaminationInternalMedicine.initColumn();
    var table = new BSTable(ExaminationInternalMedicine.id, "/examinationInternalMedicine/list", defaultColunms);
    ExaminationInternalMedicine.table = table.init();
});
