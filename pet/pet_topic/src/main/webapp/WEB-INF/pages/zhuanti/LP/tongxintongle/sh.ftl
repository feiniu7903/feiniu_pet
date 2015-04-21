<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>上海暑期主题旅游_上海娱乐/科普/自然景点好去处-驴妈妈旅游网 </title> 
<meta name="keywords" content="上海暑期,上海主题旅游"> 
<meta name="description" content="炎热的暑期别赖在空调下啦!跟着驴妈妈玩乐大课堂,踏足上海娱乐,科普,自然的旅游景点,寻找童年大乐趣!还有幸运大奖等您来抽取,同时我们将和您一起进行一公斤公益旅行!让世界因旅行而更美好!"> 
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/liuyi/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/dialog.css" >
<base target="_blank">

</head>

<body>

<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="wrap classStyle1">

    <div class="topBox">
        <div class="topBanner">
        	<ul class="btnList">
            	<li class="nav_li"><span class="classBtn1"></span></li>
                <li><span class="classBtn2"></span></li>
                <li><span class="classBtn3"></span></li>
            </ul>
        </div>
    </div>
    
    <h3 class="titleBox"></h3>
  <!--娱乐课-->
    <div class="cpBox" style="display:block">
    	<div class="cpBoxBg">
    	  <@s.iterator value="map.get('${station}_sh_ylk_recommend')" status="st">
        	<div class="itemBox">
            	<div class="leftBox">
                	<h3><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <#if remark?exists && remark!=""> <p><span>推荐理由</span>${remark?if_exists}</p></#if>
                    <#if bakWord3?exists && bakWord3!=""> <p class="teshhu"><span>景区活动</span>${bakWord3?if_exists}</p></#if>
                    <span class="price mt20">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                    <#if bakWord6?exists && bakWord6!="">  <span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                    <a href="${url?if_exists}" target="_blank" class="orderBtn1"></a>
                </div>
                <div class="rightBox">
                	<a href="${url?if_exists}" target="_blank" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="261" alt="${title?if_exists}"/></a>
                </div>
            </div>
          </@s.iterator>   
           
            <ul class="cpList">
            	<@s.iterator value="map.get('${station}_sh_ylk')" status="st">
            	<li>
            		 <#if bakWord4?exists && bakWord4=="买小送大"> <span class="tips1"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="买大送小"> <span class="tips2"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="大小同价"> <span class="tips3"></span></#if>
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="240" height="154" alt="${title?if_exists}"/></a>
                    <h3><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <div class="priceBox">
                    	<div class="jiageBox">
                        	<span class="price">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   	 		<#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                        </div>
                        <a href="${url?if_exists}" class="orderBtn2"></a>
                    </div>
                </li>
                </@s.iterator>
            </ul>
        </div>
    </div>
    
    
    <!--科普课-->
    <div class="cpBox">
    	<div class="cpBoxBg">
    	 <@s.iterator value="map.get('${station}_sh_kpk_recommend')" status="st">
        	<div class="itemBox">
            	<div class="leftBox">
                	<h3><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <#if remark?exists && remark!=""> <p><span>推荐理由</span>${remark?if_exists}</p></#if>
                    <#if bakWord3?exists && bakWord3!=""> <p class="teshhu"><span>景区活动</span>${bakWord3?if_exists}</p></#if>
                    <span class="price mt20">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                    <#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                    <a href="${url?if_exists}" class="orderBtn1"></a>
                </div>
                <div class="rightBox">
                	<a href="${url?if_exists}" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="261" alt="${title?if_exists}"/></a>
                </div>
            </div>
          </@s.iterator>   
           
            <ul class="cpList">
            	<@s.iterator value="map.get('${station}_sh_kpk')" status="st">
            	<li>
                	 <#if bakWord4?exists && bakWord4=="买小送大"> <span class="tips1"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="买大送小"> <span class="tips2"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="大小同价"> <span class="tips3"></span></#if>
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="240" height="154" alt="${title?if_exists}"/></a>
                    <h3><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <div class="priceBox">
                    	<div class="jiageBox">
                        	<span class="price">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   	 		<#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                        </div>
                        <a href="${url?if_exists}" class="orderBtn2"></a>
                    </div>
                </li>
                </@s.iterator>
            </ul>
        </div>
    </div>
    
    <!--自然课-->
	<div class="cpBox">
    	<div class="cpBoxBg">
        	 <@s.iterator value="map.get('${station}_sh_zrk_recommend')" status="st">
        	<div class="itemBox">
            	<div class="leftBox">
                	<h3><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <#if remark?exists && remark!=""> <p><span>推荐理由</span>${remark?if_exists}</p></#if>
                    <#if bakWord3?exists && bakWord3!=""> <p class="teshhu"><span>景区活动</span>${bakWord3?if_exists}</p></#if>
                    <span class="price mt20">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                    <#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                    <a href="${url?if_exists}" class="orderBtn1"></a>
                </div>
                <div class="rightBox">
                	<a href="${url?if_exists}" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="261" alt="${title?if_exists}"/></a>
                </div>
            </div>
          </@s.iterator>   
            
            <ul class="cpList">
				 <@s.iterator value="map.get('${station}_sh_zrk')" status="st">
            	<li>
                	 <#if bakWord4?exists && bakWord4=="买小送大"> <span class="tips1"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="买大送小"> <span class="tips2"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="大小同价"> <span class="tips3"></span></#if>
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="240" height="154" alt="${title?if_exists}"/></a>
                    <h3><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <div class="priceBox">
                    	<div class="jiageBox">
                        	<span class="price">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   	 		<#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                        </div>
                        <a href="${url?if_exists}" class="orderBtn2"></a>
                    </div>
                </li>
                </@s.iterator>
                
            </ul>
        </div>
    </div>
    <a id="luckdraw" name="luckdraw" style="font-size:0"></a>
    <h3 class="choujiang"></h3>
    <div class="cjBox">
    	<div class="cj_box cj_yuan">
        	<ul class="cj_list">
            	<li class="jp_1"><img src="http://pic.lvmama.com/img/zt/liuyi/jp_1.png" width="230" height="147" alt=""></li>
                <li class="jp_2"><img src="http://pic.lvmama.com/img/zt/liuyi/jp_2.png" width="182" height="199" alt=""></li>
                <li class="jp_3"><img src="http://pic.lvmama.com/img/zt/liuyi/jp_3.png" width="182" height="199" alt=""></li>
                <li class="jp_4"><img src="http://pic.lvmama.com/img/zt/liuyi/jp_4.png" width="230" height="147" alt=""></li>
                <li class="jp_5"><img src="http://pic.lvmama.com/img/zt/liuyi/jp_5.png" width="182" height="199" alt=""></li>
                <li class="jp_6"><img src="http://pic.lvmama.com/img/zt/liuyi/jp_6.png" width="182" height="199" alt=""></li>
            </ul>
            <a class="start js_start" href="javascript:void(0)" target="_self">立即抽奖<span></span></a>
        </div>
        <div class="hjmd_box">
            <ul class="md_list js_md_list">
                <li><span title="xxxxxxxx">用户名：xxxxxxxx</span><b>奖品：xxxxxxxx</b></li>
            </ul>
        </div>
    </div>
    <div class="cjbzBox"><p></p></div>
    <a id="publicinterest" name="publicinterest" style="font-size:0"></a>
    <h3 class="gongyi"></h3>
    <div class="gyBox">
    	<div class="gyclearBox">
            <div class="gynrBox">
                <div class="tjYList">
                    <div class="shuzi">000000</div>
                    <span>元</span>
                </div>
                <p>活动将在6月15日截止，届时我们将在6月15日，携手一公斤组织，奉献爱心公益：带领民工子弟们一起游上海！</p>
            </div>
        </div>
        <div class="gypicBox"></div>
    </div>
  
  	<div class="sp_pop">
    	<a class="cgjBtn" href="#luckdraw" target="_self"></a>
        <a class="jgkBtn" href="#publicinterest" target="_self"></a>
        <a class="topBtn" href="#" target="_self"></a>
        <ul class="fdcList"> 
        	<li class="nav_li"><a href="http://www.lvmama.com/zt/lvyou/shuqi1" target="_self">上海</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/shuqi1/guangzhou.html" target="_self">广州</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/shuqi1/beijing.html" target="_self">北京</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/shuqi1/chengdou.html" target="_self">成都</a></li>
        </ul>
    </div>
    
