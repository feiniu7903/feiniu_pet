		<!-- 登录弹出层 -->
		<div class="bgLogin"></div>
		<iframe frameborder="0" id="loginDIV" height="1"></iframe>
		<div class="LoginAndReg">
		    <p class="topLogin"><span class="titleLogin">快速预订 / 登录</span><a class="btn-close"><img src="http://pic.lvmama.com/img/icons/closebtn.gif" alt="关闭" /></a></p>
		    <div class="pop_loginner">
				<div class="contLogin">
				<div class="contLeft">
					<h1 class="titleUser">非会员快速预订</h1>
					<p class="quick-area"><span class="inputName">手机号：</span><span class="inputValue"><input name="mobileLoginText" type="text" id="mobileLoginText" onblur="mobileRegisterOnBlur('mobileLoginText','mobileLoinTextSuccessTip','mobileLoinTextErrorTip')"/><span id="mobileLoinTextSuccessTip"></span></span><span id="mobileLoinTextErrorTip"></span></p>
					
					<p><span class="inputName">&nbsp;</span><span class="inputValue"><a class="quickBuy" onclick="rapidRegister()" href="javascript:void(0)"><img src="http://pic.lvmama.com/img/order/quickBuy.gif" alt="快速预订" style="cursor:pointer;"/></a></span></p>				
					<p><span class="inputValue"><em class="contTsInfo">我们会将您注册为驴妈妈会员，手机号为您的用户名，密码将通过免费短信发送到您手机。</em></span></p>
				</div>
				<div class="contRight">
					<h1 class="titleUser">会员登录后预订</h1>
					<p class="mb-10"><span class="inputName">账户：</span><span class="inputValue"><input type="text" name="mobileOrEMail" id="sso_mobileAndEmail" value="" /></span></p>
					<p class="mb-10"><span class="inputName">密码：</span><span class="inputValue"><input name="password" id="sso_password" type="password" /></span></p>
					<p><span class="inputName">验证码：</span><span class="inputValue"><input style="width:80px;margin-right:5px;" type="text" name="verifycode" id="sso_verifycode1"><img id="image" src="http://login.lvmama.com/nsso/account/checkcode.htm" /> <a href="#" class="link_blue" onClick="refreshCheckCode('image');return false;">换一张</a></span></p>
					<p id="loinTextErrorTip"></p>
					<p><span class="inputName">&nbsp;</span><span class="inputValue"><a class="quickBuy" href="javascript:void(0)" onclick="login()"><img src="http://pic.lvmama.com/img/order/LoginAndBuy.gif" alt="登录，继续订购" onclick="login()" style="cursor:pointer;"/></a><a class="findPass" href="http://login.lvmama.com/nsso/findpass/index.do" target="_blank">忘记密码？</a></span></p>

					<div class="weiboLogin">
						<h1 class="weiboH1">还不是驴妈妈会员？<a class="freeUser" href="http://login.lvmama.com/nsso/register/registering.do" target="_blank">免费注册</a></h1>

						<div class="weiboBtn">
						    <p>
								<label class="login_lbl">使用合作网站帐号登录</label>
								<a class="weiboBtnA login_conQQ" href="http://login.lvmama.com/nsso/cooperation/tencentQQUnionLogin.do?isRefresh=false" target="_unionlogin" onclick="tipsWindow('tipsWindow','bgColor');">QQ</a>					        						
								<a class="weiboBtnA login_conTT" href="http://login.lvmama.com/nsso/cooperation/tencentUnionLogin.do?isRefresh=false" target="_unionlogin" onclick="tipsWindow('tipsWindow','bgColor');"> 腾讯微博</a>
								<a class="weiboBtnA login_conBD" href="http://login.lvmama.com/nsso/cooperation/baiduTuanUnionLogin.do?isRefresh=false" target="_unionlogin" onclick="tipsWindow('tipsWindow','bgColor');"> 百度</a>
							</p>
							<a class="weiboBtnA login_conSN" href="http://login.lvmama.com/nsso/cooperation/sinaUnionLogin.do?isRefresh=false" target="_unionlogin" onclick="tipsWindow('tipsWindow','bgColor');">新浪微博</a>

							<a class="weiboBtnA login_conALP" href="http://login.lvmama.com/nsso/cooperation/alipayUnionLogin.do?isRefresh=false" target="_unionlogin" onclick="tipsWindow('tipsWindow','bgColor');">支付宝</a>
							<a class="weiboBtnA login_conKX" href="http://www.kaixin001.com/login/connect.php?appkey=85704812783077bafc036569af59c655&amp;re=/nsso/cooperation/kaixinUnionLogin.do&amp;t=25" target="_blank" style="padding-left:22px;">开心网</a>
							<a class="weiboBtnA login_conSD" href="http://login.lvmama.com/nsso/cooperation/sndaUnionLogin.do?isRefresh=false" target="_unionlogin" onclick="tipsWindow('tipsWindow','bgColor');">盛大</a>
						</div>
				        </div>
				</div>
			</div>
		     </div><!--inner-->
		</div>
		<!-- /登录弹出层 -->
		
		<style>
			/*弹出提示层*/
			#bgColor {z-index:9990;display:none;position:fixed;_position:absolute;top:0px;background-color:#000;left:0px;width:100%;height:100%;filter:alpha(opacity=40);-moz-opacity:0.4;opacity:0.4;}
			#tipsWindow {z-index:9999;display:none;position:absolute;top:310px;left:60px;width:460px;border:3px solid #c06;background-color:#fff;padding:15px 20px;}
			#tipsWindow img {vertical-align:middle;margin-top:-3px;}
			#tipsWindow h3 {font-size:14px;font-weight:bold;color:#333;line-height:30px;border-bottom:1px solid #D8DFEA;}
			#tipsWindow strong {color:#333;font-weight:bold;display:block;}
			#tipsWindow p {color:#666;line-height:20px;padding:15px 0;ertical-align:middle;}
			#tipsWindow .tbtn {text-align:center;padding:15px 0 30px;}
			#tipsWindow span {position:absolute;right:20px;top:20px;cursor:pointer;ertical-align:middle;}
			#tipsWindow input {cursor:pointer;margin:0 15px;}
			#mobileLoinTextErrorTip font {margin-left:0;}
		</style>
		<div id="windowLayer">
    		<div id="tipsWindow">
    			<span onclick="closeMsg('tipsWindow','bgColor')"><img src="http://pic.lvmama.com/img/icons/close.gif" />&nbsp;关闭</span>
        		<h3>登录遇到问题？</h3>
        		<p><img src="http://pic.lvmama.com/img/icons/warning.gif" />&nbsp;请在联合登录前不要关闭此窗口。完成登录后根据您好的情况点击下面的按钮：</p>
        		<strong>请在新开网页上完成登录后再选择。</strong>
        		<div class="tbtn">
        			<input name="completePayBtn" type="button" value="已完成登录" id="completePayBtn" onclick='completeLogin()'/><input type="button" value="登录遇到问题" onclick="javascript:window.open('http://www.lvmama.com/public/reg_and_login')" /></div>
        			<a href="###" onclick="closeMsg('tipsWindow','bgColor');">返回重新选择登录方式</a>
    			</div>
    		<div id="bgColor"></div>
		</div>		
		
		<!-- <script src="http://rest.kaixin001.com/api/FeatureLoader_js.php" type="text/javascript"></script>				
		<script type="text/javascript">
			KX001.init("85704812783077bafc036569af59c655", "http://login.lvmama.com/nsso/cooperation/kaixinUnionLogin.do?isRefresh=false","<font color='white'>开心网</font>");
		</script> -->

		
