var Registration = {
	layerIndex : null	
};

Registration.openDetail = function(id) {
	var index = layer.open({
        type: 2,
        title: '体检信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: false,
        closeBtn: 0,
        content: Feng.ctxPath + '/personal/personalExaminationHealth_detail' + Feng.makeGetParams({})
    });
    this.layerIndex = index;
}

