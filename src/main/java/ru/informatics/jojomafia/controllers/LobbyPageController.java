package ru.informatics.jojomafia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.form.EmptyForm;
import ru.informatics.jojomafia.form.MessageForm;
import ru.informatics.jojomafia.protocol.RequestCommands;
import ru.informatics.jojomafia.protocol.RequestsHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LobbyPageController implements Initializable {
    @FXML
    Label nickname;

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextField messageField;

    @FXML
    TextArea chat;

    @FXML
    Button sendMessageButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector.getMap().put("lobby", this);
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
        Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new EmptyForm(), RequestCommands.GET_LOBBY_INFO.name()));
        chat.autosize();
        chat.setEditable(false);
        chat.setFocusTraversable(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setOnMouseClicked(event -> messageField.requestFocus());
        chat.setOnMouseClicked(event -> messageField.requestFocus());
    }


    @FXML
    public void createRoom() {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pages/room_create_page.fxml"))), 640, 360);
            stage.getIcons().add(new Image("img/micro-img.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage() {
        if (messageField.getText().length() > 0) {
            Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new MessageForm(messageField.getText()), RequestCommands.SEND_MESSAGE.name()));
            messageField.setText("");
        }
    }
}
