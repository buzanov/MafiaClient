package ru.informatics.jojomafia.controllers;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.form.LoginForm;
import ru.informatics.jojomafia.protocol.RequestCommands;
import ru.informatics.jojomafia.protocol.RequestsHandler;
import ru.informatics.jojomafia.protocol.ResponseCommands;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterPageController implements Initializable {

    @FXML
    ImageView backImage;
    @FXML
    ImageView imageView;

    @FXML
    AnchorPane anchor;

    @FXML
    PasswordField password;

    @FXML
    TextField login;

    @FXML
    Button registrationButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector.getMap().put("login", this);
        anchor.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submit();
            }
        });
        imageView.fitWidthProperty().bind(Connector.getStage().widthProperty());
        imageView.fitHeightProperty().bind(Connector.getStage().heightProperty());
    }

    @FXML
    public void submit() {
        if (login.getText().length() >= 5 || password.getText().length() >= 8) {
            LoginForm loginForm = new LoginForm(login.getText(), password.getText());
            Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(loginForm, RequestCommands.SIGN_UP.name()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Login or password is too short.");
            alert.showAndWait();
        }
    }

    @FXML
    public void back(){
        final TranslateTransition scaleTransition = new TranslateTransition(Duration.millis(100));
        scaleTransition.setNode(backImage);
        scaleTransition.setFromX(1);
        scaleTransition.setByX(-1400);
        scaleTransition.playFromStart();
        scaleTransition.setOnFinished((e) -> {
            try {
                Stage stage = (Stage) registrationButton.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pages/home_page.fxml")));
                stage.getScene().setRoot(root);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }


}
