<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<ul class="xzxx_tab">
	<%-- <s:iterator value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@values()" var="property"> --%>
		
		<s:if test="code!='EBK_AUDIT_TAB_TIME_PRICE' and code!='EBK_AUDIT_TAB_RELATION'">
			
			<s:if test="ebkProdProduct.productType=='SURROUNDING_GROUP'">
				<li id="EBK_AUDIT_TAB_BASE"  <s:if test="'EBK_AUDIT_TAB_BASE'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_BASE')"/></li>
				<li id="EBK_AUDIT_TAB_RECOMMEND"  <s:if test="'EBK_AUDIT_TAB_RECOMMEND'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_RECOMMEND')"/></li>
				<li id="EBK_AUDIT_TAB_TRIP"  <s:if test="'EBK_AUDIT_TAB_TRIP'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_TRIP')"/></li>
				<li id="EBK_AUDIT_TAB_COST"  <s:if test="'EBK_AUDIT_TAB_COST'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_COST')"/></li>
				<li id="EBK_AUDIT_TAB_PICTURE"  <s:if test="'EBK_AUDIT_TAB_PICTURE'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_PICTURE')"/></li>
				<li id="EBK_AUDIT_TAB_TRAFFIC" <s:if test="'EBK_AUDIT_TAB_TRAFFIC'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_TRAFFIC')"/></li>
				<li id="EBK_AUDIT_TAB_OTHER"  <s:if test="'EBK_AUDIT_TAB_OTHER'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_OTHER')"/></li>
			</s:if>
			
			<s:elseif test="ebkProdProduct.productType=='DOMESTIC_LONG'"><!-- 长线 -->
				<li id="EBK_AUDIT_TAB_BASE"  <s:if test="'EBK_AUDIT_TAB_BASE'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_BASE')"/></li>
				<li id="EBK_AUDIT_TAB_RECOMMEND"  <s:if test="'EBK_AUDIT_TAB_RECOMMEND'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_RECOMMEND')"/></li>
				<li id="EBK_AUDIT_TAB_TRIP"  <s:if test="'EBK_AUDIT_TAB_TRIP'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_TRIP')"/></li>
				<li id="EBK_AUDIT_TAB_COST"  <s:if test="'EBK_AUDIT_TAB_COST'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_COST')"/></li>
				<li id="EBK_AUDIT_TAB_PICTURE"  <s:if test="'EBK_AUDIT_TAB_PICTURE'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_PICTURE')"/></li>
				<li id="EBK_AUDIT_TAB_OTHER"  <s:if test="'EBK_AUDIT_TAB_OTHER'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_OTHER')"/></li>
			</s:elseif>
			
			<s:elseif test="ebkProdProduct.productType=='ABROAD_PROXY'"><!-- 境外游 -->
			
				<li id="EBK_AUDIT_TAB_BASE"  <s:if test="'EBK_AUDIT_TAB_BASE'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_BASE')"/></li>
				<li id="EBK_AUDIT_TAB_RECOMMEND"  <s:if test="'EBK_AUDIT_TAB_RECOMMEND'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_RECOMMEND')"/></li>
				<s:if test='ebkProdProduct.isMultiJourney=="Y"'>
					<li id="EBK_AUDIT_TAB_MULTITRIP"  <s:if test="'EBK_AUDIT_TAB_MULTITRIP'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_MULTITRIP')"/></li>
				</s:if>
				<s:else>
					<li id="EBK_AUDIT_TAB_TRIP"  <s:if test="'EBK_AUDIT_TAB_TRIP'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_TRIP')"/></li>
					<li id="EBK_AUDIT_TAB_COST"  <s:if test="'EBK_AUDIT_TAB_COST'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_COST')"/></li>
				</s:else>
				<li id="EBK_AUDIT_TAB_PICTURE"  <s:if test="'EBK_AUDIT_TAB_PICTURE'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_PICTURE')"/></li>
				<li id="EBK_AUDIT_TAB_OTHER"  <s:if test="'EBK_AUDIT_TAB_OTHER'==ebkProductEditModel"> class="tab_this" </s:if> ><s:property value="@com.lvmama.comm.vo.Constant$EBK_AUDIT_TABS_NAME@getCnNameByCode('EBK_AUDIT_TAB_OTHER')"/></li>
				<%-- <s:if test="code=='EBK_AUDIT_TAB_MULTITRIP'">
					<s:if test='ebkProdProduct.isMultiJourney=="Y"'><!-- 多行程 -->
						<li id="<s:property value="code"/>" <s:if test="code==ebkProductEditModel"> class="tab_this" </s:if>><s:property value="cnName"/></li>
					</s:if>
				</s:if>
				<s:else>
					<s:if test='ebkProdProduct.isMultiJourney=="Y"&&(code=="EBK_AUDIT_TAB_TRIP"||code=="EBK_AUDIT_TAB_COST")'><!-- 单行程 --></s:if>
				<s:else>
						<li id="<s:property value="code"/>" <s:if test="code==ebkProductEditModel"> class="tab_this" </s:if>><s:property value="cnName"/></li>
				</s:else>
				</s:else> --%>
			</s:elseif>
			
			
			<s:else>
				<s:if test="code!='EBK_AUDIT_TAB_TRAFFIC'">
					<li id="<s:property value="code"/>" <s:if test="code==ebkProductEditModel"> class="tab_this" </s:if>><s:property value="cnName"/></li>
				</s:if>
			</s:else>
			
		</s:if>
	<%-- </s:iterator> --%>
</ul>