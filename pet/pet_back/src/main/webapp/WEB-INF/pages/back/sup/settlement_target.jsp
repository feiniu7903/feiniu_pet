<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 结算对象</title>
</head>
<body>
<div>
<a href="javascript:void(0)" class="addSettmentBtn button" data="${supplierId}">新增对象</a>
</div>
<table style="width:150%" class="zhanshi_table" cellspacing="0" cellpadding="0">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>结算周期</th>
		<th>开户名称</th>
		<th>开户银行</th>
		<th>开户账号</th>
		<th>支付宝账号</th>
		<th>支付宝用户名</th>
		<th>类型</th>
		<th>付款方式</th>
		<th>联行号</th>
		<th>备注</th>
		<th>操作</th>
	</tr>
	<s:iterator value="settlementTargetList">
	<tr>
		<td><s:property value="targetId"/></td>
		<td><s:property value="name"/></td>
		<td><s:property value="zhSettlementPeriod"/></td>
		<td><s:property value="bankAccountName"/></td>
		<td><s:property value="bankName"/></td>
		<td><s:property value="bankAccount"/></td>
		<td><s:property value="alipayAccount" /></td>
		<td><s:property value="alipayName" /></td>
		<td><s:property value="zhType"/></td>
		<td><s:property value="paymentTypeName" /></td>
		<td><s:property value="bankLines" /></td>
		<td><s:property value="memo" /></td>
		<td>
		<mis:checkPerm permCode="1412">
			<a href="javascript:void(0)" class="editSettlementBtn" data="<s:property value="targetId"/>">修改</a>
		</mis:checkPerm>
		&nbsp;&nbsp;<a href="javascript:void(0)" class="showMetaProductListBtn" data="<s:property value="targetId"/>">查看采购产品</a></td>
	</tr>
	</s:iterator>
</table>
<br/><br/>
<div style="margin:0 auto;width:100%">
<table class="cg_xx" width="600">
<tr>
<td>
结算对象修改规则：
</td>
</tr>
<tr>
<td>
1、一个月中只有一种结算周期。<br/>

（如果中途修改周期会出现如下情况：月结改成单结算，财务会在修改后的第一天将本月的所有的订单结算掉。单结算改成月结算，从下个月1号开始结算修改日之后的所有订单）
</td>
</tr>
<tr>
<td>
2、修改结算本身的账号名单信息，月结产品仅仅可以在11-30号修改，每单结算可以在每天的5点后修改，但是修改后第二天的打款即刻为新账号
</td>
</tr>
<tr>
<td>
3、提前打款规则为，按照游玩日期提前一个工作日打款。遇到节假日和周末以最近的工作日为依据。
</td>
</tr>
<tr>
<td>
4、尽量系统以月结和单结算（提前打款）两种结算方式为主。
</td>
</tr>
</table>

</div>
<div id="addSettlementDiv" url="${basePath}/sup/target/toAddSettlement.do"></div>
<div id="metaProductListDiv"></div>
</body>
</html>