package com.example.servlet20;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/secure/cars/add")
public class PostServlet extends HttpServlet {
    String carId;
    String carName;
    String carPrice;
    String horsePower;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining("!"));
        carId = requestBody.split("!")[0].split("=")[1];
        carName = requestBody.split("!")[1].split("=")[1];
        carPrice = requestBody.split("!")[2].split("=")[1];
        horsePower = requestBody.split("!")[3].split("=")[1];
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cars", "postgres", "Pshtqapipi0209");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cars (id, name, price, horsepower) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, carId);
            preparedStatement.setString(2, carName);
            preparedStatement.setInt(3, Integer.parseInt(carPrice));
            preparedStatement.setInt(4, Integer.parseInt(horsePower));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL state: %s \n %s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().println("Your car has successfully registered!!!");
    }
}