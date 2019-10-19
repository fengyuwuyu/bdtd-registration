/**
 * 管理初始化
 */
var PatientDiabilityRating = {
    id: "PatientDiabilityRatingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientDiabilityRating.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientDiabilityRating.id)
        },
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgId', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '疾病名称', field: 'disabilityName', visible: true, align: 'center', valign: 'middle'},
            {title: '是否申请', field: 'apply', visible: true, align: 'center', valign: 'middle'},
            {title: '申请时间', field: 'applyDate', visible: true, align: 'center', valign: 'middle'},
            {title: '申请等级', field: 'applyRating', visible: true, align: 'center', valign: 'middle'},
            {title: '评残结果', field: 'disabilityResult', visible: true, align: 'center', valign: 'middle'},
            {title: '结果时间', field: 'resultDate', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientDiabilityRating.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientDiabilityRating.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
PatientDiabilityRating.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientDiabilityRating.search();
    });
};

/**
 * 点击添加
 */
PatientDiabilityRating.openAddPatientDiabilityRating = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientDiabilityRating/patientDiabilityRating_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
PatientDiabilityRating.openPatientDiabilityRatingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientDiabilityRating/patientDiabilityRating_update/' + PatientDiabilityRating.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
PatientDiabilityRating.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientDiabilityRating/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientDiabilityRating.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientDiabilityRatingId",PatientDiabilityRating.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询列表
 */
PatientDiabilityRating.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientDiabilityRating.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientDiabilityRating.initColumn();
    var table = new BSTable(PatientDiabilityRating.id, "/patientDiabilityRating/list", defaultColunms);
    PatientDiabilityRating.table = table.init();
});
