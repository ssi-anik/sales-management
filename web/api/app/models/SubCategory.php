<?php

class SubCategory extends \Eloquent {
    protected $table = 'sub_categories';
    protected $hidden = array('created_at', 'updated_at');
	protected $fillable = ['*'];
	
	public function category(){
	    return $this->belongsTo('Category', 'category_id', 'id');
	}
	
	public function products(){
	    return $this->morphMany('Product', 'hierarchy')->where('is_deleted', '0')->select('id', 'hierarchy_id', 'hierarchy_type', 'name');
	}
}