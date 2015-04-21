<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>html地图管理</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css">
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/seo/siteMapHtml_map.js" language="javascript"></script>
</head>

	<body>
		<br/>
		<p id="siteMap_div" style="color:red"></p>
		<div class="menus_main xml_main">
			<form action="<%=basePath%>/seo/single/generateSiteMap.do" method="post" onsubmit="return chkSiteMap()">
				<table cellspacing="0" cellpadding="0" class="gl_table js_sz_table">
				<tbody>
					<tr class="menus_bg">
						<th colspan="3" align="center" class="menus_list">
							生成Sitemaps
						</th>
					</tr>
					<tr>
						<td width="68" height="38" align="center" class="menus_a">
							更新频率
						</td>
						<td width="53" height="38" class="menus_a">
							<select name="frequency">
								<option value="0.1">
									0.1
								</option>
								<option value="0.2">
									0.2
								</option>
								<option value="0.3">
									0.3
								</option>
								<option value="0.4">
									0.4
								</option>
								<option value="0.5">
									0.5
								</option>
								<option value="0.6">
									0.6
								</option>
								<option value="0.7">
									0.7
								</option>
								<option value="0.8">
									0.8
								</option>
								<option value="0.9">
									0.9
								</option>
								<option value="1.0">
									1.0
								</option>
							</select>
						</td>
						<td width="652" height="38" class="menus_a">
							周期
							<select name="cycle">
								<option value="always">
									always
								</option>
								<option value="hourly">
									hourly
								</option>
								<option value="daily">
									daily
								</option>
								<option value="weekly">
									weekly
								</option>
								<option value="mothly">
									mothly
								</option>
								<option value="yearly">
									yearly
								</option>
								<option value="never">
									never
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="68" height="38" align="center" class="menus_a">
							Xml数量:
						</td>
						<td height="38" colspan="2" class="menus_a">
							<input type="text" name="xmlQuantity" id="xmlQuantity" value="3000" class="form_add1" />
						</td>
					</tr>
					<tr>
					<td height="50" colspan="3" align="left" valign="middle">
						<input name="siteMap_sub" id="siteMap_sub" style="width:99px;" type="submit" value="确定"  />
						<a href="<%=basePath%>/seo/single/synSiteMap.do"> 同步站点地图 </a>
					</td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</body>
</html>
