@extends('master')
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('public/js/bootstrap.min.js')}}

@section('content')

@include('scripts')
	
	<ul class="breadcrumb">
    	<li class="active">Complaint</li>
    	<span class="divider">/</span>
    	<li><b>Create</b></li>
  	</ul>

	{{ Form::open(array('url'=>'complaint/create','files'=>true,'method'=>'post')) }}

		<input type="text" name="title" class="form-control" placeholder="Mobile Number"><br/>

		

		<input name="image" type="file"/>
		<br><br>
		
		<input type="radio" name="is_solved" value="1">Completed &nbsp;
		<input type="radio" name="is_solved" value="0">Pending

		
	    <div style="text-align:center;">
	      <button type="submit" class="btn btn-success">Create</button>
	    </div>
	{{ Form::close() }}

	  @foreach($errors->all('<div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">&times;</button>:message</div>') as $message)
	  	{{ $message }}
	  @endforeach

@stop

@section('scripts')
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$("#menu_newz").addClass('active');
		$("#pick_date").datepicker();
	</script>
@stop