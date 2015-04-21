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
<title>编辑点评</title>
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

function saveComment()
{	
    if ($('#editContent').val()==null || $('#editContent').val()=='') {
    	alert("点评内容不能为空");
    	return;
   }
	
 	 var url = "<%=basePath%>/commentManager/saveComment.do";
	 $.ajax({
		type: 'POST',
	 	url: url,
	 	data: {"commentId":$('#currentCommentId').val(),"editContent":encodeURI($('#editContent').val(),"UTF-8")},
	 	dataType:"json",
	 	success: function(result) {
    		if (result.success) {
    			alert('更新成功');
    			location.href = location.href;
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
						
						<td>用户名：</td>
						<td>
							${currentComment.userName}
						</td>
					</tr>
					<tr>
						<s:if test='currentComment.subjectType != null && currentComment.subjectName != null'>
				        <td>主题名称：</td>
						<td>
							${currentComment.subjectName}
						</td>
						<td>主题类型：</td>
						<td>
                           ${currentComment.subjectType}
						</td>
					    </s:if>
					    <s:else>
					    <td>主题名称：</td>
					    <td></td>
					    <td>主题类型：</td>
					    <td></td>
					    </s:else>
					</tr>
					<tr>
						<td>发表时间：</td>
						<td>${currentComment.formatterCreatedTime}</td>

                       	<td>点评类型：</td>
						<td>
							${currentComment.chCmtType}
						</td>
					</tr>
					<tr>
						<td>审核状态：</td>
						<td>${currentComment.chAudit}</td>

                       	<td>是否隐藏：</td>
						<td>
							${currentComment.chIsHide}
						</td>
					</tr>
					<tr>
						<td>是否精华：</td>
						<td>${currentComment.chIsBest}</td>

                       	<td>回复数：</td>
						<td>
							${currentComment.replyCount}
						</td>
					</tr>
					<tr>
						<td>有用数：</td>
						<td>${currentComment.usefulCount}</td>

                       	<td>返现金额：</td>
						<td>
							<s:if test='currentComment.cashRefundYuan != null'> 
								${currentComment.cashRefundYuan} 
							</s:if>
						</td>
					</tr>
					<tr>
						<td>上传图片数：</td>
						<td>${currentComment.pictureCount}</td>

                       	<td>评分：</td>
						<td>
							${latitudeDescriptionInfo}
						</td>
					</tr>
					<tr>
					   <input type="hidden" id="currentCommentContent"  value="${currentComment.content}" />
						<td><em>点评内容：</em></td>
						<td colspan="3">
							<s:textarea label="Description" name="editContent" id="editContent" cols="80" rows="10" value="">
                           </s:textarea>
						</td>
					</tr>
					<tr>
					    <td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="button" class="button" value="保存" onClick="saveComment()"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
		</div>
	</div>
</body>
</html>


