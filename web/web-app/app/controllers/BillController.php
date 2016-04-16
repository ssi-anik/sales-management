<?php

class BillController extends \BaseController{
    public function index(){
        return View::make('bills.index');
    }
    
    public function allItem(){
        $bill_keys = DB::table('bills')->select(DB::raw('DISTINCT(bill_key) AS bill_key'))->get();
	    $total_bills = array();
        foreach($bill_keys as $keys){
        	$bills = DB::table('bills')->where('bill_key', $keys->bill_key)->get();
        	$single_bill = array();
        	$products = "";
        	$total_price = 0;
        	$mobile_number = '';
        	$asked_by = '';
        	foreach($bills as $bill){
        		$products .= (DB::table('products')->where('id', $bill->product_id)->first()->name)." (". $bill->quantity ."), ";
        		$total_price += $bill->quantity * $bill->price;
        		$mobile_number = $bill->mobile_number;
        		$asked_by = $bill->company_id;
        	}
        	$asked_by = DB::table('shops')->where('id', $asked_by)->first()->own_name;
        	$agent = DB::table('agents')->where('phone', $mobile_number)->first()->agent_name;
        	/*if($agent){
        		$ordered_by = $agent->agent_name." (A)";
        	} else{
        		$ordered_by = DB::table('shops')->where('phone', $mobile_number)->first()->own_name." (S)";
        	}*/
        	//$single_order['id'] = $keys->order_key;
        	$single_bill['bill_key'] = $keys->bill_key;
        	$single_bill["products"] = substr($products, 0, strlen($products)-2); 
        	$single_bill['asked_by'] = $asked_by;
        	$single_bill['price'] = $total_price;
        	$single_bill['agent'] = $agent;
        	$total_bills[] = $single_bill;
        }
        return Response::json(array('data'=>$total_bills));
    }
    
    public function edit($id){
        return $id." edit page";
    }
}