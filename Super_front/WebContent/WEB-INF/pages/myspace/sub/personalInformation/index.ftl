<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的信息-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-userinfo">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap"><p><a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; <a class="current">我的信息</a></p></div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 个人资料 -->
				<div id="tabs" class="ui-box mod-edit userinfo-edit">
					<div class="ui-tab-title"><h3>个人资料</h3>
						<ul class="tab-nav hor"><li><a href="#tabs-1">个人资料</a></li><!--li><a href="#tabs-2">修改头像</a></li--></ul>
					</div>
					<div id="tabs-1" class="ui-tab-box">
						<!-- 个人信息 -->
						<div class="edit-box clearfix userinfo-edit-box">
							<div class="edit-inbox p_rel">
							    <@s.if test='!"Y".equals(user.isEmailChecked) && !"F".equals(user.isEmailChecked)'>
							    <div class="minitip lv-right tooltip bind-email"><div class="cont"></div><div class="pointy-bordr"></div><div class="pointy"></div>绑定邮箱后，可以用邮箱登录。绑定邮箱成功可获得300积分。</div>
							    </@s.if>
							    <@s.if test='!"Y".equals(user.isMobileChecked) && !"F".equals(user.isMobileChecked)'>
							    <div class="minitip lv-right tooltip bind-phone"><div class="cont"></div><div class="pointy-bordr"></div><div class="pointy"></div>绑定手机后，可以用手机号码登录。绑定手机成功可获得300积分。</div>
							    </@s.if>
							    <p><label><span>*</span>用户名：</label><span class="u-info"><@s.property value="user.userName" /></span>
							    <@s.if test='null == user.nameIsUpdate || !"Y".equals(user.nameIsUpdate)'>
								    <@s.if test='user.isMobileChecked == "Y" && user.isEmailChecked == "Y"'>
									<a class="link-edit" href="/myspace/userinfo/userName.do">修改</a>　<i class="lv-cc">(用户名只能修改一次)</i>
								    </@s.if>
								    <@s.else>
									<a class="pop link-edit">修改</a>　<i class="lv-cc">(用户名只能修改一次)</i>					
								    </@s.else>
							    </@s.if>
							    </p>

							    <@s.if test="null == user.email">
								<p><label><span>*</span>Email：</label><i class="lv-cc">未绑定</i>　<a href="/myspace/userinfo/email_bind.do">立即绑定</a><span class="tips-ico03 tips-show ico-bind-email"></span></p>
							    </@s.if>
							    <@s.if test='null != user.email && !"Y".equals(user.isEmailChecked)'>
								 <p>
								 	<label><span>*</span>Email：</label><span class="u-info"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></span>
								 	<a href="/myspace/userinfo/email_send.do">立即验证</a>　
								 	<span class="tips-ico03 tips-show ico-bind-email"></span>
								 </p>
							    </@s.if>                             
							    <@s.if test='null != user.email && "Y".equals(user.isEmailChecked)'>
								 <p><label><span>*</span>Email：</label><span class="u-info"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></span><a href="/myspace/userinfo/email_bind.do">修改</a> <a href="/myspace/userinfo/email_delete.do">解绑</a> <i class="lv-cc">已验证</i></p>
							    </@s.if>

							    <@s.if test="null == user.mobileNumber">
								<p><label><span>*</span>手机号码：</label><i class="lv-cc">未绑定</i>　<a href="/myspace/userinfo/phone.do">立即绑定</a>　<span class="tips-ico03 tips-show ico-bind-phone"></span></p>
							    </@s.if>
							    <@s.if test='null != user.mobileNumber && !"Y".equals(user.isMobileChecked)'>
								<p><label><span>*</span>手机号码：</label><span class="u-info"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span>
								<a href="/myspace/userinfo/phone.do">立即验证</a>　
								<span class="tips-ico03 tips-show ico-bind-phone"></span></p>
							    </@s.if>
							    <@s.if test='null != user.mobileNumber && "Y".equals(user.isMobileChecked)'>
								<p><label><span>*</span>手机号码：</label><span class="u-info"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span><a href="/myspace/userinfo/phone.do">修改</a> <a href="/myspace/userinfo/phone_delete.do">解绑</a> <i class="lv-cc">已验证</i></p>
							    </@s.if>
							    <form action="myspace/updatePersonalInformation.do" method="post" id="personInformationForm">
								    <p><label>真实姓名：</label><input type="text" name="realName" value="<@s.property value="user.realName" />" maxlength="20" class="input-text input-uname"/></p>
								    <p class="clearfix"><label>性别：</label>
								    <label class="sex"><input name="gender" type="radio" value="M" <@s.if test='"M".equals(user.gender)'>checked</@s.if> >男</label>
								    <label class="sex"><input name="gender" type="radio" value="F" <@s.if test='"F".equals(user.gender)'>checked</@s.if> >女</label></p>
								    <p><label>生日：</label><select class="lv-select s-wa" name="year"><@s.property value="year"/>-<@s.property value="month"/>-<@s.property value="day"/>
								    <option value="">年</option>
									<option value="1900" <@s.if test='"1900".equals(year)'>selected</@s.if>>1900</option>
									<option value="1901" <@s.if test='"1901".equals(year)'>selected</@s.if>>1901</option>
									<option value="1902" <@s.if test='"1902".equals(year)'>selected</@s.if>>1902</option>
									<option value="1903" <@s.if test='"1903".equals(year)'>selected</@s.if>>1903</option>
									<option value="1904" <@s.if test='"1904".equals(year)'>selected</@s.if>>1904</option>
									<option value="1905" <@s.if test='"1905".equals(year)'>selected</@s.if>>1905</option>
									<option value="1906" <@s.if test='"1906".equals(year)'>selected</@s.if>>1906</option>
									<option value="1907" <@s.if test='"1907".equals(year)'>selected</@s.if>>1907</option>
									<option value="1908" <@s.if test='"1908".equals(year)'>selected</@s.if>>1908</option>
									<option value="1909" <@s.if test='"1909".equals(year)'>selected</@s.if>>1909</option>
									<option value="1910" <@s.if test='"1910".equals(year)'>selected</@s.if>>1910</option>
									<option value="1911" <@s.if test='"1911".equals(year)'>selected</@s.if>>1911</option>
									<option value="1912" <@s.if test='"1912".equals(year)'>selected</@s.if>>1912</option>
									<option value="1913" <@s.if test='"1913".equals(year)'>selected</@s.if>>1913</option>
									<option value="1914" <@s.if test='"1914".equals(year)'>selected</@s.if>>1914</option>
									<option value="1915" <@s.if test='"1915".equals(year)'>selected</@s.if>>1915</option>
									<option value="1916" <@s.if test='"1916".equals(year)'>selected</@s.if>>1916</option>
									<option value="1917" <@s.if test='"1917".equals(year)'>selected</@s.if>>1917</option>
									<option value="1918" <@s.if test='"1918".equals(year)'>selected</@s.if>>1918</option>
									<option value="1919" <@s.if test='"1919".equals(year)'>selected</@s.if>>1919</option>
									<option value="1920" <@s.if test='"1920".equals(year)'>selected</@s.if>>1920</option>
									<option value="1921" <@s.if test='"1921".equals(year)'>selected</@s.if>>1921</option>
									<option value="1922" <@s.if test='"1922".equals(year)'>selected</@s.if>>1922</option>
									<option value="1923" <@s.if test='"1923".equals(year)'>selected</@s.if>>1923</option>
									<option value="1924" <@s.if test='"1924".equals(year)'>selected</@s.if>>1924</option>
									<option value="1925" <@s.if test='"1925".equals(year)'>selected</@s.if>>1925</option>
									<option value="1926" <@s.if test='"1926".equals(year)'>selected</@s.if>>1926</option>
									<option value="1927" <@s.if test='"1927".equals(year)'>selected</@s.if>>1927</option>
									<option value="1928" <@s.if test='"1928".equals(year)'>selected</@s.if>>1928</option>
									<option value="1929" <@s.if test='"1929".equals(year)'>selected</@s.if>>1929</option>
									<option value="1930" <@s.if test='"1930".equals(year)'>selected</@s.if>>1930</option>
									<option value="1931" <@s.if test='"1931".equals(year)'>selected</@s.if>>1931</option>
									<option value="1932" <@s.if test='"1932".equals(year)'>selected</@s.if>>1932</option>
									<option value="1933" <@s.if test='"1933".equals(year)'>selected</@s.if>>1933</option>
									<option value="1934" <@s.if test='"1934".equals(year)'>selected</@s.if>>1934</option>
									<option value="1935" <@s.if test='"1935".equals(year)'>selected</@s.if>>1935</option>
									<option value="1936" <@s.if test='"1936".equals(year)'>selected</@s.if>>1936</option>
									<option value="1937" <@s.if test='"1937".equals(year)'>selected</@s.if>>1937</option>
									<option value="1938" <@s.if test='"1938".equals(year)'>selected</@s.if>>1938</option>
									<option value="1939" <@s.if test='"1939".equals(year)'>selected</@s.if>>1939</option>
									<option value="1940" <@s.if test='"1940".equals(year)'>selected</@s.if>>1940</option>
									<option value="1941" <@s.if test='"1941".equals(year)'>selected</@s.if>>1941</option>
									<option value="1942" <@s.if test='"1942".equals(year)'>selected</@s.if>>1942</option>
									<option value="1943" <@s.if test='"1943".equals(year)'>selected</@s.if>>1943</option>
									<option value="1944" <@s.if test='"1944".equals(year)'>selected</@s.if>>1944</option>
									<option value="1945" <@s.if test='"1945".equals(year)'>selected</@s.if>>1945</option>
									<option value="1946" <@s.if test='"1946".equals(year)'>selected</@s.if>>1946</option>
									<option value="1947" <@s.if test='"1947".equals(year)'>selected</@s.if>>1947</option>
									<option value="1948" <@s.if test='"1948".equals(year)'>selected</@s.if>>1948</option>
									<option value="1949" <@s.if test='"1949".equals(year)'>selected</@s.if>>1949</option>
									<option value="1950" <@s.if test='"1950".equals(year)'>selected</@s.if>>1950</option>
									<option value="1951" <@s.if test='"1951".equals(year)'>selected</@s.if>>1951</option>
									<option value="1952" <@s.if test='"1952".equals(year)'>selected</@s.if>>1952</option>
									<option value="1953" <@s.if test='"1953".equals(year)'>selected</@s.if>>1953</option>
									<option value="1954" <@s.if test='"1954".equals(year)'>selected</@s.if>>1954</option>
									<option value="1955" <@s.if test='"1955".equals(year)'>selected</@s.if>>1955</option>
									<option value="1956" <@s.if test='"1956".equals(year)'>selected</@s.if>>1956</option>
									<option value="1957" <@s.if test='"1957".equals(year)'>selected</@s.if>>1957</option>
									<option value="1958" <@s.if test='"1958".equals(year)'>selected</@s.if>>1958</option>
									<option value="1959" <@s.if test='"1959".equals(year)'>selected</@s.if>>1959</option>
									<option value="1960" <@s.if test='"1960".equals(year)'>selected</@s.if>>1960</option>
									<option value="1961" <@s.if test='"1961".equals(year)'>selected</@s.if>>1961</option>
									<option value="1962" <@s.if test='"1962".equals(year)'>selected</@s.if>>1962</option>
									<option value="1963" <@s.if test='"1963".equals(year)'>selected</@s.if>>1963</option>
									<option value="1964" <@s.if test='"1964".equals(year)'>selected</@s.if>>1964</option>
									<option value="1965" <@s.if test='"1965".equals(year)'>selected</@s.if>>1965</option>
									<option value="1966" <@s.if test='"1966".equals(year)'>selected</@s.if>>1966</option>
									<option value="1967" <@s.if test='"1967".equals(year)'>selected</@s.if>>1967</option>
									<option value="1968" <@s.if test='"1968".equals(year)'>selected</@s.if>>1968</option>
									<option value="1969" <@s.if test='"1969".equals(year)'>selected</@s.if>>1969</option>
									<option value="1970" <@s.if test='"1970".equals(year)'>selected</@s.if>>1970</option>
									<option value="1971" <@s.if test='"1971".equals(year)'>selected</@s.if>>1971</option>
									<option value="1972" <@s.if test='"1972".equals(year)'>selected</@s.if>>1972</option>
									<option value="1973" <@s.if test='"1973".equals(year)'>selected</@s.if>>1973</option>
									<option value="1974" <@s.if test='"1974".equals(year)'>selected</@s.if>>1974</option>
									<option value="1975" <@s.if test='"1975".equals(year)'>selected</@s.if>>1975</option>
									<option value="1976" <@s.if test='"1976".equals(year)'>selected</@s.if>>1976</option>
									<option value="1977" <@s.if test='"1977".equals(year)'>selected</@s.if>>1977</option>
									<option value="1978" <@s.if test='"1978".equals(year)'>selected</@s.if>>1978</option>
									<option value="1979" <@s.if test='"1979".equals(year)'>selected</@s.if>>1979</option>
									<option value="1980" <@s.if test='"1980".equals(year)'>selected</@s.if>>1980</option>
									<option value="1981" <@s.if test='"1981".equals(year)'>selected</@s.if>>1981</option>
									<option value="1982" <@s.if test='"1982".equals(year)'>selected</@s.if>>1982</option>
									<option value="1983" <@s.if test='"1983".equals(year)'>selected</@s.if>>1983</option>
									<option value="1984" <@s.if test='"1984".equals(year)'>selected</@s.if>>1984</option>
									<option value="1985" <@s.if test='"1985".equals(year)'>selected</@s.if>>1985</option>
									<option value="1986" <@s.if test='"1986".equals(year)'>selected</@s.if>>1986</option>
									<option value="1987" <@s.if test='"1987".equals(year)'>selected</@s.if>>1987</option>
									<option value="1988" <@s.if test='"1988".equals(year)'>selected</@s.if>>1988</option>
									<option value="1989" <@s.if test='"1989".equals(year)'>selected</@s.if>>1989</option>
									<option value="1990" <@s.if test='"1990".equals(year)'>selected</@s.if>>1990</option>
									<option value="1991" <@s.if test='"1991".equals(year)'>selected</@s.if>>1991</option>
									<option value="1992" <@s.if test='"1992".equals(year)'>selected</@s.if>>1992</option>
									<option value="1993" <@s.if test='"1993".equals(year)'>selected</@s.if>>1993</option>
									<option value="1994" <@s.if test='"1994".equals(year)'>selected</@s.if>>1994</option>
									<option value="1995" <@s.if test='"1995".equals(year)'>selected</@s.if>>1995</option>
									<option value="1996" <@s.if test='"1996".equals(year)'>selected</@s.if>>1996</option>
									<option value="1997" <@s.if test='"1997".equals(year)'>selected</@s.if>>1997</option>
									<option value="1998" <@s.if test='"1998".equals(year)'>selected</@s.if>>1998</option>
									<option value="1999" <@s.if test='"1999".equals(year)'>selected</@s.if>>1999</option>
									<option value="2000" <@s.if test='"2000".equals(year)'>selected</@s.if>>2000</option>
									<option value="2001" <@s.if test='"2001".equals(year)'>selected</@s.if>>2001</option>
									<option value="2002" <@s.if test='"2002".equals(year)'>selected</@s.if>>2002</option>
									<option value="2003" <@s.if test='"2003".equals(year)'>selected</@s.if>>2003</option>
									<option value="2004" <@s.if test='"2004".equals(year)'>selected</@s.if>>2004</option>
									<option value="2005" <@s.if test='"2005".equals(year)'>selected</@s.if>>2005</option>
									<option value="2006" <@s.if test='"2006".equals(year)'>selected</@s.if>>2006</option>
									<option value="2007" <@s.if test='"2007".equals(year)'>selected</@s.if>>2007</option>
									<option value="2008" <@s.if test='"2008".equals(year)'>selected</@s.if>>2008</option>
									<option value="2009" <@s.if test='"2009".equals(year)'>selected</@s.if>>2009</option>
									<option value="2010" <@s.if test='"2010".equals(year)'>selected</@s.if>>2010</option>
									<option value="2011" <@s.if test='"2011".equals(year)'>selected</@s.if>>2011</option>
									<option value="2012" <@s.if test='"2012".equals(year)'>selected</@s.if>>2012</option>
									<option value="2013" <@s.if test='"2013".equals(year)'>selected</@s.if>>2013</option>
									<option value="2014" <@s.if test='"2014".equals(year)'>selected</@s.if>>2014</option>
									<option value="2015" <@s.if test='"2015".equals(year)'>selected</@s.if>>2015</option>
									<option value="2016" <@s.if test='"2016".equals(year)'>selected</@s.if>>2016</option>
									<option value="2017" <@s.if test='"2017".equals(year)'>selected</@s.if>>2017</option>
									<option value="2018" <@s.if test='"2018".equals(year)'>selected</@s.if>>2018</option>
									<option value="2019" <@s.if test='"2019".equals(year)'>selected</@s.if>>2019</option>
									<option value="2020" <@s.if test='"2020".equals(year)'>selected</@s.if>>2020</option>
									<option value="2021" <@s.if test='"2021".equals(year)'>selected</@s.if>>2021</option>
									<option value="2022" <@s.if test='"2022".equals(year)'>selected</@s.if>>2022</option>
									<option value="2023" <@s.if test='"2023".equals(year)'>selected</@s.if>>2023</option>
									<option value="2024" <@s.if test='"2024".equals(year)'>selected</@s.if>>2024</option>
									<option value="2025" <@s.if test='"2025".equals(year)'>selected</@s.if>>2025</option>
									<option value="2026" <@s.if test='"2026".equals(year)'>selected</@s.if>>2026</option>
									<option value="2027" <@s.if test='"2027".equals(year)'>selected</@s.if>>2027</option>
									<option value="2028" <@s.if test='"2028".equals(year)'>selected</@s.if>>2028</option>
									<option value="2029" <@s.if test='"2029".equals(year)'>selected</@s.if>>2029</option>
									<option value="2030" <@s.if test='"2030".equals(year)'>selected</@s.if>>2030</option>
								    </select><select class="lv-select s-wc" name="month">
								    <option value="">月</option>
									<option value="1" <@s.if test='"01".equals(month)'>selected</@s.if>>1</option>
									<option value="2" <@s.if test='"02".equals(month)'>selected</@s.if>>2</option>
									<option value="3" <@s.if test='"03".equals(month)'>selected</@s.if>>3</option>
									<option value="4" <@s.if test='"04".equals(month)'>selected</@s.if>>4</option>
									<option value="5" <@s.if test='"05".equals(month)'>selected</@s.if>>5</option>
									<option value="6" <@s.if test='"06".equals(month)'>selected</@s.if>>6</option>
									<option value="7" <@s.if test='"07".equals(month)'>selected</@s.if>>7</option>
									<option value="8" <@s.if test='"08".equals(month)'>selected</@s.if>>8</option>
									<option value="9" <@s.if test='"09".equals(month)'>selected</@s.if>>9</option>
									<option value="10" <@s.if test='"10".equals(month)'>selected</@s.if>>10</option>
									<option value="11" <@s.if test='"11".equals(month)'>selected</@s.if>>11</option>
									<option value="12" <@s.if test='"12".equals(month)'>selected</@s.if>>12</option>
								    </select><select class="lv-select s-wc" name="day">
								    <option value="">日</option>
									<option value="1" <@s.if test='"01".equals(day)'>selected</@s.if>>1</option>
									<option value="2" <@s.if test='"02".equals(day)'>selected</@s.if>>2</option>
									<option value="3" <@s.if test='"03".equals(day)'>selected</@s.if>>3</option>
									<option value="4" <@s.if test='"04".equals(day)'>selected</@s.if>>4</option>
									<option value="5" <@s.if test='"05".equals(day)'>selected</@s.if>>5</option>
									<option value="6" <@s.if test='"06".equals(day)'>selected</@s.if>>6</option>
									<option value="7" <@s.if test='"07".equals(day)'>selected</@s.if>>7</option>
									<option value="8" <@s.if test='"08".equals(day)'>selected</@s.if>>8</option>
									<option value="9" <@s.if test='"09".equals(day)'>selected</@s.if>>9</option>
									<option value="10" <@s.if test='"10".equals(day)'>selected</@s.if>>10</option>
									<option value="11" <@s.if test='"11".equals(day)'>selected</@s.if>>11</option>
									<option value="12" <@s.if test='"12".equals(day)'>selected</@s.if>>12</option>
									<option value="13" <@s.if test='"13".equals(day)'>selected</@s.if>>13</option>
									<option value="14" <@s.if test='"14".equals(day)'>selected</@s.if>>14</option>
									<option value="15" <@s.if test='"15".equals(day)'>selected</@s.if>>15</option>
									<option value="16" <@s.if test='"16".equals(day)'>selected</@s.if>>16</option>
									<option value="17" <@s.if test='"17".equals(day)'>selected</@s.if>>17</option>
									<option value="18" <@s.if test='"18".equals(day)'>selected</@s.if>>18</option>
									<option value="19" <@s.if test='"19".equals(day)'>selected</@s.if>>19</option>
									<option value="20" <@s.if test='"20".equals(day)'>selected</@s.if>>20</option>
									<option value="21" <@s.if test='"21".equals(day)'>selected</@s.if>>21</option>
									<option value="22" <@s.if test='"22".equals(day)'>selected</@s.if>>22</option>
									<option value="23" <@s.if test='"23".equals(day)'>selected</@s.if>>23</option>
									<option value="24" <@s.if test='"24".equals(day)'>selected</@s.if>>24</option>
									<option value="25" <@s.if test='"25".equals(day)'>selected</@s.if>>25</option>
									<option value="26" <@s.if test='"26".equals(day)'>selected</@s.if>>26</option>
									<option value="27" <@s.if test='"27".equals(day)'>selected</@s.if>>27</option>
									<option value="28" <@s.if test='"28".equals(day)'>selected</@s.if>>28</option>
									<option value="29" <@s.if test='"29".equals(day)'>selected</@s.if>>29</option>
									<option value="30" <@s.if test='"30".equals(day)'>selected</@s.if>>30</option>
									<option value="31" <@s.if test='"31".equals(day)'>selected</@s.if>>31</option>
								    </select></p>
								    <p><label>所在地：</label><select id="provinceId" class="lv-select s-wa" onChange="updateCities(this.value)">
									<option value ="110000" <@s.if test='"110000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >北京市</option>
									<option value ="120000" <@s.if test='"120000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >天津市</option>
									<option value ="130000" <@s.if test='"130000".equals("<@s.property value="provinceId"/>")'>selected</@s.if> >河北省</option>
									<option value ="140000" <@s.if test='"140000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >山西省</option>
									<option value ="150000" <@s.if test='"150000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >内蒙古</option>
									<option value ="210000" <@s.if test='"210000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >辽宁省</option>
									<option value ="220000" <@s.if test='"220000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >吉林省</option>
									<option value ="230000" <@s.if test='"230000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >黑龙江省</option>
									<option value ="310000" <@s.if test='"310000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >上海市</option>
									<option value ="320000" <@s.if test='"320000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >江苏省</option>
									<option value ="330000" <@s.if test='"330000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >浙江省</option>
									<option value ="340000" <@s.if test='"340000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >安徽省</option>
									<option value ="350000" <@s.if test='"350000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >福建省</option>
									<option value ="360000" <@s.if test='"360000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >江西省</option>
									<option value ="370000" <@s.if test='"370000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >山东省</option>
									<option value ="410000" <@s.if test='"410000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >河南省</option>
									<option value ="420000" <@s.if test='"420000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >湖北省</option>
									<option value ="430000" <@s.if test='"430000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >湖南省</option>
									<option value ="440000" <@s.if test='"440000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >广东省</option>
									<option value ="450000" <@s.if test='"450000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >广西省 </option>
									<option value ="460000" <@s.if test='"460000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >海南省</option>
									<option value ="500000" <@s.if test='"500000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >重庆市</option>
									<option value ="510000" <@s.if test='"510000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >四川省</option>
									<option value ="520000" <@s.if test='"520000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >贵州省</option>
									<option value ="530000" <@s.if test='"530000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >云南省</option>
									<option value ="540000" <@s.if test='"540000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >西藏</option>
									<option value ="610000" <@s.if test='"610000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >陕西省</option>
									<option value ="620000" <@s.if test='"620000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >甘肃省</option>
									<option value ="630000" <@s.if test='"630000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >青海省</option>
									<option value ="640000" <@s.if test='"640000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >宁夏</option>
									<option value ="650000" <@s.if test='"650000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >新疆</option>
									<option value ="F10000" <@s.if test='"F10000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >香港</option>
									<option value ="F20000" <@s.if test='"F20000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >澳门</option>
									<option value ="F30000" <@s.if test='"F30000".equals(<@s.property value="provinceId"/>)'>selected</@s.if> >台湾</option>
								    </select><select class="lv-select s-wb" id="cityId" name="cityId">
								    </select></p>
								    <p><a class="ui-btn ui-button" onClick="submitForm()"><i>&nbsp;保 存&nbsp;</i></a></p>
							    </form>
							</div>
						</div>
						<!-- 个人信息 -->
					</div>
					<!--div id="tabs-2" class="ui-tab-box photo-edit">
						
						<div class="edit-box clearfix">
							<div class="edit-inbox">
							<p class="up-photo"><span class="ui-btn btn-up-photo"></span><input type="file" exts="jpg|bmp" id="selectFile" class="file" name="file"></p>
							<div class="view-photo">
							<p>仅支持JPG、GIF、PNG图片文件，且文件小于500KB</p>
							<div class="show-photo"></div>
							</div>
							<div class="view-photo view-photo-big">
							<p>您上传的头像会自动生成两种尺寸，<br>请注意小尺寸的头像是否清晰</p>
							<div class="show-photo"></div>
							<p>大尺寸头像，130×130像素</p>
							</div>
							<div class="view-photo view-photo-small">
							<div class="show-photo"></div>
							<p>小尺寸头像<br>76×76像素</p>
							</div>
							<div class="hr_b"></div>
							<p><a class="ui-btn ui-button"><i>&nbsp;保 存&nbsp;</i></a>　　　<a href="" class="edit-cancel">取消</a></p>
						    </div>
						</div-->
						
				</div>				
				<!-- 个人资料 -->
			</div>
		</div>

		<div class="lv_pop ie6png  mylv_ckzh_pop">
		    <div class="lv_pop_inner">
		     <div class="lv_pop_close"></div>
		     <div class="lv_pop_tit">提示</div>
		     <div class="lv_pop_cont">
			  <p class="lv_pop_p">您还未验证邮箱和手机，请先去绑定。</p>
		     </div>
		     <div class="lv_pop_btn">
			   <@s.if test='user.isEmailChecked != "Y"'><a href="/myspace/userinfo/email_bind.do" class="mylv_ckzh_btn">验证邮箱</a></@s.if>
			   <@s.if test='user.isMobileChecked != "Y"'><a href="/myspace/userinfo/phone.do" class="mylv_ckzh_btn">验证手机</a></@s.if>
		     </div> 
		    </div>     
		</div>
		<div id="pageOver"></div>

	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	
