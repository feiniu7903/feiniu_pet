<%@page import="com.lvmama.comm.bee.po.prod.ProductModelProperty"%>
<%@page import="com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
	});
</script>
<table class="newfont06" border="0"  cellpadding="0"  >
	<input type="hidden" name="ebkProdProductId" value="ebkProdProduct.ebkProdProductId"/>
	<tr>
		<td width="130">供应商产品名称：</td>
		<td width="190">
			 <input type="text" name="" maxlength="10" style="margin:0" readonly="readonly" value="${ebkProdProduct.metaName}"/>
			 <s:if test="compareEbkProductBase.containsKey('metaName')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductBase.metaName}">审</span>
			 </s:if>
		</td>
		<td style="width: 150px">驴妈妈产品名称：</td>
		<td>
			<input type="text" name="ebkHousePrice.housePriceId" maxlength="10" style="margin:0" readonly="readonly" value="${ebkProdProduct.prodName}"/>
			<s:if test="compareEbkProductBase.containsKey('prodName')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductBase.prodName}">审</span>
			 </s:if>
		</td>
	</tr>
	<tr>
		<td>产品类型：</td>
		<td>
			 <input type="text" name="ebkHousePrice.submitUser" maxlength="25" style="margin:0" readonly="readonly" value="${ebkProdProduct.subProductTypeZh}"/>
			 <s:if test="compareEbkProductBase.containsKey('subProductType')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductBase.subProductTypeZh}">审</span>
			 </s:if>
		</td>
		<td style="width: 100px">一句话推荐：</td>
		<td>
			 <input type="text" name="ebkHousePrice.confirmUser" maxlength="100" style="margin:0" style="width: 200px" readonly="readonly" value="${ebkProdProduct.recommend}"/>
			 <s:if test="compareEbkProductBase.containsKey('recommend')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductBase.recommend}">审</span>
			 </s:if>
		</td>
	</tr>
	<tr>
		<td>出发地：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="${fromPlaceName}"/>
			 <s:if test="compareEbkProductBase.containsKey('fromPlaceId')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${fromPlaceNameOld}">审</span>
			 </s:if>
		</td>
		<td>目的地：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="${toPlaceName}"/>
			 <s:if test="compareEbkProductBase.containsKey('toPlaceId')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${toPlaceNameOld}">审</span>
			 </s:if>
		</td>
	</tr>
	<tr>
		<td>行程内包含景点：</td>
		<td colspan="3">
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="width: 500px;margin:0" readonly="readonly" value="${tripScenery}"/>
			 <s:if test="compareEbkProductBase.containsKey('ebkProdPlace')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${tripSceneryOld}">审</span>
			 </s:if>
		</td>
	</tr>
	<s:if test="'ABROAD_PROXY'==ebkProdProduct.productType">
		<tr>
			<td>最少成团人数：</td>
			<td>
				 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.initialNum"/>"/>
				 <s:if test="compareEbkProductBase.containsKey('initialNum')">
				 	<span class="tip_text" tip-title="老数据:" tip-content="<s:property value="compareEbkProductBase['initialNum']"/>">审</span>
				 </s:if>
			</td>
			<td>与游客签署合同范本：</td>
			<td>
				 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.econtractTemplateCh"/>"/>
				 <s:if test="compareEbkProductBase.containsKey('econtractTemplateCh')">
				 	<span class="tip_text" tip-title="老数据:" tip-content="<s:property value="compareEbkProductBase['econtractTemplateCh']"/>">审</span>
				 </s:if>
			</td>
	</tr>
	<tr>
		<td>区域划分：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.regionNameCh"/>"/>
			 <s:if test="compareEbkProductBase.containsKey('regionNameCh')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="<s:property value="compareEbkProductBase['regionNameCh']"/>">审</span>
			 </s:if>
		</td>
		<td>送签国家：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.country"/>"/>
			 <s:if test="compareEbkProductBase.containsKey('country')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="<s:property value="compareEbkProductBase['country']"/>">审</span>
			 </s:if>
		</td>
	</tr>
	<tr>
		<td>送签城市：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.cityCh"/>"/>
			 <s:if test="compareEbkProductBase.containsKey('cityCh')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="<s:property value="compareEbkProductBase['cityCh']"/>">审</span>
			 </s:if>
		</td>
		<td>送签类型：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.visaTypeCh"/>"/>
			 <s:if test="compareEbkProductBase.containsKey('visaTypeCh')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="<s:property value="compareEbkProductBase['visaTypeCh']"/>">审</span>
			 </s:if>
		</td>
	</tr>
	</s:if>
	<tr>
		<td>是否多行程：</td>
		<td>
			 <input type="text" name="ebkHousePrice.subject" maxlength="25" style="margin:0" readonly="readonly" value="<s:property value="ebkProdProduct.zhMultiJourney"/>"/>
		</td>
	</tr>
	<s:iterator value="#request.modelPropertyList" var="model">
		<tr>
			<td><s:property value="firstModelName" />&gt;<s:property value="secondModelName" />：</td>
			<td colspan="3">
			<%
			List<EbkProdModelProperty> productModelPropertyList=(List<EbkProdModelProperty>)request.getAttribute("ebkProdModelPropertys");
			ProductModelProperty productModelProperty=(ProductModelProperty)request.getAttribute("model");
			String str="";
			for(EbkProdModelProperty pmp :productModelPropertyList){
				if(Long.valueOf(pmp.getEbkPropertyType())==productModelProperty.getSecondModelId()){
					str+=pmp.getModelPropertyName()+";";
				}
			}
			out.print("<input type='text'  maxlength='25' style='width: 500px;margin:0' readonly='readonly' value='"+str+"'/>");
			%>
			<%
				Map<String,Object> compareEbkProductBase=(Map<String,Object>)request.getAttribute("compareEbkProductBase");
				if(compareEbkProductBase.containsKey("MODEL_PROPERTY_"+productModelProperty.getSecondModelId())){
					out.print("<span class='tip_text' tip-title='老数据:' tip-content='"+compareEbkProductBase.get("MODEL_PROPERTY_"+productModelProperty.getSecondModelId())+"'>审</span>");
				}
			%>
			</td>
		</tr>
	</s:iterator>
	
	<tr>
		<td>
		<div style="color: red;"><b>结算凭证信息：</b></div>
		</td>
	</tr>
	<tr>
		<td>履行方式：</td>
		<td colspan="3">
			<table class="newfont06 ebk_tab_small_table" border="1" cellpadding="0" style="width: 500px;">
				<tr>
					<td>编号</td>
					<td>名称</td>
					<td>履行方式</td>
					<td>履行信息</td>
					<td>支付信息</td>
					<s:if test="compareEbkProductBase.containsKey('supPerformTarget')">
						<span class="tip_text" tip-title="老数据:" tip-content="${supPerformTargetOld.targetId}；${supPerformTargetOld.name}；${supPerformTargetOld.zhCertificateType}；${supPerformTargetOld.performInfo}；${supPerformTargetOld.paymentInfo}" style="float: right;">审</span>
					</s:if>
				</tr>
				<tr>
					<td>${supPerformTarget.targetId}</td>
					<td>${supPerformTarget.name}</td>
					<td>${supPerformTarget.zhCertificateType}</td>
					<td>${supPerformTarget.performInfo}</td>
					<td>${supPerformTarget.paymentInfo}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>订单确认方式：</td>
		<td colspan="3">
			<table class="newfont06 ebk_tab_small_table" border="1"  cellpadding="0" style="width: 500px">
				<tr>
					<td>编号</td>
					<td>名称</td>
					<td>凭证</td>
					<td>备注</td>
					<s:if test="compareEbkProductBase.containsKey('supBCertificateTarget')">
						<span class="tip_text" tip-title="老数据:" tip-content="${supBCertificateTargetOld.targetId}；${supBCertificateTargetOld.name}；${supBCertificateTargetOld.viewBcertificate}；${supBCertificateTargetOld.memo}}" style="float: right;">审</span>
					</s:if>
				</tr>
				<tr>
					<td>${supBCertificateTarget.targetId}</td>
					<td>${supBCertificateTarget.name}</td>
					<td>${supBCertificateTarget.viewBcertificate}</td>
					<td>${supBCertificateTarget.memo}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>结算信息：</td>
		<td colspan="3">
			<table class="newfont06 ebk_tab_small_table" border="1"  cellpadding="0" style="width: 500px">
				<tr>
					<td>编号</td>
					<td>名称</td>
					<td>结算周期</td>
					<td>备注</td>
					<s:if test="compareEbkProductBase.containsKey('supSettlementTarget')">
						<span class="tip_text" tip-title="老数据:" tip-content="${supSettlementTargetOld.targetId}；${supSettlementTargetOld.name}；${supSettlementTargetOld.zhSettlementPeriod}；${supSettlementTargetOld.memo}}" style="float: right;">审</span>
					</s:if>
				</tr>
				<tr>
					<td>${supSettlementTarget.targetId}</td>
					<td>${supSettlementTarget.name}</td>
					<td>${supSettlementTarget.zhSettlementPeriod}</td>
					<td>${supSettlementTarget.memo}</td>
				</tr>
			</table>
		</td>
	</tr>
	
	
	
	<tr>
		<td>
		<div style="color: red;"><b>预订控制信息：</b></div>
		</td>
	</tr>
	<tr>
		<td>驴妈妈产品联系人：</td>
		<td>
			 <input type="text" name="ebkHousePrice.housePriceId" maxlength="10" style="margin:0" readonly="readonly" value="${permUserManager.realName}"/>
			 <s:if test="compareEbkProductBase.containsKey('managerId')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${permUserManagerOld.realName}">审</span>
			 </s:if>
		</td>
		<td>驴妈妈联系电话：</td>
		<td>
			<input type="text" name="ebkHousePrice.housePriceId" maxlength="10" style="margin:0" readonly="readonly" value="${permUserManager.mobile}"/>
			<s:if test="compareEbkProductBase.containsKey('managerId')">
				<span class="tip_text" tip-title="老数据:" tip-content="${permUserManagerOld.mobile}">审</span>
			</s:if>
		</td>
	</tr>
	<tr>
		<td>所属公司：</td>
		<td>
			 <input type="text" name="ebkHousePrice.housePriceId" maxlength="10" style="margin:0" readonly="readonly" value="${ebkProdProduct.orgIdZh}"/>
			 <s:if test="compareEbkProductBase.containsKey('orgId')">
				<span class="tip_text" tip-title="老数据:" tip-content="${orgNameOld}">审</span>
			</s:if>
		</td>
	</tr>
</table>