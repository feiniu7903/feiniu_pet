<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>订单处理后台_修改订单价格审核</title>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css">
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
			<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
			<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript">
			function approveApplay(amountApplayId,status){
		  			var param = {'ordOrderAmountApplay.applayStatus':status,'ordOrderAmountApplay.amountApplayId':amountApplayId}
		  			$.ajax({type : "POST",dataType : "html",url : "<%=basePath%>ord/approveApplay.do",async : false,data :param,timeout : 3000,
						success : function(data) {
							document.getElementById("applayForm").submit();
						}
					})
		  		}
		</script>
	</head>
	<body>
		<div id="table_box">
					<!--=========================主体内容==============================-->
					<div class="table_box"  id=tags_content_1>
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>/op/opOrderList.do'>
                                 <table width="98%" border="0"  class="newfont06" style="font-size: 12; text-align: left;">
									<td>订单号：<input type="text" name="orderId" value="<s:property value="orderId"/>"/> </td>	
					<td>审核状态:
					<s:select list="#{'':'不限','UNVERIFIED':'未审核','PASS':'审核通过','FAIL':'不通过','CONFIRM':'已确定'}" name="ordOrderAmountApplay.applayStatus" listKey="key"
									listValue="value">
								</s:select>
 
					</td>
					<td>
						<input type="submit" value="查询">
					</td>
								</table>
							</form>
						</div>
							
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
								    <td height="35" width="9%" align="center"> 订单号 </td>
									<td height="35" width="5%" align="center"> 申请人 </td>
									<td height="35" width="5%" align="center"> 审核人 </td>
									<td width="10%" align="center"> 金额 </td>
									<td width="15%" align="center"> 类型 </td>
									<td width="4%" align="center"> 备注 </td>
									<td width="4%" align="center"> 状态 </td>
									<td width="4%" align="center"> 创建时间 </td>
									<td width="10%" align="center"> 操作</td>
								</tr>
								<s:iterator id="order" value="ordOrderAmountApplayList">
							<tr bgcolor="#ffffff">
								<td> <s:property value="orderId" /> </td>
								<td> <s:property value="applayUser" /> </td>
								<td> <s:property value="approveUser" /> </td>
								<td> <s:property value="amount" /> </td>
								<td> 类型 </td>
								<td> <s:property value="memo" /> </td>
								<td> <s:property value="applayStatus" /> </td>
								<td> <s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
								<td>
									<s:if test="!isApprovePass()">
										<a href="javascript:approveApplay('<s:property value="amountApplayId"/>','PASS')">审核通过</a>|
										<a href="javascript:approveApplay('<s:property value="amountApplayId"/>','FALL')">不通过</a>
									</s:if>
								</td>
							</tr>
						</s:iterator>
								<tr><td colspan="17"  bgcolor="#eeeeee"> </td></tr>
							</tbody>
						</table>
					</div>
					<!--=========================主体内容 end==============================-->
			<table width="98%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
		<!--main2 end-->
		
	</body>
</html>
