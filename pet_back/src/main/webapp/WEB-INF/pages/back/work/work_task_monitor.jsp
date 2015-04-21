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
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.jsonSuggest-2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.jsonSuggest.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
	</head>
	<body>
		<form id="monitor_form" action="monitorList.do" method="post">
		<table class="p_table form-inline">
            <tbody>
                <tr>
                	<td class="p_label">工单编号：</td>
                    <td><input type="text" id="workOrderId" name="workOrderId" value="${workOrderId }"/></td>
                    <td class="p_label">订单号：</td>
                    <td><input type="text" id="orderId" name="orderId" value="${orderId }"/></td>
					<td class="p_label">产品名称：</td>
                    <td>
                      <input type="text" name="productName" value="${productName }" id="productName"/>
		              <input type="hidden" name="productId" value="${productId }" id="productId"/>
                    </td>
                </tr>
                <tr>
                	<td class="p_label">工单类型：</td>
                    <td>
                    	<input type="hidden" id="workOrderTypeHd" name="workOrderType" value="${workOrderType}"/>
                    	<input type="hidden" id="workOrderTypeNameHd" name="workOrderTypeName" value="${workOrderTypeName}"/>
                    	<input type="text" id="workOrderTypeInput" value="${workOrderTypeName}"/>
                    </td>
					<td class="p_label">任务编号：</td>
					<td><input type="text" name="taskSeqNo" value="${taskSeqNo }"/></td>
					<td class="p_label">状态：</td>
                    <td>
                    	<select name="status">
                    		<option value=""></option>
                    		<option value="UNCOMPLETED" 
                    			<s:if test="#request.status == \"UNCOMPLETED\"">selected="selected"</s:if>
                    		>未完成</option>
                    		<option value="COMPLETED"
                    			<s:if test="#request.status == \"COMPLETED\"">selected="selected"</s:if>
                    		>已完成</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                	<td class="p_label">用户名：</td>
                    <td><s:textfield name="workOrderUserName"/>
                    </td>
                    <td class="p_label">联系人手机：</td>
                    <td><input type="text" name="workOrderMobileNumber" value="${workOrderMobileNumber }"/></td>
                    <td class="p_label">提交时间：</td>
                    <td>
                        <input id="createTimeStartInput" type="text" name="createTimeStart" value="${createTimeStart}"/>
                    	<input id="createTimeEndInput" type="text" name="createTimeEnd" value="${createTimeEnd}"/>
                    </td>
                </tr>
                <tr>
                	<td class="p_label">接收部门/组织/人：</td>
                    <td colspan="2">
                    <s:select name="receiveDept"  list="departmentList" listKey="workDepartmentId" listValue="departmentName"  headerKey="" headerValue="请选择"></s:select>
						<s:select name="receiveGroup"  list="workGroupList"  listKey="workGroupId" listValue="groupName"></s:select>
						<s:select name="receiveUser"  list="workGroupUserList"  listKey="userName" listValue="userName" headerKey="" headerValue=""></s:select>
                    </td>
                   
					<td>
					<label>
						<input type="checkbox" name="completeTimeOut" value="1"
							<s:if test="#request.completeTimeOut == 1">checked = "checked"</s:if>
						/>处理超时工单
					</label>
					</td>
					<td class="p_label">提交人：</td>
                    <td><input type="text" name="createUserName" value="${createUserName }"/></td>
                </tr>
                <tr>
                	<td colspan="6" style="text-align: right;">
						<input type="button" value="查询" style="height: 25px;width:60px;" onclick="query();"></input>
					</td>
                </tr>
            </tbody>
        </table>
        </form>
		<div class="">
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th width="130">工单编号</th>
						<th>任务编号</th>
						<th>订单号</th>
						<th>产品名称</th>
						<th>用户名</th>
						<th>联系人手机</th>
						<th>工单类型</th>
						<th>提交人</th>
						<th>接收组织/人</th>
						<th>提交时间</th>
						<th>剩余处理时间</th>
						<th>状态</th>
						<th nowrap="nowrap">操作</th>
					</tr>
					<s:iterator value="#request.workTaskPage.items" var="item">
					<tr>
						<td>G${item.workOrderId }
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
						<td>${item.taskSeqNo }</td>
						<td><a href="#" class="underline" onclick="javascript:showDetailDiv('historyDiv','${item.workOrder.orderId}');">${item.workOrder.orderId}</a></td>
						<td>${item.workOrder.productName}</td>
						<td>${item.workOrder.userName}</td>
						<td>${item.workOrder.mobileNumber}</td>
						<td>${item.workOrderType.typeName }</td>
						<td>${item.workOrder.createUserName}</td>
						<td>${item.workGroupUser.workGroupName}/${item.workGroupUser.userName }</td>
						<td>
							<s:date name="#request.item.createTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
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
							<a class="underline" href="javascript:void(0);" onclick="showHandler(${item.workTaskId})">查看</a>&nbsp;&nbsp;
