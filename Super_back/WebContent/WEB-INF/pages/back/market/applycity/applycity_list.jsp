<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>报名城市管理</title>
		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/prod/condition.js"></script>
		<script type="text/javascript" >
		function addCity(){
			if($("#cityName").val()==""){
				alert("请输入城市名称");
				return false;
			}
			if($("#cityPY").val()==""){
				alert("请输入城市名拼音");
				return false;
			}
			if(confirm("是否添加该城市信息？")){
				$.post("<%=basePath%>/applycity/addCity.do",
					{cityName:$("#cityName").val(),
						cityPY:$("#cityPY").val()
					},function(data){
						if("SUCCESS" == data){
							alert("添加成功！");
							location.reload();
						}else if("EXIST" == data){
							alert("该城市名已存在！");
						}else{
							alert("添加失败！");
						}
				  });
			}
		}
		function delCity(cityId){
			if(confirm("是否删除该城市信息？")){
				$.post("<%=basePath%>/applycity/delCityById.do?applyCityId="+cityId,
					function(data){
						if("SUCCESS" == data){
							alert("删除成功！");
							location.reload();
						}else{
							alert("删除失败！");
						}
				  });
			}
		}
		</script>
	</head>

	<body>
		<div class="main main02">
			<div class="row1">
				<h3 class="newTit">
					报名城市
				</h3>
				<form method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="newInput"
						width="100%">
						<tr>
							<td>
								<em>城市中文名：</em>
							</td>
							<td>
								<input type="text" class="newtext1" id="cityName" name="cityName" />
							</td>
							<td>
								<em>城市名拼音：</em>
							</td>
							<td>
								<input type="text" class="newtext1" name="cityPY" id="cityPY" />
							</td>
							<td>
								<input type="button" class="button" value="新增" onclick="addCity()" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="row2" style="text-align: center;">
				<table border="0" cellspacing="0" cellpadding="0" class="newTable">
					<tr class="newTableTit">
						<td>
							城市中文名
						</td>
						<td>
							城市名拼音
						</td>
						<td>
							操作
						</td>
					</tr>
					<s:iterator value="applyCityList" var="city">
						<tr>
							<td>${city.cityName }</td>
							<td>${city.cityPY }</td>
							<td><a href="javascript:delCity(${city.applyCityId })">删除</a></td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
		</div>
	</body>
</html>


