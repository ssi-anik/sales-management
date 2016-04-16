@extends('master')

@section('content')

@include('scripts_pa')


{{ HTML::style('css/bootstrap-datetimepicker.min.css',array('media'=>'all')) }}

<style type="text/css">
label{text-align: left !important;}
.col-sm-5 input{width: 80%;}
/*select {width: 80% !important;}
*/option {padding-left: 15px;}

  dt { margin-top: 1em; }
  dt:first-child { margin-top: 0; }
  dd { width: 200px;float: left;}
h3{margin-left: 28px;}
  *[data-editor-field] {
    border: 1px dashed #ccc;
    padding: 0.5em;
    margin: 0.5em;
  }
  .align h5 {text-align: center !important;}
  .align #inlineCheckbox1{text-align: center !important;}
  *[data-editor-field]:hover {
    background: #f3f3f3;
    border: 1px dashed #333;
  }

</style>


</div></div></div>
   <ol class="breadcrumb" style="width:100% !important;">
    <div class="container-fluid">
                <h3>Confirm Customer Details</h3>
      <div>
   </ol>
<div class="container-fluid">


  <div class="col-xs-12 col-md-12">
          <!-- <h3>Account Number</h3><br/> -->
    <span class="seperator"></span>
    <div class="dt-example">
      
          
        <form class="form-horizontal" action="edit" method="post">
<br/><br/>
          <div class="col-sm-12">

            <label for="inputEmail3" class="col-sm-2 control-label" style="font-size:17px;"><b>Account Number</b></label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="account_number" disabled value="{{$account_number}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="account_number" value="{{$account_number}}">
            <br/></div>
            <div class="col-sm-6"><button type="submit" class="btn btn-default btn-primary pull-right">Edit Details</button></div>
          </div>


          <div class="col-sm-12">
            <div style="width:auto;height:auto;border-top: 1px solid #cccccc;"></div><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Start Date :</label>
            <div class="col-sm-4">
              <div class="input-group date" id="datetimepicker8">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">
                        </span>
                    </span>
                <input type="text" class="form-control" name="start_date" disabled value="{{$start_date}}">
                <input type="hidden" class="form-control" name="start_date"  value="{{$start_date}}">
              </div>
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Promo Area :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputPassword3" name="promo_code" disabled value="{{$promo_code}}"><br/>
              <input type="hidden" class="form-control" id="inputPassword3" name="promo_code"  value="{{$promo_code}}"><br/>
            </div>
          </div>

          <h3 class="text-muted">Client Information</h3>
<div style="width:auto;height:auto;border-top: 1px solid #cccccc;"></div>
          <div class="col-sm-12"><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Client Name :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="client_name" disabled value="{{$client_name}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="client_name" value="{{$client_name}}">
            </div>
          </div>

        <div class="col-sm-12">
          <div class="col-sm-6">
            <label for="inputEmail3" class="col-sm-6 control-label" style="padding-left:0px;">Service Address :</label>
            <textarea class="form-control" name="service_address" disabled rows="6">{{$service_address}}</textarea>
            <input type="hidden" class="form-control" id="inputEmail3" name="service_address" value="{{$service_address}}">
          </div>
          <div class="col-sm-6">
            <label for="inputPassword3" class="col-sm-6 control-label" style="padding-left:0px;">Billing Address :</label>
            <input type="hidden" class="form-control" id="inputEmail3" name="billing_address" value="{{$billing_address}}">
            <textarea class="form-control" rows="3" name="billing_address" disabled >{{$billing_address}}</textarea><br/>
            <label for="inputPassword3" class="col-sm-4 control-label" style="padding-left:0px;">Phone Number :</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" id="inputPassword3" name="phone1" disabled value="{{$phone1}}">
              <input type="hidden" class="form-control" id="inputPassword3" name="phone1" value="{{$phone1}}">
            </div>
          </div>
         </div>

          <div class="col-sm-12"><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Email :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="email" disabled value="{{$email}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="email"  value="{{$email}}">
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Phone Number :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputPassword3" name="Phone2" disabled value="{{$Phone2}}"><br/>
              <input type="hidden" class="form-control" id="inputPassword3" name="Phone2" value="{{$Phone2}}">
            </div>
          </div>

          <h3 class="text-muted">Treatment Plan</h3>
