<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>峨眉山旅游推荐_峨眉山游玩线路|图,价格,多少钱-驴妈妈旅游网</title>
<meta name="keywords" content="峨眉山旅游,峨眉山,线路图" />
<meta name="description" content="驴妈妈推荐：最经典和最热门的峨眉山旅游游玩线路，还有非常专业的游览线路图;【特价】峨眉山旅游线路价格,这么实惠的价格,要多少钱都值了。" />
<base target="_blank" />
<link href="http://pic.lvmama.com/styles/zt/emei/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div class="wapper">
  <div class="wapper_cont"> <img alt="" src="http://pic.lvmama.com/img/zt/emei/e_top_01.jpg" /> <img alt="" src="http://pic.lvmama.com/img/zt/emei/e_top_02.jpg"/> <img alt="" src="http://pic.lvmama.com/img/zt/emei/e_top_03.jpg" />
    <div class="contant_prod">
    
    	<div class="top_prod_tuijian title">
      	<ul class="promo_box">
      		<@s.iterator value="map.get('${station}_10636')" status="st">
        	<li style="display:block;">
            	<img src="${imgUrl?if_exists}" width="604" height="225" alt="" />
                <h4><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h4>
            </li>
            </@s.iterator>
        </ul>
        <ul class="promo_nav">
        <@s.iterator value="map.get('${station}_10636')" status="st">
        	<li class="navLi"></li>
        </@s.iterator>
        </ul>
      </div>
      
      <!--特色自由行-->
        <div class="free_title titile01 clearfix">
        <h3><span>特色自由行</span></h3>
        <div class="aside">
          <h4>点击<span>金顶</span><span>半山</span><span>山下</span>查看峨眉山详细产品</h4>
          <div class="map_list"> <a class="map_name map_postion">金 顶</a> <a class="map_name map_postion01">半 山</a> <a class="map_name map_postion02 map_current">山 下</a> </div>
        </div>
        <div class="main"> 
          <div class="map_list_main">
          
            <ul class="map_shanxia clearfix">
          	  <@s.iterator value="map.get('${station}_10639')" status="st">
            	<li>
                	<img src="${imgUrl?if_exists}"  width="200" height="220" alt="" />
                    <h4><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h4>
                    <div class="priceBox">
                    	<span><i>现价：￥</i>${memberPrice?if_exists?replace(".0","")}</span>
                        <#if bakWord1?exists && bakWord1!=""><del>原价:￥ ${bakWord1?if_exists}</del></#if>
                        <a href="${url?if_exists}" target="_blank" title="${title?if_exists}"></a>
                    </div>
                </li>
              </@s.iterator> 
            </ul>
            
            <ul class="map_shanxia clearfix">
            	<@s.iterator value="map.get('${station}_10638')" status="st">
            	<li>
                	<img src="${imgUrl?if_exists}" width="200" height="220" alt="" />
                    <h4><a href="${url?if_exists}" target="_blank">${title?if_exists}</a></h4>
                    <div class="priceBox">
                    	<span><i>现价：￥</i>${memberPrice?if_exists?replace(".0","")}</span>
                        <#if bakWord1?exists && bakWord1!=""><del>原价:￥${bakWord1?if_exists}</del></#if>
                        <a href="${url?if_exists}" target="_blank"></a>
                    </div>
                </li>
                </@s.iterator>
            </ul>
            
            <ul class="map_shanxia clearfix map_shanxia_block">
         	   <@s.iterator value="map.get('${station}_10637')" status="st">
            	<li>
                	<img src="${imgUrl?if_exists}" width="200" height="220" alt="" />
                    <h4><a href="${url?if_exists}" target="_blank">${title?if_exists}</a></h4>
                    <div class="priceBox">
                    	<span><i>现价：￥</i>${memberPrice?if_exists?replace(".0","")}</span>
                       <#if bakWord1?exists && bakWord1!="">  <del>原价:￥${bakWord1?if_exists}</del></#if>
                        <a href="${url?if_exists}" target="_blank"></a>
                    </div>
                </li>
                </@s.iterator>
            </ul>
          </div>
          
        </div>
        <div class="clear"></div>
      </div>
      
  <!--成都酒店推荐-->
      <div class="free_title titile01 cd_tuijian_main clearfix">
        <div class="aside_01">
          <h3>成都酒店推荐</h3>
          <div class="cd_list_01">
            <ul class="cd_tuijian">
				<@s.iterator value="map.get('${station}_10644')" status="st">
					<li> 
						<a href="${url?if_exists}" target="_blank"><img alt="" src="${imgUrl?if_exists}" /></a> <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
						<div class="cd_tips"> 
							<span class="pingjia">评价：</span><span class="star_pic${bakWord1?if_exists}"></span> <span class="price_01"><em><i>¥</i>${bakWord2?if_exists}</em>起</span> </div>
						<p class="jieshao">${bakWord10?if_exists}</p>
					</li>
				</@s.iterator>
            </ul>
          </div>
        </div>
        <div class="main_01">
          <div class="tese_tuan">
            <h3 class="title02"></h3>
            <ul class="tese_city">
              <li class="current"><a>景点门票</a></li>
              <li><a>特色酒店</a></li>
              <li><a>跟团游 </a></li>
              <li><a>自由行 </a></li>
              <!--<li><a>广州出发</a></li>-->
            </ul>
            <div class="tese_city_main">
              <div class="mian_city tese_city_maindisa">
                <ul class="tese_info_list">
					<@s.iterator value="map.get('${station}_10640')" status="st">
						<li>
							<div class="title_contant title_contant0 clearfix">
								<div class="title_mian"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
									<p>${remark?if_exists} </p>
								</div>
								<span><i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起 
							</div>
							<div class="current_list"> 
								<a href="${url?if_exists}" target="_blank" class="pic_01"><img alt="" src="${imgUrl?if_exists}" /></a>
								<div class="pic_info01"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists} </a>
									<#if bakWord1?exists && bakWord1!=""><p>◎ ${bakWord1?if_exists}</p></#if>
									<#if bakWord2?exists && bakWord2!=""><p>◎ ${bakWord2?if_exists}</p></#if>
									<a href="${url?if_exists}" target="_blank" class="yuding_bt"></a>
									<div class="yuding">
										<del><i>¥</i>${marketPrice?if_exists?replace(".0","")}</del><span> <i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起
									</div>
								</div>
							</div>
						</li>
					</@s.iterator>
                </ul>
              </div>
              <div class="mian_city">
                <ul class="tese_info_list">
					<@s.iterator value="map.get('${station}_10641')" status="st">
						<li>
							<div class="title_contant title_contant0 clearfix">
								<div class="title_mian"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
									<p>${remark?if_exists} </p>
								</div>
								<span><i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起 
							</div>
							<div class="current_list"> 
								<a href="${url?if_exists}" target="_blank" class="pic_01"><img alt="" src="${imgUrl?if_exists}" /></a>
								<div class="pic_info01"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists} </a>
									<#if bakWord1?exists && bakWord1!=""><p>◎ ${bakWord1?if_exists}</p></#if>
									<#if bakWord2?exists && bakWord2!=""><p>◎ ${bakWord2?if_exists}</p></#if>
									<a href="${url?if_exists}" target="_blank" class="yuding_bt"></a>
									<div class="yuding">
										<del><i>¥</i>${marketPrice?if_exists?replace(".0","")}</del><span> <i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起
									</div>
								</div>
							</div>
						</li>
					</@s.iterator>
				</ul>
              </div>
              <div class="mian_city">
                <ul class="tese_info_list">
					<@s.iterator value="map.get('${station}_10642')" status="st">
						<li>
							<div class="title_contant title_contant0 clearfix">
								<div class="title_mian"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
									<p>${remark?if_exists} </p>
								</div>
								<span><i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起 
							</div>
							<div class="current_list"> 
								<a href="${url?if_exists}" target="_blank" class="pic_01"><img alt="" src="${imgUrl?if_exists}" /></a>
								<div class="pic_info01"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists} </a>
									<#if bakWord1?exists && bakWord1!=""><p>◎ ${bakWord1?if_exists}</p></#if>
									<#if bakWord2?exists && bakWord2!=""><p>◎ ${bakWord2?if_exists}</p></#if>
									<a href="${url?if_exists}" target="_blank" class="yuding_bt"></a>
									<div class="yuding">
										<del><i>¥</i>${marketPrice?if_exists?replace(".0","")}</del><span> <i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起
									</div>
								</div>
							</div>
						</li>
					</@s.iterator>
				</ul>
              </div>
              
              <!--自由行-->
                <div class="mian_city tese_city_maindisa">
                <ul class="tese_info_list">
                	<@s.iterator value="map.get('${station}_eMeiShang_free')" status="st">
						<li>
							<div class="title_contant title_contant0 clearfix">
								<div class="title_mian"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
									<p>${remark?if_exists} </p>
								</div>
								<span><i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起 
							</div>
							<div class="current_list"> 
								<a href="${url?if_exists}" target="_blank" class="pic_01"><img alt="" src="${imgUrl?if_exists}" /></a>
								<div class="pic_info01"> 
									<a href="${url?if_exists}" target="_blank">${title?if_exists} </a>
									<#if bakWord1?exists && bakWord1!=""><p>◎ ${bakWord1?if_exists}</p></#if>
									<#if bakWord2?exists && bakWord2!=""><p>◎ ${bakWord2?if_exists}</p></#if>
									<a href="${url?if_exists}" target="_blank" class="yuding_bt"></a>
									<div class="yuding">
										<del><i>¥</i>${marketPrice?if_exists?replace(".0","")}</del><span> <i>¥</i>${memberPrice?if_exists?replace(".0","")}</span>起
									</div>
								</div>
							</div>
						</li>
					</@s.iterator>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="free_title titile01 titile02 clearfix">
        <h3><span>攻略与问答</span></h3>
          <div class="gl">
          
          <@s.iterator value="map.get('${station}_eMeiShang_strategy')" status="st">
        	<div class="glList">
            	<ul class="photoList">
                    <li><img src="${imgUrl?if_exists}" width="462" height="196" alt="" /></li>
                </ul>
                <div class="intro">
                	<h4> <a href="${url?if_exists}" target="_blank">${title?if_exists}</a></h4>
                    <p><b>推荐理由：</b>${remark?if_exists}</p>
                </div>
            </div>
            </@s.iterator>
       	 </div>
        <br class="clear"/>
        <div class="qa_question">
          <h4>峨眉山旅游Q&A </h4>
        </div>
        <dl class="qa_list">
          <dt>Q：峨眉山的门票、索道、观光车是多少钱？</dt>
          <dd>A：全票：150元;优惠票：80元;淡季（12月15日-次年1月14日）：90元</dd>
          <dd class="text_list"> 观光车:全山段票价：90元;索道分为万年索道，金顶索道... <a href="http://www.lvmama.com/guide/2012/0428-162547.html">查看全部》</a></dd>
        </dl>
        <dl class="qa_list">
          <dt>Q：峨眉山市区离峨眉山景区有多远，市区那些公交可以到景区？</dt>
          <dd class="text_list">A：市区离景区5-6公里，白天可乘公交5路（可在报国寺或终点站黄湾售票大厅下车，1.5元）、8路（天下名山牌坊下车后需步行200米进入报国寺景区范围，1元）... <a href="http://www.lvmama.com/guide/2012/0428-162547-2.html">查看全部》</a></dd>
        </dl>
        <dl class="qa_list">
          <dt>Q：峨眉山必去哪些景点？</dt>
          <dd class="text_list">A：金顶、万年寺、清音阁、一线天、猴区、报国寺... <a href="http://www.lvmama.com/guide/2012/0428-162547-3.html">查看全部》</a></dd>
        </dl>
        <dl class="qa_list">
          <dt>Q：去峨眉山，自驾车可以上山吗？山上有哪些停车场？如何收费？</dt>
          <dd class="text_list">A：私家车是可以上山的，最高可到雷洞坪车场（公路终点），但如果山上停车位置已满，在适当位置，会临时执行交通管制，限制车辆继续前行。这时可将车辆停在相应停车场，转乘景区观光车上山... <a href="http://www.lvmama.com/guide/2012/0428-162547.html">查看全部》</a></dd></dd>
        </dl>
      </div>
    </div>
  </div>
</div>
<div class="footer">
  <div class="online_pic">
    <h4>热门活动正在进行</h4>
    <div class="online">
      <ul>
        <li><a href="http://www.lvmama.com/guide/zt/scwq/index.html"><img alt="" src="http://pic.lvmama.com/img/zt/emei/pic02.jpg" /></a></li>
        <li><a href="http://www.lvmama.com/comment/zt/jzgems/"><img alt="" src="http://pic.lvmama.com/img/zt/emei/pic03.jpg" /></a></li>
        <li><a href="http://www.lvmama.com/comment/zt/chengdu"><img alt="" src="http://pic.lvmama.com/img/zt/emei/pic04.jpg" /></a></li>
      </ul>
    </div>
  </div>
  <div class="ztfooter"> 客服热线：1010-6060&#12288;服务时间：周一至周日早8:00&mdash;晚24:00&#12288;客服邮箱：service@lvmama.com <br />
    Copyright&copy;2012 www.lvmama.com. 景域旅游运营集团版权所有&#12288;沪ICP备07509677</div>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>

<script type="text/javascript" src="http://pic.lvmama.com/js/zt/emei/index.js"></script>
</body>
</html>
