window.m_place_search_result = "m_place_search_result"; // 景点搜索结果 . 

$(function() {
	// 自动补全查询
	$("#keyWorld").keyup(function() {
		searchAutoComplete();	
		var _clearVal = $(this).val();
		if(_clearVal != ''){
			$('#clear_password').show();
		}else {
			$('#clear_password').hide();
		}
	});
	// 初始化搜索记录 . 
	initSearchTip();
});
var _clearValload = $('#keyword').val()
if(_clearValload != ''){
	$('#clear_password').show();
}else {
	$('#clear_password').hide();
}
$('#clear_password').live('click',function(){
	if(_clearValload != ''){
		$('#clear_password').show();
	}else {
		$('#clear_password').hide();
	}
});



// 点击列表 
function searchTip(name) {
	$("#keyWorld").val(name) ;
	search();
}

/**
 * 查询 
 */
function search() {
	var stage = $("#stage").val();
	var history_search = window.m_place_search_result + stage;
	var keyworld = $("#keyWorld").val();
	alert(keyworld);
	if($.trim(keyworld) == '') {
		$("input.lv-input-search").parent().parent().addClass("lv-input-focus");
		
		return false;
	} else {
		// 保存localSession
		if(testSessionStorage()) {
			window.localStorage.setItem(history_search,keyworld +","+ getLocalStroage(history_search));
		}
		$("#searchForm").submit();
	}
}

function initSearchTip() {
	var stage = $("#stage").val();
	var history_search = window.m_place_search_result + stage;
	var his_search = getLocalStroage(history_search);
	if(null != his_search) {
		var datasArray = his_search.split(',');
		if(null != datasArray) {
			datasArray = datasArray.unique();
			if(datasArray.length > 0) {
				var str = [];
				for(var i = 0 ;i < datasArray.length && i < 10;i++) {
					if(null != datasArray[i] && '' != datasArray[i]) {
						str.push("<li onclick=\"searchTip('"+datasArray[i]+"')\">"+datasArray[i]+"</li>");
						//str.push("<div class=\"lv-seprator-line\"></div>");
					}
				}
				
				if(null != str && str.length > 0) {
					str.push("<li class=\"lv-search-li\" style='text-align: center;margin-top: 20px;' onclick=\"removeLocalStroage('"+history_search+"')\">清除历史记录</li>");
				}
				$("#search_success").html(str.join(""));
				$("#search_success").show();
				$("#search_fail").hide();
			}
		}
	}
}

// 根据key获取key.
function getLocalStroage(key) {
		return null == window.localStorage.getItem(key)?"":window.localStorage.getItem(key);
}

//删除根据key.
function removeLocalStroage(key) {
	window.localStorage.removeItem(key);
	initSearchTip();
}

// 是否支持sessionStorage
function testSessionStorage() {
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
}

// 是否支持localStroage
function testLocalStorage() {
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
}

//搜索. 
function searchAutoComplete() {
	var keyworld = $("#keyWorld").val();
	if($.trim(keyworld) == '') {
		return false;
	}
	var param = {"keyWorld":encodeURIComponent(keyworld),"stage":$("#stage").val()};
	$.ajax({type:"POST", url:contextPath+"/search_auto_complete", data:param, dataType:"json", success:function (data) {
		if(data.code=='success'){
			var datas = data.datas;
			if(null != datas) {
				datas = datas.unique();
				if(datas.length > 0) {
					initDatas(datas);
				} 
			}else {
				$("#search_success").hide();
				$("#search_fail").show();
			}
		}else{
			$("#search_success").hide();
			$("#search_fail").show();	
		}
	}});
}

function initDatas(datasArray) {
	var str = [];
	for(var i = 0 ;i < datasArray.length && i < 10;i++) {
		var obj = datasArray[i];
		str.push("<li onclick=\"searchTip('"+obj.name+"')\">"+obj.name+"</li>");
		//str.push("<div class=\"lv-seprator-line\"></div>");
	}
	$("#search_success").html(str.join(""));
	$("#search_success").show();
	$("#search_fail").hide();
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
}

