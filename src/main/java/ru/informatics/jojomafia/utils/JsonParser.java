package ru.informatics.jojomafia.utils;

import ru.informatics.jojomafia.dto.PlayerDto;
import ru.informatics.jojomafia.dto.RoomDto;
import ru.informatics.jojomafia.dto.UserDto;
import ru.informatics.jojomafia.dto.UserStats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonParser {
    public static UserDto getUserDto(LinkedHashMap<String, Object> map) {
        LinkedHashMap<String, Integer> userStats = (LinkedHashMap<String, Integer>) map.get("userStats");
        return new UserDto((long) (int) map.get("id"), (String) map.get("login"),
                new UserStats((long) (int) userStats.get("id"), (long) (int) userStats.get("winCount"),
                        (long) (int) userStats.get("lostCount"), (long) (int) userStats.get("userId")), null);
    }

    public static RoomDto getRoomDto(LinkedHashMap<String, Object> roomObject) {
        RoomDto roomDto = new RoomDto();
        roomDto.setName((String) roomObject.get("name"));
        roomDto.setHasGameStarted((boolean) roomObject.get("hasGameStarted"));
        List<Object> users = (List<Object>) roomObject.get("userList");
        List<UserDto> usersDto = new ArrayList<>();
        if (users != null && users.size() != 0) {
            for (Object user : users) {
                usersDto.add(new UserDto(null, (String) ((LinkedHashMap<String, Object>) user).get("login"), null, null));
            }
        }
        roomDto.setUserList(usersDto);
//roomDto.setChosenGamePack((GamePackDto) roomObjectMap.get("chosenGamePack"));
        roomDto.setMaxPlayers((int) roomObject.get("maxPlayers"));
        return roomDto;
    }

    public static List<PlayerDto> getPlayerDtoList(LinkedHashMap<String, Object> map) {
        List<Object> players = (List<Object>) map.get("players");
        List<PlayerDto> playerList = new ArrayList<>();
        if (players != null && players.size() != 0) {
            for (Object player : players) {
                LinkedHashMap<String, Object> p = (LinkedHashMap<String, Object>) player;
                PlayerDto playerDto = new PlayerDto();
                playerDto.setAlive((boolean) p.get("alive"));
                playerDto.setName((String) p.get("name"));
                playerDto.setRole((String) p.get("role"));
                playerList.add(playerDto);
            }
        }
        return playerList;
    }


}
