 (function($) {
    $.fn.loadUrlHtml = function() {
    var $this =$(this)
	var url =  $this.attr("href");
    var param =$this.attr("param");
    var jsonObj = null;
    if(param!=null&&""!=param){
    	jsonObj = eval('('+param +')')
    }
   
	if(url!=null||url=="") {
	 $.ajax({
  type: "POST",
  dataType: "html",
  url: url,
  async:false,
  data:jsonObj,
  beforeSend:function(){
		 $this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
  },
  
  timeout:3000,
  error:function(a,b,c){
	if(b=="timeout"){
	  alert("地址"+url+"请求超时");
  	} else if(b=="error"){
	  alert("无法请求href的地址");
  	}
	 $this.html("");
  },
  success: function(data){
 	 $this.html(data);

  }
  });
	} else {
	alert("请指定"+$this.attr("id")+"href属性");
	}

	}
     $.fn.refresh = function(json) {
    	  var $this =$(this)
	var url =  $this.attr("href");
    var param =$this.attr("param");
    var jsonObj = null;
    if(param!=null&&""!=param){
    	jsonObj = eval('('+param +')')
    }
   
	if(url!=null||url=="") {
	 $.ajax({
  type: "POST",
  dataType: "html",
  url: url,
  async:false,
  data:json,
  beforeSend:function(){
		 $this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
  },
  timeout:3000,
  error:function(a,b,c){
	if(b=="timeout"){
	  alert("地址"+url+"请求超时");
  	} else if(b=="error"){
	  alert("无法请求href的地址");
  	}
	 $this.html("");
  },
  success: function(data){
 	 $this.html(data);
  }
  });
	} else {
	alert("请指定href属性");
	}
    }
     
     
      $.fn.reload = function(options) {
    	  var $this =$(this)
	var url =  $this.attr("href");
	if(url!=null||url=="") {
	 $.ajax({
  type: "POST",
  dataType: "html",
  url: url,
  async:false,
  data:options,
  beforeSend:function(){
		 $this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
  },
  timeout:3000,
  error:function(a,b,c){
	if(b=="timeout"){
	  alert("地址"+url+"请求超时");
  	} else if(b=="error"){
	  alert("无法请求href的地址");
  	}
	 $this.html("");
  },
  success: function(data){
 	 $this.html(data);
  }
  });
	} else {
	alert("请指定href属性");
	}

    }
     
})(jQuery); 