<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" /> 
<title>三亚自由行推荐_三亚必玩景点推荐_三亚哪里最好玩-驴妈妈旅游网</title>
<meta name="keywords" content="三亚自由行,三亚景点" />
<meta name="description" content="美丽三亚,浪漫天涯,驴妈妈特推三亚旅游经典产品:包罗了三亚特色酒店.三亚景点门票,潜水,邮轮等等项目,让您的三亚自由行充满快乐" />
<link href="http://pic.lvmama.com/styles/zt/sanya/sanya.css" rel="stylesheet" type="text/css" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<!--[if IE 6]> 
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script> 
<script type="text/javascript"> 
DD_belatedPNG.fix('#ren img,.posi,.posi02'); 
</script> 
<![endif]--> 
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
	<div id="wrap">
    <div class="page">
    	<div class="top">
            	<div class="Slide_pic" id="Slide_pic">
                	<ul class="bigImg">                    	
                        <li>
                        <@s.iterator value="map.get('${station}_17')" status="st">
                        	<#if st.index=0>
                        	<a href="${url?if_exists}" target="_blank" class="pic_link"><img class="first_img" src="${imgUrl?if_exists}" height="250" width="660"/></a>
                        	</#if>
                        </@s.iterator>
                            <div>
                            <@s.iterator value="map.get('${station}_1')" status="st">
                            <p>
                          	 <a href="${url?if_exists}" target="_blank"><strong>[${bakWord2?if_exists}] ——</strong><em>${title?if_exists}</em></a>
                            <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                            </p>
                            </@s.iterator>
                            </div>
                     	</li>                    	
                         <li>
                         <@s.iterator value="map.get('${station}_17')" status="st">
                        	<#if st.index=1>
                        	<a href="${url?if_exists}" target="_blank" class="pic_link"><img src="${imgUrl?if_exists}" height="250" width="660"/></a>
                           	</#if>
                         </@s.iterator>
                            <div>
                            <@s.iterator value="map.get('${station}_2')" status="st">
                            <p>
                          	 <a href="${url?if_exists}" target="_blank"><strong>[${bakWord2?if_exists}] ——</strong><em>${title?if_exists}</em></a>
                            <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                            </p>
                            </@s.iterator>
                            </div>
                     	</li> 
                           <li>
                        	<@s.iterator value="map.get('${station}_17')" status="st">
                        	<#if st.index=2>
                        	<a href="${url?if_exists}" target="_blank" class="pic_link"><img src="${imgUrl?if_exists}" height="250" width="660"/></a>
                           	</#if>
                         </@s.iterator>
                            <div>
                            <@s.iterator value="map.get('${station}_3')" status="st">
                            <p>
                          	 <a href="${url?if_exists}" target="_blank"><strong>[${bakWord2?if_exists}] ——</strong><em>${title?if_exists}</em></a>
                            <span>¥<font>${bakWord1?if_exists}</font>起</span>
                            </p>
                            </@s.iterator>
                            </div>
                     	</li>               
                           <li>
                        	<@s.iterator value="map.get('${station}_17')" status="st">
                        	<#if st.index=3>
                        	<a href="${url?if_exists}" target="_blank" class="pic_link"><img src="${imgUrl?if_exists}" height="250" width="660"/></a>
                           	</#if>
                         </@s.iterator>
                            <div>
                            <@s.iterator value="map.get('${station}_4')" status="st">
                            <p>
                          	 <a href="${url?if_exists}" target="_blank"><strong>[${bakWord2?if_exists}] ——</strong><em>${title?if_exists}</em></a>
                            <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                            </p>
                            </@s.iterator>
                            </div>
                     	</li>                              	
                 </ul>
                 <ul class="img_num" id="img_num">
                        <li class="num1 cur-textImg"><a href="#" target="_blank"></a></li>
                        <li class="num2"><a href="#" target="_blank"></a></li>
                        <li class="num3"><a href="#" target="_blank"></a></li>
                        <li class="num4"><a href="#" target="_blank"></a></li>
                 </ul>
          	  </div><!--Slide_pic end-->
        </div><!--top end-->
        <div class="main_hz">
          <img class="posi" src="http://pic.lvmama.com/img/zt/sanya/yuan.png" height="62" width="51" />
          <img class="posi02" src="http://pic.lvmama.com/img/zt/sanya/xing.png" height="54" width="94" />
          <div class="main">
        	<div class="main_left">
        	<div class="main_box">
            	    <div class="head">
                    	<h3>欢乐自由行</h3>
                        <label>出发地选择:</label>
                        <select id="place" class="place">
                        <option value="当地自由行" selected="">当地自由行</option>
                        <option value="从上海出发">从上海出发</option>
                        <option value="从北京出发">从北京出发</option>
                        <option value="从成都出发">从成都出发</option>
                        </select>
                        <a  href="http://www.lvmama.com/dest/hainan_sanya/freeness_tab_10_1" target="_blank" class="more">更多&gt;&gt;</a>
                    </div>
                    <!-- 当地自由行 -->
                    <div class="select_hz dis">
                          <ul>
                          <@s.iterator value="map.get('${station}_5')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                          </@s.iterator>
                          </ul>
                          <div class="head">
                          		<h4>舒心跟团游</h4>
                          		<a  href="http://www.lvmama.com/dest/hainan_sanya/dest2dest_tab_frm_10_1" target="_blank" class="more">更多&gt;&gt;</a>
                          </div>
                          <ul>
                            <@s.iterator value="map.get('${station}_6')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                          	</@s.iterator>
                           </ul>
                     </div>
                    <!-- 当地自由行 end -->
                    <!-- 上海 -->
                    <div class="select_hz">
                          <ul>
                              <@s.iterator value="map.get('${station}_7')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                          	  </@s.iterator>
                          </ul>
                          <div class="head">
                          		<h4>舒心跟团游</h4>
                          		<a  href="http://www.lvmama.com/dest/hainan_sanya/dest2dest_tab_frm_10_1" target="_blank" class="more">更多&gt;&gt;</a>
                          </div>
                          <ul>
                              <@s.iterator value="map.get('${station}_8')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                          	  </@s.iterator>
                           </ul>
                     </div>
                     <!-- 上海 end -->
                     <!-- 北京 -->
                    <div class="select_hz">
                          <ul>
                              <@s.iterator value="map.get('${station}_9')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                          	  </@s.iterator>
                          </ul>
                          <div class="head">
                          		<h4>舒心跟团游</h4>
                          		<a  href="http://www.lvmama.com/dest/hainan_sanya/dest2dest_tab_frm_10_1" target="_blank" class="more">更多&gt;&gt;</a>
                          </div>
                          <ul>
                              <@s.iterator value="map.get('${station}_10')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                          	  </@s.iterator>
                           </ul>
                     </div>
                     <!-- 北京 end -->
                     <!-- 成都 -->
                    <div class="select_hz">
                          <ul>
                              <@s.iterator value="map.get('${station}_11')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                              </@s.iterator>
                          </ul>
                          <div class="head">
                          		<h4>舒心跟团游</h4>
                          		<a  href="http://www.lvmama.com/dest/hainan_sanya/dest2dest_tab_frm_10_1" target="_blank" class="more">更多&gt;&gt;</a>
                          </div>
                          <ul>
                              <@s.iterator value="map.get('${station}_12')" status="st">
                              <li>
                                  <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                                  <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                              </li>
                              </@s.iterator>
                           </ul>
                     </div>
                     <!-- 成都 end -->
           </div><!--main_box end-->
           <div class="main_box botbg">
                   	<div class="head">
                    	<h3>特色酒店</h3>
                        <a href="http://www.lvmama.com/search/placeSearch!hotelSearch.do?city=%E4%B8%89%E4%BA%9A&stage=3&keyword=&priceRange=0%2C1100000" target="_blank" 
                        class="more">更多&gt;&gt;</a>
                    </div>
                    <ul>
                    <@s.iterator value="map.get('${station}_13')" status="st">
                    	<li>
                        	<span class="textlink">
                            <a href="${url?if_exists}" target="_blank"><img src="<#if imgUrl?exists && imgUrl!="">http://pic.lvmama.com${imgUrl}</#if>" height="50" width="100" /></a>
                            <a href="${url?if_exists}" target="_blank" class="text">${title?if_exists}</a>
                            </span>
                            <span class="bot">¥<font>${bakWord1?if_exists}</font>起</span>
                        </li>
                    </@s.iterator>
                    </ul>
                </div><!--main_box end-->
             </div>
            <div class="side_box">
            	<div class="head">
                		<h3>景点门票</h3>
                         <a href="http://www.lvmama.com/dest/hainan_sanya/ticket_tab_5_1" target="_blank" class="more">更多&gt;&gt;</a>
                    </div>
                       <ul>
                       <@s.iterator value="map.get('${station}_14')" status="st">
                    	<li>
                        	<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                            <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                        </li>
                        </@s.iterator>
                     </ul>
                     <div class="head mar_side">
                		<h3>周边一日游</h3>
                         <a href="http://www.lvmama.com/dest/hainan_sanya/surrounding_tab_10_1" target="_blank" class="more">更多&gt;&gt;</a>
                    </div>
                       <ul>
                       <@s.iterator value="map.get('${station}_15')" status="st">
                       <li>
                        	<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                            <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                        </li>
                        </@s.iterator>
                     </ul>
                     <div class="head mar_side">
                		<h3>自选产品</h3>
                    </div>
                       <ul>
                       <@s.iterator value="map.get('${station}_16')" status="st">
                    	<li>
                        	<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                            <span>¥<font>${memberPrice?if_exists?replace(".0","")}</font>起</span>
                        </li>
                        </@s.iterator>
                     </ul>
            </div><!--side_box-->
        </div><!--main end-->
          <div class="bottom">
          	  <div class="bott_left">
              	<h3>三亚旅游贴士 - 温馨提醒</h3>
                <table width="410" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <th scope="row">月份</th>
                      <td>1月</td>
                      <td>2月</td>
                      <td>3月</td>
                      <td>4月</td>
                      <td>5月</td>
                      <td>6月</td>
                    </tr>
                    <tr>
                      <th scope="row">三亚</th>
                      <td>22.3℃</td>
                      <td>23.3℃</td>
                      <td>26.2℃</td>
                      <td>28.0℃</td>
                      <td>27.5℃</td>
                      <td>29.0℃</td>
                    </tr>
                  </table>
                   <table width="410" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <th scope="row">月份</th>
                      <td>1月</td>
                      <td>2月</td>
                      <td>3月</td>
                      <td>4月</td>
                      <td>5月</td>
                      <td>6月</td>
                    </tr>
                    <tr>
                      <th scope="row">三亚</th>
                      <td>29.9℃</td>
                      <td>29.4℃</td>
                      <td>27.8℃ </td>
                      <td>27.0℃ </td>
                      <td>24.8℃</td>
                      <td>20.3℃</td>
                    </tr>
                  </table>
				 <p class="bot_p"><strong>出行</strong>：无论是火车还是飞机，出行前一定要预留充裕的时间。如是飞机，则应时刻关注天气状况。</p> 
				 <p><strong>话费</strong>：漫游话费较高，出发前记得充足话费，方便和他人联系。</p> 
			  	 <p><strong>药品</strong>：主要是带一些常用感冒药、肠胃药、防蚊虫的花露水、防暑药品、晕船晕车药等。</p> 
				 <p><strong>住宿</strong>：如对目的地不是很熟悉，建议最好提前预订酒店。驴妈妈为您提供价廉物美的酒店选择。</p> 
				 <p><strong>安全</strong>：最好结伴出行，尤其是晚上。单身女性或是小孩最好能随身准备写必备的防卫工具。</p> 
				 <p><strong>装备</strong>：相机必不可少，还有考虑防晒护肤等其他用品。</p>
              </div><!--bott_left end-->
              <div class="bott_right">
              	  <dl>
                    	<dt><a href="http://www.lvmama.com/guide/sanya/" target="_blank"><img src="http://pic.lvmama.com/img/zt/sanya/sanyalink.jpg" height="162" width="115" /></a></dt>
                        <dd>
                        	<h4>三亚官方攻略</h4>
                        	<p>三亚向来是旅游热门地，一年四季都能感受到阳光沙滩的魅力，但是三亚昂贵的住宿和购物却难倒了众多游客，驴妈妈在此为您奉上官方旅游攻略，带您轻松享受南国风光。
                            </p>
                            <a href="http://www.lvmama.com/guide/downs.php?f=sanya.pdf" target="_blank"><img src="http://pic.lvmama.com/img/zt/sanya/xiazai.jpg" height="31" width="95" />
                            </a>
                        </dd>
                    </dl>
              </div>
          </div><!--bottom end-->
        </div><!--main_hz end-->
    	<div class="ren" id="ren"><img src="http://pic.lvmama.com/img/zt/sanya/ren.png" height="528" width="418" /></div>
        <div class="footer_hz">
        		<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
        </div>
    </div><!--page end-->
    <div class="bgtop_01"></div>
    <div class="bgtop_02"></div>
    </div><!--wrap end-->
