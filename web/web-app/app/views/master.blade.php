<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- <link rel="shortcut icon" href="{{asset('/images/logo.png')}}"> -->

    <title>Sales Management</title>

    <!-- Bootstrap core CSS -->

    {{ HTML::style('public/css/bootstrap.css',array('media'=>'all')) }}
    <!-- <link media="all" type="text/css" rel="stylesheet" href="http://mockup.in/billing_app/public/css/bootstrap.css"> -->

        <!-- Custom styles for this template -->
    {{ HTML::style('public/css/custom.css',array('media'=>'all')) }}

    <style type="text/css">
    .sidebarcolor li {/*background-color: #F5F5F5;*/ border: 2px solid #F5F5F5;}
    .sidebarcolor li a {padding: 5px 15px;}
    h3{margin-top: 12px;}
    .container-fluid{max-width: 1100px;}
    #ui-datepicker-div{z-index: 1000px !important;}
    .breadcrumb {height: 40px;background-color: white !important;}
    .breadcrumb h3{color: #7A797A !important;font-size: 12px;}
    .navs li {border-right: .5px solid #F5F5F5;}
    </style>
    

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('public/js/bootstrap.min.js')}}
 -->
<!-- <script src="http://getbootstrap.com/assets/js/docs.min.js"></script> -->

<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<!-- {{ HTML::script('public/js/bootstrapValidator.js')}} -->


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    .breadcrumb{background-color: #e7e7e7;}
    </style>
  </head>

  <body>
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

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- / bootstrap -->


@yield('scripts')

  </body>
</html>
