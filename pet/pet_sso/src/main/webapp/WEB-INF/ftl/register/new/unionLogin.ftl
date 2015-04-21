<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>驴妈妈登录 绑定账号</title>
    <link href="http://pic.lvmama.com/styles/login/login_20111206.css" rel="stylesheet" type="text/css" />  
    <link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
    <script src='/nsso/js/member/common.js' type='text/javascript'></script>
    <script src='/nsso/js/member/new_login_web.js' type='text/javascript'></script>   
</head>

<body>
<dl class="c_Steps">
  <dt id="Steps_Title_4"> <strong>网站登录</strong> </dt>

  <dd id="Steps_Content_4">
   <h4>您好！${session.getAttribute("SESSION.UNION.TEMP.USER").getUserName()}，欢迎来到驴妈妈旅游网</h4>
   
   <div class="login_lf">
        <form action="/nsso/union/bindingRegister.do" id="bindForm" method="post">
        <input type="hidden" name="cooperationName" value="<@s.property value="cooperationName"/>">
        <input type="hidden" name="cooperationUserAccount" value="<@s.property value="cooperationUserAccount"/>">
        
        <ul class="login_emali">
            <li class="nums_bg">我没有驴妈妈账号<br /><span class="mali_tips">填写邮箱，注册为驴妈妈会员，尊享一站式旅游服务</span></li>
            <li class="login_marg login_em"><label>电子邮箱</label></li>
            <li>
                <input name="email" id="email" type="text" class="login_input" tabindex="1"/>
            </li>
            <li class="warm_info" id="bind_errorText"><font color='gray'>请填写您的常用Email地址，做为驴妈妈的登录账号</font></li>
            <li class="login_info_nums">注册后，注册验证邮件将发送到您的注册邮箱，请查收确认！</li>
            <li class="login_info_nums">您也可以继续使用合作网站账号登录</li>
            <li class="login_check"><input id="terms" class="news_chkbox" type="checkbox" value="cookieSave" checked="checked" name="txt-notice">同意《<a href="http://www.lvmama.com/public/terms_of_service#m_3" target="_blank">驴妈妈旅游网会员服务条款</a>》</li>       
            <li><input  type="button" class="sub_bt" value="登录驴妈妈" id="bindBtn"/></li>        
        </ul>
        </form>
   </div>
   <div class="login_lf none_bg">
        <ul class="login_emali">
            <form action="/nsso/union/binding.do" method="post" id="myForm" name="myForm">
            <input type="hidden" name="cooperationName" value="<@s.property value="cooperationName"/>">
            <input type="hidden" name="cooperationUserAccount" value="<@s.property value="cooperationUserAccount"/>">
            <li class="nums_bg nums_bg_01">我有驴妈妈账号<br /><span class="mali_tips">请登录账号进行绑定</span></li>
            <li class="login_marg login_em"><label>用户名/手机号/Email、会员卡：</label></li>
            <li>
                <input name="mobileOrEMail" value="" id="sso_mobileAndEmail" type="text" class="login_input" />
                <span style="display:none;color:red;" id="sso_mobileAndEmail_errorText"  class="new_error"></span>
            </li>            
            <li class="login_em"><label>密码：</label></li>
            <li>
                <input name="password" id="sso_password" type="password" class="login_input" />
                <span style="display:none;color:red;" id="sso_password_errorText"  class="new_error"></span>
            </li>      
            <li id="actionerror"><font color='red'><@s.actionerror/></font></li>
            <li><input  type="button" class="sub_bt" value="登录并绑定账号" onClick="binding();"/></li>
            </form>
            
            <li class="warm_info01"><a href="http://login.lvmama.com/nsso/findpass/index.do" target="_blank">忘记密码了？</a></li>
            
            <!--li class="warm_info01"><span>忘记密码了？</span>可以<a href="javascript:void(0);" class="shouji"> 手机获取密码</a>或者<a href="javascript:void(0);" class="emali"> Email重置密码</a></li-->

            <!--li>
              <div class="yz_tanlist" id="getPasswordByMobileDiv" style="display:none">            
                <table cellspacing="5" cellpadding="5" border="0">
                    <tbody>
                        <tr>
                            <td>请输入您的手机号码：</td>
                            <td colspan="2"><input name="input" id="getPasswordByMobileText" type="text" class="login_yanzheng login_yanzheng01"></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>    
                            <td colspan="2"><span id="getPasswordByMobileTextError"></span></td>
                        </tr>
                        <tr>
                            <td>请输入验证码：</td>
                            <td><input name="Input2" id="getPasswordByMobileSecurityCodeText" type="text" class="login_yanzheng"></td>
                            <td><img id="image" src="/nsso/account/checkcode.htm" /><a href="#" onClick="refreshCheckCode('image');return false;">换一张</a></td>
                        </tr>
                        <tr>
    
                            <td>&nbsp;</td>
                            <td colspan="2"><span id="getPasswordByMobileSecurityCodeTextError">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="2"><img style="cursor:pointer;" onclick="getPasswordByMobileFunc()" class="validation_btn" alt="确认发送" src="http://pic.lvmama.com/img/icons/d_login_submit.gif"></td>
                        </tr>
                    </tbody>
                  </table>
                  </form>
                </div>
    
                <div  class="yz_tanlist01"  id="getPasswordByMailDiv" style="display:none">
                    <table cellspacing="5" cellpadding="5" border="0">
                        <tbody><tr>
                            <td>请输入您的邮箱地址：</td>
                            <td colspan="2"><input type="text" class="login_yanzheng login_yanzheng01" id="getPasswordByMailText" name="input"></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
    
                            <td colspan="2"><span id="getPasswordByMailTextError"></span></td>
                        </tr>
                        <tr>
                            <td>请输入验证码：</td>
                            <td><input type="text" class="login_yanzheng" id="getPasswordByMailSecurityCodeText" name="Input2"></td>
                            <td><img id="image1" src="/nsso/account/checkcode.htm" /><a href="#" onClick="refreshCheckCode('image1');return false;">换一张</a></td>
                        </tr>
                        <tr>
    
                            <td>&nbsp;</td>
                            <td colspan="2"><span id="getPasswordByMailSecurityCodeTextError">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td colspan="2"><img style="cursor:pointer;" onClick="getPasswordByEmailFunc();" class="validation_btn" alt="确认发送" src="http://pic.lvmama.com/img/icons/d_login_submit.gif"></td>
                        </tr>
                     </tbody></table>
                </div>
                <div id="successSendMessageDiv"></div>
             </li-->          
             
            
            </ul>
   </div>
   </dd>
</dl>
<script src="http://pic.lvmama.com/js/common/losc.js" language="javascript"></script>
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
</body>
 <script src='/nsso/js/unionlogin.js' type='text/javascript'></script>
</html>
