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
	<div class="iframe_content mt20" style="height:500px">
        <div class="ui_title f14">
	        <ul class="ui_tab">
		        <li class="active" tag="payClass"><a href="#">消费记录</a></li>
              <!--   <li tag="rechargeClass" ><a href="#">充值记录</a></li> -->
                <li tag="refundmentClass"><a href="#">退款记录</a></li>
                <li tag="moneyDrawClass"><a href="#">提现记录</a></li>
                <!-- <li tag="commissionClass"><a href="#">返佣记录</a></li> -->
	        </ul>
        </div>
        <div class="p_box payClass"></div>
        <div class="p_box rechargeClass" style="display: none"></div>
        <div class="p_box refundmentClass" style="display: none"></div>
        <div class="p_box moneyDrawClass" style="display: none"></div>
        <div class="p_box commissionClass" style="display: none"></div>
    </div>
</body>
<script type="text/javascript">
$(function(){
	$(".ui_tab").find("li").click(function(){
		$(".ui_tab").find("li").removeClass("active");
		$(this).parent().parent().siblings(".p_box").hide();
		$(this).addClass("active");
		$("."+$(this).attr("tag")).show();
		return false;
	});
	var url = "/tnt_back/cashaccount/dist/queryCashPay.do?cashAccountId=${cashAccountId}";
	query(url,"payClass");
	/* query("/tnt_back/cashaccount/dist/queryCashRecharge.do?cashAccountId=${cashAccountId}","rechargeClass"); */
	query("/tnt_back/cashaccount/dist/queryCashRefundment.do?cashAccountId=${cashAccountId}","refundmentClass");
	query("/tnt_back/cashaccount/dist/queryCashMoneyDraw.do?cashAccountId=${cashAccountId}","moneyDrawClass");
	/* query("/tnt_back/cashaccount/dist/queryCashCommission.do?cashAccountId=${cashAccountId}","commissionClass"); */
});
function query(url,divClass) {
	$.ajax({
		type : "get",
		url : url,
		success : function(response) {
			$("."+divClass).html(response);
		}
	});
}
</script>
</html>
