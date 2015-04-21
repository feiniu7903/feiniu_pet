<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="kcwh_table" style="width:95%">
	<thead>
		<tr>
			<th width="80">产品类型</th>
			<th width="120">价种名称</th>
			<th width="80">成人数</th>
			<th width="80">儿童数</th>
			<th width="80">主类别</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
			<s:iterator value="ebkProdBranchList" var="branchitem" id="column">
				<tr>
					<td><s:iterator value="branchTypeMap" id="type">
							<s:if test="branchType==#type.key">
								<s:property value="#type.value" />
							</s:if>
						</s:iterator></td>
					<td title="<s:property value="branchName"/>"><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#column.branchName,15)" /></td>
					<td><s:property value="adultQuantity" /></td>
					<td><s:property value="childQuantity" /></td>
					<td><s:if test='"true"==defaultBranch'>是</s:if><s:else>否</s:else></td>
					<td prodbranchid="<s:property value="prodBranchId"/>"
						branchtype="<s:property value="branchType"/>">
						<s:if test='"SHOW_EBK_PRODUCT"!=toShowEbkProduct'>
						<a href="javascript:void(0)" class="ebkprodbranchupdate">修改</a>　
						<s:if test="branchType!='VIRTUAL'">
							<a hred="javascript:void(0)" class="ebkProdbranchFirst" defaultbranch="<s:property value="defaultBranch"/>">设为主类别</a>　
						</s:if>
						<s:else>
							<a hred="javascript:void(0)" class="setEbkVirtualBranch" defaultbranch="<s:property value="defaultBranch"/>">设共享库存</a>　
						</s:else>
						<a href="javascript:void(0)" class="ebkprodbranchdelete" defaultbranch="<s:property value="defaultBranch"/>">删除</a>　
						</s:if>
						<a href="javascript:void(0)" class="viewprodbranchpricestore">价格库存</a>　
						<a href="javascript:void(0)" class="viewprodbranchlog">类别日志</a>　
						<a href="javascript:void(0)" class="viewprodbranchpricestorelog">价格库存日志</a>
					</td>
				</tr>
			</s:iterator>
	</tbody>
	<tbody><tr><td colspan="6">
	<div  id="queryProdTimePriceStockTbody"></div>
	</td></tr></tbody>
</table>