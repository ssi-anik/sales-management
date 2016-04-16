<?php

class BaseController extends Controller {


	//public function __construct()
    //{
        // Always run csrf protection before the request when posting
        // $this->beforeFilter('csrf', array('on' => 'post'));

        // Here's something that happens after the request
        // $this->afterFilter(function() {
        //     // something
        // });

    //}

	/**
	 * Setup the layout used by the controller.
	 *
	 * @return void
	 */
	protected function setupLayout()
	{
		if ( ! is_null($this->layout))
		{
			//$this->layout->title = 'Home page';
			//$menu = DB::table('main_menu')->where('status', '=', 1)->get();
			//return View::make('home.nusoftmanager',array('menu'=>$menu));

			//$this->layout->content('menu', $menu);
			$this->layout = View::make($this->layout);
			//$this->layout->content = View::share($this->layout)->with(array('menu'=>$menu));
		}
	}

	// public function setupMenu()
	// {
	// 		$menu = DB::table('main_menu')->where('status', '=', 1)->get();
	// 		return View::make('menu',array('menu'=>$menu));
	// }

}