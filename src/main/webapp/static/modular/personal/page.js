//分页组件
var BdtdPage = {
	pageNumber: 1,
	pageSize: 8,
	setPageNumber: function(pageNumber) {
		this.pageNumber = pageNumber;
	},
	total: 0,
	setTotal: function(total) {
		this.total = total;
	},
	hasNextPage: function() {
		return Math.ceil(this.total / this.pageSize) != this.pageNumber;
	},
	hasPrePage: function() {
		return this.pageNumber != 1;
	},
	nextPage: function() {
		if (this.hasNextPage()) {
			
		}
	},
	prePage: function() {
		if (this.hasPrePage()) {
			
		}
	}
};