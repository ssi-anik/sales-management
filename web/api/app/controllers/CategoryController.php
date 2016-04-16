<?php

class CategoryController extends \BaseController {
	
	public function index()
	{
		$categories = Category::where('is_deleted', 0)->get(array("id", "name", "category_photo"));
		if($categories){
			return Response::json(array(
				'error' => false,
				'data' => $categories
			), 200);
		} else{
			return Response::json(array(
				'error' => true,
				'reason' => "No category available"
			), 400);
		}
	}
	
	public function show($id){
		$category = Category::with('subcategories.products.singleImage', 'products', 'products.singleImage')->where('is_deleted', 0)->where('id', $id)->first(array('id'));
		if(!$category){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid category id'
			), 400);
		}
		
		return Response::json(array(
			'error' => false,
			'data'  => $category
		), 200);
		
	}

}