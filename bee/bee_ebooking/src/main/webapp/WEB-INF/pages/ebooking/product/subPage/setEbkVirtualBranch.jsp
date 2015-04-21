<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="dialog-body">
	<div data-content="content" class="dialog-content clearfix">
	<input type="hidden" name="ebkProdProductId" id="ebkProdProductVirtualId" value="${ebkProdProductId }" />
	<input type="hidden" name="prodBranchId" id="prodVirtualBranchId" value="${prodBranchId }" />
		<table class="rowpro" style="border: none">
	        <tr>
	            <td>
	            <s:if test="ebkProdBranchList != null && ebkProdBranchList.size > 0">
					<s:iterator value="ebkProdBranchList" var="prodBranch">
						<input class="virtualBranchcheckbox" type="checkbox" value="<s:property value="prodBranchId"/>" 
							<s:if test="true==#prodBranch.isInVirtualBranch">
								checked="checked" 
							</s:if>
						><s:property value="branchName"/>&nbsp;
					</s:iterator>
				</s:if>
	            </td>
	        </tr>
	    </table>
	    <span class="btn_bc checkedVirtualBranchOK">确定</span>　<span class="btn_bc checkedVirtualBranchCancel">取消</span>
	    <br><br><br>
	     <b style="color: #999999;">（注:最少需要共享2个类别的库存。如不再使用共享库存，则直接将共享库存类别删除即可。）</b>
	</div>
	