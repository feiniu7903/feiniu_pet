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
<title>组织管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_organization_index.js"></script>
</head>
<body>
	<form id="searchForm" action="save_organization.do" method="post" class="formClass">
	<ul class="zz_top">
		<li>组织名称：<input id="departmentNameInput" class="input_b" name="departmentName" value=""></li>
	    <li>父级：
	    	<select id="parentOrgLevelSlct" name="parentOrgLevel" onchange="levelChangeHandler()">
	    		<option value="0">顶级</option>
	    		<option value="1">一级</option>
	    		<option value="2">二级</option>
	    		<option value="3">三级</option>
	    	</select>
	    	<select id="parentOrgSlct" name="parentOrgId">
	    	</select>
	    </li>
		<li>
		<mis:checkPerm permCode="1125">
			<input name="" value="新增" type="submit">
		</mis:checkPerm>
		</li> 
	</ul>
	</form>
	<table class="zz_table" cellpadding="0" cellspacing="0">
	  <tbody>
	  <tr>
	  	<td class="gl_table_3_t_bg" rowspan="2" width="10%"></td>
	    <td class="gl_table_3_t_bg" width="20%">一级部门</td>
	    <td class="gl_table_3_t_bg" width="20%">二级部门</td>
	    <td class="gl_table_3_t_bg" width="20%">三级部门</td>
	    <td class="gl_table_3_t_bg" width="20%">四级以下部门</td>
	    <td class="gl_table_3_t_bg" rowspan="2" width="10%"></td>
	  </tr>
	  <tr>
	    <td valign="top">
	    	<ul id="ul0" class="bumen_list">
			  	<s:iterator value="#request.firstOrgList" var="item">
					<li>
						<span>
							<a href="#" onclick="deleteOrgHandler(${item.orgId})">删除</a>|
							<a href="#" onclick="editOrgHandler(${item.orgId},'${item.departmentName}',${item.permLevel},${item.parentOrgId})">修改</a>
						</span>
						<label>
							<input id="${item.orgId }" type="radio" value="${item.orgId }" name="org1"
							onclick="orgHandler1(${item.orgId })"/>
							${item.departmentName }
						</label>
					</li>
				</s:iterator>
			</ul>
	    </td>
	    <td valign="top">
	    	<ul id="ul1" class="bumen_list">
	        </ul>
	    </td>
	    <td valign="top">
	    	<ul id="ul2" class="bumen_list">
	        </ul>
	    </td>
	    <td valign="top">
	    	<ul id="ul3" class="bumen_list">
	        </ul>
	    </td>
	  </tr>
	</tbody>
	</table>
	<div id="editWin" style="display: none">
		<form id="searchForm1" action="save_organization.do" method="post" class="formClass">
		<input id="orgIdHd" name="orgId" type="hidden"/>
		<ul class="zz_top">
			<li>组织名称：<input id="departmentNameInput1" class="input_b" name="departmentName" value=""></li>
		    <li>父级：
		    	<select id="parentOrgLevelSlct1" name="parentOrgLevel" onchange="levelChangeHandler1()">
		    		<option value="0">顶级</option>
		    		<option value="1">一级</option>
		    		<option value="2">二级</option>
		    		<option value="3">三级</option>
		    	</select>
		    	<select id="parentOrgSlct1" name="parentOrgId">
		    	</select>
		    </li>
			<li><input name="" value="保存" type="submit"></li> 
		</ul>
		</form>
	</div>
</body>
</html>