</div>

<div class="PopBox"> 	
    <div class="PopBox_p">
    	<div class="state1">
        	<h4>恭喜您获得</h4>
            <h3>880元现金优惠券套餐！</h3>
            <p>我们会在7个工作日之内通过<br/><a target="_blank" href="http://www.lvmama.com/myspace/message.do">站内信</a>发送给你，清注意查收！</p>
            <div class="close_Btn"></div>
        </div>
        <div class="state2">
        	<h3>恭喜您抽中了:“童年智造”趣味玩具</h3>
            <h4>留下你的信息，我们会在14个工作日内快递给您</h4>
            <div class="formBox">
            <form>
            	<label class="titlelabel">真实姓名：</label><input type="text" class="textBox name"/>
                <label class="titlelabel">手机号码：</label><input type="text" class="textBox mobile"/>
                <label class="titlelabel">收货地址：</label><input type="text" class="textBox teshuTextBox address"/>
                <label class="titlelabel">邮　　编：</label><input type="text" class="textBox zip"/>
            </form>
            </div>
            <a href="javascript:void(0);" class="sureBtn huojiang" target="_self">确　定</a>
        </div>
       <div class="state3">
        	<h4>谢谢参与！</h4>
        	<div class="close_Btn"></div>
        </div>
        <div class="state4">
        	<h4>恭喜您获得</h4>
            <h3>驴妈妈5元全场通用优惠券！</h3>
            <div class="close_Btn"></div>
        </div>
    </div>
</div>
<div class="pop_body_bg"></div>
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" type="text/javascript"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/tongxintongle/index.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
<script src="http://www.lvmama.com/zt/000global/js/eventCM.js" type="text/javascript"></script>
</body>
</html>
