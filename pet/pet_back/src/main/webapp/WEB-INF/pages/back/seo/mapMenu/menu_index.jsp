<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
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
<script type="text/javascript" src="<%=basePath%>/js/seo/siteMapHtml_menu.js"></script>
<script type="text/javascript">
 var basePath='<%=basePath%>';
</script>
</head>

<body>

<form action="<%=basePath%>/seo/updateMainMapSeq.do" method="post" onsubmit="return chk_seq()">
<s:hidden name="returnUrl" value="jumpMenuIndexDo"></s:hidden>
<table>
<tbody>
	 <tr>
	  <th colspan="4" align="left" height="50" align="left" valign="bottom">
	    <input type="submit" value="保存排序" style="width:99px"/> <b>&nbsp;【<a href="javascript:void(0);" class="add_menus" onclick="openWin('add_menu');">新建</a>】</b>
	  </th>
	 </tr>
</tbody>
</table>
	<table cellspacing="0" cellpadding="0" class="p_table gl_table js_sz_table">
	 <tbody>
		<tr class="menus_bg">
			<th width="10%">
			</th>
			<th width="10%">
				排序
			</th>
			<th width="30%" >
				菜单名称
			</th>
			<th width="30%">
				菜单URL
			</th>
			<th>
				操作    
			</th>
		</tr>
		<s:iterator value="mainMapMenuList">
			<tr>
			    <td><input type="checkbox" name="mapMenuSeq_Id" value="${seoSiteMapHtmlId}"/></td>
				<td height="38" align="center" >
				<input type="hidden" name="mapMenuId"  value="${seoSiteMapHtmlId}"/>
				<input type="text" name="mapMenuSeq"  value="${seq}" class="p_num" />
				</td>
				<td height="38" >
					<a href="<%=basePath%>/seo/jumpMenuChild.do?htmlMenuId=${seoSiteMapHtmlId}" class="other_menus">${htmlMenuName}</a>
				</td>
				<td height="38" >
					${htmlMenuUrl}
				</td>
				<td height="38" class="gl_cz">
					<a href="javascript:void(0);" onClick="editMainMenu('${seoSiteMapHtmlId}','${htmlMenuName}','${htmlMenuUrl}','${seq}')" class="other_menus1">修改</a>
					<a href="javascript:void(0);" onClick="delMainMenu(${seoSiteMapHtmlId})"  class="other_menus2">删除</a>
				</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	</form>

    <div class="js_zs js_cl_all" id="add_menu">
		<h3><a class="close" href="javascript:void(0);" onClick="closeWin('add_menu')">X</a>添加菜单</h3>
	    <div class="tab_ztxx_all">
			<form action="<%=basePath%>/seo/addMainMapMenu.do" method="post" onSubmit="return chk_add_value()">
			<div class="add_cont">
				 <table cellspacing="0" cellpadding="0" border="0" class="tab_ztxx">
					<tr>
						<td width="24%" height="50" align="center" >
							<label>
								菜单名称：
							</label>
						</td>
						<td width="38%" height="50" >
							<input type="text" name="seoSiteMapHtml.htmlMenuName" id="ipt_add_menuName"
								class="form_add" onChange="clean_ipt('div_add_menuName')"/>
						</td>
						<td width="38%" height="50" >
							<div id="div_add_menuName" >
							</div>
						</td>
					</tr>
					<tr>
						<td height="50" align="center" >
							<label>
								菜单链接：
							</label>
						</td>
						<td height="50" >
							<input type="text" name="seoSiteMapHtml.htmlMenuUrl"  id="ipt_add_menuUrl"
								class="form_add" onChange="clean_ipt('div_add_menuUrl')"/>
						</td>
						<td height="50" >
							<div id="div_add_menuUrl" >
								
							</div>
						</td>
					</tr>
					<tr>
						<td height="50" align="center" >
							<label>
								菜单SEQ：
							</label>
						</td>
						<td height="50" >
							<input type="text" name="seoSiteMapHtml.seq" id="ipt_add_seq"
								class="form_add" onChange="clean_ipt('div_add_seq')"/>
						</td>
						<td  height="50" >
							<span id="div_add_seq" />
						</td>
					</tr>
					<tr>
						<td height="40">
							&nbsp;
						</td>
						<td height="40">
							&nbsp;
						</td>
						<td height="40">
							&nbsp;
						</td>
					</tr>
				</table>
				
			</div>
			<div class="bottom_bg" align="center">
				<input type="submit"  value="确定" align="center" class="buttom_sure" />
				<input type="button" class="buttom_no" onClick="closeWin('add_menu')" value="取消"/>
			</div>
			</form>
			</div>
			</div>

	<div class="js_zs js_cl_all" id="xiugai">
	<h3><a class="close" href="javascript:void(0);" onClick="closeWin('xiugai')">X</a>修改菜单</h3>
    <div class="tab_ztxx_all">
       <form action="<%=basePath%>/seo/editMainMapMenu.do" method="post" onSubmit="return chk_edit_value();">
			<input type="hidden" name="seoSiteMapHtml.seoSiteMapHtmlId" id="ipt_edit_main_menuId" value=""/>
			<div class="add_cont">
				 <table cellspacing="0" cellpadding="0" border="0" class="tab_ztxx">
					<tr>
						<td width="24%" height="50" align="center" >
							<label>
								菜单名称：
							</label>
						</td>
						<td width="38%" height="50" >
							<input type="text" name="seoSiteMapHtml.htmlMenuName" id="ipt_edit_main_name"
								class="form_add" onChange="clean_ipt('div_edit_main_name')"/>
						</td>
						<td width="38%" height="50" >
							<div id="div_edit_main_name" >
							</div>
						</td>
					</tr>
					<tr>
						<td height="50" align="center" >
							<label>
								菜单链接：
							</label>
						</td>
						<td height="50" >
							<input type="text" name="seoSiteMapHtml.htmlMenuUrl" id="ipt_edit_main_url"
								class="form_add"  onChange="clean_ipt('div_edit_main_url')"/>
						</td>
						<td height="50" >
							<div id="div_edit_main_url" >
							</div>
						</td>
					</tr>
					<tr>
						<td height="50" align="center" >
							<label>
								菜单SEQ：
							</label>
						</td>
						<td height="50" >
							<input type="text" name="seoSiteMapHtml.seq" id="ipt_edit_main_seq"
								class="form_add" onChange="clean_ipt('div_edit_main_seq')"/>
						</td>
						<td height="50" >
							<div id="div_edit_main_seq" >
							</div>
						</td>
					</tr>
					<tr>
						<td height="40">
							&nbsp;
						</td>
						<td height="40">
							&nbsp;
						</td>
						<td height="40">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<p class="ztxx_bot">
				<input type="submit" value="确定" align="center" class="buttom_sure" />
				<input type="button" onClick="closeWin('xiugai')" class="buttom_no" value="取消" />
			</p>
			</form>
    </div>
</div>
	</body>
</html>
