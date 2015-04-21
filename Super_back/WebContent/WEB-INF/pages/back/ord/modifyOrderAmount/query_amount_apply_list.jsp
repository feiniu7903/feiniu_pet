<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单处理后台_修改订单价格审核</title>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css">
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript">
			var lock_approve_apply = true;
			$(function(){
			function approveApply(amountApplyId,status,$dlg){
					var memo=$dlg.find("textarea").val();
		  			var param = {'ordOrderAmountApply.applyStatus':status,'ordOrderAmountApply.amountApplyId':amountApplyId,
		  					'ordOrderAmountApply.approveMemo':memo};
		  			if(!lock_approve_apply){
		  				return false;
		  			}else{
		  				lock_approve_apply = false;
		  			}
		  			$.post("<%=basePath%>ord/approveApply.do",param, function(dt) {
		  					var data=eval("("+dt+")");
		  					lock_approve_apply = true;
		  					if(data.success){
								document.getElementById("applyForm").submit();
								$dlg.dialog("close");
							}
		  					else{
		  						alert(data.msg);
		  						return ;
		  					}
						});
		  		}
				$("a.amountApplyId").click(function(){
					var $dlg=$("#approve_memo_dialog");
					$dlg.find("textarea").val("");
					var current=$(this).attr("result");	
					$dlg.dialog({
						"title":"审核备注",
						"width":300,
						"modal":true,
						buttons:{
							"审核通过":function(){								
								approveApply(current,'PASS',$dlg);
							},
							"审核不通过":function(){								
								approveApply(current,'FAIL',$dlg);
							},
							"关闭":function(){
								$dlg.dialog("close");
							}
						}
					});
				});
				})
		</script>
	</head>
	<body>
		<div id="table_box">
					<!--=========================主体内容==============================-->
					<div class="table_box"  id=tags_content_1>
						<div class="mrtit3">
							<form id='applyForm' method='post'
								action='<%=basePath%>ord/queryAmountApplyList.do'>
						<table width="60%" border="0" class="newfont06"
							style="font-size: 12; text-align: left;">
							<tr>
							<td>
								订单号：
								<input type="text" name="orderId"
									value="<s:property value="orderId"/>" />
							</td>
							<td>
								审核状态:
								<s:select
									list="#{'UNVERIFIED':'未审核','ALL':'不限','PASS':'审核通过','FAIL':'不通过','CONFIRM':'已确定'}"
									name="applyStatus" listKey="key"
									listValue="value">
								</s:select>
							</td>
							<td>
							    <input type='hidden' name="applyAmountType" value="${applyAmountType }" />
								<input type='submit' name="btnOrdListQuery" value="查 询" class="right-button08" />
							</td>
							</tr>
						</table>
					</form>
						</div>
							
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="100%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
								    <td height="35" width="6%" align="center"> 订单号 </td>
									<td height="35" width="6%" align="center"> 申请人 </td>
									<td height="35" width="6%" align="center"> 审核人 </td>
									<td width="6%" align="center"> 金额 </td>
									<td width="8%" align="center"> 类型 </td>
									<td width="6%" align="center"> 状态 </td>
									<td width="20%" align="center"> 申请原因 </td>
									<td width="20%" align="center"> 审核备注</td>
									<td width="6%" align="center"> 创建时间 </td>
									<td width="6%" align="center"> 审核时间 </td>
									<td width="8%" align="center"> 操作</td>
								</tr>
								
								<s:iterator id="orderAmount" value="pagination.records">
							<tr bgcolor="#ffffff">
								<td align="center"> <s:property value="orderId" /> </td>
								<td align="center"> <s:property value="applyUser" /> </td>
								<td align="center"> <s:property value="approveUser" /> </td>
								<td align="center"> <s:property value="amountYuan" /> </td>
								<td align="center"> <s:property value="zhApplyType" /></td>
								<td align="center" id="apply_status_${orderAmount.amountApplyId}">
										<s:property value="orderAmountApplyStatusStr"/>
									</td>
								<td> <s:property value="applyMemo" /> </td>
								<td> <s:property value="approveMemo" /> </td>
								<td align="center"> <s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
								<td align="center"> <s:date name="approveTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
								<td align="center">
									<s:if test="applyStatus == 'UNVERIFIED'">
									<%--<s:if test="isApproveUnverified()">--%>
									   <a href="#amountApplyId" class="amountApplyId" result="${orderAmount.amountApplyId}">审核操作</a>
									</s:if>
										
								</td>
							</tr>
						</s:iterator>
								<tr><td colspan="17"  bgcolor="#eeeeee"> </td></tr>
							</tbody>
						</table>
					</div>
					<!--=========================主体内容 end==============================-->
			<table  width="80%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
		<!--main2 end-->
		
		<!-- 审核备注-->		
		<div id="approve_memo_dialog" style="display: none">
			<form>
		        <table width="100%">		
				 <tr>
				 <td>审核备注</td>
				 </tr>
				 <tr>
				 <td><textarea rows="5" cols="40" ></textarea></td>
				 </tr>
		        </table>
			</form>
		</div>
		
	</body>
</html>