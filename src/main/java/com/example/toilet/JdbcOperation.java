package com.example.toilet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcOperation{
    public static List<Location> getAllLocations() {
        Properties properties = new Properties();
        try (InputStream input = ToiletUpdateUpload.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        String jdbcUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        Connection connection = null;
        Statement statement = null;

        //create database
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS toilets";
        statement.executeUpdate(createDatabaseSQL);
        System.out.println("Database created successfully...");


        try {
            // loading JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            statement = connection.createStatement();

            //create database
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS toilets";
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database created successfully...");

            // Use Database
            String useDatabaseSQL = "USE toilets";
            statement.executeUpdate(useDatabaseSQL);
            System.out.println("Using database...");

            //Create Table
            String createTableLocationsSQL = "CREATE TABLE IF NOT EXISTS locations ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "longitude DOUBLE NOT NULL,"
                    + "latitude DOUBLE NOT NULL,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "type VARCHAR(255) NOT NULL,"
                    + "isFree BOOLEAN NOT NULL,"
                    + "PRIMARY KEY (id)),"
                    + "FOREIGN KEY (Status) REFERENCES status(id)";

            String createTableStatusSQL = "CREATE TABLE IF NOT EXISTS status ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "isAvailable BOOLEAN NOT NULL,"
                    + "isClean BOOLEAN NOT NULL,"
                    + "isPaper BOOLEAN NOT NULL,"
                    + "isSoap BOOLEAN NOT NULL,"
                    + "PRIMARY KEY (id)),"
                    + "FOREIGN KEY (Locations) REFERENCES locations(id)";

            statement.executeUpdate(createTableLocationsSQL);
            System.out.println("Table locations created successfully...");

            statement.executeUpdate(createTableStatusSQL);
            System.out.println("Table status created successfully...");

            //Insert Data
            String insertLocationsDataSQL = "INSERT INTO locations (longitude, latitude, name, type, isFree) VALUES (116.397128, 39.916527, 'Beijing', 'Toilet', true)";
            statement.executeUpdate(insertLocationsDataSQL);

            String insertStatusDataSQL = "INSERT INTO status (isAvailable, isClean, isPaper, isSoap) VALUES (true, true, true, true)";

            //Extract All Data
            List<Location> retLocations = new ArrayList<>();
            String selectDataSQL = "SELECT * FROM locations l, status s WHERE l.id = s.id";
            ResultSet resultSet = statement.executeQuery(selectDataSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double longitude = resultSet.getDouble("longitude");
                double latitude = resultSet.getDouble("latitude");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                boolean isFree = resultSet.getBoolean("isFree");
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                boolean isClean = resultSet.getBoolean("isClean");
                boolean isPaper = resultSet.getBoolean("isPaper");
                boolean isSoap = resultSet.getBoolean("isSoap");

                retLocations.add(l);
            }
            return retLocations;



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }

}