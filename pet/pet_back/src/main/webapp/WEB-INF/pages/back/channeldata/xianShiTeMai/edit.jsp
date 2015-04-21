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
		<title>编辑</title>
	</head>
	<body>
		<form action="/channeldata/xianShiTeMai/save.do" method="post">
			<input type="hidden" name="recommendInfo.recommendInfoId" id="recommendInfo_recommendInfoId" value="${recommendInfo.recommendInfoId }"/>
			<table class="p_table form-inline">
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>类型:</td>
					<td>
						<select id="recommendInfo_remark" name="recommendInfo.remark">					
							<option value = "1" <s:if test='recommendInfo.remark == "1"'>selected</s:if> >门票</option>
							<option value = "2" <s:if test='recommendInfo.remark == "2"'>selected</s:if> >周边</option>
							<option value = "3" <s:if test='recommendInfo.remark == "3"'>selected</s:if> >国内</option>
							<option value = "4" <s:if test='recommendInfo.remark == "4"'>selected</s:if> >出境</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>产品名称:</td>
					<td>
						<input type="text" name="recommendInfo.title" id="recommendInfo_title" value="${recommendInfo.title }"/>	
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>产品链接:</td>
					<td>
						<input type="text" name="recommendInfo.url" id="recommendInfo_url" value="${recommendInfo.url }"/>
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>规划在线时间:</td>
					<td>
						<input type="text" name="recommendInfo.bakWord2" id="recommendInfo_bakWord2" value="${recommendInfo.bakWord2 }"/>天
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>折扣率:</td>
					<td>
						<input type="text" name="recommendInfo.bakWord3" id="recommendInfo_bakWord3" value="${recommendInfo.bakWord3 }"/>
					</td>
				</tr>
				<tr>	
					<td class="p_label" width="20%"><span class="red">*</span>排序值:</td>
					<td>
						<input type="text" name="recommendInfo.seq" id="recommendInfo_seq" value="${recommendInfo.seq }"/>
					</td>
				</tr>											 			 			
		   </table>
		   <p class="mt20">
		   		<button class="btn btn-small w5" type="button" onclick="save()">保存</button>　
		   </p>
	   </form>       
	</body>
	<script type="text/javascript">
		function save() {
			if ($("#recommendInfo_title").val() == "") {
				alert("请输入产品名称");
				$("#recommendInfo_title").focus();
				return;
			}
			if ($("#recommendInfo_url").val() == "") {
				alert("请输入产品链接");
				$("#recommendInfo_url").focus();
				return;
			}
			if ($("#recommendInfo_bakWord2").val() == "" || isNaN($("#recommendInfo_bakWord2").val())) {
				alert("请输入合法的在线时间");
				$("#recommendInfo_bakWord2").focus();
				return;
			}
			if ($("#recommendInfo_bakWord3").val() == "") {
				alert("请输入折扣率");
				$("#recommendInfo_bakWord3").focus();
				return;
			}
			if ($("#recommendInfo_seq").val() == "" || isNaN($("#recommendInfo_seq").val())) {
				alert("请输入合法的排序");
				$("#recommendInfo_seq").focus();
				return;
			}
			$.ajax({
    	 		url: "<%=basePath%>/channeldata/xianShiTeMai/save.do",
				type:"post",
    	 		data: {
						"recommendInfo.recommendInfoId":$("#recommendInfo_recommendInfoId").val(),
						"recommendInfo.remark":$("#recommendInfo_remark").val(),
						"recommendInfo.title":$("#recommendInfo_title").val(),
						"recommendInfo.url":$("#recommendInfo_url").val(),
						"recommendInfo.bakWord2":$("#recommendInfo_bakWord2").val(),
						"recommendInfo.bakWord3":$("#recommendInfo_bakWord3").val(),
						"recommendInfo.seq":$("#recommendInfo_seq").val()
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						alert(result.message);
						document.location.reload();
					} else {
						alert(result.message);
					}
    	 		}
    		});			
		}
	</script>
</html>