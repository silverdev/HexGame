package com.sam.hex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class fileIO {

    public static String loadTextFile(String file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while(line != null) {
            sb.append(line);
            line = br.readLine();
        }
        String text = sb.toString();
        br.close();
        return text;

    }

    public static String loadTextOrNull(String file) {
        try {
            return loadTextFile(file);
        }
        catch(IOException e) {
            return null;
        }
    }

    public static boolean textToFile(String filePath, String output) {
        File file = new File(filePath);
        return textToFile(file, output);
    }

    public static boolean textToFile(File file, String output) {

        // if file doesn't exists, then create it
        if(!file.exists()) {
            try {
                System.out.print("making file");
                file.createNewFile();
            }
            catch(IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }

        FileWriter writer;
        try {
            writer = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(output);
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
