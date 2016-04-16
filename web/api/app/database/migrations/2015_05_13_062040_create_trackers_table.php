<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateTrackersTable extends Migration {

	/**
	 * Run the migrations.
	 *
	 * @return void
	 */
	public function up()
	{
		Schema::create('trackers', function(Blueprint $table)
		{
			$table->increments('id');
			$table->string('mobile_number');
			$table->string('latitude');
			$table->string('longitude');
			$table->string('time');
			$table->timestamps();
		});
	}


	/**
	 * Reverse the migrations.
	 *
	 * @return void
	 */
	public function down()
	{
		Schema::drop('trackers');
	}

}
