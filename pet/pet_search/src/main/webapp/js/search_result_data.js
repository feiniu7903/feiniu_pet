$(function(){
	if($("#cmt_data_div").length>0){
		var placeId = $("#cmt_data_div").attr("placeId");
		$.ajax({
			url : "http://www.lvmama.com/search/ajax/cmt.do?placeId="+ placeId,
			success : function(data) {
				if(data!=""){
					$("#cmt_data_div").html(data);
				}else{
					$("#cmt_data_div").parent().remove();
				}
			},
			error : function() {
				$("#cmt_data_div").parent().remove();
			}
		});
	}
	if($("#guide_data_div").length>0){
		var placeId = $("#guide_data_div").attr("placeId");
		var official_Guide = true;
		$.ajax({
			url: "http://www.lvmama.com/guide/ajax/api.php?action=getOrgInfo&id="+placeId, 
			dataType:"jsonp", 
			success: function(res){
				var data = res.data;
				if(data != null && data!=""){
					var g1 ="<div class=\"imgtext clearfix\">"+
	                "<p class=\"img\"><a target=\"_blank\" href=\""+data.url+"\"><img src=\""+data.thumb+"\" width=\"89\" height=\"124\"></a></p>"+
	                "<p>攻略版本：<br>"+data.version+"<br><br>更新时间：<br>"+data.updatetime+"<br>"+
	                "<a target=\"_blank\" class=\"abtn abtn-gray\" target=\"_blank\" href=\""+data.url+"\">去下载</a>"+
	                "</p></div>";
					$("#guide_data_div").find("#guide_title_a").attr("href",data.url);
					$("#guide_data_div_loading").before(g1);
				}else{
					official_Guide=false;
				}
				$.ajax({
					url: "http://www.lvmama.com/guide/ajax/api.php?action=getOrgLatestArticle&id="+placeId, 
					dataType:"jsonp", 
					success: function(res2){
						if(res2.data != null && res2.data!=""){
							var g2 = " <ul class=\"guide-list\">";
							$.each(res2.data,function(i,n){
								g2 = g2+
								" <li class=\"guide-item\"><a target=\"_blank\" href=\""+n.url+"\">"+n.title+"</a>"+
								" <span class=\"guide-item-info\">"+n.inputtime+"&nbsp;&nbsp;&nbsp;&nbsp;浏览"+n.hits+"次</span></li>";
							});
							g2 = g2+ "</ul>";
							$("#guide_data_div_loading").before(g2);
							$("#guide_data_div_loading").remove();
						}else if(!official_Guide){
							$("#guide_data_div").hide();
						}
					},
					error: function(){
						$("#guide_data_div_loading").remove();
						if(!official_Guide){
							$("#guide_data_div").hide();
						}
					}
				});
			},
			error: function(){
				$("#guide_data_div").hide();
			}
		});
	}
});