<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>站内消息查询</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<link rel="stylesheet" type="text/css" href="${basePath}themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath}themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}themes/mis.css">
<link rel="stylesheet" type="text/css" href="${basePath}css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
</head>
<body>
<div><form action="${basePath}/onlineLetter/template/index.do" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="search_table">
			<tr>
				<td>创建时间：</td>
				<td>
					<input type="hidden" name="initial" value="false"/>
					<input id="startDate" type="text" class="newtext1"
							name="beginCreatedTime" value="<s:date name="beginCreatedTime" format="yyyy-MM-dd"/>"/> ~<input id="endDate" type="text"
							class="newtext1" name="endCreatedTime" value="<s:date name="endCreatedTime" format="yyyy-MM-dd"/>"/>
				</td>
				<td>内容关键字：	</td>
				<td><input type="text" class="newtext1" name="keywords" 	value="<s:property value="keywords"/>" /></td>
			 </tr>
			 <tr>
				<td colspan="4"><input class="button" type="submit" name="select" value="查询"/></td>
			 </tr>
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th width="30" >编号</th>
			<th>标题</th>
			<th>模板内容</th>
			<th width="200">有效期</th>
			<th width="70">接收人数</th>
			<th width="70">打开人数</th>
			<th>操作</th>
		</tr>
		<s:iterator value="pagination.items" var="template">			
		<tr>
			<td><input type="hidden" name="beginTime" value="<s:date name="beginTime" format="yyyy-MM-dd"/>"/><input type="hidden" name="endTime" value="<s:date name="endTime"  format="yyyy-MM-dd"/>"/><input type="checkbox" name="template.id" value="<s:property value="id"/>"/><s:property value="id"/></td>
			<td><input type="text" value="<s:property value="title"/>" name="template.title" disabled="disabled" template_key="title" style="width: 100%; height: 100%;"/></td>
			<td><textarea name="template.content" disabled="disabled" template_key="content" style="width: 100%; height: 100%;"><s:property value="content"/></textarea></td>
			<td><s:date name="beginTime" format="yyyy年MM月dd日"/>~<s:date name="endTime" format="yyyy年MM月dd日"/></td>
			<td><s:property value="receiveCount"/></td>
			<td><s:property value="readerCount"/></td>
			<td><input type="button" value="修改" class="updatetemplate"/></td>
		</tr>	
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="8" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
	</table>
</div>
</body>
<script type="text/javascript" charset="utf-8">
$(function(){
	$('#startDate').datepicker({dateFormat: 'yy-mm-dd'});
    $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
    $(":checkbox").click(function(){$(this).parent().parent().find("[template_key]").attr("disabled",!$(this).attr("checked"));});
    $(".updatetemplate").click(function(){
    	var parent = $(this).parent().parent();
    	if(!parent.find(":checkbox[name='template.id']").attr("checked")){
    		return false;
    	}
    	var id = parent.find(":checkbox[name='template.id']").val();
    	var title = parent.find(":text[name='template.title']").val();
    	var vcontent = parent.find("textarea[name='template.content']").val();
    	if(title.length == 0){alert("标题不能为空");return false;}
    	if(title.length >200){alert("标题不能大于200个字符");return false;}
    	if(vcontent.length == 0){alert("内容不能为空");return false;}
    	if(vcontent.length >2000){alert("内容不能大于2000个字符");return false;}
    	$.ajax( {
			url : "${basePath}/onlineLetter/template/update.do",
			data:{"template.title":title,"template.content":vcontent,"template.beginTime":parent.find(":hidden[name='beginTime']").val(),"template.endTime":parent.find(":hidden[name='endTime']").val(),"template.id":id},
			type: "POST",
			dataType:"json",
			success : function(data) {
					if(data.success){
						alert("修改成功");
					}else{
						alert(data.errorText);
					}
				}
			});
    });
});
</script>
</html>