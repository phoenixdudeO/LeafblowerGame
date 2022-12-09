package me.herrphoenix.leafblower.io.util;

import java.util.Scanner;

public class FileReader {
    public static String readFile(String path) {
        Scanner reader = new Scanner(FileReader.class.getResourceAsStream(path));

        StringBuilder source = new StringBuilder();

        while(reader.hasNextLine()) {
            source.append(reader.nextLine()).append("\n");
        }

        return source.toString();
    }
}
