<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${placeName?if_exists}公司旅游_${placeName?if_exists}商务旅游_企业旅游,定制游-驴妈妈旅游网</title>
<meta name="keywords" content="${placeName?if_exists}公司旅游,${placeName?if_exists}商务旅游" />
<meta name="description" content="驴妈妈旅游网(www.lvmama.com)-是国内最专业的旅游服务预订中心,${placeName?if_exists}公司旅游打造完美的公司旅游,商务旅游,${placeName?if_exists}企业定制游,团体旅游定制等高品质综合服务,相信我们!选择我们!详情请致电1010-6060" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css,/styles/v3/custom.css,/styles/v3/forms.css" >

<#include "/common/coremetricsHead.ftl">
</head>
<body class="custom">
<#include "/common/header.ftl">

<!-- wrap\\ 1 -->
<div class="wrap wrap-quick">
    <div class="aside fl">
    
        <#include "/WEB-INF/pages/www/customize/quick_menu.ftl">
        <!-- //.quick-menu 定制游分类目录 -->
		<div class="hr_d"></div>
        
	    <div class="side-box border sidebox1">
			<div class="side-title">
				<h4>联系我们</h4>
			</div>
			<div class="content">
			    <p class="gray">所有线路均可根据人数、住宿要求、出发时间进行调整提交定制</p>
				<p class="gray">
				联系电话：<span>400-1161-808</span> <br>
				投诉电话：<span>021-60561635</span>
				<!--<img src="http://pic.lvmama.com/img/v3/telphone.jpg" width="162" height="52" alt="驴妈妈定制游电话">-->
				</p>
			</div>
		</div>
		
		<div class="side-box">
		    <a href="http://www.lvmama.com/company/team.html"><img src="http://pic.lvmama.com/img/v3/team.jpg" width="200" height="88" alt="了解我们的团队" ></a>
		</div>
		
	</div> <!-- //.aside -->
    <div class="col-w fr">
		
		<div class="custom-process">
			<a id="goto_custom" href="javascript:void(0);">开始定制旅程</a>
		</div>
		
		<div class="cpro-item">
		     <@s.iterator value="productList" status="st"> 
	             <@s.if test="#st.isFirst()">
		            <h3><span>${bakWord1?if_exists}</span></h3>
	               </@s.if>
             </@s.iterator> 
			<div class="cpro-clist">
			    <ul>
             <@s.iterator value="productList" status="st"> 
			    	<li><a class="link-details" rel="nofollow" href="${url?if_exists}">下载行程</a><span>价格：<i>一团一议</i></span><a href="${url?if_exists}">${title?if_exists}</a></li>
			    	 </@s.iterator>  
			    </ul>
			</div>
		</div>
		
	</div> <!-- //.col-w -->
	
	
</div> <!-- //.wrap 2 -->

<!-- 帮助提醒 -->	
<#include "/common/footer_help.ftl">

<script src="http://pic.lvmama.com/js/common/copyright.js"></script>

<div class="hh_cooperate"> 
    <#include "/WEB-INF/pages/staticHtml/friend_link/customed_footer.ftl"> 
</div>

<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,js/v3/plugins.js,js/v3/app.js"></script>


