package org.example;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Basket basket;
        ClientLog clientLog;
        basket = Basket.loadFromJson(new File("basket.json"));
        clientLog = new ClientLog();
        System.out.println("Товары, которые есть в магазине:");
        String[] products = basket.getProducts();
        int[] prices = basket.getPrices();
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт.");
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введи номер товара и количество или end");
            String partOfBasket = sc.nextLine();
            if (partOfBasket.equals("end")) {
                break;
            }
            String[] parts = partOfBasket.split(" ");
            basket.addToCart(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            basket.saveToJson(new File("Basket.json"));

            clientLog.log(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }
        basket.printCart();
        clientLog.exportAsCSV(new File("client.csv"));
    }
}