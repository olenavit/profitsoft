package ua.com.vitkovska;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The StatisticUtils class provides functionality to make XML files with statistic.
 */
public class StatisticUtils {
    /**
     * Create XML statistic file.
     *
     * @param players   list of {@link Player}s based on which statistics are generated.
     * @param attribute based on which statistics are generated(position, team, year_of_birth).
     * @return A list of parsed {@link Player}s
     * @throws IOException when invalid attribute is given
     */
    public static File writeStatisticToXML(List<Player> players, String attribute) throws Exception {
        Map<String, Long> statistics = calculateAttributeStatistic(players, attribute);
        Document doc = buildDocumentWithStatistic(statistics);
        DOMSource source = new DOMSource(doc);
        String filename = "statistics_by_" + attribute + ".xml";
        File file = new File(filename);

        try (FileWriter writer = new FileWriter(file)) {
            StreamResult result = new StreamResult(writer);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
            return file;
        }
    }

    private static Document buildDocumentWithStatistic(Map<String, Long> statistics) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("statistics");
        doc.appendChild(rootElement);
        statistics.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> {
                    Element item = doc.createElement("item");
                    Element value = doc.createElement("value");
                    value.appendChild(doc.createTextNode(entry.getKey()));
                    item.appendChild(value);
                    Element count = doc.createElement("count");
                    count.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
                    item.appendChild(count);
                    rootElement.appendChild(item);
                });
        return doc;
    }

    private static Map<String, Long> calculateAttributeStatistic(List<Player> players, String attribute) throws IOException {
        return switch (attribute) {
            case "year_of_birth" -> statisticByYearOfBirth(players);
            case "team" -> statisticByTeam(players);
            case "position" -> statisticByPosition(players);
            default -> throw new IOException("Invalid attribute : " + attribute);
        };
    }

    private static Map<String, Long> statisticByYearOfBirth(List<Player> players) {
        return players.stream().collect(Collectors.groupingBy(player -> String.valueOf(player.getYearOfBirth()), Collectors.counting()));
    }

    private static Map<String, Long> statisticByTeam(List<Player> players) {
        return players.stream().collect(Collectors.groupingBy(Player::getTeam, Collectors.counting()));
    }

    private static Map<String, Long> statisticByPosition(List<Player> players) {
        return players.stream()
                .flatMap(player -> player.getPosition().stream())
                .collect(Collectors.groupingBy(position -> position, Collectors.counting()));
    }
}
