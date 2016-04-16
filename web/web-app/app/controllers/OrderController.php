<?php

class OrderController extends BaseController {

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
		return View::make('order.index');
	}

	public function getAllitem()
	{
	    $order_keys = DB::table('orders')->select(DB::raw('DISTINCT(order_key) AS order_key'))->get();
	    $total_orders = array();
        foreach($order_keys as $keys){
        	$orders = DB::table('orders')->where('order_key', $keys->order_key)->get();
        	$single_order = array();
        	$products = "";
        	$total_price = 0;
        	$mobile_number = '';
        	foreach($orders as $order){
        		$products .= (DB::table('products')->where('id', $order->product_id)->first()->name)." (". $order->quantity ."), ";
        		$total_price += $order->quantity * $order->price;
        		$mobile_number = $order->mobile_number;
        	}
        	$ordered_by = "";
        	$agent = DB::table('agents')->where('phone', $mobile_number)->first();
        	if($agent){
        		$ordered_by = $agent->agent_name." (A)";
        	} else{
        		$ordered_by = DB::table('shops')->where('phone', $mobile_number)->first()->own_name." (S)";
        	}
        	//$single_order['id'] = $keys->order_key;
        	$single_order['order_key'] = $keys->order_key;
        	$single_order["products"] = substr($products, 0, strlen($products)-2); 
        	$single_order['ordered_by'] = $ordered_by;
        	$single_order['price'] = $total_price;
        	$single_order['is_delivered'] = DB::table("orders")->where('order_key', $keys->order_key)->first()->is_delivered == '1' ? "Delivered" : "Not delivered";
        	$total_orders[] = $single_order;
        }
        return Response::json(array('data'=>$total_orders));
	}


	public function getEdit()
	{
		return View::make('order.edit');
	}

	public function getAdd()
	{
		return View::make('order.add');
	}
	public function getOrder()
	{
		return View::make('agent.order');
	}
	public function getCompleted()
	{
		return View::make('agent.completed');
	}
	public function getBonus()
	{
		return View::make('agent.bonus');
	}
	public function getAgent()
	{
		return View::make('agent.agent');
	}
	public function getComplaint()
	{
		return View::make('agent.complaint');
	}
	
	// public function getAllitem()
	// {
	//     $items = DB::table('billing_item_groups')
	//     	->select('item_group_id AS id','name AS title','start','end','url')
	//     		->get();
 //        // return View::make('setting.enrollment.index',array('item'=>$items));
 //        return Response::json($items);
	// }

	// public function postEdit($id)
	// {
	//     $items = DB::table('billing_item_groups')
	//     	->select('item_group_id AS id','name AS title','start','end','url')
	//     		->get();
 //        // return View::make('setting.enrollment.index',array('item'=>$items));
 //        return Response::json($items);
	// }
}