/**
 * 发热报警管理初始化
 */
var Fever = {
    id: "FeverTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Fever.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(Fever.id)
        },
            {title: '部门序号', field: 'depSerial', visible: true, align: 'center', valign: 'middle'},
            {title: '部门名称', field: 'depName', visible: true, align: 'center', valign: 'middle'},
            {title: '发热人数', field: 'count', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Fever.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Fever.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
Fever.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        Fever.search();
    });
};

/**
 * 点击添加发热报警
 */
Fever.openUpdateConfig = function () {
    var index = layer.open({
        type: 2,
        title: '修改报警参数',
        area: ['600px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/alertFever/openUpdateConfig'
    });
    this.layerIndex = index;
};

/**
 * 点击添加发热报警
 */
Fever.openDetail = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '发热患者详情',
			area: ['100%', '100%'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/alertFever/openDetail/' + Fever.seItem['depSerial']
		});
		this.layerIndex = index;
	}
};

/**
 * 删除发热报警
 */
Fever.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/alertFever/delete", function (data) {
	            Feng.success("删除成功!");
	            Fever.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("feverId",Fever.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询发热报警列表
 */
Fever.search = function () {
    var queryData = $('#searchForm').serializeObject();
    Fever.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Fever.initColumn();
    var table = new BSTable(Fever.id, "/alertFever/list", defaultColunms);
    Fever.table = table.init();
});
