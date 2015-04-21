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
		<title>添加组织</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">添加组织</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">所属部门:</td>
							<td>
								<s:select list="departmentList" id="m" name="departmentId" listKey="workDepartmentId" listValue="departmentName"></s:select>	
							</td>
						</tr>
						<tr>
						    <td class="p_label">组织名称:</td>
						    <td>
						       <input type="text" name="groupName" maxlength="20"/>&nbsp;&nbsp;
						       	组织类型：
						       	<s:select list="#{'':'请选择','jd':'计调'}" id="gType" name="groupType"></s:select>
						    </td>
						</tr>
						<tr>
						    <td class="p_label">描述:</td>
						    <td>
						       <textarea name="groupDesc" rows="4" cols="100" style="width:300px;" id="desc"></textarea>
						    </td>
						</tr>
					</table>
				</form>
				<p class="tc mt20">
		            <button class="btn btn-small w5" type="button" onclick="addGroup();">提交</button>　
	           </p>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	  function addGroup(){
		  var departmentId =$("#m").find('option:selected').val();
		  var groupName=$("input[name='groupName']").val();
		  var groupType =$("#gType").find('option:selected').val();
		  var groupDesc=$("#desc").val();
		  
		  if(groupDesc.length >= 100){
			  alert("描述长度不能大于100字符");
			  return;
		  }
		  $.post("save_group.do",{departmentId:departmentId,groupName:groupName,groupDesc:groupDesc,groupType:groupType},function(data){
			  if("SUCCESS" == data){
				  alert("操作成功");
				  $("form[action='list.do']",parent.document).submit();
			  }else{
				  alert("操作失败");
			  }
		  });
	  }
	</script>
</html>