<!-- 定制游 用户 开始定制旅程-->
<div id="xh_dialog" class="xh_dialog custon-form">
	<div class="xh_title">
		<span class="close">×</span>
		<h3>旅程信息</h3>
	</div>
	<div class="content">
		<form class="custom-start form-inline form-small">
			<p>　为方便我们为您提供服务，带有 <i class="req">*</i> 字段为必填字段。</p>
			<dl class="dl-hor">
				<dt>游玩类型：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="play_types" type="radio" value="business" checked="">公务考察</label>
					<label class="radio"><input class="input-radio" name="play_types" type="radio" value="meeting">商务会务</label>
					<label class="radio"><input class="input-radio" name="play_types" type="radio" value="visiting">观光型</label>
					<label class="radio"><input class="input-radio" name="play_types" type="radio" value="kuozhan">团队拓展</label>
					<label class="radio"><input class="input-radio" name="play_types" type="radio" value="wenquan">温泉游</label>
					<label class="radio"><input class="input-radio" name="play_types" type="radio" value="other">其他</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>出发地：</dt>
				<dd>
					<select name="starting" id="starting">
						<option value="shanghai" selected="selected">上海</option>
						<option value="beijing">北京</option>
						<option value="guangzhou">广州</option>
						<option value="chengdu">成都</option>
						<option value="hangzhou">杭州</option>
					</select>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>目的地：</dt>
				<dd>
					<input name="destination" id="destination" type="text"><span id="destinationError" class="red">请认真填写您的目的地，以便我们为您提供服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>出发日期：</dt>
				<dd>
					<input name="start_time" id="start_time" type="text"><span name="start_timeError" class="red">请选择出发日期，以便我们为您提供服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>游玩天数：</dt>
				<dd>
					<select name="play_days"> 
						<option value="1">1天</option>
						<option value="2" selected="selected">2天</option>
						<option value="3">3天</option>
						<option value="4">4天</option>
						<option value="5">5天</option>
						<option value="6">6天</option>
						<option value="7">7天</option>
						<option value="8">8天</option>
						<option value="9">9天</option>
					</select>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>出游人数：</dt>
				<dd><input class="input-min" name="people_number" id="people_number" type="text"> 人 
					<label class="inline">　其中包含老人 <input class="input-min" name="old_man" id="old_man" type="text" value="0"> 人</label>
					<label class="inline">　包含儿童 <input class="input-min" name="children" id="children" type="text" value="0"> 人</label> 
					<span id="people_numberError" class="red">请详细填写人员数量，以便我们为您提供更周全的服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>出游预算：</dt>
				<dd>
					<input id="travel_budget" class="input-min" type="text"> 元/人
					<span id="travel_budgetError" class="red">请详细填写出游预算，以便我们为您提供更周全的服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>学生团队：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="student" type="radio">是</label>
					<label class="radio"><input class="input-radio" name="student" type="radio">否</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>交通方式：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="traffic" type="radio">旅游巴士</label>
					<label class="radio"><input class="input-radio" name="traffic" type="radio">火车</label>
					<label class="radio"><input class="input-radio" name="traffic" type="radio">轮船</label>
					<label class="radio"><input class="input-radio" name="traffic" type="radio">飞机</label>
					<label class="radio"><input class="input-radio" name="traffic" type="radio">无需安排</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>住宿标准：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="hotel" type="radio" value="twinroom" checked="">普通双标间</label>
					<label class="radio"><input class="input-radio" name="hotel" type="radio" value="economic">经济型</label>
					<label class="radio"><input class="input-radio" name="hotel" type="radio" value="3star">三星级</label>
					<label class="radio"><input class="input-radio" name="hotel" type="radio" value="4star">四星级</label>
					<label class="radio"><input class="input-radio" name="hotel" type="radio" value="5star">五星级</label>
					<label class="radio"><input class="input-radio" name="hotel" type="radio" value="not">无需安排</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>用餐标准：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="dinner" type="radio" value="20" checked="">20～30元/人/餐</label>
					<label class="radio"><input class="input-radio" name="dinner" type="radio" value="30">30～40元/人/餐</label>
					<label class="radio"><input class="input-radio" name="dinner" type="radio" value="40">40～50元/人/餐</label>
					<label class="radio"><input class="input-radio" name="dinner" type="radio" value="50">50元以上/人/餐</label>
					<label class="radio"><input class="input-radio" name="dinner" type="radio" value="not">无需安排</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>导游要求：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="guide" type="radio" value="M" checked="">男导游</label>
					<label class="radio"><input class="input-radio" name="guide" type="radio" value="F">女导游</label>
					<label class="radio"><input class="input-radio" name="guide" type="radio" value="MF">男女均可</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>语言要求：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="language" type="radio" value="cn" checked="">中文导游</label>
					<label class="radio"><input class="input-radio" name="language" type="radio" value="ce">中文英导游</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>购物要求：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="buy_space" type="radio" value="not">不需要安排</label>
					<label class="radio"><input class="input-radio" name="buy_space" type="radio" value="bit" checked="">少量安排</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>会议安排：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="meeting" type="radio" value="need">需要</label>
					<label class="radio"><input class="input-radio" name="meeting" type="radio" value="not" checked="">不需要</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>备注：</dt>
				<dd>
					<textarea name="remark"></textarea>
				</dd>
			</dl>
			<h3 class="pepe_messg">联系人信息</h3>
			<dl class="dl-hor">
				<dt><i class="req">*</i>联系人：</dt>
				<dd>
					<input name="contact" id="contact" type="text">　　
					<label class="radio"><input class="input-radio" name="gender" type="radio" value="M" checked="">男</label> 
					<label class="radio"><input class="input-radio" name="gender" type="radio" value="F">女</label>
					<span id="contactError" class="red">请认真填写您的信息，以便我们为您提供服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt><i class="req">*</i>联系人电话：</dt>
				<dd>
					<input name="mobile" id="mobile" type="text"><span id="mobileError" class="red">请认真填写您的联系电话，以便我们为您提供服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>常用邮箱：</dt>
				<dd>
					<input name="email" id="email" type="text"><span id="emailError" class="error">请认真填写您的常用邮箱，以便我们为您提供服务</span>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>回复时间：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="reply_time" type="radio" value="anytime" checked="">随时</label>
					<label class="radio"><input class="input-radio" name="reply_time" type="radio" value="worktime">工作时间</label>
					<label class="radio"><input class="input-radio" name="reply_time" type="radio" value="nonworktime">非工作时间</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>优先回复：</dt>
				<dd>
					<label class="radio"><input class="input-radio" name="prior" type="radio" value="email">邮箱</label>
					<label class="radio"><input class="input-radio" name="prior" type="radio" value="mobile" checked="">电话</label>
				</dd>
			</dl>
			<dl class="dl-hor">
				<dt>验证码：</dt>
				<dd>
					<input class="input-min" name="verification" id="verification" type="text">
					<img src="http://www.lvmama.com/others/big_client/imgcode.php?_=1366518136447" id="verImg" alt="看不清楚，换一张" align="absmiddle" style="cursor: pointer;" onclick="javascript:newgdcode(this,this.src);">
				</dd>
			</dl>
			<p class="tc"><button id="custom_sumbit" class="btn btn-pink" type="button"  >&nbsp;&nbsp;提&nbsp;交&nbsp;&nbsp;</button></p>
		</form>
    </div>
