<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>长三角特色酒店_上海,北京,杭州,南京特色酒店旅游推荐-驴妈妈旅游网</title>
<meta name="keywords" content="特色酒店,主题酒店" />
<meta name="description" content="驴妈妈旅游网为你提供更多特色酒店服务,上海特色酒店,杭州特色酒店等众多推荐酒店，在活动期间内预订酒店单房间，即可获赠礼或免费升级房间。" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/hotel/css.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<base target="_blank" />
<!--[if IE 6]> 
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script> 
<script type="text/javascript"> 
DD_belatedPNG.fix('.xing1,.xing2,.xing3,.xing4,.xing5,.xing6,.xing7,.xing8,.xing9,.xing10'); 
</script> 
<![endif]--> 
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div class="wraper">
	<div class="wraper-in">
    	<div class="mainCont">
        	<div class="detail">在活动期间内预订酒店单房间，即可获赠礼或免费升级房间。</div>
            <ul class="hotel-list" id="hotel-list">
			 <@s.iterator value="map.get('${station}_9534')" status="st">
            	<li>
                	<div class="hotel-cont">
                    	<a href="${url?if_exists}" class="hotel-name">${title?if_exists}</a><span class="${bakWord1?if_exists}"></span>
                        <p class="hotel-info">
                        	<span class="tit">酒店特色：</span>
                          ${bakWord2?if_exists}
                        </p>
                        <p class="hotel-info hotel-info2">
                        	<span class="tit">预订有礼：</span>
                    ${bakWord3?if_exists}
                        </p>
                        <p class="hotel-info">
                        	<span class="tit">活动时间：</span>
                           ${bakWord4?if_exists}
                        </p>
                        <div class="pri-info">
                          <font>￥<i>${memberPrice?if_exists?replace(".0","")}</i>起</font>
                        	<a class="chakan" href="${url?if_exists}">查看详情</a>
                        </div>
                    </div>
                    <div class="hotel-pic">
                    	<a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="260" height="180" alt="" /></a>
                    </div>
                </li>
				 </@s.iterator>
             </ul>
          <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(function(){
			$("#hotel-list li").each(function(index){
				$("#hotel-list li").eq(index).hover(function(){
				$("#hotel-list li").removeClass("bg-hover");	
				$(this).addClass("bg-hover");
				},function(){
					$(this).removeClass("bg-hover");
					});
			});
		});
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
