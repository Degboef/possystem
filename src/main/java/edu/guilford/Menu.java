package edu.guilford;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items;
    
    public Menu() {
        items = new ArrayList<>();
        // Add some sample menu items
        addItem("Hamburger", 4.99);
        addItem("Cheeseburger", 5.99);
        addItem("French Fries", 2.99);
        addItem("Onion Rings", 3.99);
        addItem("Soft Drink", 1.99);
    }
    
    public List<MenuItem> getItems() {
        return items;
    }
    
    public void addItem(String name, double price) {
        MenuItem newItem = new MenuItem(name, price);
        items.add(newItem);
    }
    
    public void removeItem(MenuItem item) {
        items.remove(item);
    }
    
    public MenuItem findItemByName(String name) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}

class MenuItem {
    private String name;
    private double price;
    
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return name + " - $" + String.format("%.2f", price);
    }
}
