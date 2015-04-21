<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript">

    var current_product_id =<s:if test="productId!=null">${productId}</s:if><s:else>${-1}</s:else>;
    var has_visa_prod = <s:if test="product.subProductType=='VISA'">true</s:if><s:else>false</s:else>;
    var current_product_type = '${product.productType}';
    var has_self_pack = '<s:property value="product.hasSelfPack()"/>';

    function go(url,obj) {

        if (current_product_id == -1) {
            alert("请保存基本信息再操作其他的页面");
            return;
        }
        var fullurl = "";
        if (url == 'base') {
            if (has_visa_prod) {
                fullurl = '/super_back/prod/editAsiaProduct.do?productId=' + current_product_id;
            } else {
                fullurl = '/super_back/prod/toProductAuditingShow.do?productId=' + current_product_id;
            }
        } else if (url == 'toViewJourneyAuditingShow' || url == 'toViewContentAuditingShow') {
            fullurl = '/super_back/view/' + url + '.do?productId=' + current_product_id;
        } else if (url == 'queryProdBusinessCouponListAuditingShow') {
            var metaType = 'SALES';
            fullurl = '/super_back/prod/' + url + '.do?productId=' + current_product_id + '&metaType=' + metaType;
        } else {
            fullurl = '/super_back/prod/' + url + '.do?productId=' + current_product_id;
        }

        document.getElementById("myframe").src = fullurl;

        $(".newCurrent").removeClass("newCurrent");
        $(obj).parent().addClass("newCurrent");


    }
</script>
<div class="nav">
    <ul class="newNav">
        <li class="newCurrent">
            <a onclick="go('base',this)" id="baseA">基本信息</a>
        </li>
        <s:if test="product.subProductType!='VISA'">
            <li>
                <a onclick="go('toProductPlaceAditingShow',this)">标的</a>
            </li>
            <s:if test="product.hasSelfPack()">
                <li>
                    <a onclick="go('toSelfPackJourneyAuditingShow',this)">行程</a>
                </li>
            </s:if>
        </s:if>
        <li>
            <a onclick="go('toProductBranchAuditingShow',this)">类别</a>
        </li>
        <s:if test="product.subProductType!='VISA'">
            <li>
                <a onclick="go('toProductAdditionalAuditingShow',this)">附加产品</a>
            </li>
            <s:if test="product.productType!='TRAFFIC'">
                <li>
                    <a onclick="go('toProductImageAuditingShow',this)">产品图片</a>
                </li>
                <li>
                    <a onclick="go('toViewContentAuditingShow',this)">描述信息</a>
                </li>
                <li>
                    <a onclick="go('toViewJourneyAuditingShow',this)">行程展示</a>
                </li>
                <li>
                    <a onclick="go('toProductOtherAuditingShow',this)">其他信息</a>
                </li>
                <s:if test="(!product.hasSelfPack())&&product.isPaymentToLvmama()">
                    <li>
                        <a onclick="go('queryProdBusinessCouponListAuditingShow',this)">设置优惠</a>
                    </li>
                </s:if>
            </s:if>
        </s:if>
    </ul>
</div>




