<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
<!--
	suggest();
//-->
</script>
<div>
<h4 class="journeyProductDivTit"><s:property value="getProductTypeStr()"/>组</h4>  
<table border="0" cellspacing="0" cellpadding="0" class="newTableB">	
	<tr>
		<td>  
            	         	<form name="journeyProductForm" action="<%=request.getContextPath()%>/prod/addJourneyProduct.do" class="tableForm" onsubmit="return false"><input type="hidden" name="productId"/>
            		<input type="hidden" name="productType" id="productType" value="${type}"/>
            		<input type="hidden" name="prodJourneyProduct.prodJourenyId" value="${prodJourneyId}"/>
                	产品名称、编号或ID<input type="text" class="text1" name="text1" id="productSuggest" />
                    <b>类别：</b>
                    <select name="prodJourneyProduct.prodBranchId">               
                    </select>
                    <em class="button button2 saveJourneyProduct" ff="journeyProductForm">新增产品</em></form>                    
                    
                    </td>	
		<td><b>组的属性：</b></td>
		<td><input name="policy" class="radio01" type="radio" value="false" <s:if test="journeyPolicy==false">checked</s:if> /><span>可选</span><input name="policy" value="true" class="radio01" type="radio" <s:if test="journeyPolicy==true">checked</s:if>/><span>必选</span></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>	
</table>         
</div>              
				<form>
               <table width="90%" border="0" cellspacing="0" cellpadding="0" class="newTable">
                 <tr class="newTableTit">
                   <td>产品ID</td>
                   <td>产品名称</td>
                   <td>类别</td>
                   <td>上线状态</td>
                   <td>属性</td>
                   <td>价格/库存</td>
                   <td>优惠金额</td>
                   <td>操作</td>
                 </tr>
                 <s:iterator value="productList" var="product">               
                 <tr id="product_${product.productId}">
                   <td>${product.productId}</td>
                   <td>${product.productName}</td>
                   <td>&nbsp;</td>
                   <td><s:if test="#product.isOnLine()">上线</s:if><s:else>下线</s:else></td>
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                 </tr>
                 <s:iterator value="#product.prodJourneyProductList" var="jp">
                 <tr id="product_${product.productId}_${jp.journeyProductId}" productId="${product.productId}" jpId="${jp.journeyProductId}">
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                   <td>${jp.prodBranch.branchName}(${jp.prodBranch.prodBranchId})</td>
                   <td><s:if test="#jp.prodBranch.hasOnline()">上线</s:if><s:else>下线</s:else></td>
                   <td><input name="defaultProduct" type="checkbox" value="${jp.journeyProductId}" <s:if test="#jp.hasDefaultProduct()">checked</s:if> class="checkbox" /><em>默认</em>&nbsp;&nbsp;<input name="require" type="checkbox" value="${jp.journeyProductId}" <s:if test="#jp.hasRequire()">checked</s:if> class="checkbox" /><em>必选</em></td>
                   <td><a href="#timePrice" tt="PROD_PRODUCT" class="showTimePrice" param="{'prodBranchId':${jp.prodBranch.prodBranchId},'editable':false}">查看</a></td>
                   <td class="discount"><s:text name="global.format.int.number"><s:param value="#jp.discountYuan"/></s:text>元</td>          	
                   <td><a href="#delete" class="jp_delete">删除</a>&nbsp;<a href="#discount" class="discount" ds="<s:text name="global.format.int.number"><s:param value="#jp.discountYuan"/></s:text>">修改优惠</a></td>
                 </tr>
                 </s:iterator>
                 </s:iterator>                 
     		   </table>
     		   </form>
     		   <div style="display:none" id="editDiscountDiv">
     		   	<form onsubmit="return false"><input type="hidden" name="journeyProductId" />
     		   		<table width="100%">
     		   			<tr>
     		   				<td width="100">优惠金额:</td><td><input type="text" name="discount"/></td>
     		   			</tr>
     		   		</table>
     		   	</form>
     		   </div>
     		   <!--  
               <p><em class="button button2">保存</em></p>
                -->