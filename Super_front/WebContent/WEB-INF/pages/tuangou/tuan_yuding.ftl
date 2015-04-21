           <h3 class="buy-head3">预订信息</h3>
           <div class="buy-info">
                <dl>
                    <dt>您预订的是：</dt>
                    <dd><span style="color:#06c;">${mainProdBranch.prodProduct.productName}</span></dd>
                </dl>
                <dl>
                    <dt>游玩日期：</dt>
                    <dd>${buyInfo.visitTime?if_exists}</dd>
                </dl>
                <dl>
                    <dt>预订数量：</dt>
                    <dd>
	                    <#if mainProdBranch.branchName?? && mainProdBranch.branchName?length gt 20>
							<span style="color:#06c;">${mainProdBranch.branchName?substring(0,18)}...</span>
						<#else>
							<span style="color:#06c;">${mainProdBranch.branchName?if_exists}</span>
						</#if> × 
						<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>
					</dd>
                </dl>
            </div>