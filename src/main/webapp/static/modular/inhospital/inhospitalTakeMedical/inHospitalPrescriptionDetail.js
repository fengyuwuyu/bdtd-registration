var InHospitalPrescriptionDetail = {
    id: "InHospitalPrescriptionDetailTable",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InHospitalPrescriptionDetail.initColumn = function () {
	return [
        {field: 'selectItem', radio: true},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(InHospitalPrescriptionDetail.id)
        },
        {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
        {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
        {title: '规格', field: 'specification', visible: true, align: 'center', valign: 'middle'},
        {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
        {title: '次/日', field: 'period', visible: true, align: 'center', valign: 'middle'},
        {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
        {title: '军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusStr', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
        	if (value === '已取药') {
        		return '<span style="color:green">' + value + '</span>';
        	}
        	return '<span style="color:red">' + value + '</span>';
        }}
    ];
//	var type = $('#type').val();
//	if (type == 2) {
//		return [
//	        {field: 'selectItem', radio: true},
//	        {
//	            title: '序号',
//	            field: '',
//	            align: 'center',
//	            formatter: Feng.getTableSerialNumberFunc(InHospitalPrescriptionDetail.id)
//	        },
//	            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
//	            {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
//	            {title: '次/日', field: 'period', visible: true, align: 'center', valign: 'middle'},
//	            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
//	            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
//	            {title: '军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
//	            {title: '时间', field: 'adviceDate', visible: true, align: 'center', valign: 'middle'},
//	            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
//	            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
//	            {title: '药品id', field: 'medicineId', visible: false, align: 'center', valign: 'middle'},
//	            {title: '住院表表id', field: 'inHospitalId', visible: false, align: 'center', valign: 'middle'},
//	            {title: '状态', field: 'statusStr', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
//	            	if (value === '已取药') {
//	            		return '<span style="color:green">' + value + '</span>';
//	            	}
//	            	return '<span style="color:red">' + value + '</span>';
//	            }}
//	    ];
//	}
//    return [
//        {field: 'selectItem', radio: true},
//        {
//            title: '序号',
//            field: '',
//            align: 'center',
//            formatter: Feng.getTableSerialNumberFunc(InHospitalPrescriptionDetail.id)
//        },
//            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
//            {title: '药品id', field: 'medicineId', visible: false, align: 'center', valign: 'middle'},
//            {title: '组别', field: 'groupLevel', visible: true, align: 'center', valign: 'middle'},
//            {title: '药品名称', field: 'medicalName', visible: true, align: 'center', valign: 'middle'},
//            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
//            {title: '数量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
//            {title: '用量', field: 'dosage', visible: true, align: 'center', valign: 'middle'},
//            {title: '用法', field: 'usage', visible: true, align: 'center', valign: 'middle'},
//            {title: '次/日', field: 'period', visible: true, align: 'center', valign: 'middle'},
//            {title: '开始时间', field: 'beginDate', visible: true, align: 'center', valign: 'middle'},
//            {title: '结束时间', field: 'endDate', visible: true, align: 'center', valign: 'middle'},
//            {title: '军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
//            {title: '住院表表id', field: 'inHospitalId', visible: false, align: 'center', valign: 'middle'},
//            {title: '状态', field: 'statusStr', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
//            	if (item.status === 4) {
//            		return '<span style="color: green">' + value + '</span>';
//            	} else {
//            		return '<span style="color: red">' + value + '</span>';
//            	}
//            }},
//            {title: '军医', field: 'physicianNo', visible: false, align: 'center', valign: 'middle'}
//    ];
};

InHospitalPrescriptionDetail.openPrescriptionPrint = function () {
	var id = $('#id').val();
    var type = $('#type').val();
    var index = layer.open({
        type: 2,
        title: '处方详情',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/inhospitalTakeMedical/openPrescriptionPrint/' + id + '/' + type
    });
    this.layerIndex = index;
    
	setTimeout(() => {
		InHospitalPrescriptionDetail.table.refresh();
	}, 1000);
};

/**
 * 检查是否选中
 */
InHospitalPrescriptionDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        InHospitalPrescriptionDetail.seItem = selected[0];
        return true;
    }
};

/**
 * 关闭此对话框
 */
InHospitalPrescriptionDetail.close = function() {
    parent.layer.close(window.parent.InhospitalTakeMedical.layerIndex);
}


/**
 * 删除长期医嘱药详情
 */
InHospitalPrescriptionDetail.delete = function () {
    if (this.check()) {
    	var status = InHospitalPrescriptionDetail.seItem.status;
    	if (status === 4) {
    		Feng.msg('已取药记录不允许删除！');
    		return;
    	}
    	
    	var alertMsg = "是否删除该选项？";
    	if (status === 3) {
    		alertMsg = "该记录已开单，是否删除该选项？";
    	}
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/inhospitalTakeMedical/deleteMedical", function (data) {
	            Feng.success("删除成功!");
	            InHospitalPrescriptionDetail.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("id", InHospitalPrescriptionDetail.seItem.id);
	        ajax.set("type", $('#type').val());
	        ajax.start();
	    };
        
        Feng.confirm(alertMsg, operation);
    }
};

$(function () {
    var defaultColunms = InHospitalPrescriptionDetail.initColumn();
    var table = new BSTable(InHospitalPrescriptionDetail.id, "/inhospitalTakeMedical/medicalList", defaultColunms, {height: 590});
    var id = $('#id').val();
    var type = $('#type').val();
    table.setQueryParams({id: id, type: type});
    InHospitalPrescriptionDetail.table = table.init();
});

