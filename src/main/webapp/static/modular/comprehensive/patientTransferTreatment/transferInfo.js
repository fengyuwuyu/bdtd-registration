/**
 * 管理初始化
 */
var PatientTransferTreatment = {
    id: "PatientTransferTreatmentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientTransferTreatment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {
            title: '序号',
            field: '',
            align: 'center',
            formatter: Feng.getTableSerialNumberFunc(PatientTransferTreatment.id)
        },
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	if (value == 3) {
        		return '<span style="color:red;">未回报</span>'
        	} else if (value == 4) {
        		return '<span style="color:green;">已回报</span>'
        	}
        }},
        {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'userSex', visible: false, align: 'center', valign: 'middle'},
        {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
        {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
        {title: '初步诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientTransferTreatment.openInitialDiagnosis('+item['patientInfoId']+');">详情</a>';
        }},
        {title: '开单汇总', field: 'billingName', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientTransferTreatment.openBillDetail('+item['id']+');">查看详细</a>';
        }},
        {title: '转诊时间', field: 'transferDate', visible: true, align: 'center', valign: 'middle'},
        {title: '转诊医院', field: 'hospital', visible: true, align: 'center', valign: 'middle'},
        {title: '医院诊断', field: 'hospitalDiagnosis', visible: true, align: 'center', valign: 'middle'},
        {title: '去向信息', field: 'hospitalDiagnosis', visible: true, align: 'center', valign: 'middle', formatter: function(value, item) {
        	return '<a href="javascript:void(0);" onclick="PatientTransferTreatment.openDispositionDetail('+item['id']+');">查看详细</a>';
        }}
    ];
};

/**
 * 检查是否选中
 */
PatientTransferTreatment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientTransferTreatment.seItem = selected[0];
        return true;
    }
};

/**
 * 重置搜索表单，并刷新
 */
PatientTransferTreatment.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
        PatientTransferTreatment.search();
    });
};

/**
 * 点击添加
 */
PatientTransferTreatment.openAddPatientTransferTreatment = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientTransferTreatment/patientTransferTreatment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
PatientTransferTreatment.openPatientTransferTreatmentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientTransferTreatment/patientTransferTreatment_update/' + PatientTransferTreatment.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
PatientTransferTreatment.delete = function () {
    if (this.check()) {
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientTransferTreatment/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientTransferTreatment.table.refresh();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientTransferTreatmentId",PatientTransferTreatment.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

/**
 * 查询列表
 */
PatientTransferTreatment.search = function () {
    var queryData = $('#searchForm').serializeObject();
    PatientTransferTreatment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PatientTransferTreatment.initColumn();
    var table = new BSTable(PatientTransferTreatment.id, "/patientTransferReport/list", defaultColunms);
    PatientTransferTreatment.table = table.init();
});
