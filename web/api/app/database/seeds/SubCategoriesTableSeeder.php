<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class SubCategoriesTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		SubCategory::truncate();
		Eloquent::unguard();
		foreach(range(1, 10) as $category) {
			foreach(range(1, 2) as $sub_category) {
				SubCategory::create([
					'name' => implode(" ", $faker->words(3)),
					'category_id' => $category
				]);
			}
		}
	}

}