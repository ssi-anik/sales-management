<?php

class Shop extends \Eloquent {

	protected $table = 'shops';

	protected $fillable = array();
	
	public function agent(){
		return $this->belongsTo('Agent', 'agent_id', 'id')->select(array('id', 'agent_name', 'phone', 'email', 'address'));
	}

}