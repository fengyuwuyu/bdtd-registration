var PatientSearchCommon = {
	initUserNameSearch : function(conf, callback) {
		conf = conf || {};
		var userSearch = conf.el || $('#userName');
		if (!userSearch) {
			Feng.error('not find userName input');
			return;
		}
		var url = conf.url || '/patientInfo/findCanInHospital';
		userSearch.combogrid({
			width: '100%',
			panelWidth: 700,
			idField: 'id',   
		    delay: 500,    
		    mode: 'remote', 
			textField: 'userName',
			url: Feng.ctxPath + url,
			columns:[[    
				{field:'id', hidden: true},    
		        {field:'userNo',title:'编号',width:70, align: 'center'},    
		        {field:'userName',title:'姓名',width:80, align: 'center'},    
		        {field:'userSex',title:'性别',width:40, align: 'center'},    
		        {field:'age',title:'年龄',width:40, align: 'center'},    
		        {field:'userDuty',title:'身份',width:60, align: 'center'},    
		        {field:'orgName',title:'单位',width:100, align: 'center'},
		        {field:'mainDiagnosis',title:'主要诊断',width:140, align: 'center'},    
		        {field:'clinicDate',title:'诊断日期',width:140, align: 'center'}
		    ]],
		    onClickRow: function(index, row) {
		    	userSearch.combogrid('setValue', row['userName'])
		    	if (callback && $.isFunction(callback)) {
		    		callback(index, row);
		    	} else {
		    		$('#addForm').form('load', row);
		    	}
		    }
		});
	},
	initAllDoctorsSearch: function(ids) {
		if (!ids || ids.length == 0) {
			return;
		}
		
		for (var id in ids) {
			var el = $('#' + id);
			PatientSearchCommon.initUserNameSearch({el: el}, function(index, row) {
				el.combogrid('setValue', row['userName'])
			});
		}
	}
};