<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<p style="font-size: 14px; color: #333333;font-weight: bold;height: 24px;padding-bottom: 5px;">12产品属性：</p>
<table class="lvyouquyu">
	<tbody>
<s:if test="modelPropertyList2!=null && modelPropertyList2.size>0">
			<s:iterator value="modelPropertyList2" var="model" status="index">
				<tr>
					<td width="135" align="right"><s:property
							value="firstModelName" />&gt;<s:property
							value="secondModelName" />：<br><s:if test='isMultiChoice!="Y"'>（只能选择一个）</s:if><s:else>（可选择多个）</s:else>
					</td>
					<td width="700">
						<%-- <span	showtitle="<s:property	value="firstModelName" />&gt;<s:property value="secondModelName" />" 
								firstModelId="<s:property value='firstModelId'/>"					
								secondModelId="<s:property value='secondModelId'/>" 
								class="fp_btn js_property">请选择</span> --%>
						<div id="div_prop_<s:property value="#index.index"/>" index="<s:property value="#index.index"/>" class="jia_box div_prop"  firstModelId="<s:property value='firstModelId'/>"	secondModelId="<s:property value='secondModelId'/>" >
						
							<input type="text" class="searchInput" id="search_prop_id_<s:property value="#index.index"/>" autocomplete="off"  value="" 
							messagetitle="<s:property value='firstModelName' />&gt;<s:property value='secondModelName' />"/>
							
							<%-- <span class="ebkProdModelPropertys"  id='<s:property value="modelPropertyId" />'>
								<input	type='hidden' name="ebkProdProduct.ebkProdModelPropertys[<s:property value="#index.index"/>].modelPropertyId"	value='<s:property value="modelPropertyId" />' />
								<input	type='hidden' name="ebkProdProduct.ebkProdModelPropertys[<s:property value="#index.index"/>].ebkPropertyType" value="<s:property value="ebkPropertyType" />"/>
								<s:property value="modelPropertyName" /> <a class="colsecurrentspan"><font color="red">X</font></a>
							</span> --%>
							<div class="jia_box" id="propAddBox_<s:property value="#index.index"/>">
										
										<s:iterator value="ebkProdModelPropertys" var="ebkproperty" status="index1">
										<s:if test="ebkPropertyType==secondModelId">
											<span class="ebkProdModelPropertys"  id='prop_box_<s:property value="modelPropertyId" />'>
											<input	type='hidden' name="ebkProdProduct.ebkProdModelPropertys[<s:property value="#index.index"/>].modelPropertyId"	isMultiChoice="<s:property value="isMultiChoice"/>" value='<s:property value="modelPropertyId" />' />
											<input	type='hidden' name="ebkProdProduct.ebkProdModelPropertys[<s:property value="#index.index"/>].ebkPropertyType" value="<s:property value="ebkPropertyType" />"/>
											<s:property value="modelPropertyName" /> <a class="colsecurrentspan"><font color="red">X</font></a>
											</span>
										</s:if>
										</s:iterator>
							</div>
							
							
							
						</div>
					</td>
				</tr>
			</s:iterator>
</s:if>
</tbody>
</table>
<script type="text/javascript">
	$(function() {
		$(".div_prop").each(function(){
			var index  = $(this).attr("index");
			$("#search_prop_id_"+index).jsonSuggest({
				url:"${contextPath}/ebooking/product/searchProp.do?firstModelId=" + $("#div_prop_"+index).attr("firstModelId") + "&secondModelId=" + $("#div_prop_"+index).attr("secondModelId")+ "&subProductType=" + $("#subProductType").find("option:selected").val(),
				maxResults: 20,
				minCharacters:0,
				onSelect:function(item){
					if(item.text!='' && item.text!='undefined' && item.text!=undefined){
						if($("#prop_box_"+item.id).val()==undefined){
							var $inpuObj = $("input[name='ebkProdProduct.ebkProdModelPropertys["+index+"].modelPropertyId']");
							var size = $inpuObj.size();
							var flag = $inpuObj.attr("isMultiChoice");
							if (flag != 'Y' && size > 0) {
								$("#search_prop_id_"+index).val("");
								return;
							}
							var html="<span id='prop_box_"+item.id+"' class='ebkProdModelPropertys'>";
							html+="<input type='hidden' name='ebkProdProduct.ebkProdModelPropertys["+index+"].modelPropertyId' value='" + item.id + "' isMultiChoice='"+ item.isMultiChoice +"'/> ";
							html+="<input type='hidden' name='ebkProdProduct.ebkProdModelPropertys["+index+"].ebkPropertyType' value='" + item.type + "' />";
							html+=item.text+" <a class='colsecurrentspan'><font color='red'>X</font></a></span> ";
							
							$("#propAddBox_"+index).append(html);
							$("#search_prop_id_"+index).val("");
						}else{
							alert("您已添加过此属性，请选择其它属性！");
						} 
						return;
					}
				}
			});
		});
	});
</script>