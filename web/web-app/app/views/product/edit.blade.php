@extends('master')

@section('content')
	
	</div></div></div>
<div class="container-fluid">
<div class="row">
  <div class="col-xs-12 col-md-12">
	<ul class="breadcrumb">
    	<li class="active">Edit Product</li>
    	<span class="divider">/</span>
    	<li><b>Edit::{{ $_item->id }}</b></li>
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
	{{ Form::open(array('url'=>'product/edit','method'=>'post')) }}

		<input type="hidden" name="id" value="{{ $_item->id }}"/>

		<input type="text" name="title" class="form-control" placeholder="Title Here" value="{{ $_item->name }}">
<br/>
		<textarea placeholder="Description Here" class="form-control" name="description" rows="10">{{ $_item->description }}</textarea>
<br/>
			<select name="video_link" class="form-control">
			<option>{{ $_item->sub_category_id }}</option>
 			 @foreach($data_list as $data)
                          <option value="{{$data->id}}">{{$data->name}}</option>
                         @endforeach
	       		</select> 

		
	    <div style="text-align:center;">
	    <br/>
	      <button type="submit" class="btn btn-success">Update</button>
	    </div>
	{{ Form::close() }}

	  @foreach($errors->all('<div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">&times;</button>:message</div>') as $message)
	  	{{ $message }}
	  @endforeach
</div></div></div>
@stop

@section('scripts')
	<script type="text/javascript">
		$("#menu_testimonial").addClass('active');
	</script>
@stop