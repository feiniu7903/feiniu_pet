<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>分销管理_分销商管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">

	//显示弹出层
	function addDetailDiv(){
		var url = "${basePath}/distribution/searchDistributor.do";
		
		$("<iframe frameborder='0' id='addDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "新增分销商基本信息",
	        position: 'top',
	        width: 720
		}).width(700).height(350).attr("src",url);
	}
	
	function editDetailDiv(id) {
		var url = "${basePath}/distribution/searchDistributor.do?distributorInfoId=" + id;
		
		$("<iframe frameborder='0' id='editDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "修改分销商基本信息",
	        position: 'top',
	        width: 720
		}).width(700).height(350).attr("src",url);
	}
	
	function manageIpDetailDiv(id){
		var url = "${basePath}/distribution/searchListDistributorIP.do?distributorInfoId=" +id;
		$("<iframe frameborder='0' id='manageIpDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "分销商IP管理",
	        position: 'top',
	        width: 520
		}).width(500).height(400).attr("src",url);
	}
	
	function manageDistributionDetailDiv(id){
		var url = "${basePath}/distribution/searchDistributionProdCategory.do?distributorInfoId=" +id;
		$("<iframe frameborder='0' id='manageDistributionWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "修改分销商返佣点设置",
	        position: 'top',
	        width: 820
		}).width(800).height(400).attr("src",url);
		
	}
	
	function checkAndSubmitDistributorCondition(){
		var value = $.trim($("#form1 #distributorInfoId").val());
		$("#form1 #distributorInfoId").val(value);
		if(value != ""){
		if(!(/^\+?[1-9][0-9]*$/.test(value))){
			$("#form1 #distributorInfoId").focus();
			alert("分销商ID只能为正整数");
			return false;
		}
		}
		document.form1.submit();
	}

</script>
</head>
<body>
<form name='form1' id="form1" method='post' action='${basePath}/distribution/searchListDistributor.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">分销商名称：</td>
			<td ><input type="text" id="distributorName" name="distributorName"  value="${distributorName}" class="newtext1"/>
			</td>
			<td colspan="2"/>
			<td class="p_label">分销商ID：</td>
			<td width="12%">
				<input type="text" id="distributorInfoId" name="distributorInfoId"  value="${distributorInfoId}"/>
			</td>
			<td colspan="2"></td>
			<td colspan="2"><input type="button" value="查 询" onclick="checkAndSubmitDistributorCondition()"
				class="right-button08 btn btn-small" id="btnDistributorQuery" /></td>
			<td>
			<td colspan="2">
			<a href="javascript:addDetailDiv();" class="right-button08 btn btn-small" >新增</a>
			</td>
		</tr>
	</table>
</form>
<table class="p_table table_center Padding5">
	<tbody>
		<tr >
			<th >分销商ID</th>
			<th width="15%">分销商名称</th>
			<th >分销商编号</th>
			<th >分销商渠道</th>
			<th >是否允许用户注册</th>
			<th >是否推送更新信息</th>	
			<th >是否系统新增产品</th>		
			<th width="20%">操作</th>
		</tr>
		<s:iterator id="distributor" var="distributorItem" value="distributorPage.items">
			<tr>
				<td >${distributorItem.distributorInfoId}</td>
				<td>${distributorItem.distributorName}</td>
				<td>${distributorItem.distributorCode }</td>
				<td>${distributorItem.channelCode }</td>
				<td><s:if test="#distributorItem.registerUser">是</s:if><s:else>否</s:else></td>
				<td><s:if test="#distributorItem.pushUpdate">是</s:if><s:else>否</s:else></td>
				<td><s:if test="#distributorItem.addNewprod">是</s:if><s:else>否</s:else></td>
				<td>
					<a href="javascript:editDetailDiv('${distributorItem.distributorInfoId}');">修改</a> &nbsp;&nbsp;
					<a href="javascript:manageIpDetailDiv('${distributorItem.distributorInfoId}');">IP管理</a> &nbsp;&nbsp;
					<a href="javascript:manageDistributionDetailDiv('${distributorItem.distributorInfoId}');">分销产品管理</a> 
				</td>
			</tr>
		</s:iterator> 
		
		<tr>
			<td colspan="3" align="left">共有<s:property
					value="distributorPage.totalResultSize" />个
			</td>
			<td colspan="7" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(distributorPage)"/>
			</td>
		</tr>
		
	</tbody>
</table>
</body>
</html>

