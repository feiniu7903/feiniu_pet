<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form action="<%=request.getContextPath()%>/prod/goBackProductList.do" style="padding:0px; margin:-35px 27px 5px 20px;">
	<input type="hidden" name="productType" value="${product.productType}"/>
	<input type="hidden" name="subProductType" value="${product.subProductType}"/>
  	<input type="submit" value="返回列表" style="float:right;" class="button01 add_margin_left add_margin_left01"/>
</form>
