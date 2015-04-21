var clockArray = new Array();
function clockHandler(){
	for(var i=0;i<clockArray.length;i++){
			clockArray[i]();
	}
}
window.x_init_hook_clock = function () {
    if (jQuery("span.deal-timeleft").length == 1 && jQuery("span[num]").length == 0) {
    
        var a = parseInt(jQuery('span.deal-timeleft').attr('diff'));
        if (!a > 0) return;
        var b = (new Date()).getTime();
        var e = function () {
            var c = (new Date()).getTime();
            var ls = a + b +86400000- c;
            if (ls > 0) {
                var ld = parseInt(ls / 86400000); ls = ls % 86400000;
                var lh = parseInt(ls / 3600000); ls = ls % 3600000;
                var lm = parseInt(ls / 60000); ls = ls % 60000;
                var ls = (ls % 60000 / 1000).toFixed(0);
	
                if (ld > 0) {
                    var html = '<span>' + ld + '</span>天<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                } else {
                    var html = '<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                }
                
                jQuery('#counter').html(html);
            } else {
                jQuery("#counter").stopTime('counter');
                jQuery('#counter').html('团购已结束');
                window.location.reload();
            }
        };
       	window.setInterval(e,1000);
    }
    else {
        jQuery("span.deal-timeleft").each(function () {
            var a = parseInt(jQuery(this).attr('diff'));
            var numid = jQuery(this).attr("num");
            if (!a > 0) return;
            var b = (new Date()).getTime();
            var e = function () {
                var c = (new Date()).getTime();
                var ls = a + b +86400000- c;
                if (ls > 0) {
                    var ld = parseInt(ls / 86400000); ls = ls % 86400000;
                    var lh = parseInt(ls / 3600000); ls = ls % 3600000;
                    var lm = parseInt(ls / 60000);
                    var ls = (ls % 60000 / 1000).toFixed(0);
                    if (ld > 0) {
                        var html = '<span>' + ld + '</span>天<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                    } else {
                        var html = '<span>' + lh + '</span>时<span>' + lm + '</span>分<span>' + ls + '</span>秒';
                    }
                    jQuery('#counter' + numid).html(html);
                } else {
                    jQuery('#counter' + numid).html('团购已结束');
                    window.location.reload();
                }

            };
 			clockArray.push(e);
        });
        window.setInterval(clockHandler,1000);

    }

};

$(document).ready(function(){
	window.x_init_hook_clock();
})