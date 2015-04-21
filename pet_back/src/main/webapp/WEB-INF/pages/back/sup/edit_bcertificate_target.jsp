<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 凭证对象</title>
</head>
<body>

<form method="post" id="editbcForm" action="${basePath}/sup/target/saveBCertificate.do" onsubmit="return false">
<s:hidden name="supBCertificateTarget.supplierId"/><s:hidden name="supBCertificateTarget.targetId"/>
	<table style="width:100%" class="zhanshi_table" cellspacing="0" cellpadding="0">
		<tr>
			<td>对象名称:</td><td>自动生成</td>
			<td><font color="red">*</font>B凭证方式:</td>
			<td>
 			<s:checkbox name="supBCertificateTarget.faxFlag" id="faxFlag"></s:checkbox>自动传真
			<s:checkbox name="supBCertificateTarget.dimensionFlag" id="dimensionFlag"   ></s:checkbox>二维码
			<s:checkbox name="supBCertificateTarget.supplierFlag" id="supplierFlag"></s:checkbox>供应商系统审核
			</td>
		</tr>
		
		<tr>
			<td><font color="red">*</font>传真号码：</td><td><s:textfield name="supBCertificateTarget.faxNo" maxlength="40" cssClass="required"/></td>
			<td>人工传真：</td><td>
				<s:if test="supBCertificateTarget.manualFaxFlag=='true'">
					<input type="checkbox" name="supBCertificateTarget.manualFaxFlag"  id="manualFaxFlag" Value="true" 	checked="true"/>可人工生成传真
				</s:if>
				<s:else>
					<input type="checkbox" name="supBCertificateTarget.manualFaxFlag"  id="manualFaxFlag" Value="true" 	/>可人工生成传真
				</s:else>
			</td>
		</tr>
		<tr>
			<td>传真策略</td><td><s:select list="faxStrategyList" name="supBCertificateTarget.faxStrategy" listKey="code" listValue="cnName"/></td>
			<td>凭证里是否体现结算价：</td><td> 
			<s:if test="supBCertificateTarget.showSettlePriceFlag==null">
			<s:radio name="supBCertificateTarget.showSettlePriceFlag" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" value="true"></s:radio>
			</s:if><s:else>
			<s:radio name="supBCertificateTarget.showSettlePriceFlag" list="#{'true':'是','false':'否'}"  listKey="key" listValue="value"></s:radio>
			</s:else>
			</td>
		</tr>
		<tr>
			<td>供应商是否使用禁售功能：</td>
			<td> 
				<s:if test="supBCertificateTarget.supplierForbidSaleFalg==null">
					<s:radio name="supBCertificateTarget.supplierForbidSaleFalg" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" value="false"></s:radio>
				</s:if>
				<s:else>
					<s:radio name="supBCertificateTarget.supplierForbidSaleFalg" list="#{'true':'是','false':'否'}"  listKey="key" listValue="value"></s:radio>
				</s:else>
			</td>
			<td>供应商变价/库存修改是否审核：</td>
			<td> 
				<s:if test="supBCertificateTarget.priceStockVerifyFalg==null">
					<s:radio name="supBCertificateTarget.priceStockVerifyFalg" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" value="true"></s:radio>
				</s:if>
				<s:else>
					<s:radio name="supBCertificateTarget.priceStockVerifyFalg" list="#{'true':'是','false':'否'}"  listKey="key" listValue="value"></s:radio>
				</s:else>
			</td>
		</tr>	
		<tr>
			<td>供应商修改信息免审核部分：</td>
			<td colspan="3">
				<input type="checkbox"  name="checkAll" id="cfg-check-all" class="cfgChange"/> <label>全选</label><br/>
				<s:iterator value="#request.cfgList" var="cfgItem" >
					<s:set var="ck" value="false"></s:set>
					<s:iterator value="supBCertificateTarget.ebkProdAuditCfgList" var="item">
						<s:if test="#item==#cfgItem.code">
							<s:set var="ck" value="true"></s:set>
						</s:if>
					</s:iterator>
					<span style="width:150px;"><input class="cfgChange" type="checkbox" <s:if test="#ck==true">checked="checked"</s:if> value="<s:property value="code"/>" name="cfgItem" /> <label><s:property value="cnName"/></label></span>
				</s:iterator>
			</td>
		</tr>	
		<tr>
			<td>备注</td>
			<td colspan="3"><s:textarea name="supBCertificateTarget.memo" cols="40" rows="2"/></td>
		</tr>	
		<tr>
			<td>传真联系人：<s:hidden name="contactListId"/></td>
			<td colspan="3">
			<div id="contact_show_pos">
				<s:iterator value="contactList">
					<div contactId="<s:property value="contactId"/>"><span><s:property value="toHtml()" escape="false"/></span><a href='javascript:void(0)' class='deleteRelation'>删除</a></div>
				</s:iterator>
			</div>
			<input type="button" value="绑定联系人" class="bindContactBtn button"/>
			</td>
		</tr>
	</table>
	<input style="display: none;" type="radio" name="supBCertificateTarget.faxTemplate" value="SYSTEM" checked="checked"/>
	<br/>
	<div><input type="submit" value="保存" class="bcertificateSubmit button"/></div>
</form>

<!-- /产品审核配置 -->
<script type="text/javascript">
	$("#editbcForm #supplierFlag,#faxFlag").click(function(){
		//alert($("#supplierFlag").size());
		var checked = $(this).attr("checked");
		$("#editbcForm #faxFlag").removeAttr("checked");
		$("#editbcForm #supplierFlag").removeAttr("checked");
		$(this).attr("checked",checked);
	});
	$("#supplierFlag").click(function(){
		//alert($(this).attr("checked"));
		if($(this).attr("checked")){
			$("#manualFaxFlag").attr("checked","checked");
		}else{
			$("#manualFaxFlag").attr("checked","");
		}
	});
</script>
</body>
</html>