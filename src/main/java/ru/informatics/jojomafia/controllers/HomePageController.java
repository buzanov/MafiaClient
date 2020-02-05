package ru.informatics.jojomafia.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.informatics.jojomafia.client.Connector;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    AnchorPane pane;

    @FXML
    ImageView imageView;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;

    @FXML
    public void sentToLoginPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pages/login_page.fxml")));
        stage.getScene().setRoot(root);
    }

    @FXML
    public void sendToRegisterPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pages/register_page.fxml")));
        stage.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector.getMap().put("home", this);
        imageView.fitWidthProperty().bind(Connector.getStage().widthProperty());
        imageView.fitHeightProperty().bind(Connector.getStage().heightProperty());
    }
}
