/**
 * 报警部门管理初始化
 */
var AlertDep = {
    id: "AlertDepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AlertDep.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(AlertDep.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '部门序号', field: 'depSerial', visible: false, align: 'center', valign: 'middle'},
            {title: '部门编号', field: 'depNo', visible: true, align: 'center', valign: 'middle'},
            {title: '部门名称', field: 'depName', visible: true, align: 'center', valign: 'middle'},
            {title: '上级部门', field: 'parentName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AlertDep.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AlertDep.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
AlertDep.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        AlertDep.search();
    });
};

/**
 * 点击添加报警部门
 */
AlertDep.openAddAlertDep = function () {
    var index = layer.open({
        type: 2,
        title: '添加报警部门',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/alertDep/alertDep_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看报警部门详情
 */
AlertDep.openAlertDepDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '报警部门详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/alertDep/alertDep_update/' + AlertDep.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除报警部门
 */
AlertDep.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/alertDep/delete", function (data) {
	            Feng.success("删除成功!");
	            AlertDep.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("alertDepId",AlertDep.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询报警部门列表
 */
AlertDep.search = function () {
    var queryData = $('#searchForm').serializeObject();
    AlertDep.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AlertDep.initColumn();
    var table = new BSTable(AlertDep.id, "/alertDep/list", defaultColunms);
    AlertDep.table = table.init();
});
