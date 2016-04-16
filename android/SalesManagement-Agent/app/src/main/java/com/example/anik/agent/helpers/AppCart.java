package com.example.anik.agent.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class AppCart {
    private static List<Product> productList = new ArrayList<>();
    private static Shop shop;

    public static void addToProductList(Product currentProduct) {
        boolean isAdded = false;
        for (Product previouslyAddedProduct : productList) {
            if (currentProduct.getProductUniqueId().equals(previouslyAddedProduct.getProductUniqueId())) {
                isAdded = true;
                int totalQuantity = previouslyAddedProduct.getQuantity() + currentProduct.getQuantity();
                previouslyAddedProduct.setQuantity(totalQuantity);
                break;
            }
        }
        if (!isAdded)
            productList.add(currentProduct);
    }

    public static List<Product> getCart() {
        return productList;
    }

    public static boolean productExists(Product product) {
        if (productList.contains(product)) {
            return true;
        }
        return false;
    }

    public static int cartSize() {
        return productList.size();
    }

    public static Shop getShop() {
        return shop;
    }

    public static void setShop(Shop shop) {
        AppCart.shop = shop;
    }

    public static void destroy() {
        shop = null;
        productList.clear();
    }

    public static Product removeFromProductCart(String productUniqueId) {
        Product toBeRemoved = null;
        for (Product product : getCart()) {
            if (product.getProductUniqueId().equals(productUniqueId)) {
                toBeRemoved = product;
            }
        }
        if (null != toBeRemoved) {
            productList.remove(toBeRemoved);
            return toBeRemoved;
        }
        return null;
    }
}
