<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>旅游团购-驴妈妈旅游网-疯抢记录</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v7style/globalV1_0.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/group.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_tg/lvtg.css"/>

<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"  charset="utf-8"></script>
<script src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/ob_tg/tg.js"  charset="utf-8"></script>
</head>
<body class="tuangou">
<@s.set name="currentTab" value="'record'"/>
<#include "/common/header.ftl">

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
    <#-- <li>
       <span class="lvtg_headtxt">驴妈妈旅游团购：最专业、最保障、最新颖、最实惠的旅游团购！</span>
    </li> -->
    <li class="lvtg_headR">
        <a href="http://www.lvmama.com/tuangou/" class="lvtg_link_blue">往期团购</a>
        <a href="http://www.lvmama.com/edm/showSubscribeEmail.do" class="lvtg_email" target="_blank">邮件订阅</a>
    </li>
</ul><!--hh_sub_recom end-->

<!--Content Area-->
<div class="shortinner">
	<!--Ad-->
    <#include "/WEB-INF/pages/group/include/ad.ftl" />
    <!--leftContent-->
    <div class="gropu-l-c">
	<!---product-->
        <div class="record-block">
        	<h2>疯抢记录</h2>
        	<ul class="record-list tlist">
        		<@s.iterator value="recordList" status="record">
		            	<li class="record-border">
		                	<dl <@s.if test="(#record.index+1)%2==0">class="right-dl"</@s.if>>
		                    	<dt><@s.date name="prodProduct.onlineTime" format="yyyy年MM月dd日"/> </dt>
		                        <dt><a href="/product/${prodProduct.productId}" >${prodProduct.productName}</a></dt>
		                        <dd>
		                        	<div class="group-state <@s.if test="${completeFlag=='Y'}">group-over</@s.if>">
		                            	<a href="/product/${prodProduct.productId}"><img width="200px" height="100px" src="${prodProduct.absoluteSmallImageUrl}" /></a>
		                            	<span class="img-bg"></span>
		                            </div>
		                            <p>
		                            	<strong>${orderCount}</strong>人购买<br />
		                                原价：<del>&yen;${prodProduct.marketPriceYuan}</del><br />
		                                折扣：<em><@s.if test="${discount<10}">${discount}</@s.if><@s.else>无</@s.else>折</em><br />
		                                现价：<em>&yen;${prodProduct.sellPriceYuan}</em>
		                            </p>
		                        </dd>
		                        <dd>共为用户节省：<strong>&yen;${(prodProduct.getMarketPriceYuan()-prodProduct.getSellPriceYuan())*orderCount}</strong></dd>
		                    </dl>
		                </li>
                </@s.iterator>
                <@s.if test="recordList.size%2>0">
                		<li class="record-border">
                			<dl class="right-dl" >
		                    	
		                    </dl>
                		</li>
                </@s.if>
          </ul>
          <!--page-->
          <ul class="record-page">
            	<@s.if test="page>1 && page<=totalPage">
            			<li><a href="/tuangou/fengqiang_1">首页</a></li>
            			<li><a href="/tuangou/fengqiang_${page-1}">上一页</a></li>
            	</@s.if>
            	<@s.set name="startPage" value="1"/>
            	<@s.if test="totalPage==1">
            			<li class="current-page">1</li>
            	</@s.if>
            	<@s.if test="totalPage>1">
            			<#list pageRange.minPage .. pageRange.maxPage as  pix>
            					<#if pix==page >
            						<li class="current-page">${pix}</li>
            					</#if>
            					<#if pix!=page >
            						<li ><a href="/tuangou/fengqiang_${pix}">${pix}</a></li>
            					</#if>
						</#list>
            	</@s.if>
            	<@s.if test="totalPage>1 && page<totalPage">
            			<li><a href="/tuangou/fengqiang_${page+1}">下一页</a></li>
            	</@s.if>
                <@s.if test="totalPage>1 && page!=totalPage" >
                	<li><a href="/tuangou/fengqiang_${totalPage}">末页</a></li>
                </@s.if>
                 <@s.if test="page==totalPage" >
                	<li><a href="  http://tuan.lvmama.com/team/index.php" target="_blank">老系统往期团购</a></li>
                </@s.if>
              
            </ul>
        </div>
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
	<div style="width:980px;margin:0 auto 5px;">
		<#include "/WEB-INF/pages/group/include/group_footer.ftl">
	</div>
<script type="text/javascript">
	  function switchIndex(placeCode,placeId,placeName){
	        var $switchIndexForm = $('#switchIndexForm');
	        $('#fromPlaceCode').val(placeCode);
	        $('#fromPlaceId').val(placeId);
	        $('#fromPlaceName').val(placeName);
	        $switchIndexForm.submit();
	   }
</script>
</body>
</html>
