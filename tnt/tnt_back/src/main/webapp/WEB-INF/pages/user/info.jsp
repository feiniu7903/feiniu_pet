<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<body>
	<sf:form id="infoApplyForm" modelAttribute="tntUser"
		action="/user/waiting" target="_top" method="post">
		<input type="hidden" name="_method" value="put" />
		<sf:hidden path="userId" id="userId" />
		<table class="e_table form-inline ">
			<tbody>
				<tr>
					<td colspan="2">会员登录名</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>会员登录名：</td>
					<td>${tntUser.userName}</td>
				</tr>
				<tr>
					<td colspan="2">个人资料和联系方式</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>用户真实姓名：</td>
					<td>${tntUser.realName}</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>性别：</td>
					<td><lv:mapValueShow key="${tntUser.detail.gender}"
							map="${genderMap }" /></td>
				</tr>
				<tr>
					<td class="e_label td_top">用户职务：</td>
					<td>${tntUser.detail.duties}</td>
				</tr>
				<tr>
					<td class="e_label td_top">所在部门：</td>
					<td>${tntUser.detail.department}</td>
				</tr>
				<tr>
					<td class="e_label td_top">常用固定电话：</td>
					<td>${tntUser.detail.phoneNumber}</td>
				</tr>
				<tr>
					<td class="e_label td_top">传真：</td>
					<td>${tntUser.detail.faxNumber}</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>手机：</td>
					<td>${tntUser.detail.mobileNumber}</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>通讯地址：</td>
					<td>${tntUser.detail.address}</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>邮编：</td>
					<td>${tntUser.detail.zipCode}</td>
				</tr>
				<tr>
					<td class="e_label td_top"><i class="cc1">*</i>电子邮件：</td>
					<td>${tntUser.detail.email}</td>
				</tr>
				<tr>
					<td class="e_label td_top">网址：</td>
					<td>${tntUser.detail.netUrl}</td>
				</tr>
				<tr>
					<td class="e_label td_top">QQ：</td>
					<td>${tntUser.detail.qqAccount}</td>
				</tr>
				<c:if test="${tntUser.isCompany }">
					<tr>
						<td colspan="2">公司相关资料</td>
					</tr>
					<tr>
						<td class="e_label td_top"><i class="cc1">*</i>公司所在地：</td>
						<td>${provinceName} ${cityName }</td>
					</tr>
					<tr>
						<td class="e_label td_top"><i class="cc1">*</i>公司名称：</td>
						<td>${tntUser.detail.companyName}</td>
					</tr>

					<tr>
						<td class="e_label td_top"><i class="cc1">*</i>行业类别：</td>
						<td><lv:mapValueShow key="${tntUser.detail.companyTypeId}"
								map="${companyTypeMap }" /></td>
					</tr>
					<tr>
						<td class="e_label td_top"><i class="cc1">*</i>客服负责人：</td>
						<td>${tntUser.detail.chargeName}</td>
					</tr>
					<tr>
						<td class="e_label td_top"><i class="cc1">*</i>客服电话：</td>
						<td>${tntUser.detail.serviceTel}</td>
					</tr>
					<tr>
						<td class="e_label td_top" width="100px">公司简介：</td>
						<td>${tntUser.detail.companyProfile}</td>
					</tr>
					<tr>
						<td class="e_label td_top">法人代表：</td>
						<td>${tntUser.detail.legalRepresentative}</td>
					</tr>
					<tr>
						<td class="e_label td_top">员工人数：</td>
						<td>${tntUser.detail.employeeNum}</td>
					</tr>
				</c:if>
				<c:if test="${canApprove==true }">
					<tr>
						<td colspan="2"><div class="fl operate">
								<a class="btn btn_cc1" href="javascript:agree()">审核通过并发送激活邮件</a><a
									href="javascript:showRejectBox()" class="btn">审核不通过并发送通知邮件</a>
							</div></td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</sf:form>

	<div style="display: none" id="rejectBox">
		<sf:form id="infoRejectForm" method="post" modelAttribute="tntUser"
			target="_top" action="/user/waiting">
			<sf:hidden path="userId" />
			<input type="hidden" name="_method" value="delete" />
			<div class="iframe_content pd0">
				<div>基本信息审核不通过原因：</div>
				<div>
					<sf:textarea path="detail.failReason" cssClass="required" rows="5"
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
					"detail.failReason" : {
						required : true,
						maxCNLen : 300
					}
				}
			});
		</script>
	</div>
	<script type="text/javascript">
		var showRejectBox = function() {
			$.dialog({
				width : 400,
				title : " 分销商基本信息审核不通过",
				content : $("#rejectBox").html()
			});
		};

		var lock = false;
		var agree = function() {
			if (!lock) {
				$('#infoApplyForm').ajaxSubmit({
					beforeSubmit : function() {
						lock = true;
					},
					success : function(data) {
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常！");
						lock = false;
					}
				});
			}
		};

		var reject = function() {
			var form = $("#infoRejectForm");
			if (!form.validate().form()) {
				return;
			}
			if (!lock) {
				form.ajaxSubmit({
					beforeSubmit : function() {
						lock = true;
					},
					success : function(data) {
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常！");
						lock = false;
					}
				});
			}
		};
	</script>
</body>
</html>