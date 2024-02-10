package com.connectify.controller.fxmlcontrollers;

import com.connectify.utils.DBConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class StatisticsController implements Initializable {

    @FXML
    private BorderPane statisticsPane;

    @FXML
    private PieChart usersModePieChart;

    @FXML
    private PieChart genderPieChart;

    @FXML
    private PieChart countryPieChart;

    private Connection connection;

    private ScheduledExecutorService executor;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            executor = Executors.newSingleThreadScheduledExecutor();
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Couldn't connect to database: " + e.getMessage());
        }
        executor.scheduleAtFixedRate(this::updateCharts, 0, 5, SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (executor != null) {
                executor.shutdown();
            }
        }));
    }




    private void updateCharts() {
        updatePieChart(usersModePieChart, "SELECT mode, COUNT(*) FROM users GROUP BY mode");
        updatePieChart(genderPieChart, "SELECT gender, COUNT(*) FROM users GROUP BY gender");
        updatePieChart(countryPieChart, "SELECT country, COUNT(*) FROM users GROUP BY country");
    }

    private void updatePieChart(PieChart pieChart, String query) {
        try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            Map<String, Integer> data = new HashMap<>();
            while (result.next()) {
                data.put(result.getString(1), result.getInt(2));
            }
            Platform.runLater(() -> {
                pieChart.setData(FXCollections.observableArrayList(data.entrySet().stream()
                        .map(entry -> new PieChart.Data(getFormattedKey(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toList())));
            });
        } catch (SQLException e) {
            System.err.println("Error updating " + pieChart.getId() + " chart: " + e.getMessage());
        }
    }

    private String getFormattedKey(String key) {
        return key.equals("M") ? "Male" : (key.equals("F") ? "Female" : key);
    }
}
