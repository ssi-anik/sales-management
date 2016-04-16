<?php

class Product extends \Eloquent {
    protected $table = 'products';
    protected $hidden = array('created_at', 'updated_at');
	protected $fillable = ['*'];
	
	public function getProductClassAttribute($value){
		$product_classes = array(1 => 'Basic', 2 => 'Mid', 3 => 'Regular', 4 => 'Premium');
		return $product_classes[$value];
	}
	
	public function getProductGuaranteeAttribute($value){
		return Guarantee::where('id', $value)->first()->description;
	}
	
	public function sub_category() {
	    return $this->belongsTo('SubCategory', 'sub_category_id', 'id');
	}
	
	public function images(){
		return $this->hasMany('ProductImage', 'product_id', 'id')->where('is_deleted', 0);
	}
	
	public function taxes(){
		return $this->hasMany('ProductTax', 'product_id', 'id')->where('is_deleted', 0);
	}
	
	public function guarantee(){
		return $this->belongsTo('Guarantee', 'product_guarantee', 'id');
	}
	
	public function links(){
		return $this->hasMany('Link', 'product_id', 'id')->where('is_deleted', 0);
	}
	
	public function singleImage(){
		return $this->hasOne('ProductImage', 'product_id', 'id');
	    //return $this->hasMany('ProductImage', 'product_id', 'id')->limit(1);
	}
	
	public function hierarchy(){
		return $this->morphTo();
	}
}