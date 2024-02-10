package com.connectify.loaders;

import com.connectify.controller.fxmlcontrollers.AdminController;
import com.connectify.controller.fxmlcontrollers.AnnouncementController;
import com.connectify.controller.fxmlcontrollers.MainController;
import com.connectify.controller.fxmlcontrollers.StatisticsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewLoader {

    private static ViewLoader instance;
    private final Pane mainPane;

    private ViewLoader(){
        mainPane = loadMainPane();
    }

    public static ViewLoader getInstance(){
        if(instance == null){
            instance = new ViewLoader();
        }
        return instance;
    }

    public Pane getMainPane(){
        return mainPane;
    }

    private Pane loadMainPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/server-view.fxml"));
        MainController controller = new MainController();
        controller.setAdminPane(loadAdminPane());
        controller.setAnnouncementPane(loadAnnouncementPane());
        controller.setStatisticsPane(loadStatisticsPane());
        fxmlLoader.setController(controller);
        Pane mainPane = null;
        try {
            mainPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mainPane;
    }

    private Pane loadAdminPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane adminPane = null;
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

    private Pane loadAnnouncementPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane announcementPane = null;
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

    private Pane loadStatisticsPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane statisticsPane = null;
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
