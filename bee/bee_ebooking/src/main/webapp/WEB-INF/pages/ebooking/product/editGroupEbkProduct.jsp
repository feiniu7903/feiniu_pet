<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<!-- 引用EBK公共资源(CSS、JS) -->
<s:include value="./common/ebkCommonResource.jsp"></s:include>
</head>
<body id="body_cpgl">
	<s:include value="./subPage/ebkProductHead.jsp"></s:include>
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkProduct.do" id="confirm" method="post">
		<input name="ebkProdProduct.ebkProdProductId" id="ebkProdProductId" value="${ebkProdProduct.ebkProdProductId}" type="hidden">
		<input name="ebkProdProduct.supplierId" id="supplierId" value="${ebkProdProduct.supplierId }" type="hidden">
		<input name="ebkProdProduct.productType" id="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="toShowEbkProduct" value="${toShowEbkProduct }" id="toShowEbkProduct" type="hidden">
		
		<!--新增产品基础信息-->
		<div class="xzxx_box">
			<span class="fp_btn kcwh_btn_t">提交审核</span>
			<ul class="xzxx_tab">
				<li class="tab_this" id="EBK_AUDIT_TAB">产品基础信息</li>
				<li id="EBK_AUDIT_TAB_TIME_PRICE">价格/库存维护</li>
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
					<li style="display: block;">
						<table class="jibenxinxi">
							<tr>
								<td width="140" align="right"><span class="red_ff4444">*</span>供应商产品名称：</td>
								<td width="180"><input type="text" name="ebkProdProduct.metaName" maxLength="100" value="${ebkProdProduct.metaName }" messagetitle="供应商产品名称"></td>
								<td width="160" align="right"><span class="red_ff4444">*</span>驴妈妈产品名称：</td>
								<td width="160"><input type="text" name="ebkProdProduct.prodName" maxLength="100" value="${ebkProdProduct.prodName}" messagetitle="驴妈妈产品名称"></td>
								<td width="220"><span tip-content="1、【主题+高品质**酒店】（挂牌四星以上酒店写酒店的简称，主题和酒店统一表述在括号内，且主题与酒店之间用空格隔开）+主要景点或城市+3（游玩天数）+日游+可选行程（如无可选行程则无需填写）。例如：<br>
																	<span style='color: #EE8800;'>有可选行程的</span>，产品名称为：【独家入住米兰风尚】船游天目湖、漫步南山竹海2日游（可选行程：夜泡御水温泉）。
																	<br><span style='color: #EE8800;'>无可选行程的</span>，产品名称为：【高品质国睿金陵大酒店】颐尚温泉、夫子庙、中山陵2日游
																	</br>2、若申请通过做开心驴行，标题前加开心驴行四个字，开心驴行后面跟的一横为如下：“—”
																	开心驴行—【高品质国睿金陵大酒店】颐尚温泉、夫子庙、中山陵2日游（上海出发）"
									class="text_ts tip-icon tip-icon-info"></span></td>
							</tr>
							<tr>
								<td align="right"><span class="red_ff4444">*</span>供应商产品类型：</td>
								<td>
									<s:select id="subProductType" list="productTypeList" maxLength="50" name="ebkProdProduct.subProductType" listKey="code" listValue="name" headerKey="" headerValue="请选择"  messagetitle="供应商产品类型"/>
									<span tip-content="默认选择短途跟团游。<br>自助巴士班，即开心驴行。若供应商觉得产品足够好，则向产品经理申请做开心驴行。" class="text_ts tip-icon tip-icon-info"></span>
								</td>
								<td align="right"><span class="red_ff4444">*</span>一句话推荐：</td>
								<td><input type="text" name="ebkProdProduct.recommend" maxlength="30"  value="${ebkProdProduct.recommend}"  messagetitle="一句话推荐"></td>
								<td><b>(限30个字以内，不得出现公司名称)</b></td>
							</tr>
							<tr>
								<td align="right"><span class="red_ff4444">*</span>出发地：</td>
								<td>
									<input type="text" name="ebkProdProduct.fromPlaceName" class="searchInput" autocomplete="off" id="fromPlaceName" value="<s:if test="null!=showColumnMap.get('fromPlaceName')"><s:property value="showColumnMap['fromPlaceName'].toString()"/></s:if>">
									<input id="fromPlaceId"  name="ebkProdProduct.fromPlaceId" type="hidden"  messagetitle="出发地" value="${ebkProdProduct.fromPlaceId}">
								</td>
								<td align="right"><span class="red_ff4444">*</span>目的地：</td>
								<td>
									<input type="text"  name="ebkProdProduct.toPlaceName"  class="searchInput" autocomplete="off"  id="toPlaceName" value="<s:if test="null!=showColumnMap.get('toPlaceName')"><s:property value="showColumnMap['toPlaceName'].toString()"/></s:if>">
									<input id="toPlaceId"  name="ebkProdProduct.toPlaceId" type="hidden"  messagetitle="目的地" value="${ebkProdProduct.toPlaceId}">
								</td>
								<td></td>
							</tr>
							<tr>
								<td align="right"><span class="red_ff4444">*</span>行程内包含景点：</td>
								<td>
									<input type="text"  class="searchInput" id="placeName" messagetitle="行程内包含景点">
									<input id="placeId"  name="placeId" type="hidden"  messagetitle="行程内包含景点">
								</td>
								<td colspan="3"><b>（如未找到相关行程内包含景点，可联系产品经理添加）</b></td>
							</tr>
							<tr>
								<td></td>
								<td colspan="4">
									<div class="jia_box" id="placeAddBox">
										<s:if test="null!=ebkProdProduct.ebkProdPlaces && ebkProdProduct.ebkProdPlaces.size()>0">
											<s:iterator value="ebkProdProduct.ebkProdPlaces" var="place" status="index">
												<span id='place_box_${place.placeId }'>${place.placeName } <a class="colsecurrentspan"><font color="red">X</font></a> <input type="hidden" name="ebkProdProduct.ebkProdPlaces[${index.index }].placeName" value="${place.placeName }"><input type="hidden" name="ebkProdProduct.ebkProdPlaces[${index.index }].placeId" value="${place.placeId }"></span>
											</s:iterator>
										</s:if>
									</div>
								</td>
							</tr>
							
						</table>
						<div id="showEbkProdPropertyListId"><s:include value="./subPage/ebkProdProperty.jsp"></s:include></div>

						<p class="xzxx_beizhu">（产品属性里的表格信息，如未找到对应标识，可联系产品经理添加）</p>

						<dl class="pingzheng">
							<dt>结算凭证信息：</dt>
							<dd>
								<p class="pingzheng_t">
									<span class="red_ff4444">*</span><font>履行方式</font>：<span class="fp_btn js_target" tt="SUP_PERFORM_TARGET">请选择</span>
								</p>
								<table class="pz_table" id="SUP_PERFORM_TARGET_tb">
									<tr>
										<th width="80">编号</th>
										<th width="200">名称</th>
										<th width="80">履行方式</th>
										<th width="200">履行信息</th>
										<th width="80">支付信息</th>
									</tr>
									
									<s:if test="supplierPerformTargetList != null && supplierPerformTargetList.size > 0">
										<s:iterator value="supplierPerformTargetList" var="supPerformTarget" >
											<tr>
												<td>
													${supPerformTarget.targetId }
													<input name='ebkProdProduct.ebkProdTargets[0].targetId' value='${supPerformTarget.targetId }' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[0].targetName' value='${supPerformTarget.name }' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[0].targetType' value='SUP_PERFORM_TARGET' type='hidden'>
												</td>
												<td>
													${supPerformTarget.name }
												</td>
												<td>
													${supPerformTarget.zhCertificateType }
												</td>
												<td>
													${supPerformTarget.performInfo }
												</td>
												<td>
													${supPerformTarget.paymentInfo }
												</td>
											</tr>
										</s:iterator>
									</s:if>
								</table>

								<p class="pingzheng_t">
									<span class="red_ff4444">*</span><font>订单确认方式</font>：<span class="fp_btn js_target" tt="SUP_B_CERTIFICATE_TARGET">请选择</span>
								</p>
								<table class="pz_table" id="SUP_B_CERTIFICATE_TARGET_tb">
									<tr>
										<th width="80">编号</th>
										<th width="200">名称</th>
										<th width="200">凭证</th>
										<th width="100">备注</th>
									</tr>
									<s:if test="supplierBCertificateTargetList != null && supplierBCertificateTargetList.size > 0">
										<s:iterator value="supplierBCertificateTargetList" var="supBCertificateTarget" >
											<tr>
												<td>
													${supBCertificateTarget.targetId }
													<input name='ebkProdProduct.ebkProdTargets[1].targetId' value='${supBCertificateTarget.targetId }' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[1].targetName' value='${supBCertificateTarget.name }' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[1].targetType' value='SUP_B_CERTIFICATE_TARGET' type='hidden'>
												</td>
												<td>
													${supBCertificateTarget.name }
												</td>
												<td>
													${supBCertificateTarget.viewBcertificate }
												</td>
												<td>
													${supBCertificateTarget.memo }
												</td>
											</tr>
										</s:iterator>
									</s:if>
								</table>

								<p class="pingzheng_t">
									<span class="red_ff4444">*</span><font>结算信息</font>：<span class="fp_btn js_target" tt="SUP_SETTLEMENT_TARGET">请选择</span>
								</p>
								<table class="pz_table" id="SUP_SETTLEMENT_TARGET_tb">
									<tr>
										<th width="80">编号</th>
										<th width="200">名称</th>
										<th width="200">结算周期</th>
										<th width="100">备注</th>
									</tr>
									<s:if test="supplierSettlementTargetList != null && supplierSettlementTargetList.size > 0">
										<s:iterator value="supplierSettlementTargetList" var="supSettlementTarget" >
											<tr>
												<td>
													${supSettlementTarget.targetId }
													<input name='ebkProdProduct.ebkProdTargets[2].targetId' value='${supSettlementTarget.targetId }' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[2].targetName' value='${supSettlementTarget.name}' type='hidden'>
													<input name='ebkProdProduct.ebkProdTargets[2].targetType' value='SUP_SETTLEMENT_TARGET' type='hidden'>
												</td>
												<td>
													${supSettlementTarget.name }
												</td>
												<td>
													${supSettlementTarget.zhSettlementPeriod }
												</td>
												<td>
													${supSettlementTarget.memo }
												</td>
											</tr>
										</s:iterator>
									</s:if>
								</table>
							</dd>
						</dl>

						<dl class="pingzheng">
							<dt>预订控制信息：</dt>
							<dd>
								<table class="jibenxinxi">
									<tr>
										<td width="140" align="right"><span class="red_ff4444">*</span>驴妈妈联系人：</td>
										<td width="180"><input type="text" name="ebkProdProduct.managerName"  class="searchInput" autocomplete="off" id="managerName" value="<s:property value="showColumnMap['manager'].realName"/>">
											<input id="managerId"  name="ebkProdProduct.managerId" type="hidden"  messagetitle="驴妈妈联系人" value="${ebkProdProduct.managerId}">
										</td>
										<td width="160" align="right">驴妈妈联系电话：</td>
										<td width="160"><input type="text" id="managerMobile" readonly  value="<s:property value="showColumnMap['manager'].mobile"/>"></td>
									</tr>
									<tr>
										<td width="140" align="right"><span class="red_ff4444">*</span>所属公司：</td>
										<td width="180">
											<s:select id="orgId" list="@com.lvmama.comm.vo.Constant$FILIALE_NAME@values()" name="ebkProdProduct.orgId" listKey="name()" listValue="cnName" headerKey="" headerValue="请选择" messagetitle="所属公司"></s:select>
										</td>
										<td width="160" align="right"></td>
										<td width="160"></td>
									</tr>
								</table>
							</dd>
						</dl>
					</li>
				</ul>
				<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
					<span class="btn_bc submitEdit saveEbkProdProductBaseInfo">保存</span><span><font color="red" id="errorMessageSpan"></font></span>
				</s:if>
			</div>
		</div>
		
		<!--旅游区域，游玩特色，产品主题，表单弹出层内容-->
		<div  id="property_text_div"></div>
		
		<!--履行，凭证，结算对象，表单弹出层内容-->
	   	<div id="target_list_div"></div>
	</form>
		
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function() {
		/* $(".div_prop").each(function(){
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
		}); */
		
		/*出发地查询*/
		$("#fromPlaceName").jsonSuggest({
			url:"${contextPath}/ebooking/product/searchPlace.do",
			maxResults: 20,
			minCharacters:2,
			onSelect:function(item){
				$("#fromPlaceId").val(item.id);
			}
		});
		/*目的地查询*/
		$("#toPlaceName").jsonSuggest({
			url:"${contextPath}/ebooking/product/searchPlace.do",
			maxResults: 20,
			minCharacters:2,
			onSelect:function(item){
				$("#toPlaceId").val(item.id);
			}
		});
		/*行程内包含景点查询*/
		$("#placeName").jsonSuggest({
			url:"${contextPath}/ebooking/product/searchPlace.do",
			maxResults: 20,
			minCharacters:2,
			onSelect:function(item){
				$("#placeId").val(item.id);
				if(item.text!='' && item.text!='undefined' && item.text!=undefined){
					$("#placeName").val("");
					if($("#place_box_"+item.id).val()==undefined){
						$("#placeAddBox").append("<span id='place_box_"+item.id+"'>"+item.text+" <a class='colsecurrentspan'><font color='red'>X</font></a><input type='hidden' value='"+item.id+"' name='ebkProdProduct.ebkProdPlaces.placeId'/></span> ");
					}else{
						alert("您已添加过此景点，请选择其它景点！");
					}
					return;
				}
			}
		});
		
		/*驴妈妈联系人*/
		$("#managerName").jsonSuggest({
			url:"${contextPath}/product/showManangeUser.do",
			maxResults: 20,
			minCharacters:2,
			onSelect:function(item){
				$("#managerId").val(item.id);
				$("#managerMobile").val(item.mobile);
			}
		});
	});
</script>
</html>