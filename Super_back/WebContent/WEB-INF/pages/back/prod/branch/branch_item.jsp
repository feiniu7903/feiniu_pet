<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<form action="<%=basePath%>prod/addBranchItem.do" method="post"  onsubmit="return false" name="addBranchItemForm">
<input type="hidden" name="direction"/>
<div class="row2">
<span>采购产品:</span><span class="require">[*]</span><input type="hidden" name="branchItem.prodBranchId" value="${prodBranchId}"/><input type="hidden" name="metaProductId"/><input type="text" name="searchMeta" id="searchMeta"/>
<span>类别:</span><span class="require">[*]</span><select name="branchItem.metaBranchId" id="branchItem_metaBranchId"></select>
<span>数量:</span><span class="require">[*]</span><input type="text" name="branchItem.quantity" style="width:40px;"/>
<input type="button" value="添加" class="addBranchItem" ff="addBranchItemForm"/>
<a href="#log" class="showLogDialog" param="{'parentType':'PROD_PRODUCT_BRANCH','objectType':'PROD_PRODUCT_BRANCH_ITEM','parentId':${prodBranchId}}">查看操作日志</a>
</div>
</form>   
<div class="row2">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="meta_branch_tb">
<tr class="tableTit">
	<td>采购产品</td>
	<td>类别</td>
	<td>数量</td>
	<td>人数</td>                        
	<td>操作</td>
</tr>
<s:iterator value="branchItemList">
<tr id="tr_item_<s:property value="branchItemId"/>" result="<s:property value="branchItemId"/>">
	<td>
	<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=<s:property value="metaProduct.metaProductId"/>&metaBranchId=<s:property value="metaBranchId"/>&productType=<s:property value="metaProduct.productType"/>',700,700)">
	<s:property value="metaProduct.productName"/>(<s:property value="metaProduct.metaProductId"/>)
	</a>
	</td>
	<td><s:property value="metaBranch.branchName"/>(<s:property value="metaBranchId"/>)</td>
	<td><s:property value="quantity"/></td>
	<td>成人:<s:property value="metaBranch.adultQuantity"/><br/>
		儿童:<s:property value="metaBranch.childQuantity"/>
	</td>
	<td>
	<a href="#timeprice" class="showTimePrice" tt="META_PRODUCT" param="{'metaBranchId':<s:property value="metaBranchId"/>}">修改价格</a>
	<a href='#delete' class='deleteItem' >删除</a></td>
</tr>
</s:iterator>
</table>
</div>