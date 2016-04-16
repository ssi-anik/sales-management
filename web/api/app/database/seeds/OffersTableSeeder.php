<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class OffersTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		Eloquent::unguard();
		foreach(range(1, 10) as $index)
		{
			Offer::create([
				'title' => implode(' ', $faker->words(6)),
				'description' => $faker->realText(),
			]);
		}
	}

}