<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="dialog-body">
	<div data-content="content" class="dialog-content clearfix">
		<table class="xzkc_1">
			<tbody>
				<tr>
					<td width="105" align="right">选择类型：</td>
					<td>&nbsp;&nbsp;<s:select list="branchTypeMap" listKey="key"
							name="ebkProdBranch.branchType" listValue="value"
							value="ebkProdBranch.branchType" headerKey="" headerValue="请选择"/>
							</td>
				</tr>
				<tr>
					<td align="right">价种名称：</td>
					<td>
						<s:if test="'VIRTUAL'==ebkProdBranch.branchType">
							<s:textfield name="ebkProdBranch.branchName" id="branch_name" maxLength="50" readonly='true'/></td>
						</s:if>
						<s:else>
							<s:textfield name="ebkProdBranch.branchName" id="branch_name" maxLength="50"/></td>
						</s:else>
				</tr>
				<tr>
					<td align="right">成人数：</td>
					<td><s:textfield name="ebkProdBranch.adultQuantity"
							id="adult_quantity" maxLength="4"/></td>
				</tr>
				<tr>
					<td align="right">儿童数：</td>
					<td><s:textfield name="ebkProdBranch.childQuantity"
							id="child_quantity" maxLength="4" /></td>
				</tr>
				<tr>
					<td align="right"><s:hidden name="ebkProdBranch.prodBranchId"
							id="prod_branch_id" /><input type="hidden" value="${ebkProdBranch.branchType }"
							id="prod_branch_old_type" /><s:hidden name="ebkProdBranch.defaultBranch"/><span class="fp_btn ebkprodbranchsavebtn">保存</span>
					</td>
					<td>　<span><font color="red"  id="saveebkprodbranerrormessage"></font></span></td>
				</tr>
			</tbody>
		</table>
	</div>