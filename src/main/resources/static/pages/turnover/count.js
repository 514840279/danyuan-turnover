$(function() {
	loadParam();
});
var params={"zhichu":[],"shouru":[],"type":[]};
function loadParam(){
	
	$.each($("#tornover_type_search_box_List_li").find("ul li"),function(index,li){
		$(li).find("input").click(function(){
			if($(this).prop("checked")){
				params["type"].push($(li).text());
			}else{
				params["type"].splice(params["type"].indexOf($(li).text()), 1);
			}
		});
	});
	
	var url = "/sysDicName/findkeyList";
	var param={code:"支出"};
	ajaxPost(url, param, zhichuSuccess);
	function zhichuSuccess(result){
		var ul = $("#zhichu_search_box_List_li").find("ul");
		$.each(result,function(index,value){
			var li = ul.find("li:eq(0)").clone(false);
			li.find("span").text(value.keyword);
			li.css({"display":""});
			li.find("input").click(function(){
				if($(this).prop("checked")){
					params["zhichu"].push(li.text());
				}else{
					params["zhichu"].splice(params["zhichu"].indexOf(li.text()), 1);
				}
			});
			
			ul.append(li);
		});
		var more = $("#zhichu_search_box_List_li").find('div.item_more');
		more.css({"display":""});
		more.find('a.fa-angle-double-down').click(function(){
			$(this).css({"display":"none"});
			more.find('a.fa-angle-double-up').css({"display":""});
			ul.addClass("all")
		});
		more.find('a.fa-angle-double-up').click(function(){
			$(this).css({"display":"none"});
			more.find('a.fa-angle-double-down').css({"display":""});
			ul.removeClass("all")
		});
		
	};
	var param={code:"收入"};
	ajaxPost(url, param, shouruSuccess);
	function shouruSuccess(result){
		var ul = $("#shouru_search_box_List_li").find("ul");
		$.each(result,function(index,value){
			var li = ul.find("li:eq(0)").clone(false);
			li.find("span").text(value.keyword);
			li.css({"display":""});
			li.find("input").click(function(){
				if($(this).prop("checked")){
					params["shouru"].push(li.text());
				}else{
					params["shouru"].splice(params["shouru"].indexOf(li.text()), 1);
				}
			});
			ul.append(li);
		});
		var more = $("#shouru_search_box_List_li").find('div.item_more');
		more.css({"display":""});
		more.find('a.fa-angle-double-down').click(function(){
			$(this).css({"display":"none"});
			more.find('a.fa-angle-double-up').css({"display":""});
			ul.addClass("all")
		});
		more.find('a.fa-angle-double-up').click(function(){
			$(this).css({"display":"none"});
			more.find('a.fa-angle-double-down').css({"display":""});
			ul.removeClass("all")
		});
		
	};
}


function countType(type){
	params["dateType"]=type;
	var url="/sysTurnoverInfo/chart";
	ajaxPost(url,params,successLoadCharts);
	function successLoadCharts(result){
		
	}
}