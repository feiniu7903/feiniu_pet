			    <h3><s></s>产品信息</h3>
			    <table class="orderpro-list">
					<thead>
					    <tr>
						<th class="col1">入住日期</th>
						<th class="col1">离开日期</th>
						<th class="col1">入住晚数</th>
						<th class="col6">入住房型</th>
						<th class="col3">市场价</th>
						<th class="col4">现售价</th>
						<th class="col6">数量(间)</th>
						<th class="col7">小计</th>
					    </tr>
					</thead>
					<tbody>
						<tr>
							<td class="col1" value="${mainProdBranch.productId?if_exists}" id="id">
								${buyInfo.visitTime?if_exists}
							</td>
							<td class="col1">
								${buyInfo.leaveTime?if_exists}
							</td>
							<td class="col1">${buyInfo.days}晚</td>
							<td class="col6">
								<a href="http://www.lvmama.com/product/${mainProdBranch.productId?if_exists}">${mainProdBranch.branchName}</a>
							</td>
							<td class="col3"><del>&yen;${marketPrice}</del></td>
							<td class="col4"><strong>&yen;${sellPrice}</strong></td>
							<td class="col6 btn-nums">
							    <input type="hidden" productId="${mainProdBranch.prodBranchId}" id="param${mainProdBranch.prodBranchId}"  ordNum="ordNum"  name="paramName" minAmt="${mainProdBranch.minimum}" maxAmt="<#if (mainProdBranch.maxinum>0) >${mainProdBranch.maxinum}<#else>${mainProdBranch.stock}</#if>" textNum="textNum${mainProdBranch.prodBranchId}" people="${mainProdBranch.adultQuantity+mainProdBranch.childQuantity}" buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}*${mainProdBranch.adultQuantity+mainProdBranch.childQuantity}'/>" value="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>" sellPriceYuan="${sellPrice}"/>
							    <button class="btn-minus" type="button" onClick="updateOperator('param${mainProdBranch.prodBranchId}','miuns');refreshTotal('param${mainProdBranch.prodBranchId}','textNum${mainProdBranch.prodBranchId}',1,'total${mainProdBranch.prodBranchId}')">-</button>
							    <input name="textNum${mainProdBranch.prodBranchId}" type="text" id="textNum${mainProdBranch.prodBranchId}" value="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>" onblur="updateOperator('param${mainProdBranch.prodBranchId}','input');refreshTotal('param${mainProdBranch.prodBranchId}','textNum${mainProdBranch.prodBranchId}',1,'total${mainProdBranch.prodBranchId}')" couponPrice="0"  type="text" cashRefund="${mainProdBranch.cashRefundFloat?if_exists}" sellName="sellName" marketPrice="${mainProdBranch.marketPriceYuan?if_exists}"  sellPrice="${mainProdBranch.sellPriceYuan}" class="inp-text" size="2" autocomplete="off"/>
							    <button class="btn-add" type="button" onClick="updateOperator('param${mainProdBranch.prodBranchId}','add');refreshTotal('param${mainProdBranch.prodBranchId}','textNum${mainProdBranch.prodBranchId}',1,'total${mainProdBranch.prodBranchId}')">+</button>
							</td>
							<td class="col7"><span>&yen;</span><strong class="total" id="total${mainProdBranch.prodBranchId}"><script>document.write(${sellPrice} * <@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>)</script></strong></td>
						</tr>
						<@s.iterator value="productsList" status="st">
							<input  type="hidden" name="buyInfo.timeInfo[${st.index}].quantity" value="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>" />
							<input type="hidden" name="buyInfo.timeInfo[${st.index}].visitTime" value="<@s.property value="key"/>"/>
							<input type="hidden" name="buyInfo.timeInfo[${st.index}].productId" value="${mainProdBranch.prodBranchId}"/>
						</@s.iterator>
					</tbody>
			    </table>
