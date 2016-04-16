<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

    <title>Snap Pest Solutions</title>
<link href='http://localhost/CAL/fullcalendar.css' rel='stylesheet' />
<link href='http://localhost/CAL/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='http://localhost/CAL/moment.min.js'></script>
<script src='http://localhost/CAL/jquery.min.js'></script>
<script src='http://localhost/CAL/fullcalendar.min.js'></script>

<script>

  $(document).ready(function() {
    
    $('#calendar').fullCalendar({
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
      },
      defaultDate: '2015-02-12',
      editable: true,
      eventLimit: true, // allow "more" link when too many events
      events: [
        {
          title: 'All Day Event',
          start: '2015-02-01'
        },
        {
          title: 'Long Event',
          start: '2015-02-07',
          end: '2015-02-10'
        },
        {
          id: 999,
          title: 'Repeating Event',
          start: '2015-02-09T16:00:00'
        },
        {
          id: 999,
          title: 'Repeating Event',
          start: '2015-02-16T16:00:00'
        },
        {
          title: 'Conference',
          start: '2015-02-11',
          end: '2015-02-13'
        },
        {
          title: 'Meeting',
          start: '2015-02-12T10:30:00',
          end: '2015-02-12T12:30:00'
        },
        {
          title: 'Lunch',
          start: '2015-02-12T12:00:00'
        },
        {
          title: 'Meeting',
          start: '2015-02-12T14:30:00'
        },
        {
          title: 'Happy Hour',
          start: '2015-02-12T17:30:00'
        },
        {
          title: 'Dinner',
          start: '2015-02-12T20:00:00'
        },
        {
          title: 'Birthday Party',
          start: '2015-02-13T07:00:00'
        },
        {
          title: 'Click for Google',
          url: 'http://google.com/',
          start: '2015-02-28'
        }
      ]
    });
    
  });

</script>
<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 900px;
    margin: 0 auto;
  }

</style>
  </head>

  <body>
    <div class="container-fluid" >

        
        <div class="container-fluid">
          <div class="row">
            
               @yield('content')
          </div>
          <br>
          <div class="container-fluid" style="text-align:center;border-top:2px solid #f5f5f5;">
            <p class="text-muted"><br>Snap Pest Solutions @ Copywrighted 2014-2019</p><br>
          </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- / bootstrap -->



  </body>
</html>
