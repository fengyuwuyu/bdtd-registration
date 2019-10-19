var Personal = {
	contextPath: "/yljz",
	getUserNo: function() {
		return document.getElementById("userNo").value;
	}
};

Personal.ajaxAsyncJson = function(url, option, callback, type) {
	$.ajax(url, {
		type : type || 'post',
		dataType : 'json',
		data : option,
		async : false,
		complete : function(data) {
		},
		error : function(response, textStatus, errorThrown) {
			
		},
		success : function(data) {
			// 坚持登录
			if ($.isFunction(callback)) {
				callback(data);
			}
		}
	});
}

Personal.openDetail = function(id, type) {
	  if (type == 1) {
	      Personal.openWithUserNo("/personal/personalExaminationHealth_detail/" + id + "/");
	  } else if (type == 2) {
		  Personal.openWithUserNo("/personal/personalPatientInfo_detail/" + id + "/");
	  } else if (type == 3) {
		  Personal.openWithUserNo("/personal/personalInHospital_detail/" + id + "/");
	  }
}

Personal.goHomePage = function() {
	window.open(Personal.contextPath + "/personal/homePage", "_self");
};

Personal.goUserInfoByUserNo = function() {
	Personal.openWithUserNo("/personal/userInfoByUserNo/");
};

Personal.goRegistration = function() {
	Personal.openWithUserNo("/personal/registration/");
};

Personal.goPersonalQuery = function() {
	Personal.openWithUserNo("/personal/personalQuery/");
};

Personal.goCommonOutpatient = function() {
	Personal.openWithUserNo("/personal/doRegistration/1/");
};

Personal.goFeverOutpatient = function() {
	Personal.openWithUserNo("/personal/doRegistration/2/");
};

Personal.goExaminationRecord = function() {
	Personal.openWithUserNo("/personal/examinationHealth/");
};

Personal.goPatientInfo = function() {
	Personal.openWithUserNo("/personal/patientInfo/");
};

Personal.goInHospital = function() {
	Personal.openWithUserNo("/personal/inHospital/");
};

Personal.goDisabilityRating = function() {
	Personal.openWithUserNo("/personal/disabilityRating/");
};

Personal.goUserInfoChoose = function() {
	Personal.openWithUserNo("/personal/personalQuery/");
}

Personal.formatDate = function(time, pattern) {
	if (!time || time == "") {
		return '';
	}
	pattern = pattern || "yyyy-MM-dd hh:mm:ss";
	var date = new Date();
	date.setTime(time);
	return date.format(pattern);
};

Personal.openWithUserNo = function(url) {
	var userNo = Personal.getUserNo();
	window.open(Personal.contextPath + url + userNo, "_self");
};

Date.prototype.format = function(fmt) { // author: meizz
var o = {
	"M+" : this.getMonth() + 1, // 月份
	"d+" : this.getDate(), // 日
	"h+" : this.getHours(), // 小时
	"m+" : this.getMinutes(), // 分
	"s+" : this.getSeconds(), // 秒
	"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
	"S" : this.getMilliseconds()
// 毫秒
};
if (/(y+)/.test(fmt))
	fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
			.substr(4 - RegExp.$1.length));
for ( var k in o)
	if (new RegExp("(" + k + ")").test(fmt))
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
				: (("00" + o[k]).substr(("" + o[k]).length)));
return fmt;
};