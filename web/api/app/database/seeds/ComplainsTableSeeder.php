<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ComplainsTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();

		foreach(range(1, 10) as $index)
		{
			Complain::create([
				'title' => implode(' ', $faker->words(6)),
				'description' => $faker->realText(),
			]);
		}
	}

}