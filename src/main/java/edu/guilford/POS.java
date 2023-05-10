package edu.guilford;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class POS extends GridPane {
    private Menu menu;
    private List<OrderItem> orderItems;
    private ObservableList<String> itemNames;
    private ListView<String> itemList;
    private Label subtotalLabel;
    private Label taxLabel;
    private Label totalLabel;
    private double subtotal;
    private double tax;
    private double total;
    
    public POS(Menu menu) {
        this.menu = menu;
        orderItems = new ArrayList<OrderItem>();
        itemNames = FXCollections.observableArrayList();
        itemList = new ListView<String>(itemNames);
        subtotalLabel = new Label();
        taxLabel = new Label();
        totalLabel = new Label();
        subtotal = 0.0;
        tax = 0.0;
        total = 0.0;
        
        // Set up the UI elements
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.TOP_CENTER);
        
        Label menuLabel = new Label("Menu");
        menuLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        add(menuLabel, 0, 0);
        
        Label orderLabel = new Label("Order");
        orderLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        add(orderLabel, 1, 0);
    // Set up the menu list
    for (MenuItem item : menu.getItems()) {
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            addItemToOrder(item);
        });
        HBox itemBox = new HBox(10, new Label(item.toString()), addButton);
        add(itemBox, 0, menu.getItems().indexOf(item) + 1);
    }
    
    // Set up the order list
    itemList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    add(itemList, 1, 1, 1, 4);
    
    Label subtotalTitle = new Label("Subtotal:");
    add(subtotalTitle, 1, 5);
    add(subtotalLabel, 2, 5);
    
    Label taxTitle = new Label("Tax:");
    add(taxTitle, 1, 6);
    add(taxLabel, 2, 6);
    
    Label totalTitle = new Label("Total:");
    add(totalTitle, 1, 7);
    add(totalLabel, 2, 7);
    
    // Set up the payment section
    Label paymentLabel = new Label("Payment");
    paymentLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    add(paymentLabel, 3, 0);
    
    Label amountLabel = new Label("Amount:");
    TextField amountField = new TextField();
    add(amountLabel, 3, 1);
    add(amountField, 4, 1);
    
    Button cashButton = new Button("Cash");
    cashButton.setOnAction(event -> {
        double amount = Double.parseDouble(amountField.getText());
        processCashPayment(amount);
    });
    add(cashButton, 3, 2);
    
    Button cardButton = new Button("Credit Card");
    cardButton.setOnAction(event -> {
        double amount = Double.parseDouble(amountField.getText());
        processCardPayment(amount);
    });
    add(cardButton, 4, 2);
    
    Button clearButton = new Button("Clear Order");
    clearButton.setOnAction(event -> {
        clearOrder();
    });
    add(clearButton, 3, 4, 2, 1);
}

private void addItemToOrder(MenuItem item) {
    OrderItem orderItem = new OrderItem(item);
    orderItems.add(orderItem);
    itemNames.add(orderItem.toString());
    updateTotals();
}

private void updateTotals() {
    subtotal = 0.0;
    for (OrderItem item : orderItems) {
        subtotal += item.getTotalPrice();
    }
    tax = subtotal * 0.06;
    total = subtotal + tax;
    subtotalLabel.setText("$" + String.format("%.2f", subtotal));
    taxLabel.setText("$" + String.format("%.2f", tax));
    totalLabel.setText("$" + String.format("%.2f", total));
}

private void processCashPayment(double amount) {
    double change = amount - total;
    itemNames.clear();
    orderItems.clear();
    updateTotals();
    showReceipt("Cash", amount, change);
}

private void processCardPayment(double amount) {
    itemNames.clear();
    orderItems.clear();
    updateTotals();
    showReceipt("Credit Card", amount, 0);
}

private void clearOrder() {
    itemNames.clear();
    orderItems.clear();
    updateTotals();
}

private void showReceipt(String paymentType, double amount, double change) {
    VBox receiptBox = new VBox();
    receiptBox.setAlignment(Pos.CENTER);
    receiptBox.setSpacing(10);
    Label thankYouLabel = new Label("Thank You for Your Order!");
    thankYouLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    receiptBox.getChildren().add(thankYouLabel);
    
    Label paymentLabel = new Label("Payment Type: " + paymentType);
    receiptBox.getChildren().add(paymentLabel);
    
    Label amountLabel = new Label("Amount Paid: $" + String.format("%.2f", amount));
    receiptBox.getChildren().add(amountLabel);
    
    if (change > 0) {
        Label changeLabel = new Label("Change: $" + String.format("%.2f", change));
        receiptBox.getChildren().add(changeLabel);
    }
    
    Label orderLabel = new Label("Order:");
    orderLabel.setStyle("-fx-font-weight: bold;");
    receiptBox.getChildren().add(orderLabel);
    
    for (String itemName : itemNames) {
        Label itemLabel = new Label(itemName);
        receiptBox.getChildren().add(itemLabel);
    }
    
    Stage receiptStage = new Stage();
    receiptStage.setScene(new Scene(receiptBox, 300, 400));
    receiptStage.setTitle("Receipt");
    receiptStage.show();
}

public static void main(String[] args) {
    launch(args);
}

private static void launch(String[] args) {
}
}

public class MenuItem {
private String name;
private double price;
public MenuItem(String name, double price) {
    this.name = name;
    this.price = price;
}

public MenuItem(String string) {
}

public String getName() {
    return name;
}

public double getPrice() {
    return price;
}

@Override
public String toString() {
    return name + " - $" + String.format("%.2f", price);
}

public void setOnAction1(Object object) {
    // Method implementation 1
}

public void setOnAction2(Object object) {
    // Method implementation 2
}

public void setOnAction3(Object object) {
    // Method implementation 3
}

public void setOnAction4(Object object) {
    // Method implementation 4
}

public void setOnAction5(Object object) {
    // Method implementation 5
}

public void setOnAction3(Object object) {
}
}

public class Menu {
private ArrayList<MenuItem> items;
public Menu() {
    items = new ArrayList<>();
}

public Menu(String string) {
}

public void addItem(MenuItem item) {
    items.add(item);
}

public ArrayList<MenuItem> getItems() {
    return items;
}
}

class OrderItem {
private MenuItem item;
private int quantity;
public OrderItem(MenuItem item) {
    this.item = item;
    this.quantity = 1;
}

public MenuItem getItem() {
    return item;
}

public int getQuantity() {
    return quantity;
}

public void incrementQuantity() {
    quantity++;
}

public double getTotalPrice() {
    return item.getPrice() * quantity;
}

@Override
public String toString() {
    return item.getName() + " x " + quantity + " - $" + String.format("%.2f", getTotalPrice());
}
}

