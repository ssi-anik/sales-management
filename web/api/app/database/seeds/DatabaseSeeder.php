<?php

class DatabaseSeeder extends Seeder {

	private $tables = array(
			'categories',
			'sub_categories',
			'products',
			'product_images',
			'news',
			'offers',
			'complaints',
			'shops'
		);
		
	private $seeders = array(
			'CategoriesTableSeeder',
			'SubCategoriesTableSeeder',
			'ProductsTableSeeder',
			'NewsTableSeeder',
			'OffersTableSeeder',
			//'ComplainsTableSeeder'
			'ShopTableSeeder',
		);
	public function run()
	{
		Eloquent::unguard();
		// truncate table
		foreach($this->tables as $table){
			DB::table($table)->truncate();
		}
		// seed table
		foreach($this->seeders as $seeder){
			$this->call($seeder);
		}
	}

}
