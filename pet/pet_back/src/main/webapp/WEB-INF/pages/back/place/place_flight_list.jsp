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
<title>航班列表页面</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="<%=basePath%>/js/base/dialog.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
    <script type="text/javascript">
		  $(function(){
			  $("#searchcity_start").jsonSuggest({
					url : "/pet_back/place/searchPlace.do",
					maxResults : 10,
					width : 300,
					emptyKeyup : false,
					minCharacters : 1,
					onSelect : function(item) {
						$("#startPlaceId").val(item.id);
					}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#startPlaceId").val("");
				}
			});
			 $("#searchcity_arrive").jsonSuggest({
					url : "/pet_back/place/place/searchPlace.do",
					maxResults : 10,
					width : 300,
					emptyKeyup : false,
					minCharacters : 1,
					onSelect : function(item) {
						$("#arrivePlaceId").val(item.id);
					}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#arrivePlaceId").val("");
				}
			});
			  $("#addPlaceFlightBtn").click(function(){
					$("#viewPlaceFlightDiv").showWindow({
					url:"<%=basePath%>place/placeFlightAdd.do",width:780});
			});
			
			$("a.editPlaceFlight").click(function(){
				var flightId=$(this).attr("data");
				$("#viewPlaceFlightDiv").showWindow({
					url:"<%=basePath%>place/placeFlightview.do",width:780,
					data:{"placeFlightId":flightId}});
			});  
	     });
</script>
</head>
<body>
<div id="viewPlaceFlightDiv" style="display: none"></div>
	<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">航班列表</a></li>
			</ul>
	</div>
	<div class="iframe-content">
		<div class="p_box">
				<form action="<%=basePath%>place/placeFlightList.do" method="post">
				   <table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label"><em>航班编号：</em></td>
						<td><s:textfield name="flightNo" /></td>
						<td class="p_label"><em>航空公司：</em></td>
						<td><s:select list="placeAirlineList" headerValue="请选择"
								headerKey="" listKey="placeAirlineId" listValue="airlineName"
								name="airlineId" /></td>
						</tr>
						<tr>
						<td class="p_label"><em>出发城市：</em></td>
						<td><s:textfield id="searchcity_start"
								name="placeFlight.startPlace.name" cssClass="required"/> <s:hidden
								name="startPlaceId" id="startPlaceId" /></td>
						<td class="p_label"><em>抵达城市：</em></td>
						<td><s:textfield id="searchcity_arrive"
								name="placeFlight.arrivePlace.name" cssClass="required"/> <s:hidden
								name="arrivePlaceId" id="arrivePlaceId" /></td>
					</tr>
				</table>
				<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　<button class="btn btn-small w5"  id="addPlaceFlightBtn"  type="button">添加</button></p>
			</form>
			</div>
		 <div class="p_box">
				<table class="p_table table_center">
				<tr>
				    <th>ID</th>
					<th>航班号</th>
					<th>航空公司</th>
					<th>出发城市</th>
					<th>抵达城市</th>
					<th>起飞时间</th>
					<th>降落时间</th>
					<th>经停次数</th>
					<th>操作</th>
				</tr>
				<s:iterator value="pagination.items" var="flight">
						<tr>
						<td>${flight.placeFlightId}</td>
						<td>${flight.flightNo}</td>
						<td>${flight.airline.airlineName}</td>
						<td>${flight.startPlace.name}</td>
						<td>${flight.arrivePlace.name}</td>
						<td>${flight.startTime}</td>
						<td>${flight.arriveTime}</td>
						<td>${flight.stopTime}</td>
						<td>
						<a href="javascript:void(0)" data="${flight.placeFlightId}" class="editPlaceFlight">编辑</a>
						</td>
						</tr>
					</s:iterator>
					<tr>
						<td align="right">
							总条数：<s:property value="pagination.totalResultSize"/>
						</td>
						<td colspan="8" style="text-align:right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
					</tr>
				</table>
			</div>
			</div>
		</body>
</html>