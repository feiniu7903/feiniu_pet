var LOCAL_STRTORAGE_CURRENT_CITY="local_storage_current_city"; // 当前所在城市 .
var LOCAL_STRTORAGE_GROUPBUY_CITY = "local_storage_groupbuby_city"; // 团购所在城市 
var LOCAL_STRTORAGE_GROUPBUY_CITY_ID = "local_storage_groupbuby_city_id"; // 团购所在城市 
var LOCAL_STRTORAGE_ROUTE_CITY = "local_storage_route_city"; // 线路所在城市 
var DEFAULT_CITY = "上海";
var LOCAL_STORAGE_LAT="local_storage_lat"; // 经度
var LOCAL_STORAGE_LON="local_storage_lon"; // 维度 
var LOCAL_STORAGE_ROUTE_LAT="local_storage_route_lat"; // 经度
var LOCAL_STORAGE_ROUTE_LON="local_storage_route_lon"; // 维度 
var  LT_ORDER_CURRENT_PAY_ORDER_ID = "lt_order_current_pay_order_id";
var latitude;
var longitude;
var currentCity;

// 根据key获取值.
function getLocalStorage(key) {
	try {
		return null == window.localStorage.getItem(key)?"":window.localStorage.getItem(key);
	} catch(err){
		
	}
}

// 设置localStroage
function setLocalStorage(key,value){
	try {
	window.localStorage.setItem(key,value);
	} catch(err){
		
	}
}

//删除根据key.
function removeLocalStorage(key) {
	try {
	window.localStorage.removeItem(key);
	} catch(err){
		
	}
}

// 是否支持sessionStorage
function testSessionStorage() {
	try {
	if (!!window.sessionStorage) {
		testSessionStorage = function() {
			return true;
		};
	} else {
		testSessionStorage = function() {
			return false;
		};
	}
	return testSessionStorage();
	} catch(err){
	
	}
}

// 是否支持localStroage
function testLocalStorage() {
	try {
	if (!!window.localStorage) {
		testLocalStorage = function() {
			return true;
		};
	} else {
		testLocalStorage = function() {
			return false;
		};
	}
	return testLocalStorage();
	} catch(err){
		
	}
}




//获取当前城市,更加城市经度和维度获取城市名称 . 
function initCity(lat,lon,city_constant) {
	currentCity = getLocalStorage(city_constant);
	// 如果当前城市存在 ，直接返回 
	if(null != currentCity && "" != currentCity  && currentCity !== "undefined") {
		initDatas(currentCity);
	} else {
		var param = {"lat":lat,"lon":lon};
		$.ajax({type:"POST", 
			url:contextPath+"/city_info.htm", 
			data:param, 
			dataType:"json", 
			success:function (data) {
				if(data.status=='OK'){
					currentCity = data.result.addressComponent.city;
					if(null != currentCity && "" != currentCity) {
						currentCity = currentCity.replace("市","").replace('县','');
					}
					setLocalStorage(city_constant,currentCity);
					// 加载数据 
					initDatas(currentCity);
				} else {
					initDatas(DEFAULT_CITY);
					setLocalStorage(city_constant,DEFAULT_CITY);
				}
			},
			error:function() {
				initDatas(DEFAULT_CITY);
				setLocalStorage(city_constant,DEFAULT_CITY);
			}
		});
	}
}


/**
 * 数组 去掉重复项. 
 * @returns {Array}
 */
Array.prototype.unique = function() {
	var ret = [];
	var o = {};
	for ( var i = 0, len = this.length; i < len; ++i) {
		if (!o[this[i]]) {
			ret.push(this[i]);
			o[this[i]] = this[i];
		}
	}
	return ret;
};
