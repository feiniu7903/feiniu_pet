<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>  
    <meta charset="utf-8">
    <title>礼品卡-驴妈妈旅游网</title>
    <#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
    <#include "/common/coremetricsHead.ftl">
</head>
<body id="page-lipinka">
        <#include "/WEB-INF/pages/myspace/base/header.ftl"/>
        <div class="lv-nav wrap">
            <p>
                <a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
                &gt;
                <a class="current">礼品卡</a>
            </p>
        </div>
        <div class="wrap ui-content lv-bd">
            <#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
            <div class="lv-content">
               
<!-- 我的积分>> -->
<div class="ui-box mod-top mod-info">
    <div class="ui-box-container clearfix">
        <div class="info-detail fl">
            <div class="hv_blank fr"></div>
            <h3>礼品卡张数</h3>
            <p>
                <span class="ui-btn ui-btn5 ui-btn-lpk"><i>绑定礼品卡</i></span>
                <dfn class="info-num"><i><@s.if test="pagination.totalResultSize==null||pagination.totalResultSize==0">0</@s.if><@s.else>${pagination.totalResultSize}</@s.else></i>张</dfn>
            </p>
        </div>
        
        <div class="info-tips lv-cc fl">
            <h4>提示：</h4>一次可以绑定多张礼品卡哟；
        </div>
    </div>
</div>


<div id="lv-tabs" class="ui-box mod-edit points-edit">
    <div class="ui-tab-title"><h3>礼品卡明细</h3>
        
    </div>
    
    <div id="tabs-1" class="lv-tabs-box lv-tabs-box-selected">
        <!-- 获取积分>> -->
        <div class="points-box">
            <table class="lv-table points-table">
                <colgroup>
                <col class="lvcol-1">
                <col class="lvcol-2">
                <col class="lvcol-3">
                <col class="lvcol-4">
                </colgroup>
                <thead>
                    <tr class="thead">
                        <th>礼品卡卡号</th>
                        <th>面值</th>
                        <th>已使用金额</th>
                        <th>未使用金额</th>
                        <th>有效期</th>
                        <th>卡状态</th>
                        <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                      <@s.iterator   value="lvmamaList" status="st">
                          <tr>
                            <td>${cardNo}</td>
                            <td>${amountFloat}</td>
                            <td>${amountFloat-balanceFloat}</td>
                            <td>${balanceFloat}</td>
                            <td><@s.date value="overTime" format="yyyy年MM月dd日" /></td>
                            <td>${cnStatus}</td>
                            <td><span class="ui-btn ui-btn4 ui-btn-mx" onclick="javascript:dodetail('${cardNo}')"><i>明细</i></span></td>
                          </tr>
                      </@s.iterator>
                       
                </tbody>
            </table>
            <div class="pages">
                     <@s.property escape="false" value="pagination.pagination"/>        
                       </div>
        <!-- <<获取积分 -->
    </div>

</div>
<!-- <<我的积分 -->

            </div>
        </div>
<div class="hr_b"></div>

<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>

<script type="text/javascript">
    
    
</script>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/dialog.css,/styles/v4/modules/button.css">
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>

<script>
        function imageEverificationCode(){
                var verifycode= $.trim($("#imageEverificationCode").val());
                if(verifycode==""){
                  alert("验证码不能为空");
                   return false;
                 }
                 $.ajax({
                    type: "POST",
                    url: "/ajax/chackVerifycode.do?verifycode="+verifycode,
                    dataType:'json',
                    async:false,
                    success: function(data){
                         if(data.success){
                            return true;
                         }else{
                             alert(data.msg);
                             $("#imageEverificationCode").val("");
                             return false;
                         }
                    },
                    failure:function(error){alert(error); $("#imageEverificationCode").val("");}
                   });
                return false;
               }
$(function(){ 

 $("#imageEverificationCode").bind('change',  function () {
            imageEverificationCode()
    });
 
   $("#messagEverificationCode").bind('change',  function () {
        var code=$.trim($("#messagEverificationCode").val());
        if(code==""){
            alert("手机校验码不能为空");
        }
        if(code!=""){
              $.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile=${user.mobileNumber}&authenticationCode=" + $.trim($("#messagEverificationCode").val()) + "&jsoncallback=?",function(json){
                    if (json.success == true) {
                       //alert("手机验证码成功");
                    }else{
                        alert( "手机验证码输入错误,"+json.errorText);
                        $("#messagEverificationCode").val("");
                    }  
            }); 
        }
   });

