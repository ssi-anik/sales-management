<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateComplainsTable extends Migration {

	/**
	 * Run the migrations.
	 *
	 * @return void
	 */
	public function up()
	{
		Schema::create('complaints', function(Blueprint $table)
		{
			$table->increments('id');
			$table->string('title');
			$table->text('description');
			$table->string('mobile_number');
			$table->string('tracking_number');
			$table->string('image_path');
			$table->enum('is_solved', array(0,1))->default(0);
			$table->text('solution')->default('');
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
		Schema::drop('complaints');
	}

}
