<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发票申请详情</title>
<#include "/common/commonJsIncluedTopNew.ftl"/>
<link href="http://pic.lvmama.com/styles/v4style/myspace_fapiao.css?r=3252" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var invoice_id=${ordInvoice.invoiceId};
	$(function(){
		//点击"取消申请"按钮.
		$('#cancelApplyId').click(function() {
			if(!invoice_id){
				alert("发票信息不存在");
			}else{
				var $this=$(this);
				$.post('${base}/usr/cancelInvoiceApply.do',{"invoiceId":invoice_id},function(dt){
					var data=eval("("+dt+")");
					if(data.success){
						$this.remove();
						$("#status").html("废弃");
					}else{
						alert(data.msg);
					}
				});
			}
		});
	});
</script>
</head>

<body>

<#--
<h2 class="fapiao_mess">发票信息</h2>
-->
<div class="mess_contant">
	<div class="mess_01">订单号:<span>${ordInvoice.orderids}</span></div>
    <div class="mess_01">发票金额:<span class="price_red">¥${ordInvoice.amountYuan}</span></div>
    <div class="mess_01">发票号:<span>${ordInvoice.invoiceNo}</span></div>
    <div class="mess_01">发票抬头:<span>${ordInvoice.title}</span></div>
    <div class="mess_01">发票明细:<span>${ordInvoice.zhDetail}</span></div>
    <div class="mess_01">送货方式:<span>${ordInvoice.zhDeliveryType}</span></div>
    <#if ordInvoice.deliveryType!='SELF'>
    <div class="mess_01">快递单号:<span>${ordInvoice.expressNo}</span></div>
    <div class="mess_01">收件人姓名:<span>${ordInvoice.deliveryAddress.name}</span></div>
    <div class="mess_01">手机号码:<span>${ordInvoice.deliveryAddress.mobile}</span></div>
    <div class="mess_01">地址:<span>${ordInvoice.deliveryAddress.getFullAddress()}</span></div>
    </#if>
    <div class="mess_01">状态:<span id="status">${ordInvoice.zhStatus}</span></div>
    <div class="mess_01">备注:<span>${ordInvoice.memo}</span></div>
    <div class="mess"_02">
    	<#if ordInvoice.status=='UNBILL'||ordInvoice.status=='APPROVE'>
    		<input id="cancelApplyId" type="button" value="取消申请" class="mess_bt" title="发票状态为未开票时可以取消"/>
    	<#else>    	
    		<span style="color:red;font-size:16px;">当前发票状态不可以取消</span>
    	</#if>
    	<input name="" type="submit" value="返 回" class="mess_bt" onClick="window.location.href='javascript:history.back()'"/>
    </div>
</div>
</body>
</html>
