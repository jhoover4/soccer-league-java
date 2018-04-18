package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

class CreateTeams {
    private List<Map<String, String>> players;
    private String lettersDirPath;

    CreateTeams(List<Map<String, String>> players) {
        this.players = players;
    }

    private void createLetterDir(){
        String newDirStr = System.getProperty("user.dir") + File.separator + "player_letters";
        Path newDirPath = Paths.get(newDirStr);

        try {
            Files.createDirectory(newDirPath);
        }
        catch (IOException ioe){
            System.out.println("Directory already created.");
        }

        this.lettersDirPath = newDirStr;
    }

    private String createLetterPath(String name){
        String [] namesArr = name.split("\\s+");
        String fileName = String.join("_", namesArr).toLowerCase();

        return this.lettersDirPath + File.separator + fileName + ".txt";
    }

    void createLetters() {
        createLetterDir();

        for (Map<String, String> player :
                players) {
            String heading = String.format("Dear %s,%n%nCongratulations, %s will be playing on the %s this season! ",
                    player.get("guardian"), player.get("name"), "Sharks");
            String body = String.format("Please be there for the first practice this Sunday at 1:00pm. " +
                    "Looking forward to a good season.%n%nRegards,%nCoach Jordan");
            String fileName = createLetterPath(player.get("name"));

            try {
                Path path = Paths.get(fileName);
                String text = heading + body;
                byte[] strToBytes = text.getBytes();
                Files.write(path, strToBytes);
            } catch (IOException ioe) {
                System.out.println("File not written properly.");
                ioe.printStackTrace();
                System.exit(-1);
            }
        }
    }
}