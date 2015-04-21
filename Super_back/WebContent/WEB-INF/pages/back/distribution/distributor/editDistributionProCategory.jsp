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
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script>
	
	function checkAndSubmitDistributionProdCategory(){
		if($.trim($("#form1 #discountRate").val()) == ""){
			$("#form1 #discountRate").focus();
			alert("分销返佣点不能为空");
			return false;
		}
		if(!(/^([0-9]{1}\d*)(\.(\d){1,2})?$/.test($.trim($("#form1 #discountRate").val())))){
			$("#form1 #discountRate").focus();
			alert("分销返佣点只能为整数数字或2位小数");
			return false;
		}
		
		if($.trim($("#form1 #discountRate").val())>=100){
			$("#form1 #discountRate").focus();
			alert("分销返佣点只能为小于100的整数");
			return false;
		}
		
		if($("#form1 #productType").val() == "TICKET" && $("#form1 #subProductType").val() == "" && $("#form1 #payOnline").val() == ""){
			alert("门票的支付方式不能为空");
			return false;
		}
		
		if($("#form1 #productType").val() == "ROUTE" && $("#form1 #subProductType").val() == "" && $("#form1 #payOnline").val() == ""){
			alert("线路的子类型不能为空");
			return false;
		}
		
		$.post($("#form1").attr("action"),
				$("#form1").serialize(),
				function(dt){
					var data=dt;
					if(data.success){
						alert("操作成功");
						location.reload(true);
					}else{
						alert(data.msg);
					}
				}
		);
	}
	
	function deleteDistributionProdCategory(id){
		$.post(
				"${basePath }/distribution/deleteDistributionProdCategory.do",
				{
					distributionProductCategoryId: id
				},
				function(data){
					var resultData = eval("(" + data + ")");
					if(resultData.result == '1'){
						alert("删除成功！");
						//删除相关的html代码
						$('#no'+id).remove();
					}else{
						alert("删除失败！");
					}		
				}
			);
	}
	
	function onchangeShow(option){
		var subProductTypeHtml = '';
		var payOnlineTdHtml = '';
		if(option == 'TICKET'){
			subProductTypeHtml = 
			"<select id='subProductType' style='width:100px;' name='distributionProductCategory.subProductType'>"+
			"<option value=''></option>"+
			"</select>";
			$('#subProductTypeTd').html(subProductTypeHtml);
			
			payOnlineTdHtml =
			"<select id='payOnline' style='width:80px;' name='distributionProductCategory.payOnline'>"+
			"<option  selected='selected' value=''></option>"+
			"<option value='true'>线上支付</option>"+
			"<option value='false'>景区支付</option>"+
			"</select>";
			$('#payOnlineTd').html(payOnlineTdHtml);
		}else{
			subProductTypeHtml = 
			"<select id='subProductType'  style='width:100px;' name='distributionProductCategory.subProductType'>"+
			"<option value=''></option>"+
			"<option value='GROUP'>短途跟团游</option>"+
			"<option value='GROUP_LONG'>长途跟团游</option>"+
			"<option value='GROUP_FOREIGN'>出境跟团游</option>"+
			"<option value='FREENESS'>目的地自由行</option>"+
			"<option value='FREENESS_LONG'>长途自由行</option>"+
			"<option value='FREENESS_FOREIGN'>出境自由行</option>"+
			"<option value='SELFHELP_BUS'>自助巴士班</option>"+
			"</select>";			
			$('#subProductTypeTd').html(subProductTypeHtml);
			
			payOnlineTdHtml =
			"<select id='payOnline' style='width:80px;' name='distributionProductCategory.payOnline'>"+
			"<option  selected='selected' value=''></option>"+
			"</select>";
			$('#payOnlineTd').html(payOnlineTdHtml);
		}
	}
</script>
</head>
<body style="height: auto;">
<div>
	<form id="form1" action="${basePath }/distribution/insertDistributionProdCategory.do" method="post">
	<input type="hidden" id="distributorInfoId" name="distributionProductCategory.distributorInfoId" value="${distributorInfoId}"/>
	<table class="p_table form-inline">
			<tr>
				<td class="p_label">分销产品类型</td>
				<td >
				<select id="productType" style="width:80px;" name="distributionProductCategory.productType" onchange="onchangeShow(this.value)">
				<option value="TICKET">门票</option>
				<option value="ROUTE">线路</option>
				</select>
				</td>
				<td class="p_label">子类型</td>
				<td id="subProductTypeTd">
				<select id="subProductType" style="width:100px;" name="distributionProductCategory.subProductType">
				<option value=""></option>
				</select>
				</td>
				<td class="p_label">支付方式</td>
				<td id="payOnlineTd" width="10%">
				<select id="payOnline" style="width:80px;" name="distributionProductCategory.payOnline">
				<option  selected="selected" value=""></option>
				<option value='true'>线上支付</option>
				<option value='false'>景区支付</option>
				</select>
				</td>
				<td class="p_label">分销返佣点</td>
				<td><input type="text" id="discountRate" style="width:80px;" name="distributionProductCategory.discountRateY">%</td>
				<td class="p_label"><input type="button" value="添加"
				class="right-button08 btn btn-small" id="btnDistributorQuery" onclick="checkAndSubmitDistributionProdCategory()"/></td>
			</tr>
	</table>
	</form>
	<table class="p_table table_center Padding5">
			<tr>
				<th>分销产品类型</th>
				<th>子类型</th>
				<th>支付方式</th>
				<th>分销返佣点</th>
				<th>操作</th>
			</tr>
			<s:iterator id="distributionProductCategory" var="item" value="distributionProductCategoryList" >
			<tr id="no<s:property value="#item.distributionProductCategoryId"/>">
				<td>
				<s:property value="#item.zhProductType"/>
				</td>
				<td >
				<s:property value="#item.zhSubProductType"/>
				</td>
				<td>
				<s:if test="#item.payOnline == 'true'">线上支付</s:if>
				<s:elseif test="#item.payOnline == 'false'">景区支付</s:elseif>
				<s:else>无</s:else>
				</td>
				<td><s:property value="#item.discountRateY"/>%</td>
				<td>
				<a href="javascript:deleteDistributionProdCategory('<s:property value="#item.distributionProductCategoryId"/>');">删除</a>
				</td>
			</tr>
			</s:iterator>
	</table>
	
</div>
</body>
</html>