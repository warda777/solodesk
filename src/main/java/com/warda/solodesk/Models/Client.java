package com.warda.solodesk.Models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Client {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String createdAt;
    private final String company;
    private final String phone;
    private final String email;


    public Client(int id, String firstName, String lastName, String username, LocalDate createdAt, String company, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.company = company;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCompany() {
        return company;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
