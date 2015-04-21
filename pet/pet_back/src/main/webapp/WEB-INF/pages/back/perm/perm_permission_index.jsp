<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限设置</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_permission_index.js"></script>
</head>
<body>
	<ul class="gl_top">
		<form id="searchForm" action="search_permission.do" method="post">
			<li>权限名称&ID：<input class="input_b" name="name" value="<s:property value="name" />"></li>
			<li>级别：
				<select name="permLevel">
					<option value="">--请选择--</option>
					<option value="1" <s:if test="permLevel==1">selected="selected"</s:if>>1</option>
					<option value="2" <s:if test="permLevel==2">selected="selected"</s:if>>2</option>
					<option value="3" <s:if test="permLevel==3">selected="selected"</s:if>>3</option>
				</select>
			</li>
			<li>类型：
				<select name="type"><option value="">--请选择--</option>
					<option value="URL" <s:if test="type=='URL'">selected="selected"</s:if>>URL</option>
					<option value="ELEMENT" <s:if test="type=='ELEMENT'">selected="selected"</s:if>>元素</option>
				</select>
			</li>
			<li>类别：
			    <select name="category">
				    <option value="">--请选择--</option>
				   	<s:iterator value="gategoryList" var="data">	
				   	<option value="${data.category}" <s:if test="%{#data.selected==true}">selected="selected"</s:if>>${data.name}</option>
				   	</s:iterator>
			    </select>	
		    </li>
		    <li>父级ID：<input class="input_b" name="parentId" value="<s:property value="parentId" />"></li>
		    <li>是否删除：<input name="valid" type="checkbox" <s:if test="valid=='on'">checked="checked" </s:if> ></li>
			<li><input name="search" value="查询" type="submit">
			<mis:checkPerm permCode="1114">
				<input value="新增一级菜单" type="button" onclick="newPermissionHandler()">
			</mis:checkPerm>
			</li> 
		</form>
	</ul>
	<div class="tab_top"></div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>权限ID</th>
	    <th>权限名称</th>
	    <th>父级名称</th>
	    <th>类型</th>
	    <th>是否可用</th>
	    <th>类别</th>
	    <th>级别</th>
	    <th>URL</th>
	    <th>父级ID</th>
	    <th>用来排序</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="permPermissionPage.items" var="item">
			<tr>
				<td><s:property value="permissionId" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="parentName" /></td>
				<td><s:property value="zkType" /></td>
				<td><s:property value="valid" /></td>
				<td><s:property value="category" /></td>
				<td><s:property value="permLevel" /></td>
				<td><s:property value="url" /></td>
				<td><s:property value="parentId" /></td>
				<td><s:property value="seq" /></td>
				<td class="gl_cz">
					<mis:checkPerm permCode="1115">
					<s:if test="editVisible">
						<a  href="javascript:editPermission(<s:property value="permissionId" />);" >[修改 ]</a> 
					</s:if>
					</mis:checkPerm>
					<mis:checkPerm permCode="1971">
					<s:if test="delVisible">
						<a href="javascript:deletePermission(<s:property value="permissionId" />);" >[删除]</a> 
					</s:if>
					</mis:checkPerm>
					<mis:checkPerm permCode="1116">
					<s:if test="editVisible">
						<a href="javascript:viewChildList(<s:property value="permissionId" />,'URL');" >[修改子菜单]</a> 
					</s:if>
					</mis:checkPerm>
					<mis:checkPerm permCode="1117">
					<s:if test="addVisible">
						<a href="javascript:addChildPermission(<s:property value="permissionId" />);" >[新增子菜单]</a> 
					</s:if>
					</mis:checkPerm>
					<mis:checkPerm permCode="1972">
					<s:if test="viewVisible">
						<a href="javascript:viewChildList(<s:property value="permissionId" />,'ELEMENT');" >[查看元素]</a> 
					</s:if>
					</mis:checkPerm>
				</td>
			</tr>
	  </s:iterator>
	  <tr>
		<td colspan="1">总条数：<s:property value="permPermissionPage.totalResultSize" />
		</td>
		<td colspan="10" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)"/>
		</td>
	  </tr>
	</table>
	<div id='popup_div'  style="display:none;" ></div>
</body>
</html>