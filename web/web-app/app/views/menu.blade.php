<nav class="navbar navbar-default" role="navigation" >
            <div class="container-fluid">
              <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                  <span class="sr-only">Toggle navigation</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </button>
              </div>

              <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('home/index','<i class="glyphicon glyphicon-home"></i> Home  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li>
                  
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('admin/index','<i class="glyphicon glyphicon-user"></i> Admin  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li>
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('shop/index','<i class="glyphicon glyphicon-shopping-cart"></i> Shops  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li>
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('agent/index','<i class="glyphicon glyphicon-user"></i> Agents  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li>
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('order/index','<i class="glyphicon glyphicon-pencil"></i> Orders  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li><li class="dropdown">
 {{ HTML::decode(HTML::link('news/index','<i class="glyphicon glyphicon-envelope"></i> News  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li>
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('offers/index','<i class="glyphicon glyphicon-user"></i> Offers  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li>
                  <li class="dropdown">
 {{ HTML::decode(HTML::link('product/index','<i class="glyphicon glyphicon-th"></i> Products  ', array('class' => 'dropdown-toggle'))) }}
                    
                  </li><li class="dropdown">
 {{ HTML::decode(HTML::link('complaint/index','<i class="glyphicon glyphicon-exclamation-sign"></i> Complaints  ', array('class' => 'dropdown-toggle'))) }}
                  </li><li class="dropdown">
 {{ HTML::decode(HTML::link('bills','<i class="glyphicon glyphicon-list-alt"></i> Bills  ', array('class' => 'dropdown-toggle'))) }}
                 </li>
           
                </ul> 
              </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
         </nav>
