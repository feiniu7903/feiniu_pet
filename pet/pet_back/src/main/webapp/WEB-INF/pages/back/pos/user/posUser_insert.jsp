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
  if(!checkFormat("^[A-Z0-9]{10}$", $("#empNo_ins").val())){
	   alert("员工号必须是大写字母加数字,并且长度必须是10位!");
	  return ;
  }
  if(!checkFormat("^[0-9]{6}$", $("#empPasswd_ins").val())){
	   alert("密码必须为6位的数字!");
	  return ;
  }
		var dataPost = {
			commercialId : $('#commPosCommercial').val(),
			commPosId : $('#commPos').val(),
			empNo : $('#empNo_ins').val(),
			empName : $('#empName_ins').val(),
			empPasswd : $('#empPasswd_ins').val(),
			empCompanyNo : $('#empCompanyNo_ins').val(),
			empCompanyName : $('#empCompanyName_ins').val(),
			empStatus : $('#empStatus_ins').val(),
			empLocation : $('#empLocation_ins').val()
		};
		$.ajax( {
			type : "POST",
			url : "<%=basePath%>pos/insertUser.do",
			data : dataPost,
			success : function(dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					alert("添加员工成功!! " + data.posUserId);
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
	$("#empName_ins").jsonSuggest({ 
		url:"<%=basePath%>pos/searchUserName.do", 
		maxResults: 20, 
		minCharacters:1, 
		onSelect:function(item){ 
			$("#empName_ins").val(item.id); 
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
								value="cCommercialId"  listValue="(commercialNo)+'['+(commercialName)+']'" 
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
							<input id="empNo_ins" name="empNo_ins" class="easyui-validatebox"
								required="true" value="${empNo}" /><font color="red">(必须是10位,作为POS机登录名)</font>
						</td>
					</tr>
					<tr>
						<td>
							员工名：
						</td>
						<td>
							<input type="text" id="empName_ins" name="empName_ins" class="searchInput"
							          autocomplete="off" style="height: 22px"/>
						</td>
				    </tr>
					<tr>
						<td>
							员工所在地：
						</td>
						<td>
							<input id="empLocation_ins" name="empLocation_ins"
								class="easyui-validatebox" required="true"
								value="${empLocation}" /><font color="red">(最多30位汉字)</font>
						</td>
                    </tr>
					<tr>
						<td>
							员工状态：
						</td>
						<td>
						<s:select name="empStatus_ins" list="#{'NORMAL':'启用','CANCEL':'停用'}"></s:select>
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input id="empPasswd_ins" name="empPasswd_ins" class="easyui-validatebox"
								required="true" value="${empPasswd}" /><font color="red">(必须是6位数字)</font>
						</td>
                   </tr>
				   <tr>
						<td>
							单位编号：
						</td>
						<td>
							<input id="empCompanyNo_ins" name="empCompanyNo_ins" class="easyui-validatebox" required="true"
								value="${empCompanyNo}" /><font color="red">(最多15位汉字)</font>
						</td>
                   </tr>
				   <tr>
						<td>
							单位名称：
						</td>
						<td>
							<input id="empCompanyName_ins" name="empCompanyName_ins" class="easyui-validatebox" required="true"
								value="${empCompanyName}" /><font color="red">(最多30位汉字)</font>
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
