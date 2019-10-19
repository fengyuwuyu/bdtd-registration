/**
 * 长期医嘱药详情管理初始化
 */
var InHospitalLongtermMedicineInfo = {
    id: "InHospitalLongtermMedicineInfoTable",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InHospitalLongtermMedicineInfo.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InHospitalLongtermMedicineInfo.id)
        },
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '药品id', field: 'medicineId', visible: false, align: 'center', valign: 'middle'},
            {title: '组别', field: 'groupLevel', visible: true, align: 'center', valign: 'middle'},
            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
            {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
            {title: '次/日', field: 'period', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endDate', visible: true, align: 'center', valign: 'middle'},
            {title: '军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            {title: '住院表表id', field: 'inHospitalId', visible: false, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusStr', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
            	if (value === '已取药') {
            		return '<span style="color:green">' + value + '</span>';
            	}
            	return '<span style="color:red">' + value + '</span>';
            }}
    ];
};

/**
 * 检查是否选中
 */
InHospitalLongtermMedicineInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        InHospitalLongtermMedicineInfo.seItem = selected[0];
        return true;
    }
};

InHospitalLongtermMedicineInfo.checkSelectOne = function () {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
	if(selected.length != 1){
		Feng.info("请先选中表格中的某一记录！");
		return false;
	}else{
		InHospitalLongtermMedicineInfo.seItem = selected[0];
		return true;
	}
};

InHospitalLongtermMedicineInfo.inhospitalPrescription = function () {
	var selected = $('#' + InHospitalLongtermMedicineInfo.id).bootstrapTable('getSelections');
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
    InHospitalLongtermMedicineInfo.openPrescription(inHospitalId, JSON.stringify(ids), 1);
    
};

/**
 * 打开处方页
 */
InHospitalLongtermMedicineInfo.openPrescription = function (inHospitalId, ids, type) {
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
 * 重置搜索表单，并刷新
 */
InHospitalLongtermMedicineInfo.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        InHospitalLongtermMedicineInfo.search();
    });
};

/**
 * 点击添加长期医嘱药详情
 */
InHospitalLongtermMedicineInfo.openAddInHospitalLongtermMedicineInfo = function () {
	var inHospitalId = $('#inHospitalId').val();
	if (!inHospitalId) {
		Feng.error("服务器内部错误！");
		return;
	}
    var index = layer.open({
        type: 2,
        title: '添加长期医嘱药详情',
        area: ['1000px', '600px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/inHospitalLongtermMedicineInfo/inHospitalLongtermMedicineInfo_add/' + inHospitalId
    });
    this.layerIndex = index;
};

/**
 * 打开查看长期医嘱药详情详情
 */
InHospitalLongtermMedicineInfo.openInHospitalLongtermMedicineInfoDetail = function () {
    if (this.checkSelectOne()) {
    	if (InHospitalLongtermMedicineInfo.seItem.status === 4) {
    		Feng.msg('已取药记录不允许修改！')
    		return;
    	}
    	if (InHospitalLongtermMedicineInfo.seItem.status === 3) {
    		Feng.confirm('该记录已开单，确认要修改吗？', function() {
    			var index = layer.open({
    	            type: 2,
    	            title: '长期医嘱药详情详情',
    	            area: ['800px', '600px'], // 宽高
    	            fix: false, // 不固定
    	            maxmin: true,
    	            content: Feng.ctxPath + '/inHospitalLongtermMedicineInfo/inHospitalLongtermMedicineInfo_update/' + InHospitalLongtermMedicineInfo.seItem.id
    	        });
    	        this.layerIndex = index;
    		})
    	} else {
    		var index = layer.open({
                type: 2,
                title: '长期医嘱药详情详情',
                area: ['800px', '600px'], // 宽高
                fix: false, // 不固定
                maxmin: true,
                content: Feng.ctxPath + '/inHospitalLongtermMedicineInfo/inHospitalLongtermMedicineInfo_update/' + InHospitalLongtermMedicineInfo.seItem.id
            });
            this.layerIndex = index;
    	}
    }
};

/**
 * 删除长期医嘱药详情
 */
InHospitalLongtermMedicineInfo.delete = function () {
    if (this.check()) {
    	if (InHospitalLongtermMedicineInfo.seItem.status === 4) {
    		Feng.msg('已取药记录不允许删除！')
    		return;
    	}
    	
    	var alertMsg = "是否删除该选项？";
    	if (InHospitalLongtermMedicineInfo.seItem.status === 3) {
    		alertMsg = "该记录已开单，是否删除该选项？";
    	}
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/inHospitalLongtermMedicineInfo/delete", function (data) {
	            Feng.success("删除成功!");
	            InHospitalLongtermMedicineInfo.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("inHospitalLongtermMedicineInfoId",InHospitalLongtermMedicineInfo.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm(alertMsg, operation);
    }
};

/**
 * 查询长期医嘱药详情列表
 */
InHospitalLongtermMedicineInfo.search = function () {
    var queryData = $('#searchForm').serializeObject();
    InHospitalLongtermMedicineInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InHospitalLongtermMedicineInfo.initColumn();
    var table = new BSTable(InHospitalLongtermMedicineInfo.id, "/inHospitalLongtermMedicineInfo/list", defaultColunms, {height: 590});
    var inHospitalId = $('#inHospitalId').val();
    table.setQueryParams({inHospitalId: inHospitalId});
    InHospitalLongtermMedicineInfo.table = table.init();
});
