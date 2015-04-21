var businessTypeGrid;
var contentTypeGrid;
var contentGrid
var businessTypeWindow;
var contentTypeWindow;
var contentWindow
var addBusinessTypeForm;
var addContentTypeForm;
var addContentForm;
$(document).ready(function() {
	$("#typeDiv").hide();
	$("#contentDiv").show();
	$("#contentResultListDiv").show();
	$('#type-manage').linkbutton();
	$('#content-add').linkbutton();
	$('#btn-search-content').linkbutton();
	$('#btn-save-business-type').linkbutton();
	$('#btn-cancel-business-type').linkbutton();
	$('#btn-save-content-type').linkbutton();
	$('#btn-cancel-content-type').linkbutton();
	$('#content-add').click(function() {
		displayContentDiv();
	});
	$('#type-manage').click(function() {
		queryList("type");
	});
	$('#btn-search-content').click(function() {
		queryList("content");
	});
	businessTypeWindow = $('#businessTypeWindow').window( {
		closed : true
	});
	contentTypeWindow = $('#contentTypeWindow').window( {
		closed : true
	});
	contentWindow = $('#contentWindow').window( {
		closed : true
	});
	$('#btn-save-content').linkbutton();
	$('#btn-cancel-content').linkbutton();
	addBusinessTypeForm = businessTypeWindow.find('form');
	addContentTypeForm = contentTypeWindow.find('form');
	addContentForm = contentWindow.find('form');
	displayContentDiv();

    $.messager.defaults={ok:"确定",cancel:"取消"};   
 	KE.show({
		id : 'contentA'
	});
});

function refershHelpContentTypeList(defaultFirstTypeId)
{
	$("#helpContentType").empty();
	var typeId = defaultFirstTypeId;
	if($("#helpBusinessType").val()!=null && $("#helpBusinessType").val()!="")
	{
		typeId = $("#helpBusinessType").val();
	}
	
	if(typeId!=null && typeId!="")
	{
		$.ajax( {
			type : "POST",
			url : path + 'help/queryContentTypeJsonList.do',
			data : {
				"resultInfoQuesTypeHierarchy.parentTypeId" : typeId
			},
			dataType : 'json',
			success : function(data) {
				var d = eval(data);
				if (d) {
					$.each(d.rows, function(index, value) {
						$("#helpContentType").append(
								'<option value=' + value.typeId + '>'
										+ value.typeName + '</option>')
					});
				} else {
					alert("获取发生错误！");
				}
			}
		});
	}
}


function displayContentDiv() {
	
	$("#helpBusinessType").empty();
	var m = "";
	var defaultFirstTypeId;
	$.ajax( {
		type : "POST",
		url : path + 'help/queryBusinessTypeJsonList.do',
		data : m,
		dataType : 'json',
		success : function(data) {
			var d = eval(data);
			if (d) {
				$.each(d.rows, function(index, value) {
					if(index==0) defaultFirstTypeId = value.typeId;
					$("#helpBusinessType").append(
							'<option value=' + value.typeId + '>'
									+ value.typeName + '</option>')
				});
				refershHelpContentTypeList(defaultFirstTypeId);
				
			} else {
				alert("获取发生错误！");
			}
		}
	});
	
	$("#typeDiv").hide();
	$("#contentDiv").show();
	$("#contentResultListDiv").hide();
}

function queryList(queryParam) {

	if(queryParam == "content")
	{
		var m = $('#searchContentFrom').getForm({   
	        prefix:''  
	    }); 
		if ( $('#helpBusinessType').val() == null
				|| $('#helpBusinessType').val() == ""|| $('#helpContentType').val() == null
				|| $('#helpContentType').val() == "") {
			$.messager.alert('错误', "请选择类型", 'error');
			return false;
		}
		
		$("#typeDiv").hide();
		$("#contentDiv").show();
		$("#contentResultListDiv").show();
		createTypeListGrid(m, "#listContentGrid");
	}
	else//type
	{
		$("#typeDiv").show();
		$("#contentDiv").hide();
		$("#contentResultListDiv").hide();
		createTypeListGrid("", "#listBusinessTypeGrid");
		createTypeListGrid("", "#listContentTypeGrid");
	}
}