/**刷新验证码**/
refreshCheckCode('image1');

 /*绑定礼品卡*/
    $(".ui-btn-lpk").click(function () {
        pandora.dialog({
            width:500,
            title: "绑定礼品卡",
            content: $("#lpk_bd")/*弹出层调用这个ID的html内容*/,
            ok:function(){
               if(!checkFrom()){return false;}
               var cardNo=$('#cardNo').val();var cardPassword=$('#cardPassword').val();
                $.ajax({
                   type: "POST",
                   url: "/myspace/lipinka/addLipinka.do",
                   data: { cardNo: cardNo,cardPassword:cardPassword},
                   dataType:'json',
                   success: function(msg){
                       if(msg.success){
                            alert("绑定成功!");window.location.reload(true);
                       }else if(msg.message!=null){
                            alert("绑定失败！"+msg.message);
                            /**刷新验证码**/
                        refreshCheckCode('image1');
                       }else{
                        alert("绑定失败！")
                        /**刷新验证码**/
                        refreshCheckCode('image1');
                       }
                   }
                });
                this.close();
                return false
            },
        cancel: true
        });
    });
    
    
       /*发送验证码*/
              $('#send-verifycode').click(function(){
                            var mobileNumber=$.trim('${user.mobileNumber}').replace(/\s/g,"");
                            if(mobileNumber==""){
                                alert("请绑定手机号码");
                                return;
                            }
                            $.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail="+mobileNumber+"&jsoncallback=?",function(json){
                                if (json.success) {
                                   alert("短信验证码发送成功！");
                                } else {
                                    if(json.errorText == 'phoneWarning'){
                                        alert("已超过每日发送上限，请于次日再试");
                                       
                                    }else if(json.errorText == 'ipLimit'){
                                       alert("当前IP发送频率过快，请稍后重试");
                                     }else if(json.errorText == 'waiting'){
                                        alert("发送频率过快，请稍后重试");
                                        
                                    }else{
                                        alert(json.errorText);
                                        
                                    }
                                }
                            }); 
                         });
            
 });
     /* 更新验证码 */
    function refreshCheckCode(id){
        document.getElementById(id).src = "/account/checkcode.htm?now=" + new Date();
    }
    
    function dodetail(cardNo){
           $.ajax({
               type: "POST",
               url: "/lipinka/detail.do?cardNo="+cardNo,
               dataType:'json',
               success: function(json){
                     if(json.success){
                       var  content="<table class=\"lv-table points-table\">"+
                                "<thead>"+
                                    "<tr class=\"thead\">"+
                                        "<th>礼品卡卡号</th>"+
                                        "<th>面值</th>"+
                                        "<th>已使用金额</th>"+
                                        "<th>未使用金额</th>"+
                                        "<th>有效期</th>"+
                                        "<th>卡状态</th>"+
                                    "</tr>"+
                                  "</thead>"+
                                  "<tbody>"+
                                      "<tr>"+
                                        "<td>"+json.lvmamaStoredCard.cardNo+"</td>"+
                                        "<td>"+json.lvmamaStoredCard.amountFloat+"元</td>"+
                                         "<td>"+json.lvmamaStoredCard.usedMoneyFloat+"元</td>"+
                                        "<td>"+json.lvmamaStoredCard.balanceFloat+"元</td>"+
                                        "<td>"+json.lvmamaStoredCard.cnOverTime+"</td>"+
                                        "<td>"+json.lvmamaStoredCard.cnStatus+"</td>"+
                                      "</tr>"+
                                "</tbody>"+
                            "</table>";
                       if(null!=json.list){
                             content=content+ "<table class=\"lv-table points-table lpk_mx_tab2\">"+
                                            "<thead>"+
                                                "<tr class=\"thead\">"+
                                                    "<th>订单号</th>"+
                                                    "<th>订单金额</th>"+
                                                    "<th>礼品卡支付金额</th>"+
                                                    "<th width=\"200\">产品名称</th>"+
                                                    "<th width=\"100\">订单日期</th>"+
                                                    "<th width=\"50\">状态</th>"+
                                                "</tr>"+
                                              "</thead>"+
                                              "<tbody>";
                                              for(var i=0;i<json.list.length;i++){
                                                  var detail="<tr>"+
                                                    "<td>"+json.list[i].orderId+"</td>"+
                                                    "<td>"+json.list[i].orderPayFloat+"</td>"+
                                                    "<td>"+json.list[i].amountFloat+"</td>"+
                                                    "<td>"+json.list[i].productName+"</td>"+
                                                    "<td>"+json.list[i].cnCreateTime+"</td>"+
                                                    "<td>"+json.list[i].zhUsageType+"</td>"+
                                                  "</tr>";
                                                  content=content+detail; 
                                                  }
                                            content=content+ "</tbody></table>";
                          }
                       $("#lpk_mx").html(content);  
                     } 
                    pandora.dialog({
                        width:700,
                        title: "礼品卡明细",
                        content: $("#lpk_mx")/*弹出层调用这个ID的html内容*/,
                        ok:true,
                        cancel: true
                    });
                },
               failure:function(error){alert(error);}
          });
     }
     

              function checkformNew(tcardNo,tpassword){
                    var searchcardNo=$("#"+tcardNo).val();
                    var searchpassword=$("#"+tpassword).val(); 
                    if(searchcardNo=="" || searchpassword==""){
                        alert("旅游卡卡号不能为空,卡密码不能为空 请填写！"); 
                        return false;            
                    }
                     var r = /^[0-9]{12}$/;
                     var rs = /^[0-9]{8}$/;
                     if(!r.test(searchcardNo)){
                      alert("旅游卡卡号必须为数字,必须为12位  请填写！"); 
                          return false;
                      }　
                      if(!rs.test(searchpassword)){
                      alert("卡密码必须为数字,必须为8位  请填写！"); 
                          return false;
                      }
                      return true;
               }

       
      function checkFrom(){
              if(!checkformNew("cardNo","cardPassword")){refreshCheckCode('image1');return false;}
              var i=$.trim($("#messagEverificationCode").val());
              if(i==""||i==null){alert("手机校验码不能为空");refreshCheckCode('image1'); return false;}
             
              var i=$.trim($("#imageEverificationCode").val());
              if(i==""||i==null){alert("验证码不能为空");$("#imageEverificationCode").focus();
                 refreshCheckCode('image1');return false;}
              return true;
       }     
