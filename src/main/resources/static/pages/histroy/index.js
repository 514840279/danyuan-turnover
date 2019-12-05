$(function() {
	
	$("#row-btn").find("div.info-box").bind("click",function(){
		var a = $(this).find("span i");
		var url = a.data("url");
		templatesLoad("main-content-box",url);
		
		var name = a.data("name");
		$(".box-title").text(name);
	});
	
	
	$("#row-btn").find("div.info-box:last").click();
});
