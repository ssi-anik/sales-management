<?php

class ShopController extends \BaseController {

	public function show($mobile_number){
		$agent = Agent::where('phone', $mobile_number)->first();
		if(!$agent){
			return Response::json(array(
					'error' => true,
					'reason' => 'Not a valid mobile number',
				), 400);
		}
		$shops = Shop::where('is_deleted', 0)->where('agent_id', $agent->id)->get(array('id', 'company_name', 'own_name', 'phone'));
		return Response::json(array(
			'error' => false,
			'data' => $shops,
		), 200);
	}
}