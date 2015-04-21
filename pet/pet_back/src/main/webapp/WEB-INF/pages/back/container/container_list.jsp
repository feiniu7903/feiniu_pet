<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/container/container.js"></script>
<script type="text/javascript" src="${basePath}/js/place/houtai.js"></script>
<link rel="stylesheet" href="${basePath}/css/place/backstage_table.css"/>
<script>
function checkForm(){
	var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
	if($("#productId").val()!=""){
		if(!reg.test($("#productId").val())){
			alert("产品ID请输入数字!");
			$("#productId").val();
			$("#productId").focus();
			return false;
		}
	}
	
	$("#form1").submit();
	return true;
}

</script>
</head>
<body>

<form action="${basePath}/container/container.do" method="post" name="form1">
<ul class="gl_top container" >
	<li><span>频道模块：</span><s:select list="prodContainerList" onchange="setFromPlace(this.options[this.options.selectedIndex].value)" name="containerCode" id="containerCode" headerKey="" headerValue="--请选择--" theme="simple" listKey="containerCode" listValue="containerName">
	</s:select>&nbsp;&nbsp;</li><li>
	<span>出发地:</span><select name="fromPlace" id="fromPlace" onchange="setToPlace(this.options[this.options.selectedIndex].value)">
	<option value="">--请选择--</option>
	</select>&nbsp;&nbsp;</li><li>
	<span>目的地:</span><select name="toPlace" id="toPlace" onchange="setToParentPlace(this.options[this.options.selectedIndex].value)">
		<option value="">--请选择--</option>
	</select>&nbsp;&nbsp;
	<select name="toParentPlace" id="toParentPlace">
		<option value="">--请选择--</option>
	</select>&nbsp;&nbsp;
	</li>
	<li>
	<input id="toPlaceHid" type="hidden" value="<s:property value='toPlace'/>">
	<input id="fromPlaceHid" type="hidden" value="<s:property value='fromPlace'/>">
	<input id="toParentPlaceHid" type="hidden" value="<s:property value='toParentPlace'/>">
	<span>产品类型:</span><select name="subProductType" id="subProductType">
		<option value="">--请选择--</option>
	</select>&nbsp;&nbsp;</li><li>
	<input id="subProductTypeHid" type="hidden" value="<s:property value='subProductType'/>">
	<span>产品ID:</span><s:textfield name="productId" theme="simple" id="productId"></s:textfield>&nbsp;&nbsp;</li><li>
	<span>产品名称:</span><s:textfield name="productName" theme="simple"></s:textfield>&nbsp;&nbsp;</li><li>
	<span>排序：</span><select name="sortType" id="sortType">
		<option value="">--请选择--</option>
		<option value="1" <s:if test='sortType=="1"'>selected</s:if>>按SEQ值从高到底</option>
		<option value="2" <s:if test='sortType=="2"'>selected</s:if>>按SEQ值从低到高</option>
		<option value="3" <s:if test='sortType=="3"'>selected</s:if>>按推荐排序值从高到低</option>
		<option value="4" <s:if test='sortType=="4"'>selected</s:if>>按推荐排序值从低到高</option>
	</select>&nbsp;&nbsp;</li><li>
	<input name="btnQuery" value="查询" onclick="return checkForm();" type="submit"></li>
	
</ul>
</form>

<table class="gl_table js_sz_table" cellspacing="0" cellpadding="0" style="clear: left;">
  <tr>
    <th>频道模块</th>
    <th>推荐排序值</th>
    <th>默认排序值</th>
    <th>产品ID</th>
    <th>产品名称</th>
    <th>产品起价</th>
    <th>出发地</th>
    <th>目的地</th>
    <th>操作</th>
  </tr>
  <s:iterator value="prodContainerProductList">
  <tr>
   <td><s:property value="containerName"/></td>
   <td><s:property value="recommendSeq"/></td>
   <td><s:property value="defaultSeq"/></td>
   <td><s:property value="productId"/></td>
   <td><s:property value="productName"/></td>
   <td><s:property value="sellPriceYuan"/></td>
   <td><s:property value="fromPlaceName"/></td>
   <td><s:property value="toPlaceName"/></td>
   <td><a href="javascript:void(0)" onclick="openRecommendSeq('<s:property value="id"/>','<s:property value="recommendSeq"/>')">推荐排序值</a>
   <a href="javascript:void(0)" onclick="showOrHide('<s:property value="id"/>','<s:property value="isValid"/>')">设置<s:if test='isValid=="Y"'>隐藏</s:if><s:else>显示</s:else></a>
   &nbsp;&nbsp;
   <a href="javascript:void(0)" onclick="showContainerOprationLog(<s:property value="id"/>)">查看操作日志</a>
   </td>
  </tr>
 </s:iterator>
  <tr><td colspan="9" style="text-align: right;padding-right: 20px;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td></tr>
</table>
<div  class="js_zs js_cl_all" id="recommendSeq">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('recommendSeq')">X</a>推荐排序值修改</h3>
	<div class="tab_ztxx_all">
		<input type="hidden" id="containerProductId"/>
		<input type="hidden" id="oldRecommendSeq"/>
		推荐排序值：<input type="text" id="recommendSeqValue" value=""/>&nbsp;&nbsp;<input type="button" id="btn_recommendSeqValue_ok" value="确 定"/>
	</div>
</div>
<div  class="js_zs js_cl_all" id="showContainerOprationLog">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('showContainerOprationLog')">X</a>操作日志</h3>
	<div class="tab_ztxx_all">
		<div id="oprationContent"></div>
	</div>
</div>
</body>
</html>