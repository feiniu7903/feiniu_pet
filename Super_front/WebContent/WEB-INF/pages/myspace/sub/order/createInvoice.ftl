<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的订单-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<link href="/style/invoice.css" rel="stylesheet" />	
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-order">
	<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
	<div class="lv-nav wrap">
		<p><a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; 
		<a href="http://www.lvmama.com/myspace/order.do">我的订单</a>&gt;
		<a class="current">开具发票</a>
		</p>
	</div>
	<div class="wrap ui-content lv-bd">
		<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
		<div class="lv-content">
			<div class="ui-box mod-plist">
				<div class="ui-box-title">
			        <h3>开具发票</h3>
			    </div>
			    <div class="invoice_wrap">
    <h4 class="invoice_detail_title"><strong>发票明细</strong></h4>
    <dl class="invoice_detail_tab">
        <dt class="invoice_detail1">订单号</dt>
        <dt class="invoice_detail2">订单金额</dt>
        <dt class="invoice_detail3">订单实付金额</dt>
        <dt class="invoice_detail4"></dt>
        <dt class="invoice_detail5">保险</dt>
        <dt class="invoice_detail6"></dt>
        <dt class="invoice_detail7">充值卡支付金额</dt>
        <dt class="invoice_detail8"></dt>
        <dt class="invoice_detail9">奖金</dt>
        <dt class="invoice_detail10"></dt>
        <dt class="invoice_detail11">退款</dt>
    </dl>
    <#list orderList as order> 
        <dl class="invoice_detail_tab">
        <dd class="invoice_detail1">${order.orderId}</dd>
        <dd class="invoice_detail2">${order.orderPayFloat}</dd>
        <dd class="invoice_detail3">${order.actualPayFloat}</dd>
        <dd class="invoice_detail4">—</dd>
        <dd class="invoice_detail5">${order.insuranceTotalAmount}</dd>
        <dd class="invoice_detail6">—</dd>
        <dd class="invoice_detail7">${order.sumCardAmountYuan}</dd>
        <dd class="invoice_detail8">—</dd>
        <dd class="invoice_detail9">${order.bonusPaidAmountYuan}</dd>
        <dd class="invoice_detail10">—</dd>
        <dd class="invoice_detail11">${order.refundedAmountYuan}</dd>
    </dl>	
    </#list>
    
    <div class="invoice_price_result">
        可开发票金额：<span class="invoice_price_num">${totalAmount}</span>元
    </div>
    <form  method="post" id="invoice_form" action="${base}/myspace/saveInvoicInfo.do">
    	<@s.token></@s.token>
    	 <input type="hidden" name="orderIds" value="${orderIds}">
        <div class="invoice_price_note">
            <p class="invoice_note_word">注：保险金额已开具保险公司定额发票，将随我方发票一同快递； 我方发票开票主体为上海驴妈妈兴旅国际旅行社有限公司。</p>
        </div>
        <div class="cf"></div>
        <h3 class="invoice_head">发票信息填写（最多可开${invoiceNum}张发票）</h3>
		<div class="invoice_num1 invoice_max_num">${invoiceNum}</div>
        <div class="invoice_num_content" <#if invoiceNum<2 >style='display:none'</#if>>您需要开具
            <input type="text" class="invoice_num input-text" value="1" <#if invoiceNum<2>readonly</#if> >张发票
                <a class="ui-btn ui-btn4 invoice_num_btn" <#if invoiceNum<2 >style='display:none'</#if>>
                    <i> &nbsp;添加&nbsp;</i>
                </a>
        </div>
        <dl class="invoice_content">
            <dd class="invoice_one_content">
                <div class="invoice_content_row">
                    发票项目：<select class="lv-select invoice_product" name="invoiceList[0].detail">
                  <#list codeItems as item>
                  	<option value="${item.code}">${item.name}</option>
           		  </#list>
            </select>
                </div>
                <div class="invoice_content_row">
                    发票金额：<input name="invoiceList[0].amountYuan" type="text" value="${totalAmount}" <#if invoiceNum=="1" >readonly</#if> class="invoice_amount input-text invoice_fun" id="invoice_money0">
                    <span id="invoice_money0Tip"></span>
                </div>
                <div class="invoice_content_row">
                    发票抬头：<input name="invoiceList[0].title" type="text" class="invoice_title input-text" id="invoice_title0">
                    <span id="invoice_title0Tip"></span>
                    <p class="invoice_title_note">(填写个人姓名或公司全称)</p>
                </div>
                <div class="invoice_num1">1</div>
            </dd>
        </dl>
        
        <h3 class="invoice_head">配送地址</h3>

        <div class="invoice_deliver_address">
        <#list usrReceiversList as usrDeliver>
            <div class="invoice_deliver_row">
                <input type="radio" value="${usrDeliver.receiverId}" name="usrReceivers.receiverId" class="invoice_deliver" id="invoice_address1" <#if usrDeliver_index==0>checked</#if> >
                <label for="invoice_address1" class="invoice_default_address">${usrDeliver.receiverName} (${usrDeliver.mobileNumber}) ${usrDeliver.address}</label>
            </div>
        </#list>
            <div class="invoice_deliver_row">
                <input type="radio" value="0" name="usrReceivers.receiverId" class="invoice_deliver" id="invoice_address2">
                <label for="invoice_address2" class="invoice_other_address other_address_active">使用其他地址
                    <i class="invoice_address_tag"></i>
                </label>
                <dl class="invoice_address_new" style="display: none;">
                    <dd class="invoice_new_row">&nbsp;&nbsp;&nbsp;收货人：
                        <input name="usrReceivers.receiverName" type="text" class="input-text invoice_consignee" id="invoice_consignee">
                        <span id="invoice_consigneeTip"></span>
                    </dd>
                    <dd class="invoice_new_row">手机号码：
                        <input type="text" name="usrReceivers.mobileNumber" class="input-text invoice_tel" id="invoice_tel">
                        <span id="invoice_telTip"></span>
                    </dd>
                    <dd class="invoice_new_row">详细地址：
                        <input type="text" name="usrReceivers.address" class="input-text invoice_detail_address" id="invoice_detail_address">
                        <span id="invoice_detail_addressTip"></span>
                    </dd>
                </dl>
            </div>
        </div>
        <p>
            <a class="ui-btn ui-btn5 invoice_submit_btn">
                <i> 提交 </i>
            </a>
        </p>
    </form>
