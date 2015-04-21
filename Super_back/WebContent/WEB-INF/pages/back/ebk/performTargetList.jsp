<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户履行对象权限</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.form.js"></script>
<script type="text/javascript" src="../../js/ebk/admin_index.js" ></script>

<script type="text/javascript">

var userId = '${userId}';
$(document).ready(function(){
	$('.binding').each(function(){
		$(this).bind('click',binding);
	});
	$('.unbinding').each(function(){
		$(this).bind('click',unbinding);
	});
	$('.searchMetaProduct').each(function(){
		$(this).click(function(){
			var targetId = $(this).parent().attr('targetId');
			searchMetaProduct(targetId);
		});
	});
}); 

function binding(){
	var targetId = $(this).parent().attr('targetId');
	var eventObject = $(this);
	$.post(
		"bindingTarget.do", 
		{ userId: userId, targetId: targetId },
		function(data){
			if(data=='success'){				
				var newObject = $('<a href="javascript:void(0);" class="unbinding">解除绑定</a>');
				newObject.bind('click',unbinding);
				eventObject.parent().prev('td').text('已绑定');
				eventObject.replaceWith(newObject);
				alert('绑定成功');
			}else{
				alert('绑定失败');
			}
		}
	);
}
function unbinding(){
	var targetId = $(this).parent().attr('targetId');
	var eventObject = $(this);
	$.post(
		"deleteBindingTarget.do", 
		{ userId: userId, targetId: targetId },
		function(data){
			if(data=='success'){
				var newObject = $('<a href="javascript:void(0);" class="binding">绑定</a>');
				newObject.bind('click',binding);
				eventObject.parent().prev('td').text('未绑定');
				eventObject.replaceWith(newObject);
				alert('解除绑定成功');
			}else{
				alert('解除绑定失败');
			}
		}
	);
}
</script>
</head>
<body>
	<input type="hidden" id="userIdHd" value="${userId}">
	<input type="hidden" id="supplierIdHd" value="${supplierId}">
	<input type="hidden" id="bizTypeHd" value="${bizType}">
	
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th width="5%">编号</th>
	    <th width="20%">名称</th>
		<th >履行方式</th>
		<th width="10%">履行时间</th>
		<th width="10%">履行信息</th>
		<th  width="10%">支付信息</th>
		<th width="10%">备注</th>
		<th width="10%">绑定状态</th>
	    <th width="15%">操作</th>
	  </tr>

	  <s:iterator value="performTargetList" var="item">
		
			<tr>
				<td><s:property value="targetId"/></td>
				<td><s:property value="name"/></td>
				<td><s:property value="zhCertificateType"/></td>
				<td><s:property value="openTime"/><br/><s:property value="closeTime"/></td>
				<td><s:property value="performInfo"/></td>
				<td><s:property value="paymentInfo"/></td>
				<td><s:property value="memo"/></td>
				<td>
					<s:set name="isBinding" value="false"/>
					<s:iterator value="userTargetList" var="userTarget">
						<s:if test="targetId == supPerformTargetId">
							<s:set name="isBinding" value="true"/>
						</s:if>
					</s:iterator>
					${isBinding eq false?'未绑定':'已绑定'}
				</td>
				<td targetId="${item.targetId}">
					${isBinding eq false?
					'<a href="javascript:void(0);" class="binding">绑定</a>':
					'<a href="javascript:void(0);" class="unbinding">解除绑定</a>'}
					&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="searchMetaProduct">查看采购</a
				</td>
			</tr>
	  </s:iterator>
	</table>
</body>
</html>