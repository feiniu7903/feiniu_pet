<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<title>订单收入退款入账不平统计</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<head>
<body>
	<form id="frmEdit" method="post" action="${basePath}/recon/selectFinanceOrderMonitor.do">
	<input type="hidden" name="beginTime" value="<s:date name="beginTime" format="yyyy-MM-dd"/>"/>
	<input type="hidden" name="endTime" value="<s:date name="endTime" format="yyyy-MM-dd"/>"/>
	<input type="hidden" name="tickedNo" value="${tickedNo}"/>
		<div>
			<table border="0" cellspacing="0" cellpadding="0" class="gl_table"
				id="resultstable">
				<tr>
					<th align="center">订单号</th>
					<th align="center">实收</th>
					<th align="center">勾兑收入</th>
					<th align="center">代收</th>
					<th align="center">订单退款</th>
					<th align="center">勾兑退款</th>
					<th align="center">代退</th>
				</tr>
				<s:iterator value="datas.items" var="obj">
				<tr class="minoter">
					<td <s:if test="null!=#obj.ORI_ORDER_ID">title="${obj.ORI_ORDER_ID}"</s:if>>${obj.ORDER_ID}<s:if test="null!=#obj.ORI_ORDER_ID"><font color="red">*</font></s:if>
					<td>${obj.ACTUAL_PAY}</td>
					<td>${obj.RECON_AMOUNT}</td>
					<td>${obj.FIN_AMOUNT}</td>
					<td>${obj.REFUND_AMOUNT}</td>
					<td>${obj.REFUND_RECON_AMOUNT}</td>
					<td>${obj.REFUND_FIN_AMOUNT}</td>
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
<script type="text/javascript">
$(function(){
	$(document.body).ready(function(){
		$("#resultstable").find(".minoter").each(function(){
			var tds = $(this).children("td");
			if(tds.eq(1).text()!=tds.eq(2).text() || tds.eq(1).text()!=tds.eq(3).text()){
				tds.eq(1).css("background-color","#CCCCCC");
				tds.eq(2).css("background-color","#CCCCCC");
				tds.eq(3).css("background-color","#CCCCCC");
			}
			if(tds.eq(4).text()!=tds.eq(5).text() || tds.eq(4).text()!=tds.eq(6).text()){
				tds.eq(4).css("background-color","#EEEEEE");
				tds.eq(5).css("background-color","#EEEEEE");
				tds.eq(6).css("background-color","#EEEEEE");
			}
		});
	});
});
</script>