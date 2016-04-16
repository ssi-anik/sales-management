<?php

class UserController extends \BaseController {

	public function isValidUserType($type){
		$valid_user_types = array(
			'shop', 
			'agent'
		);
		
		if(empty($type) || !in_array($type, $valid_user_types)){
			return false;
		}
		return true;
	}

	public function store(){
		$valid_user_types = array(
			'shop', 
			'agent'
		);
		
		if(!$this->isValidUserType(Input::get('user_type'))){
			return Response::json(array(
				'error' => true,
				'reasons' => 'Not a valid user type'
			), 400);
		}
		
		$user_type = Input::get('user_type');
		// if the user_type is agent
		if($user_type == 'agent'){
			$rules = array(
				'user_type' => 'required',
				'mobile_number' => "required|unique:agents,phone"
			);
			
			$validation = Validator::make(Input::all(), $rules);
			if($validation->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validation->errors()
				), 400);
			}
			
			$next_rule = array(
				'mobile_number' => "unique:shops,phone"
			);
			
			$validation = Validator::make(Input::all(), $next_rule);
			if($validation->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validation->errors()
				), 400);
			}
			
			$messaged_secret_key = mt_rand(1000, 9999);
			$agent = new Agent();
			$agent->phone = Input::get('mobile_number');
			$agent->activate = $messaged_secret_key;
			$agent->save();
			
			return Response::json(array(
				'error' => false,
				'data' 	=> 'New agent account is created'
			), 200);
			
		// if the user type is shops
		} elseif($user_type == 'shop'){
			$rules = array(
				'user_type' => 'required',
				'mobile_number' => "required|unique:shops,phone",
				'agent_mobile_number' => "required|exists:agents,phone",
			);
			
			$validation = Validator::make(Input::all(), $rules);
			if($validation->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validation->errors()
				), 400);
			}
			
			$next_rule = array(
				'mobile_number' => "unique:agents,phone"
			);
			
			$validation = Validator::make(Input::all(), $next_rule);
			if($validation->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validation->errors()
				), 400);
			}
			
			$messaged_secret_key = mt_rand(1000, 9999);
			$shop = new Shop();
			$shop->phone = Input::get('mobile_number');
			$shop->agent_id = Agent::where('phone', Input::get('agent_mobile_number'))->first()->id;
			$shop->activate = $messaged_secret_key;
			$shop->save();
			
			return Response::json(array(
				'error' => false,
				'data' 	=> 'New shop account is created'
			), 200);
		}
		
	}

	public function show($mobile_number){
		
		if(!$this->isValidUserType(Input::get('user_type'))){
			return Response::json(array(
				'error' => true,
				'reasons' => 'Not a valid user type'
			), 400);
		}
		
		$validateAgainst = array(
			'mobile_number' => $mobile_number,
			'user_type' => Input::get('user_type')
		);
		$rules = array(
			'mobile_number' => 'required|exists:'.str_plural(Input::get('user_type')).',phone',
			'user_type' => 'required'
		);
		$validation = Validator::make($validateAgainst, $rules);
		if($validation->fails()){
			return Response::json(array(
				'error' => true,
				'reason' => $validation->errors()
			), 400);
		}
		
		$user_type = Input::get('user_type');
		if($user_type == "agent"){
			$agent = Agent::where('phone', $mobile_number)->first(array('agent_name', 'phone', 'email', 'address', 'agent_picture', 'bonus'));
			return Response::json(array(
				'error' => false,
				'data' 	=> $agent
			), 200);
		} elseif ($user_type == 'shop') {
			$shop = Shop::with('agent')->where('phone', $mobile_number)->first(array('own_name', 'company_name', 'phone', 'email', 'address', 'agent_id'));
			return Response::json(array(
				'error' => false,
				'data' 	=> $shop
			), 200);
		}
	}
	
	public function update($mobile_number){
		if(!$this->isValidUserType(Input::get('user_type'))){
			return Response::json(array(
				'error' => true,
				'reasons' => 'Not a valid user type'
			), 400);
		}
		
		$validateAgainst = array(
			'mobile_number' => $mobile_number,
			'user_type' => Input::get('user_type'),
			'confirmation_code' => Input::get('confirmation_code')
		);
		$rules = array(
			'mobile_number' => 'required|exists:'.str_plural(Input::get('user_type')).',phone',
			'user_type' => 'required',
			'confirmation_code' => 'required'
		);
		$validation = Validator::make($validateAgainst, $rules);
		if($validation->fails()){
			return Response::json(array(
				'error' => true,
				'reason' => $validation->errors()
			), 400);
		}
		
		$user_type = Input::get('user_type');
		if($user_type == "agent"){
			$agent = Agent::where('phone', $mobile_number)->first();
			if($agent->activate != Input::get('confirmation_code')){
				return Response::json(array(
					'error' => true,
					'reason' => "Confirmation code mismatch"
				), 400);
			}
			
			return Response::json(array(
				'error' => false,
				'data' 	=> array(
					'confirmation' => 'Agent is now registered'
				)), 200);
			
		} elseif ($user_type == 'shop') {
			$shop = Shop::where('phone', $mobile_number)->first();
			if($shop->activate != Input::get('confirmation_code')){
				return Response::json(array(
					'error' => true,
					'reason' => "Confirmation code mismatch"
				), 400);
			}
			return Response::json(array(
				'error' => false,
				'data' 	=> array(
					'confirmation' => 'Shop is now registered'
				)), 200);
		}
	}
	
	public function resend(){
		if(!$this->isValidUserType(Input::get('user_type'))){
			return Response::json(array(
				'error' => true,
				'reasons' => 'Not a valid user type'
			), 400);
		}
		
		$user_type = Input::get('user_type');
		$mobile_number = Input::get('mobile_number');
		// if the user_type is agent
		if($user_type == 'agent'){
			$rules = array(
				'user_type' => 'required',
				'mobile_number' => "required|exists:agents,phone"
			);
			
			$validation = Validator::make(Input::all(), $rules);
			if($validation->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validation->errors()
				), 400);
			}
			
			
			$agent = Agent::where('is_deleted', '0')->where('phone', $mobile_number)->first();
			if(!$agent){
				return Response::json(array(
					'error' => true,
					'reason' => 'Not a valid mobile number'
				), 400);
			}
			
			return Response::json(array(
				'error' => false,
				'data' 	=> 'A message is now sent to your mobile number',
			), 200);
			
		// if the user type is shops
		} elseif($user_type == 'shop'){
			$rules = array(
				'user_type' => 'required',
				'mobile_number' => "required|exists:shops,phone",
				'agent_mobile_number' => 'required|exists:agents,phone',
			);
			
			$validation = Validator::make(Input::all(), $rules);
			if($validation->fails()){
				return Response::json(array(
					'error' => true,
					'reason' => $validation->errors()
				), 400);
			}
			
			$agent_mobile_number = Input::get('agent_mobile_number');
			
			$shop = Shop::where('is_deleted', 0)->where('phone', $mobile_number)->where('agent_id', Agent::where('phone', $agent_mobile_number)->first()->id)->first();
			if(!$shop){
				return Response::json(array(
					'error' => true,
					'reason' => array(
						'input_error' => array('Your mobile number or agent mobile number is not valid')
						) // agent and shop mobile number doesn't match as registered before
				), 400);
			}
			
			return Response::json(array(
				'error' => false,
				'data' 	=> 'A message is now sent to your mobile number'
			), 200);
		}
	}
}