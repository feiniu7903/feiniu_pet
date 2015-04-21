	//<!--联合登陆绑定页-->

    var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    
    $("#bindBtn").click(function(){        
        if(!$('#terms').attr('checked')) {
            alert('同意条款才能绑定');
            $('#terms').focus();
            return false;
        } 
        
        var myEmail=$('#email').val();       
        if(myEmail==''){
            showContent("bind_errorText","<font color='red'><em class='sW'></em>*邮箱不能为空</font>");  
            return;
        }
        
        if (EMAIL_REGX.test(myEmail)) {           
            $.ajax({
                type: "POST",
                url: "/nsso/ajax/checkUniqueField.do",
                async: false,
                data: {
                    email: myEmail
                },
                dataType: "json",
                success: function(response) {
                    if (response.success == false) {
                        showContent("bind_errorText","<font color='red'><em class='sW'></em>*邮箱地址已经被注册,请在右侧登录</font>");
                        $("#sso_mobileAndEmail").val(myEmail);  
                        return false;
                    } else {
                        clearContent("bind_errorText");
                        $("#bindForm").submit();
                        return true;
                    }
                }
            });   
        } else {
            showContent("bind_errorText","<font color='red'><em class='sW'></em>*请输入有效的邮箱地址</font>");      
        }                  
    });
        
    $('.shouji').click(function(){
        if($(".yz_tanlist").css("display") != "none"){
            $('.yz_tanlist').hide();
            return;
        }
        $('.yz_tanlist01').hide();
        $('.yz_tanlist').show(); 
        $('#successSendMessageDiv').empty(); 
        refreshCheckCode("image");      
    });
    
    $('.emali').click(function(){
        if($(".yz_tanlist01").css("display") != "none"){
            $('.yz_tanlist01').hide();
            return;
        }
        $('.yz_tanlist').hide();
        $('.yz_tanlist01').show();
        $('#successSendMessageDiv').empty();
        refreshCheckCode("image1");
    })          


    function QueryString(item){ 
        var sValue=location.search.match(new
        RegExp("[\?\&]"+item+"=([^\&]*)(\&?)","i")) 
        return sValue?sValue[1]:sValue 
    } 
    
    var orderFromChannel = QueryString('losc'); 
    if (orderFromChannel != null) { 
        var cookie_date=new Date();  
        cookie_date.setDate(cookie_date.getDate()+30); 
        document.cookie = "orderFromChannel=" + escape (orderFromChannel) +
        ";path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString(); 
    } 

    function binding() {
        var mobileOrEMail=$('#sso_mobileAndEmail').val();       
        if(mobileOrEMail==''){
            showContent("sso_mobileAndEmail_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*用户名不能为空</font>");  
            return;
        };

        var password=$('#sso_password').val();
        if(password==''){
            showContent("sso_password_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*密码不能为空</font>"); 
            return;
        };
        document.myForm.submit();        
    }

    function showContent(name, content) {
        if ($('#' + name).length > 0) {
            $('#' + name).html(content);
            $('#' + name).show();
        } 
    }
    
    function clearContent(name){
        if ($('#' + name).length > 0) {
            $('#' + name).empty();
        }
    }
