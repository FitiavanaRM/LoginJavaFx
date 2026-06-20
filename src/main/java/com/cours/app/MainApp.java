package com.cours.app;

import com.cours.app.iu.view;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage display) {
        display.setTitle("Connexion");

        view loginView = new view();
        display.setScene(loginView.buildScene(display));
        display.setResizable(false);
        display.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}