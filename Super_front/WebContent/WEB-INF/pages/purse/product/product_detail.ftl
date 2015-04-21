<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml
1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="version" content="4.0" />
    <title>财付通-我的钱包</title>
	<link href="https://img.tenpay.com/wallet/v4.0/css/wallet_v4.css" rel="stylesheet" type="text/css" />
    <link href="http://pic.lvmama.com/styles/super_v2/mybank/lvmama_v1.css?r=2916" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/mybank/mybank_func.js?r=2913"></script>
</head>
<body>
<script>
        <!--     
		jQuery(function(){
						
			//文本框弹出日历控件
			var inp_data = $("#inp_data")
			var cal_window = $("#cal_window");
			var close_btn = $("#close_btn");
			
			inp_data.click(function(){cal_window.show(0);})
			close_btn.click(function(){cal_window.hide(0);})
			cal_window.find("td").has("strong").each(function(){
				$(this).mouseover(function(){
				  $(this).addClass("current_bg");
				});
				$(this).mouseout(function(){
				  $(this).removeClass("current_bg");
				});
				$(this).click(function(){
					cal_window.hide(0);
					$("#productId").val($(this).attr("productId"));
					$("#date").val($(this).attr("date"));
					$("#productFrom").submit(); 
				})
			})
		})
		 function nextMonth(inId,outId){
				$("#"+inId).hide();
			    $("#"+outId).fadeIn();
		 }
		//-->
        </script>
