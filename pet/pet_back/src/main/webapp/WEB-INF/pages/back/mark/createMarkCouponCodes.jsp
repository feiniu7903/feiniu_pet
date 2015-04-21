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
		<title>优惠券(活动)列表</title>
		<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate"> 
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT"> 
		<META HTTP-EQUIV="expires" CONTENT="0"> 
	</head>

	<body>
		<form id="createMarkCouponCodeForm" action="<%=basePath%>/mark/coupon/queryCouponList/generateMarkCouponCodes.do" method="post" target="markCouponCodesDiv">
			<input type="hidden" name="couponId" value="${couponId}" id="couponId" />
			<table class="p_table form-inline" width="50%">
				<tr>
					<td class="p_label">生成优惠券的个数:</td>
					<td><input id="generateNumber" type="text" name="generateNumber" maxlength="10"/></td>
				</tr>
			</table>
			<p class="tc mt10">
				<input type="button" id="createMarkCouponCodesBtn" class="btn btn-small w3" value="生成优惠券" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="exportExcelBtn" class="btn btn-small w3" value="导出Excel" />如果总条数大于10000行，请寻求BI或DBA帮助导出
			</p>
		</form>
		<br/>
		
		<div id="markCouponCodesDiv" href="<%=basePath%>mark/coupon/queryMarkCouponCodes.do?couponId=${couponId}"+"&_=2">
		</div>

		<div id="createMarkCouponCodeIngDiv" style="display: none">
			 不要关闭此对话框,<br/>也不要刷新页面,<br/>请耐心等待!<br/>操作结束后此对话框会自动关闭!
		</div>
		
		<script type="text/javascript">
			var _page = 1;
			$(function(){		
				//生成优惠码.
				$("#createMarkCouponCodesBtn").click(function() {
					//检查generateNumber文本框输入内容为非数字，并且数字大小为0至10W之间的数字.
					if (isNaN(parseInt($("#generateNumber").val())) || parseInt($("#generateNumber").val()) <= 0 || parseInt($("#generateNumber").val()) > 100000) {
						alert("请输入0至100000之间的数字!");
						return;
					}
					//正在生成优惠码的提示框.
					var $dlg=$("#createMarkCouponCodeIngDiv"); 
					$dlg.dialog({
		        			modal:true,
		        			title:"正在生成优惠码...",
		        			width:200,
		        			height:120
		            });
					 
					$.ajax({
						url:"<%=basePath%>/mark/coupon/generateMarkCouponCodes.do",
						data:{couponId:$("#couponId").val(),generateNumber:$("#generateNumber").val(),date:(new Date).getTime()},
						type:"POST",
						dataType:"json",
						success:function(dt){
							if (dt.success) {
								$dlg.dialog("close");
								alert("成功生成优惠码");
								var h_url=$("#markCouponCodesDiv").attr('href')+"&_="+(new Date()).getTime();
								//刷新iframe.
								$("#markCouponCodesDiv").load(h_url,function(){});
							} else {
								alert("生成优惠码失败!");
							}
						}
					});			
				});
				
				//优惠码导出到Excel.
				$("#exportExcelBtn").click(function() {
					var url = "<%=basePath%>/mark/coupon/exportExcel.do?couponId=" + $("#couponId").val();
					window.location = url;
				});
				
				$("#markCouponCodesDiv").load("<%=basePath%>mark/coupon/queryMarkCouponCodes.do?couponId=${couponId}&_="+(new Date()).getTime(),function(){});	

			});
		</script>		

	</body>
</html>
