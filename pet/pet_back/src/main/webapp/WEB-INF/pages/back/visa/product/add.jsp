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
		<title>新增签证销售产品</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div class="box_content">
			<table class="p_table form-inline" width="100%">
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>产品名称:</td>
					<td>
						
					</td>
					<td class="p_label" width="20%"><span class="red">*</span>产品ID:</td>
					<td>
						
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>签证有效期:</td>
					<td>
						
					</td>
					<td class="p_label" width="20%"><span class="red">*</span>送签类型:</td>
					<td>
						
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>销售价:</td>
					<td>
						
					</td>
					<td class="p_label" width="20%"><span class="red">*</span>是否附加:</td>
					<td>
						
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>结算价:</td>
					<td>
						
					</td>
					<td class="p_label" width="20%"><span class="red">*</span>产品编号:</td>
					<td>
						
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%">市场价:</td>
					<td>
						
					</td>
					<td class="p_label" width="20%">材料截止收取提前:</td>
					<td>
						
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>所属公司:</td>
					<td>
					
					</td>
					<td class="p_label" width="20%"><span class="red">*</span>产品经理:</td>
					<td>
						
					</td>
				</tr>
				<tr>
					<td class="p_label" width="20%"><span class="red">*</span>关联供应商:</td>
					<td>
						
					</td>
					<td class="p_label" width="20%"><span class="red">*</span>结算对象:</td>
					<td>
						
					</td>
				</tr>
				<tr>
						<td class="p_label">预订须知：</td>
						<td class="form-inline" colspan="3">
							<textarea class="p-textarea"></textarea>
						</td>
					</tr>
		   </table>
	   </div>

	   <div class="p_box">
		   <div class="title"><h4>关联所需材料</h4>
	   </div>
	   <div class="p_line form-inline">
			<label class="label">国家：<input type="text" name="country" id="country"/></label>
			<label class="label">签证类型：<s:select key="visaType" list="visaTypeList"/></label>
			<label class="label">人群：<s:select key="occupation" list="visaOccupationList"/></label>
		</div>
	   <p class="tc mt10"><input type="button" id="btnSubmit" class="btn btn-small w3" value="保存" /></p>
	</body>
	<script type="text/javascript">
		  $(function(){
		  	$("#country").jsonSuggest({
		  		url : basePath + "/visa/queryVisaCountry.do",
				maxResults : 10,
				width : 300,
				emptyKeyup : false,
				minCharacters : 1,
				onSelect : function(item) {
					$("#country").val(item.id);
				}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#country").val("");
				}
			});
	      });
	</script>
</html>