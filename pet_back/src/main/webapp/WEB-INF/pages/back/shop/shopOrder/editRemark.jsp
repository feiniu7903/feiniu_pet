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
<title>订单备注</title>  
</head>
<body>
	<div class="main main02">
		<div class="row1">
			<form action="<%=basePath%>shop/shopOrder/updateShopOrder.do" method="post" id="updateShopOrderForm">
			
				<table border="1" cellspacing="0" cellpadding="0" class="search_table">
					<input type="hidden" value="${orderId}" id="orderId" name="orderId"/>
					<tr>
						<td>备注：</td>
						<td>
							<textarea label="Description"  id="orderRemark"  rows="8" cols="130">${remark} </textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="button" value="保存"  width="50px" onclick="save();" id="update"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
		<script type="text/javascript">
			 //提交备注
		    function save()
		    {
				var orderRemark = $("#orderRemark").val();
				var orderId = ${orderId};
				if(orderId == null){
					alert(orderId)
					return;
				}
				
		        var url = "<%=basePath%>shop/shopOrder/updateShopOrder.do";
		        $.ajax({
		        	 url: url,
		        	 data: {"orderId":orderId,"remark":orderRemark},
		        	 dataType:"json",
		        	 success: function(result) {
			        	if (result.success) {
			        		window.location.reload();
			        	} else {
			        		alert("提交失败!");
			        	}
		        	 }
		        });
		    }
		</script>
</body>
</html>
  
