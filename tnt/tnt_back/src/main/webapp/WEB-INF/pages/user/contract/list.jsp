<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<html>
<body>
	<sf:form id="searchForm"
		action="/user/contract/list/${tntContract.userId }" method="GET">
	</sf:form>
	<sf:form action="/user/contract" method="POST" id="uploadContractForm"
		modelAttribute="tntContract" enctype="multipart/form-data">
		<sf:hidden path="userId" />
		<table class="e_table form-inline ">
			<tbody>
				<tr>
					<td><input name="file" type="file" class="required" />
					<td>
						<div class="fl operate">
							<a class="btn btn_cc1" id="uploadButton"
								href="javascript:upload()">上传合同</a>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</sf:form>
	<sf:form id="contractForm" action="/user/contract" method="post"
		modelAttribute="tntContract">
		<sf:hidden path="userId" />
		<sf:hidden path="contractId" id="contractId" />
		<input type="hidden" name="_method" id="contractForm_method"
			value="delete" />

		<table class="pg_d_table table_center">
			<thead>
				<tr>
					<th>合同名称</th>
					<th>合同上传时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tntContractList }" var="tntContract">
					<tr>
						<td>${ tntContract.contractName}</td>
						<td><lv:dateOutput date="${tntContract.uploadTime }" /></td>
						<td><a
							href='<c:url value="/pet/ajax/file/downLoad?fileId=${tntContract.attachment}" />'>下载
						</a><a href="javascript:remove('${tntContract.contractId }')">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${page!=null }">
			<div id="dialogPage">${page.pagination}</div>
			<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
			<script type="text/javascript">
				var getContractDialog = function() {
					return contractBox;
				}
				dialogAjaxPages("getContractDialog()");
			</script>
		</c:if>
	</sf:form>

	<script type="text/javascript">
		var lock = false;
		var upload = function() {
			var form = $("#uploadContractForm");
			if (!form.validate().form()) {
				return;
			}
			if (!lock) {
				$("#uploadButton").attr("disabled", "true").text("上传中...");
				form.ajaxSubmit({
					beforeSubmit : function() {
						lock = true;
					},
					success : function(data) {
						$.dialog({
							width : 300,
							title : "消息提示",
							ok : function() {
								contractBox.reload();
							},
							okValue : "确定",
							content : "合同上传成功！"

						});
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						lock = false;
						$.dialog({
							width : 300,
							title : "消息提示",
							ok : function() {
								$("#uploadButton").attr("disabled", "false")
										.text("上传合同");
							},
							okValue : "确定",
							content : "合同上传失败！"
						});
					}
				});
			}
		};

		var remove = function(id) {
			$.confirm("确定要删除该合同吗？", function() {
				var hidId = document.getElementById("contractId");
				hidId.value = id;
				var method = document.getElementById("contractForm_method");
				method.value = "delete";
				var form = $("#contractForm");
				form.ajaxSubmit({
					success : function(data) {
						contractBox.reload();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("删除失败！");
					}
				});
			});
		};
	</script>
</body>
</html>
