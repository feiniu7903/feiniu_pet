<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>EBK供应商待审核产品</title>
<script type="text/javascript">
	$(function(){
		$('#createTimeBegin').datepicker({dateFormat : 'yy-mm-dd'});
		$('#createTimeEnd').datepicker({dateFormat : 'yy-mm-dd'});
		$('#confirmTimeBegin').datepicker({dateFormat : 'yy-mm-dd'});
		$('#confirmTimeEnd').datepicker({dateFormat : 'yy-mm-dd'});
		//查找供应商
		$("#searchSupplierName").jsonSuggest({
			url : "${basePath}/supplier/searchSupplier.do",
			maxResults : 10,
			minCharacters : 1,
			onSelect : function(item) {
				$("#comSupplierId").val(item.id);
			}
		});
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
		$(".ebk_tab_detail li").click(function (){
			$(this).addClass('current').siblings().removeClass('current');
		});
	});
	
	function tabSwitch(ebkProdProductId,type) {
		$("#tabPage").load("${basePath}/ebooking/prod/prodAuditApplyDetail"+type+".do?ebkProdProductId="+ ebkProdProductId);
	}
	
	if($("#tabPage").html()==''){
		tabSwitch("${ebkProdProductId}",'Base');
		$("#tabBase").addClass('current');
	}
</script>
</head>
<body>
	<ul class="ebk_tab_detail" style="overflow:hidden;">
    	<li id="tabBase"><a href="javascript:tabSwitch(${ebkProdProductId},'Base');">基础信息</a>
    		<s:if test="compareTabsChange.EBK_AUDIT_TAB_BASE=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
    	</li>
    	<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Recommend');">产品推荐及特色</a>
			<s:if test="compareTabsChange.EBK_AUDIT_TAB_RECOMMEND=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
		</li>
		<s:if test='ebkProdProduct.isMultiJourney!="Y"'>
			<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Trip');">行程描述</a>
				<s:if test="compareTabsChange.EBK_AUDIT_TAB_TRIP=='true'">
	    			<span class="tip_text" tip-title="" tip-content="">审</span>
	    		</s:if>
			</li>
			<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Cost');">费用说明</a>
				<s:if test="compareTabsChange.EBK_AUDIT_TAB_COST=='true'">
	    			<span class="tip_text" tip-title="" tip-content="">审</span>
	    		</s:if>
			</li>
		</s:if>
		<s:else>
			<li ><a href="javascript:tabSwitch(${ebkProdProductId},'MultiTrip');">多行程说明</a>
				<s:if test="compareTabsChange.EBK_AUDIT_TAB_MULTITRIP=='true'">
	    			<span class="tip_text" tip-title="" tip-content="">审 </span>
	    		</s:if>
			</li>
		</s:else>
		<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Picture');">产品图片</a>
			<s:if test="compareTabsChange.EBK_AUDIT_TAB_PICTURE=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
		</li>
		<s:if test="ebkProdProduct.productType=='SURROUNDING_GROUP'">
		<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Traffic');">发车信息</a>
			<s:if test="compareTabsChange.EBK_AUDIT_TAB_TRAFFIC=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
		</li>
		</s:if>
		<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Other');">其它条款</a>
			<s:if test="compareTabsChange.EBK_AUDIT_TAB_OTHER=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
		</li>
		
		<li ><a href="javascript:tabSwitch(${ebkProdProductId},'TimePrice');">价格库存</a>
			<s:if test="compareTabsChange.EBK_AUDIT_TAB_TIME_PRICE=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
		</li>
		<s:if test="ebkProdProduct.productType=='ABROAD_PROXY'">
		<li ><a href="javascript:tabSwitch(${ebkProdProductId},'Relation');">关联销售产品</a>
			<s:if test="compareTabsChange.EBK_AUDIT_TAB_RELATION=='true'">
    			<span class="tip_text" tip-title="" tip-content="">审</span>
    		</s:if>
		</li>
		</s:if>
		<s:if test="ebkProdProductStatus=='THROUGH_AUDIT' || ebkProdProductStatus=='REJECTED_AUDIT'">
			<li ><a href="javascript:tabSwitch(${ebkProdProductId},'AuditResult');"><lable style="color: red;">审核结果</lable></a>
			</li>
		</s:if>
  	</ul>
	<div class="gl_top">
		<div id="tabPage"></div>
	</div>
</body>
</html>