<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈团购网 - 驴妈妈旅游团购 打折门票团购 自由行线路团购 跟团游|<@s.property value="stationName"  escape="false"/>购物|<@s.property value="stationName"  escape="false"/>团购|<@s.property value="stationName"  escape="false"/>打折</title>
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
<meta name="description" content="驴妈妈旅游团购 打折门票团购 自由行线路团购 跟团游|<@s.property value="stationName"  escape="false"/>购物|<@s.property value="stationName"  escape="false"/>团购|<@s.property value="stationName"  escape="false"/>打折" />
<meta name="keywords" content="驴妈妈团购网, <@s.property value="stationName"  escape="false"/>, <@s.property value="stationName"  escape="false"/>驴妈妈团购网，<@s.property value="stationName"  escape="false"/>购物，<@s.property value="stationName"  escape="false"/>团购，<@s.property value="stationName"  escape="false"/>打折，团购，打折，精品消费，购物指南，消费指南" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/group.css"/>
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="/js/clock.js"></script>
<script type="text/javascript" src="/js/tuan.js"></script>
<script src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript">
function outSinaWeibo(){
		var _w = 86 , _h = 16; 
		var param = { 
		url:location.href, type:'6', count:'', /**鏄惁鏄剧ず鍒嗕韩鏁帮紝1鏄剧ず(鍙€?*/ 
		appkey:'87692682', /**鎮ㄧ敵璇风殑搴旂敤appkey,鏄剧ず鍒嗕韩鏉ユ簮(鍙€?*/
		title:'', /**鍒嗕韩鐨勬枃瀛楀唴瀹?鍙€夛紝榛樿涓烘墍鍦ㄩ〉闈㈢殑title)*/ 
		pic:'', /**鍒嗕韩鍥剧墖鐨勮矾寰?鍙€?*/ 
		ralateUid:'', /**鍏宠仈鐢ㄦ埛鐨刄ID锛屽垎浜井鍗氫細@璇ョ敤鎴?鍙€?*/ 
		rnd:new Date().valueOf() 
		} 
		var temp = []; 
		for( var p in param ){ 
		temp.push(p + '=' + encodeURIComponent( param[p] || '' ) ) 
		}		 
		document.write('<iframe allowTransparency="true" frameborder="0" scrolling="no" src="http://hits.sinajs.cn/A1/weiboshare.html?' + temp.join('&') + '" width="'+ _w+'" height="'+_h+'"></iframe>')
}

