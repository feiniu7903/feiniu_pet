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
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script> 
<link href="${basePath}/css/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function clickform() {
 if(!checkFormat("^[A-Z0-9]{10}$", $("#empNo").val())){
	   alert("员工号必须是大写字母加数字,并且长度必须是10位!");
	  return ;
  }
	var dataPost = {
	
		commercialId : $('#commPosCommercial').val(),
		commPosId : $('#commPos').val(),
		empNo : $('#empNo').val(),
		empName : $('#empName').val(),
		empCompanyNo : $('#empCompanyNo').val(),
		empCompanyName : $('#empCompanyName').val(),
		empLocation : $('#empLocation').val()
		
	};
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>pos/updateUser.do?commPosUserId=${commPosUserId}", 
		data : dataPost,
		success : function(dt) {
			var data = eval("(" + dt + ")");
			if (data.success) {
				alert("更新POS用户成功!! " + data.posUserId);
				window.close()
			} else {
				alert(data.msg);
			}
			
		}
	});
}
		//格式校验 
	function checkFormat(format, input) { 
		var myReg = new RegExp(format); 
		return myReg.test(input); 
	}
	$(function(){	
		$("#empName").jsonSuggest({ 
			url:"<%=basePath%>pos/searchUserName.do", 
			maxResults: 20, 
			minCharacters:1, 
			onSelect:function(item){ 
				$("#empName").val(item.id); 
			}
		});
	});
</script>

<style>

.doubleselect br{ 
display:br; 
}

</style>

	</head>
	<body>	
	

		<!-- form name='posform' id="posform"
			action='<%=basePath%>pos/insertUser.do' method="post"> -->
			<table width="100%" class="datatable">
				<tr>
					<td>
						添加POS用户信息
					</td>
				</tr>
			</table>
			 <div class="mrtit3">
				<table width="100%" class="datatable">
					<tr>
						<td>
							商户/终端： <br /> (只显示有终端的商户)
						 </td>
						<td>  
							<div class="doubleselect"> 
						 <s:form name="doubleSelectForm" >  
							<s:doubleselect  name="commPosCommercial"  id="commPosCommercial" list="terminalMap.keySet()" 
								value="commercialId"  listValue="(commercialNo)+'['+(commercialName)+']'" 
								listKey="commercialId"  doubleName="commPos"
								doubleId="commPos" doubleValue="commPosId"
								doubleList="terminalMap[top]"  doubleListKey="posId" doubleListValue="terminalNo">
							</s:doubleselect>
							</s:form>
							</div>
						</td>
				    </tr>
					<tr>
						<td>
							POS员工号：
						</td>
						<td>
							<input id="empNo" name="empNo" class="easyui-validatebox"
								required="true" value="${payPosUser.empNo}" /><font color="red">(必须是10位,作为POS机登录名)</font>
						</td>
					</tr>
					<tr>
						<td>
							员工名：
						</td>
						<td>
							<input type="text" id="empName" name="empName" class="searchInput" autocomplete="off" style="height: 22px" value="${payPosUser.empName}"/>
						</td>
				    </tr>
					<tr>
						<td>
							员工所在地：
						</td>
						<td>
							<input id="empLocation" name="empLocation"
								class="easyui-validatebox" required="true"
								value="${payPosUser.empLocation}" /><font color="red">(最多30位汉字)</font>
						</td>
                    </tr>
					
				   <tr>
						<td>
							单位编号：
						</td>
						<td>
							<input id="empCompanyNo" name="empCompanyNo"
								class="easyui-validatebox" required="true"
								value="${payPosUser.empCompanyNo}" /><font color="red">(最多15位汉字)</font>
						</td>
                   </tr>
				   <tr>
						<td>
							单位名称：
						</td>
						<td>
							<input id="empCompanyName" name="empCompanyName"
								class="easyui-validatebox" required="true"
								value="${commPosUser.empCompanyName}" /><font color="red">(最多30位汉字)</font>
						</td>
				  </tr>
				  <tr>
						<td colspan="2" align="center">
							<input type='button' name="btnAddPosItem" value="保存"
								class="right-button08" onclick="clickform()" />
						</td>

				 </tr>
				</table>
             </div>
		
	</body>
</html>