function openAddOrEditTypeWindow(command,objectWindow)
{
	if(command == 'add')
	{
		if(objectWindow == contentTypeWindow)//add content type
		{
			$("#businessTypeSelect").empty();
			var m = "";
			$.ajax( {
				type : "POST",
				url : path + 'help/queryBusinessTypeJsonList.do',
				data : m,
				dataType : 'json',
				success : function(data) {
					var d = eval(data);
					if (d) {
						$.each(d.rows, function(index, value) {
							$("#businessTypeSelect").append(
									'<option value=' + value.typeId + '>'
											+ value.typeName + '</option>')
						});
					} else {
						alert("获取发生错误！");
					}
				}
			});
			addContentTypeForm.form('clear');
			addContentTypeForm.url = path + 'help/saveType.do';
		   $("#businessTypeSelect").show();
		   $("#businessTypeSelectLabel").hide();
		}
		else//add business type
		{
			addBusinessTypeForm.form('clear');
			addBusinessTypeForm.url = path + 'help/saveType.do';
		}
	}
	else//edit
	{
		var resultInfoQuesTypeHierarchy;
		if(objectWindow == contentTypeWindow)//edit content type
		{
			resultInfoQuesTypeHierarchy = contentTypeGrid.datagrid('getSelected');
			if (resultInfoQuesTypeHierarchy == null) {
				$.messager.alert("提示", "请选择一行数据再编辑");
				return false;
			}
			addContentTypeForm.form('clear');
			addContentTypeForm.url = path + 'help/saveType.do';
			$("#contentTypeName").val(resultInfoQuesTypeHierarchy.typeName);
			$("#contentTypeId").val(resultInfoQuesTypeHierarchy.typeId);
			$("#businessTypeSelectLabel").text(resultInfoQuesTypeHierarchy.parentTypeName);
			$("#businessTypeSelectLabel").show();
			$("#businessTypeSelect").hide();
		}
		else//edit business type
		{
			resultInfoQuesTypeHierarchy = businessTypeGrid.datagrid('getSelected');
			if (resultInfoQuesTypeHierarchy == null) {
				$.messager.alert("提示", "请选择一行数据再编辑");
				return false;
			}
			addBusinessTypeForm.form('clear');
			addBusinessTypeForm.url = path + 'help/saveType.do';
			$("#businessTypeName").val(resultInfoQuesTypeHierarchy.typeName);
			$("#businessTypeId").val(resultInfoQuesTypeHierarchy.typeId);
		}
	}
	
	objectWindow.window('open');
	var left = ($("body").width() - objectWindow.width()) / 2;
	var top = ($("body").height() - objectWindow.height()) / 2;
	top = top < 0 ? $("body").height() : top;
	$("div[class='panel window'],div[class='window-shadow']").css( {
		left : left + "px",
		top : top + "px"
	});
}


function openAddOrEditContentWindow(command) {
	$("#helpContentBusinessType").empty();
	$("#doubleHelpContentType").empty();
	if(command == 'add')
	{
		addContentForm.form('clear');
		addContentForm.url = path + 'help/saveHelpInfo.do';
		$("#oneHelpInfoRowUserName").val($("#userName").val());
		$("#doubleHelpContentType").val($("#helpContentType").val());
		$("#contentWindowBusinessTypeName").text("业务板块:"+$("#helpBusinessType").find("option:selected").text());
		$("#contentWindowContentTypeName").text("内容板块:"+$("#helpContentType").find("option:selected").text());
		$("#contentParentTypeId").val($("#businessTypeSelect").val());
		
		$("#oneHelpInfoRowContent").val('');
		KE.html("contentA",'');
	}
	else//edit
	{
		var resultInfoQuesTypeHierarchy = contentGrid.datagrid('getSelected');
		if (resultInfoQuesTypeHierarchy == null) {
			$.messager.alert("提示", "请选择一行数据再编辑");
			return false;
		}
		
		addContentForm.form('clear');
		addContentForm.url = path + 'help/saveHelpInfo.do';
		$("#oneHelpInfoRowUserName").val($("#userName").val());
		$("#doubleHelpContentType").val($("#helpContentType").val());
		$("#oneHelpInfoRowContentId").val(resultInfoQuesTypeHierarchy.id);
		$("#contentWindowBusinessTypeName").text("业务板块:"+$("#helpBusinessType").find("option:selected").text());
		$("#contentWindowContentTypeName").text("内容板块:"+$("#helpContentType").find("option:selected").text());
		$("#oneHelpInfoRowTitle").val(resultInfoQuesTypeHierarchy.title);
		$("#oneHelpInfoRowContent").val(convertHtmlStringToTextString(resultInfoQuesTypeHierarchy.content));
		KE.html("contentA",convertHtmlStringToTextString(resultInfoQuesTypeHierarchy.content));		
	}
	contentWindow.window('open');
	var left = ($("body").width() - contentWindow.width()) / 2;
	var top = ($("body").height() - contentWindow.height()) / 2;
	top = top < 0 ? $("body").height() : top;
	$("div[class='panel window'],div[class='window-shadow']").css( {
		left : left + "px",
		top : 15 + "px"
	});

}


