/**
 * 放射检查管理初始化
 */
var PatientRadiateExamine = {
    id: "PatientRadiateExamineTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

PatientRadiateExamine.cancelHandleType = function() {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	Feng.confirm('确定要取消放射检查信息吗？', function() {
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientRadiateExamine/deleteByPatientInfoId', {patientInfoId: PatientInfo.id}, function(data) {
			PatientRadiateExamine.refresh();
		});
	});
};
PatientRadiateExamine.updateHandleType = function() {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	if (!PatientInfo.submitPatientInfo()) {
		return;
	}
	
	PatientInfo.updateHandleType(4);
};

/**
 * 初始化表格的列
 */
PatientRadiateExamine.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(PatientMedicalUsageStep.id)
            },
            {title: '部位', field: 'bodyPart', visible: true, align: 'center', valign: 'middle'},
            {title: '体位', field: 'position', visible: true, align: 'center', valign: 'middle'},
            
            {title: '病患信息id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
    ];
};



/**
 * 检查是否选中
 */
PatientRadiateExamine.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientRadiateExamine.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加放射检查
 */
PatientRadiateExamine.openAddPatientRadiateExamine = function () {
	if (!PatientInfo.checkOnDiagnosis) {
		return;
	}
    var index = layer.open({
        type: 2,
        title: '添加放射检查',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientRadiateExamine/patientRadiateExamine_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看放射检查详情
 */
PatientRadiateExamine.openPatientRadiateExamineDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '放射检查详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientRadiateExamine/patientRadiateExamine_update/' + PatientRadiateExamine.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除放射检查
 */
PatientRadiateExamine.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientRadiateExamine/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientRadiateExamine.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientRadiateExamineId",PatientRadiateExamine.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询放射检查列表
 */
PatientRadiateExamine.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientRadiateExamine.table.refresh({query: queryData});
};

PatientRadiateExamine.refresh = function () {
	PatientRadiateExamine.table.refresh({query: {patientInfoId: PatientInfo.id}});
};

$(function () {
    var defaultColunms = PatientRadiateExamine.initColumn();
    var table = new BSTable(PatientRadiateExamine.id, "/patientRadiateExamine/list", defaultColunms, {height: '100%', pagination: false});
    table.setQueryParams({patientInfoId: PatientInfo.id});
    PatientRadiateExamine.table = table.init();
});
