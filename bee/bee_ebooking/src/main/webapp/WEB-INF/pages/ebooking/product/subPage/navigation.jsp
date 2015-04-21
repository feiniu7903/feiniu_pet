<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" name="base_path" id="basepathhiddenid" value="${basePath}"/>
<input type="hidden" name="ebkProductViewTypeHidden" id="ebkproductviewtypehiddenid" value="<s:property value="ebkProductViewType"/>"/>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home"><a href="${basePath }ebookingindex.do">首页</a></li>
    	<li><a href="${basePath }product/queryProduct.do?ebkProductViewType=<s:property value="ebkProductViewType"/>">产品管理</a></li>
    	<li><a href="${basePath }product/queryProduct.do?ebkProductViewType=<s:property value="ebkProductViewType"/>"><s:property value="ebkProductViewTypeCh"/></a></li>
    	<s:if test="null!=ebkProdProductId"><li><s:hidden name="ebkProdProduct.metaName"/><a href="${basePath }ebooking/product/editEbkProductInit.do?ebkProdProductId=<s:property value="ebkProdProductId"/>&toShowEbkProduct=<s:property value="toShowEbkProduct"/>"><s:property value="ebkProdProduct.metaName" /></a></li></s:if>
    </ul>
    <s:if test="ebkProductViewType=='SURROUNDING_GROUP'">
    	<a href="http://www.lvmama.com/zt/ppt/ebk/zhoubian_guide.ppt" class="ppt_xz">产品管理操作PPT下载</a>
    </s:if>
     <s:if test="ebkProductViewType=='DOMESTIC_LONG'">
    	<a href="http://www.lvmama.com/zt/ppt/ebk/guonei_guide.ppt" class="ppt_xz">产品管理操作PPT下载</a>
    </s:if>
     <s:if test="ebkProductViewType=='ABROAD_PROXY'">
    	<a href="http://www.lvmama.com/zt/ppt/ebk/chujing_guide.ppt" class="ppt_xz">产品管理操作PPT下载</a>
    </s:if>
</div>