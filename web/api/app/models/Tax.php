<?php

class Tax extends \Eloquent{
	protected $table = 'taxes';
	protected $fillable = array('name', 'tax_percentage');
}