<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>工单组织列表</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">工单组织列表</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="list.do" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">组织名称：</td>
							<td>
								<input type="text" name="groupName" value="${groupName}"/>	
							</td>
							<td class="p_label">所属部门：</td>
							<td>
								<s:select list="departmentList" name="departmentId" listKey="workDepartmentId" listValue="departmentName" headerKey="" headerValue="请选择"></s:select>
							</td>
							<td class="p_label">处理人：</td>
							<td>
								<input type="text" name="permUserName" value="${permUserName}"/>	
							</td>
						</tr>			
					</table>
					<p class="tc mt20">
						<button id="searchBtn" class="btn btn-small w5" type="button">查询</button>　
						<button onclick="addGroup()" class="btn btn-small w5" type="button">新增</button>　
					</p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>组织名称</th>
						<th>所属部门</th>
						<th>创建时间</th>
						<th nowrap="nowrap">操作</th>
					</tr>
					<s:iterator value="#request.workGroupPage.items" var="item">
					<tr>
						<td>${item.groupName }</td>
						<td>${item.departmentName}</td>
						<td><s:date name="#item.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td nowrap="nowrap">
							<a href="javascript:editGroup(${item.workGroupId});">修改组织</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:editGroupUser(${item.workGroupId});">修改人员</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</s:iterator>
					<tr>
	    				<td colspan="2" align="right">总条数：${workGroupPage.totalResultSize}</td>
						<td colspan="3" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(#request.workGroupPage.pageSize,#request.workGroupPage.totalPageNum,#request.workGroupPage.url,#request.workGroupPage.currentPage)"/></td>
   				    </tr>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		function editGroupUser(groupId){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "修改用户",
		        position: 'center',
		        width: 1020, 
		        height: 600
			}).width(1000).height(580).attr("src","user/list.do?workGroupId="+groupId);
		}
		function addGroup(option){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "新增组织",
		        position: 'center',
		        width: 720, 
		        height: 400
			}).width(700).height(380).attr("src","add.do");
			
		}
		function editGroup(groupId){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "修改组织",
		        position: 'center',
		        width: 720, 
		        height: 400
			}).width(700).height(380).attr("src","edit.do?workGroupId="+groupId);
		}
		$("#searchBtn").click(function(){
			$("form[action='list.do']").submit();
		});
	</script>
</html>
