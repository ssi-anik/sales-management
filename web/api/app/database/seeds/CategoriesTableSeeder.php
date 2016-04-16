<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class CategoriesTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		Category::truncate();
		Eloquent::unguard();
		foreach(range(1, 10) as $index)
		{
			Category::create([
				'name' => implode(" ", $faker->words(3))
			]);
		}
	}

}