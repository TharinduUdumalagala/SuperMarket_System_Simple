package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ReportVIewController {
    public AnchorPane reporteContext;

    public void onCustomerReport(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerReportView.fxml");
        Parent load = FXMLLoader.load(resource);
        reporteContext.getChildren().clear();
        reporteContext.getChildren().add(load);
    }

    public void onItemReport(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ItemReportView.fxml");
        Parent load = FXMLLoader.load(resource);
        reporteContext.getChildren().clear();
        reporteContext.getChildren().add(load);
    }

    public void onOrderReport(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderReportView.fxml");
        Parent load = FXMLLoader.load(resource);
        reporteContext.getChildren().clear();
        reporteContext.getChildren().add(load);
    }
}
