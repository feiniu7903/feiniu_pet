<%@page import="java.util.Date"%>
<%@page import="com.lvmama.comm.utils.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link href="http://pic.lvmama.com/styles/ebooking/base.css" rel="stylesheet">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link href="http://pic.lvmama.com/js/ui/lvmamaUI/css/jquery.common.css" rel="stylesheet">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${contextPath }/js/eplace/snspt_pop.js"></script>
<script>
function loginHandler(){
	if($.trim($("#userName").val()) == ""){
		$("#msgSpan").text("请输入用户名");
		return false;
	}
	if($.trim($("#password").val()) == ""){
		$("#msgSpan").text("请输入密码");
		return false;
	}
	if($.trim($("#validateCode").val()) == ""){
		$("#msgSpan").text("请输入验证码");
		return false;
	}
	$("#loginForm").submit();
}
function changeValidateCodeHandler(){
	$("#validateCodeImg").attr("src","login/validate_code.do?_=" + (new Date).getTime());
	return ;
}
function enterHandler(event){
	if(event.keyCode==13) {
        $("#loginBtn").click();
  }
}
function ancHandler(ancId){
	window.open("${contextPath}/announcement/show_announcement_detail.do?id="+ancId);
}

	$(function() {
		$("#userName").focus();

		$(".adminEnter").click(function() {
			$("#sid").val($(this).attr("data"));

			$.post("${basePath}admin/userCheck.do", {
				sid : $(this).attr("data")
			}, function(data) {
				if (data == "1") {
					$("#adminEnterSubmit").click();
				} else {
					var html_cont = ("未开通EBK");
					lv_alert(400,html_cont,"通关处理"); 
				}
			});

		});
	});
</script>
<style type="text/css">
.login_main {
	height:auto;	
}
.snspt_footer{ margin-top:70px;}
.gl_table th{
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="login_top">
		<div class="login_logo">
			<span class="login_logo_a"></span>
		</div>
	</div>
	<div class="time_all">
		<div class="time">今天是：<%=DateUtil.formatDate(new Date(), "yyyy年MM月dd日") + "&nbsp;&nbsp;&nbsp;&nbsp;" + DateUtil.getZHDay(new Date()) %></div>
	</div>

	<div class="login_main">
		<div>
			<form action="${basePath}admin/supplierList.do" method="post">
				<table border="0" cellspacing="0" cellpadding="0" class="search_table">
				<tr>
					<td>供应商名称：</td>
					<td>
						<input type="text" class="newtext1" name="supplierName" value="<s:property value="supplierName"/>" />
					</td>				
					<td>供应商ID：</td>
					<td>
						<input type="text" class="newtext1" name="supplierId" value="<s:property value="supplierId"/>" />
					</td>
					<td><input type="submit" value="查询" class="button"/></td>
				</tr>
				</table>
			</form>
			<form action="${basePath}adminlogin.do" id="adminEnterForm" style="display: none" method="post">
				<input type="hidden" name="sid" id="sid"/>
				<input type="submit" id="adminEnterSubmit"/>
			</form>
			<s:if test="pagination.items">
			<br>
			<br>
			<table border="1" cellspacing="0" cellpadding="0" class="gl_table" width="100%">
				<tr>
					<th>编号	</th>
					<th>供应商名称</th>
					<th>上级供应商</th>
					<th>供应商地区</th>
					<th>操作</th>
				</tr>
				<s:iterator value="pagination.items" var="supplier">			
				<tr>
					<td><s:property value="supplierId"/></td>
					<td><s:property value="supplierName"/></td>
					<td><s:property value="parentSupplier.supplierName"/></td>
					<td><s:property value="comCity.cityName"/></td>
					<td>
						<a href="javascript:void(0)" data="${supplier.supplierId}" class="adminEnter">进入</a>
					</td>					
				</tr>	
				</s:iterator>
				<tr>
					<td>
						总条数：<s:property value="pagination.totalResultSize"/>
					</td>
					<td colspan="8" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
				</tr>
			</table>
			</s:if>
		</div>
		
	</div>


	<jsp:include page="../common/footer.jsp"></jsp:include>

	<script type="text/javascript"
		src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>

</body>
</html>