<script type="text/javascript">
	function updateCities(value){
		$("#cityId").empty();
			
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
			if (document.getElementById("cityId").options[i].value == '<@s.property value="user.cityId"/>')                
			 	document.getElementById("cityId").options[i].selected ="true"; 
		}
	}


	function submitForm(){
		$("#personInformationForm").submit();
	}

	for(var i = 0; i < document.getElementById("provinceId").options.length;i++) {  
		if (document.getElementById("provinceId").options[i].value == '<@s.property value="provinceId"/>')                
			document.getElementById("provinceId").options[i].selected ="true"; 
	}

	updateCities("<@s.property value="provinceId"/>");

	function sucshow(showPop,evtobj,black_bg,selfevt){
		   $(evtobj).live("click",function(){
				   var evt_index=$(this).index();
				   var w_scroll =parseInt(document.body.offsetWidth/2);
				   var w_object =parseInt($(showPop).width()/2);
				   var e_obj_top=$(window).scrollTop()+$(window).height()/2-$(showPop).height()/2;
				   if(e_obj_top<$(window).scrollTop()){
					  e_obj_top=$(window).scrollTop();
				   }
				   var l_obj =w_scroll-w_object;
				   var t_obj =e_obj_top;
				   $(showPop).css({"left":l_obj,"top":t_obj,"margin":"auto"});
			   $(showPop).show();
			   var dh=document.body.scrollHeight;
			   var wh=window.screen.availHeight;
			   var yScroll;
			   dh>wh?yScroll =dh:yScroll = wh;
			   $(black_bg).css("height", yScroll);
			   $(black_bg).show();
		   })
	}//弹出层


	 $(".pop").each(function(){
	    sucshow(".mylv_ckzh_pop",".pop","#pageOver");//弹出框对象，触发对象，蒙层
	 })
	 close_evt(".lv_pop_close","#pageOver",".mylv_ckzh_pop");//关闭弹出层
	function close_evt(close_btn,black_bg,popdiv){	      
			$(close_btn).bind("click",function(){
			$(this).parents(popdiv).hide();
			$(black_bg).hide();
		 });
	}//弹出层关闭

</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
<script>
	cmCreatePageviewTag("个人资料", "D1003", null, null);
</script>
</body>
</html>
