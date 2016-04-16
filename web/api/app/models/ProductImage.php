<?php

class ProductImage extends \Eloquent {
    protected $table = 'product_images';
    protected $hidden = array('id', 'product_id', 'created_at', 'updated_at');
	protected $fillable = ['*'];
}