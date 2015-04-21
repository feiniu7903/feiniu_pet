$(document).ready(function() {

	if ($('#currentContentPageTypeId').val() != null
			&& $('#currentContentPageTypeId').val() != "") {

		var contentPageTypeId = $('#currentContentPageTypeId').val();
        var bussinessHref = "/public/help_"+contentPageTypeId;
		$('#businessTypeLi').html(
				'> <a href="'+bussinessHref+'">' + $('#businessTypeName' + contentPageTypeId)
						.val() + '</a>');
		$('#contentTypeLi').html(
				'> ' + $('#contentTypeName' + contentPageTypeId).val());
		$('#contentTypeH2').html($('#contentTypeName' + contentPageTypeId).val());

		$('#defaultContentTypeId' + contentPageTypeId).click();
	}

		$(".side_list_one").find("li").each(function(index){
		    $(this).bind("click",function() {
			if ($(this).hasClass('show_list_bg')){
				$(this).removeClass('show_list_bg');
			} else{
				$(this).siblings('.show_list_bg').removeClass('show_list_bg');
				$(this).addClass('show_list_bg');
			}
			
			if ($('.side_list_one li').children('dl').children('dd')
					.children('a').hasClass('add_color')) {
				$('.side_list_one li').children('dl').children('dd')
						.children('a').removeClass('add_color');
			}
			
			$('#currentContentTypeHref'+contentPageTypeId).addClass('add_color');
		});	
	
    });
})



 
  function submitToContentDetailPage(formName){
  var obj=document.getElementById(formName); 
  obj.submit();
  }
  