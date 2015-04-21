<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	<div class="row2">
		<div class="rowpro" style="border: none">
			<dl>
				<dd>
					<s:if
						test="supplierPerformTargetList != null && supplierPerformTargetList.size > 0">
						<table width="96%" border="1" cellspacing="0" cellpadding="0" class="newTable" >
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>履行方式</td>
								<td>履行信息</td>
								<td>支付信息</td>
								<td>操作</td>
							</tr>
							<s:iterator value="supplierPerformTargetList">
								<tr id="SUP_PERFORM_TARGET_${targetId}">
									<td>${targetId}<input name='ebkProdProduct.ebkProdTargets[1].targetId' value='${targetId}' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[1].targetType' value='SUP_PERFORM_TARGET' type='hidden'></td>
									<td>${name}</td>
									<td>${zhCertificateType }</td>
									<td>${performInfo}</td>
									<td>${paymentInfo}</td>
									<td><a href="#choose" class="choose" tt="SUP_PERFORM_TARGET"
										result="${targetId}--${name}--${zhCertificateType }--${performInfo}--${paymentInfo}">选择</a></td>
								</tr>
							</s:iterator>
						</table>
					</s:if>
					<s:elseif
						test="supplierBCertificateTargetList != null && supplierBCertificateTargetList.size > 0">
						<table width="96%" border="1" cellspacing="0" cellpadding="0"
							 class="newTable">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>凭证</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
							<s:iterator value="supplierBCertificateTargetList">
								<tr id="SUP_B_CERTIFICATE_TARGET_${targetId}">
									<td>${targetId}<input name='ebkProdProduct.ebkProdTargets[1].targetId' value='${targetId}' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[1].targetType' value='SUP_B_CERTIFICATE_TARGET' type='hidden'></td>
									<td>${name}</td>
									<td>${viewBcertificate}</td>
									<td>${memo }</td>
									<td><a href="#choose" class="choose"
										tt="SUP_B_CERTIFICATE_TARGET" result="${targetId}--${name}--${viewBcertificate}--${memo }">选择</a></td>
								</tr>
							</s:iterator>
						</table>
					</s:elseif>
					<s:elseif
						test="supplierSettlementTargetList != null && supplierSettlementTargetList.size > 0">
						<table width="96%" border="1" cellspacing="0" cellpadding="0"
							 class="newTable">
							<tr class="newTableTit">
								<td>编号</td>
								<td>名称</td>
								<td>结算周期</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
							<s:iterator value="supplierSettlementTargetList">
								<tr id="SUP_SETTLEMENT_TARGET_${targetId}">
									<td >${targetId}<input name='ebkProdProduct.ebkProdTargets[1].targetId' value='${targetId}' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[1].targetType' value='SUP_SETTLEMENT_TARGET' type='hidden'></td>
									<td>${name}</td>
									<td>${zhSettlementPeriod}</td>
									<td>${memo }</td>
									<td><a href="#choose" class="choose" result="${targetId}--${name}--${zhSettlementPeriod}--${memo }"
										tt=SUP_SETTLEMENT_TARGET>选择</a></td>
								</tr>
							</s:iterator>
						</table>
					</s:elseif>
				</dd>
			</dl>
		</div>
	</div>



