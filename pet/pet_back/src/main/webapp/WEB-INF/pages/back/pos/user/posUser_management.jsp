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
        <script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=basePath%>/js/base/perm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script type="text/javascript" src="<%=basePath%>/js/base/jquery.jsonSuggest-2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
		
<script type="text/javascript">
function queryUser() {
	var dataPost = {
		terminalNo : $('#terminalNo').val(),
		empNo : $('#empNo').val(),
		empStatus : $('#empStatus').val()
	};
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>pos/selectUser.do",
		data : dataPost,
		success : function(data) {
			 
		}
	});
}		

function open_add_pos_user() {
	openWin('<%=basePath%>pos/addPosUserPage.do', 650,400)
}

function open_update_pos_user(id) {
	openWin('<%=basePath%>pos/updateUserPage.do?commPosUserId=' + id,650,400)
}

function showUserDialog(terminalNo){
//$('#addPosUser').reload();
	$('#addPosUser').reload({terminalNo:terminalNo});
	$('#addPosUser').openDialog();
}



function open_change_pos_user_status(commPosUserId) {
	$('#divPosUserStatus').reload({commPosUserId:commPosUserId});
	$('#divPosUserStatus').openDialog();
}

$(function(){
	$("#addPosUser").lvmamaDialog({modal:true,width:800,height:220,close:function(){}});
	$("#divPosUserStatus").lvmamaDialog({modal:true,width:300,height:200,close:function(){}});
	
	$("a.posUserStatus").click(function(){
		var $dlg=$("#posUser_status_dialog");
		var posUserId=$(this).attr("posUserId");
		var $td=$("#tdstatus_"+posUserId);
		var current=$td.attr("result");
		$dlg.find("option[value="+current+"]").attr("selected","selected");
		$dlg.dialog({
			"title":"修改用户状态",
			"width":300,
			"modal":true,
			buttons:{
				"保存":function(){
					var newVal=$dlg.find("select option:selected").val();
					if(newVal===current){
						alert("您没有选中要修改的新状态!!");
						return false;
					}else{
						$.post("<%=basePath%>/pos/modifyStatusUser.do",{"commPosUserId":posUserId,"empStatus":newVal},function(dt){
							var data=eval("("+dt+")");
							if(data.success){
								$td.attr("result",newVal);
								$td.html($dlg.find("select option:selected").text());
								$dlg.dialog("close");
							}else{
								alert(data.msg);
							}
						});
					}
				},
				"取消":function(){
					$dlg.dialog("close");
				}
			}
		});
	});
	
	
	
	
	$("a.posUserPassword").click(function(){
		var $dlg=$("#posUser_password_dialog");
		var posUserId=$(this).attr("posUserId");
		$dlg.dialog({
			"title":"修改用户密码",
			"width":300,
			"modal":true,
			buttons:{
				"保存":function(){
					var newVal=$dlg.find("input[name=empPasswd]").val();
					if(newVal.length==6){
					   $.post("<%=basePath%>/pos/modifyPassword.do",{"commPosUserId":posUserId,"empPasswd":newVal},function(dt){
							var data=eval("("+dt+")");
							if(data.success){
								alert("修改密码成功!!");
								$dlg.dialog("close");
							}else{
								alert(data.msg);
							}
						});
					}else{
						alert("请填写新的密码,新的密码必须是6位!!");
						return false;
					}
				},
				"取消":function(){
					$dlg.dialog("close");
				}
			}
		});
	});
	
		$("#searcEmpName").jsonSuggest({ 
			url:"<%=basePath%>pos/searchUserName.do", 
			maxResults: 20, 
			minCharacters:1, 
			onSelect:function(item){ 
				$("#searcEmpName").val(item.id); 
			}
		});
	
	
		$("#searchCommercialIdSelect").change(function(){
			var val=$(this).val();
			var $payment=$("#paymentTradeNoSelect");
			$payment.empty();
			if(val==''){
				var $opt=$("<option/>");
				$opt.val("");
				$opt.text("请选择");
				$city.append($opt);
			}else{
				$.post("/pet_back/pos/loadCommPoss.do",{"searchCommercialId":val},function(data){
					var len=data.searchPayPosList.length;
					for(var i=0;i<len;i++){
						var payment=data.searchPayPosList[i];
						var $opt=$("<option/>");
						$opt.val(payment.posId);
						$opt.text(payment.terminalNo);
						$payment.append($opt);
						$("#paymentTradeNoSelect").trigger("change");
					}
				},"json");
				
			}
		});
})



