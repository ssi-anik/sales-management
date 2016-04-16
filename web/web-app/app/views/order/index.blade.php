@extends('master')
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
{{ HTML::script('js/bootstrap.min.js')}}
@section('content')
	@include('scripts')



	<style type="text/css">
		.col-sm-5 input{
			width: 80%;
		}
		option {
			padding-left: 15px;
		}
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
			return '<table cellpadding="5" cellspacing="0" border="0" style="">'+
						'<tr>'+
							'<td>Order ID:</td>'+
							'<td>'+ d.order_key +'</td>'+
						'</tr>'+
						'<tr>'+
							'<td>Products:</td>'+
							'<td>'+ d.products +'</td>'+
						'</tr>'+
						'<tr>'+
							'<td>Ordered by:</td>'+
							'<td>'+ d.ordered_by +'</td>'+
						'</tr>'+
						'<tr>'+
							'<td>Price:</td>'+
							'<td>'+ d.price +'</td>'+
						'</tr>'+
						'<tr>'+
							'<td>Is delivered:</td>'+
							'<td>'+ d.is_delivered +'</td>'+
						'</tr>'+
					'</table>';
		}


		var editor;

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
				idSrc: "id",
				fields: [ {
						label: "Owner Name:",
						name: "name"
					}, {
						label: "Service Address:",
						name: "service_address",
					}, {
						label:     "Active:",
						name:      "status",
						type:      "checkbox",
						separator: "|",
						ipOpts:    [{ 
							label: '', 
							value: 1 
						}]
					}]
			});

			editor.on( 'preSubmit', function ( e, o ) {
				if ( o.data.name === '' ) {
					this.error('name', 'A Name must be given');
					return false;
				} else if ( o.data.description === '' ) {
					this.error('description', 'A Description must be given');
					return false;
				} else if ( o.data.creation_date === '' ) {
					this.error('creation_date', 'A Date must be given');
					return false;
				}

			});

			var table = $('#example').DataTable({
				bAutoWidth:false,
				sDom: 'CT<"clear"><"H"lfr>t<"F"ip>',
				colVis: {
					exclude: [ 0 ]
				},
				ajax: "allitem",
				columns: [{
						class:'details-control',
						orderable:false,
						data:null,
						searchable:false,
						sWidth: '20px',
						defaultContent:''
					}, /*{ 
						data: "id"//"order_id" 
					}, { 
						data: "order_key"//"own_name" 
					}, */{ 
						data: "products"//"company_name" 
					}, /*{ 
						data: "quantity"//"submited_by" 
					},*/ { 
						data: "ordered_by"//"items"
					}, { 
						data: "price"//"cost", render: $.fn.dataTable.render.number( ',', '.', 0, '$' ) 
					}, { 
						data: "is_delivered"//"delivery_on"
					}, {
						targets: 0,
						data: "order_key",
						render: function ( data, type, full, meta ) {
							return '<a href="edit/'+data+'">Edit</a>';
						}
					}
				],
				order: [[1, 'asc']],
				tableTools: {
					sRowSelect: "os",
					sSwfPath: "{{ URL::asset('public/swf/copy_csv_xls_pdf.swf') }}",
					aButtons: [ { 
						sExtends: "editor_remove", editor: editor 
					}, {
						sExtends: "collection",
						sButtonText: "Save",
						sButtonClass: "save-collection",
						aButtons: [ 'copy', 'csv', 'xls', 'pdf' ]
					}, {
						"sExtends": "print",
						"sInfo": "Please press escape when done"
					}],
					sRowSelector: 'td:not(:last-child)' 
				},
				rowCallback: function ( row, data ) {
					$('input.editor-active', row).prop( 'checked', data.status == 1 );
				}
			});

			$('#example').on( 'change', 'input.editor-active', function () {
				editor
					.edit( $(this).closest('tr'), false )
					.set( 'status', $(this).prop( 'checked' ) ? 1 : 0 )
					.submit();
			});

			$('#example tbody').on('click', 'td.details-control', function() {
				var tr = $(this).parents('tr');
				var row = table.row(tr);
				if (row.child.isShown()) {
					row.child.hide();
					tr.removeClass('shown');
				} else {
					row.child(format(row.data())).show();
					tr.addClass('shown');
				}
			});

		} );

		$(document).ready(function() {
			var table = $('#example').DataTable();
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

			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
		<div class="col-xs-12 col-md-12">
		<h3>All Orders{{ HTML::decode(HTML::link("order/add","<i class='glyphicon glyphicon-plus'></i> Add Order",array('class'=>'btn btn-default pull-right'))) }}</h3>
		<br/>
		<table id="example" class="display" cellspacing="0" width="50%">
			<thead>
				<tr>
					<th></th>
					<!-- <th>Order Id</th> -->
					<!-- <th>Order Key</th> -->
					<th>Products(quantity)</th>
					<!-- <th>Quantity</th> -->
					<th>Ordered by(Agent/Shop)</th>
					<th>Price(Total)</th>
					<th>Is Delivered</th>
					<th>Action</th>
				</tr>
			</thead>

			<tfoot>
				<tr>
					<th></th>
					<!-- <th>Order Id</th> -->
					<!-- <th>Order Key</th> -->
					<th>Products(quantity)</th>
					<!-- <th>Quantity</th> -->
					<th>Ordered by(Agent/Shop)</th>
					<th>Price(Total)</th>
					<th>Is Delivered</th>
					<th>Action</th>
				</tr>
			</tfoot>
		</table>
		</div>
		</div>
	</div>
	<script type="text/javascript" language="javascript" src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
@stop