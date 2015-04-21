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
<title>增加积分</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>

<script type="text/javascript">
$(function(){
    $('#editContent').val($('#currentCommentContent').val());
});

function addPoint()
{	
	
   if ($('#point').val()==null) {
    	alert("积分不能为空");
    	return;
   } else if ($('#point').val() == 0) {
	   alert("积分不能为0");
	   return;
   }
	
 	 var url = "<%=basePath%>/commentManager/addPoint.do";
	 $.ajax({
		type: 'POST',
	 	url: url,
	 	data: {"commentId":$('#currentCommentId').val(), "point":$('#point').val()},
	 	dataType:"json",
	 	success: function(result) {
    		if (result.success) {
    			alert('增加积分成功');
    			location.href = location.href;
    			if(result.successMessage != null)
    			{
    				alert(result.successMessage);
    			}
    		} else {
    			alert(result.errorMessage);
    		}
	 	}
	 });
}
    </script>
</head>
<body>
	<div class="main main02">
		<div class="row1">
                <input type="hidden" value="${currentComment.commentId}" id="currentCommentId" />
				<table border="1" cellspacing="0" cellpadding="0" class="search_table"
					width="100%">
					<tr>
						<td>点评编号：</td>
						<td>
							${currentComment.commentId}
						</td>
						
						<td>审核状态：</td>
						<td>${currentComment.chAudit}</td>
					</tr>
					<tr>
					
						<td>用户名：</td>
						<td>
							${currentComment.userName}
						</td>
						<s:if test='currentComment.subjectType != null && currentComment.subjectName != null'>
				        <td>主题名称：</td>
						<td>
							${currentComment.subjectName}
						</td>
					    </s:if>
					    <s:else>
					    <td>主题名称：</td>
					    <td></td>
					    </s:else>
					</tr>

					<tr>
						<td>点评内容：</td>
						<td colspan="3">
                           ${currentComment.contentDelEnter}
						</td>
					</tr>
					<tr>
						<td>增加积分：</td>
						<td colspan="3">
                           <s:textfield id="point" name="point" cssClass="newtext1"/>
						</td>
					</tr>
					<tr>
					    <td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="button" class="button" value="增加积分" onClick="addPoint()"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
		</div>
	</div>
</body>
</html>


