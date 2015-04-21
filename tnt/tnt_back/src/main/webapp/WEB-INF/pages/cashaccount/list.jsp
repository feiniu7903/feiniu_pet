<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript" src="${basePath }/tnt_back/js/log.js" ></script>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + path+"/";
%>
<body>
	<div class="iframe_search">
		<sf:form method="POST" name="tntCashAccount" modelAttribute="t"
			action="/cashaccount/list.do" id="searchForm">
			<input type="hidden" name="page" id="page" value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						
						<td class="s_label">分销商名称：</td>
						<td class="w14">
						
						<input name="realName" value="${tntCashAccount.realName }"/></td>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><input name="userName" value="${tntCashAccount.userName }" /></td>
						<td class = "s_label operate mt20" align="right" ><a class="btn btn_cc1" id="search_button" href="javascript:search();">查询</a></td>
						<c:if test="${totalCount>0}">
							<td class = "s_label operate mt20" align="right" >有  <font color="red">${totalCount}</font> 笔提现申请 <a class="btn btn_cc1" id="search_button" href="<c:url value="/cashaccount/showCashMoneyDraw.do"/>">查询</a></td>
						</c:if>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<table >
				<tr>
					<c:if test="${tntAccount.accountId!=null }">
					<td width="300px">预存款充值银行：${tntAccount.bankName }</td>
					<td width="300px">预存款充值账号：${tntAccount.bankAccount }</td>
					<td width="300px">银行账户名：${tntAccount.accountName }</td>
					<td width="300px"><input type="button" value="修改账户信息" onclick="editAccount()"/></td>
					</c:if>
					<c:if test="${tntAccount.accountId==null }">
					<td width="300px">您还未设置分销商充值预存款驴妈妈银行账户信息！</td>
					<td width="300px"><input type="button" value="设置账户信息" onclick="editAccount()"/></td>
					</c:if>
				</tr>
		</table>
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
								<a href="javascript:showDetail('${tntCashAccount.cashAccountId }')">明细查询</a>
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
	<div id="rechargeBox" style="display: none;">
		<sf:form id="addMoneyForm" modelAttribute="tntAccount"
			target="_top" action="/cashaccount/saveAccount.do" method="post">
			<sf:hidden path="accountId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>开户银行：</td>
						<td><sf:input path="bankName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行账号：</td>
						<td><sf:input path="bankAccount" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行账户名：</td>
						<td><sf:input path="accountName" /></td>
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
						alert("提交成功");
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>
	<div class="detailDiv" style="display: none" href="<c:url value="/cashaccount/financel/showDetail.do?cashAccountId="/>"></div>
	<div class="accountDiv" style="display: none" href="<c:url value="/cashaccount/editAccount.do"/>"></div>
	
	<script type="text/javascript">
		var search = function() {
			var form = $("#searchForm");
			if (!form.validate().form()) {
				return;
			}
			$("#page").val(1);
			form.submit();
		};
		
		 var showDetail = function(cashAccountId) {
			 var url= $(".detailDiv").attr("href")+cashAccountId;
			 $.dialog({
				 	width : 1024,
					title : "分销商现金账户交易明细",
					content : url
				});
		}; 
		var editAccount = function() {
			 var url= $(".accountDiv").attr("href");
			 $.dialog({
				 	width : 624,
					title : "设置预存款充值账户信息",
					content : $("#rechargeBox").html()
				});
		}; 
	</script>
</body>
</html>
