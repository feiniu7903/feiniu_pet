$(document).ready(function(){
	$("[searchCondition='search']").click(function(){
		var searchVal = $(this).attr("searchvo");
		$("#searchVa").val(searchVal);
		$("#searchVaForm").submit();
	});
});