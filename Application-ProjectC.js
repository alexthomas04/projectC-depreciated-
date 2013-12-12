

$(document).ready(function(){
	$('.Tab').on('click',function(event){
		event.preventDefault();
		var pages = $(this).closest('.TabView').find('.Page');
		var id = parseInt($(this).data('id'));
		var selectedPage = pages.eq(id);
		$('.selected').removeClass('selected');
		$(this).addClass('selected');
		pages.css('display', 'none');
		selectedPage.css('display', 'block');
		console.log($(this).data());
	});

	$(window).on('resize',function(){
		center();
	});
});

function initializeTabs(){
	var TabView = $('.TabView');
	var Tabs =  TabView.children('.Tabs:last').children('.Tab');
	var Tab;
	for (var i = 0; i < Tabs.length; i++) {
		Tab=Tabs.eq(i);
		Tab.data('id',i+"");
	};

		
	

}