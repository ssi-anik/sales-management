package com.example.anik.agent.helpers;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anik on 05-Aug-15, 005.
 */
public class AppStorage extends Application {

    private static String userMobileNumber = "";
    private static String categories = "";
    private static String subCategories = "";
    private static String filesDirectory = "";
    private static String products = "";
    private static String productDetails = "";
    private static String currentActivity = "";
    private static String news = "";
    private static String offers = "";
    private static String allProducts = "";
    private static String nextIntent = "";
    private static String shopsJson = "";
    private static AppStorage instance;

    public static String getShopsJson() {
        return shopsJson;
    }

    public static void setShopsJson(String shopsJson) {
        AppStorage.shopsJson = shopsJson;
    }

    public static String getNextIntent() {
        return nextIntent;
    }

    public static void setNextIntent(String nextIntent) {
        AppStorage.nextIntent = nextIntent;
    }

    public static AppStorage getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public static String getUserMobileNumber() {
        return AppStorage.userMobileNumber;
    }

    public static void setUserMobileNumber(String mobileNumber) {
        AppStorage.userMobileNumber = mobileNumber;
    }

    public static String getFilesDirectory() {
        return filesDirectory;
    }

    public static void setFilesDirectory(String cachedDir) {
        AppStorage.filesDirectory = cachedDir;
    }

    public static String getCategories() {
        return categories;
    }

    public static void setCategories(String response) {
        categories = response;
    }

    public static String getSubCategories() {
        return subCategories;
    }

    public static void setSubCategories(String response) {
        subCategories = response;
    }

    public static String getProducts() {
        return products;
    }

    public static void setProducts(String response) {
        products = response;
    }

    public static String getProductDetails() {
        return productDetails;
    }

    public static void setProductDetails(String response) {
        productDetails = response;
    }

    public static String getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(String activity) {
        currentActivity = activity;
    }

    public static String getNews() {
        return news;
    }

    public static void setNews(String response) {
        news = response;
    }

    public static String getOffers() {
        return offers;
    }

    public static void setOffers(String offers) {
        AppStorage.offers = offers;
    }

    public static String getAllProducts() {
        return allProducts;
    }

    public static void setAllProducts(String allProducts) {
        AppStorage.allProducts = allProducts;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
