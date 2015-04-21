<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>订单处理后台_订单监控</title>
		<script type="text/javascript">
var path = '<%=basePath%>';
</script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<script type="text/javascript">
			function beforeSubmit(){
				var userName=$("input[name=userName]").val();
				var userMobile=$("input[name=userMobile]").val();
				var userEmail=$("input[name=userEmail]").val();
				var contactName=$("input[name=contactName]").val();
				var contactMobile=$("input[name=contactMobile]").val();
				var orderId=$("input[name=orderId]").val();
				if($.trim(userName)==''&&$.trim(userMobile)==''&&$.trim(userEmail)==''&&
						$.trim(contactName)==''&&$.trim(contactMobile)==''&&$.trim(orderId)==''){
					alert("查询条件不可以全为空");
					return false;
				}
				return true;
			}
			$(function(){
				$("#checkall").click(function(){
					$("input[name=checkBoxName]").attr("checked",$(this).attr("checked"));
				});
				
				
				$("input#addToWait").click(function(){
					var $arr=$("input[name=checkBoxName]:checked");
					if($arr.size()==0){
						alert("没有选中要操作的订单");
						return false;
					}
					var ids="";
					$.each($arr,function(i,n){
						if(i>0){
							ids+=",";
						}
						ids+=$(n).val();
					});
					if($.trim(ids)==''){
						alert("没有选中订单号");
						return false;
					}
					$.post("<%=basePath%>ord/invoiceAddReq.do",{"orderids":ids},function(dt){
						var data=eval("("+dt+")")						;
						if(data.error){
							alert(data.error);
						}
						if(data.success){
							$("#waitToAddDiv").reload();	
							var tr_in_=$("tr[id^=tr_in_]").size();	
							var totalYuan=$("#totalYuan").val();							
							if(tr_in_>1 || totalYuan<=1){
								$("#manyNumber").attr("disabled",true);
								$("#invoiceNumber").attr("readonly",true);
							}								
						}
					});
				});
				
				$("a.delete").live("click",function(){
					var result=$(this).attr("result");
					if($.trim(result)==''){
						alert("订单不存在");
						return false;
					}
					
					$.post("<%=basePath%>ord/removeOrderInInvoice.do",{orderId:result},function(dt){
						var data=eval("("+dt+")");
						if(data.success){
							$("#tr_in_"+result).remove();
							$("#waitToAddDiv").loadUrlHtml();												
						}
					});
				});
				
				<s:if test="#session.invoice_req!=null">
				$(document).ready(function(){									
					$("#waitToAddDiv").loadUrlHtml();					
					var tr_in_=$("tr[id^=tr_in_]").size();
					var totalYuan=$("#totalYuan").val();									
					if(tr_in_>1 ||totalYuan<=1){
						$("#manyNumber").attr("disabled",true);
						$("#invoiceNumber").attr("readonly",true);
					}						
				});
				</s:if>
			})
		</script>
		<style type="text/css">
			#waitToAddDiv{margin:10px;}
		</style>
	</head>
	<body>
		
			<div>
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1><form name='form1' method='post'
			action='<%=basePath%>ord/waitInvoceOrder.do'
			onsubmit="return beforeSubmit();">
						<div class="mrtit3">

							<table width="100%" border="0" class="newfont06"
								style="font-size: 12; text-align: left;">
								<tr>
									<td width="8%">
										下单人姓名：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1974" name="userName"
											value="${userName}" />
									</td>
									<td width="8%">
										下单人手机：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1975" name="userMobile"
											value="${userMobile}" />
									</td>
									<td width="8%">
										电子邮件：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1976" name="userEmail"
											value="${userEmail}" />
									</td>									
								</tr>
								<tr>
									<td width="8%">
										联系人姓名：
									</td>
									<td>
										<mis:checkElement permCode="1978" name="contactName"
											value="${contactName}" />
									</td>
									<td width="8%">
										联系人手机：
									</td>
									<td>
										<mis:checkElement permCode="1979" name="contactMobile"
											value="${contactMobile}" />
									</td>
									<td width="8%">
										订单编号：
									</td>
									<td>
										<mis:checkElement permCode="1980" name="orderId"
											value="${orderId}" />
									</td>									
								</tr>
								<tr>
									<td style="text-align: right;" colspan="6">
									<input type="submit" value="查 询" class="right-button08"/>
									</td>
								</tr>
							</table>
						</div>
						</form>
						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="100%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td width="4%">
										<input type="checkbox" name="checkall" value="1" id="checkall" />
									</td>
									<td height="35">
										订单号
									</td>	
									<td>订单类型</td>																	
									<td width="6%">
										联系人姓名
									</td>
									<td width="6%">
										联系人电话
									</td>									
									<td width="6%">
										下单人
									</td>
									<td>操作</td>
								</tr>
								<s:iterator id="order" value="ordersList">
									<tr bgcolor="#ffffff">
										<td>
											<input type="checkbox" name="checkBoxName" value="${orderId}"></input>
										</td>
										<td height="30">
											${orderId}
										</td>		
										<td>${zhOrderType}</td>								
										<td>
											${contact.name }
										</td>
										<td>
											${contact.mobile }
										</td>	
										<td>${userName}</td>	
										<td>
											<a href="javascript:showDetailDiv('historyDiv', '${orderId}');">查看</a>
										</td>			
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<table width="90%" border="0" align="center">
							<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
						</table>
						<div style="text-align: right;margin:5px">
							<input type="button"  class="right-button08" value="添加到待开票" id="addToWait"/>
						</div>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>
		<div style="margin:10px;">
兴旅：境外产品（自由行/门票/酒店/签证）；国内线路类产品；分社（除三亚外分社）国内/出境线路类产品。 <br/> 
景域：国内自由行/单门票/单酒店；分社自由行/单门票/单酒店；实体票业务。 <br/> 
国旅：三亚分社国内线路类产品。 <br/> 
		</div>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="<%=basePath%>ord/orderDetail.do">
		</div>
		<div id="waitToAddDiv" href="<%=basePath%>ord/waitToAddList.do"></div>
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                           0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
	</body>
</html>
