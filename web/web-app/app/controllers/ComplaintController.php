<?php

class ComplaintController extends BaseController {
	
	public function getIndex()
	{
		$query = DB::table('complaints')->get();

		return View::make('complaint.index',array('data_list'=>$query));
	}
	public function getCreate()
	{
		return View::make('complaint.create');
	}

	public function postCreate()
	{
		$rules = array(
            'title' => 'required',
            'image' => 'required',
            'is_solved' => 'required'
        );

        $validator = Validator::make(Input::all(), $rules);

        if($validator->fails()){
            Input::flash();
            return Redirect::to('complaint/create')->withErrors($validator);
        }
        
        try{
            
            if(Input::hasFile('image')){

                $name = Input::file('image')->getClientOriginalName();

                Input::file('image')->move('images/complaints', $name);

                $query = DB::table('complaints')->insert(array('mobile_number' => Input::get('title'),'is_solved'=>Input::get('is_solved'),'image_path'=>$name));

        		if($query){
                    return Redirect::to('complaint/index');
        		}
            }
            else{
                //$query = DB::table('complaints')->insert(array('mobile_number' => Input::get('title'),'ab_desc'=>Input::get('description'),'ab_date'=>Input::get('date')));

                //if($query){
                    return Redirect::to('complaint/create');
                //}
            }
        }

        catch(Exception $ex){
            return $ex;
        }
	}

	public function getEdit($id)
	{
		$query = DB::table('complaints')->where('id','=',$id)->first();
		return View::make('complaint.edit')->with('_item',$query);
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
            return Redirect::to('complaint/edit/'.Input::get('id'))->withErrors($validator);
        }

        try{


            if(Input::hasFile('image')){

                $name = Input::file('image')->getClientOriginalName();

                Input::file('image')->move('newz_pics', $name);

            	$query = DB::table('complaints')
                        ->where('id', '=', Input::get('id'))
                        ->update(array('ab_title' => Input::get('title'),'ab_desc'=>Input::get('description'),'ab_date'=>Input::get('date'),'ab_image'=>$name));

                return Redirect::to('complaint/index');
            }
            else{
                $query = DB::table('complaints')
                        ->where('id', '=', Input::get('id'))
                        ->update(array('mobile_number' => Input::get('title'),'is_solved'=>Input::get('is_solved')));

                return Redirect::to('complaint/index');
            }
        }
        catch(Exception $ex){
        	return $ex;
        }
	}

	public function getDelete($id)
	{
		try{
			DB::table('complaints')->where('id','=',$id)->delete();
			return Redirect::to('complaint/index');
		}
		catch(Exception $ex)
		{
			return $ex;
		}
	}
	
	public function store()
	{
		$ruleForImage = array(
			'image' => 'required|image',
			'tracking_number' => 'required',
			'api_key' => 'required'
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
				'api_key' => 'required',
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
			$complaint->mobile_number = Input::get('api_key');
			$complaint->tracking_number = Input::get('tracking_number');
			$complaint->is_solved = 0;
			$complaint->image_path = '';
			$complaint->save();
			
			return Response::json(array(
				'error' => false,
				'reason' => array(
					"Complaint is submitted successfully"
				)
			), 200);
			
		} else{
			// input has image
			$image = Input::file('image');
			$destination_path = base_path()."/images/complaints/";
			$file_name = md5(strtotime("now"))."_".$image->getClientOriginalName();
			$file_path = $destination_path.$file_name;
			$image->move($destination_path, $file_name);
			$complaint = new Complaint();
			$complaint->image_path = $file_name;
			$complaint->mobile_number = Input::get('api_key');
			$complaint->tracking_number = Input::get("tracking_number");
			$complaint->save();
			
			return Response::json(array(
				'error' => false,
				'reason' => array(
					"Complaint is submitted successfully"
				)
			), 200);
			
		}
	}

	/**
	 * Display the specified resource.
	 * GET /compaints/{id}
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function show($tracking_number)
	{
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
				'solution' => $complaint->solution
			)
		), 200);
	}

}