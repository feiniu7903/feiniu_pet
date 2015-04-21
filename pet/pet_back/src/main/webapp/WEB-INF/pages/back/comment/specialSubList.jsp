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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专题列表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
 
<script type="text/javascript">
        $(function(){
            //新增专题.
            $("#addSpecialSubject").click(function() {
            	$("#addSpecialSubjectDiv").load("<%=basePath%>/commentManager/editSpecialSubject.do",function() {
            		$(this).dialog({
            			modal:true,
            			title:"新增专题",
            			width:550,
            			height:250
                	});
            	});
            });
        });
        //查看专题
        function editSpecialSubject(id) {
        	$("#editSpecialSubjectDiv").load("<%=basePath%>/commentManager/editSpecialSubject.do?id=" + id,function() {
        		$(this).dialog({
        			modal:true,
        			title:"查看专题",
        			width:550,
        			height:250
            	});
        	});
        }
    </script>
</head>
<body>
<div id="addSpecialSubjectDiv" style="display: none"></div>
<div id="editSpecialSubjectDiv" style="display: none"></div>
<div id="createMarkCouponCodesDiv" style="display: none"></div>
	<div class="main main02">
		<div class="row1">
			<h3 class="newTit"></h3>
			<h3 class="newTit">专题列表</h3>
			<form action="<%=basePath%>/commentManager/editSpecialSubject.do" method="post">
				<table border="1" cellspacing="0" cellpadding="0" class="search_table"
					width="100%">
					<tr>
						<td>
							<input id="addSpecialSubject" type="button" class="button" value="新增" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="row2">
		</listbox>
			<table border="1" cellspacing="0" cellpadding="0" class="gl_table">
				<tr>
					<th>创建时间</th>
					<th>第几期</th>
					<th>主题</th>
					<th>链接</th>
					<th>操作</th>
				</tr>
				<s:iterator value="cmtSpecialSubjectList" var="cmtSpecialSubject">
					<tr>
						<td>${cmtSpecialSubject.strCreatedTime}</td>
						<td>${cmtSpecialSubject.versionNum}</td>
						<td>${cmtSpecialSubject.title}</td>
						<td>${cmtSpecialSubject.url}</td>
						<td>
							 <a href="javascript:editSpecialSubject(${cmtSpecialSubject.id});">查看</a>
						</td>
					</tr>
				</s:iterator>
				<tr>
     				<td colspan="3"> 总条数：<s:property value="pagination.totalResultSize"/> </td>
     				<td colspan="7" align="left">
     					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/>
     				</td>
    			</tr>		
			</table>
			 
		</div>
	</div>
</body>
</html>


