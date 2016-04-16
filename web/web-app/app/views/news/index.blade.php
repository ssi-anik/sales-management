@extends('master')

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

{{ HTML::script('/js/bootstrap.min.js')}}


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


<script type="text/javascript" language="javascript" class="init">

function format ( d ) {
  // `d` is the original data object for the row
  return '<table cellpadding="5" cellspacing="0" border="0" style="">'+
  '<tr>'+
      '<td>News ID:</td>'+
      '<td>'+ d.news_id +'</td>'+
    '</tr>'+
    '<tr>'+
      '<td>Title:</td>'+
      '<td>'+ d.title +'</td>'+
    '</tr>'+
    '<tr>'+
      '<td>Description:</td>'+
      '<td>'+ d.desc +'</td>'+
    '</tr>'+
    '<tr>'+
      '<td>Link:</td>'+
      '<td>'+ d.link +'</td>'+
    '</tr>'+
    
  '</table>';
}


var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function() {


  editor = new $.fn.dataTable.Editor( {
    ajax: {
      create: {
        type: 'POST',
        url:  'additem'
      },
      edit: {
        type: 'PUT',
        url:  'edititem/_id_'
      },
      remove: {
        type: 'DELETE',
        url:  'deleteitem/_id_'
      }
    },
    table: "#example",
    idSrc: "order_id",
    fields: [ {
        label: "Title:",
        name: "title"
      }, {
        label: "Description:",
        name: "desc",
        // type:"service_address"
      }, 
      {
        label: "Link:",

        name: "link",
        type:"textarea"
      }, 
      // {
      //   label: "Creation Date:",
      //   name: "creation_date",
      //   type:"date",
      //   dateImage: "{{ URL::asset('public/images/calender.png') }}",                                   
      //   def:        function () { return new Date(); },
      //   dateFormat: $.datepicker.ISO_8601
      //   },
        // {
        //         label:     "Active:",
        //         name:      "status",
        //         type:      "checkbox",
        //         separator: "|",
        //         ipOpts:    [
        //             { label: '', value: 1 }
        //         ]
        //     }
      // , {
      //   label: "Extension:",
      //   name: "extn"
      // }, {
      //   label: "Start date:",
      //   name: "start_date"
      // }, {
      //   label: "Salary:",
      //   name: "salary"
      // }
    ]
  } );

  editor.on( 'preSubmit', function ( e, o ) {
        if ( o.data.title === '' ) {
            this.error('title', 'A Title must be given');
            return false;
        }
        else if ( o.data.desc === '' ) {
            this.error('desc', 'A Description must be given');
            return false;
        }
        else if ( o.data.link === '' ) {
            this.error('link', 'A Link must be given');
            return false;
        }

    } );

  var table = $('#example').DataTable( {
        // AutoWidth: 'true',

    bAutoWidth:false,
    sDom: 'CT<"clear"><"H"lfr>t<"F"ip>',
        colVis: {
            exclude: [ 0 ]
        },
    ajax: "allitem",
    columns: [
      {
                class:'details-control',
                orderable:false,
                data:null,
                searchable:false,
                sWidth: '20px',
                defaultContent:''
            },
      // { data: null, render: function ( data, type, row ) {
      //   // Combine the first and last names into a single table field
      //   return data.first_name+' '+data.last_name;
      // } },
      { data: "news_id" },
      { data: "title" },
      { data: "desc" },
      { data: "link" },
      // { data: Date.parse(data[4]) },
      // { data: "start_date" },
          
      // { data: "cost", render: $.fn.dataTable.render.number( ',', '.', 0, '$' ) },
      //        { data: "delivery_on"},
      //      {
      //           targets: 0,
      //           data: "order_id",
      //           render: function ( data, type, full, meta ) {
      //             return '<a href="edit/'+data+'">Edit</a>';
      //           }
      //      }
            // action end
    ],
    order: [[1, 'asc']],
    tableTools: {

      sRowSelect: "os",
      sSwfPath: "{{ URL::asset('public/swf/copy_csv_xls_pdf.swf') }}",
      aButtons: [
        { sExtends: "editor_create", editor: editor },
        { sExtends: "editor_edit",   editor: editor },
        // duplicate option
       
                // END duplicate option 
        { sExtends: "editor_remove", editor: editor },
        //'csv',
        // {
    //                 "sExtends": "csv",
    //                 "sButtonText": "Excel",
    //                 //"mColumns": [ 0, 1, 4 ]
    //                 //"mColumns": "visible"
    //                 "bFooter": false,
    //                 "bSelectedOnly": true, // selected rows exported in xls
    //             },
        // 'pdf',
        {
                    sExtends: "collection",
                    sButtonText: "Save",
                    sButtonClass: "save-collection",
                    aButtons: [ 'copy', 'csv', 'xls', 'pdf' ]
                },
        {
                    "sExtends": "print",
                    "sInfo": "Please press escape when done"
                }

      ],
      sRowSelector: 'td:not(:last-child)' // no row selection on last column
    },
    rowCallback: function ( row, data ) {
            // Set the checked state of the checkbox in the table
            $('input.editor-active', row).prop( 'checked', data.status == 1 );
        }
  } );

  $('#example').on( 'change', 'input.editor-active', function () {
        editor
            .edit( $(this).closest('tr'), false )
            .set( 'status', $(this).prop( 'checked' ) ? 1 : 0 )
            .submit();
    } );

  // $('#example tbody').on( 'click', 'button', function () {
  //       var data = table.row( $(this).parents('tr') ).data();
  //       alert( data[0] +"'s item_group_id is: "+ data[ 0 ] );
  //   } );
  
  $('#example tbody').on('click', 'td.details-control', function() {
            var tr = $(this).parents('tr');
            var row = table.row(tr);
 
            if (row.child.isShown()) {
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                row.child(format(row.data())).show();
                tr.addClass('shown');
            }
        });

} );

<!-- individual column filter -->



$(document).ready(function() {


    // Setup - add a text input to each footer cell
    // $('#example thead th').each( function () {
    //     var title = $('#example thead th').eq( $(this).index() ).text();
    //     $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    // } );

 
    // DataTable
    var table = $('#example').DataTable();
 
    // Apply the filter
    table.columns().eq( 0 ).each( function ( colIdx ) {
        $( 'input', table.column( colIdx ).header() ).on( 'keyup change', function () {
            table
                .column( colIdx )
                .search( this.value )
                .draw();
        } );
    } );
} );



  </script>

</div></div></div>
   <!-- <ol class="breadcrumb" style="width:100% !important;">
    <div class="container-fluid">
                <h3>All Orders</h3>
      <div>
   </ol> -->
<div class="container-fluid">
<div class="row">
  <div class="col-xs-12 col-md-12">

    <!-- <div class="table-responsive" role="form"> -->
      <h3>All News
</h3><br/>
        <table id="example" class="display" cellspacing="0" width="50%">
          <thead>
            <tr>
              <th></th>
                <th>News Id</th>
        <th>Title</th>
        <th>Description</th>
        <th>Link</th>
            </tr>
          </thead>

          <tfoot>
            <tr>
              <th></th>
              <th>News Id</th>
        <th>Title</th>
        <th>Description</th>
        <th>Link</th>
            </tr>
          </tfoot>
        </table>

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