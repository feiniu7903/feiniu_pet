<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>确认订单_积分商城—驴妈妈旅游网</title>
		<link href="http://pic.lvmama.com/styles/points/points_mall.css?r=4550" type="text/css" rel="stylesheet" />
        <link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
        <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css" > 
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v3/points.css">
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css" />
        
	</head>
	<body>
		<!-----------头部文件区域 S-------------->
		<#include "/common/header.ftl">
		<div class="main clearfix">
			<div class="mainTop">
				<p>
					<strong>您当前所处的位置：</strong>
					<span><a href="http://www.lvmama.com/">首页</a></span>
					<span><a href="/points">积分商城</a></span>
					<span>确定订单</span>
				</p>
			</div>
	
			<!----------------------------mainLeft-------------------------->
			<#include "/WEB-INF/pages/shop/mainLeft.ftl">
			
			<!----------------------------mainRight-------------------------->
			<form id="myForm" method="POST" action="/shop/createOrder.do">
				<input type="hidden" name="productId" value="${productId}" />
				<input type="hidden" name="productType" value="${product.productType}" />
				<input type="hidden" name="quantity" id="quantity" value="1"/>
				<input type="hidden" name="userInfo.address" id="userInfoAddress" />
				<input type="hidden" name="userInfo.name" id="userInfoName" />
				<input type="hidden" name="userInfo.mobile" id="userInfoMobile" />
				<input type="hidden" name="userInfo.zip" id="userInfoZip" />
				<@s.token></@s.token>
			</form>
			
		    <div class="mainRight">
				<div class="ord-top02">确认订单</div>
		        <div class="ordText clearfix">
		        	<h3>请确认您的订单信息</h3>
		        	<input type="hidden" id="shopUserPoint" value="<@s.property value="shopUser.point"/>"/>
		        	<input type="hidden" id="changePoint" value="${product.pointChange}"/>
		        	<input type="hidden" id="isValidate" value="${product.isValidate}"/>
		        	<table border="0" cellspacing="0" cellpadding="0" class="orderproList">
							    <tr class="orderproListTop">
		                            <td class="col1">商品名称</td>
		                            <td class="col2">所需积分</td>
		                            <td class="col3">数量</td>
		                            <td class="col4">合计</td>
							    </tr>
								<tr>
									<td class="col1">${product.productName}</td>
									<td class="col2">${product.pointChange}</td>
									<td class="col3 btn-nums">
									    <button type="button" class="btn-minus" onclick="minQuantity()">-</button>
									    <button type="button" class="btn-add" onclick="addQuantity(${product.stocks})">+</button>
									    <input type="text"  class="inp-text" id="inp-text" name="inp-text"  value="1" readOnly="true">
									</td>
									<td class="col4"><strong id="productChangePoint">${product.pointChange}</strong></td>
								</tr>	
					  </table>
					  
			          <h3>收件人信息  <a href="javascript:void(0)" class="InformationClick" onclick="$('.XinxiTanchu').show()" id="addInfo">添加</a></h3>
			          <div class="Information">
			              <p class="InformationMain" id="InformationMain" onclick="$('.XinxiTanchu').show()" ></p>
			          </div>
			          <#if product.isValidate == 'Y'>
			              <#include "/WEB-INF/pages/shop/validateMobile.ftl"/>
		              </#if>
		              <div class="rule">
		              		<h4>兑换前请确认已接受以下规则</h4>
		                    <div class="ruleText">
		                    	<p>1、用户在使用积分商城过程中产生的任何风险由用户自己承担。 </p>
								<p>2、用户使用积分商城服务而产生的一切后果也由其自行承担，积分商城对用户网上兑换等行为的影响不承担任何责任。 </p>
								<p>3、商品库存仅提供参考，是否存货以实际情况为准。用户需要兑换的物品缺货时，积分商城有权根据库存情况更换为其他等值物品。 </p>
								<p>4、积分商城不能为邮寄过程中出现的问题负责。 </p>
								<p>5、积分商城暂不提供查单，用户在45天后仍没有收到物品可联系我们。 </p>
								<p>6、积分商城不担保网络服务一定能满足用户的要求，也不担保网络服务不会中断，对网络服务的及时性、安全性、准确性也都不作担保。 </p>
								<p>7、积分商城不保证为向用户提供便利而设置的外部链接的准确性和完整性，同时，对于该外部链接指向的不由积分商城实际控制的任何网页上的内容，积分商城不承担任何责任。 </p>
								<p>8、对于因不可抗力或积分商城不能控制的原因造成的网络服务中断或其它缺陷，积分商城不承担任何责任，但将尽力减少因此而给用户造成的损失和影响。 </p>
								<p>9、市场价为采购价或市场标价，仅供参考。 </p>
								<p>10、积分、积分商城商品以及兑换服务在法律范围内最终解释权归驴妈妈旅游网所有。</p>
		                    </div>
		                     <label><input name="jieshou" id="jieshou"  type="checkbox" class="jieshou" checked="true"/>
		                    我接受以上规则</label>
		              </div>
		              <p class="amount">总需:<strong id="totalChangePoint">${product.pointChange}</strong>积分</p>
		              <p class="next_points_p"><a href="javascript:void(0)" class="next_points_order button_bg" id="submit-a">确认下单</a></p>             
		        </div>
		    </div>
		</div>

		<!-----------填写收件人地址-------------->
		<div class="XinxiTanchu" style="display:none">
		     <h4>带<span>&nbsp;</span>为必填项<strong class="close"></strong></h4>
		      <ul>
		          <li><label>姓名：</label><input id="name" type="text" /></li>
		          <li><label>手机：</label><input id="mobile" type="text" value="${users.mobileNumber}" /></li>
		          <li><label>地址：</label>
				      <select id="province" class="select-left" onChange="updateCities(this.value)">
						<option value ="北京市">北京市</option>
						<option value ="天津市">天津市</option>
						<option value ="河北省">河北省</option>
						<option value ="山西省">山西省</option>
						<option value ="内蒙古">内蒙古</option>
						<option value ="辽宁省">辽宁省</option>
						<option value ="吉林省">吉林省</option>
						<option value ="黑龙江省">黑龙江省</option>
						<option value ="上海市" selected>上海市</option>
						<option value ="江苏省">江苏省</option>
						<option value ="浙江省">浙江省</option>
						<option value ="安徽省">安徽省</option>
						<option value ="福建省">福建省</option>
						<option value ="江西省">江西省</option>
						<option value ="山东省">山东省</option>
						<option value ="河南省">河南省</option>
						<option value ="湖北省">湖北省</option>
						<option value ="湖南省">湖南省</option>
						<option value ="广东省">广东省</option>
						<option value ="广西省 ">广西省 </option>
						<option value ="海南省">海南省</option>
						<option value ="重庆市">重庆市</option>
						<option value ="四川省">四川省</option>
						<option value ="贵州省">贵州省</option>
						<option value ="云南省">云南省</option>
						<option value ="西藏">西藏</option>
						<option value ="陕西省">陕西省</option>
						<option value ="甘肃省">甘肃省</option>
						<option value ="青海省">青海省</option>
						<option value ="宁夏">宁夏省</option>
						<option value ="新疆">新疆省</option>
						<option value ="香港">香港省</option>
						<option value ="澳门">澳门省</option>
						<option value ="台湾">台湾省</option>
						
					</select>省
					<select id="cityId" class="select-right" name="cityId" >
						<option value ="上海市" selected>上海市</option>
					</select>市
		          </li>
		          <li><input id="address" type="text" class="address01" value="地址" /></li> 
		          <li><label>邮编：</label><input id="zip" type="text"  value="200000" /></li>
		          <li class="clearfix"><b class="button04" >确定</b><b class="button05">清空</b></li>
		       </ul>
		</div>
		
		<div class="DingdanTanchu" style="display:none">
		     <h4>订单无法完成</h4>
		  	 <p>库存数量不足！</p>
		     <span class="button06"  style="width:55px;"  onclick="$('.DingdanTanchu').hide()">确定</span>
		</div>
		
		<!-----------底部文件区域 S -------------->
		<#include "/common/orderFooter.ftl">
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/points/shop.js?r=4440"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/points/points_mall.js?r=2943"></script> 
<script type="text/javascript" src="/js/shop/shopValidate.js"></script>
<script type="text/javascript">
$(document).ready(function () {

	<#if errorText??>
		alert("${errorText}");
	</#if>			
	//限制文本框只能输入>=1的数字		
		$('#inp-text').bind('keyup',function() {
			var v = $(this).val();
			if(v.length > 0) {
				if(isNaN(v)) {
					alert('只能输入数字！');
					$(this).val("1");
				}else if (v < 1){
					alert('必须大于0！');
					$(this).val("1");
				}else if(v > ${product.stocks}) {
					alert("不能超过最大库存量！");
					$(this).val("1");
				}
				updateChangePoint();
			}
		});
	
						
		//填写收件人地址-----------------
	    $('.button04').click(function(){
	    	var userName=$("#name").val();
	    	
	    	if (!(/^([^u4E00-u9FA5]|[a-zA-Z0-9_\-]){2,6}$/.test(userName))) {
				alert("请填写有效的姓名");
				return;
			}
	    	var address=$("#address").val();
			if (!(/^([^u4E00-u9FA5]|[a-zA-Z0-9_\-]){2,28}$/.test(address))) {
				alert("请填写正确地址");
				return;
			}
	    	var zip=$("#zip").val();
			if (!checkFormat("^[0-9]{6}$",zip)) { 
				alert("请填写正确邮编"); 
				return;
			} 
	    	var mobileNumber=$("#mobile").val();
			if (!(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(mobileNumber))) {
				alert("请填写11位正确的手机号");
				return;
			}
			
	    	$('#InformationMain').html($('#name').val() + "  " + $('#mobile').val() + "  " + $('#province').val() + $('#cityId').val() + $('#address').val() + "  " + $('#zip').val());
	    	$('#userInfoAddress').val($('#province').val() + $('#cityId').val() + $('#address').val());
	    	$('#userInfoName').val($('#name').val());
	    	$('#userInfoZip').val($('#zip').val());
	    	$('#userInfoMobile').val($('#mobile').val());
	    	$('#addInfo').html('修改');
	    	$('.XinxiTanchu').hide();
	    });
		    
	    $('.button05').click(function(){
		   $('#name').val("");
		   $('#address').val("");
		   $('#zip').val("");
		   $('#mobile').val("");
		})
		$('.close').click(function(){
	    	$('.XinxiTanchu').hide();
		})
		$('#address').focus(function(){
			if ($('#address').val() == '地址') {
				$('#address').val('');
			}
		});
		$('#address').blur(function(){
			if ($('#address').val() == "") {
				$('#address').val('地址');
			}
		});
		
		$('#zip').focus(function(){
			if ($('#zip').val() == '200000') {
				$('#zip').val('');
			}
		});
		$('#zip').blur(function(){
			if ($('#zip').val() == "") {
				$('#zip').val('200000');
			}
		});
		
	    //确认兑换-----------------
	    $('#submit-a').click(function(){
		    if (parseInt($('.inp-text').val()) < 1 ) {
				alert("订购数量需大于0!");
				return;
			}
			<#if hasZhuantiCoupon == "false">
				var totalPoint = parseInt($('.inp-text').val()) * parseInt($('#changePoint').val());
			    if (totalPoint > $('#shopUserPoint').val()) {
					alert("个人积分不够!");
					return;
				}
			</#if>
			if ($('#userInfoAddress').val() == "") {
		    	alert("请填写收件人信息!");
				return;
		    }
		    if($("#isValidate").val() == "N"){
				if ($('#jieshou').attr("checked") != "checked") {
		    		alert("请接收协议!");
					return;
		    	}
    			$("#myForm").submit();
			}else{
			    if(typeof(($("#cashAccountVerifyCode")).val()) == "undefined"){
			    	alert("请先绑定手机号码!");
			    	return;
			    }else if($("#cashAccountVerifyCode").val() == ""){
			    	alert("请输入校验码!");
			    	return;
			    }else{
				    $.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile=${user.mobileNumber}&authenticationCode=" + $("#cashAccountVerifyCode").val() + "&jsoncallback=?",function(json){
						if(json.success) {
						   if ($('#jieshou').attr("checked") != "checked") {
					    		alert("请接收协议!");
								return;
					    	}
			    			$("#myForm").submit();
						}else{
							alert("验证码输入有误!");
							return;
						}
					});
			    }
			 }
	    });
});
	
		//兑换数量增减动作-----------------
		function addQuantity(maxStocks) {
			var maxNum=parseInt(${exchangeMaxNum});
			if(maxNum!=0){
				if(parseInt($('.inp-text').val()) + parseInt(${exchangeNum}) + 1 > maxNum){
					alert("该商品一个会员最多可兑换"+maxNum+"个,您之前已经兑换了"+parseInt(${exchangeNum})+"个！");
					return;
				}
			}

			if (parseInt($('.inp-text').val()) + 1 > maxStocks) {
				$('.DingdanTanchu').show();
			} else {
				$('.inp-text').val(parseInt($('.inp-text').val()) + 1);
				updateChangePoint();	
			}
		}
		
		function minQuantity() {
			if (parseInt($('.inp-text').val()) > 1 ) {
				$('.inp-text').val(parseInt($('.inp-text').val()) - 1);	
				updateChangePoint();
			}
		}
		
		function updateChangePoint() {
		   var totalPoint = parseInt($('.inp-text').val()) * parseInt($('#changePoint').val());
		   $('#productChangePoint').html(totalPoint);
		   $('#totalChangePoint').html(totalPoint);
		   $('#quantity').val(parseInt($('.inp-text').val()));
		}
		
		function checkFormat(format, input) {
			var myReg = new RegExp(format);
			return myReg.test(input);
		}

	function updateCities(value){
	    $("#cityId").empty();
		//$("#cityId").ajaxAddOption("http://www.lvmama.com/ajax/ajax!resultCity.do?jsoncallback=?", {"code":value});
		if (value == '北京市') {document.getElementById('cityId').options.add(new Option('北京','北京'));}
		if (value == '天津市') {document.getElementById('cityId').options.add(new Option('天津','天津'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('石家庄','石家庄'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('唐山','唐山'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('秦皇岛','秦皇岛'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('邯郸','邯郸'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('邢台','邢台'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('保定','保定'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('张家口','张家口'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('承德','承德'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('沧州','沧州'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('廊坊','廊坊'));}
		if (value == '河北省') {document.getElementById('cityId').options.add(new Option('衡水','衡水'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('太原','太原'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('大同','大同'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('阳泉','阳泉'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('长治','长治'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('晋城','晋城'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('朔州','朔州'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('晋中','晋中'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('运城','运城'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('忻州','忻州'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('临汾','临汾'));}
		if (value == '山西省') {document.getElementById('cityId').options.add(new Option('吕梁','吕梁'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('呼和浩特','呼和浩特'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('包头','包头'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('乌海','乌海'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('赤峰','赤峰'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('通辽','通辽'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('鄂尔多斯','鄂尔多斯'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('呼伦贝尔','呼伦贝尔'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('巴彦淖尔','巴彦淖尔'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('乌兰察布','乌兰察布'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('兴安盟','兴安盟'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('兴安盟阿尔山','兴安盟阿尔山'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('锡林郭勒盟','锡林郭勒盟'));}
		if (value == '内蒙古') {document.getElementById('cityId').options.add(new Option('阿拉善盟','阿拉善盟'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('沈阳','沈阳'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('大连','大连'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('鞍山','鞍山'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('抚顺','抚顺'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('本溪','本溪'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('丹东','丹东'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('锦州','锦州'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('营口','营口'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('阜新','阜新'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('辽阳','辽阳'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('盘锦','盘锦'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('铁岭','铁岭'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('朝阳','朝阳'));}
		if (value == '辽宁省') {document.getElementById('cityId').options.add(new Option('葫芦岛','葫芦岛'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('长春','长春'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('吉林','吉林'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('四平','四平'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('辽源','辽源'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('通化','通化'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('白山','白山'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('松原','松原'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('白城','白城'));}
		if (value == '吉林省') {document.getElementById('cityId').options.add(new Option('延边朝鲜','延边朝鲜'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('哈尔滨','哈尔滨'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('齐齐哈尔','齐齐哈尔'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('鸡西','鸡西'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('鹤岗','鹤岗'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('双鸭山','双鸭山'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('大庆','大庆'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('伊春','伊春'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('佳木斯','佳木斯'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('七台河','七台河'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('牡丹江','牡丹江'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('黑河','黑河'));}
		if (value == '黑龙江省') {document.getElementById('cityId').options.add(new Option('绥化','绥化'));}
		if (value == '上海市') {document.getElementById('cityId').options.add(new Option('上海','上海'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('周庄','周庄'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('常熟','常熟'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('南京','南京'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('无锡','无锡'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('宜兴','宜兴'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('徐州','徐州'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('常州','常州'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('苏州','苏州'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('南通','南通'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('连云港','连云港'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('淮安','淮安'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('盐城','盐城'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('扬州','扬州'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('镇江','镇江'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('泰州','泰州'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('宿迁','宿迁'));}
		if (value == '江苏省') {document.getElementById('cityId').options.add(new Option('浙西大峡谷','浙西大峡谷'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('乌镇','乌镇'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('雁荡山','雁荡山'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('楠溪江','楠溪江'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('杭州','杭州'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('桐庐','桐庐'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('千岛湖','千岛湖'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('临安','临安'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('宁波','宁波'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('象山','象山'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('余姚','余姚'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('温州','温州'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('嘉兴','嘉兴'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('湖州','湖州'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('湖州市安吉县','湖州市安吉县'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('绍兴','绍兴'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('金华','金华'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('衢州','衢州'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('舟山','舟山'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('台州','台州'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('仙居','仙居'));}
		if (value == '浙江省') {document.getElementById('cityId').options.add(new Option('丽水','丽水'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('合肥','合肥'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('芜湖','芜湖'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('蚌埠','蚌埠'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('淮南','淮南'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('马鞍山','马鞍山'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('淮北','淮北'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('铜陵','铜陵'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('安庆','安庆'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('黄山','黄山'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('黟县','黟县'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('滁州','滁州'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('阜阳市','阜阳市'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('巢湖','巢湖'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('六安市','六安市'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('池州','池州'));}
		if (value == '安徽省') {document.getElementById('cityId').options.add(new Option('宣城','宣城'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('福州','福州'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('厦门','厦门'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('莆田','莆田'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('三明','三明'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('泉州','泉州'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('漳州','漳州'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('南平','南平'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('龙岩','龙岩'));}
		if (value == '福建省') {document.getElementById('cityId').options.add(new Option('宁德','宁德'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('南昌','南昌'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('景德镇','景德镇'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('萍乡','萍乡'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('九江','九江'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('新余','新余'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('鹰潭','鹰潭'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('赣州','赣州'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('吉安','吉安'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('宜春','宜春'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('抚州','抚州'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('上饶','上饶'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('婺源','婺源'));}
		if (value == '江西省') {document.getElementById('cityId').options.add(new Option('三清山','三清山'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('济南','济南'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('青岛','青岛'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('淄博','淄博'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('枣庄','枣庄'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('东营','东营'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('烟台','烟台'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('长岛','长岛'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('潍坊','潍坊'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('济宁','济宁'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('济宁市曲阜市','济宁市曲阜市'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('泰安','泰安'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('威海','威海'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('日照','日照'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('莱芜','莱芜'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('临沂','临沂'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('德州','德州'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('聊城','聊城'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('滨州','滨州'));}
		if (value == '山东省') {document.getElementById('cityId').options.add(new Option('菏泽','菏泽'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('郑州','郑州'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('开封','开封'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('洛阳','洛阳'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('平顶山','平顶山'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('安阳','安阳'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('鹤壁','鹤壁'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('新乡','新乡'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('焦作','焦作'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('济源','济源'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('濮阳','濮阳'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('许昌','许昌'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('漯河','漯河'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('三门峡','三门峡'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('南阳','南阳'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('商丘','商丘'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('信阳','信阳'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('周口','周口'));}
		if (value == '河南省') {document.getElementById('cityId').options.add(new Option('驻马店','驻马店'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('武汉','武汉'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('黄石','黄石'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('十堰','十堰'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('宜昌','宜昌'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('襄樊','襄樊'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('鄂州','鄂州'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('荆门','荆门'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('孝感','孝感'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('荆州','荆州'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('黄冈','黄冈'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('咸宁','咸宁'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('随州','随州'));}
		if (value == '湖北省') {document.getElementById('cityId').options.add(new Option('恩施土家族','恩施土家族'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('长沙','长沙'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('株洲','株洲'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('湘潭','湘潭'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('韶山','韶山'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('衡阳','衡阳'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('邵阳','邵阳'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('岳阳','岳阳'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('常德','常德'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('张家界','张家界'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('益阳','益阳'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('郴州','郴州'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('永州','永州'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('怀化','怀化'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('娄底','娄底'));}
		if (value == '湖南省') {document.getElementById('cityId').options.add(new Option('湘西土家族','湘西土家族'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('广州','广州'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('韶关','韶关'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('深圳','深圳'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('珠海','珠海'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('汕头','汕头'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('佛山','佛山'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('南海','南海'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('江门','江门'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('湛江','湛江'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('茂名','茂名'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('肇庆','肇庆'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('惠州','惠州'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('梅州','梅州'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('汕尾','汕尾'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('河源','河源'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('阳江','阳江'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('清远','清远'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('东莞','东莞'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('中山','中山'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('潮州','潮州'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('揭阳','揭阳'));}
		if (value == '广东省') {document.getElementById('cityId').options.add(new Option('云浮','云浮'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('南宁','南宁'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('柳州','柳州'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('桂林','桂林'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('阳朔','阳朔'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('梧州','梧州'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('北海','北海'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('防城港','防城港'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('钦州','钦州'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('贵港','贵港'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('玉林','玉林'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('百色','百色'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('贺州','贺州'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('河池','河池'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('来宾','来宾'));}
		if (value == '广西省 ') {document.getElementById('cityId').options.add(new Option('崇左','崇左'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('海南东线','海南东线'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('海南中线','海南中线'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('海南西线','海南西线'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('海口','海口'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('三亚','三亚'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('琼海','琼海'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('万宁','万宁'));}
		if (value == '海南省') {document.getElementById('cityId').options.add(new Option('西沙群岛','西沙群岛'));}
		if (value == '重庆市') {document.getElementById('cityId').options.add(new Option('重庆','重庆'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('成都','成都'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('自贡','自贡'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('攀枝花','攀枝花'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('泸州','泸州'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('德阳','德阳'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('绵阳','绵阳'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('广元','广元'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('遂宁','遂宁'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('内江','内江'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('乐山','乐山'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('南充','南充'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('眉山','眉山'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('宜宾','宜宾'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('广安','广安'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('达州','达州'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('雅安','雅安'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('巴中','巴中'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('资阳','资阳'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('阿坝州','阿坝州'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('甘孜藏族','甘孜藏族'));}
		if (value == '四川省') {document.getElementById('cityId').options.add(new Option('凉山彝族','凉山彝族'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('贵阳','贵阳'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('六盘水','六盘水'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('遵义','遵义'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('安顺','安顺'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('铜仁地','铜仁地'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('黔西南布依族苗族','黔西南布依族苗族'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('毕节地区','毕节地区'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('黔东南苗族侗族','黔东南苗族侗族'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('凯里','凯里'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('黔南布依族苗族','黔南布依族苗族'));}
		if (value == '贵州省') {document.getElementById('cityId').options.add(new Option('都匀','都匀'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('中甸','中甸'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('泸沽湖','泸沽湖'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('昆明','昆明'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('曲靖','曲靖'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('玉溪','玉溪'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('保山','保山'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('昭通','昭通'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('丽江','丽江'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('普洱','普洱'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('临沧','临沧'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('楚雄','楚雄'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('红河哈尼族彝族','红河哈尼族彝族'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('文山壮族苗族','文山壮族苗族'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('西双版纳','西双版纳'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('大理白族','大理白族'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('德宏傣族景颇族','德宏傣族景颇族'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('怒江傈僳族','怒江傈僳族'));}
		if (value == '云南省') {document.getElementById('cityId').options.add(new Option('迪庆藏族','迪庆藏族'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('拉萨','拉萨'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('昌都','昌都'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('山南','山南'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('日喀则','日喀则'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('那曲','那曲'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('阿里','阿里'));}
		if (value == '西藏') {document.getElementById('cityId').options.add(new Option('林芝','林芝'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('西安','西安'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('铜川','铜川'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('宝鸡','宝鸡'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('咸阳','咸阳'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('渭南','渭南'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('延安','延安'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('汉中','汉中'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('榆林','榆林'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('安康','安康'));}
		if (value == '陕西省') {document.getElementById('cityId').options.add(new Option('商洛','商洛'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('兰州','兰州'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('嘉峪关','嘉峪关'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('金昌','金昌'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('白银','白银'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('天水','天水'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('武威','武威'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('张掖','张掖'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('平凉','平凉'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('酒泉','酒泉'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('敦煌市','敦煌市'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('庆阳','庆阳'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('定西','定西'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('陇南','陇南'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('临夏回族','临夏回族'));}
		if (value == '甘肃省') {document.getElementById('cityId').options.add(new Option('甘南藏族','甘南藏族'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('西宁','西宁'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('海东地区','海东地区'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('海北藏族','海北藏族'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('黄南藏族','黄南藏族'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('海南藏族','海南藏族'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('果洛藏族','果洛藏族'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('玉树藏族','玉树藏族'));}
		if (value == '青海省') {document.getElementById('cityId').options.add(new Option('海西蒙古族','海西蒙古族'));}
		if (value == '宁夏') {document.getElementById('cityId').options.add(new Option('银川','银川'));}
		if (value == '宁夏') {document.getElementById('cityId').options.add(new Option('石嘴山','石嘴山'));}
		if (value == '宁夏') {document.getElementById('cityId').options.add(new Option('吴忠','吴忠'));}
		if (value == '宁夏') {document.getElementById('cityId').options.add(new Option('固原','固原'));}
		if (value == '宁夏') {document.getElementById('cityId').options.add(new Option('中卫','中卫'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('乌鲁木齐','乌鲁木齐'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('克拉玛依','克拉玛依'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('吐鲁番','吐鲁番'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('哈密地区','哈密地区'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('昌吉回族','昌吉回族'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('阜康','阜康'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('博尔塔拉','博尔塔拉'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('巴音郭楞','巴音郭楞'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('阿克苏','阿克苏'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('克孜勒苏柯尔克孜','克孜勒苏柯尔克孜'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('喀什','喀什'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('和田地区','和田地区'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('伊犁哈萨克','伊犁哈萨克'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('塔城地区','塔城地区'));}
		if (value == '新疆') {document.getElementById('cityId').options.add(new Option('阿勒泰','阿勒泰'));}
		if (value == '香港') {document.getElementById('cityId').options.add(new Option('香港','香港'));}
		if (value == '澳门') {document.getElementById('cityId').options.add(new Option('澳门','澳门'));}
		if (value == '台湾') {document.getElementById('cityId').options.add(new Option('台北','台北'));}
		if (value == '台湾') {document.getElementById('cityId').options.add(new Option('台中','台中'));}
		if (value == '台湾') {document.getElementById('cityId').options.add(new Option('台东','台东'));}
		if (value == '台湾') {document.getElementById('cityId').options.add(new Option('台南','台南'));}
	}		
</script>
</body>	
</html>
