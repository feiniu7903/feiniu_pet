<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>驴妈妈注册</title>
<link rel="shortcut icon" href="http://www.lvmama.com/img/favicon.ico" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script src="/nsso/js/member/new_login_web.js" type="text/javascript"></script>

<#include "/common/coremetricsHead.ftl">
</head>
<body>
   <div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">免费注册</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="register_center">
			<ul class="regc_zc_title">
				<li class="menu menu_yx curr" onclick="location.href='http://login.lvmama.com/nsso/register/registering.do'" >邮箱注册</li>
				<li class="menu menu_sj" onclick="location.href='http://login.lvmama.com/nsso/register/mobileregist.do'" >手机注册</li>
				<li class="register_topfont"><a href="http://login.lvmama.com/nsso/register/membercardregist.do" class="link_blue">会员卡注册</a> 成为驴妈妈会员</li>
			</ul>
			<div class="reg_zc_line"></div>
			<div class="w_overflow" id="w_overflow">
				<div class="w_max" id="w_max">
					<div class="fl" id="yx_div">
						<ul class="reg_diandian hidden">
							<li class="login_sp curr"></li>
							<li class="login_sp"></li>
							<li class="login_sp"></li>
						</ul>
<form action="/nsso/register/registerUser.do" id="emailRegForm" method="post">
<@s.token></@s.token>
<input type="hidden" name="channel" value="PAGEREG"/>
						<ul style="" class="register_form register_sj_form">
							<li><label class="csmm_form_col w105"><i class="red">*</i> 您的Email地址</label><input type="text" class="zhfs_form_input" id="sso_email_b" name="email"></li>
							<li><label class="csmm_form_col w105"><i class="red">*</i> 用户名</label><input type="text" class="zhfs_form_input" id="sso_username" name="userName"></li>
							<li><label class="csmm_form_col w105"><i class="red">*</i> 设置密码</label><input type="password" class="zhfs_form_input" id="sso_password" name="password"></li>
							<li><label class="csmm_form_col w105"><i class="red">*</i> 确认密码</label><input type="password" class="zhfs_form_input" id="sso_againPassword" name="againPassword"></li>
							<#if registVerifyCodeFlag == true>
							<li><label class="csmm_form_col w105"><i class="red">*</i> 验证码</label><input type="text" class="zhfs_form_input zhfs_w99" id="sso_verifycode1" name="verifycode">
                                                                <img id="image" src="/nsso/account/checkcode.htm" /><a href="#" class="link_blue" onClick="refreshCheckCode('image');return false;">换一张</a>
							</li>
							</#if>
							<li class=""><label class="csmm_form_col w105"> 所在地</label><select id="captialId" class="province"><option value="">--请选择--</option></select>
							<select id="cityId" name="cityId" class="city"><option>--请选择--</option></select></li>
							<li class="mb10"><label class="csmm_form_col w105"></label><a href="javascript:void(0)" class="register_submit" id="submitBtn"></a></li>
							<li><label class="csmm_form_col w105"> </label><input type="checkbox" class="register_tk" checked id="terms"><a class="link_blue" href="javascript:void(0)" id="lvmama_tk">《驴妈妈旅游网会员服务条款》</a>
		<pre class="xy">驴妈妈旅游网会员服务条款
1.驴妈妈服务条款的确认
驴妈妈旅游网（以下简称“驴妈妈”）各项服务的所有权与运作权归景域旅游运营集团所有。本服务条款具有法律约束力。一旦您点选“注册”并激活邮件，即表示您自愿接受本协议之所有条款，并已成为驴妈妈的注册会员。

2．服务内容
2.1 　驴妈妈服务的具体内容由景域旅游运营集团根据实际情况提供，驴妈妈对其所提供之服务拥有最终解释权。
2.2 　景域旅游运营集团在驴妈妈上向其会员提供相关网络服务。其它与相关网络服务有关的设备（如个人电脑、手机、及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费等）均由会员自行负担。