<div style="width:auto;height:auto;border-top: 1px solid #cccccc;"></div>
          <div class="col-sm-12">
            <div class="col-sm-9 align" style="background-color:#F5F5F5;">
              <h5><b>Exterior Service:</b></h5>
              <h5>60 Day Warrantee</h5>
              <h5>Foundation, Eves, Yard, Porch</h5>
            </div>
            <div class="col-sm-3 align" style="background-color:#F5F5F5;">
              <h5><b>Interior Service:</b></h5>
              <h5>1 Year Warrantee</h5>
              <h5>Crack & Crevice, Wall Void</h5>
            </div>
          </div>

          <div class="col-sm-12">
            <div class="col-sm-3 align" style="background-color:#C9FFD4;">
              <h5><b>Spring</b></h5>
              <div class="col-sm-4">
                <h5>Mar</h5>
              </div>
              <div class="col-sm-4">
                <h5>Apr</h5>
              </div>
              <div class="col-sm-4">
                <h5>May</h5>
              </div>
                  <span><input type="checkbox" id="mar" disabled  class="group1 col-sm-4" value="mar" name="mar" <?php if (!empty($mar)) {echo 'checked';} ?>>
                  </span><span><input type="hidden" id="mar" class="group1 col-sm-4"  name="mar" value="<?php if (!empty($mar)) {echo 'checked';} ?>">
                  </span><span><input type="checkbox" id="apr"  class="group1 col-sm-4" disabled value="apr" name="apr" <?php if (!empty($apr)) {echo 'checked';} ?>>
                  </span><span><input type="hidden" id="apr"  class="group1 col-sm-4" name="apr" value="<?php if (!empty($apr)) {echo 'checked';} ?>">
                  </span><span><input type="checkbox" id="may" class="group1 col-sm-4" disabled value="may" name="may" <?php if (!empty($may)) {echo 'checked';} ?>>
                  </span><input type="hidden" id="may" class="group1 col-sm-4"  name="may" value="<?php if (!empty($may)) {echo 'checked';} ?>">

            </div>
            <div class="col-sm-3 align" style="background-color:#FFFAA7;">
              <h5><b>Summer:</b></h5>
              <div class="col-sm-4">
                <h5>Jun</h5>
              </div>
              <div class="col-sm-4">
                <h5>Jul</h5>
              </div>
              <div class="col-sm-4">
                <h5>Aug</h5>
              </div>
                                <input type="checkbox" id="jun"   class="group2 col-sm-4" disabled value="jun" name="jun" <?php if (!empty($jun)) {echo 'checked';} ?>>
                  <input type="hidden" id="jun"   class="group2 col-sm-4" name="jun" value="<?php if (!empty($jun)) {echo 'checked';} ?>">
                  <input type="checkbox" id="jul"   class="group2 col-sm-4" disabled value="jul" name="jul" <?php if (!empty($jul)) {echo 'checked';} ?>>
                  <input type="hidden" id="jul"   class="group2 col-sm-4"  name="jul" value="<?php if (!empty($jul)) {echo 'checked';} ?>">
                  <input type="checkbox" id="aug"   class="group2 col-sm-4" disabled value="aug" name="aug" <?php if (!empty($aug)) {echo 'checked';} ?>>
                  <input type="hidden" id="aug"   class="group2 col-sm-4" name="aug" value="<?php if (!empty($aug)) {echo 'checked';} ?>">

            </div>
            <div class="col-sm-3 align" style="background-color:#A3FFB1;">
              <h5><b>Fall:</b></h5>
              <div class="col-sm-4">
                <h5>Sep</h5>
              </div>
              <div class="col-sm-4">
                <h5>Oct</h5>
              </div>
              <div class="col-sm-4">
                <h5>Nov</h5>
              </div>
                                <input type="checkbox" id="sep"  class="group3 col-sm-4" disabled value="sep" name="sep" <?php if (!empty($sep)) {echo 'checked';} ?>>
                  <input type="hidden" id="sep"  class="group3 col-sm-4" name="sep" value="<?php if (!empty($sep)) {echo 'checked';} ?>">
                  <input type="checkbox"  class="group3 col-sm-4" id="oct" disabled value="oct" name="oct" <?php if (!empty($oct)) {echo 'checked';} ?>>
                  <input type="hidden"  class="group3 col-sm-4" id="oct"  name="oct" value="<?php if (!empty($oct)) {echo 'checked';} ?>">
                  <input type="checkbox"  class="group3 col-sm-4"  id="nov" disabled value="nov" name="nov" <?php if (!empty($nov)) {echo 'checked';} ?>>
                  <input type="hidden" class="group3 col-sm-4"  id="nov" name="nov" value="<?php if (!empty($nov)) {echo 'checked';} ?>">

            </div>
            <div class="col-sm-3 align" style="background-color:#F3BE92;">
              <h5><b>Winter:</b></h5>
              <div class="col-sm-4">
                <h5>Dec</h5>
              </div>
              <div class="col-sm-4">
                <h5>Jan</h5>
              </div>
              <div class="col-sm-4">
                <h5>Feb</h5>
              </div>
                                <input type="checkbox" id="dec" class="group4 col-sm-4"  disabled value="dec" name="dec" <?php if (!empty($dec)) {echo 'checked';} ?>>
                  <input type="hidden" id="dec" class="group4 col-sm-4"   name="dec" value="<?php if (!empty($dec)) {echo 'checked';} ?>">
                  <input type="checkbox" class="group4 col-sm-4"  id="jan" disabled value="jan" name="jan" <?php if (!empty($jan)) {echo 'checked';} ?>>
                  <input type="hidden" class="group4 col-sm-4"  id="jan"  name="jan" value="<?php if (!empty($jan)) {echo 'checked';} ?>">
                  <input type="checkbox" class="group4 col-sm-4"   id="feb" disabled value="feb" name="feb" <?php if (!empty($feb)) {echo 'checked';} ?>>
                  <input type="hidden" class="group4 col-sm-4"   id="feb"  name="feb" value="<?php if (!empty($feb)) {echo 'checked';} ?>">

            </div>
          </div>
