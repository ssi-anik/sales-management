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


<script type="text/javascript" language="javascript" class="init">

function format ( d ) {
  // `d` is the original data object for the row
  return '<table cellpadding="5" cellspacing="0" border="0" style="">'+
    '<tr>'+
      '<td>Name:</td>'+
      '<td>'+ d.title +'</td>'+
    '</tr>'+
    // '<tr>'+
    //   '<td>Creation date:</td>'+
    //   '<td>'+ d.creation_date +'</td>'+
    // '</tr>'+
    '<tr>'+
      '<td>Description:</td>'+
      '<td>'+ d.description +'</td>'+
    '</tr>'+
    '<tr>'+
      '<td>Percentage:</td>'+
      '<td>'+ d.percentage +'</td>'+
    '</tr>'+
    '<tr>'+
      '<td>Price:</td>'+
      '<td>'+ d.price +'</td>'+
    '</tr>'+
  '</table>';
}

var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function() {


  editor = new $.fn.dataTable.Editor( {
    ajax: {
      create: {
        type: 'POST',
        url:  'allitem'
      },
      edit: {
        type: 'PUT',
        url:  'edittaxes/_id_'
      },
      remove: {
        type: 'DELETE',
        url:  'deletetaxes/_id_'
      }
    },
    table: "#example",
    idSrc: "offer_id",
    fields: [ 
            // {
            //     label:     "Active:",
            //     name:      "status",
            //     type:      "checkbox",
            //     separator: "|",
            //     ipOpts:    [
            //         { label: '', value: 1 }
            //     ]
            // },
            {
        label: "Name:",
        name: "title"
      }, {
        label: "Description:",
        name: "description",
        type:"textarea"
      }
      // }, {
      //   label: "Creation Date:",
      //   name: "creation_date"
      // }
      , {
        label: "Type",
        name: "type",
        type:  "select",
                ipOpts: [
                    { label: "Percentage", value: 0 },
                    { label: "Price",  value: 1 }
                ],
                "default": 0
      }, {
        label: "Percentage:",
        name: "percentage"
      }, {
        label: "Price:",
        name: "price"
      }, {
        label: "Start date:",
        name: "start_date",
        type:"date",
        dateImage: "{{ URL::asset('images/calender.png') }}",                                   
        def:        function () { return new Date(); },
        dateFormat: $.datepicker.ISO_8601
      }, {
        label: "End Date:",
        name: "end_date",
        type:"date",
        dateImage: "{{ URL::asset('images/calender.png') }}",                                   
        def:        function () { return new Date(); },
        dateFormat: $.datepicker.ISO_8601
        }
    ]
  } );

    editor.on( 'preSubmit', function ( e, o ) {
        if ( o.data.name === '' ) {
            this.error('title', 'A Name must be given');
            return false;
        }
        else if ( o.data.description === '' ) {
            this.error('description', 'A Description must be given');
            return false;
        }
        else if ( o.data.type === '' ) {
            this.error('type', 'A Type must be given');
            return false;
        }
    } );

  var table = $('#example').DataTable( {
        // AutoWidth: 'true',

    // aoColumns: [
    //    { sWidth: "300px"},
    //    { sWidth: "300px"},
    //    { sWidth: "300px"},
    //    { sWidth: "300px"},
    //    { sWidth: "300px"}
    // ],
    bAutoWidth:false,
    //dom: "Tfrtip",
    //sDom: 'T<"clear">lfrtip',
    //dom: 'C<"clear">lfrtip',
    sDom: 'CT<"clear"><"H"lfr>t<"F"ip>',
        colVis: {
            exclude: [ 0 ]
        },
    //dom: '<"top"i>rt<"bottom"flp><"clear">',
    ajax: "allitem",
    columns: [
      {
                class:'details-control',
                orderable:false,
                data:null,

                sWidth: '20px',
                defaultContent:''
            },
      // { data: null, render: function ( data, type, row ) {
      //   // Combine the first and last names into a single table field
      //   return data.first_name+' '+data.last_name;
      // } },
      { data: "title" },
      { data: "description" },
      { data: "type" },
      { data: "percentage" },
      { data: "price" },
      { data: "start_date" },
      { data: "end_date" },
      // { data: "" },

      //{ data: "creation_date" },
      // { data: "start_date" },
      // { data: "salary", render: $.fn.dataTable.render.number( ',', '.', 0, '$' ) },
      
      // Checkbox for status
      // {
      //           data:   "status",
      //           render: function ( data, type, row ) {
      //               if ( type === 'display' ) {
      //                   return '<input type="checkbox" class="editor-active">';
      //               }
      //               return data;
      //           },
      //           className: "dt-body-center"
      //       }
      // END Checkbox for status
    ],
    order: [[1, 'asc']],
    tableTools: {

      sRowSelect: "os",
      sSwfPath: "{{ URL::asset('public/swf/copy_csv_xls_pdf.swf') }}",
      aButtons: [
        { sExtends: "editor_create", editor: editor },
        { sExtends: "editor_edit",   editor: editor },
        // duplicate option
        {
                    sExtends:    "select_single",
                    sButtonText: "Duplicate",
                    fnClick:     function( button, config ) {
                        var node = this.fnGetSelected();
 
                        if ( node.length !== 1 ) {
                            return;
                        }
 
                        // Place the selected row into edit mode (but hidden),
                        // then get the values for all fields in the form
                        var values = editor.edit( node[0], false ).val();
 
                        // Create a new entry (discarding the previous edit) and
                        // set the values from the read values
                        editor
                            .create( {
                                title: 'Duplicate '+values['users.first_name']+' '+values['users.last_name'],
                                buttons: 'Create from existing'
                            } )
                            .set( values );
                    }
                },
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

      ]
      //sRowSelector: 'td:not(:last-child)' // no row selection on last column
    }
    //,
    // rowCallback: function ( row, data ) {
    //         // Set the checked state of the checkbox in the table
    //         $('input.editor-active', row).prop( 'checked', data.status == 1 );
    //     }
  } );

  // $('#example').on( 'change', 'input.editor-active', function () {
  //       editor
  //           .edit( $(this).closest('tr'), false )
  //           .set( 'status', $(this).prop( 'checked' ) ? 1 : 0 )
  //           .submit();
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
      //editor.hide( 'percentage' );
      editor.hide( 'price' );


} );


