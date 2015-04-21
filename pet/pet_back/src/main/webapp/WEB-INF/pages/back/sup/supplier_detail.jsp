<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>供应商详细信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/base/back_base.css" />
<style type="text/css">
	   .obj_tabtit{ line-height:27px;}
	   .obj_tab{padding:9px;border:1px solid #ddd; overflow:hidden; zoom:1;margin:5px 0px;}
	   .obj_tab table{float:left; width:49%;}
	   .obj_tab .line{border-left:1px solid #ddd;}
	   .obj_tab td{ text-indent:4px;  line-height:24px;}
	</style>
</head>

<body>
	<h3 class="obj_tabtit">供应商基本信息</h3>
	<table style="width:780px;" class="cg_xx" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="105px">供应商名称:</td><td width="155px"><s:property value="supplier.supplierName"/></td>
			<td width="105px">供应商类型:</td><td width="155px"><s:property value="supplier.zhSupplierType"/></td>
			<td width="105px">所在省市:</td><td width="155px"><s:if test="comProvince!=null"><s:property value="comProvince.provinceName"/></s:if><s:if test="comCity!=null">&nbsp;<s:property value="comCity.cityName"/></s:if></td>
		</tr>
		<tr>
			<td>地址:</td><td><s:property value="supplier.address"/></td>
			<td>供应商电话:</td><td><s:property value="supplier.telephone"/></td>
			<td>传真:</td><td><s:property value="supplier.fax"/></td>
		</tr>
		<tr>
			<td>邮编:</td><td><s:property value="supplier.postcode" /></td>		
			<td>我方负责人:</td><td><s:property value="supplier.bosshead"/></td>
			<td>法定代表人:</td><td><s:property value="supplier.legalPerson"/></td>
		</tr>
		<tr>
			<td>旅行社许可证号:</td><td><s:property value="supplier.travelLicense"/></td>
			<td>父供应商:</td><td><s:property value="supplier.parentSupplier.supplierName"/></td>
			<td>网址:</td><td><s:property value="supplier.webSite"/></td>
		</tr>
		<tr>
			<td>我方结算主体:</td><td><s:property value="supplier.zhCompanyName"/></td>
			<td>预存款预警金额:</td><td><s:property value="supplier.advancedpositsAlertYuan"/></td>
			<td>押金回收时间:</td><td><s:date name="supplier.foregiftsAlert" format="yyyy-MM-dd" /></td>
		</tr>
		<tr>
			<td>预存款余额:</td><td><s:property value="supplier.advancedepositsBalanceYuan"/></td>
			<td>押金余额:</td><td><s:property value="supplier.foregiftsBalanceYuan"/></td>
			<td>担保函金额:</td><td><s:property value="supplier.guaranteeLimitYuan"/></td>
		</tr>
	</table>
	<h3 class="obj_tabtit">联系人:</h3>
	<table class="zhanshi_table" border="0" cellspacing="0" cellpadding="0" style="width:780px;margin:0">
		<tr>
			<th>联系人姓名</th>
			<th>电话</th>
			<th>手机</th>
			<th>性别</th>
			<th>职务</th>
			<th>说明</th>
			<th>Email</th>
			<th>其他联系方式</th>			
		</tr>
		<s:iterator value="contactList">
		<tr>
			<td><s:property value="name"/></td>
			<td><s:property value="telephone"/></td>
			<td><s:property value="mobilephone"/></td>
			<td><s:property value="zhSex"/></td>
			<td><s:property value="title"/></td>
			<td><s:property value="memo"/></td>
			<td><s:property value="email"/></td>
			<td><s:property value="otherContact"/></td>			
		</tr>
		</s:iterator>
	</table>

	<h3 class="obj_tabtit">结算对象:</h3>
	<s:iterator value="settlementTargetList" var="st" status="status">		
		<table style="width:780px;" class="cg_xx">
			<tr>
				<td width="105px">对象名称:</td><td width="280px"><s:property value="name"/></td>
				<td width="105px">开户名称:</td><td width="290px"><s:property value="bankAccountName"/></td>				
			</tr>
			<tr>
				<td>结算周期:</td><td><s:property value="zhSettlementPeriod"/></td>
				<td>开户银行:</td><td><s:property value="bankName"/></td>
			</tr>
			<tr>
				<td>类型:</td><td><s:property value="zhType"/></td>
				<td>开户帐号:</td><td><s:property value="bankAccount"/></td>
			</tr>
			<tr>
				<td>付款方式:</td><td><s:property value="zhPaymentType"/></td>
				<td>支付宝账号:</td><td><s:property value="alipayAccount"/></td>
			</tr>
			<tr>
				<td>联行号:</td><td><s:property value="bankLines"/></td>
				<td>支付宝用户名:</td><td><s:property value="alipayName"/></td>
			</tr>
			<tr>
				<td>备注:</td><td colspan="3"><s:property value="memo"/></td>
			</tr>
			<tr>
				<td>财务联系人:</td><td colspan="3">
				<s:iterator value="contactList">
				<div><s:property value="toHtml()" escape="false"/></div>
				</s:iterator>
				</td>
			</tr>
		</table><br/>		
	</s:iterator>	

	<h3 class="obj_tabtit">传真对象:</h3>
		<s:iterator value="bcertificateTargetList" status="status">
			<table style="width:780px" class="cg_xx">
				<tr>
					<td>对象名称:</td><td colspan="3"><s:property value="name"/></td>
				</tr>
				<tr>
					<td width="105px">B凭证方式:</td><td width="280px"><s:property value="viewBcertificate"/></td>
					<td width="105px">传真号码:</td><td width="290px"><s:property value="faxNo"/></td>
				</tr>
				<tr>
					<td>使用模板:</td><td>系统模板</td>
					<td>传真策略:</td><td><s:property value="zhFaxStrategy"/></td>
				</tr>
				<tr>
					<td>备注:</td><td colspan="3"><s:property value="memo"/></td>
				</tr>
				<tr>
					<td>传真联系人:</td><td colspan="3">
					<s:iterator value="contactList">
					<div><s:property value="toHtml()" escape="false"/></div>
					</s:iterator>
					</td>
				</tr>
			</table><br/>
		</s:iterator>
	
	<h3 class="obj_tabtit">履行对象:</h3>
		<s:iterator value="performTargetList" status="status">
				<table style="width:780px" class="cg_xx">
					<tr>
						<td>对象名称:</td><td colspan="3"><s:property value="name"/></td>
					</tr>
					<tr>
						<td width="105px">履行方式:</td><td width="280px"><s:property value="zhCertificateType"/></td>
						<td width="105px">履行时间:</td><td width="290px"><s:property value="openTime"/>~<s:property value="closeTime"/></td>
					</tr>
					<tr>
						<td>支付信息:</td><td><s:property value="paymentInfo"/></td>
						<td>履行信息:</td><td><s:property value="performInfo"/></td>
					</tr>
					<tr>
						<td>履行联系人:</td><td colspan="3">
						<s:iterator value="contactList">
						<div><s:property value="toHtml()" escape="false"/></div>
						</s:iterator>
						</td>
					</tr>					
				</table><br/>
		</s:iterator>
	
	<h3 class="obj_tabtit">资质合同:</h3>
		<table style="width:780px" class="cg_xx">
			<tr>
				<td width="105px">资质审核:</td>
				<td width="155px"><s:property value="supplierAptitude.zhAptitudeAudit"/></td>
				<td width="105px">到期时间:</td>
				<td width="155px"><s:date name="supplierAptitude.aptitudeEndTime" format="yyyy-MM-dd"/></td>
				<td width="105px">创建时间:</td><td width="155px"><s:date name="supplierAptitude.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td>资质扫描件:</td>	
				<td colspan="5">
					<s:if test="supplierAptitude!=null">
					<s:iterator value="supplierAdtitudeTypeList" var="s">
					<s:if test="supplierAptitude.isNotEmptyFile(#s.code)">
						<a href="${basePath}/contract/downLoad.do?path=<s:property value="supplierAptitude.getFileId(#s.code)"/>" target="_blank"><s:property value="cnName"/></a>
					</s:if>
					</s:iterator>
					</s:if>
				</td>
			</tr>
		</table>
	
	<h3 class="obj_tabtit">供应商得分:</h3>
	<table>
		<tr>
			<td>目前得分</td><td><s:property value="supplier.assessPoints"/></td>
		</tr>
	</table>
</body>
</html>