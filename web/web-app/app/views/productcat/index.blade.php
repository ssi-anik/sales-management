@extends('master')

@section('content')
	
	<div>
		<ul class="breadcrumb">
		    <li class="active">Product Categories( {{ count($data_list) }} ) </li>
		    <span class="options pull-right">
		    	<li title="add new">
		    		{{ HTML::decode(HTML::link('productcat/create','<i class="icon-plus"></i> Add New Category')) }}	
		    	</li>
		    </span>
	    </ul>
	</div>
  <div class="col-xs-6 col-md-3">
    <ul class="nav nav-pills nav-stacked sidebarcolor">
      <li class="">{{ HTML::decode(HTML::link('/product',' All Products')) }}</li>
      <li>{{ HTML::decode(HTML::link('product/create',' Add Product')) }}</li>
            <li>{{ HTML::decode(HTML::link('productcat/index',' All Product Category')) }}</li>
      <li>{{ HTML::decode(HTML::link('productcat/create',' Add Product Category')) }}</li>

    </ul>
  </div>
  <div class="col-xs-9 col-md-9">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>Title</th><!--
          <th>Description</th>-->
          <th>Date</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        @foreach($data_list as $item)
        	<tr>
        		<td>{{ $item->id }}</td>
        		<td>{{ $item->name }}</td>
        		<!--<td>{{-- $item->ab_desc --}}</td>-->
        		<td>{{ $item->created_at }}</td>
        		<td>
        			{{ HTML::decode(HTML::link('productcat/edit/'.$item->id,'<i class="icon-pencil"></i> EDIT')) }}
        			&nbsp;
        			{{ HTML::decode(HTML::link('productcat/delete/'.$item->id,'<i class="icon-trash"></i> DELETE')) }}
        		</td>
        	</tr>
        @endforeach
      </tbody>
    </table>
    </div>

@stop

@section('scripts')
	<script type="text/javascript">
		$("#menu_articlez").addClass('active');
	</script>
@stop