</div>
<!-- 定制游 用户 开始定制旅程 end-->
<script>

$(function(){
    $("body").append('<iframe class="xh_overlay"></iframe><div class="xh_overlay"></div>');
	 <@s.if test="userId == null">
			 $("#goto_custom").ui("login");	
     </@s.if>
     <@s.else>
		$("#goto_custom").click(function(){
			xh_dialog();
		})
     </@s.else>
	
	//刷新验证码
	function newgdcode(obj,url) {
		obj.src = url+ '?nowtime=' + new Date().getTime();
		//后面传递一个随机参数，否则在IE7和火狐下，不刷新图片
	}
    function xh_dialog(){
       $(".red").hide();
		var top=$(document).scrollTop();
		$("div.xh_dialog").css({"top":top+0+"px","width":"800px","margin-left":"-400px"}).show();
		$("div.xh_overlay").show().height($(document).height());
		if ($.browser.msie && ($.browser.version == "6.0") && !$.support.style) {
			$("iframe.xh_overlay").show().height($(document).height());
		}
		$("#verImg").attr("src","http://www.lvmama.com/others/big_client/imgcode.php?_="+new Date().getTime());
	}
	$(".xh_dialog .close").click(function(){
		$(".xh_dialog,.xh_overlay").hide();
	})
	$("body").ui("calendar",{
	   input : "#start_time",
	   parm:{dateFmt:'yyyy-MM-dd'}
	})

	
	//出发日期
	if (trim($("#start_time").val()) == "") {
		$("#start_timeError").show();
		$("#start_time").focus();
		return;
	}
   
})
//提交
	$("#custom_sumbit").click(function(){
			 tijiao(); 
		})
 
	function tijiao() {
		 $(".red").hide();
		//目的地
		if (trim($("#destination").val()) == "" || trim($("#destination").val()).length >= 23 || /^\d+?$/.test(trim($("#destination").val()))) {
			$("#destinationError").show();
			$("#destination").focus();
			return;
		}


		//出发日期
		if (trim($("#start_time").val()) == "") {
			$("#start_timeError").show();
			$("#start_time").focus();
			return;
		}

		//出游人数
		if (trim($("#people_number").val()) == ""
		|| isNaN($("#people_number").val())
		|| trim($("#old_man").val()) == ""
		|| isNaN($("#old_man").val())
		|| trim($("#children").val()) == ""
		|| isNaN($("#children").val())) {
			$("#people_numberError").show();
			if (trim($("#people_number").val()) == "" || isNaN($("#people_number").val())) {
				$("#people_number").focus();
			}
			if (trim($("#old_man").val()) == "" || isNaN($("#old_man").val())) {
				$("#old_man").focus();
			}
			if (trim($("#children").val()) == "" || isNaN($("#children").val())) {
				$("#children").focus();
			}
			return;
		}

		//出游预算
		if(trim($("#travel_budget").val()) == "" ){
			$("#travel_budgetError").show();
			$("#travel_budget").focus();
			return;
		}
		if ($("#travel_budget").val() != 'undefined' && trim($("#travel_budget").val()) != "" && isNaN($("#travel_budget").val())) {
			alert("请填写正确的出游预算!");
			$("#travel_budget").focus();
			return;
		}

		//联系人
		if (trim($("#contact").val()) == "" || $("#contact").val().length >= 20 || !/^\D+?$/.test(trim($("#contact").val())) ) {
			$("#contactError").show();
			$("#contact").focus();
			return;
		}

		//联系人电话
		if (trim($("#mobile").val()) == "" || !/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(trim($("#mobile").val()))) {
			$("#mobileError").show();
			$("#mobile").focus();
			return;
		}

                //验证码
		if (trim($("#verification").val()) == "") {
			alert("请填写正确的验证码!");
			$("#verification").focus();
			return;
		}

		$("#addform").submit();
		//document.addform.reset();
		$("#xh_dialog,.xh_overlay").hide();
		alert("提交成功");
		
	}

	function trim(str) {
		return str.replace(/(^\s+)|(\s+$)/g, "");
	}
</script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
		<script>
        	cmCreatePageviewTag("定制游_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(placeName)"/>", "L0001", null, null);
        </script>
</body>
</html>