<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/msg_ord_snspt.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/button.css">
<link rel="stylesheet" href="${basePath}/themes/ebk/select2/select2.css">
<style> 
#input{width:120px;height:30px;cursor:pointer;} 
.plug_calendar_d_box p {overflow: hidden;zoom:1;}
.plug_calendar_d_box p b { float: left;}
.plug_calendar_d_box p span { float: left;}
</style> 

<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
	});
	
	
	$("#prodBranchIdSelect").change(function (){
		var ebkProdBranchProdBranchId=$("#prodBranchIdSelect  option:selected").val();
		$("#tabPage").load("${basePath}/ebooking/prod/prodAuditApplyDetailTimePrice.do?ebkProdProductId=${ebkProdProductId}&ebkProdBranchProdBranchId="+ebkProdBranchProdBranchId);
	});
	$(".viewprodtimepricebtnAudit").click(function(){
		var monthType = $(this).attr("tt");
		var currPageDate = $(this).attr("currpagedate");
		
		var ebkProdBranchProdBranchId=$("#prodBranchIdSelect  option:selected").val();
		$("#tabPage").load("${basePath}/ebooking/prod/prodAuditApplyDetailTimePrice.do?ebkProdProductId=${ebkProdProductId}&ebkProdBranchProdBranchId="+ebkProdBranchProdBranchId+"&currPageDate="+currPageDate+"&monthType="+monthType);
	});
</script>
<div class="time_box_all">
	<input type="hidden" value="${timePriceMonth}" id="currentMonth"/>
	选择价种：
	<select id="prodBranchIdSelect">	
		<s:iterator value="ebkProdBranchList" var="ebkProdBranch">
			<option value="${ebkProdBranch.prodBranchId}" <s:if test="#ebkProdBranch.prodBranchId==ebkProdBranchProdBranchId">selected="selected"</s:if>>${ebkProdBranch.branchName}</option>
		</s:iterator>
	</select>
	</br></br>
	<div class="time_table">
		<div class="time_updown">
			<span class="btn_prev viewprodtimepricebtnAudit" tt="UP" currpagedate="<s:date name="currPageDate" format="yyyy-MM-dd"/>">&lt;&lt;上个月</span>
			<span class="btn_next viewprodtimepricebtnAudit" tt="DOWN" currpagedate="<s:date name="currPageDate" format="yyyy-MM-dd"/>">下个月&gt;&gt;</span>
		</div>
		<div class="time_box">
			<div class="plug_calendar_main">
				<h2 class="plug_calendar_tit"><s:date name="currPageDate" format="yyyy年MM月"/></h2>
					<div class="plug_calendar_table">
						<ul class="plug_calendar_t">
							<li>日</li>
							<li>一</li>
							<li>二</li>
							<li>三</li>
							<li>四</li>
							<li>五</li>
							<li>六</li>
						</ul>
						<s:iterator value="ebkProdTimePriceAuditComboList" var="ebkProdTimePriceAuditCombo" status="timePriceStatus">
							<ul class="plug_calendar_d">
								<li class="plug_calendar_full">
									<div data-sellbale="true" class="plug_calendar_d_box month_1">
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.marketPrice!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.marketPrice!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.marketPrice==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.marketPrice">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>门：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.marketPrice/100}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.marketPrice/100}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.price!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.price!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.price==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.price">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>售：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.price/100}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.price/100}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.settlementPrice!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.settlementPrice!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.settlementPrice==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.settlementPrice">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>结：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.settlementPrice/100}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.settlementPrice/100}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.resourceConfirm!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.resourceConfirm!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.resourceConfirm==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.resourceConfirm">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>资源：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.resourceConfirm=='true'?'是':'否'}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.resourceConfirm=='true'?'是':'否'}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.overSale!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.overSale!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.overSale==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.overSale">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>超卖：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.overSale=='true'?'是':'否'}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.overSale=='true'?'是':'否'}</span>
											</p>
										</s:if>
										
										<s:if test="(#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.breakfastCount!=null && #ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.breakfastCount>0) || (#ebkProdTimePriceAuditCombo.ebkProdTimePrice.breakfastCount!=null && #ebkProdTimePriceAuditCombo.ebkProdTimePrice.breakfastCount>0)">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.breakfastCount==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.breakfastCount">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>早餐：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.breakfastCount}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.breakfastCount}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.forbiddenSell!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.forbiddenSell!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.forbiddenSell==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.forbiddenSell">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>禁售：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.forbiddenSell=='true'?'是':'否'}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.forbiddenSell=='true'?'是':'否'}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.cancelStrategy!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.cancelStrategy!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.cancelStrategy==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.cancelStrategy">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>退改：</b><span>${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.cancelStrategyZh}</span>
												<span class="c_gray">${ebkProdTimePriceAuditCombo.ebkProdTimePrice.cancelStrategyZh}</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.dayStock!=null
												|| #ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.stockType!=null
												|| #ebkProdTimePriceAuditCombo.ebkProdTimePrice.dayStock!=null 
												|| #ebkProdTimePriceAuditCombo.ebkProdTimePrice.stockType!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.dayStock==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.dayStock
														&& #ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.stockType==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.stockType">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>库存：</b><span>
															<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.stockType=='UNLIMITED_STOCK'">
																${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.stockTypeCh}
															</s:if>
															<s:else>
																${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.stockTypeShort}
																${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.dayStock}
															</s:else>
														</span>
												<span class="c_gray">
													<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePrice.stockType=='UNLIMITED_STOCK'">
														${ebkProdTimePriceAuditCombo.ebkProdTimePrice.stockTypeCh}
													</s:if>
													<s:else>
														${ebkProdTimePriceAuditCombo.ebkProdTimePrice.stockTypeShort}
														${ebkProdTimePriceAuditCombo.ebkProdTimePrice.dayStock}
													</s:else>
												</span>
											</p>
										</s:if>
										
										<s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.aheadHour!=null || #ebkProdTimePriceAuditCombo.ebkProdTimePrice.aheadHour!=null">
											<p <s:if test="#ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.aheadHour==#ebkProdTimePriceAuditCombo.ebkProdTimePrice.aheadHour">class="kucun"</s:if><s:else>class="menshijia"</s:else>>
											<b>提前订：</b><span><fmt:formatNumber value="${ebkProdTimePriceAuditCombo.ebkProdTimePriceNew.aheadHour/60}" pattern="#0.0" />h</span>
												<span class="c_gray"><fmt:formatNumber value="${ebkProdTimePriceAuditCombo.ebkProdTimePrice.aheadHour/60}" pattern="#0.0" />h</span>
											</p>
										</s:if>
										
										<span class="plug_calendar_day">
											<s:if test="#ebkProdTimePriceAuditCombo.isFirstDayOfMonth()">
												<s:date name="#ebkProdTimePriceAuditCombo.day" format="MM月d"/>
											</s:if>
											<s:else>
												<s:date name="#ebkProdTimePriceAuditCombo.day" format="d"/>
											</s:else>
										</span>
									</div>
								</li>
							</ul>
						</s:iterator>
				</div>
			</div>
		</div>
	</div>
</div>