<?php

class NewsController extends BaseController {

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
		return View::make('news.index');
	}

	public function getAllitem()
	{
	    $items = DB::table('news')->get();
        // return View::make('setting.enrollment.index',array('item'=>$items));
        return Response::json(array('data'=>$items));
	}
	
	    public function postAdditem()
    {
         $rules = array(
            'title' => 'required',
            'description' => 'required',       
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

            $query = DB::table('news')->insert(array('title' => $input['title'] ,'desc' => $input['description'],'link' => $input['link']));

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
            'title' => 'required',
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

            $query = DB::table('news')->where('news_id', '=', $id )->update(array('title' => $input['title'] ,'desc' => $input['desc'],'link' => $input['link']));

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

            $query = DB::table('news')->where('news_id', '=', $id )->delete();
            return Response::json();
        }

        catch(Exception $ex){
            return $ex;
        }
    }
    
    


	public function getEdit()
	{
		return View::make('order.edit');
	}

	public function getAdd()
	{
		return View::make('order.add');
	}
	public function getOrder()
	{
		return View::make('agent.order');
	}
	public function getCompleted()
	{
		return View::make('agent.completed');
	}
	public function getBonus()
	{
		return View::make('agent.bonus');
	}
	public function getAgent()
	{
		return View::make('agent.agent');
	}
	public function getComplaint()
	{
		return View::make('agent.complaint');
	}
	
	// public function getAllitem()
	// {
	//     $items = DB::table('billing_item_groups')
	//     	->select('item_group_id AS id','name AS title','start','end','url')
	//     		->get();
 //        // return View::make('setting.enrollment.index',array('item'=>$items));
 //        return Response::json($items);
	// }

	// public function postEdit($id)
	// {
	//     $items = DB::table('billing_item_groups')
	//     	->select('item_group_id AS id','name AS title','start','end','url')
	//     		->get();
 //        // return View::make('setting.enrollment.index',array('item'=>$items));
 //        return Response::json($items);
	// }
}