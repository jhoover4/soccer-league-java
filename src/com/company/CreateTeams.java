package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CreateTeams {
    private List<Map<String, String>> players;
    private String lettersDirPath;
    private List<Map<String, String>> sharks;
    private List<Map<String, String>> raptors;
    private List<Map<String, String>> dragons;


    CreateTeams(List<Map<String, String>> players) {
        this.players = players;
        this.sharks = new ArrayList<>();
        this.raptors = new ArrayList<>();
        this.dragons = new ArrayList<>();

        List<Map<String, String>> expPlayers = separateExp("yes");
        List<Map<String, String>> nonExpPlayers = separateExp("no");

        addPlayers(expPlayers);
        addPlayers(nonExpPlayers);
    }

    void createTeamsTxt(){
        // deletes file if it already exists
        File file = new File("teams.txt");
        if (file.delete()){
            System.out.println("teams.txt already exists. Rewriting...");
        }

        writeToFile(this.sharks, "Sharks");
        writeToFile(this.raptors, "Raptors");
        writeToFile(this.dragons, "Dragons");
    }

    private void writeToFile(List<Map<String, String>> team, String teamName){
            try(FileWriter fw = new FileWriter("teams.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {
                out.println(teamName);
                for (Map<String, String> player :
                        team) {
                    out.println(player.get("name"));
                }
                out.println();
            } catch (IOException ioe) {
                System.out.println("File not written properly.");
                ioe.printStackTrace();
                System.exit(-1);
            }
    }

    private List<Map<String, String>> separateExp(String expAnswer){
        expAnswer = expAnswer.toLowerCase();
        List<Map<String, String>> returnPlayers = new ArrayList<>();

        for ( Map<String, String> player : this.players ){
            if (player.get("exp").toLowerCase().equals(expAnswer)){
                returnPlayers.add(player);
            }
        }

        return returnPlayers;
    }

    private void addPlayers(List<Map<String, String>> players){
        int increment = Math.round(players.size() / 3);

        this.sharks.addAll(players.subList(0, increment));
        this.raptors.addAll(players.subList(increment, (increment + increment)));
        increment += increment;
        this.dragons.addAll(players.subList(increment, (increment + (increment / 2))));
    }

    private void createLetterDir(){
        String newDirStr = System.getProperty("user.dir") + File.separator + "player_letters";
        Path newDirPath = Paths.get(newDirStr);

        try {
            Files.createDirectory(newDirPath);
        }
        catch (IOException ioe){
            System.out.println("player_letters directory already created.");
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