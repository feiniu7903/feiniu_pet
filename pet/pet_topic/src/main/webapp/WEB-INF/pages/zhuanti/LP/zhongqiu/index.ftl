<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>2013中秋国庆旅游_中秋旅游好去处_国庆热门旅游线路-驴妈妈旅游网</title>
<meta name="keywords" content="中秋,旅游,国庆" />
<meta name="description" content="2013中秋国庆国庆以月亮的名义和自己的爱人去旅游的好地方,下面介绍一些中国中秋旅游的好去处和中秋国庆旅游一些的热门精品线路,介绍国庆最适合的旅游景点." />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/zhongqiu/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="body_bg2">
    <div class="main_all">
        <div class="banner">
            
        </div>
    	<div class="main_list">
        	<h3>中秋热推</h3>
            <ul class="rtxl_list">
            	<li class="first">
                <@s.iterator value="map.get('${station}_rt_1')" status="st">
            		<@s.if test="#st.isFirst()">
                	<div class="jdt_list" style="display:block;">
                        <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="322" height="376" alt=""></a>
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                    </div>
                    </@s.if>
            		<@s.else>
                    <div class="jdt_list">
                        <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="322" height="376" alt=""></a>
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
						
                    </div>
                    </@s.else>
        		</@s.iterator>
                
                	<dl class="jdt_tab">
                    <@s.iterator value="map.get('${station}_rt_1')" status="st">
            		<@s.if test="#st.isFirst()">
                    	<dd class="jdt_tab_dd"></dd>
                    </@s.if>
            		<@s.else>
                        <dd></dd>
                    </@s.else>
        			</@s.iterator>
                    </dl>
                </li>
                <@s.iterator value="map.get('${station}_rt_2')" status="st">
                <li>
                	<div class="img_box">
                        <a href="${url?if_exists}"><img src="${imgUrl?if_exists}"  alt=""></a>
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
						
                    </div>
                </li>
                </@s.iterator>
            </ul>
            
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b1.gif" width="1000" height="33" alt="">
        
       
        	
        <div class="main_list shanghai">
        	<h3>产品列表</h3>
            <ul class="cplb_list">
            <@s.iterator value="map.get('${station}_sh')" status="st">
            	<@s.if test="#st.isFirst()">
            	<li>
                	<div class="list_t"><b>上海</b><span>精彩魔都</span></div>
                	<div class="img_box">
                        <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="156" alt=""></a>
                        <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
						<div class="lijian">${bakWord3?if_exists}</div> 
                    </div>
                    <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                    <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                </li>
                </@s.if>
            	<@s.else>
                <li>
                	<div class="img_box">
                        <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="217" alt=""></a>
                        <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
						<div class="lijian">${bakWord3?if_exists}</div> 
                    </div>
                    <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                    <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                </li>
                </@s.else>
        		</@s.iterator>
               
            </ul>
            
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b2.gif" width="1000" height="28" alt="">
        
        <div class="main_list zhejiang">
        	<div class="qiehuan">
            	<div class="list_t"><b>浙江</b><span>山水浙江</span></div>
                <ul class="list_tab">
                	<li class="list_tab_li"><span>全部</span></li>
                    <@s.iterator value="map.get('${station}_zj')" status="st">
                    <li><span>${title?if_exists}</span></li>
                    </@s.iterator>
                </ul>
            </div>
            <div class="cplb_list_box">
                <ul class="cplb_list" style="display:block;">
                    <li>
                        <div class="jdt_list_box">
                        <@s.iterator value="map.get('${station}_zj_all1')" status="st">
            			<@s.if test="#st.isFirst()">
                            <div class="jdt_list" style="display:block;">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                            </@s.if>
            				<@s.else>
                            <div class="jdt_list">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                             </@s.else>
        					</@s.iterator>
                            	<dl class="jdt_tab">
                                <@s.iterator value="map.get('${station}_zj_all1')" status="st">
                                <@s.if test="#st.isFirst()">
                                <dd class="jdt_tab_dd"></dd>
                                </@s.if>
                                <@s.else>
                                    <dd></dd>
                                </@s.else>
                                </@s.iterator>
                                </dl>
                        </div>
                        
                    </li>
                    <@s.iterator value="map.get('${station}_zj_all2')" status="st">
                    <li>
                        <div class="img_box">
                            <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="137" alt=""></a>
                            <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
							<div class="lijian">${bakWord3?if_exists}</div> 
                        </div>
                        <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                    </li>
                     </@s.iterator>
                    
                </ul>
                <@s.iterator value="map.get('${station}_zj')" status="st">
                <ul class="cplb_list">
                    
                    <@s.iterator value="map.get('${station}_zj_${st.index + 1}')" status="sts">
                    <li>
                        <div class="img_box">
                            <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="137" alt=""></a>
                            <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
							<div class="lijian">${bakWord3?if_exists}</div> 
                        </div>
                        <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                    </li>
                     </@s.iterator>
                    
                </ul>
                 </@s.iterator>
                
            </div>
            
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b3.gif" width="1000" height="19" alt="">
        <div class="main_list jiangsu2">
        	<div class="qiehuan">
            	<div class="list_t"><b>江苏</b><span>美好江苏</span></div>
                <ul class="list_tab">
                	<li class="list_tab_li"><span>全部</span></li>
                    <@s.iterator value="map.get('${station}_js')" status="st">
                    <li><span>${title?if_exists}</span></li>
                    </@s.iterator>
                </ul>
            </div>
            <div class="cplb_list_box">
                <ul class="cplb_list" style="display:block;">
                    <li>
                        <div class="jdt_list_box">
                        <@s.iterator value="map.get('${station}_js_all1')" status="st">
            			<@s.if test="#st.isFirst()">
                            <div class="jdt_list" style="display:block;">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                            </@s.if>
            				<@s.else>
                            <div class="jdt_list">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                             </@s.else>
        					</@s.iterator>
                            	<dl class="jdt_tab">
                                <@s.iterator value="map.get('${station}_zj_all1')" status="st">
                                <@s.if test="#st.isFirst()">
                                <dd class="jdt_tab_dd"></dd>
                                </@s.if>
                                <@s.else>
                                    <dd></dd>
                                </@s.else>
                                </@s.iterator>
                                </dl>
                        </div>
                        
                    </li>
                    <@s.iterator value="map.get('${station}_js_all2')" status="st">
                    <li>
                        <div class="img_box">
                            <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="137" alt=""></a>
                            <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
							<div class="lijian">${bakWord3?if_exists}</div> 
                        </div>
                        <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                    </li>
                     </@s.iterator>
                    
                </ul>
                <@s.iterator value="map.get('${station}_js')" status="st">
                <ul class="cplb_list">
                    
                    <@s.iterator value="map.get('${station}_js_${st.index + 1}')" status="sts">
                    <li>
                        <div class="img_box">
                            <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="137" alt=""></a>
                            <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
							<div class="lijian">${bakWord3?if_exists}</div> 
                        </div>
                        <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                    </li>
                     </@s.iterator>
                    
                </ul>
                 </@s.iterator>
                
            </div>
            
        </div>
        
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b4.gif" width="1000" height="20" alt="">
        
        <div class="main_list anhui">
            <ul class="cplb_list">
            	 <@s.iterator value="map.get('${station}_ah')" status="st">
            	<@s.if test="#st.isFirst()">
            	<li>
                	<div class="list_t"><b>安徽</b><span>逐梦徽州</span></div>
                	<div class="img_box">
                        <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="156" alt=""></a>
                        <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
						<div class="lijian">${bakWord3?if_exists}</div> 
                    </div>
                    <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                    <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                </li>
                </@s.if>
            	<@s.else>
                <li>
                	<div class="img_box">
                        <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="217" alt=""></a>
                        <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
						<div class="lijian">${bakWord3?if_exists}</div> 
                    </div>
                    <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                    <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                </li>
                </@s.else>
        		</@s.iterator>
            </ul>
            
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b5.gif" width="1000" height="15" alt="">
        
        <div class="main_list damei">
        	<h3>大美中国</h3>
        	<div class="qiehuan">
                <ul class="list_tab">
                <@s.iterator value="map.get('${station}_dm')" status="st">
            		<@s.if test="#st.isFirst()">
                	<li class="list_tab_li"><span>${title?if_exists}</span></li>
                     </@s.if>
           			 <@s.else>
                    <li><span>${title?if_exists}</span></li>
                     </@s.else>
        		</@s.iterator>  
                </ul>
            </div>
            <div class="cplb_list_box">
             <@s.iterator value="map.get('${station}_dm')" status="st">
             <@s.if test="#st.isFirst()">
                <ul class="cplb_list" style="display:block;">
                    <li>
                        <div class="jdt_list_box">
                        <@s.iterator value="map.get('${station}_dml_${st.index + 1}')" status="sts">
            			<@s.if test="#st.isFirst()">
                            <div class="jdt_list" style="display:block;">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                            </@s.if>
            				<@s.else>
                            <div class="jdt_list">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                             </@s.else>
        					</@s.iterator>
                            	<dl class="jdt_tab">
                                <@s.iterator value="map.get('${station}_dml_${st.index + 1}')" status="sts">
                                <@s.if test="#st.isFirst()">
                                <dd class="jdt_tab_dd"></dd>
                                </@s.if>
                                <@s.else>
                                    <dd></dd>
                                </@s.else>
                                </@s.iterator>
                                </dl>
                        </div>
                        
                    </li>
                   <@s.iterator value="map.get('${station}_dm_${st.index + 1}')" status="sts">
                    <li>
                        <div class="img_box">
                            <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="137" alt=""></a>
                            <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
							<div class="lijian">${bakWord3?if_exists}</div> 
                        </div>
                        <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                    </li>
                     </@s.iterator>
                </ul>
                 </@s.if>
            	<@s.else>
                <ul class="cplb_list">
                    <li>
                        <div class="jdt_list_box">
                        <@s.iterator value="map.get('${station}_dml_${st.index + 1}')" status="sts">
            			<@s.if test="#st.isFirst()">
                            <div class="jdt_list" style="display:block;">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                            </@s.if>
            				<@s.else>
                            <div class="jdt_list">
                                <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="373" alt=""></a>
                                <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
                                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
								<div class="lijian">${bakWord3?if_exists}</div> 
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                            </div>
                             </@s.else>
        					</@s.iterator>
                            	<dl class="jdt_tab">
                                <@s.iterator value="map.get('${station}_dml_${st.index + 1}')" status="sts">
                                <@s.if test="#st.isFirst()">
                                <dd class="jdt_tab_dd"></dd>
                                </@s.if>
                                <@s.else>
                                    <dd></dd>
                                </@s.else>
                                </@s.iterator>
                                </dl>
                        </div>
                        
                    </li>
                   <@s.iterator value="map.get('${station}_dm_${st.index + 1}')" status="sts">
                    <li>
                        <div class="img_box">
                            <a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="137" alt=""></a>
                            <h4><a href="${url?if_exists}">${bakWord1?if_exists}</a></h4>
							<div class="lijian">${bakWord3?if_exists}</div> 
                        </div>
                        <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                        <p class="jiage"><a class="btn_qg" href="${url?if_exists}"></a>现价：<span>${memberPrice?if_exists?replace(".0","")}</span>元起</p>
                    </li>
                     </@s.iterator>
                </ul>
                </@s.else>
        		</@s.iterator>
            </div>
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b6.gif" width="1000" height="15" alt="">
        
        <div class="main_list">
        	<h3>景点推荐</h3>
            <ul class="jdtj_list">
            <@s.iterator value="map.get('${station}_jd')" status="st">
            	<li>
                	<a href="${url?if_exists}"><img original="${imgUrl?if_exists}" width="234" height="162" alt=""></a>
                    <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
					
                </li>
             </@s.iterator>   
            </ul>
            
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b6.gif" width="1000" height="15" alt="">
         
        <div class="main_list">
            <a href="http://yxp.163.com/act/ONLY0815.html?act=yxpzqj_20130830_18"><img original="http://www.lvmama.com/zt/promo/zhongqiu/images/yinxiang111.jpg" width="978" height="95" alt=""></a>
        </div>
        <img class="box_b_bg" src="http://www.lvmama.com/zt/promo/zhongqiu/images/box_b6.gif" width="1000" height="25" alt="">
    </div>	
</div>



<div class="nav_r">
	<ul class="nav_r_list">
    	<li>中秋热推</li>
        <li>上海</li>
        <li>浙江</li>
        <li>江苏</li>
        <li>安徽</li>
        <li>大美中国</li>
        <li>景点推荐</li>
    </ul>
    <a class="go_top" href="#" target="_self"></a>
</div>





<div class="main_b">
	<div class="hd_title">
    	<p></p>
        <h3>以下活动正在进行</h3>
    </div>
   <ul class="hd_list"> 
        <@s.iterator value="map.get('${station}_hd')" status="st">
    	<li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="224" height="160" alt=""></a></li>
		</@s.iterator>
	</ul>
    <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/zt/promo/zhongqiu/js/index.js"></script>
<script>
$(function(){ 
	$(".cplb_list").lazyLoad();
	$('.main_list').lazyLoad();
	$('.jdtj_list').lazyLoad();
	$('.hd_list').lazyLoad();
});
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
