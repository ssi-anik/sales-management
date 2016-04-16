<?php

class NewsController extends \BaseController {

	public function index(){
		$news = News::where('is_deleted', 0)->get();
		return Response::json(array(
			'error' => false,
			'data' => $news
		), 200);
	}

}