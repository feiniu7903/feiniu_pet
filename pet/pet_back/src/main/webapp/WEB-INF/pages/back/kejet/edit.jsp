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
		<title>编辑</title>
	</head>
	<body>
		<p class="lead">请填写文字代码：<span class="gray">（可使用HTML代码直接在页面中显示）</span></p>
        <div class="box_content center-item" >
        	<textarea style="width:380px; height:120px" name="code" id="code">${comKeJetWord.code}</textarea>
        </div>
		<p class="lead">代码用途：</p>
        <div class="box_content center-item" >
        	<input type="text" id="description" value="${comKeJetWord.description}"/>
        </div>
		        

        <p class="tc mt20">
        	<button class="btn btn-small w3" id="saveAddKeJetWord" type="button">保存</button>
        	<button class="btn btn-small w3" id="closeBtn" type="button">关闭</button>
        </p>       
	</body>
	<script type="text/javascript">
		$(function(){
			$("#closeBtn").click(function(){
				$("#popDiv").dialog("close");	
			});
			
			$("#saveAddKeJetWord").click(function(){
				if ($("#code").val() == "") {
					alert("请输入文字代码");
					return;
				}
				$.ajax({
	    	 		url: "<%=basePath%>/pub/kejet/save.do",
					type:"post",
	    	 		data: {
							"comKeJetWord.id":'${comKeJetWord.id}',
							"comKeJetWord.code":$("#code").val(),
							"comKeJetWord.description":$("#description").val()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("操作成功");
							$("#popDiv").dialog("close");
							document.location.reload();
						} else {
							alert("数据丢失，操作失败");
						}
	    	 		}
	    		});	
			});
			
	 	});
	</script>
</html>