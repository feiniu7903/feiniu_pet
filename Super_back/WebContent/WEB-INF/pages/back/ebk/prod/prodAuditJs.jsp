<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$('#sumitDateBegin').datepicker({dateFormat : 'yy-mm-dd'});
		$('#sumitDateEnd').datepicker({dateFormat : 'yy-mm-dd'});
		//查找供应商
		$("#supplierIdInput").jsonSuggest({
			url : "${basePath}/supplier/searchSupplier.do",
			maxResults : 10,
			minCharacters : 1,
			onSelect : function(item) {
				$("#comSupplierId").val(item.id);
			}
		});
		
		//产品经理
		$("#managerIdInput").jsonSuggest({
			url : "${basePath}/ebooking/prod/searchPermUser.do",
			maxResults : 10,
			minCharacters : 1,
			onSelect : function(item) {
				$("#comManagerId").val(item.id);
			}
		});
		
		//修改分页请求方式
		var pages=$('.PrevPage,.PageLink,.NextPage');
		$.each(pages, function(i, n){
			 $(n).click(function() {
				 $("#page").val($(this).attr("data-page"));
				 $("#prodApprovalAuditForm").submit();
			});
		});
		
		
		
		
	});
</script>