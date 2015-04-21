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
		<title>备注</title>
	</head>
	<body>
		<div class="box_content center-item">
        	<textarea id="memo" style="width:300px; height:100px"></textarea>
			<p class="tc mt20">
				<button class="btn btn-small w3" id="saveMemoBtn" type="button">保存</button>　　
			</p>
			${visaApprovalDetails.memo}
        </div>
	</body>
	<script type="text/javascript">
		 function closeMemoBtnClick() {
			$("#popDiv2").dialog("close");	
		 }
		 
		 $(function(){
			$("#saveMemoBtn").click(function(){
				$.ajax({
	    	 		url: "<%=basePath%>/visa/approval/saveMemo.do",
					type:"post",
	    	 		data: {
							"visaApprovalDetails.detailsId":${visaApprovalDetails.detailsId},
							"visaApprovalDetails.memo":$("#memo").val()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("添加成功");
							$("#popDiv2").dialog("close");
						} else {
							alert("添加失败");
						}
	    	 		}
	    		});					
			});
		 });
	</script>
</html>