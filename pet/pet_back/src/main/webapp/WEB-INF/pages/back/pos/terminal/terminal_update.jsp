<%@ page language="java" pageEncoding="UTF-8"%>
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
		<link href="${basePath}/themes/cc.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${basePath}js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${basePath}js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="${basePath}js/base/comPlaceLoad.js"></script>
		<script type="text/javascript" src="${basePath}js/base/form.js"></script>
		<link   href="${basePath}themes/start/jquery-ui.css"  rel="stylesheet"></link>
		<script type="text/javascript" src="${basePath}js/ui/jquery-ui-1.8.5.js"></script>
		<link href="${basePath}themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${basePath}js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="${basePath}js/base/jquery.jsonSuggest.js"></script>
		<script type="text/javascript" src="${basePath}js/base/lvmama_common.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/back_calendar.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/back_calendar_mend.js"></script>
		<link href="${basePath}themes/mis.css"  rel="stylesheet" type="text/css" />
			 
	<script>
	function posUpdate(){
	
	 if(!checkFormat("^[0-9]{8}$", $("#terminalNo").val())){
	  alert("终端号必须是数字,并且长度必须是8位!");
	  return;
	  }
	  if($("#commercialListBox").val()==0){
	   alert("请选择商户信息!");
	   return;
	  }
	
				var dataPost = {
				    commercialId:$('#commercialListBox').val(),
					commPosId:$('#commPosId').val(),
					commercialNo:$('#commercialNo').val(),
					terminalNo:$('#terminalNo').val(),
					memo:$('#memo').val(),
					status:$('#status').val()
				};
				$.ajax({ 
							type: "POST", 
							url: "<%=basePath%>pos/updatePos.do?commPosId=${commPosId}", 
							data: dataPost,
							success: function(dt){
								data=eval("("+dt+")");
								if(data.success){
									alert("更新成功!"+data.posId);
									window.close();
								}
								else{
									alert(data.msg)
								}
							}
						});
						
			}
			
					//格式校验 
	function checkFormat(format, input) { 
		var myReg = new RegExp(format); 
		return myReg.test(input); 
	}
	</script>
			
		</head>
		<body>
		<form name='posform' id="posform"
			action='<%=basePath%>pos/insertUser.do' method="post">
			<table width="100%" class="datatable">
				<tr>
					<td>
						修改终端信息
					</td>
				</tr>
			</table>
			<div class="mrtit3">
				<table width="100%" class="datatable">
                     <tr>
							<td>
								商户：
							</td>
							<td>
				
								<s:select name="commercialListBox" 
									list="commercialList" value="commercialId"
									listValue="(commercialName)+'/'+(commercialNo)" listKey="commercialId" 
									headerValue="---请选择商户---" headerKey=""></s:select>
				
							</td>
					<tr>
					<tr>
						<td>
							终端号：
						</td>
						<td>
							<input id="terminalNo" name="terminalNo"
								class="easyui-validatebox" value="${payPos.terminalNo}" /><font color="red">(必须是8位数字)</font>
						</td>
					<tr>
					</tr>
						<td>
							描述：
						</td>

						<td>
							<textarea name="memo" cols="50" rows="5" id="memo"
								value="${payPos.memo}">${payPos.memo}</textarea><font color="red">(最多100位汉字)</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type='button' name="btnAddPos" value="保存"
								class="right-button08" onclick="posUpdate()" />
						</td>

					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
