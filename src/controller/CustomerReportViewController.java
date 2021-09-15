package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import view.tm.CustomerTM;

import java.util.ArrayList;

public class CustomerReportViewController {
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colContact;

    public void customerTableLoad(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            CustomerController customerController = new CustomerController();
            ArrayList<Customer> customer = customerController.getAllCustomers();
            ObservableList<CustomerTM> observableList = FXCollections.observableArrayList();
            customer.forEach(e ->{
                observableList.add(new CustomerTM(
                        e.getId(),
                        e.getName(),
                        e.getAddress(),
                        e.getCity(),
                        e.getProvince(),
                        e.getContact()));
            });
            tblCustomer.setItems(observableList);
        }catch (Exception e){
        }
    }

    public void refreshPage(ActionEvent actionEvent) {
        customerTableLoad();
    }
}
