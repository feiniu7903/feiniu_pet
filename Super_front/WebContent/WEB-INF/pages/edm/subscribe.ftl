<!doctype>
<html>
 <head>
  <meta charset="utf-8"/>
  <title>邮件订阅</title>
  <script src="http://pic.lvmama.com/min/index.php?g=common"></script>
  <link href="http://pic.lvmama.com/styles/new_v/header-air.css" type="text/css" rel="stylesheet"/>
  <link href="http://pic.lvmama.com/styles/new_v/ob_main/main.css" type="text/css" rel="stylesheet"/>
  <link href="http://pic.lvmama.com/styles/new_v/ui_plugin/lmmcomplete.css" rel="stylesheet" type="text/css">
  <link href="http://pic.lvmama.com/styles/new_v/ob_main/search_compl.css" type="text/css" rel="stylesheet"/>
  <link href="http://pic.lvmama.com/styles/new_v/ob_yjdy/yjdy.css" rel="stylesheet" type="text/css">
 </head>

 <body>
 <#include "/common/header.ftl"/>
	<div class="dy_main">
		<div class="dy_detail">
			<h1 class="dy_h1"><i>立即订阅驴妈妈旅游期刊，就有机会获得优惠券，享受购物折扣噢！</i>驴妈妈电子邮件订阅</h1>
			<table class="dy_table" cellspacing="10">
				<tr>
					<td class="dy_tleft"><i class="red">*</i> 我的订阅邮箱：</td>
					<td class="dy_tright">
						<div class="pos_r">
							<div id="edm_type_hidden_id"><@s.iterator value="subscribe.infoList" id="stat"><input type="hidden" name="type" value="<@s.property value="type"/>"/></@s.iterator></div>
							<input type="hidden" name="subscribe.id" value="<@s.property value='subscribe.id'/>"/>
							<input type="hidden" name="oldEmail" value="<@s.property value='subscribe.email'/>"/>
							<input type="hidden" name="subscribe.mustWantedTravel" value="<@s.property value='subscribe.mustWantedTravel'/>"/>
							<input type="hidden" name="subscribe.travelTime" value="<@s.property value='subscribe.travelTime'/>"/>
							<input type="text" class="dy_email" id="dy_email" name="subscribe.email"  value="<@s.property value='subscribe.email'/>"/>
							<span class="dy_error dy_e0">请输入订阅的邮箱地址</span>
							<span class="dy_success_icon dy_s0"></span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="dy_tleft dy_t_top"><i class="red">*</i> 我的出发地：</td>
					<td class="dy_tright">
						<div class="pos_r">
							<select name="subscribe.province" id="provinceId"  class="dy_select">
								<@s.iterator value="provinceList" >
									<option value ="<@s.property value="provinceId"/>" <@s.property value="checked"/>><@s.property value="provinceName"/></option>
								</@s.iterator>
							</select> <select class="dy_select" name="subscribe.city" id="cityId">
							<@s.if test="cityList.size()==0"><option>请选择</option>
							</@s.if>
							<@s.else>
								<@s.iterator value="cityList" >
									<option value="<@s.property value="cityId"/>" <@s.property value="checked"/>><@s.property value="cityName"/></option>
								</@s.iterator>
							</@s.else></select>
							<span class="dy_error dy_e1">请选择出发地</span>
							<span class="dy_success_icon dy_s1"></span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="dy_tleft"><i class="red">*</i> 我想要的信息：</td>
					<td class="dy_tright">
						<ul class="dy_myinfo">
							<li class="dy_my1"><a href="javascript:void(0)"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample02.jpg"/></a></li>
							<li class="dy_my2">
								<b>当季热卖</b>
								<p>精选国内优质旅游线路、景区门票，助你轻松安排自由行程</p>
							</li>
							<li class="dy_my3" li_type="subscribed" edm_type="PRODUCT_EMAIL" style="display:none;">已订阅 <a href="javascript:void(0)" class="yjdy_no_btn">取消</a></li>
							<li class="dy_my3" li_type="onsubscribed" edm_type="PRODUCT_EMAIL"><a class="yjdy_btn" hred="javascript:void(0)">订阅</a></li>
						</ul>
						<ul class="dy_myinfo">
							<li class="dy_my1"><a href="javascript:void(0)"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample03.jpg"/></a></li>
							<li class="dy_my2">
								<b>旅游资讯</b>
								<p>最全旅游攻略，最新旅游资讯，最热景点推荐</p>
							</li>
							<li class="dy_my3" li_type="subscribed" edm_type="MARKETING_EMAIL" style="display:none;">已订阅 <a href="javascript:void(0)" class="yjdy_no_btn">取消</a></li>
							<li class="dy_my3" li_type="onsubscribed" edm_type="MARKETING_EMAIL"><a class="yjdy_btn" hred="javascript:void(0)">订阅</a></li>
						</ul>
						<ul class="dy_myinfo">
							<li class="dy_my1"><a href="javascript:void(0)"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample05.jpg"/></a></li>
							<li class="dy_my2">
								<b>促销活动</b>
								<p>超好玩的线上线下活动，更有精美奖品哦</p>
							</li>
							<li class="dy_my3" li_type="subscribed" edm_type="SELF_HELP_EMAIL" style="display:none;">已订阅 <a href="javascript:void(0)" class="yjdy_no_btn">取消</a></li>
							<li class="dy_my3" li_type="onsubscribed" edm_type="SELF_HELP_EMAIL"><a class="yjdy_btn" hred="javascript:void(0)">订阅</a></li>
						</ul>
						<div class="dy_error dy_e2">请选择订阅类型</div>
						<div class="dy_success_icon dy_s2"></div>
					</td>
				</tr>
				<tr>
					<td class="dy_tleft"> 平常出游时间：</td>
					<td  class="dy_tright">
				        <span class="dy_cyspan"><label><input type="checkbox" name="travelTime" value="1"/> 周末、周边短途</label></span>
						<span class="dy_cyspan"><label><input type="checkbox" name="travelTime" value="2"/> 端午、清明、五一小长假</label></span>
						<span class="dy_cyspan"><label><input type="checkbox" name="travelTime" value="3"/> 十一黄金周</label></span>
						<span class="dy_cyspan"><label><input type="checkbox" name="travelTime" value="6"/> 圣诞元旦</label></span>
						<span class="dy_cyspan"><label><input type="checkbox" name="travelTime" value="4"/> 春节长假</label></span>
						<span class="dy_cyspan"><label><input type="checkbox" name="travelTime" value="5"/> 其他时间</label></span>
					</td>
				</tr>
				<tr>
					<td class="dy_tleft"> 最想去玩的地方：</td>
					<td class="dy_tright">
						<p>
							<select  class="dy_select" id="mustWantTravelProvince">
								<@s.iterator value="provinceList" >
									<option value ="<@s.property value="provinceId"/>"><@s.property value="provinceName"/></option>
								</@s.iterator>
							</select> <select class="dy_select" id="mustWantTravelCity"><option>请选择</option></select>
							<button class="dy_add">+ 添加</button> <i class="red">最多可添加5个</i></p>
						<p class="dy_chalist">
							<@s.iterator value="placeList" id="stat"><span class="dy_cha" value="<@s.property value="provinceId"/>-<@s.property value="cityId"/>"><@s.property value="cityName"/><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/cha.jpg"/></span></@s.iterator>
						</p>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td class="dy_tright">
						<div class="pos_r">
							<input type="image" src="http://pic.lvmama.com/img/new_v/ob_yjdy/dy_submit.jpg" id="submitButton"/>
							<div class="dy_error dy_e3" style="display: none;"></div>
							<div class="dy_success" style="display:none;">恭喜您订阅成功！</div>
						</div>
					</td>
				</tr>
			</table>
			<div class="dy_bottom">
				<h1>友情提示</h1>
				<p>尊重您的邮件隐私：邮件地址仅用于驴妈妈旅游网发布信息</p>
				<p>尊重您的退订选择：您可以随时通过您所收到的邮件中进行各类邮件的退订</p>
			</div>
		</div>
		<div class="dy_near_top">
			<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('e08a3fac622f70170001','js',null)"/>
				
				<div class="dy_near_top_bg"></div>
		</div>
		<div class="dy_near">
			<b class="dy_rb">最新订阅周刊预览</b>
			<div class="dy_content">
				<div class="dy_line1"></div>
				<div class="dy_rlist">
					<a href="http://www.lvmama.com/zt/edm/xinpin/sh.html" target="_blank"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample01.jpg"/></a><br/>
					<b class="dy_rlb">当季热卖</b><br/>
					<a class="dy_rla" href="http://www.lvmama.com/zt/edm/xinpin/sh.html" target="_blank">预览</a>
				</div>
				<div class="dy_rlist">
					<a href="http://www.lvmama.com/zt/edm/zixun/sh.html" target="_blank"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample06.jpg"/></a><br/>
					<b class="dy_rlb">旅游资讯</b><br/>
					<a class="dy_rla" href="http://www.lvmama.com/zt/edm/zixun/sh.html" target="_blank">预览</a>
				</div>
				<div class="dy_line2"></div>
				<div class="dy_rlist">
					<a href="http://www.lvmama.com/zt/edm/tuangou/sh.html" target="_blank"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample07.jpg"/></a><br/>
					<b class="dy_rlb">超值团购</b><br/>
					<a class="dy_rla" href="http://www.lvmama.com/zt/edm/tuangou/sh.html" target="_blank">预览</a>
				</div>
				<div class="dy_rlist">
					<a href="http://www.lvmama.com/zt/edm/hd01/01.html" target="_blank"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/sample08.jpg"/></a><br/>
					<b class="dy_rlb">促销活动</b><br/>
					<a class="dy_rla" href="http://www.lvmama.com/zt/edm/hd01/01.html" target="_blank">预览</a>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<script>
	function is_single(email,isUpdate){
		var result = false;
		$.getJSON("/edm/checkEmailIsSubscribe.do?subscribe.email=" + email + "&isUpdate="+isUpdate+"&jsoncallback=?",function(json){
				if(json.success ==false){
					alert(json.errorText);
					return false;
				}else{
					result = true;
				}		
			});	
		return result;
	}
	function check_subscribe_type(){
		if($("#edm_type_hidden_id").children().size()<1){
	 		$(".dy_e2").css("display","inline-block").next().hide();
	 	}else{
	 		$(".dy_s2").css("display","inline-block").prev().hide();
	 	}
	}
	function check_email(email){
			var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if($.trim($(email).val())==""){
				$(email).next().html("请输入订阅邮箱").css("display","inline-block").next().hide();
			}else if(!reg.test($(email).val())){
				$(email).next().html("邮箱格式不正确，请重新输入").css("display","inline-block").next().hide();
			}else{
				$(email).next().hide().next().css("display","inline-block");
			}
		}
	 $(function(){
	 	$("#dy_email").blur(function(){check_email(this);});
	 	$("#provinceId").change(function(){
	 		$(".dy_s1").hide().prev().hide();
	 		var provinceId = $(this).val();
	 		if(provinceId==null || $.trim(provinceId).length == 0){
	 			$(".dy_e1").css("display","inline-block").next().hide();
	 			$("[name^=subscribe][name$=city]").empty().append("<option>请选择</option>");
	 			return;
	 		}
			$.getJSON("/edm/getCityByProvinceId.do?provinceId=" + provinceId + "&jsoncallback=?",function(json){
				var list = json.list;
				$("[name^=subscribe][name$=city]").empty();
				for(var i=0;i<list.length;i++){
					var city = list[i];
					$("[name^=subscribe][name$=city]").append("<option value="+city.cityId+">"+city.cityName+"</option>");
				}
				$(".dy_e1").css("display","inline-block").next().hide();			
			});	
	 	});
	 	$("#cityId").change(function(){
	 		var city = $(this).val();
	 		$(".dy_e1").hide().next().hide();
	 		if(city==null || $.trim(city).length == 0 || city=="null"){
	 			$(".dy_e1").css("display","inline-block");
	 		}else{
	 			$(".dy_e1").next().css("display","inline-block");
	 		}
	 	});
	 	$("#mustWantTravelProvince").change(function(){
	 		var provinceId = $(this).val();
	 		if(provinceId==null || $.trim(provinceId).length == 0){
	 			$("#mustWantTravelCity").empty().append("<option>请选择</option>");
	 			return;
	 		}
			$.getJSON("/edm/getCityByProvinceId.do?provinceId=" + provinceId + "&jsoncallback=?",function(json){
				var list = json.list;
				$("#mustWantTravelCity").empty();
				for(var i=0;i<list.length;i++){
					var city = list[i];
					$("#mustWantTravelCity").append("<option value="+city.cityId+">"+city.cityName+"</option>");
				}			
			});	
	 	});
	 	$(".yjdy_btn").click(function(){
	 		$("#edm_type_hidden_id").append("<input type='hidden' name='type' value='"+$(this).parent().attr("edm_type")+"'/>");
	 		$(this).parent().hide();
	 		$("li[li_type=subscribed][edm_type="+$(this).parent().attr("edm_type")+"]").css("display","inline-block");
	 		check_subscribe_type();
	 	});
	 	$(".yjdy_no_btn").click(function(){
	 		$(":hidden[name=type]").remove("[value="+$(this).parent().attr("edm_type")+"]");
	 		$(this).parent().hide();
	 		$("li[li_type=onsubscribed][edm_type="+$(this).parent().attr("edm_type")+"]").css("display","inline-block");
	 		check_subscribe_type();
	 	});
	 	$(".dy_add").click(function(){
	 		if($(".dy_cha").size()>=5){
	 			return false;
	 		}
	 		var mustWantTravelCity=$("#mustWantTravelCity").val();
	 		if(null==mustWantTravelCity || "null"==mustWantTravelCity || ""==mustWantTravelCity || "请选择"==mustWantTravelCity){
	 			return false;
	 		}
	 		var element = null;
	 		$(".dy_cha").each(function(){
	 			if($(this).attr("value")==($("#mustWantTravelProvince").val()+"-"+mustWantTravelCity)){
	 				element = $(this).attr("value");
	 			};
	 		});
	 		if(element != null){
	 			return false;
	 		}
	 		$(".dy_chalist").append("<span class=\"dy_cha\" value=\""+$("#mustWantTravelProvince").val()+"-"+mustWantTravelCity+"\">"+$("#mustWantTravelCity").find(":selected").text()+"<img src=\"http://pic.lvmama.com/img/new_v/ob_yjdy/cha.jpg\"/></span>");
	 		var mustWantedTravel="";
	 		$(".dy_cha").each(function(){
	 			mustWantedTravel +=$(this).attr("value")+",";
	 		});
	 		if(mustWantedTravel.length>0){
	 			mustWantedTravel = mustWantedTravel.substring(0,mustWantedTravel.length-1);
	 		}
	 		$(":hidden[name$=mustWantedTravel]").val(mustWantedTravel);
	 	});
	 	$(".dy_cha").live("click",function(){
	 		$(this).removeAttr("value").remove().empty();
	 		var mustWantedTravel="";
	 		$(".dy_cha").each(function(){
	 			mustWantedTravel +=$(this).attr("value")+",";
	 		});
	 		if(mustWantedTravel.length>0){
	 			mustWantedTravel = mustWantedTravel.substring(0,mustWantedTravel.length-1);
	 		}
	 		$(":hidden[name$=mustWantedTravel]").val(mustWantedTravel);
	 	});
	 	$(":checkbox[name=travelTime]").change(function(){
	 		var travelTime="";
	 		$(":checkbox[name=travelTime]:checked").each(function(){
	 			travelTime +=$(this).attr("value")+",";
	 		});
	 		if(travelTime.length>0){
	 			travelTime = travelTime.substring(0,travelTime.length-1);
	 		}
	 		$(":hidden[name^=subscribe][name$=travelTime]").val(travelTime);
	 	});
	 	$(document.body).ready(function(){
		 	$(":hidden[name=type]").each(function(){
		 		$("li[li_type=subscribed][edm_type="+$(this).val()+"]").css("display","inline-block");
		 		$("li[li_type=onsubscribed][edm_type="+$(this).val()+"]").hide();
		 	});
			var travelTime=$(":hidden[name^=subscribe][name$=travelTime]").val();
			var travelTime = travelTime.split(",");
			$(":checkbox[name=travelTime]").each(function(){
				var value = $(this).val();
				for(var i=0;i<travelTime.length;i++){
					if(value == travelTime[i]){
						$(this).attr("checked","checked");
						break;
					}
				}
			});
			$("#provinceId").children().each(function(){if($(this).val()=="<@s.property value="subscribe.province"/>"){$(this).attr("selected","selected");}});
			$("#cityId").children().each(function(){if($(this).val()=="<@s.property value="subscribe.city"/>"){$(this).attr("selected","selected");}});
	 	});
	 	$("#submitButton").click(function(){
	 		//1.验证用户所填EMAIL，所在地，订阅邮件类型
	 		$(".dy_e3").hide().next().hide();
	 		check_email(".dy_email");
	 		var city = $("#cityId").val();
	 		if($.trim(city).length == 0 || null == city || undefined == city || "请选择" == city){
	 			$(".dy_e1").css("display","inline-block").next().hide();
	 		}else{
	 			$(".dy_s1").css("display","inline-block").prev().hide();
	 		}
	 		var id=$(":hidden[name^=subscribe][name$=id]").val();
	 		var url = "/edm/subscribeEmail.do";
	 		var isCreate = true;
	 		if($.trim(id).length == 0 || null ==id || undefined == id){
	 			check_subscribe_type();
	 		}else{
	 			isCreate = false;
	 			url = "/edm/updateSubscribeEmail.do";
	 		}
	 		if($(".dy_s0").is(":hidden")||$(".dy_s1").is(":hidden")|| ($(".dy_s2").is(":hidden") && isCreate)){
	 			return false;
	 		}
	 		var subscribe ="({";
	 		subscribe+='\"oldEmail\":\"'+  $(":hidden[name=oldEmail]").val()+'\",';
	 		subscribe+='\"email\":\"'+  $(":text[name^=subscribe][name$=email]").val()+'\",';
	 		subscribe+='\"subscribe.id\":\"'+ $(":hidden[name^=subscribe][name$=id]").val()+'\",';
	 		subscribe+='\"subscribe.email\":\"'+  $(":text[name^=subscribe][name$=email]").val()+'\",';
	 		subscribe+='\"subscribe.province\":\"'+  $("[name^=subscribe][name$=province] :selected").val()+'\",';
	 		subscribe+='\"subscribe.city\":\"'+  $("[name^=subscribe][name$=city] :selected").val()+'\",';
	 		subscribe+='\"subscribe.travelTime\":\"'+  $(":hidden[name^=subscribe][name$=travelTime]").val()+'\",';
	 		subscribe+='\"subscribe.channel\":\"DETAIL\",';
	 		subscribe+='\"subscribe.mustWantedTravel\":\"'+  $(":hidden[name^=subscribe][name$=mustWantedTravel]").val()+'\",';
	 		var end =$("#edm_type_hidden_id").children().size();
	 		var i=0;
	 		var type = new Array();
		 	$("#edm_type_hidden_id").children().each(function(){
		 		type.push($(this).val());
		 	});
		 	subscribe+="\"regEdmType\":\""+type+'\",';
	 		if(subscribe.length > 1){
				subscribe = subscribe.substring(0, subscribe.length-1);
			}
	 		subscribe +="})";
	 		subscribe = eval(subscribe);
			$.ajax({
				type:"POST",
				async:true,
				url:url,
				data:subscribe,
				dataType:"json", 
				success:function (data) {
					 var o=data;
					 if(data.success!=true && data.success!="true"){
					 	var error = data.errorText;
					 	if(error=="A"){
					 		error = "请填写邮箱后订阅";
					 	}else if(error=="B"){
					 		error = "请填写正确的邮箱";
					 	}else if(error=="C"){
					 		error = "请用其它邮箱订阅";
					 	}else if(error=="D"){
					 		error = "请选择订阅邮件类型";
					 	}else if(error=="F"){
					 		error = "订阅失败，请稍后重试";
					 	}
					 	$(".dy_e3").css("display","inline-block").html(error);
					 }else{
					 	$(".dy_success").css("display","inline-block");
					 }
				}});
	 	});
	 });
	</script>
	<#include "/common/footer.ftl"/>
 </body>
</html>
