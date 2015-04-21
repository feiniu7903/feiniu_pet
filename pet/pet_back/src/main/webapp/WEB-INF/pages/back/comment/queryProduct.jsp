<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>写后台点评</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">

<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>

<script type="text/javascript"> 
function query()
{	
   var reg = /^[1-9]*[1-9][0-9]*$/;
   if ($('#productId').val()==null || $('#productId').val()=='') {
    	alert("查询产品ID不能为空");
    	return;
   }
   if(!reg.test($("#productId").val())){
   		alert("产品ID请填写数字");
   		$("#productId").focus();
   		return false; 
   	}
   
   var productId = $('#productId').val();
	$("#addCommentDiv").load("<%=basePath%>/commentManager/writeBackComment.do?productId=" + productId,function() {
		$(this).dialog({
			modal:true,
			title:"新增后台点评",
			width:950,
			height:580
    	});
	});
}

$(function(){
	$("#productId").click(function(){
		$("#data1").html("");
		$("#data2").html("");
	});
});

    </script>
</head>
<body>
	<div id="addCommentDiv" style="display: none"></div>
	<div class="main main02">
		<div class="row1">
			<h3 class="newTit">后台写点评</h3>
			<form name="queryProductFrom" id="queryProductFrom" action="${basePath}/commentManager/writeBackComment.do" method="post">
			
				<s:if test="showErrorMessage != null"><div id="data1">${showErrorMessage } </div></s:if>
				<s:if test="result == 'fail'"><div id="data2"> 关闭当前窗口重查.</div></s:if>
				
				<s:if test="result != 'fail'">
					<table border="1" cellspacing="0" cellpadding="0" class="search_table"  width="100%">
						<tr>
							<td width="25%" align="right">输入产品ID：</td>
							<td width="25%">
								<input id="productId"  name="productId" value="${productId}" />
							</td>
							<td width="25%"></td><td width="25%"></td>
						</tr> 
						<tr>
							<td width="25%" align="right">
						   		 <input type="button" class="button" value="查询" onClick="query()"/>
						    </td>
						    <td width="25%"> </td>
							<td width="25%"></td><td width="25%"></td>
						</tr>
					</table>
				</s:if>
			</form>
		</div>
	</div>
</body>
</html>