</script>

	</head>
	<body>
	<div id="bgPos" class="bg" style="display: none;">
		<iframe
			style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =     0); opacity =0; border-style: none; z-index: -1">
		</iframe>
	</div>
		<div id='listQueryForm' class="wrap" >
			<div class="main2">
				<s:if test="returnable=='return'"> 
							&nbsp;&nbsp;&nbsp
								<a href='javascript:history.go(-1)'>后退</a>
				</s:if>
				<s:if test="returnable==null"> 
				<form name='posform' id="posform"
					action="<%=basePath%>pos/selectUser.do" method="post">
					<div class="mrtit3">
						<table width="100%">
                              <tr>
								<td>
									商户号[名称]：
								</td>
                              <td>  <s:select id="searchCommercialIdSelect" name="searchCommercialId" list="searchCommercialList" value="searchCommercialId"
							                 listValue="(commercialNo)+'['+(commercialName)+']'"
							               listKey="commercialId" headerValue="---请选择商户---" headerKey="" label="CLASS"></s:select>
								</td>
								<td>
									POS终端号：
								</td>
                              <td >
                                  <s:select  id="paymentTradeNoSelect"  listKey="posId"  listValue="terminalNo" name="searchCommPosId" list="searchCommPosList"> </s:select>
                                  
							  </td>
							  <td colspan="4">
								</td>
							</tr>
							<tr>
								<td>
									员工号：
								</td>
								<td>
									<input id="searchEmpNo" name="searchEmpNo" class="easyui-validatebox"
										value="${searchEmpNo}" />
								</td>

								<td>
									用户名：
								</td>
								<td>
									<input type="text" id="searcEmpName" name="searcEmpName" class="searchInput" autocomplete="off" style="height: 22px" value="${searcEmpName}"/>
								</td>

								<td>
									员工状态：
								</td>
								<td>
									<s:select name="searchEmpStatus"
										list="#{'':'全部','NORMAL':'启用','CANCEL':'停用'}"></s:select>
								</td>

								<td>
									<input type="submit" name="btnQueryPosItemList" value="查询"
										class="right-button08" />
								</td>

								<td>
									<input type="button" name="btnAddPosItemList" value="添加员工"
										onClick="open_add_pos_user()" class="right-button08" />
								</td>

							</tr>
						</table>
					</div>
				</form>
				</s:if>
				<div class="table_box" id=tags_content_1>
					<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
						width="100%" class="newfont06"
						style="font-size: 12; text-align: center;">
						<tbody>
							<tr bgcolor="#eeeeee">
								
								<td width="8%">
									商户号 
								</td>
								<td width="7%">
									商户状态
								</td>
								<td width="8%">
									终端号
								</td>
								<td width="7%">
									终端状态
								</td>
								<td width="7%">
									POS员工号 
								</td>
								<td width="7%">
									用户名
								</td>
								<td width="8%">
									用户真实名
								</td>
								<td width="9%">
									单位号
								</td>
								<td width="13%">
									单位名称
								</td>
								<td width="6%">
									员工所在地
								</td>
								<td width="5%">
									员工状态
								</td>
								<td width="15%">
									操作
								</td>
							</tr>
							<s:iterator value="payPosUserPage.items" var="posItem">
								<tr bgcolor="#ffffff">
									
									<td>
										${posItem.commercialNo}
									</td>
									<td>
										<s:if test="#posItem.commercialStatus=='NORMAL'"> 启用</s:if>
										<s:if test="#posItem.commercialStatus=='CANCEL'"> 停用</s:if>
									</td>
									
									<td>
										${posItem.terminalNo}
									</td>
									<td>
										<s:if test="#posItem.terminalStatus=='NORMAL'"> 启用</s:if>
										<s:if test="#posItem.terminalStatus=='CANCEL'"> 停用</s:if>
									</td>
									
									<td>
										${posItem.empNo}
									</td>
									<td>
										${posItem.empName}
									</td>
									<td>
										${posItem.trueName}
									</td>
									<td>
										${posItem.empCompanyNo}
									</td>
									<td>
										${posItem.empCompanyName}
									</td>
									<td>
										${posItem.empLocation}
									</td>
									
									<td id='tdstatus_${posItem.posUserId}'>
										<s:if test="#posItem.empStatus=='NORMAL'"> 启用</s:if>
										<s:if test="#posItem.empStatus=='CANCEL'"> 停用</s:if>
									</td>
									<td>
										<a href="javascript:open_update_pos_user(${posItem.posUserId})">修改</a>
										<a href="javascript:void(0);" class="posUserPassword" posUserId="${posItem.posUserId}">修改密码</a>
										<a href="javascript:void(0);" class="posUserStatus" posUserId="${posItem.posUserId}">更改状态</a>
										
										<a href="javascript:void(0)" class="showLogDialog" param="{'objectId':${posItem.posUserId},'objectType':'PAY_POS_USER'}">日志</a>
										
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>

				<br />
				
				<table width="98%" border="0" align="center">
				<tr bgcolor="#ffffff">
					<td colspan="2">总条数：<s:property
							value="payPosUserPage.totalResultSize" />
					</td>
					<td colspan="7" align="right"><s:property escape="false"
							value="@com.lvmama.comm.utils.Pagination@pagination(payPosUserPage)" />
					</td>
					
				</tr>
			</table>
				
			</div>
 
			<div id="addPosUser" style="position: absolute;z-index: 10000;" href="<%=basePath%>pos/getAddPage.do">
			
			</div>
			<div id="divPosUserStatus" style="position: absolute;z-index: 10000;" href="<%=basePath%>pos/getChangePosUserStatus.do">
			
			</div>
			
			<div id="posUser_status_dialog" style="display: none">
			<div>修改当前POS用户状态</div>
			<form>
				POS用户状态:<select>
				    	<option value="NORMAL">启用</option>
				   	    <option value="CANCEL">停用</option>
				</select>
			</form>
		</div>
		
		<div id="posUser_password_dialog" style="display: none">
			<div>修改当前POS用户密码</div>
			<form>
				POS用户密码:
				<input id="empPasswd" name="empPasswd" class="easyui-validatebox"/><font color="red">(必须是6位)</font>
			</form>
		</div>	
			
			
		</div>
	</body>
</html>