3．会员账号及密码
您一旦注册成功成为用户，您将得到一个账号和密码。如果您不保管好自己的账号和密码安全，因此产生的所有损失将由您全部承担并负相应责任。此外，每个用户都要对其账户中的所有活动和事件负全责。您可随时更改您的密码，也可以重开一个新的账户。用户若发现任何非法使用用户账号或安全漏洞的情况，请立即通告驴妈妈。

4．会员权责
4.1 　会员有权按照驴妈妈规定的程序和要求使用驴妈妈向会员提供的各项网络服务，如果会员对该服务有异议，可以与驴妈妈联系以便得到及时解决。
4.2 　用户在申请使用驴妈妈旅游网服务时，必须向驴妈妈提供准确的个人资料，如个人资料有任何变动，必须及时更新。
4.3 　会员在驴妈妈的网页上发布信息或者利用驴妈妈的服务时必须符合国家的法律法规以及国际法的有关规定。
4.4 　对于会员通过驴妈妈网上消息平台（包括但不限于论坛、 BBS ）上传到驴妈妈网站上可公开获取区域的任何内容，会员同意授予驴妈妈在全世界范围内享有完全的、免费的、永久性的、不可撤销的、非独家的权利，以及再许可第三方的权利，以使用、复制、修改、改编、出版、翻译、据以创作衍生作品、传播、表演和展示此等内容（整体或部分），和/或将此等内容编入当前已知的或以后开发的其他任何形式的作品、媒体或技术中。
<u>4.5 驴妈妈旅游网社区版块内所有素材图片或文字均由网友上传而来，驴妈妈不享有此类素材图片或文字的版权，此类图片或文字作为驴妈妈会员与网友学习交流之用，若需商业使用，需获得版权拥有者授权，如因非法使用引起纠纷，一切后果由使用者承担。
4.6 驴妈妈作为网络服务提供者，对非法转载、使用行为的发生不具备充分的监控能力。但当版权拥有者提出侵权指控并提供充分的版权证明材料时，驴妈妈将移除非法使用、转载的作品及停止继续传播。驴妈妈对他人在网站上实施的此类侵权行为不承担法律责任，侵权的法律责任由会员本人承担。向驴妈妈社区发布作品的会员应保证其上传作品的真实、合法，不侵犯任何第三方的合法权利。
4.7 向驴妈妈社区发布作品的会员同意许可驴妈妈旅游对其上传的图片或文字进行使用，使用范围、使用时间及使用方式无限制，如对特殊图片或文字使用有限制的，请及时与驴妈妈进行书面告知，自接到会员通知时起驴妈妈将对图片或文字进行相关限制使用。</u>
4.8 　会员承诺不会在驴妈妈的消息平台（包括但不限于论坛、 BBS ）发布如下信息：
(1) 反对宪法所确定的基本原则的；
(2) 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；
(3) 损害国家荣誉和利益的；
(4) 煽动民族仇恨、民族歧视，破坏民族团结的；
(5) 破坏国家宗教政策，宣扬邪教和封建迷信的；
(6) 散布谣言，扰乱社会秩序，破坏社会稳定的；
(7) 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；
(8) 侮辱或者诽谤他人，侵害他人合法权益的；
(9) 含有法律、行政法规禁止的其他内容的。
4.9 　会员单独为其发布在驴妈妈上的信息承担责任。会员若在驴妈妈散布和传播违法信息，网络会员服务的系统记录有可能作为会员违法之证据。
4.10 　会员不得利用本站的服务从事以下活动：
(1) 未经允许，进入计算机信息网络或者使用计算机信息网络资源；
(2) 未经允许，对计算机信息网络功能进行删除、修改或者增加；
(3) 未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加；
(4) 故意制作、传播计算机病毒等破坏性程序；
(5) 其他危害计算机信息网络安全的行为。
4.11 　 会员不得以任何方式干扰本站的服务。
4.12 　会员承诺遵守本站的所有其他规定和程序。
4.13 　如果会员违反上述规定，驴妈妈有权要求其改正或直接采取一切必要措施（包括但不限于更改或删除会员发布的信息、中断或终止会员使用网络的权利等），以减轻会员不当行为所造成的影响。

