<!--====== 日历开始 S =======-->
        <form id="productFrom" action="/purse/buy.do" method="post">
        	<input type="hidden" name="buyInfo.buyNum.product_<@s.property value="prodCProduct.prodProduct.productId"/>" value="<@s.property value="prodCProduct.prodProduct.minimum"/>"/>
        	<@s.if test='prodCProduct.prodProduct.payToLvmama=="true"'>
					<input type="hidden" name="buyInfo.paymentTarget" value="TOLVMAMA"/>
				</@s.if>
				<@s.elseif test='prodCProduct.prodProduct.payToSupplier=="true"'>
					<input type="hidden" name="buyInfo.paymentTarget" value="TOSUPPLIER"/>
				</@s.elseif>
        	<input type="hidden" id="productId" name="buyInfo.productId" />
        	<input type="hidden" id="date" name="buyInfo.visitTime"/>
        </form>
 		<div class="lv-cal" id="cal_window">
                <div   id="cal_window">
                	<div><button type="button" class="lv-close-btn" id="close_btn">×</button>
			                    	
			                    	<@s.iterator   value="calendarModels" status="st" var="cal">
			                    		<@s.if test="#st.index==0">
					                    	<table id="tb<@s.property value="#cal.month" />" class="lv-cal-table">
					                                  <thead><tr>
					                                    <th colspan="7">
					                                    <span class="turn_left"><@s.property value="#cal.month" />月</span>
					                                    <span class="turn_right_c" onclick="nextMonth('tb<@s.property value="#cal.month" />','tb<#if month==12>1<#else><@s.property value="#cal.month+1" /></#if>')" id="nextMonth"><#if month==12>1<#else><@s.property value="#cal.month+1" /></#if>月</span><@s.property value="#cal.year" />年<@s.property value="#cal.month" />月价格日历<strong>（点击选择）</strong></th></tr></thead>
					                                  <tbody>
					                                  <tr>
					                                    <th>星期日</th>
					                                    <th>星期一</th>
					                                    <th>星期二</th>
					                                    <th>星期三</th>
					                                    <th>星期四</th>
					                                    <th>星期五</th>
					                                    <th>星期六</th>
					                                  </tr>
					                                  </tbody>
					                                  <tbody>
						                                  	<@s.iterator   value="#cal.calendar" status="st" var="day">
									                                  <tr>
										                                  <@s.iterator value="#day" status="tday" var="t">
													                                    <td date="<@s.date name="#t.specDate" format="yyyy-MM-dd"/>" productId="<@s.property value="#t.productId"/>">
													                                    		<@s.property value="#t.friday"/>
														                                   		 <@s.if test="#t.priceF=='0.0'">
														                                   			 <font><@s.date name="#t.specDate" format="dd"/></font>
														                                   			 <br/>
																							     </@s.if>
																							     <@s.else>
																								     <span>
																								     	<strong><@s.date name="#t.specDate" format="dd"/></strong>
																								     	<br/>
																								     	￥<@s.property value="#t.priceF"/>
																								    </span>
																							     </@s.else>
													                                    </td>
										                                      </@s.iterator>
									                                  </tr>
									                            </@s.iterator>
					                                  </tbody>
					                        </table>
					                      </@s.if>
					                      <@s.else>
					                      		<table id="tb<@s.property value="#cal.month" />" class="lv-cal-table" style="display:none;">
					                                  <thead><tr>
					                                    <th colspan="7">
					                                    <span class="turn_left_c" onclick="nextMonth('tb<@s.property value="#cal.month" />','tb<#if (month-1)==0>12<#else><@s.property value="#cal.month-1" /></#if>')"><#if (month-1)==0>12<#else><@s.property value="#cal.month-1" /></#if>月</span>
					                                    <span class="turn_right"  id="nextMonth"><@s.property value="#cal.month" />月</span><@s.property value="#cal.year" />年<@s.property value="#cal.month" />月价格日历<strong>（点击选择）</strong></th></tr></thead>
					                                  <tbody>
					                                  <tr>
					                                    <th>星期日</th>
					                                    <th>星期一</th>
					                                    <th>星期二</th>
					                                    <th>星期三</th>
					                                    <th>星期四</th>
					                                    <th>星期五</th>
					                                    <th>星期六</th>
					                                  </tr>
					                                  </tbody>
					                                  <tbody>
						                                  	<@s.iterator   value="#cal.calendar" status="st" var="day">
									                                  <tr>
										                                  <@s.iterator value="#day" status="tday" var="t">
													                                    <td date="<@s.date name="#t.specDate" format="yyyy-MM-dd"/>" productId="<@s.property value="#t.productId"/>">
													                                    		<@s.property value="#t.friday"/>
														                                   		 <@s.if test="#t.priceF=='0.0'">
														                                   			 <font><@s.date name="#t.specDate" format="dd"/></font>
														                                   			 <br/>
																							     </@s.if>
																							     <@s.else>
																								     <span>
																								     	<strong><@s.date name="#t.specDate" format="dd"/></strong>
																								     	<br/>
																								     	￥<@s.property value="#t.priceF"/>
																								    </span>
																							     </@s.else>
													                                    </td>
										                                      </@s.iterator>
									                                  </tr>
									                            </@s.iterator>
					                                  </tbody>
					                        </table>
					                      </@s.else>
			                         </@s.iterator>
                	</div>
                </div>
                <!--====== 日历开始 E =======-->

        
