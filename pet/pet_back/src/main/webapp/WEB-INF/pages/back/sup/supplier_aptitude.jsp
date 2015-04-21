<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商资质</title>
<script type="text/javascript" src="${basePath}/js/base/ajaxupload.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_aptitude_form.js"></script>
</head>
<body>
	<div><form action="${basePath}/sup/aptitude/saveAptitude.do" method="post" id="supplierAptitudeForm" onsubmit="return false">
	<s:hidden name="supplierAptitude.supplierAptitudeId"/>
	<s:hidden name="supplierAptitude.supplierId"/>
	<s:hidden name="supplierAptitude.businessLicenceFile" tt="BUSINESS_LICENCE"/>
	<s:hidden name="supplierAptitude.organizationFile" tt="ORGANIZATION"/>
	<s:hidden name="supplierAptitude.operationPermissionFile" tt="OPERATION_PERMISSION"/>
	<s:hidden name="supplierAptitude.insuranceFile" tt="INSURANCE"/>
	<s:hidden name="supplierAptitude.taxRegistrationFile" tt="TAX_REGISTRATION"/>
		<table width="100%" class="cg_xx" cellspacing="0" cellpadding="0">
			<tr>
				<td>资质审核状态：</td><td><s:radio list="validTypeList" name="supplierAptitude.aptitudeAudit" listKey="code" listValue="cnName" cssClass="required"/></td>
				<td>资质到期时间：</td><td><s:textfield name="supplierAptitude.aptitudeEndTime" cssClass="dateISO required">
					<s:param name="value"><s:date name="supplierAptitude.aptitudeEndTime" format="yyyy-MM-dd"/></s:param>
				</s:textfield></td>
			</tr>
			<tr>
				<td colspan="4">
					<div class="fileList">
					<s:iterator value="supplierAdtitudeTypeList" var="s">
					<s:if test="supplierAptitude.isNotEmptyFile(#s.code)">
						<span tt="<s:property value="code"/>"><s:property value="cnName"/><a href="javascript:void(0)" class="deleteAptitudeFile">删除</a>|</span>
					</s:if>
					</s:iterator>
					</div>
					<div>文件路径：<s:select list="supplierAdtitudeTypeList" listKey="code" listValue="cnName" name="dataType"/><input type="button" class="button" value="上传" id="uploadFile" serverType="SUP_SUPPLIER"/></div>
					<div>文件上传后需要保存才可以生效</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right"><input type="submit" value="保存" class="aditudeSubmit button"/></td>
			</tr>
		</table>	
		</form>
	</div>
</body>
</html>