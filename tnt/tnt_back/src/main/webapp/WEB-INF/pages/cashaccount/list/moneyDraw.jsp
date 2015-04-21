<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + path+"/";
%>
<body >
	<div class="iframe_content mt20">
        <c:if test="${tntMoneyDrawList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商名称</th>
							<th>分销商账号</th>
							<th>申请日期</th>
							<th>申请提现金额</th>
							<th>收款户名</th>
							<th>收款账号</th>
							<th>转账银行</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCashAccount" items="${tntMoneyDrawList}">
							<tr>
								<td>${tntCashAccount.cashAccount.realName}</td>
								<td>${tntCashAccount.cashAccount.userName}</td>
								<td>${tntCashAccount.cnCreateTime}</td>
								<td>${tntCashAccount.drawAmountToYuan}</td>
								<td>${tntCashAccount.bankAccountName}</td>
								<td>${tntCashAccount.bankAccount}</td>
								<td>${tntCashAccount.kaiHuHang}</td>
								<td>${tntCashAccount.cnAuditStatus}</td>
								<td>
								<a href="javascript:editCashMoneyDrawBox('${tntCashAccount.moneyDrawId}')">提现</a>
								<a href="javascript:release('${tntCashAccount.moneyDrawId}')">作废</a
								></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging moneyDrawClass_pags">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						<div class="paging">${page.pagination}</div>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntMoneyDrawList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目！
			</div>
		</c:if>
        </div>
       <div class="editDrawDiv" style="display: none" href="<c:url value="/cashaccount/editMoneyDraw.do?"/>"></div>
</body>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript">
function release(drawId){
	if(confirm("确认作废该提现申请!")){
		$.ajax({
			type: "post",
			url:  "/tnt_back/cashaccount/releaseDraw.do?drawId="+drawId,
			success: function(response) {
				alert("作废成功！");
				location.href="/tnt_back/cashaccount/showCashMoneyDraw.do";
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常，请确保您提交的数据的正确！");
			}
		});	
	}
};
var editCashMoneyDrawBox = function(drawId) {
	 var url= $(".editDrawDiv").attr("href")+"drawId=" + drawId;
	 $.dialog({
		 	width : 824,
			title : "分销商预存款账户提现",
			content : url
		});
};
</script>
</html>
