@extends('master')

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('js/bootstrap.min.js')}}


@section('content')

@include('scripts')



<style type="text/css">
.col-sm-5 input{width: 80%;}
option {padding-left: 15px;}
td.details-control {
    background: url('{{ URL::asset('images/details_open.png') }}') no-repeat center center;
    cursor: pointer;
}
tr.shown td.details-control {
    background: url('{{ URL::asset('images/details_close.png') }}') no-repeat center center;
}
</style>




</div></div></div>
<div class="container-fluid">
<div class="row">
  <div class="col-xs-12 col-md-12">
      <h3>Latest Order's <code>Today's</code></h3><br/>
    <table class="table table-hover">
    <thead>
      <tr>
        <th>Order Id</th>
        <th>Customer Name</th>
        <th>Company Name</th>
        <th>Items</th>
        <th>Cost</th>
        <th>Delivery On</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
              <tr>
          <td>1</td>
          <td>Abc</td>
          <td>Goyal</td>
          <td>Item1(2),Item3(10),Item5(12)</td>
          <td>Rs 50,000</td>
          <td>4 june 2015</td>
          <td>
              {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>
              <tr>
          <td>2</td>
          <td>abc2</td>
          <td>Company</td>
          <td>Item1(23),Item3(10),Item5(1)</td>
          <td>Rs 10,000</td>
          <td>4 june 2015</td>
          <td>
              {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>

          </tbody>
  </table>

     <!-- </div> -->
  </div>

  

</div>   



</div>   
<script type="text/javascript">
// $('#confirmDelete').on('show.bs.modal', function (e) {
//         $message = $(e.relatedTarget).attr('data-message');
//         $(this).find('.modal-body p').text($message);
//         $title = $(e.relatedTarget).attr('data-title');
//         $(this).find('.modal-title').text($title);
   
//         // Pass form reference to modal for submission on yes/ok
//         var form = $(e.relatedTarget).closest('form');
//         $(this).find('.modal-footer #confirm').data('form', form);
//     });
   
//     <!-- Form confirm (yes/ok) handler, submits form -->
//     $('#confirmDelete').find('.modal-footer #confirm').on('click', function(){
//         $(this).data('form').submit();
//     }); 
</script>
  <script type="text/javascript" language="javascript" src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
@stop