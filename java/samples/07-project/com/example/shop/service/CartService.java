package com.example.shop.service;

import com.example.shop.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * カート操作のユースケース。
 */
public class CartService {
    private final List<Product> items = new ArrayList<>();

    public void add(Product product) {
        items.add(product);
    }

    public int totalPrice() {
        int sum = 0;
        for (Product item : items) {
            sum += item.getPrice();
        }
        return sum;
    }

    public List<Product> items() {
        return List.copyOf(items);
    }
}
