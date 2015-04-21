<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>立即充值-驴妈妈旅游网</title>
	<link href="http://pic.lvmama.com/styles/prestore.css" rel="stylesheet" />
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/store.do">存款账户</a>
				&gt;
				<a class="current">立即充值</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit store_set-edit">
					<div class="ui-box-title"><h3>立即充值</h3></div>
					<div class="ui-box-container">
				    	<!-- 充值>> -->
				    	<div class="edit-box clearfix store_set-edit-box">
				        	<p class="tips-info"><span class="tips-ico03"></span>存款账户充值后请进行产品购买消费，不支持充值后直接提现的操作。</p>
				      		<@s.if test="user.mobileNumber==null">
					        	<div class="set-step set-step1 clearfix">
									<ul class="hor">
								    <li class="s-step1"><span class="s-num">1</span>绑定手机</li>
								    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机绑定成功</li>
								     <li class="s-step2"><i class="s-arrow"></i><span class="s-num">3</span>充值</li>
								    </ul>
								</div>
				        	</@s.if>
				        	<div class="edit-inbox" id="form-box">
				        	
				        	<!--手机未绑定状态-->
				        	<@s.if test="user.mobileNumber==null">
								<p><label>您的手机号：</label><input type="text" id="mobileNumberInput" class="input-text input-phone"></p>
				        		<p>
				        			<a href="javascript:;" class="ui-btn ui-btn1" id="send-verifycode"><i>免费获取校验码</i></a>
					        		<span style="display:none" id="JS_countdown">
					        		<span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span>
					        		<span class="tips-winfo"><span href="" class="ui-btn ui-disbtn"><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?&#12288;</span></span>
				        		</p>
				        		<p>
				        			<label>校验码：</label><input type="text" id="verifycodeInput" class="input-text i-checkcode" >
				        		</p>
							</@s.if>
							<!--/手机未绑定状态-->
				            
				            <p><label>充值金额：</label><input type="text" id="payAmount" name="payAmount" value="" class="input-text input-money" />元<span id='payAmountTip'></span></p>
				            <p id="J_tab-type" class="clearfix"><label>　充值方式：</label>
				            <label class="store_set_type"><input name="store_set_type" class="set_type" type="hidden" value="0" checked="checked"><span class="pay-type pay-type1"></span></label>
				            </p>
					            <div id="J_tab-info" class="edit-m-info">
						            <div class="tabcon selected">
						                <p class="lv-cc"><span class="hr_line"></span>您的存款账户余额有任何变动，通知信息将以短信的方式发送到您的手机。</p>
						            </div>
					            </div>
				                <p><a href="javascript:dosubmit();" class="ui-btn ui-btn7"><i>&nbsp;立即充值&nbsp;</i></a></p>
				            </div>
				        </div>
				        <!-- <<充值 -->
					</div>
				</div>
				<!-- <<立即充值 -->
		</div>
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
				<script type="text/javascript">
				var phone = /^1[3|5|8][0-9]\d{8}$/;
				function mobileNumberInput_Check(){
					var mobileNumber=$.trim($("#mobileNumberInput").val().replace(/\s+/g,''));
					if(mobileNumber== ""){
						error_tip("#mobileNumberInput","手机号码不能为空");
					}else if(!phone.test(mobileNumber)){
						error_tip('#mobileNumberInput','请输入有效的手机号码');
					}else{
						$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?mobile=" + mobileNumber + "&jsoncallback=?",function(json){
							if (json.success == true) {
								outOk($("#mobileNumberInput"));
							} else {
								error_tip('#mobileNumberInput','该手机号已被注册，请更换其它手机号，或用此手机号登录');
							}	
						});	
					}
			   }
			   function verifycodeInput_Check(){
			   		var mobileNumber=$.trim($("#mobileNumberInput").val().replace(/\s+/g,''));
					$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+mobileNumber+"&authenticationCode=" + $.trim($("#verifycodeInput").val()) + "&jsoncallback=?",function(json){
						if (json.success == true) {
							outOk($("#verifycodeInput"));
						}else{
							error_tip("#verifycodeInput","验证码输入错误");
						}
					});
				}
				
				$(function(){
					<@s.if test="user.mobileNumber==null">
						 $("#mobileNumberInput").ui("validate",{
				            active : "请输入手机号码",
				            empty : "手机号码不允许为空",
				            phone : "手机号码格式不正确"
				        });
				        
				         $("#mobileNumberInput").blur( function () {
				         	mobileNumberInput_Check();
				         });
				        
						$("#verifycodeInput").ui("validate",{
				            active : "请输入短信校验码",
				            empty : "校验码不能为空"
				        });
				        
				         $("#verifycodeInput").blur( function () {
				         	var mobileNumber=$.trim($("#mobileNumberInput").val().replace(/\s+/g,''));
				         	var code=$.trim($("#verifycodeInput").val());
				         	if(mobileNumber!=""&&code==""){
				         		error_tip("#verifycodeInput","校验码不能为空");
				         	}
				         	if(mobileNumber!=""&&code!=""){
				         		verifycodeInput_Check();
				         	}
				         });
				        
					</@s.if>
					
					$('#payAmount').bind("blur", function(){
						clearContent("#payAmountTip");
						var payAmount=$('#payAmount').val();
						if($.trim(payAmount)==""){
							$("#payAmountTip").nextAll("span").remove();
							var msg='<span validate="msg" class="zhfs_state zhfs_v_error"><i></i><label class="label_tip">请填写充值金额。<\/label></span>';
							$("#payAmountTip").after(msg);
							$(this).addClass("input_border_red");
						}else if(isNaN(payAmount)||!/^\d+(.\d{0,2}){0,1}$/.test(payAmount)){
							$("#payAmountTip").nextAll("span").remove();
							var msg='<span validate="msg" class="zhfs_state zhfs_v_error"><i></i><label class="label_tip">金额必须为整数或者小数,小数点后不超过2位。</label></span>';
							$("#payAmountTip").after(msg);
							$(this).addClass("input_border_red");
						}else if(payAmount>=1000000){
							$("#payAmountTip").nextAll("span").remove();
							var msg='<span validate="msg" class="zhfs_state zhfs_v_error"><i></i><label class="label_tip">金额不能超过100万</label></span>';
							$("#payAmountTip").after(msg);
							$(this).addClass("input_border_red");
						}else{
							$("#payAmountTip").nextAll("span").remove();
							outOk($("#payAmount"));
						}
					}).bind("focus", function(){
						clearContent("#payAmountTip");
					});
			        
				});
				
					<@s.if test="user.mobileNumber==null">
						$('#send-verifycode').click(function(){
							var mobileNumber=$.trim($("#mobileNumberInput").val()).replace(/\s/g,"");
							if(mobileNumber==""){
								alert("请填写手机号码");
								return;
							}
							$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail="+mobileNumber+"&jsoncallback=?",function(json){
								if (json.success) {
									$('#send-verifycode').hide();
									$("#JS_countdown").show();
									JS_countdown("#JS_countdown span.num-second");
								} else {
									alert('验证码发送失败，请重新尝试');
								}
							});	
						 });
						function JS_countdown(_cdbox){
							 var _InterValObj; //timer变量，控制时间
							 var _count = 60; //间隔函数，1秒执行
							 var _curCount;//当前剩余秒数
							 sendMessage(_count);
							 function sendMessage(_count){
							  _curCount = _count;
							   $(_cdbox).html(_curCount);
							   _InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
							  }
							 //timer处理函数
							 function SetRemainTime() {
							  if (_curCount == 0) {                
							   window.clearInterval(_InterValObj);//停止计时器
							   var expr = _cdbox.indexOf("-old")>0?"-old":"";
							   $("#JS_countdown"+expr).children(".tips-success").html("<span class=\"tips-ico01\"></span>校验码已发送成功，以最近发送的校验码为准").end().hide();
							   $("#send-verifycode"+expr).html("<i>重新发送验证码</i>").show();
							  }
							  else {
							   _curCount--;
							   $(_cdbox).html(_curCount);
							  }
							 }
							}
					</@s.if>
						
					function dosubmit(){
						var input = $("#form-box").find("input");
			            input.each(function(){
			                $(this).trigger("blur");
			                if($(this).hasClass("input_border_red")){
			                    return false;
			                }
			            });
			            var error_input = input.filter(".input_border_red");
			            if(error_input.size()>0){
			              return;
			            }
					
						<@s.if test="user.mobileNumber==null">
						var mobileNumber=$.trim($("#mobileNumberInput").val()).replace(/\s/g,"");
						var verifycode=$.trim($("#verifycodeInput").val());
						</@s.if>
						
						var setType=document.getElementsByName("store_set_type");
						var flag=true;
						var type=setType[0].value;
			    		var payAmount=document.getElementById('payAmount').value;
			    		payAmount=payAmount*100;
						var rechargeUrl;
						$.ajax({
							url:"/myspace/account/store_recharge.do",
							type:"post",
							async:false,
							dataType:"json",
							data:{rechargeAmount:payAmount<@s.if test="user.mobileNumber==null">,mobileNumber:mobileNumber,verifycode:verifycode</@s.if>},
							success:function(data){
							  if(data.success==true){//成功
								  	var top=$(document).scrollTop();
                           			$("#tipsWindow").css("top",top+220+"px").show();
                           			$("div.xh_overlay").show().height($(document).height());
									rechargeUrl = data.rechargeUrl;
									if(type==0){
										var payUrl='${constant.paymentUrl}pay/alipay.do?'+rechargeUrl;
										tipsWindow('tipsWindow','bgColor');
										window.open(payUrl,'blank');
									}
							   }else if(data.success==false){
							   		alert(data.msg);
							   }
							}
						});
					}
					function tipsWindow(obj,screenBg){
						$("#"+obj).fadeIn("fast");
						$("#"+screenBg).fadeIn("fast");
						$("#"+screenBg).css({"opacity":".8"});
					}
					function closeMsg(obj,screenBg){
						$("#tipsWindow, div.xh_overlay").hide();
						$("#"+obj).fadeOut("fast");
						$("#"+screenBg).fadeOut("fast");
					}
				</script>
<script src='/js/myspace/account.js' type='text/javascript'></script>
	<div class="xh_overlay"></div>
	<div id="tipsWindow" class="xh_tipsWindow">
    <div id="tipsContent" class="clearfix">
        <a class="tipClose" onclick="closeMsg('tipsWindow','bgColor')">×</a>
        <h4>充值</h4>
        <div class="tipWrap">
            <p class="tipTitle">请在新开页面付款后选择：</p>
            <p class="payOK"><span>充值成功</span><a href="http://www.lvmama.com/myspace/account/viewcharge.do">查看存款账户</a></p>
            <p class="payFalse"><span>充值失败</span><a class="otherPay"  href="javascript:void(0);" onclick="closeMsg('tipsWindow','bgColor');">重新充值</a><a href="http://www.lvmama.com/public/order_and_pay#m_1_3" target="_blank">查看充值帮助</a></p>
        </div>
    </div>
	</div>
	<script>
		cmCreatePageviewTag("立即充值", "D1002", null, null);
	</script>
</body>
</html>