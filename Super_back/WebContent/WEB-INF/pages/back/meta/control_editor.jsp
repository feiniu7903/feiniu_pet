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
<form action="" id="controlForm">
<input type="hidden" name="control.metaProductControlId" value="<s:property value="control.metaProductControlId"/>"/>
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
					<s:if test="control.productBranchId == null">
						<s:select id="productBranchId" name="control.productBranchId"
							 value="control.productBranchId"
							 list="branchList"
							 listKey="metaBranchId" listValue="branchName" headerKey="" headerValue="请选择" />
					</s:if>
					<s:else>
						<font style="font-weight:bold;">
							<s:property value="control.branchName"/>
						</font>
						<input type="hidden" id="productBranchId" name="control.productBranchId" value="<s:property value="control.productBranchId"/>"/>
					</s:else>
				</span>
			</s:if>
		</td>
	</tr>
	<tr>
		<td>
			<span>如果到期卖不完,能否退还给供应商:</span>
			<span style="width:70px;" id="backAbleGroup">
				<s:radio name="control.backAble"
						list="#{'true':'是','false':'否'}"></s:radio>
			</span>
			
			<span>能否延期:</span>
			<span style="width:70px;" id="delayAbleGroup">
				<s:radio name="control.delayAble" list="#{'true':'是','false':'否'}"></s:radio>
			</span>
			<script>
				var backAble = '<s:if test="control.backAble == null">false</s:if><s:else><s:property value="control.backAble"/></s:else>';
				if (backAble == 'true') {
					$('#backAbleGroup').find('input[value=true]').attr('checked',true);
				} else {
					$('#backAbleGroup').find('input[value=false]').attr('checked',true);
				}
				var delayAble = '<s:if test="control.delayAble == null">false</s:if><s:else><s:property value="control.delayAble"/></s:else>';
				if (delayAble == 'true') {
					$('#delayAbleGroup').find('input[value=true]').attr('checked',true);
				} else {
					$('#delayAbleGroup').find('input[value=false]').attr('checked',true);
				}
			</script>
		</td>
	</tr>
	<tr>
		<td>
			<span class="span_label">预控数量:</span>
			<span style="width:122px;">
				<input name="control.controlQuantity"
					onkeyup="doTextKeyUp(this)"
					value="<s:property value="control.controlQuantity"/>"
					onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
	    			maxLength="10"
				 	id="controlQuantity" type="text" class="input_style" />
			</span>
			<span>已销量:</span>
			<span class="span_label" style="font-weight:bold;"><s:property value="control.saleQuantity"/></span>
		</td>
	</tr>
	<tr>
		<td>
			<span class="span_label">销售起始日:</span>
			<input name="control.saleStartDate" 
					 type="text" readonly="readonly"
					 id="saleStartDate"
					 value="<s:property value="control.saleStartDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'saleEndDate\')}', onpicked:calculateDefaultTime})" />
			<span class="span_label">销售截止日:</span>
			<input name="control.saleEndDate"
					readonly="readonly" type="text"
					id="saleEndDate"
					value="<s:property value="control.saleEndDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width" onclick="WdatePicker({minDate:'#F{$dp.$D(\'saleStartDate\')}', onpicked:calculateDefaultTime})" />
		</td>
	</tr>
	<tr>
		<td>
			<span class="span_label">使用有效起始日:</span>
			<input name="control.startDate" 
					 type="text" readonly="readonly"
					 id="startDate"
					 value="<s:property value="control.startDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',minDate:'#F{$dp.$D(\'saleStartDate\')}'})" />
			<span class="span_label">使用有效截止日:</span>
			<input name="control.endDate"
					readonly="readonly" type="text"
					id="endDate"
					value="<s:property value="control.endDateStr"/>"
        			class="input_text01 table_input_other Wdate date_width" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\'&&\'saleEndDate\')}'})" />
		</td>
	</tr>
	<tr>
		<td>
			<span>到</span>
			<input name="control.firstTime" 
					 type="text" readonly="readonly"
					 value="<s:property value="control.firstTimeStr"/>"
        			class="input_text01 table_input_other Wdate date_width"  onclick="WdatePicker()" />
			<span>为止,如果实际销量比应销量低</span>
			<input name="control.firstLevel"
					value='<s:if test="control.firstLevel == null">30</s:if><s:else><s:property value="control.firstLevel"/></s:else>'
			 		onkeyup="doTextKeyUp(this)"
    				onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
    				maxLength="2"
			 		type="text"
			 		class="input_style" />
			<span>%,发邮件提醒我</span>
		</td>
	</tr>
	<tr>
		<td>
			<span>到</span>
			<input name="control.secondTime" 
					 type="text" readonly="readonly"
					 value="<s:property value="control.secondTimeStr"/>"
        			class="input_text01 table_input_other Wdate date_width"  onclick="WdatePicker()" />
			<span>为止,如果实际销量比应销量低</span>
			<input name="control.secondLevel"
					value='<s:if test="control.secondLevel == null">20</s:if><s:else><s:property value="control.secondLevel"/></s:else>' 
					onkeyup="doTextKeyUp(this)"
    				onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
    				maxLength="2"
					type="text" class="input_style" />
			<span>%,发邮件提醒我</span>
		</td>
	</tr>
	<tr>
		<td>
			<span>到</span>
			<input name="control.thirdTime" 
					 type="text" readonly="readonly"
					 value="<s:property value="control.thirdTimeStr"/>"
        			class="input_text01 table_input_other Wdate date_width"  onclick="WdatePicker()" />
			<span>为止,如果实际销量比应销量低</span>
			<input name="control.thirdLevel"
					value='<s:if test="control.thirdLevel == null">10</s:if><s:else><s:property value="control.thirdLevel"/></s:else>' 
					onkeyup="doTextKeyUp(this)"
    				onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
    				maxLength="2"
					type="text" class="input_style" />
			<span>%,发邮件提醒我</span>
		</td>
	</tr>
	<tr>
		<td>
			<span><input type="checkbox" name="control.notGot" value="true" <s:if test='control.notGot == "true"'>checked="checked"</s:if> /></span>
			<span>我希望达到上述时间时,实际销量未达到应销量就提醒我</span>
		</td>
	</tr>
	<tr>
		<td>
			<span><input type="checkbox" name="control.finishSale" value="true" <s:if test='control.finishSale == "true"'>checked="checked"</s:if> /></span>
			<span>我希望买断库存全部销售完的时候提醒我</span>
		</td>
	</tr>
	<tr>
		<td>
			<span style="font-weight:bold;color:red;">注:如果预警时间不填,则不会有邮件预警</span>
		</td>
	</tr>
</table>
</form>
<div style="text-align: center;">
	<input type="button" style="width: 100px;height: 20px;" value="保存" onclick="saveControl()">
	<input type="button" style="width: 100px;height: 20px;" value="关闭" onclick="closeControlDialog()">
</div>
</body>
</html>