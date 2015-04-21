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
    <title>super后台——门票销售产品</title>
    <%@ include file="/WEB-INF/pages/back/base/jquery.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/prod/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
    <%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/prod/validate.js"></script>
    <script type="text/javascript">
	var current_product_type="TICKET";
	</script>
    <script type="text/javascript" src="<%=basePath%>js/prod/product.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/prod/sensitive_word.js"></script>
</head>
<body>
<div class="main main08">
    <div class="row1">
        <h3 class="newTit">销售产品信息

    </div>
    <!--row1 end-->
    <form method="post" action="<%=basePath%>prod/saveTicketProduct.do" name="prodFrom" class="mySensitiveForm">
        <s:hidden name="product.productId" id="productId"/>
        <s:hidden name="product.productType"/>
        <div class="row2">

            <s:include value="/WEB-INF/pages/back/prod/base/header.jsp"></s:include>

            <table border="0" cellspacing="0" cellpadding="0" class="newTableB">
                <tr>
                    <td><em>门票子类型：<span class="require">[*]</span></em></td>
                    <td colspan="3"><s:radio list="subProductTypeList" name="product.subProductType" listKey="code" listValue="name"/></td>
                </tr>
                <tr>
                    <td><em>门票区域：</em></td>
                    <td colspan="3"><s:radio name="product.isForegin" list="#{'N':'境内门票','Y':'境外门票'}" listKey="key" listValue="value" labelSeparator="&nbsp;"/>
                        <s:select name="product.regionName" list="regionNamesList" listKey="code" listValue="name"></s:select>
                    </td>
                </tr>
            </table>
            <%@include file="/WEB-INF/pages/back/prod/base/other_prop.jsp" %>
            <span class="msg"></span>

        </div>
        <!--row2 end-->
    </form>
</div>
<!--main01 main04 end-->
</body>
</html>
