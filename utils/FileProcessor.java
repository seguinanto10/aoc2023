package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

    public static List<String> readFile(String path) {
        BufferedReader reader;
        List<String> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new java.io.FileReader(path));
            String currentLine;
            while ((currentLine = reader.readLine()) != null){
                lines.add(currentLine);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
