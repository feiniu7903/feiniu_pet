<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	
	</head>
	<script type="text/javascript"
		src="<%=basePath%>js/base/lvmama_dialog.js"></script>	
	<body>
		<script type="text/javascript">
			function checkSelect(form){
				var mobileNumber=$(form).find("input[name=mobileNumber]:checked").val();
				if(mobileNumber=="undefined"||mobileNumber==undefined){
					alert("请选择短信接收人");
					return false;
				}
				
				return true;
			}
		</script>
		<form name='frmOrdSms' method='post'
			action='<%=basePath%>ordSms/sendOrdOrderSms.do' onsubmit="return checkSelect(this)">
			<input type="hidden" name="checkValue" value="${checkValue}">
			<input type="hidden" name="ordOrderId" value="${ordOrderId}">
			<input type="hidden" name="smsType" value="${smsType}">
			<input type="hidden" name="returnUrl" value="${returnUrl}">
			<div class="orderpoptit">
				<strong>发送短信凭证</strong>
				<p class="inputbtn">
					<input type="button" name="btnCloseDetailDiv" class="button" value="关闭"
						onclick="javascript:closeDetailDiv('order_sms');">
				</p>
			</div>

			<div class="orderpopmain">
				<div class="popbox">
					<strong>短信信息</strong>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
						<tbody>
							<tr align="center">
								<td bgcolor="#f4f4f4" width="2%">
									手机号码
								</td>
								<td bgcolor="#ffffff" align="left">
									<table>
									<tr bgcolor="#eeeeee">
										<th>类别</th>
										<th>姓名</th>
										<th>手机</th>
									</tr>
									<s:if test="orderDetail.contact!=null">
									<tr bgcolor="#ffffff" align="center">
										<td height="25">
											<input type="radio" name="mobileNumber" value="${orderDetail.contact.mobile }"/>
											取票人/联系人
										</td>
										<td>
											${orderDetail.contact.name }
										</td>
										<td>
											${orderDetail.contact.mobile }
										</td>
									</tr>
									</s:if>
									<s:iterator id="person" value="orderDetail.personList">
									<s:if test="#person.personType=='TRAVELLER'">
										<tr bgcolor="#ffffff" align="center">
											<td height="25">
												<input type="radio" name="mobileNumber" value="${person.mobile}">
												游客
											</td>
											<td>
												${person.name }
											</td>
											<td>
												${person.mobile }
											</td>									
										</tr>
									</s:if>
									</table>
								</s:iterator>										
								</td>
							</tr>
							<tr align="center">
								<td bgcolor="#f4f4f4" width="15%">
									选择发送
								</td>
								<td bgcolor="#ffffff" align="left">
									<input type="radio" name="redio_type"  checked="checked"
										value="new" />
									发送短信凭证新内容
								</td>
							</tr>
							<tr align="center">
								<td height="35" bgcolor="#f4f4f4" width="10%">
								</td>
								<td bgcolor="#ffffff" align="left">
									<input type="radio"  name="redio_type" />
									补充内容
									<textarea id="ordContent" name="ordContent" rows="5"
										cols="80"></textarea>
								</td>
							</tr>
						</tbody>
					</table>

				</div>
				<div class="orderpop" id="order_sms" style="display: none;"
					href="<%=basePath%>ord/showOrderPay.do">
				</div>
				<!--弹出层灰色背景-->
				<div id="bgPay" class="bg" style="display: none;">
				</div>
				<input type="submit" name="btnOrdSmsSend" class="right-button08" value="发送" />
				<!--popbox end-->
				<p class="submitbtn2">
					<input type="button"  name="btnOrdSmsCloseDiv" class="button" value="关闭"
						onclick="javascript:closeDetailDiv('order_sms');">
				</p>
			</div>
		</form>
	</body>
</html>
