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
		<title></title>
	</head>
	<body>
        <div class="box_content center-item">
	        <table class="p_table form-inline" width="100%">
				<tr>
					<td class="p_label">订单号</td>
					<td>
						<input type="text" name="orderId" id="orderId"/><button class="btn btn-small w3" onclick="showOrderItemName()" type="button">获取订单产品</button>	
					</td>
				</tr>
					<td colspan="4" id="orderItem">
					</td>
				</tr>
				<tr>
					<td class="p_label">国家</td>
					<td>
						<input type="text" name="country" id="country"/>	
					</td>
				</tr>
				<tr>
					<td class="p_label">签证类型：</td>
					<td>
						<s:select name="visaType"  list="#{'':'-- 请选择 --','GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证','BUSINESS_VISA':'商务签证','VISIT_VISA':'探亲访友签证','STUDENT_VISA':'留学签证','MATCH_VISA':'赛事签证'}"/>
					</td>
				</tr>
				<tr>
					<td class="p_label">送签城市：</td>
					<td>
						<s:select name="city" list="#{'':'-- 请选择 --','SH_VISA_CITY':'上海送签','BJ_VISA_CITY':'北京送签','GZ_VISA_CITY':'广州送签','CD_VISA_CITY':'成都送签','TJ_VISA_CITY':'天津送签','WH_VISA_CITY':'武汉送签','SY_VISA_CITY':'沈阳送签'}"/>
					</td>					
				</tr>			
			</table>
        </div>

        <p class="tc mt20"><button class="btn btn-small w3" onclick="javascript:saveVisaApproval()" type="button">保存</button>　　<button class="btn btn-small w3 btn-close" onclick="closeVisaApproval()" id="closeGetBtn" type="button">关闭</button></p>
  
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
					$("#visaApplicationDocument_country").val("");
				}
			});
		});
		
		function closeVisaApproval() {
			$("#popDiv").dialog("close");
		}
		
		function showOrderItemName() {
			$("#orderId").val($.trim($("#orderId").val()));
			if ($("#orderId").val() == "" || isNaN($("#orderId").val())) {
				alert("请输入合法的订单号");
				$("#orderId").focus();
				return;
			}
			$.ajax({
    	 		url: "<%=basePath%>/visa/approval/getOrderItemName.do",
				type:"post",
    	 		data: {
						"orderId":$("#orderId").val()
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						var content = "<a href=\"http://www.lvmama.com/product/" + result.productId +"\" target=\"_blank\">";
						content = content + result.productName;
						content = content + "</a>";
						
						$("#orderItem").html(content);
					} else {
						alert("无法获取到产品信息");
					}
    	 		}
    		});
		}
		
		function saveVisaApproval() {
			$("#orderId").val($.trim($("#orderId").val()));
			if ($("#orderId").val() == "" || isNaN($("#orderId").val())) {
				alert("请输入合法的订单号");
				$("#orderId").focus();
				return;
			}
			if ($("#country").data("selectValue")== "" || $("#country").val() == "") {
				alert("请选择有效的国家");
				$("#country").focus();
				return;
			}
			if ($("#visaType").val() == "") {
				alert("请选择签证类型");
				$("#visaType").focus();
				return;
			}
			if ($("#city").val() == "") {
				alert("请选择送签城市");
				$("#city").focus();
				return;
			}
			$.ajax({
    	 		url: "<%=basePath%>/visa/approval/saveVisaApproval.do",
				type:"post",
    	 		data: {
						"orderId":$("#orderId").val(),
						"country":$("#country").val(),
						"visaType":$("#visaType").val(),
						"city":$("#city").val()
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						document.location.reload();
					} else {
						alert(result.message);
					}
    	 		}
    		});
		}
	</script>
</html>