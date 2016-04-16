<?php

//use Illuminate\Auth\UserInterface;
//use Illuminate\Auth\Reminders\RemindableInterface;

class Contract extends Eloquent  {

	/**
	 * The database table used by the model.
	 *
	 * @var string
	 */

	protected $table = 'billing_contracts';

	public $timestamps = false;

	/**
	 * The attributes excluded from the model's JSON form.
	 *
	 * @var array
	 */

	//protected $hidden = array('password');


}