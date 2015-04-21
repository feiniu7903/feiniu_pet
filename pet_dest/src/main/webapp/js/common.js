 /* 采用版本： Google Maps JavaScript API V3 */
       
    // 景区坐标数据
    var beachArray = new Array();
    // 中心坐标
    var centerCoord;
    // 中心坐标
    var centerParamT, centerParamG;
    // 地图对象
    var map;
    // 当前坐标
    var currentLat, currentLog;
    // 当前弹出窗口
    var currentWindow;
    // 当前标记
    var currentMarker;
    // 路线服务
    var directionsService;
    // 路线渲染
    var directionsDisplay;
    // 标记数组
    var markerArray = new Array();
   
    /**
     * 初始化地图 
     */
    function initialize(mapZoom) {
        initData();
        
        //创建中心坐标对象
        var latlng = new google.maps.LatLng(centerParamT, centerParamG); 
        
        var myOptions = { 
                zoom: mapZoom,
                center: latlng, 
                mapTypeId: google.maps.MapTypeId.ROADMAP 
            };
        
        // 创建地图对象
        map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
        directionsDisplay = new google.maps.DirectionsRenderer();
        directionsService = new google.maps.DirectionsService();
        
        // 循环创建景区标记
        for (var i = 0; i < beachArray.length; i++) {
           createMarker(beachArray[i]);
        }
    }
    
    /**
     * 创建景区标记
     * @param {Object} beach
     */
    function createMarker(beach) {      
        // 坐标对象
        var latlng = new google.maps.LatLng(beach[1],beach[2]);
        var image = getIconImages(beach);
        
        if (image!="") {    
            // 创建标记对象
            var marker = new google.maps.Marker({
                    position: latlng,
                    map: map,
                    title: beach[0],
                    icon: getIconImages(beach)
                });
            
            // 标记信息窗口                             
            var simpleInfoWindow = getInfoWindow(beach,"simple");
            setValueToMarkerArray("MK", beach[4], marker);
            setValueToMarkerArray("SIW", beach[4], simpleInfoWindow);
            
            if (beach[5]=="true") {             	
                simpleInfoWindow.open(map,marker);                
            }
            
            // 增加标记事件
            google.maps.event.addListener(marker, 'click', function() { 
                closeCurrentInfoWindow(beach[4]); 
                simpleInfoWindow.open(map,marker);
            });
        }
    }
    
     /**
      * 标记图片
      */
    function getIconImages(beach) {     
        if (beach[3]=="2") {
            if (beach[5]=="true") {
                return "http://www.lvmama.com/dest/img/ticket2.png"
            } else {
                return "http://www.lvmama.com/dest/img/ticket1.png"
            }
        }
        if (beach[3]=="3") {
            if (beach[5]=="true") {
                return "http://www.lvmama.com/dest/img/hotel2.png"
            } else {
                return "http://www.lvmama.com/dest/img/hotel1.png"
            }
        }
        return "http://www.lvmama.com/dest/img/1.png";
    }
    
     // 获取标记信息窗口
    function getInfoWindow(beach, type) {        
        var infowindow ; 
        var contentString;
        var orderTickeUrl; //在线预订门票URL
        if (beach[3]!="2") {
            orderTickeUrl = "http://www.lvmama.com/search/placeSearch.do?keyword=" + beach[0];
        } else {
            orderTickeUrl = "http://www.lvmama.com/dest/" + beach[6] + "/_12_1_1";
        }
        
        var contentHtml = '<div id="mess_title">'+beach[0]+'<a href="http://www.lvmama.com/dest/'+beach[6]+'" target="_blank">(详细信息)</a> </div><p>推荐指数：<strong>50</strong> &nbsp;&nbsp;&nbsp; </p><div id="linkDiv"><p id="mess_que"><a onclick="showInfoWindow(\''+beach+'\');" id="targetRout" href="javascript:void(0)">如何到达？</a>&nbsp;<a href="http://www.lvmama.com/travel/'+beach[6]+'/dish" target="_blank">周围美食住宿推荐</a>&nbsp;<a href="'+orderTickeUrl+'" target="_blank">在线预订门票</a></p>';
        if (type=="simple") {
            contentString = '<div id="mess_box" style="height:70px;*height:90px; width:270px; overflow:hidden">' + contentHtml + '</div></div>';      
        } else {
        	var inputHtml = '<div id="inputDiv_'+beach[4]+'"><p id="mess_que">出发地址：<input type="text" id="searchTextField_'+beach[4]+'" size="27" onFocus="arrive('+beach[4]+','+beach[1]+','+beach[2]+');"><input id="startPlace_'+beach[4]+'" type="hidden"/></p></div>';
        	contentString = '<div id="mess_box" style="height:130px; width:270px;*width:300px; overflow:hidden">' + contentHtml + inputHtml + '</div></div>';
        }
        
        // 生成信息窗口对象
        infowindow = new google.maps.InfoWindow({
               content: contentString
            });
        
        return infowindow;
     }
    
    /**
     * 如何到达
     * @param {Object} placeId
     * @param {Object} lat
     * @param {Object} log
     */
    function arrive(placeId, lat, log) {         
        // 地方字段文本域
        var input = document.getElementById("searchTextField_"+placeId);
        
        // 地方自动填充(当用户从列表中选择了一个“地方”时，系统会返回该“地方”的相关信息)
        var autocomplete = new google.maps.places.Autocomplete(input);
        
        // 地方下拉框触发事件
        google.maps.event.addListener(autocomplete, 'place_changed', function() {           
            var place = autocomplete.getPlace();
            $("#searchTextField_"+placeId).val(place.name);
            
            /*
             * 以下代码是绘制起点和终点线路 */
            directionsDisplay.setMap(map);
            var directionDisplay = new google.maps.DirectionsRenderer();
            var directionsService = new google.maps.DirectionsService();
            var startPlace = lat + "," + log;
            var destPlace = place.geometry.location.Xa + "," + place.geometry.location.Ya;
            
            var request = {
                origin:startPlace,
                destination:place.geometry.location,
                travelMode: google.maps.TravelMode.DRIVING
            };
            directionsService.route(request, function(response, status) {
               if (status == google.maps.DirectionsStatus.OK) {
                  directionsDisplay.setDirections(response);
                }
            });
            closeCurrentInfoWindow(placeId);            
        });
    }
     
     /**
      * 显示新的信息窗口
      * @param {Object} beach
      */
     function showInfoWindow(beach) {
          var bc = beach.split(",");
          var marker = getValueFromMarkerArray("MK", bc[4]);
          var infoWindow = getValueFromMarkerArray("IW", bc[4]);
          
          // 若没有信息窗口，则创建，并加班数组中
          if (infoWindow==null) {
              infoWindow = getInfoWindow(bc,"");
              setValueToMarkerArray("IW", bc[4], infoWindow);
          }
          
          closeCurrentInfoWindow(bc[4]); 
          infoWindow.open(map, marker);
     }
     
      /**
       * 关闭当前弹出窗口
       */ 
     function closeCurrentInfoWindow(placeId) {
         for (var i=0; i<markerArray.length; i++) {
             if (markerArray[i][0]==placeId) {
                 if (markerArray[i][2]!=null) {
                     markerArray[i][2].close();              
                 }
                 if (markerArray[i][3]!=null) {
                     markerArray[i][3].close();
                 }
                 break;
             }
         }
     }
     
    /**
     * 向标记数组中设置值
     */   
    function setValueToMarkerArray(type, placeId, value) {
         for (var i=0; i<markerArray.length; i++) {
             if (markerArray[i][0]==placeId) {
                 if (type=="SIW" && markerArray[i][3]==null) {
                     markerArray[i][3] = value;
                 }else if(type=="IW" && markerArray[i][2]==null) {
                     markerArray[i][2] = value;
                 }else if(type=="MK" && markerArray[i][1]==null) {
                     markerArray[i][1] = value;
                 } 
                 return;
             }
         }
         
         var tempMA = new Array();
         tempMA[0] = placeId;
         if (type=="SIW") {
             tempMA[3] = value;
         }else if(type=="IW") {
             tempMA[2] = value;
         }else if(type=="MK") {
             tempMA[1] = value;
         }
         markerArray[markerArray.length] = tempMA;
     }
    
    /**
     * 根据类型获取标记数组中相应的值
     * @param {Object} type
     * @param {Object} placeId
     * @return {TypeName} 
     */
    function getValueFromMarkerArray(type, placeId) {
         for (var i=0; i<markerArray.length; i++) {
             if (markerArray[i][0]==placeId) {
                 if (type=="SIW" && markerArray[i][3]!=null) {
                     return markerArray[i][3];
                 }else if(type=="IW" && markerArray[i][2]!=null) {
                     return markerArray[i][2];
                 }else if(type=="MK" && markerArray[i][1]!=null) {
                     return markerArray[i][1];
                 } 
             }
         }
    }