<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>E景通_驴妈妈旅游网</title>
		<link href="<%=basePath%>css/e.css" rel="stylesheet" type="text/css" />
		<script src="http://pic.lvmama.com/js/jquery142.js"
			type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
		var ctx = "${pageContext.request.contextPath}";
		</script>
				<script src="<%=basePath%>js/queryPassPort.js" type="text/javascript"></script>
	</head>

	<body>
		<form name='frmPassPort' id='frmPassPort' method='post'
			action='<%=basePath%>port/queryPassPort.do' onsubmit="return chk_submitQueryPassPort()">
			<jsp:include page="/WEB-INF/epalce/toolbar_user_info.jsp"
				flush="true" />
			<div class="order-search">
			    <s:if test="operatorId=='23'">
					<input type="hidden" name="port_mobile" id="port_mobile" value="${port_mobile}" />
					<input type="hidden" name="port_orderId" id="port_orderId" value="${port_orderId}" />
					<input type="hidden" name="port_userName" id="port_userName" value="${port_userName}" />
				</s:if>
				<s:else>
				  手机号：
					<input type="text" name="port_mobile" id="port_mobile" value="${port_mobile}" />
					订单号：
					<input type="text" name="port_orderId" id="port_orderId" value="${port_orderId}" />
					游客姓名：
					<input type="text" name="port_userName" id="port_userName" value="${port_userName}" />
				</s:else>
				辅助码:
				<input type="text" name="port_passPort" id="port_passPort" value="${port_passPort}" />
				<button type="submit">
					查 询
				</button>
				<div class="date">
					今天是：${todayDate}
				</div>
			</div>
			<table class="passport-order" name="order-table">
				<thead>
					<tr>
						<th>
							订单号
						</th>
						<th>
							游客姓名
						</th>
						<th>
							手机号
						</th>
						<th>
							身份证
						</th>
						<th>
							产品名称
						</th>
						<th>
							下单日期
						</th>
						<th>
							预计游玩日期
						</th>
						<th>
							人数(成人/儿童)
						</th>
						<th>
							通关人数
						</th>
						<th>
							备注
						</th>
						<th>
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="fulfillList">
						<tr>
							<td>
								${orderId}
							</td>
							<td>
								${contactName}
							</td>
							<td>
								${contactMobile}
							</td>
							<td>
								${contactCertNo}
							</td>
							<td>
								${branchName}
							</td>
							<td>
								${strOrderCreateTime}
							</td>
							<td>
								${strDeadlineTime}
							</td>
							<td>
								${visitorQuantity}
								(${adultQuantity}/${childQuantity})
							</td>
							<td>
								${performPassedQuantity}
							</td>
							<td>
								<a onclick="viewOrder(this,'${faxMemo}')">查看</a>
							</td>
							<td>
							<s:if test="isShowPass&&operate||operatorId=='23'">
									<a onclick="passPort('${orderId}','${performPassedQuantity}')">通关</a>
							</s:if>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</form>

		<table class="passport-order" width="90%" border="0" align="center">
			<tr>
				<td width="30%">
			当前页合并（ <s:property value="uniteCount"/> ）条
				</td>			
				<td>
			<s:property escape="false" value="pagination.pagination"/>
				</td>			
			</tr>
		</table>

		<!--查看层 S-->
		<div class="view" name="view">
			<img src="http://pic.lvmama.com/img/icons/closebtn.gif" alt="关闭"
				class="closebtn" onclick="closeBtn(this)" />
			<h3>
				查看详情
			</h3>
			<div class="view-content">
				<p id="p_divFaxMemo" />
			</div>
		</div>
		<!--订单信息 支付给景区 E-->
		<div class="alphabg"></div>

	</body>
</html>
