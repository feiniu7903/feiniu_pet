<div class="lv-nav">
            <h2>在线预订</h2>
            <span>
            <@s.if test='mainProduct!=null&&mainProduct.productType=="TICKET"'>
           		<a href="/purse/ticket/index.do">预订首页</a> 
            </@s.if>
            <@s.else>
            	 <a href="/purse/route/index.do">预订首页</a> 
            </@s.else>
            | <a href="/purse/order.do">我的订单</a> | <a href="/purse/help/order.do">预订帮助</a> | <a href="/purse/help/service.do">客服</a></span>
        </div>
