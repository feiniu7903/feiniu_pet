<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages/tld/lvmama-tags.tld"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>保证金账户</title>
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css,/styles/v4/modules/dialog.css,/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/paging.css"
	rel="stylesheet" type="text/css">
<script src="/js/ajaxpage.js/"></script>
</head>
<body>
	<div class="cash_topBox2">
		<div class="cash_topLeftBox2">
			<h4>保证金账户</h4>
			<p>
				保证金余额<span>${tntRecognizance!=null?tntRecognizance.balanceY:0.00}</span>元
			</p>
			<p>
				所需保证金<span>${tntRecognizance!=null?tntRecognizance.limitsY:0.00}</span>元
			</p>
			<p>
				还需补交<strong>${tntRecognizance!=null?tntRecognizance.needPay:0.00}</strong>元<a
					href="javascript:" id="pop04">如何补交？</a>
			</p>
		</div>
		<div class="cash_topRightBox2">
			<dl>
				<dt>保证金目的</dt>
				<dd>保证金是为了维护消费者的正当权益，和驴妈妈品牌形象而向分销商预收的一定金额。</dd>
			</dl>
			<dl>
				<dt>保证金扣除</dt>
				<dd>分销商以欺骗行为损害了消费者的正当权益而引起的纠纷，投诉，罚款或赔偿时使用；违背双方的合作合同或其他对驴妈妈品牌造成影响时使用。</dd>
			</dl>
			<dl>
				<dt>保证金退还</dt>
				<dd>分销商在正常情况下和驴妈妈解除合作后，保证金将按额退还分销商。</dd>
			</dl>

		</div>
	</div>
	<!--cash_topBox end-->
	<h3>保证金明细</h3>
	<div class="rightBox" id="queryBox">
		<table width="760" border="0" class="pre_deposit_table">
			<tr>
				<th width="180">日期</th>
				<th width="180">金额</th>
				<th width="180">类型</th>
				<th width="180">状态</th>
				<th width="180">原因</th>
			</tr>
			<c:forEach items="${tntRecognizanceChangeList }"
				var="tntRecognizanceChange">
				<tr>
					<td>${tntRecognizanceChange.strCreateTime }</td>
					<c:if test="${tntRecognizanceChange.debit==true }">
						<td><strong>-${tntRecognizanceChange.amountY }</strong></td>
					</c:if>
					<c:if test="${tntRecognizanceChange.debit==false }">
						<td><b>+${tntRecognizanceChange.amountY }</b></td>
					</c:if>
					<td>${tntRecognizanceChange.typeName }</td>
					<td><lv:mapValueShow
							key="${tntRecognizanceChange.approveStatus }"
							map="${approveStatusMap }"></lv:mapValueShow></td>
					<td>${tntRecognizanceChange.reason }</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${page!=null }">
			${page.pagination}
			<script type="text/javascript">
				$(function() {
					setAjaxPages("recognizance.do", "queryRecognizanceList.do");
				});
			</script>
		</c:if>
	</div>
	<div id="demo04" style="display: none">
		<p>
			<b> 保证金缴纳需要通过银行转账的方式线下进行。<br> 
				银行转账时请注明用途为：分销交纳保证金。<br>
				银行转账完成后，请电话联系我们的客服。<br> 
				驴妈妈确认银行转账到账后，会充值到您的保证金账户里面。<br>
				处理时间周一至周五9:00--17:00，节假日不予处理。<br>
			</b>
		</p>
		<p id="accountDesc"></p>
	</div>
	<script type="text/javascript">
	$("#pop04").click(function(){
		var url = "/user/showAccount.do";
		$.getJSON(url, function(data) {
			if(data){
				var content = "驴妈妈账户：" + data.bankAccount + "（"
				+ data.bankName + "）<br/>开户人姓名："
				+ data.accountName
				+ "<br/>客服电话：021-51212088-3530";
				$("#accountDesc").html(content);				
				$.dialog({
					title : "补交保证金",
					content : $("#demo04").html()
				});
			}
		});
	});
	</script>
</body>
</html>