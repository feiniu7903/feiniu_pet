<!DOCTYPE html>
<head>
<meta  charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>古镇客栈</title>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/guzhenhotel/guzhenhotel.css" rel="stylesheet" type="text/css" />
<base target="_blank" />
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div class="wraper">
	<div class="wraper-in">
    	<div class="wraper-inner">
            <div class="banner">
                <img src="http://pic.lvmama.com/img/zt/guzhenhotel/ban-01.jpg" alt="" />
                <img src="http://pic.lvmama.com/img/zt/guzhenhotel/ban-02.jpg" alt="" />
            </div>
            <div class="hotel-list">
            	<div class="hotel-list-bg">
                	<ul class="hotel-list-cont">
                    <!--li 是一个 循环-->
                    <@s.iterator value="map.get('${station}_10619')" status="st">		
                    	<li>
                        	<a class="hotel-pic" href="${url?if_exists}"><img src="${imgUrl?if_exists}" alt="${title?if_exists}" width="282px" height="200px"/>
                          <a class="hotel-name" href="${url?if_exists}">${title?if_exists}</a>
                          <div class="pri"><span class="price">¥<i>${memberPrice?if_exists?replace(".0","")}</i>起</span><em class="${bakWord1?if_exists}"></em></div>
                          <p class="hotel-detail">${bakWord2?if_exists}</p>
                      </li>
										</@s.iterator>
                    <!--li  end-->
                    </ul>
                	
                </div>
            </div>
        </div>
    </div>

</div>

<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
