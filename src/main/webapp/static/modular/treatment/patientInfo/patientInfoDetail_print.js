var PatientInfoPrint = {
	layerIndex: null,
	id: null,
	openPrintPrescription: function() {
		if (this.canPrint()) {
			var index = layer.open({
		        type: 2,
		        title: '处方笺',
		        area: ['800px', '550px'], //宽高
		        fix: false, //不固定
		        maxmin: true,
		        content: Feng.ctxPath + '/printPatientInfo/openPrescription/' + PatientInfoPrint.id
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
		        content: Feng.ctxPath + '/printPatientInfo/openDiagnosisProve/' + PatientInfoPrint.id
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
				content: Feng.ctxPath + '/printPatientInfo/openPhysicalTherapy/' + PatientInfoPrint.id
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
				content: Feng.ctxPath + '/printPatientInfo/openDiagnosis/' + PatientInfoPrint.id
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
				content: Feng.ctxPath + '/printPatientInfo/openRadiateCheck/' + PatientInfoPrint.id
			});
			this.layerIndex = index;
		}
	}, 
	canPrint: function() {
		var id = $('#patientInfoId').val();
		console.log(id)
		if (!id) {
			return false;
		}
		PatientInfoPrint.id = id;
		return true;
	}
}