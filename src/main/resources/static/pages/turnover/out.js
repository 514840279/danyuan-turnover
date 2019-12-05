window.operateEvents = {
	// 修改
	'click #clickA ': function (e, value, row, index) {
		$("#cost_uuid").val(row.uuid);
		$("#cost_deleteFlag").val(row.deleteFlag);
		$("#cost_discription").val(row.discription);
		$("#cost_name").val(row.name);
		$("#cost_money").val(row.money);
		$("#cost_type").val(row.type).trigger('change');
		$("#datepicker").datepicker('setDate',new Date(row.turnDate));
		
		$("#cost_modal").modal("show");
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
					var url = "/sysTurnoverInfo/delete";
					ajaxPost(url, row, refreshTableInfo);
					function refreshTableInfo(result){
						if(result.code==200){
							$("#cost-table-id").bootstrapTable('refresh');
							loadTotal();
						}
					};
				}
			}
		});
	}
}
var id=null;
$(function() {
	// Date picker
	$('#datepicker,#datepicker2').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		language : 'zh-CN', // 修改默认为cn
		minViewMode : 0,
		orientation : "auto",
		rtl : false,
		todayBtn : "linked",
		todayHighlight : true,
		weekStart : 0,
	});
	$('#datepicker2').datepicker('setDate',new Date()).on("changeDate", function(e) {
		$("#cost-table-id").bootstrapTable('refresh');
	});
	
	init();
	
	var url = "/sysDicName/findkeyList";
	var param={code:"支出"};
	ajaxPost(url, param, formTypeSuccess);
	function formTypeSuccess(result){
		var data = [{id:'类型',text:'类型'}];
		$.each(result,function(index,value){
			data.push({id:value.keyValue,text:value.keyword});
		})
		$("#cost_type").select2({
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
	
	$("#addnew_cost").click(function(){
		$("#cost_uuid").val("");
		$("#cost_deleteFlag").val(0);
		$("#cost_discription").val("");
		$("#cost_name").val("");
		$("#cost_money").val("");
		$("#cost_type").val("类型").trigger('change');
		$("#datepicker").datepicker('setDate',new Date());
		
		$("#cost_modal").modal("show");
		
	});
	
	$("#addnew_cost").click();
	
	$("#cost_save_button").click(function(){
		var url="/sysTurnoverInfo/save";
		var param={
			uuid:	$("#cost_uuid").val(),
			deleteFlag:$("#cost_deleteFlag").val(),
			discription:$("#cost_discription").val(),
			name:$("#cost_name").val(),
			money:$("#cost_money").val(),
			type:$("#cost_type").val(),
			turnDate:$("#datepicker").val(),
			category:"支出",
			createUser:username,
			updateUser:username
		};
		ajaxPost(url,param,successSave);
		function successSave(result){
			if(result.code==200){
				$("#cost_modal").modal("hide");
				$("#cost-table-id").bootstrapTable('refresh');
				loadTotal();
			}
		};
		
	});
	
});

function init(){
	
	$("#cost-table-id").bootstrapTable({
		url : "/sysTurnoverInfo/pageCost",
		dataType : "json",
		toolbar : '#cost_toolbar', // 工具按钮用哪个容器
		cache : true, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sortName:"createTime",
		pagination : true, // 分页
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
//		strictSearch : true,
		classes: "table  table-striped table-sm ",
//		classes: "table table-bordered table-striped table-sm table-dark",
//		showColumns : true, // 是否显示所有的列
		showRefresh : true, // 是否显示刷新按钮
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
					category:"支出",
					turnDate:$("#datepicker2").val()
				}
			}; 
			return param;
		},
		columns : [ 
			{title : 'id',field : 'uuid',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '类型',	field : 'type','width':150,align : 'left',sortable : true,valign : 'middle',switchable:true,visible:true},
			{title : '名称',	field : 'name','width':140,align : 'left',sortable : true,valign : 'middle',switchable:true,visible:true},
			{title : '金额',	field : 'money','width':100,align : 'left',sortable : true,valign : 'middle',switchable:true,visible:true},
			{title : '项目描述',	field : 'discription',align : 'left',sortable : true,	valign : 'middle',switchable:true,visible:false},
			{title : '创建时间',	field : 'createTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '创建者',	field : 'createUser',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '更新时间',	field : 'updateTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '更新者',	field : 'updateTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '标记',		field : 'deleteFlag',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},
			{title : '操作',align : 'left','width':240,sortable : true,valign : 'middle',switchable:true,visible:true,events: operateEvents,formatter : function(value, row, index) {
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
		$(".info").removeClass("info");
		$(ele).addClass("info");
	});
}