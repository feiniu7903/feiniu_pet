if(!basePath) {
	var basePath = "/ebooking";
}
function showLog(id) {
	var url = basePath+"/ebooking/task/ebkTaskLog.do?ebkTaskId=" + id;
	if($("#ebk_log_eject_rz")) {
		$("#ebk_log_bg_opacity1").remove();
		$("#ebk_log_bg_opacity2").remove();
		$("#ebk_log_eject_rz").remove();
	}
	$.ajax({
		url : url,
		success: function(html){
			$("body").append(html);
			var index = $('.rizhi_show').index(this);
			var _hight_w =$(window).height();
			var _hight_t =$('.eject_rz').eq(index).height();
			var _hight   =_hight_w - _hight_t;
			var _top     = $(window).scrollTop()+_hight/2;
			var height_w =$(document).height();
			
			if(_top<0){
				_top = 5;
				if(height_w<_hight_t+32){
					height_w = _hight_t+32;
				}
			}
			
			$('.eject_rz').eq(index).css({'top':_top}).show();
			$('.bg_opacity2').eq(index).css({'height':_hight_t+31,'top':_top-5}).show();
			$('.bg_opacity1').eq(index).css({'height':height_w,'width':$(document.body).width()}).show();
			
			$('.close').click(function() {
				$('.show_hide').hide();
			});
		}
	});
}