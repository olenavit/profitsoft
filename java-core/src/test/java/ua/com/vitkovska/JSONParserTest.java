package ua.com.vitkovska;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JSONParserTest {

    @Test
    void shouldThrowIOExceptionWhenInvalidFolderPath() {
        String incorrectPath = "IncorrectPath";

        IOException exception = assertThrows(IOException.class, () -> JSONParser.parsePlayersFromFolder(incorrectPath));

        assertEquals("Invalid folder path: " + incorrectPath, exception.getMessage());
    }

    @Test
    void shouldThrowFileNotFoundExceptionWhenJsonFilesIsAbsent() {
        String folderWithNoJsonFile = "src/test/java/ua/com/vitkovska";

        FileNotFoundException exception = assertThrows(FileNotFoundException.class, () -> JSONParser.parsePlayersFromFolder(folderWithNoJsonFile));

        assertEquals("There are no JSON files in the folder " + folderWithNoJsonFile, exception.getMessage());
    }


    @Test
    void shouldThrowIOExceptionWhenInvalidFileGiven() throws IOException {
        File incorrectJsonFile = new File("src/test/java/ua/com/vitkovska/Json.json");
        incorrectJsonFile.createNewFile();
        try (FileWriter fileWriter = new FileWriter(incorrectJsonFile)) {
            fileWriter.write("incorrect json file");
        }

        IOException exception = assertThrows(IOException.class, () -> JSONParser.parsePlayersFromFolder(incorrectJsonFile.getParent()));

        assertEquals("Error reading file: " + incorrectJsonFile.getName(), exception.getMessage());
        incorrectJsonFile.delete();
    }

    @Test
    void shouldParsePlayersFromFolder() throws Exception {
        String correctPath = "src/test/resources";

        Player player1 = new Player("John", "Doe", "Team A", 2003, "Center, Power Forward");
        Player player2 = new Player("Jane", "Smith", "Team B", 2000, "Shooting guard, Point Guard");
        Player player3 = new Player("Mike", "Johnson", "Team A", 2001, "Small forward, Point Guard");
        List<Player> givenPlayerList = List.of(player1, player2, player3);

        List<Player> parsedPlayerList = JSONParser.parsePlayersFromFolder(correctPath);

        assertEquals(givenPlayerList, parsedPlayerList);
    }


}
