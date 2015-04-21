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
		<title></title>
	</head>
	<body>
        <div class="box_content center-item">
	        <table class="p_table form-inline" width="100%">
				<tr>
					<td class="p_label">订单号</td>
					<td>
						<input type="text" name="orderId" id="orderId"/>
					</td>
				</tr>
			</table>
        </div>

        <p class="tc mt20"><button class="btn btn-small w3" onclick="javascript:saveDeleteVisaApproval()" type="button">提交</button></p>
  
	</body>
	<script type="text/javascript">
		
		function saveDeleteVisaApproval() {
			$("#orderId").val($.trim($("#orderId").val()));
			if ($("#orderId").val() == "" || isNaN($("#orderId").val())) {
				alert("请输入合法的订单号");
				$("#orderId").focus();
				return;
			}
			if (confirm("您确定需要删除订单号" + $("#orderId").val() + "订单的所有签证审核记录吗？此操作不可恢复!")) {
				$.ajax({
	    	 		url: "<%=basePath%>/visa/approval/saveDeleteApproval.do",
					type:"post",
	    	 		data: {
							"orderId":$("#orderId").val()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							document.location.reload();
						} else {
							alert(result.message);
						}
	    	 		}
	    		});				
			}

		}
	</script>
</html>