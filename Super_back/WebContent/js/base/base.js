jQuery(function($){
	var datatable =$(".datatable");
	datatable.find("th:last-child").css({"border-right":"none"});
	datatable.find("td:last-child").addClass("lasttd");
	datatable.find("tr:last>td").css({"border-bottom":"none"});
	datatable.find("tr:even").addClass("other-bgcolor");
	datatable.find("tr").mouseover(function(){$(this).addClass("current-bgcolor")})
						.mouseout(function(){$(this).removeClass("current-bgcolor")})
						.toggle(function () {
							$(this).addClass("select-bgcolor");
						  },
						  function () {
							$(this).removeClass("select-bgcolor");
						  });
})

function showtest(obj){
alert(1);
}