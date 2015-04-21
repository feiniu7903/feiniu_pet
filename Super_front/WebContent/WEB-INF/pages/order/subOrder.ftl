<script type="text/javascript">
	<@s.if test="!isLogin() ">
		$(function(){
			$('.btn-close').click(function(){
				$(window.parent.document).find(".bgLogin,.LoginAndReg, #loginDIV").hide();
			});

		});	
		
		function showRadidLoginDiv() {
			$(".bgLogin").show();		
			$(".LoginAndReg,#loginDIV").show();						
			var topN = $(window).scrollTop()/2 + 250,
			leftN = $(window).width()/2 - $(".LoginAndReg").width()/2;
			$(".LoginAndReg,#loginDIV").fadeIn(200).css({"top":topN,"left":leftN,"position":"absolute"}); 
			$("#loginDIV").height($(".LoginAndReg").outerHeight()).width($(".LoginAndReg").outerWidth());				 
		}
		
		function cooperationRegisterLogin(){	
			$.getJSON("http://login.lvmama.com/nsso/ajax/registerLoginByCooperationCache.do?jsoncallback=?",
			{
			},
			function(json){
				if (json.success) {
	                    loginFormSubmit();
				} else {
					    showRadidLoginDiv();
				}
			});
		}
	</@s.if>

	function changeSubmitForm() {
		<@s.if test = 'mainProdBranch.prodProduct.productType == "TICKET" || mainProdBranch.prodProduct.productType == "HOTEL" || "FREENESS" ==mainProdBranch.prodProduct.subProductType || travellerInfoOptions == null'>
			var baoxian = 0;
			$("select[tt='baoxianSelect']").each(function(){ 
				baoxian+=parseInt($(this).val()); 
			});
			if (baoxian > 0) {
				$("#buyUpdateForm").attr("action","/buy/fillTraveler.do");	
			}
		</@s.if>	
	}
	
	function btnFormSubmit() {
		if (subOrders()) {
			<@s.if test='!isLogin()'>
				var cooperationCacheAccount;
				var cooperationType;
				var arrStr = document.cookie.split("; ");
				for (var i = 0; i < arrStr.length; i++) {
					var temp = arrStr[i].split("=");
					if (temp[0] == "cooperationCacheAccount") {
						cooperationCacheAccount = unescape(temp[1]);
					}
					
					if (temp[0] == "orderFromChannel") {
						cooperationType = unescape(temp[1]);
					}
					if(cooperationCacheAccount != null && cooperationCacheAccount != "" && cooperationType != null && cooperationType != ""){
					  break;
					}
				}
				if(cooperationCacheAccount != null && cooperationCacheAccount != "" && cooperationType != null && cooperationType == "qqcb"){
				   cooperationRegisterLogin();
				}else{
				   showRadidLoginDiv();
				}
	    		</@s.if>
	 		<@s.else>
				loginFormSubmit();
	 		</@s.else>
		}
	}
	
	function loginFormSubmit() {
		<!-- if(checkVisitorIsExisted()){ -->
		 	$("#buyUpdateForm").submit();
		<!-- } -->
	}
</script>	