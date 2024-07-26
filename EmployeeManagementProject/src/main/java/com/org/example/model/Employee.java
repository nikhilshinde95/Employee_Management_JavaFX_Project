package com.org.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty position;
    private final SimpleStringProperty mobile;
    private final SimpleStringProperty email;

    public Employee(int id, String name, String position, String mobile, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.position = new SimpleStringProperty(position);
        this.mobile = new SimpleStringProperty(mobile);
        this.email = new SimpleStringProperty(email);
    }

    // Getters and Setters for all properties
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public SimpleStringProperty nameProperty() { return name; }

    public String getPosition() { return position.get(); }
    public void setPosition(String position) { this.position.set(position); }
    public SimpleStringProperty positionProperty() { return position; }

    public String getMobile() { return mobile.get(); }
    public void setMobile(String mobile) { this.mobile.set(mobile); }
    public SimpleStringProperty mobileProperty() { return mobile; }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public SimpleStringProperty emailProperty() { return email; }
}
