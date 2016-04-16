<?php

class OfferController extends \BaseController {

	public function index() {
		$offers = Offer::where('is_deleted', 0)->get();
		return Response::json(array(
			'error' => false,
			'data' => $offers
		), 200);
	}

}