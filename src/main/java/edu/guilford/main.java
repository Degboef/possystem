package edu.guilford;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class main extends Application {
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

    public main() {
        menu = new Menu();
        orderItems = new ArrayList<>();
        itemNames = FXCollections.observableArrayList();
        itemList = new ListView<>(itemNames);
        subtotalLabel = new Label();
        taxLabel = new Label();
        totalLabel = new Label();
        subtotal = 0.0;
        tax = 0.0;
        total = 0.0;
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

    
        @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Create the menu
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        mainLayout.setTop(menuBar);

        // Add the list view for items
        mainLayout.setCenter(itemList);

        // Create the labels for totals
        VBox totalsBox = new VBox(10);
        totalsBox.setAlignment(Pos.CENTER);
        totalsBox.setPadding(new Insets(10));
        totalsBox.getChildren().addAll(subtotalLabel, taxLabel, totalLabel);
        mainLayout.setBottom(totalsBox);

        // Set the scene and show the stage
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setTitle("GUI Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    

