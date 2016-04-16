<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ProductsTableSeeder extends Seeder {

	private $product_images_array = array(
		'http://mockup.in/sales-management/product_images/01488_spaghettijunction_1280x800.jpg',
		'http://mockup.in/sales-management/product_images/01510_sunsetbeach_1280x800.jpg',
		'http://mockup.in/sales-management/product_images/01725_thelookout_1280x800.jpg',
		'http://mockup.in/sales-management/product_images/1238922_10151939107098336_1544251896_n.jpg',
		'http://mockup.in/sales-management/product_images/9.jpg',
		'http://mockup.in/sales-management/product_images/6.jpg',
		'http://mockup.in/sales-management/product_images/3.jpg',
		'http://mockup.in/sales-management/product_images/14774.jpg',
		'http://mockup.in/sales-management/product_images/abc.png',
	);
	private $delivery_time_array = array(
		'1 day',
		'2 days',
		'5 days',
		'3 days',
		'4 days', 
		'6 days',
		'1 week', 
	);
	
	private $hierarchy_array = array(
		array('Category', '12'),
		array('SubCategory', '33'),
	);
	
	private $product_class_array = array(1, 2, 3, 4);
	private $product_guarantee_array = array(1, 2, 3, 4, 5, 6);

	public function run(){
		
		$faker = Faker::create();
		
		Product::truncate();
		ProductImage::truncate();
		ProductTax::truncate();
		Link::truncate();
		
		Eloquent::unguard();
		foreach(range(1, 50) as $i){
			
			shuffle($this->delivery_time_array);
			shuffle($this->product_class_array);
			shuffle($this->product_guarantee_array);
			shuffle($this->hierarchy_array);
			$selected = $this->hierarchy_array[0];
			
			$name 						= implode(" ", $faker->words(5));
			$description 				= implode(" ", $faker->words(20));
			$attributes 				= implode("\n", $faker->sentences(3));
			$product_unique_id 			= \uniqid("product_", true);
			
			$mrp 						= $faker->randomFloat(2, 50, 500);
			$regular_price 				= $faker->randomFloat(2, $mrp - 10, $mrp);
			$best_buy 					= $faker->randomFloat(2, $regular_price - 10, $regular_price);
			$dealer_price 				= $faker->randomFloat(2, $best_buy - 10, $best_buy);
			$price_class_a 				= $dealer_price;
			$price_class_b 				= $best_buy;
			$price_class_c 				= $regular_price;
			$price_class_d 				= $mrp;
			
			$delivery_time 				= $this->delivery_time_array[0]; // random value from array();
			$product_brand 				= implode(" ", $faker->words(5));
			$product_class 				= $this->product_class_array[0]; // random value for array(1, 2, 3, 4);
			$product_guarantee 			= $this->product_guarantee_array[0]; // random value from guarantees table;
			$hierarchy_id 				= rand(1, $selected[1]); // random value from either category or sub category;
			$hierarchy_type 			= $selected[0]; // random value according to the h_id;
			
			$product 					= new Product();
			$product->name 				= $name;
			$product->description 		= $description;
			$product->attributes 		= $attributes;
			$product->product_unique_id = $product_unique_id;
			$product->mrp 				= $mrp;
			$product->regular_price 	= $regular_price;
			$product->best_buy 			= $best_buy;
			$product->dealer_price 		= $dealer_price;
			$product->price_class_a 	= $price_class_a;
			$product->price_class_b 	= $price_class_b;
			$product->price_class_c 	= $price_class_c;
			$product->price_class_d 	= $price_class_d;
			$product->delivery_time 	= $delivery_time;
			$product->product_brand 	= $product_brand;
			$product->product_class 	= $product_class;
			$product->product_guarantee = $product_guarantee;
			$product->hierarchy_id 		= $hierarchy_id;
			$product->hierarchy_type 	= $hierarchy_type;
			$product->save();
			$taxes = range(1, 8);
			
			shuffle($taxes);
			foreach(range(1, rand(1, 5)) as $index){
				ProductTax::create([
					'tax_id' 			=> $taxes[$index],
					'product_id' 		=> $product->id,
				]);
			}
			
			foreach(range(1, rand(1, 5)) as $link_index){
				Link::create([
					'link' 				=> $faker->url,
					'product_id' 		=> $product->id,
				]);
			}
			
			shuffle($this->product_images_array);
			foreach(range(1, rand(1, 5)) as $image_index){
				ProductImage::create([
					'image_link' 		=> $this->product_images_array[$image_index],
					'product_id' 		=> $product->id
				]);	
			}
		}
	}

}