function saveType(object)
{
	var objectForm;
	var objectWindow;
	if(object == 'addContentTypeForm')
	{
		if (($("#businessTypeSelect").val() == null
				|| $("#businessTypeSelect").val() == "" || 
				$("#contentTypeName").val() == null
				|| $("#contentTypeName").val() == "") && ($("#businessTypeSelectLabel").val() !=null && $("#businessTypeSelectLabel").val() !="")) {
			$.messager.alert('错误', "请选择类型和输入值", 'error');
			return false;
		}
		objectForm = addContentTypeForm;
		objectWindow = contentTypeWindow; 
		$("#contentParentTypeId").val($("#businessTypeSelect").val());
	}
	else//addBusinessTypeForm
	{
		if ($("#businessTypeName").val() == null
				|| $("#businessTypeName").val() == "") {
			$.messager.alert('错误', "请输入值", 'error');
			return false;
		}
		objectForm = addBusinessTypeForm;
		objectWindow = businessTypeWindow; 
	}
	objectForm.form('submit', {
		url : objectForm.url,
		success : function(data) {
			var d = eval(data);
			if (d) {
				if (businessTypeGrid == null || contentTypeGrid == null) {
					var m = $('#addBusinessTypeForm').getForm( {
						prefix : ''
					});
					createTypeListGrid(m, '#listBusinessTypeGrid');
					m = $('#addContentTypeForm').getForm( {
						prefix : ''
					});
					createTypeListGrid(m, '#listContentTypeGrid');
				} else {
					businessTypeGrid.datagrid('reload');
					contentTypeGrid.datagrid('reload');
				}
				objectWindow.window('close');
			} else {
				$.messager.alert('错误', data.msg, 'error');
			}
		}
	});
}

function saveContent() {
		
	if ($("#oneHelpInfoRowTitle").val() == null || $("#oneHelpInfoRowTitle").val() == '') {
			$.messager.alert("提示", "请填写内容主题");
			return false;
	}
	if ($("#contentA").val() == null || $("#contentA").val() == '') {
			$.messager.alert("提示", "请填写内容");
			return false;
	}
	$("#oneHelpInfoRowContent").val($("#contentA").val());
	if ($("#doubleHelpContentType").val() == null
	|| $("#doubleHelpContentType").val() == "") {
		$.messager.alert('错误', "请选择类型", 'error');
		return false;
	}
	
	addContentForm.form('submit', {
		url : addContentForm.url,
		success : function(data) {
			var d = eval(data);
			if (d) {
				if (contentGrid == null) {
					var m = $('#addContentForm').getForm( {
						prefix : ''
					});
					createTypeListGrid(m, '#listContentGrid');
				} else {
					contentGrid.datagrid('reload');
				}
				contentWindow.window('close');
			} else {
				$.messager.alert('错误', data.msg, 'error');
			}
		}
	});
}


function removeType(grid)
{
	var resultInfoQuesTypeHierarchy = grid.datagrid('getSelected');
	if (resultInfoQuesTypeHierarchy == null) {
		$.messager.alert("提示", "请选择一行数据");
		return false;
	}
	
	var removeMessage = "确认删除此条数据";
	if(grid == businessTypeGrid)
	{
		removeMessage = "确认删除此条数据，该操作会删除此数据\n\n下所有内容版块";
	}
	
	$.messager
			.confirm(
					'确认删除',
					removeMessage,
					function(r) {
						if (r) {
							$
									.ajax( {
										type : "POST",
										url : path + "/help/removeType.do",
										data : {
											"resultInfoQuesTypeHierarchy.typeId" : resultInfoQuesTypeHierarchy.typeId
										},
										success : function(data) {
											var d = eval(data);
											if (d) {
												$.messager.alert('删除成功',
														'删除已经成功', 'info');
												businessTypeGrid
														.datagrid('reload');
												contentTypeGrid
														.datagrid('reload');
											} else {
												$.messager.alert('错误', "操作出错",
														'error');
											}
										}
									});
						}
					});
}


