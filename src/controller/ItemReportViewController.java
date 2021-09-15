package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import view.tm.ItemTM;

import java.util.ArrayList;

public class ItemReportViewController {

    public TableView<ItemTM> tblItem;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUniqPrice;
    public TableColumn colQTY;
    public TableColumn colItemCode;

    public void itemTableLoad(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUniqPrice.setCellValueFactory(new PropertyValueFactory<>("uniqPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        try {
            ItemController controller = new ItemController();
            ArrayList<Item> items = controller.getAllItem();
            ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
            items.forEach( e->{
                observableList.add(
                        new ItemTM(
                                e.getItemCode(),
                                e.getDescription(),
                                e.getPackSize(),
                                e.getUnitPrice(),
                                e.getQtyOnHand()));
            });
        }catch (Exception e){

        }
    }

    public void refreshItem(ActionEvent actionEvent) {
        itemTableLoad();
    }
}
