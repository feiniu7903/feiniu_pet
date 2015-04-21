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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理系统——新建行程</title>
<script type="text/javascript" src="<%= basePath%>js/prod/sensitive_word.js"></script>
<script type="text/javascript">
	$(function() {
		
		var multiJourneyId = $("input[name='ebkMultiJourney.multiJourneyId']").val();
		if(multiJourneyId=="" || null==multiJourneyId){
			var days = $("#tourDays").val();
			$("input[name='ebkMultiJourney.days']").val(days);
		}
		
		$(".saveMultiJourneyBtn").click(function() {
			var $form = $(this).parents("form");
			var paraProductId=$("#productId").val();
			var name = $.trim($form.find("input[name='ebkMultiJourney.journeyName']").val());
			if(name == "") {
				alert("行程名称不能为空!");
				return false;
			}
			if(name.length > 20) {
				alert("行程名称不能超过20个字符!");
				return false;
			}
			var re = /^[1-9]\d*$/;
			var days = $.trim($form.find("input[name='ebkMultiJourney.days']").val());
			if(days == "") {
				alert("行程天数不能为空!");
				return false;
			}
			if (!re.test(days)){
				alert("行程天数必须为大于0的正整数!");
				return false;
			}
			if(days.length > 2) {
				alert("行程天数不能超过2个字符!");
				return false;
			}
			var nights = $.trim($form.find("input[name='ebkMultiJourney.nights']").val());
			if(nights == "") {
				alert("晚数不能为空!");
				return false;
			}
			if (!re.test(nights)){
				alert("晚数必须为大于0的正整数!");
				return false;
			}
			if(nights.length > 2) {
				alert("晚数不能超过2个字符!");
				return false;
			}
			var content = $.trim($form.find("textarea[name='ebkMultiJourney.content']").val());
			if(content == "") {
				alert("描述内容不能为空!");
				return false;
			}
			if(content.length > 100) {
				alert("描述内容不能超过100个字符!");
				return false;
			}
			
			$.ajax( {
				type : "POST",
				url : "/ebooking/view/saveMultiJourney.do",
				async : false,
				data : $form.serialize(),
				timeout : 3000,
				success : function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("操作成功");
						var url2="<%=request.getContextPath()%>/ebooking/product/editEbkProdMultirpInit.do?ebkProdProductId="+paraProductId;
						window.location.href=url2;
					} else {
						alert(data.msg);
					}
				}
			});
		});
	});
</script>
</head>

<body>
	<div class="row2">
		<form class="mySensitiveForm">
			<input type="hidden" name="ebkProdProductId" id="productId" value="${productId }" />
			<input type="hidden" name="ebkMultiJourney.productId" value="${productId }" />
			<input type="hidden" name="ebkMultiJourney.multiJourneyId" value="${multiJourneyId }" />
			<input type="hidden" name="isCopy" value="${isCopy }" />
			<input type="hidden" name="tourDays" id="tourDays" value="${tourDays }" />
			
			<table class="newTable" width="90%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>行程名称：</td>
					<td><s:textfield name="ebkMultiJourney.journeyName" size="50" maxlength="20" cssClass="sensitiveVad" /><font color="red">最多不超20字符，直接用于前台产品详情页，请认真填写</font></td>
				</tr>
				<tr>
					<td>行程天数：</td>
					<td><s:textfield name="ebkMultiJourney.days" size="3" />天<s:textfield name="ebkMultiJourney.nights" size="3" />晚</td>
				</tr>
				<tr>
					<td>内容描述：</td>
					<td>
					<s:if test="ebkMultiJourney == null">
						<s:textarea name="ebkMultiJourney.content" rows="3" cols="50" value="★ 行程天数：             ★ 进出城市：             ★航班：" cssClass="sensitiveVad">
						</s:textarea>
					</s:if>
					<s:else>
						<s:textarea name="ebkMultiJourney.content" rows="3" cols="50" cssClass="sensitiveVad">
						</s:textarea>
					</s:else>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span class="fp_btn saveMultiJourneyBtn">保存</span></td>
				</tr>
			</table>
			<s:hidden name="hasSensitiveWord"></s:hidden>
		</form>
	</div>
</body>
</html>

