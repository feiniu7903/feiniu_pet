<#if showErrorMessage == 'true'>
		<script type="text/javascript" charset="utf-8">
			alert("您的上传时间过长，请减少图片张数或压缩图片容量。期待您的旅程美图！");
		</script>
</#if>
<form id="myForm" action="/comment/writeComment.do" method="POST" enctype="multipart/form-data">
	<!--判断是否登陆-->
	<input type="hidden" name="placeId" id="placeId" value="${placeId}" />
	<input type="hidden" name="productId" id="productId" value="${productId}" />
	<input type="hidden" name="orderId" id="orderId" value="${orderId}" />
	<input type="hidden" name="productType" id="productType" value="${productType}" />
	
	        <!-- 星星打分样式-->
	        <ul class="mycomments">
	          <li class="c_w_padd">
	            <label class="c_Gencom">总体点评<i class="c_bixu">*</i>：</label>
	            <!-- <div> -->
	            <span class="com_StarValueCon total_val_posi">  
	            <font><em class="commentStarValue">0分</em></font><s class="star_bg">
	             <i class="ct_Star0">
	            <input name="latitudeIds" value="FFFFFFFFFFFFFFFFFFFFFFFFFFFF" type="hidden">
	            <input name="latitudeNames" value="总体点评" type="hidden">
	            <input name="scores" value="ct_Star0" type="hidden">
	            <b title="1分 失望" data-star="ct_Star1">1分</b> <b title="2分 不满" data-star="ct_Star2">2分</b> 
	            <b title="3分 一般" data-star="ct_Star3">3分</b> <b title="4分 推荐" data-star="ct_Star4">4分</b>
	            <b title="5分 力荐" data-star="ct_Star5" class="ct_Star5">5分</b> </i> </s> </span>
	             
	            <@s.iterator value="commentLatitudeList">
		            <span class="com_StarValueCon c_w_star"> <em>${name}</em> 
		            <s class="star_bg"> <i class="ct_Star0">
		            <input name="latitudeIds" value="${latitudeId}" type="hidden">
		            <input name="latitudeNames" value="${name}" type="hidden">
		            <input name="scores" value="ct_Star0" type="hidden">
		            <b title="1分 失望" data-star="ct_Star1">1分 失望</b> <b title="2分 不满" data-star="ct_Star2">2分 不满</b> 
                    <b title="3分 一般" data-star="ct_Star3">3分 一般</b> <b title="4分 推荐" data-star="ct_Star4">4分 推荐</b>
                    <b title="5分 力荐" data-star="ct_Star5" class="ct_Star5">5分 力荐</b> </i> </s>
		            <span class="commentStarValue"> </span> </span><!--ct_StartValuesCon end-->  
				</@s.iterator>
	            <!-- </div>--> 
	          </li>
	        
	         <li>
	            <label>点评内容<i class="c_bixu">*</i>：</label>
	            <div class="c_w_tarea fl">
	              <textarea class="c_tarea" rows="" cols="" id="content" name="content" ><#if content??>${content}<#else>可以输入500个汉字...</#if></textarea>
	              <p id="c_w_num" class="c_w_error">您尚需<i>20</i>字才能提交点评</p>
	            </div>
	          </li>
	           <li class="margin-top:-10px;"><label>精华说明：</label>发表精华点评，多送150积分，同时登上频道首页哦！　<a href="http://www.lvmama.com/comment/zt/fanxian/image/jh_01.jpg" class="xh_example" rel="nofollow">示例1</a>　　<a href="http://www.lvmama.com/comment/zt/fanxian/image/jh_02.jpg" class="xh_example" rel="nofollow">示例2</a>　　<a href="http://www.lvmama.com/comment/zt/fanxian/image/jh_03.jpg" class="xh_example" rel="nofollow">示例3</a> </li>
	     <li class="c_w_padd">
            <label class="fl">点评照片：</label>
            <div class="upfile_box">
              <p>目前支持GIF、PNG、JPG或JPEG格式，且不超过5MB</p>
              <ul id="upfile_list">
                <li>
                  <input class="c_upfile" type="file" name="fileData" id="fileData">
                  <label>照片描述：</label>
                  <input type="text" class="c_input_text" name="photoDescList" id="photoDescList" maxlength="20">
                </li>
              </ul>
              <div id="add_inputfile" style="display:none;">
                <ul class="item">
                  <li>
                    <input class="c_upfile" type="file" name="fileData" id="fileData">
                    <label>照片描述：</label>
                    <input type="text" class="c_input_text"  name="photoDescList" id="photoDescList" maxlength="20">
                    <span class="cancel_this" title="点击取消这一项">取消</span>
                </ul>
              </div>
            </div>
          </li>
	          
	          	<li style="display: none;" id="login">
	            <p style="margin-bottom:10px"><label for="loginName">用户名：</label>
		            <span class="c_inp_tips fl">
		            	<input type="text" class="c_input_text" id="loginName" name="name">
		            </span><i id="errotLoginName"></i>
	            </p>
                <p style="margin-bottom:10px">
		            <label id="pwd" for="password">密码：</label>
		            <span class="c_inp_tips fl">
		            	<input type="password" class="c_input_pwd" id="password" name="passwrod">
		            </span>
                	<i id="errorPassword"></i>
                </p>
				<p>
		            <label id="pwd" for="password">验证码：</label>
		            <span class="c_inp_tips fl">
		            	<input type="text" style="width:80px" class="c_input_pwd" id="sso_verifycode1" name="verifycode" >
	                    <img id="image" src="http://login.lvmama.com/nsso/account/checkcode.htm" /> <a href="#" class="link_blue" onClick="refreshCheckCode('image');return false;">换一张</a>
	                </span>
	                <i id="errorVerifyCode"></i>
                </p>
	            <br>
	            <a href="http://login.lvmama.com/nsso/register/registering.do" target="_blank" class="c_account">还没有账号</a>
	            <p class="c_log_tips c_w_error">如您没有驴妈妈账号，请勿关闭或刷新当前页面。在新打开的注册页面完成注册后，再回来点击发表功能</p>
	          </li>
	          <li class="c_publish_1">
	            <input type="button"  class="subButton" id="c_publish_2" value="发表" onClick="publish()">
	          </li>
	          
	        </ul>
	      <!--c_w_com end--> 
	    <!--c_list_lt end--> 
	    
	    <#if siglePage=="Y">
		    <!-- 单独的发点评页面显示, right_section_began -->
		    <!--
		    <div class="container_rt">
		      <div class="aside_box write_abox c_shadow">
		        <dl class="c_example">
		          <dt><a  class="c_exam_icon see_ex_box">看例子</a></dt>
		          <dd>·我们决不允许虚假点评！</dd>
		          <dd>·我们十分期待您的精华点评！</dd>
		          <dd><a href="http://www.lvmama.com/public/help_center_152" target="_blank" class="fr">了解更多&gt;&gt;</a>·所有点评必须原创首发</dd>
		        </dl>
		      </div>
		    </div>
		     -->
		    <!--container_rt end-->
	   </#if>
	
	<#if siglePage=="Y">
		<!--弹出层>>-->
		<div id="fb_tips" class="see_example"><div class="tips_inner"></div>
		  <h4>驴妈妈会员点评举例</h4>
		  <p>景点：千岛湖<br/>
		    内容：外界盛传千岛湖的景美如天堂，有幸一见，果不其然。搭着酒店的游艇在湖中心游览了一会，碧水蓝天，一尘不染，远远得看到梅峰观岛，形如梅花，这也许正是名字的由来。同事们各随其愿，有的选择步行到达观景台，有的选择乘缆车观全景，缆车收费，来回40元/人。步行用时并不多，一路拾阶而上，两旁绿树成荫，虽说低头看路抬头看树，但也难得做一回山林野人了。到达观景台，世界都安静了，被眼前的一切所折服，该用什么语言来表达那时的心情呢，壮阔的美，静谧和谐，偶尔也有闯入画中的小船和天空自由飞翔的鸟儿……也难怪连香港电影都会选择这里取景了。下山选择去坐了导游介绍的“滑草”，20元每人。说实话，刚开始见到滑草本尊，着实被雷到了，心想，这次被骗惨了。草是那种塑料做的，共三条滑道，工作人员把每个人放进一个超大的盆里，开始数一二三你就下去了。当我坐进那个盆里的时候，我竟然有点害怕了，工作人员一放手瞬间就滑了起来，速度之快，与过山车有得一拼，另我措手不及。此游乐设施不可貌相。好在下山还有一小段路，可以用来平复一下心情，一路走走停停拍拍照，将这无边的美景带回。</p>
		  <b class="close"></b> </div>
		<div id="c_overlay"></div>
		<div id="c_w_pop"> <a class="c_w_close"></a> <img src="http://pic.lvmama.com/img/new_v/ob_comment/load.gif" width="190" height="14">
		  <p>图片上传完成后，该窗口会自动关闭。请耐心等待</p>
		  <p class="c_hide"><a>隐藏</a></p>
		</div>
	  </#if>
	 
