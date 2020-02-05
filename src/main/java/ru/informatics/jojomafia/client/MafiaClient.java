package ru.informatics.jojomafia.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.informatics.jojomafia.dto.UserDto;
import ru.informatics.jojomafia.protocol.Request;
import ru.informatics.jojomafia.protocol.RequestCommands;
import ru.informatics.jojomafia.protocol.Response;
import ru.informatics.jojomafia.protocol.ResponseDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MafiaClient {

    private Socket clientSocket;
    private PrintWriter clientOutput;
    private BufferedReader clientInput;
    private String token;
    private ObjectMapper objectMapper;
    private UserDto user;
    private String role;
    private String gamePack;
    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getGamePack() {
        return gamePack;
    }

    public void setGamePack(String gamePack) {
        this.gamePack = gamePack;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            objectMapper = new ObjectMapper();
            clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(receiveMessagesTask).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Runnable receiveMessagesTask = () -> {
        while (true) {
            try {
                String line = clientInput.readLine();
                System.out.println(line);
                Response response = objectMapper.readValue(line, Response.class);
                ResponseDispatcher.doDispatch(response);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    };

    public void sendMessage(String message) {
        String[] wordsFromMessage = message.split(" ");
        Request request = new Request();
        request.setHeader(wordsFromMessage[0]);
        RequestCommands command = RequestCommands.valueOf(request.getHeader());
        switch (command) {
            case SIGN_IN:
            case SIGN_UP: {
                request.setPayloadParameter("login", wordsFromMessage[1]);
                request.setPayloadParameter("password", wordsFromMessage[2]);
                break;
            }
            case CREATE_ROOM: {
                request.setPayloadParameter("roomName", wordsFromMessage[1]);
                request.setPayloadParameter("maxPlayers", wordsFromMessage[2]);
                request.setPayloadParameter("chosenGamePack", wordsFromMessage[3]);
                break;
            }
            case JOIN_ROOM:
            case LEAVE_ROOM:
            case START_GAME: {
                break;
            }
        }
        request.setToken(token);
        try {
            clientOutput.println(objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void sendJsonMessage(Request request) {
        try {
            System.out.println("=========CLIENT MESSAGE===========");
            System.out.println(request.getHeader() + " " + request.getPayload());
            request.setToken(token);
            clientOutput.println(objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
