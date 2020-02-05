package ru.informatics.jojomafia.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.form.CreateRoomForm;
import ru.informatics.jojomafia.model.GamePack;
import ru.informatics.jojomafia.protocol.RequestCommands;
import ru.informatics.jojomafia.protocol.RequestsHandler;
import ru.informatics.jojomafia.utils.GamePackLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class RoomCreateController implements Initializable {
    @FXML
    ImageView imageView;
    @FXML
    ChoiceBox amountOfPlayers;
    @FXML
    ChoiceBox gamePack;
    @FXML
    TextField roomName;
    @FXML
    Button createRoomButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Integer> list = new ArrayList<>();
        for (int i = 5; i < 11; i++) {
            list.add(i);
        }
        amountOfPlayers.setItems(new ObservableListWrapper(list));
        gamePack.setItems(new ObservableListWrapper(GamePackLoader.getList()));
        Connector.getMap().put("createRoom", this);

    }

    @FXML
    public void createRoom() {
        if (roomName.getText().length() > 0) {
            Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new CreateRoomForm((int) amountOfPlayers.getValue(), (String) gamePack.getValue(), roomName.getText()), RequestCommands.CREATE_ROOM.name()));
            Stage stage = (Stage) createRoomButton.getScene().getWindow();
            stage.close();
        }
    }
}
