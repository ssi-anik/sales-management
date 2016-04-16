<?php

class AccountController extends BaseController {

	public function getLogin()
	{
		return View::make('account.login');
	}

	public function postLogin()
	{
		$rules = array(
        	'username' => 'required',
        	'password' => 'required',

    	);
 
    	$validator = Validator::make(Input::all(), $rules);

    	if($validator->fails()){
    		Input::flash();
    		return Redirect::to('account/login')->withErrors($validator);
    	}

    	try{

            $userdata = array(
                'login' => Input::get('username'),
                'password' => Input::get('password')
                );
                if ( Auth::attempt($userdata) ){
                        if(Auth::check()){ //check if the user is already logged in
                            $user_id = Auth::user()->core_user_id;

                            //get user data
                            $user = user::where('core_user_id', '=', $user_id)
                                ->first(array('core_user_id','school_id', 'rol_id','lang', 'status'));

                            $status = $user->status;

                            if($status == 1){ //check if user account is enabled
                            Session::put('username', $user->core_user_id);
                            Session::put('school_id', $user->school_id);
                            Session::put('lang', $user->lang);
                            Session::put('rol_id', $user->rol_id);
                            //menu query
                            //$menu = DB::table('main_menu')->where('status', '=', 1)->get();
                                return Redirect::to('home/index');

                            // redirect according to roles
                                // if($user->rol_id == 1000 ){
                                //     // principal
                                //     return Redirect::to('home/principal');
                                // }elseif ($user->rol_id == 1010 ) {
                                //     // software admin
                                //     return Redirect::to('home/softadmin');
                                // }elseif ($user->rol_id == 1020 ) {
                                //     // nfm
                                //     return Redirect::to('home/nusoftmanager');
                                // }elseif ($user->rol_id == 1030 ) {
                                //     // nfa
                                //     return Redirect::to('home/nusoftassistant');
                                // }elseif ($user->rol_id == 1040 ) {
                                //     // school financial manager
                                //     return Redirect::to('home/index');
                                // }elseif ($user->rol_id == 1050 ) {
                                //     // sfa
                                //     return Redirect::to('home/schoolassistant');
                                // }elseif ($user->rol_id == 1070 ) {
                                //     // parent
                                //     return Redirect::to('ps/index');
                                // }elseif ($user->rol_id == 1080 ) {
                                //     // student
                                //     return Redirect::to('ps/index');
                                // }else{
                                //     return Redirect::to('account/login')
                                //     ->with('status', 'Technical error : home page not found');
                                // }

                            }else{//if user account is disabled
                                return Redirect::to('account/login')
                                    ->with('status', 'your account is disabled');
                            }
                        }
                    } else {
                        return Redirect::to('account/login')->with('status', 'invalid username or password');
                    }

    	}
    	catch(Exception $ex){
            //return $ex;
            return Redirect::to('account/login')->with('status','Server Error : Please check your server connection');
    		// return View::make('Account.Register')->with('message','<div class="alert alert-error error_fix"><button type="button" class="close" data-dismiss="alert">&times;</button>Oops !! Something Went Wrong . Please Try Again Later.</div>');
		}
	}

    public function getForgetpassword()
    {
        return View::make('account.forgetpassword');
    }

    public function getForgetusername()
    {
        return View::make('account.forgetusername');
    }

}