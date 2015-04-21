<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——采购产品列表</title>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/meta/product_list.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/meta/control.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css"/>
<link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css"/>
<script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>
<script type="text/javascript">
	var open_valid="<mis:checkPerm permCode="1919" permParentCode="${permId}">true</mis:checkPerm>";
	var close_valid="<mis:checkPerm permCode="2006" permParentCode="${permId}">true</mis:checkPerm>";
	function doTextKeyUp(text) {
		var v = text.value.replace(/[^\d]/g, '');
		text.value = v;
	}
</script>
</head>
  
<body>
<div class="main main01">
	<div class="row1">
    	<h3 class="newTit">产品预控配置列表</h3>
    	<form action="<%=basePath%>meta/searchControlList.do" method="post" id="query_form">
       <table width="980" border="0" cellspacing="0" cellpadding="0" class="newInput">
      <tr>
      		<td><em>产品名称：</em></td>
        	<td><input type="text" class="newtext1" name="controlCondition.productName" value="<s:property value="controlCondition.productName"/>" /></td>
        	<td><em>产品ID：</em></td>
        	<td><input type="text"
	        	onkeyup="doTextKeyUp(this)"
	   			onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
        	 	class="newtext1" name="controlCondition.productId" value="<s:property value="controlCondition.productId"/>" /></td>
        	<td><em>类别名称：</em></td>
        	<td><input type="text"  class="newtext1" name="controlCondition.branchName"  value="<s:property value="controlCondition.branchName"/>" /></td>
        	<td><em>类别ID：</em></td>
        	<td><input type="text" 
        		onkeyup="doTextKeyUp(this)"
	   			onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
        		class="newtext1" 
        		name="controlCondition.branchId"  
        		value="<s:property value="controlCondition.branchId"/>" /></td>
      </tr>
      <tr>
        <td><em>供应商名称：</em></td>
        <td><input type="text" id="searchSupplierName" name="controlCondition.supplierName" class="newtext1" value="<s:property value="controlCondition.supplierName"/>"/>
        <input type="hidden" name="controlCondition.supplierId" id="comSupplierId" value="<s:property value="controlCondition.supplierId"/>"/></td>
        <td><em>预控级别：</em></td>
        <td><s:select name="controlCondition.controlType"
			 value="controlCondition.controlType"
			 list="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_TYPE@values()"
			 listKey="code" listValue="cnName" headerKey="" headerValue="请选择" />
		</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td> 
      </tr>
      <tr>
      	<s:if test="subProductTypeList.size()>0">
        <td><em>产品类型：</em></td>
        <td><s:select name="subProductType" list="subProductTypeList" listKey="code" listValue="name" headerKey="" headerValue="请选择"/></td>
        </s:if>
        <td><input type="submit" class="button" value="查询"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </form>
    </div>
    <div class="row2">
    	<table width="96%" border="0" cellspacing="0" cellpadding="0" class="newTable">
              <tr class="newTableTit">
                <td style="width:170px;">供应商</td>
                <td style="width:40px;">预控级别</td>
                <td style="width:50px;">产品ID</td>
                <td style="width:90px;">产品名称</td>
                <td style="width:50px;">类别ID</td>
                <td style="width:90px;">类别名称</td>
                <td class="timew">销售起始日</td>
                <td class="timew">销售截止日</td>
                <td class="timew">使用有效起始日</td>
                <td class="timew">使用有效截止日</td>
                <td style="width:40px;">预控数量</td>
                <td style="width:150px;">操作</td>
              </tr>
              <s:iterator value="controlList" id="ct">
              <tr>
                <td><s:property value="supplierName"/></td>
                <td align="center"><s:property value="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_TYPE@getCnName(controlType)"/></td>
                <td align="center"><s:property value="productId"/></td>
                <td align="center"><s:property value="productName"/></td>
                <td align="center"><s:property value="productBranchId"/></td>
                <td align="center"><s:property value="branchName"/></td>
                <td align="center"><s:date name="saleStartDate" format="yyyy-MM-dd"/></td>
                <td align="center"><s:date name="saleEndDate" format="yyyy-MM-dd"/></td>
                <td align="center"><s:date name="startDate" format="yyyy-MM-dd"/></td>
                <td align="center"><s:date name="endDate" format="yyyy-MM-dd"/></td>
                <td align="center"><s:property value="controlQuantity"/></td>
                <td>
                	<a href="javascript:showCopyControlDialog(<s:property value="metaProductControlId"/>)">复制</a>
               		<a href="javascript:showModifyControlDialog(<s:property value="metaProductControlId"/>)">修改</a>
            		<a href="javascript:deleteControl(<s:property value="metaProductControlId"/>)">删除</a>
                </td>
              </tr>
               </s:iterator>
      </table>
      <table width="96%">
      	<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
      </table>
    </div>
</div>
<div id="control_div" url="<%=basePath%>meta/copyControl.do"></div>
</body>
</html>

