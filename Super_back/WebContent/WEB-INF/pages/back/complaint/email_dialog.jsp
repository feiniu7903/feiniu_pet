<%--
  Created by IntelliJ IDEA.
  User: zhushuying
  Date: 13-11-11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title></title>
	<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
	<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/prod/condition.js"></script>
    <script type="text/javascript" src="<%=basePath%>kindeditor-3.5.1/kindeditor.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>kindeditor-3.5.1/skins/default.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/complaint/complaint_edit.css"/>
    <style type="text/css">
        .ar {
            text-align: right;
            padding-right: 5px;
        }

        .al {
            text-align: left;
            padding-left: 5px;
        }
        #remind_from .nc_table td textarea{
        	height: 120px;
        	width: 650px;
        }
    </style>
    <script>
                KE.show({
                    id:'contentEmail',
                    cssPath : '<%=basePath%>kindeditor-3.5.1/skins/default.css',
                    filterMode : true
                });
    	function checkValue(){
    		if($("#visitorEmail").val()==""){
    			alert("请输入游客邮箱");
    			return false;
    		}
    		if($("#contentEmail").val()==""){
    			alert("内容不能为空");
    			return false;
    		}
    		return true;
    	}
    	function saveEmail(){
    		if(!checkValue()){
    			return false;
    		}
            $.post("order/complaint/saveEmail.do",
            		$("#remind_from").serialize() ,
            		function (data) {
            	if (data == "success") {
                	alert("发送成功");
                	parent.location.reload();
                } else {
    				alert("发送失败");
    			}
    		});
    	}
    	$(function(){
	    	$("a.searchEmialContent").click(function(){
				var contentId=$(this).attr("data");
				$("#searchEmialContent").showWindow({
					title: '邮件内容',
	                width: 500,
					url:"<%=basePath%>order/complaint/searchContent.do",
					data:{"emailId":contentId}});
			});
    	});
    </script>
</head>
<body>
<div>
    <form id="remind_from" style="text-align: center;">
        <s:hidden name="complaintId" id="complaintId"/>
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar">*游客邮箱：</td>
                <td class="nc_tr_body al">
                    <input type="text" id="visitorEmail" name="visitorEmail" />
                </td>
            </tr>
            <tr>
                <td class="nc_tr_head ar">标题：</td>
                <td class="nc_tr_body al">
                	<input type="text" id="title" name="title" style="width: 200px;" />
                </td>
            </tr>
            <tr>
                <td class="nc_tr_head ar">*内容：</td>
                <td class="nc_tr_body al" width="665px">
                	<textarea name="contentEmail" id="contentEmail" cols="text2" rows="text2" class="text2"></textarea>
                </td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="saveEmail()" id="saveBtn" value="确认" style="align: center;width: 70px; height: 25px"/>
    </form>
</div>
<div>
	已发邮件列表
	<table class="nc_table" cellspacing="1" cellpadding="1">
		<tr>
			<td class="nc_tr_head">邮箱</td>
			<td class="nc_tr_head">标题</td>
			<td class="nc_tr_head">发送时间</td>
			<td class="nc_tr_head">操作人</td>
			<td class="nc_tr_head">操作</td>
		</tr>
		<s:iterator value="emailList" var="email">
			<tr>
				<td class="nc_tr_body">${toAddress }</td>
				<td class="nc_tr_body">${subject }</td>
				<td class="nc_tr_body">
					<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="nc_tr_body">${fromName }</td>
				<td class="nc_tr_body">
					<a href="javascript:void(0)" data="<s:property value="contentId"/>" class="searchEmialContent">查看内容</a>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>

<div id="searchEmialContent" url="<%=basePath%>order/complaint/searchContent.do"></div>

</body>
</html>