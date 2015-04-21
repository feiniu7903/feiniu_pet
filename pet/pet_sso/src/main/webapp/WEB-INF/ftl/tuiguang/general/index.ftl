<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>手机注册</title>
<link href="/nsso/style/newegg_css.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div class="lvmama_login">    
    <div class="step_list"><img src="/nsso/images/newegg/step.gif" /></div>    
    <form action="/nsso/tuiguang/verification.do" id="regForm" method="post">
    <@s.token></@s.token>
	<input name="channel" type="hidden" value="${channel}"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="24%" align="right" valign="middle"><div class="zhuci_title">手机号：</div></td>
        <td width="11%" valign="middle">
        <input name="mobile" id="sso_mobile" type="text" class="login_txt" /></td>
        <td width="65%" valign="middle">
        	<span id="sso_mobile_pic" style="display:none;"><em class="sR"></em></span>
        	<span id="sso_mobile_errorText" class="zhuci_tips"></span>        </td>
      </tr>
      <tr>
        <td height="28"  align="right" valign="middle"><div class="zhuci_title">用户名：</div></td>
        <td valign="middle">
            <input name="userName" id="sso_username" type="text" class="login_txt" />
        </td>
        <td valign="middle">
        	<span id="sso_username_pic" style="display:none;"><em class="sR"></em></span>
			<span id="sso_username_errorText" class="zhuci_tips"></span>        </td>
      </tr>
      <tr>
        <td align="right" valign="middle"><div class="zhuci_title">设定密码：</div></td>
        <td valign="middle">
        	<input name="password" id="sso_password" type="password" class="login_txt" />
        </td>
        <td valign="middle">
        	<span id="sso_password_pic" style="display:none;"><em class="sR"></em></span>
			<span id="sso_password_errorText" class="zhuci_tips"></span>		</td>
      </tr>
      <tr>
        <td align="right" valign="middle"><div class="zhuci_title">确认密码：</div></td>
        <td valign="middle">
        	<input name="password2" id="sso_againPassword" type="password" class="login_txt" />
        </td>
        <td valign="middle">
        	<span id="sso_againPassword_pic" style="display:none;"><em class="sR"></em></span>
			<span id="sso_againPassword_errorText" class="zhuci_tips"></span>        </td>
      </tr>
      <tr>
        <td align="right" valign="middle"><div class="zhuci_title">所在地：</div></td>
        <td valign="middle">
        	<select id="captialId" onChange="updateCities(this.value)">
				<option value ="110000" >北京市</option>
				<option value ="120000" >天津市</option>
				<option value ="130000" >河北省</option>
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
			&nbsp;
			&nbsp;
			<select id="cityId" name="cityId">
				<option value ="310000" selected>上海市</option>
			</select>
        </td>        
      </tr>
      <tr>
        <td align="right" valign="middle">&nbsp;</td>
        <td><input type="button" name="registBtn" id="registBtn" value="下一步"  class="bt_lv1"/></td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </form>
  </div>
  <#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</body>

<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="/nsso/js/common/closeF5MouseRight.js"></script>
<script type="text/javascript" src="/nsso/js/common/jquery.selectboxes.js"></script>
<script type="text/javascript" src="/nsso/js/form.js"></script>
<script type="text/javascript">
		$("#regForm").checkForm({
			fields:["mobile","username","password","againPassword"],
			submitButton:"registBtn"			
		});	
		
		for(var i = 0; i < document.getElementById("captialId").options.length;i++) {  
			if (document.getElementById("captialId").options[i].value == "<@s.property value="captialId"/>"){                
			    document.getElementById("captialId").options[i].selected ="true";
			    updateCities("<@s.property value="captialId"/>");
			    break; 
			}
		}	
			
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
</html>