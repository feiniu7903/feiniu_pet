$(function(){
	$("#searchMeta").jsonSuggest({
		url:"/super_back/meta/searchMetaList.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("input[name=item.metaProductId]").val(item.id);
		}
	});
})