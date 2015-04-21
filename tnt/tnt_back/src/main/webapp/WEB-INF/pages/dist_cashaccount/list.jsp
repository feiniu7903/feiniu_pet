<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + path+"/";
%>
<body>
	<div class="iframe_search">
			<div class="ui_title f14">
				<ul class="ui_tab">
					<li class="active"><a href="<c:url value="/cashaccount/search.do"/>">预存款账户信息</a></li>
					<li><a href="<c:url value="/cashaccount/dist/queryCashRechargeList.do"/>">充值单信息</a></li>
					<li><a href="<c:url value="/cashaccount/dist/queryFreezeList.do"/>">冻结单信息</a></li>
				</ul>
			</div>
		<sf:form method="POST" name="tntCashAccount" modelAttribute="t"
			action="/cashaccount/search.do" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商名称：</td>
						<td class="w14"><input name="realName" value="${tntCashAccount.realName }"/></td>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><input name="userName" value="${tntCashAccount.userName }" /></td>
						<td class = "s_label operate mt20" align="right" ><a class="btn btn_cc1" id="search_button" href="javascript:search();">查询</a></td>
						<c:if test="${totalCount>0}">
							<td class = "s_label operate mt20" align="right" >有  <font color="red">${totalCount}</font> 笔提现申请<a class="btn btn_cc1" id="search_button" href="<c:url value="/cashaccount/showCashMoneyDraw.do"/>">查询</a></td>
						</c:if>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>

	<div class="iframe_content mt20">
		<c:if test="${tntCashAccountList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>分销商用户名</th>
							<th>总金额</th>
							<th>可用金额</th>
							<th>冻结金额</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCashAccount" items="${tntCashAccountList}">
							<tr>
								<td>${tntCashAccount.realName}</td>
								<td>${tntCashAccount.tntUser.detail.companyNameOrPerson}</td>
								<td>${tntCashAccount.userName}</td>
								<td>${tntCashAccount.totalMoneyToYuan}</td>
								<td>${tntCashAccount.balanceToYuan}</td>
								<td>${tntCashAccount.freezeMoneyToYuan}</td>
								<td class="oper">
								<%-- <a href="<c:url value="/cashaccount/showDetail.do?cashAccountId=${tntCashAccount.cashAccountId }"/>" >明细查询</a> --%>
								<a href="javascript:showDetail('${tntCashAccount.cashAccountId }')">明细查询</a>
								<a href="javascript:recharge('${tntCashAccount.cashAccountId }','${tntCashAccount.realName }')">充值</a>
								<%-- <a href="javascript:addRefundment('${tntCashAccount.cashAccountId }','${tntCashAccount.userName }','${tntCashAccount.realName }')">返佣</a> --%>
								<a href="javascript:addFreeze('${tntCashAccount.cashAccountId }','${tntCashAccount.realName }')">冻结</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						<div class="paging">${page.pagination}</div>
						<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
					<script type="text/javascript">
						postSubmitPages("searchForm");
					</script>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntCashAccountList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目，重新输入相关条件查询！
			</div>
		</c:if>
	</div>


	<!-- 分销商现金账户充值-->
	<div style="display: none" id="rechargeBox">
		<sf:form id="addMoneyForm" modelAttribute="tntCashRecharge"
			target="_top" action="/cashaccount/addMoney.do" method="post">
			<sf:hidden path="cashAccountId" id="form_cashAccountId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商账户名：</td>
						<td><input type="text" readonly="readonly"
							id="rechargeForm_userName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>充值额度：</td>
						<td><sf:input path="amountY" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>对应转账单编号：</td>
						<td><sf:input path="billNo" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>对应转账单日期：</td>
						<td><sf:input path="billTime" 
								onFocus="WdatePicker({readOnly:true})" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商银行账号：</td>
						<td><sf:input path="bankAccount" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商银行账户名：</td>
						<td><sf:input path="bankAccountName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行名称：</td>
						<td><sf:input path="bankName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>充值原因：</td>
						<td><sf:textarea path="reason" cssStyle="width:250px;"
								rows="5" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="addMoneyButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#addMoneyForm").validate(addMoney);
			});
			$("#addMoneyButton").bind("click", function() {
				var form = $("#addMoneyForm");
				if (!form.validate().form()) {
					return;
				}
				form.ajaxSubmit({
					success : function(data) {
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>

	<!-- 分销商现金账户返佣-->
	<div style="display: none" id="refundmentBox">
		<sf:form id="tntCashCommissionForm" modelAttribute="tntCashCommission"
			target="_top" action="/cashaccount/addCommission.do" method="post">
			<sf:hidden path="cashAccountId" id="form_cashAccountId"/>
			<table class="p_table form-inline" border="0">
				<tbody>
					<tr>
						<td class="p_label" style="text-align: left">分销商用户名:</td><td style="text-align: left"><span class="ref_userName"></span></td>
						<td class="p_label" style="text-align: left">分销商名称:</td><td  style="text-align: left"><span class="ref_userRealName"></span></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>产品类型：</td>
						<td><sf:select path="productType" ><sf:options items="${productTypes }"  itemLabel="zhName"></sf:options></sf:select></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>履行时间：</td>
						<td colspan="3"><sf:input path="performBeginDate" onFocus="WdatePicker({readOnly:false})" />~ <sf:input path="performEndDate" onFocus="WdatePicker({readOnly:false})" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>订单总数：</td>
						<td><sf:input path="orderCount" /> 笔</td>
						<td colspan="2"></td>
					</tr>
					<tr>
					<td class="p_label" style="text-align: left">总金额：</td><td style="text-align: left"><sf:input path="totalAmountY" htmlEscape="false"/> 元</td>
					<td class="p_label" style="text-align: left">返佣比率：</td><td  style="text-align: left"><sf:input path="commisRate" htmlEscape="false"/> %</td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>返佣金额：</td>
						<td><sf:input path="commisAmountY" readonly="true"/> 元</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="addcashCommissionButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		
		<!-- 分销商现金账户冻结-->
	<div style="display: none" id="freezeBox">
		<sf:form id="addFreezeForm" modelAttribute="tntCashFreezeQueue"
			target="_top" action="/cashaccount/addFreeze.do" method="post">
			<sf:hidden path="cashAccountId" id="form_cashAccountId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商账户名：</td>
						<td><input type="text" readonly="readonly" id="rechargeForm_userName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>冻结额度：</td>
						<td><sf:input path="freezeAmountY" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>冻结原因：</td>
						<td><sf:textarea path="reason" cssStyle="width:250px;" rows="5" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="addFreezeButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#addFreezeForm").validate(addFre);
			});
			$("#addFreezeButton").bind("click", function() {
				var addFreezeForm = $("#addFreezeForm");
				if (!addFreezeForm.validate().form()) {
					return;
				}
				addFreezeForm.ajaxSubmit({
					success : function(data) {
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#tntCashCommissionForm").validate(cashCommission);
			});
			$("#addcashCommissionButton").bind("click", function() {
				var form = $("#tntCashCommissionForm");
				if (!form.validate().form()) {
					return;
				}
				var form = $("#tntCashCommissionForm");
				form.ajaxSubmit({
					success: function(data) {
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
			$("#totalAmountY").blur(function(){
				if($("#totalAmountY").val()!="" && $("#commisRate").val()!=""){
					var count= Math.floor($("#totalAmountY").val()*$("#commisRate").val())/100;
					$("#commisAmountY").val(count);
				}
			});
			$("#commisRate").blur(function(){
				if($("#totalAmountY").val()!="" && $("#commisRate").val()!=""){
					var count= Math.floor($("#totalAmountY").val()*$("#commisRate").val())/100;
					$("#commisAmountY").val(count);
				}
			});
		</script>
	</div>
	<script type="text/javascript" src="${basePath }/tnt_back/js/log.js" ></script>
	<div class="detailDiv" style="display: none" href="<c:url value="/cashaccount/dist/showDetail.do?cashAccountId="/>"></div>
	<div class="drawDiv" style="display: none" href="<c:url value="/cashaccount/showCashMoneyDraw.do"/>"></div>
	
	<script type="text/javascript">
		var search = function() {
			var form = $("#searchForm");
			if (!form.validate().form()) {
				return;
			}
			$("#page").val(1);
			form.submit();
		};
		var recharge = function( cashAccountId, userName) {
			$.dialog({
				width : 550,
				title : "分销商现金账户充值",
				content : $("#rechargeBox").html()
			});
			document.getElementById("form_cashAccountId").value = cashAccountId;
			document.getElementById("rechargeForm_userName").value = userName;
			
		};
		var addRefundment = function(cashAccountId, userName,realName) {
			$.dialog({
				autoOpen:false,
				width : 650,
				title : "分销商现金账户返佣",
				content : $("#refundmentBox").html()
			});
			document.getElementById("form_cashAccountId").value = cashAccountId;
			$(".ref_userName").html(userName);
			$(".ref_userRealName").html(realName);
		};
		var addFreeze = function(cashAccountId,realName) {
			$.dialog({
				autoOpen:false,
				width : 650,
				title : "分销商预存款账户资金冻结",
				content : $("#freezeBox").html()
			});
			document.getElementById("form_cashAccountId").value = cashAccountId;
			document.getElementById("rechargeForm_userName").value = realName;
		};
		 var showDetail = function(cashAccountId) {
			 var url= $(".detailDiv").attr("href")+cashAccountId;
			 $.dialog({
				 	width : 824,
					title : "分销商现金账户交易明细",
					content : url
				});
		}; 
		var showDraw = function() {
			 var url= $(".drawDiv").attr("href");
			 $.dialog({
				 	width : 524,
					title : "分销商预存款账户提现申请列表",
					content : url
				});
		}; 	
	</script>
</body>
</html>
