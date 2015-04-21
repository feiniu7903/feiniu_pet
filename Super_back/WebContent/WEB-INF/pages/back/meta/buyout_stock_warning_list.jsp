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
<title>super后台——产品预控列表</title>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/meta/product_list.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>

<link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css"/>
<link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css"/>
<script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>
<script type="text/javascript">
function doTextKeyUp(text) {
	var v = text.value.replace(/[^\d]/g, '');
	text.value = v;
}
function exportViewList() {
	var action = '<%=basePath%>meta/exportViewList.do';
	var form = $('#queryForm');
	form.attr('action', action);
	form.submit();
}
function queryViewList() {
	var action = '<%=basePath%>meta/searchBtsWarningList.do';
	var form = $('#queryForm');
	form.attr('action', action);
	form.submit();
}
</script>
<style type="text/css">
	.container_span_progress {
		border:solid #999 1px;
		background-color: #fff;
		height: 7px;
		overflow: hidden;
		display: inline-block;
		width:40px;
	}
	.content_span_progress {
		background-color: #19ED12;
		height: 7px;
		display: inline-block;
		margin: 0 !important;
	}
	.date_searcher_fill {
		width: 132px;
		height: 20px;
		border: 1px solid #A1C5E6;
		cursor:pointer;
	}
</style>
</head>
  
