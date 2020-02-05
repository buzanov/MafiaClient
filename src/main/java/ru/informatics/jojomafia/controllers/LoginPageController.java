package ru.informatics.jojomafia.controllers;

import javafx.animation.TranslateTransition;
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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    public AnchorPane getAnchor() {
        return anchor;
    }

    @FXML
    ImageView backImage;
    @FXML
    ImageView imageView;
    @FXML
    AnchorPane anchor;

    @FXML
    TextField loginField;

    @FXML
    PasswordField password;

    @FXML
    Button loginButton;


    public void login() {
        if (loginField.getText().length() >= 5 || password.getText().length() > 8) {
            Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new LoginForm(loginField.getText(), password.getText()), RequestCommands.SIGN_IN.name()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Login or password is too short.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector.getMap().put("login", this);
        anchor.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
        imageView.fitWidthProperty().bind(Connector.getStage().widthProperty());
        imageView.fitHeightProperty().bind(Connector.getStage().heightProperty());
    }

    @FXML
    public void back() throws IOException {
        final TranslateTransition scaleTransition = new TranslateTransition(Duration.millis(100));
        scaleTransition.setNode(backImage);
        scaleTransition.setFromX(1);
        scaleTransition.setByX(-1400);
        scaleTransition.playFromStart();
        scaleTransition.setOnFinished((e) -> {
            try {
                Stage stage = Connector.getStage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pages/home_page.fxml")));
                stage.getScene().setRoot(root);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }


}
