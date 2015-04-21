$(function(){
	$("#proviceSelect").change(function(){
		changeCity($(this),'citySelect');
	});
	
	$("select.provinceLoad").change(function(){
		//var val=$(this).val();
		var cityId=$(this).attr("cityId");
		changeCity(this, cityId);
	});
});


function changeCity(obj,cityId){
	var status=$(obj).data('status');
	if(status=="off"){
		return;
	}
	$(obj).data('status','off');
	var val=$(obj).val();
	var $city=$("#"+cityId);
	$city.empty();
	if(val==''){
		var $opt=$("<option>");
		$opt.val("");
		$opt.text("请选择");
		$city.append($opt);
	}else{
		$.post("/pet_back/common/loadCitys.do",{"provinceId":val},function(data){
			var len=data.cityList.length;
			for(var i=0;i<len;i++){
				var city=data.cityList[i];
				var $opt=$("<option>");
				$opt.val(city.cityId);
				$opt.text(city.cityName);
				$city.append($opt);
			}
			$(obj).data('status','on');
		},"json");
	}
}