package ua.com.vitkovska;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            if (args == null || args.length < 2) {
                throw new IllegalArgumentException("Invalid arguments were given");
            }
            String folderPath = args[0];
            String attribute = args[1];
            List<Player> players = JSONParser.parsePlayersFromFolder(folderPath);
            StatisticUtils.writeStatisticToXML(players, attribute);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
