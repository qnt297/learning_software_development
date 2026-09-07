package com.example.shop;

import com.example.shop.domain.Product;
import com.example.shop.service.CartService;

/**
 * パッケージ分割された小さなショップアプリの起動クラス。
 *
 * 実行例:
 *   cd java/samples
 *   javac 07-project/com/example/shop/domain/Product.java \
 *         07-project/com/example/shop/service/CartService.java \
 *         07-project/com/example/shop/App.java
 *   java -cp 07-project com.example.shop.App
 */
public class App {
    public static void main(String[] args) {
        CartService cart = new CartService();
        cart.add(new Product("p1", "マグカップ", 1200));
        cart.add(new Product("p2", "ステッカー", 400));

        System.out.println("カート内容:");
        for (Product product : cart.items()) {
            System.out.println("- " + product.getName() + " / " + product.getPrice() + "円");
        }
        System.out.println("合計: " + cart.totalPrice() + "円");
    }
}
