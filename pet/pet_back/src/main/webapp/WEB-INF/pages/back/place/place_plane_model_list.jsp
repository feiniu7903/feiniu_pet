<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机型列表页面</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="<%=basePath%>/js/base/dialog.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
<script type="text/javascript">
		  $(function(){
			  $("#addPlaneModelBtn").click(function(){
					$("#viewPlacePlaneModelDiv").showWindow({
					url:"<%=basePath%>/place/placePlaneModelAdd.do",width:450});
			});
			
			$("a.editPlaneModel").click(function(){
				var modelId=$(this).attr("data");
				$("#viewPlacePlaneModelDiv").showWindow({
					url:"<%=basePath%>/place/placePlaneModelview.do",width:450,
					data:{"placeModelId":modelId}});
			});  
	     });
</script>
</head>
<body>
<div id="viewPlacePlaneModelDiv" style="display: none"></div>
	<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">机型列表</a></li>
			</ul>
	</div>
	<div class="iframe-content">
		<div class="p_box">
				<form action="<%=basePath%>place/placePlaneModelList.do" method="post">
				   <table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label"><em>机型编号：</em></td>
						<td><s:textfield class="newtext1" name="planeCode"/></td>
						<td class="p_label"><em>机型名称：</em></td>
						<td><s:textfield  class="newtext1" name="planeName"/></td>
					</tr>
				</table>
				<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　<button class="btn btn-small w5"  id="addPlaneModelBtn"  type="button">添加</button></p>
			</form>
			</div>
		 <div class="p_box">
				<table class="p_table table_center">
				<tr>
					<th width="100px">ID</th>
					<th>机型编号</th>
					<th>机型名称</th>
					<th>类型</th>
					<th>最少座位数</th>
					<th>最多座位数</th>
					<th>操作</th>
				</tr>
				<s:iterator value="pagination.items" var="plane">
						<tr>
							<td>
							${plane.placeModelId}
							</td>
							<td>
							${plane.planeCode}
							</td>
							<td>
							${plane.planeName}
							</td>
							<td>
							<s:if test="placeType=='NARROW'">窄体</s:if>
							<s:if test="placeType=='BROAD'">宽体</s:if>
							</td>
							<td>${plane.minSeat}</td>
							<td>${plane.maxSeat}</td>
							<td>
							<a href="javascript:void(0)" data="${plane.placeModelId}" class="editPlaneModel">编辑</a>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td>总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="6" style="text-align:right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
					</tr>
				</table>
			</div>
			</div>
		</body>
</html>