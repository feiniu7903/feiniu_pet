<#assign l=JspTaglibs["/tld/lvmama-tags.tld"] />
<!doctype html> 
<html> 
<head> 
<meta charset="utf-8" />
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/tuan/">
<@s.if test="type!=''||city!=''">
<@s.if test="type=='ticket'"><#global typeName='门票'/></@s.if>
<@s.elseif test="type=='hotel'"><#global typeName='酒店'/></@s.elseif>
<@s.elseif test="type=='freetour'"><#global typeName='自由行'/></@s.elseif>
<@s.elseif test="type=='around'"><#global typeName='跟团游'/></@s.elseif>
<@s.elseif test="type=='destroute'"><#global typeName='长途游'/></@s.elseif>
<@s.elseif test="type=='abroad'"><#global typeName='出境游'/></@s.elseif>
<title>【团购】<@s.property value="cities[city]" escape="false"/>${typeName}团购_<@s.property value="cities[city]" escape="false"/>${typeName}旅游团购,大全_驴妈妈旅游团购网</title>
<meta name="keywords" content="<@s.property value="cities[city]" escape="false"/>团购,<@s.property value="cities[city]" escape="false"/>旅游团购"/>
<meta name="description" content="【满意度100%】最新最全的<@s.property value="cities[city]" escape="false"/>景点${typeName}团购信息，驴妈妈旅游网为您提供最好的<@s.property value="cities[city]" escape="false"/>旅游团购服务，【抄底游】，一张也能团、一张也优惠！尽在驴妈妈团购网！"/>
</@s.if>
<@s.else>
<title>${comSeoIndexPage.seoTitle}</title>
<meta name="keywords" content="${comSeoIndexPage.seoKeyword}"/>
<meta name="description" content="${comSeoIndexPage.seoDescription}"/>
</@s.else>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_tg/lvtg.css"/>
<!--[if lte IE 6]>
<script src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js" ></script>
<script type="text/javascript">
   DD_belatedPNG.fix('.ie6png,.lvtg_shoplist_imginfo');
</script>
<![endif]-->

<#include "/common/coremetricsHead.ftl">
</head>

<body class="lvtg tuangou"> 
<@s.set var="pageMark" value="'tuangou'" />
<#include "/common/header.ftl">
<input type="hidden" value="freetour" id="pageName">

<ul class="hh_sub_recom clearfix">
	<li><i class="hh_icon_now"></i>当前城市：</li>
    <li><dl class="hh_now_place" id="hh-now-place">
			<dt><strong class="hh_now_city">${fromPlaceName}<i class="hh_icon"></i></strong></dt>
			<dd class="hh_now_city_group">
				<p style="overflow: hidden;zoom:1;">
                    <a href="javascript:switchIndex('SH','79','上海');">上海</a>
                    <a href="javascript:switchIndex('BJ','1','北京');" >北京</a>
                    <a href="javascript:switchIndex('CD','279','成都');" >成都</a>
                    <a href="javascript:switchIndex('GZ','229','广州');" >广州</a>
                </p>
                <p style="overflow: hidden;zoom:1;">
                    <a href="javascript:switchIndex('HZ','100','杭州');" >杭州</a>
                    <a href="javascript:switchIndex('NJ','82','南京');" >南京</a>
                    <a href="javascript:switchIndex('SZ','231','深圳');" >深圳</a>
                </p>
			</dd>
		</dl>
		<form method="post" id="switchIndexForm" action="http://www.lvmama.com/tuangou">
			<input type="hidden" name="fromPlaceCode" id="fromPlaceCode" value="${fromPlaceCode}" />
			<input type="hidden" name="fromPlaceId" id="fromPlaceId" value="${fromPlaceId}" />
			<input type="hidden" name="fromPlaceName" id="fromPlaceName" value="${fromPlaceName}" />
		</form>
    <!--now_place end-->
    </li>
    <#-- 
    <li>
       <span class="lvtg_headtxt">驴妈妈旅游团购：最专业、最保障、最新颖、最实惠的旅游团购！</span>
    </li>
    -->
    <li class="lvtg_headR">
        <a href="http://www.lvmama.com/tuangou/" class="lvtg_link_blue">往期团购</a>
        <a href="http://www.lvmama.com/edm/showSubscribeEmail.do" class="lvtg_email" target="_blank">邮件订阅</a>
    </li>
