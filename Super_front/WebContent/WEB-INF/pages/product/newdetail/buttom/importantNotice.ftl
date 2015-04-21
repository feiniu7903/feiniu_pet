<#import "/WEB-INF/pages/product/newdetail/buttom/view_content_func.ftl" as vcf>
            <i id="row_6" class="pkg-maodian">&nbsp;</i>
            <h3 class="h3_tit"><span>重要提示</span></h3>
            <div class="row important_prompt">
            
                <@vcf.showViewContentTemplate 'ACITONTOKNOW' '行前须知'/> 
                
                <@vcf.showViewContentTemplate 'ORDERTOKNOWN' '预订须知'>
	                <@s.if test="prodCProduct.prodProduct.isTicket()">
					<br><dd>本产品不含旅游人身意外险，我们强烈建议游客购买。游客可在填写订单时勾选附加保险产品或致电1010-6060进行订购 。</dd>
	    			</@s.if>                     
		    		<br><dd>游泳、漂流、潜水等水上运动，均存在危险。参与前请根据自身条件，并充分参考当地海事部门及其它专业机构相关公告及建议后量力而行。</dd>
                    <br><dd>如需开具旅游发票(仅限支付给驴妈妈的订单用户)，请与客服专员确定发票内容与抬头及准确的发票邮寄地址，我司在收到邮寄地址信息后向您寄送发票；为避免因发生不可抗力或意外事项致实际消费额与发票开具的相应数额不符，建议您在游玩归来后两个月内索领取发票。</dd>                
                </@vcf.showViewContentTemplate>
                 
				<@vcf.showViewContentTemplate 'REFUNDSEXPLANATION' '退款说明'/> 
               
                <@vcf.showViewContentTemplate 'SERVICEGUARANTEE' '旅游服务保障'/> 
                
                <@vcf.showViewContentTemplate 'PLAYPOINTOUT' '游玩提示'/> 
            </div><!--important_prompt end-->
