<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<script src='<c:url value="/js/jquery.popImage.js/"/>'></script>
<sf:form id="infoApplyForm" modelAttribute="tntUserMaterial"
	action="/user/material" target="_top" method="post">
	<sf:hidden path="userId" id="userId" />
	<sf:hidden path="materialId" id="agree_materialId" />
	<table class="pg_d_table table_center">
		<thead>
			<tr>
				<th>基本资料</th>
				<th>上传文件</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tntUserMaterialList }" var="tntUserMaterial">
				<tr>
					<td><a class="showPic" href="javascript:"
						title="http://pic.lvmama.com/pics/${tntUserMaterial.materialUrll }"><lv:mapValueShow
								key="${tntUserMaterial.materialType }" map="${typeMap }" /></a></td>
					<td>${tntUserMaterial.materialName }</td>
					<td id="${tntUserMaterial.materialId }materialStatus"><lv:mapValueShow
							key="${tntUserMaterial.materialStatus }" map="${statusMap }" /></td>
					<td><c:if test="${canApprove==true }">
							<a
								href="javascript:singleApprove('${tntUserMaterial.materialId }','${tntUserMaterial.userId }',null);">审核</a>
						</c:if> <!--  
						<a
						href='<c:url value="/pet/ajax/file/downLoad?fileId=${tntUserMaterial.materialUrll}" />'>下载				
					</a>
					--> <a class="showPic" href="javascript:"
						title="http://pic.lvmama.com/pics/${tntUserMaterial.materialUrll }">查看</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${tntUserMaterialList!=null }">
		<c:if test="${canApprove==true }">
			<br />
			<div class="operate" align="center">
				<a class="btn btn_cc1" href="javascript:agree()">审核通过</a><a
					href="javascript:showRejectBox()" class="btn">审核不通过</a>
			</div>
		</c:if>
	</c:if>
</sf:form>

<div style="display: none" id="picBox">
	<img id="imageFrame" alt="" src="" />
</div>

<div style="display: none" id="rejectBox">
	<sf:form id="infoRejectForm" method="post"
		modelAttribute="tntUserMaterial" target="_self"
		action="/user/material">
		<sf:hidden path="userId" />
		<input type="hidden" name="materialId" id="materialId" />
		<input type="hidden" name="_method" value="delete" />
		<div class="iframe_content pd0">
			<div>资料审核不通过原因：</div>
			<div>
				<sf:textarea path="failReason" cssClass="required" rows="5"
					cssStyle="width:250px;" />
			</div>
		</div>
	</sf:form>
	<div>
		<input class="pbtn pbtn-small btn-ok" onclick="reject();"
			style="float: right; margin-top: 20px;" type="button" value="保存" />
	</div>
	<script type="text/javascript">
		$("#infoRejectForm").validate({
			rules : {
				failReason : {
					required : true,
					maxCNLen : 300
				}
			}
		});
	</script>
</div>

<script type="text/javascript">
	$(".showPic").popImage({
		"tagName" : "title"
	});
	var showPic = function(url) {
		$.dialog({
			content : $("#picBox").html()
		});
		document.getElementById("imageFrame").src = url;
	};

	var singleApprove = function(materialId, userId, failReason) {
		$.dialog({
			width : 400,
			title : "单个资料审核",
			ok : function() {
				document.getElementById("agree_materialId").value = materialId;
				$("#infoApplyForm").ajaxSubmit({
					success : function(data) {
						$("#" + materialId + "materialStatus").text("审核通过");
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常！");
					}
				});

			},
			okValue : "审核通过",
			content : "",
			cancel : function() {
				showRejectBox();
				$("#materialId").val(materialId);
				if (failReason) {
					$("#failReason").val(failReason);
				}
			},
			cancelValue : "审核不通过"
		});
	};
	var rejectBox;
	var showRejectBox = function() {
		rejectBox = $.dialog({
			width : 400,
			title : "分销商资料审核不通过",
			content : $("#rejectBox").html()
		});
		document.getElementById("materialId").value = '';
	};

	var agree = function() {
		$("#infoApplyForm").ajaxSubmit({
			success : function(data) {
				$("#searchForm").submit();
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常！");
			}
		});
	};

	var reject = function() {
		var form = $("#infoRejectForm");
		if (!form.validate().form()) {
			return;
		}
		form.ajaxSubmit({
			success : function(data) {
				var id = $("#materialId").val();
				if (id) {
					rejectBox.close();
					$("#" + id + "materialStatus").text("审核不通过");
				} else {
					$("#searchForm").submit();
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常！");
			}
		});
	};
</script>

