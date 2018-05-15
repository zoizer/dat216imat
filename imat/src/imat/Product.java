/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat;

/**
 *
 * @author Zoizer
 */
public class Product {
    private final String name; // considered id.
    private int priceHekto;
    
    public Product() {
        name = "<Product missing>";
        priceHekto = 100;
    }
    
    public Product(String n, int price) {
        name = n;
        priceHekto = price;
    }
    
    public String toString() {
        return name;
    }
    
    public int GetHektoPrice() {
        return priceHekto;
    }
    
    public String GetPrice() {
        String price = "" + priceHekto;
        if (price.length() < 3) {
            price = "0," + price;
        } else {
            price = price.substring(0, price.length() - 2) + "," + price.substring(price.length() - 2, price.length());
        }
        
        return price;
    }
    
    @Override
    public boolean equals(Object arg0) {
        return arg0 instanceof Product && ((Product)arg0).name.equals(name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
