<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="robots" content="index,follow" />
<meta name="author" content="出国在线" />

<title>国际时差_世界各国时差查询工具_驴妈妈帮助中心</title>
<meta name="keywords" content="国际时差,时差查询"/>
<meta name="description" content="驴妈妈国际时差帮助中心:提供世界各国时差查询,其中包括美国,英国伦敦,法国巴黎,雅典,纽约,夏威夷时差查询,更多旅游工具,就在驴妈妈帮助中心."/>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
<link href="http://pic.lvmama.com/styles/new_v/help_center/hc_index.css?r=4589" rel="stylesheet" type="text/css" />
<#include "/common/coremetricsHead.ftl">
</head>

<body onload=startclock(); onunload=stopclock();>
<#include "/WEB-INF/pages/helpCenter/head.ftl"/>
<div class="wapper_hc">
  <div class="hc_main">
   <ul class="daohang_bars">
   		<li><a href="/public/help">帮助中心 </a></li>
        <li>> 旅游百宝箱 </li>
        <li>> 国际时差</li>
   </ul>
<h2 class="time_biao">国际时差对照</h2>
<div class="map_style">

	  <SCRIPT language=javascript>
var biz;
var speed=1000;
var pre=1;
var tidA=null;
var tidB=null;
var offsetA;
var offsetB;
var diff;
var t;

function doDateA()

{
    dA = new Date();
    gtA = dA.getTime();
    gtA = gtA + offsetA;
    dA.setTime(gtA);
    document.addform.localtime.value=dA.toGMTString();
    tidA=window.setTimeout("doDateA()",speed);

}


function doDateB()
{
    dB = new Date();
    gtB = dB.getTime();
    gtB = gtB + offsetB;
    dB.setTime(gtB);
    document.addform.worldtime.value=dB.toGMTString();
    tidB=window.setTimeout("doDateB()",speed);
}

function start() {
  d = new Date();
  if (navigator.appVersion.substring(0,3) == "2.0")
    offsetA = (d.getTimezoneOffset()*60*1000);
  else
    offsetA = -(d.getTimezoneOffset()*60*1000);
    tidA=window.setTimeout("doDateA()",speed);
    biz=offsetA;
 }

function startLong(diff) {
  offsetB = (diff) * 60 * 60 * 1000 + biz;
  tidB=window.setTimeout("doDateB()",speed);
}

function cleartid() {
  document.addform.worldtime.value=" ";
  window.clearTimeout(tidB);
}

