<div class="xdl-hor">
	<dl class="xdl">
	    <dt class="B">您预订：</dt>
	    <dd><a class="B" target="_blank" href="http://www.lvmama.com/product/${mainProdBranch.productId?if_exists}">${mainProdBranch.prodProduct.productName}</a></dd>
	</dl>
	<dl class="xdl">
	    <dt class="B"><i class="req">*</i>
	    	<!--非不定期才显示游玩日期 -->
			<#if mainProdBranch.prodProduct.IsAperiodic()>
				产品有效期：
			<#else>
				游玩日期：
			</#if>
		</dt>
	    <dd>
	    	<#if mainProdBranch.prodProduct.IsAperiodic()>
					${mainProdBranch.validBeginTime?string('yyyy-MM-dd')}至${mainProdBranch.validEndTime?string('yyyy-MM-dd')}
					<#if mainProdBranch.invalidDateMemo != null && mainProdBranch.invalidDateMemo != "">
						(${mainProdBranch.invalidDateMemo})
					</#if>
			<#else>	
		        	
		        	<div class="dinput dinput-date">
                    <input id="input_visitTime" class="input-date calendar" type="text" placeholder="${buyInfo.visitTime?if_exists}" maxlength="10">
                    <span class="date-info">
                    <i class="xicon icon-date"></i>
                    <span class="text-info"></span>
                    </span>
                    </div>
			</#if>
	    </dd>
	</dl>
	<dl class="xdl JS_check">
	    <dt class="B"><i class="req">*</i>预订数量：</dt>
		<dd class="check-radio-box">
			<div class="check-text">
				<span class="check-radio-item">
				<#if mainProdBranch.description?if_exists??>
					<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
				</#if>
				<a href="javascript:;">
					<#if mainProdBranch.branchName?? && mainProdBranch.branchName?length gt 20>
						${mainProdBranch.branchName?substring(0,18)}...
					<#else>
						${mainProdBranch.branchName?if_exists}
					</#if>
				</a>
				</span>
				<!-- minAmt="最小起订数" maxAmt="最大订购数" textNum="用户订购数" buyPeople="订购总人数" people="成人数+儿童数" -->
				<input type="hidden" 
				    value="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>" 
					productId="${mainProdBranch.prodBranchId}" 
					id="param${mainProdBranch.prodBranchId}" 
					ordNum="ordNum" name="paramName" 
					minAmt="${mainProdBranch.minimum}" 
					maxAmt="<#if (mainProdBranch.maximum>0) >${mainProdBranch.maximum}<#else>${mainProdBranch.stock}</#if>" 
					textNum="textNum${mainProdBranch.prodBranchId}" 
					people="${mainProdBranch.adultQuantity+mainProdBranch.childQuantity}" 
					buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}*${mainProdBranch.adultQuantity+mainProdBranch.childQuantity}'/>" 
					value="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}*${mainProdBranch.adultQuantity+mainProdBranch.childQuantity}'/>" 
					sellPriceYuan="${mainProdBranch.sellPriceYuan}"/>
				<span class="oper-numbox">
					<a class="op-reduce" onClick="updateOperator('param${mainProdBranch.prodBranchId}','miuns');">-</a>
					<input type="text" class="op-number" id="textNum${mainProdBranch.prodBranchId}" name="buyInfo.buyNum.product_${mainProdBranch.prodBranchId}" 
					value="<@s.property value='buyInfo.buyNum.product_${mainProdBranch.prodBranchId}'/>" 
					onblur="updateOperator('param${mainProdBranch.prodBranchId}','input');" 
					couponPrice="0"  type="text" cashRefund="${mainProdBranch.prodProduct.cashRefundFloat?if_exists}" 
					sellName="sellName" marketPrice="${mainProdBranch.prodProduct.marketPriceYuan?if_exists}"  
					sellPrice="${mainProdBranch.sellPriceYuan}" size="2" autocomplete="off" mainBranchName="${mainProdBranch.branchName}"/>
					<a class="op-increase" onClick="updateOperator('param${mainProdBranch.prodBranchId}','add');">+</a>
				</span>
				 （单价：<dfn>&yen;<i>${mainProdBranch.sellPriceYuan?if_exists}</i></dfn>）
			</div>
			<#if undefined!=mainProdBranch.description&&mainProdBranch.description?if_exists?? && mainProdBranch.description != ''>
				<div class="tiptext tip-info check-content">
					<span class="tip-close">&times;</span>
					<div class="pre-wrap">
					${mainProdBranch.description?if_exists?replace('<div class=\'xtext\'>', '')?replace('</div>', '')?replace('<h4>', '')?replace('</h4>', '')?replace('<p>', '')?replace('\n', '')?replace('\t', '')?replace('</p>', '</br>')}</div>  
				</div>
			</#if>
		</dd>
		<#list relatedProductList as product>
		    <dd class="check-radio-box">
			<div class="check-text">
			    <span class="check-radio-item">
			    	<#if product.description?if_exists><i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></#if>
			    	<#if product.branchName?? && product.branchName?length gt 20>
						${product.branchName?substring(0,18)}...
					<#else>
						${product.branchName?if_exists}
					</#if>
			    </span>
			    <span class="oper-numbox">
			    	<input type="hidden" productId="${product.prodBranchId}" id="param${product.prodBranchId}"  ordNum="ordNum"  
			    	name="paramName" 
			    	minAmt="${product.minimum}" 
			    	maxAmt="<#if (product.maximum>0) >${product.maximum}<#else>${product.stock}</#if>" 
			    	textNum="textNum${product.prodBranchId}" 
			    	people="${product.adultQuantity+product.childQuantity}" 
			    	buyPeopleNum="<@s.property value="buyInfo.getBuyNumValue('product_${product.prodBranchId}') * ${product.adultQuantity+product.childQuantity}"/>" 
			    	value="<@s.property value="buyInfo.getBuyNumValue('product_${product.prodBranchId}')"/>" 
			    	sellPriceYuan="${product.sellPriceYuan}"/>
			    	<a class="op-reduce" onClick="updateOperator('param${product.prodBranchId}','miuns');">-</a>
			    	<input type="text" class="op-number" id="textNum${product.prodBranchId}" name="buyInfo.buyNum.product_${product.prodBranchId}" 
					value="<@s.property value='buyInfo.buyNum.product_${product.prodBranchId}'/>" 
					onblur="updateOperator('param${product.prodBranchId}','input');" 
					couponPrice="0"  type="text" cashRefund="${product.prodProduct.cashRefundFloat?if_exists}" 
					sellName="sellName" marketPrice="${product.prodProduct.marketPriceYuan?if_exists}"  
					sellPrice="${product.sellPriceYuan?if_exists}" size="2" autocomplete="off" mainBranchName="${product.branchName}"/>
			    	<a class="op-increase" onClick="updateOperator('param${product.prodBranchId}','add');">+</a>
			    </span>
			          （单价：<dfn>&yen;<i>${product.sellPriceYuan?if_exists}</i></dfn>）
			</div>
			<#if undefined!=product.description&&product.description?if_exists&&product.description!="" >
				<div class="tiptext tip-info check-content">
				    <span class="tip-close">&times;</span>
				    <div class="pre-wrap">${product.description?if_exists?replace('<div class=\'xtext\'>', '')?replace('</div>', '')?replace('<h4>', '')?replace('</h4>', '')?replace('<p>', '')?replace('\n', '')?replace('\t', '')?replace('</p>', '</br>')}</div>  
				</div>
			</#if>
		    </dd>
		</#list>
		<!--房差-->
		<#if additionalProduct?? && additionalProduct['房差']?? && additionalProduct['房差'].size() gt 0>
			<#list additionalProduct['房差'] as product>      
					<dd class="check-radio-box">
						<div class="check-text">
						    <span class="check-radio-item">
						    	<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>${product.branch.branchName?if_exists}
						    </span>
						    <span class="oper-numbox">
						    	<a class="op-reduce">-</a>
						    	<input type="text" class="op-number" name="buyInfo.buyNum.product_${product.prodBranchId?if_exists}" 
						    	btype="${product.branch.branchType}" 
						    	pstype="${product.relationProduct.subProductType}"  
						    	saleNumType="${product.saleNumType}" 
						    	id="input${product.branch.prodBranchId}" minAmt="${product.branch.minimum}" size="2" 
						    	maxAmt="<#if (product.branch.maximum>0) >${product.branch.maximum}<#else>${product.branch.stock}</#if>" 
						    	sellPrice="${product.branch.sellPriceYuan}" minBranchName="${product.branch.branchName}"> 
						    	<a class="op-increase">+</a>
						    </span>
						         （单价：<dfn>&yen;<i>${product.branch.sellPriceYuan?if_exists}</i></dfn>）
						</div>
						<div class="tiptext tip-info check-content">
						    <span class="tip-close">&times;</span>
						    <div class="pre-wrap">行程中的住宿一般为两个床位的标准间，出游人数（成人）为单数时，需补足额外一个床位的费用。您也可以选择接受与其他人拼房，当无法拼房时再补房差。</div>  
						</div>
				    </dd>
			</#list>
		</#if> 
	</dl>
	<!-- 集合地点 -->
	<#if prodAssemblyPointList?? && prodAssemblyPointList.size() gt 0>
		<dl class="xdl">
		    <dd class="dot_line">间隔线</dd>
		    <dt class="B"><i class="req">*</i>集合地点：</dt>
		    <dd>
			<#list  prodAssemblyPointList as assemblyPoint>
				<div class="check-text">
					<label class="radio inline">
						<input class="input-radio" name="prodAssemblyPoint" type="radio" value="${assemblyPoint.assemblyPoint?if_exists}">${assemblyPoint.assemblyPoint?if_exists}
					</label>
				</div>
			</#list>
			<div class="tiptext tip-warning">为保证您的出行顺利，请务必选择出发地点</div>
		    </dd>
		</dl>
	</#if>
	<!-- 税金 -->
	<#if additionalProduct?? && additionalProduct['税金']?? && additionalProduct['税金'].size() gt 0>
		<dl class="xdl JS_check">
			<#list additionalProduct['税金'] as tax>      
				    <dd class="dot_line">间隔线</dd>
					    <dt class="B">税金：</dt>
						<dd class="check-radio-box">
							<div class="check-text">
							    <label class="radio inline">
							    	 <input type="hidden" ordNum='ordNum' value="0" name="buyInfo.buyNum.product_${tax.branch.prodBranchId}"  
								     id="addition${tax.branch.prodBranchId}" sellName="sellName" 
								     cashRefund="" couponPrice="0" marketPrice="${tax.branch.marketPriceYuan?if_exists}" 
								     sellPrice="${tax.branch.sellPriceYuan?if_exists}" minBranchName="${tax.relationProduct.productName?if_exists}(${tax.branch.branchName?if_exists})"/>
							    	<input class="input-radio" name="tax" 
							    	type="radio" btype="${tax.branch.branchType}" pstype="${tax.relationProduct.subProductType}" 
							    	id="${tax.branch.prodBranchId}" saleNumType="${tax.saleNumType}" onClick="validateRadioIsTrue();" tt="fromRadio"/>
							    </label>
							    <span class="check-radio-item">
							    	<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
							    	${tax.relationProduct.productName}(${tax.branch.branchName?if_exists})
							    </span>
							    <dfn>&yen;${tax.branch.sellPriceYuan?if_exists}</dfn>/人
							</div>
							<#if tax.description??>
							<div class="tiptext tip-info check-content">
							    <span class="tip-close">&times;</span>
							    <div class="pre-wrap">${tax.description?if_exists?replace("\n","<br/>")}</div>
							</div>
							</#if>
					    </dd>
						</dt>
					</dd>
			</#list>
		</dl>
	</#if>
	<#if mainProdBranch.prodProduct.productType == "ROUTE">
		<dl class="xdl">
            <dd class="dot_line">间隔线</dd>
            <dt></dt>
            <dd>
                <h5>以下信息存在请勾选，方便我们更好地为您服务</h5>
                <div class="check-text"><label class="checkbox inline"><input class="input-checkbox" name="otherinfo" type="checkbox">出游人中存在16岁以下未成年人</label></div>
                <div class="check-text"><label class="checkbox inline"><input class="input-checkbox" name="otherinfo" type="checkbox">出游人中存在70岁以上老人</label></div>
                <div class="check-text"><label class="checkbox inline"><input class="input-checkbox" name="otherinfo" type="checkbox">出游人中有外籍友人（包含港澳台同胞）</label></div>
                <div class="check-text"><label class="checkbox inline"><input class="input-checkbox" name="otherinfo" type="checkbox">出游人中有身体不健康者</label></div>
            </dd>
        </dl>
	</#if>
</div>