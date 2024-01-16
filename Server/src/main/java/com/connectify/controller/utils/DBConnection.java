package com.connectify.controller.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;


public class DBConnection {

    private Connection connection;
    private final DataSource dataSource;
    private static DBConnection instance;
    private DBConnection(){
        dataSource = getDataSource();
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection openConnection() throws SQLException {
        connection = dataSource.getConnection();
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
    private DataSource getDataSource(){
        Properties credentials = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            credentials.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL(credentials.getProperty("db.url"));
        dataSource.setUser(credentials.getProperty("db.user"));
        dataSource.setPassword(credentials.getProperty("db.password"));
        return dataSource;
    }

}
