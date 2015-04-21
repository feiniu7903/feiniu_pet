<script type="text/javascript" src="/js/member/selectOrder.js"></script>
<script type="text/javascript" src="/js/myspace/iflight_popup.js"></script>
<link href="/style/invoice.css" rel="stylesheet" />
<link href="/style/popup.css" rel="stylesheet" />
<table data-spm="6" class="bought-table">
	<colgroup>
        <col class="product-name">
        <col class="price">
        <col class="order-status">
        <col class="deal-status">
        <col class="operate">
        <col class="other">
    </colgroup>
    <thead>
        <tr class="col-name">
            <th class="product-name">产品名称</th>
            <th class="price">金额(元)</th>
            <th class="order-status">订单状态</th>
            <th class="deal-status">合同状态</th>
            <th class="remark">操作</th>
            <th class="other">其它操作</th>
        </tr>
        <tr class="sep-row">
            <td colspan="6"></td>
        </tr>
    	<tr class="toolbar skin-gray">
            <td colspan="6"><p class="fl"><label><input type="checkbox" class="all-selector" id="mergePayCheckBox"> 全选</label>
                <a href="javascript:mergePay();" class="ui-btn ui-button order_submit_btn order_combine_pay">
                    <i> 合并付款 </i>
                </a>
                <span class="ui-btn ui-disbtn"> <i>合并开票</i> </span>                
                <a class="ui-btn ui-button order_submit_btn order_combine_ticket" style="display:none">
                    <i> 合并开票 </i>
                </a>
            </td>
        </tr>
        
    </thead>
    <#if pageConfig.items?has_content>
		<#list pageConfig.items as objUserOrder>
			<#if objUserOrder.order?has_content>
				<#assign obj=objUserOrder.order/>
				
				<#assign statusNameStr=(obj.zhOrderViewStatus)?default("no")/>
	        	<#if obj.isPayToLvmama() && !obj.isPaymentSucc() && obj.isApprovePass() && obj.isExpireToPay()>
					<#assign statusNameStr="取消"/>
			    </#if>
			    
				<#assign statusStyleStr="done"/>
				<#assign tdStyleStr="success-order"/>
			    <#if (obj.canToPay || obj.canToPrePay) && !obj.expireToPay>
					<#assign statusStyleStr="wait"/>
					<#assign tdStyleStr=""/>
			    </#if>
				<tbody class="xcard combo-order ${tdStyleStr?if_exists}">
		            <tr class="sep-row">
		                <td colspan="6"></td>
		            </tr>
		            <tr class="order-hd">
		                <td colspan="6"><span class="no">
		                	<label><input value="${obj.orderId?if_exists}" type="checkbox" style="vertical-align: middle;" name="orderMergePayCheckBox" ordType="${obj.orderType}" canPay="${obj.canMergePay()}" canInvoice="${obj.canCreatInvoice}" <#if !obj.canMergePay() && obj.canCreatInvoice=='false' >disabled="disabled"</#if>/></label>
		                	
		                    <label>订单号：<span class="order-num">${obj.orderId?if_exists}</span> </label>
		                    </span> <span class="deal-time">下单时间：<#if obj.createTime?exists>${obj.createTime?string("yyyy-MM-dd HH:mm")}</#if></span>
		                    <span class="payment">支付方式：<#if obj.paymentTarget?default("")=="TOLVMAMA">在线支付</#if><#if obj.paymentTarget?default("")=="TOSUPPLIER">景区支付</#if></span>
		                </td>
		            </tr>
		            <tr class="order-bd last">
		                <td colspan="1" class="product-name"><span class="desc">
			                <#if obj.selfPack=='true' >
			                	<#if false && obj.paymentChannel?default("")=="BAIDU_PAY">
			                	<a href="javascript:alert('百度大促活动请前往相关页面查看。');" class="product-name-name">
						            ${obj.mainProduct.productName?if_exists}
						        </a><br/>
			                	<#else>
			                	<a href="/product/${obj.mainProduct.productId?if_exists}" target="_blank" class="product-name-name">
						            ${obj.mainProduct.productName?if_exists}
						        </a><br/>
			                	</#if>
						    <#else>
							    <#list obj.ordOrderItemProds as itemObj>
							    	<!-- 如果单独下保险，则显示保险名称 -->
							    	<#if obj.mainProduct.productType=='OTHER' && obj.mainProduct.subProductType=='INSURANCE'>
							    		${obj.mainProduct.productName?if_exists}<br/>
							    	<#else>
							    		<#if itemObj.additional?default("false")=="false">
								            <#if itemObj.wrapPage?default("false")=="true">
								            	<#if false && obj.paymentChannel?default("")=="BAIDU_PAY">
										        <a href="javascript:alert('百度大促活动请前往相关页面查看。');" target="_blank">
							                	<#else>
								            	<a href="/product/${itemObj.productId?if_exists}" target="_blank">
							                	</#if>
								            </#if>
								            <#if itemObj.productName?length gt 33>
								            	${itemObj.productName?substring(0,32)}...<br/>
								            <#else>
								            	${itemObj.productName?if_exists}<br/>
								            </#if>
								            <#if itemObj.wrapPage?default("false")=="true"></a></#if>
							         	</#if>
							    	</#if>
							    </#list>
						    </#if>
		                    </span>
		                </td>
		                <td class="price">${obj.oughtPayYuan?if_exists}</td>
		                <td rowspan="1" class="order-status">
		                	<span class="status ${statusStyleStr?if_exists}">${statusNameStr?if_exists}</span>
		                	<a target="_blank" class="status detail-link" href="${base}/myspace/order_detail.do?orderId=${obj.orderId?if_exists}">订单详情</a>
		                </td>
		                <td class="deal-status" rowspan="1">
		                	<#if obj.isNeedEContract()>
						    	<#if obj.isPayToLvmama() && !obj.isPaymentSucc() && obj.isApprovePass() && obj.isExpireToPay()>已作废
						    	<#elseif obj.isCanceled()>已作废
						    	<#elseif !obj.isEContractConfirmed()>未签约
								<#elseif obj.isEContractConfirmed() && (!obj.isPaymentSucc() || (obj.isNeedResourceConfirm() && !obj.isApprovePass()))>已签约未生效
								<#elseif obj.isPaymentSucc()>已生效
								</#if>
							</#if>
		                </td>
		                <td rowspan="1" class="operate">
			                <#if !obj.isEContractConfirmed() && !obj.isCanceled() && obj.isNeedEContract()>
					       		<a href="${base}/view/view.do?orderId=${obj.orderId?if_exists}" class="ui-btn ui-btn4"><i>&nbsp;在线签约&nbsp;</i></a><br class="clear"/> 
					       	<#else>
						        <#if !obj.expireToPay>  
								    <#if obj.isCanToPay() || obj.isCanToPrePay()>
								       <!--<#if obj.mainProduct.productType?? && obj.mainProduct.productType == 'TICKET'>
									   		<a href="${base}/pay/ticket-${obj.mainProduct.productId}-${obj.orderId?if_exists}"  class="ui-btn ui-btn4"><i>&nbsp;立即支付&nbsp;</i></a><br class="clear"/>	
									   	<#else>	
									   		<a href="${base}/view/view.do?orderId=${obj.orderId?if_exists}"  class="ui-btn ui-btn4"><i>&nbsp;立即支付&nbsp;</i></a><br class="clear"/> 
									   </#if> -->
									   		<#if false && obj.paymentChannel?default("")=="BAIDU_PAY">
									   		<a href="javascript:alert('百度大促活动订单仅限活动页支付，请前往相关页面。');"  class="ui-btn ui-btn4"><i>&nbsp;立即支付&nbsp;</i></a>
									   		<#else>
									   		<a href="${base}/view/view.do?orderId=${obj.orderId?if_exists}"  class="ui-btn ui-btn4"><i>&nbsp;立即支付&nbsp;</i></a>
									   		</#if>
									   		<br class="clear"/> 
								    </#if> 
								 </#if> 
					         </#if>
							<#if obj.orderStatus?default("")=="NORMAL">
								<#if obj.paymentStatus?default("")=="UNPAY" && !obj.expireToPay && obj.performStatus!="PERFORMED" && obj.paymentToSupplierCancelAble>
									<a href="javascript:void(0);" onClick="toCancel(${objUserOrder.userOrderId?if_exists});"  class="cancel-order">取消订单</a>
									<br class="clear"/>
								</#if>
							</#if>
							<#if obj.isNeedEContract() && obj.isEContractConfirmed() && !obj.isCanceled()>
								<a href="/ord/downPdfContractDetail.do?orderId=${obj.orderId?if_exists}"  class="ui-btn ui-btn2"><i>下载电子合同</i></a>
								<br class="clear"/>
							</#if> 
							<#if obj.shouldSendCert>
								<#if (obj.travellerList?size == 0)>
									<a href="javascript:void(0);" onclick="sendSms(this,'${obj.orderId?if_exists}','${obj.contact.mobile?if_exists}');" name="sent_mms" class="ui-btn ui-btn2"><i>重发短信凭证</i></a>
									<br class="clear"/>
								<#else>
									<a href="javascript:void(0);" onclick="sendSms(this,'${obj.orderId?if_exists}','${obj.travellerList.get(0).mobile?if_exists}');" name="sent_mms"  class="ui-btn ui-btn2"><i>重发短信凭证</i></a>
									<br class="clear"/>
								</#if>
							</#if>
							<!--
							<#if obj.orderStatus?default("")=="NORMAL" && obj.paymentStatus?default("")=="PAYED" &&  obj.isHasLastCancelTime()>
								<a href="javascript:void(0);" class="add_hover" title="${obj.lastCancelStr?if_exists}">取消订单</a>
							 </#if>
							 -->
		                </td>
		                <td rowspan="1" class="other">
							<#if !obj.isCanceled() && "ROUTE"==obj.mainProduct.productType && obj.isNeedEContract() && obj.isEContractConfirmed() >
								<a href="/ord/downloadTravel.do?productId=${obj.mainProduct.productId?if_exists}&orderId=${obj.orderId?if_exists}&productName=${obj.mainProduct.productName?if_exists}" target="_blank" rel="nofollow">下载行程</a>
								<br class="clear" />
							</#if>
							<#if obj.mainProduct.productType?if_exists=="ROUTE"&& obj.paymentStatus?default("")=="PAYED">
						 	     <#if obj.orderRoute.groupWordStatus?if_exists=="SENT_NO_NOTICE"||obj.orderRoute.groupWordStatus?if_exists=="MODIFY_NO_NOTICE"||obj.orderRoute.groupWordStatus?default("")=="SENT_NOTICE" || obj.orderRoute.groupWordStatus?default("")=="MODIFY_NOTICE">
						         	<a href="${base}/groupAdviceNoteDownload/order.do?objectId=${obj.orderId?if_exists}&objectType=ORD_ORDER">下载出团通知书</a>
					 	         </#if>
					        </#if>
					        <#list policyList as policy>
					        	<#if policy.orderId == obj.orderId>
					        		<a href="${base}/myspace/order/downloadpolicy.do?objectId=${policy.policyId?if_exists}&orderId=${policy.orderId?if_exists}">下载投保单</a>
					        	</#if>
					        </#list>
	
					        <#if obj.canCreatInvoice=='true'><a href="${base}/myspace/addInvoicInfo.do?orderIds=${obj.orderId?if_exists}">开具发票</a>
					        <#elseif obj.invoiceList!=null><a href="${base}/myspace/order_detail.do?orderId=${obj.orderId?if_exists}#invoiceMaodian">查看发票</a>				        
					        </#if>
					        <#if obj.iscanComment?default("")=="true">
                                <#if obj.paymentTarget?default("")=="TOLVMAMA">
						         <a href="http://www.lvmama.com/comment/writeComment/fillComment.do?productId=${obj.mainProduct.productId}&amp;orderId=${obj.orderId}&amp;productType=${obj.mainProduct.productType}" class="ui-btn ui-btn2"><i>立即点评</i></a><p>返<dfn class="B">¥<i class="B">${obj.getCashRefundYuan()}</i></dfn>奖金</p>
                                </#if>
                                <#if obj.paymentTarget?default("")=="TOSUPPLIER">
                                     <a href="http://www.lvmama.com/comment/writeComment/fillComment.do?productId=${obj.mainProduct.productId}&amp;orderId=${obj.orderId}&amp;productType=${obj.mainProduct.productType}" class="ui-btn ui-btn2"><i>立即点评</i></a>
                                </#if>
							    <br class="clear"/>
							</#if> 
							<!--
							<#if obj.orderViewStatus == "FINISHED">
								<a href="/comment/writeComment/fillComment.do?productId=${obj.mainProduct.productId}&orderId=${obj.orderId}">
									<span class="lvapp-icon icon-apps16-1013"></span><i class="ie6_blank"></i>点评
								</a>
							</#if>
							-->
		                </td>
		                
		            </tr>
		        </tbody>
			<#elseif objUserOrder.vstOrder?has_content>
				<#assign obj=objUserOrder.vstOrder/>
				
				<#assign statusNameStr=(obj.zhOrderViewStatus)?default("no")/>
				<#assign statusStyleStr="done"/>
				<#assign tdStyleStr="success-order"/>
		
			    <#if (obj.waitingPay)??>
					<#assign statusStyleStr="wait"/>
					<#assign tdStyleStr=""/>
			    </#if>
			    
			    <tbody class="xcard combo-order ${tdStyleStr?if_exists}">
		            <tr class="sep-row">
		                <td colspan="6"></td>
		            </tr>
		            <tr class="order-hd">
		                <td colspan="6"><span class="no">
		                	<label><input value="${obj.orderId?if_exists}" type="checkbox" style="vertical-align: middle;" name="orderMergePayCheckBox" ordType="${(objUserOrder.orderType)!}" 
		                	canPay="${(obj.canMergePay)!"false"}" canInvoice="false" 
		                	<#--
		                	<#if !((obj.canMergePay)??) || !(obj.canMergePay) >disabled="disabled"</#if>
		                	-->
		                	disabled="disabled"
		                	/></label>
		                    <label>订单号：<span class="order-num">${obj.orderId?if_exists}</span> </label>
		                    <span class="deal-time">下单时间：<#if obj.createTime?exists>${obj.createTime?string("yyyy-MM-dd HH:mm")}</#if></span>
		                    <span class="payment">支付方式：<#if (obj.zhPaymentTarget)??>${obj.zhPaymentTarget}</#if></span>
		                </td>
		            </tr>
		            <tr class="order-bd last">
		                <td colspan="1" class="product-name">
		                	<span class="desc">${(obj.productName)!}</span>
		                </td>
		                <td class="price">${obj.oughtPayYuan?if_exists}</td>
		                <td rowspan="1" class="order-status">
		                	<span class="status ${statusStyleStr?if_exists}">${statusNameStr?if_exists}</span>
		                	<a target="_blank" class="status detail-link" href="http://hotels.lvmama.com/order/viewDetail?orderId=${obj.orderId?if_exists}">订单详情</a>
		                </td>
		                <td class="deal-status" rowspan="1">
		                	
		                </td>
		                <td rowspan="1" class="operate">
		                	<#if ((obj.waitingPay)!)==true>
		                		<a href="http://hotels.lvmama.com/order/view.do?orderId=${obj.orderId?if_exists}"  class="ui-btn ui-btn4"><i>&nbsp;立即支付&nbsp;</i></a><br class="clear"/>
		                	</#if>
		                	
		                	<#if ((obj.canCancel)!) == true>
		                		<a href="javascript:void(0);" onClick="toCancel(${objUserOrder.userOrderId?if_exists});"  class="cancel-order">取消订单</a><br class="clear"/>
		                	</#if>
		                </td>
		                <td rowspan="1" class="other">
							
		                </td>
		                
		            </tr>
		        </tbody>
			</#if>
			
		</#list>
	<#else>
		<tbody class="no-list">
            <tr>
                <td colspan="6"><p class="no-list">您近期还没有提交过订单</p></td>
            </tr>
        </tbody>
	</#if>
	<tfoot>
        <tr class="sep-row">
            <td colspan="6"></td>
        </tr>
    </tfoot>
