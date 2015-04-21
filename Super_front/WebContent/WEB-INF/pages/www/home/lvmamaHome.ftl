<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" >
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/">
<link rel="dns-prefetch" href="//s1.lvjs.com.cn">
<link rel="dns-prefetch" href="//s2.lvjs.com.cn">
<link rel="dns-prefetch" href="//s3.lvjs.com.cn">
<title>${comSeoIndexPage.seoTitle?if_exists}</title>
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" > 
<meta name="keywords" content="${comSeoIndexPage.seoKeyword?if_exists}" > 
<meta name="description" content="${comSeoIndexPage.seoDescription?if_exists}" >
<meta property="qc:admins" content="276353266764651516375" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v6/header.css,/styles/v6/index.css" >
<#include "/common/coremetricsHead.ftl">
</head>

<body class="home">

<#include "/WEB-INF/pages/www/home/headerBody.ftl">

<div class="wrap">
	<div class="index_top clearfix">
    	<div class="hot_box">
        	<h3>热门目的地分类<i class="index_icon index_icon1"></i></h3>
            <div class="quick_list">
            	<div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/freetour" rel="nofollow" target="_blank" hidefocus="false">周边游</a></h4>
                        <p class="quick_hot">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ambitus')" status="st1">
                           	 <@s.if test="#st1.count<=6">
                           		 <a href="${url?if_exists}" target="_blank" hidefocus="false">${title?if_exists}</a>
                           	  </@s.if>
                            </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ambitus_right_region')" status="status" var="var" status="sp">
                              <dl>
                                  <dt>${title?if_exists}</dt>
                                  <dd> 
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="st">
                                   		 <a target="_blank" <@s.if test="(#sp.odd)&&(#st.count==2)">class="c_e38"</@s.if>  href="${url?if_exists}">${title?if_exists}</a>
                                   </@s.iterator>
                                 </dd>
                            </dl>
                         </@s.iterator>
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
                                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ambitus_leftImg')" status="status">
	                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
                                </@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
                    	 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ambitus_rightImg')" status="status">
	                    	<div class="quick_r">
	                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="198" height="300" alt="${title?if_exists}"></a>
	                        </div>
                        </@s.iterator>
                    </div>
                </div>
                
                
                  <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/abroad" rel="nofollow" target="_blank" hidefocus="false">出境游</a></h4>
                        <p class="quick_hot">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_shanghaiAmbitus')" status="status">
                           	 <a href="${url?if_exists}" target="_blank" hidefocus="false">${title?if_exists}</a>
                           </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_southeastAsia_right_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
		                           	 <a href="${url?if_exists}" <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> target="_blank" hidefocus="false">${title?if_exists}</a>
		                            </@s.iterator>
                                </dd>
                            </dl>
                         </@s.iterator>    
                         
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_exit_leftImg')" status="status">
	                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
                               		</@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_exit_rightImg')" status="status">
                    	<div class="quick_r">
                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
                        </div>
                        </@s.iterator>
                    </div>
                </div>
               
                <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/destroute" rel="nofollow" target="_blank" hidefocus="false">国内游</a></h4>
                        <p class="quick_hot">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_home')" status="status">
                                	   <a target="_blank"  href="${url?if_exists}">${title?if_exists}</a>
                           </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_home_right_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
                                	   <a target="_blank" <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> href="${url?if_exists}">${title?if_exists}</a>
                           		   </@s.iterator>
                                </dd>
                            </dl>
                       </@s.iterator>
                       	 <!--左边两张-->
                         <div class="quick_l_box">
                                <ul class="quick_l_img">
                                	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_home_leftImg')" status="status">
	                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
                               		</@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
                        <!--右边1张图-->
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_home_rightImg')" status="status">
                    	<div class="quick_r">
                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
                        </div>
                        </@s.iterator>
                    </div>
                </div>
                
                
              
                
                   <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/youlun/" target="_blank" hidefocus="false">邮轮</a>
                        <i class="index_icon icon_new2"></i></h4>
                        <p class="quick_hot">
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_cruises')" status="status">
		                           	 <a href="${url?if_exists}" target="_blank" hidefocus="false">${title?if_exists}</a>
		                   	</@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                    		<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_cruises_right_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
		                           	 <a href="${url?if_exists}" <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> target="_blank" hidefocus="false">${title?if_exists}</a>
		                   		  </@s.iterator>
                                </dd>
                            </dl>
                            </@s.iterator>
                            
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
	                                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_cruises_leftImg')" status="status">
		                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
	                               	</@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
	                  <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_cruises_rightImg')" status="status">
                    	<div class="quick_r">
                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
                        </div>
	                  </@s.iterator>
                    </div>
                </div>
                <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/zt/lvyou/qianzheng" rel="nofollow" target="_blank" hidefocus="false">签证</a></h4>
                        <p class="quick_hot">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_visa')" status="status">
		                           	 <a href="${url?if_exists}" target="_blank" hidefocus="false">${title?if_exists}</a>
		                     </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                    	
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_visa_right_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
		                           	 <a href="${url?if_exists}" <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> target="_blank" hidefocus="false">${title?if_exists}</a>
		                            </@s.iterator>
                                </dd>
                            </dl>
                         </@s.iterator>    
                    	
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
                               		 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_visa_leftImg')" status="status">
		                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
	                               	</@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
	                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_visa_rightImg')" status="status">
	                    	<div class="quick_r">
	                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
	                        </div>
		                  </@s.iterator>
                    </div>
                </div>
                
                
                  <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/zt/lvyou/lvxing/index.html" rel="nofollow" target="_blank" hidefocus="false">开心驴行</a></h4>
                        <p class="quick_hot">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_happyTravel')" status="status">
                                	   <a target="_blank"  href="${url?if_exists}">${title?if_exists}</a>
                            </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                    		<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_happyTravel_right_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
		                           	 <a href="${url?if_exists}"  <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> target="_blank" hidefocus="false">${title?if_exists}</a>
		                            </@s.iterator>
                                </dd>
                            </dl>
                         </@s.iterator>    
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
                                     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_happyTravel_leftImg')" status="status">
		                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
	                               	</@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
                    	 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_happyTravel_rightImg')" status="status">
	                    	<div class="quick_r">
	                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
	                        </div>
		                  </@s.iterator>
                    </div>
                </div>
                
              
             
                <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.lvmama.com/company" rel="nofollow" target="_blank" hidefocus="false">定制游</a></h4>
                        <p class="quick_hot">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_customSwim')" status="status">
		                           	 <a href="${url?if_exists}" target="_blank" hidefocus="false">${title?if_exists}</a>
		                   		  </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                    		<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_customSwim_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
		                           	 <a href="${url?if_exists}" <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> target="_blank" hidefocus="false">${title?if_exists}</a>
		                            </@s.iterator>
                                </dd>
                            </dl>
                         </@s.iterator>    
                    		
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_customSwim_leftImg')" status="status">
		                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
	                               	</@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
                    	<div class="quick_r">
	                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_customSwim_rightImg')" status="status">
		                    	<div class="quick_r">
		                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
		                        </div>
			                  </@s.iterator>
                        </div>
                    </div>
                </div>
                <div class="quick_nav">
                	<div class="quick_nav_t">
                        <h4><a href="http://www.feilvway.com" rel="nofollow" target="_blank" hidefocus="false">飞驴湾</a></h4>
                        <p class="quick_hot">
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_flyingDonkeysBay')" status="status">
		                           	 <a href="${url?if_exists}" target="_blank" hidefocus="false">${title?if_exists}</a>
		                   		  </@s.iterator>
                        </p>
                    </div>
                    <div class="quick_main clearfix">
                    	<div class="quick_l">
                            <dl>
                          <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_flyingDonkeysBay_region')" status="sp">
                            <dl>
                                <dt>${title?if_exists}</dt>
                                <dd>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="status">
		                           	 <a href="${url?if_exists}" <@s.if test="(#sp.odd)&&(#status.count==2)">class="c_e38"</@s.if> target="_blank" hidefocus="false">${title?if_exists}</a>
		                            </@s.iterator>
                                </dd>
                            </dl>
                         </@s.iterator>    
                             
                            <div class="quick_l_box">
                                <ul class="quick_l_img">
	                               <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_flyingDonkeysBay_leftImg')" status="status">
			                                    <li><a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="100" alt="${title?if_exists}"></a></li>
		                           </@s.iterator>
                                </ul>
                            </div>
                            
                        </div>
	                	 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_flyingDonkeysBay_rightImg')" status="status">
	                    	<div class="quick_r">
	                        	<a href="${url?if_exists}" title="${title?if_exists}" target="_blank"><img src="${imgUrl?if_exists}"  width="198" height="300" alt="${title?if_exists}"></a>
	                        </div>
		                  </@s.iterator>
                    </div>
                </div>
            </div>
        </div>
        <!--焦点图 -->
        <div class="banner_box">
        	<div class="banner_box1 js_banner1">
                <div class="banner_list_box">
                    <ul class="banner_list">
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_bigfocus1&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#520px#360px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_bigfocus2&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#520px#360px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_bigfocus3&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#520px#360px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_bigfocus4&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#520px#360px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_bigfocus5&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#520px#360px"></li>
                       </ul>
                </div>
                <ul class="banner_tab">
                    <li class="active"><span></span></li>
                    <li><span></span></li>
                    <li><span></span></li>
                    <li><span></span></li>
                    <li><span></span></li>
                </ul>
                <span class="btn_l"></span>
                <span class="btn_r"></span>
            </div>
            <div class="banner_box2">
                <ul class="banner_small">
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_xfocus1&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#260px#130px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_xfocus2&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#260px#130px"></li>
                </ul>
            </div>
        </div>
        
        <div class="banner_r">
        	<a data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_rightfocus1&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#200px#245px"></a>
            <a data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_rightfocus2&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#200px#245px"></a>
        </div>
        
        <div class="top_side">
        	
        	<ul class="superiority">
            	<li>
                	<h4>价格保证</h4>
                    <p>同类产品，保证低价</p>
                    <i class="index_icon icon_bao"></i>
                </li>
                <li>
                	<h4>退订保障</h4>
                    <p>因特殊情况影响出行，保证退订</p>
                    <i class="index_icon icon_kuai"></i>
                </li>
                <li>
                	<h4>救援保障</h4>
                    <p>旅途中遇意外情况，保证救援</p>
                    <i class="index_icon icon_tui"></i>
                </li>
                <li>
                	<h4>7x24小时服务</h4>
                    <p>快速响应，全年无休</p>
                    <i class="index_icon icon_pei"></i>
                </li>
            </ul>
            <p class="announ_gd"><a class="btn_gd" href="http://www.lvmama.com/public/user_security" rel="nofollow" target="_blank">更多服务保障<i class="icon_arrow"></i></a></p>
            
            <div class="announ_box">
            	<ul class="announ_tab JS_announ_tab">
                	<li class="active">会员活动</li>
                    <li>网站公告</li>
                </ul>
                <dl class="announ_list_box">
                	<dd style="display:block;">
                    	<ul class="announ_list">
                    	<@s.iterator value="recommendInfoMainList.get('${channelPage}_huodong')" status="st">
                          <li><i class="icon_dian"></i><a href="${url?if_exists}" title="${title?if_exists}" rel="nofollow" target="_blank">${title?if_exists}</a></li>
						</@s.iterator>
                        </ul>
                     </dd>
                    <dd>
                    	<ul class="announ_list">
							<@s.iterator value="recommendInfoMainList.get('${channelPage}_gonggao')" status="st">
                          	<li><i class="icon_dian"></i><a href="${url?if_exists}" title="" rel="nofollow" target="_blank">${title?if_exists}</a></li>
							</@s.iterator>                        </ul>
                        <p class="announ_gd"><a class="btn_gd" href="http://www.lvmama.com/info/" rel="nofollow" target="_blank">更多<i class="icon_arrow"></i></a></p>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
    
    <div class="main_box clearfix">
    	
    	<!--左侧栏-->
    	<div class="main_l">
        	<!--马上出发-->
        	<div class="chufa_box">
        		<div class="main_title">
                	<a class="title_gd" href="http://www.lvmama.com/freetour" rel="nofollow" target="_blank">更多<i class="icon_arrow"></i></a>
                	<h3>马上出发</h3>
                    <span class="main_title_small">拎包就走,轻松出游</span>
                </div>
                <div class="chufa_mian clearfix">
                    <div class="fachu_city_box">
                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_mashangchufa')" status="st" var="var">
                            <@s.if test="#st.first">
                         <div class="fachu_title bg_sky"><!--此处bg_sky要可以后台录入，专门换颜色的，共有6种颜色可换-->
                            <a href="${url?if_exists}" target="_blank">
	                             <h3>
			                           	 ${title?if_exists}        
			                    </h3>
	                            <p>   
	                                ${bakWord1?if_exists}             
			                   	 </p>
                    	 </a>
                    	 </div>
                    	  </@s.if>
                      </@s.iterator>
                    	
                        <dl class="fachu_city_list">
	                            <dt> 
	                             <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_not_to_be_missed_place')" status="st" var="var">
		                           	  <@s.if test="#st.first">${title?if_exists}</@s.if>        
		                   		 </@s.iterator>
		                   		   </dt>
	                            <dd class="h_50">
	                               <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_not_to_be_missed_place')" status="st">
		                           	 <@s.if test="!(#st.first)"><a   <@s.if test="#st.count==3"> class="c_e38" </@s.if> href="${url?if_exists}" target="_blank">${title?if_exists}</a></@s.if>
		                   		   </@s.iterator>
	                            </dd>
	                            <dt>
	                               <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_not_to_be_missed_destination')" status="st">
		                           	 <@s.if test="#st.first">  ${title?if_exists} </@s.if>
		                   		   </@s.iterator>
		                   		   </dt>
	                            <dd class="h_75">
	                           	   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_not_to_be_missed_destination')" status="st">
		                           	 <@s.if test="!(#st.first)"><a href="${url?if_exists}" <@s.if test="#st.count==3"> class="c_e38" </@s.if>  target="_blank">${title?if_exists}</a></@s.if>
		                   		   </@s.iterator>
	                            </dd>
                        </dl>
                    </div>
                   
                   <!--马上出发大图1-->
                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_bigImg')" status="st">
                     <@s.if test="#st.first">
                    <div class="chufa_big css3_run css3_y-5 css3_shadow">
                        <a class="block" href="${url?if_exists}" title="${title?if_exists}" target="_blank">
                            <img to="${imgUrl?if_exists}" width="623" height="312">
                            <div class="product_info">
                                <h4>${title?if_exists}</h4>
                                <@s.if test="bakWord1!=null && bakWord1.length()>0"><p><span>¥${bakWord1?if_exists}</span>${remark?if_exists}</p></@s.if>
                            </div>
                            <@s.if test="bakWord2!=null && bakWord2.length()>0"><span class="tag_box"><b>${bakWord2?if_exists}</b></span></@s.if>
                        </a>
                    </div>
                    </@s.if>
                   </@s.iterator>
                   <!--马上出发小图3-->
                   
                    <div class="chufa_list_box">
                        <ul class="chufa_list clearfix css3_run css3_liy-5 css3_shadow_li">
                      	  <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_smallImg')" status="st">
                      	  <@s.if test="#st.count<=3">
                            <li>
                                <a class="block" href="${url?if_exists}" title="${title?if_exists}" target="_blank">
                                    <img to="${imgUrl?if_exists}" width="306" height="204" alt="">
                                    <div class="product_info">
                                        <h4>${title?if_exists}</h4>
                                        <@s.if test="bakWord1!=null && bakWord1.length()>0"><p><span>¥${bakWord1?if_exists}</span>${remark?if_exists}</p></@s.if>
                                    </div>
                                   <@s.if test="bakWord2!=null && bakWord2.length()>0"> <span class="tag_box"><b>${bakWord2?if_exists}</b></span></@s.if>
                                </a>
                            </li>
                            </@s.if>
                         </@s.iterator>
                        </ul>
                    </div>
                </div>
            </div>
            
            <!--出境游-->
            <div class="xianlu_box clearfix">
        		<div class="main_title">
                	<a class="title_gd" href="http://www.lvmama.com/abroad" rel="nofollow" target="_blank">更多<i class="icon_arrow"></i></a>
                	<div class="main_title_list">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_exit_left_more')">
                    	<a href="${url?if_exists}" target="_blank">${title?if_exists}<i class="icon_arrow"></i></a>
                       </@s.iterator> 	
                    </div>
                	<h3>海外旅行</h3><span class="main_title_small">世界有多大,我就玩多远</span>
                </div>
                <div class="banner_box3 js_banner3">
                    <div class="banner_list_box">
                        <ul class="banner_list">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_exit_left')">
                            <li>
                            	<a href="${url?if_exists}" target="_blank" title="${title?if_exists}">
                            	<img to="${imgUrl?if_exists}" width="226" height="314" alt="">
                                <div class="img_text">
                                	<p>${bakWord1?if_exists}</p>
                                    <h4>${bakWord2?if_exists}</h4>
                                   <@s.if test="bakWord3!=null && bakWord3.length()>0"> <span><big>${bakWord3?if_exists}</big></span></@s.if>
                                </div>
                                </a>
                            </li>
                        </@s.iterator> 
                        </ul>
                    </div>
                    <ul class="banner_tab">
                        <li class="active"><span></span></li>
                    </ul>
                    <span class="btn_l"></span>
                    <span class="btn_r"></span>
                </div>
                
                
                <ul class="product_list clearfix css3_run css3_liy-5  css3_shadow_li">
                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_exit_right')">
                    <li>
                        <a class="block" href="${url?if_exists}" title="${title?if_exists}" target="_blank">
                            <img to="${imgUrl?if_exists}" width="226" height="151" alt="">
                            <div class="product_text">
                           	<@s.if test="bakWord1!=null && bakWord1.length()>0"><span>¥${bakWord1?if_exists}</span></@s.if>
                                <h4>${title?if_exists}</h4>
                            </div>
                        </a>
                    </li>
                 </@s.iterator>   
                </ul>
            </div>
            
            
               <!--国内游-->
            <div class="xianlu_box clearfix">
        		<div class="main_title">
                	<a class="title_gd" href="http://www.lvmama.com/destroute" rel="nofollow" target="_blank">更多<i class="icon_arrow"></i></a>
                	<div class="main_title_list">
                	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_home_left_more')">
                    	<a href="${url?if_exists}" target="_blank">${title?if_exists}<i class="icon_arrow"></i></a>
                    </@s.iterator> 	
                        
                    </div>
                	<h3>玩转国内</h3><span class="main_title_small">行走山水间,足迹遍中国</span>
                </div>
                <!--国内游左边切换图-->
                <div class="banner_box3 js_banner2">
                    <div class="banner_list_box">
                        <ul class="banner_list">
                       	    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_home_left')">
                            <li>
                            	<a href="${url?if_exists}" target="_blank" title="${title?if_exists}">
                            	<img to="${imgUrl?if_exists}" width="226" height="314" alt="">
                                <div class="img_text">
                                	<@s.if test="bakWord1!=null && bakWord1.length()>0"><p>${bakWord1?if_exists}</p></@s.if>
                                    <@s.if test="bakWord2!=null && bakWord2.length()>0"><h4>${bakWord2?if_exists}</h4></@s.if>
                                    <@s.if test="bakWord3!=null && bakWord3.length()>0"><span><big>${bakWord3?if_exists}</big></span></@s.if>
                                </div>
                                </a>
                            </li>
                            </@s.iterator>
                        </ul>
                    </div>
                    <ul class="banner_tab">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_home_left')">
                    	<@s.if test="#st.count==1">
                        <li class="active"><span></span></li>
                        </@s.if>
                        <@s.else>
                         <li><span></span></li>
                        </@s.else>
                     </@s.iterator>
                    </ul>
                    <span class="btn_l"></span>
                    <span class="btn_r"></span>
                </div>
                
                <!--国内游右边图-->
                <ul class="product_list clearfix css3_run css3_liy-5 css3_shadow_li">
                	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_home_right')">
                    <li>
                        <a class="block" href="${url?if_exists}" title="${title?if_exists}" target="_blank">
                            <img to="${imgUrl?if_exists}" width="226" height="151" alt="">
                            <div class="product_text">
                            <@s.if test="bakWord1!=null && bakWord1.length()>0"><span>¥${bakWord1?if_exists}</span></@s.if>
                                <h4>${title?if_exists}</h4>
                            </div>
                        </a>
                    </li>
                	 </@s.iterator>   
                </ul>
            </div>
            
            
            <!--他们去哪玩了-->
            <div class="quna_box clearfix">
        		<div class="main_title">
                	<h3>他们去哪玩</h3><span class="main_title_small">晒晒旅程，分享快乐</span>
                </div>
                <div class="main_title2">
                    <a href="http://www.lvmama.com/zt/promo/tiyan/" rel="nofollow" target="_blank">【新产品免费体验员招募】报名参与<i class="icon_arrow"></i></a>
                	<h3>最受关注的行程</h3>
                </div>
                <div class="product_list_box">
                    <ul class="product_list clearfix css3_run css3_liy-5  css3_shadow_li">
                      <@s.iterator value="(recommendInfoMainList).get('${channelPage}_zuishouguanzhuxincheng')" status="st" var="var">
                       <@s.if test="#st.count<=5">
                        <li class="<@s.if test="#st.count==2" >w_464</@s.if><@s.if test="#st.count==5" > quna_last</@s.if> ">
                            <a class="block" href="${url?if_exists}" title="" target="_blank">
                                <img to="${imgUrl?if_exists}" <@s.if test="#st.count==2" >width="464" height="238"</@s.if><@s.else> width="226" height="113"</@s.else> alt="">
                                <div class="product_text">
                                    <h4>${title?if_exists}</h4>
                                </div>
                                 <div class="tag_box2">
                                 	<@s.if test="null!=bakWord1" ><span title="${bakWord1?if_exists}次浏览"><i class="index_icon icon_yan"></i>${bakWord1?if_exists}</span> </@s.if>
                                    <@s.if test="null!=bakWord2" ><span ><i class="index_icon icon_tu"></i>${bakWord2?if_exists}</span></@s.if>
                           </div>
                            </a>
                        </li>
                        </@s.if>
                       </@s.iterator>
                    </ul>
                </div>
                
                <div class="quna_b clearfix">
                	<div class="quna_b_l">
                    	<div class="main_title2"><h3>好评如潮的目的地</h3></div>
                        <ul class="product_list clearfix css3_run css3_liy-5 css3_shadow_li">
                       <@s.iterator value="(recommendInfoMainList).get('${channelPage}_haopingrucao')" status="st" var="var">
                       <@s.if test="#st.count<=6">
                            <li>
                                <a class="block" href="${url?if_exists}" title="${title?if_exists}" target="_blank">
                                    <img to="${imgUrl?if_exists}" width="226" height="113" alt="${title?if_exists}">
                                    <div class="product_text">
                                        <h4>${title?if_exists}</h4>
                                    </div>
                                    <div class="tag_box2">
                                        <span>${bakWord1?if_exists}<dfn>条点评</dfn></span>
                                    </div>
                                </a>
                            </li>
                            </@s.if>
                            </@s.iterator>
                        </ul>
                        
                    </div>
                    <div class="quna_b_r">
                    	<div class="main_title2">
                            <a href="http://www.lvmama.com/guide/gfgl/" rel="nofollow" target="_blank">更多<i class="icon_arrow"></i></a>
                            <h3>人气官方攻略</h3>
                        </div>
                    	<div class="guide_box">
                        	<ul class="guide_list">
                         <@s.iterator value="(recommendInfoMainList).get('${channelPage}_gonglv')" status="st" var="var">
                          <@s.if test="#st.count<=2">
                            	<li>
                                	<a href="${url?if_exists}" target="_blank" title="${title?if_exists}">
                                	<img to="${imgUrl?if_exists}" width="107" height="161" alt="${title?if_exists}"></a>
                                    <div class="guide_text">
                                        <h5>${bakWord1?if_exists}</h5>
                                        <p>${bakWord2?if_exists}更新</p>
                                        <p>${bakWord3?if_exists}次下载</p>
                                    </div>
                                </li>
                                </@s.if>
                            </@s.iterator>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        
        <!--右侧栏-->
        <div class="main_r">
        	
            <div class="main_title"><h3>团购</h3></div>
            <@s.iterator value="recommendInfoMainList.get('${channelPage}_tuangou')" status="st">
               <@s.if test="#st.count<=2">
	            <div class="tuan_box">
	                <a class="block" href="${url?if_exists}" target="_blank" title="${title?if_exists}">
	                    <img to="${imgUrl?if_exists}" width="210" height="140" alt="">
	                    <div class="product_text">
	                        <h4>${title?if_exists}</h4>
	                    </div>
	                   <@s.if test="null!=bakWord2">  <span class="tag_box"><i class="index_icon icon_lj"></i>${bakWord2?if_exists}</span></@s.if>
	                </a>
	                <div class="tuan_jg">
	                    <a class="btn_qg css3_run" href="${url?if_exists}" target="_blank">立即抢购</a>
	                    <dfn>¥<big>${memberPrice?if_exists}</big></dfn> 
	                </div>
	                <div class="tuan_down">
 	                    <span class="tuan_num">${bakWord1?if_exists}</span>人已参与
	                </div>
	            </div>
	            </@s.if>
            </@s.iterator>
            
            <div class="main_title"><h3>促销优惠</h3></div>
            <ul class="cx_list css3_run css3_lix-5">
                    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_button01&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#240px#65px"></li>
                    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_button02&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#240px#65px"></li>
                    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_button03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#240px#65px"></li>
                    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_button04&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#240px#65px"></li>
                    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_button05&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#240px#65px"></li>
             </ul>
            
            <!--新增模块-->
            <@s.if test="null != prodHotSellList && prodHotSellList.size()>0">
            <div class="main_title"><h3>热销排行</h3></div>
            <ul class="rx_tab">
            <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_ZZY') && prodHotSellList.get('${fromPlaceCode}_ZZY').size()>0">
                <li class="active" >自助游<i class="icon_arrow"></i></li>
            </@s.if>
            <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_MPL') && prodHotSellList.get('${fromPlaceCode}_MPL').size()>0">
                <li class="active" >门票类<i class="icon_arrow"></i></li>
            </@s.if>
             <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_GTY') && prodHotSellList.get('${fromPlaceCode}_GTY').size()>0">
                <li>跟团游<i class="icon_arrow"></i></li>
            </@s.if>
            <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_JDL') && prodHotSellList.get('${fromPlaceCode}_JDL').size()>0">
                <li>酒店类<i class="icon_arrow"></i></li>
            </@s.if>
            <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_ZYX') && prodHotSellList.get('${fromPlaceCode}_ZYX').size()>0">
                <li>自由行<i class="icon_arrow"></i></li>
            </@s.if>
            <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_CJY') && prodHotSellList.get('${fromPlaceCode}_CJY').size()>0">
                <li>出境游<i class="icon_arrow"></i></li>
            </@s.if>
            </ul>
            
            <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_ZZY') && prodHotSellList.get('${fromPlaceCode}_ZZY').size()>0">
            <ul style="display: block;" class="rx_list">
             <@s.iterator value="prodHotSellList.get('${fromPlaceCode}_ZZY')" status="st" var="var">
               <@s.if test = "#st.count==1">
                <li class="rx_list_first">
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <img width="210" height="140" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <div class="product_text">
                            <h4>${productName?if_exists }</h4>
                        </div>
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a>
                    <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b><del>￥${marketPrice?if_exists }</del></p>
                </li>
              </@s.if><@s.else>
                <li>
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <h5>${productName?if_exists }</h5>
                        <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b></p>
                        <img width="90" height="60" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a> 
                </li>
                </@s.else>
               </@s.iterator>
             </ul>
             </@s.if>
             
             <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_MPL') && prodHotSellList.get('${fromPlaceCode}_MPL').size()>0">
             <ul style="display: block;" class="rx_list">  
               <@s.iterator value="prodHotSellList.get('${fromPlaceCode}_MPL')" status="st" var="var">
               <@s.if test = "#st.count==1">
                <li class="rx_list_first">
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <img width="210" height="140" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <div class="product_text">
                            <h4>${productName?if_exists }</h4>
                        </div>
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a>
                    <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b><del>￥${marketPrice?if_exists }</del></p>
                </li>
              </@s.if><@s.else>
                <li>
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <h5>${productName?if_exists }</h5>
                        <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b></p>
                        <img width="90" height="60" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a> 
                </li>
                </@s.else>
               </@s.iterator>
             </ul>  
             </@s.if>
             
             <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_GTY') && prodHotSellList.get('${fromPlaceCode}_GTY').size()>0">
             <ul class="rx_list">  
               <@s.iterator value="prodHotSellList.get('${fromPlaceCode}_GTY')" status="st" var="var">
               <@s.if test = "#st.count==1">
                <li class="rx_list_first">
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <img width="210" height="140" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <div class="product_text">
                            <h4>${productName?if_exists }</h4>
                        </div>
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a>
                    <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b><del>￥${marketPrice?if_exists }</del></p>
                </li>
              </@s.if><@s.else>
                <li>
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <h5>${productName?if_exists }</h5>
                        <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b></p>
                        <img width="90" height="60" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a> 
                </li>
                </@s.else>
               </@s.iterator>
             </ul>  
             </@s.if>
             
             <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_JDL') && prodHotSellList.get('${fromPlaceCode}_JDL').size()>0">
             <ul class="rx_list">  
               <@s.iterator value="prodHotSellList.get('${fromPlaceCode}_JDL')" status="st" var="var">
               <@s.if test = "#st.count==1">
                <li class="rx_list_first">
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <img width="210" height="140" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <div class="product_text">
                            <h4>${productName?if_exists }</h4>
                        </div>
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a>
                    <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b><del>￥${marketPrice?if_exists }</del></p>
                </li>
              </@s.if><@s.else>
                <li>
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <h5>${productName?if_exists }</h5>
                        <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b></p>
                        <img width="90" height="60" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a> 
                </li>
                </@s.else>
               </@s.iterator>
             </ul>  
             </@s.if>
             
             <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_ZYX') && prodHotSellList.get('${fromPlaceCode}_ZYX').size()>0">
             <ul class="rx_list">  
               <@s.iterator value="prodHotSellList.get('${fromPlaceCode}_ZYX')" status="st" var="var">
               <@s.if test = "#st.count==1">
                <li class="rx_list_first">
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <img width="210" height="140" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <div class="product_text">
                            <h4>${productName?if_exists }</h4>
                        </div>
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a>
                    <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b><del>￥${marketPrice?if_exists }</del></p>
                </li>
              </@s.if><@s.else>
                <li>
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <h5>${productName?if_exists }</h5>
                        <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b></p>
                        <img width="90" height="60" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a> 
                </li>
                </@s.else>
               </@s.iterator>
             </ul>  
             </@s.if>
             
             
              <@s.if test="null != prodHotSellList.get('${fromPlaceCode}_CJY') && prodHotSellList.get('${fromPlaceCode}_CJY').size()>0">
             <ul class="rx_list">  
               <@s.iterator value="prodHotSellList.get('${fromPlaceCode}_CJY')" status="st" var="var">
               <@s.if test = "#st.count==1">
                <li class="rx_list_first">
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <img width="210" height="140" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <div class="product_text">
                            <h4>${productName?if_exists }</h4>
                        </div>
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a>
                    <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b><del>￥${marketPrice?if_exists }</del></p>
                </li>
              </@s.if><@s.else>
                <li>
                    <a title="${productName?if_exists }" target="_blank" href="http://www.lvmama.com/product/${productId }" class="block">
                        <h5>${productName?if_exists }</h5>
                        <p class="rx_jg"><span><dfn>${orderQuantity?if_exists }</dfn>份已售</span><b>￥${sellPrice?if_exists }</b></p>
                        <img width="90" height="60" alt="${productName?if_exists }" src="${imgUrl?if_exists }">
                        <span class="tag_box"><i class="index_icon icon_lj"></i>${st.count }</span>
                    </a> 
                </li>
                </@s.else>
               </@s.iterator>
             </ul>  
             </@s.if>
            </@s.if>
            <!--新增模块-->
                            
            <div class="main_title pinpai_t"><h3>合作品牌</h3></div>
            <ul class="cooperation_list">
                <@s.iterator value="recommendInfoMainList.get('${channelPage}_hezuopingpai')" status="st" var="var">
                      <li><a href="${url?if_exists}" target="_blank"><img to="${imgUrl?if_exists}" width="110" height="55" alt="${title?if_exists}"></a></li>
                </@s.iterator>
            </ul>
        </div>
        
    </div>
    
    <!--通栏广告-->
    <!--通栏广告-->
    <div class="bot_banner js_banner4">
    	<div class="banner_list_box">
            <ul class="banner_list">
                <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_banner&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#1200px#80px">
                 </li>
               <!-- <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_banner02&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#1200px#80px">
                 </li>
                <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_banner03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#1200px#80px">
                 </li> -->
            </ul>
        </div>
        <ul class="banner_tab">
            <li class="active"><span></span></li>
            <!--<li><span></span></li>
            <li><span></span></li> -->
        </ul> 
        <span class="btn_l"></span>
        <span class="btn_r"></span>
    	
    </div>
