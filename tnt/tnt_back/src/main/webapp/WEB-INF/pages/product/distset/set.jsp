<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<sf:form id="prodPolicyForm" modelAttribute="tntProdPolicy"
	method="post" action="/product/distset" target="_top">
	<sf:hidden path="tntPolicyId" />
	<sf:hidden path="branchId" />
	<sf:hidden path="productId" />
	<sf:hidden path="targetId" />
	<sf:hidden path="targetType" />
	<sf:hidden path="channelId" />
	<table class="p_table form-inline">
		<tbody>
			<tr>
				<td class="p_label"><span class="notnull">*</span>分销渠道类型：</td>
				<td><lv:mapValueShow key="${tntProdPolicy.channelId }"
						map="${channelMap }" /></td>
			</tr>
			<c:if test="${'PROD_CHANNEL'==tntProdPolicy.targetType}">
				<tr>
					<td class="p_label"><span class="notnull">*</span>是否分销：</td>
					<td><sf:radiobutton path="canDist" value="true" />是 <sf:radiobutton
							path="canDist" value="false" />否</td>
				</tr>
			</c:if>
			<c:if test="${'PROD_DISTRIBUTOR'==tntProdPolicy.targetType}">
				<tr>
					<td class="p_label"><span class="notnull">*</span>分销商类型：</td>
					<td><lv:mapValueShow map="${companyTypeMap }"
							key="${tntProdPolicy.user.detail.companyTypeId }" /></td>
				</tr>
				<tr>
					<td class="p_label"><span class="notnull">*</span>分销商名称：</td>
					<td>${tntProdPolicy.user.realName }</td>
				</tr>
			</c:if>
			<tr>
				<td class="p_label"><span class="notnull">*</span>分销价格设置：</td>
				<td><sf:radiobutton path="policyType" id="1" value="CUT_PROFIT" />按毛利润<sf:radiobutton
						path="policyType" id="2" value="CUT_SALE_PRICE" />按销售价</td>
			</tr>
			<tr>
				<td></td>
				<td><span id="p1">分销价=销售价-（销售价-结算价）*<sf:input
							path="discountY" cssStyle="width:25px;" />%
				</span> <span id="p2">分销价=销售价*（1- <sf:input path="discountY"
							cssStyle="width:25px;" />%）
				</span></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>分销库存设置：</td>
				<td><input type="radio" checked="checked" />共享主站库存</td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>点评返现设置：</td>
				<td><input type="radio" checked="checked" />不返现</td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>是否参与返佣：</td>
				<td><sf:radiobutton path="rebate" value="true" disabled="true" />是<sf:radiobutton
						path="rebate" value="false" disabled="true" />否</td>
			</tr>
		</tbody>
	</table>
</sf:form>
<input class="pbtn pbtn-small btn-ok" id="prodPolicyButton"
	style="float: right; margin-top: 20px;" type="button" value="保存" />
<script type="text/javascript">
	$().ready(function() {
		var checkPolicyType = function(radio) {
			var id = radio.attr("id");
			var p = $("#p" + id);
			if (radio.attr("checked") == "checked") {
				p.show();
				p.find("input").attr("name", "discountY");
			} else {
				p.hide();
				p.find("input").attr("name", "hidDiscountY");
			}
		};

		$("#prodPolicyForm").validate(prodPolicy);
		var value = $("input[name='policyType']");
		value.each(function(i) {
			var r = $(value[i]);
			checkPolicyType(r);
			r.click(function() {
				value.each(function(i) {
					var r = $(value[i]);
					checkPolicyType(r);
				});
			});
		});

	});

	$("#prodPolicyButton").bind("click", function() {
		var form = $("#prodPolicyForm");
		if (!form.validate().form()) {
			return;
		}
		form.ajaxSubmit({
			success : function(data) {
				var targetType = '${tntProdPolicy.targetType}';
				var url = "/tnt_back/product/distset/";
				if ("PROD_CHANNEL" == targetType) {
					url += "channel/${tntProdPolicy.branchId}";
				} else {
					url += "user?branchId=${tntProdPolicy.branchId}";
				}
				window.location.href = url;
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常，请确保您提交的数据的正确！");
			}
		});
	});
</script>