</table>


<!--=====  弹出层 Start ========-->
<div id="float_layer" style="z-index:1000;" class="fl-layer">
    <span><img src="http://pic.lvmama.com/img/myspace/close_dot.gif" alt="关闭" id="close_btn" class="close-btn" /></span>
    <h3>发送短信凭证</h3>
    <div id="resendmsgdiv">
        您的手机号码：<input id="sendmobile" type="text" size="25" readonly="true"/>
        <input id="resendmsg" type="button" onclick="resendmsg()"  value="" class="sm-btn"/>
        <input id="reorderheadId" type="hidden" size="25" />
        <span class="sent-time" id="timer"></span>
    </div>
    <div id="remsgerrordiv" class="fl-bm">若还收不到确认短信，请拨打<em>1010-6060</em>与客服取得联系</div>
</div>
<div id="float_bg" class="fl-bg"></div>



<!--=====  弹出层 End ========-->
<!-- 弹出层 -->
<div class="bg-tan"></div>
<div class="cancel_wapper">
    <input type="hidden" id="orderIdBtn" name="orderId"/>
    <h4>取消订单</h4>
    <a class="cancel_bg"><img src="http://pic.lvmama.com/img/myspace/myspace_fapiao/cancel_bt.jpg" alt="关闭" id="close_btn" /></a>
    <div class="cancel_list">
        <h3>为了更好的改进我们的服务，请选择取消原因：</h3>
        <ul class="cancel_list_s">
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="50">支付遇到问题</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="51">行程变更</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="52">产品定错（时间、房型、数量等）</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="53">重复订单</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="54">在其他网站购买</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="55">价格不够优惠</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="56">想直接去景区/酒店</li>
            <li><input type="radio" id="other" class="input_radio"  name="itemCode" value="57">其他<input name="" id="otherReason" type="text" class="input_01" /></li>
            <li class="sub_bt"><input name="" type="button" value="确认" class="denglv denglv01" onclick="cancel();" /><input name="" type="button" value="取消" class="denglv denglv03" /></li>
        </ul>
    </div>
