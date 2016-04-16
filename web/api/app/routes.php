<?php

// routes those need mobile number to verify if a valid user, works like authentication
Route::group(array('before' => 'isUser'), function(){
    
});
// route those don't need any authentication
Route::resource('users', 'UserController', array('only' => array('store', 'show', 'update')));
Route::post('resend', 'UserController@resend');
Route::resource('categories', 'CategoryController', array('only' => array('index', 'show')));
Route::resource('sub_categories', 'SubCategoryController', array('only' => array('show')));
Route::get('products/order/{mobile_number}', 'ProductController@order');
Route::resource('products', 'ProductController', array('only' => array('index', 'show')));
Route::get('ask/{mobile_number}/{product_id}', 'ProductController@ask');
Route::resource('shops', 'ShopController', array('only' => array('show')));
Route::resource('news', 'NewsController', array('only' => array('index')));
Route::resource('offers', 'OfferController', array('only' => array('index')));
Route::resource('complaints', 'CompaintsController', array('only' => array('index', 'store', 'show')));
Route::resource('orders', 'OrderController');
Route::resource('bills', 'BillController');
Route::resource('trackers', 'TrackerController');
Route::resource('quick', 'QuickOrderController');


