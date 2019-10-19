/**
 * 临时医嘱药详情管理初始化
 */
var InHospitalTemporaryMedicineInfo = {
    id: "InHospitalTemporaryMedicineInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InHospitalTemporaryMedicineInfo.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InHospitalTemporaryMedicineInfo.id)
        },
        {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
        {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
        {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
        {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
        {title: '次/日', field: 'period', visible: true, align: 'center', valign: 'middle'},
        {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
        {title: '军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
        {title: '时间', field: 'adviceDate', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
        	if (value) {
        		return value.split(' ')[0]
        	}
        	return ''
        }},
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '药品id', field: 'medicineId', visible: false, align: 'center', valign: 'middle'},
        {title: '住院表表id', field: 'inHospitalId', visible: false, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusStr', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
        	if (value === '已取药') {
        		return '<span style="color:green">' + value + '</span>';
        	}
        	return '<span style="color:red">' + value + '</span>';
        }}
    ];
};

InHospitalTemporaryMedicineInfo.inhospitalPrescription = function () {
	var selected = $('#' + InHospitalTemporaryMedicineInfo.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    
    var ids = [], others = [];
    selected.forEach((item) => {
    	if (item.status === 1) {
    		ids.push(item.id)
    	} else {
    		others.push(item.id)
    	}
    });
    
    if (others.length > 0) {
    	Feng.msg('只能选择未处方的记录！');
    	return;
    }

    var inHospitalId = $('#inHospitalId').val();
    InHospitalTemporaryMedicineInfo.openPrescription(inHospitalId, JSON.stringify(ids), 2);
    
};

/**
 * 打开处方页
 */
InHospitalTemporaryMedicineInfo.openPrescription = function (inHospitalId, ids, type) {
    var index = layer.open({
        type: 2,
        title: '处方',
        area: ['800px', '600px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/inhospitalTakeMedical/openPrescription/' + inHospitalId + '/' + ids + '/' + type
    });
    this.layerIndex = index;
};

/**
 * 检查是否选中
 */
InHospitalTemporaryMedicineInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        InHospitalTemporaryMedicineInfo.seItem = selected[0];
        return true;
    }
};
InHospitalTemporaryMedicineInfo.checkSelectOne = function () {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
	if(selected.length != 1){
		Feng.info("请先选中表格中的某一记录！");
		return false;
	}else{
		InHospitalTemporaryMedicineInfo.seItem = selected[0];
		return true;
	}
};

/**
 * 重置搜索表单，并刷新
 */
InHospitalTemporaryMedicineInfo.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        InHospitalTemporaryMedicineInfo.search();
    });
};

/**
 * 点击添加临时医嘱药详情
 */
InHospitalTemporaryMedicineInfo.openAddInHospitalTemporaryMedicineInfo = function () {
	var inHospitalId = $('#inHospitalId').val();
	if (!inHospitalId) {
		Feng.error("服务器内部错误！");
		return;
	}
    var index = layer.open({
        type: 2,
        title: '添加临时医嘱药详情',
        area: ['1000px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/inHospitalTemporaryMedicineInfo/inHospitalTemporaryMedicineInfo_add/' + inHospitalId
    });
    this.layerIndex = index;
};

/**
 * 打开查看临时医嘱药详情详情
 */
InHospitalTemporaryMedicineInfo.openInHospitalTemporaryMedicineInfoDetail = function () {
    if (this.checkSelectOne()) {
    	if (InHospitalTemporaryMedicineInfo.seItem.status === 4) {
    		Feng.msg('已取药记录不允许修改！')
    		return;
    	}
    	if (InHospitalTemporaryMedicineInfo.seItem.status === 3) {
    		Feng.confirm('该记录已开单，确认要修改吗？', function() {
    			 var index = layer.open({
    		            type: 2,
    		            title: '临时医嘱药详情详情',
    		            area: ['800px', '600px'], //宽高
    		            fix: false, //不固定
    		            maxmin: true,
    		            content: Feng.ctxPath + '/inHospitalTemporaryMedicineInfo/inHospitalTemporaryMedicineInfo_update/' + InHospitalTemporaryMedicineInfo.seItem.id
    		        });
    		        this.layerIndex = index;
    		})
    	} else {
    		 var index = layer.open({
    	            type: 2,
    	            title: '临时医嘱药详情详情',
    	            area: ['800px', '600px'], //宽高
    	            fix: false, //不固定
    	            maxmin: true,
    	            content: Feng.ctxPath + '/inHospitalTemporaryMedicineInfo/inHospitalTemporaryMedicineInfo_update/' + InHospitalTemporaryMedicineInfo.seItem.id
    	        });
    	        this.layerIndex = index;
    	}
       
    }
};

/**
 * 删除临时医嘱药详情
 */
InHospitalTemporaryMedicineInfo.delete = function () {
    if (this.check()) {
    	if (InHospitalTemporaryMedicineInfo.seItem.status === 4) {
    		Feng.msg('已取药记录不允许删除！')
    		return;
    	}
    	
    	var alertMsg = "是否删除该选项？";
    	if (InHospitalTemporaryMedicineInfo.seItem.status === 3) {
    		alertMsg = "该记录已开单，是否删除该选项？";
    	}
    	
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/inHospitalTemporaryMedicineInfo/delete", function (data) {
	            Feng.success("删除成功!");
	            InHospitalTemporaryMedicineInfo.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("inHospitalTemporaryMedicineInfoId",InHospitalTemporaryMedicineInfo.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm(alertMsg, operation);
    }
};

/**
 * 查询临时医嘱药详情列表
 */
InHospitalTemporaryMedicineInfo.search = function () {
    var queryData = $('#searchForm').serializeObject();
    InHospitalTemporaryMedicineInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InHospitalTemporaryMedicineInfo.initColumn();
    var table = new BSTable(InHospitalTemporaryMedicineInfo.id, "/inHospitalTemporaryMedicineInfo/list", defaultColunms, {height: 590});
    var inHospitalId = $('#inHospitalId').val();
    table.setQueryParams({inHospitalId: inHospitalId});
    InHospitalTemporaryMedicineInfo.table = table.init();
});
