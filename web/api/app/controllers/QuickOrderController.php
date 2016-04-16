<?php

class QuickOrderController extends \BaseController {

	public function index() {
		return Quick::all();
	}

	public function store() {
		$rules = array(
			'batch' => 'required',
			'product_id' => 'required', // product_id
			'quantity' => 'required', // quantity of products
			'user_type' => 'required', // user type, agent/ shop
			'price' => 'required', // product price
			'total_price' => 'required', // total price
			'order_by' => 'required', // agent or shop mobile number
			'order_for' => 'required', // for agent this will be shop mobile number, for shop this will be shop mobile number too
		);
		$validator = Validator::make(Input::all(), $rules);
		if($validator->fails() || !in_array(Input::get('user_type'), array('agent', 'shop'))){
			return Response::json(array(
				'error' => true,
				'cause' => count($validator->messages()) > 0 ? $validator->messages() : 'Not a valid user type'
			), 400);
		}
		$user_type = '';
		$agent = Agent::where('phone', Input::get('order_by'))->first();
		if(!$agent){
			$shop = Shop::where('phone', Input::get('order_by'))->first();
			if(!$shop){
				return Response::json(array(
				'error' => true,
				'cause' => 'Not a valid mobile number'
			), 400);
			} else{
				$user_type = 'Shop';
			}
		} else{
			$user_type = 'Agent';
		}
		
		// Validation passes
		// insert those data into database
		$quick = new Quick();
		$quick->order_key = Input::get('batch');
		$quick->product_id = Input::get('product_id');
		$quick->quantity = Input::get('quantity');
		$quick->mobile_number = Input::get('order_by');
		$quick->order_by = Input::get('order_by');
		$quick->order_for = Input::get('order_for');
		$quick->price = Input::get('price');
		$quick->total_price = Input::get('total_price');
		$quick->user_type = $user_type;
		$quick->save();
		return Response::json(array(
				'error' => false,
				'reason' => array(
					'batch' => Input::get('batch'),
					'product_id' => Input::get('product_id'),
					'message' => 'Quick order is placed successfully'
				)
			), 200);
	}
	
	public function show($order_key) {
		/*
		 * is_accepted = 0 // order is not accepted yet
		 * is_accepted = 1 // order is accepted, tax applied
		 * is_accepted = 2 // order is accepted, tax not applied
		 **/
		$quick = Quick::where('order_key', $order_key)->first();
		if(!$quick){
			return Response::json(array(
				'error' => true,
				'data' => array(
					'reason' => 'Order does not exists or not delivered!'
				)), 400);
		}
		if($quick->is_accepted == 0){
			return Response::json(array(
					'error' => false,
					'data' => 'Order is yet not accepted'
				), 200); // ok, not touched yet
		} elseif($quick->is_accepted == 1){
			return Response::json(array(
					'error' => false,
					'data' => array(
						'batch' => $order_key,
						'message' => 'Order is accepted and tax applied',
					)), 201); // created, tax applied
		} elseif($quick->is_accepted == 2){
			return Response::json(array(
					'error' => false,
					'data' => array(
						'batch' => $order_key,
						'message' => 'Order is accepted and tax not applied',
					)), 202); // accepted
		}
	}

}