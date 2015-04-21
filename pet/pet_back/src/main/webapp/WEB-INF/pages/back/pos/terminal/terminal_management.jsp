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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/stored/stored.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript">
function open_add_pos() {
	openWin('<%=basePath%>pos/getAddPosPage.do',600,300)
}

function open_update_pos(id) {
	openWin('<%=basePath%>pos/getUpdatePosPage.do?commPosId=' + id, 
			600,300)
}

function open_add_pos_user(posId , commercialId) {
	openWin('<%=basePath%>pos/addPosUserPage.do?commPosId='+posId+'&commercialId='+commercialId, 650,400)
}

$(document).ready(function(){
	$("#divPosStatus").lvmamaDialog({modal:true,width:300,height:150,close:function(){}});
});

function open_change_pos_status(commPosId) {
	$('#divPosStatus').reload({commPosId:commPosId});
	$('#divPosStatus').openDialog();
}

$(function(){	
		$("a.commerciaStatus").click(function(){
					var $dlg=$("#terminal_status_dialog");
					var terminallId=$(this).attr("terminallId");
					var $td=$("#tdstatus_"+terminallId);
					var current=$td.attr("result");
					$dlg.find("option[value="+current+"]").attr("selected","selected");
					$dlg.dialog({
						"title":"修改终端号状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("select option:selected").val();
								if(newVal===current){
									alert("您没有选中要修改的新状态!!");
									return false;
								}else{
									$.post("<%=basePath%>/pos/modifyStatusPos.do",{"commPosId":terminallId,"status":newVal},function(dt){
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
		})

</script>
	</head>
	<body>
		<div class="wrap">
			<div class="main2">
				<div class="table_box" id=tags_content_1>
					<s:if test="returnable=='return'"> 
							&nbsp;&nbsp;&nbsp
								<a href='javascript:history.go(-1)'>后退</a>
							</s:if>
							<s:if test="returnable==null"> 
					
					<form name='posform' id="posform" action="<%=basePath%>pos/selectPos.do" method="post">
						<div class="mrtit3">
							
							
							<table width="100%">
								<tr>
									<td>
										商户号[名称]：
									</td>
									<td>
									   <s:select name="searchCommercialId" list="commercialList" value="commercialId"
							                    listValue="(commercialNo)+'['+(commercialName)+']'"
							                   listKey="commercialId" headerValue="---请选择商户---" headerKey=""></s:select>
									</td>
									<td>
										终端号：
									</td>
									<td>
										<input id="searchTerminalNo" name="searchTerminalNo"
											class="easyui-validatebox" value="${searchTerminalNo}" />
									</td>
									<td>
										状态：
									</td>
									<td>
										<s:select name="searchStatus" id="searchStatus"
											list="#{'':'全部','NORMAL':'启用','CANCEL':'停用'}"></s:select>
									</td>


									<td>
										<input type="submit" name="btnQueryPosList" value="查询"
											class="right-button08" />
									</td>

									<td>
										<input type="button" name="btnAddPosList" value="添加终端"
											class="right-button08" onClick="open_add_pos()" />
										
									</td>

									<td class="no-right-border"></td>
									<!-- test -->

								</tr>
							</table>
							
						</div>
					</form>
					</s:if>
				</div>
				<div class="table_box" id=tags_content_1>
					<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
						width="100%" class="newfont06"
						style="font-size: 12; text-align: center;">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td width="15%">
									商户名称
								</td>
								<td width="10%">
									商户号
								</td>
								<td width="10%">
									POS供应商
								</td>
								<td width="5%">
									商户状态
								</td>
								<td width="10%">
									终端号
								</td>
								<td width="5%">
									终端状态
								</td>
								<td width="20%">
									终端描述
								</td>
								<td width="20%">
									操作
								</td>
							</tr>
							<s:iterator value="payPosPage.items" var="Item">
								<tr bgcolor="#ffffff">
									<td>
										${Item.commercialName} 
									</td>
									<td>	
										${Item.commercialNo}
									
									</td>
									<td>	
										${Item.zhSupplier}
									
									</td>
									
									<td>	
										<s:if test="#Item.commercialStatus=='NORMAL'"> 启用</s:if>
										<s:if test="#Item.commercialStatus=='CANCEL'"> 停用</s:if>
									
									</td>
									
									<td>
										${Item.terminalNo}
									</td>
									<td id='tdstatus_${Item.posId}'>
										<s:if test="#Item.status=='NORMAL'"> 启用</s:if>
										<s:if test="#Item.status=='CANCEL'"> 停用</s:if>
										
									</td>
									<td>
										${Item.memo}
									</td>
									
									<td>
										<a href="javascript:open_update_pos(${Item.posId})">修改</a>
										
										<a href="javascript:void(0);" class="commerciaStatus" terminallId="${Item.posId}">更改状态</a>
										
										<a href="javascript:open_add_pos_user(${Item.posId},${Item.commercialId})">添加员工</a>
										<a href="<%=basePath%>pos/selectUser.do?commercialId=${Item.commercialId}&commPosId=${Item.posId}&returnable=return">
											查看员工
										</a>
										<a href="javascript:void(0)" class="showLogDialog" param="{'objectId':${Item.posId},'objectType':'PAY_POS'}">日志</a>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>

				</div>

				<br />
				<table width="98%" border="0" align="center">
				<tr bgcolor="#ffffff">
					<td colspan="2">
						总条数：<s:property value="payPosPage.totalResultSize"/>
					</td>
					<td colspan="7" align="right">
							<s:property escape="false" 
							value="@com.lvmama.comm.utils.Pagination@pagination(payPosPage)"/>	
					</td>
				</tr>
			</table>
			</div>
		</div>
		
		<div id="divPosStatus" style="position: absolute;z-index: 10000;" href="<%=basePath%>pos/getChangePosStatus.do">
			
		</div>
		
		
		<div id="terminal_status_dialog" style="display: none">
			<div>修改当前终端状态</div>
			<form>
				终端状态:<select>
				    	<option value="NORMAL">启用</option>
				   	    <option value="CANCEL">停用</option>
				</select>
			</form>
		</div>
		
		
		
	</body>
</html>
