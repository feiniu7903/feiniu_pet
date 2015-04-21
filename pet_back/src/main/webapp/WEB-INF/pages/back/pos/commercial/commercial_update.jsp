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
	function commercialUpdate(){
	
		if(checkFormat("^[0-9]{15}$", $("#commercialNo").val())){
		var dataPost = {
				commercialId:$('#commercialId').val(),
				commercialNo:$('#commercialNo').val(),
				commercialName:$('#commercialName').val(),
				remark:$('#remark').val()
			};
			
			$.ajax( {
				type : "POST",
				url: "<%=basePath%>pos/updateCommercial.do?commercialId=${payPosCommercial.commercialId}", 
				data : dataPost,
				success : function(dt) {
					data = eval("(" + dt + ")");
					if (data.success) {
						alert("更新成功!("+data.CommercialId +")");
						window.close()
					} else {
						alert(data.msg)
					}
		
				}
			});
		}else{
		   alert("商务号必须是数字,并且长度必须是15位!");
		}
   }
			
			//格式校验 
	function checkFormat(format, input) { 
		var myReg = new RegExp(format); 
		return myReg.test(input); 
	}
			
			</script>
			
		</head>

		<body>
				<!--form name='posform' id="posform" action='<%=basePath%>pos/updateCommercial.do' method="post"-->
				 <table width="100%" class="datatable">
					<tr>
						<td>
							修改商户信息
						</td>
			       </tr>
			       </table>
					<div class="mrtit3">
					<table width="100%" class="datatable">

						<tr>
							<td>
								商户号：
							</td>
							<td>
								<input id="commercialNo" name="commercialNo"
									class="easyui-validatebox" 
									value="${payPosCommercial.commercialNo}" 
									 /><font color="red">(必须是15位数字)</font>
							</td>
						</tr>
						<tr>
							<td>
								商户名称：
							</td>
							<td>
								<input id="commercialName" name="commercialName"
									class="easyui-validatebox" 
									value="${payPosCommercial.commercialName}" /><font color="red">(最多30位汉字)</font>
							</td>
						</tr>
						<tr>
							<td>
								备注：
							</td >
							<td>
									<textarea name="remark" cols="48" rows="5"  class="easyui-validatebox" 
									id="remark" value="${payPosCommercial.remark}">${payPosCommercial.remark}</textarea><font color="red">(最多100位汉字)</font>
							</td>	
							
							
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type='button' name="btnAddPos" value="保存"
									class="right-button08" onclick="commercialUpdate()" />
							</td>
							
						</tr>
					</table>
					</div>
		</body>
</html>
