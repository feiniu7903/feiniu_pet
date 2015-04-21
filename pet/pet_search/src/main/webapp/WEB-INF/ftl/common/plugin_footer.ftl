
<!--[if lt IE 7]>
<script src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js" ></script>
<script type="text/javascript">
   DD_belatedPNG.fix('.ie6png');
</script>
<![endif]-->
<!--<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.min.js"></script>-->
<script src="http://10.3.1.41/search/js/jquery-1.7.min.js"></script>
<script src="http://10.3.1.41/search/js/js/ori-tooltip.js"></script>
<!--<script src="http://10.3.1.41/search/js/ori-popover.js"></script>-->
<script src="http://10.3.1.41/search/js/js/ori-affix.js"></script>

<script>
$(function(){
	
	
//	$("a[rel=tooltip]")
//	.tooltip({
//		placement: 'bottom-left'
//	})
	$('a[class^="icon30"]')
	.tooltip({
		placement: 'bottom-left',
		delay: 100
	})

    $('#tooltip-box').tooltip({
      selector: 'a[class=icon202]',
	  placement: 'bottom-right',
	  offset:[0,20]
    })

	$('a[class=icon201]')
	.tooltip({
		placement: 'bottom-left'
	})
//    $('section [href^=#]').click(function (e) {
//      e.preventDefault()
//    })
	//$('a[class=icon202]')
	//.tooltip({
	//	placement: 'bottom-right',
	//	offset:[0,20]
	//})
	
	/*	$("a[rel=tooltip]")
		.tooltip({
			placement: 'bottom-left'
		})
      .click(function(e) {
        e.preventDefault()
      })*/
    //$('.popover-test').popover()
//
//    // popover demo
//    $('span[class^="icon"]')
//      .popover({trigger: 'hover'})
//      .click(function(e) {
//        e.preventDefault()
//      })


})
</script>

