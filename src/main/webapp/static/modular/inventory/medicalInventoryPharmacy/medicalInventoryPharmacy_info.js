/**
 * 初始化药房管理详情对话框
 */
var MedicalInventoryPharmacyInfoDlg = {
		medicalId: null
};

/**
 * 关闭此对话框
 */
MedicalInventoryPharmacyInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventoryPharmacy.layerIndex);
}

MedicalInventoryPharmacyInfoDlg.initMedicalSearch = function(callFunc) {
	$('#medicalNameSearch').combogrid({
		width: '100%',
		panelWidth: 600,
		idField: 'id',   
	    delay: 500, 
	    fitColumns: true,
	    mode: 'remote', 
		textField: 'spell',
		url: Feng.ctxPath + '/medicalInventoryStair/findByMedicalSpell',
		columns:[[    
			{field:'id', hidden: true},    
	        {field:'medicalName',title:'药品名称',width:100, align: 'center'},    
	        {field:'spell',title:'拼音',width:80, align: 'center'},    
	        {field:'producer',title:'生产企业',width:100, align: 'center'},    
	        {field:'specification',title:'规格',width:60, align: 'center'},    
	        {field:'unit',title:'单位',width:50, align: 'center'},   
	        {field:'remark',title:'备注',width:100, align: 'center'}   
	    ]],
	    onClickRow: function(index, row) {
	    	if (callFunc && $.isFunction(callFunc)) {
	    		callFunc(index, row);
	    	} else {
	    		$('#parentId').val(row['id']);
	    		MedicalInventoryPharmacyInfoDlg.parentId = row['id'];
	    		$('#medicalNameSearch').combogrid('setValue', row['medicalName']);
	    	}
	    }
	});
};


/**
 * 提交添加
 */
MedicalInventoryPharmacyInfoDlg.addSubmit = function() {
	if (!MedicalInventoryPharmacyInfoDlg.parentId) {
		Feng.info("请选择药品！");
		return;
	}
	
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryPharmacy/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventoryPharmacy.table.refresh();
        MedicalInventoryPharmacyInfoDlg.close();
        MedicalInventoryPharmacyInfoDlg.parentId = null;
    });
    data['parentId'] = MedicalInventoryPharmacyInfoDlg.parentId;
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryPharmacyInfoDlg.putInStorage = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryPharmacy/putInStorage", function(data){
        Feng.success("入库成功!");
        window.parent.MedicalInventoryPharmacy.table.refresh();
        MedicalInventoryPharmacyInfoDlg.close();
    });
    ajax.set({id: data.id, count: data.inventoryNum});
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryPharmacyInfoDlg.putInDrugStorage = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();
	
	//提交信息
	var ajax = new $ax(Feng.ctxPath + "/medicalInventoryPharmacy/putInDrugStorage", function(data){
		Feng.success("退药还库成功!");
		window.parent.MedicalInventoryPharmacy.table.refresh();
		MedicalInventoryPharmacyInfoDlg.close();
	});
	ajax.set({id: data.id, count: data.inventoryNum, orgId: data.orgId});
	ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryPharmacyInfoDlg.putOffStorage = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryPharmacy/putOffStorage", function(data){
        Feng.success("出库成功!");
        window.parent.MedicalInventoryPharmacy.table.refresh();
        MedicalInventoryPharmacyInfoDlg.close();
    });
    ajax.set({id: data.id, count: data.inventoryNum, orgId: data.orgId});
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryPharmacyInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryPharmacy/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventoryPharmacy.table.refresh();
        MedicalInventoryPharmacyInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

$(function() {
	MedicalInventoryPharmacyInfoDlg.initMedicalSearch();
});
