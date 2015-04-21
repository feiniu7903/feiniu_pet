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
<head><title>机型详细信息</title>
<script type="text/javascript">
		 $(function(){	
			$("#planeModelFrom").validateAndSubmit(function($form,dt){
        	 			var data=eval("("+dt+")");
						if (data.success) {
							alert('操作成功！');
							document.location.reload();
						}else{
							alert(data.msg);
						}
			});
		 });
		 function closeDialog(){
				$("#viewPlacePlaneModelDiv").dialog("close");
			}
	</script></head>
	<body>
	<div class="p_box">
	<form action="<%=basePath%>place/placePlaneModelSave.do" method="post" name="planeModelFrom" id="planeModelFrom">
	<s:hidden name="placePlaneModel.placeModelId"/>
		<table class="p_table no_bd form-inline" width="100%">
			<tr>
				<td class="label"><span class="required">*</span>机型编号:</td>
				<td><s:textfield name="placePlaneModel.planeCode" cssClass="required" maxlength="10" /></td>

			</tr>
			<tr>
				<td class="label"><span class="required">*</span>机型名称:</td>
				<td><s:textfield name="placePlaneModel.planeName" cssClass="required" /></td>
			</tr>
			<tr>
				<td class="label"><span class="red">*</span>类型:</td>
				<td><s:select list="#{'BROAD':'宽体','NARROW':'窄体' }" headerValue="请选择" headerKey="" name="placePlaneModel.placeType" cssClass="required" /></td>
			</tr>
				<tr>
				<td class="label"><span class="red">*</span>最少座位数:</td>
				<td><s:textfield name="placePlaneModel.minSeat" cssClass="required digits" /></td>
			</tr>
			<tr>
				<td class="label"><span class="red">*</span>最多座位数:</td>
				<td><s:textfield name="placePlaneModel.maxSeat" cssClass="required digits" /></td>
			</tr>
		</table>
	    <p class="tc mt10"><input type="submit" class="btn btn-small w3" value="保存" />
	    <input class="btn btn-small w5" type="button"  value="关闭" onclick="javascript:closeDialog();"/> 
	    </p>
	</form>
	</div>
	</body>
</html>