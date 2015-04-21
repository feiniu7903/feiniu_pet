<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.importantTipProdDetail {
	padding: 10px;
	line-height: 30px;
}
.TableProdDetail{margin:18px 0;padding:9px 20px;font-size:12px;margin:0 10px; border-left: 1px;}
.importantTipProdDetail table,.importantTipProdDetail table td,.importantTipProdDetail table th{border:1px solid #5D9BC4;border-collapse:collapse;}
.TableProdDetail td{height:30px; padding:2px;}
.TableProdDetail em{font-weight: 300 ;}
.TableProdDetailTit td{background:#e5eeff; line-height:20px;font-size:14px; font-family:"微软雅黑", "黑体", "宋体";}
</style>
<script type="text/javascript">
</script>
</head>
<body>
	<div style="font-size: 12px;">
		
		<div class="importantTipProdDetail">
		<!-- 详细信息  start-->
		<strong class="add_margin_left" style="color:#4D4D4D;">详细信息</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr>
        		<td width="120px;"><em>产品名称：</em></td>
        		<td><s:property value='product.productName'/></td>
        		<td width="120px;"><em>产品编号：</em></td>
        		<td><s:property value='product.bizcode'/></td>
        	</tr>
        	<!-- 
        	<tr>
        		<td width="120px;"><em>产品短名称：</em></td>
        		<td colspan="3"><s:property value='product.shortName'/></td>
        	</tr> -->
        	<tr>
        		<!-- <td><em>最小起订量：</em></td>
        		<td><s:property value='product.productName'/></td> -->
        		<td><em>上下线时间：</em></td>
        		<td><s:property value='product.viewOnlineTime'/>~<s:property value='product.viewOfflineTime'/></td>        		
        		<td><em>是否需要签约：</em></td>
        		<td>
        		<s:if test="product.productType == 'ROUTE'">
        			<s:property value='prodRoute.isEContract()'/>
        		</s:if>
        		<s:else>
        			<s:property value='product.isEContract()'/>
        		</s:else>
        		</td>
        	</tr>
        	<tr>
        		<td><em>渠道：</em></td>
        		<td colspan="3"><s:property value='channels'/></td>
        	</tr>
        	<tr>
        		<td><em>短信内容：</em></td>
        		<td colspan="3"><s:property value='product.smsContent'/></td>
        	</tr>
        	<tr>
        		<td><em>支付对象：</em></td>
        		<td>
        			<s:if test="product.payToSupplier == 'true'">支付给供应商</s:if>
        			<s:else>支付给驴妈妈</s:else>
        		</td>
        		<td><em>产品经理：</em></td>
        		<td><s:property value='mangerName'/></td>
        	</tr>
        </table>
        <!-- 详细信息  end-->
        <!-- 特征信息  start-->
        <strong class="add_margin_left" style="color:#4D4D4D;">特征信息</strong>
        <table class="TableProdDetail" border="1" cellspacing="0" cellpadding="0" width="92%">
        	<tr>
        		<td width="120px;"><em>产品子类型：</em></td>
        		<td><s:property value='product.zhSubProductType'/></td>
        		<td width="120px;"><em>最少成团人数：</em></td>
        		<td>
        			<s:property value='product.groupMin'/>
        		</td>
        	</tr>
        	<tr>
        		<td width="120px;"><em>行程天数：</em></td>
        		<td colspan="3">
        		<s:if test="product.productType == 'ROUTE'">
        			<s:property value='product.days'/>
        		</s:if>
        		</td>
        	</tr>
        </table>
        <!-- 特征信息  end-->
        <!-- 关联标地  start-->
        <strong class="add_margin_left" style="color:#4D4D4D;">关联标的</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr class="TableProdDetailTit">
              <td style="width:10%">编号 </td>
              <td style="width:90%">名称</td>
            </tr>
            <s:iterator value="placeList">
            <tr>
            	<td><s:property value='placeId'/></td>
            	<td><s:property value='placeName'/></td>
            </tr>
            </s:iterator>
        </table>
        <!-- 关联标地  end-->
       	<!-- 来自采购产品的凭证对象  start-->
       	<s:if test="targetList!=null">
       	<s:iterator value="targetList" id="tl">
       	<div>
       	<h3 style="color:#4D4D4D;margin: 0px 10px;display:block">类 别:${tl.prodBranch.branchName}</h3>
        <strong class="add_margin_left" style="color:#4D4D4D;">来自采购产品的凭证对象</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr class="TableProdDetailTit">
              <td style="width:10%">对象ID</td>
              <td style="width:50%">产品名称</td>
              <td style="width:30%">对象名称</td>
              <td style="width:20%">凭证 </td>
            </tr>
            <s:iterator value="#tl.bcertificateList" var="bcertificate">
            <s:iterator value="#bcertificate.targetList">
            <tr>
            	<td><s:property value='targetId'/></td>
            	<td><a class="showTarget" url="${basePath}targets/certificatetarget/detailcertificatetarget.zul?targetId=${targetId}" href="#showTarget">
            	<s:property value='#bcertificate.metaProduct.productName'/></a></td>
            	<td><s:property value='name'/></td>
            	<td><s:property value='viewBcertificate'/></td>
            </tr>
            </s:iterator>
            </s:iterator>
        </table>
        <!-- 来自采购产品的凭证对象  end-->
        <!-- 来自采购产品的履行对象  start-->
        <strong class="add_margin_left" style="color:#4D4D4D;">来自采购产品的履行对象</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr class="TableProdDetailTit">
              <td style="width:10%">对象ID</td>
              <td style="width:40%">产品名称</td>
              <td style="width:20%">对象名称</td>
              <td style="width:15%">支付信息</td>
              <td style="width:15%">履行信息</td>
            </tr>
            <s:iterator value="#tl.performList" var="perform">
	            <s:iterator value="#perform.targetList">
	            <tr>
	            	<td><s:property value='targetId'/></td>
	            	<td>
	            	<a class="showTarget" url="${basePath}targets/performtarget/detailperformtarget.zul?targetId=${targetId}" href="#showTarget">
	            	<s:property value='#perform.metaProduct.productName'/></a>
	            	</td>
	            	<td><s:property value='name'/></td>
	            	<td><s:property value='paymentInfo'/></td>
	            	<td><s:property value='performInfo'/></td>
	            </tr>
	            </s:iterator>
            </s:iterator>
        </table>
        <!-- 来自采购产品的履行对象  end-->
        <!-- 来自采购产品的结算对象  start-->
        <strong class="add_margin_left" style="color:#4D4D4D;">来自采购产品的结算对象</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr class="TableProdDetailTit">
              <td style="width:10%">对象ID</td>
              <td style="width:50%">产品名称</td>
              <td style="width:30%">对象名称</td>
              <td style="width:20%"> 结算周期  </td>
            </tr>
            <s:iterator value="#tl.settlementList" var="settlement">
            <s:iterator value="#settlement.targetList">
            <tr>
            	<td><s:property value='targetId'/></td>
            	<td><a class="showTarget" url="${basePath}targets/settlementtarget/detailsettlementtarget.zul?targetId=${targetId}" href="#showTarget">
            	<s:property value='#settlement.metaProduct.productName'/></a></td>
            	<td><s:property value='name'/></td>
            	<td><s:property value='zhSettlementPeriod'/></td>
            </tr>
            </s:iterator>
            </s:iterator>
        </table>
        </div>
        </s:iterator>
        </s:if>
        <!-- 来自采购产品的结算对象  end-->
        <%-- 
        <!-- 来自采购产品的信息提示  start-->
        <strong class="add_margin_left" style="color:#4D4D4D;">来自采购产品的信息提示</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr class="TableProdDetailTit">
              <td style="width:10%">序号</td>
              <td style="width:50%">时间</td>
              <td style="width:30%">预订限制内容</td>
              <td style="width:20%"> 类型  </td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
        </table>
        <!-- 来自采购产品的信息提示  end-->--%>
        <!-- 来自销售商品的信息提示  start-->
        <strong class="add_margin_left" style="color:#4D4D4D;">来自销售商品的信息提示</strong>
        <table class="TableProdDetail" border="0" cellspacing="0" cellpadding="0" width="92%">
        	<tr class="TableProdDetailTit">
              <td style="width:10%">序号</td>
              <td style="width:50%">时间</td>
              <td style="width:30%">内容</td>
              <td style="width:20%"> 类型  </td>
            </tr>
            <s:iterator value="conditionList">
            <tr>
            	<td><s:property value='conditionId'/></td>
            	<td><s:date name="beginTime" format="yyyy-MM-dd"/>~<s:date name="endTime"  format="yyyy-MM-dd"/></td>
            	<td><s:property value='content'/></td>
            	<td><s:property value='conditionType'/></td>
            </tr>
            </s:iterator>
        </table>
        <!-- 来自销售商品的信息提示  end-->
        </div>
	</div>
<div id="showTargetDiv"></div>
<script type="text/javascript">
$(function() {
	$("a.showTarget").click(function(){
		$("#showTargetDiv").empty().load($(this).attr("url")+"&closeable=false",null,function(dt){
			$(this).dialog({
				title:"采购产品对象相关信息",
				width:1000,
				modal : true
			})
		})
	});
});
</script>
</body>
</html>