<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>订单处理后台_Ebooking已处理订单查询</title>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<!-- LVMAMA -->
<script type="text/javascript" src="${basePath}js/base/form.js"></script>
<script type="text/javascript" src="${basePath}js/base/lvmama_common.js"></script>
<script type="text/javascript" src="${basePath}js/base/lvmama_dialog.js"></script>
<!-- url -->
<script type="text/javascript"
	src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>
<link rel="stylesheet"
	href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<!-- jquery -->
<script type="text/javascript"
	src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath}/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/ui-components.css" />
<link type="text/css" rel="stylesheet" href="${basePath}/themes/base/jquery.jsonSuggest.css">
<script type="text/javascript">
	$(function() {
		$("input[name='createTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='confirmTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='confirmTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='visitTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='visitTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#orderStatus").val("${ebkTask.orderStatus}");
		$("#consumeTime").val("${consumeTime}");
		
		$("#confirmStatus").val("${confirmStatus}");
		$("#orderType").val("${orderType}");
		$("#paymentStatus").val("${ebkTask.paymentStatus}");
		$("#productType").val("${ebkCertificate.productType}");
		$("#useStatus").val("${useStatus}");
		$("#ebkCertificateType").val("${ebkCertificate.ebkCertificateType}");
	});

	$(function() {
		//查找供应商
		$("#searchSupplierName").jsonSuggest({
			url : "${basePath}supplier/searchSupplier.do",
			maxResults : 10,
			minCharacters : 1,
			onSelect : function(item) {
				$("#comSupplierId").val(item.id);
			}
		});
	});
	//显示弹出层
	function showDetailDiv(id) {
		var url = "${basePath}/ebooking/order/ebkOrdProcessedDetail.do?ebkTaskId=" + id;
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "订单详情",
	        position: 'top',
	        width: 1024
		}).width(1000).height(600).attr("src",url);
	}
	function closePopupWin() {
		$("#showDetailWin").dialog("close");
		$("#showDetailWin").remove();
	}
	//发送传真
	function sendFax(id){
		if(confirm("是否确定转为传真方式？")){
			$.ajax({
				type : "post",
				url : "${basePath}/ebooking/order/sendFax.do?id="+id,
				cache : false,
				success : function(data) {
					if(data=="SUCCESS"){
						alert("操作成功！")
						location.reload();
					}else{
						alert(data);
					}
				},
				error : function(data) {
					alert("操作错误！");
				}
			});
		}
	}
</script>


