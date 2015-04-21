<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="p_box">
	<table class="p_table form-inline" width="100%">
		<tr>
			<td class="p_label"  width="10%">工单类型<span class="red">*</span>：</td>
			<td width="25%">
				<s:select name="workOrderTypeId" list="workOrderTypeList" listKey="workOrderTypeId" listValue="typeName" headerKey="" headerValue="请选择"></s:select>
			</td>
			<td class="p_label" width="10%">联系人手机<s:if test="workOrderType.paramUserName=='true'"><span class="red">*</span></s:if>：</td>
		     <td width="20%">
		         <input type="text" name="mobileNumber" id="mobileNumber" value="${mobileNumber}"/>
		     </td>
			<td class="p_label" width="10%">处理时限：</td>
		     <td width="25%">
		         <s:textfield name="limitTime"/> 分钟
		     </td>
		</tr>
		<tr>
			<td class="p_label">发送组织：</td>
			<td >
				<s:select name="createWorkGroup" list="createWorkGroupUserList" listKey="workGroupId" listValue="workGroupName"></s:select>
			</td>
		     <td class="p_label" >订单号<s:if test="workOrderType.paramOrderId=='true'"><span class="red">*</span></s:if>：</td>
		     <td>
		         <input type="text" name="orderId" id="orderId" value="${orderId }"/>	
		     </td>
		     <td class="p_label">产品名称<s:if test="workOrderType.paramProductId=='true'"><span class="red">*</span></s:if>：</td>
		     <td>
		         <input type="text" name="productName" id="productName" value="${productName }"/>
		         <input type="hidden" name="productId" id="productId" value="${productId }">
		     </td>
		</tr>
		<s:if test="workOrderType.useAgent=='true'">
			<tr>
			     <td class="p_label">增加备选人：</td>
			     <td colspan="5">
					<div id="agentUserDiv">	
					</div>			     
			     </td>
			</tr>
		</s:if>
		<tr>
		     <td class="p_label">工单内容<span class="red">*</span>：</td>
		     <td colspan="5">
		         <s:textarea name="content" style="width: 85%; height: 100px;"></s:textarea>
		     </td>
		</tr>
	</table>
</div>
<p>任务信息</p>
<div class="p_box">
	<table class="p_table form-inline" width="100%">
		<tr>
			<td class="p_label"  width="10%">接收部门/人：</td>
			<td>
				<s:select name="receiveDept"  list="departmentList" listKey="workDepartmentId" listValue="departmentName" ></s:select>
				<s:select name="receiveGroup"  list="workGroupList"  listKey="workGroupId" listValue="groupName"></s:select><br>
				<div id="workGroupUserDiv">
				<s:iterator value="workGroupUserList" var="obj" status="L">
					<input type="radio" value="${obj.workGroupUserId }" name="receiveUser"> 
					<label>${obj.userName }/${obj.realName }<s:if test="leader=='true'"><span style="color:red">（leader）</span></s:if></label>
					<s:if test="(#L.index+1)%6==0 "><br></s:if>
				</s:iterator>
				</div>
				<s:hidden name="receiveGroupValue"/>
			</td>
		</tr>
		<tr>
			<td class="p_label">紧急状态：</td>
			<td>
				<s:checkbox name="processLevel" id="processLevel"/>紧急
			</td>
		</tr>
		<tr>
			<td class="p_label">特殊要求：</td>
			<td>
				<s:textarea name="taskContent" style="width: 85%; height: 100px;"></s:textarea>
			</td>
		</tr>
	</table>
	<p class="tc mt20">
		<button id="saveBtn" class="btn btn-small w5" type="submit">提交</button>　
	</p>
