package com.example.toilet;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

@Service
public class JdbcOperation {

    private Connection connection;

    // 获取数据库连接
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

    // 初始化数据库连接
    private synchronized void initializeConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();
            try (InputStream input = JdbcOperation.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new FileNotFoundException("db.properties file not found");
                }
                properties.load(input);

                String jdbcUrl = properties.getProperty("jdbc.url");
                String user = properties.getProperty("jdbc.username");
                String password = properties.getProperty("jdbc.password");

                connection = DriverManager.getConnection(jdbcUrl, user, password);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 初始化数据库表结构（如果不存在）
    @PostConstruct
    public void initializeDatabase() {
        try (Statement statement = getConnection().createStatement()) {
            String useDatabaseSQL = "USE toilet";
            statement.executeUpdate(useDatabaseSQL);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS toilet_location (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL," +
                    "type VARCHAR(255) NOT NULL," +
                    "isFree BOOLEAN NOT NULL," +
                    "isAvailable BOOLEAN," +
                    "isClean BOOLEAN," +
                    "isPaper BOOLEAN," +
                    "isSoap BOOLEAN," +
                    "Longitude VARCHAR(255) NOT NULL," +
                    "Latitude VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id))";

            statement.executeUpdate(createTableSQL);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // 添加厕所位置记录到数据库
    public void addToiletLocation(Location location) {
        String sql = "INSERT INTO toilet_location (name, type, isFree, isAvailable, isClean, isPaper, isSoap, longitude, latitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getType());
            preparedStatement.setBoolean(3, location.isFree());
            preparedStatement.setBoolean(4, location.isAvailable());
            preparedStatement.setBoolean(5, location.isClean());
            preparedStatement.setBoolean(6, location.isPaper());
            preparedStatement.setBoolean(7, location.isSoap());
            preparedStatement.setString(8, location.getLongitude());
            preparedStatement.setString(9, location.getLatitude());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Inserted 1 row into toilet_location table.");
            } else {
                System.out.println("Failed to insert into toilet_location table.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // 获取所有厕所位置记录
    public List<Location> getAllLocations() {
        List<Location> retLocations = new ArrayList<>();
        String sql = "SELECT * FROM toilet_location";
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                boolean isFree = resultSet.getBoolean("isFree");
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                boolean isClean = resultSet.getBoolean("isClean");
                boolean isPaper = resultSet.getBoolean("isPaper");
                boolean isSoap = resultSet.getBoolean("isSoap");
                String longitude = resultSet.getString("longitude");
                String latitude = resultSet.getString("latitude");

                Location location = new Location(name, type, isFree, isAvailable, isClean, isPaper, isSoap, longitude, latitude);
                retLocations.add(location);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return retLocations;
    }

    // 关闭数据库连接
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
