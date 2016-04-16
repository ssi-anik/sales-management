@extends('master1')

@section('content')
  {{ HTML::style('Stylesheets/basic.css') }}

	
	<div>
		<ul class="breadcrumb">
		    <li class="active">All Products ( {{ count($data_list) }} ) </li>
		    <span class="options pull-right">
		    	<li title="add new">
		    	{{ HTML::decode(HTML::link('productcat/index','<i class="icon-plus"></i> All Product Category &nbsp; &nbsp;')) }}	
		    		{{ HTML::decode(HTML::link('productcat/create','<i class="icon-plus"></i> Add Product Category &nbsp;&nbsp;&nbsp;')) }}	
		    		<u>{{ HTML::decode(HTML::link('product/create','<i class="icon-plus"></i> Add Product')) }}	</u>
		    	</li>
		    </span>
	    </ul>
	</div>

    <table class="table table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>Title</th>
          <th>Description</th>
          <th>Category</th>
          <th>Upload Images</th>
          <th>Time</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        @foreach($data_list as $item)
        	<tr>
        		<td>{{ $item->id }}</td>
        		<td>{{ $item->name }}</td>
        		<td>{{ $item->description }}</td>
        		<td>{{ $item->sub_category_id}}</td>
        		<td style="width:150px;"><a id="{{ $item->id }}" href="#myModal2" class="upload_current_id"   data-toggle="modal" data-target="#myModal2"><i class="icon-upload"></i>
Add Images</a>
<!--<div id="myModal2" class="modal hide fade modal_fix" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:500px;">-->
<div class="modal fade modal_fix" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="">
  <div class="modal-dialog">
  <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">Upload Documents</h3>
      </div>
    
      <div class="modal-body" style="text-align:center;">
        
        {{ Form::open(array('url'=>'product/upload','files'=>true,'method'=>'post','class'=>'dropzone','id'=>'dropzone')) }}
          
          <input type="hidden" name="ab_id" id="ab_id" value="{{ $item->id }}"/>
        {{ Form::close() }}

        <div id="uploaded_files" style="margin-bottom:15px;">
       
        </div>
      </div>
</div>
</div>
  </div></td>
        		<td>{{ $item->created_at }}</td>
        		<td>
        			{{ HTML::decode(HTML::link('product/edit/'.$item->id,'<i class="icon-pencil"></i>')) }}
        			&nbsp;
        			{{ HTML::decode(HTML::link('product/delete/'.$item->id,'<i class="icon-trash"></i>')) }}
        		</td>
        	</tr>
        @endforeach
      </tbody>
    </table>


@stop

@section('script')
{{ HTML::script('Scripts/dropzone.min.js') }}

  <script type="text/javascript">
Dropzone.autoDiscover = false;
     $('#dropzone').dropzone();

      var cbrDropzone = Dropzone.forElement("#dropzone");

      cbrDropzone.on("success", function(file, response) {
        // alert("done");

        var f_adas = $("#ab_id").val();

        $.getJSON('{{ Url("product/upload/'+f_adas +'") }}',function(data){
          $("#uploaded_files").empty();

          $.each(data,function(key,value){

            $("#uploaded_files").append('<div id="'+ value.id +'" class="well well-small">'+
              '<span class="pull-left">'+
                '<a href="' + value.image_link + '">'+value.image_link +'</a>'+
              '</span>'+
              '<span class="pull-right">'+
                '<i id="'+ value.id +'" class="icon-trash uploaded_item_del"></i>'+
              '</span>'+
            '</div>');

          });

        });

      });

      $(".current_id").click(function(){
      $("#current_id").val($(this).attr('id'));
    });

    $(".upload_current_id").click(function(){
      $("#ab_id").val($(this).attr('id'));

      $.getJSON('{{ Url("product/upload/'+$(this).attr('id') +'") }}',function(data){

        $("#uploaded_files").empty();

        $.each(data,function(key,value){

          $("#uploaded_files").append('<div id="'+ value.id +'" class="well well-small">'+
            '<span class="pull-left">'+
              '<a href=' + value.image_link + ' target="_blank">'+value.image_link +'</a>'+
            '</span>'+
            '<span class="pull-right">'+
              '<i id="'+ value.id +'" class="icon-trash uploaded_item_del"></i>'+
            '</span>'+
          '</div>');

        });

      });

    });

    $(document).on('click','.uploaded_item_del',function(){
      var id = $(this).attr('id');
      $.getJSON('{{ Url("product/delfile/'+ id +'") }}',function(data){
        if(data == "deleted"){
          $("#uploaded_files>#"+id).hide();
        }
        else{
          alert(data);
          $("#uploaded_files>#"+id).addClass('alert-warning');
        }
      })
    })

    $('#myModal2').on('hidden', function() {
      $("#uploaded_files").empty();
      $("#dropzone").empty();
      $(".dropzone>div").hide();
      $(".dz-default.dz-message").show();
    });
</script>
	<script type="text/javascript">
		$("#menu_testimonial").addClass('active');
	</script>
@stop