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
function openAddWin() {
	openWin('<%=basePath%>pos/goCommercialInsert.do',
	600,300)
}

function openUpdateWin(id) {
	openWin('<%=basePath%>pos/goCommercialUpdate.do?commercialId=' + id, 
			600,300)
}

function openAddTerminalWin(id) {
	openWin('<%=basePath%>pos/getAddPosPage.do?commercialId='+id, 
	600,300)
}

	$(function(){	
		$("a.commerciaStatus").click(function(){
					var $dlg=$("#commercial_status_dialog");
					var commercialId=$(this).attr("commercialId");
					var $td=$("#tdstatus_"+commercialId);
					var current=$td.attr("result");
					$dlg.find("option[value="+current+"]").attr("selected","selected");
					$dlg.dialog({
						"title":"修改商户号状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("select option:selected").val();
								if(newVal===current){
									alert("您没有选中要修改的新状态!!");
									return false;
								}else{
									$.post("<%=basePath%>/pos/modifyStatusCommercial.do",{"commercialId":commercialId,"status":newVal},function(dt){
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
		
		$("a.commerciaSupplier").click(function(){
			var $dlg=$("#commercial_supplier_dialog");
			var commercialId=$(this).attr("commercialId");
			var $td=$("#tdSupplier_"+commercialId);
			var current=$td.attr("result");
			$dlg.find("option[value="+current+"]").attr("selected","selected");
			$dlg.dialog({
				"title":"修改商户号供应商",
				"width":300,
				"modal":true,
				buttons:{
					"保存":function(){
						var newVal=$dlg.find("select option:selected").val();
						if(newVal===current){
							alert("您没有选中要修改的新状态!!");
							return false;
						}else{
							$.post("<%=basePath%>/pos/modifySupplier.do",{"commercialId":commercialId,"supplier":newVal},function(dt){
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
					<form name='queryListForm' id="queryListForm"
						action="<%=basePath%>pos/commercialQueryList.do" method="post">
						<div class="mrtit3">
						
							<table width="100%">
								<tr>
									<td>
										商户号：
									</td>
									<td>
										<input id="searchCommercialNo" name="searchCommercialNo" value="${searchCommercialNo}"
											class="easyui-validatebox" />
									</td>

									<td>
										商户名称：
									</td>
									<td>
										<input id="searchCommercialName" name="searchCommercialName"  value="${searchCommercialName}"
											class="easyui-validatebox"/>
									</td>
									
									<td>
										当前状态：
									</td>
									<td>
										<s:select name="searchStatus" id="searchStatus" 
											list="#{'':'全部','NORMAL':'启用','CANCEL':'停用'}"></s:select>
									</td>
                                    <td>
										供应商：
									</td>
									<td>
										<s:select name="searchSupplier" id="searchSupplier" 
											list="#{'':'全部','COMM':'交通银行','SAND':'杉德'}"></s:select>
									</td>

									<td>
										<input type="submit" name="btnQueryList" value="查询"
											class="right-button08" />
									</td>

									<td>
										<input type="button" name="btnAddList" value="添加商户"
											class="right-button08" onClick="openAddWin()" />
										
									</td>

									<td class="no-right-border"></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<div class="table_box" id=tags_content_1>
					<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
						width="100%" class="newfont06"
						style="font-size: 12; text-align: center;">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td width="10%">
									商户号
								</td>
								<td width="15%">
									商户名称
								</td>
								<td width="8%">
									供应商
								</td>
								<td width="7%">
									状态
								</td>
								<td width="20%">
									备注
								</td>
								<td width="30%">
									操作
								</td>
							</tr>
							<s:iterator value="payPosCommerciaPage.items" var="Item">
								<tr bgcolor="#ffffff">
									<td>
										${Item.commercialNo}
										<!-- <a href="<%=basePath%>pos/commercialQueryList.do?commercialId=${Item.commercialId}">	
											${Item.commercialNo}
										</a> -->
									</td>
									<td>
										${Item.commercialName}
									</td>
									<td id='tdSupplier_${Item.commercialId}'>
										${Item.zhSupplier}
									</td>
									<td id='tdstatus_${Item.commercialId}'> 
										<s:if test="#Item.status=='NORMAL'"> 启用</s:if>
										<s:if test="#Item.status=='CANCEL'"> 停用</s:if>
									</td>
									<td>
										${Item.remark}
									</td>
									<td>
										<a href="javascript:openUpdateWin(${Item.commercialId})">修改</a>
										
										<a href="javascript:void(0);" class="commerciaStatus" commercialId="${Item.commercialId}">更改状态</a>
										
										<a href="javascript:void(0);" class="commerciaSupplier" commercialId="${Item.commercialId}">更改供应商</a>
										
										<a href="javascript:openAddTerminalWin(${Item.commercialId})">添加终端</a>
										<a href="<%=basePath%>pos/selectPos.do?commercialId=${Item.commercialId}&returnable=return">
											查看终端
										</a>
										<a href="<%=basePath%>pos/selectUser.do?commercialId=${Item.commercialId}&returnable=return">
											查看员工
										</a>
										<a href="javascript:void(0)" class="showLogDialog" param="{'objectId':${Item.commercialId},'objectType':'PAY_POS_COMMERCIAL'}">日志</a>
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
							value="payPosCommerciaPage.totalResultSize" />
					</td>
					<td colspan="7" align="right"><s:property escape="false"
							value="@com.lvmama.comm.utils.Pagination@pagination(payPosCommerciaPage)" />
					</td>
				</tr>
			</table>
		</div>
		</div>
		
		<div id="divCommercialStatus" style="position: absolute;z-index: 10000;" href="<%=basePath%>pos/goCommercialModifyStatus.do">
			
		</div>
		
		
		<div id="commercial_status_dialog" style="display: none">
			<div>修改当前商户状态</div>
			<form>
				商户状态:<select>
				    	<option value="NORMAL">启用</option>
				   	    <option value="CANCEL">停用</option>
				</select>
			</form>
		</div>
		
		<div id="commercial_supplier_dialog" style="display: none">
			<div>修改当前商户供应商</div>
			<form>
				供应商:<select>
				    	<option value="COMM">交通银行</option>
				   	    <option value="SAND">杉德</option>
				</select>
			</form>
		</div>
		
	</body>
</html>
