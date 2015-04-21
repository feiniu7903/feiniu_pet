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
</script>
</head>
  
  
<body>
<div class="main main01">
	<div class="row1">
    	<h3 class="newTit confirm">产品列表</h3>
    	<form action="<%=basePath%>meta/metaProductList2.do" method="post"><s:hidden name="productType"/>
       <table width="980" border="0" cellspacing="0" cellpadding="0" class="newInput">
      <tr>
      		<td><em>产品名称：</em></td>
        	<td><input type="text" class="newtext1" name="productName" value="<s:property value="productName"/>" /></td>
        	<td><em>产品ID：</em></td>
        	<td><input type="text" class="newtext1" name="metaProductId" value="<s:property value="metaProductId"/>" /></td>
        	<td><em>产品编号：</em></td>
        	<td><input type="text"  class="newtext1" name="bizCode"  value="<s:property value="bizCode"/>" /></td>
      </tr>
      <tr>
        <td><em>供应商名称：</em></td>
        <td><input type="text" id="searchSupplierName" name="supplierName" class="newtext1" value="<s:property value="supplierName"/>"/><input type="hidden" name="supplierId" id="comSupplierId" value="<s:property value="supplierId"/>"/></td>
        <td><em>期票：</em></td>
        <td><input type="checkbox" name="isAperiodic" value="true" <s:if test='isAperiodic=="true"'>checked</s:if> /></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td> 
      </tr>
      <tr>
      	<s:if test="subProductTypeList.size()>0">
        <td><em>产品类型：</em></td>
        <td><s:select name="subProductType" list="subProductTypeList" listKey="code" listValue="name" headerKey="" headerValue="请选择"/></td>
        </s:if>
        
        <s:if test="productType!='TRAFFIC'">
        	 <td><em>采购主体：</em></td>
		     <td>
		     <s:select name="filialeName" list="@com.lvmama.comm.vo.Constant$FILIALE_NAME@values()" listKey="code" listValue="cnName" headerKey="" headerValue="请选择" />
			 </td>	
        </s:if>
		
        <td><input type="submit" class="button" value="查询"/></td>
        <td>
        <mis:checkPerm permCode="1422" permParentCode="${permId}">
        	<input type="button" class="newProduct button" value="新增采购"/>
        </mis:checkPerm>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </form>
    </div>
    <div class="row2">
    	<table width="96%" border="0" cellspacing="0" cellpadding="0" class="newTable">
              <tr class="newTableTit">
                <td class="idw">编号</td>
                <td class="prodw">采购产品编号</td>
                <td>采购产品名称</td>
                <td>供应商名称</td>
                <td class="timew">录入时间</td>
                <td class="statew">状态</td>
                <td style="width:350px;">操作</td>
              </tr>
              <s:iterator value="metaProductList" id="mp">
              <tr>
                <td><s:property value="metaProductId"/> </td>
                <td><s:property value="bizCode"/></td>
                <td>
               <a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=<s:property value="metaProductId"/>&metaBranchId=&productType=<s:property value="productType"/>',700,700)">
                <s:property value="productName"/>
                </a>
                </td>
                <td><s:property value="supplierName"/></td>
                <td><s:date name="createTime" format="yyyy-MM-dd"/> </td>
                <td class="valid"><s:property value="strValid"/></td>
                <td>
               		<a href="<%=basePath%>meta/toEditProduct.do?metaProductId=<s:property value="metaProductId"/>">修改</a>
               		
               		<s:if test='#mp.valid=="N"'>
            		<mis:checkPerm permCode="1919" permParentCode="${permId}">
			                <a href="#valid" class="changeValid" result="<s:property value="metaProductId"/>">开启</a>
					</mis:checkPerm>
					</s:if>
					<s:if test='#mp.valid=="Y"'>
					<mis:checkPerm permCode="2006" permParentCode="${permId}">
			                <a href="#valid" class="changeValid" result="<s:property value="metaProductId"/>">关闭</a>
					</mis:checkPerm>
					</s:if>
	                <a href="#log" class="showLogDialog" param="{'parentType':'META_PRODUCT','parentId':<s:property value="metaProductId"/>}">查看操作日志</a>
					<s:if test='#mp.controlType!=null'>
               			[<a href="javascript:showControlDialog(<s:property value="metaProductId"/>)">新增预控</a>
               			<a href="javascript:deleteControls(<s:property value="metaProductId"/>)">删除预控</a>
               			<a href="<%=basePath%>meta/searchControlList.do?controlCondition.productId=<s:property value="metaProductId"/>">查看预控</a>]
               		</s:if>
                </td>
              </tr>
               </s:iterator>
      </table>
      <table width="96%">
      	<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
      </table>
    </div>
</div>
<div id="control_div" url="<%=basePath%>meta/editControl.do"></div>
<div id="control_batch_div" url="<%=basePath%>meta/editBatchControl.do"></div>
<div id="confirm_div" url="<%=basePath%>confirm/confirm.html"></div>
</body>
</html>

