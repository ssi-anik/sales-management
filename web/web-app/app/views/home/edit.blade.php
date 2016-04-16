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
      
          
        <form class="form-horizontal" action="save" method="post">
<br/><br/>
          <div class="col-sm-12">
            <label for="inputEmail3" class="col-sm-2 control-label" style="font-size:17px;"><b>Account Number</b></label>

            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="account_number" value="{{$account_number}}">
            <br/></div>
            
          </div>


          <div class="col-sm-12">
            <div style="width:auto;height:auto;border-top: 1px solid #cccccc;"></div><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Start Date :</label>
            <div class="col-sm-4">
              <div class="input-group date" id="datetimepicker8">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">
                        </span>
                    </span>
                <input type="text" class="form-control" name="start_date" value="{{$start_date}}">
              </div>
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Promo Area :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputPassword3" name="promo_code" value="{{$promo_code}}"><br/>
            </div>
          </div>

          <h3 class="text-muted">Client Information</h3>
<div style="width:auto;height:auto;border-top: 1px solid #cccccc;"></div>
          <div class="col-sm-12"><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Client Name :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="client_name" value="{{$client_name}}">
            </div>
          </div>

        <div class="col-sm-12">
          <div class="col-sm-6">
            <label for="inputEmail3" class="col-sm-6 control-label" style="padding-left:0px;">Service Address :</label>
            <textarea class="form-control" name="service_address"  rows="6">{{$service_address}}</textarea>
          </div>
          <div class="col-sm-6">
            <label for="inputPassword3" class="col-sm-6 control-label" style="padding-left:0px;">Billing Address :</label>
            <textarea class="form-control" rows="3" name="billing_address"  >{{$billing_address}}</textarea><br/>
            <label for="inputPassword3" class="col-sm-4 control-label" style="padding-left:0px;">Phone Number :</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" id="inputPassword3" name="phone1"  value="{{$phone1}}">
            </div>
          </div>
         </div>

          <div class="col-sm-12"><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Email :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="email"  value="{{$email}}">
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Phone Number :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputPassword3" name="Phone2"  value="{{$Phone2}}"><br/>
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
              <input type="checkbox" id="mar" class="group1 col-sm-4" value="mar" name="mar" <?php if (!empty($mar)) {echo 'checked';} ?>>
              <input type="checkbox" id="apr" class="group1 col-sm-4" value="apr" name="apr" <?php if (!empty($apr)) {echo 'checked';} ?>>
              <input type="checkbox" id="may" class="group1 col-sm-4" value="may" name="may" <?php if (!empty($may)) {echo 'checked';} ?>>

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
                  <input type="checkbox" id="jun"  class="group2 col-sm-4" value="jun" name="jun" <?php if (!empty($jun)) {echo 'checked';} ?>>
                  <input type="checkbox" id="jul"  class="group2 col-sm-4" value="jul" name="jul" <?php if (!empty($jul)) {echo 'checked';} ?>>
                  <input type="checkbox" id="aug"  class="group2 col-sm-4" value="aug" name="aug" <?php if (!empty($aug)) {echo 'checked';} ?>>

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
              <input type="checkbox" id="sep"  class="group3 col-sm-4" value="sep" name="sep" <?php if (!empty($sep)) {echo 'checked';} ?>>
                  <input type="checkbox" id="oct"  class="group3 col-sm-4" value="oct" name="oct" <?php if (!empty($oct)) {echo 'checked';} ?>>
                  <input type="checkbox" id="nov"  class="group3 col-sm-4"value="nov" name="nov" <?php if (!empty($nov)) {echo 'checked';} ?>>

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
                  <input type="checkbox" id="dec"  class="group4 col-sm-4" value="dec" name="dec" <?php if (!empty($dec)) {echo 'checked';} ?>>
                  <input type="checkbox" id="jan"  class="group4 col-sm-4" value="jan" name="jan" <?php if (!empty($jan)) {echo 'checked';} ?>>
                  <input type="checkbox" id="feb"  class="group4 col-sm-4" value="feb" name="feb" <?php if (!empty($feb)) {echo 'checked';} ?>>

            </div>
          </div>
