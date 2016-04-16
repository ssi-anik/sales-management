<?php

class Offer extends \Eloquent {
    protected $table = 'offers';
    protected $hidden = array('created_at', 'updated_at', 'is_deleted');
	protected $fillable = ['*'];
}