<div style="height:1px;border-top: 1px solid #cccccc;"></div>

          <div class="col-sm-12"><br/><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Schedule on the  :</label>
            <div class="col-sm-2">
              <input type="text" class="form-control col-sm-2" id="inputEmail3" name="schedule_on" disabled value="{{$schedule_on}}"><br/>
              <input type="hidden" class="form-control col-sm-2" id="inputEmail3" name="schedule_on" value="{{$schedule_on}}">
            </div>
            <label for="inputEmail3" class="col-sm-2 control-label"><b>Mar, Jun, Sept, Jan </b></label>
            <label for="inputEmail3" class="col-sm-2 control-label">of every month</label>
          </div>
<br/>
          <h3 class="text-muted" style="padding-top:20px;">Payment Info</h3>
          <div class="col-sm-12">
            <div style="width:auto;height:auto;border-top: 1px solid #cccccc;"></div><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Initial Service :</label>
            <div class="col-sm-4">
              <div class="input-group">
              <span class="input-group-addon" id="sizing-addon1">$</span>
              <input type="text" class="form-control" id="inputEmail3" name="initial_service" disabled value="{{$initial_service}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="initial_service" value="{{$initial_service}}">
             </div>
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Regular Service:</label>
            <div class="col-sm-4">
              <div class="input-group">
                <span class="input-group-addon" id="sizing-addon1">$</span>
              <input type="text" class="form-control" id="inputPassword3" name="regular_service" disabled value="{{$regular_service}}"><br/>
              <input type="hidden" class="form-control" id="inputPassword3" name="regular_service" value="{{$regular_service}}"><br/>
            </div><br/>
            </div>
          </div>

          <div class="col-sm-12">
            <label for="inputEmail3" class="col-sm-2 control-label">Credit Card :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="credit_card" disabled value="{{$credit_card}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="credit_card" value="{{$credit_card}}">
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Expire on:</label>
            <div class="col-sm-2">
              <input type="text" class="form-control" id="inputEmail3" name="expire_month" disabled value="{{$expire_month}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="expire_month" value="{{$expire_month}}">
              <!-- <select class="form-control" name="expire_month">
                <option>Month</option>
                <option value="jan">Jan</option>
                <option value="feb">Feb</option>
                <option value="march">March</option>
              </select> -->
            </div>
             <div class="col-sm-2">
              <input type="text" class="form-control" id="inputEmail3" name="expire_year" disabled value="{{$expire_year}}">
              <input type="hidden" class="form-control" id="inputEmail3" name="expire_year" value="{{$expire_year}}">
              <!-- <select class="form-control" name="expire_year">
                <option>year</option>
                <option value="2015">2015</option>
                <option value="2016">2016</option>
                <option value="2017">2017</option>
              </select> -->
            </div>
          </div>

          <div class="col-sm-12"><br/>
            <input type="radio" class="col-sm-1 control-label" disabled name="easypay" <?php if (!empty($easypay)) {echo 'checked';} ?>> EasyPay
            <input type="hidden" class="col-sm-1 control-label" name="easypay" value="<?php if (!empty($easypay)) {echo 'checked';} ?>">
            
          </div>

        <div class="col-sm-12">
          <div class="col-sm-3">
            <h3 class="text-muted" style="padding-top:20px;">Special Areas :</h3>
            <textarea class="form-control" name="special_area"  disabled  rows="6">{{$special_area}}</textarea>
            <input type="hidden" class="form-control" id="inputPassword3" name="special_area" value="{{$special_area}}">
          </div>
          <div class="col-sm-3">
            <h3 class="text-muted" style="padding-top:20px;">Pests :</h3>
            <textarea class="form-control" rows="6" name="pests"  disabled >{{$pests}}</textarea><br/>
            <input type="hidden" class="form-control" id="inputPassword3" name="pests" value="{{$pests}}">
          </div>
          <div class='col-sm-6'>
            <h3 class="text-muted" style="padding-top:20px;">Official Use :</h3>
            <input type="checkbox" name="offical_use1" disabled <?php if (!empty($offical_use1)) {echo 'checked';} ?>> Special Access (e.g. Locked Gates) <br/>
            <input type="checkbox" name="offical_use2" disabled <?php if (!empty($offical_use2)) {echo 'checked';} ?>> SPC <br/>
            <input type="checkbox" name="offical_use3" disabled <?php if (!empty($offical_use3)) {echo 'checked';} ?>> Pre-Authorization Req. ($20 Extra) <br/>
            <input type="checkbox" name="offical_use4" disabled <?php if (!empty($offical_use4)) {echo 'checked';} ?>> Interior on Initial Service ($30 Extra) <br/>
            <input type="checkbox" name="offical_use5" disabled <?php if (!empty($offical_use5)) {echo 'checked';} ?>> Interior Every Time ($20 Extra) <br/>
            <input type="checkbox" name="offical_use6" disabled <?php if (!empty($offical_use6)) {echo 'checked';} ?>> Exterior Only treatment <br/>

            <input type="hidden" name="offical_use1" value="<?php if (!empty($offical_use1)) {echo 'checked';} ?>">
            <input type="hidden" name="offical_use2" value="<?php if (!empty($offical_use2)) {echo 'checked';} ?>">
            <input type="hidden" name="offical_use3" value="<?php if (!empty($offical_use3)) {echo 'checked';} ?>">
            <input type="hidden" name="offical_use4" value="<?php if (!empty($offical_use4)) {echo 'checked';} ?>">
            <input type="hidden" name="offical_use5" value="<?php if (!empty($offical_use5)) {echo 'checked';} ?>">
            <input type="hidden" name="offical_use6" value="<?php if (!empty($offical_use6)) {echo 'checked';} ?>">
          </div>
         </div>

          <div class="col-sm-12">
          <div class="col-sm-12">
            <h3 class="text-muted" style="padding-top:20px;">Account Notes :</h3>
            <textarea class="form-control"  disabled  name="account_notes" rows="3">{{$account_notes}}</textarea>
            <input type="hidden" class="form-control" id="inputPassword3" name="account_notes" value="{{$account_notes}}">
          </div>
         </div>


          <div class="col-sm-12"><br/>
            <div class="col-sm-offset-4 col-sm-10">
              <button type="submit" class="btn btn-default btn-primary">Confirmed Details</button>
            </div>
          </div>
        </form>
      <!-- two form action/submit used first for Next/submit and second is when we redirected from confirm page.( then direct submit ) -->
    </div>


  </div>


<script type="text/javascript">
        // $(function () {
        //     $('#datetimepicker8').datetimepicker({
        //       format: 'MM/DD/YYYY',
        //         icons: {
        //             time: "fa fa-clock-o",
        //             date: "fa fa-calendar",
        //             up: "fa fa-arrow-up",
        //             down: "fa fa-arrow-down"
        //         }
        //     });
        // });
$('input[type="checkbox"]').on('change', function() {
   $(this).siblings('input[type="checkbox"]').not(this).prop('checked', false);
});

    </script>
</div>   
@stop