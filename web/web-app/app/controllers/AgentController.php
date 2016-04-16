<?php

class AgentController extends BaseController {

	/*
	|--------------------------------------------------------------------------
	| Default Home Controller
	|--------------------------------------------------------------------------
	|
	| You may wish to use controllers instead of, or in addition to, Closure
	| based routes. That's great! Here is an example controller method to
	| get you started. To route to this controller, just add the route:
	|
	|	Route::get('/', 'HomeController@showWelcome');
	|
	*/

	public function getIndex()
	{
		$query = DB::table('agents')->get();

		return View::make('agent.index',array('data'=>$query));
		//return View::make('agent.index');
	}

	public function getEdit($id)
	{
		$data = DB::table('agents')->where('id', $id)->first();
		return View::make('agent.edit')->with('data', $data);
	}
	
	public function postEdit($id){
		return "$id";
	}

	public function getView($id)
	{
		$data = DB::table('agents')->where('id', $id)->first();
		return View::make('agent.view')->with('data', $data);
	}
	public function getOrder()
	{
		return View::make('agent.order');
	}
	public function getCompleted()
	{
		return View::make('agent.completed');
	}
	public function getBonus()
	{
		return View::make('agent.bonus');
	}
	public function getAgent()
	{
		return View::make('agent.agent');
	}
	public function getComplaint()
	{
		return View::make('agent.complaint');
	}
	public function getTrack()
	{
		return View::make('agent.track');
	}

	public function tracker($id){
		$dates = DB::table('trackers')->select(DB::raw('DISTINCT(DATE(created_at)) AS created_at'))->where('mobile_number', DB::table('agents')->find($id)->phone)->get();
		return View::Make('agent.showDates')->with("dates", $dates)->with('id', $id);
	}

	public function trackerOnDate($id, $date){
		$dates = DB::table('trackers')->select(DB::raw('DISTINCT(DATE(created_at)) AS created_at'))->where('mobile_number', DB::table('agents')->find($id)->phone)->get();
		$locations = DB::table('trackers')->where('mobile_number', DB::table('agents')->find($id)->phone)->where(DB::raw('Date(created_at)'), '=', $date)->get();
		return View::Make('agent.showDates')->with("dates", $dates)->with('id', $id)->with('locations', $locations)->with('on', $date);
	}

	public function location($id, $date){
		$locations = DB::table('trackers')->where('mobile_number', DB::table('agents')->find($id)->phone)->where(DB::raw('Date(created_at)'), '=', $date)->get(array('latitude', 'longitude'));
		$random = rand(0, count($locations));
		$center_lat = "";
		$center_lon = "";
		$is_central = true;
		$script = "var paths = [\n";
		foreach($locations as $location){
			if($is_central){
				$is_central = false;
				$center_lat = $location->latitude;
				$center_lon = $location->longitude;
			}
			$script .= "new google.maps.LatLng(".$location->latitude.", ".$location->longitude."),\n";
		}
		$script = substr($script, 0, strlen($script) - 2);
		$script .= "\n];";
		return View::make('agent.locations')->with('locations', $script)->with('c_lat', $center_lat)->with('c_lon', $center_lon);
	}
	
	// public function getAllitem()
	// {
	//     $items = DB::table('billing_item_groups')
	//     	->select('item_group_id AS id','name AS title','start','end','url')
	//     		->get();
 //        // return View::make('setting.enrollment.index',array('item'=>$items));
 //        return Response::json($items);
	// }

	// public function postEdit($id)
	// {
	//     $items = DB::table('billing_item_groups')
	//     	->select('item_group_id AS id','name AS title','start','end','url')
	//     		->get();
 //        // return View::make('setting.enrollment.index',array('item'=>$items));
 //        return Response::json($items);
	// }
}