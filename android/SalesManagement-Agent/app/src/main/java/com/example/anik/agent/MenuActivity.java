package com.example.anik.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.anik.agent.helpers.AppCart;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.httpService.HttpService;
import com.example.anik.agent.services.AppServices;

import java.io.File;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    boolean continueAsUsual = false;
    Button btnProducts, btnPlaceOrder, btnPlaceBill, btnComplaints, btnBonus, btnOrderDetails, btnBillDetails, btnAccountDetails, btnOffers, btnNews, btnQuickOrder, btnActivityLog;
    File filesDir = null;
    View.OnClickListener productButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MenuActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            HttpService httpService = new HttpService(MenuActivity.this);
            httpService.onUrl(AppConstant.LINK_CATEGORIES)
                    .withMethod(HttpService.HTTP_GET)
                    .nextIntent(CategoryListActivity.class)
                    .execute();
        }
    };

    View.OnClickListener orderPlacementButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MenuActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            if (MenuActivity.this.isPropertyEmpty(AppCart.getCart())) {
                AppStorage.setNextIntent("order");
                HttpService httpService = new HttpService(MenuActivity.this);
                httpService.onUrl(AppConstant.LINK_SHOP, AppStorage.getUserMobileNumber())
                        .withMethod(HttpService.HTTP_GET)
                        .nextIntent(ShopActivity.class)
                        .execute();
            } else {
                String title = String.format("%s was selected for %s", AppCart.getShop().getCompanyName(), AppStorage.getNextIntent());
                String body = String.format("%d products are already listed.\nKeeping it will proceed to order placement.", AppCart.getCart().size());
                MenuActivity.this.showAlert(title, body, "order");
            }
        }
    };

    View.OnClickListener orderDetailsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MenuActivity.this.nextIntent(OrderListActivity.class);
        }
    };

    View.OnClickListener billingButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MenuActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }

            if (MenuActivity.this.isPropertyEmpty(AppCart.getCart())) {
                AppStorage.setNextIntent("bill");
                HttpService httpService = new HttpService(MenuActivity.this);
                httpService.onUrl(AppConstant.LINK_SHOP, AppStorage.getUserMobileNumber())
                        .withMethod(HttpService.HTTP_GET)
                        .nextIntent(ShopActivity.class)
                        .execute();
            } else {
                String title = String.format("%s was selected for %s", AppCart.getShop().getCompanyName(), AppStorage.getNextIntent());
                String body = String.format("%d products are already listed.\nKeeping it will proceed to bills.", AppCart.getCart().size());
                MenuActivity.this.showAlert(title, body, "bill");
            }
        }
    };

    View.OnClickListener billDetailsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nextIntent(BillListActivity.class);
        }
    };

    View.OnClickListener complaintsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MenuActivity.this.nextIntent(ComplaintActivity.class);
        }
    };

    View.OnClickListener accountDetailsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MenuActivity.this.nextIntent(AccountDetailsActivity.class);
        }
    };

    View.OnClickListener bonusButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MenuActivity.this.nextIntent(BonusActivity.class);
        }
    };

    View.OnClickListener offersButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!MenuActivity.this.isPropertyEmpty(AppStorage.getOffers())) {
                MenuActivity.this.nextIntent(OfferActivity.class);
                return;
            }
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MenuActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            HttpService httpService = new HttpService(MenuActivity.this);
            httpService.onUrl(AppConstant.LINK_OFFERS)
                    .withMethod(HttpService.HTTP_GET)
                    .nextIntent(OfferActivity.class)
                    .execute();
        }
    };

    View.OnClickListener newsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!MenuActivity.this.isPropertyEmpty(AppStorage.getNews())) {
                MenuActivity.this.nextIntent(NewsActivity.class);
                return;
            }
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MenuActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            HttpService httpService = new HttpService(MenuActivity.this);
            httpService.onUrl(AppConstant.LINK_NEWS)
                    .withMethod(HttpService.HTTP_GET)
                    .nextIntent(NewsActivity.class)
                    .execute();
        }
    };

    View.OnClickListener quickOrderButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MenuActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }

            if (MenuActivity.this.isPropertyEmpty(AppCart.getCart())) {
                AppStorage.setNextIntent("quick order");
                HttpService httpService = new HttpService(MenuActivity.this);
                httpService.onUrl(AppConstant.LINK_SHOP, AppStorage.getUserMobileNumber())
                        .withMethod(HttpService.HTTP_GET)
                        .nextIntent(ShopActivity.class)
                        .execute();
            } else {
                String title = String.format("%s was selected for %s", AppCart.getShop().getCompanyName(), AppStorage.getNextIntent());
                String body = String.format("%d products are already listed.\nKeeping it will proceed to quick order.", AppCart.getCart().size());
                MenuActivity.this.showAlert(title, body, "quick order");
            }
        }
    };

    View.OnClickListener activityLogButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nextIntent(ActivityLogActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        filesDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), AppConstant.APP_STORAGE_FOLDER);

        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        AppStorage.setFilesDirectory(filesDir.getPath());

        btnProducts = (Button) findViewById(R.id.buttonProducts);
        btnPlaceOrder = (Button) findViewById(R.id.buttonPlaceOrder);
        btnOrderDetails = (Button) findViewById(R.id.buttonOrderDetails);
        btnPlaceBill = (Button) findViewById(R.id.buttonPlaceBill);
        btnBillDetails = (Button) findViewById(R.id.buttonBillDetails);
        btnComplaints = (Button) findViewById(R.id.buttonSubmitComplaint);
        btnAccountDetails = (Button) findViewById(R.id.buttonAccountDetails);
        btnBonus = (Button) findViewById(R.id.buttonBonus);
        btnOffers = (Button) findViewById(R.id.buttonOffers);
        btnNews = (Button) findViewById(R.id.buttonNews);
        btnQuickOrder = (Button) findViewById(R.id.buttonQuickOrder);
        btnActivityLog = (Button) findViewById(R.id.buttonActivityLog);

        btnProducts.setOnClickListener(productButtonClickListener);
        btnPlaceOrder.setOnClickListener(orderPlacementButtonClickListener);
        btnOrderDetails.setOnClickListener(orderDetailsButtonClickListener);
        btnPlaceBill.setOnClickListener(billingButtonClickListener);
        btnBillDetails.setOnClickListener(billDetailsButtonClickListener);
        btnComplaints.setOnClickListener(complaintsButtonClickListener);
        btnAccountDetails.setOnClickListener(accountDetailsButtonClickListener);
        btnBonus.setOnClickListener(bonusButtonClickListener);
        btnOffers.setOnClickListener(offersButtonClickListener);
        btnNews.setOnClickListener(newsButtonClickListener);
        btnQuickOrder.setOnClickListener(quickOrderButtonClickListener);
        btnActivityLog.setOnClickListener(activityLogButtonClickListener);

        Intent intent = new Intent(MenuActivity.this, AppServices.class);
        startService(intent);

    }

    private void nextIntent(Class nextIntentClass) {
        Intent intent = new Intent(MenuActivity.this, nextIntentClass);
        startActivity(intent);
    }

    private boolean showAlert(String title, String body, final String nextIntentExtra) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuActivity.this);
        AppStorage.setNextIntent(nextIntentExtra);
        alertDialog.setTitle(title);
        alertDialog.setMessage(body);
        alertDialog.setPositiveButton("Keep it", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Class nextClass = null;
                if (AppStorage.getNextIntent().equals("quick order")) {
                    nextClass = QuickOrderActivity.class;
                } else {
                    nextClass = SelectProductActivity.class;
                }

                nextIntent(nextClass);
            }
        });
        alertDialog.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AppCart.destroy();
                HttpService httpService = new HttpService(MenuActivity.this);
                httpService.onUrl(AppConstant.LINK_SHOP, AppStorage.getUserMobileNumber())
                        .withMethod(HttpService.HTTP_GET)
                        .nextIntent(ShopActivity.class)
                        .execute();
            }
        });
        alertDialog.show();
        return continueAsUsual;
    }

    private boolean isPropertyEmpty(String property) {
        if (property.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isPropertyEmpty(List property) {
        if (property.isEmpty()) {
            return true;
        }
        return false;
    }

}
