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
<title>分销后台-分销商</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>

<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function() {
		
	});
	function checkAndSubmitDistPod(){
		if($("#submitDistributionProdForm [name='distributorInfoIds']:checked").size() == 0) {
			alert("请至少选择一个分销商");
			return false;
		}
		if($("#submitDistributionProdForm #memo").size() > 0 && $.trim($("#submitDistributionProdForm #memo").val()) == ""){
			$("#submitDistributionProdForm #memo").focus();
			alert("请输入理由");
			return false;
		}
		$.post($("#submitDistributionProdForm").attr("action"),
				$("#submitDistributionProdForm").serialize(),
				function(dt){
					var data=dt;
					if(data.success){
						alert("操作成功");
						parent.location.reload(true);
					}else{
						alert(data.msg);
					}
				}
		);
	}

	function checkAll(ele, nextName){
		var check = $(ele).attr("checked");
		$("[name='"+nextName+"']").each(function(){
			if(check) {
				$(this).attr("checked", "checked")
			} else {
				$(this).removeAttr("checked");
			}
		});
	}
	
	function showDetailDiv(productBranchId, distributorInfoId,distributorName,profit,rakeBackRate) {
		if (profit=='') {
			alert('该产品已过期');
			return;
		}
		var url = "${basePath}/distribution/distributionProd/rakeBackDetail.do?productBranchId=" + 
				productBranchId + "&distributorInfoId=" + distributorInfoId + 
				"&distributorName=" + encodeURI(encodeURI(distributorName)) + 
				"&profit=" + profit + "&rakeBackRate=" + rakeBackRate;
		
		$("<iframe frameborder='0' id='showDetailWin2'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : '设置返佣点',
	        position: 'top',
	        width: 400
		}).width(360).height(250).attr("src",url);
	}
	function showCommentsCashbackDiv(productBranchId, distributorInfoId,distributorName){
		var url = "${basePath}/distribution/distributionProd/cassBackDetail.do?productBranchId=" + productBranchId + "&distributorInfoId=" + distributorInfoId + "&distributorName=" + encodeURI(encodeURI(distributorName));

		$("<iframe frameborder='0' id='showDetailWin2'></iframe>").dialog({
			autoOpen: true, 
		    modal: true,
		    title : '设置点评返现值',
		    position: 'top',
		    width: 400
		}).width(360).height(250).attr("src",url);
	}
	function closeDetailDiv(){
		$("#showDetailWin2").dialog("close");
		window.location = window.location;
	}
</script>
</head>
<body>
<div>
<form id='submitDistributionProdForm' method='post' action='${basePath}/distribution/distributionProd/saveDistributorProduct.do'>
<input type="hidden" name="operateType" value="${operateType }"/>
<input type="hidden" name="productBranchId" value="${productBranchId }"/>
<table class="p_table">
	<tr>
		<th width="10%"><input type="checkbox" id="checkAllDist" onclick="checkAll(this, 'distributorInfoIds');"/></th>
		<th>分销商</th>
		<!-- <th>利润率</th> -->
		<th>分销返佣点</th>
		<th>利润必须大于返佣</th>
		<s:if test="operateType == 'WHITE_LIST'">
		<th>点评返现值</th>
		<th>操作</th>
		</s:if>
	</tr>
	<s:iterator value="distributorList" var="dist">
	<tr>
		<td>
			<s:if test="operateType != 'RELEASE_LIST'">
				<input type="checkbox" name="distributorInfoIds" value="${dist.distributorInfoId}" <s:if test="#dist.checked=='true'">checked onclick='return false;'</s:if>/>
			</s:if>
			<s:else>
				<input type="checkbox" name="distributorInfoIds" value="${dist.distributorInfoId}" <s:if test="#dist.checked=='true'">checked</s:if>/>
			</s:else>
		</td>
		<td>${dist.distributorName}</td>
		<%-- <td>
			<s:if test="profit!=null">
				${profit}%
			</s:if>
		</td> --%>
		<td><s:if test="#dist.rakeBackRate != null">${dist.rakeBackRate}%</s:if></td>
		<td><s:if test="#dist.rateVolid=='true'">是</s:if><s:else>否</s:else></td>
		<s:if test="operateType == 'WHITE_LIST'">
		<s:if test="#dist.distributorCode == 'QUNA_TICKET'">
			<td>${distributionProdRakebackId}</td>			
		</s:if>
		<s:else>
			<td></td>	
		</s:else>
		<td>
		<a href="javascript:void(0);" onclick="showDetailDiv('${productBranchId}','${dist.distributorInfoId}','${dist.distributorName}','${profit}','${dist.rakeBackRate}');">设置返佣点</a>
			<s:if test="#dist.distributorCode == 'QUNA_TICKET'">
				<a href="javascript:void(0);" onclick="showCommentsCashbackDiv('${productBranchId}','${dist.distributorInfoId}','${dist.distributorName}');">设置点评返现值</a>			
			</s:if>
		</td>
		</s:if>
	</tr>
    </s:iterator>
    <s:if test="operateType != 'WHITE_LIST'">
    	<tr>
    	<td >
			理由
		</td>
		<td colspan="4">
			<textarea rows="5" cols="300" name="memo" id="memo"></textarea>
		</td>
    	</tr>
    </s:if>
</table>
<p class="tc mt20">
	<input type="button" value="保存" id="submitDistributionProd_btn" onclick="checkAndSubmitDistPod();" class="btn btn-small w6" />
</p>
</form>
</div>
</body>
</html>

