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
        <h3>Details of shop</h3>
  <div class="col-xs-6 col-md-3">
    <ul class="nav nav-pills nav-stacked sidebarcolor">
      <li class="active">{{ HTML::decode(HTML::link('shop/view',' Home (name) ')) }}</li>
      <li class="">{{ HTML::decode(HTML::link('shop/order',' New Order ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/completed',' Completed Order ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/bonus',' Bonus Points ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/agent',' Agent Details ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/complaint',' Complaint Details ')) }}</li>
      <li>{{ HTML::decode(HTML::link('shop/edit',' Edit Account ')) }}</li>

    </ul>
  </div>
  <div class="col-xs-9 col-md-9">
  <form class="form-horizontal" accept-charset="UTF-8" action="" method="POST">
      <div class="col-sm-12">
          <div class="col-sm-12">
            <label class="col-sm-4 control-label" for="inputEmail3">Name of Shop :</label>
            <div class="col-sm-8">
              <input type="text" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Name of Owner (phone) :</label>
            <div class="col-sm-4">
              <input type="text" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
            <div class="col-sm-4">
              <input type="text" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Email :</label>
            <div class="col-sm-8">
              <input type="email" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Mobile Number (APP) :</label>
            <div class="col-sm-8">
              <input type="email" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Phone Number's :</label>
            <div class="col-sm-8">
              <input type="email" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Company Address :</label>
            <div class="col-sm-8">
              <textarea row="3" col="3" class="form-control"></textarea>
              <!-- <input type="email" value="" name="client_name" id="inputEmail3" class="form-control"> -->
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Location :</label>
            <div class="col-sm-8">
              <input type="email" value="" name="client_name" id="inputEmail3" class="form-control" disabled>
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Category of Company :</label>
            <div class="col-sm-8">
              <input type="email" value="" name="client_name" id="inputEmail3" class="form-control">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Company Picture :</label>
            <div class="col-sm-8">
              <input type="file" value="" name="client_name" id="inputEmail3" class="">
            </div>
          </div>

          <div class="col-sm-12"><br>
            <label class="col-sm-4 control-label" for="inputEmail3">Owner Picture :</label>
            <div class="col-sm-8">
              <input type="file" value="" name="client_name" id="inputEmail3" class="">
            </div>
          </div>

      </div>
      <!-- <div class="col-sm-4">
            <div class="pull-right"><img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQzLjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" class="img-rounded" /></div>
      </div> -->

          

      <br/>
    <div class="col-sm-offset-4 col-sm-10"><br/>
<button id="update-details" class="btn btn-default btn-primary" type="submit">Update Details</button>
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