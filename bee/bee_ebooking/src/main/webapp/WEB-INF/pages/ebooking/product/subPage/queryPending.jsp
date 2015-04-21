<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<li class="order_all_li" style="display:block;">
		<div class="order_list">
		<form action="/ebooking/product/query/queryPending.do" id="queryProductForm" method="POST">
    	<jsp:include page="./queryCommonParams.jsp"></jsp:include>
    	<dl>
        	<dt>查找产品：</dt>
            <dd>
            	<ul class="search_ul_t">
                	
                	<li>
                    	<label>供应商产品名称：<s:textfield name="metaName"/></label>
                    </li>
                    <li class="search_ul_b_3">
                    	<label>提交时间：<s:textfield name="commitTimeStart" readonly="readonly" id="Calendar21"/>~<s:textfield name="commitTimeEnd" readonly="readonly" id="Calendar22"/></label>
                    </li>
                    
                    <li>
                    	<label>销售ID：<s:textfield name="prodProductId"/></label>
                    </li>
                    <li>   </li>
                    <li>
                    	<label>驴妈妈产品名称：<s:textfield name="prodName"/></label>
                    </li>
                    <li class="search_ul_b_but"><span  id="searchAllProductBtn">查找</span></li><li><span id="errormessageid"></span></li>
                </ul>
            </dd>
        </dl>
		</form>
    	</div>
		
        <div class="tableWrap">
            <table width="960" border="0" class="table01">
                <tbody>
                    <tr class="even">
                        <th width="20%">供应商产品名称</th>
                        <th width="8%">销售ID</th>
                        <th width="20%">驴妈妈产品名称</th>
                        <th width="10%">产品类型</th>
                        <th width="10%">产品经理</th>
                        <th width="12%">提交时间</th>
                        <th>操作</th>
                    </tr>
                    <s:iterator value="pagination.items" var="item">
                    <tr>
                        <td title="<s:property value="metaName"/>"><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#item.metaName,15)"/></td>
                        <td><s:if test="null!=prodProductId"><s:property value="prodProductId"/></s:if><s:else>未知</s:else></td>
                        <td  title="<s:property value="prodName"/>"><s:if test="null!=prodProductId"><a href="http://www.lvmama.com/product/<s:property value="prodProductId"/>" target="_bank"></s:if><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#item.prodName,15)"/><s:if test="null!=prodProductId"></a></s:if></td>
                        <td><s:property value="subProductTypeCh"/><s:if test="'SELFHELP_BUS'==subProductType"><span class="text_ts tip-icon tip-icon-info" tip-content="自助巴士班，即开心驴行。若供应商觉得产品足够好，则向产品经理申请做开心驴行。"></span></s:if></td>
                        <td><s:property value="managerName"/></td>
                        <td><s:if test="null!=sumitDate"><s:date name="sumitDate" format="yyyy/MM/dd HH:mm:ss"/></s:if><s:else>--</s:else></td>
                        <td ebkprodproductid="<s:property value="ebkProdProductId"/>" ebkprodproductname="<s:property value="metaName"/>"><a href="javascript:void(0)" class="auditrevokeebkproduct">撤销审核</a>  <a href="${basePath }ebooking/product/editEbkProductInit.do?ebkProdProductId=<s:property value="ebkProdProductId"/>&toShowEbkProduct=SHOW_EBK_PRODUCT" target="_blank">查看</a></td>
                    </tr>
                    </s:iterator>
                </tbody>
            </table>
            <jsp:include page="./queryPageFooter.jsp"></jsp:include>
    	</div>
	</li>