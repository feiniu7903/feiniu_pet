<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<title>订单统计</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<head>
<body>
	<form id="frmEdit" method="post" action="${basePath}/recon/queryOrderAccount.do">
	<input type="hidden" name="beginTime" value="<s:date name="beginTime" format="yyyy-MM-dd"/>"/>
	<input type="hidden" name="endTime" value="<s:date name="endTime" format="yyyy-MM-dd"/>"/>
	<input type="hidden" name="finStatus" value="${finStatus}"/>
	<input type="hidden" name="accountType" value="${accountType}"/>
	<input type="hidden" name="tickedNo" value="${tickedNo}"/>
	<input type="hidden" name="currentPage" value="${page}"/>
		<div>
			<table border="0" cellspacing="0" cellpadding="0" class="gl_table"
				id="resultstable">
				<tr>
					<th align="center">订单号</th>
					<th align="center">预收</th>
					<th align="center">退款冲预收</th>
					<th align="center">废单冲预收</th>
					<th align="center">收入</th>
					<th align="center">冲收入</th>
					<th align="center">成本</th>
				</tr>
				<s:iterator value="datas.items" var="obj">
				<tr>
					<td>${obj.TICKED_NO}</td>
					<td>${obj.BOOKING_INCOME}</td>
					<td>${obj.REFUNDMENT_ADVANCE}</td>
					<td>${obj.CANCEL_INCOME_HEDGE}</td>
					<td>${obj.DETERMINE_INCOME}</td>
					<td>${obj.REFUNDMENT_INCOME}</td>
					<td>${obj.ORDER_COST}</td>
				</tr>
				</s:iterator>
				<tr>
					<td>总条数：<s:property value="datas.totalResultSize" />
					</td>
					<td colspan="16" align="right"><s:property escape="false"
							value="@com.lvmama.comm.utils.Pagination@pagePost(datas.pageSize,datas.totalPageNum,datas.url,datas.currentPage)" /></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>