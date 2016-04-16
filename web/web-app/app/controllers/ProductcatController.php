<?php

class productcatController extends BaseController {

	public function getIndex()
	{
		$query = DB::table('categories')->get();

		return View::make('productcat.index',array('data_list'=>$query));
	}
	public function getCreate()
	{
		return View::make('productcat.create');
	}

	public function postCreate()
	{
		$rules = array(
            'title' => 'required',
            'description' => 'required',
            'date'=>'required'
        );

        $validator = Validator::make(Input::all(), $rules);

        if($validator->fails()){
            Input::flash();
            return Redirect::to('productcat/create')->withErrors($validator);
        }
        
        try{
            $query = DB::table('car_pages_articles')->insert(array('ab_title' => Input::get('title'),'ab_desc'=>Input::get('description'),'ab_date'=>Input::get('date')));

    		if($query){
                return Redirect::to('productcat');
    		}
        }

        catch(Exception $ex){
            return $ex;
        }
	}

	public function getEdit($id)
	{
		$query = DB::table('categories')->where('id','=',$id)->first();
		return View::make('productcat.edit')->with('_item',$query);
	}

	public function postEdit()
	{
		$rules = array(
            'title' => 'required',
            //'description' => 'required',
            //'date' => 'required'
        );

        $validator = Validator::make(Input::all(), $rules);

        if($validator->fails()){
            Input::flash();
            return Redirect::to('productcat/edit/'.Input::get('id'))->withErrors($validator);
        }

        try{
        	$query = DB::table('categories')
                    ->where('id', '=', Input::get('id'))
                    ->update(array('name' => Input::get('title')));

            return Redirect::to('productcat');
        }
        catch(Exception $ex){
        	return $ex;
        }
	}

	public function getDelete($id)
	{
		try{
			DB::table('categories')->where('id','=',$id)->delete();
			return Redirect::to('productcat');
		}
		catch(Exception $ex)
		{
			return $ex;
		}
	}
}