<!-- 预订须知 -->
<#if reserveNoticeMap?? && reserveNoticeMap.size() gt 0>
<div class="hr_a"></div>
<div class="order-title">
    <h3>预订须知</h3>
</div>
<div class="order-content xdl-hor">
    <div class="hr_a"></div>
    <div class="booking-policy">
      	<#if reserveNoticeMap['feiyong']?? && reserveNoticeMap['feiyong'] != ''>
	        <dl class="xdl">
	            <dt class="B">费用说明：</dt>
	            <dd>${reserveNoticeMap['feiyong']}</dd>
	        </dl>
	        <div class="dot_line"></div>
      	</#if>
		<#if reserveNoticeMap['yuding']?? && reserveNoticeMap['yuding'] != ''>
	        <dl class="xdl">
	            <dt class="B">预订须知：</dt>
	            <dd>${reserveNoticeMap['yuding']}</dd>
	        </dl>
	        <div class="dot_line"></div>
		</#if>      	
		<#if reserveNoticeMap['tuikuan']?? && reserveNoticeMap['tuikuan'] != ''>
        <dl class="xdl">
            <dt class="B">退款说明：</dt>
            <dd>${reserveNoticeMap['tuikuan']}</dd>
        </dl>
        <div class="dot_line"></div>
        </#if>
    </div>
</div> <!-- //预订须知 -->
</#if>