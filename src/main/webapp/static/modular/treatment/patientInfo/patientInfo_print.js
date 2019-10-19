var PatientInfoPrint = {
	layerIndex: null,
	openPrintPrescription: function() {
		if (this.canPrint()) {
			var index = layer.open({
		        type: 2,
		        title: '处方笺',
		        area: ['800px', '550px'], //宽高
		        fix: false, //不固定
		        maxmin: true,
		        content: Feng.ctxPath + '/printPatientInfo/openPrescription/' + PatientInfo.id
		    });
			this.layerIndex = index;
		}
	},
	openDiagnosisProve: function() {
		if (this.canPrint()) {
			var index = layer.open({
		        type: 2,
		        title: '诊断证明',
		        area: ['800px', '550px'], //宽高
		        fix: false, //不固定
		        maxmin: true,
		        content: Feng.ctxPath + '/printPatientInfo/openDiagnosisProve/' + PatientInfo.id
		    });
			this.layerIndex = index;
		}
	},
	openPhysicalTherapy: function() {
		if (this.canPrint()) {
			var index = layer.open({
				type: 2,
				title: '理疗单',
				area: ['800px', '550px'], //宽高
				fix: false, //不固定
				maxmin: true,
				content: Feng.ctxPath + '/printPatientInfo/openPhysicalTherapy/' + PatientInfo.id
			});
			this.layerIndex = index;
		}
	},
	openDiagnosis: function() {
		if (this.canPrint()) {
			var index = layer.open({
				type: 2,
				title: '门诊处置单',
				area: ['800px', '550px'], //宽高
				fix: false, //不固定
				maxmin: true,
				content: Feng.ctxPath + '/printPatientInfo/openDiagnosis/' + PatientInfo.id
			});
			this.layerIndex = index;
		}
	},
	openRadiateCheck: function() {
		if (this.canPrint()) {
			var index = layer.open({
				type: 2,
				title: '放射检查单',
				area: ['800px', '550px'], //宽高
				fix: false, //不固定
				maxmin: true,
				content: Feng.ctxPath + '/printPatientInfo/openRadiateCheck/' + PatientInfo.id
			});
			this.layerIndex = index;
		}
	}, 
	canPrint: function() {
		if (!PatientInfo.id) {
			Feng.info('当前没有诊断的患者！');
			return false;
		}
		
		console.log(PatientInfo.sumbitPatientInfoSuccess)
		if (!PatientInfo.sumbitPatientInfoSuccess) {
			Feng.info('请先提交病情诊断！');
			return false;
		}
		return true;
	}
}