<%@ page language="java" import="java.util.*,com.lvmama.comm.vo.Constant" pageEncoding="UTF-8"%>
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
<title>点评活动</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath %>js/base/ajaxupload.js"></script>
<script type="text/javascript">
$(function(){
    $('#activitySubject').val($('#currentActivitySubject').val());
    $('#title').val($('#currentActivityTitle').val());
    $('#url').val($('#currentActivityUrl').val());
    $('#picUrl').val($('#currentPicUrl').val());
    $('#content').val($('#currentActivityContent').val());   
});

function saveActivity()
{	
	
    if ($('#activitySubject').val()==null || $('#activitySubject').val()==''||
    		$('#title').val()==null || $('#title').val()==''||
    		$('#url').val()==null || $('#url').val()==''||
    		$('#picUrl').val()==null || $('#picUrl').val()==''||
    		$('#content').val()==null || $('#content').val()=='') {
    	alert("内容不能为空");
    	return;
   }
	
    $("#saveActivityForm").submit();
}

    </script>
</head>
<body>
	<div class="main main02">
		<div class="row1">
		<form id="saveActivityForm" name="saveActivityForm" action="<%=basePath%>/commentManager/saveActivity.do" method ="post" enctype="multipart/form-data">
                <input type="hidden" value="${currentActivity.activityId}" id="activityId"  name="activityId"/>
				<table border="1" cellspacing="0" cellpadding="0" class="search_table"
					width="100%">
					<tr>
						<td>活动主题：[*]</td>
						<td>
							<input type="hidden" id="currentActivitySubject"  value="${currentActivity.activitySubject}" />
							<s:textfield id="activitySubject" name="activitySubject" cssClass="newtext1" disabled="true"/>
						</td>
					</tr>
					<tr>
				        <td>标题：[*]</td>
						<td>
						    <input type="hidden" id="currentActivityTitle"  value="${currentActivity.title}" />
							<s:textfield id="title" name="title" cssClass="newtext1"/>
						</td>
					</tr>
					<tr>
						<td>链接：[*]</td>
						<td>
						<input type="hidden" id="currentActivityUrl"  value="${currentActivity.url}" />
						<s:textfield id="url" name="url" cssClass="newtext1"/>
						</td>
					</tr>
					<tr>
						<td>图片：[*]</td>
						<td>
						<input type="hidden" id="currentPic" name="currentPic" value="${currentActivity.pic}" />
						<s:file id="uploadFile" style="font-size:13px;margin:0;padding:0;" name="file"/>
						<div id="sig_preview"></div>
						</td>
					</tr>
					<tr>
						<td>图片链接：[*]</td>
						<td>
						<input type="hidden" id="currentPicUrl"  value="${currentActivity.picUrl}" />
						<s:textfield id="picUrl" name="picUrl" cssClass="newtext1"/>
						</td>
					</tr>
					<tr>
						<td>内容：[*]</td>
						<td>
						<input type="hidden" id="currentActivityContent"  value="${currentActivity.content}" />
						<s:textarea label="Description" name="content" id="content" cols="80" rows="10">
                        </s:textarea>
                        </td>
					</tr>
					<tr>
					    <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="button" class="button" value="保存" onClick="saveActivity()"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
		</div>
		</form>
	</div>
</body>
</html>