function shareSinaWebo(){
	var _w = 86 , _h = 16; 
		var param = { 
		url:location.href, type:'6', count:'', /**鏄惁鏄剧ず鍒嗕韩鏁帮紝1鏄剧ず(鍙€?*/ 
		appkey:'87692682', /**鎮ㄧ敵璇风殑搴旂敤appkey,鏄剧ず鍒嗕韩鏉ユ簮(鍙€?*/
		title:'', /**鍒嗕韩鐨勬枃瀛楀唴瀹?鍙€夛紝榛樿涓烘墍鍦ㄩ〉闈㈢殑title)*/ 
		pic:'', /**鍒嗕韩鍥剧墖鐨勮矾寰?鍙€?*/ 
		ralateUid:'', /**鍏宠仈鐢ㄦ埛鐨刄ID锛屽垎浜井鍗氫細@璇ョ敤鎴?鍙€?*/ 
		rnd:new Date().valueOf() 
		} 
		var temp = []; 
		for( var p in param ){ 
		temp.push(p + '=' + encodeURIComponent( param[p] || '' ) ) 
		}
		window.open("http://service.weibo.com/share/share.php?"+temp.join('&'),"_blank","width=615,height=505");
}
function shareDouban(){
	var d=document,e=encodeURIComponent,s1=window.getSelection,s2=d.getSelection,s3=d.selection,s=s1?s1():s2?s2():s3?s3.createRange().text:'',r='http://www.douban.com/recommend/?url='+e(d.location.href)+'&amp;title='+e(d.title)+'&amp;sel='+e(s)+'&amp;v=1',x=function(){if(!window.open(r,'douban','toolbar=0,resizable=1,scrollbars=yes,status=1,width=450,height=330'))location.href=r+'&amp;r=1'};if(/Firefox/.test(navigator.userAgent)){setTimeout(x,0)}else{x()}
}
function shareKaixin(){
	d=document;t=d.selection?(d.selection.type!='None'?d.selection.createRange().text:''):(d.getSelection?d.getSelection():'');void(kaixin=window.open('http://www.kaixin001.com/~repaste/repaste.php?&amp;rurl='+escape(d.location.href)+'&amp;rtitle='+escape(d.title)+'&amp;rcontent='+escape(d.title),'kaixin'));kaixin.focus();
}
function shareRenRen(){
	var s=screen;var d = document;var e = encodeURIComponent;if(/xiaonei\.com/.test(d.location))return;var f='http://share.xiaonei.com/share/buttonshare.do?link=',u=d.location,l=d.title,p=[e(u),'&amp;title=',e(l)].join('');function a(){if(!window.open([f,p].join(''),'xnshare',['toolbar=0,status=0,resizable=1,width=626,height=436,left=',(s.width-626)/2,',top=',(s.height-436)/2].join('')))u.href=[f,p].join('');};if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();
}
</script>
</head>
<@s.set name="currentTab" value="'groupIndex'"/>
<body>
<#include "/WEB-INF/pages/group/head.ftl"/>

