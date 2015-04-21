<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<li class="order_all_li" style="display:block;">
		<div class="order_list">
		<form action="/ebooking/product/queryProduct.do" id="queryProductForm" method="POST">
		<jsp:include page="./queryCommonParams.jsp"></jsp:include>
    	<dl>
        	<dt>查找产品：</dt>
            <dd>
            	<ul class="search_ul_t">
                	<li>
                    	<label>供应商产品名称：<s:textfield name="metaName"/></label>
                    </li>
                    <li>
                    	<label>产品类型：
                    		<s:select list="subProductTypes" id="subProductTypesId"	name="subProductType" value="subProductType" listKey="code" listValue="cnName" headerKey="" headerValue="全部"></s:select>
                        </label>
                    </li>
                    <li>
                    	<label>上下线状态：
                    	<s:select name="onLine" value="onLineId" id="onLineId" list="#{'':'全部','true':'上线','false':'下线'}"/>
                        </label>
                    </li>
                    <li>
                    	<label>销售ID：<s:textfield name="prodProductId"/></label>
                    </li>
                    
                    <li><label>驴妈妈产品名称：<s:textfield name="prodName"/></label>
                    	
                    </li>
                    <li>
                    	<label>审核状态：
                    	<s:select list="ebkProductAuditStatus" id="ebkProductAuditStatusId"	name="status" value="status" listKey="code" listValue="cnName" headerKey="" headerValue="全部"></s:select>
                        </label>
                    </li>
                    
                    <li class="search_ul_b_but"><span  id="searchAllProductBtn">查找</span></li><li><span id="errormessageid"></span></li>
                </ul>
            </dd>
        </dl>
		</form>
    	</div>
        <div class="xinzeng">
        <a class="btn_xz" href="${basePath}ebooking/product/editEbkProductInit.do?ebkProductViewType=<s:property value="ebkProductViewType"/>" target="_blank">新增产品</a>
        <a class="btn_xz" href="javascript:;" id="import-prod-from-super-btn" data-type="<s:property value="ebkProductViewType"/>">一键导入产品</a>
        </div>
		
        <div class="tableWrap">
            <table width="960" border="0" class="table01">
                <tbody>
                    <tr class="even">
                        <th width="20%">供应商产品名称</th>
                        <th width="5%">销售ID</th>
                        <th width="20%">驴妈妈产品名称</th>
                        <th width="10%">产品类型</th>
                        <th width="7%">产品经理</th>
                        <th width="7%">申请状态</th>
                        <th width="7%">下线时间</th>
                        <th width="7%">上下线状态</th>
                        <th>操作</th>
                    </tr>
                    <s:iterator value="pagination.items" var="item">
                    <tr>
                        <td title="<s:property value="metaName"/>"><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#item.metaName,15)"/></td>
                        <td><s:if test="null!=prodProductId"><s:property value="prodProductId"/></s:if><s:else>未知</s:else></td>
                        <td  title="<s:property value="prodName"/>"><s:if test="null!=prodProductId"><a href="http://www.lvmama.com/product/<s:property value="prodProductId"/>" target="_bank"></s:if><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#item.prodName,15)"/><s:if test="null!=prodProductId"></a></s:if></td>
                        <td><s:property value="subProductTypeCh"/><s:if test="'SELFHELP_BUS'==subProductType"><span class="text_ts tip-icon tip-icon-info" tip-content="自助巴士班，即开心驴行。若供应商觉得产品足够好，则向产品经理申请做开心驴行。"></span></s:if></td>
                        <td><s:property value="managerName"/></td>
                        <td><s:property value="statusCh"/></td>
                        <td><s:if test="null!=offlineTime"><s:date name="offlineTime" format="yyyy/MM/dd"/></s:if><s:else>--</s:else></td>
                        <td><s:if test="null!=onLineCh"><s:property value="onLineCh"/></s:if><s:else>--</s:else></td>
                        <td parenttype="EBK_PROD_PRODUCT" parentid="<s:property value="ebkProdProductId"/>"  objecttype="EBK_PROD_PRODUCT" objectid="<s:property value="ebkProdProductId"/>" ebkprodproductid="<s:property value="ebkProdProductId"/>" ebkprodproductname="<s:property value="metaName"/>"><s:if test="'REJECTED_AUDIT'==status or 'THROUGH_AUDIT'==status"><a href="javascript:void(0);" class="auditrecover">重新编辑</a>　</s:if><s:if test="'UNCOMMIT_AUDIT'==status"><a href="${basePath }ebooking/product/editEbkProductInit.do?ebkProdProductId=<s:property value="ebkProdProductId"/>">编辑</a>　<a href="javascript:void(0)" class="tijiao auditcommitebkproduct">提交审核</a>　</s:if><s:if test="'PENDING_AUDIT'==status"><a href="javascript:void(0)" class="auditrevokeebkproduct">撤销审核</a>　</s:if><a  href="${basePath }ebooking/product/editEbkProductInit.do?ebkProdProductId=<s:property value="ebkProdProductId"/>&toShowEbkProduct=SHOW_EBK_PRODUCT" target="_blank">查看</a>　<a class="queryebkcomlog" href="javascript:void(0)">日志</a></td>
                    </tr>
                    </s:iterator>
                </tbody>
            </table>
            <jsp:include page="./queryPageFooter.jsp"></jsp:include>
    	</div>
	</li>