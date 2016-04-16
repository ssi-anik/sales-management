<?php

class BillController extends \BaseController{
    public function index(){
        return Bill::all();
    }
    
    public function store(){
        $rules = array(
            'company_mobile_number' => 'required',
            'agent_mobile_number' => 'required',
            'bill_key' => 'required',
            'product_id' => 'required',
            'quantity' => 'required',
            'price' => 'required',
        );
        $validation = Validator::make(Input::all(), $rules);
        if($validation->fails()){
            return Response::json(array(
                'error' => true,
                'cause' => $validation->errors()
            ), 400);
        }
        $company_mobile_number = Input::get('company_mobile_number');
        $agent_mobile_number = Input::get('agent_mobile_number');
        $shop = Shop::where('phone', $company_mobile_number)->first();
        $agent = Agent::where('phone', $agent_mobile_number)->first();
        if(!$shop || !$agent){
            return Response::json(array(
                'error' => true,
                'cause' => 'Not a valid phone number'
            ), 400);
        }
        $bill = new Bill();
        $bill->company_id = $shop->id;
        $bill->mobile_number = $agent_mobile_number;
        $bill->bill_key = Input::get('bill_key');
        $bill->product_id = Input::get('product_id');
        $bill->quantity = Input::get('quantity');
        $bill->price = Input::get('price');
        $bill->save();
        return Response::json(array(
            'error' => false,
				'reason' => array(
					'bill_key' => Input::get('bill_key'),
					'product_id' => Input::get('product_id'),
					'message' => 'Bill is successfully inserted',
				)
		), 200);
    }
}