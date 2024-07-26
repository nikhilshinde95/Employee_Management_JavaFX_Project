package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginFXMLController implements Initializable {



    @FXML
    private Button loginBtn;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button close;

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }

    private Connection connect;

    private PreparedStatement prepare ;


    private ResultSet result;

    public void loginAdmin() {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = Database.connectDb(); // Make sure this method returns a valid Connection object

        try {
            if (connect != null) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, username.getText());
                prepare.setString(2, password.getText());
                result = prepare.executeQuery();
                Alert alert;

                if (username.getText().isEmpty() || password.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                } else {
                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information MESSAGE");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Log In..");
                        alert.showAndWait();
                        loginBtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("/org/example/DashboardFXML.fxml"));
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.TRANSPARENT);
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR MESSAGE");
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong username and Password");
                        alert.showAndWait();
                    }
                }
            } else {
                System.err.println("Failed to make connection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
