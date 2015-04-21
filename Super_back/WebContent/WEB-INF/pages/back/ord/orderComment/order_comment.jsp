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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>点评管理_订单点评返现管理</title>
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
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript">
			
		</script>
	</head>
	<body>
		<div id="table_box">
					<!--=========================主体内容==============================-->
					<div class="table_box"  id=tags_content_1>
						<div class="mrtit3">
							<form id='applyForm' method='post'
								action='<%=basePath%>orderComment/orderCommentList.do'>
						<table width="60%" border="0" class="newfont06"
							style="font-size: 12; text-align: left;">
							<tr>
							<td>
								订单号：
								<input type="text" name="orderId"
									value="<s:property value="orderId"/>" />
							</td>
							<td>
								<input type='submit' name="btnOrdListQuery" value="查 询" class="right-button08" />
							</td>
							</tr>
						</table>
					</form>
						</div>
							
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="100%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
								    <td height="35" width="10%" align="center"> 订单号 </td>
                                    <td height="35" width="10%" align="center"> 用户名 </td>
                                    <td height="35" width="10%" align="center"> 返现金额 </td>
									<td height="35" width="10%" align="center"> 点评ID </td>
                                    <td height="35" width="10%" align="center"> 点评时间 </td>
									<td width="8%" align="center"> 操作</td>
								</tr>

								<s:iterator id="orderAmount" value="orderCommentlist">
							     <tr bgcolor="#ffffff">
								<td align="center"> <s:property value="orderId" /> </td>
								<td align="center"> <s:property value="userName" /> </td>
								<td align="center"> <s:property value="cashRefundFloat" />  </td>
								<td align="center"> <s:property value="commentId" />  </td>
								<td align="center"> <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />  </td>
							
								<td align="center"><a href="<%=basePath%>orderComment/cashOrder.do?orderId=<s:property value="orderId"/>">返现</a> </td>
							</tr>
					            </s:iterator>
								
							</tbody>
						</table>
					</div>
					<!--=========================主体内容 end==============================-->
		<!--main2 end-->
		
		
		
	</body>
</html>