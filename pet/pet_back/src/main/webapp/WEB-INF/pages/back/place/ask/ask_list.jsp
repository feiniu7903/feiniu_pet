<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>常见答题</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script src="${basePath}/js/place/place.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
</head>
<body>
	<div id="popDiv2" style="display: none"></div>
	<table class="p_table form-inline" width="80%">
	<mis:checkPerm permCode="4000"><a href="javascript:toAddEdit(${placeId});">添加</a></mis:checkPerm><br />

	<mis:checkPerm permCode="4000"><a href="${basePath}place/scenicList.do?place.placeId=${placeId}">返回</a></mis:checkPerm>
		<thead >
			<tr>
				<td class="p_label">问</td>
				<td class="p_label">答</td>
				<td class="p_label">排序</td>
				<td class="p_label">操作</td>
			</tr>
		</thead>
		<s:if test="asklist!=null">
			<tbody>
				<s:iterator value="asklist" var="ask" >
				<tr>
					<td>${question }</td>
					<td>${answer }</td>
					<td>${seq }</td>
					<td><a href="javascript:toAskEdit(${placeQaId },${placeId});">修改</a>
						<a href="javascript:doDelAsk(${placeQaId },${placeId});">删除</a></td>
				</tr>
				</s:iterator>
				<tr>
    				<td colspan="3" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
					<td colspan="5"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
    			</tr>
			</tbody>
		</s:if>
	</table>
</body>
<script type="text/javascript">
function toAskEdit(placeQaId,placeId) {
	$("#popDiv2").load("${basePath }QuestionAnswer/goToAskEdit.do?askId="+placeQaId, function() {
		$(this).dialog({
        	modal:true,
            title:"常见问题",
            width:650,
            height:450
        });
     });							
} 

function toAddEdit(placeId) {
	$("#popDiv2").load("${basePath }QuestionAnswer/goToAskEdit.do?placeId="+placeId, function() {
		$(this).dialog({
        	modal:true,
            title:"常见问题",
            width:650,
            height:450
        });
     });							
}

function doDelAsk(placeQaId,placeId) {
	if(confirm("你确定要删除吗?")){
		location=${basePath}+"QuestionAnswer/delAsk.do?askId="+placeQaId+"&placeId="+placeId;
	}
}


</script>

</html>