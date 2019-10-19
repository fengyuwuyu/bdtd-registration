/**
 * 理疗单管理初始化
 */
var PatientPhysicalTherapy = {
    id: "PatientPhysicalTherapyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientPhysicalTherapy.submitPhysicalTherapy = function () {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	if (!PatientInfo.submitPatientInfo()) {
		return;
	}
	
	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPhysicalTherapy/submitPhysicalTherapy', {patientInfoId: PatientInfo.id});
};

PatientPhysicalTherapy.cancelPhysicalTherapy = function () {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	Feng.confirm('确定要取消理疗诊断吗？', function() {
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPhysicalTherapy/cancelPhysicalTherapy', {patientInfoId: PatientInfo.id}, function(data) {
			PatientPhysicalTherapy.refresh();
		});
	});
};



/**
 * 初始化表格的列
 */
PatientPhysicalTherapy.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(PatientPhysicalTherapy.id)
            },
            {title: '理疗方式', field: 'phyrapyType', visible: true, align: 'center', valign: 'middle'},
            {title: '开始日期', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '次/日', field: 'countPerDay', visible: true, align: 'center', valign: 'middle'},
            {title: '天数', field: 'dayCount', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
PatientPhysicalTherapy.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientPhysicalTherapy.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加理疗单
 */
PatientPhysicalTherapy.openAddPatientPhysicalTherapy = function () {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
    var index = layer.open({
        type: 2,
        title: '添加理疗单',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientPhysicalTherapy/patientPhysicalTherapy_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看理疗单详情
 */
PatientPhysicalTherapy.openPatientPhysicalTherapyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '理疗单详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientPhysicalTherapy/patientPhysicalTherapy_update/' + PatientPhysicalTherapy.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除理疗单
 */
PatientPhysicalTherapy.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientPhysicalTherapy/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientPhysicalTherapy.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientPhysicalTherapyId",PatientPhysicalTherapy.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询理疗单列表
 */
PatientPhysicalTherapy.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientPhysicalTherapy.table.refresh({query: queryData});
};

PatientPhysicalTherapy.refresh = function () {
	PatientPhysicalTherapy.table.refresh({query: {patientInfoId: PatientInfo.id}});
};

$(function () {
    var defaultColunms = PatientPhysicalTherapy.initColumn();
    var table = new BSTable(PatientPhysicalTherapy.id, "/patientPhysicalTherapy/findByPatientInfoId", defaultColunms, {height: '100%', pagination: false});
    table.setQueryParams({patientInfoId: PatientInfo.id});
    PatientPhysicalTherapy.table = table.init();
    
});