5．服务条款的修改
驴妈妈有权在必要时修改本服务条款，服务条款的内容一旦发生变动，驴妈妈将会通过适当方式向会员提示修改内容。会员如不同意修改，可以主动选择取消会员资格；如果会员继续使用驴妈妈服务，将被视为接受修改后的服务条款。

6．服务内容的修改或中断
鉴于网络服务的特殊性，驴妈妈保留随时修改或中断其部分或全部网络服务的权利，并无需通知会员或为此对会员及任何第三方负责。

7．会员隐私保护
驴妈妈尊重会员的隐私权，不会公开、编辑或泄露任何有关会员的个人资料以及会员在使用网络服务时存储在驴妈妈的非公开内容，但以下情况除外：
（1）事先获得会员的明确授权；
（2）遵守法律规定或驴妈妈合法服务程序；
（3）按照相关政府主管部门的合理要求；
（4）维护社会公众利益；
（5）维护驴妈妈的合法权益；
（6）符合其他合法要求。

8．中断或终止服务
如发生下列任何一种情形，驴妈妈有权随时中断或终止向会员提供本协议项下的网络服务，而无需对会员或任何第三方承担任何责任。
（1）会员向驴妈妈提供的个人资料不真实。
（2）会员违反本协议的规则或不履行其所承担的义务。
除此之外，会员可随时根据需要通知驴妈妈终止向该会员提供服务，会员服务终止后，会员使用服务的权利同时终止。自会员服务终止之时起，驴妈妈不再对该会员承担任何责任。

9．知识产权
9.1 驴妈妈在网络服务中提供的任何文本、图片、图形、音频和视频资料均受版权、商标权以及其他相关法律法规的保护。未经驴妈妈事先同意，任何人不能擅自复制、传播这些内容，或用于其他任何商业目的，所有这些资料或资料的任何部分仅可作为个人或非商业用途而保存在某台计算机内。
9.2 驴妈妈为提供网络服务而使用的任何软件（包括但不限于软件中的任何文字、图形、音频、视频资料及其辅助资料）的一切权利属于该软件的著作权人，未经该著作权人同意，任何人不得对该软件进行反向工程、反向编译或反汇编。

10．免责声明
10.1 驴妈妈对任何因会员不正当或非法使用服务、在网上进行交易、或会员传送信息变动而产生的直接、间接、偶然、特殊及后续的损害不承担责任。
10.2 驴妈妈对任何他人的威胁性的、诽谤性的、淫秽的、令人反感的或非法的内容或行为或对他人权利的侵犯（包括知识产权）不承担责任；并对任何第三方通过服务发送或在服务中包含的任何内容不承担责任。
10.3 会员明确同意其使用驴妈妈服务所存在的风险以及使用驴妈妈旅游网服务产生的一切后果由其自己承担。
10.4 对于因不可抗力或驴妈妈不能控制的原因造成的网络服务中断或其它缺陷，驴妈妈不承担任何责任，但将尽力减少因此而给用户造成的损失和影响。
10.5 驴妈妈不对所提供之网络服务做任何类型之担保，包括但不限于：
（1） 网络服务一定能满足会员要求；
（2） 网络服务不会中断；
（3） 网络服务的及时性、安全性、准确性。
但是驴妈妈对不违反规定的特定目的担保不作限制。

11．赔偿
因会员对本服务之使用而导致景域旅游运营集团遭受任何来自第三方之纠纷、诉讼及索赔要求，会员同意向驴妈妈及其关联企业、职员赔偿相应损失（包括合理的律师费），并尽力使之免受损害。

12．通告
所有发给会员的通告都可以通过重要页面的公告、电子邮件以及常规信件的形式传送。

13．法律
景域旅游运营集团服务条款之效力、解释、执行均适用中华人民共和国法律。如发生争议，应提交至有管辖权之人民法院。

14．其他规定
本服务条款中的标题仅为方便而设，在解释本服务条款时应被忽略。
		</pre>
		<div id="terms_xx"></div>
							</li>
						</ul>
