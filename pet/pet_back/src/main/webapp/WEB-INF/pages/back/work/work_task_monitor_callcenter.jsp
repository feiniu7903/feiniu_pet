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
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.jsonSuggest-2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.ui.all.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.jsonSuggest.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
	</head>
	<body>
		<form action="monitor_callcenter.do" method="post">
			<s:hidden name="status"/>
			<s:hidden name="workOrderMobileNumber"/>
        </form>
		<div class="">
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>用户名</th>
						<th>联系人手机</th>
						<th>订单号</th>
						<th>产品名称</th>
						<th>提交人</th>
						<th>提交时间</th>
						<th>接收组织/人</th>
						<th width="120px">工单编号</th>
						<th>工单类型</th>
						<th>剩余处理时间</th>
						<th>状态</th>
						<th nowrap="nowrap">操作</th>
					</tr>
					<s:iterator value="#request.workTaskPage.items" var="item">
					<tr>
						<td>${item.workOrder.userName}</td>
						<td>${item.workOrder.mobileNumber}</td>
						<td><a href="#" onclick="javascript:showOrderDetailWin('/super_back/ord/showOrderDetailById.do?orderId=${item.workOrder.orderId}');">${item.workOrder.orderId}</a></td>
						<td>${item.workOrder.productName}</td>
						<td>${item.createWorkGroupUser.userName}</td>
						<td>
							<s:date name="#request.item.createTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>${item.workGroupUser.workGroupName}/${item.workGroupUser.userName }</td>
						<td class="jiaTag"><a href="javascript:showHandler(${item.workTaskId});">G${item.workOrderId }</a>
							<s:if test="#item.workOrder.processLevel==\"PROMPTLY\"">
								<span class="jinJi">紧急</span>
							</s:if>
							<s:if test="#item.workOrder.processLevel==\"REPEAT_PROMPTLY\"">
								<span class="jinJi">紧急</span><span class="chongFu">重复</span>
							</s:if>
							<s:if test="#item.workOrder.processLevel==\"REPEAT\"">
								<span class="chongFu">重复</span>
							</s:if>
						</td>
						<td>${item.workOrderType.typeName }</td>
						<td>
							<s:if test="#request.item.status == \"UNCOMPLETED\"">
								<span <s:if test="#item.workOrder.limitTimeNow lt 0"> style="color:red;" </s:if>>
									${item.workOrder.limitTimeNowStr}
								</span>
							</s:if>
						</td>
						<td>
							<s:if test="#request.item.status == \"UNCOMPLETED\"">未完成</s:if>
							<s:if test="#request.item.status == \"COMPLETED\"">已完成</s:if>
						</td>
						<td nowrap="nowrap">
							<s:if test="#item.workOrder.processLevel!=\"REPEAT_PROMPTLY\"">
								<s:if test="#item.workOrder.processLevel!=\"PROMPTLY\"">
									<label class="jiaJJ"><input  type="checkbox" name="processLevel" data="${item.workOrderId }" value="PROMPTLY" />紧急&nbsp;</label>
								</s:if>
								<s:if test="#item.workOrder.processLevel!=\"REPEAT\"">
									<label class="jiaCF"><input  type="checkbox" name="processLevel" data="${item.workOrderId }" value="REPEAT" />重复&nbsp;</label>
								</s:if>
							</s:if>
						</td>
					</tr>
					</s:iterator>
					<tr>
	    				<td colspan="1" align="right">总条数：${workTaskPage.totalResultSize}</td>
						<td colspan="12" align="right"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(#request.workTaskPage)"/></div></td>
   				    </tr>
				</table>
			</div>
			
			<p class="tc mt20">
			<button id="submitBtn" class="btn btn-small w5" type="button">提交</button>　
		</div>
		<script type="text/javascript">
			function showHandler(workTaskId){
				$("<iframe frameborder='0' ></iframe>").dialog({
					autoOpen: true, 
			        modal: true,
			        title : "查看任务",
			        position: 'center',
			        width: 720, 
			        height: 420
				}).width(700).height(400).attr("src","work_task_detail.do?workTaskId="+workTaskId);
			}
			function showOrderDetailWin(url){
				$("<iframe frameborder='0' ></iframe>").dialog({
					autoOpen: true, 
			        modal: true,
			        title : "订单信息",
			        position: 'top',
			        width: 965, 
			        height: 550
				}).width(940).height(540).attr("src",url);
			}
		$(document).ready(function(){
			$('#submitBtn').click(function(){
				var processLevelArr="";
				$("input:checked[name='processLevel']").each(function(i,e){
					if(processLevelArr!="")processLevelArr+="&";
					processLevelArr+="processLevelArr="+$(e).attr('data')+","+e.value;
				});
				if(processLevelArr==""){
					parent.window.closePopWin();
					return;
				}
				var url="/pet_back/work/order/changeProcessLevel.do?"+processLevelArr;
				$.getJSON(url,function(data){
	 			  	if("SUCCESS" == data.status){
		 				alert("操作成功");
		 				parent.window.closePopWin();
	 			  	}else{
	 				  	alert("操作失败\r\n"+data.msg);
	 			  	}
	 		  	});
			});
			$('.jiaCF input').click(function(){
				var chongfu = '<span class="chongFu">重复</span>'
				var $jiaTag = $(this).parents('td').siblings('.jiaTag');
				$jiaTag.find('.chongFu').remove();
				if(this.checked){
					$jiaTag.append(chongfu);
				}
			});
			$('.jiaJJ input').click(function(){
				var jinji   = '<span class="jinJi">紧急</span>'
				var $jiaTag = $(this).parents('td').siblings('.jiaTag');
				$jiaTag.find('.jinJi').remove();
				if(this.checked){
					$jiaTag.append(jinji);
				}
			});	
		});
	</script>
	</body>
</html>