<!-- individual column filter -->



$(document).ready(function() {


     // Setup - add a text input to each footer cell
    // $('#example thead th').each( function () {
    //     var title = $('#example thead th').eq( $(this).index() ).text();
    //     if($(this).index() !=0) $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
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


$(document).on( 'change', '#DTE_Field_type', function () {
//   // value was changed...
//   alert('hello');
//   $("#DTE_Field_Name_price").attr('disabled','disabled');
if ( $(this).val() === '1' ) {
      editor.hide( 'percentage' );
      editor.show( 'price' );
   }else {
      editor.show( 'percentage' );
      editor.hide( 'price' );
   }
} );


// $(function() {
//     $('#DTE_Field_type').change(function(){
//         if ($(this).val() == "0") {
//         $("#DTE_Field_Name_price").removeAttr("disabled"); 
//         $("#DTE_Field_Name_percentage").attr("disabled","disabled");

//         } else if ($(this).val() == "1"){
//             $('.DTE_Field_Name_price').show();
//             $('.DTE_Field_Name_percentage').hide();
//             $("#DTE_Field_Name_percentage").attr('disabled','disabled');
//         }else {
//         }
//     });
// });

  </script>

   <!-- <ol class="breadcrumb">
      <li><a href="#">Setting</a></li>
      <li class="active">Taxes</li>
   </ol> -->
<div class="row">
  <div class="col-xs-12 col-md-12">
          <h3>Offers<!-- <button class="btn pull-right"  data-toggle="modal" data-target=".bs-example-modal-sm"><i class="glyphicon glyphicon-plus"></i> Add Item</button> -->
          </h3>

    <!-- <div class="table-responsive" role="form"> -->

      
        <table id="example" class="display" cellspacing="0" width="50%">
          <thead>
            <tr>
              <th></th>
              <th>Name</th>
              <th>Description</th>
              <th>Type</th>
              <th>Percentage</th>
              <th>Price</th>
              <th>Start Date</th>
              <th>End Date</th>
              <!-- <th>No of Models</th> -->
              <!-- <th>Action</th> -->
            </tr>
          </thead>

          <tfoot>
            <tr>
              <th></th>
              <th>Name</th>
              <th>Description</th>
              <th>Type</th>
              <th>Percentage</th>
              <th>Price</th>
              <th>Start Date</th>
              <th>End Date</th>
            </tr>
          </tfoot>
        </table>

     <!-- </div> -->
  </div>

  <!-- <div class="col-xs-6 col-md-3"><br><br>
    <ul class="nav nav-pills nav-stacked sidebarcolor">
      <li >{{ HTML::decode(HTML::link('setting/index',' Variable ')) }}</li>
      <li class="active">{{ HTML::decode(HTML::link('setting/item',' Item Groups ')) }}</li>
      <li>{{ HTML::decode(HTML::link('setting/model',' Model Types ')) }}</li>
      <li>{{ HTML::decode(HTML::link('setting/taxes',' Taxes ')) }}</li>
    </ul>
  </div> -->

   

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