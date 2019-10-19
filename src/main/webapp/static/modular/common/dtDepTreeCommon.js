var DtDepTree = {
		
};

DtDepTree.initDepTree = function () {
	$('#depSerial').combotree({
		url: Feng.ctxPath + '/registration/depTree'
	});
	var depSerial = $('#depSerial').combotree('getValue');
	console.log(depSerial)
	if (depSerial) {
		$('#depSerial').combotree('setValue', depSerial);
	}
};

$(function() {
	DtDepTree.initDepTree();
});