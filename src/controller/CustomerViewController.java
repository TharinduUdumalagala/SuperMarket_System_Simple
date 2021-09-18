package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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
            setCustomerTable(new CustomerController().getAllCustomers());
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
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            clear();
            setCustomerTable(new CustomerController().getAllCustomers());
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Update Customer...").show();
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,"Are you suer you want to Delete?",yes,no);
        alert.setTitle("Confirmation alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes){
            if(new CustomerController().deleteCustomer(txtCustomerId.getText())){
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                clear();
                setCustomerTable(new CustomerController().getAllCustomers());
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
            clear();
        }
    }

    private void clear() {
        txtCustomerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtCity.clear();
        txtProvince.clear();
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtCustomerId.getText();
        Customer customer = new CustomerController().getCustomer(customerId);
        if(customer == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
            setData(customer);
        }
    }

    void setData(Customer v) {
        txtCustomerId.setText(v.getId());
        txtName.setText(v.getName());
        txtAddress.setText(v.getAddress());
        txtCity.setText(v.getCity());
        txtProvince.setText(v.getProvince());
        txtContact.setText(v.getContact());

    }
}
