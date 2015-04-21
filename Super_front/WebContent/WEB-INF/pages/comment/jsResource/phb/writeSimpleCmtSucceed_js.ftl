<script type="text/javascript">
$(function(){
	$("#returnUrl").click(function(){
		parent.document.getElementById("iframeId").src = $('#targetUrl').val();
	});
	
	$(".close").click(function(){
		$("#com_suc").hide();
		parent.document.getElementById("ban_bg").style.display = "none";
	    parent.document.getElementById("div_comment").style.display = "none";
	});

	$("#co_but").click(function(){
		 copyToClipBoard(document.getElementById("url_text").value);
	});
});

function copyToClipBoard(s) {
            if (window.clipboardData) {
                window.clipboardData.setData("Text", s);
                alert("已经复制到剪切板！"+ "\n" + s);
            } else if (navigator.userAgent.indexOf("Opera") != -1) {
                window.location = s;
            } else if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                } catch (e) {
                    alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
                }
                var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
                if (!clip)
                    return;
                var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
                if (!trans)
                    return;
                trans.addDataFlavor('text/unicode');
                var str = new Object();
                var len = new Object();
                var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
                var copytext = s;
                str.data = copytext;
                trans.setTransferData("text/unicode", str, copytext.length * 2);
                var clipid = Components.interfaces.nsIClipboard;
                if (!clip)
                    return false;
                clip.setData(trans, null, clipid.kGlobalClipboard);
                alert("已经复制到剪切板！" + "\n" + s)
            }
        }
</script>

<script type="text/javascript"> 
$(window).load(function(){ 
var frame_name="iframeId"; //iframe的id名称, iframe紧贴外框div高度
var body_name="com_suc"; //潜入的div的id名称
return iframeResizeHeight(frame_name,body_name,0); 
}) 
function iframeResizeHeight(frame_name,body_name,offset) { 
parent.document.getElementById(frame_name).height=document.getElementById(body_name).offsetHeight+offset; 
} 
</script>
