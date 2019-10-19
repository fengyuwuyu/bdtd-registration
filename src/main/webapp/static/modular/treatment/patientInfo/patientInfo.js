/**
 * 病患信息管理初始化
 */
var PatientInfo = {
	registrationCommonId: 'registrationCommonTable',	
	diagnosisedCommonId: 'diagnosisedCommonTable',	
	registrationFeverId: 'registrationFeverTable',	
	diagnosisedFeverId: 'diagnosisedFeverTable',	
	registrationCommonTable: null,
	diagnosisedCommonTable: null,
	registrationFeverTable: null,
	diagnosisedFeverTable: null,
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    outpatient: 1,
    id: null,
    vm: null,
    prescriptionInfoId: null,
    clinicDiagnosisId: null,
    sumbitPatientInfoSuccess: false,
    
    hideDom: function() {
    	$('#cancelInhospital').hide();
    	$('#cancelTransfer').hide();
    },
    loadAllFormData: function() {
    	if (PatientInfo.id) {
    		PatientClinicDisposisInfoDlg.loadForm();
    		PatientInfo.loadPrescriptionForm();
    		PatientSickRestInfoDlg.loadForm();
    		
    		
    		PatientPrescriptionMedicineInfo.refresh();
    		PatientMedicalUsageStep.refresh();
    		PatientRadiateExamine.refresh();
    		PatientPhysicalTherapy.refresh();
    	}
    }, 
    cancelPrescription: function() {
    	if (!PatientInfo.checkOnDiagnosis()) {
    		return;
    	}
    	
    	Feng.confirm('确定要删除处方信息吗？', function() {
    		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPrescriptionInfo/cancelPrescription', {patientInfoId: PatientInfo.id});
    		$('#prescriptionForm').form('reset');
    		PatientPrescriptionMedicineInfo.table.refresh();
    	});
    },
    updateHandleType: function(handleType, callback) {
    	if (PatientInfo.checkOnDiagnosis()) {
    		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInfo/updateHandleType', {id: PatientInfo.id, handleType: handleType}, callback);
    	}
    },
    cancelHandleType: function(handleType, callback) {
    	if (PatientInfo.checkOnDiagnosis()) {
    		Feng.confirm('确定取消吗？', function() {
    			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInfo/cancelHandleType', {id: PatientInfo.id, handleType: handleType}, callback);
    		});
    	}
    }
};

PatientInfo.submitTransferSuccess = function() {
	$('#confirmTransfer').hide();
	$('#cancelTransfer').show();
};

PatientInfo.cancelTransferSuccess = function() {
	$('#cancelTransfer').hide();
	$('#confirmTransfer').show();
};

PatientInfo.submitTransfer = function(){
	if (PatientInfo.checkOnDiagnosis()) {
		Feng.confirm('确定转诊吗？', function() {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferTreatment/add', {patientInfoId: PatientInfo.id}, function(data) {
				if (data && data.code == 200) {
					PatientInfo.submitTransferSuccess();
				} else {
					Feng.error(data.message || "服务器内部错误！");
				}
			});
		});
	}
};

PatientInfo.cancelTransfer = function(){
	if (PatientInfo.checkOnDiagnosis()) {
		Feng.confirm('确定取消转诊吗？', function() {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientTransferTreatment/cancelTransfer', {patientInfoId: PatientInfo.id}, function(data) {
				if (data && data.code == 200) {
					PatientInfo.cancelTransferSuccess();
				} else {
					Feng.error(data.message || "服务器内部错误！");
				}
			});
		});
	}
};

PatientInfo.submitInHospitalSuccess = function() {
	$('#confirmInhospital').hide();
	$('#cancelInhospital').show();
};

PatientInfo.cancelInHospitalSuccess = function() {
	$('#cancelInhospital').hide();
	$('#confirmInhospital').show();
};

PatientInfo.submitInHospital = function(){
	if (PatientInfo.checkOnDiagnosis()) {
		Feng.confirm('确定住院吗？', function() {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/add', {patientInfoId: PatientInfo.id}, function(data) {
				if (data && data.code == 200) {
					PatientInfo.submitInHospitalSuccess();
				} else {
					Feng.error(data.message || "服务器内部错误！");
				}
			});
		});
	}
};

