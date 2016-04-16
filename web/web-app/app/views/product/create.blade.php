@extends('master')

@section('content')
	</div></div></div>
<div class="container-fluid">
<div class="row">
  <div class="col-xs-12 col-md-12">
	<ul class="breadcrumb">
    	<li class="active">Testimonials</li>
    	<span class="divider">/</span>
    	<li><b>Create</b></li>
  	</ul>
  <div class="col-xs-6 col-md-3">
    <ul class="nav nav-pills nav-stacked sidebarcolor">
      <li class="">{{ HTML::decode(HTML::link('/product',' All Products')) }}</li>
      <li>{{ HTML::decode(HTML::link('product/create',' Add Product')) }}</li>
            <li>{{ HTML::decode(HTML::link('productcat/index',' All Product Category')) }}</li>
      <li>{{ HTML::decode(HTML::link('productcat/create',' Add Product Category')) }}</li>

    </ul>
  </div>
  <div class="col-xs-9 col-md-9">
	{{ Form::open(array('url'=>'product/create','method'=>'post','class'=>'table')) }}

		<input type="text" name="title" class="form-control" placeholder="Title Here">
<br/>
		<textarea placeholder="Description Here" class="form-control" name="description" rows="10"></textarea>
<br/>
		<!--<input type="text"  placeholder="Video URL Here">-->
		 	<select name="video_link" class="form-control">
 			 @foreach($data_list as $data)
                          <option value="{{$data->ab_title}}">{{$data->ab_title}}</option>
                         @endforeach
	       		</select> 

		<br/>
	    <div style="text-align:center;">
	      <button type="submit" class="btn btn-success">Create</button>
	    </div>
	{{ Form::close() }}

	  @foreach($errors->all('<div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">&times;</button>:message</div>') as $message)
	  	{{ $message }}
	  @endforeach
</div></div>
</div>
@stop

@section('scripts')
	<script type="text/javascript">
		$("#menu_testimonial").addClass('active');
	</script>
@stop