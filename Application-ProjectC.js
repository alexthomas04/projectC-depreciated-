

$(document).ready(function(){
	$('.Tab').on('click',function(event){
		event.preventDefault();
		var id = parseInt($(this).data('id'));
		selectTab(id);
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

function selectTab(id){
		var pages = $('.TabView').find('.Page');
		var selectedPage = pages.eq(id);
		$('.selected').removeClass('selected');
		$(".Tab").eq(id).addClass('selected');
		pages.css('display', 'none');
		selectedPage.css('display', 'block');
	}