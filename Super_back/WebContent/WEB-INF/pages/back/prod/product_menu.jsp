<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	var current_product_id=<s:if test="productId!=null">${productId}</s:if><s:else>${-1}</s:else>;
	var has_visa_prod=<s:if test="product.subProductType=='VISA'">true</s:if><s:else>false</s:else>
	var current_product_type='${product.productType}';
	var has_self_pack ='<s:property value="product.hasSelfPack()"/>';
	function go(url){
		if(current_product_id==-1){
			alert("请保存基本信息再操作其他的页面");
			return;
		}
		var fullurl="";
		if(url=='base'){
			if(has_visa_prod){
			fullurl='/super_back/prod/editAsiaProduct.do?productId='+current_product_id;
			}else{
			fullurl='/super_back/prod/toEditProduct.do?productId='+current_product_id;
			}
		} else if(url=='toViewContent'||url=='toViewJourney'){
			fullurl='/super_back/view/'+url+'.do?productId='+current_product_id;
		}else if(url=='queryProdBusinessCouponList'){
			var metaType='SALES';
			fullurl='/super_back/prod/'+url+'.do?productId='+current_product_id+'&metaType='+metaType;
		}else{
			fullurl='/super_back/prod/'+url+'.do?productId='+current_product_id;
		}
		window.location.href=fullurl;
	}
</script>
<div class="nav">
	<ul class="newNav">
		<li <s:if test='menuType=="prod"'>class="newCurrent"</s:if>>
			<a href="javascript:go('base')">基本信息</a>
		</li>
		<s:if test="product.subProductType!='VISA'">
		<li <s:if test='menuType=="place"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toProductPlace')">标的</a>
		</li>
		<s:if test="product.hasSelfPack()">
		<li <s:if test='menuType=="selfpack"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toSelfPackJourney')">行程</a>
		</li>
		</s:if>		
		</s:if>
		<li <s:if test='menuType=="branch"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toProductBranch')">类别</a>
		</li>
		<s:if test="product.subProductType!='VISA'">
		<li <s:if test='menuType=="additional"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toProductAdditional')">附加产品</a>
		</li>
		<s:if test="product.productType!='TRAFFIC'">
		<li <s:if test='menuType=="image"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toProductImage')">产品图片</a>
		</li>
		<li <s:if test='menuType=="detail"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toViewContent')">描述信息</a>
		</li>
		<li <s:if test='menuType=="journey"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toViewJourney')">行程展示</a>
		</li>
		<li <s:if test='menuType=="other"'>class="newCurrent"</s:if>>
			<a href="javascript:go('toProductOther')">其他信息</a>
		</li>
		<s:if test="(!product.hasSelfPack())&&product.isPaymentToLvmama()">
			<li <s:if test='menuType=="coupon"'>class="newCurrent"</s:if>>
				<a href="javascript:go('queryProdBusinessCouponList')">设置优惠</a>
			</li>
		</s:if>
		<s:if test='product!=null'>
			<li>
				<a href="http://www.lvmama.com/product/preview.do?id=<s:property value="product.productId"/>" target="_blank">预览</a>
			</li>
		</s:if>
		</s:if>
		</s:if>
	</ul>
</div>




