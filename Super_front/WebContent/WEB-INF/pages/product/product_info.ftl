<div class="free_hotel_btitle">
	<h1>重要提示</h1>
	<input type="image" src="/img/free/free_star.jpg" onmouseover="this.src='/img/free/free_hotel_close.png'" onmouseout="this.src='/img/free/free_star.jpg'" class="free_hb_close" id="free_hb_close"/>
</div>
<div class="free_hotel_bcontent">
	<div class="free_hotel_left" style="width:auto;">
		<b>行前须知：</b>
		<p>
		<@s.if test="viewPage!=null&&viewPage.contents.ACITONTOKNOW!=null">
         	<@s.property value="viewPage.contents.ACITONTOKNOW.content" escape="false"/>
        </@s.if>
        </p>
	</div>
	<div class="clear"></div>
	<div class="free_hotel_bottom">
		<b>预订须知：</b>
		<p>
			<@s.if test="viewPage!=null&&viewPage.contents.ORDERTOKNOWN!=null">
	         	<@s.property value="viewPage.contents.ORDERTOKNOWN.content" escape="false"/>
	        </@s.if>
			
		</p>
	</div>
</div>
