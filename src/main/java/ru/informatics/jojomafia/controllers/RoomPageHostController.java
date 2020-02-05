package ru.informatics.jojomafia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.form.MessageForm;
import ru.informatics.jojomafia.protocol.RequestCommands;
import ru.informatics.jojomafia.protocol.RequestsHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomPageHostController implements Initializable {
    @FXML
    FlowPane players;
    @FXML
    TextArea chat;
    @FXML
    TextField messageField;
    @FXML
    Button sendMessageButton;
    @FXML
    Label roomName;

    public void sendMessage() {
        if (messageField.getText().length() > 0) {
            Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new MessageForm(messageField.getText()), RequestCommands.SEND_MESSAGE.name()));
            messageField.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
