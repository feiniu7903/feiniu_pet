<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>super后台——关联附加产品</title>
    <%@ include file="/WEB-INF/pages/back/base/jquery.jsp" %>
    <%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/prod/additional.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/timeprice/time.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>

<body>
<div class="main main07">
    <div class="row1">
        <h3 class="newTit">销售产品信息</h3>
    </div>
    <!--row1 end-->
    <div class="row2">
        <table width="90%" border="0" cellspacing="0" cellpadding="0" class="additionaTable newTable" id="additiona_tb">
            <tr class="tableTit newTableTit">
                <td>类型</td>
                <td>产品ID</td>
                <td>产品名称</td>
                <td>类别</td>
                <td width="70" style="padding:0 10px">产品数量属性</td>
                <td>价格</td>
                <td>计价单位</td>
            </tr>
            <s:iterator value="productList">
                <tr id="tr_<s:property value="relationId"/>" relationId="<s:property value="relationId"/>">
                    <td><s:property value="relationProduct.zhSubProductType"/></td>
                    <td><s:property value="relatProductId"/></td>
                    <td><s:property value="relationProduct.productName"/></td>
                    <td><s:property value="branch.branchName"/>(<s:property value="prodBranchId"/>)</td>
                    <td width="70" style="padding:0 10px">
                        <input class="saleNumType" name="require_<s:property value="relationId"/>" type="radio" value="OPT" <s:if test='saleNumType=="OPT"'>checked="checked"</s:if>/><label>任选</label>
                        <input class="saleNumType" name="require_<s:property value="relationId"/>" type="radio" value="ANY" <s:if test='saleNumType=="ANY"'>checked="checked"</s:if>/><label>可选</label>
                        <input class="saleNumType" name="require_<s:property value="relationId"/>" type="radio" value="ALL" <s:if test='saleNumType=="ALL"'>checked="checked"</s:if>/><label>等量</label>
                    </td>
                    <td><a href="#view" tt="PROD_PRODUCT" class="showTimePrice" param="{prodBranchId:<s:property value="branch.prodBranchId"/>,editable:false}">查看</a></td>
                    <td><s:if test="branch!=null"><s:property value="branch.priceUnit"/></s:if></td>
                </tr>
            </s:iterator>
            <s:iterator value="petProductList">
                <tr id="tr_<s:property value="relationId"/>" relationId="<s:property value="relationId"/>">
                    <td><s:property value="relatedProduct.zhSubProductType"/></td>
                    <td><s:property value="relatProductId"/></td>
                    <td><s:property value="relatedProduct.productName"/></td>
                    <td></td>
                    <td width="70" style="padding:0 10px">
                        <input class="saleNumType2" name="require_<s:property value="relationId"/>" type="radio" value="OPT" <s:if test='relatedProduct.saleNumType=="OPT"'>checked="checked"</s:if>/><label>任选</label>
                        <input class="saleNumType2" name="require_<s:property value="relationId"/>" type="radio" value="ANY" <s:if test='relatedProduct.saleNumType=="ANY"'>checked="checked"</s:if>/><label>可选</label>
                        <input class="saleNumType2" name="require_<s:property value="relationId"/>" type="radio" value="ALL" <s:if test='relatedProduct.saleNumType=="ALL"'>checked="checked"</s:if>/><label>等量</label>
                    </td>
                    <td><s:property value="relatedProduct.sellPriceYuan"/></td>
                    <td></td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <!--row2 end-->
</div>
<!--main01 main05 end-->
</body>
</html>


