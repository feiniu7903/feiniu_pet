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
		<title>工单类型列表</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">工单类型列表</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="list.do" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">类型名称：</td>
							<td>
								<input type="text" name="typeName" value="${typeName }"/>	
							</td>
							<td class="p_label">是否内置：</td>
							<td>
								<select name="system">
								    <option value="">请选择</option>
								    <option value="true" <s:if test="#request.system == \"true\"">selected="selected"</s:if>>是</option>
								    <option value="false" <s:if test="#request.system == \"false\"">selected="selected"</s:if>>否</option>
								</select>
							</td>
						</tr>			
					</table>
					<p class="tc mt20">
						<button class="btn btn-small w5" type="submit">查询</button>　
						<button onclick="addWorkOrderType()" class="btn btn-small w5" type="button">新增</button>　
					</p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>工单类型名称</th>
						<th>标示符</th>
						<th>是否内置</th>
						<th>URL</th>
						<th nowrap="nowrap">操作</th>
					</tr>
					<s:iterator value="#request.workOrderTypePage.items" var="item">
					<tr>
						<td>${item.typeName }</td>
						<td>${item.typeCode}</td>
						<td>	
							<s:if test="#request.item.system == \"true\"">是</s:if>
							<s:if test="#request.item.system == \"false\"">否</s:if>
						</td>
						<td>${item.urlTemplate }</td>
						<td nowrap="nowrap">
							<a href="javascript:editWorkOrderType(${item.workOrderTypeId});">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</s:iterator>
					<tr>
	    				<td colspan="2" align="right">总条数：${workOrderTypePage.totalResultSize}</td>
						<td colspan="3" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(#request.workOrderTypePage.pageSize,#request.workOrderTypePage.totalPageNum,#request.workOrderTypePage.url,#request.workOrderTypePage.currentPage)"/></td>
   				    </tr>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		function editWorkOrderType(workOrderTypeId){
			$("<iframe id='editWin' frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "修改工单类型",
		        position: 'center',
		        width: 920, 
		        height: 600
			}).width(900).height(580).attr("src","edit.do?workOrderTypeId="+workOrderTypeId);
		}
		
	   function addWorkOrderType(){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "新增工单类型",
		        position: 'center',
		        width: 920, 
		        height: 600
			}).width(900).height(580).attr("src","add.do");
	   }
	   function closePopWin(winId){
		   $("#"+winId).dialog("close");
	   }
	</script>
</html>
