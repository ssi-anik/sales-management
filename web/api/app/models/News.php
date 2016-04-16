<?php

class News extends \Eloquent {
    protected $hidden = array('created_at', 'updated_at', 'is_deleted');
    protected $table = 'news';
	protected $fillable = ['*'];
}