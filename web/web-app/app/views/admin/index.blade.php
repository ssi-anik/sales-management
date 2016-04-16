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
      <h3>All Sub-Admin
                      {{ HTML::decode(HTML::link("admin/add","<i class='glyphicon glyphicon-plus'></i> Add Admin",array('class'=>'btn btn-default pull-right'))) }}
</h3><br/>
        <table class="table table-hover">
    <thead>
      <tr>
        <th>Sr.</th>
        <th>Name</th>
        <th>Username</th>
        <th>Access Level</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
              <tr>
          <td>1</td>
          <td>Admin</td>
          <td>admin</td>
          <td>Full Access</td>
          <td>
                        &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("admin/edit","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

        </tr>
              <tr>
          <td>2</td>
          <td>kamal deep</td>
          <td>deepak</td>
          <td>Full Access</td>
          <td>
                        &nbsp;&nbsp;
            {{ HTML::decode(HTML::link("admin/edit","<i class='glyphicon glyphicon-edit'></i>")) }}&nbsp;&nbsp;{{ HTML::decode(HTML::link("#","<i class='glyphicon glyphicon-trash'></i>")) }}          </td>

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