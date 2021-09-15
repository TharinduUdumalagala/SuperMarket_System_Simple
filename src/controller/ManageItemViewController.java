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
import model.Item;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

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
            colUniqPrice.setCellValueFactory(new PropertyValueFactory<>("uniqPrice"));
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
            new Alert(Alert.AlertType.CONFIRMATION, "Item Saved...").show();
            URL resource = getClass().getResource("../view/ManageItemView.fxml");
            Parent load = FXMLLoader.load(resource);
            itemContext.getChildren().clear();
            itemContext.getChildren().add(load);
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
            new Alert(Alert.AlertType.CONFIRMATION, "Item Update...").show();
            URL resource = getClass().getResource("../view/ManageItemView.fxml");
            Parent load = FXMLLoader.load(resource);
            itemContext.getChildren().clear();
            itemContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void deleteItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (new ItemController().deleteItem(txtItemCode.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted...").show();
            URL resource = getClass().getResource("../view/ManageItemView.fxml");
            Parent load = FXMLLoader.load(resource);
            itemContext.getChildren().clear();
            itemContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }
}
