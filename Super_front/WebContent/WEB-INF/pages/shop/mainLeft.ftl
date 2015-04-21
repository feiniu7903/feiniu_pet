<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/points/integral_store.css">

	<div class="aside fl">
	    <!-- 我的积分 -->
		<#if shopUser??>
			<div class="side-box border sidebox1">
				<div class="stitle">
					<h4>我的积分</h4>
				</div>
				<div class="content">
					 <p>您好！<@s.property value="shopUser.userName"/><br>
                		<span class="gray">可用积分余额：<dfn><i><@s.property value="shopUser.point"/></i>分</dfn></span>
                	  </p>
					  <p class="line">
					  	<a href="http://www.lvmama.com/myspace/account/points.do" target="_blank">查看积分明细</a>
              			<a href="http://www.lvmama.com/myspace/account/points_order.do" target="_blank">兑换记录</a>
		             </p>
				</div>
			</div>
		<#else>
			<div class="side-box border sidebox1">
				<div class="stitle">
					<h4>积分商城</h4>
				</div>
				<div class="content">
				    <p class="tc">
			    		<button class="btn btn-pink" onclick="userLogin();" >&nbsp;&nbsp登录&nbsp;&nbsp;</button> 
			    		<button class="btn" onclick="window.open('http://login.lvmama.com/nsso/register/registering.do','_blank');">免费注册</button>
				    </p>
					<p class="line gray">登录后，可查看您的积分余额，用积分兑换商品、参与抽奖</p>
				</div>
			</div> 
		</#if>
		<!-- 我的积分  end-->
		<!-- 热门兑换排行 -->
		<div class="side-box border sidebox3">
            <div class="side-title">
                <h4>热门兑换排行</h4>
            </div>
            <div class="content JS_hover_select">
            	<@s.iterator value="topProductList" var="topProduct" status="sts">
            		<@s.if test="#sts.index==0"><dl class="selected"></@s.if>
		          	<@s.else><dl class=""></@s.else>
					<dt><@s.property value="#sts.index+1"/></dt>
					<dd>
                        <h6>
                        	<a href="/points/prod/${topProduct.productId}" title="${topProduct.productName}" >
                        		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,13)"/>
                        	</a>
                        </h6>
                        <p>所需积分：<dfn><i>${topProduct.pointChange}</i>分</dfn></p>
                        <a href="/points/prod/${topProduct.productId}"  class="img" title="${topProduct.productName}" >
                        	<img src="${topProduct.getFirstAbsolutePictureUrl()}" title="${topProduct.productName}" />
                        </a>
                    </dd>
					</dl>
            	</@s.iterator>
            </div>
        </div>
		<!-- 热门兑换排行 end -->	
	</div>	
    
<script type="text/javascript" >
	function userLogin(){
		$(UI).ui("login");
	}
</script>
