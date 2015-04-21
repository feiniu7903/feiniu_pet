<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>保证金账户</title>
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css,/styles/v4/modules/dialog.css,/styles/v5/modules/tip.css" rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/paging.css"
	rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	$("#mobile_bond").click(function () {
			query("/userspace/cashAccount/mobileBond.do?mobile=${tntCashAccount.mobileNumber}",".main_r");
	});
	$("#paypassword_bod2").click(function () { 
		query("/user/updatePasswordPage.do?ispay=true",".main_r"); 
		}); 
	$("#paypassword_bod1").click(function () { 
		query("/userspace/cashAccount/payPwdBond.do",".main_r"); 
		}); 
</script>
<body>
    	<div class="cash_topBox">
    	<input type="hidden" name="balance" value="${tntCashAccount.balanceToYuan }" id="balance">
        	<div class="cash_topLeftBox">
        	<h4>预存款账户</h4> 
            <p>&nbsp;&nbsp;&nbsp;总金额<span><c:if test="${not empty tntCashAccount }">${tntCashAccount.totalMoneyToYuan }</c:if><c:if test="${empty tntCashAccount }">0</c:if></span>元</p>
            <p>可用余额<strong><c:if test="${not empty tntCashAccount }">${tntCashAccount.balanceToYuan }</c:if><c:if test="${empty tntCashAccount }">0</c:if> </strong>元<a href="javascript:;" id="pop011">如何充值？</a></p>
            <p>冻结余额<span><c:if test="${not empty tntCashAccount }">${tntCashAccount.freezeMoneyToYuan }</c:if><c:if test="${empty tntCashAccount }">0</c:if></span>元<!-- <input type="submit" class="pbtn pbtn-small pbtn-blue pop03"   value="提现"><a href="javascript:;" id="pop02">如何提现？</a> --></p>
            <p>需先绑定手机和设置支付密码才能够充值和提现。</p>
            </div>
            <div class="cash_topRightBox">
        	<h4>账号安全</h4>
            <c:if test="${empty tntCashAccount }">
	            <p>手机绑定
	           		<b>未绑定</b><input type="submit" class="pbtn pbtn-small pbtn-blue" value="立即绑定" id ="mobile_bond">
	            </p>
	            <p>支付密码设置<b>请绑定支付手机</b>
	            <!-- <input type="submit" class="pbtn pbtn-small pbtn-blue" value="立即设置" id = "paypassword_bod1" disabled="disabled"> -->
	            </p>
	            <p>绑定手机号，您的消费记录将向您的手机发送信息。</p>
            </c:if>
             <c:if test="${not empty tntCashAccount }">
             	<p>手机绑定
	           		<b>${tntCashAccount.mobileNumber }</b><input type="submit" class="pbtn pbtn-small pbtn-blue" value="重新绑定" id ="mobile_bond">
	            </p>
	            <p>支付密码设置
	            <c:if test="${empty tntCashAccount.paymentPassword }">
	            	<b>未设置</b><input type="submit" class="pbtn pbtn-small pbtn-blue" value="立即设置" id = "paypassword_bod1">
	            </c:if>
	            <c:if test="${not empty tntCashAccount.paymentPassword }">
	            	<b>*****</b><input type="submit" class="pbtn pbtn-small pbtn-blue" value="重新设置" id = "paypassword_bod2">
	            </c:if>
	            </p>
	            <p>绑定手机号，您的消费记录将向您的手机发送信息。</p>
             </c:if>
            </div>
		</div><!--cash_topBox end--> 
        <div id="tab">
    		<div class="nav">
            	<strong>预存款明细</strong>
        		<ul class="ui_tab">
            		<li class="active" tag="payClass"><a href="javascript:;">消费记录</a></li>
            		<li tag="rechargeClass"><a href="#">充值记录</a></li>
	                <li tag="refundmentClass"><a href="#">退款记录</a></li>
	                <!-- 
	                	<li tag="moneyDrawClass"><a href="#">提现记录</a></li>
	                	<li tag="commissionClass"><a href="#">返佣记录</a></li>
	                -->
        		</ul>
    		</div>
    		<div class="content">
        		<div class="box payClass" style="display:block;"></div>
        		<div class="box rechargeClass" ></div>
		        <div class="box refundmentClass"></div>
		        <div class="box moneyDrawClass"></div>
		        <div class="box commissionClass"></div>
    		</div>
		</div><!--tab end-->
