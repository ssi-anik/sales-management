<?php

class TrackerController extends \BaseController {

	public function index() {
		//Tracker::truncate();
		return Tracker::all();
	}


	public function store() {
		$rules = array(
			'mobile_number' => 'required',
			'latitude' => 'required',
			'longitude' => 'required',
			'time' => 'required'
		);
		$validator = Validator::make(Input::all(), $rules);
		if($validator->fails()){
			return Response::json(array(
				'error' => true,
				'reason' => $validator->messages()
			), 400);
		}
		
		$tracker = new Tracker();
		$tracker->mobile_number = Input::get('mobile_number');
		$tracker->latitude = Input::get('latitude');
		$tracker->longitude = Input::get('longitude');
		$tracker->time = Input::get('time');
		$tracker->save();
		
		if($tracker->id){
			return Response::json(array(
				'error' => false,
				'data' => Input::all()
			), 200);
		}
		
		return Response::json(array(
			'error' => true,
			'reason' => 'Error occurred'
		), 400);
	}

}