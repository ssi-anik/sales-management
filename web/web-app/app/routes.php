<?php

Route::get('/',function(){
	if(Session::get('username') != null){
		return Redirect::to('home/index');
	}
	else{
		return Redirect::to('account/login');
	}
});

Route::filter('onlyuser',function()
{
	if(Session::get('username') === null){
		return Redirect::to('account/login');
	}
});

Route::filter('onlyguest',function(){
	if(Session::get('username') != null){
		return Redirect::to('home/index');
	}
});

Route::when('account/*','onlyguest');
Route::when('home/*','onlyuser');
Route::when('shop/*','onlyuser');
Route::when('agent/*','onlyuser');
Route::when('order/*','onlyuser');
Route::when('news/*','onlyuser');
Route::when('complaint/*','onlyuser');
Route::when('offers/*','onlyuser');
Route::when('admin/*','onlyuser');
Route::when('product/*','onlyuser');
Route::when('productcat/*','onlyuser');
Route::when('bills/*','onlyuser');

Route::get('p', function(){
	return public_path();
});
Route::get('agent/track/{id}', 'AgentController@tracker');
Route::get('agent/track/{id}/{date}', 'AgentController@trackerOnDate');
Route::get('agent/location/{id}/{date}', 'AgentController@location');

Route::controller('home','HomeController');
Route::controller('account','AccountController');
Route::controller('shop','ShopController');
Route::controller('agent','AgentController');
Route::controller('order','OrderController');
Route::controller('news','NewsController');
Route::controller('complaint','ComplaintController');
Route::controller('offers','OffersController');
Route::controller('admin','AdminController');
Route::controller('product','ProductController');
Route::controller('productcat','ProductcatController');
Route::controller('newaccount','NewaccountController');
Route::get('bills/allitem', 'BillController@allItem');
Route::resource('bills', 'BillController');