<div style="height:1px;border-top: 1px solid #cccccc;"></div>

          <div class="col-sm-12"><br/><br/>
            <label for="inputEmail3" class="col-sm-2 control-label">Schedule on the  :</label>
            <div class="col-sm-2">
              <input type="text" class="form-control col-sm-2" id="inputEmail3" name="schedule_on" value="{{$schedule_on}}"><br/>
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
              <input type="text" class="form-control" id="inputEmail3" name="initial_service"  value="{{$initial_service}}">
             </div>
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Regular Service:</label>
            <div class="col-sm-4">
              <div class="input-group">
                <span class="input-group-addon" id="sizing-addon1">$</span>
              <input type="text" class="form-control" id="inputPassword3" name="regular_service" value="{{$regular_service}}"><br/>
            </div><br/>
            </div>
          </div>

          <div class="col-sm-12">
            <label for="inputEmail3" class="col-sm-2 control-label">Credit Card :</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="inputEmail3" name="credit_card" value="{{$credit_card}}">
            </div>
            <label for="inputPassword3" class="col-sm-2 control-label">Expire on:</label>
            <div class="col-sm-2">
              <select class="form-control" name="expire_month">
                <option>Month</option>
                <option selected value="{{$expire_month}}">{{$expire_month}}</option>
                <option value="jan">Jan</option>
                <option value="feb">Feb</option>
                <option value="march">March</option>
              </select>
            </div>
             <div class="col-sm-2">
              <select class="form-control" name="expire_year">
                <option>year</option>
                <option selected value="{{$expire_year}}">{{$expire_year}}</option>
                <option value="2015">2015</option>
                <option value="2016">2016</option>
                <option value="2017">2017</option>
              </select>
            </div>
          </div>

          <div class="col-sm-12"><br/>
            <input type="radio" class="col-sm-1 control-label" name="easypay" value="<?php if (!empty($easypay)) {echo 'checked';} ?>"> EasyPay
            
          </div>

        <div class="col-sm-12">
          <div class="col-sm-3">
            <h3 class="text-muted" style="padding-top:20px;">Special Areas :</h3>
            <textarea class="form-control" name="special_area"    rows="6">{{$special_area}}</textarea>
          </div>
          <div class="col-sm-3">
            <h3 class="text-muted" style="padding-top:20px;">Pests :</h3>
            <textarea class="form-control" rows="6" name="pests"  >{{$pests}}</textarea><br/>
          </div>
          <div class='col-sm-6'>
            <h3 class="text-muted" style="padding-top:20px;">Official Use :</h3>

            <input type="checkbox" name="offical_use1" <?php if (!empty($offical_use1)) {echo 'checked';} ?>> Special Access (e.g. Locked Gates) <br/>
            <input type="checkbox" name="offical_use2" <?php if (!empty($offical_use2)) {echo 'checked';} ?>> SPC <br/>
            <input type="checkbox" name="offical_use3" <?php if (!empty($offical_use3)) {echo 'checked';} ?>> Pre-Authorization Req. ($20 Extra) <br/>
            <input type="checkbox" name="offical_use4" <?php if (!empty($offical_use4)) {echo 'checked';} ?>> Interior on Initial Service ($30 Extra) <br/>
            <input type="checkbox" name="offical_use5" <?php if (!empty($offical_use5)) {echo 'checked';} ?>> Interior Every Time ($20 Extra) <br/>
            <input type="checkbox" name="offical_use6" <?php if (!empty($offical_use6)) {echo 'checked';} ?>> Exterior Only treatment <br/>
          </div>
         </div>

          <div class="col-sm-12">
          <div class="col-sm-12">
            <h3 class="text-muted" style="padding-top:20px;">Account Notes :</h3>
            <textarea class="form-control"   name="account_notes" rows="3">{{$account_notes}}</textarea>
          </div>
         </div>


          <div class="col-sm-12"><br/>
            <div class="col-sm-offset-4 col-sm-10">
              <button type="submit" class="btn btn-default btn-primary">Save Details</button>
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