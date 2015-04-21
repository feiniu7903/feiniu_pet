<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <head>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">

	<script charset="utf-8" src="<%=basePath %>kindeditor-4.0.2/kindeditor.js"></script>
	<script charset="utf-8" src="<%=basePath %>kindeditor-4.0.2/lang/zh_CN.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/base/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/base/base.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/knowledge/help/help.js"></script>
	<script type="text/javascript">
	   var path='<%=basePath%>';
	</script>
	
	</head>
  
  <body>
   <div id="search">
     
   <table  width="100%" class="datatable">
			<tr>
		                <th>
		                <a href="javascript:void(0)" id="content-add" icon="icon-save">内容管理</a>
		                </th>
		                <th>
		                <a href="javascript:void(0)" id="type-manage" icon="icon-save">板块管理</a>
		                </th>

</tr>
	</table>

  </div> 
  
   <div id="contentDiv">
   	       <form id="searchContentFrom" name="searchContentFrom" method="post">
   	       <table  width="100%" class="datatable">
			<tr>
					   <th>
		               <label>业务板块</label>
		               <select id="helpBusinessType" name="helpBusinessType" onchange="refershHelpContentTypeList()">
		               </select>
		               <label>内容板块</label>
		               <select id="helpContentType" name="searchInfoQuesTypeHierarchy.typeId">
		               </select>
		                <a href="javascript:void(0)" id="btn-search-content" icon="icon-search">查询</a>
						</th>
						<th class="no-right-border"></th>
         </tr>
	</table>
	 </form>
   </div>
   	<div id="typeDiv">
   <table id="listBusinessTypeGrid"></table>
   <table id="listContentTypeGrid"></table>
   	</div>
<div id="businessTypeWindow" title="添加业务板块" style="padding:5px;width:400px;height:250px;" idField="id">
	<form id="addBusinessTypeForm" method="post" >
		 <table class="laytable" border="0" cellspacing="0" cellpadding="0" width="100%">
	  		<tr>
		  		<td>业务板块名称：</td>
		  		<td><input id="businessTypeName" type="text" name="resultInfoQuesTypeHierarchy.typeName" ></td>
		            <input id="businessTypeId" type="hidden" name="resultInfoQuesTypeHierarchy.typeId" >
	  		</tr>
	  	    
  		</table>
    </form>
    	<div style="text-align:center;padding:5px;">
			<a href="javascript:void(0)" onclick="return saveType('addBusinessTypeForm');" id="btn-save-business-type" icon="icon-save">保存</a>
			<a href="javascript:void(0)" onclick="closeWindow('businessTypeWindow')" id="btn-cancel-business-type" icon="icon-cancel">取消</a>
		</div>
</div>

<div id="contentTypeWindow" title="添加内容板块" style="padding:5px;width:400px;height:250px;" idField="id">
	<form id="addContentTypeForm" method="post" >
		 <table class="laytable" border="0" cellspacing="0" cellpadding="0" width="100%">
		 <tr>
		  		<td>业务板块：</td>
		  		<td>
		  		     <select id="businessTypeSelect" name="businessTypeSelect">
					</select>
	                <span id="businessTypeSelectLabel" />
				</td>
	  		</tr>
	  		<tr>
	  		    <input  id="contentParentTypeId" type="hidden" name="resultInfoQuesTypeHierarchy.parentTypeId" />
		  		<td>新添加内容板块名称：</td>
		  		<td><input id="contentTypeName" type="text" name="resultInfoQuesTypeHierarchy.typeName" ></td>
	            <input id="contentTypeId" type="hidden" name="resultInfoQuesTypeHierarchy.typeId" >
	  		</tr>
	  	    
  		</table>
    </form>
    	<div style="text-align:center;padding:5px;">
			<a href="javascript:void(0)" onclick="return saveType('addContentTypeForm');" id="btn-save-content-type" icon="icon-save">保存</a>
			<a href="javascript:void(0)" onclick="closeWindow('contentTypeWindow')" id="btn-cancel-content-type" icon="icon-cancel">取消</a>
		</div>
</div>

<div id="contentWindow" title="添加帮助信息" style="width:700px;height:600px;" idField="id">
   <input name="userName" type="hidden" id="userName"  value="${userName}"/>  
   <form id="addContentForm" method="post">
		<table width="100%" class="laytable">
		<tr>
		<td>
		分类：
		</td>
		<td>
	         <span id="contentWindowBusinessTypeName"></span><br/>
	         <span id="contentWindowContentTypeName"></span>
	         <input type ="hidden" id="doubleHelpContentType" name="oneHelpInfoRow.typeId" value="" />
		</td>
		<tr>
		<td>
		标题：
		</td>
		<td>
	    <input type="hidden" id="oneHelpInfoRowContentId" name="oneHelpInfoRow.id"/>
		<input type="hidden" id="oneHelpInfoRowUserName" name="oneHelpInfoRow.userName"/>
		<input id="oneHelpInfoRowTitle" type="text" name="oneHelpInfoRow.title" value="" size="60"/>
		</td>
		</tr>
		<tr>
		<td>
		内容：
		</td>
		<td>
			<textarea id="oneHelpInfoRowContent" name="oneHelpInfoRow.content"  style="width: 5px; height: 5px; visibility: hidden;"></textarea>
			<textarea id="contentA" name="contentA"  style="width: 600px; height: 400px; visibility: hidden;"></textarea>
		</td>
		</tr>
		</table>
		</form>
		<div style="text-align:center;padding:5px;">
			<a href="javascript:void(0)" onclick="return saveContent()" id="btn-save-content" icon="icon-save">保存</a>
			<a href="javascript:void(0)" onclick="closeWindow('contentWindow')" id="btn-cancel-content" icon="icon-cancel">取消</a>
		</div>
</div>
<div id="contentResultListDiv">
<table id="listContentGrid"></table>
</div> 
  </body>
</html>

