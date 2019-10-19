/**
 * 管理初始化
 */
var InhospitalTakeMedical = {
    id: "InhospitalTakeMedicalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InhospitalTakeMedical.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InhospitalTakeMedical.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '编号', field: 'userNo', visible: false, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '入伍时间', field: 'enrolDate', visible: true, align: 'center', valign: 'middle'},
            {title: '类型', field: 'typeStr', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusStr', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
            	if (item.status === 4) {
            		return '<span style="color: green">' + value + '</span>';
            	} else {
            		return '<span style="color: red">' + value + '</span>';
            	}
            }},
            {title: '主要诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '处方时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '取药时间', field: 'takeMedicalDate', visible: true, align: 'center', valign: 'middle'},
            {title: '诊断军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '操作员', field: 'operatorName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
InhospitalTakeMedical.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        InhospitalTakeMedical.seItem = selected[0];
        return true;
    }
};

InhospitalTakeMedical.openTakeMedical = function () {
	if (this.check()) {
		if (InhospitalTakeMedical.seItem.status !== 3 && InhospitalTakeMedical.seItem.status !== 4) {
    		Feng.msg('未开单记录不能取药！');
    		return ;
    	}
		var index = layer.open({
			type: 2,
			title: '处方药详情',
			area: ['800px', '600px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/inhospitalTakeMedical/openTakeMedical/' + InhospitalTakeMedical.seItem.id + '/' + InhospitalTakeMedical.seItem.type
		});
		this.layerIndex = index;
	}
};

/**
 * 重置搜索表单，并刷新
 */
InhospitalTakeMedical.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        InhospitalTakeMedical.search();
    });
};

/**
 * 点击添加
 */
InhospitalTakeMedical.openPrescriptionDetail = function () {
	if (InhospitalTakeMedical.check()) {
		 var index = layer.open({
		        type: 2,
		        title: '处方药详情',
		        area: ['1000px', '700px'], //宽高
		        fix: false, //不固定
		        maxmin: true,
		        content: Feng.ctxPath + '/inhospitalTakeMedical/openPrescriptionDetail/' + InhospitalTakeMedical.seItem.id + '/' + InhospitalTakeMedical.seItem.type
		    });
		    this.layerIndex = index;
	}
   
};

/**
 * 打开查看详情
 */
InhospitalTakeMedical.openInhospitalTakeMedicalDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['1000px', '700px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/inhospitalTakeMedical/inhospitalTakeMedical_update/' + InhospitalTakeMedical.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
InhospitalTakeMedical.delete = function () {
    if (this.check()) {
    	if (InhospitalTakeMedical.seItem.status === 4) {
    		Feng.msg('已取药记录不允许删除!');
    		return;
    	}
    	var alertMsg = "是否删除该选项？";
    	if (InhospitalTakeMedical.seItem.status === 3) {
    		alertMsg = '该记录已开单，是否删除该选项？';
    	}
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/inhospitalTakeMedical/delete", function (data) {
	            Feng.success("删除成功!");
	            InhospitalTakeMedical.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("inhospitalTakeMedicalId",InhospitalTakeMedical.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm(alertMsg, operation);
    }
};

/**
 * 查询列表
 */
InhospitalTakeMedical.search = function () {
    var queryData = $('#searchForm').serializeObject();
    InhospitalTakeMedical.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InhospitalTakeMedical.initColumn();
    var table = new BSTable(InhospitalTakeMedical.id, "/inhospitalTakeMedical/list", defaultColunms);
    var queryType = $('#queryType').val();
    table.setQueryParams({queryType: queryType});
    InhospitalTakeMedical.table = table.init();
});
