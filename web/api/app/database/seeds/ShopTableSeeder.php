<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ShopTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		Eloquent::unguard();
		Shop::truncate();

		foreach(range(1, 10) as $index)
		{
			Shop::create([
				'own_name' => $faker->name,
				'company_name' => $faker->company . " " . $faker->companySuffix,
				'agent_id' => 12,
				'phone' => $faker->phoneNumber,
				'email' => $faker->email,
				'location' => $faker->address,
				'activate' => rand(1000, 9999)
			]);
			
		}
	}

}