PatientInfo.cancelInHospital = function(){
	if (PatientInfo.checkOnDiagnosis()) {
		Feng.confirm('确定取消住院吗？', function() {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInHospital/cancelInHospital', {patientInfoId: PatientInfo.id}, function(data) {
				if (data && data.code == 200) {
					PatientInfo.cancelInHospitalSuccess();
				} else {
					Feng.error(data.message || "服务器内部错误！");
				}
			});
		});
	}
};

PatientInfo.submitClinicDiagnosis = function() {
	var clinicDisgnosisForm = $('#clinicDisgnosisForm');
	if (Feng.validateForm(clinicDisgnosisForm)) {
		var data = clinicDisgnosisForm.serializeObject();
		if (PatientInfo.clinicDisgnosisId) {
			data['id'] = PatientInfo.clinicDiagnosisId;
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientClinicDisposis/update', data);
		} else {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientClinicDisposis/add', data, function(data) {
				PatientInfo.clinicDiagnosisId = data.id;
			});
		}
	}
};

PatientInfo.clearAllForm = function() {
	PatientInfo.id = null;
	PatientInfo.prescriptionInfoId = null;
	PatientInfo.clinicDiagnosisId = null;
	
	$('#userForm').form('reset');
	$('#patientInfoForm').form('reset');
	$('#prescriptionForm').form('reset');
	$('#clinicDisgnosisForm').form('reset');
	$('#sickRestForm').form('reset');
	
	PatientPrescriptionMedicineInfo.refresh();
	PatientMedicalUsageStep.refresh();
	PatientRadiateExamine.refresh();
	PatientPhysicalTherapy.refresh();
	
	PatientInfo.cancelInHospitalSuccess();
	PatientInfo.cancelTransferSuccess();
	
	
};

PatientInfo.submitPatientInfoForm = function(callback) {
	var patientInfoForm = $('#patientInfoForm');
	var userForm = $('#userForm');
	if (Feng.validateForm(patientInfoForm)) {
		var data = patientInfoForm.serializeObject();
		var irritabilityHistory = $('#irritabilityHistory').val();
		if (!Feng.isNull(irritabilityHistory)) {
			data['irritabilityHistory'] = irritabilityHistory;
		}
		data['id'] = PatientInfo.id;
		data['status'] = 3;
		data['outpatient'] = PatientInfo.outpatient;
		var userInfo = $('#userForm').serializeObject();
		data['userNo'] = userInfo['userNo'];
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInfo/update', data, callback);
	} else {
		Feng.info("诊断信息不完整！");
		
	}
};

/**
 * 初始化表格的列
 */