</form>
  
<script type="text/javascript">

/*alert——addpop样式*/
var lv_popjs = document.createElement("script");
lv_popjs.setAttribute("type", "text/javascript");
lv_popjs.setAttribute("src","http://pic.lvmama.com/js/pop.js");
document.getElementsByTagName("body")[0].appendChild(lv_popjs);
/*alert——addpop样式*/

	function refreshCheckCode(s) {
		 var elt = document.getElementById(s);
		 elt.src = elt.src + "?_=" + (new Date).getTime();
	}

	function publish() {
		 /** 页面校验  **/
		if(!chkForm()){
			return;
		}
		 /** 登陆检测  **/
		if(document.getElementById('login').style.display=='block')
	    {
	    	login();
	    }
	    
	     //登陆成功就提交否则显示登陆框
	    $.getJSON(
			"http://login.lvmama.com/nsso/ajax/checkLoginStatus.do?jsoncallback=?",
			{
			},
			 function(data) {
				if (data.success) {
					$("#myForm").attr("target","_self");
	 				$("#myForm").submit();
				} else {
					 document.getElementById('login').style.display='block';  
				}
			}
		); 
	}

/** 页面校验  **/
function chkForm(){ 
		var scores = document.getElementsByName("scores");
		if((scores[0].value == "ct_Star0")||(scores[1].value == "ct_Star0") ||(scores[2].value == "ct_Star0") ||(scores[3].value == "ct_Star0") ||(scores[4].value == "ct_Star0")){
		  		lv_alert("请对5个维度完整打分.");
		  		return false;
		}
		if($("#photoDescList").val().length > 20) {
				lv_alert('描述小于20个字符。');
				return false;	
		}
		
		if($("#content").val()=="" || $("#content").val().length < 20 || $("#content").val().length > 500||$("#content").val()=="可以输入500个汉字...") {
				lv_alert('回复内容长度应该在20-500个字符。');
				$("#content").focus();;
				return false;
		}
		return true;
} 
  
