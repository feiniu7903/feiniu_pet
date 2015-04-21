$(function(){
	$("#searchPlace").jsonSuggest({
		url:"/super_back/prod/searchPlace.do",
		maxResults: 20,
		minCharacters:1,
		onSelect:function(item){
			$("#comPlaceId").val(item.id);
		}
	});
	function fillPlace(place){
		var $tr=$("<tr/>");
		$tr.attr("id","tr_"+place.productPlaceId);
		$tr.attr("productPlaceId",place.productPlaceId);
		
		var $td=$("<td/>");
		$td.html(place.placeId);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(place.placeName);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.addClass("from");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.addClass("to");
		$tr.append($td);
		
		
		$td=$("<td/>");
		$td.html("<a href='#delete' class='delete'>删除</a>&nbsp;<a href='#from' class='changeFT' type='FROM'>出发地</a><a href='#to' class='changeFT' type='to'>目的地</a>");
		$tr.append($td);
		
		$("#place_tb").append($tr);
	}
	
	$("a.changeFT").live("click",function(){
		var $this=$(this);
		var type=$this.attr("type");
		var $tr=$this.parent().parent();
		var productPlaceId=$tr.attr("productPlaceId");
		if($.trim(productPlaceId)==''){
			alert("操作的标地不存在");
			return false;
		}
		if($.trim(type)==''){
			alret("操作的类型不存在");
			return false;
		}
		
		$.post("/super_back/prod/changeProductFT.do",{"productPlaceId":productPlaceId,"target":type},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				var t=type.toLowerCase();
				$("td."+t).each(function(i,n){
					$(this).html("");
				});
				$("#tr_"+productPlaceId).find("td."+t).html("true");
			}else{
				alert(data.msg);
			}
		});
	});
	$("a.delete").live("click",function(){
		var $this=$(this);
		var type=$this.attr("type");
		var $tr=$this.parent().parent();
		var productPlaceId=$tr.attr("productPlaceId");
		if($.trim(productPlaceId)==''){
			alert("操作的标地不存在");
			return false;
		}
		
		$.post("/super_back/prod/deletePlace.do",{"productPlaceId":productPlaceId},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$tr.remove();
			}else{
				alert(data.msg);
			}
		});
	});
	$("input.addPlace").click(function(){
		var str=$(this).attr("ff");
		var $form=$("form[name="+str+"]");
		var placeId=$form.find("input[name=place.placeId]").val();
		if($.trim(placeId)==''){
			alert("没有选中标的信息");
			return false;
		}
		
		$.ajax({
			url:"/super_back/prod/editProductPlace.do",
			data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					fillPlace(data.place);
					$("#comPlaceId").val('');
					$("#searchPlace").val('');
				}else{
					alert(data.msg);
				}
			}
		})
	});
})