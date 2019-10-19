/**
 * 药品使用步骤管理初始化
 */
var PatientMedicalUsageStep = {
    id: "PatientMedicalUsageStepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    skinTestMedicalId: null,
    refreshSkinTestSelect: function(patientInfoId) {
    	patientInfoId = patientInfoId || -1;
    	$('#skinTestMedicalId').combobox({
    		valueField: 'id',
    		textField: 'name',
    		editable: false,
    		width: 200,
    		panelHeight: 'auto',
    		maxPanelHeight: 200,
    		url: Feng.ctxPath + '/patientPrescriptionMedicineInfo/findByPatientInfoId',
    		queryParams: {patientInfoId: patientInfoId}
    	});
    	if (PatientMedicalUsageStep.skinTestMedicalId) {
    		$('#skinTestMedicalId').combobox('setValue', PatientMedicalUsageStep.skinTestMedicalId);
    	}
		
//    	if (patientInfoId) {
//    	}
    }
};

/**
 * 初始化表格的列
 */
PatientMedicalUsageStep.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '步骤',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(PatientMedicalUsageStep.id)
            },
            {title: '医嘱内容', field: '', visible: true, align: 'center', valign: 'middle', formatter: function(value, row, index) {
            	return '<a href="javascript:void(0);" onclick="PatientMedicalUsageStep.openUsageStepMedicalEdit('+row['id']+');">详情</a>';
            }},
            {title: '患者信息表id', field: 'patientInfoId', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientMedicalUsageStep.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientMedicalUsageStep.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加药品使用步骤
 */
PatientMedicalUsageStep.openAddPatientMedicalUsageStep = function () {
	if (!PatientInfo.checkOnDiagnosis) {
		return;
	}
	
    var index = layer.open({
        type: 2,
        title: '添加药品使用步骤',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientMedicalUsageStep/patientMedicalUsageStep_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看药品使用步骤详情
 */
PatientMedicalUsageStep.openPatientMedicalUsageStepDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '药品使用步骤详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientMedicalUsageStep/patientMedicalUsageStep_update/' + PatientMedicalUsageStep.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看药品使用步骤详情
 */
PatientMedicalUsageStep.openUsageStepMedicalIndex = function () {
	if (!PatientInfo.id) {
		Feng.info('请选择要诊断的病人！');
		return;
	}
	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPrescriptionMedicineInfo/countByPatientInfoId', {patientInfoId: PatientInfo.id}, function(data) {
		if (!data.count || data.count == 0) {
			Feng.msg('处方中未选择药品，请先在处方中添加药品！');
			$('#handleTypeTab').tabs('select', 0);
			return;
		}
		
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientMedicalUsageStep/add', {patientInfoId: PatientInfo.id}, function(data) {
			if (data.success) {
				PatientMedicalUsageStep.refresh();
				var index = layer.open({
					type: 2,
					title: '药品使用步骤详情',
					area: ['1000px', '700px'], //宽高
					fix: false, //不固定
					maxmin: true,
					content: Feng.ctxPath + '/patientUsageStepMedical/' + data.id
				});
				this.layerIndex = index;
			}
			
		});
		
	
	});
};

PatientMedicalUsageStep.openUsageStepMedicalEdit = function (id) {
	if (id || this.check()) {
		var index = layer.open({
			type: 2,
			title: '药品使用步骤详情',
			area: ['1000px', '700px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/patientUsageStepMedical/' + (id || PatientMedicalUsageStep.seItem.id)
		});
	}
};

/**
 * 删除药品使用步骤
 */
PatientMedicalUsageStep.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientMedicalUsageStep/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientMedicalUsageStep.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientMedicalUsageStepId",PatientMedicalUsageStep.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询药品使用步骤列表
 */
PatientMedicalUsageStep.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientMedicalUsageStep.table.refresh({query: queryData});
};

PatientMedicalUsageStep.refresh = function () {
	PatientMedicalUsageStep.table.refresh({query: {patientInfoId: PatientInfo.id}});
};

$(function () {
    var defaultColunms = PatientMedicalUsageStep.initColumn();
    var table = new BSTable(PatientMedicalUsageStep.id, "/patientMedicalUsageStep/list", defaultColunms, {height: '100%', pagination: false});
    table.setQueryParams({patientInfoId: PatientInfo.id});
    PatientMedicalUsageStep.table = table.init();
    
    PatientMedicalUsageStep.refreshSkinTestSelect();
});
