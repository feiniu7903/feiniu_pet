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
		<title>新增工单类型</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/util.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">新增工单类型</a></li>
			</ul>
		</div>
		<div class="iframe-content">
			<div class="p_box">
				<form id="save_workOrderType" action="<%=basePath%>work/order_type/save_workOrderType.do" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">类型名称：</td>
							<td>
								<input type="text" name="typeName" id="typeName"/>	
							</td>
						</tr>
						<tr>
						     <td class="p_label">标示符:</td>
						     <td>
						         <input type="text" name="typeCode" id="typeCode"/>	
						     </td>
						</tr>
						<tr>
						     <td class="p_label">工单发起部门:</td>
						     <td>
						        <s:select list="departmentList" name="departmentId" listKey="workDepartmentId" listValue="departmentName"  onchange="changeGroupValue(this,'sendGroupId')"></s:select>
						     	<s:select list="sendGroupList" name="sendGroupId" listKey="workGroupId" listValue="groupName" headerKey="" headerValue="--请选择--"></s:select>
						     </td>
						</tr>
						<tr>
						     <td class="p_label">是否增加备选人:</td>
						     <td>
						         <input type="radio" name="useAgent" value="true"/>是
						         <input type="radio" name="useAgent" value="false"/>否
						     </td>
						</tr>
						<tr>
						     <td class="p_label">结束对象:</td>
						     <td>
						         <input type="radio" name="creatorComplete" value="self"/>发送人
						         <input type="radio" name="creatorComplete" value="anyone"/>任何人
						         <input type="radio" name="creatorComplete" value="system"/>系统触发
						     </td>
						</tr>
						<tr>
							<td class="p_label">是否内置：</td>
							<td>
							     <input type="radio" name="system" value="true"/>是
						         <input type="radio" name="system" value="false"/>否
							</td>
						</tr>
						<tr>
						     <td class="p_label"><span id="limitTimeRequired" style='color:red;'>&nbsp;</span>默认时效:</td>
						     <td>
						        <input type="text" name="limitTime" style="width:60px;"/>分钟
							  </td>
						</tr>
						<tr>
						     <td class="p_label">必填项设置：</td>
						     <td>
						        	<s:checkbox name="paramRequire" value="order_id"/>&nbsp;订单号&nbsp;&nbsp;&nbsp;&nbsp;
						        	<s:checkbox name="paramRequire" value="mobile_number"/>&nbsp;联系人手机&nbsp;&nbsp;&nbsp;&nbsp;
						        	<s:checkbox name="paramRequire" value="product_id"/>&nbsp;产品
							  </td>
						</tr>
						<tr>
						     <td class="p_label">转发限制:</td>
						     <td>
						         <input type="radio" name="limitReceiver" value="ANY_DEPARTMENT"/>任何部门
						         <input type="radio" name="limitReceiver" value="SAME_DEPARTMENT"/>本部门
						         <input type="radio" name="limitReceiver" value="SAME_GROUP"/>本部门组织
						     </td>
						</tr>
						<tr>
						     <td class="p_label">内容:</td>
						     <td>
						        <textarea rows="4" cols="100" name="content" id="content" style="width:300px;"></textarea>
						     </td>
						</tr>
						<tr>
						     <td class="p_label">URL模板:</td>
						     <td>
						        <input  type="text" name="urlTemplate" style="width:300px;"/>
						        <br/>
						                    可传参数：订单号${order_id}、产品ID${product_id}、用户名${user_name}
						     </td>
						</tr>
						<tr>
						     <td class="p_label">发送人是否可选择接收组织:</td>
						     <td>
						        <input  type="radio" name="receiverEditable"  value="true" />是
						        <input  type="radio" name="receiverEditable"  value="false"/>否
						     </td>
						</tr>
						<tr>
						     <td class="p_label">接收组织:</td>
						     <td>
						         <s:select list="departmentList" name="departmentId1" listKey="workDepartmentId" listValue="departmentName" onchange="changeGroupValue(this,'workGroupId')"></s:select>
						         <s:select list="workGroupList" name="workGroupId" listKey="workGroupId" listValue="groupName"></s:select>
						     </td>
						</tr>
						<tr>
						     <td class="p_label">是否系统重新分单:</td>
						     <td>
						        <input  type="radio" name="sysDistribute"  value="true" />是
						        <input  type="radio" name="sysDistribute"  value="false"/>否
						     </td>
						</tr>
					</table>
				</form>
				<p class="tc mt20">
		            <button id="saveBtn" class="btn btn-small w5" type="button">提交</button>　
	           </p>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	   $(document).ready(function(){
	    	$("input[name='system']").click(function(){
	    		var val=$("input:checked[name='system']").attr("value");
	    		if(val=='true'){
	    			$("input[name='receiverEditable']").attr("disabled",true).filter(":eq(1)").attr("checked",true);
					$("input[name='limitTime']").rules("add",{required:true,positiveNumber:true,messages:{required:"默认时效不能为空",positiveNumber:"请输入正确数字"}});
	    			$("#limitTimeRequired").html("*")
	    		}else{
	    			$("input[name='receiverEditable']").removeAttr("checked");
	    			$("input[name='receiverEditable']").removeAttr("disabled");
	    			$("input[name='limitTime']").rules("remove");
	    			$("#limitTimeRequired").empty();
	    		}
	    	});
	    	$("#save_workOrderType").validate({
	    		rules:{
	    			"typeName":{
	    				required:true,
	    				remote: {
	    				    url: "check_type_name.do",     
	    				    type: "post",              
	    				    dataType: "json",
	    				    data: {                     
	    				        typeName: function(){return $("#typeName").val();}
	    				    }
	    				},
	    				maxlength:25
	    			},
	    			"typeCode":{
	    				required:true,
	    				remote: {
	    				    url: "check_type_code.do",     
	    				    type: "post",              
	    				    dataType: "json",
	    				    data: {                     
	    				        typeCode: function(){return $("#typeCode").val();}
	    				    }
	    				},
	    				maxlength:50
	    			},
                    "departmentId":{
                    	required:true
	    			},
                    "creatorComplete":{
	    				required:true
	    			},
	    			"limitReceiver":{
	    				required:true
	    			},
	    			"content":{
	    				required:true,
	    				maxlength:250
	    			},
	    			"system":{
	    				required:true
	    			},
	    			"receiverEditable":{
	    				required:true
	    			},
	    			"workGroupId":{
	    				required:true
	    			},
	    			"sysDistribute":{
	    				required:true
	    			}
	    		},
	    		messages:{
	    			"typeName":{
	    				required:"类型名称不能为空",
	    				remote:"类型名称重复",
	    				maxlength:"字符数必需小于25"
	    			},
	    			"typeCode":{
	    				required:"标示符不能为空",
	    				remote:"标示符重复",
	    				maxlength:"字符数必需小于50"
	    			},
                    "departmentId":{
                    	required:"工单发起部门不能为空"
	    			},
                    "creatorComplete":{
	    				required:"结束对象不能为空"
	    			},
	    			"limitReceiver":{
	    				required:"转发限制不能为空"
	    			},
	    			"content":{
	    				required:"内容不能为空",
	    				maxlength:"字符长度必需小于250"
	    			},
	    			"system":{
	    				required:"是否内置不能为空"
	    			},
	    			"receiverEditable":{
	    				required:"发送人是否可选择接收组织不能为空"
	    			},
	    			"workGroupId":{
	    				required:"接收组织不能为空"
	    			},
	    			"sysDistribute":{
	    				required:"是否系统分单不能为空"
	    			}
	    		},
	    		 errorPlacement: function (error, element) {
                     error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面              
                 }
	    	});
	    	jQuery.validator.addMethod("positiveNumber",function(value, element, param) {
	    		 if(/^[1-9][0-9]{0,5}$/.test(value)){
	    			 return true;
	    		 }
	    		 return false;
	    	 });
	    	$("#saveBtn").click(function(){
				var val=$("input:checked[name='system']").attr("value");
	    		if(val=='true'){
					$("input[name='limitTime']").rules("add",{required:true,positiveNumber:true,messages:{required:"默认时效不能为空",positiveNumber:"请输入正确数字"}});
	    		}else{
	    			$("input[name='limitTime']").rules("remove");
	    		}
				if($("#save_workOrderType").valid()){
					$("#save_workOrderType").submit();
				}
	    	});
	    });
	   
		function changeGroupValue(obj,target){
			Utils.setComboxDataSource("/pet_back/work/order_type/changeGroupList.do?departmentId1="+$(obj).val(), target, false, undefined);
		}
	</script>
</html>
