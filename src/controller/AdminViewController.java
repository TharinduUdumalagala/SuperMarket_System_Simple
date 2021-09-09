package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AdminViewController {
    public AnchorPane adminContext;

    public void onIncome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/IncomeView.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void onCustomerWiseIncome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerIncomeView.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void onManageItems(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemView.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void onMovesItems(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MovesItemView.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }
}
