@extends('master')
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('public/js/bootstrap.min.js')}}

@section('content')

@include('scripts')
	
	<ul class="breadcrumb">
    	<li class="active">Edit Complaint</li>
    	<span class="divider">/</span>
    	<li><b>Edit::{{ $_item->id }}</b></li>
  	</ul>

	<div class="container">
	{{ Form::open(array('url'=>'complaint/edit','files'=>true,'method'=>'post')) }}

		<input type="hidden" class="form-control" name="id" value="{{ $_item->id }}"/>

		<input type="text" name="title" class="form-control" placeholder="Mobile Number" value="{{ $_item->mobile_number }}"><br/>

		<img class="img-responsive" src="http://mockup.in/sales-management/test/images/complaints/{{ $_item->image_path }}" style="width:120px;"><br/>

		<input type="radio" name="is_solved" value="1">Completed &nbsp;
		<input type="radio" name="is_solved" value="0">Pending
		
	    <div style="text-align:center;">
	      <button type="submit" class="btn btn-success">Update</button>
	    </div>
	{{ Form::close() }}

	  @foreach($errors->all('<div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">&times;</button>:message</div>') as $message)
	  	{{ $message }}
	  @endforeach
	  </div>

@stop

@section('scripts')
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$("#menu_newz").addClass('active');
		$("#pick_date").datepicker();
	</script>
@stop