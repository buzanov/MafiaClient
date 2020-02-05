package ru.informatics.jojomafia.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.informatics.jojomafia.model.GamePack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePackLoader {
    private static String json;
    private static Map<String, GamePack> gamePacks = new HashMap();

    public static void load() {
        try {
            json = "{\n" +
                    "  \"gamepacks\": [\n" +
                    "    {\n" +
                    "      \"name\": \"JoJo\",\n" +
                    "      \"backgroundImage\": \"img/bg/jojo.png\",\n" +
                    "      \"backCard\": \"img/cards/jojo/card.png\",\n" +
                    "      \"mafiaCard\": \"img/cards/jojo/mafia.png\",\n" +
                    "      \"doctorCard\": \"img/cards/jojo/doctor.jpg\",\n" +
                    "      \"seekerCard\": \"img/cards/jojo/seeker.png\",\n" +
                    "      \"citizenCards\": \"img/cards/jojo/citizen1.png\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"name\": \"Classic Mafia\",\n" +
                    "      \"backgroundImage\": \"img/bg/classic.jpg\",\n" +
                    "      \"backCard\": \"img/cards/classic/back.png\",\n" +
                    "      \"mafiaCard\": \"img/cards/classic/mafia.png\",\n" +
                    "      \"doctorCard\": \"img/cards/classic/doctor.png\",\n" +
                    "      \"seekerCard\": \"img/cards/classic/seeker.png\",\n" +
                    "      \"citizenCards\":\n" +
                    "        \"img/cards/classic/citizen.png\"\n" +
                    "\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n";
//            json = Files.lines(new File("/gamepack/package.json").toPath()).reduce("", (x, y) -> x + y);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readValue(json, ObjectNode.class).get("gamepacks");
            for (JsonNode jsonNode : node) {
                GamePack pack = mapper.convertValue(jsonNode, GamePack.class);
                gamePacks.put(pack.getName(), pack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getList() {
        ArrayList<String> strings = new ArrayList<>();
        gamePacks.entrySet().stream().forEach(stringGamePackEntry -> strings.add(stringGamePackEntry.getKey()));
        return strings;
    }

    public static GamePack getGamePack(String name) {
        System.out.println("Trying to find " + name);
        return gamePacks.get(name);

    }

    public static void printGP() {
        gamePacks.entrySet().forEach(o -> System.out.println(o.getKey() + " : " + o.getValue()));
    }
}