<script type="text/javascript">			  
$(document).ready(function(){
	var num=0;
	var timer,fastHoverFun;
	var icon=$("#img_num li");
	$(".img_num li").each(function(index){
		$(this).mouseover(function(){
			num=index;
			$(this).addClass("cur-textImg").siblings().removeClass("cur-textImg");
			$(".bigImg li").eq(index).show().siblings().hide();
			$(".bigImg .pic_link img").animate({"opacity":"0"},0);
			$(".bigImg .pic_link img").eq(index).stop().animate({"opacity":"1.0"},1000);
		});
	});
	  changeFun=function (n){
		if(!$(".bigImg li").is(":animated")){
			$(".img_num li").eq(n).addClass("cur-textImg").siblings().removeClass("cur-textImg");
			$(".bigImg li").eq(n).show().siblings().hide();
			$(".bigImg .pic_link img").animate({"opacity":"0"},0);
			$(".bigImg .pic_link img").eq(n).animate({"opacity":"1.0"},1000);
		   }
	     }
		  icon.hover(function(){  
          clearInterval(timer);  
          num = icon.index(this);  
          hasIcoHighName = $(this).hasClass("cur-textImg");  
         //setTimeout避免用户快速(无意识性划过)划过时触发事件  
          fastHoverFun = setTimeout(function(){  
           //鼠标划入当前图片按钮时不闪烁  
           if (!hasIcoHighName) {  
               changeFun(num);
				}  
          }, 150);  
        }, function(){  
       clearTimeout(fastHoverFun);  
        //自动切换  
       timer=setInterval(function(){  
			num++;  
  			changeFun(num);  
		   if (num ==4) {  
				changeFun(0);  
			    num = 0;  
			   }
		  } ,4000)
	  }).eq(0).trigger('mouseleave');
		$(".bigImg li").hover(function(){
				clearInterval(timer);
				num = $(".bigImg li").index(this); 
			},function(){
				icon.eq(num).trigger('mouseleave'); 		
			});
		$("#Slide_pic p").hover(function(){
			$(this).addClass("active_bg");
		}, function(){
			$(this).removeClass("active_bg");
	    });
});
</script>    
<script type="text/javascript">			  
$(document).ready(function(){
	$("#place").change(function(){
	var index= $(this).find("option:selected").index();
	$(".main_left .select_hz").eq(index).show().siblings(".select_hz").hide();
	});
  });
</script>
<script type="text/javascript">kfguin="4006666699";eid="218808P8z8p8R8Q8P8Q8q";ws="www.lvmama.com"; type="0";wpadomain="b";</script> <script type="text/javascript" src="http://im.bizapp.qq.com:8000/kf.js?t=110922"></script>
 <!--SEM搜索引擎--> 

<!--SEM搜索引擎END-->
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
