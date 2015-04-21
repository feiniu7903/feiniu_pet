<#import "/WEB-INF/pages/product/newdetail/buttom/view_content_func.ftl" as vcf>
<!--签证/签注-->
            <i id="row_8" class="pkg-maodian">&nbsp;</i>
			
		<h3 class="h3_tit"><span>签证/签注</span></h3>
		<div class="l_row row" style="margin-bottom: 10px;">
		    <div class="visa_info_xh">
			   <!-- <h5>签证须知</h5>
				<p>1. 如需我司为您代办签证，请在后续页面选择相应的可选项；如您自备签证，请确保签证的有效性，以免耽误行程。</p>
				<p>2. 本产品行程中涉及普吉岛与槟城，所需证件：有效护照＋新加坡2次或多次签证＋泰国签证，中国籍乘客在马来西亚可享受72小时免签过境，非中国籍乘客还需自理马来西亚签证。</p>
                <@vcf.showViewContentTemplate 'VISA' ''/>-->
                    <ul class="visa_nav_a clearfix">
				    <li class="selected">
				        <@s.if test="prodCProduct!=null&&prodCProduct.prodRoute!=null">
				        	${prodCProduct.prodRoute.cnCountry?if_exists}${prodCProduct.prodRoute.cnVisaType?if_exists}
				        	<input type="hidden" id="visa_Email_cnVisaType" value="${prodCProduct.prodRoute.cnCountry?if_exists}${prodCProduct.prodRoute.cnVisaType?if_exists}"/>
				        </@s.if>
				    </li>
					</ul> 
				<div class="visa_content">
					<div class="tabcon" style="display:block">
                        <ul class="visa_subnav_a J_tab_subnav clearfix">
					    <@s.iterator value="visaVOList" status="st">
							<li <@s.if test="#st.isFirst()">class="selected"</@s.if>>${cnOccupation?if_exists}
							<input type="hidden" value="${cnOccupation?if_exists}" id="ssrq_${st.getIndex()}"/>
							<i></i>
							<style>
							.visa_mail{
							   width:16px;
							   height:15px;
							   background:url("http://pic.lvmama.com/img/v3/combo.png") no-repeat -24px -71px;
							   margin-left:5px;
							   display:inline-block;
							}
							</style>
							<a class="visa_mail" href="javascript:;" id="btn_${st.getIndex()}" onclick="visaEmailSend(${st.getIndex()});"></a>
							</br>
							</li>
						 </@s.iterator>
						</ul>
						<div class="J_tab_subswitch">
						 <@s.iterator value="visaVOList" id="id" status="visa">
							<div class="tabcon"  <@s.if test="#visa.isFirst()">style="display:block"</@s.if>>
								<div class="visa_info_box">
									<input type="hidden" value="${documentId?if_exists}" id="visa_Email_documentId_${visa.getIndex()}"/>
									    <@s.iterator value="visaApplicationDocumentDetailsList" status="st2">
												<div class="visa_info_item">
													<b>${title?if_exists}</b>
													<div class="text">${content?if_exists}</div>
												</div>
										</@s.iterator>
								</div>
							</div>
						</@s.iterator>
						</div><!-- //div J_tab_switch -->
					</div>
					
				</div><!-- //div J_tab_switch -->
			</div>
		</div>
		<!--弹出层-->
	    <div id="visaDialog" style="display:none">
		    <span>将签证信息发至信箱：<input type="text" size="30" id="visaEmail" value="" style="background:transparent;border:1px solid #CDC28D;">
		    	
		    </span>
    	</div>
			