<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>html地图管理</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css">
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/seo/siteMapHtml_menu.js"></script>
<script type="text/javascript">
 var basePath='<%=basePath%>';
</script>
</head>

<body>
		<div class="menus_main">
			<form action="<%=basePath%>/seo/updateMainMapSeq.do" method="post" onsubmit="return chk_seq()">
				<input type="hidden" name="htmlMenuId" value="${htmlMenuId}"/>
				<input type="hidden" name="returnUrl" value="jumpMenuChildDo"/>
<table>
<tbody>
	 <tr>
	  <th colspan="4" align="left" height="50" align="left" valign="bottom">
	    <input type="submit" value="保存排序" style="width:99px"/> <b>&nbsp;【<a href="javascript:void(0);" class="add_menus" onclick="openWin('add_menu');">新建</a>】</b>
	    <a href="<%=basePath%>/seo/jumpMenuIndex.do" class="add_menus" >返回</a>
	  </th>
	 </tr>
</tbody>
</table>
				<table cellspacing="0" cellpadding="0" class="gl_table js_sz_table p_table">
					<tr class="menus_bg">
						<th width="10%" align="center" class="menus_list">
						</th>
						<th width="10%" align="center" class="menus_list">
							排序
						</th>
						<th width="40%" class="menus_list">
							子菜单名称
						</th>
						<th width="35%" class="menus_list">
							菜单URL
						</th>
						<th  class="menus_list">
							操作 
						</th>
					</tr>
					<s:iterator value="childMapMenuList">
						<tr>
							<td><input type="checkbox" name="mapMenuSeq_Id" value="${seoSiteMapHtmlId}"/></td>
							<td height="38" align="center" class="menus_a">
								<input type="hidden" name="mapMenuId" value="${seoSiteMapHtmlId}" />
								<input type="text" name="mapMenuSeq" value="${seq}" class="p_num" />
							</td>
							<td height="38" class="menus_a">
								${htmlMenuName}
							</td>
							<td height="38" class="menus_a">
								${htmlMenuUrl}
							</td>
							<td height="38" class="menus_a">
								<a href="javascript:void(0);" onClick="editMainMenu('${seoSiteMapHtmlId}','${htmlMenuName}','${htmlMenuUrl}','${seq}')" class="other_menus1">修改</a>
								<a href="javascript:void(0);" onClick="delMainMenu(${seoSiteMapHtmlId})" class="other_menus2">删除</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</form>
		</div>

	<div class="js_zs js_cl_all" id="add_menu">
		<h3>
			<a class="close" href="javascript:void(0);" onClick="closeWin('add_menu')">X</a>添加子菜单
		</h3>
		<div class="tab_ztxx_all">
			<form action="addChildMapMenu.do" method="post"
				onsubmit="return chkChildAddValue()">
				<input type="hidden" name="htmlMenuId" value="${htmlMenuId}" />
				<div class="add_cont">
					<table cellspacing="0" cellpadding="0" class="gl_table js_sz_table">
						<tr>
							<td width="24%" height="50" align="center" class="menus_a">
								<label> 上级菜单： </label>
							</td>
							<td width="38%" height="50" class="menus_a"><s:select
									list="mapMenuNameList" name="seoSiteMapHtml.seoSiteMapHtmlId"
									listKey="htmlMenuId" listValue="htmlMenuName" headerKey=""
									headerValue="--请选择--" id="ipt_add_menuId"
									onchange="clean_ipt('div_add_menuId')">
								</s:select></td>
							<td width="38%" height="50" class="menus_a">
								<div id="div_add_menuId"></div>
							</td>
						</tr>
						<tr>
							<td width="24%" height="50" align="center" class="menus_a">
								<label> 子菜单名称： </label>
							</td>
							<td width="38%" height="50" class="menus_a"><input
								type="text" name="seoSiteMapHtml.htmlMenuName"
								id="ipt_add_menuName" class="form_add"
								onChange="clean_ipt('div_add_menuName')" /></td>
							<td width="38%" height="50" class="menus_a">
								<div id="div_add_menuName"></div>
							</td>
						</tr>
						<tr>
							<td height="50" align="center" class="menus_a"><label>
									子菜单链接： </label></td>
							<td height="50" class="menus_a"><input type="text"
								name="seoSiteMapHtml.htmlMenuUrl" id="ipt_add_menuUrl"
								class="form_add" onChange="clean_ipt('div_add_menuUrl')" /></td>
							<td height="50" class="menus_a">
								<div id="div_add_menuUrl"></div>
							</td>
						</tr>
						<tr>
							<td height="50" align="center" class="menus_a"><label>
									子菜单SEQ： </label></td>
							<td height="50" class="menus_a"><input type="text"
								name="seoSiteMapHtml.seq" id="ipt_add_seq" class="form_add"
								onChange="clean_ipt('div_add_seq')" /></td>
							<td height="50" class="menus_a">
								<div id="div_add_seq"></div>
							</td>
						</tr>
						<tr>
							<td height="40">&nbsp;</td>
							<td height="40">&nbsp;</td>
							<td height="40">&nbsp;</td>
						</tr>
					</table>
				</div>
				<div class="bottom_bg" align="center">
					<input type="submit" value="确定" class="buttom_sure" /> <input
						onClick="closeWin('add_menu')" type="button" class="buttom_no"
						value="取消" />
				</div>
			</form>
		</div>
	</div>

	<div class="js_zs js_cl_all" id="xiugai">
		<h3>
			<a class="close" href="javascript:void(0);" onClick="closeWin('xiugai')">X</a>修改子菜单
		</h3>
		<div class="tab_ztxx_all">
			<form action="editChildMapMenu.do" method="post"
				onsubmit="return chkChildEditValue()">
				<input type="hidden" name="htmlMenuId" value="${htmlMenuId}" />
				<input type="hidden" name="seoSiteMapHtml.seoSiteMapHtmlId" value="" id="ipt_edit_main_menuId" />
				<div class="add_cont">
					<table cellspacing="0" cellpadding="0" class="gl_table js_sz_table">
						<tr>
							<td width="24%" height="50" align="center" class="menus_a">
								<label> 上级菜单： </label>
							</td>
							<td width="38%" height="50" class="menus_a">
							<s:select list="mapMenuNameList" listKey="seoSiteMapHtmlId" listValue="htmlMenuName" headerKey=""
									value="{htmlMenuId}" headerValue="--请选择--" id="ipt_edit_menuId" name="seoSiteMapHtml.parentId"
									onchange="clean_ipt('div_edit_menuId')"  >
							</s:select>
							</td>
							<td width="38%" height="50" class="menus_a">
								<div id="div_edit_menuId"></div>
							</td>
						</tr>
						<tr>
							<td width="24%" height="50" align="center" class="menus_a">
								<label> 菜单名称： </label>
							</td>
							<td width="38%" height="50" class="menus_a"><input
								type="text" name="seoSiteMapHtml.htmlMenuName"
								id="ipt_edit_main_name" class="form_add"
								onChange="clean_ipt('div_edit_main_name')" /></td>
							<td width="38%" height="50" class="menus_a">
								<div id="div_edit_main_name"></div>
							</td>
						</tr>
						<tr>
							<td height="50" align="center" class="menus_a"><label>
									菜单链接： </label></td>
							<td height="50" class="menus_a"><input type="text"
								name="seoSiteMapHtml.htmlMenuUrl" id="ipt_edit_main_url"
								class="form_add" onChange="clean_ipt('div_edit_main_url')" /></td>
							<td height="50" class="menus_a">
								<div id="div_edit_main_url"></div>
							</td>
						</tr>
						<tr>
							<td height="50" align="center" class="menus_a"><label>
									菜单SEQ： </label></td>
							<td height="50" class="menus_a"><input type="text"
								name="seoSiteMapHtml.seq" id="ipt_edit_main_seq"
								class="form_add" onChange="clean_ipt('div_edit_main_seq')" /></td>
							<td height="50" class="menus_a">
								<div id="div_edit_main_seq"></div>
							</td>
						</tr>
						<tr>
							<td height="40">&nbsp;</td>
							<td height="40">&nbsp;</td>
							<td height="40">&nbsp;</td>
						</tr>

					</table>
				</div>
				<div class="bottom_bg" align="center">
					<input type="submit" value="确定" class="buttom_sure" />
					<input type="button" onClick="closeWin('xiugai')" class="buttom_no"
						value="取消" />
				</div>
			</form>
		</div>
	</div>

	</body>
</html>
