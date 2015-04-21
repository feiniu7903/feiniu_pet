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
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/util.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
	</head>
	<body>
		<div class="iframe-content">
			<p class="lead">工单信息</p>
			<div class="p_box">
				<table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label"  width="20%">工单类型：</td>
						<td width="30%">
							${workTask.workOrderType.typeName}
						</td>
						<td class="p_label"  width="20%" rowspan="3">工单内容：</td>
						<td width="30%" rowspan="3">
							${workTask.workOrder.content}
						</td>
					</tr>
					<tr>
					    <td class="p_label" >订单号：</td>
						<td>
							${workTask.workOrder.orderId}
						</td>
					</tr>
					<tr>
					    <td class="p_label" >产品名称：</td>
						<td>
							${workTask.workOrder.productName}
						</td>
					</tr>
					<tr>
					      <td class="p_label">用户名：</td>
						<td>
							${workTask.workOrder.userName}
						</td>
						<td class="p_label"  rowspan="1">手机号：</td>
						<td>
							${workTask.workOrder.mobileNumber}
						</td>
					</tr>
					<tr>
					      <td class="p_label">邮箱：</td>
						<td>
							${workTask.workOrder.userUser.email}
						</td>
						<td class="p_label">创建时间：</td>
						<td>
							<s:date name="workTask.workOrder.createTime" format="yyyy-MM-dd HH:mm" />
						</td>
					</tr>
				</table>
			</div>
			<p class="lead">任务信息</p>
			<div class="p_box">
				<table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label"  width="20%">任务编号：</td>
						<td width="30%">
							${workTask.taskSeqNo}
							<s:hidden name="workTaskId"/>
						</td>
						<td class="p_label"  width="20%">接收部门/人：</td>
						<td width="30%">
							${workTask.workGroupUser.departmentName} - ${workTask.workGroupUser.workGroupName} - ${workTask.workGroupUser.userName}/${workTask.workGroupUser.realName}
						</td>
					</tr>
					<tr>
						<td class="p_label" >处理时限：</td>
						<td>
							${workTask.workOrder.limitTime} 分钟
						</td>
						<td class="p_label" rowspan="3">内容：</td>
						<td rowspan="3">
							${workTask.content}
						</td>
					</tr>
					<tr>
						<td class="p_label" rowspan="1">提交人：</td>
						<td>
							<s:if test="#request.workTask.createrName == null">
								系统
							</s:if>
							<s:else>
								${workTask.createrName}
							</s:else>
						</td>
					</tr>
					<tr>
						<td class="p_label"  rowspan="1">提交时间：</td>
						<td>
							<s:date name="workTask.createTime" format="yyyy-MM-dd HH:mm" />
						</td>
					</tr>
				</table>
		</div>
			<p>
				<input type="button" class="btn btn-small w5" value="任务处理" onclick="processHandler('${workTask.workOrder.url}')"/>
				&nbsp;&nbsp;&nbsp;剩余处理时限 <span <s:if test="workTask.remainTime<0">style='color:red;'</s:if>> ${workTask.workOrder.limitTimeNow} </span>分钟
			</p>

			<p class="tc mt20">
				<button id="transBtn" class="btn btn-small w5" type="button">转单</button>　
				
				<s:if test="workTask.showComplete=='true'">	
					<s:if test="workTask.complete=='anyone'">
						<button id="closeBtn" class="btn btn-small w5" type="button">完成</button>
					</s:if>
				</s:if>
				<s:if test="workTask.showFeedback=='true'">
					<s:if test="workTask.complete=='self'">
						<button id="replyBtn" class="btn btn-small w5" type="button">回复</button>
					</s:if>
				</s:if>
			</p> 
			<p class="lead"><a id="process_func" style="cursor: pointer;">查看任务处理过程</a></p>
			<div class="p_box" id="process_div" style="display:none;">
				<table class="p_table form-inline" width="100%">
					<tr>
						<th width="10%">任务编号</th>
						<th width="10%">状态</th>
						<th width="10%">发送人</th>
						<th width="10%">接收组织/人</th>
						<th width="15%">内容</th>
						<th width="15%">回复内容</th>
						<th width="10%">创建时间</th>
						<th width="10%">完成时间</th>
						<th width="10%">处理时间</th>
					</tr>
					<s:iterator value="workTaskList" var="item">
						<tr>
							<td>${item.taskSeqNo}</td>
							<td><s:property value="@com.lvmama.comm.vo.Constant$WORK_TASK_STATUS@getCnName(status)" /></td>
							<td>${item.createrName}</td>
							<td>${item.workGroupUser.departmentName} - ${item.workGroupUser.workGroupName} - ${item.workGroupUser.userName}/${item.workGroupUser.realName}</td>
							<td>${item.content}</td>
							<td>${item.replyContent}</td>
							<td><s:date name="createTime" format="yyyy-MM-dd HH:mm" /></td>
							<td><s:date name="completeTime" format="yyyy-MM-dd HH:mm"  nice="createTime"/></td>
							<td>${item.useTime}</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
		<div id="finishDiv" style="display:none;">
			<ul>
				<li>
					接收部门/组长/成员：<span id="createInfoSpan"></span>
				</li>
				<li>
					<br/>
					信息内容：
					<s:textarea id="taskContent" name="taskContent"></s:textarea>
				</li>
				<s:if test="workOrderType.typeCode=='cjxl'">
					<li>
						<input type="checkbox" id="noResource" name="noResource" value="0" />资源状态不满足
					</li>
				</s:if>
				<s:elseif test="workOrderType.typeCode=='cxxl'">
					<li>
						<input type="checkbox" id="noResource" name="noResource" value="0" />资源状态不满足
					</li>
				</s:elseif>
			</ul>
		</div>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#noResource").click(function(){
			var v = $("#noResource").attr("checked");
			if (v == true) {
				var oldContent = $("#taskContent").val();
				$("#taskContent").val("资源状态不满足\n" + oldContent);
			}
		});
		
		$("#transBtn").click(function(){
			var url="<%=basePath%>/work/order/transition.do?workTaskId="+${workTask.workTaskId};
			transTask(url);
		});
		$("#process_func").click(function(){
			$("#process_div").slideToggle(500);
		})
	});
	$("#closeBtn").click(function(){
		$( "#finishDiv" ).dialog({
			title:'完成',
			buttons: [ { text: "提交", 
				click: function() { 
			 		$.post("finish.do",{workTaskId:${workTask.workTaskId},taskContent:$("#taskContent").val()},function(data){
		 			  	if("SUCCESS" == data.status){
			 				  alert("操作成功");
			 				  parent.location.reload();
		 			  	}else{
		 				  	alert("操作失败\r\n"+data.msg);
		 			  	}
		 		  	},'json');
				} 
			} ]
		});
	});
	$("#replyBtn").click(function(){
		<s:if test="firstCreateUser!=null">
			var createInfo="${firstCreateUser.departmentName}/${firstCreateUser.workGroupName}/${firstCreateUser.userName}";
			$("#createInfoSpan").html(createInfo);
		</s:if>
		$( "#finishDiv" ).dialog({
			title:'回复',
			buttons: [ { text: "提交", 
				click: function() { 
			 		$.post("reply.do",{workTaskId:${workTask.workTaskId},taskContent:$("#taskContent").val()},function(data){
		 			  	if("SUCCESS" == data.status){
			 				  alert("操作成功");
			 				  $("#createInfoSpan").empty();
			 				  parent.location.reload();
		 			  	}else{
		 				  	alert("操作失败\r\n"+data.msg);
		 				  	$("#createInfoSpan").empty();
		 			  	}
		 		  	},'json');
				} 
			} ]
		});
	});
	function transTask(url){
		$("<iframe frameborder='0' ></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "转单",
	        position: 'center',
	        width: 660, 
	        height: 300
		}).width(650).height(320).attr("src",url);
	}
	function processHandler(url, permId){
		if(url==null || url=='null' || $.trim(url)==''){
			alert("没有任务需要处理");
		}else{
			if(url.indexOf("?") > -1) {
				url += "&permId="+permId;
			} else {
				url += "?permId="+permId;
			}
			url+="&workTaskId="+$("#workTaskId").val();
			parent.popupWin(url);
		}
	}
// 	回复内容
	</script>
</html>
