<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<form action="<%=request.getContextPath()%>/meta/goBackMetaProuctList.do" style="padding:0px; margin:-35px 27px 5px 20px;">
	<input type="hidden" name="productType" value="${metaProduct.productType}"/>
  	<input type="submit" value="返回列表" style="float:right;" class="button01 add_margin_left add_margin_left01" />
</form>
