package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

public class LoginViewController {
    public TextField txtUserName;
    public PasswordField pwdPassword;
    public Button btnLogin;
    public AnchorPane loginContext;
    public Label lblDate;
    public Label lblTime;
    private int hour;


    public void initialize(){
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(simpleDateFormat.format(date));

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

    public void onLogin(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("Admin")){
            URL resource = getClass().getResource("../view/AdminView.fxml");
            Parent load = FXMLLoader.load(resource);
            loginContext.getChildren().clear();
            loginContext.getChildren().add(load);
        }else
            if(txtUserName.getText().equals("Cashier")){
                URL resource = getClass().getResource("../view/CashierView.fxml");
                Parent load = FXMLLoader.load(resource);
                loginContext.getChildren().clear();
                loginContext.getChildren().add(load);
            }
    }

    public void goTOHome(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginView.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void goToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginView.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void goLogOut(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Thread.sleep(5);
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you suer you want to Log out",yes,no);
        alert.setTitle("Confirmation alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes){
            Platform.exit();
            System.exit(0);
        }else {
        }
    }
}
