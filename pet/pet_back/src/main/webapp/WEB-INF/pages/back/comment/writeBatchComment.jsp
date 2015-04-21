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
<style type="text/css">
td{ border-left:1px solid #AAA; border-top:1px solid #AAA; padding:5px;}
table{ border-right:1px solid #AAA; border-bottom:1px solid #AAA;}
.tipFont{ color:#FF0000;}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/base/jquery.datepick-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.ui.all.css" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/base/jquery.form.js"></script>
<title>后台批量写点评</title> 
 
<script type="text/javascript"> 

$(function(){
	$('#startDate').datepicker({dateFormat: 'yy-mm-dd'});
	$('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
	//ajax提交表单
	 $("#batchRecommentForm").ajaxForm({
		dataType:"json",
		success : function(data) {
			var $operateMsg = $("#operateMsg").html("");
			if(data["result"]=="1"){
				alert(data["message"][0]);
			}else{
				var errorMsg = data["message"];
				$operateMsg.append("导入点评数据失败!<br/>");
				if(errorMsg){
					for(var i=0;i<errorMsg.length;i++){
						$operateMsg.append(data["message"][i]+"</br>");
					}
				}
			}
			$("#operDiv").css({"display":"none"});
			$("#btnDiv").css({"display":"block"});
		}
	});
});
function downLoadTemplate(){
	var $productId = $("#productId");
	var productId = $.trim($productId.val());
	if(productId==""){
		alert("产品ID不能为空");
		$productId.get(0).focus();
		return;
	}
	window.location.href="${basePath}commentManager/downLoadTemplate.do?productId="+productId;
}

function saveComment() {
	var $startDate = $("#startDate");
	var $endDate = $("#endDate");
	var $file = $("#recommentFile");
	var dateReg = new RegExp("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
	if(!(validataNotEmpty($startDate,"点评时间段开始日期不能为空!") && validataNotEmpty($endDate,"点评时间段结束日期不能为空!"))){
		return;
	}
	if ($.trim($file.val()) == ""
			|| ".xls" != $file.val().substr($file.val().lastIndexOf("."))) {
		alert("数据文件必须为xls格式的文件");
		$file.get(0).focus();
		return;
	}
    if(!dateReg.test($startDate.val()))
    {
        alert("点评时间段开始日期格式必须为YYYY-MM-DD！");
        $startDate.get(0).focus();
    }
    if(!dateReg.test($endDate.val()))
    {
        alert("点评时间段结束日期格式必须为YYYY-MM-DD！");
        $endDate.get(0).focus();
    }
    if(!dateCompare($startDate.val(),$endDate.val())){
    	alert("点评结束日期必须大于开始日期");
    	return;
    }

	$("#batchRecommentForm").submit();
	$("#operDiv").css({"display":"block"});
	$("#btnDiv").css({"display":"none"});
}

/**
 * 判断enddate是否大于startdate
 */
function dateCompare(startdate, enddate) {
	var arr = startdate.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2]);
	var starttimes = starttime.getTime();

	var arrs = enddate.split("-");
	var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	var lktimes = lktime.getTime();

	if (starttimes >= lktimes) {
		return false;
	} else{
		return true;
	}
}

function validataNotEmpty($target, errorMsg) {
	if ($target && $.trim($target.val()) == "") {
		if (errorMsg) {
			alert(errorMsg);
		}
		$target.get(0).focus();
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<div class="main main02">
		<div class="row1">
			<div style="display: block;line-height:30px; font-size:22px; font-weight: bold;">后台批量写点评</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
				<td>
				使用说明：先输入产品id，然后下载模板，在模板中填写评论的数据，然后上传数据文件，完成批量添加评论的操作！<br/>
				</td>
				</tr>
				<tr>
					<td>产品ID：<input id="productId"  name="productId"/>
					<input type="button" class="button" value="下载模板" onClick="downLoadTemplate()" /></td>
				</tr>
			</table>
			<br/>
			<div style="color:#FF0000; line-height: 22px;" id="operateMsg"></div>
			<form id="batchRecommentForm" action="${basePath}commentManager/saveBatchRecomment.do" enctype="multipart/form-data" method="post">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
				    <td>点评时间段<font class="tipFont">(评论时间将在该时间段内随机取值)</font><br/>
				    <input type="text" name="startDate" id="startDate"/>-<input type="text" name="endDate" id="endDate"/></td>
				</tr>
				<tr>
				    <td>数据文件:<input type="file" name="file" id="recommentFile"/></td>
				</tr>
				<tr>
				    <td>
				    <div id="operDiv" style="color:#FF0000;display: none">
				    点评正在写入中，请求稍等............
				    </div>
				    <div id="btnDiv">
				    <input type="button" class="button" value="保存" onClick="saveComment()"/>
				    </div>
				    </td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>