PatientInfo.initColumn = function (status) {
	//如果是挂号状态
	if (status && status == 1) {
		return [
			{field: 'ck', radio: true},
			{title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
			{title: 'handleType', field: 'handleType', visible: false, align: 'center', valign: 'middle'},
			{title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
			{title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'},
			{title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: function(value) {
				if (value == 1) {
					return "已挂号";
				} else if (value == 2) {
					return "诊断中";
				}
			}}
			];
	}
	
    return [
    	{title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
		{title: 'handleType', field: 'handleType', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '编号', field: 'userNo', visible: true, align: 'center', valign: 'middle'}
    ];
};

PatientInfo.bindHandTypeOnSelectFunc = function() {
	$('#handleTypeTab').tabs({
		onSelect: function(title, index) {
			if (index == 1 && PatientInfo.id) {
				PatientMedicalUsageStep.refreshSkinTestSelect(PatientInfo.id);
			}
		}
	});
}

PatientInfo.bindTabsOnSelectFunc = function() {
	$('#clinic').tabs({
		onSelect: function(title, index) {
			PatientInfo.outpatient = index + 1;
		}
	});
};

PatientInfo.callNumber = function() {
	var tableInstance = PatientInfo.registrationCommonTable;
	var table = PatientInfo.registrationCommonTable.bstableId;
	if (PatientInfo.outpatient == 2) {
		table = PatientInfo.registrationFeverTable.bstableId;
		tableInstance = PatientInfo.registrationFeverTable;
	}
	var row = Feng.getTableSelectOne($('#' + table));
	
	if (row && PatientInfo.id && row.id == PatientInfo.id) {
		Feng.info("您正在诊断此患者，请勿重复操作！");
		tableInstance.refresh();
		return;
	}
	
	if (PatientInfo.id) {
		Feng.confirm("请确认当前就诊是否已完成？", function() {
			PatientInfo.submitPatientInfoForm(function(data) {
				console.log(data)
				if (data.code && data.code == 500) {
					Feng.error(data.message);
					$('#mainDiagnosis').combobox('setValue', '');
					
					return;
				}
				PatientInfo.clearAllForm();
				PatientInfo.doCallNumberPre(row);
				$('#handleTypeTab').tabs('select', 0);
			});
		});
	} else {
		PatientInfo.doCallNumberPre(row);
	}
};

PatientInfo.doCallNumberPre = function(row) {
	if (!row) {
		return;
	}
 	
	if (row.status == 2) {
		Feng.confirm("该患者处于诊断中，是否确认叫号？", function() {
			PatientInfo.doCallNumber(row);
		});
	} else {
		PatientInfo.doCallNumber(row);
	}

};


PatientInfo.doCallNumber = function(row) {
	var ajax = new $ax(Feng.ctxPath + '/patientInfo/callNumber', function(data) {
		row['inTreatmentCount'] = data.inTreatmentCount;
		row['irritabilityHistory'] = data.irritabilityHistory;
		$('#userForm').form('load', row);
		$('#patientInfoForm').form('load', row);
		if (row && row.mainDiagnosis) {
			PatientInfo.sumbitPatientInfoSuccess = true;
		}
		PatientInfo.id = row['id'];
		
		PatientInfo.loadAllFormData();
		
		var handleType = row.handleType;
		console.log(handleType)
		if (handleType) {
			if ((handleType & 8) == 8) {
				PatientInfo.submitInHospitalSuccess();
			}
			if ((handleType & 16) == 16) {
				PatientInfo.submitTransferSuccess();
			}
		}
	});
	var currId = row ? row['id'] : null;
	var userNo = row ? row['userNo'] : null;
	ajax.setData({currId: currId, outpatient: PatientInfo.outpatient, userNo: userNo});
	ajax.start();
};

PatientInfo.refreshAllTable = function(outpatient, status) {
	PatientInfo.registrationCommonTable.refresh({query: {outpatient: 1, status: 1}});
	PatientInfo.diagnosisedCommonTable.refresh({query: {outpatient: 1, status: 3}});
	
	PatientInfo.registrationFeverTable.refresh({query: {outpatient: 2, status: 1}});
	PatientInfo.diagnosisedFeverTable.refresh({query: {outpatient: 2, status: 3}});
};

PatientInfo.websocket = function() {
	var socket;  
    if(typeof(WebSocket) == "undefined") {  
        Feng.error("您的浏览器不支持WebSocket");  
    }else{  
    	var preUrl = $('#wsServer').val();
    	console.log("ws://" + preUrl + '/treatment');
    	socket = new WebSocket("ws://" + preUrl + '/treatment');  
        //打开事件  
        socket.onopen = function() {  
            console.log("Socket 已打开");  
            socket.send("这是来自客户端的消息" + location.href + new Date());  
        };  
        //获得消息事件  
        socket.onmessage = function(msg) {  
            //发现消息进入    调后台获取  
            PatientInfo.refreshAllTable(msg.data.outpatient, msg.data.status);  
        };  
        //关闭事件  
        socket.onclose = function() {  
            console.log("Socket已关闭");  
            setTimeout(PatientInfo.websocket, 60000);
        };  
        //发生了错误事件  
        socket.onerror = function() {  
            console.log("Socket发生了错误");  
        }  
         $(window).unload(function(){  
              socket.close();  
            });  
    }
};

/**
 * 点击添加病患信息
 */
PatientInfo.openAddPatientInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加病患信息',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/patientInfo/patientInfo_add'
    });
    this.layerIndex = index;
};

PatientInfo.checkOnDiagnosis = function() {
	if (!PatientInfo.id) {
		Feng.info('请选择要诊断的病人！');
		return false;
	}
	
//	if (!PatientInfo.sumbitPatientInfoSuccess) {
//		Feng.info('请先提交病情诊断!');
//		return false;
//	}
	return true;
}

PatientInfo.loadPrescriptionForm = function() {
	Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPrescriptionInfo/findByPatientInfoId', {patientInfoId: PatientInfo.id}, function(data) {
		if (data.data) {
			$('#prescriptionForm').form('load', data.data);
			PatientInfo.prescriptionInfoId = data.data.id;
		}
	});
};

