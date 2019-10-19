var UserSearchCommon = {
	initUserNameSearch : function(conf, callback) {
		conf = conf || {};
		var userSearch = $('#userName');
		if (!userSearch) {
			Feng.error('not find userName input');
			return;
		}
		var url = conf.url || '/registration/findByUserName';
		userSearch.combogrid({
			width: '100%',
			panelWidth: 480,
			idField: 'id',   
		    delay: 500,    
		    mode: 'remote', 
			textField: 'userName',
			url: Feng.ctxPath + url,
			columns:[[    
				{field:'id', hidden: true},    
		        {field:'userNo',title:'编号',width:100, align: 'center'},    
		        {field:'userName',title:'姓名',width:100, align: 'center'},    
		        {field:'userSex',title:'性别',width:40, align: 'center'},    
		        {field:'age',title:'年龄',width:40, align: 'center'},    
		        {field:'userDuty',title:'身份',width:60, align: 'center'},    
		        {field:'orgName',title:'单位',width:120, align: 'center'}   
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
	}
};