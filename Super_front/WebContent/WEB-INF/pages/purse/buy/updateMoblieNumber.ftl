<@s.if test='comUserId!=null&&comUserId!=""'>
<input name="comUserId" type="text" id="comUserId" value="${comUserId}" />
<script type="text/javascript">
$(document).ready(function(){
	$("#sub_button").bind("click",function() {
			var mobile = $("#buyTicketMobile").val();
			var userId = $("#comUserId").val();
			if(checkMobile(mobile)){
				alert(mobkile+"   asdf "+userId);
				$.getJSON("http://login.lvmama.com/nsso/cooperation/facade!updateMobileNumber.do?jsoncallback=?",
				{
					mobile_number:mobile,
					user_id:userId
				}
				,function(json){
				});
			}
	})
});
</script>
</@s.if>