</div>
<script>
	$(document).ready(function(){
		<s:if test="workOrderType.receiverEditable=='false'">
 			$("#receiveDept").attr("disabled","true");
 			$("#receiveGroup").attr("disabled","true");
		</s:if>
		$('#productName').jsonSuggest({
			url:'queryProductList.do',
			maxResults: 10,
			minCharacters: 2,
			onSelect: function(obj){
				$("#productId").val(obj.id);
			}
		});
		
		$("#processLevel").click(function(){
			var checked = $("#processLevel").attr("checked");
			if (true==checked) {
				var workOrderTypeName = $("#workOrderTypeId").find("option:selected").text();
				if (workOrderTypeName == '长线线路单' ||
						workOrderTypeName == '长线工单' ||
						workOrderTypeName == '出境线路单' ||
						workOrderTypeName == '出境工单') {
					$.post("<%=basePath %>work/order/setupLimitTime.do",function(data){
						$("#limitTime").val(data.time);
					},'json');
				}
			}
		});
// 		$('#mobileNumber').jsonSuggest({
// 			url:'queryUserList.do',
// 			maxResults: 10,
// 			minCharacters: 4,
// 			onSelect: function(obj){
// 				alert(obj.data);
// 				alert(obj.extra);
// 			}
// 		});
		/* $("#mobileNumber").autocomplete({
			source: function(request,response){
				$.post("queryUserList.do",
						{search: request.term},
						function( data ) {
							response( $.map( data, function( item ) {
								return {
									value:item.id
								};
							}));
						},
				"json");
			},
			minLength:3
		}); */
		
		$("#workOrderTypeId").change(function(){
			var params={
					workOrderTypeId:this.value,
					orderId:'${ orderId}',
					productId:'${ productId}',
					mobileNumber:'${ mobileNumber}'
				};
			$.post("<%=basePath%>work/order/add_inner.do",params,function(data){
				$("#orderId").rules("remove");
				$("#productId").rules("remove");
				$("#mobileNumber").rules("remove");
				$("#add_inner").html(data);
			},"html");
		});
		$("#receiveDept").change(function(){
			$("#receiveGroup").empty();
			$("#workGroupUserDiv").empty();
			$.post("<%=basePath%>work/order/getWorkGroupByDeptId.do",{receiveDept:this.value},function(data){
				var first=null;
				$.each(data,function(i,e){
					if(i==0)first=e;
					var line="<option  value='"+ e.workGroupId +"'>"+e.groupName+"</option>";
					$("#receiveGroup").append(line);
				});
				if(first!=null){
					$.post("<%=basePath%>work/order/getWorkGroupUserByGroupId.do",{receiveGroup:first.workGroupId,workStatus:'ONLINE'},function(data2){
						$.each(data2,function(i,e){
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
		});
		
		$("#receiveGroup").change(function(){
			$("#workGroupUserDiv").empty();
			$.post("<%=basePath%>work/order/getWorkGroupUserByGroupId.do",{receiveGroup:this.value,workStatus:'ONLINE'},function(data){
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
		});
		$("input[name='receiveUser']").click(function(){
			checkGroupUser(this.value,$(this).next('label').html());
		});

		$validator = $("#save_order").validate({
			rules:{
				"content":{
					required:true,
					maxlength:250
				},
				"workOrderTypeId":{
					required:true
				},
				taskContent:{
					maxlength:250
				},
				orderId:{
					maxlength:10,
					digits:true
				},
				limitTime:{
					required:true,
					maxlength:6,
					digits:true
				}
			},
			messages:{
				"content":{
					required:"请填写工单内容",
					maxlength:"工单内容字符数必需小于250"
				},
				"workOrderTypeId":{
					required:"请选择工单类型"
				},
				taskContent:{
					maxlength:"特殊要求字符数必需小于250"
				},
				orderId:{
					maxlength:"请输入有效订单号",
					digits:"请输入有效订单号"
				},
				limitTime:{
					required:"请填写处理时限",
					maxlength:"请填写有效处理时限",
					digits:"请输入有效数字"
				}
			}
        });



		$(this).change(function(){
			$("#saveBtn").removeAttr("disabled");
		});
		
		$("#saveBtn").click(function(){
			<s:if test="workOrderType.paramOrderId=='true'">
				$("#orderId").rules("remove");
	         	$("#orderId").rules("add", { required: true,digits:true,maxlength:10,messages: { required: "请填写订单号",digits:"请输入有效订单号",maxlength:"请输入有效订单号"} });
			</s:if>
			<s:else>
				$("#orderId").rules("remove");
	         	$("#orderId").rules("add", {digits:true,maxlength:10,messages: {digits:"请输入有效订单号",maxlength:"请输入有效订单号"} });
			</s:else>
			<s:if test="workOrderType.paramProductId=='true'">
				$.validator.addMethod("validateProductFn",function(value,element,params){
					if(value=='' || $("#productId").val()==''){
						return false;
					}else{
						return true;
					}
				},"请选择产品");
				$("#productName").rules("remove");
	         	$("#productName").rules("add", { validateProductFn: true,messages: { required: "请选择产品"} });
			</s:if>
			<s:if test="workOrderType.paramUserName=='true'">
				$.validator.addMethod("validateUserFn",function(value,element,params){
					if(value=='' || $("#userNameHid").val()==''){
						return false;
					}else{
						return true;
					}
				},"请选择用户");
				$("#mobileNumber").rules("remove");
		     	$("#mobileNumber").rules("add", { validateUserFn: true,messages: { required: "请输入手机号"} });
			</s:if>
			if($("#mobileNumber").val()!=""){
				var reg=/^[0-9]\d{10}$/; 
				var result= reg.test($("#mobileNumber").val()); 
				if(!result) {
					alert("手机号码格式不正确,请重新输入");
					$("#mobileNumber").val("");
					return false;
				}
			}
			$("#saveBtn").attr({"disabled":"disabled"});
			$("#save_order").submit();
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
		function isInt(param){ 
			var patrn=/^[+-]?[0-9]*$/; 
			if (!patrn.exec(param)) return false ; 
			return true ; 
		}
		// 处理时限日期控件处理
		var limitTimeBtn = $('#limitTime');
		limitTimeBtn.datetimepicker({ 
			minDateTime:new Date(),
			onClose: function(dateText, inst) {
				var limitTime = limitTimeBtn.datetimepicker('getDate');
				if(isInt(dateText)==false){
					var nowDate=new Date();
					var minu=Math.ceil((limitTime.getTime()-nowDate.getTime())/60000);
					$('#limitTime').val(minu);
				}
				$validator.form();
			},
			onSelect: function (selectedDateTime){
			}
		});
		<s:if test="workOrderType.useAgent=='true'">
			function appendAgentUser(data){
				$("#agentUserDiv").empty();
				$.each(data,function(i,e){
					var line="<input type='checkbox' name='agentUsers' value='"+e.userName+"'>";
						line+="<label>"+e.userName+"/"+e.realName+"</label>&nbsp;&nbsp;";
					if((i+1)%6==0)line+="<br>";
					$("#agentUserDiv").append(line);
				});
				
			}
			$('#createWorkGroup').change(function(){
				var createWorkGroupId=$('#createWorkGroup').val();
				if(createWorkGroupId==''){
					$("#agentUserDiv").empty();
				}else{
					$.post("<%=basePath%>work/order/getWorkGroupUserByGroupId.do",{receiveGroup:createWorkGroupId},function(data){
						appendAgentUser(data);
					},'json');
				}
			})
			$.post("<%=basePath%>work/order/getWorkGroupUserByGroupId.do",{receiveGroup:$('#createWorkGroup').val()},function(data){
					appendAgentUser(data);
			},'json');
		</s:if>
	})

</script>