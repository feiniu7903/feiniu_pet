<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<title></title>
<style type="text/css">
	table tr {
		height:30px;
	}
	table tr span {
		display:inline-block;
	}
	.input_style {
		border: solid 1px #999;
		height: 10px;
		width: 60px;
	}
	.date_width {
		width:120px;
	}
	.span_label {
		width:90px;
	}
</style>
</head>
<body>
<form action="" id="controlBatchForm">
<input type="hidden" name="control.productId" value="<s:property value="control.productId"/>"/>
<input type="hidden" name="control.controlType" value="<s:property value="control.controlType"/>"/>
<input type="hidden" name="control.saleQuantity" value="<s:property value="control.saleQuantity"/>"/>
<table>
	<tr>
		<td>
			<span class="span_label">产品名称:</span>
			<span style="width:160px;font-weight:bold;"><s:property value="control.productName"/></span>
			
			<s:if test="control.controlType == 'BRANCH_LEVEL'">
				<span>类别:</span>
				<span style="width:100px;">
					<s:select id="_productBranchId" name="control.productBranchId"
						 value="control.productBranchId"
						 list="branchList"
						 listKey="metaBranchId" listValue="branchName" headerKey="" headerValue="请选择" />
				</span>
			</s:if>
		</td>
	</tr>
	<tr>
		<td>
			<span>如果到期卖不完,能否退还给供应商:</span>
			<span style="width:70px;" id="_backAbleGroup">
				<s:radio name="control.backAble"
						list="#{'true':'是','false':'否'}"></s:radio>
			</span>
			
			<span>能否延期:</span>
			<span style="width:70px;" id="_delayAbleGroup">
				<s:radio name="control.delayAble" list="#{'true':'是','false':'否'}"></s:radio>
			</span>
			<script>
				var backAble = '<s:if test="control.backAble == null">false</s:if><s:else><s:property value="control.backAble"/></s:else>';
				if (backAble == 'true') {
					$('#_backAbleGroup').find('input[value=true]').attr('checked',true);
				} else {
					$('#_backAbleGroup').find('input[value=false]').attr('checked',true);
				}
				var delayAble = '<s:if test="control.delayAble == null">false</s:if><s:else><s:property value="control.delayAble"/></s:else>';
				if (delayAble == 'true') {
					$('#_delayAbleGroup').find('input[value=true]').attr('checked',true);
				} else {
					$('#_delayAbleGroup').find('input[value=false]').attr('checked',true);
				}
			</script>
		</td>
	</tr>
	<tr>
		<td>
			<span class="span_label">预控数量:</span>
			<span>
				<input name="control.controlQuantity"
					onkeyup="doTextKeyUp(this)"
					value="<s:property value="control.controlQuantity"/>"
					onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
	    			maxLength="10"
				 	id="_controlQuantity" type="text" class="input_style" />
			</span>
			<span style="color:red;">若每天预控量不同,预控数量可不填默认为0,可在预控配置中修改</span>
		</td>
	</tr>
	<tr>
		<td>
			<span class="span_label">销售起始日:</span>
			<input name="control.saleStartDate" 
					 type="text" readonly="readonly"
					 id="_saleStartDate"
					 value="<s:property value="control.saleStartDateStr"/>"
        			 class="input_text01 table_input_other Wdate date_width"
        			 onclick="WdatePicker({onpicked:function(){$('#_startDate').val($('#_saleStartDate').val())}, maxDate:'#F{$dp.$D(\'_saleEndDate\')}'})" />
        	<span class="span_label">销售截止日:</span>
			<input name="control.saleEndDate"
					readonly="readonly" type="text"
					id="_saleEndDate"
					value="<s:property value="control.saleEndDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width" onclick="WdatePicker({minDate:'#F{$dp.$D(\'_saleStartDate\')}', onpicked:function(){$('#_endDate').val($('#_saleEndDate').val())}})" />
		</td>
	</tr>
	<tr>
		<td>
			<span class="span_label">使用有效起始日:</span>
			<input name="control.startDate" 
					 type="text" readonly="readonly"
					 id="_startDate"
					 value="<s:property value="control.startDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'_endDate\')}',minDate:'#F{$dp.$D(\'_saleStartDate\')}'})" />
			<span class="span_label">使用有效截止日:</span>
			<input name="control.endDate"
					readonly="readonly" type="text"
					id="_endDate"
					value="<s:property value="control.endDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width" onclick="WdatePicker({minDate:'#F{$dp.$D(\'_startDate\'&&\'_saleEndDate\')}'})" />
		</td>
	</tr>
</table>
</form>
<div style="text-align: center;">
	<input type="button" style="width: 100px;height: 20px;" value="保存" onclick="saveBatchControl()">
	<input type="button" style="width: 100px;height: 20px;" value="关闭" onclick="closeBatchControlDialog()">
</div>
</body>
</html>