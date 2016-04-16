<?php
/**
 * Created by PhpStorm.
 * User: Anik
 * Date: 17-Jul-15, 017
 * Time: 05:32 AM
 */

class Link extends Eloquent{
	protected $table = 'links';
	protected $fillable = array('link', 'product_id');
} 