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
<title>权限监控</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript">
	$(document).ready(function(){
		$("#searchForm").validate({
			rules: {                     
				permissionId:{                         
					required: true,
					number: true
				}
			}, 
			messages: {                     
				permissionId: {                         
					required: "请输入权限ID",
					number:"请输入数字"
				}
			}, 
			errorPlacement: function (error, element) { 
		        error.appendTo(element.parent());            
		    } 
		});
	}
	);
	function deleteHandler(){
		var upIds = "";
		$(".cbClass").each(function(){
			if($(this).attr("checked")){
				upIds = upIds + $(this).attr("userId") + "-" + $(this).attr("permissionId") + "-" + $(this).attr("statusFlag") + ",";
			}
		});
		if(upIds.length == 0){
			Utils.alert("请选择用户");
			return false;
		}else{
			$("<div />").dialog({
				modal:true,
				autoOpen:true,
				title:"提示",
				width:100,
				height:100,
				position: 'center',
		        show: "explode",
		        hide: "highlight",
		        buttons:{
		        	"确定":function(){
		        		$.post("deleteUserPermission.do",
		        			{
		        				upIds : upIds
		        			},
		        			function(data){
		        				if(data != "success"){
	    							Utils.alert("操作失败");
	    						}else{
	    							Utils.alert("操作成功");
	    							$("#searchForm").submit();
	    						}
		        			}
		        		);
		        	}
		        }
			}).html("确定删除？");
		}
		
	}
</script>
</head>
<body>
	<form id="searchForm" action="search.do" method="post">
	<ul id="formUl" class="gl_top">
		<li>权限ID：<input type="text" class="input_b" name="permissionId" value="${permissionId }"/></li>
		<li>状态：
			<select id="statusSlct" name="status">
	    		<option value="">请选择</option>
	    		<option value="1"
	    			<s:if test="status == \"1\"">selected="selected"</s:if>>
	    		有效</option>
	    		<option value="0"
	    			<s:if test="status == \"0\"">selected="selected"</s:if>>
	    		禁止</option>
	    	</select>
		</li>
		<li><input name="" value="查询" type="submit"></li> 
	</ul>
	</form>
	<table class="gl_table" cellpadding="0" cellspacing="0">
	  <tbody><tr>
	    <th>用户ID</th>
	    <th>用户名</th>
	    <th>姓名</th>
	    <th>直属部门</th>
	    <th>是否有效</th>
	    <th>职务</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="permUserPage.items" var="item">
		  <tr>
		    <td>${item.userId }</td>
		    <td>${item.userName }</td>
		    <td>${item.realName }</td>
		    <td>${item.departmentName }</td>
		    <td>
		    	<s:if test="#request.item.status == 1">有效</s:if>
		    	<s:elseif test="#request.item.status == 0">禁用</s:elseif>	
		    </td>
		    <td>${item.position }</td>
		    <td class="gl_cz">
		    	<input name="" value="" class="cbClass" type="checkbox"
		    	 userId="${item.userId }" permissionId="${item.permissionId }" statusFlag="${item.status }"
		    	>
		    </td>
		  </tr>
	  </s:iterator>
	  <tr>
		<td colspan="2">总条数：<s:property value="permUserPage.totalResultSize" />
		</td>
		<td colspan="5" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permUserPage)"/>
		</td>
	  </tr>
	</tbody>
	</table>
	<p class="qx_jk_p">
		<input name="" value="删除" type="button" onclick="deleteHandler()">
	</p>
	
</body>
</html>