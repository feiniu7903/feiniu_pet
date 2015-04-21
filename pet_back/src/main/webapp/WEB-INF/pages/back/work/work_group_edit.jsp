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
		<title>修改组织</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">修改组织</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">所属部门:</td>
							<td>
								<s:select list="departmentList" id="m" name="departmentId" listKey="workDepartmentId" listValue="departmentName"
								></s:select>	
							</td>
						</tr>
						<tr>
						    <td class="p_label">组织名称:</td>
						    <td>
						       <input type="text" name="groupName" value="${request.workGroup.groupName }" maxlength="20" />&nbsp;&nbsp;
						    	组织类型:
						       	<select name="groupType" id="gType">
						       		<option value="">请选择</option>
						            <option value="jd"
						            <s:if test="#request.workGroup.groupType == \"jd\"">selected="selected"</s:if>
						            >计调</option>
						       	</select>
						    </td>
						</tr>
						<tr>
						    <td class="p_label">是否可用:</td>
						    <td>
						       <select name="valid" id="valid">
						            <option value="true"
						            <s:if test="#request.workGroup.valid == \"true\"">selected="selected"</s:if>
						            >可用</option>
						            <option value="false"
						            <s:if test="#request.workGroup.valid == \"false\"">selected="selected"</s:if>
						            >不可用</option>
						       </select>	
						    </td>
						</tr>		
						<tr>
						    <td class="p_label">描述:</td>
						    <td>
						       <textarea name="groupDesc" rows="4" cols="45" id="desc">${request.workGroup.memo } </textarea>
						    </td>
						</tr>
					</table>
					<input type="hidden" name="workGroupId"  value="${request.workGroupId }" id="workGroupId"/>
				</form>
				<p class="tc mt20">
		            <button class="btn btn-small w5" type="button" onclick="editGroup();">提交</button>　
	           </p>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	  function editGroup(){
		  var departmentId =$("#m").find('option:selected').val();
		  if(departmentId==""){
			  alert("请选择组织所属部门");
			  $("#m").focus();
			  return;
		  }
		  var groupName=$("input[name='groupName']").val();
		  if(groupName==""){
			  alert("请填写组织名称");
			  $("input[name='groupName']").focus();
			  return;
		  }
		  var valid=$("#valid").find('option:selected').val();
		  var workGroupId=$("#workGroupId").val();
		  var groupType=$("#gType").val();
		  var groupDesc=$("#desc").val();
		  if(groupDesc.length >= 100){
			  alert("描述长度不能大于100字符");
			  return;
		  }
		  $.post("edit_group.do",{departmentId:departmentId,groupName:groupName,groupDesc:groupDesc,workGroupId:workGroupId,valid:valid,groupType:groupType},function(data){
			  if("SUCCESS" == data){
				  alert("操作成功");
				  $("form[action='list.do']",parent.document).submit();
			  }else if("EXIST"==data){
				  alert("组织名称已存在");
			  }else{
				  alert("操作失败");
			  }
		  });
	  }
	</script>
</html>
