<script type="text/javascript">
//iframe登陆和发点评页面
$(function(){

//鼠标点击之后粉红色的心变成玫红色的心
		$(".text_hz .go").click(function(){
			if(!$(this).hasClass("visited")){
				$(this).removeClass("go").addClass("visited");
			}
		});
});

function votes(num){
	var num=num;
	$.ajax({
		type: "POST",
		url: "http://www.lvmama.com/others/paihangbang/manage.php",
		data:"num="+num,
		success: function(msg){
			//alert(msg);
			if(msg=="Success"){
				document.getElementById('v'+num).innerHTML=parseInt(document.getElementById('v'+num).innerHTML)+parseInt(1);
			}
			else{
				alert("你已点过一次.");
			}
		 }
	 });
}

</script>