</script>
<!--绑定礼品卡div-->
<div class="lpk_bd" id="lpk_bd"  style="display:none">
    <table class="lpk_bd_table">
        <tr>
            <td align="right">礼品卡卡号：</td>
            <td><input type="text" id="cardNo" placeholder="请输入卡号"></td>
        </tr>
        <tr>
            <td align="right">礼品卡密码：</td>
            <td><input type="password" id="cardPassword" placeholder="请输入密码"></td>
        </tr>
        <tr>
            <td align="right">手机验证码：</td>
            <td><input type="text" id="messagEverificationCode" placeholder="请输入短信验证码">
            <span class="ui-btn ui-btn1"><i ><a id="send-verifycode">发送</a></i></span>
               
            <a class="lpk_bundling" target="_blank" href="http://www.lvmama.com/myspace/userinfo/phone.do">手机未绑定？</a></td>
        </tr>
        <tr>
            <td align="right">验证码：</td>
            <td><input class="w_80" id="imageEverificationCode" type="text" placeholder="请输入验证码">
            <img id="image1" src="/account/checkcode.htm" /><a href="#" class="link_blue" onClick="refreshCheckCode('image1');return false;">换一张</a> </td>
        </tr>
    </table>
</div>
<!-- 明细div-->
<div class="lpk_mx" id="lpk_mx" style="display:none">
     
</div>
	<script>
		cmCreatePageviewTag("礼品卡", "D1003", null, null);
	</script>
</body>
</html>