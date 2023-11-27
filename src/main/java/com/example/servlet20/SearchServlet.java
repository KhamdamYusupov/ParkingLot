package com.example.servlet20;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cars/search")
public class SearchServlet extends HttpServlet {
    List<String> carNames = new ArrayList<>();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String message = req.getReader().readLine();
        boolean found = false;

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cars", "postgres", "Pshtqapipi0209");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cars WHERE name = ?")) {
            preparedStatement.setString(1, message);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                found = resultSet.getString("name").equalsIgnoreCase(message);
            }
            if (found) {
                resp.getWriter().println("The searched car " + message + " exists in the database");
            } else {
                resp.getWriter().println("The searched car " + message + " does NOT exist in the database");
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
