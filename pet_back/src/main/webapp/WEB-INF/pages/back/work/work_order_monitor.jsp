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
		<title>工单监控</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.jsonSuggest-2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/jquery-ui-timepicker-addon.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.jsonSuggest.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
	</head>
	<body>
		<form id="monitor_form" action="orderMonitor.do" method="post">
		<table class="p_table form-inline">
            <tbody>
                <tr>
                	<td class="p_label">工单编号：</td>
                    <td><input type="text" id="workOrderId" name="workOrderId" value="${workOrderId }"/></td>
                    <td class="p_label">订单号：</td>
                    <td><input type="text" id="orderId" name="orderId" value="${orderId }"/></td>
                     <td class="p_label">联系人手机：</td>
                    <td><input type="text" name="workOrderMobileNumber" value="${workOrderMobileNumber }"/></td>
                </tr>
                <tr>
                	<td class="p_label">工单类型：</td>
                    <td>
                    	<input type="hidden" id="workOrderTypeHd" name="workOrderType" value="${workOrderType}"/>
                    	<input type="hidden" id="workOrderTypeNameHd" name="workOrderTypeName" value="${workOrderTypeName}"/>
                    	<input type="text" id="workOrderTypeInput" value="${workOrderTypeName}"/>
                    </td>
					<td class="p_label">产品名称：</td>
                    <td>
                      <input type="text" name="productName" value="${productName }" id="productName"/>
		              <input type="hidden" name="productId" value="${productId }" id="productId"/>
                    </td>
					<td colspan="2">
							<input type="checkbox" name="completeTimeOutFlag" value="1"
								<s:if test="#request.completeTimeOutFlag == 1">checked = "checked"</s:if>
							/>处理超时工单
							<input type="checkbox"  name="emergencyFlag" value="PROMPTLY"
								<s:if test="#request.emergencyFlag == 'PROMPTLY'">checked = "checked"</s:if>
							/>紧急
							<input type="checkbox"  name="repeatFlag" value="REPEAT"
								<s:if test="#request.repeatFlag == 'REPEAT'">checked = "checked"</s:if>
							/>重复
					</td>
                </tr>
                <tr>
                	<td class="p_label">接收部门/组织/人：</td>
                    <td>
                    	<s:select name="receiveDept"  list="departmentList" listKey="workDepartmentId" listValue="departmentName"  headerKey="" headerValue="请选择"></s:select>
						<s:select name="receiveGroup"  list="workGroupList"  listKey="workGroupId" listValue="groupName"></s:select><br />
						<input type="text" name="receiveUser" value="${userName }" id="receiveUser" />
						<input type="hidden" name="workGroupId" id="workGroupId"/>
                    </td>
                    <td class="p_label">提交时间：</td>
                    <td colspan="3">
                        <input id="createTimeStartInput" type="text" class="datetime" name="limitTimeStart" value="${limitTimeStart}"/>&nbsp;至
                    	<input id="createTimeEndInput" type="text" class="datetime" name="limitTimeEnd" value="${limitTimeEnd}"/>
                    </td>
                </tr>
                <tr>
					<td colspan="6" style="text-align: center;">
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
						<th width="130px">工单编号</th>
						<th>任务编号</th>
						<th>订单号</th>
						<th>产品名称</th>
						<th>用户名</th>
						<th>联系人手机</th>
						<th>工单类型</th>
						<th>提交人</th>
						<th>接收组织/人</th>
						<th id="submitTime"  class="selected">提交时间<i class="css-arrow-up"></i>
							<input type="hidden" name="pageCount" id="pageCount" value="${page}"/>
						</th>
						<th id="limitTime"  class="selected">剩余处理时间<i  class="css-arrow-up"></i></th>
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
						<td>${item.createWorkGroupUser.userName}</td>
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
						</td>
						<td nowrap="nowrap">
							<a class="underline" href="javascript:void(0);" onclick="showHandler(${item.workTaskId})">查看</a>&nbsp;&nbsp;
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
			$("#receiveUser").val("${receiveUser}");//查询刷新后初始化文本框中的值
			$( "#createTimeStartInput" ).datetimepicker();
			$( "#createTimeEndInput" ).datetimepicker();
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
			//查询在线用户
			$('#receiveUser').jsonSuggest({
	    		url:'<%=basePath%>work/order/getWorkGroupUserByGroupId.do',
	    		maxResults: 10,
	    		param:[$("#receiveGroup")]
	    	})
		    $("#receiveDept").change(function(){
				$("#receiveGroup").empty();
				$("#receiveUser").val("");
				 if(this.value!=""){
					$.post("<%=basePath%>/work/order/getWorkGroupByDeptId.do",{receiveDept:this.value},function(data){
						var first=null;
						$.each(data,function(i,e){
							if(i==0)first=e;
							var line="<option  value='"+ e.workGroupId +"'>"+e.groupName+"</option>";
							$("#receiveGroup").append(line);
						});
					},"json");
				}
			});
			$("#receiveGroup").change(function(){
				$("#receiveUser").val("");
			});
		});
		
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
		//改变排序
		$(function(){ 
			var pageCnt = $("#pageCount").val();
			//按提交时间排序
			var submitTimeSort = "<%=request.getParameter("submitTimeSort")%>";
			if(submitTimeSort==null || submitTimeSort=="2" || submitTimeSort=="6" ){
				if(submitTimeSort!=null){
					$("th#limitTime").removeClass('selected');
				}
				if(submitTimeSort=="2"){
					$("th#submitTime").find('i').removeClass("css-arrow-up").addClass('css-arrow-down');
				}
			}
			$('th#submitTime').click(function(){
				$(this).addClass('selected').siblings('th#submitTime').removeClass('selected'); 
				if($(this).find('i').hasClass('css-arrow-up')){ 
					$("#monitor_form").attr("action","{basePath}/orderMonitor.do?submitTimeSort=2&page="+pageCnt);
					$("#monitor_form").submit();
				}else{
					$("#monitor_form").attr("action","{basePath}/orderMonitor.do?submitTimeSort=6&page="+pageCnt);
					$("#monitor_form").submit();
				} 
			}) 
			//按剩余处理时间和状态排序
			var limitTimeSort = "<%=request.getParameter("limitTimeSort")%>";
			if(limitTimeSort==null || limitTimeSort=="3" || limitTimeSort=="5" ){
				 if(limitTimeSort!=null){
					$("th#submitTime").removeClass('selected');
				 }
				 if(limitTimeSort=="5"){
					$("th#limitTime").find('i').removeClass("css-arrow-up").addClass('css-arrow-down');
				 }
			}
			$('th#limitTime').click(function(){ 
				$(this).addClass('selected').siblings('th#limitTime').removeClass('selected'); 
				if($(this).find('i').hasClass('css-arrow-up')){ 
					$("#monitor_form").attr("action","{basePath}/orderMonitor.do?limitTimeSort=5&page="+pageCnt);
					$("#monitor_form").submit();
				}else{
					$("#monitor_form").attr("action","{basePath}/orderMonitor.do?limitTimeSort=3&page="+pageCnt);
					$("#monitor_form").submit();
				} 
			}) 
		})
		
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
