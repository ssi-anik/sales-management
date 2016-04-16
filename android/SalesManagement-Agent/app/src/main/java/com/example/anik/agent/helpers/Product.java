package com.example.anik.agent.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 11-Aug-15, 011.
 */
public class Product implements Serializable, Cloneable {
    private List<String> productImageUrls = new ArrayList<>();
    private List<Tax> taxes = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private String id;
    private String name;
    private String description;
    private String attributes;
    private String product_unique_id;
    private String delivery_time;
    private String product_brand;
    private String product_class;
    private String product_guarantee;
    private String product_price = "";
    private String totalTax;
    private int quantity = 0;

    public Product(String id, String name, List<String> images) {
        this.id = id;
        this.name = name;
        this.productImageUrls = images;
    }

    private Product(List<String> links, List<String> productImageUrls, List<Tax> taxes, String... attributes) {
        this.links.addAll(links);
        this.productImageUrls.addAll(productImageUrls);
        this.taxes.addAll(taxes);
        this.id = attributes[0];
        this.name = attributes[1];
        this.description = attributes[2];
        this.attributes = attributes[3];
        this.product_unique_id = attributes[4];
        this.delivery_time = attributes[5];
        this.product_brand = attributes[6];
        this.product_class = attributes[7];
        this.product_guarantee = attributes[8];
    }

    public static Product buildProduct(String productDetails) {
        String id, name, description, attributes, product_unique_id, delivery_time, product_brand, product_class, product_guarantee;
        List<String> images, links;
        List<Tax> taxes;
        try {
            JSONObject jsonObject = new JSONObject(productDetails);
            JSONObject data = jsonObject.getJSONObject("data");
            id = data.getString("id");
            name = data.getString("name");
            description = data.getString("description");
            attributes = data.getString("attributes");
            product_unique_id = data.getString("product_unique_id");
            delivery_time = data.getString("delivery_time");
            product_brand = data.getString("product_brand");
            product_class = data.getString("product_class");
            product_guarantee = data.getString("product_guarantee");

            JSONArray product_images = data.getJSONArray("images");
            images = new ArrayList<>();
            for (int i = 0; i < product_images.length(); ++i) {
                JSONObject image = product_images.getJSONObject(i);
                images.add(image.getString("image_link"));
            }

            JSONArray product_taxes = data.getJSONArray("taxes");
            taxes = new ArrayList<>();
            for (int i = 0; i < product_taxes.length(); ++i) {
                JSONObject tax = product_taxes.getJSONObject(i);
                JSONObject percentage = tax.getJSONObject("percentage");
                String tax_name = percentage.getString("name");
                String tax_percentage = percentage.getString("tax_percentage");
                taxes.add(new Tax(tax_name, tax_percentage));
            }

            JSONArray product_links = data.getJSONArray("links");
            links = new ArrayList<>();
            for (int i = 0; i < product_links.length(); ++i) {
                JSONObject link = product_links.getJSONObject(i);
                links.add(link.getString("link"));
            }
            return new Product(links, images, taxes, id, name, description, attributes, product_unique_id, delivery_time, product_brand, product_class, product_guarantee);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Product buildProductFromObject(String productDetails) {
        String id, name, description, attributes, product_unique_id, delivery_time, product_brand, product_class, product_guarantee;
        List<String> images = new ArrayList<>(),
                links = new ArrayList<>();
        List<Tax> taxes;
        try {
            JSONObject data = new JSONObject(productDetails);
            id = data.getString("id");
            name = data.getString("name");
            description = data.getString("description");
            attributes = data.getString("attributes");
            product_unique_id = data.getString("product_unique_id");
            delivery_time = data.getString("delivery_time");
            product_brand = data.getString("product_brand");
            product_class = data.getString("product_class");
            product_guarantee = data.getString("product_guarantee");
            String productPrice = "0.0";
            if (data.has("price_class_a")) {
                productPrice = data.getString("price_class_a");
            } else if (data.has("price_class_b")) {
                productPrice = data.getString("price_class_b");
            } else if (data.has("price_class_c")) {
                productPrice = data.getString("price_class_c");
            } else if (data.has("price_class_d")) {
                productPrice = data.getString("price_class_d");
            }

            JSONArray product_taxes = data.getJSONArray("taxes");
            taxes = new ArrayList<>();
            for (int i = 0; i < product_taxes.length(); ++i) {
                JSONObject tax = product_taxes.getJSONObject(i);
                JSONObject percentage = tax.getJSONObject("percentage");
                String tax_name = percentage.getString("name");
                String tax_percentage = percentage.getString("tax_percentage");
                taxes.add(new Tax(tax_name, tax_percentage));
            }
            Product product = new Product(links, images, taxes, id, name, description, attributes, product_unique_id, delivery_time, product_brand, product_class, product_guarantee);
            product.setProductPrice(productPrice);
            return product;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getProductUniqueId() {
        return product_unique_id;
    }

    public String getDeliveryTime() {
        return delivery_time;
    }

    public String getProductBrand() {
        return product_brand;
    }

    public String getProductClass() {
        return product_class;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = String.format("%.2f", totalTax);
    }

    public String getProductGuarantee() {
        return product_guarantee;
    }

    public String getProductPrice() {
        return product_price;
    }

    public void setProductPrice(String product_price) {
        this.product_price = product_price;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getProductImageUrls() {
        return productImageUrls;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public String getFirstImageUrl() {
        return productImageUrls.get(0);
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotalPriceWithTax() {
        double total_tax = 0.0;
        for (Tax tax : this.getTaxes()) {
            total_tax += Double.parseDouble(tax.getPercentage());
        }
        this.setTotalTax(total_tax);
        double product_price = Double.parseDouble(this.getProductPrice());
        double total_price = product_price + ((product_price * total_tax) / 100);
        return String.format("%.2f", total_price * this.getQuantity());
    }

    public String getTotalPriceWithoutTax() {
        return String.format("%.2f", this.getQuantity() * Double.parseDouble(this.getProductPrice()));
    }

    @Override
    public boolean equals(Object object) {
        if (null == object)
            return false;
        return (object instanceof Product) && ((Product) object).getId().equals(this.id);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
