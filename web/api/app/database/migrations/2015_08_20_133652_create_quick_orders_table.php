<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateQuickOrdersTable extends Migration {

	/**
	 * Run the migrations.
	 *
	 * @return void
	 */
	public function up()
	{
		Schema::create('quick_orders', function(Blueprint $table)
		{
			$table->increments('id');
			$table->string('order_key');
			$table->integer('product_id');
			$table->integer('quantity');
			$table->string('mobile_number');
			$table->double('price');
			$table->string('order_remarks')->nullable();
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
		Schema::drop('quick_orders');
	}

}
