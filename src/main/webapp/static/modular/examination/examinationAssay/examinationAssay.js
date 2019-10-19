/**
 * 化验管理初始化
 */
var ExaminationAssay = {
    id: "ExaminationAssayTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExaminationAssay.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(ExaminationAssay.id)
        },
            {title: '主键', field: 'assayId', visible: true, align: 'center', valign: 'middle'},
            {title: '肝功', field: 'liverFunction', visible: true, align: 'center', valign: 'middle'},
            {title: '(HBsAg)', field: 'HBsAg', visible: true, align: 'center', valign: 'middle'},
            {title: '胆固醇', field: 'cholesterol', visible: true, align: 'center', valign: 'middle'},
            {title: '甘油三醇', field: 'triglycerideAlcohol', visible: true, align: 'center', valign: 'middle'},
            {title: '血糖', field: 'bloodGlucose', visible: true, align: 'center', valign: 'middle'},
            {title: '血常规', field: 'bloodRoutine', visible: true, align: 'center', valign: 'middle'},
            {title: '尿常规', field: 'urineRoutine', visible: true, align: 'center', valign: 'middle'},
            {title: '便常规', field: 'stoolRoutine', visible: true, align: 'center', valign: 'middle'},
            {title: '其他', field: 'assayOther', visible: true, align: 'center', valign: 'middle'},
            {title: '体检信息id', field: 'healthExaminationId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExaminationAssay.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExaminationAssay.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
ExaminationAssay.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        ExaminationAssay.search();
    });
};

/**
 * 点击添加化验
 */
ExaminationAssay.openAddExaminationAssay = function () {
    var index = layer.open({
        type: 2,
        title: '添加化验',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/examinationAssay/examinationAssay_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看化验详情
 */
ExaminationAssay.openExaminationAssayDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '化验详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/examinationAssay/examinationAssay_update/' + ExaminationAssay.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除化验
 */
ExaminationAssay.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/examinationAssay/delete", function (data) {
	            Feng.success("删除成功!");
	            ExaminationAssay.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("examinationAssayId",ExaminationAssay.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询化验列表
 */
ExaminationAssay.search = function () {
    var queryData = $('#searchForm').serializeObject();
    ExaminationAssay.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExaminationAssay.initColumn();
    var table = new BSTable(ExaminationAssay.id, "/examinationAssay/list", defaultColunms);
    ExaminationAssay.table = table.init();
});
