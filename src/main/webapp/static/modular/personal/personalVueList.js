var PersonalVueList = {
	init: function(url) {
		var vm = new Vue({
		    el: "#container",
		    data: {
		      pageNumber: 1,
		      pageSize: 8,
		      total: 0,
		      totalPage: 1,
		      list: [],
		      userNo: document.getElementById('userNo').value,
		      query: false,
		      totalList: []
		    },
		    methods: {
		      initPage: function(url) {
		    	if (!this.userNo) {
		    		return;
		    	}
		    	
		    	if (!this.query && url) {
		    		var me = this;
		    		Personal.ajaxAsyncJson(Personal.contextPath + url, {userNo: this.userNo}, function(data) {
		        		if (data && data.list) {
		        			me.totalList = data.list;
		        		}
		        	});	
		    		this.query = true;
		    	}

				var len = this.totalList.length;
		    	if (!this.totalList || len == 0) {
		    		return;
		    	}
		    	
		    	var begin = (this.pageNumber - 1) * this.pageSize;
				var end = this.pageNumber * this.pageSize;
				begin = begin >= len ? len - 1 : begin;
				end = end > len ? len : end;
				this.list = this.totalList.slice(begin, end);
			    this.total = this.totalList.length;  
			    this.totalPage = Math.ceil(this.total / this.pageSize);
		      },
		      hasNextPage: function() {
		        return this.totalPage != this.pageNumber;
		      },
		      hasPrePage: function() {
		        return this.pageNumber != 1;
		      },
		      nextPage: function() {
		        if (this.hasNextPage()) {
		          this.pageNumber++;
		          this.initPage();
		        }
		        this.changeBtnImg();
		      },
		      prePage: function() {
		        if (this.hasPrePage()) {
		          this.pageNumber--;
		          this.initPage();
		        }
		        this.changeBtnImg();
		      }, 
		      goUserInfoChoose: function() {
		    	  Personal.goUserInfoChoose();
		      },
		      changeBtnImg: function() {
		    	  if (this.totalPage == 1) {
	    			  $('#next-page').attr('src', Personal.contextPath + '/static/img/personal/btn/btn_nextPage_gray.png');
		    		  $('#pre-page').attr('src', Personal.contextPath + '/static/img/personal/btn/btn_prePage_gray.png');
	    		  } else if (this.pageNumber == 1) {
		    		  $('#pre-page').attr('src', Personal.contextPath + '/static/img/personal/btn/btn_prePage_gray.png');
		    		  $('#next-page').attr('src', Personal.contextPath + '/static/img/personal/btn/btn_nextPage.png');
		    	  } else if (this.pageNumber == this.totalPage) {
		    		  $('#pre-page').attr('src', Personal.contextPath + '/static/img/personal/btn/btn_prePage.png');
		    		  $('#next-page').attr('src', Personal.contextPath + '/static/img/personal/btn/btn_nextPage_gray.png');
		    	  }
		      },
		      showDetail: function(id, type) {
		    	  Personal.openDetail(id, type);
		      }

		    }
		  });

		  vm.initPage(url);
		  vm.changeBtnImg();
	}
}; 

