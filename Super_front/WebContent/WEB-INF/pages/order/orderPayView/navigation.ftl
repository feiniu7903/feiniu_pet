<#-- 
	实现下单流程的导航 
	productType 产品类型
	subProductType 产品子类型
	resourceConfirm 资源是否确认	
	stepView 步骤视图
	payToSupplier 支付给供应商
-->
<#macro navigation productType="TICKET" subProductType="" resourceConfirm ="false" eContract="false" stepView="fillView" payToSupplier="false">
   		<#assign step_total_tmp>${(payToSupplier=='true')?string('2','3')}</#assign>	
	    <#assign step_total_add>${(eContract=='true')?string('1','0')}</#assign>	
	    <#assign step_total>${step_total_tmp?number+step_total_add?number}</#assign>	
	    
	    <ol class="ui-step ui-step-${step_total}">
		    <li class="ui-step1 ui-step-start <#if stepView=="fill">ui-step-active</#if>">
		        <div class="ui-step-arrow">
		            <i class="arrow_r1"></i>
		            <i class="arrow_r2"></i>
		        </div>
		        <span class="ui-step-text">1.填写订单</span>
		    </li>
		    <#if payToSupplier!='true'>
			    <li class="ui-step2 <#if stepView=="pay">ui-step-active</#if>">
			        <div class="ui-step-arrow">
			            <i class="arrow_r1"></i>
			            <i class="arrow_r2"></i>
			        </div>
			        <span class="ui-step-text">2.选择支付方式支付</span>
			    </li>
		    </#if>
		    <#if eContract=='true'>
		    	<li class="ui-step${step_total-1} <#if stepView=="econtract">ui-step-active</#if>">
			        <div class="ui-step-arrow">
			            <i class="arrow_r1"></i>
			            <i class="arrow_r2"></i>
			        </div>
			        <span class="ui-step-text">${step_total-1}.签署旅游合同</span>
			    </li>
		    </#if>
		    <li class="ui-step${step_total} ui-step-end <#if stepView=="success">ui-step-active</#if>">
		        <div class="ui-step-arrow">
		            <i class="arrow_r1"></i>
		            <i class="arrow_r2"></i>
		        </div>
		        <span class="ui-step-text">${step_total}.完成</span>
		    </li>
		</ol>
</#macro>