PatientInfo.submitPatientInfo = function() {
	var patientInfoForm = $('#patientInfoForm');
	if (Feng.validateForm(patientInfoForm)) {
		var data = patientInfoForm.serializeObject();
		var irritabilityHistory = $('#irritabilityHistory').val();
		if (!Feng.isNull(irritabilityHistory)) {
			data['irritabilityHistory'] = irritabilityHistory;
		}
		data['id'] = PatientInfo.id;
		data['outpatient'] = PatientInfo.outpatient;
		Feng.ajaxAsyncJson(Feng.ctxPath + '/patientInfo/update', data);
		PatientInfo.sumbitPatientInfoSuccess = true;
		return true;
	} else {
		Feng.msg('请先填写病情诊断！');
		return false;
	}
};

PatientInfo.submitPrescriptionForm = function() {
	if (!PatientInfo.checkOnDiagnosis()) {
		return;
	}
	
	if (!PatientInfo.submitPatientInfo()) {
		return;
	}
	
	var prescriptionForm = $('#prescriptionForm');
	if (Feng.validateForm(prescriptionForm)) {
		var data = prescriptionForm.serializeObject();
		data['patientInfoId'] = PatientInfo.id;
		data['status'] = 0;
		if (PatientInfo.prescriptionInfoId) {
			data['id'] = PatientInfo.prescriptionInfoId;
			if (data.remark) {
				Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPrescriptionInfo/update', data);
			}
		} else {
			Feng.ajaxAsyncJson(Feng.ctxPath + '/patientPrescriptionInfo/add', data, function(data) {
				PatientInfo.prescriptionInfoId = data.id;
				Feng.msg('保存成功');
			});
		}
	}
};

$(function () {
    var registrationColunms = PatientInfo.initColumn(1);
    var diagnosisedColunms = PatientInfo.initColumn(3);
    PatientInfo.registrationCommonTable = new BSTable(PatientInfo.registrationCommonId, "/patientInfo/listQueue", 
    		registrationColunms, {showRefresh: false, showColumns: false, height: 300, pagination: false, queryParams: {outpatient: 1, status: 1, asc: 1} });
    PatientInfo.diagnosisedCommonTable = new BSTable(PatientInfo.diagnosisedCommonId, "/patientInfo/listQueue", 
    		diagnosisedColunms, {showRefresh: false, showColumns: false, height: 300, pagination: false, queryParams: {outpatient: 1, status: 3} });
    PatientInfo.registrationFeverTable = new BSTable(PatientInfo.registrationFeverId, "/patientInfo/listQueue", 
    		registrationColunms, {showRefresh: false, showColumns: false, height: 300, pagination: false, queryParams: {outpatient: 2, status: 1, asc: 1} });
    PatientInfo.diagnosisedFeverTable = new BSTable(PatientInfo.diagnosisedFeverId, "/patientInfo/listQueue", 
    		diagnosisedColunms, {showRefresh: false, showColumns: false, height: 300, pagination: false, queryParams: {outpatient: 2, status: 3} });
    
    
    PatientInfo.registrationCommonTable.init();
    PatientInfo.diagnosisedCommonTable.init();
    PatientInfo.registrationFeverTable.init();
    PatientInfo.diagnosisedFeverTable.init();
    
    
    
    PatientInfo.websocket();
    PatientInfo.bindTabsOnSelectFunc();
    PatientInfo.bindHandTypeOnSelectFunc();
    
    PatientInfo.hideDom();
    
});
