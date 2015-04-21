$(function(){

	//popupModal(true, '<span>产品id</span><em>400-2122-21212</em>', calback, 50, true);
	/*right box*/
	$('body').delegate('ul[_tabBox] li','click',function(){
		$('section[_sliceTab]').addClass('sliceTab');
	});
	
	/*input clear*/
	inputClear();
	
});

/*
popup
isSuccess-----(true, false)
callBack ------function a(){}
time is num
toastText is string
buttonBoole is button boole
*/
function popupModal(isSuccess, toastText, callBack, time, buttonBoole){
var _popupBox = $('div[_popup]');
var _modal = $('div[_modal]');
	var _top = 170;
	var _modalHeight = $(document).height();
	if(typeof(callBack) === 'function'){
		callBack();
	}
	if(isSuccess){
		setTimeout(function() {
			_popupBox.css({
					top: _top
				}).fadeIn();
			_modal.css({
				'height': _modalHeight
			}).fadeIn();
			_popupBox.find('article p').html(toastText);
		}, time);
	}
	if(!isSuccess){
		setTimeout(function() {
				_popupBox.fadeOut();
				_modal.fadeOut();
		}, time);
	}
	/*button defult is show and false*/
	if(buttonBoole){
		_popupBox.find('.ic_yellow').hide();
	}else{
		_popupBox.find('.ic_yellow').show();
	}
	 $('body').delegate('div[_modal]', 'click', function(){
//		  _popupBox.fadeOut();
//		  _modal.fadeOut();
	 });
}
function popupModalItem(obj, isSuccess, toastText, callBack, time, buttonBoole){
	var _popupBox = obj;
	var _modal = $('div[_modal]');
		var _top = $(document).scrollTop()+180;
		var _modalHeight = $(document).height();
		if(typeof(callBack) === 'function'){
			callBack();
		}
		if(isSuccess){
			setTimeout(function() {
				_popupBox.css({
						top: _top,
					}).fadeIn();
				_modal.css({
					'height': _modalHeight
				}).fadeIn();
				_popupBox.find('article p').html(toastText);
			}, time);
		}
		if(!isSuccess){
			setTimeout(function() {
					_popupBox.fadeOut();
					_modal.fadeOut();
			}, time);
		}
		/*button defult is show and false*/
		if(buttonBoole){
			_popupBox.find('.ic_yellow').hide();
		}else{
			_popupBox.find('.ic_yellow').show();
		}
		 $('body').delegate('div[_modal]', 'click', function(){
//			  _popupBox.fadeOut();
//			  _modal.fadeOut();
		 });
	}
	/*
		input clear
		@inputVal is input val
		@inputPlaceholder is input attribute
		@_inputObj is obj
	*/
	function inputClear(){
		var inputVal, inputPlaceholder, _inputObj;
		_inputObj = $('input');
		inputVal = _inputObj.val();
		inputPlaceholder = _inputObj.attr('placeholder');
		if(inputVal !='' && inputVal != inputPlaceholder){
			_inputObj.each(function(i) {
				var _index = i;
				var numObj = _inputObj.eq(_index);
				var _valI = _inputObj.eq(_index).val();
				if(_valI !=''){
					numObj.next('.ic_delete').show();
				}
				$('body').delegate('input', 'keyup',function(){
					inputVal = numObj.val();
					if(inputVal != ''){
						numObj.next('.ic_delete').show();
					}else {
						numObj.next('.ic_delete').hide();
					}
				});
				$('body').delegate('input', 'keydown',function(){
					inputVal = numObj.val();
					if(inputVal == ''){
						numObj.next('.ic_delete').hide();
					}else {
						numObj.next('.ic_delete').show();
					}
				});
				
			});
		  $('body').delegate('.ic_delete', 'click',function(){
			   $(this).hide();
			   $(this).prev('input').val('');
		  });
		}
	}
