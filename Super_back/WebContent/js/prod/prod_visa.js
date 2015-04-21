$(function(){	
	$("#country").jsonSuggest({
		url : "/pet_back/visa/queryVisaCountry.do",
		maxResults : 10,
		width : 300,
		emptyKeyup : true,
		minCharacters : 1,
		onSelect : function(item) {
			$("#product_country").val(item.id);
		}
	}).change(function() {
		if ($.trim($(this).val()) == "") {
			$("#country").val("");
		}
		$("#visa_document_div").html("");
	});
	$("#city").change(function(){
	   $("#visa_document_div").html("");
	});
	function showDocument(){
		var form=$(this).parents("form");
		var country=form.find("input[name=product.country]").val();
		var visaType=form.find("input[name=product.visaType]:checked").val();
		var city=form.find("select[name=product.city] :selected").val();
		if($.trim(city)=='' || $.trim(country)==''||$.trim(visaType)==''){
		  alert("国家或签证类型或送签城市不能为空！");	
		  return false;
		}
		
		$.post("/pet_back/visa/document/query.do",{"country":country,"visaType":visaType,"city":city},function(dt){
			$("#visa_document_div").html(dt);
		});
	}
	$("a.showDocument").click(showDocument);
});
  function clearsDocumentdiv(){
	$("#visa_document_div").html("");
  }