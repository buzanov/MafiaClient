package ru.informatics.jojomafia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.form.RoomForm;
import ru.informatics.jojomafia.form.MessageForm;
import ru.informatics.jojomafia.protocol.RequestCommands;
import ru.informatics.jojomafia.protocol.RequestsHandler;
import ru.informatics.jojomafia.protocol.ResponseDispatcher;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
    @FXML
    VBox players;
    @FXML
    Button backButton;
    @FXML
    TextArea chat;
    @FXML
    TextField messageField;
    @FXML
    Button sendMessageButton;
    @FXML
    Label roomName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector.getMap().put("room", this);

    }

    public void back() throws IOException {
        Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new RoomForm(roomName.getText()), RequestCommands.LEAVE_ROOM.name()));
        Connector.getStage().getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/lobby_page.fxml"))));
    }

    public void sendMessage() {
        if (messageField.getText().length() > 0) {
            Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new MessageForm(messageField.getText()), RequestCommands.SEND_MESSAGE.name()));
            messageField.setText("");
        }
    }

}
