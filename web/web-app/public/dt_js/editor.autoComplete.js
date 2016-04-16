/**
 * AutoComplete is a very useful way of providing input guidance to the end
 * user, providing the easy of selection of a `<select>` list with the
 * flexibility of a free form text input. This plug-in provides integration
 * between [jQuery UI's AutoComplete](http://jqueryui.com/) control and Editor,
 * adding the `autoComplete` field type to Editor.
 *
 * @name jQuery UI AutoComplete
 * @summary Use jQuery UI's AutoComplete library with Editor to allow easy and
 *     accurate input of data.
 * @requires jQuery UI's AutoComplete library
 * @depcss http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css
 * @depjs http://code.jquery.com/ui/1.10.0/jquery-ui.js
 * 
 * @opt `e-type object` **`opts`**: jQuery UI AutoComplete initialisation
 *     options object. Please refer to the jQuery UI documentation for the full
 *     range of options available.
 *
 * @method **`node`**: Get the input element as a jQuery object that is used for
 *     the AutoComplete so you can run custom actions on it, including
 *     `autocomplete` methods. This is useful if you wish to update the data
 *     that is available to the AutoComplete control.
 *
 * @example
 *     
 * new $.fn.dataTable.Editor( {
 *   "ajax": "php/dates.php",
 *   "table": "#example",
 *   "fields": [ {
 *       "label": "Genres:",
 *       "name": "genre",
 *       "type": "autoComplete",
 *       "opts": {
 *         "source": [
 *           // array of genres...
 *         ]
 *       }
 *     }, 
 *     // additional fields...
 *   ]
 * } );
 */

$.fn.dataTable.Editor.fieldTypes.autoComplete = {
	"create": function ( conf ) {
		conf._input = $('<input type="text" id="'+conf.id+'">')
			.autocomplete( conf.opts || {} );

		return conf._input[0];
	},

	"get": function ( conf ) {
		return conf._input.val();
	},

	"set": function ( conf, val ) {
		conf._input.val( val );
	},

	"enable": function ( conf ) {
		conf._input.autocomplete( 'enable' );
	},

	"disable": function ( conf ) {
		conf._input.autocomplete( 'disable' );
	},

	// Non-standard Editor method - custom to this plug-in
	"node": function ( conf ) {
		return conf._input;
	}
};
