var DtDepTree = {
	data: null
};



DtDepTree.initDepTree = function () {
	Feng.ajaxAsyncJson(Feng.ctxPath + '/registration/depSelect', {}, function(data) {
		if (data) {
			DtDepTree.data = data;
			$('#orgId').combobox({
//				url: Feng.ctxPath + '/registration/depSelect',
				valueField: 'id',    
		        textField: 'name', 
				panelMaxHeight: 200,
				panelHeight: 'auto',
				data: data,
				onChange: function(newValue, oldValue) {
					if (Feng.isNull(newValue)) {
						$('#orgId').combobox('loadData', DtDepTree.data);
						return;
					}
					var arr = []
					DtDepTree.data.forEach(function(item) {
						if (item.name.indexOf(newValue) != -1) {
							arr.push(item)
						}
					});
					$('#orgId').combobox('loadData', arr);
				}
			});
		}
	})
};

$(function() {
	DtDepTree.initDepTree();
});