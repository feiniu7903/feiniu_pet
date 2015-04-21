<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——采购产品对象</title>
</head>

<body>
	<div class="row2">
		<div class="rowpro" style="border: none">
			<dl>
				<dd>
					<s:hidden name="preTargetId" id="preTargetId" />
					<s:if
						test="supplierPerformTargetList != null && supplierPerformTargetList.size > 0">
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
							<s:iterator value="supplierPerformTargetList">
								<tr id="META_PERFORM_${targetId}">
									<td>${targetId}</td>
									<td><a href="#showTarget"
										url="${basePath}targets/performtarget/detailperformtarget.zul?targetId=${targetId}"
										class="showTarget">${name}</a></td>
									<td>${zhCertificateType }</td>
									<td>${performInfo}</td>
									<td>${paymentInfo}</td>
									<td><a href="#choose" class="choose" tt="META_PERFORM"
										result="${targetId}">选择</a></td>
								</tr>
							</s:iterator>
						</table>
					</s:if>
					<s:elseif
						test="supplierBCertificateTargetList != null && supplierBCertificateTargetList.size > 0">
						<table width="96%" border="0" cellspacing="0" cellpadding="0"
							id="META_B_CERTIFICATE_tb" class="newTable">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>凭证</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
							<s:iterator value="supplierBCertificateTargetList">
								<tr id="META_B_CERTIFICATE_${targetId}">
									<td>${targetId}</td>
									<td><a href="#showTarget"
										url="${basePath}targets/certificatetarget/detailcertificatetarget.zul?targetId=${targetId}"
										class="showTarget">${name}</a></td>
									<td>${viewBcertificate}</td>
									<td>${memo }</td>
									<td><a href="#choose" class="choose"
										tt="META_B_CERTIFICATE" result="${targetId}">选择</a></td>
								</tr>
							</s:iterator>
						</table>
					</s:elseif>
					<s:elseif
						test="supplierSettlementTargetList != null && supplierSettlementTargetList.size > 0">
						<table width="96%" border="0" cellspacing="0" cellpadding="0"
							id="META_SETTLEMENT_tb" class="newTable">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>结算周期</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
							<s:iterator value="supplierSettlementTargetList">
								<tr id="META_SETTLEMENT_${targetId}">
									<td id>${targetId}</td>
									<td><a href="#showTarget"
										url="${basePath}targets/settlementtarget/detailsettlementtarget.zul?targetId=${targetId}"
										class="showTarget">${name}</a></td>
									<td>${zhSettlementPeriod}</td>
									<td>${memo }</td>
									<td><a href="#choose" class="choose" result="${targetId}"
										tt="META_SETTLEMENT">选择</a></td>
								</tr>
							</s:iterator>
						</table>
					</s:elseif>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>



