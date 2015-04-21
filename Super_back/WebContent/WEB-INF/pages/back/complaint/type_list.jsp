<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>投诉类型</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/condition.js"></script>
<script type="text/javascript">
	function editType(typeId) {
		$("<iframe frameborder='0' ></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "投诉类型修改",
	        position: 'center',
	        width: 750, 
	        height: 280
		}).width(720).height(240).attr("src","<%=basePath%>complaintType/searchType.do?typeId="+typeId);
	}
	function addType() {
		$("<iframe frameborder='0' ></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "新增投诉类型",
	        position: 'center',
	        width: 750, 
	        height: 280
		}).width(720).height(240).attr("src","<%=basePath%>complaintType/searchType.do");
	}
</script>
</head>

<body>
<div class="row2" style="text-align: center;">
	<form id="queryType_form" action="<%=basePath%>complaintType/queryTypeList.do" method="post">
		<table border="0" cellspacing="0" cellpadding="0" class="newTable">
			<tr class="newTableTit">
				<td>投诉类型</td>
				<td>类型描述</td>
				<td>排序</td>
				<td>操作</td>
			</tr>
			<s:iterator value="typePage.items" var="type">
				<tr>
					<td>${type.typeName }</td>
					<td>${type.typeDescription }</td>
					<td>${type.sort }</td>
					<td>
						<a href="javascript:void(0)" onclick="editType('${type.typeId}');" >修改</a>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td align="left">总条数：<s:property
						value="typePage.totalResultSize" />
				</td>
				<td colspan="3" align="right" style="text-align: right"><s:property
						escape="false"
						value="@com.lvmama.comm.utils.Pagination@pagination(typePage)" />
				</td>
			</tr>
		</table>
	</form>
</div>
<button style="width: 80px;height: 25px;margin-left: 10px;" type="button" onclick="addType();">新增</button>
</body>
</html>