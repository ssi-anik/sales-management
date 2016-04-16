<?php

class CompaintsController extends \BaseController {

	public function index() {
		return Complaint::all();
	}


	public function store() {
		$ruleForImage = array(
			'image' => 'required|image',
			'tracking_number' => 'required',
			'user_type' => 'required',
			'mobile_number' => 'required'
		);
		$validator = Validator::make(Input::all(), $ruleForImage);
		if($validator->fails()){
			if(Input::hasFile('image')){
				return Response::json(array(
					'error' => true,
					'reason' => $validator->messages()
				), 400);
			}
			
			// input has only text complain
			$ruleForTextComplaints = array(
				'title' => 'required', 
				'description' => 'required',
				'mobile_number' => 'required',
				'user_type' => 'required',
				'tracking_number' => 'required',
			);
			
			$validator = Validator::make(Input::all(), $ruleForTextComplaints);
			if($validator->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validator->messages()
				), 400);
			}
			
			$complaint = new Complaint();
			$complaint->title = Input::get('title');
			$complaint->description = Input::get("description");
			$complaint->mobile_number = Input::get('mobile_number');
			$complaint->user_type = ucfirst(Input::get('user_type'));
			$complaint->tracking_number = Input::get('tracking_number');
			$complaint->is_solved = 0;
			$complaint->image_path = '';
			$complaint->save();
			
			return Response::json(array(
				'error' => false,
				'reason' => array(
					"tracking_number" => Input::get("tracking_number"),
					"message" => "Complaint is submitted successfully"
				)
			), 200);
			
		} else{
			// input has image
			$image = Input::file('image');
			$destination_path = base_path()."/images/complaints/";
			$file_name = md5(strtotime("now"))."_".$image->getClientOriginalName();
			$file_path = $destination_path.$file_name;
			$image->move($destination_path, $file_name);
			$file_path = str_replace(base_path(), url('/'), $file_path);
			$complaint = new Complaint();
			$complaint->image_path = $file_path;
			$complaint->mobile_number = Input::get('mobile_number');
			$complaint->user_type = ucfirst(Input::get('user_type'));
			$complaint->tracking_number = Input::get("tracking_number");
			$complaint->save();
			
			return Response::json(array(
				'error' => false,
				'reason' => array(
					"tracking_number" => Input::get("tracking_number"),
					"message" => "Complaint is submitted successfully"
				)
			), 200);
			
		}
	}
	
	public function show($tracking_number) {
		$complaint = Complaint::where('tracking_number', $tracking_number)->where('is_solved', 1)->first();
		if(!$complaint){
			return Response::json(array(
				'error' => true,
				'data' => array(
					'reason' => 'tracking number does not exists or not solved'
				)), 400);
		}
		return Response::json(array(
			'error' => false,
			'data' =>array(
				'tracking_number' => $tracking_number,
				'solution' => $complaint->solution
			)
		), 200);
	}

	/**
	 * Show the form for editing the specified resource.
	 * GET /compaints/{id}/edit
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function edit($id)
	{
		//
	}

	/**
	 * Update the specified resource in storage.
	 * PUT /compaints/{id}
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function update($id)
	{
		//
	}

	/**
	 * Remove the specified resource from storage.
	 * DELETE /compaints/{id}
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function destroy($id)
	{
		//
	}

}