
document.onkeydown = function (event_e) {
	if (window.event) {
		event_e = window.event;
	}
	var int_keycode = event_e.charCode || event_e.keyCode;
	if (int_keycode == 116) {
		if ($.browser.msie) {
			event_e.keyCode = 0;
			event_e.cancelBubble = true;
			return false;
		}
		return false;
	}
};
