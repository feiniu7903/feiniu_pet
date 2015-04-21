<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查询关联记录</title>
<script type="text/javascript">
	$(function() {
		$("input.date").datepicker({dateFormat:'yy-mm-dd',
			changeMonth: true,
			changeYear: true,
			showOtherMonths: true,
			selectOtherMonths: true,
			buttonImageOnly: true
		}).attr("readonly", true);
		
		$("#receive_link_certificate_submit")
				.click(
						function() {
							var $form = $(this).parents("form");
							var operator = $.trim($form.find(
									"input[name=operator]").val());
							if (operator == "") {
								alert("操作人不能为空！");
								return false;
							}
							var arr = [];
							$form.find("input").each(function(){
							    arr.push(this.name+'='+encodeURIComponent(this.value));
							});
							var data = arr.join("&");
							refreshLinkCertificateList($form.attr("action") + "?" + data);
						});
	})
	
	function refreshLinkCertificateList(url) {
		$("#receive_link_certificate_div").load(url, function(){
    		$(this).dialog({
    			modal:true,
    			width : 1000,
				title : '查询关联记录'
        	});	
    	});
	}
	
</script>
</head>
<body>
	<div class="iframe-content">
		<div class="p_box">
			<form action="${basePath }/fax/faxReceive/searchRelateList.do"
				method="post">
				<table border="0" cellspacing="0" cellpadding="0"
					class="p_table form-inline">
					<tr>
						<td class="p_label">操作人：</td>
						<td><s:textfield name="operator" /></td>
						<td class="p_label">关联时间：</td>
						<td><s:textfield name="minOperateTime" cssClass="date" />~<s:textfield
								name="maxOperateTime" cssClass="date" /></td>
						<td class="tc"><input type="button" value="查询"
							id="receive_link_certificate_submit" class="btn btn-small" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="p_box">
			<table class="p_table table-center">
				<tr>
					<th>回传编号</th>
					<th>关联凭证</th>
					<th>关联时间</th>
					<th>回传状态</th>
					<th>回传备注</th>
					<th>操作人</th>
				</tr>
				<s:iterator value="ordFaxRecvLinkPage.items">
					<tr>
						<td>${ordFaxRecvId }</td>
						<td>${ebkCertificateId }</td>
						<td>${zhCreateTime }</td>
						<td>${zhResultStatus }</td>
						<td>${memo }</td>
						<td>${operator }</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="2">总条数：<s:property
							value="ordFaxRecvLinkPage.totalResultSize" />
					</td>
					<td colspan="4" align="right"><s:property escape="false"
							value="@com.lvmama.comm.utils.Pagination@pagination(ordFaxRecvLinkPage.pageSize,ordFaxRecvLinkPage.totalPageNum,ordFaxRecvLinkPage.url,ordFaxRecvLinkPage.currentPage,'js')" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>