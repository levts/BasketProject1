package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        XMLReader xmlReader = new XMLReader();
        Parameters parameters = xmlReader.read(new File("shop.xml"));
        Basket basket;
        ClientLog clientLog = null;
        if (parameters.load) {
            if (parameters.loadType.equals("json")) {
                basket = Basket.loadFromJson(new File(parameters.loadFile));
            } else {
                basket = Basket.loadFromTxt(new File(parameters.loadFile));
            }
        } else {
            basket = new Basket();
        }
        if (parameters.log) {
            clientLog = new ClientLog();
        }
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
            if (parameters.save) {
                if (parameters.saveType.equals("json")) {
                    basket.saveToJson(new File("Basket.json"));
                } else {
                    basket.saveToTxt(new File("Basket.txt"));
                }
            }
            if (parameters.log) {
                clientLog.log(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }
        }
        basket.printCart();
        if (parameters.log) {
            assert clientLog != null;
            clientLog.exportAsCSV(new File(parameters.logFile));
        }
    }
}