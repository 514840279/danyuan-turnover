window.operateEvents = {
	// 修改
	'click #clickA ': function (e, value, row, index) {
		$("#histroy_uuid").val(row.uuid);
		$("#histroy_deleteFlag").val(row.deleteFlag);
		$("#histroy_discription").val(row.discription);
		$("#histroy_name").val(row.name);
		$("#histroy_money").val(row.money);
		$("#histroy_type").val(row.type).trigger('change');
		
		$("#histroy_modal").modal("show");
	},
	// 删除
	'click #clickD': function (e, value, row, index) {
		bootbox.setLocale("zh_CN");
		bootbox.confirm({
			message : "确定要删除选定行",
			title : "系统提示",
			callback : function(result) {
				if (result) {
					// 删除
					var url = "/histroyCount/delete";
					ajaxPost(url, row, refreshTableInfo);
					function refreshTableInfo(result){
						if(result.code==200){
							$("#histroy-table-id").bootstrapTable('refresh');
						}
					};
				}
			}
		});
	}
}
var id=null;
$(function() {
	init();
	
	var url = "/sysDicName/findkeyList";
	var param={code:"_form_type"};
	ajaxPost(url, param, formTypeSuccess);
	function formTypeSuccess(result){
		var data = [{id:'类型',text:'类型'}];
		$.each(result,function(index,value){
			data.push({id:value.keyValue,text:value.keyword});
		})
		$("#histroy_type").select2({
			tags: true,
			placeholder: "类型",
			data: data,
			width:150
		}).on('select2:select', function (evt) {
			id = evt.params.data.id;
			if(id=="类型"){
				id = "";
			}
		})
		
	}
	
	$("#addnew_histroy").click(function(){
		$("#histroy_uuid").val("");
		$("#histroy_deleteFlag").val(0);
		$("#histroy_discription").val("");
		$("#histroy_name").val("");
		$("#histroy_money").val("");
		$("#histroy_type").val("");
		$("#histroy_modal").modal("show");
		
	});
	
	$("#histroy_save_button").click(function(){
		var url="/histroyCount/save";
		var param={
			uuid:	$("#histroy_uuid").val(),
			deleteFlag:$("#histroy_deleteFlag").val(),
			discription:$("#histroy_discription").val(),
			name:$("#histroy_name").val(),
			money:$("#histroy_money").val(),
			type:$("#histroy_type").val(),
		};
		ajaxPost(url,param,successSave);
		function successSave(result){
			if(result.code==200){
				$("#histroy_modal").modal("hide");
				$("#histroy-table-id").bootstrapTable('refresh');
			}
		};
		
	});
	
});

function init(){
	
	$("#histroy-table-id").bootstrapTable({
		url : "/histroyCount/page",
		dataType : "json",
		toolbar : '#histroy_toolbar', // 工具按钮用哪个容器
		cache : true, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		pagination : true, // 分页
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
//		strictSearch : true,
		bordered:false,
		striped:true,
		sm:true,
//		showColumns : true, // 是否显示所有的列
//		showRefresh : true, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
//		height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId : "uuid", // 每一行的唯一标识，一般为主键列
//		showToggle : true, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		singleSelect : false,
		locales : "zh-CN", // 表格汉化
		search : false, // 显示搜索框
		sidePagination : "server", // 服务端处理分页
		//设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  
		//设置为limit可以获取limit, offset, search, sort, order  
		queryParamsType : "undefined",
		contentType : "application/json",
		method : "post", // 使用get请求到服务器获取数据
		queryParams: function queryParams(params) {  
			var param = {  
				pageNumber: params.pageNumber,    
				pageSize: params.pageSize,
				sortOrder:params.sortOrder,
				sortName:params.sortName,
				filter:params.filter,
				info:{
				}
			}; 
			return param;
		},
		columns : [ 
//			{title : '全选',	checkbox : true,align : 'center',valign : 'middle'}, 
			{title : 'id',field : 'uuid',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '类型',	field : 'type','width':100,align : 'left',sortable : true,valign : 'middle',switchable:true,visible:true},
			{title : '名称',	field : 'name','width':150,align : 'left',sortable : true,valign : 'middle',switchable:true,visible:true},
			{title : '金额',	field : 'money','width':150,align : 'left',sortable : true,valign : 'middle',switchable:true,visible:true},
			{title : '项目描述',	field : 'discription',align : 'left',sortable : true,	valign : 'middle',switchable:true,visible:false},
			{title : '创建时间',	field : 'createTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '创建者',	field : 'createUser',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '更新时间',	field : 'updateTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '更新者',	field : 'updateTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '标记',		field : 'deleteFlag',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '操作',align : 'left','width':180,sortable : true,valign : 'middle',switchable:true,visible:true,events: operateEvents,formatter : function(value, row, index) {
				var A = "<i  type='button' id='clickA'  class=' btn btn-info btn-sm fa fa-edit' title='编辑' ></i> ";
				var D = "<i  type='button' id='clickD'  class=' btn btn-info btn-sm fa fa-remove' title='删除' ></i> ";
				return A+D;
			}}
		],
		responseHandler: function(result){  // 成功时执行
			return {rows:result.data.content,total:result.data.totalElements}; // 绑定数据 
		},
		onLoadError:function(statue,result){ // 错误时执行
			if($(result.responseText).find("form").attr("action")=="/login"){
				window.location.href="/";
			}
		},
		contextMenu: '#context-menu', // 右键菜单绑定
		onContextMenuItem: function(row,$ele){ // 右键菜单事件
		}
	}).on('dbl-click-row.bs.table', function (e, row, ele,field) { // 行双击事件 
	}).on('click-row.bs.table', function (e, row, ele,field) { // 行单击事件
	});
}