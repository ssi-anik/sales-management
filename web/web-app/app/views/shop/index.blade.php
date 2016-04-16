@extends('master')

@section('content')



<style type="text/css">
h3 {margin-left: 25px;}  .pager li a{width: 150px !important;font-size: 16.5px;}

</style>


</div></div></div>
   <!-- <ol class="breadcrumb" style="width:100% !important;">
    <div class="container-fluid">
                <h3>Shops > All</h3>
      <div>
   </ol> -->
<div class="container-fluid">
<div class="row">
  <h3>All Shops</h3>
  <!-- <div class="col-xs-6 col-md-3">
    <ul class="nav nav-pills nav-stacked sidebarcolor">
      <li class="active">{{ HTML::decode(HTML::link('setting/index',' Variable ')) }}</li>
      <li>{{ HTML::decode(HTML::link('setting/item',' Item Groups ')) }}</li>
      <li>{{ HTML::decode(HTML::link('setting/model',' Model Types ')) }}</li>
      <li>{{ HTML::decode(HTML::link('setting/taxes',' Taxes ')) }}</li>

    </ul>
  </div> -->

  <div class="col-xs-12 col-md-12">
    <span class="seperator"></span>
    <table class="table table-hover">
    <thead>
      <tr>
        <th>Shop Id</th>
        <th>Activation Link</th>
        <th>Owner Name</th>
        <th>Company Name</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Location</th>
        <th>Created On</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
    @foreach($data as $item)
        	<tr>
        		<td>{{ $item->id }}</td>
        		<td>{{ $item->activate }}</td>
        		<td>{{ $item->own_name }}</td>
        		<td>{{ $item->company_name}}</td>
        		<td>{{ $item->phone}}</td>
        		<td>{{ $item->email}}</td>
        		<td>{{ $item->location}}</td>
        		<td>{{ $item->created_at}}</td>
        		<td>
              {{ HTML::decode(HTML::link("shop/view","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("shop/edit","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>
        	</tr>
   @endforeach
              <!--<tr>
          <td>1</td>
          <td>Abc</td>
          <td>Goyal ( <span style="color:red;">new</span> )</td>
          <td>988889898</td>
          <td>abc@gmail.com</td>
          <td>location</td>
          <td>4 june 2015</td>
          <td>
              {{ HTML::decode(HTML::link("shop/view","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("shop/edit","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>
              <tr>
          <td>2</td>
          <td>abc2</td>
          <td>Company</td>
          <td>989898989</td>
          <td>abc@gmail.com</td>
          <td>location</td>
          <td>4 june 2015</td>
          <td>
              {{ HTML::decode(HTML::link("shop/view","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("shop/edit","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>-->

          </tbody>
  </table>


  </div>


</div>   


{{ HTML::script('dt_js/jquery.js')}}
{{ HTML::script('js/bootstrap.js')}}


<!-- popup to send emails -->
@stop