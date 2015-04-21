<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>存款账户-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-money">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; 
				<a class="current">现金账户</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 现金账户 -->
				<div class="ui-tab-title mb10"><h3>现金账户</h3>
					<ul class="lv-tabs-nav hor">
						<li><a href="/myspace/account/bonusreturn.do">奖金<small>(${moneyAccount.bonusBalanceYuan?number?string("0.00")}元)</small></a></li>
						<li class="lv-tabs-nav-selected"><a href="/myspace/account/store.do">存款<small>(${(moneyAccount.maxPayMoney/100)?number?string("0.00")}元)</small></a></li>
					</ul>
				</div>
				<@s.if test='moneyAccount.valid=="N"'>
					<font color="#FF0000">&nbsp;&nbsp;&nbsp;&nbsp;亲爱的用户，系统认为您的帐户存在风险，为了您的账户安全，系统已将您的账户冻结，请立即致电客服10106060。</font>
				</@s.if>
				<div class="ui-box mod-top mod-info">
					<div class="ui-box-container clearfix">
						<form id="forms" action="#" method="post"></form>
				    	<div class="info-detail fl">
				        	<div class="hv_blank fr"></div>
				        	<h3>可用余额</h3>
				            <p><dfn class="info-num"><i>
				             	<@s.if test="moneyAccount.maxPayMoney/100==0">
					            	${moneyAccount.maxPayMoney/100}
					            </@s.if>
					            <@s.else>
				            		${(moneyAccount.maxPayMoney/100)?number?string("0.00")}
					            </@s.else>
				            </i>元</dfn>
				            
				            <@s.if test="rechargeAble==true">
				            	<a href="/myspace/account/store_add.do" class="ui-btn ui-btn5"><i>充 值</i></a>
				            </@s.if>
				            
							<@s.if test='moneyAccount.valid=="Y"&&moneyAccount.maxDrawMoneyYuan>0'>
								<a href="/myspace/account/store_del.do" class="ui-btn ui-btn6"><i>提 现</i></a>
							</@s.if>
				            </p>
				            <p>可提现金额：<dfn><i>
					            <@s.if test="moneyAccount.maxDrawMoneyYuan==0">
					            	${moneyAccount.maxDrawMoneyYuan}
					            </@s.if>
					            <@s.else>
					            	${moneyAccount.maxDrawMoneyYuan?number?string("0.00")}
					            </@s.else>
					            </i></dfn>元
				           		<span class="hv_line"></span>
				           	已充值金额：<dfn><i>
				             	<@s.if test="moneyAccount.rechargeBalanceYuan==0">
					           		${moneyAccount.rechargeBalanceYuan}
					         	</@s.if>
					         	<@s.else>
				            		${moneyAccount.rechargeBalanceYuan?number?string("0.00")}
				            	</@s.else>
				            	</i></dfn>元<span class="hv_line"></span>
				                                       冻结金额：<dfn><i>
				                <@s.if test="(moneyAccount.rechargeBalance+moneyAccount.refundBalance-moneyAccount.maxPayMoney)/100==0">
					           		${(moneyAccount.rechargeBalance+moneyAccount.refundBalance-moneyAccount.maxPayMoney)/100}
					         	</@s.if>
					         	<@s.else>
				            		${((moneyAccount.rechargeBalance+moneyAccount.refundBalance-moneyAccount.maxPayMoney)/100)?number?string("0.00")}
				            	</@s.else>
				           		</i></dfn>元</p>
				        </div>
				        <#include "/WEB-INF/pages/myspace/base/accountsetting.ftl"/>
				        <div class="clear"></div>
				        <p class="info-m-tips lv-cc">说明：存款账户是驴妈妈的现金账户平台，您可以进行现金的充值、提现或退款。</p>
				    </div>
				</div>
				<div id="tabs" class="ui-box mod-edit account-edit ui-tabs-gray">
					<div class="ui-tab-title">
						<h3>交易明细</h3>
				    	<ul class="tab-nav hor">
				    		<li><a id="tab_PAY" href="#tabs-1">消费(${moneyInit.PAY})</a></li>
				    		<li><a id="tab_RECHARGE" href="#tabs-2">充值(${moneyInit.RECHARGE})</a></li>
				    		<li><a id="tab_REFUND" href="#tabs-3">退款(${moneyInit.REFUND})</a></li>
				    		<li><a id="tab_DRAW" href="#tabs-4">提现(${moneyInit.DRAW})</a></li>
				    	</ul>
				    </div>
					<div id="tabs-1" class="ui-tab-box">
						<@s.if test="moneyInit.changeType=='PAY'">
				    	<!-- 消费>> -->
				    	<div class="account-box">
					        <table class="lv-table account-table">
						        <colgroup>
						        <col class="lvcol-1">
						        <col class="lvcol-2">
						        <col class="lvcol-3">
						        <col class="lvcol-4">
						        </colgroup>
					        	<thead>
								    <tr class="thead">
								        <th>日期</th>
								        <th class="price">消费金额</th>
								        <th>订单号</th>
					                    <th class="product-name">产品名称</th>
								    </tr>
					              </thead>
					              <tbody>
								    <@s.iterator value="tansList" var="trans">
					                  <tr bgcolor="#ffffff">
					                  	<td>${trans.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
					                  	<td class="price">
					                        <span class="fontColorR fontColorW">${trans.expenditure?number?string("0.00")}</span>
					                    </td>
					                    <td><a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">${trans.orderId}</a></td>
									  	<td>
									  		<a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">${trans.productName}</a>
									  	</td>
					                  </tr>
					                 </@s.iterator>
								</tbody>
							</table>
					        <div class="pages">
								<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/store.do?currentPage=',${currentPage})"/>
							</div>
				      	</div>
				       </@s.if>
				       <!-- <<消费 -->
					</div>
				    <div id="tabs-2" class="ui-tab-box">
				    	<!-- 充值>> -->
				    	<@s.if test="moneyInit.changeType=='RECHARGE'">
					    	<div class="account-box">
						        <table class="lv-table account-table">
							        <colgroup>
							        <col class="lvcol-1">
							        <col class="lvcol-2">
							        </colgroup>
						        	<thead>
									    <tr class="thead">
									        <th>日期</th>
									        <th class="price">充值金额</th>
									    </tr>
						              </thead>
						              <tbody>
									   <@s.iterator value="tansList" var="trans">
							              <tr bgcolor="#ffffff">
							                <td>${trans.createTime?string("yyyy-MM-dd HH:mm:dd")}</td>
							                <td class="price"><span class="fontColorL">${trans.income?number?string("0.00")}</span></td>
							              </tr>
							              </@s.iterator>
							              <@s.if test="tansList.size==0">
											<tr bgcolor="#ffffff"><td colspan="3" align=center></td></tr>                      	
							            </@s.if>
									</tbody>
								</table>
						        <div class="pages"> 
									<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/viewcharge.do?currentPage=',${currentPage})"/>
						        </div>
					        </div>
				         </@s.if>
				        <!-- <<充值 -->
					</div>
				    <div id="tabs-3" class="ui-tab-box">
				    	<!-- 退款>> -->
				    	<@s.if test="moneyInit.changeType=='REFUND'">
				    	<div class="account-box">
					        <table class="lv-table account-table">
						        <colgroup>
						        <col class="lvcol-1">
						        <col class="lvcol-2">
						        <col class="lvcol-3">
						        <col class="lvcol-4">
						        </colgroup>
					        	<thead>
								    <tr class="thead">
								        <th>日期</th>
								        <th class="price">退款金额</th>
								        <th>订单号</th>
					                    <th class="product-name">产品名称</th>
								    </tr>
					              </thead>
					              <tbody>
								    <@s.iterator value="tansList" var="trans">
					                  <tr bgcolor="#ffffff">
					                  	<td>${trans.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
					                  	<td class="price">
					                        <span class="fontColorR fontColorW">${trans.income?number?string("0.00")}</span>
					                    </td>
					                    <td><a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">${trans.orderId}</a></td>
									  	<td><a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}"><span class="fontColorL">${trans.productName}</span></a></td>
					                  </tr>
					                 </@s.iterator>
								</tbody>
							</table>
					        <div class="pages"> 
								<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/viewrefund.do?currentPage=',${currentPage})"/>
					        </div>
				        </div>
				        </@s.if>
				        <!-- <<退款 -->
					</div>
				    <div id="tabs-4" class="ui-tab-box">
				    	<!-- 提现>> -->
				    	<@s.if test="moneyInit.changeType=='DRAW'">
				    	<div class="account-box">
					        <table class="lv-table account-table">
						        <colgroup>
						        <col class="lvcol-1">
						        <col class="lvcol-2">
						        <col class="lvcol-3">
						        <col class="lvcol-4">
						        <col class="lvcol-5">
						        </colgroup>
					        	<thead>
								    <tr class="thead">
								        <th>日期</th>
								        <th class="price">提现金额</th>
								        <th>收款户名</th>
					                    <th class="accoun-num">收款账号</th>
					                    <th>状态</th>
								    </tr>
					              </thead>
					              <tbody>
								    <@s.iterator value="moneyDrawList" var="moneyDraw">
					                  <tr>
					                  	<td>${moneyDraw.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
					                  	<td class="price">${moneyDraw.drawAmountYuan?number?string("0.00")}</td>
					                  	<td>${moneyDraw.bankAccountName}</td>
					                    <td>${moneyDraw.bankAccount}</td>
					                    <td class="status">${moneyDraw.payStatusName}</td>
					                  </tr>
					                  </@s.iterator>
					                  <@s.if test="moneyDrawList.size==0">
										<tr><td colspan="5" align=center></td></tr>                      	
					                  </@s.if>
								</tbody>
							</table>
					        <div class="pages"> 
						        <@s.if test="pageConfig.totalResultSize>=1">
								<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/viewdraw.do?currentPage=',${currentPage})"/>
								</@s.if>
					        </div>
				        </div>
				        </@s.if>
				        <!-- <<提现 -->
					</div>
				</div>
			</div>
		</div>


	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	var type='${moneyInit.changeType}';
	if('PAY'==type){
		$('#tabs').tabs({ selected: 0 });
	}
	if('RECHARGE'==type){
		$('#tabs').tabs({ selected: 1 });
	}
	if('REFUND'==type){
		$('#tabs').tabs({ selected: 2 });
	}
	if('DRAW'==type){
		$('#tabs').tabs({ selected: 3 });
	}
	$('#tab_PAY').click(function(){
		$("#forms").attr("action","/myspace/account/store.do");
		$("#forms").submit();
	});
	
	$("#tab_RECHARGE").click(function(){
		$("#forms").attr("action","/myspace/account/viewcharge.do");
		$("#forms").submit();
	});
	
	$('#tab_REFUND').click(function(){
		$("#forms").attr("action","/myspace/account/viewrefund.do");
		$("#forms").submit();
	});
	$('#tab_DRAW').click(function(){
		$("#forms").attr("action","/myspace/account/viewdraw.do");
		$("#forms").submit();
	});
});

</script>
<script>
	if("<@s.property value="moneyInit.changeType"/>"=="PAY"){
		cmCreatePageviewTag("现金账户-存款-消费", "D1002", null, null);
	}else if("<@s.property value="moneyInit.changeType"/>"=="RECHARGE"){
		cmCreatePageviewTag("现金账户-存款-充值", "D1002", null, null);
	}else if("<@s.property value="moneyInit.changeType"/>"=="REFUND"){
		cmCreatePageviewTag("现金账户-存款-退款", "D1002", null, null);
	}else if("<@s.property value="moneyInit.changeType"/>"=="DRAW"){
		cmCreatePageviewTag("现金账户-存款-提现", "D1002", null, null);
	}
      
 	</script>
</body>
</html>