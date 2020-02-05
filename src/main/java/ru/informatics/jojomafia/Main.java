package ru.informatics.jojomafia;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.model.GamePack;
import ru.informatics.jojomafia.utils.GamePackLoader;


import java.util.Objects;

import static ru.informatics.jojomafia.client.Connector.connectToServer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connector.setStage(primaryStage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pages/home_page.fxml")));
        primaryStage.setTitle("AniMafia");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("img/micro-img.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        connectToServer(args[0], Integer.parseInt(args[1]));
        GamePackLoader.load();
        launch(args);
    }
}
