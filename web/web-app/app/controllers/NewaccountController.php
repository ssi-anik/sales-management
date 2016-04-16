<?php

class NewaccountController extends BaseController {

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
		//return View::make('news.index');
	}



	public function postCreate()
	{

        $type = Input::get('type');
        // check if type exists
        if(empty($type) || is_null($type))
        	return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Type is not defined'), 400);
        // check if the user type is SHOP or AGENT
        if($type != "shops" && $type != "agents"){
        	return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Not a valid user type'), 417);
        }
        $phone = Input::get('phone');
        // check if phone number exists
        if(empty($phone) || is_null($phone))
        	return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Phone number is not defined'), 400);
        // cheeck if the phone number is valid
        if(Validator::make(array('phone' => $phone), array('phone' => 'numeric'))->fails()){
        	return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Not a valid phone number'), 417);
        }

        // check the uniqueness of the phone number in shops table
        $rules = array('phone' => 'unique:shops,phone');
        $validator = Validator::make(Input::all(), $rules);

        // if the phone number is not unique in shop table
        if($validator->fails()){
        	return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Phone number is already registered'), 409);
        }
        // check the uniqueness of the phone number in agents table
        $rules = array('phone' => 'unique:agents,phone');
        $validator = Validator::make(Input::all(), $rules);

        // if the phone number is not unique in shop table
        if($validator->fails()){
        	return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Phone number is already registered'), 409);
        }

        // checking is done
        // everthing is good
        try{
        	// activation link code is here
        	$active_link = rand(1000, 9999);
        	// your twillo api logic here, 
        	// $active link is the unique id
         	if($type == 'shops'){
        		$query = DB::table('shops')->insert(array('phone' => Input::get('phone'), 'activate' => $active_link));
			} else{
				$query = DB::table('agents')->insert(array('phone' => Input::get('phone'), 'activate' => $active_link));
			}
	    		
    		if($query){
				return Response::json(array('error'=> false, 'status' => 'Ok', 'cause' => 'Message is sent to '.$phone), 200);
    		} else{
    			return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Internal error'), 500);
    		}
        }catch(Exception $ex){
	            return $ex;
        }
    }

    public function postActivation(){
        $type = Input::get('type');
        // check if type exists
        if(empty($type) || is_null($type))
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Type is not defined'), 400);
        // check if the user type is SHOP or AGENT
        if($type != "shops" && $type != "agents"){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Not a valid user type'), 417);
        }
        $phone = Input::get('phone');
        // check if phone number exists
        if(empty($phone) || is_null($phone))
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Phone number is not defined'), 400);
        // cheeck if the phone number is valid
        if(Validator::make(array('phone' => $phone), array('phone' => 'numeric'))->fails()){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Not a valid phone number'), 417);
        }
        $user_activation_code = Input::get('activate');
        // check if activate exists
        if(empty($user_activation_code) || is_null($user_activation_code))
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Activation code is empty'), 400);

        // check the uniqueness of the phone number in shops table
        $rules = array('phone' => 'exists:shops,phone');
        if($type == "shops"){
            $rules = array('phone' => 'exists:shops,phone');
        } elseif($type == "agents"){
            $rules = array('phone' => 'exists:agents,phone');
        }
        $validator = Validator::make(Input::all(), $rules);
        // if the phone number is not exists in shop table
        if($validator->fails()){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => "You have to register your mobile number first"), 409);
        }

        $db_activation_code = \DB::table($type)->where('phone', $phone)->first(array('activate'));
        if($user_activation_code != $db_activation_code->activate){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => "Activation code doesn't seem to be ok."), 417);
        }

        // insert this api key into database table
        $api_key = uniqid($type."_", true);
        return Response::json(array('error'=> false, 'status' => 'Ok', 'cause' => 'Your mobile number is successfully registered', 'api_key' => $api_key), 200);


    }

    public function postResend(){
        $type = Input::get('type');
        // check if type exists
        if(empty($type) || is_null($type))
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Type is not defined'), 400);
        // check if the user type is SHOP or AGENT
        if($type != "shops" && $type != "agents"){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Not a valid user type'), 417);
        }
        $phone = Input::get('phone');
        // check if phone number exists
        if(empty($phone) || is_null($phone))
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Phone number is not defined'), 400);
        // cheeck if the phone number is valid
        if(Validator::make(array('phone' => $phone), array('phone' => 'numeric'))->fails()){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => 'Not a valid phone number'), 417);
        }

        $rules = array('phone' => 'exists:shops,phone');
        if($type == "shops"){
            $rules = array('phone' => 'exists:shops,phone');
        } elseif($type == "agents"){
            $rules = array('phone' => 'exists:agents,phone');
        }
        $validator = Validator::make(Input::all(), $rules);
        // if the phone number is not exists in shop table
        if($validator->fails()){
            return Response::json(array('status' => 'error', 'error' => true, 'cause' => "You have to register your mobile number first"), 409);
        }

        // send activation code again, 
        // handle your messaging api


        return Response::json(array('error'=> false, 'status' => 'Ok', 'cause' => 'Activation code is again sent to '.$phone), 200);

    }

}