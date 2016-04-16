@extends('master')

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('public/js/bootstrap.min.js')}}


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
      <h3>Add New Admin</h3><br/>
  <form class="form-horizontal" accept-charset="UTF-8" action="" method="POST">
          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Name :</label>
            <div class="col-sm-6">
              <input type="text" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">UserName :</label>
            <div class="col-sm-6">
              <input type="text" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Email :</label>
            <div class="col-sm-6">
              <input type="email" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Password :</label>
            <div class="col-sm-6">
              <input type="password" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Shops :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Agents :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Orders :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Create  &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Complaint :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Create  &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">News :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Create  &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">offers :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Create  &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Products :</label>
            <div class="col-sm-6">
              <input type="checkbox"/>Read &nbsp;&nbsp;<input type="checkbox"/>Create  &nbsp;&nbsp;<input type="checkbox"/>Edit  &nbsp;&nbsp;<input type="checkbox"/>Delete 
            </div>
          </div>

      <br/>
    <div class="col-sm-offset-4 col-sm-10"><br/>
<button id="update-details" class="btn btn-default btn-primary" type="submit">Add Admin</button>
</div>

  </form>            </div>
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