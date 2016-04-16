<?php

class Tracker extends \Eloquent {
	protected $fillable = array('*');
	protected $hidden = array('id', 'created_at', 'updated_at');
}