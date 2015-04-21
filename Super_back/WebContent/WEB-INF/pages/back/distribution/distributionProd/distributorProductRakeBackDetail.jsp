<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>分销后台-分销商</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>

<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
$(function(){
	$("#btnSave").click(function(){
		var profit = $("#profit").val();
		var rakeBack = $("#rakeBackRate").val();
		if ("" == rakeBack) {
			alert("请输入返佣点!");
			return;
		}
		var reg = new RegExp("^[0-9]*$");
		if(!reg.test(rakeBack)){
			alert("只能输入数字!");
			return;
		}
	/* 	if($("#rateVolid").attr("checked")==true){
			if (parseInt(profit) < parseInt(rakeBack)) {
				alert("返佣点不能高于利润率");
				return;
			}
		} */
		jQuery.ajax({
            url: "${basePath}/distribution/distributionProd/saveRakeBack.do",
            dataType:'json',
            data: $('#rakeBackForm').serialize(),
            type: "POST",
            success: function(myJSON){
                if (myJSON.flag == "success") {
                	alert("保存成功!");
                	//close
                	parent.closeDetailDiv();
                } else {
                	alert(myJSON.msg);
                }
            }
        });
        return false;
	});
});
	
</script>
</head>
<body>
<div>
<form id='rakeBackForm' method='post' action='${basePath}/distribution/distributionProd/saveRakeBack.do' onsubmit="return false">
<input type="hidden" name="productBranchId" value="${productBranchId }"/>
<input type="hidden" name="distributorInfoId" value="${distributorInfoId }"/>
<input type="hidden" id="profit" name="profit" value="${profit }"/>
<table class="p_table">
	<%-- <tr>
		<td>利润率</td>
		<td>${profit}%</td>
	</tr> --%>
	<tr>
		<td>分销商</td>
		<td>${distributorName}</td>
	</tr>
	<tr>
		<td>现在返佣点</td>
		<td>${rakeBackRate}%</td>
	</tr>
	<tr>
		<td>分销返佣点</td>
		<td><input type="text" id="rakeBackRate" name="rakeBackRate" value="${rakeBackRate}" maxlength="3"/>%</td>
	</tr>
	<tr>
		<td>利润大于返佣</td>
		<td><input type="checkBox" id="rateVolid" value="true" name="rateVolid" <s:if test="rateVolid=='true'">checked</s:if>/></td>
	</tr>
</table>
<p class="tc mt20">
	<input type="button" value="保存" id="btnSave" class="btn btn-small w6" />
</p>
</form>
</div>
</body>
</html>

