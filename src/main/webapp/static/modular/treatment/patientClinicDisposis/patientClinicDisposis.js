/**
 * 门诊处置管理初始化
 */
var PatientClinicDisposis = {
    id: "PatientClinicDisposisTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientClinicDisposis.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '患者信息id', field: 'patientInfoId', visible: true, align: 'center', valign: 'middle'},
            {title: '皮试药品id', field: 'skinTestMedicalId', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientClinicDisposis.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientClinicDisposis.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加门诊处置
 */
PatientClinicDisposis.openAddPatientClinicDisposis = function () {
    var index = layer.open({
        type: 2,
        title: '添加门诊处置',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientClinicDisposis/patientClinicDisposis_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看门诊处置详情
 */
PatientClinicDisposis.openPatientClinicDisposisDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '门诊处置详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientClinicDisposis/patientClinicDisposis_update/' + PatientClinicDisposis.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除门诊处置
 */
PatientClinicDisposis.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientClinicDisposis/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientClinicDisposis.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientClinicDisposisId",PatientClinicDisposis.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询门诊处置列表
 */
PatientClinicDisposis.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientClinicDisposis.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientClinicDisposis.initColumn();
    var table = new BSTable(PatientClinicDisposis.id, "/patientClinicDisposis/list", defaultColunms);
    PatientClinicDisposis.table = table.init();
});
