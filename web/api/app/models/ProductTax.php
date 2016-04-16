<?php
/**
 * Created by PhpStorm.
 * User: Anik
 * Date: 17-Jul-15, 017
 * Time: 05:29 AM
 */

class ProductTax extends Eloquent{
	protected $table = 'product_taxes';
	protected $fillable = array('tax_id', 'product_id');
	
	public function percentage(){
		return $this->belongsTo('Tax', 'tax_id', 'id');
	}
} 