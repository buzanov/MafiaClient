package ru.informatics.jojomafia.client;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Connector {

    private static MafiaClient mafiaClient;
    private static Stage stage;
    private static Map<String, Initializable> map = new HashMap<>();

    public static Map<String, Initializable> getMap() {
        return map;
    }

    public static void connectToServer(String ip, int port) {
        mafiaClient = new MafiaClient();
        mafiaClient.startConnection(ip, port);
    }

    public static MafiaClient getMafiaClient() {
        return mafiaClient;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Connector.stage = stage;
    }
}
