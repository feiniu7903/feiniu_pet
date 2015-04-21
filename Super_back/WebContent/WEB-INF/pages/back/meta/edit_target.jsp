<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——采购产品对象</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
<script type="text/javascript" src="<%=basePath%>js/meta/target.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<style type="text/css">
dt form span {
	float: left;
	margin-right: 15px;
}
</style>
</head>

<body>
	<div class="main main04">
		<div class="row1">
			<h3 class="newTit">
				采购产品信息
				<s:if test="metaProductId!=null">
					<jsp:include page="/WEB-INF/pages/back/meta/goUpStep.jsp"></jsp:include>
				</s:if>
			</h3>
			<s:include value="/WEB-INF/pages/back/meta/nav.jsp" />
		</div>
		<!--row1 end-->
		<div class="row2">
			<div class="rowpro" style="border: none">
				<dl>
					<dt>
						<span class="dome_title">履行对象</span>
						<s:if
							test="performTargetList == null || performTargetList.size < 1">
							<a href="#change" class="change" tt="META_PERFORM">选择对象</a>
						</s:if>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="#log"
							class="showLogDialog"
							param="{'objectType':'META_PERFORM','parentType':'META_PRODUCT','parentId':${metaProductId}}">查看操作日志</a>
					</dt>
					<dd>
						<table width="96%" border="0" cellspacing="0" cellpadding="0"
							class="newTable" id="META_PERFORM_tb">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>履行方式</td>
								<td>履行信息</td>
								<td>支付信息</td>
								<td>操作</td>
							</tr>
							<s:iterator value="performTargetList">
								<tr id="META_PERFORM_${targetId}">
									<td>${targetId}</td>
									<td><a href="#showTarget"
										url="${basePath}targets/performtarget/detailperformtarget.zul?targetId=${targetId}"
										class="showTarget">${name}</a></td>
									<td>${zhCertificateType }</td>
									<td>${performInfo}</td>
									<td>${paymentInfo}</td>
									<td><a href="#change" class="change" tt="META_PERFORM"
										result="${targetId}">更换</a></td>
								</tr>
							</s:iterator>
						</table>
					</dd>
				</dl>
				<dl>
					<dt>
						<span class="dome_title">凭证对象</span>
						<s:if
							test="metaBCertificateList == null || metaBCertificateList.size < 1">
							<a href="#change" class="change" tt="META_B_CERTIFICATE">选择对象</a>
						</s:if>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="#log"
							class="showLogDialog"
							param="{'objectType':'META_B_CERTIFICATE','parentType':'META_PRODUCT','parentId':${metaProductId}}">查看操作日志</a>
					</dt>
					<dd>
						<table width="96%" border="0" cellspacing="0" cellpadding="0"
							id="META_B_CERTIFICATE_tb" class="newTable">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>凭证</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
							<s:iterator value="metaBCertificateList">
								<tr id="META_B_CERTIFICATE_${targetId}">
									<td>${targetId}</td>
									<td><a href="#showTarget"
										url="${basePath}targets/certificatetarget/detailcertificatetarget.zul?targetId=${targetId}"
										class="showTarget">${name}</a></td>
									<td>${viewBcertificate}</td>
									<td>${memo }</td>
									<td><a href="#change" class="change"
										tt="META_B_CERTIFICATE" result="${targetId}">更换</a></td>
								</tr>
							</s:iterator>
						</table>
					</dd>
				</dl>
				<div class="chuangzhen">
					传真模板:<label style="color: red">${faxTemplate}</label>
				</div>
				<dl>
					<dt>
						<span class="dome_title">结算对象</span>
						<s:if
							test="metaSettlementList == null || metaSettlementList.size < 1">
							<a href="#change" class="change" result="${targetId}"
								tt="META_SETTLEMENT">选择对象</a>
						</s:if>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="#log"
							class="showLogDialog"
							param="{'objectType':'META_SETTLEMENT','parentType':'META_PRODUCT','parentId':${metaProductId}}">查看操作日志</a>
					</dt>
					<dd>
						<table width="96%" border="0" cellspacing="0" cellpadding="0"
							id="META_SETTLEMENT_tb" class="newTable">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>结算周期</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
							<s:iterator value="metaSettlementList">
								<tr id="META_SETTLEMENT_${targetId}">
									<td id>${targetId}</td>
									<td><a href="#showTarget"
										url="${basePath}targets/settlementtarget/detailsettlementtarget.zul?targetId=${targetId}"
										class="showTarget">${name}</a></td>
									<td>${zhSettlementPeriod}</td>
									<td>${memo }</td>
									<td><a href="#change" class="change" result="${targetId}"
										tt="META_SETTLEMENT">更换</a></td>
								</tr>
							</s:iterator>
						</table>
					</dd>
				</dl>
			</div>
		</div>
	</div>
	<!--main01 main04 end-->
	<div id="showTargetDiv"></div>
	<div id="target_list_div"></div>
</body>
</html>



