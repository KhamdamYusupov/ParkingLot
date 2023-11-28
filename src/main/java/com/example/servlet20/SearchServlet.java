package com.example.servlet20;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/secure/cars/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (request.getInputStream() == null ) {
            response.sendError(400, "Bad Request: The Json is empty!");
            response.getWriter().println("You must enter the request details in a json format!");
        }
        JsonNode jsonNode = null;
        while(request.getInputStream().available() > 0) {
            jsonNode = objectMapper.readTree(request.getInputStream());
        }
        assert jsonNode != null;
        String name = jsonNode.get("name").asText();

            boolean found = false;

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/cars", "postgres", "Pshtqapipi0209");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cars WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                found = resultSet.getString("name").equalsIgnoreCase(name);
            }
            if (found) {
                response.getWriter().println("The searched car " + name + " exists in the database");
            } else {
                response.getWriter().println("The searched car " + name + " does NOT exist in the database");
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
