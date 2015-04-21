<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<!-- 引用EBK公共资源(CSS、JS) -->
<s:include value="./common/ebkCommonResource.jsp"></s:include>
</head>
<body id="body_cpgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkProdTrafficContent.do" id="confirm" method="post">
		<input name="ebkProdProduct.ebkProdProductId" id="ebkProdProductId" value="${ebkProdProduct.ebkProdProductId}" type="hidden">
		<input name="ebkProdProduct.supplierId" id="supplierId" value="${ebkProdProduct.supplierId }" type="hidden">
		<input name="ebkProdProduct.productType" id="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="toShowEbkProduct" value="${toShowEbkProduct }" id="toShowEbkProduct" type="hidden">
		
		<!--新增产品基础信息-->
		<div class="xzxx_box">
			<span class="fp_btn kcwh_btn_t">提交审核</span>
			<ul class="xzxx_tab">
				<li class="tab_this" id="EBK_AUDIT_TAB">产品基础信息</li>
				<li id="EBK_AUDIT_TAB_TIME_PRICE">价格/库存维护</li>
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
		            <li style="display: block;">
		            	<p class="fcxx_t"><b>发车信息：</b></p>
		                <table class="fcxx_b" id="traffic_tab">
		                	<tr>
		                    	<th width="500">发车信息</th>
		                        <th width="80">操作</th>
		                    </tr>
		                    
		                    <s:if test="ebkProdProduct.ebkProdContents != null && ebkProdProduct.ebkProdContents.size > 0">
	                        	<s:iterator value="ebkProdProduct.ebkProdContents" var="ebkProdContent" status="index">
	                        		<tr id="traffic_tr_${ebkProdContent.contentId}">
				                    	<td>
				                    		<input type="text" name="ebkProdProduct.ebkProdContents[${index.index }].content" value="${ebkProdContent.content}">
				                    		<input type="hidden" name="ebkProdProduct.ebkProdContents[${index.index }].contentId" value="${ebkProdContent.contentId}">
				                    		
				                    	</td>
				                        <td><span class="fp_btn delete_span" id="${ebkProdContent.contentId}">删除</span></td>
				                    </tr>
								</s:iterator>
	                        </s:if>
		                    
		                </table>
		                <span class="fp_btn add_traffic_span">新增一栏</span>
		            </li>
		            
				</ul>
				<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
					<span class="btn_bc submitEdit saveEbkProdTrafficInit">保存</span><span><font color="red" id="errorMessageSpan"></font></span>
				</s:if>
			</div>
		</div>
	</form>
	
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	var basePath = "${basePath}";
	var traffic_tr_index = $("#traffic_tab tr").length-1;
	
	/*新增一条发车信息*/
	$('.add_traffic_span').live('click', function() {
		var tr_ = $("<tr  id='traffic_tr_"+traffic_tr_index+"'><td><input type='text' name='ebkProdProduct.ebkProdContents["+traffic_tr_index+"].content'><input type='hidden' name='ebkProdProduct.ebkProdContents["+traffic_tr_index+"].contentId' ></td><td><span class='fp_btn delete_span' id='"+traffic_tr_index+"'>删除</span></td></tr>");
		$("#traffic_tab").append(tr_);
		traffic_tr_index++;
	});
	
	/*删除操作*/
	$('.delete_span').live('click', function() {
		$("#traffic_tr_"+$(this).attr("id")).remove();
	});
</script>
</html>