<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>驴妈妈注册中心</title>
		<link href="http://pic.lvmama.com/styles/login/Login_20100420.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="c_MainContainer">
			<div class="c_eHeader"><a href="http://www.lvmama.com/" class="aNone_sty"><img src="http://pic.lvmama.com/img/lvmamalogo.gif" alt="驴妈妈网旅游网" /></a><img src="/nsso/images/login_tel.gif" alt="订购热线 8:00-20:00　1010-6060" class="c_tel" /></div>
  
			<dl class="c_Steps">
    				<dt id="Steps_Title_1"><strong>1.填写注册信息</strong></dt>
				<dd id="Steps_Content">
					<form action="/nsso/register/registerUser.do" id="regForm" method="post">
					        <@s.token></@s.token>
        					<p><span class="c_B1007E">　HI,欢迎注册驴妈妈旅游网！</span></p>
        					<ul>
							<li class="tips-card">
                						持有驴妈妈会员卡的用户，请<em onClick="$('#membershipDiv').toggle()">输入</em>会员卡号，成为驴妈妈会员卡用户。
							</li>
							<li id="membershipDiv" style="display:none;">
								<em class="c_card">会员卡：</em>
								<span><input name="membershipCard" id="sso_membership" type="text" /></span>
								<span id="sso_membership_pic" style="display:none;"><img src="/nsso/images/input_ok.gif" /></span>
								<span id="sso_membership_errorText" class="c_Gray"></span>
							</li>
            						<li>
                						<em class="c_Phone">手机号或Email：</em>
								<span><input name="mobileOrEMail" id="sso_mobileAndEmail" type="text" /></span>
								<span id="sso_mobileAndEmail_pic" style="display:none;"><img src="/nsso/images/input_ok.gif" /></span>
								<span id="sso_mobileAndEmail_errorText" class="c_Gray"></span>
							</li>
            						<li>
                						<em class="c_ID">用户名：</em>
								<span><input name="userName" id="sso_username" type="text" /></span>
								<span id="sso_username_pic" style="display:none;"><img src="/nsso/images/input_ok.gif" /></span>
								<span id="sso_username_errorText" class="c_Gray"></span>
							</li>
            						<li>
                						<em class="c_PW">设定密码：</em>
								<span><input name="password" id="sso_password" type="password" /></span>
								<span id="sso_password_pic" style="display:none;"><img src="/nsso/images/input_ok.gif" /></span>
								<span id="sso_password_errorText" class="c_Gray"></span>
							</li>
            				<li>
                				<em>再次输入密码：</em>
								<span><input name="password2" id="sso_againPassword" type="password" /></span>
								<span id="sso_againPassword_pic" style="display:none;"><img src="/nsso/images/input_ok.gif" /></span>
								<span id="sso_againPassword_errorText" class="c_Gray"></span>
							</li>
            				<li>
                				<em>所属省份：</em>
								<span>
									<select id="captialId" onChange="updateCities(this.value)">
										<option value ="110000" >北京市</option>
										<option value ="120000">天津市</option>
										<option value ="130000">河北省</option>
										<option value ="140000">山西省</option>
										<option value ="150000">内蒙古</option>
										<option value ="210000">辽宁省</option>
										<option value ="220000">吉林省</option>
										<option value ="230000">黑龙江省</option>
										<option value ="310000" selected>上海市</option>
										<option value ="320000">江苏省</option>
										<option value ="330000">浙江省</option>
										<option value ="340000">安徽省</option>
										<option value ="350000">福建省</option>
										<option value ="360000">江西省</option>
										<option value ="370000">山东省</option>
										<option value ="410000">河南省</option>
										<option value ="420000">湖北省</option>
										<option value ="430000">湖南省</option>
										<option value ="440000">广东省</option>
										<option value ="450000">广西省 </option>
										<option value ="460000">海南省</option>
										<option value ="500000">重庆市</option>
										<option value ="510000">四川省</option>
										<option value ="520000">贵州省</option>
										<option value ="530000">云南省</option>
										<option value ="540000">西藏</option>
										<option value ="610000">陕西省</option>
										<option value ="620000">甘肃省</option>
										<option value ="630000">青海省</option>
										<option value ="640000">宁夏</option>
										<option value ="650000">新疆</option>
										<option value ="F10000">香港</option>
										<option value ="F20000">澳门</option>
										<option value ="F30000">台湾</option>
									</select>	
								</span>
							</li>
            				<li>
                				<em>所属城市：</em>
								<span>
									<select id="cityId" name="cityId">
										<option value ="310000" selected>上海市</option>
									</select>	
								</span>
							</li>																
							<li class="c-tiaokuan-li">
								<input name="info" type="checkbox" checked="checked" class="c_chkbox" /><span style="color:gray;">愿意接受旅游资讯免费信息</span><br />
								<input name="info" id="terms" type="checkbox" checked="checked" class="c_chkbox" /><span style="color:gray;">同意驴妈妈旅游网会员服务条款</span>
							</li>
							<li class="c_LoginBtn_loc">
								<img src="/nsso/images/c_login_next.gif" id="registBtn" style="cursor:pointer"/>
								&nbsp;&nbsp;<span style="color:#666;">已经有驴妈妈网帐号？ <a href="/nsso/login">请登录</a></span>
							</li>
							<p class="lv_rules2">
								<strong style="font-size: 12px; font-weight: bold; display: block; margin: 0pt 0pt 10px">驴妈妈旅游网会员服务条款：</strong>
								<textarea rows="6" cols="60">驴妈妈旅游网会员服务条款
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
4.5 　会员承诺不会在驴妈妈的消息平台（包括但不限于论坛、 BBS ）发布如下信息：
(1) 反对宪法所确定的基本原则的；
(2) 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；
(3) 损害国家荣誉和利益的；
(4) 煽动民族仇恨、民族歧视，破坏民族团结的；
(5) 破坏国家宗教政策，宣扬邪教和封建迷信的；
(6) 散布谣言，扰乱社会秩序，破坏社会稳定的；
(7) 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；
(8) 侮辱或者诽谤他人，侵害他人合法权益的；
(9) 含有法律、行政法规禁止的其他内容的。
4.7 　会员单独为其发布在驴妈妈上的信息承担责任。会员若在驴妈妈散布和传播违法信息，网络会员服务的系统记录有可能作为会员违法之证据。
4.8 　会员不得利用本站的服务从事以下活动：
(1) 未经允许，进入计算机信息网络或者使用计算机信息网络资源；
(2) 未经允许，对计算机信息网络功能进行删除、修改或者增加；
(3) 未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加；
(4) 故意制作、传播计算机病毒等破坏性程序；
(5) 其他危害计算机信息网络安全的行为。
4.9 　 会员不得以任何方式干扰本站的服务。
4.10 　会员承诺遵守本站的所有其他规定和程序。
4.11 　如果会员违反上述规定，驴妈妈有权要求其改正或直接采取一切必要措施（包括但不限于更改或删除会员发布的信息、中断或终止会员使用网络的权利等），以减轻会员不当行为所造成的影响。

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
								</textarea>
							</p>
						</ul>
					</form>
					<dl>
						<dd>注册即享有：<br> 超低景点门票预订，一个人也有优惠！<br> 会员专享体验点评现金奖金！<br> 更多自由行线路促销优惠！</dd>
					</dl>
				</dd>
			</dl>
    
			<div class="c_eFoot">Copyright &copy; 2011 www.lvmama.com. 景域旅游运营集团版权所有<br />沪ICP备07509677 </div>
		</div>
	</body>
	<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
	<script type="text/javascript" src="/nsso/js/common/closeF5MouseRight.js"></script>
	<script type="text/javascript" src="/nsso/js/common/jquery.selectboxes.js"></script>
	<script type="text/javascript" src="/nsso/js/form.js"></script>
	<script type="text/javascript">
	    for(var i = 0; i < document.getElementById("captialId").options.length;i++) {  
			if (document.getElementById("captialId").options[i].value == "<@s.property value="captialId"/>"){                
			    document.getElementById("captialId").options[i].selected ="true";
			    updateCities("<@s.property value="captialId"/>");
			    break; 
			}
	    }
		
		$("#regForm").checkForm({
			fields:["mobileAndEmail","username","password","againPassword","membership"],
			submitButton:"registBtn",
			checkBefore:function(){
				if(!$('#terms').attr('checked')) {
					alert('阅读并同意条款才能进行下一步');
					$('#terms').focus();
					return false;
				}
			}
			
		});	
		function updateCities(value){
		    $("#cityId").empty();
			//$("#cityId").ajaxAddOption("http://www.lvmama.com/ajax/ajax!resultCity.do?jsoncallback=?", {"code":value});
if (value =="510000") {document.getElementById("cityId").options.add(new Option("达州","511700"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("雅安","511800"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("赣州","360700"));}
if (value =="640000") {document.getElementById("cityId").options.add(new Option("银川","640100"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("许昌","411000"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("郑州","410100"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("开封","410200"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("兴安盟","152200"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("漳州","350600"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("唐山","130200"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("信阳","411500"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("商丘","411400"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("广州","440100"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("石家庄","130100"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("张家口","130700"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("河源","441600"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("攀枝花","510400"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("江门","440700"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("丽江","530700"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("海南藏族","632500"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("红河哈尼族彝族","532500"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("铜川","610200"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("渭南","610500"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("定西","621100"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("敦煌市","620982"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("嘉峪关","620200"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("惠州","441300"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("酒泉","620900"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("鄂尔多斯","150600"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("保定","130600"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("通辽","150500"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("平顶山","410400"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("沧州","130900"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("廊坊","131000"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("呼伦贝尔","150700"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("衡水","131100"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("秦皇岛","130300"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("乌兰察布","150900"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("琼海","469002"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("朝阳","211300"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("保山","530500"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("海东地区","632100"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("阿坝州","513200"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("临沧","530900"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("海北藏族","632200"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("黄南藏族","632300"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("文山壮族苗族","532600"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("西宁","630100"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("昭通","530600"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("葫芦岛","211400"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("普洱","530800"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("大理白族","532900"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("曲靖","530300"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("迪庆藏族","533400"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("海西蒙古族","632800"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("德宏傣族景颇族","533100"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("商洛","611000"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("西双版纳","532800"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("安康","610900"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("怒江傈僳族","533300"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("呼和浩特","150100"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("汉中","610700"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("宝鸡","610300"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("鹤壁","410600"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("濮阳","410900"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("西安","610100"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("延安","610600"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("阿拉善盟","152900"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("漯河","411100"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("赤峰","150400"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("洛阳","410300"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("驻马店","411700"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("安阳","410500"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("周口","411600"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("包头","150200"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("南阳","411300"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("焦作","410800"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("新乡","410700"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("三门峡","411200"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("安庆","340800"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("阜阳市","341200"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("合肥","340100"));}
if (value =="110000") {document.getElementById("cityId").options.add(new Option("北京","110000"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("淮南","340400"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("遵义","520300"));}
if (value =="F20000") {document.getElementById("cityId").options.add(new Option("澳门","F20001"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("巢湖","341400"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("平凉","620800"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("塔城地区","654200"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("克孜勒苏柯尔克孜","653000"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("杭州","330100"));}
if (value =="310000") {document.getElementById("cityId").options.add(new Option("上海","310000"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("阜康","652302"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("南平","350700"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("菏泽","371700"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("防城港","450600"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("贵港","450800"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("河池","451200"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("崇左","451400"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("锡林郭勒盟","152500"));}
if (value =="120000") {document.getElementById("cityId").options.add(new Option("天津","120000"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("池州","341700"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("滁州","341100"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("六安市","341500"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("毕节地区","522400"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("铜仁地","522200"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("马鞍山","340500"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("芜湖","340200"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("深圳","440300"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("泉州","350500"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("莆田","350300"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("宁波","330200"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("承德","130800"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("厦门","350200"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("海南中线","460002"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("三明","350400"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("宣城","341800"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("东莞","441900"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("黔西南布依族苗族","522300"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("龙岩","350800"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("海南东线","460001"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("福州","350100"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("榆林","610800"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("昆明","530100"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("玉溪","530400"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("兰州","620100"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("临夏回族","622900"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("甘南藏族","623000"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("海南西线","460003"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("佛山","440600"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("白银","620400"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("陇南","621200"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("天水","620500"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("武威","620600"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("张掖","620700"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("广元","510800"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("泸州","510500"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("大连","210200"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("沈阳","210100"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("宜宾","511500"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("拉萨","540100"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("阿里","542500"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("林芝","542600"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("日喀则","542300"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("梅州","441400"));}
if (value =="640000") {document.getElementById("cityId").options.add(new Option("石嘴山","640200"));}
if (value =="640000") {document.getElementById("cityId").options.add(new Option("吴忠","640300"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("本溪","210500"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("绵阳","510700"));}
if (value =="640000") {document.getElementById("cityId").options.add(new Option("中卫","640500"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("成都","510100"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("德阳","510600"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("抚顺","210400"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("丹东","210600"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("桂林","450300"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("锦州","210700"));}
if (value =="640000") {document.getElementById("cityId").options.add(new Option("固原","640400"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("来宾","451300"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("营口","210800"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("钦州","450700"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("巴中","511900"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("资阳","512000"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("广安","511600"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("阜新","210900"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("遂宁","510900"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("内江","511000"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("南充","511300"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("眉山","511400"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("凉山彝族","513400"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("甘孜藏族","513300"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("乐山","511100"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("辽阳","211000"));}
if (value =="510000") {document.getElementById("cityId").options.add(new Option("自贡","510300"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("盘锦","211100"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("楚雄","532300"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("景德镇","360200"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("余姚","330281"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("牡丹江","231000"));}
if (value =="F30000") {document.getElementById("cityId").options.add(new Option("台南","F30004"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("博尔塔拉","652700"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("济宁","370800"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("齐齐哈尔","230200"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("阳江","441700"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("日照","371100"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("南通","320600"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("吉安","360800"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("青岛","370200"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("四平","220300"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("大同","140200"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("宿迁","321300"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("象山","330225"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("威海","371000"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("黄山","341000"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("苏州","320500"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("济宁市曲阜市","370881"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("东营","370500"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("七台河","230900"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("德州","371400"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("滨州","371600"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("昌都","542100"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("山南","542200"));}
if (value =="540000") {document.getElementById("cityId").options.add(new Option("那曲","542400"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("朔州","140600"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("淮北","340600"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("铜陵","340700"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("庆阳","621000"));}
if (value =="620000") {document.getElementById("cityId").options.add(new Option("金昌","620300"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("茂名","440900"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("中山","442000"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("潮州","445100"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("揭阳","445200"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("长沙","430100"));}
if (value =="410000") {document.getElementById("cityId").options.add(new Option("济源","410881"));}
if (value =="610000") {document.getElementById("cityId").options.add(new Option("咸阳","610400"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("咸宁","421200"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("湘西土家族","433100"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("阿克苏","652900"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("蚌埠","340300"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("云浮","445300"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("玉林","450900"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("淄博","370300"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("汕头","440500"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("乌海","150300"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("巴彦淖尔","150800"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("济南","370100"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("莱芜","371200"));}
if (value =="500000") {document.getElementById("cityId").options.add(new Option("重庆","500108"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("南京","320100"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("玉树藏族","632700"));}
if (value =="F10000") {document.getElementById("cityId").options.add(new Option("香港","F10001"));}
if (value =="630000") {document.getElementById("cityId").options.add(new Option("果洛藏族","632600"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("湖州","330500"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("岳阳","430600"));}
if (value =="350000") {document.getElementById("cityId").options.add(new Option("宁德","350900"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("无锡","320200"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("九江","360400"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("萍乡","360300"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("聊城","371500"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("巴音郭楞","652800"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("武汉","420100"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("孝感","420900"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("怀化","431200"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("烟台","370600"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("绥化","231200"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("长春","220100"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("湛江","440800"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("泰安","370900"));}
if (value =="F30000") {document.getElementById("cityId").options.add(new Option("台北","F30001"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("黄石","420200"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("南海","440605"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("扬州","321000"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("张家界","430800"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("伊春","230700"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("佳木斯","230800"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("金华","330700"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("邯郸","130400"));}
if (value =="130000") {document.getElementById("cityId").options.add(new Option("邢台","130500"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("临安","330185"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("万宁","469006"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("海口","460100"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("三亚","460200"));}
if (value =="460000") {document.getElementById("cityId").options.add(new Option("西沙群岛","469037"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("安顺","520400"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("贵阳","520100"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("台州","331000"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("黔东南苗族侗族","522600"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("六盘水","520200"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("丽水","331100"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("嘉兴","330400"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("舟山","330900"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("衢州","330800"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("黔南布依族苗族","522700"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("绍兴","330600"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("温州","330300"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("哈密地区","652200"));}
if (value =="F30000") {document.getElementById("cityId").options.add(new Option("台中","F30002"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("白山","220600"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("肇庆","441200"));}
if (value =="F30000") {document.getElementById("cityId").options.add(new Option("台东","F30003"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("铁岭","211200"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("喀什","653100"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("梧州","450400"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("清远","441800"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("南昌","360100"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("常州","320400"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("昌吉回族","652300"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("衡阳","430400"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("珠海","440400"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("乌鲁木齐","650100"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("新余","360500"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("常德","430700"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("娄底","431300"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("枣庄","370400"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("吐鲁番","652101"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("宜春","360900"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("永州","431100"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("太原","140100"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("克拉玛依","650200"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("仙居","331024"));}
if (value =="340000") {document.getElementById("cityId").options.add(new Option("黟县","341023"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("长岛","370634"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("韶山","430382"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("都匀","522701"));}
if (value =="520000") {document.getElementById("cityId").options.add(new Option("凯里","522601"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("镇江","321100"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("宜兴","320282"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("晋城","140500"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("临汾","141000"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("潍坊","370700"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("周庄","320001"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("乌镇","330001"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("雁荡山","330002"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("楠溪江","330003"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("浙西大峡谷","330004"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("泰州","321200"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("晋中","140700"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("吕梁","141100"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("淮安","320800"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("柳州","450200"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("运城","140800"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("婺源","361130"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("三清山","361131"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("桐庐","330122"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("千岛湖","330123"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("阳朔","450321"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("中甸","530001"));}
if (value =="530000") {document.getElementById("cityId").options.add(new Option("泸沽湖","530002"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("忻州","140900"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("盐城","320900"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("阳泉","140300"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("郴州","431000"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("邵阳","430500"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("哈尔滨","230100"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("双鸭山","230500"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("恩施土家族","422800"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("大庆","230600"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("株洲","430200"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("鸡西","230300"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("阿勒泰","654300"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("百色","451000"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("鹤岗","230400"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("益阳","430900"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("徐州","320300"));}
if (value =="230000") {document.getElementById("cityId").options.add(new Option("黑河","231100"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("和田地区","653200"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("宜昌","420500"));}
if (value =="650000") {document.getElementById("cityId").options.add(new Option("伊犁哈萨克","654000"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("襄樊","420600"));}
if (value =="210000") {document.getElementById("cityId").options.add(new Option("鞍山","210300"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("十堰","420300"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("鹰潭","360600"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("黄冈","421100"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("北海","450500"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("鄂州","420700"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("白城","220800"));}
if (value =="140000") {document.getElementById("cityId").options.add(new Option("长治","140400"));}
if (value =="430000") {document.getElementById("cityId").options.add(new Option("湘潭","430300"));}
if (value =="370000") {document.getElementById("cityId").options.add(new Option("临沂","371300"));}
if (value =="330000") {document.getElementById("cityId").options.add(new Option("湖州市安吉县","330523"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("荆门","420800"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("辽源","220400"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("汕尾","441500"));}
if (value =="150000") {document.getElementById("cityId").options.add(new Option("兴安盟阿尔山","152202"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("随州","421300"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("延边朝鲜","222400"));}
if (value =="440000") {document.getElementById("cityId").options.add(new Option("韶关","440200"));}
if (value =="420000") {document.getElementById("cityId").options.add(new Option("荆州","421000"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("南宁","450100"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("抚州","361000"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("松原","220700"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("通化","220500"));}
if (value =="450000") {document.getElementById("cityId").options.add(new Option("贺州","451100"));}
if (value =="220000") {document.getElementById("cityId").options.add(new Option("吉林","220200"));}
if (value =="360000") {document.getElementById("cityId").options.add(new Option("上饶","361100"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("连云港","320700"));}
if (value =="320000") {document.getElementById("cityId").options.add(new Option("常熟","320002"));}



			
			for(var i = 0; i < document.getElementById("cityId").options.length;i++) {  
				if (document.getElementById("cityId").options[i].value == '<@s.property value="cityId"/>')                
			    	document.getElementById("cityId").options[i].selected ="true"; 
			}
		}
	</script>
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>

