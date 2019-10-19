/**
 * 病患信息管理初始化
 */
var PatientInfoSearch = {
    id: "PatientInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PatientInfoSearch.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: Feng.getTableSerialNumberFunc(PatientInfoSearch.id)
            },
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value, row, index) {
            	if (value == 1) {
                	return '<span style="color: red">挂号</span>';
            	} else if (value === 2) {
            		return '<span style="color: red">诊断中</span>'; 
            	}
            	
            	if (row.handleType.indexOf('处方') == -1) {
            		return "未处方";
            	}
            	if (value == 4) {
            		return '<span style="color: green">已取药</span>';
            	} else if(value == 3) {
            		return '<span style="color: red">未取药</span>';
            	}
            }},
            {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'userSex', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'orgName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份', field: 'userDuty', visible: true, align: 'center', valign: 'middle'},
            {title: '就诊时间', field: 'clinicDate', visible: true, align: 'center', valign: 'middle'},
            {title: '主要诊断', field: 'mainDiagnosis', visible: true, align: 'center', valign: 'middle'},
            {title: '转诊', field: 'transfer', visible: true, align: 'center', valign: 'middle'},
            {title: '住院', field: 'inhospital', visible: true, align: 'center', valign: 'middle'},
            {title: '训练伤', field: 'trainWound', visible: true, align: 'center', valign: 'middle'},
            {title: '发热', field: 'fever', visible: true, align: 'center', valign: 'middle'},
            {title: '病休', field: 'sickRest', visible: true, align: 'center', valign: 'middle'},
            {title: '诊断军医', field: 'physicianName', visible: true, align: 'center', valign: 'middle'},
            
            {title: '门诊', field: 'outpatient', visible: false, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: false, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: false, align: 'center', valign: 'middle'},
            {title: '卡号', field: 'userCard', visible: false, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: false, align: 'center', valign: 'middle'},
            {title: '单位id', field: 'orgId', visible: false, align: 'center', valign: 'middle'},
            {title: '处理方式', field: 'handleType', visible: false, align: 'center', valign: 'middle'},
            {title: '军医意见', field: 'doctorOpinion', visible: false, align: 'center', valign: 'middle'},
            {title: '过敏史', field: 'irritabilityHistory', visible: false, align: 'center', valign: 'middle'},
            {title: '病情摘要', field: 'abstractIllness', visible: false, align: 'center', valign: 'middle'},
            {title: '次要诊断', field: 'minorDiagnosis', visible: false, align: 'center', valign: 'middle'},
            {title: '诊断军医', field: 'physicianNo', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PatientInfoSearch.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PatientInfoSearch.seItem = selected[0];
        return true;
    }
};

/**
 * 查询病患信息列表
 */
PatientInfoSearch.search = function () {
    var queryData = $('#searchForm').serializeObject();
    if (Feng.isNull(queryData['beginDate'])) {
    	delete queryData['beginDate'];
    }
    
    if (Feng.isNull(queryData['endDate'])) {
    	delete queryData['endDate'];
    }
    PatientInfoSearch.table.refresh({query: queryData});
};

PatientInfoSearch.resetSearchForm = function () {
    Feng.clearSearchForm(function() {
    	PatientInfoSearch.search();
    });
};

/**
 * 删除
 */
PatientInfoSearch.delete = function () {
    if (this.check()) {
		if (PatientInfoSearch.seItem.status === 4) {
			Feng.info('该患者已取药, 不能删除！');
			return
		}
		
	    var operation = function() {
	    	var ajax = new $ax(Feng.ctxPath + "/patientInfo/delete", function (data) {
	            Feng.success("删除成功!");
	            PatientInfoSearch.search();
        	}, function (data) {
            	Feng.error("删除失败!" + data.responseJSON.message + "!");
        	});
	        ajax.set("patientInfoId", PatientInfoSearch.seItem.id);
	        ajax.start();
	    };
        
        Feng.confirm("是否删除该选项？", operation);
    }
};

PatientInfoSearch.resetStatus = function() {
	if (this.check()) {
		if (PatientInfoSearch.seItem.status === 4) {
			Feng.info('该患者已取药, 不能重新诊断！');
			return
		}
		Feng.confirm('确定要重新诊断该患者吗？', function(r) {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInfo/resetStatus/' + PatientInfoSearch.seItem.id, function() {
				PatientInfoSearch.search();
			});
		});
	}
};

PatientInfoSearch.openPatientInfoDetail = function() {
	if (this.check()) {
		var index = layer.open({
            type: 2,
            title: '病患信息详情',
            area: ['1400px', '800px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/patientInfo/patientInfo_detail/' + PatientInfoSearch.seItem.id
        });
        this.layerIndex = index;
	}
}

$(function () {
    var defaultColunms = PatientInfoSearch.initColumn();
    var table = new BSTable(PatientInfoSearch.id, "/patientInfo/list", defaultColunms, {showColumns: false, showRefresh: false});
    PatientInfoSearch.table = table.init();
});