</head>
<body>
<form name='queryEbkTaskForm' method='post' action='${basePath}ebooking/order/ebkOrdProcessedList.do'>
<table class="search_table newfont06">
	<tr>
		<td align="right" width="8%">订单号：</td>
		<td width="8%">
			<input type="text" id="orderId" name="orderId" value="${orderId}" class="newtext1"/>
		</td>
		<td align="right" width="8%">供应商：</td>
		<td width="10%">
			<input type="text" id="searchSupplierName" name="ebkCertificate.supplierName" 
			 value="${ebkCertificate.supplierName}" class="newtext1"/> 
			<input type="hidden" name="ebkCertificate.supplierId" id="comSupplierId"/>
		</td>
		<td align="left" width="10%">采购产品名称：</td>
		<td width="8%">
			<input type="text" id="metaProductName"name="ebkCertificateItem.metaProductName"
			 class="newtext1" value="${ebkCertificateItem.metaProductName }"> 
		</td>
		<td align="left" width="8%">确认人：</td>
		<td width="8%">
			<input type="text" name="ebkTask.confirmUser" value="${ebkTask.confirmUser}" class="newtext1"/>
		</td>
	</tr>
	<tr>
		<td align="right" width="10%">订单状态：</td>
		<td><select id="orderStatus" name="ebkTask.orderStatus" style="width: 125px;">
				<option value="">全部</option>
				<option value="NORMAL">正常</option>
				<option value="CANCEL">取消</option>
		</select></td>
		<td align="right" >支付状态：</td>
		<td><select id="paymentStatus" name="ebkTask.paymentStatus" style="width: 125px;">
				<option value="">全部</option>
				<option value="PAYED">已支付</option>
				<option value="UNPAY">未支付</option>
		</select></td>
		<td align="right" width="8%">确认状态：</td>
        <td>
           <select name="confirmStatus" id="confirmStatus">
             <option value="">全部</option>
             <s:iterator value="@com.lvmama.comm.vo.Constant$EBK_CERTIFICATE_TYPE_AND_STATUS@values()" var="enmu">
                <option value="${enmu}">${enmu.cnName}</option>
             </s:iterator>
           </select>
        </td>
		<td align="right" width="8%">下单时间：</td>
		<td width="30%">
			<input name="createTimeStart" value="${createTimeStart }" id="createTimeStart"/> ~
			<input name="createTimeEnd" value="${createTimeEnd }" id="createTimeEnd"/></td>
	</tr>
	<tr>
		<td align="right" width="10%">供应商确认时间：</td>
		<td width="30%">
			<input name="confirmTimeStart" id="confirmTimeStart" value="${confirmTimeStart }" />
			~ <input name="confirmTimeEnd" id="confirmTimeEnd" value="${confirmTimeEnd }" /></td>
		<td align="right" width="8%">耗时：</td>
		<td><select id="consumeTime" name="consumeTime" style="width: 125px;">
				<option value="">全部</option>
				<option value="FIVE_MIN">5分钟以内</option>
				<option value="FIVE_TEN_MIN">5-10分钟</option>
				<option value="TEN_MIN">10分钟以上</option>
		</select></td>
		<td align="right" width="8%">类型：</td>
		<td>
			<select id="ebkCertificateType" name="ebkCertificate.ebkCertificateType" style="width: 125px;">
			<option value="">全部</option>
           	<s:iterator value="@com.lvmama.comm.vo.Constant$EBK_CERTIFICATE_TYPE@values()" var="enmu">
                <option value="${enmu}">${enmu.cnName}</option>
            </s:iterator>
			</select>
		</td>
		<td align="right" width="8%">产品类型：</td>
		<td>
			<select name="ebkCertificate.productType" id="productType">
				<option value="">全部</option>
				<option value="ROUTE">线路</option>
				<option value="HOTEL">酒店</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" width="8%">期票订单：</td>
		<td>
			<select name="useStatus" id="useStatus">
				<option value="">全部</option>
				<option value="UNACTIVATED">未激活</option>
				<option value="ACTIVATED">已激活</option>
			</select>
		</td>
		<td align="right" width="10%">游玩时间：</td>
		<td width="30%">
			<input name="visitTimeStart" id="visitTimeStart" value="${visitTimeStart }" />
			~ <input name="visitTimeEnd" id="visitTimeEnd" value="${visitTimeEnd }" /></td>
		<td align="right" width="10%">订单类型：</td>
		<td>
		<select id="orderType" name="orderType" style="width: 125px;" >
			<option value="">全部</option>
           	<s:iterator value="@com.lvmama.comm.vo.Constant$ORDER_TYPE@values()" var="enmu">
                <option value="${enmu}">${enmu.cnName}</option>
            </s:iterator>
		</select></td>
		<td align="right"><input type="submit" value="查 询" class="right-button08 btn btn-small"/></td>
	</tr>
</table>
</form>
<!-- --------------查询按钮结果集--------------------------------------------- -->
<table class="gl_table">
	<tbody>
		<tr>
			<th width="60">订单号</th>
			<th width="">采购产品名称</th>
			<th width="60">订单状态</th>
			<th width="80">确认状态</th>
			<th width="60">支付状态</th>
			<th width="120">下单时间</th>
			<th width="120">供应商确认时间</th>
			<th width="60">确认人</th>
			<th width="60">耗时</th>
			<th width="80">操作</th>
		</tr>
		<s:iterator id="item" value="ebkTaskPage.items">
			<tr bgcolor="#ffffff">
				<td>${orderId}
					<s:if test="ebkCertificate.testOrder == 'true'"><div style="color: red;">测试单</div></s:if>
					<s:if test="ebkCertificate.confirmChannel != null && ebkCertificate.confirmChannel != 'EBK'">
						<div style="color:red;">(${ebkCertificate.zhConfirmChannel })</div>
					</s:if>
					<s:if test="ebkCertificate.isAperiodic=='true'">
						<span style="color:red;">(不定期)</span>
					</s:if>	
				</td>
				<td>
					<s:iterator value="ebkCertificate.ebkCertificateItemList" var="item" status="i">
		   		  		${metaProductName }<s:if test="!#i.last"><hr/></s:if>
		   		  	</s:iterator>
	   		  	</td>
	   		  	<td>${zhOrderStatus}</td>
				<td>${ebkCertificate.zhCertificateTypeStatus }</td>
				<td>${zhPaymentStatus }</td>
				<td><s:date name="orderCreateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="confirmTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${confirmUser }</td>
				<td>${consumeTime}<s:if test="consumeTime>0">分</s:if></td>
				<td>
					<a href="javascript:showDetailDiv('${ebkTaskId}');">查看</a>
					<s:if test="ebkCertificate.certificateStatus=='CREATE' && ebkCertificate.ebkCertificateType != 'ENQUIRY'">
						<a href="#" onclick="return sendFax('${ebkCertificateId}')">转传真</a>
					</s:if>	
				</td>
			</tr>
		</s:iterator>
		<tr bgcolor="#ffffff">
			<td colspan="3" align="left">共有
			<s:property value="ebkTaskPage.totalResultSize" />个订单
			</td>
			<td colspan="8" align="right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(ebkTaskPage)"/>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>

