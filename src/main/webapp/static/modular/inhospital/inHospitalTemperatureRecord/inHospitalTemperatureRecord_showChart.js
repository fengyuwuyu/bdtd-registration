/**
 * 体温记录管理初始化
 */
var InHospitalTemperatureRecordShowChart = {
	drawChart : function() {
		Feng.ajaxAsyncJson(Feng.ctxPath + '/inHospitalTemperatureRecord/chartList', {inHospitalId: $('#inHospitalId').val()}, function(data) {
			if (!data.data) {
				return;
			}
			data = data.data;

			var xAxisData = data.xAxisData;
			for (var i = 0; i < xAxisData.length; i++) {
				var str = xAxisData[i];
				var arr = str.split(' ');
				if (arr[1] != '2点') {
					xAxisData[i] = arr[1];
				} else {
					xAxisData[i] = {
						value: 	xAxisData[i],
						textStyle: {
							 margin: '0 auto',
							 width: '20px',
							 'line-height': '24px'
						}
					};
				}
			}
			var myChart = echarts.init(document.getElementById('cylinderShape'));
			option = {
				title : {
					text: data.title,
	    	        subtext: data.subTitle,
	    	        x: 'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : data.legend
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : true
						},
						dataView : {
							show : true,
							readOnly : false
						},
						magicType : {
							show : true,
							type : [ 'line', 'bar' ]
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					boundaryGap : false,
					data : xAxisData
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel : {
						formatter : '{value} °C'
					},
					min: 30,
					max: 50
				} ],
				series : data.series
			};
			myChart.setOption(option);

		});
	},
	close : function() {
		if (window.parent.PatientInHospitalQuery) {
			parent.layer.close(window.parent.PatientInHospitalQuery.layerIndex);
		} else {
			parent.layer.close(window.parent.PatientInHospital.layerIndex);
		}
	}
};

$(function() {
	InHospitalTemperatureRecordShowChart.drawChart();
});
