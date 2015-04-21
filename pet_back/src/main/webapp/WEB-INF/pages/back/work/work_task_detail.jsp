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
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
	</head>
	<body>
		<p class="lead">工单信息：</p>
		<div class="box_content center-item">
	        <table class="p_table J_additem_box">
	            <tbody>
	                <tr>
	                    <td width="150" class="p_label">工单类型：</td>
						<td width="250">${workTask.workOrderType.typeName }</td>
						<td width="150" class="p_label">联系人手机：</td>
						<td width="250">${workTask.workOrder.mobileNumber}</td>
					</tr>
					<tr>
	                    <td class="p_label">工单编号：</td>
						<td >G${workTask.workOrderId }
							<s:if test="#request.workTask.workOrder.processLevel==\"PROMPTLY\"">
								<span class="jinJi">紧急</span>
							</s:if>
							<s:if test="#request.workTask.workOrder.processLevel==\"REPEAT_PROMPTLY\"">
								<span class="jinJi">紧急</span><span class="chongFu">重复</span>
							</s:if>
							<s:if test="#request.workTask.workOrder.processLevel==\"REPEAT\"">
								<span class="chongFu">重复</span>
							</s:if>
						</td>
						<td class="p_label">发送人/备选人：</td>
						<td >${workTask.workOrder.createUserName}<s:if test="#request.workTask.workOrder.agentUserName !=null">/${workTask.workOrder.agentUserName}</s:if>
						</td>
					</tr>
					<tr>
	                	<td class="p_label" rowspan="1">产品名称：</td>
						<td rowspan="1">${workTask.workOrder.productName}</td>
	                	<td class="p_label">处理时限：</td>
						<td >${workTask.workOrder.limitTime}分钟</td>
	                </tr>
	                <tr>
	                	<td class="p_label">订单号：</td>
						<td >${workTask.workOrder.orderId }</td>
	                	<td class="p_label">创建时间：</td>
						<td ><s:date name="#request.workTask.workOrder.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
	                </tr>
	                <tr>
	                	<td class="p_label">用户名：</td>
						<td >${workTask.workOrder.userName}</td>
						<td class="p_label">邮箱：</td>
						<td >${workTask.workOrder.userUser.email}</td>
	                </tr>
	                <tr>	
						 <td width="140" class="p_label">内容：</td>
						<td width="160"  colspan="3">${workTask.workOrder.content}</td>
	                </tr>
	            </tbody>
	        </table>
        </div>
        <p class="lead">任务信息：</p>
		<div class="box_content center-item">
	        <table class="p_table J_additem_box">
	            <tbody>
	                <tr>
	                    <td width="150" class="p_label">任务编号：</td>
						<td width="250">${workTask.taskSeqNo}</td> 
						<td  width="150" class="p_label">提交人：</td>
						<td width="250">${workTask.createWorkGroupUser.userName }</td>
	                </tr>
	                <tr>
						<td  class="p_label">提交时间：</td>
						<td ><s:date name="#request.workTask.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td width="140" class="p_label">接收组织/人：</td>
						<td width="160">${workTask.workGroupUser.workGroupName}/${workTask.workGroupUser.userName }</td>
	                </tr>
	                <tr>
	                    <td  class="p_label">特殊要求：</td>
						<td  colspan="3">${workTask.content }</td>
	                </tr>
	                <tr>
	                    <td  class="p_label">回复内容：</td>
						<td  colspan="3">${workTask.replyContent }</td>
	                </tr>
	            </tbody>
	        </table>
        </div>
        <p class="lead">任务处理过程：</p>
       	<table class="p_table table_info">
        	<tbody>
	        	<tr>
	               <td width="50">任务编号</td>
	               <td width="50">状态</td>
	               <td>发送人</td>
	               <td>接收组织/人</td>
	               <td>内容</td>
	               <td>回复内容</td>
	               <td>创建时间</td>
	               <td>完成时间</td>
	               <td>实际处理时间</td>
	            </tr>
            </tbody>
            <tbody>
            	<s:iterator value="#request.workTaskList" var="item">
	            <tr>
	               <td>${item.taskSeqNo }</td>
	               <td>
	               		<s:if test="#request.item.status == \"UNCOMPLETED\"">未完成</s:if>
						<s:if test="#request.item.status == \"COMPLETED\"">已完成</s:if>
					</td>
	               <td>${item.createWorkGroupUser.userName }</td>
	               <td>${item.workGroupUser.workGroupName}/${item.workGroupUser.userName }</td>
	               <td>${item.content }</td>
	               <td>${item.replyContent }</td>
	               <td><s:date name="#request.item.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
	               <td><s:date name="#request.item.completeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
	               <td>${item.usedTimeStr}</td>
	            </tr>
	            </s:iterator>
           	</tbody>
       	</table>
	</body>
</html>
