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
	<title></title>
	</head>
	<body>
	<div class="main main02">
		<div class="row1">
			<table border="1" cellspacing="0" cellpadding="0" class="search_table" width="100%">
				<tr>
					<td><input type="hidden" name="placeId" id="placeId"/> 景区名称：</td>
					<td><input type="text" name="place" id="place"/><br/></td>
				</tr>
				<tr id="currentCmtTitle">
					<td>景区现有点评主题:</td>
					<td></td>	
				</tr>
				<tr>
					<td>更新为：</td>
					<td><s:select list="subjectList" id="topic"  name="topic" headerKey="" headerValue="--请选择--" theme="simple" listKey="subjectName" listValue="subjectName"></s:select></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" class="button" value="保存"  id="saveCmtTitle"/></td>
				</tr>
			</table>
		</div>
	</div>
	</body>
	<script type="text/javascript">
	$(function(){
		$("#place").jsonSuggest({
			url : basePath + "/place/queryPlace.do",
			maxResults : 10,
			width : 300,
			emptyKeyup : false,
			minCharacters : 1,
			onSelect : function(item) {
				$("#place").val(item.text);
				$("#placeId").val(item.id);
				$('#currentCmtTitle').html("<td>景区现有点评主题:</td><td>" + item.cmtTitle +"</td>");
			}
		}).change(function() {
			if ($.trim($(this).val()) == "") {
				$("#place").val("");
				$("#placeId").val("");
				$('#currentCmtTitle').html("<td>景区现有点评主题:</td><td></td>");
			}
		});	
		
		$("#saveCmtTitle").click(function(){
			if ($("#placeId").val() == "") {
				alert("请先选择合适的景区");
				return;
			}
			if ($("#topic").val() == "") {
				alert("请选择需要更新的点评主题");
				return;
			}
			$.ajax({
    	 		url: "<%=basePath%>/commentManager/updateScienceCommentTopic.do",
				type:"post",
    	 		data: {
						"placeId":$("#placeId").val(),
						"subjectName":$("#topic").val()
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						alert("操作成功");
						$("#editCommentDiv").dialog("close");
					} else {
						alert("数据丢失，操作失败");
					}
    	 		}
    		});	
		});
	});
	

	</script>
</html>


