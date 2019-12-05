$(function() {
	
	var url="/sysTurnoverInfo/total";
	ajaxPost(url,{},successTotal);
	
	$("#row-btn").find("div.info-box").bind("click",function(){
		var a = $(this).find("span i");
		var url = a.data("url");
		templatesLoad("main-content-box",url);
		
		var name = a.data("name");
		$(".box-title").text(name);
	});
	
	$("#row-btn").find("div.info-box:last").click();
	loadTotal();
	
});

function loadTotal(){
	var url="/sysTurnoverInfo/total";
	ajaxPost(url,{},successTotal);
	
}
function successTotal(result){
	if(result.code==200){
		var total = 0;
		$.each(result.data,function(index,row){
			if(row.NAME=="历史"){
				$("#history-text").text(row['MONEY'].toFixed(2));
				total +=row['MONEY'];
			}
			if(row['NAME']=="支出"){
				$("#cost-text").text(row['MONEY'].toFixed(2));
				total -= row['MONEY'];
			}
			if(row['NAME']=="收入"){
				$("#income-text").text(row['MONEY'].toFixed(2));
				total +=row['MONEY'];
			}
			
		});
		$("#total-text").text(total.toFixed(2));
	}
};