</div>

<div class="invoiceContentModel" style="display:none" >
<dd class="invoice_one_content">
     <div class="invoice_content_row">
                    发票项目：<select class="lv-select invoice_product" name="invoiceList[_num_].detail">
                  <#list codeItems as item>
                  	<option value="${item.code}">${item.name}</option>
           		  </#list>
            </select>
      </div>
      <div class="invoice_content_row">
                    发票金额：<input name="invoiceList[_num_].amountYuan" type="text" class="invoice_amount input-text invoice_fun" id="invoice_money_num_">
            <span id="invoice_money_num_Tip"></span>
      </div>
      <div class="invoice_content_row">
                    发票抬头：<input name="invoiceList[_num_].title" type="text" class="invoice_title input-text" id="invoice_title_num_">
      <span id="invoice_title_num_Tip"></span>
	 <p class="invoice_title_note">(填写个人姓名或公司全称)</p>
     </div>
     <div class="invoice_num1">_index_</div>
</dd>
</div>
<style>
    .invoice_popup_content{
        min-height: 150px;
    }
    .invoice_popup_tab{
        margin-top: 10px;
        margin-bottom: 10px;
    }
    .invoice_popup_tab th{
        background-color: #e4e4e4;
    }
    .invoice_popup_tab th, .invoice_popup_tab td{
        border: 1px solid #d8d8d8;
        text-align: center;
    }

