var CallNumber = {
		ctxPath: '',
    	formatterData (data) {
    		if (data.list && data.list.length != 0) {
				data.list.forEach(function(item) {
    				if (item.outpatient == 1) {
    					item.outpatient = "普通门诊";
    				} else {
    					item.outpatient = "发热门诊";
    				}
    			});
    		}
    		
    		if (data.currPatient) {
    			if (data.currPatient.outpatient == 1) {
    				data.currPatient.outpatient = "普通门诊";
				} else {
					data.currPatient.outpatient = "发热门诊";
				}
    		}
    	},
		initVue: function(data) {
			CallNumber.formatterData(data);
			var vm = new Vue({
		        el: '#container',
		        data () {
		        	  return {
		        		  list: data.list,
		        		  currPatient: data.currPatient
		        	  }	
		        },
		        computed: {
		        	isCurrPatient () {
		        		return this.currPatient;
		        	},
		        	waitingPatient () {
		        		if (this.list && this.list.length > 0) {
		        			return this.list[0];
		        		} 
		        		return null;
		        	}
		        },
		        methods: {
		    		refreshData: function() {
		    			var me = this;
		    			Personal.ajaxAsyncJson(Personal.contextPath + '/personal/callNumberData', {}, function(data) {
		    				CallNumber.formatterData(data);
		    		        me.list = data.list;
		    		        if (data.currPatient) {
		    		        	if (data.currPatient.outpatient && (!me.currPatient || me.currPatient.id != data.currPatient.id)) {
		    		        		if (data.currPatient.outpatient === '普通门诊') {
			    		        		 document.getElementById('common').play();
			    		        	} else {
			    		        		 document.getElementById('fever').play();
			    		        	}
				    		        me.currPatient = data.currPatient;
		    		        	}
		    		        } else {
		    		        	me.currPatient = null;
		    		        }
		    		    });
		    		},
		        	webSocket: function() {
		        		var me = this;
		                var socket;  
		                if(typeof(WebSocket) == "undefined") {  
		                    alert("您的浏览器不支持WebSocket");  
		                }else{  
		                    var preUrl = CallNumber.ctxPath.replace('http', 'ws');
		                    console.log(preUrl + '/treatment')
		                    socket = new WebSocket(preUrl + '/treatment');  
		                    //打开事件  
		                    socket.onopen = function() {  
		                        console.log("Socket 已打开");  
		                        socket.send("这是来自客户端的消息" + location.href + new Date());  
		                    };  
		                    //获得消息事件  
		                    socket.onmessage = function(msg) {  
		                        //发现消息进入    调后台获取  
		                        me.refreshData();
		                    };  
		                    //关闭事件  
		                    socket.onclose = function() {  
		                        console.log("Socket已关闭");  
		                        setTimeout(me.webSocket, 60000);
		                    };  
		                    //发生了错误事件  
		                    socket.onerror = function() {  
		                        console.log("Socket发生了错误");  
		                    }  
		                     $(window).unload(function(){  
		                          socket.close();  
		                        });  
		                }
		            }
		        },
		        mounted () {
		        	this.webSocket();
		        }
		    });
		}
	}; 

	