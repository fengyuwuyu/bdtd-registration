$(function() {
	$('#userCard').focus();
	
	$('#userCard').blur(function() {
		$('#userCard').focus();
	});
	
	
	var id = setInterval(()=> {
	    var userCard = $('#userCard').val();
	    if (userCard && userCard.trim() != '') {
	    	window.open(Feng.ctxPath + '/personal/userInfo/' + userCard, "_self", true);
	    	clearInterval(id);
	    }
	  }, 1500);
	
	
});