function removeContent()
{
	var oneHelpInfoRow = contentGrid.datagrid('getSelected');
	if (oneHelpInfoRow == null) {
		$.messager.alert("提示", "请选择一行数据");
		return false;
	}
	$.messager
			.confirm(
					'确认删除',
					'确认删除此条数据',
					function(r) {
						if (r) {
							$.ajax( {
										type : "POST",
										url : path + "/help/removeHelpInfo.do",
										data : {
											"oneHelpInfoRow.id" : oneHelpInfoRow.id
										},
										success : function(data) {
											var d = eval(data);
											if (d) {
												$.messager.alert('删除成功',
														'删除已经成功', 'info');
												contentGrid.datagrid('reload');
											} else {
												$.messager.alert('错误', "操作出错",
														'error');
											}
										}
									});
						}
					});
}

// 创建grid 集合
function createTypeListGrid(queryParam, typeGrid) {
	if (typeGrid == '#listBusinessTypeGrid') {
		businessTypeGrid = $(typeGrid).datagrid( {
			title : '业务板块列表',
			iconCls : 'icon-save',
			nowrap : false,
			striped : true,
			collapsible : true,
			singleSelect : true,
			url : path + 'help/queryBusinessTypeJsonList.do',
			remoteSort : false,
			idField : 'typeId',
			queryParams : queryParam,
			frozenColumns : [ [

			] ],
			columns : [ [ {
				field : 'typeName',
				title : '业务板块',
				width : 150,
				sortable : true
			} ] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
				openAddOrEditTypeWindow("add",businessTypeWindow);
				}
			},{
					text:'修改',
					iconCls:'icon-edit',
					handler:function(){
				openAddOrEditTypeWindow("edit",businessTypeWindow);
					}
				}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					removeType(businessTypeGrid);
				}
			} ]

		});
	} else if (typeGrid == '#listContentTypeGrid') {
		contentTypeGrid = $(typeGrid).datagrid( {
			title : '内容板块列表',
			iconCls : 'icon-save',
			nowrap : false,
			striped : true,
			collapsible : true,
			singleSelect : true,
			url : path + 'help/queryContentTypeJsonList.do',
			remoteSort : false,
			idField : 'typeId',
			queryParams : {"resultInfoQuesTypeHierarchy.parentTypeId":""},
			frozenColumns : [ [
  
			] ],
			columns : [ [ {
				field : 'typeName',
				title : '内容板块',
				width : 150,
				sortable : true
			}, {
				field : 'parentTypeName',
				title : '对应业务板块',
				width : 150,
				sortable : true
			} ] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
				openAddOrEditTypeWindow("add",contentTypeWindow);
				}
			},{
					text:'修改',
					iconCls:'icon-edit',
					handler:function(){
				openAddOrEditTypeWindow("edit",contentTypeWindow);
					}
				}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
				    removeType(contentTypeGrid);
				}
			} ]

		});
	} else {
		contentGrid = $(typeGrid).datagrid( {
			title : '内容列表',
			iconCls : 'icon-save',
			nowrap : false,
			striped : true,
			collapsible : true,
			singleSelect : true,
			url : path + 'help/queryHelpInfoJsonList.do',
			remoteSort : false,
			idField : 'id',
			queryParams : queryParam,
			frozenColumns : [ [

			] ],
			columns : [ [ {
				field : 'title',
				title : '标题',
				width : 150,
				sortable : true
			}, {
				field : 'typeName',
				title : '内容板块',
				width : 150,
				sortable : true
			}, {
				field : 'userName',
				title : '用户名',
				width : 150,
				sortable : true
			}] ],
			toolbar : [  {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
				openAddOrEditContentWindow("add");
				}
			},{
					text:'修改',
					iconCls:'icon-edit',
					handler:function(){
				openAddOrEditContentWindow("edit");
					}
				}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
				removeContent();
				}
			} ]
		});

	}
}

function convertHtmlStringToTextString(htmlString)
{
  var textString = htmlString;
   var regS = new RegExp("<br/>","gi");
  if(textString != null){
	   textString = textString.replace(regS, "\r\n");
  }
  regS = new RegExp("&nbsp;&nbsp;&nbsp;&nbsp;","gi");
  if(textString != null){
	    textString = textString.replace(regS, "\t");
  }
  regS = new RegExp("&nbsp;","gi");
  if(textString != null){
	   textString = textString.replace(regS, " ");
  }
  return textString;
}


function closeWindow(id) {
	$("#" + id).window('close');
}