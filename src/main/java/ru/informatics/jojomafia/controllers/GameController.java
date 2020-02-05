package ru.informatics.jojomafia.controllers;

import javafx.fxml.Initializable;
import ru.informatics.jojomafia.client.Connector;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector.getMap().put("game", this);
    }
}
