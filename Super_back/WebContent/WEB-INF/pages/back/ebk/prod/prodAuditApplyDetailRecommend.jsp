<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
		
		var time=null;
		 $("#features_tg")["mouseenter"]("bind",function(){
		       $("#features-tips").show();
		       clearTimeout(time);
		    })["bind"]("mouseleave",function(){
		    	time= setTimeout(function(){
		            $("#features-tips").hide();
		    },200);
		});
	    $("#features-tips").mouseenter(function(){
	       clearTimeout(time);
	    }).mouseleave(function(){
	       $(this).hide();
	    });
		
	});
</script>
<table class="newfont06" border="0"  cellpadding="0"  >
	<input type="hidden" name="ebkProdProductId" value="ebkProdProduct.ebkProdProductId"/>
	<tr>
		<td width="130">产品经理推荐：</td>
		<td width="190">
			<textarea rows="6" style="width: 300px" readonly="readonly">${ebkProdContentMap.MANAGERRECOMMEND}</textarea>
			 <s:if test="compareEbkProductRecommend.containsKey('managerrecommend')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductRecommend.managerrecommend}">审</span>
			 </s:if>
		</td>
	</tr>
	<s:if test="ebkProdProduct.productType!='DOMESTIC_LONG'">
		<tr>
			<td width="130">产品特色：</td>
			<td width="190">
				<div id="features" style="width: 500px;height: 200px;border:2px solid #999;overflow-y:auto;">${ebkProdContentMap.FEATURES}</div>
			</td>
			<td >
				<s:if test="compareEbkProductRecommend.containsKey('features')">
				 	<span class="tip_text" id="features_tg">审</span>
				 </s:if>
				 <div id="features-tips" style="display: none;" class="tooltip tooltip1">
				 	<div class="tooltip-arrow"></div><div class="tooltip-outer">
				 	<div class="tooltip-shadow"></div><div class="tooltip-inner">
					 	<h5 class="tooltip-title" style="display: block;">老数据:</h5>
					 	 <div class="tooltip-content"> 
					 	 <p>${compareEbkProductRecommend.features}</p>
					 	 </div>
					 </div>
					</div>
			 	 </div>
			</td>
		</tr>
	</s:if>
</table>
