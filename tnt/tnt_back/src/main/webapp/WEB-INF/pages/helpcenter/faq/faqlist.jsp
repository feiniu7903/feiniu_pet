<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<script>
   function queryAndCheck(){
	   if ($.trim($("#beginpublishdate").val()) > $.trim($("#endpublishdate").val())){
			alert("发布查询开始时间必须小于等于发布查询结束时间！");
			return false;
	   }
	   javascript:$('#searchForm').submit();
   }
</script>
</head>
<body>
	<div class="iframe_search">
		<sf:form id="searchForm" method="POST" modelAttribute="tntFAQ"
			action="/helpcenter/faq/list">
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">问题：</td>
						<td class="w18"><sf:input path="question" /></td>
						<td class="s_label">发布时间：</td>
						<td><sf:input path="beginpublishdate" onFocus="WdatePicker({readOnly:false})" class="required"/>--<sf:input
								path="endpublishdate" onFocus="WdatePicker({readOnly:false})" class="required"/></td>						
						<td class="s_label operate mt20"><a class="btn btn_cc1"
							id="search_button" onclick="javascript:queryAndCheck()">查询</a></td>
						<td class=" operate mt10"><a class="btn btn_cc1"
							onclick="toAddFAQ()" id="new_button">新增常见问题</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
		<c:if test="${tntFAQList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>发布时间</th>
							<th>问题</th>
							<th>答案</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntFAQ" items="${tntFAQList}">
							<tr>
								<td><lv:dateOutput date="${tntFAQ.publishtime}"
										format="yyyy-MM-dd" /></td>
								<td>${tntFAQ.question}</td>
								<td>${tntFAQ.answer}</td>
								<td class="oper"><a
									href="javascript:toModify('${tntFAQ.tntFAQId}')"
									class="baseInfo">修改</a> <a
									href="javascript:toDelete('${tntFAQ.tntFAQId}')">删除</a></td>
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
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntFAQList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无常见问题，重新输入相关条件查询！
			</div>
		</c:if>
	</div>
	<!-- 添加常见问题 -->
	<div style="display: none" id="addFAQBox">
		<sf:form id="addFAQForm" modelAttribute="tntFAQ" target="_top"
			action="/helpcenter/faq/add">
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>问题：</td>
						<td><sf:textarea path="question" cssStyle="width:250px;"
								rows="4" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>答案：</td>
						<td><sf:textarea path="answer" cssStyle="width:250px;"
								rows="6" /></td>
					</tr>
				</tbody>
			</table>
			<div>
				<input class="pbtn pbtn-small btn-ok" id="faqSaveButton"
					style="float: right; margin-top: 20px;" type="button" value="保存" />
			</div>
		</sf:form>
		<script type="text/javascript">
			$().ready(function() {
				$("#addFAQForm").validate(tntFAQ);
			});
			$("#faqSaveButton").bind("click", function() {
				var form = $("#addFAQForm");
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

	<!-- 删除平台公告-->
	<div style="display: none" id="deleteBox">
		<sf:form id="deleteFAQForm" method="post" action="/helpcenter/faq"
			target="_top">
			<input type="hidden" name="_method" value="delete" />
			<input type="hidden" name="tntFAQId" id="deleteBox_tntFAQId" />
			<div class="iframe_content pd0">
				<div>确认要删除这条常见问题?</div>
			</div>
			<div>
				<input type="submit" value="确定" />
			</div>
		</sf:form>
	</div>


	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		//添加常见问题
		var toAddFAQ = function() {
			$.dialog({
				width : 550,
				height : 390,
				title : "新增常见问题",
				content : $("#addFAQBox").html()
			});
		};

		//显示修改类型弹窗
		var toModify = function(tntFAQId) {
			var content = "edit/" + tntFAQId;
			new xDialog(content, null, {
				title : "修改常见问题",
				width : 550
			});
		};

		//显示删除类型弹窗
		var toDelete = function(tntFAQId) {
			$
					.ajax({
						url : "toDelete/" + tntFAQId,
						context : document.body,
						success : function(text) {
							if (text) {
								$
										.dialog({
											width : 400,
											title : "删除常见问题",
											content : "确认要删除这条常见问题?",
											okValue : "确定",
											ok : function() {
												document
														.getElementById("deleteBox_tntFAQId").value = tntFAQId;
												$("#deleteFAQForm")
											       .ajaxSubmit(
															{
																success : function(
																		data) {
																	$(
																			"#searchForm")
																			.submit();
																},
																error : function(
																		XmlHttpRequest,
																		textStatus,
																		errorThrown) {
																	alert("系统处理异常！");
																}
															});
											}
										});
							}
						}
					});
		};
	</script>
</body>
</html>
