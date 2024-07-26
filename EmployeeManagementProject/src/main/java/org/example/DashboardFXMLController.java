package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DashboardFXMLController implements Initializable {

    @FXML
    private Label totalEmp;

    @FXML
    private Label presentsEmp;

    @FXML
    private Label inactiveEmp;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button close;

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button addBtn;

    @FXML
    void addBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/AddEmployeeFXML.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ADD EMPLOYEE DETAILS");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateEmployeeCounts();
    }

    private void updateEmployeeCounts() {
        Connection connect = Database.connectDb();
        String totalQuery = "SELECT COUNT(*) AS count FROM public.employee";
        String presentsQuery = "SELECT COUNT(*) AS count FROM public.employee WHERE status = 'Present'";
        String inactiveQuery = "SELECT COUNT(*) AS count FROM public.employee WHERE status = 'Inactive'";

        try (Statement stmt = connect.createStatement()) {
            ResultSet rs = stmt.executeQuery(totalQuery);
            if (rs.next()) {
                totalEmp.setText(String.valueOf(rs.getInt("count")));
            }

            rs = stmt.executeQuery(presentsQuery);
            if (rs.next()) {
                presentsEmp.setText(String.valueOf(rs.getInt("count")));
            }

            rs = stmt.executeQuery(inactiveQuery);
            if (rs.next()) {
                inactiveEmp.setText(String.valueOf(rs.getInt("count")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    void logoutBtn(ActionEvent event) {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/LoginFXML.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();

            // Create a new scene with the login root
            Scene loginScene = new Scene(loginRoot);

            // Set the new scene on the current stage
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
}