/**
 * 用户登录
 **/
 function login() {
		 var mobileOrEMail=$('#loginName').val();
		 if(mobileOrEMail==''){	
		 		$('#loginName').focus(function(){
						$("#errotLoginName").html("");
				});
				$("#errotLoginName").html("<font color='red'>用户名不能为空</font>");
				return false;
		 };
							
		var password = $('#password').val(); 
		if(password==''){
				$('#password').focus(function(){
						$("#errorPassword").html("");
				});	
				$("#errorPassword").html("<font color='red'>密码不能为空</font>");
				return false;
		};
		
	    var verifycode = $('#sso_verifycode1').val(); 
		if(verifycode==''){
				$('#sso_verifycode1').focus();	
				$("#errorVerifyCode").html("<font color='red'>验证码不能为空</font>");
				return false;
		};
		
		$.getJSON(
			"http://login.lvmama.com/nsso/ajax/login.do?jsoncallback=?",
			{
			 mobileOrEMail : mobileOrEMail,
			 password	  : password,
			 verifycode   : verifycode
			},
			 function(data) {	   
				if (data.success) {
						document.getElementById('login').style.display='none';
					if(!chkForm()){
						return;
					}
					 /** 登陆完提交页面  **/
					$("#myForm").attr("target","_self");
					$("#myForm").submit();
				} else {
					$("#errotLoginName").html("<font color='red'>请确认用户名，密码，验证码是否正确!</font>");
					$("#errorPassword").html("");
					$("#errorVerifyCode").html("");
				}
			}
	);		
}
</script>
<script>
$(function(){
    var example_dialog = '<div class="example_overlay" style="width: 100%;height: 100%;background: #000;filter: Alpha(opacity=50);opacity: 0.5;left: 0;top: 0;position: fixed;z-index: 999;display: none;_position: absolute;"></div><div class="example_dialog" style="display:none;width:700px;position:fixed;z-index:1000;top:50px;left:50%;margin-left:-350px;text-align:center;"><a href="javascript:;" class="example_close" style="position:absolute;display:inline-block;top:-25px;right:0;padding:0 10px;color:#fff;">关闭</a><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/loadingGIF46px.gif" alt="" /></div>';
    $("body").append(example_dialog);
    $("a.xh_example").live("click",function(){
        $("div.example_overlay").show();
        $("div.example_dialog").show().find("img").attr("src",$(this).attr("href"))
        return false;
    })
    $("div.example_overlay,a.example_close").live("click",function(){
        $("div.example_overlay,div.example_dialog").hide();
    })
    
})
</script>