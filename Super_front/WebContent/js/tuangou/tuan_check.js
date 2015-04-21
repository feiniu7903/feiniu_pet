//前段验证
window.onload = function(){
	 $(function(){
			pandora.selectModel({selectElement : $('.buy-sel')})
			//--身份证号码验证-支持新的带x身份证 
	        function isIdCardNo(cardNum) {
	            if (cardNum.length == 0) {
	                return true;
	            }
	           // 11-15，21-23，31-37 ，41-46 ，50-54 ，61-65,81-82
	            var area = {
	                11 : "北京",
	                12 : "天津",
	                13 : "河北",
	                14 : "山西",
	                15 : "内蒙古",
	                21 : "辽宁",
	                22 : "吉林",
	                23 : "黑龙江",
	                31 : "上海",
	                32 : "江苏",
	                33 : "浙江",
	                34 : "安徽",
	                35 : "福建",
	                36 : "江西",
	                37 : "山东",
	                41 : "河南",
	                42 : "湖北",
	                43 : "湖南",
	                44 : "广东",
	                45 : "广西",
	                46 : "海南",
	                50 : "重庆",
	                51 : "四川",
	                52 : "贵州",
	                53 : "云南",
	                54 : "西藏",
	                61 : "陕西",
	                62 : "甘肃",
	                63 : "青海",
	                64 : "宁夏",
	                65 : "新疆",
	                71 : "台湾",
	                81 : "香港",
	                82 : "澳门",
	                91 : "国外"
	            };
	            var iSum = 0;
	            cardNum = cardNum.replace("\uff38", "X");
	            cardNum = cardNum.replace("x", "X");
	            if (!/^\d{17}(\d|x)$/i.test(cardNum)) {
	                //alert("输入身份证号码长度不对！");
	                return false;
	            }
	            cardNum = cardNum.replace(/x$/i, "a");
	            if (area[parseInt(cardNum.substr(0, 2))] == null) {
	                //alert("错误的身份证号码！");
	                return false;
	            }
	            sBirthday = cardNum.substr(6, 4) + "-" + Number(cardNum.substr(10, 2))
	                    + "-" + Number(cardNum.substr(12, 2));
	            var d = new Date(sBirthday.replace(/-/g, "/"));
	            if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d
	                    .getDate())) {
	                //alert("错误的身份证号码！");
	                return false;
	            }
	            for ( var i = 17; i >= 0; i--) {
	                iSum += (Math.pow(2, i) % 11) * parseInt(cardNum.charAt(17 - i), 11);
	            }
	            if (iSum % 11 != 1) {
	                //alert("错误的身份证号码！");
	                return false;
	            }
	            return true;
	        };


	        //表单验证
	        var inputs = $('.buy-ticket').find('input[type="text"]');
	        inputs.live('blur', function(){
	            var txtVal = $.trim($(this).val());
	            var _bool = true;
	            if(txtVal == ''){
	                _bool = false;
	                $(this).addClass('order-error');
	                var txt = $(this).parents('dl').find('dt').text();
	                txt = txt.substring(0,txt.length-1)
	                if($(this).parents('.buy-check').length>0){
	                    var tip = '请输入取票人的' + $(this).attr('placeholder');
	                }else{
	                    var tip = '请输入'+  $.trim(txt) + '的' + $(this).attr('placeholder');
	                }
	                $('.buy-tip').text(tip);
	                return false;
	            }else{
	                if($(this).hasClass('buy-phone')){
	                    if(!(/^(13|14|15|18)[0-9]{9}$/.test(txtVal))){
	                        _bool = false;
	                        $(this).addClass('order-error');
	                        $('.buy-tip').text('请输入正确的手机号码');
	                        $(this).val('');
	                        return false;
	                    }
	                }
	                if($(this).attr('placeholder') == '姓名'){
	                    var tLen = len = txtVal.length;
	                    for(var i=0; i<len; i++){
	                        if(txtVal.charCodeAt(i)>299){
	                            tLen++;
	                        }
	                    }
	                    if(!(/^[a-zA-Z\u4e00-\u9fa5]+$/.test(txtVal)) || tLen<2 || tLen>20){
	                        _bool = false;
	                        $(this).addClass('order-error');
	                        $('.buy-tip').text('姓名只能为中文或英文,长度为2-20字符');
	                        return false;
	                    }
	                }
	                if($(this).attr('placeholder') == '证件号码'){
	                    if($(this).prev('.buy-sel').val() == 'ID_CARD'){
	                        if(!isIdCardNo(txtVal)){
	                            _bool = false;
	                            $(this).addClass('order-error');
	                            $('.buy-tip').text('请输入正确的证件号码');
	                            return false;
	                        }
	                    }
	                }
	            }
	            if(_bool){
	                $(this).removeClass('order-error');
	                $('.buy-tip').text('');
	            }
	        })

	        $('.buy-order').bind('click', function(){
	            var _bool = true;
	            inputs.each(function(){
	                var txtVal = $.trim($(this).val());
	                if(txtVal == ''){
	                    _bool = false;
	                    $(this).focus();
	                    var txt = $(this).parents('dl').find('dt').text();
	                    txt = txt.substring(0,txt.length-1)
	                    if($(this).parents('.buy-check').length>0){
	                        var tip = '请输入取票人的' + $(this).attr('placeholder');
	                    }else{
	                        var tip = '请输入'+  txt + '的' + $(this).attr('placeholder');
	                    };
	                    $('.buy-tip').text(tip);
	                    return false;
	                }else{
	                    if($(this).hasClass('buy-phone')){
	                        if(!(/^(13|14|15|18)[0-9]{9}$/.test(txtVal))){
	                            _bool = false;
	                            $(this).focus();
	                            $('.buy-tip').text('请输入正确的手机号码');
	                            return false;
	                        }
	                    }
	                    if($(this).attr('placeholder') == '姓名'){
	                        var tLen = len = txtVal.length;
	                        for(var i=0; i<len; i++){
	                            if(txtVal.charCodeAt(i)>299){
	                                tLen++;
	                            }
	                        }
	                        if(!(/^[a-zA-Z\u4e00-\u9fa5]+$/.test(txtVal)) || tLen<2 || tLen>20){
	                            _bool = false;
	                            $(this).addClass('order-error');
	                            $('.buy-tip').text('姓名只能为中文或英文,长度为2-20字符');

	                            return false;
	                        }
	                    }
	                    if($(this).attr('placeholder') == '证件号码'){
	                        if($(this).prev('.buy-sel').val() == 'ID_CARD'){
	                            if(!isIdCardNo(txtVal)){
	                                _bool = false;
	                                $(this).addClass('order-error');
	                                $('.buy-tip').text('请输入正确的证件号码');
	                                return false;
	                            }
	                        }
	                    }
	                }
	            })
	            if(_bool){
	                $('.buy-tip').text('');
	                $('form').attr("action","/product/submitDistributionOrder.do");
	                $('form').submit();
	            }
	        })
		
		})
};