<?php

class Order extends \Eloquent {
    protected $hidden = array('created_at', 'updated_at');
    protected $table = 'orders';
	protected $fillable = ['*'];
	
	public function product(){
	    return $this->belongsTo('Product', 'product_id', 'id');
	}
}