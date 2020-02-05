package ru.informatics.jojomafia.protocol;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.informatics.jojomafia.client.Connector;
import ru.informatics.jojomafia.dto.GameInfo;
import ru.informatics.jojomafia.dto.PlayerDto;
import ru.informatics.jojomafia.dto.RoomDto;
import ru.informatics.jojomafia.form.InteractForm;
import ru.informatics.jojomafia.form.RoomForm;
import ru.informatics.jojomafia.model.GamePack;
import ru.informatics.jojomafia.utils.GamePackLoader;
import ru.informatics.jojomafia.utils.JsonParser;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResponseDispatcher {
    public static void doDispatch(Response response) {
        ResponseCommands.Type type = ResponseCommands.Type.valueOf(response.getHeader().getType());
        ResponseCommands.Name name = ResponseCommands.Name.valueOf(response.getHeader().getName());
        if (type.equals(ResponseCommands.Type.ERROR)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) response.getData();
                alert.setContentText((String) hashMap.get("errorMessage"));
                alert.show();
            });
        } else {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getData();
            Stage stage = Connector.getStage();
            switch (name) {
                case SIGN_UP: {
                    try {
                        Connector.getStage().getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/login_page.fxml"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case JOIN_ROOM: {
                    LinkedHashMap<String, Object> pack = (LinkedHashMap<String, Object>) map.get("chosenGamePack");
                    GamePack gamePack = GamePackLoader.getGamePack((String) pack.get("name"));
                    Connector.getMafiaClient().setGamePack(gamePack.getName());
                    System.out.println(Connector.getMafiaClient().getGamePack());
                    Platform.runLater(() -> {
                        try {
                            stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/room_page.fxml"))));
                            String room = (String) map.get("name");
                            ((Label) stage.getScene().lookup("#roomName")).setText(room);
                            Connector.getMafiaClient().setRoomName(room);
                            renderSlotsInRoom(JsonParser.getRoomDto(map));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                }

                case SIGN_IN: {
                    Connector.getMafiaClient().setUser(JsonParser.getUserDto(map));
                    Connector.getMafiaClient().setToken((String) map.get("token"));
                    Platform.runLater(() -> {
                        try {
                            stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/lobby_page.fxml"))));
                            ((Label) stage.getScene().lookup("#nickname")).setText("Nickname: " + Connector.getMafiaClient().getUser().getLogin());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                }
                case RECEIVE_MESSAGE: {
                    Platform.runLater(() -> {
                        TextArea chat = ((TextArea) Connector.getStage().getScene().lookup("#chat"));
                        chat.appendText("\n" + map.get("message"));
                        chat.positionCaret(chat.getText().length());
                    });
                    break;
                }
                case CREATE_ROOM: {
                    LinkedHashMap<String, Object> pack = (LinkedHashMap<String, Object>) map.get("chosenGamePack");
                    GamePack gamePack = GamePackLoader.getGamePack((String) pack.get("name"));
                    System.out.println(gamePack.toString());
                    Connector.getMafiaClient().setGamePack(gamePack.getName());
                    Platform.runLater(() -> {
                        try {
                            stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/room_page.fxml"))));
                            String room = (String) map.get("name");
                            ((Label) stage.getScene().lookup("#roomName")).setText(room);
                            Connector.getMafiaClient().setRoomName(room);
                            Button button = new Button("Start game");
                            button.setOnMouseClicked((e) -> {
                                Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new RoomForm((String) map.get("name")), RequestCommands.START_GAME.name()));
                            });
                            ((VBox) stage.getScene().lookup("#info")).getChildren().add(button);
                            renderSlotsInRoom(JsonParser.getRoomDto(map));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                }
                case RECEIVE_LOBBY_INFO: {
                    Platform.runLater(() -> {
                        ScrollPane scrollPane = (ScrollPane) stage.getScene().lookup("#scrollPane");
                        List<Object> rooms = (List<Object>) map.get("rooms");
                        for (Object roomObject : rooms) {
                            RoomDto roomDto = JsonParser.getRoomDto((LinkedHashMap<String, Object>) roomObject);
                            renderRoomInList(roomDto, scrollPane);
                        }
                        ((Label) stage.getScene().lookup("#nickname")).setText("Nickname: " + Connector.getMafiaClient().getUser().getLogin());
                        ((Label) stage.getScene().lookup("#winCount")).setText("Win Count: " + Connector.getMafiaClient().getUser().getUserStats().getWinCount());
                        ((Label) stage.getScene().lookup("#loseCount")).setText("Lose Count: " + Connector.getMafiaClient().getUser().getUserStats().getLostCount());
                        Double winRate = (Connector.getMafiaClient().getUser().getUserStats().getWinCount() * 100.0) / (Connector.getMafiaClient().getUser().getUserStats().getWinCount() + Connector.getMafiaClient().getUser().getUserStats().getLostCount());
                        ((Label) stage.getScene().lookup("#winRate")).setText("Win rate: " + winRate.intValue() + "%");
                    });
                    break;
                }
                case START_GAME: {
                    GamePack gamePack = GamePackLoader.getGamePack(Connector.getMafiaClient().getGamePack());
                    System.out.println("START_GAME : " + Connector.getMafiaClient().getGamePack());
                    Platform.runLater(() -> {
                        try {
                            stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/game_page.fxml"))));
                            System.out.println(gamePack.getBackgroundImage());
                            stage.getScene().getRoot().setStyle("-fx-background-image: url(" + gamePack.getBackgroundImage() + ")");
//                            stage.getScene().getRoot().setStyle("-fx-background-size:cover");
                        } catch (IOException e) {
                            throw new IllegalArgumentException(e);
                        }
                    });
                    break;
                }
                case GAME_INFO: {
                    renderGameScene(JsonParser.getPlayerDtoList(map),
                            GameInfo.State.valueOf((String) map.get("currentState")),
                            GameInfo.Activity.valueOf((String) map.get("currentActivity")));

                    break;
                }
                case PLAYER_INFO: {
                    Connector.getMafiaClient().setRole((String) map.get("role"));
                    break;
                }

                case UPDATE_ROOM_INFO: {
                    RoomDto roomDto = JsonParser.getRoomDto(map);
                    renderSlotsInRoom(roomDto);
                    break;
                }
                case GAME_STATUS: {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText((String) map.get("message"));
                        alert.show();
                    });
                    break;
                }
            }
        }
    }

    private static void renderGameScene(List<PlayerDto> players, GameInfo.State state, GameInfo.Activity activity) {
        GamePack gamePack = GamePackLoader.getGamePack(Connector.getMafiaClient().getGamePack());
       /* if (state.equals(GameInfo.State.NIGHT)) {
            Platform.runLater(() -> Connector.getStage().getScene().getRoot().setStyle("background-image: " +
                    "radial-gradient(rgb(255, 255, 255), rgb(232, 232, 232), rgb(209, 209, 209), rgb(185, 185, 185), rgb(162, 162, 162), rgb(139, 139, 139)," +
                    " rgb(116, 116, 116), rgb(93, 93, 93), rgb(70, 70, 70), rgb(46, 46, 46), rgb(23, 23, 23), rgb(0, 0, 0)), url(" + gamePack.getBackgroundImage() + ")"));
        } else {
            Platform.runLater(() -> Connector.getStage().getScene().getRoot().setStyle("background-image: " +
                    "radial-gradient(rgb(255, 255, 255), rgb(232, 232, 232), rgb(209, 209, 209), rgb(185, 185, 185), rgb(162, 162, 162), rgb(139, 139, 139), " +
                    "rgb(116, 116, 116), rgb(93, 93, 93), rgb(70, 70, 70), rgb(46, 46, 46), rgb(23, 23, 23), rgb(0, 0, 0)), url(" + gamePack.getBackgroundImage() + ")"));
        }*/
        PlayerDto p = players.stream().filter(o -> o.getName().equals(Connector.getMafiaClient().getUser().getLogin())).collect(Collectors.toList()).get(0);
        HBox playerBox = (HBox) Connector.getStage().getScene().lookup("#MBHbox");
        Platform.runLater(() -> playerBox.getChildren().add(renderPlayer(p, true)));
        final List<PlayerDto> playerDtos = players.stream().filter(playerDto -> !playerDto.getName().equals(Connector.getMafiaClient()
                .getUser().getLogin())).sorted(Comparator.comparing(PlayerDto::getName)).collect(Collectors.toList());
        System.out.println(playerDtos.size());
        Platform.runLater(() -> {
            int i = 0;
            if (playerDtos.size() != 0)
                for (PlayerDto player : playerDtos) {
                    if (i < players.size()) {
                        i++;
                        switch (i) {
                            case 1: {
                                HBox hBox = (HBox) Connector.getStage().getScene().lookup("#LBHBox");
                                hBox.getChildren().add(renderPlayer(player, false));
                                break;
                            }
                            case 2: {
                                HBox hBox = (HBox) Connector.getStage().getScene().lookup("#RBHbox");
                                hBox.getChildren().add(renderPlayer(player, false));
                                break;
                            }
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9: {
                                HBox hBox = (HBox) Connector.getStage().getScene().lookup("#topHBox");
                                hBox.getChildren().add(renderPlayer(player, false));
                                break;
                            }
                        }
                    } else {


                    }
                }

        });
    }

    public static VBox renderPlayer(PlayerDto player, boolean isMine) {
        System.out.println(player.toString());
        GamePack gamePack = GamePackLoader.getGamePack(Connector.getMafiaClient().getGamePack());
        VBox vBox = new VBox();
        vBox.setPrefHeight(325);
        vBox.setPrefWidth(220);
        ImageView image;
        Image img;
        if (player.isAlive() && !isMine) {
            img = new Image(gamePack.getBackCard(), 220, 300, false, false);
        } else {
            img = new Image(gamePack.getCard(player.getRole()), 220, 300, false, false);
        }
        image = new ImageView(img);
        if (!isMine)
        image.setOnMouseClicked(e -> {
            Connector.getMafiaClient().sendJsonMessage(
                    RequestsHandler.createRequest(
                            new InteractForm(Connector.getMafiaClient().getRoomName(), player.getName()), RequestCommands.INTERACT_WITH.name()));
        });
        image.resize(220, 300);
        Label label = new Label(player.getName());
        label.setPrefHeight(25);
        label.setPrefWidth(220);
        label.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(image);
        vBox.getChildren().add(label);
        return vBox;
    }

    private static void renderRoomInList(RoomDto dto, ScrollPane scrollPane) {
        Platform.runLater(() -> {
            double height = 40;
            HBox hbox = new HBox();
            hbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            hbox.setBackground(new Background(new BackgroundFill(Color.color(1, 1, 1), CornerRadii.EMPTY, new Insets(0, 0, 0, 0))));
            hbox.setPrefHeight(height);
            hbox.setPrefWidth(scrollPane.getWidth() - 2);
            hbox.setPadding(new Insets(7, 7, 7, 7));
            VBox label = new VBox();
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPrefHeight(height);
            label.setPadding(new Insets(0, 0, 0, 5));
            label.setPrefWidth(scrollPane.getWidth() / 4);
            label.getChildren().add(new Label(dto.getName()));
            hbox.getChildren().add(label);
            VBox gamePack = new VBox();
            gamePack.setAlignment(Pos.CENTER);
            gamePack.setPrefHeight(height);
            gamePack.setPrefWidth(scrollPane.getWidth() / 4);
            gamePack.getChildren().add(new Label("Classic game pack"));
            hbox.getChildren().add(gamePack);
            VBox players = new VBox();
            players.alignmentProperty().setValue(Pos.CENTER);
            players.setPrefWidth(scrollPane.getWidth() / 4);
            players.setPrefHeight(height);
            players.getChildren().add(new Label("Players: " + (dto.getUserList() == null ? 0 : dto.getUserList().size()) + "/" + dto.getMaxPlayers()));
            hbox.getChildren().add(players);
            VBox button = new VBox();
            button.setPrefWidth(scrollPane.getWidth() / 4);
            button.setPrefHeight(height);
            Button join = new Button();
            button.setAlignment(Pos.CENTER_RIGHT);
            button.setPadding(new Insets(0, 5, 0, 0));
            join.setText("Join");
            join.setOnMouseClicked((e) -> {
                Connector.getMafiaClient().sendJsonMessage(RequestsHandler.createRequest(new RoomForm(dto.getName()), RequestCommands.JOIN_ROOM.name()));
            });
//        join.setOnMouseClicked((e) -> {
//            try {
//                Connector.getStage().getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResponseDispatcher.class.getClassLoader().getResource("pages/room_page.fxml"))));
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });

            final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
            fadeIn.setNode(hbox);
            fadeIn.setToValue(1);
            hbox.setOnMouseEntered(e -> fadeIn.playFromStart());

            final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
            fadeOut.setNode(hbox);
            fadeOut.setToValue(0.5);
            hbox.setOnMouseExited(e -> fadeOut.playFromStart());

            hbox.setOpacity(0.5);


            button.getChildren().add(join);
            hbox.getChildren().add(button);

            FlowPane flowPane = (FlowPane) scrollPane.getContent();
            flowPane.getChildren().add(hbox);
            scrollPane.setContent(flowPane);
        });
    }

    private static void renderSlotsInRoom(RoomDto dto) {
//        dto.getUserList().stream().forEach(System.out::println);
        Platform.runLater(() -> {
            VBox box = (VBox) Connector.getStage().getScene().lookup("#players");
            box.getChildren().clear();
            for (int i = 0; i < dto.getUserList().size(); i++) {
                double height = 60;
                HBox label = new HBox();
                label.setBorder(new Border(new BorderStroke(Color.WHITE,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                label.setAlignment(Pos.CENTER_LEFT);
                label.setPrefHeight(height);
                label.setPadding(new Insets(10, 10, 10, 10));
                label.setPrefWidth(box.getWidth() / 4);
                Label label1 = new Label(dto.getUserList().get(i).getLogin());
                label1.setFont(new Font(40));
                label1.setTextFill(Color.WHITE);
                label.getChildren().add(label1);
                box.getChildren().add(label);
            }
        });
    }







}

