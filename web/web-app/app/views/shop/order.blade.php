@extends('master')

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('public/js/bootstrap.min.js')}}


@section('content')

@include('scripts')



<style type="text/css">
h3{margin-left: 25px;}
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
        <h3>Current Order<code class="pull-right text-muted">Company Name</code></h3>
  <div class="col-xs-6 col-md-3">
    <ul class="nav nav-pills nav-stacked sidebarcolor">
      <li class="">{{ HTML::decode(HTML::link('shop/view',' Home (name) ')) }}</li>
      <li class="active">{{ HTML::decode(HTML::link('shop/order',' New Order ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/completed',' Completed Order ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/bonus',' Bonus Points ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/agent',' Agent Details ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/complaint',' Complaint Details ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/edit',' Edit Account ')) }}</li>

    </ul>
  </div>
  <div class="col-xs-9 col-md-9">
    
<table class="table table-hover">
    <thead>
      <tr>
        <th>Order Id</th>
        <th>Items</th>
        <th>Cost</th>
        <th>Delivery On</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
              <tr>
          <td>1</td>
          <td>Item1(2),Item3(10),Item5(12)</td>
          <td>Rs 50,000</td>
          <td>4 june 2015</td>
          <td>
              {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>
              <tr>
          <td>2</td>
          <td>Item1(23),Item3(10),Item5(1)</td>
          <td>Rs 10,000</td>
          <td>4 june 2015</td>
          <td>
              {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-list'></i>")) }}           &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>

          </tbody>
  </table>

          </div>
        </div>
    </div>
</section>
</div>
     <!-- </div> -->
  </div>


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