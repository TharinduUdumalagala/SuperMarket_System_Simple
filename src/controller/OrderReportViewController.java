package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.OrderTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderReportViewController {
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderDate;
    public TableColumn colOrderTime;
    public TableColumn colCost;
    public TableView tblOrder;

    public void orderTableLoad(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        try {
            ArrayList<OrderTM> list = OrderController.getAllOrder();
            tblOrder.setItems(FXCollections.observableArrayList(list));
        }catch (SQLException throwables){

        }catch (ClassNotFoundException e){

        }
    }

    public void refreshOrder(ActionEvent actionEvent) {

    }
}
