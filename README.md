# Employee Management System

This JavaFX application is an Employee Management System that consists of a login form, a dashboard, and an employee registration form. The project utilizes JavaFX for the user interface and communicates with a database to manage employee data.

## Project Structure

- **LoginFXML.fxml**: FXML file for the login form.
- **DashboardFXML.fxml**: FXML file for the dashboard view.
- **AddEmployeeFXML.fxml**: FXML file for the employee registration form.
- **Main.java**: Entry point of the application.
- **LoginFXMLController.java**: Controller class for the login form.
- **DashboardFXMLController.java**: Controller class for the dashboard.
- **AddEmployeeFXMLController.java**: Controller class for the employee registration form.
- **Database.java**: Utility class for database connection.

## Setup Instructions

1. **Clone the Repository**
    ```sh
    git clone https://github.com/nikhilshinde95/Employee_Management_JavaFX_Project.git
    cd employee-management-system
    ```

2. **Ensure you have the following prerequisites:**
   - Java Development Kit (JDK) 8 or higher.
   - JavaFX SDK (if not bundled with your JDK).

3. **Configure the Database:**
   - Ensure you have a database set up with the necessary schema.
   - Update the `Database.java` file with your database connection details.

4. **Build and Run the Application:**
   - Navigate to the project directory.
   - Compile the Java files and run the application using your preferred IDE or command line.
   
   For command line:
   ```sh
   javac -d bin src/org/example/*.java
   java -cp bin org.example.Main
