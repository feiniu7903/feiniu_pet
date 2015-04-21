<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:iterator value="waitAuditOrderList" var="order">
	<s:iterator value="allOrdOrderItemMetas" var="ele">
	<s:if test='taken!="TAKEN" && orderItemMetaId !=excludeOrdMetaId'>
		<tr <s:if test="hasSameSupplierMetas()">bgcolor="#ffffaa"</s:if><s:else>bgcolor="#ffffff"  id="wait_audit_tr_${orderItemMetaId}"</s:else>>
			<td>
				<s:if test="#order.hasApproveAble()">
					<input type="checkbox" name="checkAuditName" value="${orderItemMetaId}" 
						<s:if test="hasSameSupplierMetas()">
							checked class="findSameSupplierMetasResult"
						</s:if>
						<s:else> class="findSameSupplierMetas"</s:else> 
						supplierId="${supplierId}"></input>
				</s:if>	
				<s:else>
					<input type="checkbox" name="checkAuditName" disabled="disabled"/>
				</s:else>		
			</td>
			<td height="30">
				<a href="javascript:openWin('/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId=${orderId }',700,700)">${orderId}</a>
				<s:if test="#order.hasApproveAble()==false">
					<div style="color:red">必须先支付才可以领单</div>
				</s:if>	
			</td>
			<td>
				<span class="perm"><s:property value="takenOperator"/></span>												
			</td>
			<td height="30">
				${orderItemMetaId}
			</td>
			<td>

				<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${metaProductId}&metaBranchId=${metaBranchId}&productType=${productType }',700,700)">${ele.productName }</a>
			</td>
			<td>
				<s:if test="productType=='HOTEL'">
					${hotelQuantity }
				</s:if>
				<s:else>
					<s:property value="productQuantity*quantity"/>
				</s:else>
			</td>
			<td>
				${userName }
			</td>
			<td>
				${contact.name }
			</td>
			<td>
				<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>
				<s:date name="dealTime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>
				<s:date name="visitTime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>
				<s:if test="stockReduced">
					已减库存
				</s:if>
				<s:else>
					未减库存
				</s:else>
			</td>
			<td>
				${zhResourceStatus }
			</td>
			<td>
				${zhPaymentStatus}
			</td>
		</tr>
		</s:if>
	</s:iterator>
</s:iterator>