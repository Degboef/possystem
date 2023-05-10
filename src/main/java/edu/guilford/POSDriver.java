package edu.guilford;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class POSDriver extends Application {

    private Menu menu;
    private POSSystem pos;
    private Label totalPriceLabel;
    private Button checkoutButton;
    private TextField paymentField;
    private Label changeLabel;
    private ResourceBundle bundle;

    @Override
    public void start(Stage stage) {
        // Load the resource bundle for the current locale
        bundle = ResourceBundle.getBundle("i18n.strings", Locale.getDefault());

        // Create the menu and POS system
        menu = new Menu();
        pos = new POSSystem();

        // Create the top menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu(bundle.getString("menu.file"));
        MenuItem exitMenuItem = new MenuItem(bundle.getString("menu.file.exit"));
        exitMenuItem.setOnAction(event -> System.exit(0));
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        // Create the left side panel with the menu items
        VBox menuPanel = new VBox();
        for (MenuItem item : menu.getItems()) {
            Button addButton = new Button("+");
            Button removeButton = new Button("-");
            Label nameLabel = new Label(item.getName());
            Label priceLabel = new Label(String.format("%.2f", item.getPrice()));
            HBox itemBox = new HBox(addButton, removeButton, nameLabel, priceLabel);
            itemBox.setSpacing(10);
            addButton.setOnAction(event -> {
                pos.addItem(item);
                updateTotalPrice();
            });
            removeButton.setOnAction(event -> {
                pos.removeItem(item);
                updateTotalPrice();
            });
            menuPanel.getChildren().add(itemBox);
        }

        // Create the right side panel with the checkout and payment details
        VBox checkoutPanel = new VBox();
        totalPriceLabel = new Label();
        updateTotalPrice();
        checkoutButton = new Button(bundle.getString("button.checkout"));
        checkoutButton.setOnAction(event -> {
            try {
                double payment = Double.parseDouble(paymentField.getText());
                double change = pos.processPayment(payment);
                changeLabel.setText(String.format(bundle.getString("label.change"), change));
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("error.payment"));
                alert.showAndWait();
            }
        });
        paymentField = new TextField();
        paymentField.setPromptText(bundle.getString("label.payment"));
        changeLabel = new Label();
        checkoutPanel.getChildren().addAll(totalPriceLabel, paymentField, checkoutButton, changeLabel);
        checkoutPanel.setSpacing(10);

        // Create the main border pane and add the components
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setLeft(menuPanel);
        root.setRight(checkoutPanel);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 800, 600);

        // Set the stage title, icon, and scene
        stage.setTitle(bundle.getString("title"));
        stage.getIcons().add(new javafx.scene.image.Image("icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void updateTotalPrice() {
        double totalPrice = pos.getTotalPrice();
        totalPriceLabel.setText(String.format(bundle.getString("label.totalPrice"), totalPrice));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
