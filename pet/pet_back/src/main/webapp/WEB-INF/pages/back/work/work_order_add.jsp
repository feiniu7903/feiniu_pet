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
		<title>新增工单</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/util.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/jquery-ui-timepicker-addon.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/jquery.jsonSuggest-2.min.js"></script>
		<link href="<%=request.getContextPath()%>/themes/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">新增工单</a></li>
			</ul>
		</div>
		<div class="iframe-content">
			<form id="save_order"  action="<%=basePath %>work/order/save.do" method="post">
                <s:token/>
			<p>工单信息</p>
				<div class="p_box" id="add_inner">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label"  width="10%">工单类型<span class="red">*</span>：</td>
							<td width="25%">
								<s:select name="workOrderTypeId" list="workOrderTypeList" listKey="workOrderTypeId" listValue="typeName" headerKey="" headerValue="请选择"></s:select>
							</td>
							<td class="p_label" width="10%">联系人手机<s:if test="workOrderType.paramUserName=='true'"><span class="red">*</span></s:if>:</td>
						     <td width="20%">
						         <input type="text" name="mobile" id="mobile" readonly='true'/>
						     </td>
							<td class="p_label" width="10%">处理时限:</td>
						     <td width="25%">
						         <s:textfield name="limitTime"  readonly='true'/> 分钟
						     </td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		$(document).ready(function(){
			var params={
					workOrderTypeId:$("#workOrderTypeId").val(),
					orderId:'${ orderId}',
					productId:'${ productId}',
					mobileNumber:'${ mobileNumber}'
				};
			var url="<%=basePath%>work/order/add_inner.do";
			$.post(url,params,function(data){
				$("#add_inner").html(data);
			},"html");
			var flag=${flag};
			if(flag==1)alert("操作成功");
			if(flag==-1)alert("操作失败，工单无法分配到组员");
		})
	</script>
</html>
