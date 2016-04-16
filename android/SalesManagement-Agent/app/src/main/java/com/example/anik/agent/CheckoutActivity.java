package com.example.anik.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.adapters.CheckoutListViewAdapter;
import com.example.anik.agent.database.ActivityLog;
import com.example.anik.agent.database.Bill;
import com.example.anik.agent.database.Order;
import com.example.anik.agent.database.Quick;
import com.example.anik.agent.helpers.AppCart;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckoutActivity extends ActionBarActivity {

    View.OnClickListener checkoutButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String randomUniqueId = AppHelper.randomUniqueNumber();
            if (next.equals("order")) {
                boolean causedError = false;
                for (Product product : AppCart.getCart()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("order_for", AppCart.getShop().getMobileNumber());
                    row.put("company_name", AppCart.getShop().getCompanyName());
                    row.put("product_id", product.getId());
                    row.put("product_name", product.getName());
                    row.put("quantity", String.valueOf(product.getQuantity()));
                    row.put("price", product.getProductPrice());
                    row.put("total_price", product.getTotalPriceWithTax());
                    row.put("batch", randomUniqueId);
                    Order order = new Order(CheckoutActivity.this);
                    boolean isOrderInserted = order.insert(row);
                    if (!isOrderInserted && !causedError) {
                        causedError = true;
                    } else if (isOrderInserted) {
                        row.remove("batch");
                        row.put("is_submitted", "1");
                        row.put("is_ready", "0");
                        row.put("bill_key", randomUniqueId);
                        Bill bill = new Bill(CheckoutActivity.this);
                        boolean isBillInserted = bill.insert(row);
                        if (!isBillInserted) {
                            Log.v(AppConstant.TAG, "Bill is not inserted");
                        }
                    }
                }
                new ActivityLog(CheckoutActivity.this).insert(randomUniqueId, "order");
                if (causedError)
                    Toast.makeText(CheckoutActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(CheckoutActivity.this, "Order is placed successfully", Toast.LENGTH_SHORT).show();
                    //AppHelper.sendBroadcast(AppConstant.BROADCAST_ORDER);
                }
            } else if (next.equals("bill")) {
                boolean causedError = false;
                for (Product product : AppCart.getCart()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("order_for", AppCart.getShop().getMobileNumber());
                    row.put("company_name", AppCart.getShop().getCompanyName());
                    row.put("product_id", product.getId());
                    row.put("product_name", product.getName());
                    row.put("quantity", String.valueOf(product.getQuantity()));
                    row.put("price", product.getProductPrice());
                    row.put("total_price", product.getTotalPriceWithTax());
                    row.put("bill_key", randomUniqueId);
                    Bill order = new Bill(CheckoutActivity.this);
                    if (!order.insert(row) && !causedError)
                        causedError = true;
                }
                new ActivityLog(CheckoutActivity.this).insert(randomUniqueId, "bill");
                if (causedError) {
                    Toast.makeText(CheckoutActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckoutActivity.this, "Bill is placed successfully", Toast.LENGTH_SHORT).show();
                    //AppHelper.sendBroadcast(AppConstant.BROADCAST_BILL);
                }
            } else if (next.equals("quick order")) {
                boolean causedError = false;
                for (Product product : AppCart.getCart()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("order_for", AppCart.getShop().getMobileNumber());
                    row.put("company_name", AppCart.getShop().getCompanyName());
                    row.put("product_id", product.getId());
                    row.put("product_name", product.getName());
                    row.put("quantity", String.valueOf(product.getQuantity()));
                    row.put("price", product.getProductPrice());
                    row.put("total_price", product.getTotalPriceWithTax());
                    row.put("batch", randomUniqueId);
                    Quick quick = new Quick(CheckoutActivity.this);
                    boolean isOrderInserted = quick.insert(row);
                    if (!isOrderInserted && !causedError) {
                        causedError = true;
                    }
                }
                if (causedError)
                    Toast.makeText(CheckoutActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                else {
                    //AppHelper.sendBroadcast(AppConstant.BROADCAST_QUICK_ORDER);
                    Toast.makeText(CheckoutActivity.this, "Quick Order is placed successfully", Toast.LENGTH_SHORT).show();
                }
            }
            AppCart.destroy();
            finish();
        }
    };

    String next = "";
    ListView listView;
    CheckoutListViewAdapter adapter;
    Button btnCheckout;
    List<Product> productsInCart = new ArrayList<>();
    ListView.OnItemLongClickListener listItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            String productUniqueId = ((TextView) view.findViewById(R.id.productUniqueIdForCheckout)).getText().toString();
            String productName = ((TextView) view.findViewById(R.id.productNameForCheckout)).getText().toString();
            showAlert(productName, productUniqueId);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        next = AppStorage.getNextIntent();
        setTitle(String.format("Checkout %s: %s", next, AppCart.getShop().getCompanyName()));

        TextView shopName = (TextView) findViewById(R.id.shopNameForCheckout);
        shopName.setText(String.format("%s", AppCart.getShop().getCompanyName()));
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        listView = (ListView) findViewById(R.id.listViewForCheckout);

        productsInCart.addAll(AppCart.getCart());
        //adapter = new CheckoutListViewAdapter(CheckoutActivity.this, productsInCart);
        //adapter = new CheckoutListViewAdapter(CheckoutActivity.this, AppCart.getCart());
        adapter = new CheckoutListViewAdapter(CheckoutActivity.this);
        listView.setAdapter(adapter);
        listView.setLongClickable(true);

        btnCheckout.setOnClickListener(checkoutButtonClickListener);

        listView.setOnItemLongClickListener(listItemLongClickListener);
    }

    private void showAlert(String productName, final String productUniqueId) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CheckoutActivity.this);
        alertDialog.setTitle("Are you sure to delete " + productName + "?");
        alertDialog.setMessage("Clicking yes will delete this product");
        alertDialog.setPositiveButton("Keep it", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Product removed = AppCart.removeFromProductCart(productUniqueId);
                if (null != removed) {
                    //productsInCart.remove(removed);
                    adapter.notifyDataSetChanged();
                }
                if (AppCart.cartSize() == 0) {
                    btnCheckout.setEnabled(false);
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void finish() {
        setResult(SelectProductActivity.CLOSE_ON_RESULT);
        super.finish();
    }
}
