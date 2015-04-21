<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8" />
    <title>点评发表页</title>

    <link href="http://pic.lvmama.com/min/index.php?g=ob_commentMySpace" rel="stylesheet" type="text/css" media="screen" charset="utf-8"/>
    <link href="http://pic.lvmama.com/min/index.php?g=commonIncluedTop" type="text/css" rel="stylesheet"/>      
    <link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
</head>
<body>

<div class="pageWrap">
<#include "/common/header.ftl">
<div class="mainContainer">
    <div class="mainCol">
        <div class="commentSearch">
            <div class="csTitle">
                <h3 class="add_xu_1701"><span class="writeCommentIcon"></span> <b>我要写点评</b><span style="font-size:12px;">&nbsp;超过<b>500,000</b>条精彩趣评，返奖金<b>&yen;895,210</b>，涉及上万个旅游景点……</span></h3>
                <p style="margin:15px 0 0 240px;font-weight:700;"><a href="http://www.lvmama.com/comment/zt/ssly/?losc=lpdpfb001_zt_lvmama" target="_blank">【驴友口碑榜】国内十大最潮的水上乐园</a></p>
                <ul class="tabs">
                    <li onClick="javaScript:changeCtn('2');">景点</li>
                    <li onClick="javaScript:changeCtn('3');">酒店</li>
                </ul>
            </div>
            <div class="panes">
                <input id="stage" value="2" type="hidden"/>
                <p class="inputCon">
	                <input class="searchInput" id="keyWord"  autocomplete="off" type="text"/>
	                <input class="submitBu" id="searchBtn" type="button" value="Continue &rarr;"/>
                </p>
            </div>
        </div>
        <div class="placeList">            
            <h3 id="ctn"><samp>请输入景点名称. 例如：上海欢乐谷</samp></h3>
            <form id="listForm" action="#">
                <ul size="10" name="" id="number_listbox"></ul>
                <p><input class="button" type="button" id="iCan" value="写点评"/></p>
            </form>                  
        </div>
    </div>
    
    <!--    Right: 右边栏提示内容        -->
    <div class="rightCol">
        <ol>
            <li><span class="listStyle">1、</span>发表体验点评，还可获得不同金额的点评奖金<strong>¥5-¥30</strong>不等。(<a class="whatJJBtn" href="#">什么是点评奖金？</a>)</li>
			<li><span class="listStyle">2、</span>写点评→<a href="http://www.lvmama.com/points" target="_blank">赚积分</a>→换超值礼品</li>
            <li><span class="listStyle">3、</span>精华点评可获3倍以上积分奖励哦。</li>
            <li><span class="listStyle">4、</span>点评和打分都将是其他网友的参考依据，并影响该景区的评价。我们注重点评的客观和真实，拒绝一切虚假点评。</li>
            
        </ol>
        <div class="whatJJDiv">
            <div class="whatBg" style="height: 174px;"></div>
            <div class="whatJJCont">
                <s></s>
                <b>点评奖金：</b您在驴妈妈旅游网上成功订购（指在线支付）门票、酒店、自由行、跟团游等产品，按预订日期顺利结束游玩或入住，对游玩的景区或入住的酒店发表点评，内容通过审核后，即可得到驴妈妈旅游网赠送给您的现金奖励。奖金会在点评通过的3个工作日之后，自动返还到奖金账户。<br>
                <a class="viewJJ" target="_blank" href="http://www.lvmama.com/public/help_285">查看详细规则&gt;&gt;</a>
            </div>
        </div>
    </div>
    <div class="cb"></div>
</div>
</div>
﻿<#include "/common/footer_top.ftl">
<#include "/common/footer_bottom.ftl">
</body>
    <script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>    
    <script src="http://pic.lvmama.com/js/new_v/ui_plugin/jquery.wb_tabs.js?r=7364" type="text/javascript" charset="utf-8"></script>
    <script src="http://www.lvmama.com/dest/js/comment/jquery.jsonSuggest.cmt2.js" type="text/javascript"></script>
    <script src="http://www.lvmama.com/dest/js/comment/selectPlace.js" type="text/javascript"></script>
</html>