<!--Content Area-->
<div class="shortinner">
	<!--Ad-->
	<#include "/WEB-INF/pages/group/include/ad.ftl" />
    <!--leftContent-->
    <div class="gropu-l-c">
    <@s.if test="groupPrdList.size<=0">
      <div class="group-bor">
      <div class="group-pro">
        		 <h1><strong>暂无团购产品</strong></h1>
       </div>
      </div>
    </@s.if>
        <!--循环显示今日的团购产品-->
    <@s.iterator value="groupPrdList" status="groupPrd">
    	<!---product-->
        <div class="group-bor">
        	<div class="group-pro">
        		<@s.if test="isRecommendPrd">
        			<div class="group-bg group-recom">推荐</div>
        		</@s.if>
        		<@s.else>
        			<div class="wid"><div class="proNum proNum${groupPrd.index+1}">推荐</div></div>
        		</@s.else>
                <h1><#include "/WEB-INF/pages/group/include/guide.ftl" /> • <@s.property value="CITY"  escape="false"/></strong><a href="/product/<@s.property value="prodProduct.productId"/>" target="_self"><@s.property value="prodProduct.productName"  escape="false"/></a></h1>
                <p class="recomment-icon">推荐语：<@s.property value='@com.lvmama.comm.utils.StringUtil@subStringStr(IMPORTMENT_CLEW,55)' escape="false"/></p>
	            <@s.if test="TAG_NAME!=null">
	               	<p class="lable-icon">标签：<@s.property value="TAG_NAME"  escape="false"/></p>
	             </@s.if>
                <!--leftContent-->
                <div class="g-c-l">
                	<div class="group-infor">
                    	<p class="price-look group-bg"><span>&yen;${prodProduct.sellPriceYuan?if_exists}</span><a href="/product/<@s.property value="prodProduct.productId"/>" target="_self">去看看</a></p>
                        <ol>
                        	<li>原价：<del>&yen;${prodProduct.marketPriceYuan?if_exists}</del></li>
                            <li>折扣：<em><@s.property value="discount"/>折</em></li>
                            <li>节省：<em>&yen;${prodProduct.marketPriceYuan-prodProduct.sellPriceYuan} </em></li>
                        </ol>
                         <@s.iterator value="productCouponList" status="coupon">
	                        <p class="infor-list"><span class="group-bg"></span><@s.property value="couponName"  escape="false"/></p>
						</@s.iterator>
                      	<#include "/WEB-INF/pages/group/include/shareweibo.ftl" />
                    </div>
                    <!--otherBg-->
                    <p class="other-bgcolor"><span class="group-bg"></span>距离团购结束还有：<br /><span class="deal-timeleft" num="${groupPrd.index}" diff="<@s.property  value="diff"/>"  id="counter${groupPrd.index}"></span></p>
                    <!--otherBg-->
                    <div class="other-bgcolor bug-cart">
                    	<p><strong><@s.property  value="orderCount"/></strong>人已购买成功</p>
                        <span class="group-bg"></span>
                        <@s.if test="orderCount>=MIN_GROUP_SIZE">
                        	团购已成功，<br />可继续购买…
                        </@s.if>
                        <@s.else>
                         	还需${MIN_GROUP_SIZE-orderCount}人，<br />本团购成功
                        </@s.else>
                    </div>
                </div>
                <!--rightContent-->
                <div class="g-c-r">                	 
                	 <@s.if test="comPictureList.size>0">
	                		<div class="scrollDiv">
								<ul class="scrollNum" id="scrollNum_${prodProduct.productId}">
									<@s.iterator value="comPictureList" status="comPic">
										<li <@s.if test="#comPic.index==0"> class="curNumLI"</@s.if> >${comPic.index+1}</li>
									</@s.iterator>
								</ul>
								<ul class="scrollImg"  id="scrollImg_${prodProduct.productId}">
									<@s.iterator value="comPictureList" status="comPic">
										<li <@s.if test="#comPic.index==0"> class="curImgLI" </@s.if> > <img  width=512 height=256 src="${absolute580x290Url}" alt="${productName}" /> </li>
									</@s.iterator>
								</ul>
							</div>
                		 <@s.if test="comPictureList.size>1">
	                		<script>
	                				var scrollImage${prodProduct.productId} = new ScrollImage("scrollNum_${prodProduct.productId}","scrollImg_${prodProduct.productId}",${comPictureList.size()},3000);
	                		</script>
                		</@s.if>
                	</@s.if>
                    <@s.if test="MANAGERRECOMMEND!=null">
                		<@s.property value="MANAGERRECOMMEND"  escape="false"/>
                	</@s.if>
                </div>
                <p class="clear"></p>
            </div>
        </div>
      </@s.iterator>
    </div>
    <!--rightContent-->
    <div class="gropu-r-c">
    	<div class="gropu-r-bg">
           	<!--显示团公告-->
            <#include "/WEB-INF/pages/group/include/groupNotice.ftl" />
			<#include "/WEB-INF/pages/group/include/lvmamaCommitment.ftl" />
        </div>
       <#include "/WEB-INF/pages/group/include/business.ftl" />
    </div>
    <!--clear-->
    <p class="clear"></p>
</div>
	
	
	

	<script type="text/javascript">
	<!-- 
	var bd_cpro_rtid="nHDdr0";
	//-->
	</script>
	<script type="text/javascript" src="http://cpro.baidu.com/cpro/ui/rt.js"></script>
	<noscript>
	<div style="display:none;">
	<img height="0" width="0" style="border-style:none;" src="http://eclick.baidu.com/rt.jpg?t=noscript&rtid=nHDdr0" />
	</div>
	</noscript>

	<!-- Google Code for &#20135;&#21697;-&#26053;&#28216;&#22242;&#36141; 540&#22825; Remarketing List -->
	<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 962608731;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "vBwfCO3KxAMQ2_yAywM";
	var google_conversion_value = 0;
	/* ]]> */
	</script>
	<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
	</script>
	<noscript>
	<div style="display:inline;">
	<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/962608731/?label=vBwfCO3KxAMQ2_yAywM&amp;guid=ON&amp;script=0"/>
	</div>
	</noscript>
  </body>
</html>
