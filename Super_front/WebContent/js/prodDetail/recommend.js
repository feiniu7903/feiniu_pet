    var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    
    function checkEmail(name){
        if(("firstEmail" != name) && (("@" == $('#' + name).val()) || ("" == $('#' + name).val()))){
            return true;
        }else if(EMAIL_REGX.test( $('#' + name).val())){
            return true; 
        }else {
            return false;
        }
        
    }
    
    function showContent(name, content) {
        if ($('#' + name).length > 0) {
            $('#' + name).html(content);
        }
    }
    function checkInput(){
        var flag = true;
        clearAllWarn();
        if(checkNullReferrerName()){
            showContent("referrerNameWarn", "<font color='red'>姓名未填写</font>");
            flag = false;
        }
        if(checkLoggerReferrerName()){
            showContent("referrerNameWarn", "<font color='red'>姓名过长</font>");
            flag = false;
        }
        if(!checkEmail("firstEmail")){
            showContent("firstEmailWarn", "<font color='red'>邮箱无效</font>");
            flag = false;
        }
        if(!checkEmail("secondEmail")){
            showContent("secondEmailWarn", "<font color='red'>邮箱无效</font>");
            flag = false;
        }
        if(!checkEmail("thirdEmail")){
            showContent("thirdEmailWarn", "<font color='red'>邮箱无效</font>");
            flag = false;
        }
        if(!checkEmail("fourthEmail")){
            showContent("fourthEmailWarn", "<font color='red'>邮箱无效</font>");
            flag = false;
        }
        return flag;
    }
    
    function checkLoggerReferrerName(){
        var length = $("#referrerName").val().length;
        return length > 16;
    }
    
    function checkNullReferrerName(){
        var length = $("#referrerName").val().length;
        var value = $("#referrerName").val();
        return length == 0 || "请输入姓名" == value;
    }
    
    function sendEmail(args){
        $.ajax({url:"/product/recomment.do", type:"post", dataType:"json", data:args, success:function (data) {
                if (data) {
                    $(".recom-succeed").show();
                    $(".recomment_wra_mid").hide();
                    $(".com-tra-bg").height($(".recommend-con").outerHeight()+6);
                }
            }
        });
    }
    
    function clearAllWarn(){
        showContent("referrerNameWarn", "");
        showContent("firstEmailWarn", "");
        showContent("secondEmailWarn", "");
        showContent("thirdEmailWarn", "");
        showContent("fourthEmailWarn", "");
    }
    
    $(function(){
        $('#referrerName').focus(function(){
            if($(this).val()=="请输入姓名"){
                $(this).val('');
            }
        })
        $('#referrerName').blur(function(){
            if($(this).val()==""){
                $(this).val('请输入姓名');
                $('#referrerName').removeClass('input-color');
            }
        })
        $('#firstEmail').focus(function(){
            if($(this).val()=="例：xiaoming@163.com"){
                $(this).val('');
            }
        })
        $('#firstEmail').blur(function(){
            if($(this).val()==""){
                $(this).val('例：xiaoming@163.com');
                $('#firstEmail').removeClass('input-color');
            }
        })
        $('#secondEmail').focus(function(){
            if($(this).val()=="@"){
                $(this).val('');
            }
        })
        $('#secondEmail').blur(function(){
            if($(this).val()==""){
                $(this).val('@');
                $('#secondEmail').removeClass('input-color');
            }
        })
        $('#thirdEmail').focus(function(){
            if($(this).val()=="@"){
                $(this).val('');
            }
        })
        $('#thirdEmail').blur(function(){
            if($(this).val()==""){
                $(this).val('@');
                $('#thirdEmail').removeClass('input-color');
            }
        })
        
        $('#fourthEmail').focus(function(){
            if($(this).val()=="@"){
                $(this).val('');
            }
        })
        $('#fourthEmail').blur(function(){
            if($(this).val()==""){
                $(this).val('@');
                $('#fourthEmail').removeClass('input-color');
            }
        })
    })
    $('.close-recom').click(function(){
        clearAllWarn();
        initAllTextInput();
    })
    
    function initTextInput(id,content){
        $('#'+id).val(content);
        $('#'+id).removeClass('input-color');
    }
    
    function initAllTextInput(){
        initTextInput("referrerName","请输入姓名");
        initTextInput("firstEmail","例：xiaoming@163.com");
        initTextInput("secondEmail","@");
        initTextInput("thirdEmail","@");
        initTextInput("fourthEmail","@");
    }
    $("#send-email").click(function(){
        var flag = checkInput();
        if(flag){
            clearAllWarn();
            var args = {username:$("#referrerName").val(),productId:$("#productId").val(),productName:$("#productName").val(),firstEmail:$("#firstEmail").val(),secondEmail:$("#secondEmail").val(),thirdEmail:$("#thirdEmail").val(),fourthEmail:$("#fourthEmail").val()};
            sendEmail(args);
            initAllTextInput();
        }
    }); 
    
    /**
     * 添加当前页面到到收藏夹
     * @param {Object} title
     * @param {Object} url
     * @return {TypeName} 
     */
    function addBookmark() {
    	var title = $("title").html();
    	var url = window.location.href;
    	
		if (window.sidebar) {  
			window.sidebar.addPanel(title, url,"");  
		} else if( document.all ) {
			window.external.AddFavorite( url, title);
		} else if( window.opera && window.print ) {
			return true;
		}
	}