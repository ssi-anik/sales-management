<?php


    class Academic extends Eloquent {

    		protected $table = 'academic_terms';

    			public $timestamps = false;

    		//protected $guarded =  array('academic_id');

				// public function getPublishedAttribute($value)
				// {
				// 	return (boolean)$value;
				// }

    }