<?php

class Category extends \Eloquent {
    protected $table = 'categories';
    protected $hidden = array('created_at', 'updated_at');
	protected $fillable = ['*'];
	
	public function subcategories(){
	    return $this->hasMany('SubCategory', 'category_id', 'id')->where('is_deleted', 0)->select('id', 'name', 'category_id', 'sub_category_photo');
	}
	
	public function products(){
	    return $this->morphMany('Product', 'hierarchy')->where('is_deleted', '0')->select('id', 'hierarchy_id', 'hierarchy_type', 'name');
	}
}