</form>
					</div>
					
				</div>
			</div>
			<div class="register_right">
				<p class="register_r_b rrb_padding rrb_fsize"><i class="reg_sb register_lw"></i>现在注册即可获得<i class="organge">100积分</i></p>
				<p class="register_r_b rrb_padding">已经有驴妈妈账号？<a href="http://login.lvmama.com/nsso/login" class="reg_sb register_login"></a></p>
				<p class="register_r_b rrb_padding register_lh">
					<label class="gray">使用合作网站账号登录</label><br/>
					    <span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/tencentQQUnionLogin.do')"><i class="login_sp login_qq"></i>QQ</a></span>
						<!--<span class="login_other"><i class="login_sp login_tx"></i><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/tencentUnionLogin.do')">腾讯微博</a></span>-->
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/sinaUnionLogin.do')"><i class="login_sp login_wb"></i>新浪微博</a></span>
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/alipayUnionLogin.do')"><i class="login_sp login_zfb"></i>支付宝</a></span>
						<span class="login_other"><a class="link_blue" href="http://www.kaixin001.com/login/connect.php?appkey=85704812783077bafc036569af59c655&amp;re=/nsso/cooperation/kaixinUnionLogin.do&amp;t=25" target="_blank"><i class="login_sp login_kx"></i>开心网</a></span>
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/sndaUnionLogin.do')"><i class="login_sp login_sd"></i>盛大</a></span>
						
				</p>
				<img src="http://pic.lvmama.com/img/new_v/ob_login/demo_right_gg.jpg"/>
			</div>
			<div class="reg_clear"></div>
		</div>
</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript">
        document.domain='lvmama.com';
        function union_login(url){ 
            window.open(url); 
        }
</script>
<script>
		
		
			function provinceLoaded()
			{
			   	  for(var i = 0; i < document.getElementById("captialId").options.length;i++) {  
			      if (document.getElementById("captialId").options[i].value == "<@s.property value="captialId"/>"){                
			        document.getElementById("captialId").options[i].selected ="true";
			        break; 
			      }
	             }
	             $("#captialId").change();
			}
			
          function cityLoaded()
          {
            for(var i = 0; i < document.getElementById("cityId").options.length;i++) {  
			if (document.getElementById("cityId").options[i].value == '<@s.property value="cityId"/>')
               {                
			    document.getElementById("cityId").options[i].selected ="true"; 
                                break;
               }
	        }
          
          }		 
		
		function sso_email_b_callback(call){
			$.ajax({
				type: "POST",
				url:  "http://login.lvmama.com/nsso/ajax/checkUniqueField.do",
				data: {
					email: this.value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						error_tip("#sso_email_b","该Email地址已被注册，请更换其它地址，或<a href='http://login.lvmama.com/nsso/login' class='link_blue'>用此Email登录</a>",undefined,"reg_mtop");
					} else {
						call();
					}
				}
			});
		}
		function sso_username_callback(call){
			$.ajax({
				type: "POST",
				url:  "http://login.lvmama.com/nsso/ajax/checkUniqueField.do",
				data: {
					userName: this.value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						error_tip("#sso_username","该用户名已被使用，请更换其他用户名");  
					} else {
						call();
					}
				}
			});				
		}
		function sso_verifycode1_callback(call){
			$.ajax({
				type: "POST",
				url:  "http://login.lvmama.com/nsso/ajax/checkAuthenticationCode.do",
				data: {
					authenticationCode: this.value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						error_tip("#sso_verifycode1","验证码输入错误",":last");  
					} else {
						call();
					}
				}
			});				
		}
		function validate_pass(){
			if($("#terms:checked").length==0){
				error_tip("#terms","抱歉，您必须同意驴妈妈旅游网的服务条款后，才能提交注册。","#terms_xx");
				return;
			}
			$("#emailRegForm").submit();
		}
		refreshCheckCode('image');
</script>

<script>
      cmCreatePageviewTag("邮箱注册", "F0001", null, null);
</script>
</body>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script type="text/javascript" src="/nsso/js/common/closeF5MouseRight.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js" language="javascript"></script>
</html>