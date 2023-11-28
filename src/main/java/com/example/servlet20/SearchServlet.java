package com.example.servlet20;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/secure/cars/search")
public class SearchServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestInputStream = request.getInputStream();
        String errorMessage = "You must enter the request details in a json format!";
        if (requestInputStream == null) {
            outputResponseInJson(response, errorMessage, 400);
            return;
        }
        Car car = null;
        while (requestInputStream.available() > 0) {
            car = objectMapper.readValue(requestInputStream, Car.class);
        }
        assert car != null;
        String name = car.getName();

        boolean found = false;
        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/cars", "postgres", "Pshtqapipi0209");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cars WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                found = resultSet.getString("name").equalsIgnoreCase(name);
            }
            String foundMessage = "The searched car " + name + " exists in the database";
            String notFoundMessage = "The searched car " + name + " does NOT exist in the database";
            if (found) {
                outputResponseInJson(response, foundMessage, 200);
            } else {
                outputResponseInJson(response, notFoundMessage, 200);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void outputResponseInJson(HttpServletResponse response, String message, Integer statusCode) throws IOException {
        final String msgJsonEmptyString = objectMapper.writeValueAsString(new ResponseMessage(message));
        final PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setStatus(statusCode);
        out.println(msgJsonEmptyString);
        out.flush();
    }
}
