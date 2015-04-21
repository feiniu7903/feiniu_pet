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
<title>super后台</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<style type="">
.table_label{background: none repeat scroll 0 0 #E2EAF4}
</style>
</head>
<body>
	<div >
		<div class="row1 table_label" style="width:600px">
			<h3 class="newTit">
				修改最晚可预约时间
			</h3>
		</div>
		<form id="ordBatchSubmitForm" action="<%=basePath%>distribution/saveTuanBatch.do" method="post" onsubmit="return check();">
			<div class="row2">
				<table class="p_table form-inline" border="0" cellspacing="0" cellpadding="0" style="width:600px">
					<tr>
						<td class="p_label" width="17%"><em>当前最晚可预约时间：</em></td>
						<td colspan="3">${endTime}</td>
					</tr>
					<tr>
						<td class="p_label"><em>新的最晚可预约时间：</em></td>
						<td colspan="3"><input readonly="readonly" class="newtext1" type="text" name="endTime" id="endTime" class="date" value="${endTime}"/></td>
					</tr>
				</table>
			</div>
			<p>
				<input type="hidden" value="${token}" name="token"/>
				<input type="hidden" value="${distributionBatchId}" name="distributionBatchId"/>
				<input type="submit" value="保  存" id="submitButton"
					class="right-button08" style="width:80px; margin-left:260px;" />
			</p>
		</form>
	</div>
</body>
<script type="text/javascript">

	$(function() {
		$("input[name='endTime']").datepicker({
			dateFormat : 'yy-mm-dd',
			"minDate":new Date()
		});
	});
	

	function check(){
		var endTime = $.trim($("input[name=endTime]").val());
		if(endTime==null  || endTime==""){
			alert("请填写最晚可预约时间");
			return false;
		}
		update_Time();
		return false;
	}
	function update_Time(){
		$('#submitButton').attr('disabled',true); 
		$.ajax({
			url:"<%=basePath%>distribution/saveTuanBatch.do",
			type:"POST",
			data:$("#ordBatchSubmitForm").serialize(),
			success:function(dt){
				if(dt.result=="true"){
					alert(dt.message);
					parent.location.href="<%=basePath%>distribution/distributionTuanBatchList.do";
				}else{
					alert("报错： "+dt.message);
				}
				$('#submitButton').attr('disabled',false); 
			}
		});
	}
</script>
</html>