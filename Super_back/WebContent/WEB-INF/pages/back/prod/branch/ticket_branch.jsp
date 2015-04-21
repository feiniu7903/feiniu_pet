<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——销售产品列表</title>
<link href="<%=basePath%>/themes/cc.css" rel="stylesheet" type="text/css" />
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/branch.js"></script>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/branch_item.js"></script>

<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"></script>
<script type="text/javascript" src="<%= basePath%>js/prod/sensitive_word.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		$("#timePriceDiv").lvmamaDialog({width:1000,height:710,close:function(){}});
	
	});
function onloadTimePrice(productId,prodBranchId){
		$('#timePriceDiv').openDialog();
		$('#timePriceDiv').reload({productId:productId,prodBranchId:prodBranchId});
}
</script>
</head>
 
<body>
<div class="main main07">
	<div class="row1">
    	<h3 class="newTit">产品列表</h3>
         <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
   </div><!--row1 end-->    
   <div class="row2">
   		<table class="newTable" width="100%" border="0" cellspacing="0" cellpadding="0" id="branch_tb">
          <tr class="newTableTit">
            <td>类别名称</td>
            <td>人数</td>
            <td>计价单位</td>
            <td>最小起订量</td>
            <td>最大订购数</td>
            <td>是否附加</td>      
            <td>是否默认</td>      
            <td>操作</td>
          </tr>
          <s:iterator value="productBranchList">
		          <tr id="tr_<s:property value="prodBranchId"/>" prodBranchId="<s:property value="prodBranchId"/>">
		            <td><s:property value="branchName"/> </td>
		            <td><span>成人：<s:property value="adultQuantity"/></span>
		            <span>儿童：<s:property value="childQuantity"/></span></td>
		            <td><s:property value="priceUnit"/></td>
		            <td><s:property value="minimum"/></td>
		            <td><s:property value="maximum"/></td>
		            <td><s:if test="additional=='true">是</s:if><s:else>否</s:else></td>
		            <td class="def"><s:if test="defaultBranch=='true'">是</s:if>&nbsp;</td>
		            <td>
						<a href='#price' class='viewPrice'>查看价格</a>&nbsp;|&nbsp;<a href="javascript:onloadTimePrice('<s:property value="productId"/>',<s:property value="prodBranchId"/>)" class='changePrice'>修改价格</a>
						<br/><a href='#pack'	class='pack'>打包采购</a>&nbsp;|&nbsp;<a href='#edit' class='edit'>修改</a>
						<br/><a href='#delete' class='online'><s:if test="online=='true'">下线</s:if><s:else>上线</s:else></a>&nbsp;|&nbsp;<a href='#def' class='setdef'>设为默认</a>
						<br/><a href='#dest' class='dest'>查看类别描述</a>
						<div style="display: none;border:1px solid #000;position:absolute;bottom:-10px;right:0;background:#fff;height:30px;padding:10px;margin:0 2px;" class="dest"><s:property value="description"/></div>
					</td>
		          </tr>
           </s:iterator>
        </table>
    </div><!--row2 end-->
    <p><em class="button button2 newBranch">新增</em></p>
    <div style="display: block;float:none;clear: both;"></div>
   	<form action="<%=basePath%>prod/editProductBranch.do" onsubmit="return false" name="branchForm" style="display: none" class="section mySensitiveForm">
   		<input type="hidden" name="branch.productId"/><input type="hidden" name="branch.prodBranchId"/>
   <div class="row3" style="display: block;clear:none;">
   	<table class="newTableB" border="0" cellspacing="0" cellpadding="0">
   		<tr>
   			<td><em>请选择：</em></td>
   			<td><s:select list="branchCodeSetList" listKey="code" listValue="name" name="branch.branchType"/></td>
   			<td><em>是否附加：</em></td>
   			<td><input type="radio" name="branch.additional" value="true"/>是&nbsp;<input type="radio" name="branch.additional" checked="checked" value="false"/>否</td>
   		</tr>
   		<tr>
   			<td><em>类别名称：</em></td>
   			<td><input type="text" class="text1 sensitiveVad" name="branch.branchName" id="branch_branchName" /> </td>
   			<td><em>计价单位：</em></td>
   			<td><input type="text" class="text1" name="branch.priceUnit"/></td>
   		</tr>
   		<tr>
   			<td><em>人数：</em><em>成人：</em></td>
   			<td><input type="text" class="text1" name="branch.adultQuantity" /></td>
   			<td><em>儿童：</em></td>
   			<td><input type="text" class="text1" name="branch.childQuantity" /></td>
   		</tr>
   		<tr>
   			<td><em>最小起订量：</em></td>
   			<td><input type="text" class="text1" name="branch.minimum"/></td>
   			<td><em>最大起订单：</em></td>
   			<td><input type="text" class="text1" name="branch.maximum"/></td>
   		</tr>
   		<tr>
   			<td><em>票种描述：</em></td>
   			<td colspan="3"><textarea name="branch.description" cols="50" rows="5" class="sensitiveVad"></textarea></td>
   		</tr>
   	</table>  
     <p><em class="button button2 saveForm" form="branchForm">保存</em></p>
   </div><!--row3 end-->
        </form> 
        <div id="branchItemDiv" class="section">
        </div>
</div><!--main01 main05 end-->
<div style="display: none">
<s:if test='product.productType=="HOTEL"'>
<s:include value="" />
</s:if>
</div>
<div id="timePriceDiv" href="<%=basePath%>prod/toProdTimePrice.do"></div>
</body>
</html>


