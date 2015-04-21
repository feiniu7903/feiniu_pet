<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>奖金账户-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-money">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a class="current">现金账户</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 现金账户 -->
				<div class="ui-tab-title mb10"><h3>现金账户</h3>
					<ul class="lv-tabs-nav hor">
						<li class="lv-tabs-nav-selected"><a href="/myspace/account/bonusreturn.do">奖金 <small>(${moneyAccount.totalBonusBalanceYuan?number?string("0.00")}元)</small></a></li>
						<li><a href="/myspace/account/store.do">存款<small>(${moneyAccount.maxPayMoneyYuan?number?string("0.00")}元)</small></a></li>
					</ul>
				</div>
				<div class="ui-box mod-top mod-info money-info">
					<div class="ui-box-container clearfix">
						<form id="forms" action="#" method="post" ></form>
				    	<div class="info-detail fl">
				        	<div class="hv_blank fr"></div>
				            <h3>奖金余额</h3>
				            <p><dfn class="info-num"><i>
				            	<@s.if test="moneyAccount.totalBonusBalanceYuan==0">
					            	${moneyAccount.totalBonusBalanceYuan}
					            </@s.if>
					            <@s.else>
				            		${moneyAccount.totalBonusBalanceYuan?number?string("0.00")}
					            </@s.else>
					            </i>元</dfn>
					            <a target="_blank" href="http://www.lvmama.com/public/help_429">[奖金说明]</a>
					        </p>
				        	<p>
					        	<span class="info-m-tips lv-cc">提示：您可以使用奖金支付订单。</span>
					        	<a target="_blank" href="http://www.lvmama.com/public/help_429">如何赚取奖金？</a>
				        	</p>
				        </div>
				        <#include "/WEB-INF/pages/myspace/base/accountsetting.ftl"/>
				      </div>
				  </div>
				<div id="tabs" class="ui-box mod-edit points-edit ui-tabs-gray">
					<div class="ui-tab-title"><h3>交易明细</h3>
					    <ul class="tab-nav hor">
						    <li><a id="tabLink1" href="#tabs-1">收入(${totalCount})</a></li>
						    <li><a id="tabLink2" href="#tabs-2">支出(${totalRecords})</a></li>
						    <li><a id="tabLink3" href="#tabs-3">退款(${totalRefund})</a></li>
					    </ul>
				    </div>
					<div id="tabs-1" class="ui-tab-box">
						<@s.if test="bonusTabType=='bonusreturn'">
				    	<!-- 获取积分>> -->
				    	<div class="money-box">
				        <table class="lv-table money-table">
					        <colgroup>
					        <col class="lvcol-1">
					        <col class="lvcol-2">
					        <col class="lvcol-3">
					        <col class="lvcol-4">
					        <col class="lvcol-5">
					        </colgroup>
				        	<thead>
							    <tr class="thead">
							        <th class="date" width="120">日期</th>
									<th class="price" width="50">金额(元)</th>
							        <th class="m-type" width="110">收入类型</th>
							        <th class="order-num" width="90">收入相关订单号</th>
							        <th class="product-name">产品名称</th>
							    </tr>
				              </thead>
				              <tbody>
				              <@s.iterator value="bonusReturnList" var="bonusreturn">
				              <tr>
				              		<td class="date">${bonusreturn.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
				              		<td class="price">${bonusreturn.bonusYuan?number?string("0.00")}</td>
				              		<td class="m-type">
				              			<@s.property value="@com.lvmama.comm.vo.Constant$BonusOperation@getCnName(comeFrom)"/>
				              		</td>
				              		<td class="order-num">
				              			<a target="_blank" href="/myspace/order_detail.do?orderId=${bonusreturn.businessId}">${bonusreturn.businessId}</a>
				              		</td>
				              		<td class="product-name">
				              			<a target="_blank" href="/myspace/order_detail.do?orderId=${bonusreturn.businessId}">${bonusreturn.productName}</a>
				              		</td>
				              </tr>
				              </@s.iterator>
							</tbody></table>
				            <div class="pages">
				            	<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/bonusreturn.do?currentPage=',${currentPage})"/>
				            </div>
				        </div>
				        <!-- <<获取积分 -->
				        </@s.if>
					</div>
				    <div id="tabs-2" class="ui-tab-box">
				    	<@s.if test="bonusTabType=='bonuspayment'">
				    	<!-- 使用积分>> -->
				    	<div class="money-box">
				        <table class="lv-table money-table">
					        <colgroup>
					        <col class="lvcol-1">
					        <col class="lvcol-2">
					        <col class="lvcol-3">
					        <col class="lvcol-4">
					        </colgroup>
				        	<thead>
							    <tr class="thead">
							        <th class="date">日期</th>
									<th class="price">金额(元)</th>
							        <th class="order-num">支付相关订单号</th>
							        <th class="product-name">产品名称</th>
							     </tr>
				              </thead>
				              <tbody>
				              <@s.iterator value="tansList" var="trans">
							    <tr>
									<td class="date">${trans.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
								    <td class="price">${trans.expenditure?number?string("0.00")}</td>
									<td class="order-num">
										<a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">${trans.orderId}</a>
									</td>
									<td class="product-name">
										<a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">${trans.productName}</a>
									</td>
							    </tr>
							  </@s.iterator>
							  </tbody>
							</table>
				            <div class="pages">
				            	<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/bonuspayment.do?currentPage=',${currentPage})"/>
				            </div>
				        </div>
				        <!-- <<使用积分 -->
				        </@s.if>
					</div>
					<div id="tabs-3" class="ui-tab-box">
						<@s.if test="bonusTabType=='bonusrefund'">
				    	<!-- 退款>> -->
				    	<div class="money-box">
				        <table class="lv-table money-table">
					        <colgroup>
					        <col class="lvcol-1">
					        <col class="lvcol-2">
					        <col class="lvcol-3">
					        <col class="lvcol-4">
					        </colgroup>
				        	<thead>
							    <tr class="thead">
							        <th class="date">日期</th>
									<th class="price">退款金额</th>
							        <th class="m-type">订单号</th>
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
					                    <td>
					                    	<a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">${trans.orderId}</a>
					                    </td>
									  	<td>
									  		<a target="_blank" href="/myspace/order_detail.do?orderId=${trans.orderId}">
									  			<span class="fontColorL">${trans.productName}</span>
									  		</a>
									  	</td>
					                  </tr>
					                 </@s.iterator>
							  </tbody>
							</table>
				            <div class="pages">
				            	<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(10,pageConfig.totalPageNum,'/myspace/account/bonusrefund.do?currentPage=',${currentPage})"/>
				            </div>
				        </div>
				        <!-- <<退款 -->
				        </@s.if>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>

<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>

<script type="text/javascript">
	<!-- <<奖金账户 -->
	$(document).ready(function(){
		var type='${bonusTabType}';
		if('bonusreturn'==type){
			$('#tabs').tabs({ selected: 0 });
		}
		if('bonuspayment'==type){
			$('#tabs').tabs({ selected: 1 });
		}
		if('bonusrefund'==type){
			$('#tabs').tabs({ selected: 2 });
		}
		$('#tabLink1').click(function(){
			$("#forms").attr("action","/myspace/account/bonusreturn.do");
			$("#forms").submit();
		});
		$('#tabLink2').click(function(){
			$("#forms").attr("action","/myspace/account/bonuspayment.do");
			$("#forms").submit();
		});
		$('#tabLink3').click(function(){
			$("#forms").attr("action","/myspace/account/bonusrefund.do");
			$("#forms").submit();
		});
	});
	
	
</script>
	<script>
	if("<@s.property value="bonusTabType"/>"=="bonusreturn"){
		cmCreatePageviewTag("现金账户-奖金-收入", "D1002", null, null);
	}else if("<@s.property value="bonusTabType"/>"=="bonuspayment"){
		cmCreatePageviewTag("现金账户-奖金-支出", "D1002", null, null);
	}else if("<@s.property value="bonusTabType"/>"=="bonusrefund"){
		cmCreatePageviewTag("现金账户-奖金-退款", "D1002", null, null);
	}
      
 	</script>
</body>
</html>