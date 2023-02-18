package org.example;

import java.io.*;

public class Basket implements Serializable{

    private String[] products;
    private int[] prices;

    private int[] counts;



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


    public void saveBin(File binFile) throws IOException{
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(binFile))){
            out.writeObject(this);
        }
    }
    public static Basket loadFromBinFile(File binFile) throws IOException, ClassNotFoundException{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(binFile))){
            return (Basket) in.readObject();
        }
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }
}
