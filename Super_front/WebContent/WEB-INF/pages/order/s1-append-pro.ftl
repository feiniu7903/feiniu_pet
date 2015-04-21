<#if additionalProduct?? && ((additionalProduct['保险']?? && additionalProduct['保险'].size() gt 0) || (additionalProduct['自费产品']?? && additionalProduct['自费产品'].size() gt 0) || (additionalProduct['其它']?? && additionalProduct['其它'].size() gt 0))>
<!-- 附加产品 -->
<div class="hr_a"></div>
<div class="order-title">
    <h3>附加产品</h3>
</div>
<div class="order-content xdl-hor">
    <div class="form-small">
	  <#if additionalProduct['保险']?? && additionalProduct['保险'].size() gt 0>
		<dl class="xdl JS_check">
		    <dt class="B">保险：</dt>
		    <#list additionalProduct['保险'] as insurance>
			    <dd class="check-radio-box">
				<div class="check-text">
				    <label class="radio inline">
					    <input type="hidden" ordNum='ordNum' value="0" name="buyInfo.buyNum.product_${insurance.branch.prodBranchId}"  
					    id="addition${insurance.branch.prodBranchId}" sellName="sellName" 
					    cashRefund="" couponPrice="0" marketPrice="${insurance.branch.marketPriceYuan?if_exists}" 
					    sellPrice="${insurance.branch.sellPriceYuan?if_exists}" minBranchName="${insurance.relationProduct.productName?if_exists}(${insurance.branch.branchName?if_exists})"/>
					    <input class="input-radio" name="insurance" type="radio" 
					    id="${insurance.branch.prodBranchId}" btype="${insurance.branch.branchType}" pstype="${insurance.relationProduct.subProductType}" 
					    saleNumType="${insurance.saleNumType?if_exists}" onClick="updateAdditionRadio('insurance');" tt="fromRadio" />
				    </label>
				    <span class="check-radio-item">
				    <#if insurance.branch.description??><i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></#if>
				    ${insurance.relationProduct.productName?if_exists}(${insurance.branch.branchName?if_exists})</span>
				    <dfn>&yen;${insurance.branch.sellPriceYuan?if_exists}</dfn>/人
				</div>
				<#if insurance.branch.description??>
					<div class="tiptext tip-info check-content">
					    <span class="tip-close">&times;</span>
					    <div class="pre-wrap">${insurance.branch.description?replace("\n","<br/>")}</div>
					</div>
				</#if>
			    </dd>
		    </#list>
		    <dd class="check-radio-box">
			<div class="check-text">
			    <label class="radio inline"><input class="input-radio no-check" name="insurance" onClick="updateAdditionRadio('insurance');" type="radio" value="0" ><span class="no-check">不需要保险</span></label>
			</div>
			<div class="tiptext tip-warning">
			    <div class="pre-wrap">旅游保险能够给您的出行安全带来更多保障，所以驴妈妈建议您务必购买旅游保险。如您放弃购买，则行程中的风险和损失将由您自行承担。</div>
			</div>
		    </dd>
		</dl>
        </#if>
        
        <#if additionalProduct['自费产品']?? && additionalProduct['自费产品'].size() gt 0>
		<dl class="xdl JS_check form-inline">
		    <dd class="dot_line">间隔线</dd>
		    <dt class="B">自费产品：</dt>
		    <#list additionalProduct['自费产品'] as ownexpense>
			    <dd class="check-radio-box">
				<div class="check-text"><label class="checkbox inline">
					<input class="input-checkbox" name="ownpro" type="checkbox" value="${ownexpense.branch.prodBranchId}" productAdditional="true"></label>
					<span class="check-radio-item">
					<#if ownexpense.branch.description??><i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></#if>
					${ownexpense.relationProduct.productName?if_exists}(${ownexpense.branch.branchName?if_exists})
					</span>
				    <dfn>&yen;${ownexpense.branch.sellPriceYuan?if_exists}</dfn>
				    <input type="hidden" ordNum='ordNum' value="0" name="buyInfo.buyNum.product_${ownexpense.branch.prodBranchId}"  
				    id="addition${ownexpense.branch.prodBranchId}" sellName="sellName" 
				    cashRefund="" couponPrice="0"  
				    marketPrice="${ownexpense.branch.marketPriceYuan?if_exists}" 
				    sellPrice="${ownexpense.branch.sellPriceYuan?if_exists}" minBranchName="${ownexpense.relationProduct.productName?if_exists}(${ownexpense.branch.branchName?if_exists})"/>
					
					<div id="input${ownexpense.branch.prodBranchId}"  btype="${ownexpense.branch.branchType}" pstype="${ownexpense.relationProduct.subProductType}" saleNumType="${ownexpense.saleNumType}" minAmt="${ownexpense.branch.minimum}" maxAmt="<#if (ownexpense.branch.maximum>0) >${ownexpense.branch.maximum}<#else>${ownexpense.branch.stock}</#if>"  class="selectbox hide">
	                	<p class="select-info like-input">
		                    <span class="select-arrow"><i class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span>
		                    <span class="select-value"></span>
		                </p>
		                <div class="selectbox-drop">
			                <ul class="select-results">
			                </ul>
		                </div>
		            </div>
				</div>
				<#if ownexpense.branch.description??>
					<div class="tiptext tip-info check-content">
					    <span class="tip-close">&times;</span>
					    <div class="pre-wrap">${ownexpense.branch.description?replace("\n","<br/>")}</div>
					</div>
				</#if>
			    </dd>
		    </#list>
		</dl>
        </#if>

	<#if additionalProduct['其它']?? && additionalProduct['其它'].size() gt 0>
		<dl class="xdl JS_check">
		    <dd class="dot_line">间隔线</dd>
		    <dt class="B">其他：</dt>
		    <#list additionalProduct['其它'] as other>
			    <dd class="check-radio-box">
				<div class="check-text"><label class="checkbox inline">
					<input class="input-checkbox" name="ownpro" type="checkbox" value="${other.branch.prodBranchId}" productAdditional="true"></label><span class="check-radio-item">
					<#if other.branch.description??><i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></#if>
					${other.relationProduct.productName?if_exists}(${other.branch.branchName?if_exists})</span>
				    <dfn>&yen;${other.branch.sellPriceYuan?if_exists}</dfn>
				    <input type="hidden" ordNum='ordNum' value="0" name="buyInfo.buyNum.product_${other.branch.prodBranchId}"  
				    id="addition${other.branch.prodBranchId}" sellName="sellName" couponPrice="0" marketPrice="${other.branch.marketPriceYuan?if_exists}" 
				    sellPrice="${other.branch.sellPriceYuan?if_exists}" minBranchName="${other.relationProduct.productName?if_exists}(${other.branch.branchName?if_exists})"/>
					
					<div id="input${other.branch.prodBranchId}"  btype="${other.branch.branchType}" pstype="${other.relationProduct.subProductType}"  saleNumType="${other.saleNumType}" minAmt="${other.branch.minimum}" maxAmt="<#if (other.branch.maximum>0) >${other.branch.maximum}<#else>${other.branch.stock}</#if>" class="selectbox hide">
		                <p class="select-info like-input">
		                    <span class="select-arrow"><i class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span>
		                    <span class="select-value"></span>
		                </p>
		                <div class="selectbox-drop">
			                <ul class="select-results">
			                </ul>
		                </div>
		            </div>
				</div>
				<#if other.branch.description??>
					<div class="tiptext tip-info check-content">
					    <span class="tip-close">&times;</span>
					    <div class="pre-wrap">${other.branch.description?replace("\n","<br/>")}</div>
					</div>
				</#if>
			    </dd>
		    </#list>
		</dl>
	</#if>
    </div>
