<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>

<form id="permission_child_form" action="save.do" method="post">
  <div class="js_zhanshi_table">
   	<h4 class="sy_title no_bor_mar">新增子菜单</h4>
       <table class="qx_xz" style="width:640px;" border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
               <span class="red">*</span>菜单名称：<input class="input_b " name="permPermission.name" value="">
           </td>
           <td><span class="red">*</span>类别：<select name="permPermission.category">
			   	 <option value="">--请选择--</option>
			   	<s:iterator value="gategoryList" var="data">	
			   	<option value="${data.category}" <s:if test="%{#data.selected==true}">selected="selected"</s:if>>${data.name}</option>
			   	</s:iterator>
		    </select></td>
         </tr>
        <tr> <td>
           	<span class="red">*</span>级别：
				<select name="permPermission.permLevel">
					<option value="">--请选择--</option>
					<option value="1" >1</option>
					<option value="2" >2</option>
					<option value="3" >3</option>
				</select>
           	 </td>
           <td>
           	<span class="red">*</span>用来排序：<input class="input_b" name="permPermission.seq" >
           	 </td>
         </tr>
          <tr>
           <td colspan="2">　父级：<s:property value="permPermission.name" /><input type="hidden" name="permPermission.parentId" value="<s:property value="permPermission.permissionId" />"/></td>
         </tr>
         <tr>
           <td colspan="2">　　URL：<input class="input_b width_400" name="permPermission.url" value=""></td>
         </tr>
         <tr>
           <td colspan="2">　　允许访问的URL路径：
           		<textarea rows="5" cols="50" name="permPermission.urlPattern"></textarea>
           		使用分号分隔
           </td>
         </tr>
       </table>
       <p class="qx_xz_p"><input value="新增" type="submit"><input  id="close_btn" value="关闭" type="button"></p>
   </div>
</form>
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_permission_add.js"></script>