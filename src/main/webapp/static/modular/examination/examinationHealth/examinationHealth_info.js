/**
 * 初始化体检信息详情对话框
 */
var ExaminationHealthInfoDlg = {

};

/**
 * 关闭此对话框
 */
ExaminationHealthInfoDlg.close = function() {
	parent.layer.close(window.parent.ExaminationHealth.layerIndex);
}

/**
 * 提交添加
 */
ExaminationHealthInfoDlg.addSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}

	var data = addForm.serializeObjectFilterNull();

	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/examinationHealth/add", function(data) {
		Feng.success("添加成功!");
		window.parent.ExaminationHealth.table.refresh();
		ExaminationHealthInfoDlg.close();
	});
	ajax.set(data);
	ajax.start();
}

/**
 * 提交修改
 */
ExaminationHealthInfoDlg.editSubmit = function() {
	var addForm = $('#addForm');
	if (!Feng.validateForm(addForm)) {
		return;
	}

	var data = addForm.serializeObjectFilterNull();

	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/examinationHealth/update", function(
			data) {
		Feng.success("修改成功!");
		window.parent.ExaminationHealth.table.refresh();
		ExaminationHealthInfoDlg.close();
	});
	ajax.set(data);
	ajax.start();
}

$(function() {
	if (!$('#userName').val() && window.UserSearchCommon) {
		UserSearchCommon.initUserNameSearch({}, function(index, row) {
			$('#addForm').form('load', row);
			var userNo = row.userNo;
			Feng.ajaxAsyncJson(Feng.ctxPath + '/commonPatientInfo/irritabilityHistory', {userNo: userNo}, function(data) {
				if (data) {
					$('#irritabilityHistory').val(data);
				}
			});
		});
	}
	
	$('#ecgPath').filebox({
		buttonText: '选择文件',
		width: 300,
		accept: 'image/*',
		onChange: function(newValue, oldValue) {
			
			//上传文件
			if (!Feng.isNull(newValue)) {
				var formFile = new FormData($('#addForm')[0]);
	             $.ajax({
	                   url: Feng.ctxPath + '/examinationHealth/upload',
	                   data: formFile,
	                   type: "Post",
	                   dataType: "json",
	                   cache: false,//上传文件无需缓存
	                   processData: false,//用于对data参数进行序列化处理 这里必须false
	                   contentType: false, //必须
	                   success: function (result) {
	                	   $('#ecgPath').filebox('setText', result);
	                	   
	                	   $('#oldFile').val(result);
	                	   Feng.msg("心电图上传成功！");
	                   }
	               })
			}
		}
	});
});
