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
		<div class="order-info2" id="id_port_info" name="name_port_info">
			<!--订单信息 支付给景区 S-->
			<h3>
				1.订单详情
			</h3>
			<table class="info1">
				<tr>
					<th>
						订单号：
					</th>
					<td>
						${passPortInfo.orderId}
					</td>
				</tr>
				<tr>
					<th>
						取票人/联系人：
					</th>
					<td>
						${passPortInfo.name}
					</td>
				</tr>
				<tr>
					<th>
						手机号：
					</th>
					<td>
						${passPortInfo.mobile}
					</td>
				</tr>
				<tr>
					<th>
						预计游玩日期：
					</th>
					<td>
						${passPortInfo.visitTime}
					</td>
				</tr>
				<tr>
					<th>
						预订游玩人数：
					</th>
					<td>
						<strong>${passPortInfo.totalMan}</strong>
						(${passPortInfo.adult}/${passPortInfo.child})
						<span>共计${passPortInfo.totalMan}人，其中成人${passPortInfo.adult}人，儿童${passPortInfo.child}人</span>
					</td>
				</tr>
				<s:if test="passPortInfo.payChannel=='TOLVMAMA'">
					<tr>
						<th>
							实际通关人数：
						</th>
						<td>
							<strong>${passPortInfo.totalMan}</strong>
						(${passPortInfo.adult}/${passPortInfo.child})
						<span>共计${passPortInfo.totalMan}人，其中成人${passPortInfo.adult}人，儿童${passPortInfo.child}人</span>
							<input type="hidden" size="5" id="id_port_quantity"
								value="${port_quantity}" />
						</td>
					</tr>
					<tr>
						<th>
							支付：
						</th>
						<td>
							<em>已支付驴妈妈</em>
						</td>
					</tr>
				</s:if>
				<s:else>
					<tr>
						<th>
							实际通关人数：
						</th>
						<td>
							<div id="div_port_quantity"><strong>${passPortInfo.totalMan}</strong>
						(${passPortInfo.adult}/${passPortInfo.child})
						<span>共计${passPortInfo.totalMan}人，其中成人${passPortInfo.adult}人，儿童${passPortInfo.child}人</span></div>
							<a class="func-btn" name="edit-btn" onclick="funcBtn('edit-btn')">修改</a>
							<input type="hidden" id="port_quantity" value="${port_quantity}">
							<input type="hidden" id="ipt_adult" value="${passPortInfo.totalAdultProductQuantity}">
							<input type="hidden" id="ipt_child" value="${passPortInfo.totalChildProductQuantity}">
						</td>
					</tr>
					<tr class="edit-info">
						<th>
							实际通关人数：
						</th>
						<td>
							此产品每份订购包含
							<em>${port_adult}</em> 成人
							<em>${port_child}</em> 儿童，游客已经订购
							<em>${port_quantity}</em> 份。
							<br />
							需修改为：
							<input type="text" size="5" id="id_port_quantity"
								value="${port_quantity}" />
							&nbsp;份&nbsp;&nbsp;
							<a class="func-btn"
								onclick="updateTotalQuantity('${passPortInfo.orderId}','${passPortInfo.targetId}')">保存</a><a
								class="func-btn" name="canc-btn"
								onclick=canc_order(${port_quantity},${passPortInfo.priceYuan});>取消</a>
						</td>
					</tr>
					<tr>
						<th>
							支付：
						</th>
						<td>
							<em>未付款，需向您支付<span id="port_price"><strong>${passPortInfo.priceYuan}</strong>
							</span>元</em>
						</td>
					</tr>
				</s:else>

			</table>
			<h3>
				2.通关游玩人信息
			</h3>
			<table class="info2">
				<tr>
					<th>
						类别
					</th>
					<th>
						游客姓名
					</th>
					<th>
						联系电话
					</th>
					<th>
						证件类型
					</th>
					<th>
						证件号码
					</th>
				</tr>
				<s:iterator value="passPortInfo.personList">
					<s:if test="personType=='TRAVELLER'">
						<tr>
							<td>
								游客
							</td>
							<td>
								${name}
							</td>
							<td>
								${mobile}
							</td>
							<td>
								<s:if test="certType=='HUZHAO'">护照</s:if>
								<s:elseif test="certType=='ID_CARD'">身份证</s:elseif>
								<s:elseif test="certType=='OTHER'">其他</s:elseif>
							</td>
							<td>
								${certNo}
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			<div class="btns">
				<button type="button">
					打印小票
				</button>
				<button type="button"
					onClick="passPortInfo('${passPortInfo.orderId}','${passPortInfo.targetId}','${port_quantity}')">
					通 关
				</button>
			</div>
		</div>
		<div class="alphabg"></div>
	</body>
</html>
