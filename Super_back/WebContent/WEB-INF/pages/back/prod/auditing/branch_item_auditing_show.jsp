<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="row2">
    <table class="newTable" width="100%" border="0" cellspacing="0" cellpadding="0" id="branch_tb">
        <tr class="newTableTit" align="center">
            <td>采购产品</td>
            <td>类别</td>
            <td>数量</td>
            <td>人数</td>
            <td>操作</td>
        </tr>
        <s:iterator value="branchItemList">
            <tr id="tr_item_<s:property value="branchItemId"/>" result="<s:property value="branchItemId"/>">
                <td><s:property value="metaProduct.productName"/>(<s:property value="metaProduct.metaProductId"/>)</td>
                <td><s:property value="metaBranch.branchName"/>(<s:property value="metaBranchId"/>)</td>
                <td><s:property value="quantity"/></td>
                <td>
                    成人:<s:property value="metaBranch.adultQuantity"/><br/>
                    儿童:<s:property value="metaBranch.childQuantity"/>
                </td>
                <td>
                    <a href="#timeprice" class="showTimePrice" tt="META_PRODUCT_AUDITING_SHOW" param="{'metaBranchId':<s:property value="metaBranchId"/>}">查看时间价格</a>
                </td>
            </tr>
        </s:iterator>
    </table>
</div>