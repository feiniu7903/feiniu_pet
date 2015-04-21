/**门票和线路点击预订按钮*（qing.lvmamam.com）
 * @param productId
 * @param branchId
 * @param canOrderToday
 */
function product_order_skip(productId,branchId,canOrderToday) {
	var url=contextPath+'/order/order_fill.htm?productId='+productId+'&branchId='+branchId+'&canOrderToday='+canOrderToday;
	bookBaiduLoginStatus(url);
}
function baiduMyOrder(){
	var url=contextPath+"/order/myorder.htm";
	bookBaiduLoginStatus(url);
}
/**
	 * 判断是否百度登陆
	 */
	function bookBaiduLoginStatus(callUrl){
		//提交操作
	    var url = contextPath+'/bdorder/baidu_login_status.htm';
	    
		$.ajax({
			url : url,
			type : "POST",
			success : function(data) {
				// 百度已经登录
				if(data!=null && data.status=="0") {
					union_skip(callUrl);
				} else {//未登录
					if(loginStatus){
						book_baidu_login(callUrl);
					}else{
						globe.lvToast(false,"页面尚未加载完，请耐心等待...",LT_LOADING_CLOSE);
					}
				}
			},
			error:function() {
				globe.lvToast(false,"哎呀,网络不给力,请稍后再试试吧",LT_LOADING_CLOSE);
			}
		});
	}
	function onSuccess() {
		clouda.mbaas.account.closeLoginDialog();	
		//union_skip(contextPath+'/order/myorder.htm');
		var callUrl=$("#callUrlBaidu").val();
		callBack(callUrl);
	}
	function callBack(callUrl){
		//clouda.mbaas.account.closeLoginDialog();	
		$("#wrapper").show();
		window.parent.location.href ="http://qing.lvmama.com"+callUrl;
	}
	function onFail() {
		//clouda.mbaas.account.closeLoginDialog();	
		$("#wrapper").show();
		//alert('登陆失败');
	}
	function book_baidu_login(callUrl){
		 var options = 
	     { 
	     'redirect_uri': "http://qing.lvmama.com/static/baidulogin.html", 
	     'scope': 'basic',
	     'login_type': 'sms', 
	     'mobile': '', 
	     'display':'mobile', 
	     'login_mode': 1,
	     'onsuccess':onSuccess, 
	     'onfail': onFail
	     }; 
		 $("#callUrlBaidu").val(callUrl);
		 $("#wrapper").hide();
	     clouda.mbaas.account.login(options);
	}
	