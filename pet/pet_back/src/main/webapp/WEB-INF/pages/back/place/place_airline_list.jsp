<%@ page language="java" import="java.util.*,com.lvmama.comm.vo.Constant" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>航空列表页面</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="<%=basePath%>/js/base/dialog.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
    <script type="text/javascript">
		  $(function(){
			  $("#addAirportBtn").click(function(){
					$("#viewPlaceAirlineDiv").showWindow({
					url:"<%=basePath%>/place/placeAirlineAdd.do",width:450});
			});
			
			$("a.editAirline").click(function(){
				var airlineId=$(this).attr("data");
				$("#viewPlaceAirlineDiv").showWindow({
					url:"<%=basePath%>/place/placeAirlineview.do",width:450,
					data:{"placeAirlineId":airlineId}});
			});
	     });
</script>
</head>
<body>
<div id="viewPlaceAirlineDiv" style="display: none"></div>
	<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">航空列表</a></li>
			</ul>
	</div>
	<div class="iframe-content">
		<div class="p_box">
				<form action="<%=basePath%>place/placeAirlineList.do" method="post">
				   <table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label"><em>代码：</em></td>
						<td><s:textfield class="newtext1" name="airlineCode"/></td>
						<td class="p_label"><em>名称：</em></td>
						<td colspan="3"><s:textfield class="newtext1" name="airlineName"/></td>
					</tr>
				</table>
				<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　<button class="btn btn-small w5"  id="addAirportBtn"  type="button">添加</button></p>
			</form>
			</div>
		 <div class="p_box">
				<table class="p_table table_center">
				<tr>
					<th width="100px">ID</th>
					<th>图标</th>
					<th>代码</th>
					<th>名称</th>
					<th>操作</th>
				</tr>
				<s:iterator value="pagination.items" var="airline">
						<tr>
							<td>
							${airline.placeAirlineId}
							</td>
							<td>
							<s:if test="airlineIcon!=null && airlineIcon!=''">
						    <img src="<%=Constant.getInstance().getPrefixPic()%>${airline.airlineIcon}"/>
						    </s:if>
							</td>
							<td>
							${airline.airlineCode}
							</td>
							<td>
							${airline.airlineName}
							</td>
							<td>
							<a href="javascript:void(0)" data="${airline.placeAirlineId}" class="editAirline">编辑</a>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td>总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="4" style="text-align:right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
					</tr>
				</table>
			</div>
			</div>
		</body>
</html>