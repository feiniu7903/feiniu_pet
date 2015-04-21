<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——销售产品类型</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
</head>

<body>
<div class="main main03">
	<div class="row1">
    	<h3 class="newTit">产品列表<a href="#">添加新产品</a></h3>
        <dl class="ticketDl">
        
        	<dt>请选择需要建立的销售产品</dt>
            
            <s:if test='"TICKET"==productType'>
            	<dd><a href="<%=basePath%>prod/toAddProduct.do?productType=<s:property value="productType"/>">整体销售产品</a><span>销售产品类别跟采购产品类别一对一，不需要录入采购产品</span></dd>
            	<dd><a href="<%=basePath%>/prod/toAddTicketProduct.do?productType=<s:property value="productType"/>">普通销售产品</a><span>销售产品类别可以打包采购产品的多个类别，或者多个采购产品类别。</span></dd>
            </s:if>
             <s:elseif test='"HOTEL"==productType'>
            	<dd><a href="<%=basePath%>prod/toAddProduct.do?productType=<s:property value="productType"/>">整体销售产品</a><span>销售产品类别跟采购产品类别一对一，不需要录入采购产品</span></dd>
            	<dd><a href="<%=basePath%>/prod/toAddTicketProduct.do?productType=<s:property value="productType"/>">普通销售产品</a><span>销售产品类别可以打包采购产品的多个类别，或者多个采购产品类别。</span></dd>
            </s:elseif>
            <s:elseif test='"ROUTE"==productType'>
            	<dd><a href="<%=basePath%>prod/toAddProduct.do?productType=<s:property value="productType"/>">整体销售产品</a><span>销售产品类别跟采购产品类别一对一，不需要录入采购产品</span></dd>
            	<dd><a href="<%=basePath%>/prod/toAddTicketProduct.do?productType=<s:property value="productType"/>">普通销售产品</a><span>销售产品类别可以打包采购产品的多个类别，或者多个采购产品类别。</span></dd>
            </s:elseif>
            <s:else>
            	<dd><a href="<%=basePath%>prod/toAddProduct.do?productType=<s:property value="productType"/>">整体销售产品</a><span>销售产品类别跟采购产品类别一对一，不需要录入采购产品</span></dd>
            	<dd><a href="<%=basePath%>/prod/toAddTicketProduct.do?productType=<s:property value="productType"/>">普通销售产品</a><span>销售产品类别可以打包采购产品的多个类别，或者多个采购产品类别。</span></dd>
             </s:else>
            <dd><a href="<%=basePath%>prod/toAddProduct.do">单独销售产品</a><span>整体采购分拆的产品，不需要关联采购产品；不能单独销售，只为自主打包的自由行产品关联。</span></dd>
        </dl>
    </div><!--row1 end-->
</div><!--main01 main03  end-->
</body>
</html>




