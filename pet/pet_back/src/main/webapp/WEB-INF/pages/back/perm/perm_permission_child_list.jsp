<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_permission_index.js"></script>
</head>
<body>
	<div class="js_zs">
    <div class="js_zhanshi_table">
  	<table class="zhanshi_table" cellspacing="0" cellpadding="0">
      <tr>
        <th>编号</th>
        <th>权限名称</th>
        <th>类型</th>
        <th>是否可用</th>
        <th>类别</th>
        <th>级别</th>
        <th>URL</th>
        <th>用来排序</th>
        <th>操作</th>
      </tr>
       <s:iterator value="permPermissionPage.items" var="item">
			<tr>
				<td><s:property value="permissionId" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="zkType" /></td>
				<td><s:property value="valid" /></td>
				<td><s:property value="category" /></td>
				<td><s:property value="permLevel" /></td>
				<td><s:property value="url" /></td>
				<td><s:property value="seq" /></td>
				<td>
					<s:if test="type=='URL'">
						<a href="javascript:editPermission(<s:property value="permissionId" />);">修改</a>
					</s:if>
				</td>
			</tr>
	  </s:iterator>
  </table>
  	<div class="js_pages">
  		<div class="pages">
  		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)"/>
		</div>
  	共<span><s:property value="permPermissionPage.totalResultSize" /></span>条
  </div>
    </div>
</div>
<div id='popup_div'  style="display:none;" ></div>
</body>
</html>