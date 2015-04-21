<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>

<form id="permission_form" action="edit.do" method="post">
  <div class="js_zhanshi_table">
   	<h4 class="sy_title no_bor_mar">修改菜单</h4>
       <table class="qx_xz" style="width:620px;" border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           		<input type="hidden" name="permPermission.permissionId" value="<s:property value="permPermission.permissionId" />"/>
               <span class="red">*</span>菜单名称：<input class="input_b " name="permPermission.name" value="<s:property value="permPermission.name" />">
           </td>
           <td><span class="red">*</span>类别：
           <select name="permPermission.category">
			   	<s:iterator value="gategoryList" var="data">	
			   	<option value="${data.category}" <s:if test="%{#data.selected==true}">selected="selected"</s:if>>${data.name}</option>
			   	</s:iterator>
		    </select>
			</td>
         </tr>
         <tr> <td>
           	<span class="red">*</span>级别：
				<select name="permPermission.permLevel">
					<option value="1" <s:if test="permPermission.permLevel==1">selected="selected"</s:if>>1</option>
					<option value="2" <s:if test="permPermission.permLevel==2">selected="selected"</s:if>>2</option>
					<option value="3" <s:if test="permPermission.permLevel==3">selected="selected"</s:if>>3</option>
				</select>
           	 </td>
           <td>
           	<span class="red">*</span>用来排序：<input class="input_b" name="permPermission.seq" value="<s:property value="permPermission.seq" />">
           	 </td>
         </tr>
         <tr>
           <td colspan="2">URL：<input class="input_b width_400" name="permPermission.url" value="<s:property value="permPermission.url" />"></td>
         </tr>
         <tr>
           <td colspan="2">允许访问的URL路径：
           		<textarea rows="5" cols="50" name="permPermission.urlPattern"><s:property value="permPermission.urlPattern" /></textarea>
          		使用分号分隔
           </td>
         </tr>
       </table>
       <p class="qx_xz_p"><input value="修改" type="submit"><input  id="close_btn" value="关闭" type="button"></p>
   </div>
</form>
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_permission_edit.js"></script>