/**
 * 管理初始化
 */
var DtUser = {
    id: "DtUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DtUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(DtUser.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userLname', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '出生日期', field: 'userBirthday', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	if (value) {
            		return value.split(" ")[0];
            	}
            	return '-';
            }},
            {title: '单位', field: 'userDepname', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '入伍时间', field: 'userWorkday', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	if (value) {
            		return value.split(" ")[0];
            	}
            	return '-';
            }}
    ];
};

/**
 * 检查是否选中
 */
DtUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DtUser.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
DtUser.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        DtUser.search();
    });
};

/**
 * 查询列表
 */
DtUser.search = function () {
    var queryData = $('#searchForm').serializeObject();
    DtUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = DtUser.initColumn();
    var table = new BSTable(DtUser.id, "/registration/userList", defaultColunms);
//    table.setPaginationType("client");
    DtUser.table = table.init();
});
