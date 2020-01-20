package idsh43.configuration.util;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {

    public static void write(String path, List<String> lines) throws IOException {
        FileWriter writer = new FileWriter(new File(path));
        for (String line: lines) {
            writer.write(line);
            writer.write(System.lineSeparator());
        }
        writer.close();
    }

    public static List<String> read(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        List<String> lines = new LinkedList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

}
