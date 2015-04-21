<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/product/${prodCProduct.prodProduct.productId}/">
<#include "/WEB-INF/pages/product/newdetail/seo_product.ftl"/>    
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_detail/free.css,/styles/new_v/ui_plugin/calendar.css,/styles/new_v/ui_plugin/jquery-ui-1.8.17.custom.css,/styles/new_v/global.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/c_free.css,/styles/new_v/ob_common/ui-components.css,/styles/v5/modules/tip.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/buttons.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>
<script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_comment/x_comment.js,/js/new_v/ui_plugin/jquery-time-price-table.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" ></script>
<#include "/common/coremetricsHead.ftl">
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>
</head>
<body>
<img src="http://pic.lvmama.com/img/new_v/newBtn_bg.gif" style="display: none;"/>
<@s.set var="pageMark" value="'productDetail'" />
	<#include "/common/setKeywor.ftl">  
	<input type="hidden" id="checkDate" value="${Parameters.checkDate?if_exists?string}"> 
	<div id="warp">            
	    <div class="main">
	        <!--上部(产品经理推荐以上部分)-->
	        <#include "/WEB-INF/pages/product/newdetail/top/mainTop.ftl">
	        <!--下部(产品经理推荐以下部分)-->
	        <#include "/WEB-INF/pages/product/newdetail/buttom/mainButtom.ftl">               
	    </div><!--main end-->
	</div><!--warp end-->
	    
	<!--===== 底部文件区域 S ======-->
	<div class="hh_cooperate">
	    <@s.if test="prodCProduct.prodProduct.isRoute()">      
	         <#include "/WEB-INF/pages/staticHtml/friend_link/route_detail_footer.ftl">
	    </@s.if>
	    <@s.elseif test="prodCProduct.prodProduct.isTicket()">   
	         <#include "/WEB-INF/pages/staticHtml/friend_link/ticket_detail_footer.ftl">      
	    </@s.elseif>
	</div>
	<script src="http://pic.lvmama.com/js/common/copyright.js"></script>
        <!--===== 底部文件区域 E ======-->    
	<script>
		<@s.if test="prodCProduct.prodProduct.isTicket()">
	    	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|ticket_2013|ticket_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
	    </@s.if>
		<@s.if test="prodCProduct.prodProduct.isFreeness()">
	    	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|around_2013|ticket_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
	    </@s.if>
		<@s.if test='prodCProduct.prodProduct.isGroup() && prodCProduct.prodProduct.subProductType=="SELFHELP_BUS"'>
	    	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
	    </@s.if>
		<@s.if test='prodCProduct.prodProduct.isGroup() && prodCProduct.prodProduct.subProductType!="SELFHELP_BUS"'>
	    	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|destroute_2013|destroute_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
	    </@s.if>
		<@s.if test="prodCProduct.prodProduct.isForeign()">
	    	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|abroad_2013|ticket_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
	    </@s.if>
	    	    	    	    	    
		$(function(){
			$("script[type='text/templete']").each(function(){
				$(this).replaceWith($(this).html());
			});
			$("textarea[type='text/templete']").each(function(){
				$(this).replaceWith($(this).html().replace(/\&lt;/g,"<").replace(/\&gt;/g,">").replace(/\&amp;/g,"&"));
			});
		});
	</script>
	<textarea type="text/templete" class="dn">
    <script src="http://pic.lvmama.com/js/new_v/ob_detail/ob_free.js"></script>
	<script src="http://www.lvmama.com/js/prodDetail/recommend.js" type="text/javascript"></script>
	<script src="http://www.lvmama.com/js/prodDetail/comment.js" type="text/javascript"></script>
	<!-- <script src="http://www.lvmama.com/js/prodDetail/quickBooker.js" type="text/javascript"></script> //代码转移到89行-->

    <script type="text/javascript">
        $(document).ready(function(){
        	//setKeyword('<@s.property value="keyword" />');
        
        	//changCity();
        	//myLvmamaSelect();  
        	//headerSearch();
        	//showMeunTool();
        	//ticketSearch();
        	//checkCookie();
        
        	//$("#placeKeyword").suggest("http://www.lvmama.com/dest/place/place!getPlaceInfoByName.do?jsoncallback=?",{});
        
            //导航栏JS
            crumbEff();                 
            
            //推荐产品控制 
            $(".recommend_tabs li").first().addClass("recommend_current");
            $(".recommend_panes div").first().show().siblings().hide();
            
            <@s.if test="!prodCProduct.prodProduct.hasSelfPack() && !prodCProduct.prodProduct.IsAperiodic()">
            //复制快速预订块内容
            $("#quickBooker2_tab2").html($("#quickBooker1_tab2").html()); 
            </@s.if>                   
           $.getScript("http://www.lvmama.com/js/prodDetail/quickBooker.js",function(){
				saveBranchObjToArray();
				$(".free_dtl_pro_tab").find("input").val("0");	
			});
			//初始化所有标签显示Tag
			$('span[class^="tags"]').ui('lvtip',{ 
				hovershow: 200
			});
	   		 //互动有奖返现金 
			$('.xtiptext').ui('lvtip',{ 
				hovershow: 200
			});
			
			function errorMsg(type, msg){
			     var html = "";
			     if(type === 1){
			         html = '<div class="zxerror" style="display: block; position: absolute; top: 0;" >';
			     }else{
			         html = '<div class="zxerror" style="display: block; position: absolute; top: 15px; left:450px;" >';
			     }
			
			     html +='<span class="zxerror-text">'+
                                    '<div class="error-arrow">'+
                                        '<em>◆</em>'+
                                        '<i>◆</i>'+
                                    '</div>'+
                                    '<p>'+msg+'</p>'+
                                '</span>'+
                            '</div>';
                            
                 return html;
			}
			
			//开始预订
			$(".immediateB").live("click", function(){
					 var parent =  $(this).parent().attr("class"),
				        val =  "",
				        number = 0;
				    if(parent==="bookerBtn"){
				         val =  $(this).parents("dl").find("select").val();
				    }else if(parent==="quick-book-ul_li5"){
				         val =  $(this).parents("ul").find("select").val();
				    }    	
				    if(val !== ""){
				         if(val === "0"){
				             if(parent==="bookerBtn"){
				                 $(".quick-error").eq(0).show();
				             }else{
				                $(".quick-error").css({"top":"13px","left":"293px"}).eq(1).show();  
				             }   
				         }else{
				            if(parent==="bookerBtn"){
	    			            $(this).parents("dd").find("table input").each(function(){
	    			               number += parseInt($(this).val(),10);
	    			            });
				            }else{
				                $(this).parents("ul").find("table input").each(function(){
	                               number += parseInt($(this).val(),10);
	                            });  
				            }
				            if(number === 0){
				                 if(parent==="bookerBtn"){
				                     $(this).parents("dd").find("table tr").eq(0).find("td").eq(2).append(errorMsg(1, "请填写数量")); 
				                 }else{
				                     $(this).parents("ul").find("table tr").eq(0).find("td").eq(2).append(errorMsg(2, "请填写数量"));
				                 }
				            }
				            else{
			            		try{ 
			            			cmCreateElementTag('${prodCProduct.prodProduct.subProductType?if_exists}预订_<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodCProduct.prodProduct.productName)"/>_${prodCProduct.prodProduct.productId?if_exists}',"产品详情页-${prodCProduct.prodProduct.productType?if_exists}预订点击");
			            			cmCreateShopAction5Tag('${prodCProduct.prodProduct.productId?if_exists}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodCProduct.prodProduct.productName)"/>',number,'${prodBranch.sellPrice/100}','${prodCProduct.prodProduct.subProductType?if_exists}');
			            			cmDisplayShops();
			            		}catch(exception){
							    }finally{   
					            	<#if login>
									        return true; 
					            	<#else>
						            	showLogin(function(){$(".play_date").find("form").submit();});
					            	</#if>
							    } 
				            }
				         }   
	                     return false;
				    }
		
			   
			});
			
			$(".quickBooker_select").click(function(){		
			     $(".quick-error").hide();
			});
			//选择行程
			$(".viewCurrentJourney").live("click", function() {
				var $this = $(this);
				var multiJourneyId = $this.attr("data");
				//相应行程展示
				$(".viewCurrentJourney").removeClass("m_active").find("span").removeClass("iy");
				$this.addClass("m_active").find("span").addClass("iy");
				$("div[id^='currentJourneyDiv_']").removeClass("d_active").hide();
				$("#currentJourneyDiv_" + multiJourneyId).addClass("d_active").show();
				//相应费用说明展示
				$("dl[id^='currentCCDl_']").hide();
				$("#currentCCDl_" + multiJourneyId).show();
				$("dl[id^='currentNCCDl_']").hide();
				$("#currentNCCDl_" + multiJourneyId).show();
			});
	   		 
        }); 
        
        $(function(){
        	$(".qijiashuoming").ui('lvtip',{ 
			hovershow: 200 
			});
        })
    </script>
    
 <script type="text/javascript">
	$(function(){
		$(".u_comment").each(function(){
		  $(this).children("dl").last().addClass("last");;
		
	  });
		var ani_time=600,ani_swi=true;
		if (!window.XMLHttpRequest){
			var $cpro_height=$("#c_line_pro").height();
			$("#c_shadebg").height($cpro_height);
		}
		
		/*$("#c_shade").click(function(){
			if(ani_swi==true){
				  $(this).animate({left:"-7px"},ani_time,function(){
					  $("#c_jtleft").fadeOut(ani_time);
					  $("#c_jtright").fadeIn(ani_time);
					  $(this).addClass("cursor_rt");
				  });
				  $("#c_reline_ct").animate({left:"-383px"},ani_time);
				  ani_swi=false;
			}else{
				$(this).animate({left:"670px"},ani_time,function(){
					$("#c_jtright").fadeOut(ani_time);
					$("#c_jtleft").fadeIn(ani_time);
					 $(this).removeClass("cursor_rt");
				});
				$("#c_reline_ct").animate({left:"15px"},ani_time);
				ani_swi=true;
			}
		})*/
	});
