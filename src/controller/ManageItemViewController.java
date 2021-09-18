package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Item;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageItemViewController {
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUniqPrice;
    public TextField txtQTY;
    public TableView<ItemTM> tblItem;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUniqPrice;
    public TableColumn colQTY;
    public TableColumn colItemCode;
    public AnchorPane itemContext;


    public void initialize(){
        try {
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colUniqPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

            setItemsTable(new ItemController().getAllItem());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private void setItemsTable(ArrayList<Item> allItem) {
        ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
        allItem.forEach( e->{
            observableList.add(new ItemTM(
                    e.getItemCode(),
                    e.getDescription(),
                    e.getPackSize(),
                    e.getUnitPrice(),
                    e.getQtyOnHand()));
        });

        tblItem.setItems(observableList);
    }

    public void saveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUniqPrice.getText()),
                txtQTY.getText());

        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUniqPrice.clear();
        txtQTY.clear();

        if (new ItemController().saveItem(item)){
            setItemsTable(new ItemController().getAllItem());
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void updateItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUniqPrice.getText()),
                txtQTY.getText());


        if (new ItemController().updateItem(item)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            clear();
            setItemsTable(new ItemController().getAllItem());
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Update Customer...").show();
        }
    }

    private void clear() {
        txtItemCode.clear();
        txtQTY.clear();
        txtPackSize.clear();
        txtDescription.clear();
        txtUniqPrice.clear();
    }

    public void deleteItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,"Are you suer you want to Delete?",yes,no);
        alert.setTitle("Confirmation alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes){
            if(new ItemController().deleteItem(txtItemCode.getText())){
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                clear();
                setItemsTable(new ItemController().getAllItem());
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
            clear();
        }
    }

    public void searchItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtItemCode.getText();
        Item item = new ItemController().getItem(itemCode);
        if(itemCode == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
            setData(item);
        }
    }

    void setData(Item v) {
        txtItemCode.setText(v.getItemCode());
        txtDescription.setText(v.getDescription());
        txtPackSize.setText(v.getPackSize());
        txtQTY.setText(v.getQtyOnHand());
        txtUniqPrice.setText(String.valueOf(v.getUnitPrice()));


    }
}
