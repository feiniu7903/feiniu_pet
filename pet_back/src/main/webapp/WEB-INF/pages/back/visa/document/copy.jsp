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
		<title>复制签证材料</title>
	</head>
	<body>
		<input type="hidden" name="visaApplicationDocument.documentId" value="${visaApplicationDocument.documentId }"/>
		<table class="p_table form-inline">
			<tr>
				<td class="p_label" rowspan="4">源签证材料</td>
				<td class="p_label" width="15%"><span class="red">*</span>国家:</td>
				<td>
					${visaApplicationDocument.country}
				</td>
			</tr>
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>签证类型:</td>
				<td>
					${visaApplicationDocument.cnVisaType}
				</td>
			</tr>
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>送签城市:</td>
				<td>
					${visaApplicationDocument.cnCity}
				</td>
			</tr>
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>所属人群:</td>
				<td>
					${visaApplicationDocument.cnOccupation}
				</td>
			</tr>
			<tr>
				<td class="p_label"></td>
				<td class="p_label"></td>
				<td class="p_label" colspan="2"></td>
			</tr>
			<tr>
			    <td class="p_label" rowspan="4">目标签证材料</td>
				<td class="p_label" width="15%"><span class="red">*</span>国家:</td>
				<td>
					<input type="text" id="visaApplicationDocument_country" name="visaApplicationDocument.country" value="${visaApplicationDocument.country}"/>
				</td>
			</tr>
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>签证类型:</td>
				<td>
					<s:select name="visaApplicationDocument.visaType" id="visaApplicationDocument_visaType" list="#{'GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证','BUSINESS_VISA':'商务签证','VISIT_VISA':'探亲访友签证','STUDENT_VISA':'留学签证','REGISTER_VISA':'签注','MATCH_VISA':'赛事签证'}"/>
				</td>
			</tr>
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>送签城市:</td>
				<td>
					<s:select name="visaApplicationDocument.city" id="visaApplicationDocument_city" list="#{'SH_VISA_CITY':'上海送签','BJ_VISA_CITY':'北京送签','GZ_VISA_CITY':'广州送签','CD_VISA_CITY':'成都送签','TJ_VISA_CITY':'天津送签','WH_VISA_CITY':'武汉送签','SY_VISA_CITY':'沈阳送签','REGION':'户籍所在地'}"/>
				</td>
			</tr>		
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>所属人群:</td>
				<td>
					<s:select name="visaApplicationDocument.occupation" id="visaApplicationDocument_occupation" list="#{'VISA_FOR_EMPLOYEE':'在职人员','VISA_FOR_RETIRE':'退休人员','VISA_FOR_STUDENT':'在校学生','VISA_FOR_PRESCHOOLS':'学龄前儿童','VISA_FOR_FREELANCE':'自由职业者','VISA_FOR_ALL':'适用所有人员'}"/>
				</td>
			</tr>			 			 			
	   </table>
	   <p class="tc mt10"><input type="button" class="btn btn-small w3" id="btnCopy" value="复制" /></p>
	   
	</body>
	<script type="text/javascript">
		 $(function(){
		 	$("#visaApplicationDocument_country").jsonSuggest({
				url : basePath + "/visa/queryVisaCountry.do",
				maxResults : 10,
				width : 300,
				emptyKeyup : false,
				minCharacters : 1,
				onSelect : function(item) {
					$("#visaApplicationDocument_country").val(item.id);
				}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#visaApplicationDocument_country").val("");
				}
			});			 
			 
			$("#btnCopy").click(function(){
				if ($("#visaApplicationDocument_country").data("selectValue")== "" || $("#visaApplicationDocument_country").val() == "") {
					alert("请选择有效的国家");
					$("#visaApplicationDocument_country").focus();
					return;
				}
				if ($("#visaApplicationDocument_visaType").val() == "") {
					alert("请选择签证类型");
					$("#visaApplicationDocument_visaType").focus();
					return;
				}
				if ($("#visaApplicationDocument_city").val() == "") {
					alert("请选择送签城市");
					$("#visaApplicationDocument_city").focus();
					return;
				}
				if ($("#visaApplicationDocument_occupation").val() == "") {
					alert("请选择所属人群");
					$("#visaApplicationDocument_occupation").focus();
					return;
				}
				$.ajax({
        	 		url: "<%=basePath%>/visa/document/copy.do",
					type:"post",
        	 		data: {
							"documentId":${visaApplicationDocument.documentId},
							"visaApplicationDocument.country":$("#visaApplicationDocument_country").val(),
							"visaApplicationDocument.visaType":$("#visaApplicationDocument_visaType").val(),
							"visaApplicationDocument.city":$("#visaApplicationDocument_city").val(),
							"visaApplicationDocument.occupation":$("#visaApplicationDocument_occupation").val()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	 		dataType:"json",
        	 		success: function(result) {
						if (result.success) {
							alert(result.message);
							document.location.reload();
						} else {
							alert(result.message);
						}
        	 		}
        		});
			});
		 });
	</script>
</html>