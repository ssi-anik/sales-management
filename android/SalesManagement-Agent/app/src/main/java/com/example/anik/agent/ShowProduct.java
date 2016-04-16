package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppNotification;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Product;
import com.example.anik.agent.helpers.Tax;
import com.example.anik.agent.httpService.HttpService;
import com.example.anik.agent.httpService.IHttpResponseListener;
import com.example.anik.agent.httpService.ImageDownloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowProduct extends ActionBarActivity {

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(ShowProduct.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            String product_id = product.getId();
            HttpService httpService = new HttpService(ShowProduct.this);
            httpService.onUrl(String.format("%s/%s/%s", AppConstant.LINK_ASK, AppStorage.getUserMobileNumber(), product_id))
                    .withMethod(HttpService.HTTP_GET)
                    .registerResponse(new IHttpResponseListener() {
                        @Override
                        public void onRemoteCallComplete(int statusCode, String json) {
                            if (statusCode == 200) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String price = object.getString("data");
                                    product.setProductPrice(price);
                                    Map<String, String> information = new HashMap<String, String>();
                                    information.put("price", price);
                                    AppNotification.build(ShowProduct.this, AppNotification.ASK_LATEST_PRICE_NOTIFICATION)
                                            .setTitle("Latest price is received")
                                            .setBody("Latest Price is Tk. " + price)
                                            .setExtraInformation(information)
                                            .send();
                                    tvProductPrice.setText(Html.fromHtml(String.format("Price (Tk.): <u>%s</u>", price)));
                                    tvProductPrice.setVisibility(View.VISIBLE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(ShowProduct.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute();
            btnAskLatestPrice.setEnabled(false);
        }
    };
    private String productDetails = "";
    private String fromActivity = "";
    private Product product;
    View.OnClickListener imageViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShowProduct.this, FullScreenImageViewActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        }
    };
    private String price = "";
    private Intent intent = null;
    private TextView tvProductName, tvProductDescription, tvProductAttributes, tvProductDeliveryTime, tvProductBrand, tvProductClass, tvProductGuarantee, tvProductTaxes, tvProductLinks, tvProductPrice;
    private ImageView productImage;
    private Button btnAskLatestPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(ShowProduct.this, AppConstant.MESSAGE_DATA_PARSE_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
        if (intent.hasExtra("response")) {
            productDetails = intent.getStringExtra("response");
            AppStorage.setProducts(productDetails);
        } else {
            productDetails = AppStorage.getProducts();
        }

        if (intent.hasExtra("fromActivity")) {
            fromActivity = intent.getStringExtra("fromActivity");
            AppStorage.setCurrentActivity(fromActivity);
        } else {
            fromActivity = AppStorage.getCurrentActivity();
        }

        if (productDetails.isEmpty() || fromActivity.isEmpty()) {
            Toast.makeText(ShowProduct.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
        prepare_response();
        show_product();

    }

    private void prepare_response() {
        product = Product.buildProduct(productDetails);
        if (null == product) {
            Toast.makeText(ShowProduct.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void show_product() {
        setTitle(product.getName());
        tvProductName = (TextView) findViewById(R.id.productName);
        tvProductDescription = (TextView) findViewById(R.id.productDescription);
        tvProductAttributes = (TextView) findViewById(R.id.productAttributes);
        tvProductDeliveryTime = (TextView) findViewById(R.id.productDeliverTime);
        tvProductBrand = (TextView) findViewById(R.id.productBrand);
        tvProductClass = (TextView) findViewById(R.id.productClass);
        tvProductGuarantee = (TextView) findViewById(R.id.productGuarantee);
        tvProductTaxes = (TextView) findViewById(R.id.productTaxes);
        tvProductLinks = (TextView) findViewById(R.id.productLinks);
        tvProductPrice = (TextView) findViewById(R.id.productPrice);

        btnAskLatestPrice = (Button) findViewById(R.id.btnAskLatestPrice);
        btnAskLatestPrice.setOnClickListener(btnClickListener);

        productImage = (ImageView) findViewById(R.id.productImage);
        productImage.setOnClickListener(imageViewClickListener);

        String productName = String.format("Name: %s", product.getName());
        tvProductName.setText(productName);

        String productDescription = String.format("Description: %s", product.getDescription());
        tvProductDescription.setText(productDescription);

        String productAttributes = String.format("Attributes: %s", product.getAttributes());
        tvProductAttributes.setText(productAttributes);

        String productDeliveryTime = String.format("Delivery Time: %s", product.getDeliveryTime());
        tvProductDeliveryTime.setText(productDeliveryTime);

        String productBrand = String.format("Brand: %s", product.getProductBrand());
        tvProductBrand.setText(productBrand);

        String productClass = String.format("Product class: %s", product.getProductClass());
        tvProductClass.setText(productClass);

        String productGuarantee = String.format("Guarantee: %s", product.getProductGuarantee());
        tvProductGuarantee.setText(productGuarantee);


        if (intent.hasExtra("price")) {
            price = intent.getStringExtra("price");
            btnAskLatestPrice.setEnabled(false);
            tvProductPrice.setText(Html.fromHtml(String.format("Price (Tk.): <u>%s</u>", price)));
            tvProductPrice.setVisibility(View.VISIBLE);
        }

        show_product_taxes(tvProductTaxes);
        show_product_links(tvProductLinks);
        show_product_image(productImage);
        download_product_images();
    }

    private void download_product_images() {
        List<String> images = product.getProductImageUrls();
        for (String image : images) {
            ImageDownloader.pushJob(image).execute();
        }
    }

    private void show_product_image(ImageView imageView) {
        String productImageUrl = product.getFirstImageUrl();
        final String image_name = productImageUrl.substring(productImageUrl.lastIndexOf("/") + 1);

        File file = new File(AppStorage.getFilesDirectory(), image_name);
        if (file.exists()) {
            AppHelper.setImageToImageView(imageView, file);
        } else {
            ImageDownloader.pushJob(productImageUrl, imageView).execute();
        }
    }

    private void show_product_links(TextView textView) {
        List<String> links = product.getLinks();
        String link_text = "Links: ";
        if (links.size() > 0) {
            link_text += "<br/>";
            int i = 1;
            for (String link : links) {
                link_text += String.format("&nbsp;&nbsp;&nbsp;&nbsp;%d. <a href=\"%s\"> %s </a><br/><br/>", i++, link, link);
            }
            textView.setText(Html.fromHtml(link_text));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            link_text += "No link is available";
            textView.setText(link_text);
        }
    }

    private void show_product_taxes(TextView textView) {
        List<Tax> taxes = product.getTaxes();
        String tax_string = "Taxes: \n";
        int i = 1;
        for (Tax tax : taxes) {
            tax_string += String.format("    %d. %s ( %s%% )\n", i++, tax.getName(), tax.getPercentage());
        }
        textView.setText(tax_string);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = null;
        if (fromActivity.equals("SubCategoryListActivity")) {
            intent = new Intent(ShowProduct.this, SubCategoryListActivity.class);
        } else if (fromActivity.equals("ProductListActivity")) {
            intent = new Intent(ShowProduct.this, ProductListActivity.class);
        } else {
            Toast.makeText(ShowProduct.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
        finish();
    }
}
