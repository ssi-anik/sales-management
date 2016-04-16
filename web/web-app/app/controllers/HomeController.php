<?php

class HomeController extends BaseController {

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
	{	// home page for sfm
		return View::make('home.index');
	}

	// item create
	public function postAdditem()
	{
	     $rules = array(
            'name' => 'required',
            'service_address' => 'required',       
            // 'level_id' => 'required',     
            // 'academic_id' => 'required',     
            // 'status' => 'required'         
        );
	    $input = Input::get('data');
        $validator = Validator::make(Input::all(), $rules);

        // if($validator->fails()){
        //     Input::flash();
        //     return Redirect::to('setting/enrollment/index')->withErrors($validator);
        // }

        try{

            $query = DB::table('billing_item_groups')->insert(array('name' => $input['name'] ,'provider_id' => '2' ,'status'=>$input['status']));

            //if($query){
                //return Redirect::to('setting/enrollment/index')->with('submit', '1');
            return Response::json();
            //}
            //return Redirect::to('setting/academic/index')->with('success', '1');
        }

        catch(Exception $ex){
            return $ex;
        }
	}

	// item edit
	public function putEdititem($id)
	{
	     $rules = array(
            'name' => 'required',
            'service_address' => 'required',       
            // 'level_id' => 'required',     
            // 'academic_id' => 'required',     
            // 'status' => 'required'         
        );
        $validator = Validator::make(Input::all(), $rules);
        $input = Input::get('data');
        if($validator->fails()){
            Input::flash();
            // return Response::json(array('error' => true), 400);
            // return Response::json();
        }

        try{

            $query = DB::table('billing_item_groups')->where('item_group_id', '=', $id )->update(array('name' => $input['name'] ,'provider_id' => '2' ,'creation_date' => $input['creation_date'],'service_address' => $input['service_address'],'status'=>$input['status']));

            //if($query){
                //return Redirect::to('setting/enrollment/index')->with('submit', '1');
            // return Response::json();
            return Response::json(array('success' => true), 200);
            //}
            //return Redirect::to('setting/academic/index')->with('success', '1');
        }

        catch(Exception $ex){
            return $ex;
        }
	}

	// item delete
	public function deleteDeleteitem($id)
	{
	    try{

            $query = DB::table('billing_item_groups')->where('item_group_id', '=', $id )->delete();
			return Response::json();
            // if($query){
            //     return Redirect::to('setting/enrollment/index')->with('delete', '1');
            // }
            //return Redirect::to('setting/academic/index')->with('success', '1');
        }

        catch(Exception $ex){
            return $ex;
        }
	}


	public function getLogout()
	{
		Session::forget('username');
        Session::flush();
		return Redirect::to('account/login');
	}

	public function getAdd()
	{
		return View::make('home.add');
	}

	public function getEdit()
	{
		return View::make('home.edit');
	}



	public function getTest()
	{
		return View::make('home.test');
	}
}