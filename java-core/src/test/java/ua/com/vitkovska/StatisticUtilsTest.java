package ua.com.vitkovska;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.com.vitkovska.entity.Player;
import ua.com.vitkovska.utils.StatisticUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StatisticUtilsTest {
    String attribute;
    static List<Player> givenPlayerList;
    ClassLoader classLoader = getClass().getClassLoader();


    @BeforeAll
    static void shouldSetPlayerList() {
        Player player1 = new Player("John", "Doe", "Team A", 2003, "Center, Power Forward");
        Player player2 = new Player("Jane", "Smith", "Team B", 2000, "Shooting guard, Point Guard");
        Player player3 = new Player("Mike", "Johnson", "Team A", 2001, "Small forward, Point Guard");
        givenPlayerList = List.of(player1, player2, player3);
    }


    @Test
    void shouldThrowIOExceptionWhenInvalidAttributeGiven() {
        attribute = "wrong attribute";

        IOException exception = assertThrows(IOException.class, () -> StatisticUtils.writeStatisticToXML(new ArrayList<>(), attribute));

        assertEquals("Invalid attribute : " + attribute, exception.getMessage());
    }

    @Test
    void shouldCreateCorrectStatisticFileByPosition() throws Exception {
        attribute = "position";
        File testFile = new File(classLoader.getResource("statistics_by_" + attribute + ".xml").getFile());

        File resultFile = StatisticUtils.writeStatisticToXML(givenPlayerList, attribute);

        assertNotNull(resultFile);
        assertTrue(resultFile.exists());
        assertEquals(resultFile.getName(), "statistics_by_" + attribute + ".xml");
        assertEquals(-1L, Files.mismatch(resultFile.toPath(), testFile.toPath()));

        resultFile.delete();

    }

    @Test
    void shouldCreateCorrectStatisticFileByTeam() throws Exception {
        attribute = "team";
        File testFile = new File(classLoader.getResource("statistics_by_" + attribute + ".xml").getFile());

        File resultFile = StatisticUtils.writeStatisticToXML(givenPlayerList, attribute);

        assertNotNull(resultFile);
        assertTrue(resultFile.exists());
        assertEquals(resultFile.getName(), "statistics_by_" + attribute + ".xml");
        assertEquals(-1L, Files.mismatch(resultFile.toPath(), testFile.toPath()));

        resultFile.delete();

    }

    @Test
    void shouldCreateCorrectStatisticFileByYearOfBirth() throws Exception {
        attribute = "year_of_birth";
        File testFile = new File(classLoader.getResource("statistics_by_" + attribute + ".xml").getFile());

        File resultFile = StatisticUtils.writeStatisticToXML(givenPlayerList, attribute);

        assertNotNull(resultFile);
        assertTrue(resultFile.exists());
        assertEquals(resultFile.getName(), "statistics_by_" + attribute + ".xml");
        assertEquals(-1L, Files.mismatch(resultFile.toPath(), testFile.toPath()));

        resultFile.delete();

    }
}
