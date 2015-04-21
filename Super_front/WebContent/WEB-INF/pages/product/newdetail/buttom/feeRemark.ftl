<#import "/WEB-INF/pages/product/newdetail/buttom/view_content_func.ftl" as vcf>
<i id="row_4" class="pkg-maodian">&nbsp;</i>
<h3 class="h3_tit"><span>费用说明</span></h3>
<div class="row statement_cost">
	<@s.if test="prodCProduct.prodProduct.isTicket()">
		<@vcf.showViewContentTemplate 'COSTCONTAIN' '费用包含' false/>   
    
	   <@vcf.showViewContentTemplate 'NOCOSTCONTAIN' '费用不包含' false/>
	
	   <@vcf.showViewContentTemplate 'RECOMMENDPROJECT' '推荐项目' false/>
	  
	   <@vcf.showViewContentTemplate 'SHOPPINGEXPLAIN' '购物说明' false/>
	</@s.if>
	<@s.else>
		<@s.if test="hasMultiJourney()">
		    <div id="multiFeeDetailDiv"></div>
	   </@s.if>
		<@s.else>
			<@vcf.showViewContentTemplate 'COSTCONTAIN' '费用包含'/>   
		    
		   <@vcf.showViewContentTemplate 'NOCOSTCONTAIN' '费用不包含'/>
		</@s.else>
	   <@vcf.showViewContentTemplate 'RECOMMENDPROJECT' '推荐项目'/>
	  
	   <@vcf.showViewContentTemplate 'SHOPPINGEXPLAIN' '购物说明'/>
    </@s.else>
</div><!--statement_cost end-->
