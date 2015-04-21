<script type="text/javascript" src="http://pic.lvmama.com/js/copy/ZeroClipboard.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".coupon_description").each(function(){var txt=$(this).html();$(this).html(txt.replace(/\r\n/g,"<br/>").replace(/\r/g,"<br/>").replace(/\n/g,"<br/>"));});
		ZeroClipboard.setMoviePath("http://pic.lvmama.com/js/copy/ZeroClipboard.swf");
		
		$(".copyCouponCode").mouseover(function() {
			var couponCode = $(this).parent().siblings(".couponCode").text();
			//alert(couponCode);
			//copyToClipboard(couponCode);
			clip = new ZeroClipboard.Client();
			clip.setHandCursor(true);
			//clip.addEventListener('load', function (client) {
			//	alert("Flash movie loaded and ready.");
			//});
			clip.addEventListener('mouseover', function (client) {
				// update the text on mouse over
				clip.setText(couponCode);
			});
			clip.addEventListener('complete', function (client, text) {
				alert("复制成功！");
			});
			clip.glue(this);
		});
	});
	function copyToClipboard(txt) {
		if (window.clipboardData) {
			window.clipboardData.clearData();
			window.clipboardData.setData("Text", txt);
		} else if (navigator.userAgent.indexOf("Opera") != -1) {
			window.location = txt;
		} else if (window.netscape) {
			try {
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	        } catch (e) {
	        	alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
	        }
	        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
	        if (!clip) return;
	        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
	        if (!trans) return;
	        trans.addDataFlavor('text/unicode');
	        var str = new Object();
	        var len = new Object();
	        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
	        var copytext = txt;
	        str.data = copytext;
	        trans.setTransferData("text/unicode",str,copytext.length*2);
	        var clipid = Components.interfaces.nsIClipboard;
	        if (!clip) return false;
	        clip.setData(trans,null,clipid.kGlobalClipboard);
	        alert("复制成功！");
        }
	}
</script>