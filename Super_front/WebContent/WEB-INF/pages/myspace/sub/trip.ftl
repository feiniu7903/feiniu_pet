<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的旅程-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body  id="page-trip">
    <#include "/WEB-INF/pages/myspace/base/header.ftl"/>
    <div class="lv-nav wrap">
        <p>
            <a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>&gt;
            <a class="current">我的旅程</a>
        </p>
    </div>
    <div class="wrap ui-content lv-bd">
        <#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
        
        <div class="lv-content">
            <div class="ui-box mod-edit travel-records">
                <div class="mylv_tripmain">
                    <img src="http://s3.lvjs.com.cn/img/mylvmama/trip.jpg" alt="开启我的旅程" />
                    <a class="trip1" target="_blank" href="http://www.lvmama.com/trip/start/"></a>
                </div> 			     
                <!-- 行程列表>> -->
                <div class="travel-records-list">
                    <table class="lv-table travel-records-table">
                    <colgroup>
                      <col class="lvcol-1">
                      <col class="lvcol-2">
                      <col class="lvcol-3">
                      <col class="lvcol-4">
                    </colgroup>
                    <thead>
                        <tr class="thead">
                            <th>我的旅程</th>
                            <th>出游时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        <tr class="sep-row">
                            <td colspan="4"></td>
                        </tr>
                     </thead>
                     
                     <tbody class="tbody" id="trip_data_body">
                       </tbody>
                    </table>
                </div>
                <div class="travel-records-list" align="right">
                    <span><a href="http://www.lvmama.com/public/help">求帮助</a></span>
                </div>
                <!-- <<行程列表 -->
            </div>
            
            <div class="ui-tab-title"><h3>精彩旅程推荐</h3></div>
            <div class="trip-list">
                <ul class="hor"> 
                    <li><a target="_blank" title="西北环游记" href="http://www.lvmama.com/trip/show/1089"><img src="http://pic.lvmama.com/img/mylvmama/trip_pic1.jpg" width="169" height="224" alt="" /></a></li>
                    <li><a target="_blank" title="活色生香魅力香港 " href="http://www.lvmama.com/trip/show/663"><img src="http://pic.lvmama.com/img/mylvmama/trip_pic2.jpg" width="169" height="224" alt="" /></a></li>
                    <li><a target="_blank" title="日本六日游" href="http://www.lvmama.com/trip/show/609"><img src="http://pic.lvmama.com/img/mylvmama/trip_pic3.jpg" width="169" height="224" alt="" /></a></li>
                    <li><a target="_blank" title="问道武当山" href="http://www.lvmama.com/trip/show/953"><img src="http://pic.lvmama.com/img/mylvmama/trip_pic4.jpg" width="169" height="224" alt="" /></a></li>
                </ul>
            </div>
        </div>
    </div>

<script type="text/javascript">
	$(function(){
		$(document.body).ready(function(){
			$.getJSON("http://www.lvmama.com/trip/api/mytrips?format=json&callback=?" ,function (data){ 
			if (data.code="200") {
				var dataArray = data.data.data,$tripContainer = $("#trip_data_body"),html='';
				$(dataArray).each(function(i,item){
					html += '  <tr>\
								<td><a href="'+item.url+'" class="lv_mytrip_titlink" target="_blank">'+item.title+'</a></td>\
								<td>'+item.trip_time+'</td>\
								<td>'+item.status+'</td>\
								<td><a href="'+item.eurl+'" class="lv_mytrip_btnS" target="_blank">编辑</a> \
									<a href="javascript:void(0);" onclick="javascript:delTrip('+item.trip_id+', this);" class="lv_mytrip_btnS">删除</a>\
								</td>\
							  </tr>';
					
				});
				$tripContainer.html(html);
			
			}
			}); 
		});
	});
    function delTrip(tripId, obj) { 
        if(confirm('确定要删除该行程么？')){ 
            $.ajax({ 
                type: "POST", 
                url: "http://www.lvmama.com/trip/api/deleteMyTrip/" + tripId, 
                async: false, 
                dataType: "json", 
                success: function(response) { 
                    alert(response.message); 
                    if (response.code == '200') { 
                        $(obj).parent().parent().remove(); 
                    } 
                } 
            }); 
        } 
    }

	</script>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	

<!-- 封住窗口的弹出框  -->	
<div class="xh_overlay"></div>
	<script>
		cmCreatePageviewTag("我的旅程", "D0001", null, null);
	</script>
</body>
</html>