</ul><!--hh_sub_recom end-->

<!--以上是头部内容-->  
<div class="lvtg_main">
    <div class="lvtg_mainT">
        <div class="lvtg_sliderbox">
             <div class="lvtg_sliderlist">
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_focus')" status="status">
                   <a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="700" height="260"></a>
                </@s.iterator>
             </div>
             <table class="lvtg_sliderbar">
                <tr>
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_focus')" status="status">
                 <td>${title?if_exists}</td>
                 </@s.iterator>
                </tr> 
             </table>
        </div> 
        <div class="lvtg_Tinfo">
            <div class="lvtg_Tinfobox">
                 <ul class="lvtg_Tinfo_bar">
                    <li><span class="lvtg_Tinfo_sL">团购公告</span></li>
                    <li><span class="lvtg_Tinfo_sM">团购承诺</span></li>
                    <li><span class="lvtg_Tinfo_sR">客户服务</span></li>
                 </ul>
                  <div class="lvtg_Tinfo_cont" style="display: none;">
                    <div class="lvtg_Tinfo_txt t_notice" style="display: none;">
                        <p style="border-bottom: medium none;">驴妈妈团购频道推出新活动啦！此频道每周将定期推出大家心目中向往的目的地产品，快来投票您心仪的产品，我们来为您采购！</p>
                    </div>
                    <div class="lvtg_Tinfo_txt" style="display: block;">
                        <ul class="t_promise">
                            <li>优惠承诺 放心预订</li>
                            <li>驴妈妈入园保障</li>
                            <li>当季新颖产品</li>
                            <li>旅游热门目的地</li>
                         </ul>
                    </div>
                    <div class="lvtg_Tinfo_txt" style="display: none;">
                        <div class="t_server">
                            <p>服务时间：7 * 24小时</p>
                            <p>客服热线：1010 6060</p>
                            <p>客服传真：021-69108793</p>
                            <p>邮箱地址：service@lvmama.com</p>
                        </div>
                    </div>
               </div>
             </div><!--lvtg-Tinfobox-->  
             <div class="lvtg_Tinfo_QA">
                  <h3 class="lvtg_Tinfo_QAtit">常见预订Q&A</h3>
                  <ul class="lvtg_Tinfo_QAlist">
                    <li>驴妈妈团购预订流程</li>
                    <li>怎么才算预订成功呢？</li>
                    <li>选不到日期怎么办？</li>
                    <li>我可以改期或取消吗？</li>
                    <li>发票怎么索取？</li>
                    <li>行程不满意怎么办?</li>
                  </ul>
             </div>
        </div><!--Tinfo-->
    </div><!--T-->
    <div class="lvtg_selectbox">
       <div class="lvtg_slt_unit  lvtg_slt_list1"> 
         <span class="lvtg_slt_left">类型</span>
         <ul class="lvtg_slt_list">
          <li <#if Request["type"]=="all"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/all-all-all-1' rel="nofollow" >全部</a></li>
          <li <#if Request["type"]=="ticket"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/ticket-all-all-1' >门票</a></li>
          <li <#if Request["type"]=="hotel"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/hotel-all-all-1' >酒店</a></li>
          <li <#if Request["type"]=="freetour"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/freetour-all-all-1' >自由行</a></li>
          <li <#if Request["type"]=="around"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/around-all-all-1' >跟团游</a></li>
          <li <#if Request["type"]=="destroute"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/destroute-all-all-1' >长途游</a></li>
          <li <#if Request["type"]=="abroad"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/abroad-all-all-1' >出境游</a></li>
         </ul>
       </div>  <!--unit-->
       <div class="lvtg_slt_unit">
         <span class="lvtg_slt_left">目的地</span>
         <ul class="lvtg_slt_list  lvtg_slt_list_space">
            <li <#if Request["city"]=="all"> class="lvtg_slt_crt lvtg_crt" </#if> ><a href='http://www.lvmama.com/tuangou/${Request["type"]}-all-all-1' rel="nofollow" >全部</a></li>
           <@s.if test="cities.size>0">
				<#list cities.keySet() as key>	
             		<li<#if key == Request["city"]> class="lvtg_slt_crt lvtg_crt"  </#if>>
             		 <a href="http://www.lvmama.com/tuangou/${Request["type"]}-${key}-${Request["sort"]}-1" >${cities.get(key)}</a>
             		</li>
           		</#list>
			</@s.if>
         </ul>
         <span  class="lvtg_slt_sz">展开</span>
       </div> <!--unit--> 
    </div><!--lvtg_selectbox-->
    
    <div class="lvtg_shoplistTop">
       <ul class="lvtg_shoplistTop_l">
          <li <#if Request["sort"]=="all"> class="lvtg_order_crt"  </#if> > <a rel="nofollow" href="http://www.lvmama.com/tuangou/${Request["type"]}-${Request["city"]}-all-1" >默认</a></li>
          <li <#if Request["sort"]=="hot"> class="lvtg_order_crt"  </#if> > <a rel="nofollow" href="http://www.lvmama.com/tuangou/${Request["type"]}-${Request["city"]}-hot-1"  >热门 <i class="lvtg_order1"></i></a></li>
          <li <#if Request["sort"]=="price"> class="lvtg_order_crt"  </#if> > <a rel="nofollow" href="http://www.lvmama.com/tuangou/${Request["type"]}-${Request["city"]}-price-1" >价格 <i class="lvtg_order"></i></a></li>
       </ul>
       
       <div class="lvtg_page">
		<#if pageConfig.totalPageNum gt 1 >
		<@l.splitPage
			pageSize=pageConfig.pageSize totalPage=pageConfig.totalPageNum
			action="${pageConfig.url}" mode=10
			page=pageConfig.currentPage>
			</@l.splitPage>     
			</#if>   
         <!--Pages-->
       </div><!--lv_page-->
    </div><!--lvtg_shoplistTop-->
       
    <ul class="lvtg_shoplist clearfix" >
     <@s.iterator value="pageConfig.items" status="groupPrd">
        <li>
           <div class="lvtg_shoplist_imgbox">
           		<@s.iterator value="comPictureList" status="comPic">
					<@s.if test="#comPic.index==0"><a href="/product/<@s.property value="prodProduct.productId"/>" target="_blank" title="<@s.property value="prodProduct.productName"  escape="false"/>"><img alt="<@s.property value="prodProduct.productName"  escape="false"/>" src="${absolute580x290Url}" width="292" height="146" alt="${productName}"></a></@s.if>
				</@s.iterator>
                <div class="lvtg_shoplist_imginfo">剩余时间：<@s.if test="diff<172800000"><span class="deal-timeleft" num="${groupPrd.index}" diff="<@s.property  value="diff"/>"  id="counter${groupPrd.index}"></@s.if> <@s.else>三天以上</@s.else></span></div>
           </div>
           <div class="lvtg_shoplist_bar">
              <a href="/product/<@s.property value="prodProduct.productId"/>" target="_blank"><span class="lvtg_shoplist_dtllink"></span></a>
              <span class="lvtg_price">&yen;<em>${prodProduct.sellPriceYuan?if_exists}</em></span>
           </div>
           <p class="lvtg_shoplist_txt">
           <a href="/product/<@s.property value="prodProduct.productId"/>" target="_blank" title="<@s.property value="prodProduct.productName"  escape="false"/>">
            <@s.if test="prodProduct.isTicket()">门票</@s.if>
			<@s.elseif test="prodProduct.isGroup()">国内游</@s.elseif>
			<@s.elseif test="prodProduct.isFreeness()">自由行</@s.elseif>
			<@s.elseif test="prodProduct.isForeign()">出境游</@s.elseif>
			<@s.elseif test="prodProduct.isHotel()">国内酒店</@s.elseif>
            • <@s.property value="prodProduct.productName"  escape="false"/></a>
           </p>
           
           <div class="lvtg_shoplist_dtl">
               <table class="lvtg_shoplist_dtlT">
                  <tr>
                   <td class="lvtg_shoplist_dtlTL">市场价：<del>&yen;${prodProduct.marketPriceYuan?if_exists}</del></td>
                   <td width="100"><#-- 折扣<em><@s.property value="discount"/></em>折--></td>
                   <td class="lvtg_shoplist_dtlTR"><em><@s.property  value="orderCount"/></em>人已购买</td>
                  <tr> 
               </table>
               <!--<div class="lvtg_shoplist_txtlist">
               <@s.if test="managerRecommend!=null">
            		<@s.property value="managerRecommend"  escape="false"/>
            	</@s.if>
               </div>-->
           </div>
        </li>
        </@s.iterator>
    </ul>  
   

       <div class="lvtg_shoplistTop">
         <div  class="lvtg_page">
		<#if pageConfig.totalPageNum gt 1 >
		<@l.splitPage
			pageSize=pageConfig.pageSize totalPage=pageConfig.totalPageNum
			action="${pageConfig.url}" mode=10
			page=pageConfig.currentPage>
			</@l.splitPage>     
		</#if>   
         <!--Pages-->
       </div><!--lv_page-->
    </div><!--lvtg_shoplistTop-->


