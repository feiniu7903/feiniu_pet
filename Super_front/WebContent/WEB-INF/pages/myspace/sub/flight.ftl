<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>国际机票订单-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body  id="page-flight">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>&gt;
				<a class="current">国际机票订单</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 订单列表>> -->
                <div class="ui-box mod-plist">
                    <div class="ui-box-title">
                        <span class="view_more fr">非登录直接预订的用户，请通过预订手机号进行查询 <a target="_blank" class="B" href="http://flight.lvmama.com/findorder.php">手机号查询订单</a></span>
                        <h3>国际机票订单</h3>
                    </div>
                    <div class="hr_b"></div>
                    <table class="lv-table guide-table">
                    <thead>
                        <tr class="thead">
                            <th>订单号</th>
                            <th>行程</th>
                            <th>航程</th>
                            <th>预订时间</th>
                            <th>订单状态</th>
                            <th>订单金额</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody class="FOListBody">
                        <!--
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td class="price"></td>
                            <td><a href="#">查看</a></td>
                        </tr>
                        -->
                        <tr>
                            <td class="tc" colspan="7">您当前暂无预订机票</td>
                        </tr>
                    </tbody>
                </table>
                
                
            </div>
            <!-- <<订单列表 -->
                
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	

<!-- 封住窗口的弹出框  -->	
<div class="xh_overlay"></div>

<script>
$(function(){
    /*
	var jsonStr={
	"orders":[//订单列表(可能多个)
		{
			"id":110,//订单号
			"orderTime":"2013-11-10 09:30:11", 	//订单日期
			"status":1,													//订单状态：0-待申请 1-订座成功 2-支付成功 3-出票成功 4-退票成功 5-取消成功 6-退款成功 7-提交退票 8-供应商退款
			"isLook":0,													//0:未锁定,1:锁定
			"totalFare":2280,   								//总价		
			"route":"首都国际机场-虹桥机场-香港机场",			//航程
			"fromtoCity":"北京-香港",				//出发到达城市,行程
			"tripType":0,				//0:单程,1:往返,2:多程	
			"fromDate":"2013-05-22",	//出发日期
			"toDate":	"2013-05-25"		//到达日期
		},
		{
			"id":220,//订单号
			"orderTime":"2013-11-10 09:30:11", 	//订单日期
			"status":1,													//订单状态：0-待申请 1-订座成功 2-支付成功 3-出票成功 4-退票成功 5-取消成功 6-退款成功 7-提交退票 8-供应商退款
			"isLook":0,													//0:未锁定,1:锁定
			"totalFare":2280,   								//总价		
			"route":"首都国际机场-虹桥机场-香港机场",			//航程
			"fromtoCity":"北京-香港",				//出发到达城市,行程
			"tripType":0,				//0:单程,1:往返,2:多程	
			"fromDate":"2013-05-22",	//出发日期
			"toDate":	"2013-05-25"		//到达日期
		}		
	]
   };
   */
   var _userid = '';
   try{
        _userid = decodeURIComponent(document.cookie).match(/\^!\^+[a-z|0-9]*/);
    }catch(e){
        _userid = unescape(document.cookie).match(/\^!\^+[a-z|0-9]*/);
    }
    _userid = _userid[0].replace('^!^','');
    
    $.ajax({
         type: "get",
	     async: false,
	     url: 'http://flight.lvmama.com/api/lvmamaorder.php?userid=' + _userid, 
	     dataType: "jsonp",
	     jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
	     jsonpCallback:"jsonFlickrFeed",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
	     success: function(jsonStr){
	        loadDataList(jsonStr);
	     },
	     error: function(){
	         alert('fail');
	     }
        
    });

    function loadDataList(jsonStr){
        var listHtml='';
        var statusArr = ['待申请','订座成功','支付成功','出票成功','退票成功','取消成功','退款成功','提交退票','供应商退款'];
        $.each(jsonStr.orders,function(index,jsonItem){
            //alert(jsonItem.SD)
            listHtml+='<tr>\
                  <td>'+jsonItem.id+'</td>\
                  <td>'+jsonItem.fromCity+'-'+jsonItem.toCity+'</td>\
                  <td>'+jsonItem.route+'</td>\
                  <td>'+jsonItem.orderTime+'</td>\
                  <td>'+statusArr[jsonItem.status]+'</td>\
                  <td class="price">&yen;'+jsonItem.totalFare+'</td>\
                  <td><a href="http://flight.lvmama.com/vieworder.php?id='+jsonItem.id+'">查看</a></td>\
               </tr>';
                
                
        })//each
        $(".FOListBody ").html(listHtml);
        //$("<div class='lv_pageWrap'></div>").append($(".orderList "));
        $(".FOListBody ").parent('table').after($("<div class='lv_pageWrap'></div>"));
        var lv_page1=new lv_page({
            $list:$(".FOListBody  tr"),
            $pageWrap:$(".lv_pageWrap"),
            pSize:5
        });
        lv_page1.start();
    };
})
</script>
<script src="http://pic.lvmama.com/js/mylvmama/lv_page.js"></script>
<script>
      cmCreatePageviewTag("国际机票订单-列表", "D0001", null, null);
 </script>
</body>
</html>