package fr.insalyon.tc.dia.locking.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

public class LockingStoreBenchmark extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        final URL resource = getClass().getResource("interface.fxml");
        Parent root = FXMLLoader.load(resource);

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Benchmarking");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(we -> System.exit(0));
    }
}
