<#-- 
	实现下单流程的导航 
	productType 产品类型
	subProductType 产品子类型
	resourceConfirm 资源是否确认	
	stepView 步骤视图
	payToSupplier 支付给供应商
	fillTravellerInfo 必填写游玩人详细信息
-->
<#macro navigation productType="TICKET" subProductType="" resourceConfirm ="false" eContract="false" stepView="fillView" payToSupplier="false" fillTravellerInfo="false">
		<#if productType="TICKET">
			<#assign local_FTI>${(productType=='ROUTE'||productType=='TRAFFIC')?string('1','0')}</#assign>
		<#else>
			<#assign local_FTI>${(fillTravellerInfo=='true' || productType=='ROUTE'||productType=='TRAFFIC')?string('1','0')}</#assign>
		</#if>
		<#assign local_RC>${(resourceConfirm=='true')?string('1','0')}</#assign>	
		<#assign local_EC>${(eContract=='true')?string('1','0')}</#assign>	
		<#assign local_PTL>${(payToSupplier=='false')?string('1','0')}</#assign>
		<#--导航标识-->
		<#assign nagFlag>${"ord-" + local_FTI  + local_RC + local_EC + local_PTL}</#assign>	
		
		<#--当前步骤视图是导航中的第stepNo步-->
		<#if stepView=="fillView">
			<#assign stepNo>1</#assign>
		<#elseif stepView=="fillRouteTravelView">
			<#assign stepNo>2</#assign>
		<#elseif stepView=="signingView">
			<#assign stepNo>${1 + local_FTI?number + 1}</#assign>
		<#elseif stepView=="checkView">
			<#assign stepNo>${1 + local_FTI?number + local_EC?number + 1}</#assign>
		<#elseif stepView=="payView">
			<#assign stepNo>${1 + local_FTI?number + local_RC?number + local_EC?number + 1}</#assign>
		<#elseif stepView=="completeView">
			<#assign stepNo>${1 + local_FTI?number + local_RC?number + local_EC?number + local_PTL?number + 1}</#assign>	
	    </#if>	    
	    
	    <#--调用样式-->			
	    <div class="${nagFlag} step${stepNo} w-980"></div>	
</#macro>
