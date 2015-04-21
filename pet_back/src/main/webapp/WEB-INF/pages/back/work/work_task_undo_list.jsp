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
		<title>待处理工单</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.jsonSuggest.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
		
	</head>
	<body>
		<div class="ui_title">
 		<s:if test="#request.loginFrom == \"LVCC\"">
			<p style=" position:fixed;top:0;right:10px;z-index:60">
				<a href="/pet_back/work/order/add.do" target="_blank" class="btn btn-mini">新增工单</a>  
				<a href="/pet_back/index.do" target="_blank" class="btn btn-mini">后台首页</a>
			</p>
 		</s:if>
		<ul class="ui_tab">
			<li class="active"><a href="undo_list.do">待处理工单</a></li>
			<li class=""><a href="my_list.do">全部工单</a></li>
		</ul>
		</div>
		<form id="searchForm" action="undo_list.do" method="post">
		<table class="p_table form-inline">
            <tbody>
                <tr>
                    <td class="p_label" width="70">工单类型：</td>
                    <td>
                    	<input type="hidden" id="workOrderTypeHd" name="workOrderType" value="${workOrderType}"/>
                    	<input type="hidden" id="workOrderTypeNameHd" name="workOrderTypeName" value="${workOrderTypeName}"/>
                    	<input type="text" id="workOrderTypeInput" value="${workOrderTypeName}" class="searchInput"/>
                    </td>
					<td>
						<input type="button" onclick="submitHandler()" value="查询" style="height: 25px;width: 50px"></input>
					</td>
                </tr>
            </tbody>
        </table>
        </form>
		<div class="iframe-content">
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th width="120px">工单编号</th>
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
						<td>${item.taskSeqNo}</td>
						<td><a href="#" class="underline" onclick="javascript:showDetailDiv('historyDiv','${item.workOrder.orderId}');">${item.workOrder.orderId}</a></td>
						<td>${item.workOrder.productName }</td>
						<td>${item.workOrder.userName}</td>
						<td>${item.workOrder.mobileNumber}</td>
						<td>${item.workOrderType.typeName }</td>
						<td>
							<s:if test="#request.item.createWorkGroupUser==null">
								系统
							</s:if>
							<s:else>
								${item.createWorkGroupUser.userName}
							</s:else>
						</td>
						<td>${item.workGroupUser.workGroupName}/${item.workGroupUser.userName }</td>
						<td>
							<s:date name="#request.item.createTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<span <s:if test="#item.workOrder.limitTimeNow lt 0"> style="color:red;" </s:if> class="limitTimeClass" limitTime="${item.workOrder.limitTimeNow}">${item.workOrder.limitTimeNowStr }</span>
						</td>
						<td nowrap="nowrap">

							<a href="javascript:void(0);" onclick="doTaskHandler('<%=basePath%>work/order/process.do?permId=${permId }&workTaskId=${item.workTaskId}')">处理</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
	</body>
	<script type="text/javascript">
		//计时
		/* var c = 0;
		function timeOutHandler(){alert('1')
			$(".limitTimeClass").each(function(){
				var time = parseFloat($(this).attr("limitTime")) - c;
				if(time < 0){
					time=Math.abs(time);
					var text=parseInt(time/60) + "小时" + (time%60) + "分钟";
					$(this).text("-"+text);
					$(this).attr("style","color:red;");
					return ;
				}else{
					$(this).text(parseInt(time/60) + "小时" + (time%60) + "分钟");
				}
				c++;
			});
		};
		var timer = window.setInterval("timeOutHandler()",1000*60); */
		
		//刷新数据
		var dataVersion = "${dataVersion}";
		function refreshData(){
			$.post("get_data_version.do",function(data){
				/* if(parseFloat(data) != parseFloat(dataVersion)){
					window.location.href = "undo_list.do";
				} */
				window.location.href = "undo_list.do";
			});
		}
		var timer2 = window.setInterval("refreshData()",1000*60);
		
		//处理task
		function doTaskHandler(url){
        	window.clearInterval(timer2);
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "处理任务",
		        position: 'center',
		        width: 1020, 
		        height: 600,
		        close:function(){
		        	timer2 = window.setInterval("refreshData()",1000*30);
		        }
			}).width(1000).height(580).attr("src",url);
		}
		
		function popupWin(url){
			$("<iframe frameborder='0' ></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "处理任务",
		        position: 'top',
		        width: 950, 
		        height: 550
			}).width(940).height(540).attr("src",url);
		}

		$("#workOrderTypeInput").autocomplete({
			source: function(request,response){
				$.ajax({
					url: "get_work_order_type_list.do",
					dataType: "json",
					type:'POST',
					data:{
						typeName: request.term
					},
					success: function( data ) {
						response( $.map( data, function( item ) {
							return {
								value:item.typeName,
								workOrderTypeId:item.workOrderTypeId
							};
						}));
					}
				});
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
		function submitHandler(){
			$("#searchForm").submit();
		}
	</script>
</html>
