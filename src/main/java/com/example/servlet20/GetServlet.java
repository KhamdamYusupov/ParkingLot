package com.example.servlet20;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/cars/list")
public class GetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Car> carList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cars", "postgres", "Pshtqapipi0209");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cars")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int horsepower = resultSet.getInt("horsepower");

                Car obj = new Car();
                obj.setId(id);
                obj.setName(name);
                obj.setPrice(price);
                obj.setHorsePower(horsepower);

                carList.add(obj);
            }
            carList.forEach(resp.getWriter()::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}