</style>
<link rel="stylesheet" href="/style/popup.css"/>
<script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="/js/myspace/iflight_popup.js"></script>
<script>
    $(function () {
        $('.invoice_num_btn').bind('click',function(){
            var num = Number($('.invoice_num').val());
            var maxNum =Number($.trim($('.invoice_max_num').text()));
            if(num>maxNum){
            	$('.invoice_num').val(maxNum);
            	num = maxNum;
            }
            if(num<1) num = 1;
            $('.invoice_content').find('.invoice_one_content').not(':eq(0)').remove();
            for (var i = 1; i < num; i++) {
            	addInvoice(i);
            }
            if(num>1){
            	$('#invoice_money'+(num-1)).attr("readonly",true);
            }
        })

		String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
		    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
		        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
		    } else {  
		        return this.replace(reallyDo, replaceWith);  
		    }  
		}  

        function addInvoice(invoice_num) {
            var addHtml = $(".invoiceContentModel").html();
            addHtml = addHtml.replaceAll("_num_",invoice_num);
            addHtml = addHtml.replaceAll("_index_",invoice_num+1);      
            $('.invoice_content').append(addHtml);

            //验证
            $('#invoice_money' + invoice_num).bind('blur', function () {
                checkInput('invoice_money' + invoice_num, 'num', '请输入正确的金额');
                if(!reSetEndMoneyAndCheck()){
            	$('#invoice_money' + invoice_num + 'Tip').html('<span class="tips-error"><span class="tips-ico02"></span>请输入正确的金额</span>');
            }
            });
            $('#invoice_title' + invoice_num).bind('blur', function () {
                checkInput('invoice_title' + invoice_num, 'content', '请输入发票抬头');
            });

            //当前面的几个发票金额发生变化时，最后一个发票金额会跟着变化
            $('.invoice_fun').bind('blur',function(){
                var totalMoney =returnMoney = Number($('.invoice_price_num').text().replace(',',''));
                var len = $('.invoice_fun').length;
                console.log(len);
                for(var i=1; i<=len; i++){
                    console.log(Number($('#invoice_money'+i).val()));
                    returnMoney -= Number($('#invoice_money'+i).val())
                }
                var ii = len+1;
                $('#invoice_money'+ii).val(returnMoney);
            })
        }
		function reSetEndMoneyAndCheck(){
			var n=0;
			var totalMoney  = Number($('.invoice_price_num').text().replace(',',''));
			var endId= 'invoice_money'+(Number($('.invoice_num').val())-1);
			$(".invoice_content").find('.invoice_one_content').each(function(){
        		var obj = $(this);
        		if(obj.find('.invoice_amount').attr("id")!=endId){
        			n = n+ Number(obj.find('.invoice_amount').val());
        		}
        		
        	});
        	var m  = totalMoney-n;
        	m = m<0?0:m;
        	m = Number(m.toFixed(2));
        	$("#"+endId).val(m);
        	if(m==0){
        		return false;
        	}
        	return true;
		}
		function isShowNewAddress(){
			if($('.invoice_deliver_row').length<2 || $('#invoice_address2').is(':checked')){
				$('#invoice_address2').attr("checked",'checked');
				showNewAddress();
			}
		}
		
		isShowNewAddress();

        //使用替他地址；
        $('.invoice_deliver').on('click', function () {
            showNewAddress();
        })
        function showNewAddress() {
            if ($('#invoice_address2').is(':checked')) {
                $('.invoice_address_new').slideDown();
                $('.invoice_other_address').removeClass('other_address_active');
            } else {
                $('.invoice_address_new').slideUp();
                $('.invoice_other_address').addClass('other_address_active');
                $('#invoice_consigneeTip').html("");
                $('#invoice_telTip').html("");
                $('#invoice_detail_addressTip').html("");
            }
        }

        //   showNewAddress()

        function checkInput(ID, type, text) {
            $input = $('#' + ID);
            var inputVal = $('#' + ID).val().replace(/\s+/g, '');
            var inputTip = $('#' + ID + 'Tip');
            switch (type) {
                case 'content':
                    if (checkContent(inputVal)) {
                        inputTip.html('<span class="tips-ico01"></span>');
                    } else {
                        inputTip.html('<span class="tips-error"><span class="tips-ico02"></span>' + text + '</span>');
                    }
                    break;
                case 'tel':
                    if (checkTel(inputVal)) {
                        inputTip.html('<span class="tips-ico01"></span>');
                    } else {
                        inputTip.html('<span class="tips-error"><span class="tips-ico02"></span>' + text + '</span>');
                    }
                    break;
                case 'num':
                    if (checkNum(Number(inputVal))) {
                    	$('#' + ID).val(Number(inputVal));
                        inputTip.html('<span class="tips-ico01"></span>');
                    } else {
                        inputTip.html('<span class="tips-error"><span class="tips-ico02"></span>' + text + '</span>');
                    }
                    break;
            }
        }

        $('#invoice_consignee').bind('blur', function () {
            checkInput('invoice_consignee', 'content', '请输入收货人');
        });
        $('#invoice_tel').bind('blur', function () {
            checkInput('invoice_tel', 'tel', '请输入正确的手机号');
        })
        $('#invoice_detail_address').bind('blur', function () {
            checkInput('invoice_detail_address', 'content', '请输入详细地址');
        });
        $('#invoice_money0').bind('blur', function () {
            checkInput('invoice_money0', 'num', '请输入正确的金额');
            if(!reSetEndMoneyAndCheck()){
            	$('#invoice_money0Tip').html('<span class="tips-error"><span class="tips-ico02"></span>请输入正确的金额</span>');
            }
        });
        $('#invoice_title0').bind('blur', function () {
            checkInput('invoice_title0', 'content', '请输入发票抬头');
        });

        $('.invoice_submit_btn').bind('click', function () {
            submitSetUser();
        })
 		var _lvmm_pop1A;
        function submitSetUser() {
        	if(valid()){
        		if(init_lvmm_pop1A()){
	        		_lvmm_pop1A.common_showPop();
	        		$('.yellow_common_btn').bind('click', function () {
			            $('#invoice_form').submit();
			        })
		        }
        	}
        }
        
        function valid(){
        	var totalMoney =returnMoney = Number($('.invoice_price_num').text().replace(',',''));
        	var num = 0;
        	$(".invoice_content").find('.invoice_one_content').each(function(){
        		obj = $(this);
        		checkInput(obj.find('.invoice_amount').attr("id"), 'num', '请输入正确的金额');
		       	checkInput(obj.find('.invoice_title').attr("id"), 'content', '请输入发票抬头');
		       	num+=Number($.trim(obj.find('.invoice_amount').val()));
        	});
        	if($('#invoice_address2').is(':checked')){
        		 checkInput('invoice_consignee', 'content', '请输入收货人');
        		 checkInput('invoice_tel', 'tel', '请输入正确的手机号');
        		 checkInput('invoice_detail_address', 'content', '请输入详细地址');
        	}
        	if(num!=totalMoney){
        		return false;
        	}
        	if ($('.tips-error').length > 0) {
                $('.tips-error').eq(0).parent().siblings('input').focus();
                return false;
            }
            return true; 
        }

        function checkContent(content) {
            if (content != null && $.trim(content).length > 0) {
                return true;
            } else {
                return false;
            }
        }

        function checkTel(tel) {
            var mobile = /^(13|15|18)[0-9]{9}$/;				//手机
            if (mobile.test(tel)) {
                return true;
            } else {
                return false;
            }
        }

        function checkNum(float) {
        	if(float<=0) return false;
           var num =  /^[+]?\d*\.?\d{1,2}$/;
            if (num.test(float)) {
                return true;
            } else {
                return false;
            }
        }
	   function init_lvmm_pop1A(){
	   var n=1;
	   var _constrA = '<div class="invoice_popup_content">\
					    <h3>请再次确认以下您的发票信息，提交后将不能修改！</h3>\
					    <table class="invoice_popup_tab">\
					        <tr>\
					            <th style="width: 60px;">序号</th>\
					            <th style="width: 80px;">发票金额</th>\
					            <th style="width: 80px;">发票项目</th>\
					            <th style="width: 335px;">发票抬头</th>\
					        </tr>';
			$(".invoice_content").find('.invoice_one_content').each(function(){
        		obj = $(this);
        	_constrA += '<tr>\
					            <td>'+ n +'</td>\
					            <td>' + obj.find('.invoice_amount').val() + '</td>\
					            <td>' + obj.find('.invoice_product').find("option:selected").text() + '</td>\
					            <td>' + obj.find('.invoice_title').val() + '</td>\
					        </tr>';
				n++;
        	});		        
					        
			_constrA +='</table><p>';
			if($('#invoice_address2').is(':checked')){
				_constrA +='<span>'+$('.invoice_consignee').val()+' ('+ $('.invoice_tel').val() +')' + $('.invoice_detail_address').val() + '</span>';
        		
        	}else{
        		_constrA +='<span>' + $("input[type='radio']:checked").siblings().html() + '</span>';
        	}
			_constrA +='</div>';
			
			_lvmm_pop1A=new Lvmm_pop({
            popW:600,
            popTop:50,
            popWarpClass:"common_pop", // pop box
            popStyleClass:"grey_common_pop", // yellow_common_pop
            popClose:"common_pop_close", // close
            pop_tit:"发票确认信息",
            pop_str: _constrA,
            pop_btn:true, // false
            popBtnSure:"common_btn yellow_common_btn", // sure yellow_common_btn
            popCover:"popCover",// layerover
            popBtnHtml:"<p class='test_flight'>\
	  				     <span class=' common_opt_closeBtn common_btn yellow_common_btn'>确认无误，提交</span>\
						 <a class='common_opt_closeBtn' href ='javascript:void(0);'>返回修改</a>\
	  			    </p>"
        });
			return true;
	   }

    })
</script>
			</div>
		</div><!-- //div .lv-content-->
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
		cmCreatePageviewTag("添加发票信息", "D1003", null, null);
	</script>
</body>
</html>