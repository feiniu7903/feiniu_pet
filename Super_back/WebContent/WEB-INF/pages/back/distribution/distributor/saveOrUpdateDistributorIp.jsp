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
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script>
	function checkAndSubmitIp(){
		if($.trim($("#form1 #ip").val()) == ""){
			$("#form1 #ip").focus();
			alert("Ip地址不能为空");
			return false;
		}

		/*if(!(/^(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])$/.test($("#form1 #ip").val()))){
			$("#form1 #ip").focus();
			alert("不是正确的Ip地址");
			return false;

		} */
		
		$.post($("#form1").attr("action"),
				$("#form1").serialize(),
				function(dt){
					var data=dt;
					if(data.success){
						alert("操作成功");
						parent.location.reload(true);
					}else{
						alert(data.msg);
					}
				}
		);
	}
	
	
</script>
</head>
<body>
<div>
	<form id="form1" method="post" action='${basePath}/distribution/saveOrUpdateDistributorIP.do'>
	<input type="hidden" name="distributorIp.distributorInfoId" value="${distributorInfoId }"/>
	<input type="hidden" name="distributorIp.distributorIpId" value="${distributorIp.distributorIpId}"/>
	<table >
		<tbody>
			<tr >
				<td width="80">IP地址：</td>
				<td><input type="text" id="ip" name="distributorIp.ip" value="${distributorIp.ip }" ></td>
			</tr>
		</tbody>
	</table>
	<p class="tc mt20">
		<input type="button" id="checkAndSubmitIp_btn" onclick="checkAndSubmitIp();"  value="保存" class="btn btn-small w6"/>
	</p>
	</form>
</div>
</body>
</html>