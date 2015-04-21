$(function() {
	$('.clickShow,.qupiaoren,.clickShow02,.userChoose,.button').click(
			function() {
				var tableHeader = $(this).attr('data-biaoshi');
				$('.' + tableHeader).show();
			})
	$('.selectShow li').click(function() {
		var tableHeader = $(this).attr('data-biaoshi');
		$('.tableHeader,.selectShow').hide();
		$('.' + tableHeader).show();
	})
	$('.button').click(function() {
		var tableInquiry2 = $(this).attr('data-biaoshi2');
		$('.' + tableInquiry2).hide();

	})
	$('.close').click(function() {
		var closeHide = $(this).attr('data-biaoshi');
		$('.' + closeHide).hide();
	})
	$('.userClose').click(function() {
		var userClose = $(this).parent().parent();
		$(userClose).remove();
	})
	$('.hoverShow').mouseover(function() {
		var hoverShow = $(this).attr('data-biaoshi');
		var thistop = $(this).offset().top;
		$('.' + hoverShow).css("top", thistop - 35);
		$('.' + hoverShow).show();
	})
	$('.lineTimeHide s').click(function() {
		var lineTimeHide = $(this).parent();
		$(lineTimeHide).addClass('lineTimeShow');
	})
	$('.clickShow03').click(
			function() {
				var invoiceInformationText = $('.invoiceInformationText').css(
						'display');
				if (invoiceInformationText == 'none') {
					$('.invoiceInformationText').show();
					$('.addressInformation').show();
				} else {
					$('.invoiceInformationText').hide();
					if ($('#isPhysical').val() != "true") {
						$('.addressInformation').hide();
					}
				}
			})
})