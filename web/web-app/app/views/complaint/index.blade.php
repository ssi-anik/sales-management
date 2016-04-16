@extends('master')
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('js/bootstrap.min.js')}}

@section('content')

@include('scripts')


	
	<div>
		<ul class="breadcrumb">
		    <li class="active">Complaints ( {{ count($data_list) }} ) </li>
		    <span class="options pull-right">
		    	<li title="add new">
		    		{{ HTML::decode(HTML::link('complaint/create','<i class="glyphicon glyphicon-plus"></i> Add New')) }}	
		    	</li>
		    </span>
	    </ul>
	</div>

    <table class="table table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>Mobile Number ( Registered )</th>
          <th>Image</th>
          <th>Solved</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        @foreach($data_list as $item)
        	<tr>
        		<td>{{ $item->id }}</td>
        		<td>{{ $item->mobile_number}}</td>
        		<td><img src="http://android-ssianik.c9.io/images/complaints/{{ $item->image_path }}" style="width:60px;"></td>
        		<td>{{ $item->is_solved}}</td>
        		<td>
        			{{ HTML::decode(HTML::link('complaint/edit/'.$item->id,'<i class="glyphicon glyphicon-edit"></i>')) }}
        			&nbsp;
        			{{ HTML::decode(HTML::link('complaint/delete/'.$item->id,'<i class="glyphicon glyphicon-trash"></i>')) }}
        		</td>
        	</tr>
        @endforeach
      </tbody>
    </table>

@stop

@section('scripts')
	<script type="text/javascript">
		$("#menu_newz").addClass('active');
	</script>
@stop