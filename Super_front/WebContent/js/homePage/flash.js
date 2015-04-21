function flash_title() {
	$.ajax({
 		type : 'GET',
 		url : "http://www.lvmama.com/homePage/getLastOrderData.do?_=" + (new Date()).getTime(),
 		async : false,
 		cache : false,
 		dataType : 'json',
 		success : function(data) {
 			if(data.success=="true")
 				$("#news").html(data.message);
 		},
 		error:function(){
 		}
 	});
 }

$(function(){
	flash_title();
	setInterval("flash_title()", 10 * 1000);	
});