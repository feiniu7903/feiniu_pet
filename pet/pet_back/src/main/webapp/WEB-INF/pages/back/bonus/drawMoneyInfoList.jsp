<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>     
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/bonus/bonus.css" />
		
		<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
	</head>
	<body>
		<table width="90%" border="0" style="padding: 20px 0 0 30px;">
			<tr>
				<td width="5%" height="20" align="center" class="tablestyle_title">
					<a
						href="<%=basePath%>bonus/drawMoneyInfoList.do?status=<%=(String) request.getAttribute("status")%>&permId=${permId}">刷新</a>
				</td>
			</tr>
			<tr>
				<table width="90%" border="0" cellpadding="4" cellspacing="1" class="tab1-cc tab1-cc-3">
					<tr>
						<td width="5%" height="20" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							编号
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							提现账户
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							申请时间
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							申请金额
						</td>
						<td width="10%" height="20" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							发票
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							税款
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							实付金额
						</td>
						<td width="15%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							联系人/手机
						</td>
						<td width="10%" height="20" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							状态
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							操作
						</td>
					</tr>
					<s:iterator value="pagination.items" var="bonusDrawMoneyInfo">
						<tr>
							<td width="5%" height="20" align="center">
								<s:property value="drawMoneyInfoId" />
							</td>
							<td width="10%" align="center">
								<s:property value="userName" />
							</td>
							<td width="10%" align="center">
								<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="10%" align="center">
								<s:property value="amountYuan" />
								元
							</td>
							<td width="10%" height="20" align="center">
								<s:if test='isInvoice == "Y"'>提供</s:if>
								<s:if test='isInvoice == "N"'>不提供</s:if>
							</td>
							<td width="10%" align="center">
								<s:property value="taxYuan" />
								元
							</td>
							<td width="10%" align="center">
								<s:property value="cashYuan" />
								元
							</td>
							<td width="15%" align="center">
								<s:property value="contactName" />
								/
								<s:property value="contactMobile" />
							</td>
							<td width="10%" height="20" align="center">
								<s:if test='status=="NEW"'>
									<span>待审核</span>
								</s:if>
								<s:if test='status=="PASS"'>
									<span>审核通过</span>
								</s:if>
								<s:if test='status=="CANCEL"'>
									<span>取消</span>
								</s:if>
								<s:if test='status=="PAYOUT"'>
									<span>已打款</span>
								</s:if>
							</td>
							<td width="10%" align="center">
								<mis:checkPerm permCode="1007,1008,1009,1010" permParentCode="${permId}">
									<a href="<%=basePath%>bonus/view.do?id=<s:property value="drawMoneyInfoId" />">查看详情</a>
								</mis:checkPerm>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td>
							总条数：<s:property value="pagination.totalResultSize"/>
						</td>
						<td colspan="9" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		            </tr>
				</table>
			</tr>
		</table>
	</body>
</html>
<script type="text/javascript">
function updateStatus() {
	if (confirm("您确定要更新状态？")) {
		return true;
	} else {
		return false;
	}
}
</script>
