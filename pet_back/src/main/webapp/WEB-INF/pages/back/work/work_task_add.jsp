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
	</head>
	<body>
		<div class="iframe-content">
			<form id="trans_task"  action="<%=basePath %>work/order/create_task.do" method="post">
			<s:hidden name="workTaskId"/>
			<s:hidden name="sourceFlag"/>
			<p>任务信息</p>
			<div class="p_box">
				<table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label"  width="30%" rowspan="2">接收部门/人：</td>
						<td>
							<s:select name="receiveDept"  list="departmentList" listKey="workDepartmentId" listValue="departmentName" ></s:select>
						</td>
					</tr>
					<tr>
						<td>
							<s:select name="receiveGroup"  list="workGroupList"  listKey="workGroupId" listValue="groupName"></s:select><br>
							<div id="workGroupUserDiv">
							<s:iterator value="workGroupUserList" var="obj" status="L">
								<input type="radio" value="${obj.workGroupUserId }" name="receiveUser"> 
								<label>${obj.userName }/${obj.realName }</label><s:if test="leader=='true'"><span style="color:red">(leader)</span></s:if>
								<s:if test="(#L.index+1)%3==0 "><br></s:if>
							</s:iterator>
							</div>
							<s:hidden name="receiveGroupValue"/>
						</td>
					</tr>
					<tr>
						<td class="p_label">信息内容：</td>
						<td>
							<s:textarea name="taskContent" cols="2"></s:textarea>
						</td>
					</tr>
				</table>
				<p class="tc mt20">
					<button id="add_task_Btn" class="btn btn-small w5" type="button">提交</button>　
				</p>
			</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		<s:if test="workOrderType.limitReceiver=='SAME_DEPARTMENT'">
			$("#receiveDept").attr("disabled","true");
		</s:if>
		<s:if test="workOrderType.limitReceiver=='SAME_GROUP'">
			$("#receiveDept").attr("disabled","true");
			$("#receiveGroup").attr("disabled","true");
		</s:if>
		$("#receiveDept").change(function(){
			$("#receiveGroup").empty();
			$("#workGroupUserDiv").empty();
			$.post("<%=basePath%>/work/order/getWorkGroupByDeptId.do",{receiveDept:this.value},function(data){
				var first=null;
				$.each(data,function(i,e){
					if(i==0)first=e;
					var line="<option  value='"+ e.workGroupId +"'>"+e.groupName+"</option>";
					$("#receiveGroup").append(line);
				});
				if(first!=null){
					$.post("<%=basePath%>/work/order/getWorkGroupUserByGroupId.do",{receiveGroup:first.workGroupId,workStatus:'ONLINE'},function(data2){
						$.each(data2,function(i,e){
							var line="<input type='radio' name='receiveUser' value='"+e.workGroupUserId+"'>";
								if(e.leader=='true'){
									line+="<label>"+e.userName+"/"+e.realName+"</label><span class='red'>(leader)</span>";
								}else{
									line+="<label>"+e.userName+"/"+e.realName+"</label>";
								}
								if((i+1)%3==0)line+="<br>";
							$("#workGroupUserDiv").append(line);
						});
						$("input[name='receiveUser']").click(function(){
							checkGroupUser(this.value,$(this).next('label').html());
						});
					},"json");
				}
			},"json");
		});
		
		$("#receiveGroup").change(function(){
			$("#workGroupUserDiv").empty();
			$.post("<%=basePath%>/work/order/getWorkGroupUserByGroupId.do",{receiveGroup:this.value,workStatus:'ONLINE'},function(data){
				$.each(data,function(i,e){
					var line="<input type='radio' name='receiveUser' value='"+e.workGroupUserId+"'>";
					if(e.leader=='true'){
						line+="<label>"+e.userName+"/"+e.realName+"</label><span class='red'>(leader)</span>";
					}else{
						line+="<label>"+e.userName+"/"+e.realName+"</label>";
					}
					if((i+1)%3==0)line+="<br>";
					$("#workGroupUserDiv").append(line);
				});
				$("input[name='receiveUser']").click(function(){
					checkGroupUser(this.value,$(this).next('label').html());
				});
			},"json");
		});
		
		$("#trans_task").validate({
			rules:{
				"receiveDept":{
					required:true
				},
				"receiveGroup":{
					required:true
				}
			},
			messages:{
				"receiveDept":{
					required:"请选择部门"
				},
				"receiveGroup":{
					required:"请选择组织"
				}
			}
		});
		
		$("#add_task_Btn").click(function(){
			if($("#trans_task").valid()){
				$.post("create_task.do",{workTaskId:${workTask.workTaskId},taskContent:$("#taskContent").val(),sourceFlag:$("#sourceFlag").val(),
					receiveGroup:$("#receiveGroup").val(),receiveUser:$('input[name="receiveUser"]:checked').val()},function(data){
					  if("SUCCESS" == data.status){
						  alert("操作成功");
						  if(data.sourceFlag!='MONITOR'){
							  parent.parent.location.reload();
						  }else{
							  parent.location.reload();
						  }
					  }else{
						  alert("操作失败,无法转单。\r\n"+data.msg);
					  }
				  },'json');
			}
		});
		$("input[name='receiveUser']").click(function(){
			checkGroupUser(this.value,$(this).next('label').html());
		});
		function checkGroupUser(value,text){
			$.post("<%=basePath%>work/order/getWorkGroupUserInfo.do",{workUserId:value},function(retData){
				if(retData.flag=='FALSE' || (retData.flag=='SUCCESS' && retData.workStatus=='OFFLINE')){
					alert('('+text+')当前不在线，请发送其他人！')
					$.post("<%=basePath%>work/order/getWorkGroupUserByGroupId.do",{receiveGroup:$('#receiveGroup').val(),workStatus:'ONLINE'},function(data){
						$("#workGroupUserDiv").empty();
						$.each(data,function(i,e){
							var line="<input type='radio' name='receiveUser' value='"+e.workGroupUserId+"'>";
							if(e.leader=='true'){
								line+="<label>"+e.userName+"/"+e.realName+"</label><span class='red'>(leader)</span>";
							}else{
								line+="<label>"+e.userName+"/"+e.realName+"</label>";
							}
							if((i+1)%6==0)line+="<br>";
							$("#workGroupUserDiv").append(line);
						});
						$("input[name='receiveUser']").click(function(){
							checkGroupUser(this.value,$(this).next('label').html());
						});
					},"json");
				}
			},"json");
		}
	});
	</script>
</html>
