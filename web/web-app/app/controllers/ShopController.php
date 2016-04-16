<?php

class ShopController extends BaseController {

	/*
	|--------------------------------------------------------------------------
	| Default Home Controller
	|--------------------------------------------------------------------------
	|
	| You may wish to use controllers instead of, or in addition to, Closure
	| based routes. That's great! Here is an example controller method to
	| get you started. To route to this controller, just add the route:
	|
	|	Route::get('/', 'HomeController@showWelcome');
	|
	*/

	public function getIndex()
	{	
		$query = DB::table('shops')->get();

		return View::make('shop.index',array('data'=>$query));
		//return View::make('shop.index');
	}

	public function getEdit()
	{
		return View::make('shop.edit');
	}

	public function getView()
	{
		return View::make('shop.view');
	}
	public function getOrder()
	{
		return View::make('shop.order');
	}
	public function getCompleted()
	{
		return View::make('shop.completed');
	}
	public function getBonus()
	{
		return View::make('shop.bonus');
	}
	public function getAgent()
	{
		return View::make('shop.agent');
	}
	public function getComplaint()
	{
		return View::make('shop.complaint');
	}

}