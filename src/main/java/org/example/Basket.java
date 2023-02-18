package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

public class Basket implements Serializable {

    private String[] products;
    private int[] prices;

    private int[] counts;


    public Basket() {
        this.products = new String[]{"Молоко", "Печенье", "Шоколад"};
        this.prices = new int[]{100, 200, 300};
        //this.counts = new int[]{0,0,0};
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
        if (counts == null) {
            System.out.println("Ваша корзина пуста! :(");
            return;
        }
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

    public void saveToJson(File file) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(gson.toJson(this));
            writer.flush();
        }
    }

    public static Basket loadFromJson(File file) throws IOException {
        Basket basket;
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try (FileReader reader = new FileReader(file)) {
            basket = gson.fromJson(reader, Basket.class);
        }
        return basket;
    }
}

