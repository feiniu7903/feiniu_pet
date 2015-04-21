<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<ul class="left_nav">
   	<li class="active"><a href="/help/join" target="_self"><i class="icon01"></i><span class="icon_jt1"></span>分销商入驻</a></li>
     <li><a href="/help/announcement" target="_self"><i class="icon02"></i><span class="icon_jt"></span>平台公告</a></li>
     <li><a href="/help/protocol" target="_self"><i class="icon03"></i><span class="icon_jt"></span>合作协议</a></li>
     <li><a href="/help/faq" target="_self"><i class="icon04"></i><span class="icon_jt"></span>常见问题</a></li>
     <li><a href="/help/contractUs" target="_self"><i class="icon05"></i><span class="icon_jt"></span>联系我们</a></li>
</ul>
<script type="text/javascript">
	$(function() {
		$(".left_nav").find("li").click(function() {
			$(".left_nav").find("li").removeClass("active");
			$(this).addClass("active");
			query($(this).find("a").attr("href"));
			return false;
		});

		query("/help/join");
	});
	function query(url) {
		$.ajax({
			type : "get",
			url : url,
			success : function(response) {
				$(".main_r").html(response);
			}
		});
	}
</script>