</div>


<!-- -->

<!--底部-->
 <#include "/WEB-INF/pages/www/home/footer.ftl" />
        <!-- 公用js--> 
 <script type="text/javascript">
        document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2014|shouye_2014|shouye_2014_flag01&db=lvmamim&border=0&local=yes#150px#50px");
  </script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/v6/header.js,/js/common/jquery.cookie.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v6/index.js,/js/v6/edm_index.js"></script>
<script>                
	$(function(){
		//QQ彩贝用户的黄色帽子 
		if (getLOSCCookie("orderFromChannel") == 'qqcb') {
			var arrStr = document.cookie.split("; "); 
			var temp;
			var HeadShow = "";
			for(var i = 0,l=arrStr.length;i < l;i++){    
				temp=arrStr[i].split("=");    
				if(temp[0] == "HeadShow") {
					HeadShow = decodeURIComponent(temp[1]).replace(new RegExp("\\+","gm"), " ");;
					break;
				}
			}
			var qqcbHtml='<div class="caibei-wrap" id="caibei-wrap"><div class="wrap"><div class="caibei-info"><i class="lv_icon icon_caibei"></i>' + HeadShow + '</div></div></div>';
			$("body").prepend(qqcbHtml);
		}
	});
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
 <script>
   cmCreatePageviewTag("新版网站首页_${stationName?if_exists}", "A0001")
 </script>
</body>
</html>
