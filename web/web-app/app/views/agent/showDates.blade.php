@extends('master')
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
{{ HTML::script('/js/bootstrap.min.js')}}
@section('content')
	@include('scripts')
	<style type="text/css">
		h3{
			margin-left: 25px;
		}
		.col-sm-5 input{
			width: 80%;
		}
		option {
			padding-left: 15px;
		}
		td.details-control {
			background: url('{{ URL::asset('images/details_open.png') }}') no-repeat center center;
			cursor: pointer;
		}
		tr.shown td.details-control {
			background: url('{{ URL::asset('images/details_close.png') }}') no-repeat center center;
		}
		.center{
			text-align: center !important;
		}
	</style>
</div>
</div>
</div>
	<div class="container-fluid">
		<div class="row">
			<h3>Details of Agent</h3>
			<div class="col-xs-6 col-md-3">
				<ul class="nav nav-pills nav-stacked sidebarcolor">
					<li>{{ HTML::decode(HTML::link('agent/view/'.$id,' Home (name) ')) }}</li>
					<li class="">{{ HTML::decode(HTML::link('agent/order',' New Order ')) }}</li>
					<li>{{ HTML::decode(HTML::link('agent/completed',' Completed Order ')) }}</li>
					<li>{{ HTML::decode(HTML::link('agent/bonus',' Bonus Points ')) }}</li>
					<li>{{ HTML::decode(HTML::link('agent/agent',' Agent Details ')) }}</li>
					<li>{{ HTML::decode(HTML::link('agent/complaint',' Complaint Details ')) }}</li>
					<li>{{ HTML::decode(HTML::link('agent/edit',' Edit Account ')) }}</li>
					<li class="active">{{ HTML::decode(HTML::link('agent/track/'. $id,' Track Agent ')) }}</li>
				</ul>
			</div>
			<div class="col-xs-9 col-md-9">
				<ul>
					@foreach($dates as $date)
						<li>{{HTML::decode(HTML::link('agent/track/'.$id."/".$date->created_at, $date->created_at))}}</li>
					@endforeach
				</ul>
				@if(!empty($locations))
					<iframe src="{{"http://".$_SERVER['SERVER_NAME']."/sales/public/agent/location/$id/$on"}}" width="100%" height="100%"></iframe>
				@endif
			</div>
		</div>
	</div>
</section>
</div>
</div>
</div>
</div>   
</div>
<script type="text/javascript" language="javascript" src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
@stop