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
		<title>用户列表</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/util.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="iframe-content">
			<div class="p_box">
				<form action="to_add.do" method="post">
					<input name="workGroupId" value="${workGroupId }" type="hidden"/>
					<table class="p_table form-inline" width="100%">
						<tr>
							<td style="width:60px;" class="p_label">所属部门</td>
							<td style="width:400px;">
								<s:select style="width:100px;" list="departmentList" name="departmentId" listKey="orgId" listValue="departmentName" onchange="changeOrgValue(this,'departmentId2')"></s:select>
								<s:select style="width:100px;" list="departmentList2" name="departmentId2" listKey="orgId" listValue="departmentName"  onchange="changeOrgValue(this,'departmentId3')"></s:select>
								<s:select style="width:100px;" list="departmentList3" name="departmentId3" listKey="orgId" listValue="departmentName"></s:select>
							</td>
							<td  style="width:60px;" class="p_label">姓名</td>
							<td>
								<input style="width:100px;" type="text" name="realName" value="${request.realName }"/>
							</td>
							<td  style="width:60px;" class="p_label">用户名</td>
							<td>
								<input style="width:100px;" type="text" name="userName" value="${request.userName }"/>
							</td>
						</tr>			
					</table>
					<p class="tc mt20">
						<button id="searchBtn" class="btn btn-small w5" type="submit">查询</button>　
						<button onclick="addUserBatch()" type="button" class="btn btn-small">添加</button>
					</p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th><input id="allCBox" type="checkbox" onchange="checkboxChangeHandler()"></input></th>
						<th>用户名</th>
						<th>姓名</th>
						<th>所属部门</th>
						<th>是否有效</th>
					</tr>
					<s:iterator value="#request.userPage.items" var="item">
						<tr>
							<td><input type="checkbox" class="checkboxClass" value="${item.permUserId}"></input></td>
							<td>${item.userName}</td>
							<td>${item.realName}</td>
							<td>${item.departmentName}</td>
							<td>
								<s:if test="item.valid == \"true\"">是</s:if>
								<s:if test="item.valid == \"false\"">否</s:if>
							</td>
						</tr>
					</s:iterator>
					<tr>
     					<td colspan="1" align="right">总条数：${userPage.totalResultSize }</td>
						<td colspan="4" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(#request.userPage.pageSize,#request.userPage.totalPageNum,#request.userPage.url,#request.userPage.currentPage)"/></td>
    				</tr>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		  $(function(){
		  		
		  });
		  function checkboxChangeHandler(){
			 $(".checkboxClass").attr("checked",$("#allCBox").attr("checked"));
		  }
		  function addUserBatch(){
			  var ids = "";
			  $(".checkboxClass").each(function(){
				if($(this).attr("checked") == true){
					ids = ids + $(this).val() + ",";
				}
			  });
			  if(ids.length == 0){
				  alert("请选择用户");
				  return ;
			  }
			  $.post("save_group_user.do",{ids:ids,workGroupId:${workGroupId}},function(data){
				  if("SUCCESS" == data){
					  alert("操作成功");
					  refreshPage();
				  }else{
					  alert("操作失败");
				  }
			  });
		  }
		  function refreshPage(){
			  $("#searchBtn").click();
		  }
		  function changeOrgValue(obj,target){
			  	if(target=='departmentId2'){
			  		$('#departmentId2').empty();
			  		$('#departmentId3').empty();
			  	}
			  	if(target=='departmentId3'){
			  		$('#departmentId3').empty();
			  	}
				Utils.setComboxDataSource("/pet_back/work/group/user/getChildOrgList.do?departmentId="+$(obj).val(), target, false, undefined);
		  }
	</script>
</html>