<body>
<div class="main main01">
	<div class="row1">
    	<h3 class="newTit">产品预控列表</h3>
    	<form id="queryForm" action="<%=basePath%>meta/searchBtsWarningList.do" method="post">
       <table width="980" border="0" cellspacing="0" cellpadding="0" class="newInput">
      <tr>
        	<td><em id="testTip">申请人：</em></td>
        	<td><input type="text" class="newtext1" value="${controlCondition.applierName }" name="controlCondition.applierName" /></td>
        	<td><em>采购产品ID：</em></td>
        	<td><input type="text" 
        		 	class="newtext1"
        		 	name="controlCondition.productId"
        		 	value="${controlCondition.productId }"
        		 	onkeyup="doTextKeyUp(this)"
	    			onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
        		 	 />
  		 	 </td>
        	
        	<td><em>采购产品名：</em></td>
	      	<td><input type="text" value="${controlCondition.productName }" class="newtext1" name="controlCondition.productName" /></td>
	      	 
        	<td><em>类别名：</em></td>
	      	<td><input type="text" value="${controlCondition.branchName }" class="newtext1" name="controlCondition.branchName" /></td>
	      	 
      </tr>
      <tr>
       	<td><em>销售起始日期：</em></td>
       	<td><input type="text" 
       			name="controlCondition.saleStartDate"
       			class="input_text01 table_input_other Wdate date_searcher_fill"
       			value="<s:date name="controlCondition.saleStartDate" format="yyyy-MM-dd"/>"
       			readonly="readonly"
       			onClick="WdatePicker()" />
		</td>
       	<td><em>销售截止日期：</em></td>
       	<td><input type="text" 
       			name="controlCondition.saleEndDate" 
       			class="input_text01 table_input_other Wdate date_searcher_fill" 
       			value="<s:date name="controlCondition.saleEndDate" format="yyyy-MM-dd"/>"
       			readonly="readonly"
       			onClick="WdatePicker()" />
		</td>
       	<td><em>使用有效起始日：</em></td>
       	<td><input type="text" 
  				name="controlCondition.startDate" 
   				class="input_text01 table_input_other Wdate date_searcher_fill" 
   				value="<s:date name="controlCondition.startDate" format="yyyy-MM-dd"/>"
   				readonly="readonly"
   				onClick="WdatePicker()" />
		</td>
       	<td><em>使用有效结束日：</em></td>
       	<td><input type="text" 
       			name="controlCondition.endDate" 
       			class="input_text01 table_input_other Wdate date_searcher_fill" 
       			value="<s:date name="controlCondition.endDate" format="yyyy-MM-dd"/>"
       			readonly="readonly"
       			onClick="WdatePicker()" />
		</td>
      </tr>
      <tr>
	    <td><em>供应商编号：</em></td>
	    <td><input type="text" 
	    		name="controlCondition.supplierId"
	    		class="newtext1"
	    		onkeyup="doTextKeyUp(this)"
	    		value="${controlCondition.supplierId }"
	    		onbeforepaste= "clipboardData.setData('text', clipboardData.getData('text').replace(/[^\d]/g, ''));"
	    		 /></td>
	    <td><em>供应商名：</em></td>
	    <td><input type="text" value="${controlCondition.supplierName }" name="controlCondition.supplierName" class="newtext1" /></td>
	    <td><em>所属中心：</em></td>
        	<td><s:select 
        			style="width: 134px;height: 22px;border: 1px solid #A1C5E6;"
        			list="groupNameList" listKey="workGroupId"
        			listValue="groupName"
        			headerKey=""
        			headerValue="请选择"
        			name="controlCondition.workGroupId"
        			 />
       	</td>
       	<td><em>预控级别：</em></td>
        <td><s:select name="controlCondition.controlType"
			 value="controlCondition.controlType"
			 list="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_TYPE@values()"
			 listKey="code" listValue="cnName" headerKey="" headerValue="请选择" />
		</td>
       </tr>
       <tr>
        <td><input type="button" class="button" value="查询" onclick="queryViewList()"/></td>
        <td><input type="button" class="button" value="导出" onclick="exportViewList()"/></td>
       </tr>
    </table>
    </form>
    </div>
    <div class="row2">
    	<table width="96%" border="0" cellspacing="0" cellpadding="0" class="newTable">
              <tr class="newTableTit">
                <td style="width:50px;">所属中心</td>
                <td style="width:40px;">预控级别</td>
                <td style="width:50px;">申请人</td>
                <td style="width:35px;">采购产品ID</td>
                <td style="width:50px;">采购产品名</td>
                <td style="width:35px;">类别ID</td>
                <td style="width:10px;">类别名</td>
                <td style="width:35px;">供应商编号</td>
                <td style="width:100px;">供应商名称</td>
                <td style="width:10px;">可延期</td>
                <td style="width:10px;">可退</td>
                <td style="width:65px;">销售起始日期</td>
                <td style="width:65px;">销售截止日期</td>
                <td style="width:65px;">使用有效起始日</td>
                <td style="width:65px;">使用有效结束日</td>
                <!-- 支付金额暂时不上 
                <td style="width:40px;">支付金额</td>
                -->
                <td style="width:40px;">预控数量</td>
                <td style="width:40px;">应销量</td>
                <td style="width:40px;">买断产品实际销量</td>
                <td style="width:40px;">总销量</td>
                <td style="width:40px;">资源剩余量</td>
                <td style="width:40px;">资源出库率</td>
                <td style="width:25px;">预警值</td>
              </tr>
              <s:iterator value="viewControlList" id="mp">
              <tr>
                <td align="center"><s:property value="belongCenter"/></td>
                <td align="center"><s:property value="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_TYPE@getCnName(controlType)"/></td>
                <td align="center"><s:property value="applierName"/></td>
                <td align="center"><s:property value="productId"/></td>
                <td align="center"><s:property value="productName"/></td>
                <td align="center"><s:property value="productBranchId"/></td>
                <td align="center"><s:property value="branchName"/></td>
                <td align="center"><s:property value="supplierId"/></td>
                <td align="center"><s:property value="supplierName"/></td>
                <td><s:if test="delayAble == 'true'">是</s:if>
                	<s:else>否</s:else>
               	</td>
                <td><s:if test="backAble == 'true'">是</s:if>
                	<s:else>否</s:else>
                </td>
                <td align="center"><s:date name="saleStartDate" format="yyyy-MM-dd"/></td>
                <td align="center"><s:date name="saleEndDate" format="yyyy-MM-dd"/></td>
                <td align="center"><s:date name="startDate" format="yyyy-MM-dd"/> </td>
                <td align="center"><s:date name="endDate" format="yyyy-MM-dd"/> </td>
                <!-- 支付金额暂时不上
                <td align="center"><s:property value="paymentAmount"/> </td>
                 -->
                <td align="center"><s:property value="controlQuantity"/></td>
                <td align="center"><s:property value="hoopQuantity"/></td>
                <td align="center"><s:property value="saleQuantity"/></td>
                <td align="center"><s:property value="totalQuantity"/></td>
                <td align="center"><s:property value="leaveQuantity"/></td>
                <td>
                	&nbsp;<s:property value="outStockScale"/>%
                	<span class="container_span_progress">
                		<s:if test="outStockScale >= 100">
                			<span class="content_span_progress" style="width: <s:property value="outStockScale"/>%;background-color: red;"></span>
                		</s:if>
                		<s:else>
		                	<span class="content_span_progress" style="width: <s:property value="outStockScale"/>%;"></span>
                		</s:else>
	                </span>
                </td>
                <td>
                	<s:property value="warningValue"/>
                	<span class="container_span_progress" style="width:12px;height:10px;background-color: <s:property value="warningColor"/>;">
	                </span>
                </td>
              </tr>
               </s:iterator>
      </table>
      <table width="96%">
      	<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
      </table>
    </div>
</div>
</body>
</html>

