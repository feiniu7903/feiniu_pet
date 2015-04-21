<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
		<sf:form id="searchForm" method="POST" modelAttribute="tntChannel"
			action="/user/channel/list">
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商渠道类型：</td>
						<td class="w18"><sf:select path="channelId">
								<sf:option value="" label="--请选择--" />
								<sf:options items="${channelMap}" />
							</sf:select></td>
						<td class=" operate mt10"><a class="btn btn_cc1"
							id="search_button" href="javascript:$('#searchForm').submit()">查询</a></td>
						<td class=" operate mt10"><a class="btn btn_cc1"
							onclick="toAddChannel()" id="new_button">新增渠道类型</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
		<c:if test="${tntChannelList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>渠道名称</th>
							<th>渠道代码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntChannel" items="${tntChannelList}">
							<tr>
								<td>${tntChannel.channelName}</td>
								<td>${tntChannel.channelCode }</td>
								<td class="oper"><a
									href="javascript:toModify('${tntChannel.channelId }')"
									class="baseInfo">修改</a> <a
									href="javascript:toDelete('${tntChannel.channelId }')">删除</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</c:if>
		<c:if test="${tntChannelList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关产品，重新输入相关条件查询！
			</div>
		</c:if>
	</div>
	<!-- 添加分销商渠道 -->
	<div style="display: none" id="addChannelBox">
		<sf:form id="addChannelForm" target="_top" action="/user/channel"
			modelAttribute="tntChannel">
			<sf:hidden path="channelId" id="channelId" />
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销渠道类型名：</td>
						<td><sf:input path="channelName" id="channelName" /></td>
					</tr>
				</tbody>
			</table>
			<div>
				<input class="pbtn pbtn-small btn-ok" id="channelSaveButton"
					style="float: right; margin-top: 20px;" type="button" value="保存" />
			</div>
		</sf:form>
		<script type="text/javascript">
			$().ready(function() {
				$("#addChannelForm").validate(companyChannel);
			});
			$("#channelSaveButton").bind("click", function() {
				var form = $("#addChannelForm");
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
	<!-- 删除分销商类型-->
	<div style="display: none" id="deleteBox">
		<sf:form id="deleteChannelForm" method="post" action="/user/channel"
			target="_top">
			<input type="hidden" name="_method" value="delete" />
			<input type="hidden" name="channelId" id="deleteBox_channelId" />
			<div class="iframe_content pd0">
				<div>确认要删除该渠道！</div>
			</div>
			<div>
				<input type="submit" value="确定" />
			</div>
		</sf:form>
	</div>
	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		//添加销售渠道
		var toAddChannel = function() {
			$.dialog({
				width : 550,
				title : "添加分销渠道类型",
				content : $("#addChannelBox").html()
			});
		};

		//显示修改类型弹窗
		var toModify = function(channelId) {
			var url = channelId;
			$.getJSON(url, function(data) {
				if (data) {
					$.dialog({
						width : 550,
						title : "修改分销渠道类型",
						content : $("#addChannelBox").html()
					});
					$("#channelId").val(data.channelId);
					$("#channelName").val(data.channelName);
				}
			});

		};

		//显示删除类型弹窗
		var toDelete = function(typeId) {
			$.dialog({
				width : 400,
				title : "删除分销渠道",
				content : "确认要删除该分销渠道！",
				okValue : "确定",
				ok : function() {
					$("#deleteBox_channelId").val(typeId);
					$("#deleteChannelForm").ajaxSubmit(
							{
								success : function(data) {
									$("#searchForm").submit();
								},
								error : function(XmlHttpRequest, textStatus,
										errorThrown) {
									alert("系统处理异常！");
								}
							});
				}
			});
		}
	</script>
</body>
</html>