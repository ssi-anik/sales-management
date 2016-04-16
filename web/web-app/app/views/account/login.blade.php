<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Sales Management</title>

    {{ HTML::style('css/bootstrap.css') }}
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 42px auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      .error-message{
        color:red;
      }

    </style>
    {{ HTML::style('/css/bootstrap-responsive.css') }}

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

  </head>

  <body>

    <div class="container">

      <form class="form-signin" action="login" method="POST">

      	<div style="text-align:center;">
          <!-- {{ HTML::image('/images/logo.png', 'logo') }} -->
          
      	</div>
        <h4 style="text-align:center;" class="form-signin-heading">Sales Management</h4>

      	<hr>
        <h4 style="text-align:center;" class="form-signin-heading">Sign In</h4>

          @if (Session::get('status') != null)
            <div class="alert alert-danger"> 
              {{ Session::get('status')}}
            </div>
          @endif

          @if ($errors->has('username'))
            @foreach ($errors->get('username', '<p class="error-message">:message</p>') as $email_error)
              {{ $email_error }}
            @endforeach
          @endif


          {{ Form::text('username', Input::old('username'), array('class' => 'form-control  input-block-level','placeholder'=>'Enter Username')) }}

           @if ($errors->has('password'))
            @foreach ($errors->get('password', '<p class="error-message">:message</p>') as $email_error)
              {{ $email_error }}
            @endforeach
          @endif

          {{ Form::password('password', array('class' => 'form-control input-block-level','placeholder'=>'Enter Password')) }}
          
          <div style="text-align:center;">
          	<button class="btn btn-large btn-success" type="submit"><i class="glyphicon glyphicon-log-in icon-white"></i>Sign In</button>
        	</div><br>
          <div style="font-size:12px;" class="pull-right">
          {{ HTML::decode(HTML::link('account/forgetpassword',' Forget Password ')) }} &nbsp;&nbsp;
          {{ HTML::decode(HTML::link('account/forgetusername',' Forget Username ')) }} 
          </div>


          {{ Form::token() }}
      </form>
    </div>

  </body>

</html>
