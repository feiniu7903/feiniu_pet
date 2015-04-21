<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>分销后台-分销产品</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>

<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	var typeArray = new Array();
	<s:iterator value="productTypeList" var="sp">typeArray.push("${sp}");</s:iterator>
	$(function() {
		for(var t = 0; t < typeArray.length; t++){
			$("#querydistributionProdForm [value='"+typeArray[t]+"']").attr("checked", "checked");
		}
		$("#querydistributionProdForm [name='filialeName']").val("${filialeName}");
		$("#querydistributionProdForm [name='distributorInfoId']").val("${distributorInfoId}");
	});
	//显示弹出层
	function showDetailDiv(id, type) {
		var url = "${basePath}/distribution/distributionProd/distributorProductDetail.do?productBranchId=" + id + "&operateType=" + type;
		var titleName = "分销产品";
		if(type == "WHITE_LIST") {
			titleName = "可分销设置";
		} else if(type == "BLACK_LIST") {
			titleName = "加入黑名单";
		} else if(type == "RELEASE_LIST") {
			titleName = "解除黑名单";
		}
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : titleName,
	        position: 'top',
	        width: 600
		}).width(580).height(450).attr("src",url);
	}
	//显示弹出层
	function showSetDiv(id, type) {
		var url = "${basePath}/distribution/distributionProd/distributorSet.do?productBranchId=" + id + "&operateType=" + type;
		var titleName = "分销产品";
		if(type == "WHITE_LIST") {
			titleName = "可分销设置";
		} else if(type == "BLACK_LIST") {
			titleName = "加入黑名单";
		} else if(type == "RELEASE_LIST") {
			titleName = "解除黑名单";
		}
		$("<iframe frameborder='0' id='showSetWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : titleName,
	        position: 'top',
	        width: 600
		}).width(580).height(450).attr("src",url);
	}
	function closePopupWin() {
		$("#showDetailWin").dialog("close");
		$("#showDetailWin").remove();
	}
	function checkDPSearchForm() {
		$("#querydistributionProdForm #productName").val($.trim($("#querydistributionProdForm #productName").val()));
		$("#querydistributionProdForm #productBranchId").val($.trim($("#querydistributionProdForm #productBranchId").val()));
		$("#querydistributionProdForm #productId").val($.trim($("#querydistributionProdForm #productId").val()));
		
		 var reg = new RegExp("^[0-9]*$");
		 if(!(reg.test(
				 $("#querydistributionProdForm #productBranchId").val()
				 ))){
			 alert("销售类别ID请填写数字！");
	        return false;
		 }
		 if(!(reg.test(
				 $("#querydistributionProdForm #productId").val()
				 ))){
			 alert("销售产品ID请填写数字！");
	        return false;
		 }
		 return true;
	}
</script>


</head>
<body style="height: auto;">
<form id='querydistributionProdForm' method='get' action='${basePath}/distribution/distributionProd/distributionProductList.do'onsubmit="return checkDPSearchForm();">
<input type="hidden" name="operateType" value="${operateType }"/>
<table class="p_table form-inline">
	<tr>
		<td class="p_label">销售产品名称：</td>
		<td>
			<input type="text" id="productName" name="productName" value="${productName}"/>
		</td>
		<td class="p_label">销售类别ID：</td>
		<td>
			<input type="text" id="productBranchId" name="productBranchId" value="${productBranchId}"/>
		</td>
		<td class="p_label">分公司：</td>
		<td>
			<select name="filialeName">
             <option value=""></option>
             <s:iterator value="filialeList" var="fil">
	             <option value="${fil.code}">${fil.cnName }</option>
             </s:iterator>
            </select>
		</td>
	</tr>
	<tr>
		<td class="p_label">销售产品类型：</td>
		<td colspan="1">
			<input type="checkbox" id="productType1" name="productTypeList" value="TICKET"/>
			门票
			<input type="checkbox" id="productType2" name="productTypeList" value="HOTEL"/>
			酒店
			<input type="checkbox" id="productType3" name="productTypeList" value="ROUTE"/>
			线路
			<input type="checkbox" id="productType4" name="productTypeList" value="OTHER"/>
			其它
		</td>
		<td class="p_label">分销商：</td>
		<td>
			<select name="distributorInfoId">
             <option value=""></option>
             <s:iterator value="distributorList" var="dist">
	             <option value="${dist.distributorInfoId}">${dist.distributorName }</option>
             </s:iterator>
            </select>
		</td>
		<td colspan="2">
			<button onclick="" type="submit" class="btn btn-small w5">查询</button>&#12288;
		</td>
	</tr>
	<tr>
		<td class="p_label">产品ID：</td>
		<td>
			<input type="text" id="productId" name="productId" value="${productId}"/>
		</td>
		<td colspan="4">
		</td>
	</tr>
</table>
</form>
<table class="p_table table_center Padding5">
	<tr>
		<th width="40px">类别ID</th>
		<th width="40px">产品ID</th>
		<th width="15%">销售产品名称</th>
		<th width="60px">产品类型</th>
		<th width="80px">上/下线状态</th>
		<th width="21%">分销商</th>
		<th width="21%">黑名单</th>
		<th>操作</th>
	</tr>
	<s:iterator value="productBranchPage.items" var="pb">
	<tr>
		<td>${pb.prodBranchId }</td>
		<td>${pb.prodProduct.productId }</td>
		<td>${pb.prodProduct.productName }(${pb.branchName })</td>
		<td>${pb.prodProduct.zhProductType }</td>
		<td>${pb.zhOnline }</td>
		<td>${pb.distributor }</td>
		<td>${pb.blackDistrbuor }</td>
		<td>
			<s:if test="operateType=='BLACK_LIST'">
				<a href="javascript:void(0);" onclick="showDetailDiv('${pb.prodBranchId}','RELEASE_LIST');">解除黑名单</a>
			</s:if>
			<s:else>
				<%-- <a href="javascript:void(0);" onclick="showDetailDiv('${pb.prodBranchId}','WHITE_LIST');">分销</a> --%>
				<a href="javascript:void(0);" onclick="showSetDiv('${pb.prodBranchId}','BLACK_LIST');">加入黑名单</a>
				<a href="javascript:void(0);" onclick="showSetDiv('${pb.prodBranchId}','WHITE_LIST');">分销设置</a>
				<a href="javascript:void(0);" onclick="showSetDiv('${pb.prodBranchId}','RELEASE_LIST');">解除黑名单</a>
			</s:else>
			<a param="{'parentType':'DISTRIBUTOR_PRODUCT','parentId':${pb.prodBranchId}}" class="showLogDialog" href="#log">日志</a>
		</td>
	</tr>
	</s:iterator>
	<tr>
		<td colspan="2" align="right">总条数：${productBranchPage.totalResultSize}</td>
		<td colspan="6" align="right" style="text-align:right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(productBranchPage)"/></td>
	</tr>
</table>
</body>
</html>

