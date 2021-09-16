package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.ItemDetails;
import model.Order;
import view.tm.ChartTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageOrderViewController {
    public TextField txtDescription;
    public TextField txtQTY;
    public TextField txtUniqPrice;
    public TextField txtOtyOnHand;
    public TableView tblChart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colUniqPrice;
    public TableColumn cilTotal;
    public ComboBox <String> cmdItemCode;
    public ComboBox <String> cmdCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtContact;
    public Label txtTotal;
    public TextField txtOrderId;
    public Label lblTime;
    public Label lblDate;
    private int hour;

    int cartSelectRowForRemove = -1;

    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUniqPrice.setCellValueFactory(new PropertyValueFactory<>("uniqPrice"));
        cilTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        setOrderId();

        try{
            loadCustomerId();
            loadItemCode();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmdCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            }catch (SQLException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        });

        cmdItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            }catch (SQLException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        });

        tblChart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectRowForRemove = (int) newValue;
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            String state = null;
            hour = currentTime.getHour();
            if (hour < 12) {
                state = "AM";
            } else {
                state = "PM";
            }
            lblTime.setText(
                    currentTime.getHour()+ ": "+currentTime.getMinute()+ ": "+currentTime.getSecond()+ " " +state
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setOrderId() {
        try {
            txtOrderId.setText(new OrderController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1 =new CustomerController().getCustomer(customerId);
        if (c1 == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtCustomerName.setText(c1.getName());
            txtCustomerAddress.setText(c1.getAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtContact.setText(c1.getContact());
        }
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item item = new ItemController().getItem(itemCode);
        if (item == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtDescription.setText(item.getDescription());
            txtOtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            txtUniqPrice.setText(String.valueOf(item.getUnitPrice()));
        }
    }

    private void loadCustomerId() throws SQLException, ClassNotFoundException {
        List<String> customerId = new CustomerController().getCustomerIds();
        cmdCustomerId.getItems().addAll(customerId);
    }

    private void loadItemCode() throws SQLException, ClassNotFoundException {
        List<String> itemId = new ItemController().getAllItemIds();
        cmdItemCode.getItems().addAll(itemId);
    }


    ObservableList<ChartTM> observableList = FXCollections.observableArrayList();

    public void addCard(ActionEvent actionEvent) {

    }

    public void clear(ActionEvent actionEvent) {
        if (cartSelectRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please Select a row").show();
        }else {
            observableList.remove(cartSelectRowForRemove);
            calculateCost();
            tblChart.refresh();
        }
    }

    private void calculateCost() {
        double total=0;
        for (ChartTM chartTM:observableList){
            total+=chartTM.getTotal();
        }
        txtTotal.setText(total+"/=");
    }

    public void placeOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        double total = 0;
        for (ChartTM chartTM:observableList){
            total+=chartTM.getTotal();
            itemDetails.add(new ItemDetails(
                    chartTM.getCode(),
                    chartTM.getUnitPrice(),
                    chartTM.getQty()));
        }

        Order order = new Order(
                txtOrderId.getText(),
                cmdCustomerId.getValue(),
                lblDate.getText(),
                lblTime.getText(),
                total,
                itemDetails);

        if (new OrderController().placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Success Order").show();

            txtCustomerName.clear();
            txtCustomerAddress.clear();
            txtCity.clear();
            txtProvince.clear();
            txtContact.clear();
            txtDescription.clear();
            txtOtyOnHand.clear();
            txtUniqPrice.clear();
            txtQTY.clear();

            setOrderId();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }
}
