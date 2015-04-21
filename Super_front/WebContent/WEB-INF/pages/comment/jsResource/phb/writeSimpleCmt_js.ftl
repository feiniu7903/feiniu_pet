<script type="text/javascript">
$(function(){
	$("#submit").click(function(){
		if ($("#content").val().length < 20) {
			alert('请填写点评内容并不少于20个字');
			return;
		}
		if ($("#content").val().length > 1000) {
			alert('点评内容不得多于1000个字');
			return;
		}	
		var actionUrl = "../comment/writeSimpleCmt!writeComment.do";
		$("#myForm").attr("target","_self");
		$("#myForm").attr("action", actionUrl);
		$("#myForm").submit();
	});

	$(".close").click(function(){
		$("#comment").hide();
		parent.document.getElementById("ban_bg").style.display = "none";
	    parent.document.getElementById("div_comment").style.display = "none";
	});
	
	$(document).keyup(function(){
			var _len=$("#content").val().length;
			$("#showContentLen").text("已输入了"+_len +"个字");
	});
});
</script>

<script type="text/javascript"> 
$(window).load(function(){ 
var frame_name="iframeId"; //iframe的id名称, iframe紧贴外框div高度
var body_name="comment"; //潜入的div的id名称
return iframeResizeHeight(frame_name,body_name,0); 
}) 
function iframeResizeHeight(frame_name,body_name,offset) { 
parent.document.getElementById(frame_name).height=document.getElementById(body_name).offsetHeight+offset; 
} 
</script>
