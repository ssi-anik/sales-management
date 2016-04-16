package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anik.agent.adapters.QuickOrderSelectedProductListViewAdapter;
import com.example.anik.agent.helpers.AppCart;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class QuickOrderActivity extends ActionBarActivity {

    public final static int CLOSE_ON_RESULT = 100;
    View.OnClickListener nextIntentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AppCart.cartSize() == 0) {
                Toast.makeText(QuickOrderActivity.this, "No product is added", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(QuickOrderActivity.this, CheckoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, CLOSE_ON_RESULT);
        }
    };
    Button btnGoToNextIntent = null;
    Button btnAddProductToCart = null;
    EditText productUniqueIdQuickOrder = null;
    EditText productQuantityForQuickOrder = null;
    String next = "";
    String allProducts = "";
    List<Product> productList = new ArrayList<>();
    View.OnClickListener btnAddProductToCartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppHelper.toggleSoftKeyboard(productUniqueIdQuickOrder);
            String productUniqueId = productUniqueIdQuickOrder.getText().toString().trim();
            String productQuantity = productQuantityForQuickOrder.getText().toString().trim();
            int quantity = 0;
            try {
                quantity = Integer.parseInt(productQuantity, 10);
            } catch (NumberFormatException ex) {

            }
            if (productUniqueId.isEmpty() || quantity == 0) {
                Toast.makeText(QuickOrderActivity.this, "Both product id and quantity are required", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isProductIdValid = false;
            Product productSelected = null;
            for (Product availableProduct : productList) {
                if (availableProduct.getProductUniqueId().equals(productUniqueId)) {
                    isProductIdValid = true;
                    try {
                        productSelected = (Product) availableProduct.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        productSelected = null;
                    }
                    break;
                }
            }
            if (!isProductIdValid) {
                Toast.makeText(QuickOrderActivity.this, "Not a valid product id", Toast.LENGTH_SHORT).show();
                return;
            }

            /*for(Product productListed : AppCart.getCart()){
                if(productListed.getProductUniqueId().equals(productUniqueId)){
                    Toast.makeText(QuickOrderActivity.this, "Product is already added", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    return;
                }
            }*/

            if (null != productSelected) {
                productSelected.setQuantity(quantity);
                AppCart.addToProductList(productSelected);
                adapter.notifyDataSetChanged();
                changeTopButtonText();
                clearInputFields();
                return;
            }


        }
    };
    List<Product> selectedProductList = new ArrayList<>();
    ListView listView;
    QuickOrderSelectedProductListViewAdapter adapter;

    private void clearInputFields() {
        productUniqueIdQuickOrder.setText("");
        productQuantityForQuickOrder.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CLOSE_ON_RESULT) {
            QuickOrderActivity.this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_order);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (AppCart.getShop() == null) {
            finish();
            return;
        }

        if (statusCode >= 400) {
            Toast.makeText(QuickOrderActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (intent.hasExtra("response")) {
            allProducts = intent.getStringExtra("response");
            AppStorage.setAllProducts(allProducts);
        } else {
            allProducts = AppStorage.getAllProducts();
        }

        next = AppStorage.getNextIntent();

        productUniqueIdQuickOrder = (EditText) findViewById(R.id.productUniqueIdQuickOrder);
        productQuantityForQuickOrder = (EditText) findViewById(R.id.productQuantityForQuickOrder);

        btnGoToNextIntent = (Button) findViewById(R.id.buttonForQuickOrderSubmission);
        btnAddProductToCart = (Button) findViewById(R.id.buttonAddProductForQuickOrder);

        btnGoToNextIntent.setOnClickListener(nextIntentListener);
        btnAddProductToCart.setOnClickListener(btnAddProductToCartListener);

        changeTopButtonText();
        process_all_products();

        listView = (ListView) findViewById(R.id.listViewForQuickOrderOrderList);
        adapter = new QuickOrderSelectedProductListViewAdapter(QuickOrderActivity.this, AppCart.getCart());
        listView.setAdapter(adapter);

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

    private void changeTopButtonText() {
        btnGoToNextIntent.setText(String.format("Place quick order [%d]", AppCart.cartSize()));
    }

}
