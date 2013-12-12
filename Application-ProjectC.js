

$(document).ready(function(){
	$('.Tab').on('click',function(event){
		event.preventDefault();
		var pages = $(this).closest('.TabView').find('.Page');
		var test = parseInt($(this).data('test'));
		var selectedPage = pages.eq(test);
		pages.css('display', 'none');
		selectedPage.css('display', 'block');
		console.log($(this).data());

	});
});

function initializeTabs(){
	var TabView = $('.TabView');
	var Tabs =  TabView.children('.Tabs:last').children('.Tab');
	var Tab;
	for (var i = 0; i < Tabs.length; i++) {
		Tab=Tabs.eq(i);
		Tab.data('test',i+"");
	};

		
	

}