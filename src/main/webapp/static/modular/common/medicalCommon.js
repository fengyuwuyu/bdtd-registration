var MedicalCommon = {
}

MedicalCommon.initMedicalSearch = function(callFunc) {
	$('#medicalName').combogrid({
		width: '100%',
		panelWidth: 1000,
		idField: 'id',   
	    delay: 500, 
	    fitColumns: true,
	    mode: 'remote', 
		textField: 'spell',
		url: Feng.ctxPath + '/medicalInventoryPharmacy/findByMedicalSpell',
		columns:[[    
			{field:'id', hidden: true},    
	        {field:'medicalName',title:'药品名称',width:100, align: 'center', formatter: Feng.formatterExpireDate()},    
	        {field:'spell',title:'拼音',width:80, align: 'center', formatter: Feng.formatterExpireDate()},    
	        {field:'producer',title:'生产企业',width:100, align: 'center', formatter: Feng.formatterExpireDate()},    
	        {field:'price',title:'价格',width:100, align: 'center', formatter: Feng.formatterExpireDate()},    
	        {field:'specificationStr',title:'规格',width:60, align: 'center', formatter: Feng.formatterExpireDate()},    
	        {field:'unitStr',title:'单位',width:50, align: 'center', formatter: Feng.formatterExpireDate()},    
	        {field:'inventoryNum',title:'库存',width:50, align: 'center', formatter: Feng.formatterExpireDate()},  
	        {field:'produceDate',title:'生产日期',width:80, align: 'center', formatter: Feng.formatterExpireDate()},   
	        {field:'expireDate',title:'过期日期',width:80, align: 'center', formatter: Feng.formatterExpireDate()},   
	        {field:'remark',title:'备注',width:100, align: 'center', formatter: Feng.formatterExpireDate()}   
	    ]],
	    onClickRow: function(index, row) {
	    	if (callFunc && $.isFunction(callFunc)) {
	    		callFunc(index, row);
	    	} else {
	    		$('#medicalName').combogrid('setValue', row.medicalName);
	    		$('#medicineId').val(row.id);
	    		if ($('#unit')) {
	    			$('#unit').val(row.unit);
	    		}
	    		if ($('#specification')) {
	    			$('#specification').val(row.specification);
	    		}
	    	}
	    }
	});
};