</script>
    <#include "/common/ga.ftl">  
	<script id="ruiguangTuiJianScriptLoad">
	window["_BFD"] = window["_BFD"] || {};
	_BFD.BFD_INFO = {
		"city" : "",
		"type" : "${prodCProduct.prodProduct.productType?if_exists}",//频道名称
		"sub_type" : "${prodCProduct.prodProduct.subProductType?if_exists}",//频道名称
		"id" : "${prodCProduct.prodProduct.productId?if_exists}",   //商品id号
		"name" : "${prodCProduct.prodProduct.productName?if_exists}",   //商品名称
		"link" : "http://www.lvmama.com/product/${prodCProduct.prodProduct.productId}",   //商品链接
		"image_link" : "${prodCProduct.prodProduct.absoluteSmallImageUrl?if_exists}",   //商品大图地址
		"price" : "<@s.property value="prodCProduct.prodProduct.sellPrice/100" escape="false"/>",   //在售价格
		"market_price" : "<@s.property value="prodCProduct.prodProduct.marketPrice/100" escape="false"/>",   //市场价格
		"category" : [],   //商品的类别详细信息，第一个元素为类别名称，第二个元素为类别地址，2维数组;类别从大到小，数组最后一项为商品当前属类别
		"category_id" : [],   //类别ID;对应类别详细信息，一维数组
		"from_city":"<@s.if test="prodCProduct.from != null">${prodCProduct.from.name?if_exists}</@s.if>",
		"destination_city" : "<@s.if test="prodCProduct.to != null">${prodCProduct.to.name?if_exists}</@s.if>",//门票所在地
		"description" : "",//产品经理推荐
		"onsale" : true,   //上下架标识  在架为true, 下架为false;
		"tag" : "",//tag标签 标题后面的图标
		"user_id" : "<@s.if test="userId == null"></@s.if><@s.else>${userId}</@s.else>", //网站当前用户id，如果未登录就为0或空字符串
		"page_type" : "detail" //当前页面全称，请勿修改
	};
	</script>
	</textarea>
	
	<script>
		//签证发邮件
		function visaEmailSend(data){
		
			pandora.dialog({
				title:"邮件发送",
				skin: "dialog-pink",				
				content: $("#visaDialog"),
				okValue: "确定发送",
				okClassName:"pbtn-pink",
				ok: function () {
				var that =this;
				var emailToAddress = $("#visaEmail").val();
				if(checkVisaEmail(emailToAddress)){
					$.ajax({
		    	 		url: "/visa/visaDetails.do",
						type:"post",
		    	 		data: {
		    	 				"emailToAddress":emailToAddress,//收件人邮箱
								"documentId":$("#visa_Email_documentId_"+data).val(),//文档Id
								"cnVisaType":$("#visa_Email_cnVisaType").val(),//签证类型
								"occupation":$("#ssrq_"+data).val(),//所属人群
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								$.msg('邮件已发送！',1000);
							} else {
								$.msg('邮件发送失败,请尝试再次发送！',2000);
							}
		    	 		}
		    		});
		    		
				}else{
					pandora.dialog({
						skin: "dialog-pink",
						wrapClass: "dialog-mini", 
						content:"<font color='red' size='4'>邮箱格式不正确！</font>", 						
						cancelValue: "返回",
						cancel: true
						});
					//$.msg('邮箱格式不正确','800');
				}
					 			
		    		
				
				},
				cancelValue: "返回",
				cancelClassName:"pbtn-pink",
				cancel: true
			})
		}
	
	//验证邮箱格式的正确性
	function checkVisaEmail(email){
	     var EMAIL_REGX = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/;
	        if(""!= email&&EMAIL_REGX.test(email)){
	            return true;
	        }else {
	        	return false;
	        }
	        
	    }
		
	</script>
	<script>
        	cmCreatePageviewTag("产品详情页-"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodCProduct.prodProduct.productName)"  escape="false"/>（可预订商品1）", "<@s.property value="prodCProduct.prodProduct.subProductType"  escape="false"/>", null, null);
    		cmCreateProductviewTag("${prodCProduct.prodProduct.productId?if_exists}", "<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodCProduct.prodProduct.productName)"  escape="false"/>", "<@s.property value="prodCProduct.prodProduct.subProductType"  escape="false"/>", "${prodCProduct.prodProduct.subProductType?if_exists}");
    </script>
</html>