<div id="demo01" style="display:none">
	<p><b>
    预存款充值需要通过银行转账的方式线下进行。<br>
    银行转账时请注明用途为：预存款账户充值。<br>
    银行转账完成后，请电话联系我们的客服。<br>
    驴妈妈确认银行转账到账后，会充值到您的保证金账户里。<br>
    处理时间周一至周五9:00--17:00，节假日不予处理。<br>
    </b></p>
    <p id="accountDesc">
    </p>   
</div>
<div id="demo02" style="display:none">
	<strong>
    预存款提现需要通过银行转账的方式线下进行，流程如下：
    </strong>
    <p>
    1、请先填写提现申请单。<br>
	2、系统进行提现金额冻结。<br>
	3、驴妈妈财务收到提现申请单后会联系你们进行确认。<br>
	4、驴妈妈财务通过银行转账进行打款。<br>
	5、驴妈妈财务扣预存款账户金额。<br>
    </p>   
    <p>
    <b>如有问题请联系：</b><br>
	客服电话：400-6040-616   财务电话：021-8855771
    </p>
</div>
<div id="demo03" style="display:none">
	<strong>
    提现金额不能超过当前预存款金额。
    </strong>
    <form:form class="demo03_form" id="tntCashMoneyDrawForm" modelAttribute="tntCashMoneyDraw"
			target="_top" action="/userspace/cashAccount/addMoneyDraw.do" method="post">
    <label>提现金额：<input type="text" id="drawAmountY" name="drawAmountY">可提现金额：<c:if test="${not empty tntCashAccount }">${tntCashAccount.balanceToYuan }</c:if>元<span class="tiptext tip-line"></span></label>
	<label>支付密码：<input type="password" id="memo" name="memo" ><span class="tiptext tip-line"></span></label>
	<label>转账银行：<input type="text" id="kaiHuHang" name="kaiHuHang"><span class="tiptext tip-line"></span></label>
	<label>账户姓名：<input type="text" id="bankAccountName" name="bankAccountName"><span class="tiptext tip-line"></span></label>
	<label>银行账号：<input type="text" id="bankAccount" name="bankAccount"><span class="tiptext tip-line"></span></label>
    </form:form>
</div>
<script src="http://pic.lvmama.com/js/fx/b2b_fx.js"></script>
<script src="${basePath}/js/jquery.validate.min.js"></script>
<script src="${basePath}/js/user/drawMoney_validate.js"></script>
<script type="text/javascript">

$(function(){
	$(".ui_tab").find("li").click(function(){
		$(".ui_tab").find("li").removeClass("active");
		$(this).parent().parent().siblings(".content").find(".box").hide();
		$(this).addClass("active");
		$("."+$(this).attr("tag")).show();
		return false;
		
	});
	queryDiv("/userspace/cashAccount/queryCashPay.do","payClass");
	queryDiv("/userspace/cashAccount/queryCashRecharge.do","rechargeClass");
	queryDiv("/userspace/cashAccount/queryCashRefundment.do","refundmentClass");
	queryDiv("/userspace/cashAccount/queryCashMoneyDraw.do","moneyDrawClass");
	queryDiv("/userspace/cashAccount/queryCashCommission.do","commissionClass");
	});
	
	$(".pop03").click(function() {
		$.dialog({
			title : "我要提现",
			content : $("#demo03").html(),
			ok : oksubmit,
			okClassName : "pbtn-orange",
			cancel : true
		});
	});
	function oksubmit() {
		var drawForm = $("#tntCashMoneyDrawForm");
		drawForm.validate(cashMoneyDraw);
		if(!drawForm.validate().form()){
			return false;
		}
		drawForm.ajaxSubmit({
			success : function(data) {
				if(data.success){
					//alert("提现请求提交成功！");
				}else{
					alert(data.errorText);
				}
				
				query("/userspace/cashAccount/index.do",".main_r");
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常，请确保您提交的数据的正确！");
			}
		}); 
		return true;
	}
	function queryDiv(url, divClass) {
		if(url=="#"){
			return false;
		}
		$.ajax({
			type : "get",
			url : url,
			success : function(response) {
				$("." + divClass).html(response);
			}
		});
	}
	$("#pop011").click(function(){
		var url = "/userspace/cashAccount/showAccount.do";
		$.getJSON(url, function(data) {
			if(data){
				var content = "驴妈妈账户：" + data.bankAccount + "（"
				+ data.bankName + "）<br/>开户人姓名："
				+ data.accountName
				+ "<br/>客服电话：021-51212088-3530";
				$("#accountDesc").html(content);				
				$.dialog({
					title : "如何充值",
					content : $("#demo01").html()
				});
			}
		});
	});
</script>
</body>
</html>