<?php

class ProductController extends BaseController {

	public function getIndex()
	{
		$query = DB::table('products')->get();

		return View::make('product.index',array('data_list'=>$query));
	}
	public function getCreate()
	{
		$queryy = DB::table('car_pages_articles')->get();
		return View::make('product.create',array('data_list'=>$queryy));
	}

	public function postCreate()
	{
		$rules = array(
            'title' => 'required',
            'description' => 'required',
            'video_link' => 'required'
        );

        $validator = Validator::make(Input::all(), $rules);

        if($validator->fails()){
            Input::flash();
            return Redirect::to('product/create')->withErrors($validator);
        }
        
        try{
            $query = DB::table('products')->insert(array('ab_title' => Input::get('title'),'ab_desc'=>Input::get('description'),'ab_video'=>Input::get('video_link')));

    		if($query){
                return Redirect::to('product');
    		}
        }

        catch(Exception $ex){
            return $ex;
        }
	}

	public function getEdit($id)
	{
		$query = DB::table('products')->where('id','=',$id)->first();
		$queryy = DB::table('categories')->get();
		//return View::make('product.edit',array('_item' => $query));
		return View::make('product.edit',array('data_list'=>$queryy))->with('_item',$query);
	}

	public function postEdit()
	{
		$rules = array(
            'title' => 'required',
            'description' => 'required',
            'video_link' => 'required'
        );

        $validator = Validator::make(Input::all(), $rules);

        if($validator->fails()){
            Input::flash();
            return Redirect::to('product/edit/'.Input::get('id'))->withErrors($validator);
        }

        try{
        	$query = DB::table('products')
                    ->where('id', '=', Input::get('id'))
                    ->update(array('name' => Input::get('title'),'description'=>Input::get('description'),'sub_category_id'=>Input::get('video_link')));

            return Redirect::to('product');
        }
        catch(Exception $ex){
        	return $ex;
        }
	}

	public function getDelete($id)
	{
		try{
			DB::table('products')->where('id','=',$id)->delete();
			return Redirect::to('product');
		}
		catch(Exception $ex)
		{
			return $ex;
		}
	}
    public function getInfo($id)
    {
        

    $query_files = DB::table('uploads')->where('stud_id','=',$id)->get();

        return View::make('product.index',array('user_files'=>$query_files));
    }
     public function getUpload($id)
    {
        $query = DB::table('product_images')->where('product_id','=',$id)->get();
        return Response::json($query);
    }
    public function postUpload()
    {

        /*$check_access = DB::table('manage_admin')->where('id','=',Session::get('username'))->first();
        
        if($check_access->can_upload_file)
        {*/

            $name = Input::file('file')->getClientOriginalName();
            $name = str_replace(" ", "", $name);

            $upload = Input::file('file')->move(public_path().'/public/uploads/', $name);

            if($upload){
                $query_insert = DB::table('product_images')
                                ->insert(array('product_id'=>Input::get('ab_id'),'image_link'=> "https://android-ssianik.c9.io/sales/public/uploads/".$name));
                return Response::json('success',200);
            }
            else{
                return Response::json('error',400);
            }
        /*}
        else
        {
            return Response::json("sorry, not authorized",400);
        }*/

    }
    
    public function getDelfile($id)
    {
        /*$check_access = DB::table('manage_admin')->where('id','=',Session::get('username'))->first();
        
        if($check_access->can_upload_file)
        {*/

            $query = DB::table('product_images')->where('id','=',$id)->delete();
            return Response::json('deleted');
        /*}
        else{
            return Response::json("sorry, not authorized");
        }*/
    }
}