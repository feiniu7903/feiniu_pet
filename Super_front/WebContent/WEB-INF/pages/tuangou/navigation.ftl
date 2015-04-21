<#macro navigation stepView="1">
	    <ol class="ui-step ui-step-4">
		    <li class="ui-step1 ui-step-start <#if stepView=="1">ui-step-active</#if>">
		        <div class="ui-step-arrow">
		            <i class="arrow_r1"></i>
		            <i class="arrow_r2"></i>
		        </div>
		        <span class="ui-step-text">1.填写券码</span>
		    </li>
		    <li class="ui-step2 <#if stepView=="2">ui-step-active</#if>">
		        <div class="ui-step-arrow">
		            <i class="arrow_r1"></i>
		            <i class="arrow_r2"></i>
		        </div>
		        <span class="ui-step-text">2.选择出游日期</span>
		    </li>
	    	<li class="ui-step3 <#if stepView=="3">ui-step-active</#if>">
		        <div class="ui-step-arrow">
		            <i class="arrow_r1"></i>
		            <i class="arrow_r2"></i>
		        </div>
		        <span class="ui-step-text">3.填写订单</span>
		    </li>
		    <#if stepView = "4">
			    <li class="ui-step4 ui-step-end ui-step-active">
			        <div class="ui-step-arrow">
			            <i class="arrow_r1"></i>
			            <i class="arrow_r2"></i>
			        </div>
			        <span class="ui-step-text">4.预约成功</span>
			    </li>
			<#elseif stepView = "5">
				<li class="ui-step4 ui-step-end ui-step-active">
			        <div class="ui-step-arrow">
			            <i class="arrow_r1"></i>
			            <i class="arrow_r2"></i>
			        </div>
			        <span class="ui-step-text">4.预约失败</span>
			    </li>
		    <#elseif stepView != "5" && stepView != "4">
				<li class="ui-step4 ui-step-end">
			        <div class="ui-step-arrow">
			            <i class="arrow_r1"></i>
			            <i class="arrow_r2"></i>
			        </div>
			        <span class="ui-step-text">4.预约成功</span>
			    </li>
		    </#if>
		</ol>
</#macro>