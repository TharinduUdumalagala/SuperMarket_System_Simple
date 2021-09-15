package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import view.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerViewController {
    public TextField txtCustomerId;
    public TextField txtCity;
    public TextField txtContact;
    public TextField txtName;
    public TextField txtProvince;
    public TextField txtAddress;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colContact;
    public AnchorPane customerContext;

    public void initialize(){
        try {
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

            setCustomerTable(new CustomerController().getAllCustomers());

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private void setCustomerTable(ArrayList<Customer> allCustomer) {
        ObservableList<CustomerTM> observableList = FXCollections.observableArrayList();
        allCustomer.forEach( e ->{
            observableList.add(new CustomerTM(
                    e.getId(),
                    e.getName(),
                    e.getAddress(),
                    e.getCity(),
                    e.getProvince(),
                    e.getContact()));
        });
        tblCustomer.setItems(observableList);
    }

    public void saveCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Customer c1 = new Customer(
                txtCustomerId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtContact.getText()
        );

        txtCustomerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtContact.clear();

        if (new CustomerController().saveCustomer(c1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved...").show();
            URL resource = getClass().getResource("../view/CustomerView.fxml");
            Parent load = FXMLLoader.load(resource);
            customerContext.getChildren().clear();
            customerContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Save Customer...").show();
        }
    }

    public void updateCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Customer customer = new Customer(
                txtCustomerId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtContact.getText()
        );

        if (new CustomerController().updateCustomer(customer)){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated...").show();
            URL resource = getClass().getResource("../view/CustomerView.fxml");
            Parent load = FXMLLoader.load(resource);
            customerContext.getChildren().clear();
            customerContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Update Customer...").show();
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (new CustomerController().deleteCustomer(txtCustomerId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted...").show();
            URL resource = getClass().getResource("../view/CustomerView.fxml");
            Parent load = FXMLLoader.load(resource);
            customerContext.getChildren().clear();
            customerContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again...").show();
        }
    }
}
