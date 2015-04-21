// JavaScript Document

$(function(){
	$('.close').click(function(){
		$('.js_zs').hide();
		$('.xh_overlay').remove();
			   });
	$('.open').click(function(){
		$('.js_zs').show();
							  });
	
	$('#bigImageButton').click(function() {
		$('#message').html("亲！！！开始上传大图了");
		$('#uploadFormDiv').show();
		$('#type').val('LARGE');
		$('#placePhotoId').val();
	});
	$('#middleImageButton').click(function() {
		var tr=$("#image-table-list").find("tr[data-type='MIDDLE']");
		if(tr.length>=1){
			$('#uploadFormDiv').hide();
			alert('中图只能上传一张');
			return;
		}
		
		$('#uploadFormDiv').show();
		$('#message').html("亲！！！开始上传中图了，注意大小！");
		$('#type').val('MIDDLE');
		$('#placePhotoId').val();
	});
	$('#smallImageButton').click(function() {
		var tr=$("#image-table-list").find("tr[data-type='SMALL']");
		if(tr.length>=1){
			$('#uploadFormDiv').hide();
			alert('小图只能上传一张');
			return;
		}
		
		$('#uploadFormDiv').show();
		$('#message').html("亲！！！开始上传小图了，注意大小！");
		$('#type').val('SMALL');
		$('#placePhotoId').val();
	});
	$('.imageChange').click(function() {
		$('#uploadFormDiv').show();
		$('#message').html("亲！！！你正在做的是，图片替换哦！");
		$('#placePhotoId').val($(this).attr('alt'));
	});
	
	$('.hover_img').hover(function(){
		$('.img_big').show()
		var  _src     = $(this).attr('src_dz')
		var   _img    = $('.img_big img');
		_img.attr('original',_src)
		getImgSize(_img);
		var _top      = $(this).offset().top;
		var _left     = $(this).offset().left;
		$('.img_big').css({'left':_left-184,'top':_top+35});
								   },function(){
		$('.img_big').hide();
		//$('.img_big img').css({'width':''})
								   });
		   
		   function getImgSize(thisimg) { 
				var i = new Image(); 
				var _thisimg = thisimg;
				var _src=_thisimg.attr("original");
				i.onload = function() {
				if(_thisimg.attr("src")!=_thisimg.attr("original")){_thisimg.attr("src",_thisimg.attr("original"))};
				autoImgSize(i.width,i.height,_thisimg);
				i.onload=function(){};
			};
			i.src=_src;
		}
		   
		   function autoImgSize(img_tw,img_th,_thisimg){//�Զ����
		var _img_tw = img_tw;
		var _img_th = img_th;
		var thisimg = _thisimg;
		var x = thisimg.parent().innerWidth(); 
		//var y = thisimg.parent().innerHeight(); 
		var w=_img_tw, h=_img_th;
		if (w > x) { //ͼƬ��ȴ���Ŀ����ʱ
			var w_original=w, h_original=h;
			h = h * (x / w); 
			w = x; //��ȵ���Ԥ�����
		}
		$(thisimg).attr({width:w,height:h});
	};
 
});


function openWin(winId){
	  var value = '<iframe class="xh_overlay"></iframe>';
	      $("#"+winId).after(value);
	  var t_height=$("#"+winId).height();
	  var c_height=$(window).height();
	  var t_top   =(c_height-t_height)/2;
	  var t_top2  =$(document).scrollTop();
	$(".xh_overlay").show().height($(document).height());
	  $("#"+winId).show().css({'top':t_top});
	  if(t_height>c_height){$("#"+winId).css({'top':t_top2})};
}

function closeWin(winId){
	var _id="#"+winId;
	$(_id).hide();
	$('.js_zs').hide();
	$('.xh_overlay').remove();
}