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
		<title>增补材料</title>
	</head>
	<body>
		<p class="lead">请填写增补材料内容：<span class="gray">（包含寄送地址和最晚时间）</span></p>
        <div class="box_content center-item" >
        	<textarea style="width:380px; height:120px" id="materialContent"></textarea>
        </div>
		<p class="tc mt10">
			<li>换行请使用&lt;br&gt;</li>
			<li>超链接请使用&lt;a href="http://www.baidu.com"&gt;百度&lt;/a&gt;</li>
		</p>        

        <p class="tc mt20">
        	<button class="btn btn-small w3" id="saveAddMaterial" type="button">保存</button>
        	<button class="btn btn-small w3" id="closeBtn" type="button">关闭</button>
        </p>       
	</body>
	<script type="text/javascript">
		$(function(){
			$("#closeBtn").click(function(){
				$("#popDiv").dialog("close");	
			});
			
			$("#saveAddMaterial").click(function(){
				if ($("#materialContent").val() == "") {
					alert("请输入需要增补的材料内容");
					return;
				}
				$.ajax({
	    	 		url: "<%=basePath%>/visa/approval/saveAddMaterial.do",
					type:"post",
	    	 		data: {
							"visaApprovalDetails.visaApprovalId":${searchVisaApprovalId},
							"visaApprovalDetails.content":$("#materialContent").val()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("操作成功");
							$("#popDiv").dialog("close");
						} else {
							alert("数据丢失，操作失败");
						}
	    	 		}
	    		});	
			});
			
	 	});
	</script>
</html>