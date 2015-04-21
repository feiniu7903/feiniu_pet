<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:if test="null!=modelTypeList && modelTypeList.size()>0 && modelTypeList.get(0).isMultiChoice==2">
		<p><font color="red">该属性只能选择两个</font></p>
	</s:if>
	<div class="row2" id="show_property_div">
	    <table class="rowpro" style="border: none">
	        <tr>
	            <td>
	            <s:if test="modelPropertyTypeList != null && modelPropertyTypeList.size > 0">
					<s:iterator value="modelPropertyTypeList" var="modelPropertyType">
							<input name="modelPropertyTypeList" firstModelId="<s:property value="firstModelId"/>" secondModelId="<s:property value="secondModelId"/>" type='<s:if test='isMultiChoice=="Y"'>checkbox</s:if><s:else>radio</s:else>' id="<s:property value="id"/>" value="<s:property value="id"/>" isMultiChoice="<s:property value="isMultiChoice"/>"  title="${modelPropertyType.property}" <s:property value="isValid"/>/>
							${property }
					</s:iterator>
				</s:if>
	            </td>
	        </tr>
	    </table>
	    <span class="btn_bc checkedEbkProdProperty">确定</span>　<span class="btn_bc checkedEbkProdPropertycancel">取消</span> 
	</div>