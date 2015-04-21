<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<body>
	<div class="iframe_search">
		<sf:form method="POST" modelAttribute="tntUser" action="/user/waiting"
			id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商名称：</td>
						<td class="w14"><sf:input path="realName" /></td>

						<td class="s_label">&nbsp;&nbsp;申请时间：</td>
						<td><sf:input path="queryStartDate"
								onFocus="WdatePicker({readOnly:false})" />--<sf:input
								path="queryEndDate" onFocus="WdatePicker({readOnly:false})" /></td>
						<td class="s_label operate mt20" align="right"><a
							class="btn btn_cc1" id="search_button"
							href="javascript:search();">查询</a></td>
					</tr>
					<tr>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><sf:input path="userName" /></td>
					</tr>
				</tbody>
			</table>
			<div class="ui_title f14">
				<ul class="ui_tab">
					<li><a href="<c:url value="/user/doing"/>">合作中的分销商</a></li>
					<li class="active"><a href="<c:url value="/user/waiting"/>">等待审核分销商</a></li>
					<li><a href="<c:url value="/user/ending"/>">已终止合作的分销商</a></li>
				</ul>
			</div>
		</sf:form>
	</div>

	<div class="iframe_content mt20">
		<c:if test="${tntUserList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>分销商用户名</th>
							<th>分销商类型</th>
							<th>申请时间</th>
							<th>基本信息审核</th>
							<th>资料审核</th>
							<th>需缴纳保证金</th>
							<th>已缴纳保证金额</th>
							<th>支付方式</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntUser" items="${tntUserList}">
							<tr>
								<td>${tntUser.realName}</td>
								<td><c:if test="${tntUser.isCompany}">
										${tntUser.detail.companyName}
									</c:if> <c:if test="${!tntUser.isCompany}">
										个人
									</c:if></td>
								<td>${tntUser.userName}</td>
								<td><c:if test="${tntUser.isCompany==true }">
										<lv:mapValueShow key="${tntUser.detail.companyTypeId}"
											map="${companyTypeMap }" />
									</c:if> <c:if test="${tntUser.isCompany==false }">
										个人
									</c:if></td>
								<td><lv:dateOutput date="${tntUser.detail.createdDate }"
										format="yyyy-MM-dd HH:mm" /></td>
								<td><lv:mapValueShow key="${tntUser.detail.infoStatus}"
										map="${infoStatusMap }"></lv:mapValueShow></td>
								<td><lv:mapValueShow key="${tntUser.detail.materialStatus}"
										map="${materialStatusMap}"></lv:mapValueShow></td>
								<td>${tntUser.recognizance.limitsY }</td>
								<td>${tntUser.recognizance.balanceY }</td>
								<td><lv:mapValueShow key="${tntUser.detail.payType }"
										map="${payTypeMap }" /></td>
								<td class="oper"><a href="#" class="baseInfo"
									id="${tntUser.userId}">审核基本信息</a> <a
									href="javascript:showMaterial('${tntUser.userId }');">资料审核</a>
									<a
									href="javascript:toSetRecognizance('<c:url value="/recognizance/" />','${tntUser.userId }');">设置保证金</a>
									<a
									href="javascript:toSetPayType('${tntUser.userId }','${tntUser.detail.payType }');">支付方式</a>
									<a
									href="javascript:toFinal('${tntUser.userId }','${tntUser.detail.infoStatus }')">最终审核</a>
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
						${page.pagination}
					</div>
					<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
					<script type="text/javascript">
						postSubmitPages("searchForm");
					</script>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntUserList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关分销商，重新输入相关条件查询！
			</div>
		</c:if>
	</div>

	<!-- 最终审核 -->
	<div style="display: none" id="finalApproveBox">
		<sf:form method="post" modelAttribute="tntUser" target="_top"
			action="/user/waiting/final" id="finalForm">
			<sf:hidden path="userId" id="finalApproveBox_userId" />
			<input type="hidden" name="status" id="finalApproveBox_status" />
			<div class="iframe_content pd0"></div>
		</sf:form>
	</div>

	<!-- 支付方式 -->
	<div style="display: none" id="payTypeBox">
		<sf:form method="post" modelAttribute="tntUser.detail" target="_top"
			action="/user/waiting/payType" id="payTypeForm">
			<sf:hidden path="userId" id="payTypeBox_userId" />
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>支付方式：</td>
						<td><sf:select path="payType" id="payTypeBox_payType"
								itemValue="MONTH">
								<sf:options items="${payTypeMap}" />
							</sf:select></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>支持原因：</td>
						<td><sf:textarea path="failReason" cssClass="required"
								rows="5" cssStyle="width:250px;" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<input class="pbtn pbtn-small btn-ok" id="setPayTypeButton"
			style="float: right; margin-top: 20px;" type="button"
			onclick="setPayType()" value="保存" />
	</div>
	<jsp:include page="/WEB-INF/pages/user/common_list.jsp"></jsp:include>
	<script type="text/javascript">
		var toSetRecognizance = function(u, userId) {
			var url = u + userId;
			new xDialog(url, null, {
				title : "分销商保证金额度设置",
				width : 550
			});
		};

		var toSetPayType = function(userId, payType) {
			$.dialog({
				width : 550,
				title : "设置支付方式",
				content : $("#payTypeBox").html()
			});
			document.getElementById("payTypeBox_userId").value = userId;
			if (payType) {
				var select = $("#payTypeBox_payType");
				select.val(payType);
			}

		};

		var setPayType = function() {
			var form = $("#payTypeForm");
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
		};

		var toFinal = function(userId, status) {
			var successArray = [ "NEEDACTIVE", "ACTIVED" ];
			var flag = jQuery.inArray(status, successArray);
			if (flag != -1) {
				var url = "waiting/final/" + userId;
				new xDialog(url, null, {
					title : "分销商最终审核",
					width : 400
				});
			} else {
				$.dialog({
					width : 400,
					title : "最终审核",
					ok : true,
					okValue : "确定",
					content : "分销商信息未通过审核，不能进行终审！"
				});
			}
		};
	</script>
</body>
</html>