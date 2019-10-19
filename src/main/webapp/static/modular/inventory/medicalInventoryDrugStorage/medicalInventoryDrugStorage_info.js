/**
 * 初始化药库管理详情对话框
 */
var MedicalInventoryDrugStorageInfoDlg = {
	parentId: null
};

/**
 * 关闭此对话框
 */
MedicalInventoryDrugStorageInfoDlg.close = function() {
    parent.layer.close(window.parent.MedicalInventoryDrugStorage.layerIndex);
}


/**
 * 提交添加
 */
MedicalInventoryDrugStorageInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!MedicalInventoryDrugStorageInfoDlg.parentId) {
		Feng.info("请选择药品！");
		return;
	}
	if (!Feng.validateForm(addForm)) {
		return;
	}
	
	var data = addForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryDrugStorage/add", function(data){
        Feng.success("添加成功!");
        window.parent.MedicalInventoryDrugStorage.table.refresh();
        MedicalInventoryDrugStorageInfoDlg.close();
        MedicalInventoryDrugStorageInfoDlg.parentId = null;
    });
    data['parentId'] = MedicalInventoryDrugStorageInfoDlg.parentId;
    ajax.set(data);
    ajax.start();
}


MedicalInventoryDrugStorageInfoDlg.initMedicalSearch = function() {
	$('#medicalNameSearch').combogrid({
		width: '100%',
		panelWidth: 750,
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
	    	$('#parentId').val(row['id']);
	    	MedicalInventoryDrugStorageInfoDlg.parentId = row['id'];
	    	$('#medicalNameSearch').combogrid('setValue', row['medicalName']);
	    }
	});
};

/**
 * 提交修改
 */
MedicalInventoryDrugStorageInfoDlg.editSubmit = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryDrugStorage/update", function(data){
        Feng.success("修改成功!");
        window.parent.MedicalInventoryDrugStorage.table.refresh();
        MedicalInventoryDrugStorageInfoDlg.close();
    });
    ajax.set(data);
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryDrugStorageInfoDlg.putInStorage = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryDrugStorage/putInStorage", function(data){
        Feng.success("入库成功!");
        window.parent.MedicalInventoryDrugStorage.table.refresh();
        MedicalInventoryDrugStorageInfoDlg.close();
    });
    ajax.set({id: data.id, count: data.inventoryNum});
    ajax.start();
}

/**
 * 提交修改
 */
MedicalInventoryDrugStorageInfoDlg.putOffStorage = function() {
	var editForm = $('#editForm');
	if (!Feng.validateForm(editForm)) {
		return;
	}
	
	var data = editForm.serializeObjectFilterNull();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/medicalInventoryDrugStorage/putOffStorage", function(data){
        Feng.success("出库成功!");
        window.parent.MedicalInventoryDrugStorage.table.refresh();
        MedicalInventoryDrugStorageInfoDlg.close();
    });
    ajax.set({id: data.id, count: data.inventoryNum});
    ajax.start();
}

$(function() {
	MedicalInventoryDrugStorageInfoDlg.initMedicalSearch();
});