</div><!--main-->


<!----------------------------------------------------addend----------------------------------------------->         
<div class="lvtg_bottom">
	<div class="banner">
	   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_banner')" status="status">
           <a href="${url?if_exists}" target="_blank"><img width="980" height="80" border="0" src="${imgUrl?if_exists}" alt="${title?if_exists}"/></a>
        </@s.iterator>
	</div><!--banner end-->
	<!-- guide channel -->
	<#include "/common/guide_channel.ftl">
    </div><!--bottom end-->
    
    <div class="hh_cooperate">
	<#include "/WEB-INF/pages/staticHtml/friend_link/tuangou_footer.ftl">
    </div>
    <#include "/common/footer.ftl">
</div>
    <!--footer end-->
</div><!--bottom end-->
<!----------------------------------------------------addstart-----------------------------------------------> 
<div class="lv_pop lvtg_pop ie6png">
    <div class="lv_pop_inner">
     <div class="lv_pop_close"></div>
     <div class="lv_pop_tit">常见预订Q&A</div>
     <div class="lv_pop_cont">
         <dl class="lv_pop_dl lvtg_QApopTxt">
          <dt>我要怎么预订团购产品呢，操作流程是什么?</dt>
          <dd>我们的流程非常简单，首先请对您感兴趣的产品详读页面信息，然后选择您要游玩的日期，点击购买并支付，然后您会收到系统发出的确认短信，您凭订单确认短信及身份证至商家确认信息后即可游玩或入住，有时根据不同的产品类型，操作方式可能有些不同，我们都会在温馨提醒中具体说明。</dd>
         </dl>
         
         <dl class="lv_pop_dl lvtg_QApopTxt">
          <dt>我怎么才能知道是不是有房，怎么样才算预订成功，那我又要怎么入住、取票呢？</dt>
          <dd>请您在预订时点击"购买"会出现一张"游玩日期表"，深色字体代表该日期仍有库存，浅字字体代表此日期不在销售范围内或库存已售完，同时请您特别注意，我们只为您保留2个小时的支付时间，请您务必在2个小时内支付完成，我们会在您支付后马上保留位子并及时给您发送短信凭证，如果预订后未支付，我们将不对此类订单作处理政策。如遇特殊操作或是长途、出境游类的产品，我们会在产品页面的"温馨提醒"中说明或请您拨打客服热线1010-6060。</dd>
          <dt>那我心里就是不放心，就想让你们给我打电话呀？</dt>
          <dd>我们能够理解您的想法，但团购优惠产品就是采取抢拍方式且在线支付完成，同时我们会在页面上将游客在预订时、取票时需要注意的事项写清楚，您只需花上几分钟把我们提供的信息看完即可。</dd>
         </dl>
         
         <dl class="lv_pop_dl lvtg_QApopTxt">
          <dt>如果想要去的那天没房了怎么办？哪些时候能够保证一定有房呢？</dt>
          <dd>在预订时如果遇到入住日没房或是先被其他游客抢定了，我们的工作人员会及时通知您并为您更换日期或是退款，现时我们建议您尽量选择非节假日出行，首先是能够保证房量充足，其次节假日出行景区人流量大，环境势必会拥挤些，平时环境相对会清幽些。</dd>
         </dl>
         
         <dl class="lv_pop_dl lvtg_QApopTxt">
          <dt>如果临时有事无法前往我要怎么办，可以退或是改期吗？</dt>
          <dd>每个产品退订或更改出游日期的信息根据供应商提供的优惠政策均有不同，请您详读订购产品页面的退改说明，仍有不详之处页面，欢迎拨打客服电话1010-6060进行咨询。</dd>
         </dl>
         
         <dl class="lv_pop_dl lvtg_QApopTxt">
          <dt>游玩结束后发票在哪里领？</dt>
          <dd>如您需要开发票，请在游玩结束后至电1010-6060，告诉客服您的订单号及邮寄地址，我们会按照您在本网消费的实际金额给您开发票并将发票快递给您，发票内容可以为"服务费"、"门票代理费"、"住宿代理费"、"旅游费"。除本套餐外的其它消费，请在景区或酒店付款时向景区或酒店索要发票。</dd>
         </dl>
         
         <dl class="lv_pop_dl lvtg_QApopTxt">
          <dt>如果在行程中发现有不满意的地方，可以通过什么渠道反映？</dt>
          <dd>我们非常欢迎您给我们的产品提供好的意见与建议，同时在游玩过程中发现有任何问题您可以及时向游玩所在地的景区、酒店或导游反映，如果得不到解决可以直接email至services@lvmama.com ，我们会第一时间将您反映的情况转至酒店，并在24小时之内给您答复。</dd>
         </dl>
         
         <div class="lv_pop_page">
             <span class="lv_pop_prev lvtg_prevQA">上一条</span> |
             <span class="lv_pop_next lvtg_nextQA">下一条</span>
         </div> 
     </div> 
    </div>     
