function showDate() {
	var date = new Date();
	document.getElementById("footer-time").innerHTML = Personal.formatDate(date.getTime(), "yyyy年MM月dd hh时mm分") + " " + "星期" + "日一二三四五六".charAt(date.getDay());
	setInterval(() => {
		date = new Date();
		if (date.getSeconds() == 1) {
			document.getElementById("footer-time").innerHTML = Personal.formatDate(date.getTime(), "yyyy年MM月dd hh时mm分") + " " + "星期" + "日一二三四五六".charAt(date.getDay());
		}
	}, 1000);
}

window.onload = function() {
	showDate();
}