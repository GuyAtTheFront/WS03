package myapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Database {

    public static final Path BASE_DIR  = Paths.get("src/myapp/cartdb");

    // public static void main(String[] args) {
    //     //setupDirectory();
    //     //setupFile("one.db");
    //     //List<String> contents = readFile("one.db");
    //     setupFile("one.db");
    // }

    public static void setupDirectory() {
        if(!BASE_DIR.toFile().exists()) {
            // create dir
            try {
                Files.createDirectories(BASE_DIR);
                System.out.println("directory created");
            } catch (Exception e) {
                System.out.println("Error creating directory");
            }
        } 
        System.out.println("Ready to go, dir already exist");  
    }

    private static void setupFile(String fname) {
        
        Path fp = BASE_DIR.resolve(fname);

        if (fp.toFile().exists()) {
            System.out.println("Ready to go, file already exist");
            return;
        } 

        try {
            Files.createFile(fp);
            System.out.println("File crated");
        } catch (Exception e) {
            System.out.println("Error creating File, " + e.getMessage());
        }
        return;

    }

    public static List<String> loadUser(String user) {
        try {
            setupFile(user + ".db");
            return Files.readAllLines(BASE_DIR.resolve(user + ".db"));
        } catch(IOException e) {
            System.out.println("error reading file, " + e.getMessage());
            return null;
        }
    }

    public static void listUsers() {

        String[] files = BASE_DIR.toFile().list();
        if (files.length == 0) {
            System.out.println("There are no registered users");
            return;
        }

        System.out.println("Registered Users: ");
        for(int i = 0; i < files.length; i++) {
            System.out.println((i+1) + ". " + files[i].substring(0, files[i].length() - 3));
        }
        return;
    }

    public static void overwriteFile(String fname, String contents) {
        try {
            Files.write(BASE_DIR.resolve(fname), contents.getBytes());
        } catch (Exception e) {}
        return;
    }
        
}