</div> <!-- //附加产品 -->
</#if>

<!-- 快递信息  -->
<#if additionalProduct?? && (additionalProduct['快递']?? && additionalProduct['快递'].size() gt 0)>
<div class="hr_a"></div>
<div class="order-title">
    <h3>快递信息</h3>
</div>
<div class="order-content xdl-hor">
    <div class="form-small">
		<dl class="xdl JS_check">
		    <dd class="dot_line">间隔线</dd>
		    <dt class="B">快递：</dt>
		    <#list additionalProduct['快递'] as express>
			    <dd class="check-radio-box">
				<div class="check-text"><label class="radio inline">
					<input type="hidden" ordNum='ordNum' value="0" name="buyInfo.buyNum.product_${express.branch.prodBranchId}"  
				    id="addition${express.branch.prodBranchId}" sellName="sellName" 
				    cashRefund="" couponPrice="0" marketPrice="${express.branch.marketPriceYuan?if_exists}" 
				    sellPrice="${express.branch.sellPriceYuan?if_exists}" minBranchName="${express.relationProduct.productName?if_exists}(${express.branch.branchName?if_exists})"/>
					<input class="input-radio" name="express" type="radio" 
					name="express" btype="${express.branch.branchType_}" pstype="${express.relationProduct.subProductType}" 
					id="${express.branch.prodBranchId}" saleNumType="${express.saleNumType?if_exists}" onClick="updateAdditionRadio();" value="1" tt="fromRadio"/>
				</label>
				<span class="check-radio-item">
				<#if express.branch.description??><i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></#if>
				${express.relationProduct.productName?if_exists}(${express.branch.branchName?if_exists})</span>
				<dfn>&yen;${express.branch.sellPriceYuan?if_exists}</dfn>
				</div>
				<#if express.branch.description??>
					<div class="tiptext tip-info check-content">
					    <span class="tip-close">&times;</span>
					    <div class="pre-wrap">${express.branch.description?replace("\n","<br/>")}</div>
					</div>
				</#if>
			    </dd>
		    </#list>
		</dl>
		<!--是否需要邮寄地址start-->
		<div id="showAddress">
			<input type="hidden" name="physical" value="true"/>
			<input type="hidden" valid="true" value="1">
			<!--如果存在地址就显示修改状态下的样式 start -->
			
	        <div id="frast-edit"  class="form-edit <#if usrReceiversList?size gt 0>hide</#if>">
	            <dl class="xdl">
	                <dt><i class="req">*</i>收件人姓名：</dt>
	                <dd>
	                    <input id="address-user" type="text" name="text-frast" class="input-text" value="${usrReceivers.receiverName}" maxlength="20"/>
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt><i class="req">*</i>手机号码：</dt>
	                <dd>
	                    <input id="address-mobile" type="text" name="text-frast" class="input-text" value="${usrReceivers.mobileNumber}" />
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt><i class="req">*</i>省份：</dt>
	                <dd>
	                    <select data-class="selectbox-small" name="province" id="">
	                       		<#list provinceList as province>
										<option value="${province.provinceId}" <#if usrReceivers.province == province.provinceName>selected</#if>>${province.provinceName}</option>
								</#list>
					    </select>
	                    <select id="address-city" data-class="selectbox-small" name="city" id="">
	                        <#if cityList??&&cityList?size gt 0>
										<#list cityList as city>
											<option value="${city.cityId}" <#if usrReceivers.city == city.city>selected</#if>>${city.cityName}</option>
										</#list>
							<#else>
								
										  	<option value="${usrReceivers.city}">${usrReceivers.city}</option>										
							</#if>
	                    </select>
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt><i class="req">*</i>收件地址：</dt>
	                <dd>
	                    <input id="address" type="text" class="input-text input-big" name="text-frast" value="${usrReceivers.address}" maxlength="100" />
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt>邮编：</dt>
	                <dd>
	                    <input id="address-postcode" type="text" class="input-text input-mini" name="text-frast" value="${usrReceivers.postCode}"  maxlength="6"/>
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt></dt>
	                <dd>
	                    <button id="frast-submit" class="pbtn pbtn-mini pbtn-blue">保存</button>
	                </dd>
	            </dl>
	        </div>
	      <!--如果存在地址就显示修改状态下的样式 end -->
	     
	     
	      <!--没有地址就隐藏修改时样式 出现填写状态start-->
	        <div id="frast-info" class="form-info <#if usrReceiversList?size <= 0>hide</#if>">
	           	<input type="hidden" name="receiverId" id="frastID" value="${usrReceivers.receiverId}" >
	           	<input type="hidden" name="receiverName" value="${usrReceivers.receiverName}" >
	           	<input type="hidden" name="mobileNumber" value="${usrReceivers.mobileNumber}" >
	           	<input type="hidden" name="province" value="${usrReceivers.province} " >
	           	<input type="hidden" name="city" value="${usrReceivers.city}" >
	           	<input type="hidden" name="address" value="${usrReceivers.address}" >
	           	<input type="hidden" name="postcode" value="${usrReceivers.postCode}" >
	           	
	            <dl class="xdl">
	                <dt><i class="req">*</i>收件人姓名：</dt>
	                <dd>
	                 ${usrReceivers.receiverName}&nbsp;
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt><i class="req">*</i>手机号码：</dt>
	                <dd>
	                  ${usrReceivers.mobileNumber}&nbsp;
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt><i class="req">*</i>省份：</dt>
	                <dd>
	                  ${usrReceivers.province}  ${usrReceivers.city}&nbsp;
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt><i class="req">*</i>收件地址：</dt>
	                <dd>
	                ${usrReceivers.address}&nbsp;
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt>邮编：</dt>
	                <dd>
	                 ${usrReceivers.postCode}
	                </dd>
	            </dl>
	            <dl class="xdl">
	                <dt></dt>
	                <dd>
	                    <button id="frast-btn" class="pbtn pbtn-mini pbtn-light">修改</button>
	                </dd>
	            </dl>
	        </div>
	        <!--没有地址就隐藏修改时样式 出现填写状态start-->
		</div>
		<!--是否需要邮寄地址end-->
 	</div>
</div>
</#if>