<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
<link rel="stylesheet" href="<%=basePath%>themes/base/jquery.ui.all.css" />
<script type="text/javascript"
	src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	function setDefaultValue(selectId, selectedValue) {
		$("#" + selectId).val(selectedValue);
	}
	$(document).ready(function() {
		setDefaultValue("typeSelect", "${feedBack.type }");
		setDefaultValue("stateIdSelect", "${feedBack.stateId }");
	});
	function httpRequest(url, param) {
		$.ajax({
			type : "POST",
			url : url + "?random=" + Math.random(),
			data : param,
			async : false,
			success : function(result) {
				var res = eval(result);
				if (res) {
					window.location.reload();
				} else {
					alert("操作失败!");
				}
			}
		});
	}
</script>
</head>

<body>
	<div>
		<div class="main2">
			<div class="table_box" id=tags_content_1>
				<div class="mrtit3">
					<form method="post" action="<%=basePath%>feedBack/search.do">
						<table width="100%" border="0" class="newfont06"
							style="font-size: 12; text-align: left;">
							<tr>
								<td width="22%">类型： <select name="feedBack.type"
									id="typeSelect">
										<option value="">请选择</option>
										<s:iterator value="typeList" id="type">
											<option value="${type }">
												<s:if test="#type=='ask'">
														咨询
													</s:if>
												<s:elseif test="#type=='complain'">
														抱怨
													</s:elseif>
												<s:elseif test="#type=='suggest'">
														建议
													</s:elseif>
												<s:else>
														${type }
													</s:else>
											</option>
										</s:iterator>
								</select>
								</td>
								<td width="22%">内容：<input type="text"
									name="feedBack.content" value="${feedBack.content }" />
								</td>
								<td width="22%">状态： <select name="feedBack.stateId"
									id="stateIdSelect">
										<option value="">请选择</option>
										<s:iterator value="stateIdList" id="stateId">
											<option value="${stateId }">
												<s:if test="#stateId=='PENDING'">
														未处理
													</s:if>
												<s:elseif test="#stateId=='PROCESSED'">
														已处理
													</s:elseif>
												<s:else>
														无效
													</s:else>
											</option>
										</s:iterator>
								</select>
								</td>
								<td><input name="right_button08Submit" type="submit"
									value="查询" class="right-button08" /></td>
							</tr>
						</table>
					</form>
				</div>
				<br />
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="90%" class="newfont06"
					style="font-size: 12; text-align: center;">
					<tbody>
						<tr bgcolor="#eeeeee">
							<td width="7%">用户名</td>
							<td height="30" width="7%">用户ID</td>
							<td width="7%">IP</td>
							<td width="7%">联系人方式</td>
							<td>内容</td>
							<td width="10%">创建时间</td>
							<td width="7%">邮箱</td>
							<td width="7%">类型</td>
							<td width="7%">状态</td>
							<td width="7%">操作</td>
						</tr>
						<s:iterator value="feedBackList">
							<tr bgcolor="#ffffff">
								<td>${userName }</td>
								<td height="30">${userId }</td>
								<td>${ip }</td>
								<td>${instantMessaging }</td>
								<td>${content }</td>
								<td>${zhCreateDate }</td>
								<td>${email }</td>
								<td><s:if test="type=='ask'">
												咨询
											</s:if> <s:elseif test="type=='complain'">
												抱怨
											</s:elseif> <s:elseif test="type=='suggest'">
												建议
											</s:elseif> <s:else>
												${type }
											</s:else></td>
								<td><s:if test="stateId=='PENDING'">
												待处理
											</s:if> <s:elseif test="stateId=='PROCESSED'">
												已处理
											</s:elseif> <s:else>
												作废
											</s:else></td>
								<td><s:if test="stateId=='PENDING'">
										<!-- mis:checkPerm permCode="1973" -->
										<a href="#"
											onclick="javascript:httpRequest('<%=basePath%>feedBack/process.do',{'userFeedbackId':'${userFeedbackId }'});">处理</a>
										<!-- /mis:checkPerm-->
									</s:if></td>
							</tr>
						</s:iterator>
						<tr bgcolor="#ffffff">
							<td colspan="2">总条数：<s:property
									value="pagination.totalResultSize" />
							</td>
							<td colspan="8" align="right"><s:property escape="false"
									value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" /></td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</div>
</body>
</html>
