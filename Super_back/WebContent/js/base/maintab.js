

dojo.addOnLoad(function(){
//var body = document.getElementById("content");
//var contentPaneDiv = document.createElement("div");
//dojo.byId("tc1").appendChild(contentPaneDiv);
//var contentPane = new dijit.layout.ContentPane({title:'产品管理'},contentPaneDiv);

	//dojo.byId("tc1").appendChild(content);

	dojo.byId("tc1").innerHTML=createTabs();
	dojo.parser.parse(dojo.byId("tc1")); 

});
//;
function createTabs(){
var str ="<div dojoType=\"dijit.layout.TabContainer\" style=\"width: 100%; height: 100%;border:0px;padding: 0 0 0 0;margin: 0 0 0 0;\" id=\"tc1\">";
str+=panels;
str+="</div>";
return str;
}
var panels="";
function tabPanel(){
}

tabPanel.prototype.addPanel=function(title,url){
panels+="<div dojoType=\"dijit.layout.ContentPane\" title=\""+title+"\" >";
panels+="<iframe style=\"border:0px;padding: 0 0 0 0;margin: 0 0 0 0;\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"no\" width=100% height=100% src=\""+url+"\"></iframe>";
panels+="</div>";
}
