PatientInHospitalComprehensiveQuery = {
	drawChart: function() {
		var queryData = $('#searchForm').serializeObject();
		Feng.ajaxAsyncJson(Feng.ctxPath + '/comprehensive/trainWoundChart', queryData, function(data) {
			if (!data.data) {
				return;
			}
			data = data.data;
			
			var myChart = echarts.init(document.getElementById('cylinderShape'));
		    var option = {
		    	    title : {
		    	        text: data.title,
		    	        subtext: data.subTitle,
		    	        x: 'center'
		    	    },
		    	    tooltip : {
		    	        trigger: 'axis'
		    	    },
		    	    legend: {
		    	        data:data.legend
		    	    },
		    	    toolbox: {
		    	        show : data.toolBoxShow || true,
		    	        feature : {
		    	            mark : {show: data.featureMarkShow || true},
		    	            dataView : {show: data.featureDataViewShow || true, readOnly: data.featureDataViewReadOnly || false},
		    	            magicType : {show: data.featureMagicTypeShow || true, type: data.featureMagicTypeType || ['line', 'bar']},
		    	            restore : {show: data.featureRestoreShow || true},
		    	            saveAsImage : {show: data.featureSaveAsImageShow || true}
		    	        }
		    	    },
		    	    calculable : data.calculable || true,
		    	    xAxis : [
		    	        { 
		    	        	name : data.xAxisName || '单位',
		    	            type : data.xAxisType || 'category',
		    	            data : data.xAxisData,
		    	            axisLabel: {
		    	            	interval:0,
		    	            	rotate:40
		    	            }
		    	        }
		    	    ],
		    	    yAxis : [
		    	        {
		    	        	name : data.yAxisName || '人数',
		    	            type : data.yAxisType || 'value'
		    	        }
		    	    ],
		    	    series : data.series
		    	};
		    	                    
		    myChart.setOption(option);
		});
	},
	close: function() {
		parent.layer.close(window.parent.PatientInfo.layerIndex);
	},
	resetSearchForm: function () {
	    Feng.clearSearchForm(function() {
	    	PatientInHospitalComprehensiveQuery.drawChart();
	    });
	},
	search: function () {
		PatientInHospitalComprehensiveQuery.drawChart();
	}
};

$(function() {
	PatientInHospitalComprehensiveQuery.drawChart();
});