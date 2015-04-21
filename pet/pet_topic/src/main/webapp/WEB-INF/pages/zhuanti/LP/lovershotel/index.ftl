<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>情侣酒店_情侣主题酒店_上海,北京,南京,广州情侣酒店推荐-驴妈妈旅游网</title>
<meta name="keywords" content="情侣酒店,主题酒店" />
<meta name="description" content="驴妈妈旅游网为你打造更多情侣酒店,最舒适、最浪漫、最激情、最时尚的情侣而准备的情侣酒店和情侣宾馆。享受你最甜蜜的蜜月之旅。" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/qinglvhotel/css.css" rel="stylesheet" type="text/css" />
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
<!--内容从这开始-->
<div class="wrap01">
  <div class="wrap02">
    <div class="wrap">
      <div class="MainContent">
        <div class="top-banner">情侣酒店专场</div>
        <div class="lm-t-01"></div>
        
        <div class="lm-c">
        	<ul class="hotel" >
				<@s.iterator value="map.get('${station}_9542')" status="st">
	            	<li>
	                	<div class="lm-t-bg"></div>
	                    <div class="lm-c-bg">
	                    	<h3><a href="${url?if_exists}">${title?if_exists}</a>&nbsp;&nbsp;<span class="${bakWord1?if_exists}"></span></h3>
	                        <div class="jd-pic"><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="400" height="180" alt="" /></a></div>
	                        <div class="jd-price"><span class="rm">${bakWord2?if_exists}</span><span class="pr"><em>¥</em>${memberPrice?if_exists?replace(".0","")}</span></div>
	                        <p class="p-qrj"><em>【情侣套餐】</em>${bakWord3?if_exists}</p>
	                        <p class="p-addr"><em>【地  址】</em>${bakWord4?if_exists}</p>
	                        <p class="p-addr"><em>【备  注】</em>${bakWord5?if_exists}</p>
	                        <a href="${url?if_exists}" class="charge"><img src="http://pic.lvmama.com/img/zt/qinglvhotel/charge.jpg" width="102" height="32"  alt=""/></a>
	                    </div>
	                    <div class="lm-b-bg"></div>
	                </li>
				</@s.iterator>
            </ul>
            
            <div class="lm-t-02"></div>
	            <ul class="route">
					<@s.iterator value="map.get('${station}_9543')" status="st">
		            	<li>
		                	<div class="route-pic"><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="102" height="67" alt="" /></a></div>
		                    <div class="route-info">
		                    	<h2><a href="${url?if_exists}">${title?if_exists}</a></h2>
		                    	<p>产品特色：${bakWord1?if_exists}</p>
		                    </div>
		                    <div class="route-charge">
		                    	<p><em>¥</em>${memberPrice?if_exists?replace(".0","")}<font>起</font></p>
		                        <a href="${url?if_exists}"><img src="http://pic.lvmama.com/img/zt/qinglvhotel/charge.jpg" width="102" height="32"  alt=""/></a>
		                    </div>
		                </li>
					</@s.iterator>
	            </ul>
             <div class="lm-t-03"><h2>热门活动进行时</h2></div>
            
	        </div>
	           <ul class="hd">
				 <@s.iterator value="map.get('${station}_9544')" status="st">
		             	<li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="284" height="135" alt=""/></a></li>
			 	</@s.iterator>
	          </ul>
	      </div>
      
    </div>
  </div>
</div>


<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script> 

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
