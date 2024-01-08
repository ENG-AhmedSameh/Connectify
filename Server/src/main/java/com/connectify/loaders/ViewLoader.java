package com.connectify.loaders;

import com.connectify.fxmlcontrollers.AnnouncementController;
import com.connectify.fxmlcontrollers.AdminController;
import com.connectify.fxmlcontrollers.ServerController;
import com.connectify.fxmlcontrollers.StatisticsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ViewLoader {



    public AnchorPane getMainPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/server-view.fxml"));
        ServerController controller = new ServerController();
        controller.setAdminPane(getAdminPane());
        controller.setAnnouncementPane(getAnnouncementPane());
        controller.setStatisticsPane(getStatisticsPane());
        fxmlLoader.setController(controller);
        AnchorPane mainPane = null;
        try {
            mainPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mainPane;
    }

    private BorderPane getAdminPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane adminPane = null;
        fxmlLoader.setLocation(getClass().getResource("/views/admin-view.fxml"));
        AdminController controller = new AdminController();
        fxmlLoader.setController(controller);
        try {
            adminPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminPane;
    }

    private BorderPane getAnnouncementPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane announcementPane = null;
        AnnouncementController controller = new AnnouncementController();
        fxmlLoader.setController(controller);
        fxmlLoader.setLocation(getClass().getResource("/views/announcement-view.fxml"));
        try {
            announcementPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return announcementPane;
    }

    private BorderPane getStatisticsPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane statisticsPane = null;
        fxmlLoader.setLocation(getClass().getResource("/views/statistics-view.fxml"));
        StatisticsController controller = new StatisticsController();
        fxmlLoader.setController(controller);
        try {
            statisticsPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statisticsPane;
    }
}
