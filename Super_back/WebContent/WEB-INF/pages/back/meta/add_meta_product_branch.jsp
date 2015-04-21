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
<title>super后台——采购产品信息</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<link href="<%=basePath%>/themes/cc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/meta/branch.js"></script>
<script type="text/javascript" src="<%=basePath%>js/timeprice/time.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>
<body>
<div class="main main05">
	<div class="row1">
    	<h3 class="newTit">采购产品信息
    	<s:if test="metaProductId != null">
    		<jsp:include page="/WEB-INF/pages/back/meta/goUpStep.jsp"></jsp:include>
    	</s:if>
    	</h3>
        <s:include value="/WEB-INF/pages/back/meta/nav.jsp"/>
   </div><!--row1 end-->    
   <div class="row2">
   		<table class="newTable" width="100%" border="0" cellspacing="0" cellpadding="0" id="branch_tb">
          <tr class="newTableTit">
            <td>类别名称</td>
            <td>人数</td>
            <td>类别描述</td>
            <td>操作</td>
          </tr>
          <s:iterator value="branchList">
          <tr id="tr_${metaBranchId}" result="${metaBranchId}">
            <td>${branchName}(<s:property value="metaBranchId"/>)</td>
            <td><span>成人：${adultQuantity}</span><span>儿童：${childQuantity}</span></td>
            <td>${description}</td>
            <td><mis:checkPerm permCode="1423" permParentCode="${permId}"><a href="#edit" class="edit">修改</a></mis:checkPerm>            	
            <%-- <a href="#viewPrice" class="viewPrice">查看价格</a>--%>
            <a href="#timeprice" tt="META_PRODUCT" class="showTimePrice" param="{metaBranchId:<s:property value="metaBranchId"/>,editable:true}">修改价格</a>
            <a href="#viewPackedProductBranch" class="viewPackedProductBranch" param="{metaBranchId:<s:property value="metaBranchId"/>}">查看关联的销售类别</a>
            <a href="#log" class="showLogDialog" param="{'objectType':'META_PRODUCT_BRANCH','objectId':${metaBranchId}}">查看操作日志</a>
            </td>
          </tr>
          </s:iterator>
        </table>
        <mis:checkPerm permCode="1423" permParentCode="${permId}"><h3 class="titBotton newBranch"><span class="button">新建</span></h3></mis:checkPerm>
   </div><!--row2 end-->
   <div class="row3">
   		<form name="branchForm" action="<%=basePath%>meta/saveBranch.do" onsubmit="return false" style="display: none">
   		
        </form>
   </div><!--row3 end-->
</div>
<!-- 该位置的数据为表单数据，放在这里的原因是为了保证数据的默认值正确 -->
<div style="display: none" id="form_content">
<form>
   		<input type="hidden" name="branch.metaBranchId"/><input type="hidden" name="branch.metaProductId"/>
   		<input type="hidden" id="isAperiodic" value="${metaProduct.isAperiodic }" />
   		<s:hidden name="metaProductType" id="metaProductType" />
   		<ul class="new">
        	<li>
         	   <span><span class="add_title">类别类型：</span><span class="require">[*]</span>
               <s:select list="branchCodeSetList" listKey="code" listValue="name" name="branch.branchType" cssClass="changeType"/>
               <s:if test="metaProduct.productType=='TRAFFIC'">
               		<s:if test="metaProduct.subProductType=='FLIGHT'">
	               		&nbsp;&nbsp;&nbsp;&nbsp;
	               		舱位类型：<span class="require">[*]</span>
	              		<s:select list="branch_1CodeSetList" listKey="code" listValue="name" name="branch.berth" cssClass="changeType"/>
	               </s:if>
	               <s:if test="metaProduct.subProductType=='TRAIN'">
              		<span class="add_title"><font style="font-weight:bold;">站站类型：</font></span><span class="require">[*]</span>
              		<s:select list="branch_1CodeSetList" listKey="code" listValue="name" name="branch.stationStationId" cssClass="changeType"/>
              		</s:if>
               </s:if>
               </span>
            </li>
        	<li><span class="add_title">类别名称：</span><span class="require">[*]</span><input type="text" class="text1" name="branch.branchName"/></li>
        	<li><span class="add_title">是否附加：</span><input type="radio" name="branch.additional" value="false" checked="checked"/>否&nbsp;<input type="radio" name="branch.additional" value="true"/>是</li>
        	<li><span class="add_title">是否需要单独创建传真：</span><input type="radio" name="branch.sendFax" value="true" checked="checked"/>是&nbsp;<input type="radio" name="branch.sendFax" value="false"/>否</li>				
        	<li><span class="add_title">是否虚拟采购产品：</span><input type="radio" name="branch.virtual" value="true" />是&nbsp;<input type="radio" name="branch.virtual" value="false" checked="checked"/>否</li>				
        	<li class="newNumber"><span class="add_title">成人数：</span><span class="require">[*]</span><input type="text" class="text1" name="branch.adultQuantity" value="0"/><span>儿童数：</span><input type="text" class="text1" name="branch.childQuantity" value="0"/></li>
        	<li><span class="add_title">类别描述：</span><textarea name="branch.description" cols="50" rows="5"></textarea></li>
        	<li><span class="add_title">是否使用总库存：</span>
        	<s:if test="metaProduct.IsAperiodic()">
        		<input name="branch.totalDecrease" class="radio01" type="radio"  value="true" checked="checked"/>
	        	<span>是</span>
	        	<input name="branch.totalDecrease" class="radio01" type="radio" value="false" disabled="disabled" />
	        	<span>否</span>
        	</s:if>
        	<s:else>
	        	<input name="branch.totalDecrease" class="radio01" type="radio"  value="true" <s:if test="decreaseDisabled">disabled="disabled"</s:if>/>
	        	<span>是</span>
	        	<input name="branch.totalDecrease" class="radio01" type="radio" checked="checked" value="false"/>
	        	<span>否</span>
        	</s:else>
        	&nbsp;(若此处选择使用总库存，那么当销售产品库存减至0后，时间价格表中将无法修改库存)
        	</li>
        	<li><span class="add_title">总库存：</span><!--<span class="require">[*]</span>--><input type="text" class="text1" name="branch.totalStock"/>
        	<s:if test="metaProduct.IsAperiodic()">
        		<font color="red">不定期产品默认使用总库存，请在此设置总库存数，库存不能小于0</font>
        	</s:if>
        	</li>
        	<li><span class="add_title">代理产品编号：</span><input type="text" class="text1" name="branch.productIdSupplier"/></li>
            <li><span class="add_title">代理产品类型：</span><input type="text" class="text1" name="branch.productTypeSupplier"/></li>
            <li><em class="button saveForm add_but" ff="branchForm">保存</em><em class="button cancelForm add_but" ff="branchForm">取消</em></li>
        </ul><p></p>
        </form>
</div>
</body>
</html>




