/**
 * 常规医嘱管理初始化
 */
var InHospitalCommonAdvice = {
    id: "InHospitalCommonAdviceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InHospitalCommonAdvice.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InHospitalCommonAdvice.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '住院id', field: 'inHospitalId', visible: false, align: 'center', valign: 'middle'},
            {title: '常规医嘱', field: 'commonAdvice', visible: true, align: 'center', valign: 'middle'},
            {title: '军医', field: 'physicianNo', visible: false, align: 'center', valign: 'middle'},
            {title: '军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
InHospitalCommonAdvice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        InHospitalCommonAdvice.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
InHospitalCommonAdvice.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        InHospitalCommonAdvice.search();
    });
};

/**
 * 点击添加常规医嘱
 */
InHospitalCommonAdvice.openAddInHospitalCommonAdvice = function () {
	var inHospitalId = $("#inHospitalId").val();
    var index = layer.open({
        type: 2,
        title: '添加常规医嘱',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/inHospitalCommonAdvice/inHospitalCommonAdvice_add/' + inHospitalId
    });
    this.layerIndex = index;
};

/**
 * 打开查看常规医嘱详情
 */
InHospitalCommonAdvice.openInHospitalCommonAdviceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '常规医嘱详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/inHospitalCommonAdvice/inHospitalCommonAdvice_update/' + InHospitalCommonAdvice.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除常规医嘱
 */
InHospitalCommonAdvice.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/inHospitalCommonAdvice/delete", function (data) {
	            Feng.success("删除成功!");
	            InHospitalCommonAdvice.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("inHospitalCommonAdviceId",InHospitalCommonAdvice.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询常规医嘱列表
 */
InHospitalCommonAdvice.search = function () {
    var queryData = $('#searchForm').serializeObject();
    InHospitalCommonAdvice.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InHospitalCommonAdvice.initColumn();
    var table = new BSTable(InHospitalCommonAdvice.id, "/inHospitalCommonAdvice/list", defaultColunms);
    var inHospitalId = $('#inHospitalId').val();
    table.setQueryParams({inHospitalId: inHospitalId});
    InHospitalCommonAdvice.table = table.init();
});
