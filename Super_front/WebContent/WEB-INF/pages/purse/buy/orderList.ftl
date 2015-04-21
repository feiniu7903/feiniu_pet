<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml
1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="version" content="4.0" />
    <title>财付通-我的钱包</title>
	<link href="https://img.tenpay.com/wallet/v4.0/css/wallet_v4.css" rel="stylesheet" type="text/css" />
    <link href="http://pic.lvmama.com/styles/super_v2/mybank/lvmama_v1.css?r=2916" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/mybank/mybank_func.js?r=2913"></script>
</head>
<body>
       <div class="lv-nav">
            <h2>订单查询</h2>
            <span>
            <@s.if test='mainProduct!=null&&mainProduct.productType=="TICKET"'>
           		<a href="/purse/ticket/index.do">预订首页</a> 
            </@s.if>
            <@s.else>
            	 <a href="/purse/route/index.do">预订首页</a> 
            </@s.else>
            | <a href="/purse/order.do">我的订单</a> | <a href="/purse/help/order.do">预订帮助</a> | <a href="/purse/help/service.do">客服</a></span>
        </div>

        <div class="lvmama-body mg-t0 relative">
        	<h3>我的订单：</h3>

            <table class="order-list">
                <thead>
                	<tr>
                    	<th>订单号</th>
                    	<th class="align-l">名称</th>
                    	<th>金额总计</th>
                    	<!--<th>状态</th>-->
						<th>操作</th>
                    </tr>
                </thead>
                <tbody>
	                <@s.iterator value="pageConfig.Items" var="order">
	                	<tr>
	                    	<td><@s.property value="orderId"/></td>
	                    	<td>
		                    	<@s.iterator value="ordOrderItemProds" status="st">
		                    		<@s.property value="productName"/>
		                    		<@s.if test="!#st.last">
		                    			<br/>
		                    		</@s.if>
		                    	</@s.iterator>
	                    	</td>
	                    	<td>￥<@s.property value="oughtPayFloat"/></td>
                            <!--<td></td>-->
	                    	<td>
	                    		<@s.if test='paymentStatus=="PAYED"'>
	                    			已付款
	                    		</@s.if>
	                    		<@s.elseif test='orderStatus=="CANCEL"'>
	                    			已取消
	                    		</@s.elseif>
	                    		<@s.elseif test='#order.isCanToPay()'>
	                    				<a href="/purse/viewView.do?orderId=${orderId}">支付</a>
	                    		</@s.elseif>
	                    		<@s.elseif test='#order.isCanToPrePay()'>
	                    				<a href="/purse/viewView.do?orderId=${orderId}">支付</a>
	                    		</@s.elseif>
	                    	</td>
	                    </tr>
	                </@s.iterator>
                </tbody>        
            </table>
            <center>
					<@s.property escape="false" value="@com.lvmama.common.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
            </center>
        </div>
</body>
</html>
