<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="time_table time_table_new">
	<div class="time_updown">
		<span class="btn_prev viewprodtimepricebtn" prodbranchid="<s:property value="ebkProdBranchId"/>"  branchname="<s:property value="branchName"/>" tt="UP" currpagedate="<s:date name="currPageDate" format="yyyy-MM-dd"/>">&lt;&lt;上个月</span><span class="btn_next viewprodtimepricebtn" prodbranchid="<s:property value="ebkProdBranchId"/>"  branchname="<s:property value="branchName"/>" tt="DOWN" currpagedate="<s:date name="currPageDate" format="yyyy-MM-dd"/>">下个月&gt;&gt;</span>
		<input type="hidden" name="ebkProdBranchTypeName" id="ebkProdBranchTypeName" value="${ebkProdBranchTypeName }" />
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
				<s:iterator value="ebkCalendarModel.ebkCalendar" var="ebk"	status="status">
					<ul class="plug_calendar_d">
						<s:iterator value="#ebk" var="timePrice">
							<li	date="<s:date name="#timePrice.specDate" format="yyyy-MM-dd"/>"	class="<s:if test="#timePrice.glCurrentDate">plug_calendar_full<s:if test='toShowEbkProduct!="SHOW_EBK_PRODUCT"'> editOneDayProdTimePriceStock</s:if></s:if> showDateAllInfo     <s:if test='#timePrice.timePriceId!=null && ebkProdProduct.status=="UNCOMMIT_AUDIT"'>weitijiao</s:if><s:if test='#timePrice.timePriceId!=null && ebkProdProduct.status=="PENDING_AUDIT"'>daishenhe</s:if><s:if test='#timePrice.timePriceId!=null && ebkProdProduct.status=="REJECTED_AUDIT"'>jujue</s:if>" 
							prodbranchid="<s:property value="ebkProdBranchId"/>" timepriceid="<s:property value="#timePrice.timePriceId"/>" ebkProdProductStatus="<s:property value="ebkProdProduct.status"/>">
								<div data-sellbale="true" class="plug_calendar_d_box month_1">
									<table>
										<tr class="align_r">
											<td width="10"><font><s:property value="#timePrice.dateStr"/></font></td>
											<td class="align_r"><font><s:property value="#timePrice.monthStr"/></font></td>
										</tr>
										<tr>
											<td align="left">
												<font>门</font><br/>
												<font>售</font><br/>
												<font>结</font><br/>
												<font>库</font><br/>
												<font>资</font><br/>
												<font>超</font><br/>
											</td>
											<td class="align_r">
											<s:iterator value="calendarModel.ebkCalendar" var="cal">
												<s:iterator value="#cal" var="superPrice" 	status="substatus">
													<s:if test="#superPrice.specDateCh==#timePrice.specDateCh">
														<font class="c_gray" columnname="marketPrice" showold="old">
															<s:if test="null!=#superPrice.marketPrice"><s:property value="#superPrice.marketPrice/100" /></s:if><s:else>　</s:else>
															<s:if test="null!=#timePrice.marketPrice">
																<s:if test="null!=#superPrice.marketPrice">/</s:if>
															<s:property value="#timePrice.marketPrice/100" /></s:if>
														</font><br/>
														<font class="c_gray" columnname="price" showold="old">
															<s:if test="null!=#superPrice.price"><s:property value="#superPrice.price/100" /></s:if><s:else>　</s:else>
															<s:if test="null!=#timePrice.price">
																<s:if test="null!=#superPrice.price">/</s:if>
															<s:property value="#timePrice.price/100" /></s:if>
														</font><br/>
														<font class="c_gray" columnname="settlementPrice" showold="old">
															<s:if test="null!=#superPrice.settlementPrice"><s:property value="#superPrice.settlementPrice/100" /></s:if><s:else>　</s:else>
															<s:if test="null!=#timePrice.settlementPrice">
																<s:if test="null!=#superPrice.settlementPrice">/</s:if>
															<s:property value="#timePrice.settlementPrice/100" /></s:if>
														</font><br/>
														<font class="c_gray" columnname="dayStock" showold="old">
															<s:if test="null!=#superPrice.dayStock"><s:if test="-1==#superPrice.dayStock">不限</s:if><s:else><s:property value="#superPrice.dayStock" /></s:else></s:if><s:else>　</s:else>
															<s:if test="null!=#timePrice.dayStock">
																<s:if test="null!=#superPrice.dayStock">/</s:if>
															<s:if test="'UNLIMITED_STOCK'==#timePrice.stockType">不限</s:if><s:else><s:property value="#timePrice.stockTypeShort"/><s:property value="#timePrice.dayStock" /></s:else></s:if>
														</font><br/>
														<font class="c_gray" columnname="resourceConfirm" showold="old">
															<s:if test="null!=#superPrice.resourceConfirm"><s:if test='"true"==#superPrice.resourceConfirm'>是</s:if><s:else>否</s:else></s:if><s:else>　</s:else>
															<s:if test="null!=#timePrice.resourceConfirm">
																<s:if test="null!=#superPrice.resourceConfirm">/</s:if>
															<s:if test='"true"==#timePrice.resourceConfirm'>是</s:if><s:else>否</s:else></s:if>
														</font><br/>
														<font class="c_gray" columnname="overSale" showold="old">
															<s:if test="null!=#superPrice.overSale"><s:if test='"true"==#superPrice.overSale'>是</s:if><s:else>否</s:else></s:if><s:else>　</s:else>
															<s:if test="null!=#timePrice.overSale">
																<s:if test="null!=#superPrice.overSale">/</s:if>
															<s:if test='"true"==#timePrice.overSale'>是</s:if><s:else>否</s:else></s:if>
														</font><br/>
														<s:if test="null!=#timePrice.timePriceId"><input type="hidden" name="updated" value="true"/></s:if>
														
														
														<s:if test="null!=#superPrice.marketPrice || null!=#timePrice.marketPrice">
															<s:if test="null!=#superPrice.marketPrice && null==#superPrice.forbiddenSell && null!=#timePrice.forbiddenSell">
																<input type="hidden" showold="old" columnname="forbiddenSell" value="是/<s:if test='"true"==#timePrice.forbiddenSell'>是</s:if><s:else>否</s:else>"/>
															</s:if>
															<s:elseif test="null!=#superPrice.forbiddenSell && null!=#timePrice.forbiddenSell">
																<input type="hidden" showold="old" columnname="forbiddenSell" 
																value="<s:if test='"true"==#superPrice.forbiddenSell'>是</s:if><s:else>否</s:else>/<s:if test='"true"==#timePrice.forbiddenSell'>是</s:if><s:else>否</s:else>"/>
															</s:elseif>
															<s:elseif test="null==#superPrice.forbiddenSell && null!=#timePrice.forbiddenSell">
																<input type="hidden" showold="old" columnname="forbiddenSell" 
																value="<s:if test='"true"==#timePrice.forbiddenSell'>是</s:if><s:else>否</s:else>"/>
															</s:elseif>
															<s:else>
																<input type="hidden" showold="old" columnname="forbiddenSell" value="是"/>
															</s:else>
															
															
															<input type="hidden" showold="old" columnname="aheadHour" value="<s:property value="#superPrice.aheadHour"/>"/>
															<input type="hidden" shownew="new" columnname="aheadHour" value="<s:property value="#timePrice.aheadHour"/>"/>
															<s:if test="null!=#superPrice.aheadHour && null!=#timePrice.aheadHour">
																<input type="hidden" showslash="showslash"/>
															</s:if>
															
															<input type="hidden" showold="old" columnname="cancelStrategy" value="<s:if test='"FORBID"==#superPrice.cancelStrategy'>不退不改</s:if><s:else>人工确认</s:else>"/>
															<s:if test="null!=#superPrice.cancelStrategy">
																<input type="hidden" showold="old" columnname="cancelStrategy" 
																value="<s:if test='"FORBID"==#superPrice.cancelStrategy'>不退不改</s:if><s:else>人工确认</s:else>/<s:if test='"FORBID"==#timePrice.cancelStrategy'>不退不改</s:if><s:else>人工确认</s:else>"/>
															</s:if>
														</s:if>
														
														<s:if test="#superPrice.haveShareStock"><input type="hidden" showold="old" columnname="shareStockNum" value="<s:property value="#superPrice.shareStockNum"/>"/></s:if>
													</s:if>	
												</s:iterator>
											</s:iterator>
											</td>
											
											
										</tr>
										
									</table>
								</div>
							</li>
						</s:iterator>
					</ul>
				</s:iterator>
			</div>
		</div>
	</div>
</div>