</div>


<div class="cancel_wapper01">    
    <h4>取消订单</h4>
    <a class="cancel_bg"><img src="http://pic.lvmama.com/img/myspace/myspace_fapiao/cancel_bt.jpg" alt="关闭" id="close_btn" /></a>
    <div class="cancel_list">
     <div class="cancel_ka"><span class="cancel_closed"> 您的订单已取消</span><span style="display: block; margin-top: -2px; margin-left: 20px;">谢谢反馈！</span>
     <div class="tips_list" id="fleshId"></div>
</div>
    </div>
</div>



<script type="text/javascript" language="JavaScript" charset=“utf-8”>
{       
    $('.cancel_bg,.denglv02,.denglv03').click(function(){
                $('.bg-tan,.cancel_wapper,.cancel_wapper01').hide();
      })      
     }

    function toCancel(orderId) {
        $("#orderIdBtn").val(orderId);
        var leftN=$(window).width()/2-162,topN=$(window).height()/2-151;
        $('.bg-tan').show();
        $(".cancel_wapper").fadeIn(10).css({"top":topN+$(window).scrollTop(),"left":leftN});
    }
    
    function cancel() {
        var reasonRadio = $('input:radio[name="itemCode"]:checked');
        var itemCode = reasonRadio.val();
        var orderId = $("#orderIdBtn").val();
        var reason = "";
        if (itemCode=="57") {
            reason = "其他_" + $("#otherReason").val();
        }
        if(itemCode==null){
        	itemCode="30";
        }
        canel(orderId,itemCode,reason);
        
        var leftN=$(window).width()/2-162,topN=$(window).height()/2-151;
        $('.bg-tan').show();
        $('.cancel_wapper').hide();
        $(".cancel_wapper01").fadeIn(10).css({"top":topN+$(window).scrollTop(),"left":leftN});
        
        for(i=0;i<=5;i++) { 
           window.setTimeout("update(" + i + ")", i * 1000);
        } 
        
    }
    
    function update(num) { 
        if(num == 5) {     
           $('.bg-tan,.cancel_wapper,.cancel_wapper01').hide(); 
        }else {
           printnr = 5-num;                
           $('#fleshId').html("页面将在<strong>" + printnr + "</strong>秒内自动刷新") ;
           $('#fleshId').show(); 
        } 
    }
    $("#mergePayCheckBox").click(function (){
    	if($('#mergePayCheckBox').attr("checked")){
    	    $("[name='orderMergePayCheckBox']").each(function(){
    			if($(this).attr("disabled")!="disabled"){
    				$(this).attr("checked",'true');	
    			}
    		})
		}
		else{
			$("[name='orderMergePayCheckBox']").removeAttr("checked");
		}
	});
	function mergePay(){
		var orderIds="";
		var isCanPay = true;
	    $("input[name='orderMergePayCheckBox']:checkbox").each(function(){ 
	        if($(this).attr("checked")){
	        	if($(this).attr("canPay")!="true"){
		    		isCanPay=false;
		    		return ;
	    		}
	        	orderIds += $(this).val()+",";
	        }
	    })
	    if(!isCanPay){
	    	alert("所选订单不符合合并支付的规则!");
	    	return;
	    }
	    if(orderIds!=""){
	    	orderIds=orderIds.substring(0,orderIds.length-1);
	    }
	    else{
	    	alert("没有可以合并支付的订单!");
	    	return ;
	    }
	    window.location.href="${base}/view/viewMergePay.do?orderIds="+orderIds;
	}
	_constrB='<p>抱歉，酒店订单不可与其他订单合并开票</p>';
	function showPop1B(){
		new Lvmm_pop({
        popW:300,
        popTop:50,
        popWarpClass:"common_pop", // pop box
        popStyleClass:"grey_common_pop", // yellow_common_pop
        popClose:"common_pop_close", // close
        pop_tit:"提示",
        pop_str: _constrB,
        pop_btn:true, // false
        popBtnSure:"common_btn yellow_common_btn", // sure yellow_common_btn
        popCover:"popCover",// layerover
        popBtnHtml:"<p class='common_center'>\
	  				     <span class=' common_opt_closeBtn common_btn yellow_common_btn combine_pay_btn'>确定</span>\
	  			    </p>"
    	}).common_showPop();
	}
    
    
