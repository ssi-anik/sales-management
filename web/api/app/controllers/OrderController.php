<?php

class OrderController extends \BaseController {
	public function index(){
		return Order::all();
	}
	

	public function store() {
		$rules = array(
			'batch' => 'required',
			'product_id' => 'required', // product_id
			'quantity' => 'required', // quantity of products
			'user_type' => 'required', // user type, agent/ shop
			'price' => 'required', // total price
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
		$order = new Order();
		$order->order_key = Input::get('batch');
		$order->product_id = Input::get('product_id');
		$order->quantity = Input::get('quantity');
		$order->mobile_number = Input::get('order_by');
		$order->order_by = Input::get('order_by');
		$order->order_for = Input::get('order_for');
		$order->price = Input::get('price');
		$order->user_type = $user_type;
		$order->save();
		return Response::json(array(
				'error' => false,
				'reason' => array(
					'batch' => Input::get('batch'),
					'product_id' => Input::get('product_id'),
					'message' => 'Order is placed successfully'
				)
			), 200);
	}


	public function show($order_key) {
		$order = Order::where('order_key', $order_key)->where('is_delivered', 1)->first();
		if(!$order){
			return Response::json(array(
				'error' => true,
				'data' => array(
					'reason' => 'Order does not exists or not delivered!'
				)), 400);
		}
		return Response::json(array(
			'error' => false,
			'data' =>array(
				'batch' => $order_key,
				'order' => $order->order_remarks
			)
		), 200);
	}

}