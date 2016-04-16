package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.shop.adapters.ProductListViewAdapter;
import com.example.anik.shop.helpers.AppCart;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.helpers.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SelectProductActivity extends ActionBarActivity {

    public final static int CLOSE_ON_RESULT = 100;
    View.OnClickListener nextIntentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AppCart.cartSize() == 0) {
                Toast.makeText(SelectProductActivity.this, "No product is added", Toast.LENGTH_SHORT).show();
                return;
            }
            SELECTED_ROW = -1;
            addPriceToCartProduct();
            Intent intent = new Intent(SelectProductActivity.this, CheckoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, CLOSE_ON_RESULT);
        }
    };


    private void addPriceToCartProduct() {
        for(Product productInCart : AppCart.getCart()){
            if(productInCart.getProductPrice() == null || productInCart.getProductPrice().isEmpty()){
                for(Product productInList : productList){
                    if(productInList.getProductUniqueId().equals(productInCart.getProductUniqueId())){
                        productInCart.setProductPrice(productInList.getProductPrice());
                    }
                }
            }
        }
    }

    ListView.OnItemClickListener productSelectionListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SELECTED_ROW =  position;
            adapter.notifyDataSetChanged();
            int listId = Integer.parseInt(((TextView) view.findViewById(R.id.productListIdInProductListView)).getText().toString(), 10);
            show_product(listId);
        }
    };
    View row = null;
    public static long SELECTED_ROW = -1;
    ListView listView;
    ProductListViewAdapter adapter;
    TextView selectedProductName, selectedProductDescription, selectedProductAttributes;
    EditText selectedProductQuantity;
    Button selectedProductAdd;
    Button btnGoToNextIntent;
    Product currentlyShowing;
    View.OnClickListener addToCartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int quantity = 0;
            try {
                quantity = Integer.parseInt(selectedProductQuantity.getText().toString(), 10);
            } catch (NumberFormatException ex) {}

            if (quantity == 0) {
                Toast.makeText(SelectProductActivity.this, "Quantity must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            AppHelper.toggleSoftKeyboard(selectedProductQuantity);
            currentlyShowing.setQuantity(quantity);
            AppCart.addToProductList(currentlyShowing);
            initializeRightPane();
            changeTopButtonText();
            SELECTED_ROW = -1;
            adapter.notifyDataSetChanged();
        }
    };
    String allProducts = "";
    List<Product> productList = new ArrayList<>();
    String next = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CLOSE_ON_RESULT) {
            SelectProductActivity.this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);

        if (statusCode >= 400) {
            Toast.makeText(SelectProductActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (intent.hasExtra("response")) {
            allProducts = intent.getStringExtra("response");
            AppStorage.setOrderAbleProductList(allProducts);
        } else {
            allProducts = AppStorage.getOrderAbleProductList();
        }

        next = AppStorage.getNextIntent();
        setTitle(String.format("%s", Character.toString(next.charAt(0)).toUpperCase() + next.substring(1)));

        btnGoToNextIntent = (Button) findViewById(R.id.btnGoToNextIntent);
        changeTopButtonText();
        btnGoToNextIntent.setOnClickListener(nextIntentListener);

        process_all_products();
        listView = (ListView) findViewById(R.id.listViewForProductList);
        adapter = new ProductListViewAdapter(SelectProductActivity.this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(productSelectionListener);

        selectedProductName = (TextView) findViewById(R.id.selectedProductName);
        selectedProductDescription = (TextView) findViewById(R.id.selectedProductDescription);
        selectedProductAttributes = (TextView) findViewById(R.id.selectedProductAttributes);
        selectedProductQuantity = (EditText) findViewById(R.id.selectedProductQuantity);
        selectedProductAdd = (Button) findViewById(R.id.selectedProductAdd);
        selectedProductAdd.setOnClickListener(addToCartListener);
        initializeRightPane();

    }

    private void initializeRightPane() {
        selectedProductName.setText("");
        selectedProductDescription.setText("");
        selectedProductAttributes.setText("");
        selectedProductQuantity.setVisibility(View.GONE);
        selectedProductAdd.setVisibility(View.GONE);
    }

    private void process_all_products() {
        try {
            JSONObject object = new JSONObject(allProducts);
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                productList.add(Product.buildProductFromObject(data.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void show_product(int id) {
        Product product = productList.get(id);
        if (AppCart.productExists(product)) {
            Toast.makeText(SelectProductActivity.this, "Product is already in the cart", Toast.LENGTH_SHORT).show();
        }
        currentlyShowing = product;
        selectedProductName.setText(product.getName());
        selectedProductDescription.setText(product.getDescription());
        selectedProductAttributes.setText(product.getAttributes());
        selectedProductQuantity.setText("");
        selectedProductQuantity.setVisibility(View.VISIBLE);
        selectedProductAdd.setVisibility(View.VISIBLE);

    }

    private void changeTopButtonText() {
        btnGoToNextIntent.setText(String.format("Place %s [%d]", next, AppCart.cartSize()));
    }

}
