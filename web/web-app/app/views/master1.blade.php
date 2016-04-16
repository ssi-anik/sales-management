<!DOCTYPE html>
<html>
<head>
    <title>Sales Management</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <meta content='text/html;charset=utf-8' http-equiv='content-type'>

    {{ HTML::style('css/bootstrap.css',array('media'=>'all')) }}
    {{ HTML::style('css/custom.css',array('media'=>'all')) }}
    <!--{{ HTML::style('Stylesheets/bootstrap.css',array('media'=>'all')) }}-->
    {{ HTML::style('Stylesheets/bootstrap-responsive.css',array('media'=>'all')) }}

    <!-- / jquery ui -->
    {{ HTML::style('Stylesheets/jquery-ui-1.10.0.custom.css',array('media'=>'all')) }}    
    {{ HTML::style('Stylesheets/jquery.ui.1.10.0.ie.css',array('media'=>'all')) }}    

    <!-- / switch buttons -->
    {{ HTML::style('Stylesheets/bootstrap-switch.css',array('media'=>'all')) }}    

    <!-- / xeditable -->
    {{ HTML::style('Stylesheets/bootstrap-editable.css',array('media'=>'all')) }}    
    {{ HTML::style('Stylesheets/bootstrap-wysihtml5.css',array('media'=>'all')) }}    

    <!-- / wysihtml5 (wysywig) -->

    {{ HTML::style('Stylesheets/jquery-ui-1.10.0.custom.css',array('media'=>'all')) }}    

    <!-- / jquery file upload -->
    {{ HTML::style('Stylesheets/jquery.fileupload-ui.css',array('media'=>'all')) }}    

    <!-- / full calendar -->
    {{ HTML::style('Stylesheets/fullcalendar.css',array('media'=>'all')) }}    

    <!-- / select2 -->
    {{ HTML::style('Stylesheets/select2.css',array('media'=>'all')) }}    


    <!-- / mention -->       
    {{ HTML::style('Stylesheets/mention.css',array('media'=>'all')) }}    

    <!-- / tabdrop (responsive tabs) -->    
    {{ HTML::style('Stylesheets/tabdrop.css',array('media'=>'all')) }}    

    <!-- / jgrowl notifications -->    
    {{ HTML::style('Stylesheets/jquery.jgrowl.min.css',array('media'=>'all')) }}    

    <!-- / datatables -->
    {{ HTML::style('Stylesheets/bootstrap-datatable.css',array('media'=>'all')) }}    

    <!-- / dynatrees (file trees) -->
    {{ HTML::style('Stylesheets/ui.dynatree.css',array('media'=>'all')) }}    

    <!-- / color picker -->
    {{ HTML::style('Stylesheets/bootstrap-colorpicker.css',array('media'=>'all')) }}    

    <!-- / datetime picker -->
    {{ HTML::style('Stylesheets/bootstrap-datetimepicker.min.css',array('media'=>'all')) }}    
    
    <!-- / daterange picker) -->
    {{ HTML::style('Stylesheets/bootstrap-daterangepicker.css',array('media'=>'all')) }}    
    <!-- / flags (country flags) -->
    {{ HTML::style('Stylesheets/flags.css',array('media'=>'all')) }}    

    <!-- / slider nav (address book) -->
    {{ HTML::style('Stylesheets/slidernav.css',array('media'=>'all')) }}    
    <!-- / fuelux (wizard) -->
    {{ HTML::style('Stylesheets/wizard.css',array('media'=>'all')) }}    
    <!-- / flatty theme -->
    {{ HTML::style('Stylesheets/light-theme.css',array('media'=>'all','id'=>'color-settings-color')) }}    
    <!-- / demo -->
    {{ HTML::style('Stylesheets/demo.css',array('media'=>'all')) }}    

    <style type="text/css">
        #content{
            padding-top: 20px;
        }
    </style>
        <style type="text/css">
    a {color:#3174c7 !important;}
    body {background:white !important;}
    .sidebarcolor li {/*background-color: #F5F5F5;*/ border: 2px solid #F5F5F5;}
    .sidebarcolor li a {padding: 5px 15px;}
    h3{margin-top: 12px;}
    .container-fluid{max-width: 1100px;}
    #ui-datepicker-div{z-index: 1000px !important;}
    .breadcrumb {height: 40px;background-color: white !important;}
    .breadcrumb h3{color: #7A797A !important;font-size: 12px;}
    .navs li {border-right: .5px solid #F5F5F5;}
    </style>
</head>
<body class=''>
    <div class="container-fluid" >

        <div class="" role="navigation" style="min-height:100px;">
          <div class="container-fluid">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" style="font-size:37px;padding-top:34px;" href="http://localhost/sales-management/home/index"><!-- <img src="{{asset('images/logo.png')}}"> -->
              <u>Sales</u> <span style="color:grey">Management</span></a>
            </div>
            <div class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right navs" style="margin-top:25px;">
                
                <li style="border-right:none !important;">{{ HTML::decode(HTML::link('home/logout','<i class="glyphicon glyphicon-log-out"></i> Sign Out ')) }}</li>
              </ul>

              
              <div class="navbar-form pull-right">
                <label class=""></label>
              </div>
            </div>
            <div class="col-md-3 col-md-offset-3" style="text-align:right;">
              
            </div>
          </div>
        </div>
        
        @include('menu')
          
        <div class="container-fluid">
          <div class="row">
            
               @yield('content')
          </div>
          <br>
          <div class="container-fluid" style="text-align:center;border-top:2px solid #f5f5f5;">
            <p class="text-muted"><br>Sales Management @ Copywrighted 2014-2019</p><br>
          </div>
        </div>
    </div>
<!-- / jquery -->
{{ HTML::script('Scripts/jquery.min.js')}}

<!-- / jquery mobile events (for touch and slide) -->
{{ HTML::script('Scripts/jquery.mobile-events.min.js')}}
<!-- / jquery migrate (for compatibility with new jquery) -->



<!-- / jquery ui -->
{{ HTML::script('Scripts/jquery.min.js')}}

<!-- / bootstrap 
{{ HTML::script('Scripts/bootstrap.min.js')}} -->
{{ HTML::script('js/bootstrap.min.js')}}

{{ HTML::script('Scripts/excanvas.js')}}

<!-- / sparklines -->
{{ HTML::script('Scripts/jquery.sparkline.min.js')}}


<!-- / fullcalendar -->
{{ HTML::script('Scripts/fullcalendar.min.js')}}

<!-- / datatables 
{{ HTML::script('Scripts/jquery.dataTables.min.js')}}

{{ HTML::script('Scripts/jquery.dataTables.columnFilter.js')}} -->

<!-- / wysihtml5 
{{ HTML::script('Scripts/wysihtml5.min.js')}}

{{ HTML::script('Scripts/bootstrap-wysihtml5.js')}} -->

<!-- / select2 -->
{{ HTML::script('Scripts/select2.js')}}

<!-- / color picker -->
{{ HTML::script('Scripts/bootstrap-colorpicker.min.js')}}

<!-- / mention -->
{{ HTML::script('Scripts/mention.min.js')}}

<!-- / input mask -->
{{ HTML::script('Scripts/bootstrap-inputmask.min.js')}}

<!-- / fileinput -->
{{ HTML::script('Scripts/bootstrap-fileinput.js')}}

<!-- / modernizr -->
{{ HTML::script('Scripts/modernizr.min.js')}}

<!-- / retina -->
{{ HTML::script('Scripts/retina.js')}}

<!-- / fileupload -->
{{ HTML::script('Scripts/tmpl.min.js')}}

{{ HTML::script('Scripts/load-image.min.js')}}

{{ HTML::script('Scripts/canvas-to-blob.min.js')}}

{{ HTML::script('Scripts/jquery.iframe-transport.min.js')}}

<!-- {{ HTML::script('Scripts/jquery.fileupload.min.js')}}

{{ HTML::script('Scripts/jquery.fileupload-fp.min.js')}}

{{ HTML::script('Scripts/jquery.fileupload-ui.min.js')}}

{{ HTML::script('Scripts/jquery.fileupload-init.js')}}
 -->
<!-- / timeago 
{{ HTML::script('Scripts/jquery.timeago.js')}} -->

<!-- / slimscroll 
{{ HTML::script('Scripts/jquery.slimscroll.min.js')}} -->

<!-- / autosize (for textareas) 
{{ HTML::script('Scripts/jquery.autosize-min.js')}} -->

<!-- / charCount 
<!-- {{ HTML::script('Scripts/chartCount.js')}} -->

<!-- / validate 
{{ HTML::script('Scripts/jquery.validate.min.js')}} -->

<!-- {{ HTML::script('Scripts/additional-method.js')}} -->

<!-- / naked password 
{{ HTML::script('Scripts/naked_password-0.2.4.min.js')}} -->

<!-- / nestable 
{{ HTML::script('Scripts/jquery.nestable.js')}} -->

<!-- / tabdrop 
{{ HTML::script('Scripts/bootstrap-tabdrop.js')}} -->

<!-- / jgrowl
{{ HTML::script('Scripts/jquery.jgrowl.min.js')}} -->

<!-- / bootbox  
{{ HTML::script('Scripts/bootbox.min.js')}} -->

<!-- / inplace editing
{{ HTML::script('Scripts/bootstrap-editable.min.js')}} 

{{ HTML::script('Scripts/wysihtml5.js')}} -->

<!-- / ckeditor -->
{{ HTML::script('Scripts/ckeditor.js')}}

<!-- / filetrees -->
{{ HTML::script('Scripts/jquery.min.js')}}

<!-- / datetime picker -->
{{ HTML::script('Scripts/bootstrap-datetimepicker.js')}}

<!-- / daterange picker -->
{{ HTML::script('Scripts/moment.min.js')}}

{{ HTML::script('Scripts/bootstrap-daterangepicker.js')}}

<!-- / max length 
{{ HTML::script('Scripts/bootstrap-maxlength.min.js')}} -->

<!-- / dropdown hover 
{{ HTML::script('Scripts/twitter-bootstrap-hover-dropdown.min.js')}} -->

<!-- / slider nav (address book) 
{{ HTML::script('Scripts/slidernav-min.js')}} -->

<!-- / fuelux 
{{ HTML::script('Scripts/wizard.js')}}-->

<!-- / flatty theme 
{{ HTML::script('Scripts/nav.js')}}-->

<!-- {{ HTML::script('Scripts/tables.js')}} 

{{ HTML::script('Scripts/theme.js')}} -->

<!-- / demo 
{{ HTML::script('Scripts/jquery.mockjax.js')}}

{{ HTML::script('Scripts/inplace_editing.js')}}

{{ HTML::script('Scripts/charts.js')}}

{{ HTML::script('Scripts/demo.js')}}-->

@yield('script')

</body>
</html>