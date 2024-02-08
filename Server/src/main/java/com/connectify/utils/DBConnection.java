package com.connectify.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MIN_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final int MAX_IDLE_TIME_EXCESS_CONN = 3000;

    private final ComboPooledDataSource dataSource;
    private static volatile DBConnection instance;

    private DBConnection(){ dataSource = setupDataSource();}

    public static DBConnection getInstance(){
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private ComboPooledDataSource setupDataSource(){
        try{
            Properties credentials = new Properties();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                credentials.load(is);
            }

            ComboPooledDataSource datasource = new ComboPooledDataSource();
            datasource.setDriverClass("com.mysql.cj.jdbc.Driver");
            datasource.setJdbcUrl(credentials.getProperty("db.url"));
            datasource.setUser(credentials.getProperty("db.user"));
            datasource.setPassword(credentials.getProperty("db.password"));

            datasource.setInitialPoolSize(INITIAL_POOL_SIZE);
            datasource.setMinPoolSize(MIN_POOL_SIZE);
            datasource.setMaxPoolSize(MAX_POOL_SIZE);
            datasource.setMaxIdleTimeExcessConnections(MAX_IDLE_TIME_EXCESS_CONN);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (dataSource != null) {
                    dataSource.close();
                }
            }));
            datasource.setAutoCommitOnClose(true);
            return datasource;
        } catch (Exception e){
            System.err.println("Datasource Exception: " + e.getMessage());
            return null;
        }
    }
}
