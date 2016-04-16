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
      <h3>Add New Order</h3><br/>
 <form class="form-horizontal">
                <div class="form-group">
                  <div class="col-sm-6">
                     <label style="text-align:right;" class="col-sm-6 control-label pull-right" for="inputPassword3">Select Company ( AutoSelect ):</label>
                  </div>
                  <div class="col-sm-6">
                    <input type="input" placeholder="Company Name" style="width:50% !important;" class="form-control col-sm-6 pull-left">
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-6">
                     <label style="text-align:right;" class="col-sm-6 control-label pull-right" for="inputPassword3">Order Number:</label>
                  </div>
                  <div class="col-sm-6">
                    <input type="input" disabled="" placeholder="2014-121-1222" style="width:50% !important;" class="form-control col-sm-6 pull-left">
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-6">
                     <label style="text-align:right;" class="col-sm-6 control-label pull-right" for="inputPassword3">Delivery Date:</label>
                  </div>
                  <div class="col-sm-3 input-group">
                    <input type="input" placeholder="12 jan 2015" style="margin-left:14px;width:94% !important;" class="form-control" id="datepicker">
                    <span style="" class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                  </div>
                </div><br/><br/>
                <div style="border:1px solid grey" class="form-group">
                  <div class="col-sm-3">
                     <label class="control-label" for="inputPassword3">Product</label>
                  </div>
                  <div class="col-sm-3">
                    <label class="control-label" for="inputPassword3">Product Sub Category</label>
                  </div>
                  <div class="col-sm-3">
                    <label class="control-label" for="inputPassword3">Units</label>
                  </div>
                  <div class="col-sm-3">
                    <label class="control-label" for="inputPassword3">Total</label>
                  </div>
                </div>
                <!-- add new record for payment -->
                <div style="" class="form-group">
                  <div class="col-sm-3">
                    <select style="" class="form-control">
                      <option>product1</option>
                      <option>product2</option>
                    </select>
                  </div>
                  <div class="col-sm-3">
                    <select style="" class="form-control">
                      <option>product sub</option>
                      <option>product sub2</option>
                    </select>
                  </div>
                  <div class="col-sm-3">
                    <!-- <label class="control-label" for="inputPassword3">$2000</label> -->
                    <input type="text" class="form-control"/>
                  </div>
                  <div class="col-sm-3">
                    <label class="control-label" for="inputPassword3">$300</label>
                    <i class="glyphicon glyphicon-trash pull-right"></i> 
                  </div>
                </div>
                <div style="" class="form-group">
                  <div class="col-sm-3">
                    <select style="" class="form-control">
                      <option>product1</option>
                      <option>product1</option>
                    </select><br>
                    <button style="margin-left:6px;" class="btn btn-xs pull-left"><i class="glyphicon glyphicon-plus"></i> New</button>
                  </div>
                  <div class="col-sm-3">
                    <select style="" class="form-control">
                      <option>product1 sub</option>
                      <option>product2 sub</option>
                    </select>
                  </div>
                  <div class="col-sm-3">
                    <input type="text" class="form-control"/>
                  </div>
                  <div class="col-sm-3">
                    <label class="control-label" for="inputPassword3">$300</label>
                    <i class="glyphicon glyphicon-trash pull-right"></i> 
                  </div>
                </div>

                <!-- end of new record -->

                <div style="" class="form-group">
                  <div class="col-sm-6">
                  </div>
                  <div class="col-sm-2">
                    <label style="text-align:right;" class="control-label" for="inputPassword3">Sub Total</label>
                  </div>
                  <div class="col-sm-4">
                    <label class="control-label" for="inputPassword3">$2300</label>
                  </div>
                </div>
                <div style="" class="form-group">
                  <div class="col-sm-6">
                  </div>
                  <div class="col-sm-2">
                    <label style="text-align:right;" class="control-label" for="inputPassword3">Discount</label>
                  </div>
                  <div class="col-sm-4">
                    <input type="input" placeholder="0" style="width:50% !important;" class="form-control">
                  </div>
                </div>
                <div style="" class="form-group">
                  <div class="col-sm-6">
                  </div>
                  <div class="col-sm-2">
                    <label style="text-align:right;" class="control-label" for="inputPassword3">Tax:</label>
                  </div>
                  <div class="col-sm-4">
                    <input type="input" placeholder="0" style="width:50% !important;" class="form-control">
                  </div>
                </div>
                <div style="" class="form-group">
                  <div class="col-sm-6">
                  </div>
                  <div class="col-sm-2">
                    <label style="text-align:right;" class="control-label" for="inputPassword3">Tax 2:</label>
                  </div>
                  <div class="col-sm-4">
                    <input type="input" placeholder="0" style="width:50% !important;" class="form-control">
                  </div>
                </div>
                <div style="" class="form-group">
                  <div class="col-sm-6">
                  </div>
                  <div class="col-sm-2">
                    <label style="text-align:right;border-top:1px solid;border-bottom:1px solid;" class="control-label" for="inputPassword3">Total :</label>
                  </div>
                  <div class="col-sm-4">
                    <label style="text-align:right;border-top:1px solid;border-bottom:1px solid;" class="control-label" for="inputPassword3">$2300</label>
                  </div>
                </div>

              </form>

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