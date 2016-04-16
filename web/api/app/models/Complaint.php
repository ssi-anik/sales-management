<?php

class Complaint extends \Eloquent {
    protected $hidden = array('created_at', 'updated_at');
	protected $fillable = ['*'];
}