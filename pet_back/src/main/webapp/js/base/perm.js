$(function(){
	var $tooltip;
	
	$(document).ready(function(){
		$tooltip=$("<div style='position:absolute;border:1px solid #ccc;padding:2px;background-color:#ffceef;display:none'></div>");
		$("body").append($tooltip);
	});
	
	function showPermDetail($this,data){
		var body="";
			body+="<div>编号:"+data.userId+"</div>";
			body+="<div>姓名:"+data.realName+"</div>";
		var offset=$this.offset();
		
		$tooltip.css("top",(offset.top+8)+"px");
		$tooltip.css("left",(offset.left+$this.width()-2)+"px")
		$tooltip.html(body).show();
		//alert(body);
		//alert($tooltip.html());
	}
	
	
	
	$("span.perm").live("mouseover",
		function(){
			var $this=$(this);
			var userName=$this.text();
			if($.trim(userName)){
				$this.die("mouseover");
				$this.die("mouseout");
			}
			var perm=$this.data("perm");
			if(!perm){//如果已经存在直接使用
				var userName=$this.text();
				$.post("/super_back/common/permDetail.do",{"userName":userName},function(dt){
					//alert(dt);
					var data=eval("("+dt+")");
					if(data.success){
						$this.data("perm",data);
						showPermDetail($this,data);
					}
				});
			}else{
				showPermDetail($this,perm);
			}
		}).live("mouseout",
		function(){
			if($tooltip){
				$tooltip.empty().hide();
			}
		});
})