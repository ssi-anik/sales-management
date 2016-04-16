<?php
/**
 * Created by PhpStorm.
 * User: Anik
 * Date: 17-Jul-15, 017
 * Time: 02:29 AM
 */

class Guarantee extends \Eloquent {

	protected $table = 'guarantees';
	protected $fillable = array('name', 'description');

} 