<%-- 							<s:if test="#request.item.status == \"UNCOMPLETED\""> --%>
<%-- 								<a href="javascript:void(0);" onclick="transHandler(${item.workTaskId})">转单</a>&nbsp;&nbsp; --%>
<%-- 							</s:if>						 --%>
						</td>
					</tr>
					</s:iterator>
					<tr>
	    				<td colspan="1" align="right">总条数：${workTaskPage.totalResultSize}</td>
						<td colspan="12" align="right"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(#request.workTaskPage)"/></div></td>
   				    </tr>
				</table>
			</div>
		</div>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="/super_back/ord/showOrderDetailById.do">
		</div>
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                           0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
	<script type="text/javascript">
		$(function() {
			$( "#createTimeStartInput" ).datepicker();
			$( "#createTimeEndInput" ).datepicker();
			$("#monitor_form").validate({
				rules:{
					"taskSeqNo":{
						maxlength:6,
						digits:true
					}
				},
				messages:{
					"taskSeqNo":{
						digits:"请输入有效任务编号",
						maxlength:"请输入有效任务编号"
					}
				}
			});
			
			
		    $('#productName').jsonSuggest({
				url:'<%=basePath%>work/order/queryProductList.do',
				maxResults: 10,
				minCharacters: 2,
				onSelect: function(obj){
					$("#productId").val(obj.id);
				}
			});
		    
		    $("#receiveDept").change(function(){
				$("#receiveGroup").empty();
				$("#receiveUser").empty();
				 if(this.value!=""){
					$.post("<%=basePath%>/work/order/getWorkGroupByDeptId.do",{receiveDept:this.value},function(data){
						var first=null;
						$.each(data,function(i,e){
							if(i==0)first=e;
							var line="<option  value='"+ e.workGroupId +"'>"+e.groupName+"</option>";
							$("#receiveGroup").append(line);
						});
						receiveUser($("#receiveGroup").val());
					},"json");
				}
			});
		    
		    $("#receiveGroup").change(function(){
		    	receiveUser(this.value);
		    });
		});
		
		function receiveUser(receiveGroup){
			$("#receiveUser").empty();
			 if(receiveGroup!=""){
				$.post("<%=basePath%>work/order/getWorkGroupUserByGroupId.do",{receiveGroup:receiveGroup,workStatus:'ALL'},function(data){
					var first=null;
					if(data.length>0){
					 $("#receiveUser").append("<option  value=''>请选择</option>");
					}
					$.each(data,function(i,e){
						if(i==0)first=e;
						 var line="<option  value='"+ e.userName +"'>"+e.userName+"</option>";
						$("#receiveUser").append(line);
					});
				},"json");
			}
		}
		
		function showHandler(workTaskId){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "查看任务",
		        position: 'center',
		        width: 920, 
		        height: 420
			}).width(900).height(400).attr("src","work_task_detail.do?workTaskId="+workTaskId);
		}
		$("#workOrderTypeInput").autocomplete({
			source: function(request,response){
				$.post("get_work_order_type_list.do",
						{typeName: request.term},
						function( data ) {
							response( $.map( data, function( item ) {
								return {
									value:item.typeName,
									workOrderTypeId:item.workOrderTypeId
								};
							}));
						},
				"json");
			},
			minLength: 1,
			select: function( event, ui ) {
				$("#workOrderTypeHd").val(ui.item.workOrderTypeId).attr("label",ui.item.value);
				$("#workOrderTypeNameHd").val(ui.item.value);
			}
		}).focusout(function(){
			if($.trim($(this).val()) == ""){
				$("#workOrderTypeHd").val("").attr("label","");
				$("#workOrderTypeNameHd").val("");
			}else{
				$(this).val($("#workOrderTypeHd").attr("label"));
			} 
		});
		
		function transHandler(workTaskId){
			var url="<%=basePath%>/work/order/transition.do?sourceFlag=MONITOR&workTaskId="+workTaskId;
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "转单",
		        position: 'center',
		        width: 670, 
		        height: 380
			}).width(650).height(360).attr("src",url);
		}
		
		function query() {
			var reg = /^[A-Za-z0-9]+$/; 
			var workOrderId = $("#workOrderId").val();
			if(workOrderId != "" && !reg.test(workOrderId)){
				alert("输入工单编号格式错误");
				return;
			}
			var orderId = $("#orderId").val();
			if(orderId != "" && !reg.test(orderId)){
				alert("输入订单号格式错误");
				return;
			}
			$("#monitor_form").submit();
		}
	</script>
	</body>
</html>