<div class="lv-nav">
            <h2>在线预订</h2>
            <span>
            <@s.if test='prodCProduct!=null&&prodCProduct.prodProduct.productType=="ROUTE"'>
           		 <a href="/purse/route/index.do">预订首页</a> 
            </@s.if>
            <@s.else>
            	<a href="/purse/ticket/index.do">预订首页</a> 
            </@s.else>
            | <a href="/purse/order.do">我的订单</a> | <a href="/purse/help/order.do">预订帮助</a> | <a href="/purse/help/service.do">客服</a></span>
        </div>
        <div class="lvmama-body mg-t0 relative">
        	<span class="back-1" onclick="javascript:history.go(-1);">[返回上一页]</span>
        	<h3 class="c-c06"><#if prodCProduct.prodProduct.productName?length lt 20 >${prodCProduct.prodProduct.productName}<#else>${prodCProduct.prodProduct.productName?string[0..19]}</#if></h3>
            	<div class="bgc-div h-65">
                    <a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" />/product/<@s.property value="prodCProduct.prodProduct.productId" />"><img src="<@s.property value="prodCProduct.prodProduct.absoluteSmallImageUrl" />" width="90" height="65" class="fl mg-r12" /></a>
                    <div class="fl t-price">
                        <div class="u-price pd-t0">驴妈妈价：<strong class="fs-18"><span>￥</span><@s.property value="prodCProduct.prodProduct.sellPriceFloat"/></strong>起　<!--<a class="underline pointer" name="mmove_layer">起价说明</a>--></div>

                        <div class="layer_content" name="layer_content">
                            <dl>
                                <dt>起价说明：</dt>
                                <dd>本起价是指末包含附加服务（如单人房差、保险费等）的基本价格。您最终确认的价格将会随所选出行日期、人数及服务项目而相应变化。</dd>
                            </dl>
                        </div>
                        <div class="d-price">门市价：<del>￥<@s.property value="prodCProduct.prodProduct.marketPriceYuan"/></del></div>

                        <div class="s-price">节省 ￥<@s.property value="prodCProduct.prodProduct.marketPriceYuan-prodCProduct.prodProduct.sellPriceFloat"/> </div>
                    </div>
                    <@s.if test='prodCProduct.prodProduct.isSellable()'> 
                    		 <img id="inp_data" src="http://pic.lvmama.com/img/super_v2/mybank/b_bug_btn.gif" alt="立即预订" class="fr mg-t15 btn-pointer" />
                    	</@s.if><@s.else>
                    		  <button type="button" class="button-submit soldout">暂不可售</button>
                    	 </@s.else> 
                </div><!产品>
                <div class="pro-detail" id="detail">
                  <script>
                  jQuery(function($){
					$("#ticket").chajian({pro_tagmenu:".pro-list-tagmenu>li",pro_tagdetail:"div[name='pro_list']"});
				  });
                  </script>
                    <@s.if test='prodCProduct.prodProduct.productType=="ROUTE"'>
                    	<ul class="pro-list-tagmenu">
                    	<li class="current"><span>行程说明</span></li>
                    	<li><span>产品特色</span></li>
                    	<li><span>费用说明</span></li>
                    </@s.if>
                    <@s.else>
                    	<ul class="pro-list-tagmenu w-213"><li class="current"><span>产品特色</span></li>
                    </@s.else>
                    <li><span>重要提示</span></li><li><span>预订须知</span></li></ul>
 					 		 <div class="d-content " name="pro_list">
                             	<div>
 					 		 		<dl>
							        	 <#list viewJourneyList as vjl>
							                <dt>第<em>${vjl.seq}</em>天：<strong>${vjl.title?if_exists}</strong>
							                <#if (vjl.traffic)??>
								                <#if (vjl.traffic?index_of("airplane")>-1)>
								                	<span class="airplane"></span>
								                </#if>
								                <#if (vjl.traffic?index_of("bus")>-1)>
								                	<span class="bus"></span>
								                </#if>
								                <#if (vjl.traffic?index_of("ship")>-1)>
								                	<span class="ship"></span>
								                </#if>
								                <#if (vjl.traffic?index_of("train")>-1)>
								                	<span class="train"></span>
								                </#if>
								             </#if>
							                </dt>
							                <dd>
							                   <p>
							                	${vjl.contentBr?default("")}
							                    </p>
							                    <p>
							                	<#if (vjl.dinner)??>
							                   		 <strong>用餐</strong>  ${vjl.dinner?default("")}
							                   	 </#if>
							                   	  </p>
							                   	   <p>
							                   	 <#if (vjl.hotel)??>
							                    	<strong>住宿</strong>  ${vjl.hotel?default("")}
							                    </#if>
							                     </p>
							                </dd>
							                </#list>
            </dl>
 					 		 	</div>
                             </div>
		            		<div class="d-content display-none" name="pro_list">
			                    <#if (viewPage.contents.get('FEATURES'))??>
									<div> 
										<@s.property value="@com.lvmama.util.HtmlUtil@replaceATag(viewPage.contents.get('FEATURES').contentRn)" escape="false"/>
									</div>
								  </#if>
			                  </div>
			                  
		                   <@s.if test='prodCProduct.prodProduct.productType=="ROUTE"'>
			                    <div class="d-content display-none" name="pro_list">
			                    <#if (viewPage.contents.get('COSTCONTAIN'))??>
										<div>   
										${viewPage.contents.get('COSTCONTAIN').contentRn?if_exists}
										</div>
								</#if>
								 </div>
						 </@s.if>
                   
	                    <div class="d-content display-none" name="pro_list">
	 						<#if (viewPage.contents.get('IMPORTMENTCLEW'))??>
								<div> <@s.property value="@com.lvmama.util.HtmlUtil@replaceATag(viewPage.contents.get('IMPORTMENTCLEW').contentRn)" escape="false"/>
								</div>
							</#if>
	                    </div>
	                    <div class="d-content display-none" name="pro_list">
		                    <#if (viewPage.contents.get('ORDERTOKNOWN'))??>
								<div>${viewPage.contents.get('ORDERTOKNOWN').contentRn?if_exists}  </div>
								</#if>
		                </div>                	
	             </div>
        </div>

<#include "/WEB-INF/pages/purse/calendar.ftl"/>
</body>
</html>