</div>
<div id="pageOver"></div>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"  charset="utf-8"></script>
<script type="text/javascript">
  function switchIndex(placeCode,placeId,placeName){
        var $switchIndexForm = $('#switchIndexForm');
        $('#fromPlaceCode').val(placeCode);
        $('#fromPlaceId').val(placeId);
        $('#fromPlaceName').val(placeName);
        $switchIndexForm.submit();
   }
</script>
<script type="text/javascript">
var clockArray = new Array();
function clockHandler(){
	for(var i=0;i<clockArray.length;i++){
			clockArray[i]();
	}
}
window.x_init_hook_clock = function () {
    if (jQuery("span.deal-timeleft").length == 1 && jQuery("span[num]").length == 0) {
    
        var a = parseInt(jQuery('span.deal-timeleft').attr('diff'));
        if (!a > 0) return;
        var b = (new Date()).getTime();
        var e = function () {
            var c = (new Date()).getTime();
            var ls = a + b +86400000- c;
            if (ls > 0) {
                var ld = parseInt(ls / 86400000); ls = ls % 86400000;
                var lh = parseInt(ls / 3600000); ls = ls % 3600000;
                var lm = parseInt(ls / 60000); ls = ls % 60000;
                var ls = (ls % 60000 / 1000).toFixed(0);
	
                if (ld > 0) {
                    var html = '<span>' + ld + '</span>天<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                } else {
                    var html = '<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                }
                
                jQuery('#counter').html(html);
            } else {
                jQuery("#counter").stopTime('counter');
                jQuery('#counter').html('团购已结束');
                window.location.reload();
            }
        };
       	window.setInterval(e,1000);
    }
    else {
        jQuery("span.deal-timeleft").each(function () {
            var a = parseInt(jQuery(this).attr('diff'));
            var numid = jQuery(this).attr("num");
            if (!a > 0) return;
            var b = (new Date()).getTime();
            var e = function () {
                var c = (new Date()).getTime();
                var ls = a + b +86400000- c;
                if (ls > 0) {
                    var ld = parseInt(ls / 86400000); ls = ls % 86400000;
                    var lh = parseInt(ls / 3600000); ls = ls % 3600000;
                    var lm = parseInt(ls / 60000);
                    var ls = (ls % 60000 / 1000).toFixed(0);
                    if (ld > 0) {
                        var html = '<span>' + ld + '</span>天<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                    } else {
                        var html = '<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                    }
                    jQuery('#counter' + numid).html(html);
                } else {
                    jQuery('#counter' + numid).html('团购已结束');
                    window.location.reload();
                }

            };
 			clockArray.push(e);
        });
        window.setInterval(clockHandler,1000);

    }

};

$(document).ready(function(){
	window.x_init_hook_clock();
})
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/ob_tg/tg.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js" charset="utf-8"></script>     
		<script>
        	cmCreatePageviewTag("团购频道首页", "N0001", null, null);
        </script>
</body>
</html>

