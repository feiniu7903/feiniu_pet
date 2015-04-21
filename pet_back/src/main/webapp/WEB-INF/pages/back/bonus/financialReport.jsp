<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/bonus/bonus.css" />
	</head>
	<body>
		<table width="90%" border="0" style="padding: 20px 0 0 30px;">
			<tr>
				<table width="90%" border="0" cellpadding="4" cellspacing="1" class="tab1-cc tab1-cc-3">
					<tr>
						<td width="5%" height="20" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							日期
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							累计返现余额
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							今日累计余额变化
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							累计达到提现条件的金额
						</td>
						<td width="10%" height="20" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							累计税后已提现金额
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE"
							class="tablestyle_title">
							今日成功提现的金额
						</td>
					</tr>
					<s:iterator value="reportPage.items">
						<tr>
							<td width="5%" height="20" align="center">
								<s:property value="createDate" />
							</td>
							<td width="10%" align="center">
								<s:property value="totalSumCashYuan" />元
							</td>
							<td width="10%" align="center">
								<s:property value="todaySumCashYuan" />元
							</td>
							<td width="10%" align="center">
								<s:property value="totalKeTiXianYuan" />元
							</td>
							<td width="10%" height="20" align="center">
								<s:property value="totalTiXianYuan" />元
							</td>
							<td width="10%" align="center">
								<s:property value="todayTiXianYuan" />元
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td>
							总条数：<s:property value="reportPage.totalResultSize"/>
						</td>
						<td colspan="8" align="right">
							<s:property escape="false" 
								value="@com.lvmama.comm.utils.Pagination@pagination(reportPage.pageSize,reportPage.totalPageNum,reportPage.url,reportPage.currentPage,reportPage.actionType,reportPage.pageParamName,reportPage.currPageParamName)"/>
						</td>
					</tr>
				</table>
			</tr>
		</table>
	</body>
</html>