</script>
<script type="text/javascript"> 
$(function(){
    $(".add_xu_cont .add_hover").bind("mouseover",function(){
        $(this).parent().css({'position':'relative'});
        $(this).next().show();
    });
      $(".add_xu_cont .add_hover").bind("mouseout",function(){
        $(this).parent().css({'position':'static'});
         $(this).next().hide();
    })
    initInvoice();
    
});

function initInvoice(){
	if($("input[caninvoice='true']").length>1){
		$('.order_combine_ticket').show();
		$(".ui-disbtn").hide();
		$('.order_combine_ticket').bind('click',function(){
			if(validaInvoice()){
				var orderIds="";
			    $("input[name='orderMergePayCheckBox']:checkbox").each(function(){ 
			        if($(this).attr("checked")){
			        	if($(this).attr("canInvoice")!="true"){
				    		isCanPay=false;
				    		return ;
			    		}
			        	orderIds += $(this).val()+",";
			        }
			    })
			    if(orderIds!=""){
			    	orderIds=orderIds.substring(0,orderIds.length-1);
			    }
			    else{
			    	alert("没有可以合并开票的订单!");
			    	return ;
			    }
			   window.location.href="${base}/myspace/addInvoicInfo.do?orderIds="+orderIds;
			}else{
				showPop1B();
			}
        
    	});
	}else{
		$('.order_combine_ticket').hide();
		$(".ui-disbtn").show();
	}
}

function validaInvoice(){
	var orderType = "";
	var isOk=true;
	$("input[name='orderMergePayCheckBox']:checkbox").each(function(){ 
		if(!isOk) return;
	        if($(this).attr("checked")){
	        	if($(this).attr("caninvoice")!="true"){
		    		_constrB='<p>抱歉，所选订单不符合合并开票的规则</p>';
		    		isOk = false;
		    	}
	        	if(orderType=="HOTEL"){
	        		if($.trim($(this).attr("ordType"))!="HOTEL"){
	        			_constrB='<p>抱歉，酒店订单不可与其他订单合并开票</p>';
	        			isOk= false;
	        		}
	        	}else{
	        		if(orderType!=""&&$.trim($(this).attr("ordType"))=="HOTEL"){
	        			_constrB='<p>抱歉，酒店订单不可与其他订单合并开票</p>';
	        			isOk = false;
	        		}
	        	}
	        	if($.trim($(this).attr("ordType"))=="HOTEL"){
	        		orderType = $(this).attr("ordType");
	        	}else{
	        		orderType += $(this).attr("ordType")+",";
	        	}
	        }
	    });
	return isOk;

}
</script>
