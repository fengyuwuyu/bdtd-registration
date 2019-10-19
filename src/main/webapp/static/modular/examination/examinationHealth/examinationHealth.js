/**
 * 体检信息管理初始化
 */
var ExaminationHealth = {
    id: "ExaminationHealthTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExaminationHealth.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(ExaminationHealth.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '体检类别', field: 'examinationType', visible: true, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '入伍时间', field: 'enrolDate', visible: true, align: 'center', valign: 'middle'},
            {title: '体检结果', field: 'recheckConclusion', visible: true, align: 'center', valign: 'middle', width: 150},
            {title: '主检医师', field: 'responsiblePhysician', visible: true, align: 'center', valign: 'middle'},
            {title: '体检日期', field: 'examinationDate', visible: true, align: 'center', valign: 'middle'},
    ];
};

ExaminationHealth.download = function () {
	if (this.check()) {
		Feng.ajaxAsyncJson(Feng.ctxPath + '/examinationHealth/findEcgPathById', {id: this.seItem['id']}, function(data) {
			if (data.data && !Feng.isNull(data.data.ecgPath)) {
				$('#fileName').val(data.data.ecgPath);
				Feng.exportData('/registration/download', $('#download').serialize());
			} else {
				Feng.info('该患者没有上传心电图！');
			}
		});
	}
};


/**
 * 检查是否选中
 */
ExaminationHealth.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExaminationHealth.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
ExaminationHealth.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        ExaminationHealth.search();
    });
};

/**
 * 点击添加体检信息
 */
ExaminationHealth.openAddExaminationHealth = function () {
    var index = layer.open({
        type: 2,
        title: '添加体检信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/examinationHealth/examinationHealth_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看体检信息详情
 */
ExaminationHealth.openExaminationHealthDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '体检信息详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/examinationHealth/examinationHealth_update/' + ExaminationHealth.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除体检信息
 */
ExaminationHealth.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/examinationHealth/delete", function (data) {
	            Feng.success("删除成功!");
	            ExaminationHealth.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("examinationHealthId",ExaminationHealth.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询体检信息列表
 */
ExaminationHealth.search = function () {
    var queryData = $('#searchForm').serializeObject();
    ExaminationHealth.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExaminationHealth.initColumn();
    var table = new BSTable(ExaminationHealth.id, "/examinationHealth/list", defaultColunms);
    ExaminationHealth.table = table.init();
});
