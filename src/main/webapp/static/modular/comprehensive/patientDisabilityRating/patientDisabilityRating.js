/**
 * 评残信息管理初始化
 */
var PatientDisabilityRating = {
    id: "PatientDisabilityRatingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientDisabilityRating.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientDisabilityRating.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '入伍时间', field: 'enrolDate', visible: true, align: 'center', valign: 'middle'},
            {title: '疾病名称', field: 'disabilityName', visible: true, align: 'center', valign: 'middle'},
            {title: '是否申请', field: 'apply', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
            	if (value == 1) {
            		return "是";
            	}
            	return "否";
            }},
            {title: '申请时间', field: 'applyDate', visible: true, align: 'center', valign: 'middle'},
            {title: '申请等级', field: 'applyRating', visible: true, align: 'center', valign: 'middle', formatter: PatientDisabilityRating.formatDisabilityRate},
            {title: '评残结果', field: 'disabilityResult', visible: true, align: 'center', valign: 'middle', formatter: PatientDisabilityRating.formatDisabilityRate},
            {title: '评残性质', field: 'crippleNature', visible: true, align: 'center', valign: 'middle', formatter: function(value, item, index) {
            		if (value == 1) {
            			return "因病";
            		} else if (value == 2) {
            			return "因公";
            		}
            		return "";
            	}
            },
            {title: '结果时间', field: 'resultDate', visible: true, align: 'center', valign: 'middle'},
    ];
};
PatientDisabilityRating.formatDisabilityRate = function(value) {
	switch(value) {
	case 1:
		return "一级";
	case 2:
		return "二级";
	case 3:
		return "三级";
	case 4:
		return "四级";
	case 5:
		return "五级";
	case 6:
		return "六级";
	case 7:
		return "七级";
	case 8:
		return "八级";
	case 9:
		return "九级";
	case 10:
		return "十级";
	case 11:
		return "未通过";
	case 12:
		return "病退";
	}
};

/**
 * 检查是否选中
 */
PatientDisabilityRating.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientDisabilityRating.seItem = selected[0];
        return true;
    }
};

PatientDisabilityRating.exportExcel = function () {
	Feng.exportData('/patientDisabilityRating/exportExcel');
};

/**
 * 重置搜索表单，并刷新
 */
PatientDisabilityRating.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientDisabilityRating.search();
    });
};

/**
 * 点击添加评残信息
 */
PatientDisabilityRating.openAddPatientDisabilityRating = function () {
    var index = layer.open({
        type: 2,
        title: '添加评残信息',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientDisabilityRating/patientDisabilityRating_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看评残信息详情
 */
PatientDisabilityRating.openPatientDisabilityRatingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '评残信息详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientDisabilityRating/patientDisabilityRating_update/' + PatientDisabilityRating.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除评残信息
 */
PatientDisabilityRating.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientDisabilityRating/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientDisabilityRating.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientDisabilityRatingId",PatientDisabilityRating.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询评残信息列表
 */
PatientDisabilityRating.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientDisabilityRating.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientDisabilityRating.initColumn();
    var table = new BSTable(PatientDisabilityRating.id, "/patientDisabilityRating/list", defaultColunms);
    PatientDisabilityRating.table = table.init();
});
