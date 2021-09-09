package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CashierViewController {

    public AnchorPane cashierContext;

    public void onOrder(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderView.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }

    public void onNewCustomer(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerView.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }

    public void onPayment(ActionEvent actionEvent) {
    }
}
