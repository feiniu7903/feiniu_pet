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
		<title>修改组员</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<table class="p_table form-inline">
            <tbody>
                <tr>
                	<td class="p_label">所属部门：</td>
                    <td>${request.group.departmentName }</td>
                    <td class="p_label">组织名称：</td>
                    <td>${request.group.groupName }</td>
                </tr>
            </tbody>
        </table>
		<p class="mt20">
			<button onclick="deleteUser()" type="button" class="btn btn-small">删除</button>&#12288;
			<button onclick="addUser()" type="button" class="btn btn-small">添加用户</button>&#12288;
		</p>
		<div class="p_box">
			<table class="p_table table_center">
				<tr>
					<th><input id="allCBox" type="checkbox" 
						onchange="javascript:$('.checkboxClass').attr('checked',$('#allCBox').attr('checked'));"></input></th>
					<th>用户名</th>
					<th>姓名</th>
					<th>操作</th>
				</tr>
				<s:iterator value="#request.userPage.items" var="item">
					<tr>
						<td><input type="checkbox" class="checkboxClass" value="${item.workGroupUserId}"></input></td>
						<td>${item.userName }</td>
						<td>${item.realName }</td>
						<td>
							<label><input type="checkbox" name="leaderRadio" 
								onchange="setLeader(this,${item.workGroupId},${item.permUserId})"
								<s:if test="#request.item.leader == \"true\"">
									checked="checked"
								</s:if>
								/>主管</label>
						</td>
					</tr>
				</s:iterator>
				<tr>
    				<td colspan="2" align="right">总条数：${userPage.totalResultSize}</td>
					<td colspan="3" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(#request.userPage.pageSize,#request.userPage.totalPageNum,#request.userPage.url,#request.userPage.currentPage)"/></td>
   				</tr>
			</table>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
		  	
		});
		function deleteUser(){
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
		  $.post("delete_group_user.do",{ids:ids},function(data){
			  if("SUCCESS" == data){
				  alert("操作成功");
				  refreshPage();
			  }else{
				  alert("操作失败");
			  }
		  });
		}
		function addUser(){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "添加用户",
		        position: 'center',
		        width: 920, 
		        height: 520,
		        close:function(){
		        	refreshPage();
		        }
			}).width(900).height(500).attr("src","to_add.do?workGroupId="+${workGroupId});
		}
		function refreshPage(){
			window.location.href = "list.do?workGroupId="+${workGroupId};
		 }
		function setLeader(obj,groupId,permUserId){
			var msg="";
			if(obj.checked){
				msg="确定设为主管？";
			}else{
				msg="确定取消主管权限？";
			}
			if(confirm(msg)){
				$.post("set_leader.do",{workGroupId:groupId,permUserId:permUserId,flag:obj.checked},function(data){
					if("SUCCESS" == data){
						  alert("操作成功");
					  }else{
						  alert("操作失败");
					  }
				});
			}
		}
	</script>
</html>
