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
<title>super后台——产品详情</title>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=basePath%>kindeditor-3.5.1/kindeditor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/sensitive_word.js"></script>
<style type="text/css">
.row2 .newTableProductDetail td textarea{height:120px;width:650px;}
.newTableProductDetail{margin:0 auto;background:#fff;font-size:12px; color:#555; width: 98%;}
.newTableProductDetail td{font-size:12px;padding:3px 3px;width:auto; height:auto;}
.newTableProductDetail td span{margin:0 3px;font-size:12px;}
.newTableProductDetail1{margin:0 auto;background:#fff;font-size:12px; color:#555; width: 98%;}
.newTableProductDetail1 td{font-size:12px;padding:3px 3px;width:auto; height:auto;}
.newTableProductDetail1 td span{margin:0 3px;font-size:12px;}

</style>
<script type="text/javascript">	
    	KE.show({
    		id:'features',    	
    		cssPath : '<%=basePath%>kindeditor-3.5.1/skins/default.css',
        	filterMode : true
    	});
    	
    	$(function(){
    		$("#save").click(function(){
    			var $form=$(this).parents("form");
    			$.ajax({
    				url:$form.attr("action"),
    				data:$form.serialize(),
    				type:"POST",
    				success:function(dt){
    					var data=eval("("+dt+")");
    					if(data.success){
    						alert("保存成功");
    					}else{
    						alert(data.msg);
    					}
    				}
    			})
    		});

            var refundsexplanation = $("#REFUNDSEXPLANATION").val();
            var productType = $("#productType").val();

            if ($.trim(refundsexplanation) == "") {
                if (productType == "TICKET") {
                    $("#REFUNDSEXPLANATION").val("在线支付门票，一经支付如需更改出游时间或取消订单，请在游玩日期前1天18点前致电1010 6060，逾期则不作退改。预订当天游玩的门票，一经支付则不作退改。");
                }
            }
    	});
    	
    	function deleteViewTravelTips(id){
    		var url = '<%=basePath%>prod/deleteViewTravelTips.do';
    		$.get(url,
    				{"viewTravelTipsId":id}, 
    				function(data){
    					if (data.success) {
    						alert("删除成功!");
    						location.reload();
    					} else {
    						alert(data.msg);
    					}
    				});
    	}
    	
    	//新增旅游须知
    	function addViewTravelTips(productId){
    		
    		var url = "<%=basePath%>/prod/searchTravelTipsToRouteProd.do?productId="+productId;
    		
    		$("<iframe frameborder='0' id='addViewTravelTipsDiv'></iframe>").dialog({
    			autoOpen: true, 
    	        modal: true,
    	        title : "新增旅游须知",
    	        position: 'top',
    	        width: 720
    		}).width(700).height(450).attr("src",url);
    		
    	}
</script>
</head>
 
<body>

<div class="main main12">
	<div class="row1">
    	<h3 class="newTit">销售产品信息 </h3>
    </div><!--row1 end-->
   <form action="<%=basePath%>view/updateViewContent.do" method="post" onsubmit="return false" class="mySensitiveForm"> 
   <input type="hidden" name="productId" value=${productId} />
   <input type="hidden" id="productType" value="<s:property value="product.productType"/>"/>
   <div class="row2">
		<table class="newTableProductDetail" width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
   			<td colspan="2"><b>产品经理推荐</b>(单行不能超过30个字)</td>
 		  </tr>
          <tr>
   			<td><textarea name="MANAGERRECOMMEND" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("MANAGERRECOMMEND").content'/></textarea></td>
   			<td>&nbsp;</td>
 		  </tr>
 		  <tr>
 		  	<td colspan="2"><b>公告</b><span>(建议文字简略，不超过3条)</span></td>
 		  </tr>
 		  <tr>
   			<td width="665px"><textarea name="ANNOUNCEMENT" cols="text2" rows="text2" class="text2 sensitiveVad" ><s:property value='viewPage.contents.get("ANNOUNCEMENT").content'/></textarea></td>
   			<td width="60%" valign="middle">公告用于非常规性信息发布，如该产品正在参加的相关活动（团购、促销、赠品等）、目的地节庆信息、其他特殊信息</td>
 		  </tr>
 		  <!-- 非多行程才显示费用说明 -->
 		  <s:if test='product.productType!="ROUTE" || !product.hasMultiJourney()'>
 		  		<tr>
 		  	<td colspan="2"><b>费用说明</b><span class="require">[*]</span></td>
 		  </tr>
		          <tr>
		   			<td colspan="2" width="100%">费用包含</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea id="cost" name="COSTCONTAIN" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("COSTCONTAIN").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="middle">
		   			<s:if test='product.productType=="HOTEL"'>
	                	1. 酒店内部设施：<br/> 2. 餐饮：<br/> 3. 免费宽带：<br/> 4. 房型
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                <font color="red">自由行产品</font><br/>
	                &nbsp;&nbsp;1. 门票：<br/> &nbsp;&nbsp;2. 住宿：房间所包含的宽带、早餐等请在此注明<br/>
	                <font color="red">长短线产品</font><br/>
	                &nbsp;&nbsp;1. 交通：<br/> &nbsp;&nbsp;2. 住宿：房间所包含的宽带、早餐等请在此注明<br/>
	                 &nbsp;&nbsp;3. 景点门票：（如无请注明：无） <br/>&nbsp;&nbsp;4. 用餐：（如无请注明：无）<br/>
	                  &nbsp;&nbsp;5. 导服：（如无请注明：无） <br/>
	                  &nbsp;&nbsp;6. 赠送：（如无请删除此项）
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'></s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	                ★费用说明：（说明产品包含的项目，注明附加的需要另外付费的项目；需注明产品是否包含保险，如不包含可建议游客在下单时选择购买）<br/>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                	1. 本产品至少包含1晚酒店住宿和1张XX景区门票费用，具体以实际选择入住酒店间夜和门票数量为准<br/>
	                	2. 若产品赠送人身意外险则在此注明<br/>
	                </s:elseif>
		   			</td>
		 		  </tr>
		 		  <tr>
		   			<td colspan="2">费用不包含</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea id="cost" name="NOCOSTCONTAIN" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("NOCOSTCONTAIN").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="middle">
	                <s:if test='product.productType=="HOTEL"'>费用中所不含的（早餐、服务费、网费、加床）等
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	               	1. 多出发地的产品需注明：该产品为上海出发，不包含苏州到上海的交通费用。
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>若不包含景区内其他小景点，请在此注明。如是实体票不包含快递，请在此注明提醒客人另外订购快递费
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                1. 以上“费用包含”中未注明的项目费用。 <br/>2. 其他私人消费。<br/>
	                3. 多出发地的产品需注明：该产品为上海出发，不包含苏州到上海的交通费用。
	                </s:elseif>
	                </td>
		 		  </tr>
 		  </s:if>
		 		  <tr>
		   			<td colspan="2">推荐项目</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea id="cost" name="RECOMMENDPROJECT" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("RECOMMENDPROJECT").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="middle">
		   			<s:if test='product.productType=="HOTEL"'>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                如产品本身不含保险，请注明：本产品不含旅游人身意外险，我们强烈建议游客购买。游客可在填写订单时勾选附件产品中的相关保险购买
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                如产品本身不含保险，请注明：本产品不含旅游人身意外险，我们强烈建议游客购买。游客可在填写订单时勾选附件产品中的相关保险购买
	                </s:elseif>
		   			</td>
		 		  </tr>
		 		  <tr>
		   			<td colspan="2">购物说明</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea name="SHOPPINGEXPLAIN" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("SHOPPINGEXPLAIN").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="top"><p>
	                <s:if test='product.productType=="HOTEL"'>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                </s:elseif>
	                </p></td>
		 		  </tr>
		 		  <tr>
		   			<td colspan="2">退款说明</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea id="REFUNDSEXPLANATION" name="REFUNDSEXPLANATION" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("REFUNDSEXPLANATION").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="top"><p>
	                <s:if test='product.productType=="HOTEL"'>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                </s:elseif>
	                </p></td>
		 		  </tr>
 		  <tr>
 		  	<td colspan="2"><b>重要提示</b><span class="require">[*]</span></td>
 		  </tr>
		          <tr>
		   			<td colspan="2">预定须知（原预订须知）</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea name="ORDERTOKNOWN" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("ORDERTOKNOWN").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="middle">
		   			<s:if test='product.productType=="HOTEL"'>
		   			1. 酒店地址、前台电话： <br/>2. 入住及离店时间： <br/>3. 证件要求：如遇酒店对入住人证件有要求，请说明<br/>
					4. 酒店套餐包含项目说明：此项针对酒店套餐产品包含项目的使用时间
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	               <font color="red">自由行产品</font><br/>
	                &nbsp;&nbsp;1. 景区门票优惠人群说明：景区优惠人群要求（儿童身高、年龄、老人年龄等）若有优惠人群价格，也请注明 <br/>
	                &nbsp;&nbsp;2. 预订限制：如遇景区对预订人地域有限制，请注明。酒店是否涉外，是否对证件有要求，请注明
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
			               ★预定须知（DEST页面景点销售信息中的预订须知参照以下模板）<br/>
				1.开放时间：(在明示景区的开闭园时间后,必须注明游客能够取票的时间)<br/>
				2.取票地点：（如取票地址不在景区或距离景区大门在200米以上需具体说明） <br/>
				3.入园凭证： （电子票：普通文字短信；电子票：二维码文字短信；电子票：二维码彩信；实体票； 特殊人群例如学生票等需要同时持有的有效证件）<br/>
				4.特殊人群：（必须注明儿童、学生、老人的特殊政策，如该景区对持有其他特别证件的人群有特殊政策的需注明）<br/>
				5.退改规则：（必须注明该门票在什么时间段可以全退改、什么时间段部分退改；一经预订付款后不得退改的也需注明；如由于景区临时活动造成游客至现场享受无法享受同等优惠可建议游客在景区直接购买优惠门票，游玩后致电驴妈妈退订该产品。）<br/>
				6.预订限制：（注明最晚预订时间和对预订数量等方面的限制）<br/>
				7.温馨提示：(对游客出行前需要注意的相关事项和准备做事先提醒建议)<br/>
				【建议您至少在闭园前前1小时取票以保证您的游玩时间】<br/>
				【电子门票短信不得删除不得转发，请在入园时出示有效的入园凭证】<br/>
				8.发票说明：【网上支付】驴妈妈网提供发票；【景区支付】景区提供发票。<br/>
				【以上景区信息仅供参考，具体以景区当日为准】<br/>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                1. 本产品仅限网上预订 <br/>2. 本产品已享受优惠组合价格，一经支付成功，不得退改，且不再参与其他任何形式的优惠活动
	                </s:elseif>
	                <s:if test='product.productType=="ROUTE"'>
	                	<br/>&nbsp;&nbsp;3. 出境产品存在多出发地情况，需提醒游客：该产品为XX出发，需自行前往XX
	                </s:if>
		   			</td>
		 		  </tr>
		 		  <tr>
		   			<td colspan="2">行前须知（原产品特别提示）</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea name="ACITONTOKNOW" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("ACITONTOKNOW").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="middle">
		   			<s:if test='product.productType=="HOTEL"'>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                <font color="red">自由行产品</font><br/>
	                &nbsp;&nbsp;1. 景区地址与取票方式：取票地址若不在景区需提供取票地点至景点距离大致为多少，若取票时间有限制也请在此说明 <br/> 
	                &nbsp;&nbsp;2. 景区营业时间： <br/> 
	                &nbsp;&nbsp;3. 酒店地址、电话、入住时间：酒店地址及联系方式，入住及离店时间要求 <br/> 
	                &nbsp;&nbsp;4. 酒店星级与房型、加床说明：可以加的，加床费多少，如何支付；不可加也有明显提示。若是特色房型（海景房、牡丹房、星海房等），需另行说明房型（大床、标间）等<br/> 
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
				                 ★行前须知：交通信息 <br/>
						自驾线路：（填写热门出发地至该景区的自驾线路）<br/>
						公交线路：（填写景区当地火车站、机场等标志性地标至景区的公共交通出行方式及路线）<br/>
						（结尾需添加【以上交通信息仅供参考，如您有更好建议欢迎致电我们】）<br/>
	                </s:elseif>
		   			<s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                </s:elseif>
		   			</td>
		 		  </tr>
		 		  <tr>
		   			<td colspan="2">旅游服务保障</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea name="SERVICEGUARANTEE" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("SERVICEGUARANTEE").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="top"><p>
	                <s:if test='product.productType=="HOTEL"'>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                </s:elseif>
	                </p></td>
		 		  </tr>
		 		  <tr>
		   			<td colspan="2">游玩提示</td>
		 		  </tr>
		 		  <tr>
		   			<td width="665px"><textarea name="PLAYPOINTOUT" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("PLAYPOINTOUT").content'/></textarea></td>
		   			<td width="40%" rowspan="2" valign="middle"><p>
	                <s:if test='product.productType=="HOTEL"'>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                </s:elseif>
	                </p></td>
		 		  </tr>
 		   <tr>
 		  	<td colspan="2"><b>交通信息</b></td>
 		  </tr>
 		  <tr>
   			<td width="665px"><textarea name="TRAFFICINFO" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("TRAFFICINFO").content'/></textarea></td>
   			<td width="40%" valign="middle">
   			<s:if test='product.productType=="HOTEL"'>
   			1. 自驾路线： <br/> 2. 公共交通： <br/>
            </s:if>
            <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
            <font color="red">自由行产品</font><br/>
            &nbsp;&nbsp;1. 自驾路线：<br/> &nbsp;&nbsp;2. 公共交通： <br/>
            </s:elseif>
            <s:elseif test='product.productType=="OTHER"'>
            
            </s:elseif>
            <s:elseif test='product.productType=="TICKET"'>
            1. 自驾路线： <br/>2. 公共交通：<br/>
            </s:elseif>
            <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	        </s:elseif>
   			</td>
 		  </tr>
 		  <tr>
 		  	<td colspan="2"><b>签证/签注</b><span>(用于出境游的产品)</span><em>保持现状</em></td>
 		  </tr>
 		  <tr>
   			<td width="665px"><textarea name="VISA" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("VISA").content'/></textarea></td>
   			<td width="40%">&nbsp;</td>
 		  </tr>
 		  <s:if test='product.productType=="ROUTE"'>
 		  <tr>
 		  	<td colspan="2"><b>旅游须知</b>&nbsp;&nbsp;&nbsp;<a href="javascript:addViewTravelTips(${productId })">新增</a></td> 
 		  </tr>
 		  <tr>
 		  	<td colspan="2">
 		  	<table bgcolor="#8C8C8C" style="width:100%">
 		  		<tr >
		 		  	<td bgcolor="#ffffff" >国家</td><td  bgcolor="#ffffff" width="100px">旅游须知名称</td><td  bgcolor="#ffffff" width="100px">操作</td> 
		 		  </tr>
 		  		<s:iterator id="viewTravelTips" var="viewTravelTipsItem" value="viewTravelTipsList">
		 		  <tr >
		 		  	<td bgcolor="#ffffff" >${viewTravelTipsItem.country}</td><td bgcolor="#ffffff" width="100px">${viewTravelTipsItem.tipsName}</td><td bgcolor="#ffffff" width="100px"><a target="_blank" href="http://www.lvmama.com/product/getTravelTips/${viewTravelTipsItem.travelTipsId}">预览</a>&nbsp;<a href="javascript:deleteViewTravelTips('${viewTravelTipsItem.viewTravelTipsId}')">删除</a></td>
		 		  </tr>
		 		  </s:iterator>
 		  	</table>
 		  	</td>
 		  </tr>
          </s:if> 		  
 		  <tr>
 		  	<td colspan="2"><b>产品特色</b></td>
 		  </tr>
 		  <tr>
   			<td width="665px"><textarea name="FEATURES" id="features" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("FEATURES").content'/></textarea></td>
   			<td width="30%">&nbsp;</td>
 		  </tr>
 		  <tr>
 		  	<td colspan="2"><b>内部提示（前台页面不显示，仅能客服在后台下单中查看）</b></td>
 		  </tr>
 		  <tr>
   			<td width="665px"><textarea name="INTERIOR" cols="text2" rows="text2" class="text2 sensitiveVad"><s:property value='viewPage.contents.get("INTERIOR").content'/></textarea></td>
   			<td width="40%">&nbsp;</td>
 		  </tr>
        </table>
        

   </div><!--row2 end-->
   <!--row3 end-->
   </form>
</div><!--main01 main05 end-->
</body>
</html>

