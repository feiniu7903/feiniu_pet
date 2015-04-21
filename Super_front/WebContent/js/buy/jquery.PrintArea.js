(function($) {
	var printAreaCount = 0;
	$.fn.printArea = function() {
		var ele = $(this);
		var idPrefix = "printArea_";
		removePrintArea(idPrefix + printAreaCount);
		printAreaCount++;
		var iframeId = idPrefix + printAreaCount;
		var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;';
		iframe = document.createElement('IFRAME');
		$(iframe).attr({
			style : iframeStyle,
			id : iframeId
		});
		document.body.appendChild(iframe);
		var doc = iframe.contentWindow.document;
		var iframe1 =$(document.getElementById('contractTemplateIframe').contentWindow.document.body).html();
		var iframe2 =$(document.getElementById('contractTemplateIframe1').contentWindow.document.body).html();
		doc.write('<div width="700">' + iframe1+"<br/>"+iframe2
				+ '</div>');
		doc.close();
		if($.browser.msie || $.browser.webkit){
			 var code="<body onload=window.print()>";
				code+=iframe1+"<br/>"+iframe2;
				code+="</body>";
			var newwin=window.open('','',"overflow=auto");
			    newwin.opener = null;
			    newwin.document.write(code+"");
			    newwin.document.close();
			    newwin.close();
		}else{
			var frameWindow = iframe.contentWindow;
			frameWindow.close();
			frameWindow.focus();
			frameWindow.print();
		}
	}
	var removePrintArea = function(id) {
		$("iframe#" + id).remove();
	};
})(jQuery);