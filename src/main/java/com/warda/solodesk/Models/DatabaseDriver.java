package com.warda.solodesk.Models;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseDriver {

    private Connection conn;


    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:solodesk.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateLogin(String username, String password) {
        if (conn == null) {
            return false;
        }

        String query = "SELECT * FROM Admins WHERE Username = ? AND Password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true, wenn ein Benutzer gefunden wurde
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ObservableList<Client> fetchAllClients() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        String query = "SELECT * FROM Clients";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("Vorname");
                String lastName = rs.getString("Name");
                String username = rs.getString("Username");
                String company = rs.getString("Firma");
                String phone = rs.getString("Telefon");
                String email = rs.getString("Email");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate createdAt = LocalDate.parse(rs.getString("Erstellt am"), formatter);
                clients.add(new Client(id, firstName, lastName, username, createdAt, company, phone, email));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public ObservableList<Invoice> fetchAllInvoices() {
        ObservableList<Invoice> invoices = FXCollections.observableArrayList();
        String query = "SELECT Invoices.ID, " +
                "Clients.Vorname || ' ' || Clients.Name AS ClientName, " +
                "Invoices.InvoiceDate, Invoices.Amount, Invoices.Status, Invoices.Description " +
                "FROM Invoices " +
                "JOIN Clients ON Invoices.ClientID = Clients.ID";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String clientName = rs.getString("ClientName");
                String date = rs.getString("InvoiceDate");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("Status");
                String description = rs.getString("Description");

                invoices.add(new Invoice(id, clientName, date, amount, status, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    public ObservableList<String> getAllClientNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        String query = "SELECT Vorname, Name FROM Clients";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String fullName = rs.getString("Vorname") + " " + rs.getString("Name");
                names.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return names;
    }

    public boolean insertInvoice(int clientId, String date, double amount, String status, String description) {
        String query = "INSERT INTO Invoices (ClientID, InvoiceDate, Amount, Status, Description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId); // ID statt Name
            stmt.setString(2, date);
            stmt.setDouble(3, amount);
            stmt.setString(4, status);
            stmt.setString(5, description);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int getClientIdByName(String fullName) {
        String query = "SELECT ID FROM Clients WHERE Vorname || ' ' || Name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // nicht gefunden
    }

    public ObservableList<Payment> fetchAllPayments() {
        ObservableList<Payment> payments = FXCollections.observableArrayList();
        String query = "SELECT Payments.ID, Clients.Vorname || ' ' || Clients.Name AS ClientName, " +
                "PaymentDate, Amount, Method, Note " +
                "FROM Payments JOIN Clients ON Payments.ClientID = Clients.ID";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String client = rs.getString("ClientName");
                String date = rs.getString("PaymentDate");
                double amount = rs.getDouble("Amount");
                String method = rs.getString("Method");
                String note = rs.getString("Note");

                payments.add(new Payment(id, client, date, amount, method, note));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public boolean insertPayment(String clientName, String date, double amount, String method, String note) {
        int clientId = getClientIdByName(clientName);
        if (clientId == -1) {
            System.out.println("Kunde nicht gefunden: " + clientName);
            return false;
        }

        String query = "INSERT INTO Payments (ClientID, PaymentDate, Amount, Method, Note) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            stmt.setString(2, date);
            stmt.setDouble(3, amount);
            stmt.setString(4, method);
            stmt.setString(5, note);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Payment> fetchPaidInvoicesAsPayments() {
        ObservableList<Payment> payments = FXCollections.observableArrayList();
        String query = "SELECT * FROM PaidInvoicesView";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String client = rs.getString("ClientName");
                String date = rs.getString("PaymentDate");
                double amount = rs.getDouble("Amount");
                String method = rs.getString("Method");
                String note = rs.getString("Note");

                payments.add(new Payment(id, client, date, amount, method, note));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean insertClient(String first, String last, String username, String company, String phone, String email) {
        String query = "INSERT INTO Clients (Vorname, Name, Username, Firma, Telefon, Email, `Erstellt am`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, first);
            stmt.setString(2, last);
            stmt.setString(3, username);
            stmt.setString(4, company);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getClientCount() {
        String query = "SELECT COUNT(*) AS Total FROM Clients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getUnpaidInvoiceCount() {
        String query = "SELECT COUNT(*) AS Total FROM Invoices WHERE Status != 'Bezahlt'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMonthlyIncome(int month) {
        String query = "SELECT Amount, PaymentDate FROM Payments";
        double total = 0;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            while (rs.next()) {
                String date = rs.getString("PaymentDate");
                double amount = rs.getDouble("Amount");

                LocalDate paymentDate = LocalDate.parse(date, formatter);
                if (paymentDate.getMonthValue() == month) {
                    total += amount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public ObservableList<String> getRecentActivities(int limit) {
        ObservableList<String> activities = FXCollections.observableArrayList();

        String queryClients = "SELECT Vorname || ' ' || Name AS Name FROM Clients ORDER BY ID DESC LIMIT " + limit;
        String queryInvoices = "SELECT ID FROM Invoices ORDER BY ID DESC LIMIT " + limit;
        String queryPayments = "SELECT Amount FROM Payments ORDER BY ID DESC LIMIT " + limit;

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs1 = stmt.executeQuery(queryInvoices);
            while (rs1.next()) {
                activities.add("🧾 Neue Rechnung erstellt: #" + rs1.getInt("ID"));
            }

            ResultSet rs2 = stmt.executeQuery(queryPayments);
            while (rs2.next()) {
                activities.add("Zahlung erhalten: " + rs2.getDouble("Amount") + " €");
            }

            ResultSet rs3 = stmt.executeQuery(queryClients);
            while (rs3.next()) {
                activities.add("Neuer Kunde: " + rs3.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }

}

