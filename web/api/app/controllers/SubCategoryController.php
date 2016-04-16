<?php

class SubCategoryController extends \BaseController {
	
	/*public function index(){
		$sub_categories = SubCategory::where('is_deleted', 0)->get(array("id", "name", "sub_category_photo"));
		if($sub_categories){
			return Response::json(array(
				'error' => false,
				'data' => $sub_categories
			), 200);
		} else{
			return Response::json(array(
				'error' => true,
				'reason' => "No sub categories available"
			), 400);
		}
	}*/
	
	public function show($id){
		$sub_categories = SubCategory::with('products', 'products.singleImage')->where('is_deleted', 0)->where('id', $id)->first(array('id'));
		if(!$sub_categories){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid sub category id'
			), 400);
		}
		
		return Response::json(array(
			'error' => false,
			'data'  => $sub_categories,
		), 200);
	}

}