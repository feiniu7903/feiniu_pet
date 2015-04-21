<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EBOOKING账号管理</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/ui-components.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/ebk/admin_index.js" ></script>
<script type="text/javascript" src="../../js/base/log.js"></script>
</head>
<body>
	<div class="gl_gys">
	供应商：<span id="supNameSp" value="${supName }">${supName }(<span id="supIdSp" value="${supId }">${supId }</span>)</span>
	<a class="btn btn-small" href="back_to_admin_supplier.do" style="margin-left: 100px;">返回</a>
	</div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>用户名</th>
	    <th>是否可用</th>
	    <th>打印通关票据</th>
	    <th>是否管理员</th>
	    <th>父级用户</th>
	    <th>驴妈妈联系人</th>
	    <th>姓名</th>
	    <th>部门</th>
	    <th>手机</th>
	    <th>备注</th>
	    <th>创建日期</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="ebkUserPage.items" var="item">
			<tr>
				<td><s:property value="userName" /></td>
				<td>
					<s:if test="#item.valid == \"true\"">是</s:if>
					<s:if test="#item.valid == \"false\"">否</s:if>
				</td>
				<td>${item.canPrint eq 'true'?'是':'否' }</td>
				<td>
					<s:if test="#item.isAdmin == \"true\"">是</s:if>
					<s:if test="#item.isAdmin == \"false\"">否</s:if>
				</td>
				<td><s:property value="parentUserName" /></td>
				<td><s:property value="lvmamaContactName" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="department" /></td>
				<td><s:property value="mobile" /></td>
				<td><s:property value="description" /></td>
				<td><s:date name="createTime" format="yyyy-MM-dd hh:mm" /></td>
				<td class="gl_cz">
					<s:if test="#item.isAdmin == \"true\"">
						<a href="javascript:void(0);" onclick="editAdminHandler(${item.userId})">修改</a>
						<a href="javascript:void(0);" onclick="initPasswordHandler(${item.userId})">初始化密码</a>
						<!-- 
						<a href="javascript:void(0);" onclick="userProductHandler(${item.userId},${item.supplierId},'${item.bizType}')">产品权限</a>
						 -->
						<a href="javascript:void(0);" onclick="userTargetHandler(${item.userId},${item.supplierId},'${item.bizType}')">履行对象绑定</a>
						<a href="#log" class="showLogDialog" param={"objectId":${item.userId},"objectType":"EBK_USER"}>日志</a>
					</s:if>
					<s:if test="#item.isAdmin == \"false\"">
						<s:if test="#item.canPrint != \"true\"">
							<a href="javascript:void(0);" onclick="canPrint(${item.userId},'true')">开通打印</a>
						</s:if>
						<s:if test="#item.canPrint == \"true\"">
							<a href="javascript:void(0);" onclick="canPrint(${item.userId},'false')">取消打印</a>
						</s:if>
						<a href="javascript:void(0);" onclick="showUserMenuHandler(${item.userId},'${item.bizType }')">查看菜单权限</a>
					</s:if>
				</td>
			</tr>
	  </s:iterator>
	   <tr>
		<td>总条数：<s:property value="ebkUserPage.totalResultSize" /></td>
		<td colspan="10" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(ebkUserPage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>