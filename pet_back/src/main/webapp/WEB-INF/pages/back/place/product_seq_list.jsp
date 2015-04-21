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
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<link rel="stylesheet" href="${basePath}/css/place/backstage_table.css"/>
<script>
function checkForm(){
	var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
	if($("#productAlltoPlaceIds").val()!=""){
		if(!reg.test($("#productAlltoPlaceIds").val())){
			alert("目的地ID必须输入数字!");
			$("#productAlltoPlaceIds").val();
			$("#productAlltoPlaceIds").focus();
			return false;
		}
	}
	
	$("#productOrderForm").submit();
	return true;
}

</script>
</head>
<body>
<ul class="gl_top" >
			<form action="${basePath}/place/productSeqList.do" method="post" id="productOrderForm">
			
				出发地：<input type="text" value="${productSearchInfo.fromDest}" name="productSearchInfo.fromDest">&nbsp;&nbsp;&nbsp;
				目的地/拼音：<input type="text"  value="${productSearchInfo.productAlltoPlaceContent}" name="productSearchInfo.productAlltoPlaceContent">
				目的地ID：<input type="text" id="productAlltoPlaceIds" value="${productSearchInfo.productAlltoPlaceIds}" name="productSearchInfo.productAlltoPlaceIds">
				产品类型：<s:select list="productTypeList" name="productSearchInfo.productType" listKey="elementCode" theme="simple" listValue="elementValue" headerKey="" headerValue="全部"></s:select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				线路类型：<s:select list="subProductTypeList" name="productSearchInfo.subProductType" listKey="elementCode" theme="simple" listValue="elementValue" headerKey="" headerValue="全部"></s:select>
				<input type="submit" value="查  询" onclick="return checkForm();" class="right-button07" id="Submit1">
			
			</form>
</ul>
<table class="gl_table js_sz_table" cellspacing="0" cellpadding="0">
  <tr>
    <th style="text-align: center;"><input type="hidden" value="${basePath}/place/productSeqList.do" id="jumpUrl"/>
    <input type="checkbox" name="chk_all" id="chk_all"/>&nbsp;&nbsp;全选<input type="button" name="savePorductSeq" id="saveProductSeq" value="保存排序"/></th>
    <th>产品ID </th>
    <th>产品名称 </th>
    <th>出发地 </th>
    <th>产品类型 </th>
    <th>线路类型 </th>
    <th>排序值 </th>   
  </tr>
  <s:iterator value="viewProductSearchInfoList">	
  <tr >
    <td><input type="checkbox" name="chk_list"  value="${productId}"/></td>
     <td><s:property value="productId"/></td>
    <td><s:property value="productName"/></td>
    <td><s:property value="fromDest"/></td>
    <td>
								<s:if test='productType=="TICKET"'>门票</s:if>
								<s:if test='productType=="HOTEL"'>酒店</s:if>
								<s:if test='productType=="ROUTE"'>线路</s:if>
							</td>
	<td>
								<s:if test='subProductType=="GROUP"'>短途跟团游</s:if>
								<s:if test='subProductType=="GROUP_LONG"'>长途跟团游</s:if>
								<s:if test='subProductType=="GROUP_FOREIGN"'>出境跟团游</s:if>
								<s:if test='subProductType=="FREENESS"'>目的地自由行</s:if>
								<s:if test='subProductType=="FREENESS_LONG"'>长途自由行</s:if>
								<s:if test='subProductType=="FREENESS_FOREIGN"'>出境自由行</s:if>
								<s:if test='subProductType=="SELFHELP_BUS"'>自助巴士班</s:if>
							</td>
    <td><input type="text"  class="seq_check" id="chk_list_${productId}" value="${seq}"/></td>    
  </tr> 
</s:iterator>
  <tr><td colspan="8" style="text-align: right;padding-right: 20px;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td></tr>
</table>













</body>
</html>