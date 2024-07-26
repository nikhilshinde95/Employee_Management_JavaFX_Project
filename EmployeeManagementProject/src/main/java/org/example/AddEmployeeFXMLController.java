package org.example;

import com.org.example.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AddEmployeeFXMLController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, Integer> IDColumn;

    @FXML
    private TableColumn<Employee, String> NameColumn;

    @FXML
    private TableColumn<Employee, String> PositionColumn;

    @FXML
    private TableColumn<Employee, String> MobileColumn;

    @FXML
    private TableColumn<Employee, String> EmailColumn;

    private Connection connect;
    private PreparedStatement prepare;
    private Integer selectedEmployeeID;

    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    @FXML
    void ADD(ActionEvent event) {
        String sql = "INSERT INTO public.employee (Name, Position, Mobile, Email) VALUES (?, ?, ?, ?)";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtName.getText());
            prepare.setString(2, txtPosition.getText());
            prepare.setString(3, txtMobile.getText());
            prepare.setString(4, txtEmail.getText());

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added successfully!");
                refreshTableData();
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error while adding employee: " + e.getMessage());
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        if (selectedEmployeeID == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No employee selected for deletion.");
            return;
        }

        String sql = "DELETE FROM public.employee WHERE ID = ?";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, selectedEmployeeID);

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deleted successfully!");
                refreshTableData();
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error while deleting employee: " + e.getMessage());
        }
    }

    @FXML
    void Update(ActionEvent event) {
        if (selectedEmployeeID == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No employee selected for update.");
            return;
        }

        String sql = "UPDATE public.employee SET Name = ?, Position = ?, Mobile = ?, Email = ? WHERE ID = ?";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtName.getText());
            prepare.setString(2, txtPosition.getText());
            prepare.setString(3, txtMobile.getText());
            prepare.setString(4, txtEmail.getText());
            prepare.setInt(5, selectedEmployeeID);

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated successfully!");
                refreshTableData();
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error while updating employee: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        MobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshTableData();

        employeeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateForm(newValue);
                    }
                }
        );
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        txtName.clear();
        txtPosition.clear();
        txtMobile.clear();
        txtEmail.clear();
        selectedEmployeeID = null;
    }

    private void populateForm(Employee employee) {
        txtName.setText(employee.getName());
        txtPosition.setText(employee.getPosition());
        txtMobile.setText(employee.getMobile());
        txtEmail.setText(employee.getEmail());
        selectedEmployeeID = employee.getId();
    }

    private void refreshTableData() {
        String sql = "SELECT * FROM public.employee";
        connect = Database.connectDb();

        try {
            employeeData.clear();
            ResultSet rs = connect.createStatement().executeQuery(sql);

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Position"),
                        rs.getString("Mobile"),
                        rs.getString("Email")
                );
                employeeData.add(employee);
            }

            employeeTableView.setItems(employeeData);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error while refreshing table data: " + e.getMessage());
        }
    }

    @FXML
    void BackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/DashboardFXML.fxml"));
            Parent dashboardRoot = loader.load();

            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(dashboardRoot);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard page: " + e.getMessage());
        }
    }
}
