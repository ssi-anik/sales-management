<?php

class ProductController extends \BaseController {

	private $retrieve_values = array(
		'id', 
		'name', 
		'description', 
		'attributes', 
		'product_unique_id', 
		'delivery_time', 
		'product_brand', 
		'product_class', 
		'product_guarantee',
	);
	
	public function index(){
		$products = Product::with('taxes', 'taxes.percentage')->where('is_deleted', 0)->limit(10)->get(array_merge($this->retrieve_values, array('dealer_price')));
		if(!$products){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid product id'
			), 400);
		}
		
		return Response::json(array(
			'error' => false,
			'data'  => $products,
		), 200);
	}

	public function show($id) {
		$products = Product::with('images', 'taxes', 'taxes.percentage', 'links')->where('is_deleted', 0)->where('id', $id)->first($this->retrieve_values);
		if(!$products){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid product id'
			), 400);
		}
		
		return Response::json(array(
			'error' => false,
			'data'  => $products,
		), 200);
	}
	
	public function ask($mobile_number, $product_id){
		if(is_null($mobile_number) || empty($mobile_number)){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid mobile_number'
			), 400);
		}
		$product = Product::where('id', $product_id)->where('is_deleted', 0)->first();
		if(!$product){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid product id'
			), 400);
		}
		$agent = Agent::where('phone', $mobile_number)->first();
		if($agent){
			return Response::json(array(
				'error' => false,
				'data'  => $product->price_class_a,
			), 200);
		} else{
			$shop = Shop::where('phone', $mobile_number)->first();
			if($shop){
				$price = 0.0;
				if($shop->shop_type == 1){
					$price = $product->price_class_a;
				} elseif($shop->shop_type == 2){
					$price = $product->price_class_b;
				} elseif($shop->shop_type  == 3){
					$price = $product->price_class_c;
				} elseif($shop->shop_type == 4){
					$price = $product->price_class_d;
				}
				return Response::json(array(
					'error' => false,
					'data'  => $price
				), 200);
			}
		}
		
		return Response::json(array(
			'error' => true,
			'reason' => 'Not a valid mobile number id'
		), 400);
	}
	
	public function order($mobile_number){
		if(is_null($mobile_number) || empty($mobile_number)){
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid mobile_number'
			), 400);
		}
		
		$shop = Shop::where('phone', $mobile_number)->first();
		$price_field = '';
		if($shop){
			if($shop->shop_type == 1){
				$price_field = 'price_class_a';
			} elseif($shop->shop_type == 2){
				$price_field = 'price_class_b';
			} elseif($shop->shop_type  == 3){
				$price_field = 'price_class_c';
			} elseif($shop->shop_type == 4){
				$price_field = 'price_class_d';
			}
			$products = Product::with('taxes', 'taxes.percentage')->where('is_deleted', 0)->get(array_merge($this->retrieve_values, array($price_field)));
			if(!$products){
				return Response::json(array(
					'error' => true,
					'reason' => 'Not a valid product id'
				), 400);
			}
			return Response::json(array(
				'error' => false,
				'data'  => $products,
			), 200);
		} else{
			return Response::json(array(
				'error' => true,
				'reason' => 'Not a valid mobile number id'
			), 400);
		}
	}
}