function selechan()
{
t=document.addform.selectarea.selectedIndex;
if (pre != t)
{
cleartid();
if ((t==65) || (t==93) || (t==113) || (t==125) || (t==135) || (t==163))
   startLong(-4);
if ((t==64) || (t==87) || (t==137)) startLong(-0.5);
if ((t==63) || (t==104) || (t==144)) startLong(-2.5);
if ((t==45) || (t==102) || (t==108)) startLong(4);
if (t==32) startLong(-18.5);
if ((t==21) || (t==60) || (t==84) || (t==103) || (t==119) || (t==154))
   startLong(0);
if ((t==19) || (t==22) || (t==24) || (t==35) || (t==41) || (t==46) || (t==53))
   startLong(-6);
if ((t==68) || (t==73) || (t==78) || (t==79) || (t==81) || (t==86) || (t==99))
   startLong(-6);
if ((t==126) || (t==128) || (t==141) || (t==148) || (t==150))
   startLong(-6);
if ((t==153) || (t==161) || (t==177) || (t==178)) startLong(-6);
if ((t==15) || (t==33) || (t==42) || (t==56) || (t==59) || (t==94) || (t==109))
   startLong(-14);
if (t==12) startLong(-2);
if ((t==11) || (t==38) || (t==43) || (t==66) || (t==74) || (t==76) || (t==85))
   startLong(-5);
if ((t==124) || (t==127) || (t==133) || (t==140) || (t==155) || (t==162) || (t==167) || (t==174))
   startLong(-5);
if ((t==10) || (t==26) || (t==28) || (t==30) || (t==34) || (t==40))
   startLong(-13);
if ((t==58) || (t==71) || (t==115) || (t==118) || (t==166))
   startLong(-13);
if ((t==8) || (t==55) || (t==91) || (t==116)) startLong(2);
if ((t==6) || (t==20) || (t==57) || (t==165)) startLong(-11);
if ((t==4) || (t==5) || (t==13) || (t==17) || (t==18) || (t==29) || (t==39))
   startLong(-12);
if ((t==48) || (t==54) || (t==92) || (t==97) || (t==106) || (t==117))
   startLong(-12);
if ((t==123) || (t==145) || (t==146) || (t==147) || (t==159) || (t==172) || (t==173))
   startLong(-12);
if ((t==3) || (t==9) || (t==14) || (t==16) || (t==25) || (t==31) || (t==36) || (t==37))
   startLong(-7);

if ((t==47) || (t==49) || (t==51) || (t==52) || (t==61) || (t==69) || (t==82))
   startLong(-7);
if ((t==83) || (t==90) || (t==96) || (t==101) || (t==105) || (t==110) || (t==111))
   startLong(-7);
if ((t==112) || (t==120) || (t==131) || (t==138) || (t==143) || (t==151))
   startLong(-7);
if ((t==152) || (t==160) || (t==169) || (t==175) || (t==176))
   startLong(-7);
if ((t==0) || (t==1) || (t==2) || (t==7) || (t==27) || (t==44))
   startLong(-8);
if (t==23) startLong(-8);
if ((t==50) || (t==62) || (t==67) || (t==70) || (t==80) || (t==89) || (t==98))
   startLong(-8);
if ((t==122) || (t==132) || (t==134) || (t==136) || (t==157) || (t==164))
   startLong(-8);
if ((t==77) || (t==156) || (t==171)) startLong(-1);
if ((t==72) || (t==75) || (t==142)) startLong(1);
if ((t==88) || (t==114)) startLong(-3);
if ((t==95) || (t==129) || (t==130)) startLong(-19);
if (t==100) startLong(-1.5);
if ((t==107) || (t==121) || (t==139) || (t==168)) startLong(3);
if (t==158) startLong(5);
if (t==170) startLong(-12.5);
if (t==149) startLong(-11.5);
pre=t;
}
}
      </SCRIPT>
          <TABLE width="750" border=0 align=center cellPadding=0 cellSpacing=0>
    <td colspan="3"><FORM name=addform>
      
            <TBODY>
              <TR>
                <TD colspan="3">　</TD>
              </TR>
              <TR>
                <TD width="200" height=30 class=centent1><div class="time_style">中国北京标准时间：</div></TD>
                <TD colspan="2" class=centent1><input size=30 name=localtime class="input_time"></TD>
              </TR>
              <TR>
                <TD height=30 ><div class="time_style">查看所选国的时间：</div></TD>
                <TD width="377" height=30><span class="centent1">
                  <span>国家</span><select onChange=selechan() size=1 name=selectarea>
                    <option 
              selected>格林威治标准时间 GMT</option>
                    <option>阿尔及利亚 Algeria</option>
                    <option>安道尔 Andorra</option>
                    <option>安哥拉 Angola</option>
                    <option>安圭拉 Anguilla</option>
                    <option>安提瓜和巴布达 Antigua and 
                      Barbuda</option>
                    <option>阿根廷 Argentina</option>
                    <option>阿松森 
                      Ascension</option>
                    <option>澳大利亚 Australia</option>
                    <option>奥地利 
                      Austria</option>
                    <option>巴哈马 Bahamas</option>
                    <option>巴林 
                      Bahrain</option>
                    <option>孟加拉国 Bangladesh</option>
                    <option>巴巴多斯 
                      Barbados</option>
                    <option>比利时 Belize</option>
                    <option>伯里兹 
                      Belize</option>
                    <option>贝宁 Benin</option>
                    <option>百慕大群岛 Bermuda 
                      Is.</option>
                    <option>玻利维亚 Bolivia</option>
                    <option>博茨瓦纳 
                      Botswana</option>
                    <option>巴西 Brazil</option>
                    <option>文莱 
                      Brunei</option>
                    <option>保加利亚 Bulgaria</option>
                    <option>布基纳法索 
                      Burkina-faso</option>
                    <option>布隆迪 Brurndi</option>
                    <option>喀麦隆 
                      Cameroon</option>
                    <option>加拿大 Canada</option>
                    <option>加那利群岛 
                      Canaries Is.</option>
                    <option>开曼群岛 Cayman Is.</option>
                    <option>智利 
                      Chile</option>
                    <option>哥伦比亚 Colombia</option>
                    <option>刚果 
                      Congo</option>
                    <option>科克群岛 Cook Is.</option>
                    <option>哥斯达黎加 Costa 
                      Rica</option>
                    <option>古巴 Cuba</option>
                    <option>塞浦路斯 
                      Cyprus</option>
                    <option>捷克斯洛伐克 Czechoslovakia</option>
                    <option>丹麦 
                      Denmark</option>
                    <option>吉布提 Djibouti</option>
                    <option>多米尼加共和国 
                      Dominica Rep</option>
                    <option>厄瓜多尔 Ecuador</option>
                    <option>埃及 
                      Eqypt</option>
                    <option>萨尔瓦多 El Salvador</option>
                    <option>埃塞俄比亚 
                      Ethiopia</option>
                    <option>赤道几内亚 Equatoria</option>
                    <option>斐济 
                      Fiji</option>
                    <option>芬兰 Finland</option>
                    <option>法国 
                      France</option>
                    <option>法属圭亚那 French Guiana</option>
                    <option>加蓬 
                      Gabon</option>
                    <option>冈比亚 Gambia</option>
                    <option>德国 
                      Germany</option>
                    <option>直布罗陀(英） Gibraltar</option>
                    <option>希腊 
                      Greece</option>
                    <option>格林纳达 Grenada</option>
                    <option>关岛 
                      Guam</option>
                    <option>危地马拉 Guatemala</option>
                    <option>圭亚那 
                      Guyana</option>
                    <option>海地 Haiti</option>
                    <option>洪都拉斯 
                      Honduras</option>
                    <option>香港 HongKong</option>
                    <option>匈牙利 
                      Hungary</option>
                    <option>冰岛 Iceland</option>
                    <option>印度 
                      India</option>
                    <option>印度尼西亚 Indonesia</option>
                    <option>伊朗 
                      Iran</option>
                    <option>伊拉克 Iraq</option>
                    <option>爱尔兰 
                      Ireland</option>
                    <option>以色列 Israel</option>
                    <option>意大利 
                      Italy</option>
                    <option>科特迪瓦 Ivory Coast</option>
                    <option>牙买加 
                      Jamaica</option>
                    <option>日本 Japan</option>
                    <option>约旦 
                      Jordan</option>
                    <option>肯尼亚 Kenya</option>
                    <option>朝鲜人民共和国 
                      D.P.R.Korea</option>
                    <option>科威特 Kuwait</option>
                    <option>老挝 
                      Laos</option>
                    <option>黎巴嫩 Lebanon</option>
                    <option>莱索托 
                      Lesotho</option>
                    <option>利比里亚 Liberia</option>
                    <option>利比亚 
                      Libya</option>
                    <option>列支敦士登 Liechtenstein</option>
                    <option>卢森堡 
                      Luxembourg</option>
                    <option>澳门 Macao</option>
                    <option>马达加斯加 
                      Madagascar</option>
                    <option>马拉维 Malawi</option>
                    <option>马来西亚 
                      Malaysia</option>
                    <option>马尔代夫 Maldive</option>
                    <option>马里 
                      Mali</option>
                    <option>马耳他 Malta</option>
                    <option>玛利亚那群岛 Mariana 
                      Is.</option>
                    <option>马提尼克 Martinique</option>
                    <option>毛里求斯 
                      Mauritius</option>
                    <option>墨西哥 Mexico</option>
                    <option>中途岛 Midway 
                      I.</option>
                    <option>摩纳哥 Monaco</option>
                    <option>蒙特塞拉特岛 Montserrat 
                      Is.</option>
                    <option>摩洛哥 Morocco</option>
                    <option>莫桑比克 
                      Mozambique</option>
                    <option>缅甸 Myanmar</option>
                    <option>纳米比亚 
                      Namibia</option>
                    <option>瑙鲁 Nauru</option>
                    <option>蒙古 
                      Mongolia</option>
                    <option>尼泊尔 Nepal</option>
                    <option>荷兰 
                      Netherlands</option>
                    <option>荷属安的列斯 Netherlands Antilles</option>
                    <option>新喀里多尼亚群岛 New Caledonia Is.</option>
                    <option>新西兰 New 
                      Zealand</option>
                    <option>尼加拉瓜 Nicaragua</option>
                    <option>尼日尔 
                      Niger</option>
                    <option>尼日利亚 Nigeria</option>
                    <option>挪威 
                      Norway</option>
                    <option>阿曼 Oman</option>
                    <option>巴基斯坦 
                      Pakistan</option>
                    <option>巴拿马 Panama</option>
                    <option>巴布亚新几内亚 
                      Papua New Guinea</option>
                    <option>巴拉圭 Paraguay</option>
                    <option>秘鲁 
                      Peru</option>
                    <option>菲律宾 Philippines</option>
                    <option>波兰 
                      Poland</option>
                    <option>波利尼西亚 Polynesia</option>
                    <option>葡萄牙 
                      Portugal</option>
                    <option>波多黎各 Puerto Rico</option>
                    <option>卡塔尔 
                      Qatar</option>
                    <option>留尼旺 Reunion</option>
                    <option>罗马尼亚 
                      Rumania</option>
                    <option>俄罗斯 Russian</option>
                    <option>卢旺达 
                      Rwanda</option>
                    <option>东萨摩亚(美) Samoa Easterm</option>
                    <option>西萨摩亚(美) Samoa Western</option>
                    <option>圣马力诺 San 
                      Marino</option>
                    <option>圣多美和普林西比 Sao Tome and Principe</option>
                    <option>沙特阿拉伯 Saudi Arabia</option>
                    <option>塞内加尔 Senegal</option>
                    <option>塞舌尔 Seychelles</option>
                    <option>塞拉利昂 Sierra Leone</option>
                    <option>新加坡 Singapore</option>
                    <option>斯洛文尼亚 Slovenia</option>
                    <option>所罗门群岛 Solomon Is.</option>
                    <option>索马里 Somali</option>
                    <option>南非 South Africa</option>
                    <option>韩国 South Korea</option>
                    <option>西班牙 Spain</option>
                    <option>斯里兰卡 Srilanka</option>
                    <option>圣克里斯托弗和尼维斯岛St.Christopher and Nevis Is.</option>
                    <option>圣卢西亚 St.Lucia</option>
                    <option>圣文森特 St.Vincent</option>
                    <option>苏丹 Sudan</option>
                    <option>苏里南 Suriname</option>
                    <option>斯威士兰 Swaziland</option>
                    <option>瑞典 Sweden</option>
                    <option>瑞士 Switzerland</option>
                    <option>叙利亚 Syria</option>
                    <option>台湾省 Taiwan Prov</option>
                    <option>坦桑尼亚 Tanzania</option>
                    <option>泰国 Thailand</option>
                    <option>多哥 Togo</option>
                    <option>汤加 
                      Tonga</option>
                    <option>特立尼达和多巴哥 Trinidad and Tobago</option>
                    <option>突尼斯 Tunisia</option>
                    <option>土尔其 Turkey</option>
                    <option>乌干达 Uganda</option>
                    <option>阿联酋 U.A.E</option>
                    <option>英国 
                      U.K.</option>
                    <option>乌拉圭 Uruguay</option>
                    <option>美国 
                      U.S.A.</option>
                    <option>乌兹别克斯坦 Uzbekistan</option>
                    <option>瓦努阿图 
                      Vanuatu</option>
                    <option>梵蒂冈 Vatican</option>
                    <option>委内瑞拉 
                      Venezuela</option>
                    <option>越南 Vietnam</option>
                    <option>维尔京群岛(英) 
                      Virgin Is.(British)</option>
                  </select>
                </span></TD>
                <TD width="307"><input size=30 name=worldtime class="input_time"></TD>
              </TR>
              <TR>
                <TD height=30 colspan="3" class=centent1><OPEION></TD>
              </TR>
	          </FORM>

            </TBODY>
</TABLE>
        <CENTER>
</CENTER>
<SCRIPT language=javascript>
start();
startLong(-8);
          </SCRIPT>  
<TABLE width=750 border=0 align=center cellPadding=0 cellSpacing=0>
  <CENTER></CENTER>
  <TBODY>
  <TR>
    <FORM class=_form name=clock method=post>
    <TD class=_form>
      <TABLE width=750 align=center border=0 bgcolor="#dc4e88" height="30">
        <TBODY>
        <TR>
          <TD width="92%"><input size=42 name=face  class="input_time input_timeadd"> 
            <span class="dianji_style">点击目标地区，即可得到相应城市时间！</span></TD>
        </TR></TBODY></TABLE></TD>
    </FORM></TR>
  <TR>
    <TD align=middle valign="middle">
      <P style="LINE-HEIGHT: 100%" aglin="center"><FONT color=#000080><MAP 
      name=Map><AREA onclick=worldtime(12); shape=RECT 
        coords=380,84,433,117><AREA onclick=worldtime(11); shape=RECT 
        coords=361,14,401,82><AREA onclick=worldtime(10); shape=RECT 
        coords=330,20,362,132><AREA onclick=worldtime(9); shape=RECT 
        coords=296,80,329,132><AREA onclick=worldtime(9); shape=RECT 
        coords=283,4,328,79><AREA onclick=worldtime(2); shape=POLY 
        coords=99,31,93,75,106,75,107,32><AREA onclick=worldtime(1); shape=POLY 
        coords=69,105,59,27,101,27,92,105><AREA onclick=worldtime(9); shape=RECT 
        coords=332,227,350,279><AREA onclick=worldtime(8); shape=RECT 
        coords=308,244,331,283><AREA onclick=worldtime(11); shape=RECT 
        coords=393,251,441,328><AREA onclick=worldtime(12); shape=POLY 
        coords=404,12,459,54,405,88><AREA onclick=worldtime(12); shape=RECT 
        coords=395,341,449,390><AREA onclick=worldtime(10); shape=RECT 
        coords=350,251,392,377><AREA onclick=worldtime(9.5); shape=RECT 
        coords=325,281,350,358><AREA onclick=worldtime(9); shape=RECT 
        coords=303,133,362,169><AREA onclick=worldtime(8); shape=RECT 
        coords=288,291,324,360><AREA onclick=worldtime(8); shape=RECT 
        coords=278,187,341,227><AREA onclick=worldtime(8); shape=RECT 
        coords=249,168,326,187><AREA onclick=worldtime(7); shape=RECT 
        coords=219,3,280,78><AREA onclick=worldtime(8); shape=RECT 
        coords=219,80,302,168><AREA onclick=worldtime(8); shape=RECT 
        coords=256,241,308,260><AREA onclick=worldtime(7); shape=RECT 
        coords=256,262,305,286><AREA onclick=worldtime(7); shape=RECT 
        coords=271,226,320,242><AREA onclick=worldtime(7); shape=RECT 
        coords=240,208,279,225><AREA onclick=worldtime(6); shape=RECT 
        coords=198,169,249,184><AREA onclick=worldtime(6); shape=RECT 
        coords=237,186,268,207><AREA onclick=worldtime(6); shape=RECT 
        coords=197,232,249,252><AREA onclick=worldtime(5.5); shape=RECT 
        coords=190,183,238,230><AREA onclick=worldtime(5); shape=RECT 
        coords=158,173,188,191><AREA onclick=worldtime(5); shape=RECT 
        coords=163,154,216,169><AREA onclick=worldtime(4.5); shape=RECT 
        coords=174,138,216,154><AREA onclick=worldtime(4); shape=RECT 
        coords=163,133,173,154><AREA onclick=worldtime(3.5); shape=RECT 
        coords=123,160,165,172><AREA onclick=worldtime(5); shape=RECT 
        coords=160,3,216,32><AREA onclick=worldtime(5); shape=RECT 
        coords=177,33,219,137><AREA onclick=worldtime(3); shape=RECT 
        coords=111,36,172,76><AREA onclick=worldtime(3); shape=RECT 
        coords=137,78,176,132><AREA onclick=worldtime(3); shape=POLY 
        coords=140,190,182,194,214,317,156,342,144,313><AREA 
        onclick=worldtime(3); shape=RECT coords=121,171,157,185><AREA 
        onclick=worldtime(-4); shape=RECT coords=641,293,684,374><AREA 
        onclick=worldtime(1); shape=RECT coords=55,193,109,294><AREA 
        onclick=worldtime(1); shape=RECT coords=45,157,96,179><AREA 
        onclick=worldtime(2); shape=RECT coords=55,296,109,361><AREA 
        onclick=worldtime(2); shape=RECT coords=110,196,140,363><AREA 
        onclick=worldtime(2); shape=RECT coords=97,161,121,192><AREA 
        onclick=worldtime(2); shape=RECT coords=95,133,162,160><AREA 
        onclick=worldtime(2); shape=RECT coords=95,77,134,134><AREA 
        onclick=worldtime(1); shape=RECT coords=38,106,96,157><AREA 
        onclick=worldtime(0); shape=RECT coords=14,178,81,193><AREA 
        onclick=worldtime(0); shape=POLY 
        coords=53,242,52,201,52,195,14,193,12,245><AREA onclick=worldtime(0); 
        shape=POLY coords=41,50,68,91,67,106,3,108,4,83><AREA 
        onclick=worldtime(-3.5); shape=CIRCLE coords=755,151,17><AREA 
        onclick=worldtime(-3); shape=RECT coords=684,291,763,372><AREA 
        onclick=worldtime(-12); shape=POLY coords=432,5,464,5,461,50><AREA 
        onclick=worldtime(-12); shape=POLY 
        coords=461,62,453,165,431,136,439,78><AREA onclick=worldtime(-12); 
        shape=POLY coords=433,155,436,254,416,233,416,155><AREA 
        onclick=worldtime(-11); shape=POLY 
        coords=467,5,493,5,493,23,467,33><AREA onclick=worldtime(-11); 
        shape=POLY coords=461,111,480,110,480,175,458,174><AREA 
        onclick=worldtime(-11); shape=POLY 
        coords=444,200,468,204,467,248,443,244><AREA onclick=worldtime(-11); 
        shape=POLY coords=442,304,464,304,465,367,450,343,440,340><AREA 
        onclick=worldtime(-10); shape=POLY 
        coords=438,171,498,182,500,211,438,195><AREA onclick=worldtime(-10); 
        shape=RECT coords=470,209,497,369><AREA onclick=worldtime(-10); 
        shape=RECT coords=483,101,498,177><AREA onclick=worldtime(-9); 
        shape=POLY coords=493,26,465,41,464,101,499,93><AREA 
        onclick=worldtime(-9); shape=RECT coords=497,5,524,35><AREA 
        onclick=worldtime(-9); shape=RECT coords=501,83,523,369><AREA 
        onclick=worldtime(-8); shape=RECT coords=499,37,568,77><AREA 
        onclick=worldtime(-8); shape=RECT coords=526,3,552,32><AREA 
        onclick=worldtime(-8); shape=POLY 
        coords=522,84,565,83,591,197,577,198,524,197><AREA 
        onclick=worldtime(-8); shape=RECT coords=526,198,567,302><AREA 
        onclick=worldtime(-8); shape=RECT coords=525,330,561,367><AREA 
        onclick=worldtime(-7); shape=RECT coords=571,6,594,104><AREA 
        onclick=worldtime(-7); shape=POLY 
        coords=574,109,611,137,612,155,582,158><AREA onclick=worldtime(-6); 
        shape=RECT coords=597,2,641,129><AREA onclick=worldtime(-6); shape=POLY 
        coords=614,132,614,159,639,160,638,131><AREA onclick=worldtime(-6); 
        shape=POLY coords=641,162,641,169,592,171,595,160><AREA 
        onclick=worldtime(-6); shape=POLY 
        coords=639,174,639,229,593,229,592,189,606,172><AREA 
        onclick=worldtime(-6); shape=RECT coords=524,303,565,329><AREA 
        onclick=worldtime(-5); shape=RECT coords=641,3,688,291><AREA 
        onclick=worldtime(-4); shape=RECT coords=689,66,714,168><AREA 
        onclick=worldtime(-6); shape=RECT coords=689,236,713,291><AREA 
        onclick=worldtime(-3); shape=POLY coords=689,4,723,81,762,40,761,7><AREA 
        onclick=worldtime(-3); shape=POLY 
      coords=715,240,715,291,758,295,746,243></MAP></FONT></P>
      <TABLE cellSpacing=0 cellPadding=0 align=left>
        <TBODY>
        <TR>
<TD><IMG class=hand src="http://pic.lvmama.com/img/new_v/hc_img/map.gif" useMap=#Map border=0></TD></TR></TBODY></TABLE></TD>
  </TR></TBODY></TABLE>


<SCRIPT language=JavaScript>//Stock Query.
function callpage(htmlurl,theform) {
  var newhtmlurl
  newhtmlurl = htmlurl + "?STYLE=SSQX&STKNO=" + theform.STKNO.value + "&MT=GET"
  var newwin=window.open(newhtmlurl,"stockWin","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=605,height=500");
  return false;
}
</SCRIPT>

<SCRIPT language=JavaScript>

<!--
var timerID = null;
var timerRunning = false;
var _area = 0;

function stopclock(){
    if(timerRunning)
        clearTimeout(timerID);
    timerRunning = false;
}

function startclock(){
    stopclock();
    showlocaltime();
}

function showlocaltime(){
    var now = new Date();
 var _day = new Array(7);
 _day[0]='日';
 _day[1]='一';
 _day[2]='二';
 _day[3]='三';
 _day[4]='四';
 _day[5]='五';
 _day[6]='六';

 var hours = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();
    var timezone= now.getTimezoneOffset()/60;
 if (timezone == 0)
  { var timeValue = "格林尼治";}
 else 
  { var timeValue = ((timezone < 0)? " 东"+(-1)*timezone : "  西"+timezone) +'区';}

    timeValue  += '时间：'
  +now.getYear()+'年'
  +eval(now.getMonth()+1)+'月'
  +now.getDate()+'日 星期'
  +_day[now.getDay()]+' '+hours;

    timeValue  += ((minutes < 10) ? ":0" : ":") + minutes;
    timeValue  += ((seconds < 10) ? ":0" : ":") + seconds+" ";
    document.clock.face.value = timeValue ;
 timerID = setTimeout("showlocaltime()",1000);
    timerRunning = true;
}


function showareatime(_area)
 {
    var now = new Date();
 var timezone = now.getTimezoneOffset()/60;
 if (_area != Math.round(_area))
  {
  _area = Math.floor(_area);
  var minutes = now.getMinutes()+30;
  if (minutes >=60)
   {
   minutes = minutes%60;
   var hours = now.getHours()+eval(timezone+_area + 1);
   }
  else
   {
   var hours = now.getHours()+eval(timezone+_area) ;
   }
  }
 else
  {
  var hours = now.getHours()+timezone+_area;
  var minutes = now.getMinutes();
  }
 if (hours < 0)
  hours = '昨天'+ eval(24 + hours);
 else
  if (hours < 24 )
   hours = '今天'+hours;
  else
   hours = '明天'+hours%24;

    var seconds = now.getSeconds();
    var timeValue = hours;
    timeValue  += ((minutes < 10) ? ":0" : ":") + minutes;
    timeValue  += ((seconds < 10) ? ":0" : ":") + seconds+" ";
 return timeValue;
 }

function worldtime(_area)
 {
 _address = new Array(28);
 _address[0]='西12区：埃尼威托克岛，夸贾林岛';
 _address[1]='西11区：中途岛，东萨摩亚';
 _address[2]='西10区：夏威夷';
 _address[3]='西9区：阿拉斯加';
 _address[4]='西8区：太平洋时间（美国和加拿大），蒂华纳';
 _address[5]='西7区：山地时间（美国和加拿大），亚利桑那';
 _address[6]='西6区：中部时间（美国和加拿大），墨西哥城，特古西加尔巴，萨斯喀彻温省';
 _address[7]='西5区：东部时间（美国和加拿大），印第安那州（东部），波哥大，利马，基多';
 _address[8]='西4区：大西洋时间（加拿大），加拉加斯，拉巴斯';
 _address[9]='西3区：巴西利亚，布宜诺斯艾利斯，乔治敦';
 _address[10]='西2区：中大西洋';
 _address[11]='西1区：亚速尔群岛，佛得角群岛';
 _address[12]='格林尼治平均时：伦敦，都柏林，爱丁堡，里斯本，卡萨布兰卡，蒙罗维亚';
 _address[13]='东1区：阿姆斯特丹，柏林，伯尔尼，罗马，斯德哥尔摩，维也纳，贝尔格莱德，布拉迪斯拉发，布达佩斯，卢布尔雅那，布拉格，布鲁赛尔，哥本哈根，马德里，巴黎，萨拉热窝，斯科普里，索非亚，华沙，萨格勒布';
 _address[14]='东2区：布加勒斯特，哈拉雷，比勒陀尼亚，赫尔辛基，里加，塔林，开罗，雅典，伊斯坦布尔，明斯克，以色列';
 _address[15]='东3区：巴格达，科威特，利雅得，莫斯科，圣彼得堡，伏尔加格勒，内罗毕';
 _address[16]='东4区：阿布扎比，马斯喀特，巴库，第比利斯';
 _address[17]='东5区：叶卡特琳堡，伊斯兰堡，卡拉奇，塔什干';
 _address[18]='东6区：阿拉木图，达卡，科伦坡';
 _address[19]='东7区：曼谷，河内，雅加达';
 _address[20]='东8区：北京，重庆，广州，上海，香港，乌鲁木齐，台北，新加坡，佩思';
 _address[21]='东9区：平壤，汉城，东京，大阪，札幌，雅库茨克';
 _address[22]='东10区：布里斯班，关岛，莫尔兹比港，霍巴特，堪培拉，墨尔本，悉尼';
 _address[23]='东11区：马加丹，所罗门群岛，新喀里多尼亚';
 _address[24]='东12区：奥克兰，惠灵顿，斐济，堪察加半岛，马绍尔群岛';
 _address[25]='西3:30区：纽芬兰';
 _address[26]='东3:30区：德黑兰';
 _address[27]='东4:30区：喀布尔';  
 _address[28]='东5:30区：孟买，加尔各答，马德拉斯，新德里'; 
 _address[29]='东9:30区：阿得莱德，达尔文';
 
	 
	 var $lv_pop_maptime=$("#lv_pop_maptime");
	 function showmap(maptimestr){
	    $lv_pop_maptime.text(maptimestr);
		$("div.lv_pop").ui("popDiv",{
				backgroundColor : "#000000",
				opacity : 0.5,
				close:"a.lv_pop_btn_yl,div.lv_pop_close"
		});
	    
	 }
	 if (_area == -3.5){
		var maptimestr=showareatime(_area)+_address[25];
		showmap(maptimestr);	
	 };
	 if (_area == 3.5){
		var maptimestr=showareatime(_area)+_address[26];
		showmap(maptimestr);
	 };
	 if (_area == 4.5){
		var maptimestr=showareatime(_area)+_address[27];
		showmap(maptimestr);
	 };
	 if (_area == 5.5) {
		var maptimestr=showareatime(_area)+_address[28];
		showmap(maptimestr);
	 };
	 if (_area == 9.5) {
		var  maptimestr=showareatime(_area)+_address[29];
		showmap(maptimestr);
	 }; 
	 if (_area == Math.round(_area)){
		var  maptimestr=showareatime(_area)+_address[eval(_area+12)];
		showmap(maptimestr);
	 };
 }
//-->
</SCRIPT>




<div class="diff_time">
	<p>世界主要城市与北京时差表</p>
    <p class="sm_info">[说明："+"表示早上北京时间；"-"表示迟于北京时间]</p>
    <p align="center"><em>|</em><span class="add_cu"> 亚洲</span> <em>|</em><a href="#m_1"> 欧洲</a> <em>|</em> <a href="#m_2">美洲 </a> <em>|</em> <a href="#m_3">非洲 </a> <em>|</em> <a href="#m_4">大洋洲</a> <em>|</em></p>
</div>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_border">
  <tr>
    <td width="87%" class="add_border add_cu add_border_align">ASIA 亚洲</td>
    <td width="13%" class="add_border">&nbsp;</td>
  </tr>
    <tr>
    <td width="87%" class="add_border add_border_align add_border_align01">城市</td>
    <td width="13%" class="add_border add_border_align add_border_align01">时差</td>
  </tr>
     <tr>
    <td width="87%" class="add_border add_border_align01 add_border_align012">Abadan 阿巴丹[伊朗](与北京东八区标准时差)</td>
    <td width="13%" class="add_border add_border_align01 add_border_align012">-4:30</td>
  </tr>
     <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Abu Dhabi 阿布扎比[阿联酋] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-4</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Aden 亚丁[也门] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Amman 安曼[约旦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Ankara 安卡拉[土耳其] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Baghdad 巴格达[伊拉克] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Baku 巴库[阿塞拜疆] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bandar Seri Begawan 斯里巴加湾港[文莱] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bangkok 曼谷[泰国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Beirut 贝鲁特[黎巴嫩] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bombay 孟买[印度] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-2:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Calcutta 加尔各答[印度] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-2:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Colombo 科伦坡[斯里兰卡] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-2:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Damascus 大马士革[叙利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Dhaka 达卡[孟加拉] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Djakarta 雅加达[印度尼西亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Guangzhou 广州[中国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Hanoi 河内[越南] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Hong Kong 中国香港 (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Irkutsk 伊尔库次克[俄罗斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Islamabad 伊斯兰堡[巴基斯坦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-3</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Jerusalem 耶路撒冷[巴勒斯坦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Karachi 卡拉奇[巴基斯坦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-3</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Katmandu 加德满都[尼泊尔] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-2:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kuala Lumpur 吉隆坡[马来西亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kuwait 科威特[科威特] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kyoto 京都[日本] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Macao 澳门地区 (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Manila 马尼拉[菲律宾] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Mecca 麦加[沙特] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Nagasaki 长崎[日本] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">New Delhi 新德里[印度] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-2:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Nikolayevsk-on-Amure&nbsp;尼古拉耶夫斯克(庙街)[俄罗斯](与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Omsk 鄂木斯克[俄罗斯] (与北京东八区标准时差)(与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-3</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Osaka 大阪[日本] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Phnom Penh 金边[柬埔寨] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Pyongyang 平壤[朝鲜] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Rangoon 仰光[缅甸] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-1:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Rawalpindi 拉瓦尔品第[巴基斯坦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-3</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Seoul 汉城[南朝鲜] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Shanghai 上海[中国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Singapore 新加坡[新加坡] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Taibei 台北[中国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Tehran 德黑兰[伊朗] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-4:30</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Tokyo 东京[日本] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Ulan Bator 乌兰巴托[蒙古] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Vladivostok 符拉迪沃斯托克(海参崴)[俄罗斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Yokohama 横滨[日本] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+1</td>
              </tr>
       <tr>
    <td colspan="2" class="add_border add_border_align01 add_border_align012">  <a id="m_1" name="m_1"></a></td>
    </tr>

    <tr>
    <td width="87%" class="add_border add_cu add_border_align">EUROPE 欧洲</td>
    <td width="13%" class="add_border add_border_align add_border_align01"><a href="javascript:scroll(0,0)">返回顶部</a></td>
  </tr>
    <tr>
    <td width="87%" class="add_border add_border_align add_border_align01">城市</td>
    <td width="13%" class="add_border add_border_align add_border_align01">时差</td>
  </tr>
        <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Aberdeen 阿伯丁[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Amsterdam 阿姆斯特丹[荷兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Antwerp 安特卫普[比利时] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Athens 雅典[希腊] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Belfast 贝尔法斯特[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Belgrade 贝尔格莱德[南斯拉夫] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Berlin 柏林[德国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Birmingham 伯明翰[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bonn 波恩[德国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Brussels 布鲁塞尔[比利时] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bucharest 布加勒斯特[罗马尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Budapest 布达佩斯[匈牙利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Constantsa 康斯坦萨[罗马尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Copenhagen 哥本哈根[丹麦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Dublin 都柏林[爱尔兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Edinburgh 爱丁堡[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Frankfurt 法兰克福[德国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Gdansk 格但斯克[波兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Geneva 日内瓦[瑞士] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Genoa 热那亚[意大利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Gibraltar 直布罗陀[英国殖民地] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Glasgow 格拉斯哥[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Hamburg 汉堡[德国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Helsinki 赫尔辛基[芬兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Istanbul 伊斯坦布尔[土耳其] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kiev 基辅[乌克兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Leeds 利兹[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Leipzig 莱比锡[德国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Lisbon 里斯本[葡萄牙] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Liverpool 利物浦[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">London 伦敦[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Madrid 马德里[西班牙] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Manchester 曼彻斯特[英国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Marseilles 马塞[法国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Milan 米兰[意大利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Moscow 莫斯科[俄罗斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Munich 慕尼黑[德国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Murmansk 摩尔曼斯克[俄罗斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Odessa 敖德萨[乌克兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Oslo 奥斯陆[挪威] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Paris 巴黎[法国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Prague 布拉格[捷克] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Reykjavik 雷克雅未克[冰岛] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Rome 罗马[意大利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Rotterdam 鹿特丹[荷兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">St. Peterburg 圣彼得堡[俄罗斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Sofia 索非亚[保加利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Stockholm 斯德哥尔摩[瑞典] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Tirana 地拉那[阿尔巴尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Vatican 梵蒂冈[罗马教廷所在地] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Venice 威尼斯[意大利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Vienna 维也纳[奥地利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Warsaw 华沙[波兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Zurich 苏黎世[瑞士] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
         <tr>
    <td colspan="2" class="add_border add_border_align01 add_border_align012">  <a id="m_2" name="m_2"></a></td>
    </tr>
      <tr>
    <td width="87%" class="add_border add_cu add_border_align">AMERICA 美洲</td>
    <td width="13%" class="add_border add_border_align add_border_align01"><a href="javascript:scroll(0,0)">返回顶部</a></td>
  </tr>
    <tr>
    <td width="87%" class="add_border add_border_align add_border_align01">城市</td>
    <td width="13%" class="add_border add_border_align add_border_align01">时差</td>
  </tr>
       <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Anchorage 安克雷奇[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-17</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Asuncion 亚松森[巴拉圭] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-12</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Atlanta 亚特兰大[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Baltimore 巴尔的摩[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Belem 贝伦[巴西] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-11</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bogota 波哥大[哥伦比亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Boston 波士顿[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Brasilia 巴西利亚[巴西] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-11</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Buenos Aires 布宜诺斯艾利斯[阿根廷] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-11</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Caracas 加拉加斯[委内瑞拉] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-12</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Chicago 芝加哥[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Churchill 丘吉尔港[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Colon 科隆[巴拿马] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Dallas 达拉斯[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Denver 丹佛[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-15</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Detroit 底特律[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Edmonton 埃德蒙顿[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-15</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Havana 哈瓦那[古巴] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Houston 休斯敦[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kingston 金斯敦[牙买加] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">La Paz 拉巴斯[玻利维亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-12</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Las Vegas 拉斯韦加斯[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-16</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Lima 利马[秘鲁] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Los Angeles 洛杉矶[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-16</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Mexico City 墨西哥城[墨西哥] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Miami 迈阿密[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Montevideo 蒙得维的亚[乌拉圭] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-11</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Montreal 蒙特利尔[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">New Orleans 新奥尔良[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">New York 纽约[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Ottawa 渥太华[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Panama 巴拿马城[巴拿马] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Paramaribo 帕拉马里博[苏里南] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-11</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Philadelphia 费城[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Pittsburgh 匹兹堡[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Port-of-Spain 西班牙港[特立尼达和多巴哥] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-12</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Quebec 魁北克[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Ria de janeiro 里约热内卢[巴西] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-11</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">saint Louis 圣路易斯[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Salt Lake City 盐湖城[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-15</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">San Diego 圣迭戈[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-16</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">San Francisco 旧金山(圣弗兰西斯科)[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-16</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">San Juan 圣胡安[波多黎各] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-12</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Santiago 圣地亚哥[智利] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-12</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Seattle 西雅图[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-16</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Toronto 多伦多[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Vancouver 温哥华[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-16</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Washington, D.c. 华盛顿[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-13</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Winnipeg 温尼伯[加拿大] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-14</td>
              </tr>
         <tr>
    <td colspan="2" class="add_border add_border_align01 add_border_align012">  <a id="m_3" name="m_3"></a></td>
    </tr>
      <tr>
    <td width="87%" class="add_border add_cu add_border_align">AFRICA 非洲</td>
    <td width="13%" class="add_border add_border_align add_border_align01"><a href="javascript:scroll(0,0)">返回顶部</a></td>
  </tr>
    <tr>
    <td width="87%" class="add_border add_border_align add_border_align01">城市</td>
    <td width="13%" class="add_border add_border_align add_border_align01">时差</td>
  </tr>
      <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Abidjan 阿比让[科特迪瓦] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Addis Ababa 亚的斯亚贝巴[埃塞俄比亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Alexandria 亚历山大[埃及] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Algiers 阿尔及尔[阿尔及利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Bamako 巴马科[马里] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Banghazi 班加西[利比亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Beira 贝拉[莫桑比克] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Brazzaville 布拉柴维尔[刚果] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Cairo 开罗[埃及] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Cape Town 开普敦[南非] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Dakar 达喀尔[塞内加尔] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Dar el Beida 达尔贝达(卡萨布兰卡)[摩洛哥] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Dar es Salaam 达累斯萨拉姆[坦桑尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Djibouti 吉布提[吉布提] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Douala 杜阿拉[喀麦隆] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Durban 德班[南非] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Gaborone 哈博罗内[博茨瓦纳] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Johannesburg 约翰内斯堡[南非] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kampala 坎帕拉[乌干达] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kano 卡诺[尼日利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Khartoum 喀土穆[苏丹] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Kinshasa 金沙萨[民主刚果] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Lagos 拉各斯[尼日利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Las Palmas 拉斯帕耳马斯[加那利群岛] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Luanda 罗安达[安哥拉] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Lusaka 卢萨卡[赞比亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Maputo 马普托[莫桑比克] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Mogadishu 摩加迪沙[索马里] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Mombasa 蒙巴萨[肯尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Monrovia 蒙罗维亚[利比里亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Nairobi 内罗毕[肯尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Ndjamena 恩贾梅纳[乍得] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Niamey 尼亚美[尼日尔] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Nouakchott 努瓦克肖特[毛里塔尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Pointe-Noire 黑角[刚果] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Port Louis 路易港[毛里求斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-4</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Pretoria 比勒陀利亚[南非] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Rabat 拉巴特[摩洛哥] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Sao Tome 圣多美[圣多美和普林西比] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-8</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Tananarive 塔那那利佛[马达加斯加] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-5</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Tripoli 的黎波里[利比亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Tunis 突尼斯[突尼斯] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-7</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Windhoek 温得和克[纳米比亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-6</td>
              </tr>
         <tr>
    <td colspan="2" class="add_border add_border_align01 add_border_align012">  <a id="m_4" name="m_4"></a></td>
    </tr>
      <tr>
    <td width="87%" class="add_border add_cu add_border_align">OCEANIA AND PACIFIC                    LSLANDS<br />                   大洋洲及太平洋岛屿</td>
    <td width="13%" class="add_border add_border_align add_border_align01"><a href="javascript:scroll(0,0)">返回顶部</a></td>
  </tr>
    <tr>
    <td width="87%" class="add_border add_border_align add_border_align01">城市</td>
    <td width="13%" class="add_border add_border_align add_border_align01">时差</td>
  </tr>
        <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Agana 阿加尼亚[关岛] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Apia 阿皮亚[西萨摩亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-19</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Auckland 奥克兰[新西兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+4</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Brisbane 布里斯班[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Canberra 堪培拉[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Fremantle 弗里曼特尔[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Hobart 霍巴特[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Honolulu 火奴鲁鲁(檀香山)[美国] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-18</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Melburne 墨尔本[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Noumea 努美阿[法属新喀里多尼亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+3</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Papeete 帕皮提[法属波利尼西亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">-18</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Perth 佩思[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">0</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Port Moresby 莫尔兹比港[巴布亚新几内亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Suva 苏瓦[斐济] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+4</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Sydney 悉尼[澳大利亚] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+2</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">vila 维拉港[瓦努阿图] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+3</td>
              </tr>
              <tr>
                <td width="87%" class="add_border add_border_align01 add_border_align012">Wellington 惠灵顿[新西兰] (与北京东八区标准时差)</td>
                <td width="13%" class="add_border add_border_align01 add_border_align012">+4</td>
              </tr>
</table>

  </div>
<#include "/WEB-INF/pages/helpCenter/left_aside.ftl"/>
<br class="clear"/>
<!-- 底通 -->
<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('nee5a1b3b8a570b30001','js',null)"/>
<!-- 底通/End -->
<!-- footer start--> 
<#include "/common/footer.ftl"> 
<!-- footer end-->
    
<!--addpop-->
    <link href="http://pic.lvmama.com/styles/global_pop.css" rel="stylesheet" type="text/css" />
    <div class="lv_pop lv_pop_w1">
    	<div class="lv_pop_inner">
              <div class="lv_pop_close"></div>
              <div class="lv_pop_tit1">提示</div>
              <p class="lv_pop_cont" id="lv_pop_maptime">
                 
              </p>
              <p class="lv_pop_btnbox"><a href="javascript:void()" class="lv_pop_btn_yl">确定</a></p>
        </div>
    </div>
    <div id="pageOver"></div>
<!--addpop-->
</div>

<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js?r=9940"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js?r=8673"></script>
	<script>
		cmCreatePageviewTag("旅游百宝箱-国际时差对照", "E1002", null, null);
	</script>
</body>
</html>
