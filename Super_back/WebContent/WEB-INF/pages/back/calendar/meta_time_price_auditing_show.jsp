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
    <title>super后台——采购时间价格表</title>
    <style type="text/css">
        .calculateHours {
            width: 20px;
        }
    </style>

    <script type="text/javascript">
        $(document).ready(function () {
            $(".time_price_dlg_div .proLabel tr").hide();
            $(".time_price_dlg_div .proLabel tr.updatePrice").show();
        });
    </script>
    <script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
</head>

<body>
<input type="hidden" id="metaProductProductType" value="${metaProduct.productType}"/>
<input type="hidden" id="metaProductSubProductType" value="${metaProduct.subProductType}"/>
<input type="hidden" id="isAperiodic" value="${metaProduct.isAperiodic}"/>
<input type="hidden" id="currPageDate" value="<s:date name="currPageDate" format="yyyy-MM-dd"/>"/>
<input type="hidden" id="metaProductId" value="${metaProductId}"/>
<input type="hidden" id="metaBranchId" value="${metaBranchId}"/>
<input type="hidden" id="payToSupplier" value="${metaProduct.payToSupplier}"/>

<div class="Rilimid">
    <div id="metaTimeDiv" class="timeDiv">
        <s:include value="/WEB-INF/pages/back/calendar/meta_time.jsp"/>
    </div>
    <s:if test="editable">
        <form onsubmit="return false">
            <input type="hidden" name="timePriceBean.metaBranchId" value="${metaBranchId}"/>
            <input type="hidden" name="timePriceBean.productId" value="${metaProductId}"/>

            <div class="row4Time">
                <div class="newTab">
                </div>
            </div>
            <!--row4Time end-->
        </form>
    </s:if>
</div>
</body>
</html>


