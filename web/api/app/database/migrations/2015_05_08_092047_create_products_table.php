<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateProductsTable extends Migration {

	/**
	 * Run the migrations.
	 *
	 * @return void
	 */
	 
	 /**
	  * MOBILE MIGRATION
	  * "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
      * "name TEXT NOT NULL, " +
      * "description TEXT NOT NULL, " +
      * "mrp REAL NOT NULL, " +
      * "best_buy REAL NOT NULL, " +
      * "dealer_price REAL NOT NULL, " +
      * "attributes TEXT DEFAULT NULL, " +
      * "sub_category_id INTEGER NOT NULL " +
	  */
	  
	public function up()
	{
		Schema::create('products', function(Blueprint $table)
		{
			$table->increments('id');
			$table->string('name');
			$table->text('description');
			$table->double('mrp', 5, 2);
			$table->double('best_buy', 5, 2);
			$table->double('dealer_price', 5, 2);
			$table->string('attributes');
			$table->integer('sub_category_id');
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
		Schema::drop('products');
	}

}
