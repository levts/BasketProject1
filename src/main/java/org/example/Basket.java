package org.example;

import java.io.*;
import java.util.Scanner;

public class Basket implements Serializable{

    private String[] products;
    private int[] prices;

    private int[] counts;


    private Basket() {

    }

    public Basket(String[] products, int[] prices) {
        this.prices = prices;
        this.products = products;
        this.counts = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        counts[productNum - 1] += amount;
    }

    public void printCart() {
        int sum = 0;
        System.out.println("Текущая корзина:");
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println(products[i] + " " + counts[i] + " шт по цене " + prices[i] + " за шт");
                sum += prices[i] * counts[i];
            }
        }
        System.out.println("Итоговая сумма покупок: " + sum + " рублей.");
    }

    public void saveToTxt(File file) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(products.length);
            String productsLine = String.join(" ", products);
            printWriter.println(productsLine);

            for (int price : prices) {
                printWriter.print(price + " ");
            }
            printWriter.println();

            for (int count : counts) {
                printWriter.print(count + " ");
            }
            printWriter.println();
        } catch (Exception ex) {
            System.out.println("Ошибка вывода в файл");
        }
    }

    public static Basket loadFromTxt(File file) throws IOException {
        try (Scanner sc = new Scanner(file)) {
            int size = Integer.parseInt(sc.nextLine());
            String[] parts;
            String[] products;
            int[] prices = new int[size];
            int[] counts = new int[size];
            products = sc.nextLine().trim().split(" ");
            parts = sc.nextLine().trim().split(" ");
            for (int i = 0; i < size; i++) {
                prices[i] = Integer.parseInt(parts[i]);
            }

            parts = sc.nextLine().trim().split(" ");
            for (int i = 0; i < size; i++) {
                counts[i] = Integer.parseInt(parts[i]);
            }
            Basket basket = new Basket();
            basket.products = products;
            basket.prices = prices;
            basket.counts = counts;
            return basket;
        }
    }



    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }
}
