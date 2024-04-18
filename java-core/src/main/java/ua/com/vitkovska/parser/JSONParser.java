package ua.com.vitkovska.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.vitkovska.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The JSONParser class provides functionality to parse JSON files.
 */
public class JSONParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    /**
     * Parses JSON files from specified folder path.
     *
     * @param folderPath folder, where JSON files to parse are located.
     * @return A list of parsed {@link Player}s
     * @throws FileNotFoundException when there is no JSON files in the folder
     * @throws IOException           when invalid folder path or incorrect file is given
     */
    public static List<Player> parsePlayersFromFolder(String folderPath) throws IOException, FileNotFoundException {
        File[] files = getAllJSONFilesFromFolder(folderPath);

        if (files.length == 0) {
            throw new FileNotFoundException("There are no JSON files in the folder " + folderPath);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        List<Player> allPlayers = Collections.synchronizedList(new ArrayList<>());

        List<Future<?>> futures = new ArrayList<>();
        for (File file : files) {
            Future<?> future = executorService.submit(() -> {
                try {
                    Player[] playersFromFileFromFile = parsePlayersFromFile(file);
                    allPlayers.addAll(Arrays.asList(playersFromFileFromFile));
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            });
            futures.add(future);
        }

        try {
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new IOException(e.getCause().getMessage());
        } finally {
            executorService.shutdown();
        }

        return allPlayers;
    }

    private static File[] getAllJSONFilesFromFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Invalid folder path: " + folderPath);
        }
        return folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
    }

    private static Player[] parsePlayersFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }
            return objectMapper.readValue(jsonContent.toString(), Player[].class);
        } catch (IOException e) {
            throw new IOException("Error reading file: " + file.getName());
        }
    }
}

