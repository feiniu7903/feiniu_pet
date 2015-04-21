<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<style type="text/css">
.input_w{width:90px}
</style>
</head>
<body>
	<div class="iframe_search">
		<sf:form method="POST" name="tntOrder" modelAttribute="tntOrder" action="/order/query.do" id="searchForm" htmlEscape="false">
			<table class="s_table">
			<tbody>
					<tr>
						<td class="s_label">主站订单号：</td>
						<td><input class="input_w" type="text" name="orderId" /></td>
						<td class="s_label">分销平台订单号：</td>
						<td><input class="input_w" type="text" name="tntOrderId"  /></td>
						<td class="s_label">分销商订单号：</td>
						<td><input class="input_w" type="text" name="partnerOrderId" /></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;下单时间：</td>
						<td><sf:input  class="input_w" path="createTimeBegin" onFocus="WdatePicker({readOnly:false})" />~ <sf:input class="input_w" path="createTimeEnd" onFocus="WdatePicker({readOnly:false})" /></td>
					</tr>
					<tr>
						<td class="s_label">联系人姓名：</td>
						<td><input class="input_w" type="text" name="contactName" /></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;联系人手机：</td>
						<td><input class="input_w" type="text" name="contactMoblie"  /></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="s_label">游玩时间：</td>
						<td><sf:input  class="input_w" path="visitTimeBegin" onFocus="WdatePicker({readOnly:false})" />~ <sf:input class="input_w" path="visitTimeEnd" onFocus="WdatePicker({readOnly:false})" /></td>
					</tr>
					<tr>
						<td class="s_label">&nbsp;&nbsp;产品名称：</td>
						<td><input class="input_w" type="text" name="productName"/></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品ID：</td>
						<td><input class="input_w" type="text" name="productId" /></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付时间：</td>
						<td><sf:input class="input_w" path="paymentTimeBegin" onFocus="WdatePicker({readOnly:false})" />~ <sf:input class="input_w" path="paymentTimeEnd" onFocus="WdatePicker({readOnly:false})" /></td>
					</tr>
					<tr>
						<td class="s_label">&nbsp;&nbsp;支付状态：</td>
						<td><sf:select  class="input_w" path="paymentStatus" ><sf:option value="" label="--请选择--" /><sf:options items="${paymentStatusList }"  itemLabel="cnName"></sf:options></sf:select></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资源状态：</td>
						<td><sf:select class="input_w" path="approveStatus" ><sf:option value="" label="--请选择--" /><sf:options items="${orderApproveList }"  itemLabel="cnName"></sf:options></sf:select></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;订单状态：</td>
						<td><sf:select path="orderStatus"  class="input_w"><sf:option value="" label="--请选择--" /><sf:options items="${orderStatusList }"  itemLabel="cnName"></sf:options></sf:select></td>
						<td class="s_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;履行状态：</td>
						<td><sf:select path="performStatus"  class="input_w"><sf:option value="" label="--请选择--" /><sf:options items="${performStatusList }"  itemLabel="cnName"></sf:options></sf:select></td>
					</tr>
					<tr>
						<td class="s_label">分销渠道类型：</td>
						<td>
							<sf:select class="input_w" path="channelId">
								<sf:option value="" label="--请选择--" />
								<sf:options items="${channeList}" />
							</sf:select>
						</td>
						<td class="s_label">&nbsp;&nbsp;分销商名称：</td>
						<td><input class="input_w" type="text" name="distributorName"/></td>
						<td class="s_label">资源不通过原因：</td>
						<td><sf:select class="input_w" path="resourceLackReason" ><sf:option value="" label="--请选择--" /><sf:options items="${resourceLackReasonList }"  itemLabel="cnName"></sf:options></sf:select></td>
						<td class="s_label">分销商结算状态：</td>
						<td><sf:select class="input_w" path="settleStatus" ><sf:option value="" label="--请选择--" /><sf:options items="${settleStatusList }"  itemLabel="cnName"></sf:options></sf:select></td>
					</tr>
					<tr>
						<td class="s_label">产品类型：</td>
						<td><input type="checkbox" name="productType" value="TICKET"/>门票 </td>
						<td class="s_label"></td>
						<td></td>
						<td class="s_label"></td>
						<td></td>
						<td class="s_label" width="700px"></td>
						<input type="hidden" name="page" value="1" id="page"/>
						<td class = "s_label operate mt20" align="right" ><a class="btn btn_cc1" id="search_button" href="javascript:buttonSearch();">查询</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
	</div>
</body>
<jsp:include page="/WEB-INF/pages/user/common_list.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$("#approveStatus").change( function() {
		var resonOptionValue = $("#approveStatus").find("option:selected").val();
	 	if(resonOptionValue != 'RESOURCEFAIL'){
	 		//选中资源审核不通过
	 		$("#resourceLackReason").val('ALL');
	 	}
	}); 
	$("#resourceLackReason").change( function() {
	 	var resonOptionValue = $("#resourceLackReason").find("option:selected").val();
	 	if(resonOptionValue != ''){
	 		//选中资源审核不通过
	 		$("#approveStatus").val('RESOURCEFAIL');
	 	}
	}); 
});
function buttonSearch(){
	$("#page").val(1);
	$("#searchForm").ajaxSubmit({
		success : function(data) {
			$(".iframe_content").html(data);
		}
	});
}
function search(){
	$("#searchForm").ajaxSubmit({
		success : function(data) {
			$(".iframe_content").html(data);
		}
	});
}
</script>
</html>
