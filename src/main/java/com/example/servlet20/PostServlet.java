package com.example.servlet20;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/secure/cars/add")
public class PostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        if (jsonBody.isBlank() || jsonBody.isEmpty()) {
            response.sendError(400, "Bad Request: The Json is empty!");
            response.getWriter().println("You must enter the request details in a json format!");
        }
        final Car carInput = objectMapper.readValue(jsonBody, Car.class);
        String id = carInput.getId();
        String name = carInput.getName();
        Integer price = carInput.getPrice();
        Integer horsePower = carInput.getHorsePower();


        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cars", "postgres", "Pshtqapipi0209");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cars (id, name, price, horsepower) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, horsePower);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL state: %s \n %s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().println("Your car has successfully registered!!!");
    }
}