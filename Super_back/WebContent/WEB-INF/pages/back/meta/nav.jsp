<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	var current_meta_product_id=<s:if test="metaProductId!=null">${metaProductId}</s:if><s:else>-1</s:else>;	
	var current_meta_product_type="${metaProduct.productType}";
	function go(page){
		if(current_meta_product_id==-1){
			alert("请保存基本信息再操作其他的页面");
			return;
		}
		var fullurl="";
		if(page=='base'){
			fullurl="meta/toEditProduct.do?metaProductId="+current_meta_product_id;			
		}else if(page=='queryProdBusinessCouponList'){
			var metaType='META';
			fullurl='prod/'+page+'.do?productId='+current_meta_product_id+'&metaType='+metaType;
		}else{
			fullurl="meta/"+page+".do?metaProductId="+current_meta_product_id;
		}
		window.location.href='<%=basePath%>'+fullurl;
	}
</script>
<div class="nav">
	<ul class="newNav">
		<li	<s:if test="menuType=='prod'">class="newCurrent"</s:if>>
			<a href="javascript:go('toEditProduct')">基本信息</a>
		</li>
		<mis:checkPerm permCode="1423" permParentCode="${permId}">
		<li	<s:if test="menuType=='target'">class="newCurrent"</s:if>>
			<a href="javascript:go('toEditTarget')">对象</a>
		</li>
		</mis:checkPerm>
		<li	<s:if test="menuType=='branch'">class="newCurrent"</s:if>>
			<a href="javascript:go('toEditBranch')">类别</a>
		</li>
		<s:if test="metaProduct.isPaymentToLvmama()">
			<li	<s:if test="menuType=='coupon'">class="newCurrent"</s:if>>
				<a href="javascript:go('queryProdBusinessCouponList')">设置优惠</a>
			</li>
		</s:if>
	</ul>
</div>